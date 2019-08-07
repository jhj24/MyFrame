package com.zgdj.djframe.activity.other

import android.Manifest
import android.os.Bundle
import android.view.View
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_pdf_viewer.*
import org.json.JSONException
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File

class PDFViewerActivity : BaseNormalActivity() {

    private var loadUrl: String? = null
    private var fileName: String? = null//文件名称
    private var fileType: String? = null//文件类型
    private var fileId: String? = null//文件ID
    private var isDownload = true

    private var loadingDialog: LoadingDialog? = null


    override fun initData(bundle: Bundle?) {
        if (bundle != null) {
            loadUrl = bundle.getString("key_url")
            fileName = bundle.getString("file_name")
            fileId = bundle.getString("file_id")
            isDownload = bundle.getBoolean("is_download", true)
            fileType = fileName?.getFileSuffix()
        }

    }

    override fun bindLayout(): Int {
        return R.layout.activity_pdf_viewer
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        loadingDialog = LoadingDialog(this)
        loadingDialog?.setSpinnerType(0)
        title = "查看PDF文档"

    }

    override fun doBusiness() {
        checkPermission()

    }

    override fun onWidgetClick(view: View?) {
    }


    //检查权限
    private fun checkPermission() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        MPermissionUtils.requestPermissionsResult(this, 2, permissions,
                object : MPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        //下载按钮
                        if (isDownload) {
                            setRightOnclick("下载") {
                                downloadPDF()
                            }
                        }

                        val path = FileUtils.getSDPath("download") + fileName
                        val file = File(path)
                        if (fileType == "pdf") {
                            if (file.exists()) {
                                displayFromFile1(file)
                            } else {
                                downloadPDF(isRead = true) {
                                    displayFromFile1(it)
                                }
                            }
                        } else {
                            val newPath = path.substring(0, path.lastIndexOf(".")) + ".pdf"
                            if (File(newPath).exists()) {
                                displayFromFile1(File(newPath))
                            } else {
                                transform2PdfTask()
                            }
                        }
                    }

                    override fun onPermissionDenied() {
                        toast("内存权限请求失败")
                    }
                })


    }

    /**
     * 获取打开网络的pdf文件
     *
     * @param fileUrl
     * @param fileName
     */
    private fun displayFromFile1(fileUrl: File?) {
        if (fileUrl?.exists() == false) {
            toast("读取失败")
            return
        }
        pdfView.fromFile(fileUrl)
                .load()
    }

    /**
     * 下载文件
     */
    private fun downloadPDF(downloadUlr: String? = loadUrl, isRead: Boolean = false, body: (File) -> Unit = {}) {
        Logger.w("loadUrl:$downloadUlr")
        if (RegexUtils.isNull(downloadUlr)) {
            ToastUtils.showShort("下载失败")
            return
        }

        val requestParams = RequestParams(downloadUlr)
        // 为RequestParams设置文件下载后的保存路径
        val path = if (isRead) {
            FileUtils.getSDPath("download") + fileName?.substring(0, fileName?.lastIndexOf(".")
                    ?: 0) + "." + fileName?.getFileSuffix()
        } else {
            FileUtils.getSDPath("download") + fileName
        }
        requestParams.saveFilePath = path
        // 下载完成后自动为文件命名
        requestParams.isAutoRename = false
        x.http().get(requestParams, object : Callback.ProgressCallback<File> {

            override fun onSuccess(result: File) {
                Logs.debug("下载成功")
                toast(if (isRead) "加载成功" else "下载成功  \n  下载路径：" + Constant.BASE_PATH)
                body(result)
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {
                Logs.debug("下载失败")
                toast(if (isRead) "加载失败" else "下载失败")

                //                    mProgressDialog.dismiss();
            }

            override fun onCancelled(cex: Callback.CancelledException) {
                Logs.debug("取消下载")
                //                    mProgressDialog.dismiss();
            }

            override fun onFinished() {
                Logs.debug("结束下载")
                loadingDialog?.dismiss()
            }

            override fun onWaiting() {

            }

            override fun onStarted() {
                loadingDialog?.setMessage(if (isRead) "正在加载..." else "正在下载...")
                loadingDialog?.show()
            }

            override fun onLoading(total: Long, current: Long, isDownloading: Boolean) {
                // 当前的下载进度和文件总大小
                Logs.debug("正在下载中：" + total.toInt() + ",current:" + current)
            }
        })

    }


    /**
     * 转换成PDF格式
     */
    private fun transform2PdfTask() {
        if (RegexUtils.isNull(fileId)) return
        val params = RequestParams(Constant.URL_TRANSFORM_TO_PDF)
        params.addHeader("token", token)
        params.addBodyParameter("file_id", fileId) //文件id
        x.http().post(params, object : Callback.CommonCallback<String> {

            override fun onSuccess(result: String) {
                if (!RegexUtils.isNull(result)) {
                    Logs.debug("格式转换：$result")
                    try {
                        val jsonObject = JSONObject(result)

                        if (jsonObject.has("code")) {
                            if (jsonObject.getInt("code") == 1) {//成功
                                if (jsonObject.has("path")) {
                                    val path = jsonObject.getString("path")
                                    if (!path.startsWith("http://")) {
                                        downloadPDF(Constant.BASE_URL + path, true) {
                                            displayFromFile1(it)
                                        }
                                    } else {
                                        downloadPDF(path, true) {
                                            displayFromFile1(it)
                                        }
                                    }

                                }
                            } else if (jsonObject.getInt("code") == -2) {
                                ToastUtils.showShort(Constant.TOKEN_LOST)
                            }
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }


                }

            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {

            }

            override fun onCancelled(cex: Callback.CancelledException) {

            }

            override fun onFinished() {

            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
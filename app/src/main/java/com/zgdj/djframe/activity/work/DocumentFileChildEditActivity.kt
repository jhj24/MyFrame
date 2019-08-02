package com.zgdj.djframe.activity.work

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.DocumentFileBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_document_file_child_edit.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class DocumentFileChildEditActivity : BaseNormalActivity() {
    private var path: String? = null
    private lateinit var loadingDialog: LoadingDialog //loading dialog
    private var code: String? = null
    private var id: Int = -1
    val list = arrayListOf<String>()
    private var cameraUtils: CameraUtils? = null //相机utils

    override fun initData(bundle: Bundle?) {
        code = bundle?.getString("code")
        id = bundle?.getInt("id", -1) ?: -1
        cameraUtils = CameraUtils(this)

    }

    override fun bindLayout(): Int {
        return R.layout.activity_document_file_child_edit
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        tv_code.text = "$code-"
        tv_file.setOnClickListener {
            checkPermission()
        }

        loadingDialog = LoadingDialog(this)
        loadingDialog.setMessage("正在上传...")
    }

    override fun doBusiness() {
        btn_commit.setOnClickListener {
            if (et_name.text.isNullOrBlank()) {
                toast("请输入图名")
                return@setOnClickListener
            } else if (et_code.text.isNullOrBlank()) {
                toast("请输入图号")
                return@setOnClickListener
            } else if (path.isNullOrBlank()) {
                toast("请选择图纸")
                return@setOnClickListener
            }


            val name = SPUtils.getInstance().getString(Constant.KEY_USER_NICK)
            val nameId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)
            if (!loadingDialog.isShowing) loadingDialog.show()
            val params = RequestParams(Constant.URL_WORK_DOCUMENT_PICTURE_ADD)
            params.addHeader("id", nameId.toString())
            params.addBodyParameter("id", id.toString())
            params.addBodyParameter("picture_name", et_name.text.toString())//用户id
            params.addBodyParameter("picture_number", tv_code.text.toString() + et_code.text.toString())//用户id
            params.addBodyParameter("file", File(path.toString()))//用户id
            params.addBodyParameter("current_nickname", name)//用户id
            x.http().post(params, object : Callback.CommonCallback<String> {
                override fun onFinished() {
                    if (loadingDialog.isShowing) loadingDialog.dismiss()
                }

                override fun onSuccess(result: String?) {
                    val jObject = JSONObject(result)
                    val msg = jObject.getString("msg")
                    val code = jObject.getInt("code")
                    toast(msg)
                    if (code == 1) {
                        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
                        val intent = Intent()
                        val data = DocumentFileBean.DataBean()
                        data.picture_name = et_name.text.toString()
                        data.picture_number = tv_code.text.toString() + et_code.text.toString()
                        data.owner = name
                        data.create_time = sdf.format(Date())
                        intent.putExtra("data", data)
                        setResult(Activity.RESULT_OK, intent)
                        NotifyListenerMangager.getInstance().nofityContext("refresh", "DocumentFileActivity")
                        finish()
                    }
                }

                override fun onCancelled(cex: Callback.CancelledException?) {
                }

                override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                }

            })
        }
    }

    override fun onWidgetClick(view: View?) {
    }

    private fun checkPermission() {
        MPermissionUtils.requestPermissionsResult(this, 1,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                object : MPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        selectFile()
                    }

                    override fun onPermissionDenied() {
                        ToastUtils.showShort("没有权限，无法使用相机功能！")
                    }

                })
    }

    fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件上传"), 345)
        } catch (ex: android.content.ActivityNotFoundException) {
            toast("请安装一个文件管理器.")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 345 && resultCode == Activity.RESULT_OK) {//345选择文件的请求码
            if (data?.data == null) {
                return
            }
            path = FileUtils.getPath(this, data.data)
            if (path == null) {
                toast("选择文件失败，请重试")
                return
            }
            tv_file.text = path?.getFileName()
        }
    }
}
package com.zgdj.djframe.utils

import android.app.Activity
import android.content.Context
import android.widget.Button
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.view.CustomerDialog
import com.zgdj.djframe.view.LoadingDialog
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File

object DownloadUtils {

    fun download(mContext: Context, fileName: String, path: String) {
        val loadingDialog = LoadingDialog(mContext)
        loadingDialog.setSpinnerType(0)
        loadingDialog.show()
        val requestParams = RequestParams(Constant.BASE_URL + path)
        val filePath = FileUtils.getSDPath("download") + fileName
        requestParams.isAutoRename = false//取消自动命名
        requestParams.saveFilePath = filePath
        x.http().get(requestParams, object : Callback.ProgressCallback<File> {
            override fun onWaiting() {}

            override fun onStarted() {
                loadingDialog.setMessage("下载中...")
            }

            override fun onLoading(total: Long, current: Long, isDownloading: Boolean) {}

            override fun onSuccess(result: File) {
                ToastUtils.showShort("下载完成！\n 下载路径：$filePath")
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {
                ex.printStackTrace()
                ToastUtils.showShort("下载失败，请检查网络和SD卡！")
            }

            override fun onCancelled(cex: Callback.CancelledException) {}

            override fun onFinished() {
                loadingDialog.dismiss()
            }
        })
    }

    fun delete(context: Context, path: String, msg: String, vararg pairs: Pair<String, String>, body: () -> Unit) {
        val dialog = CustomerDialog(context as Activity, R.layout.dialog_form_approval)
        dialog.setDlgIfClick(true)
        dialog.setOnCustomerViewCreated { window, _ ->
            val title = window.findViewById<TextView>(R.id.dialog_text_title)
            val refuse = window.findViewById<Button>(R.id.dialog_btn_refuse)
            val ok = window.findViewById<Button>(R.id.dialog_btn_ok)
            title.text = msg
            refuse.setOnClickListener {
                dialog.dismissDlg()
            }
            ok.setOnClickListener {
                dialog.dismissDlg()
                deleteTask(path, *pairs, body = body)
            }
        }
        dialog.showDlg()
    }

    private fun deleteTask(path: String, vararg pairs: Pair<String, String>, body: () -> Unit) {

        val params = RequestParams(path)
        val token = SPUtils.getInstance().getString(Constant.KEY_TOKEN)
        params.addHeader("token", token)
        pairs.forEach {
            params.addBodyParameter(it.first, it.second)
        }
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val jsonObjects = JSONObject(result)
                val code = jsonObjects.getInt("code")
                val msg = jsonObjects.getString("msg")
                ToastUtils.showShort(msg)
                if (code == 1) {//删除成功
                    body()
                } else {
                    ToastUtils.showShort(Constant.TOKEN_LOST)
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })
    }
}
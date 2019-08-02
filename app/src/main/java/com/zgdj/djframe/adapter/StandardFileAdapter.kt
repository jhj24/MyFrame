package com.zgdj.djframe.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.work.StandardFileEditActivity
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.bean.StandardFileBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.CustomerDialog
import com.zgdj.djframe.view.LoadingDialog
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File

/**
 * description:实时进度adapter
 * author: Created by Mr.Zhang on 2018/7/23
 */
class StandardFileAdapter(list: MutableList<StandardFileBean.DataBean>?, layoutId: Int, val key: Int) :
        SingleAdapter<StandardFileBean.DataBean>(list, layoutId) {
    private var dialog: CustomerDialog? = null


    @SuppressLint("SetTextI18n")
    override fun bind(holder: BaseViewHolder?, data: StandardFileBean.DataBean?) {
        holder?.let {
            val title = it.getView<TextView>(R.id.item_tv_progress_bids) //标题
            val newspaper = it.getView<TextView>(R.id.item_tv_progress_newspaper) //填报人
            val date = it.getView<TextView>(R.id.item_tv_progress_date) //日期
            val reMarks = it.getView<TextView>(R.id.item_tv_progress_remarks) //备注
            val see = it.getView<TextView>(R.id.item_tv_progress_see) //查看
            val edit = it.getView<TextView>(R.id.item_tv_progress_edit)
            val delete = it.getView<TextView>(R.id.item_tv_progress_delete) //删除
            val standard = it.getView<TextView>(R.id.item_tv_standard)

            // 赋值
            if (data != null) {
                title.text = data.standard_name
                newspaper.text = "编号：${data.standard_number}"
                date.text = "日期：${data.material_date}"
                reMarks.text = "备注：${data.remark}"
                standard.text = "替代标准：${data.alternate_standard}"
                //操作
                see.setOnClickListener {
                    //下载
                    if (data.path.isNullOrBlank()) {
                        mContext.toast("没有文件")
                    } else {
                        downloadFile(data.path)
                    }
                }
                edit.setOnClickListener {
                    val intent = Intent(mContext, StandardFileEditActivity::class.java)
                    intent.putExtra("nodeId", key)
                    intent.putExtra("data", data)
                    (mContext as Activity).startActivityForResult(intent, 1000)
                }


                delete.setOnClickListener {
                    //删除
                    if (dialog == null) {
                        dialog = CustomerDialog(mContext as Activity, R.layout.dialog_form_approval)
                        dialog?.setDlgIfClick(true)
                    }
                    dialog?.setOnCustomerViewCreated { window, _ ->
                        val title = window.findViewById<TextView>(R.id.dialog_text_title)
                        val refuse = window.findViewById<Button>(R.id.dialog_btn_refuse)
                        val ok = window.findViewById<Button>(R.id.dialog_btn_ok)
                        title.text = "是否删除${data.standard_name}？"
                        refuse.setOnClickListener {
                            // 否
                            dialog?.dismissDlg()
                        }
                        ok.setOnClickListener {
                            // 是
                            dialog?.dismissDlg()
                            delProgressTask(data.id.toString())
                        }
                    }

                    dialog?.showDlg()
                }


            }

        }

    }

    //下载文件
    private fun downloadFile(url: String) {
        val loadingDialog = LoadingDialog(mContext)
        loadingDialog.setSpinnerType(0)
        val requestParams = RequestParams(Constant.BASE_URL + url)
        val filePath = FileUtils.getSDPath("download") + url.getFileName()
        requestParams.isAutoRename = false//取消自动命名
        requestParams.saveFilePath = filePath
        x.http().get(requestParams, object : Callback.ProgressCallback<File> {
            override fun onWaiting() {}

            override fun onStarted() {
                loadingDialog.show()
                loadingDialog.setMessage("下载中...")
            }

            override fun onLoading(total: Long, current: Long, isDownloading: Boolean) {}

            override fun onSuccess(result: File) {
                ToastUtils.showShort("下载完成！\n 下载路径：" + filePath)
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

    //删除item 请求
    private fun delProgressTask(id: String) {
        val params = RequestParams(Constant.URL_WORK_STANDARD_DELETE)
        val token = SPUtils.getInstance().getString(Constant.KEY_TOKEN)
        params.addHeader("token", token)
        params.addBodyParameter("id", id)// 表单 id
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val jsonObjects = JSONObject(result)
                val code = jsonObjects.getInt("code")
                val msg = jsonObjects.getString("msg")
                ToastUtils.showShort(msg)
                if (code == 1) {//删除成功
                    NotifyListenerMangager.getInstance().nofityContext("refresh",
                            "StandardFileActivity")
                } else if (code == -2) {
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


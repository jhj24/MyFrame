package com.zgdj.djframe.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.work.DocumentDownloadHistoryListActivity
import com.zgdj.djframe.activity.work.DocumentFileChildEditActivity
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.bean.DocumentFileBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.model.FileModel
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.CustomerDialog
import org.json.JSONException
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * description:实时进度adapter
 * author: Created by Mr.Zhang on 2018/7/23
 */
class DocumentFileChildAdapter(list: MutableList<DocumentFileBean.DataBean>?, layoutId: Int) :
        SingleAdapter<DocumentFileBean.DataBean>(list, layoutId) {
    private var dialog: CustomerDialog? = null


    @SuppressLint("SetTextI18n")
    override fun bind(holder: BaseViewHolder?, data: DocumentFileBean.DataBean?) {
        holder?.let {
            val title = it.getView<TextView>(R.id.item_tv_progress_bids) //标题
            val newspaper = it.getView<TextView>(R.id.item_tv_progress_newspaper) //填报人
            val date = it.getView<TextView>(R.id.item_tv_progress_date) //日期
            val reMarks = it.getView<TextView>(R.id.item_tv_progress_remarks) //备注
            val more = it.getView<ImageView>(R.id.iv_more)

            if (data != null) {
                title.text = data.picture_name
                newspaper.text = "上传人：${data.owner}"
                date.text = "日期：${data.create_time}"
                reMarks.text = "编号：${data.picture_number}"

                more.setOnClickListener {
                    if (dialog == null) {
                        dialog = CustomerDialog(mContext as Activity, R.layout.dialog_document_file_child)
                        dialog?.setDlgIfClick(true)
                    }
                    dialog?.setOnCustomerViewCreated { window, dlg ->
                        val download = window.findViewById<TextView>(R.id.tv_download)
                        val downloadHistory = window.findViewById<TextView>(R.id.tv_download_history)
                        val share = window.findViewById<TextView>(R.id.tv_share)
                        val edit = window.findViewById<TextView>(R.id.tv_edit)
                        val read = window.findViewById<TextView>(R.id.tv_read)
                        val delete = window.findViewById<TextView>(R.id.tv_delete)
                        val cancel = window.findViewById<TextView>(R.id.tv_cancel)
                        download.setOnClickListener {
                            dialog?.dismissDlg()
                            downloadFile(data.id)
                        }
                        downloadHistory.setOnClickListener {
                            dialog?.dismissDlg()
                            val intent = Intent(mContext, DocumentDownloadHistoryListActivity::class.java)
                            intent.putExtra("documentId", data.id)
                            mContext.startActivity(intent)
                        }
                        share.setOnClickListener {
                            dialog?.dismissDlg()

                        }
                        read.setOnClickListener {
                            dialog?.dismissDlg()

                        }
                        edit.setOnClickListener {
                            dialog?.dismissDlg()
                            val intent = Intent(mContext, DocumentFileChildEditActivity::class.java)
                            intent.putExtra("data", data.children.toArrayList())
                            mContext.startActivity(intent)
                        }

                        delete.setOnClickListener {
                            dialog?.dismissDlg()
                            mContext.delete(Constant.URL_WORK_DOCUMENT_DELETE, "是否删除${data.picture_name}？", "id" to data.id.toString()) {
                                NotifyListenerMangager.getInstance().nofityContext(data.id.toString(), "DocumentFileChildActivity")
                                NotifyListenerMangager.getInstance().nofityContext("refresh", "DocumentFileActivity")
                            }
                        }
                        cancel.setOnClickListener {
                            dialog?.dismissDlg()
                        }

                    }
                    dialog?.showDlgWithGravity(Gravity.BOTTOM)

                }
            }
        }
    }

    //下载文件
    private fun downloadFile(id: Int) {

        val authorRequestParams = RequestParams(Constant.URL_WORK_DOCUMENT_DOWNLOAD)
        val userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)
        authorRequestParams.addBodyParameter("id", id.toString())
        authorRequestParams.addHeader("id", userId)
        x.http().post(authorRequestParams, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                if (!result.isNullOrBlank()) {
                    try {
                        val fileMode = Gson().fromJson<FileModel>(result, FileModel::class.java)
                        if (fileMode.code == 1) {
                            mContext.download(fileMode.data.filename, fileMode.data.path)
                        } else {
                            ToastUtils.showShort(fileMode.msg)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })
    }
}


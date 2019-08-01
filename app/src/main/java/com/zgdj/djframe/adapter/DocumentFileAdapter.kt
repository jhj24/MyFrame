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
import com.zgdj.djframe.activity.work.DocumentFileChildActivity
import com.zgdj.djframe.activity.work.DocumentFileEditActivity
import com.zgdj.djframe.activity.work.DocumentShareActivity
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
class DocumentFileAdapter(list: MutableList<DocumentFileBean.DataBean>?, layoutId: Int) :
        SingleAdapter<DocumentFileBean.DataBean>(list, layoutId) {
    private var dialog: CustomerDialog? = null


    @SuppressLint("SetTextI18n")
    override fun bind(holder: BaseViewHolder?, data: DocumentFileBean.DataBean?) {
        holder?.let {
            val title = it.getView<TextView>(R.id.tv_title)
            val code = it.getView<TextView>(R.id.tv_code)
            val time = it.getView<TextView>(R.id.tv_time)
            val more = it.getView<ImageView>(R.id.iv_more)

            // 赋值
            if (data != null) {
                title.text = data.picture_name
                code.text = data.picture_number
                time.text = data.create_time
                more.setOnClickListener {
                    if (dialog == null) {
                        dialog = CustomerDialog(mContext as Activity, R.layout.dialog_document_file)
                        dialog?.setDlgIfClick(true)
                    }
                    dialog?.setOnCustomerViewCreated { window, dlg ->
                        val download = window.findViewById<TextView>(R.id.tv_download)
                        val downloadHistory = window.findViewById<TextView>(R.id.tv_download_history)
                        val edit = window.findViewById<TextView>(R.id.tv_edit)
                        val share = window.findViewById<TextView>(R.id.tv_share)
                        val draw = window.findViewById<TextView>(R.id.tv_draw)
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
                            val intent = Intent(mContext, DocumentShareActivity::class.java)
                            intent.putExtra("documentId", data.id)
                            mContext.startActivity(intent)
                        }
                        edit.setOnClickListener {
                            dialog?.dismissDlg()
                            val intent = Intent(mContext, DocumentFileEditActivity::class.java)
                            intent.putExtra("data", data.children.toArrayList())
                            mContext.startActivity(intent)
                        }
                        draw.setOnClickListener {
                            dialog?.dismissDlg()
                            val intent = Intent(mContext, DocumentFileChildActivity::class.java)
                            intent.putExtra("data", data)
                            mContext.startActivity(intent)
                        }
                        delete.setOnClickListener {
                            dialog?.dismissDlg()
                            /* mContext.delete(Constant.URL_WORK_DOCUMENT_DELETE, "是否删除${data.picture_name}？", "id" to data.id.toString()) {
                                 NotifyListenerMangager.getInstance().nofityContext("refresh", "DocumentFileActivity")
                             }*/
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


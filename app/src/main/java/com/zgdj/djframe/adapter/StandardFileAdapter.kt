package com.zgdj.djframe.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.other.PDFActivity
import com.zgdj.djframe.activity.other.PhotoViewActivity
import com.zgdj.djframe.activity.work.StandardFileEditActivity
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.bean.ImageBean
import com.zgdj.djframe.bean.StandardFileBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.CustomerDialog
import java.io.Serializable
import java.util.*

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
            val standard = it.getView<TextView>(R.id.item_tv_standard)
            val more = it.getView<ImageView>(R.id.iv_more)

            // 赋值
            if (data != null) {
                title.text = data.standard_name
                newspaper.text = "编号：${data.standard_number}"
                date.text = "日期：${data.material_date}"
                reMarks.text = "备注：${data.remark}"
                standard.text = "替代标准：${data.alternate_standard}"
                more.setOnClickListener {
                    if (dialog == null) {
                        dialog = CustomerDialog(mContext as Activity, R.layout.dialog_standard_file)
                        dialog?.setDlgIfClick(true)
                    }
                    dialog?.setOnCustomerViewCreated { window, dlg ->
                        val download = window.findViewById<TextView>(R.id.tv_download)
                        val read = window.findViewById<TextView>(R.id.tv_read)
                        val edit = window.findViewById<TextView>(R.id.tv_edit)
                        val delete = window.findViewById<TextView>(R.id.tv_delete)
                        val cancel = window.findViewById<TextView>(R.id.tv_cancel)
                        download.setOnClickListener {
                            dialog?.dismissDlg()
                            //下载
                            if (data.path.isNullOrBlank()) {
                                mContext.toast("没有文件")
                            } else {
                                mContext.download(data.standard_name + "." + data.path.getFileSuffix(), data.path)
                            }
                        }
                        read.setOnClickListener {
                            dialog?.dismissDlg();
                            try {
                                display(data.path, data.file_id.toInt())
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                        edit.setOnClickListener {
                            dialog?.dismissDlg()
                            val intent = Intent(mContext, StandardFileEditActivity::class.java)
                            intent.putExtra("nodeId", key)
                            intent.putExtra("data", data)
                            (mContext as Activity).startActivityForResult(intent, 1000)
                        }
                        delete.setOnClickListener {
                            dialog?.dismissDlg()
                            mContext.delete(Constant.URL_WORK_STANDARD_DELETE, "是否删除${data.standard_name}？", "id" to data.id.toString()) {
                                NotifyListenerMangager.getInstance().nofityContext(data.id.toString(), "DocumentFileChildActivity")

                                NotifyListenerMangager.getInstance().nofityContext("refresh",
                                        "StandardFileActivity")
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

    fun display(path: String, id: Int) {
        val suffix = path.getFileSuffix()
        if (suffix == "jpg" || suffix == "png" || suffix == "gif") {
            val imgList = ArrayList<ImageBean>()
            imgList.add(ImageBean(Constant.BASE_URL + path, 2))
            val bundle = Bundle()
            bundle.putSerializable(PhotoViewActivity.KEY_IMAGE_LIST, imgList as Serializable)
            bundle.putInt(PhotoViewActivity.KEY_IMAGE_POSITION, 0)
            mContext.startActivity(Intent(mContext, PhotoViewActivity::class.java).putExtras(bundle))
        } else if (suffix == "pdf" || suffix == "xls" || suffix == "docx" || suffix == "doc") {
            val intent = Intent(mContext, PDFActivity::class.java)
            intent.putExtra("key_url", Constant.BASE_URL + path)
            intent.putExtra("file_type", suffix)
            intent.putExtra("file_name", path.getFileName())
            intent.putExtra("file_id", id.toString())
            intent.putExtra("is_download", false)
            mContext.startActivity(intent)
        }
    }

}


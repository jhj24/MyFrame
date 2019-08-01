package com.zgdj.djframe.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.model.DocumentDownloadHistroyModel

/**
 * description:实时进度adapter
 * author: Created by Mr.Zhang on 2018/7/23
 */
class DocumentDownloadHistoryListAdapter(list: MutableList<DocumentDownloadHistroyModel.DataBean>?, layoutId: Int) :
        SingleAdapter<DocumentDownloadHistroyModel.DataBean>(list, layoutId) {

    @SuppressLint("SetTextI18n")
    override fun bind(holder: BaseViewHolder?, data: DocumentDownloadHistroyModel.DataBean?) {
        holder?.let {
            val title = it.getView<TextView>(R.id.tv_name)
            val time = it.getView<TextView>(R.id.tv_time)

            // 赋值
            if (data != null) {
                title.text = data.user_name
                time.text = data.create_time
            }
        }
    }
}


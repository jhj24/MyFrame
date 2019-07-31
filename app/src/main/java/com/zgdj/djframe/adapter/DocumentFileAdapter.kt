package com.zgdj.djframe.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.bean.DocumentFileBean
import com.zgdj.djframe.view.CustomerDialog

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

                }
            }
        }

    }

}


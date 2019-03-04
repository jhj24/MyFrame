package com.zgdj.djframe.adapter

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.model.MessageExecutorModel

/**
 * description: 执行人适配器
 * author: Created by Mr.Zhang on 2018/6/27
 */
class ExecutorAdapter(list: MutableList<MessageExecutorModel.DataBean>?, layoutId: Int) :
        SingleAdapter<MessageExecutorModel.DataBean>(list, layoutId) {
    private var callback: RadioClick? = null
    private var executorId = "" // 执行人id


    override fun bind(holder: BaseViewHolder?, data: MessageExecutorModel.DataBean?) {
        val name = holder!!.getView<TextView>(R.id.item_executor_text_name) //用户名
        val department = holder.getView<TextView>(R.id.item_executor_text_department) //部门
        val unit = holder.getView<TextView>(R.id.item_executor_text_unit) //部门
        if (data!!.nickname.isNullOrEmpty().not()) {
            name.text = data.nickname
        } else name.text = "部门：无"
        if (data.name.isNullOrEmpty().not()) {
            department.text = "部门：${data.name}"
        } else department.text = ""
        if (data.p_name.isNullOrEmpty().not()) {
            unit.text = "单位：${data.p_name}"
        } else unit.text = "单位：无"


    }

    override fun bindCustomViewHolder(holder: BaseViewHolder, position: Int) {
        super.bindCustomViewHolder(holder, position)
        val radio = holder.getView<RadioButton>(R.id.item_executor_radio)//radio Button
        radio.isChecked = (executorId == mData[position].id)
        radio.setOnClickListener {
            callback!!.onClick(it, position)
        }

    }

    //设置radio button 选中item
    fun setRadioChecked(id: String) {
        executorId = id
    }

    //设置监听
    fun setRadioClick(callback: RadioClick) {
        this.callback = callback
    }

    // radio 回调
    interface RadioClick {
        fun onClick(view: View, pos: Int)
    }

}
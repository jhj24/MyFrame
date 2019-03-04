package com.zgdj.djframe.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.model.MessageTotalModel

class MessageRemindAdapter(list: MutableList<MessageTotalModel.DataBean.LagDataBean>?, layoutId: Int) :
        SingleAdapter<MessageTotalModel.DataBean.LagDataBean>(list, layoutId) {

    override fun bind(holder: BaseViewHolder?, data: MessageTotalModel.DataBean.LagDataBean?) {
        val title = holder!!.getView<TextView>(R.id.item_message_warming_title) //任务名称
        val startDate = holder.getView<TextView>(R.id.item_message_warming_start_date) //计划开始日期
        val endDate = holder.getView<TextView>(R.id.item_message_warming_complete_date) //计划完成日期
        val startDateReal = holder.getView<TextView>(R.id.item_message_warming_complete_percentage) //实际开始日期
        val startDateRealHint = holder.getView<TextView>(R.id.item_message_warming_complete_percentage_hint) //实际开始日期 - 提示
        val percentage = holder.getView<TextView>(R.id.item_message_warming_delay_date) //完成百分比
        val percentageHint = holder.getView<TextView>(R.id.item_message_warming_delay_date_hint) //完成百分比 - 提示
        val layout = holder.getView<LinearLayout>(R.id.item_layout_remind) //完成百分比 - 提示
        val expectComplete = holder.getView<TextView>(R.id.item_message_remind_complete_expect) //预计完成日期
        val expectLagging = holder.getView<TextView>(R.id.item_message_remind_lagging_expect) //预计滞后工期（天）

        //赋值
        title.text = data!!.name
        startDate.text = data.start
        endDate.text = data.finish
        startDateReal.text = data.actual_start
        startDateRealHint.text = "实际开始日期"
        percentage.text = data.percent_complete
        percentageHint.text = "完成百分比"
        expectComplete.text = data.edc
        expectLagging.text = data.delayed_day.toString()
        layout.visibility = View.VISIBLE

    }

}
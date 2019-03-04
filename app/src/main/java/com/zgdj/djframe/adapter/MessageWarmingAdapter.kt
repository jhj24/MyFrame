package com.zgdj.djframe.adapter

import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.model.MessageWarmingModel

/**
 * 消息滞后提醒列表adapter
 */
class MessageWarmingAdapter(list: MutableList<MessageWarmingModel.DataBean.LagDataBean>?, layoutId: Int) :
        SingleAdapter<MessageWarmingModel.DataBean.LagDataBean>(list, layoutId) {

    override fun bind(holder: BaseViewHolder?, data: MessageWarmingModel.DataBean.LagDataBean?) {
        val title = holder!!.getView<TextView>(R.id.item_message_warming_title) //标题
        val startDate = holder!!.getView<TextView>(R.id.item_message_warming_start_date) // 开始时间
        val endDate = holder!!.getView<TextView>(R.id.item_message_warming_complete_date) // 结束时间
        val percentage = holder!!.getView<TextView>(R.id.item_message_warming_complete_percentage) // 完成百分比
        val delay = holder!!.getView<TextView>(R.id.item_message_warming_delay_date) // 年中滞后工期

        title.text = data!!.name
        startDate.text = data!!.start
        endDate.text = data!!.finish
        percentage.text = data!!.year_end_percent_complete
        delay.text = data!!.year_end_lag_day.toString()

    }


}
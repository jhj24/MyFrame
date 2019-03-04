package com.zgdj.djframe.activity.message

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.MessageWarmingAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.model.MessageWarmingModel
import kotlinx.android.synthetic.main.activity_message_task_list.*

/**
 * 查看滞后任务列表
 */
class MessageTaskListActivity : BaseNormalActivity() {
    private lateinit var warmingAdapter: MessageWarmingAdapter //列表adapter
    private lateinit var mList: MutableList<MessageWarmingModel.DataBean.LagDataBean> //数据

    override fun initData(bundle: Bundle?) {
        mList = bundle!!.getSerializable("TaskList") as ArrayList<MessageWarmingModel.DataBean.LagDataBean>
    }

    override fun bindLayout(): Int {
        return R.layout.activity_message_task_list
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "滞后任务列表"
    }

    override fun doBusiness() {
        //recycler
        warmingAdapter = MessageWarmingAdapter(mList, R.layout.item_recycler_message_warming)
        task_list_recycler.adapter = warmingAdapter
        task_list_recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onWidgetClick(view: View?) {
    }

}

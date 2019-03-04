package com.zgdj.djframe.activity.message

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.message.interf.MvpCallback
import com.zgdj.djframe.activity.message.interf.MvpModel
import com.zgdj.djframe.adapter.MessageWarmingAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.fragment.MessageFragment
import com.zgdj.djframe.model.MessageWarmingModel
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.NotifyListenerMangager
import com.zgdj.djframe.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_message_warming.*
import org.xutils.common.Callback


/**
 * 警告类：滞后警告
 */
class MessageWarmingActivity : BaseNormalActivity() {
    private var messageId = "" //消息id
    private var messageUnitId = "" //消息关联表id
    private var messageType = "" //消息类型
    private var messageTitle = "" //消息标题
    private var taskType = -1 // 待办任务 1 or 已办任务 2
    private lateinit var warmingAdapter: MessageWarmingAdapter //列表adapter
    private lateinit var mList: MutableList<MessageWarmingModel.DataBean.LagDataBean> //数据

    override fun initData(bundle: Bundle?) {
        messageId = bundle!!.getString("MessageId")
        messageUnitId = bundle!!.getString("MessageUnitId")
        messageType = bundle!!.getString("MessageType")
        messageTitle = bundle!!.getString("MessageTitle")
        taskType = bundle.getInt("MessageTaskType")
    }

    override fun bindLayout(): Int {
        return R.layout.activity_message_warming
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = messageTitle
    }

    override fun doBusiness() {
        //recycler
        mList = arrayListOf()
        warmingAdapter = MessageWarmingAdapter(mList, R.layout.item_recycler_message_warming)
        warming_recycler.adapter = warmingAdapter
        warming_recycler.layoutManager = LinearLayoutManager(this)

        getWarmingDetails(when (messageType) {
            "7" -> "4"
            "77" -> "44"
            else -> ""
        })
        // 查看滞后任务列表
        warming_text_more.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("TaskList", mList as ArrayList<MessageWarmingModel.DataBean.LagDataBean>)
            jumpToInterface(MessageTaskListActivity::class.java, bundle)
        }

        //验收
        if (taskType == 2) warming_button_ok.visibility = View.GONE
        warming_button_ok.setOnClickListener {
            MvpModel.checkPlanTask(token, messageId, object : MvpCallback {
                override fun onSuccess(data: String?) {
                    Logger.json("验收", data)
                    ToastUtils.showShort("验收成功")
                    NotifyListenerMangager.getInstance().nofityContext(MessageFragment.ONREFRESH, MessageFragment.TAG)
                    finish()
                }

                override fun onCancelled(cex: Callback.CancelledException?) {
                }

                override fun onError(ex: Throwable?) {
                }

                override fun onFinished() {
                }

            })
        }
    }

    override fun onWidgetClick(view: View?) {

    }

    /**
     * 年中或年尾 滞后Task
     */
    private fun getWarmingDetails(type: String) {
        MvpModel.getRemindOfYearDetails(token, messageId, messageUnitId, type, object : MvpCallback {
            override fun onSuccess(data: String?) {
                Logger.json("年计划", data)
                val messageModel = Gson().fromJson<MessageWarmingModel>(data!!,
                        MessageWarmingModel::class.java)
                // 赋值
                warming_text_file_name.text = messageModel.data.section_name // 文件名称
                warming_text_date.text = messageModel.data.date //日期
                when (messageModel.code) {
                    1 -> {  // code = 1 不存在计划或者任务，前台提示【自己组织】的一句话
                        // 隐藏 截止日期 和 说明
                        line1.visibility = View.GONE
                        warming_text_date_hint.visibility = View.GONE
                        warming_text_date.visibility = View.GONE
                        warming_text_explain_hint.visibility = View.GONE
                        warming_text_explain.visibility = View.GONE
                        //显示提示语
                        warming_text_no_task_hint.visibility = View.VISIBLE
                        warming_text_no_task_hint.text = "该计划下不存在任务，请到年计划管理里面填加数据"
                    }
                    2 -> {  // code = 2 百分比全是100的时候，前台提示【后台返回】的一句话
                        //显示提示语
                        warming_text_no_task_hint.visibility = View.VISIBLE
                        warming_text_no_task_hint.text = messageModel.msg
                    }
                    3 -> {  // code= 3 不存在滞后数据 data [] 或者 存在滞后数据 data
                        // [有值]百分比全是 0 的情况，不存在滞后数据data [] 或者 存在滞后数据 data [有值]
                        warming_text_explain.text = when (messageType) {
                            "7" -> "本进度滞后提醒根据年进度填报的年尾完成情况填报生成，如有疑问请进入页面查看填报数据。"  //年尾
                            "77" -> "本进度滞后提醒根据年进度填报的年中完成情况填报生成，如有疑问请进入页面查看填报数据。" //年中
                            else -> ""
                        }
                        if (messageModel.data.lag_data.size > 0) {
                            warming_text_more.visibility = View.VISIBLE //显示更多按钮
                            mList = messageModel.data.lag_data
                            warmingAdapter.setData(messageModel.data.lag_data.subList(0, 1))
                            warmingAdapter.notifyDataSetChanged()
                            /*    warming_recycler.post {
                                    val height = warming_recycler.layoutManager.getChildAt(0).height
                                    val lp = warming_recycler.layoutParams
                                    if (messageModel.data.lag_data.size > 4) {
                                        lp.height = height * 4 + Utils.dp2px(this@MessageWarmingActivity, 16 * 4 + 10)
                                    } else {
                                        lp.height = height * messageModel.data.lag_data.size
                                    }
                                    warming_recycler.layoutParams = lp
                                    warming_recycler.requestLayout()
                                }*/

                        }
                    }

                    -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)

                }

            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?) {
            }

            override fun onFinished() {
            }

        })
    }

}

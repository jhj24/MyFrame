package com.zgdj.djframe.activity.message

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.message.interf.MvpCallback
import com.zgdj.djframe.activity.message.interf.MvpModel
import com.zgdj.djframe.adapter.MessageRemindAdapter
import com.zgdj.djframe.adapter.MessageWarmingAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.fragment.MessageFragment
import com.zgdj.djframe.model.MessageMonthModel
import com.zgdj.djframe.model.MessageTotalModel
import com.zgdj.djframe.model.MessageYearModel
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.NotifyListenerMangager
import com.zgdj.djframe.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_message_remind.*
import org.xutils.common.Callback

/**
 * 提醒相关类页面：填报提醒，预警提醒
 */
class MessageRemindActivity : BaseNormalActivity() {
    private var messageId = "" //消息id
    private var messageUnitId = "" //消息关联表id
    private var messageType = "" //消息类型
    private var messageTitle = "" //消息标题
    private var taskType = -1 // 待办任务 1 or 已办任务 2

    override fun initData(bundle: Bundle?) {
        messageId = bundle!!.getString("MessageId")
        messageUnitId = bundle.getString("MessageUnitId")
        messageType = bundle.getString("MessageType")
        messageTitle = bundle.getString("MessageTitle")
        taskType = bundle.getInt("MessageTaskType")
    }

    override fun bindLayout(): Int {
        return R.layout.activity_message_remind
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = messageTitle
    }

    override fun doBusiness() {
        /**
         * messageType: 1为收发文，2为单元管控, 3 为表单退回,
         * 4 月计划提醒, 5 月填报提醒, 6 年中填报提醒, 7 年中滞后提醒,
         * 8 总填报提醒, 9 总预警提醒, 66 年尾填报提醒, 77 年尾滞后提醒
         */
        when (messageType) {
            "4" -> getMonthDetails("1")
            "5" -> getMonthDetails("2")
            "6" -> getYearDetails("3")
            "66" -> getYearDetails("33")
            "8" -> getTotalDetails("5")
            "9" -> getTotalDetails("6")
        }

        //验收
        if (taskType == 2) remind_button_ok.visibility = View.GONE
        remind_button_ok.setOnClickListener {
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

    //月计划详情task
    private fun getMonthDetails(type: String) {
        MvpModel.getRemindOfMonthDetails(token, messageId, messageUnitId, type, object : MvpCallback {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(data: String?) {
                Logger.json("月计划", data)
                val messageModel = Gson().fromJson<MessageMonthModel>(data, MessageMonthModel::class.java)
                if (messageModel.code == 1) {
                    remind_text_file_name.text = messageModel.data.section_name
                    remind_text_date.text = messageModel.data.date
                    remind_text_head.text = messageModel.data.user_name
                    remind_text_content.text = "${messageModel.data.section_name}已进入" +
                            "${messageModel.data.monthly}月进度进度计划填报周期，" +
                            "请负责人及时处理并根据实际情况填报${messageModel.data.monthly}月进度的实际完成情况，" +
                            "如果已经填报请忽略本条消息。"
                    remind_text_control.text = "进入月进度填报界面，填写对应任务项的实际开始日期与实际完成日期。"
                } else if (messageModel.code == -2) {
                    ToastUtils.showShort(Constant.TOKEN_LOST)
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

    //年计划提醒task
    private fun getYearDetails(type: String) {
        MvpModel.getRemindOfYearDetails(token, messageId, messageUnitId, type, object : MvpCallback {
            override fun onSuccess(data: String?) {
                Logger.json("年计划", data)
                val messageModel = Gson().fromJson<MessageYearModel>(data, MessageYearModel::class.java)
                if (messageModel.code == 1) { // ok
                    remind_text_file_name.text = messageModel.data.section_name
                    remind_text_date.text = messageModel.data.date
                    remind_text_head.text = messageModel.data.user_name
                    remind_text_interval_hint.visibility = View.VISIBLE
                    remind_text_interval.visibility = View.VISIBLE
                    lin2.visibility = View.VISIBLE
                    remind_text_interval.text = messageModel.data.year
                    remind_text_content.text = when (messageType) {
                        "6" -> messageModel.data.section_name + "已进入年中进度填报周期，请负责人及时处理并根据实际情况填报总进度完成情况，如果已经填报请忽略本条消息。"
                        "66" -> messageModel.data.section_name + "已进入年尾进度填报周期，请负责人及时处理并根据实际情况填报总进度完成情况，如果已经填报请忽略本条消息。"
                        else -> ""
                    }
                    remind_text_control.text = when (messageType) {
                        "6" -> "进入年进度填报界面，选择所负责标段、年度、填报时段后，填报每一个工作任务的完成百分比。"
                        "66" -> "进入年进度填报界面，选择所负责标段、年度、填报时段后，填报每一个工作任务的完成百分比。"
                        else -> ""
                    }
                } else if (messageModel.code == -2) {
                    ToastUtils.showShort(Constant.TOKEN_LOST)
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

    //总计划提醒task
    private fun getTotalDetails(type: String) {
        MvpModel.getRemindOfTotalDetails(token, messageId, messageUnitId, type, object : MvpCallback {
            override fun onSuccess(data: String?) {
                Logger.json("总计划", data)
                val messageModel = Gson().fromJson<MessageTotalModel>(data, MessageTotalModel::class.java)
                remind_text_file_name.text = messageModel.data.section_name
                remind_text_date.text = messageModel.data.date
                remind_text_head.text = messageModel.data.user_name
                when (messageModel.code) {
                    1 -> { //不存在计划或任务,前台提示【自己组织】的一句话
                        remind_text_content.text = when (messageType) {
                            //    8 总填报提醒, 9 总预警提醒,
                            "8" -> messageModel.data.section_name + "标段已进入总进度填报周期，请负责人及时处理并根据实际情况填报总进度完成情况。"
                            "9" -> messageModel.data.section_name + "标段由于未添加总进度计划数据，无法进行进度预警，请及时更新总进度计划。"
                            else -> ""
                        }
                        remind_text_control.text = when (messageType) {
                            "8" -> "进入总进度填报界面，选择所负责标段后，填报每一个工作任务的完成百分比和对应完成时间。"
                            "9" -> "进入总计划管理界面，选择所负责标段后，点击版本更新按钮进行总进度计划数据的录入。"
                            else -> ""
                        }

                    }
                    2 -> { //code = 2 百分比全是0的时候，前台提示【自己组织】的一句话
                        remind_text_content.text = when (messageType) {
                            //                        8 总填报提醒, 9 总预警提醒,
                            "8" -> messageModel.data.section_name + "已进入总进度填报周期，请负责人及时处理并根据实际情况填报总进度完成情况。"
                            "9" -> messageModel.data.section_name + "标段由于未及时进行总进度填报，无法生成进度预警数据。"
                            else -> ""
                        }
                        remind_text_control.text = when (messageType) {
                            "8" -> "进入总进度填报界面，选择所负责标段后，填报每一个工作任务的完成百分比和对应完成时间。"
                            "9" -> "进入总进度填报界面，选择所负责标段后填报每一个工作任务的完成百分比和对应完成时间。"
                            else -> ""
                        }
                    }
                    3 -> { //code = 3 百分比全是100的时候，前台提示【后台返回】的一句话
                        remind_text_content.text = when (messageType) {
                            "8" -> messageModel.msg
                            "9" -> messageModel.msg
                            else -> ""
                        }
                        remind_text_control.visibility = View.GONE
                        remind_text_control_hint.visibility = View.GONE
                    }
                    4 -> { //code = 4 不存在滞后数据 data [] 或者存在滞后数据 data [有值]
                        remind_text_content_hint.visibility = View.GONE
                        remind_text_content.visibility = View.GONE
                        remind_text_control_hint.visibility = View.GONE
                        remind_text_control.visibility = View.GONE
                        if (messageModel.data.lag_data.size > 0) { //有数据
                            remind_recycler.visibility = View.VISIBLE
                            val adapter = MessageRemindAdapter(messageModel.data.lag_data, R.layout.item_recycler_message_warming)
                            remind_recycler.adapter = adapter
                            remind_recycler.layoutManager = LinearLayoutManager(this@MessageRemindActivity)
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

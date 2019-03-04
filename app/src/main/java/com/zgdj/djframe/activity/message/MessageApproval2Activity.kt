package com.zgdj.djframe.activity.message

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.fragment.MessageFragment
import com.zgdj.djframe.utils.*
import kotlinx.android.synthetic.main.activity_message_approval2.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * 消息 -- 审批2
 */
class MessageApproval2Activity : BaseNormalActivity() {
    private var formId: String? = ""
    private var executorId: String? = "" //执行人id
    private var currentId: String? = "" //当前登录人id

    override fun initData(bundle: Bundle?) {
        formId = bundle?.getString("formID")
        currentId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_message_approval2
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
    }

    @SuppressLint("SetTextI18n")
    override fun doBusiness() {
        title = "流程审批"
        //当前用户名
        val currentName = SPUtils.getInstance().getString(Constant.KEY_USER_NICK)
        approval2_text_current_name.text = "当前处理人：$currentName"
        Utils.setTextColor(this, approval2_text_current_name.text.toString(),
                approval2_text_current_name, R.color.middle_black, 6, approval2_text_current_name.text.length)
        //获取当前时间
        approval2_text_current_time.text = "处理日期：${DateUtil.getCurrentDate()}"
        Utils.setTextColor(this, approval2_text_current_time.text.toString(),
                approval2_text_current_time, R.color.middle_black, 5, approval2_text_current_time.text.length)
        // 意见反馈edit
        approval2_edit_suggestion.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                approval2_text_suggestion_record.text = "${s.toString().length}/300"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        //选择执行人
        approval2_text_choice.setOnClickListener {
            val intent = Intent(this@MessageApproval2Activity, ChoiceOfExecutorActivity::class.java)
            intent.putExtra("executorId", executorId)
            startActivityForResult(intent, 2)
        }

        // 不通过
        approval2_btn_refuse.setOnClickListener { approvalTask("-1") }
        //通过
        approval2_btn_ok.setOnClickListener {
            approvalTask("2")
        }

    }

    override fun onWidgetClick(view: View?) {
    }

    //审批请求 code:审批状态：0新建，1审批中，2已审批，-1被退回，-2作废
    private fun approvalTask(code: String) {
        if (code == "2" && executorId.isNullOrEmpty()) {
            ToastUtils.showShort("请选择执行人")
            return
        }

        val params = RequestParams(Constant.URL_MESSAGE_APPROVAL)
        params.addHeader("token", token)
        params.addBodyParameter("dataId", formId)//表单id
        params.addBodyParameter("res", code)//表单id
        params.addBodyParameter("mark", approval2_edit_suggestion.text.toString())//说明意见
        params.addBodyParameter("next_approverid", executorId)//下一个审批人，不是必须的，退回时没有下一个审批人
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                if (!result.isNullOrEmpty()) {
                    Logger.json("审批请求", result)
                    val json = JSONObject(result)
                    if (json.getInt("code") == 1) { // 审批成功
                        ToastUtils.showShort(when (code) {
                            "2" -> "审批成功"
                            "-1" -> "退回成功"
                            else -> ""
                        })
                        changeStatusTask(type = when (code) {
                            "2" -> "2"
                            "-1" -> "3"
                            else -> ""
                        })

                    } else if (json.getInt("code") == -2) {
                        ToastUtils.showShort(Constant.TOKEN_LOST)
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })
    }

    /**
     * 更改消息列表中的处理状态
     * @param type ：1为收发文，2为单元管控，3为退回
     */
    private fun changeStatusTask(type: String) {
        if (type.isNullOrEmpty()) return
        val params = RequestParams(Constant.URL_MESSAGE_APPROVE_CHANGESTATUS)
        params.addHeader("token", token)
        params.addBodyParameter("uint_id", formId)//表单id类型
        params.addBodyParameter("type", type)//
        params.addBodyParameter("id", currentId)//当前登录人的id
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {

            }

            override fun onSuccess(result: String?) {
                if (result.isNullOrEmpty().not()) {
                    Logger.json("更新状态", result)
                    val json = JSONObject(result)
                    when {
                        json.getInt("code") == 1 -> {
                            ToastUtils.showShort("修改状态成功")
                            NotifyListenerMangager.getInstance().nofityContext(MessageFragment.ONREFRESH, MessageFragment.TAG)
                            finish()
                        }
                        json.getInt("code") == -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                        else -> ToastUtils.showShort("修改状态失败")
                    }

                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {

            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 选择执行人
        when (requestCode) {
            2 -> if (data != null) {
                executorId = data.getStringExtra("executorId")
                approval2_text_select_person.text = data.getStringExtra("executorName")
            }
        }

    }


}

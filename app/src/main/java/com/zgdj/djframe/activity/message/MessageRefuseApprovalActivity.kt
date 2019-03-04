package com.zgdj.djframe.activity.message

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.fragment.MessageFragment
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.NotifyListenerMangager
import com.zgdj.djframe.utils.SPUtils
import com.zgdj.djframe.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_message_approval2.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * 审批不通过 收集退回意见
 */
class MessageRefuseApprovalActivity : BaseNormalActivity() {
    private var formId: String? = ""

    override fun initData(bundle: Bundle?) {
        formId = bundle?.getString("formID")
    }

    override fun bindLayout(): Int {
        return R.layout.activity_message_refuse_approval
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "流程审批"
    }

    override fun doBusiness() {
        //提交
        approval2_btn_refuse.setOnClickListener {
            approvalTask()
        }

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
    }

    override fun onWidgetClick(view: View?) {

    }

    //审批请求 code:审批状态：0新建，1审批中，2已审批，-1被退回，-2作废
    private fun approvalTask() {
        val params = RequestParams(Constant.URL_MESSAGE_APPROVAL)
        params.addHeader("token", token)
        params.addBodyParameter("dataId", formId)//表单id
        params.addBodyParameter("res", "-1")//审批状态
        params.addBodyParameter("mark", approval2_edit_suggestion.text.toString())//说明意见
        params.addBodyParameter("next_approverid", "")//下一个审批人，不是必须的，退回时没有下一个审批人
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {


            }

            override fun onSuccess(result: String?) {
                if (!result.isNullOrEmpty()) {
                    Logger.json("退回审批", result)
                    val json = JSONObject(result)
                    // 审批成功
                    when {
                        json.getInt("code") == 1 -> changeStatusTask()
                        json.getInt("code") == -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                        else -> ToastUtils.showShort("退回审批失败")
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
    private fun changeStatusTask() {
        val currentId = SPUtils.getInstance().getString(Constant.KEY_USER_ID) //当前登录人id
        val params = RequestParams(Constant.URL_MESSAGE_APPROVE_CHANGESTATUS)
        params.addHeader("token", token)
        params.addBodyParameter("uint_id", formId)//表单id类型
        params.addBodyParameter("type", "3")//
        params.addBodyParameter("id", currentId)//当前登录人的id
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                if (result.isNullOrEmpty().not()) {
                    Logger.json("更新退回状态", result)
                    val json = JSONObject(result)
                    when (json.getInt("code")) {
                        1 -> {
                            ToastUtils.showShort("退回审批成功")
                            NotifyListenerMangager.getInstance().nofityContext(MessageFragment.ONREFRESH, MessageFragment.TAG)
                            finish()
                        }
                        -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                        else -> ToastUtils.showShort("退回审批失败")
                    }

                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })

    }

}

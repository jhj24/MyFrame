package com.zgdj.djframe.activity.message

import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.*
import kotlinx.android.synthetic.main.activity_message_approval.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.text.SimpleDateFormat
import java.util.*

/**
 * 消息处理 -- 1
 */
class MessageApprovalActivity : BaseNormalActivity() {
    private var userId: String? = null
    private var isAutograph = false //签名标示
    private var formId: String? = ""
    private var formName: String = ""
    private var supervisor = false
    private var formType = 0
    //审批表单内容
    private var formDate = ""
    private var formTime = ""
    private var checkedPos = -1
    private var formEdit = ""


    override fun initData(bundle: Bundle?) {
        userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)
        formId = intent.getStringExtra("formID")
        formName = intent.getStringExtra("form_name")
        supervisor = intent.getBooleanExtra("supervisor", false)

    }

    override fun bindLayout(): Int {
        return R.layout.activity_message_approval
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {

    }

    override fun onResume() {
        super.onResume()
        Logger.i("onResume")
        if (supervisor)
            setFormView()
    }

    //设置表单样式
    private fun setFormView() {
        val layout: LinearLayout = this.findViewById(R.id.approval_layout_content)
        val formUtil = FormUtil.getInstance()
        formType = formUtil.getFormIndex(formName)
        if (formType != 0) {
            formUtil.createFormView(this, formType, layout)
            when (formType) {
                1, 8, 9, 10 -> { // 类型 1
                    formUtil.setCallBack(object : FormUtil.Form1Call {
                        override fun getHour(hour: String?) {
                            formTime = hour!!
                        }

                        override fun getChecked(checkedPos: Int) {
                            this@MessageApprovalActivity.checkedPos = checkedPos
                        }

                        override fun getTime(time: String?) {
                            formDate = time!!
                        }

                        override fun getEdit(info: String?) {
                            formEdit = info!!
                        }

                    }, formType)
                }
                2, 3, 5, 6 -> {// 类型 2
                    formUtil.setCallBack(FormUtil.Form2Call {
                        checkedPos = it
                    }, formType)
                }
                4, 11, 12, 13, 14, 23, 24, 25, 26 -> { //类型 4
                    formUtil.setCallBack(FormUtil.Form4Call { _, result ->
                        formEdit = result
                    }, formType)
                }
                7 -> {
                    formUtil.setCallBack(object : FormUtil.Form7Call {
                        override fun getEditResult(result: String?) {
                            formEdit = result!!
                        }

                        override fun getChecked(pos: Int) {
                            checkedPos = pos
                        }

                    }, formType)
                }

            }

            /*  formUtil.setCallBack(FormUtil.Form2Call {
              Logger.i("点击了：$it")
          }, 2)*/
        }
    }

    override fun doBusiness() {
        // 设置title
        title = "流程审批"
        //签名图片
        approval_btn_sign.setOnClickListener {
            if (!isAutograph) getSignTask()
        }
        //日期选择
        approval_text_date.setOnClickListener { showDatePickerDialog() }
        // 下一步
        approval_btn_next.setOnClickListener { handleFormInfoTask() }
    }

    override fun onWidgetClick(view: View?) {

    }

    private fun showDatePickerDialog() {
        var cal = Calendar.getInstance()
        val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val myFormat = "yyyy-MM-dd"
                    val sdf = SimpleDateFormat(myFormat)
                    approval_text_date.text = sdf.format(cal.time)
                    setButtonEnabled()
                }
        DatePickerDialog(this@MessageApprovalActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
    }


    //获取签名图片
    private fun getSignTask() {
        val params = RequestParams(Constant.URL_MESSAGE_GET_AUTOGRAPH)
        params.addHeader("token", token)
        params.addBodyParameter("id", userId)
        x.http().post(params, object : Callback.CommonCallback<String> {

            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                if (!RegexUtils.isNull(result)) {
                    Logger.json(result)
                    val json = JSONObject(result)
                    val code = json.getInt("code")
                    when (code) {
                        1 -> { // 成功
                            val imageUrl = Utils.tranFormURL(json.getString("signature"))
                            //                        Logger.w("签名图片：${imageUrl!!}")
                            Glide.with(this@MessageApprovalActivity)
                                    .load(imageUrl)
                                    .listener(object : RequestListener<Drawable> {
                                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                            ToastUtils.showShort("签名失败")
                                            isAutograph = false //临时
                                            return false
                                        }

                                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                            //                                        Util.showToast(mContext, "图片加载成功");
                                            isAutograph = true
                                            return false
                                        }

                                    })
                                    .into(approval_img_sign)
                            Logger.i("isAutograph:$isAutograph")
                            setButtonEnabled()
                        }
                        -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                        else -> ToastUtils.showShort("获取签名失败")
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })
    }

    //设置下一步可点击
    private fun setButtonEnabled() {
        if (isAutograph and !RegexUtils.isNull(approval_text_date.text.toString())) { //下一步可点击
            approval_btn_next.isEnabled = true
            approval_btn_next.setBackgroundResource(R.drawable.shape_rectangle_blue_round)
            Logger.i("可以点击了！")
        }
    }


    //更新表单
    private fun handleFormInfoTask() {
        val date = approval_text_date.text.toString()
        if (!isAutograph) {
            ToastUtils.showShort("请签名")
            return
        }
        if (RegexUtils.isNull(date)) {
            ToastUtils.showShort("请选择日期")
            return
        }
        Logger.i("form_id: $formId")
        val params = RequestParams(Constant.URL_MESSAGE_HANDLE_FORMINFO)
        params.addHeader("token", token)
        //审批表单
        if (supervisor) {
            when (formType) {
                1, 10 -> {
                    params.addBodyParameter("input_date1", formDate) //日期
                    params.addBodyParameter("input_date3", formTime) //小时
                    params.addBodyParameter("checkbox1", getcheckedPos(0))
                    params.addBodyParameter("input_date2", if (checkedPos == 0) formEdit else "")
                    params.addBodyParameter("checkbox2", getcheckedPos(1))
                    params.addBodyParameter("checkbox3", getcheckedPos(2))
                }
                2, 3 -> {
                    params.addBodyParameter("checkbox1", getcheckedPos(0))
                    params.addBodyParameter("checkbox2", getcheckedPos(1))
                    params.addBodyParameter("checkbox3", getcheckedPos(2))
                }
                4, 23, 24, 25, 26 -> {
                    params.addBodyParameter("input_hgl1", formEdit)
                }
                5 -> {
                    params.addBodyParameter("checkbox1", getcheckedPos(0))
                    params.addBodyParameter("checkbox2", getcheckedPos(1))
                    params.addBodyParameter("checkbox3", getcheckedPos(2))
                    params.addBodyParameter("checkbox4", getcheckedPos(3))
                }
                6 -> {
                    params.addBodyParameter("checkbox1", getcheckedPos(0))
                    params.addBodyParameter("checkbox2", getcheckedPos(1))
                }
                7 -> {
                    params.addBodyParameter("input_hgl1", formEdit)
                    params.addBodyParameter("checkbox1", getcheckedPos(0))
                    params.addBodyParameter("checkbox2", getcheckedPos(1))
                }
                8, 9 -> {
                    params.addBodyParameter("checkbox1", getcheckedPos(0))
                    params.addBodyParameter("checkbox2", getcheckedPos(1))
                    params.addBodyParameter("checkbox3", getcheckedPos(2))
                    params.addBodyParameter("input_hgl1", if (checkedPos == 1) formEdit else "")
                }
            }
        }
        params.addBodyParameter("id", userId)
        params.addBodyParameter("date", date)
        params.addBodyParameter("form_id", formId)
        params.addBodyParameter("supervisor", supervisor.toString())
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String?) {
                if (!RegexUtils.isNull(result)) {
                    Logger.json("更新表单", result)
                    val json = JSONObject(result)
                    val code = json.getInt("code")
                    when (code) {
                        1 -> {
                            ToastUtils.showShort("更新表单成功")
                            val bundle = Bundle()
                            bundle.putString("formID", formId)
                            jumpToInterface(MessageApproval2Activity::class.java, bundle)
                            finish()
                        }
                        -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                        else -> ToastUtils.showShort("更新表单失败")
                    }

                }
            }

            override fun onFinished() {
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }
        })
    }

    private fun getcheckedPos(pos: Int): String {
        return if (pos == checkedPos) {
            "true"
        } else {
            "false"
        }
    }

}

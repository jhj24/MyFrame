package com.zgdj.djframe.activity.work

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.DocumentFileBean
import com.zgdj.djframe.bean.Result
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.SPUtils
import com.zgdj.djframe.utils.toArrayList
import com.zgdj.djframe.utils.toast
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_document_file_edit.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.text.SimpleDateFormat
import java.util.*

class DocumentFileEditActivity : BaseNormalActivity() {
    companion object {
        private const val DEFAULT_KEY = -0x00100000

    }

    private var key = DEFAULT_KEY
    private var documentFileBean: DocumentFileBean.DataBean? = null
    private lateinit var loadingDialog: LoadingDialog //loading dialog
    private var bidList: ArrayList<String>? = null
    private var isRead = false


    override fun initData(bundle: Bundle?) {
        key = bundle?.getInt("documentId", key) ?: key
        documentFileBean = bundle?.getParcelable("data")
        isRead = bundle?.getBoolean("isRead", false) ?: isRead
    }

    override fun bindLayout(): Int {
        return R.layout.activity_document_file_edit
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        loadingDialog = LoadingDialog(this)
        loadingDialog.setMessage("正在上传...")
        val type = listOf("请选择", "A", "B", "C", "D")
        initSpinnerView(spinner_type, type)

        documentFileBean?.let {
            if (isRead) {
                read(it)
            } else {
                et_name.setText(it.picture_name)
                et_code.setText(it.picture_number)
                et_number.setText(it.picture_number)
                et_a1.setText(it.a1_picture)
                et_design.setText(it.design_name)
                et_check.setText(it.check_name)
                et_examine.setText(it.examination_name)
                tv_time.text = it.completion_time
                tv_type.text = it.paper_category
                tv_bid.text = it.section
                val bidIndex = bidList?.indexOf(it.section) ?: -1
                if (bidList?.contains(it.section) == true) {
                    spinner_bid.setSelection(bidIndex)
                }

                val typeIndex = type.indexOf(it.paper_category)
                if (typeIndex != -1) {
                    spinner_type.setSelection(typeIndex)
                }
            }
        }
    }

    private fun read(it: DocumentFileBean.DataBean) {
        et_name.setText(it.picture_name ?: "无")
        et_code.setText(it.picture_number ?: "无")
        et_number.setText(it.picture_number ?: "无")
        et_a1.setText(it.a1_picture ?: "无")
        et_design.setText(it.design_name ?: "无")
        et_check.setText(it.check_name ?: "无")
        et_examine.setText(it.examination_name ?: "无")
        tv_time.text = it.completion_time ?: "无"
        tv_type.text = it.paper_category ?: "无"
        tv_bid.text = it.section ?: "无"

        et_name.isFocusable = false
        et_code.isFocusable = false
        et_number.isFocusable = false
        et_a1.isFocusable = false
        et_design.isFocusable = false
        et_check.isFocusable = false
        et_examine.isFocusable = false
        tv_time.isFocusable = false
        spinner_bid.visibility = View.GONE
        spinner_type.visibility = View.GONE
        tv_type.visibility = View.VISIBLE
        tv_bid.visibility = View.VISIBLE
        btn_commit.visibility = View.GONE


    }


    override fun doBusiness() {
        tv_time.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault())
            val selectedDate = Calendar.getInstance();
            if (!tv_time.text.isNullOrBlank()) {
                val date = sdf.parse(tv_time.text.toString())
                selectedDate.time = date
            }
            TimePickerBuilder(this, OnTimeSelectListener { date, v ->
                val time = sdf.format(date)
                tv_time.text = time
            })
                    .setDate(selectedDate)
                    .setType(booleanArrayOf(true, true, false, false, false, false))// 默认全部显示
                    .isDialog(true)//是否显示为对话框样式
                    .build()
                    .show()

        }
        val requestParams = RequestParams(Constant.URL_WORK_DOCUMENT_PICTURE_BID)
        x.http().post(requestParams, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                if (!result.isNullOrBlank()) {
                    try {
                        val bean = Gson().fromJson<Result<List<String>>>(result, object : TypeToken<Result<List<String>>>() {}.type)
                        if (bean.code == 1) {
                            bidList = bean.data.toArrayList()
                            bidList?.add(0, "请选择")
                            initSpinnerView(spinner_bid, bidList.toArrayList())

                            documentFileBean?.let {
                                val bidIndex = bidList?.indexOf(it.section) ?: -1
                                if (bidList?.contains(it.section) == true) {
                                    spinner_bid.setSelection(bidIndex)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })


        btn_commit.setOnClickListener {
            if (et_name.text.isNullOrBlank()) {
                toast("请输入图名")
                return@setOnClickListener
            } else if (et_code.text.isNullOrBlank()) {
                toast("请输入图号")
                return@setOnClickListener
            } else if (et_a1.text.isNullOrBlank()) {
                toast("请输入折合A1图纸")
                return@setOnClickListener
            } else if (tv_time.text.isNullOrBlank()) {
                toast("请选择完成日期")
                return@setOnClickListener
            }


            val name = SPUtils.getInstance().getString(Constant.KEY_USER_NICK)
            if (!loadingDialog.isShowing) loadingDialog.show()
            val params = RequestParams(Constant.URL_WORK_DOCUMENT_FILE_ADD)
            if (documentFileBean != null) {
                params.addBodyParameter("id", documentFileBean?.id.toString())
            }
            params.addBodyParameter("selfid", key.toString())//分类树节点key
            params.addBodyParameter("picture_name", et_name.text.toString())//图名
            params.addBodyParameter("picture_number", et_code.text.toString())//图号
            params.addBodyParameter("picture_papaer_num", et_number.text.toString())//图纸张数
            params.addBodyParameter("a1_picture", et_a1.text.toString())//折合A1图纸
            params.addBodyParameter("design_name", et_design.text.toString())//设计人员姓名
            params.addBodyParameter("check_name", et_check.text.toString())//校验人姓名
            params.addBodyParameter("examination_name", et_examine.text.toString())//审查人员姓名
            params.addBodyParameter("completion_date", tv_time.text.toString())//完成日期
            params.addBodyParameter("section", spinner_bid.selectedItem.toString())//标段
            params.addBodyParameter("paper_category", spinner_type.selectedItem.toString())//图纸类别
            params.addBodyParameter("current_nickname", name)//上传人
            x.http().post(params, object : Callback.CommonCallback<String> {
                override fun onFinished() {
                    if (loadingDialog.isShowing) loadingDialog.dismiss()
                }

                override fun onSuccess(result: String?) {
                    val jObject = JSONObject(result)
                    val msg = jObject.getString("msg")
                    val code = jObject.getInt("code")
                    toast(msg)
                    if (code == 1) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }

                override fun onCancelled(cex: Callback.CancelledException?) {
                }

                override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                }

            })
        }

    }

    override fun onWidgetClick(view: View?) {
    }


    //图纸类别


    //标段
    private fun initSpinnerView(spinner: Spinner, values: List<String>) {
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line)
        spinnerAdapter.addAll(values)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.w("xx", position.toString())
                //sectionId = bidsId[position].toString()
            }
        }
    }
}

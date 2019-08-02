package com.zgdj.djframe.activity.work

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.StandardFileBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_standard_file_edit.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class StandardFileEditActivity : BaseNormalActivity() {
    private var path: String? = null
    private lateinit var loadingDialog: LoadingDialog //loading dialog
    private var data: StandardFileBean.DataBean? = null
    private var nodeId: Int = -0x00100000

    override fun initData(bundle: Bundle?) {
        data = bundle?.getParcelable<StandardFileBean.DataBean>("data")
        nodeId = bundle?.getInt("nodeId", nodeId) ?: nodeId
        if (nodeId == -0x00100000) {
            throw IllegalArgumentException()
        }

    }

    override fun bindLayout(): Int {
        return R.layout.activity_standard_file_edit
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "工程标准与规范"
        tv_file.setOnClickListener {
            checkPermission()
        }
        data?.let {
            et_code.setText(it.standard_number)
            et_name.setText(it.standard_name)
            tv_time.text = it.material_date
            et_standard.setText(it.alternate_standard)
            et_remark.setText(it.remark)
        }
        loadingDialog = LoadingDialog(this)
        loadingDialog.setMessage("正在上传...")


    }

    override fun doBusiness() {
        btn_commit.setOnClickListener {
            if (et_code.text.isNullOrBlank()) {
                toast("请输入标准编号")
                return@setOnClickListener
            } else if (tv_time.text.isNullOrBlank()) {
                toast("请选择实时日期")
                return@setOnClickListener
            }

            if (!loadingDialog.isShowing) loadingDialog.show()
            val nameId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)
            val params = RequestParams(Constant.URL_WORK_STANDARD_ADD)
            params.addHeader("id", nameId.toString())
            if (data != null) {
                params.addBodyParameter("id", data!!.id.toString())
            }
            params.addBodyParameter("nodeId", nodeId.toString())
            params.addBodyParameter("standard_number", et_code.text.toString())
            params.addBodyParameter("standard_name", et_name.text.toString())
            params.addBodyParameter("material_date", tv_time.text.toString())
            params.addBodyParameter("alternate_standard", et_standard.text.toString())
            params.addBodyParameter("remark", et_remark.text.toString())
            if (!path.isNullOrBlank()) {
                params.addBodyParameter("file", File(path.toString()))
            } else {
                params.addBodyParameter("file", "")
            }

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
                        val intent = Intent()
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }

                override fun onCancelled(cex: Callback.CancelledException?) {
                }

                override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                }

            })
        }
        tv_time.setOnClickListener {
            showDatePickerDialog()
        }
    }

    override fun onWidgetClick(view: View?) {
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val myFormat = "yyyy-MM-dd"
                    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                    tv_time.text = sdf.format(cal.time)
                }
        DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun checkPermission() {
        MPermissionUtils.requestPermissionsResult(this, 1,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                object : MPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        selectFile()
                    }

                    override fun onPermissionDenied() {
                        ToastUtils.showShort("没有权限，无法使用内存！")
                    }

                })
    }

    fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件上传"), 345)
        } catch (ex: android.content.ActivityNotFoundException) {
            toast("请安装一个文件管理器.")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 345 && resultCode == Activity.RESULT_OK) {//345选择文件的请求码
            if (data?.data == null) {
                return
            }
            path = FileUtils.getPath(this, data.data)
            if (path == null) {
                toast("选择文件失败，请重试")
                return
            }
            tv_file.text = path?.getFileName()
        }
    }
}
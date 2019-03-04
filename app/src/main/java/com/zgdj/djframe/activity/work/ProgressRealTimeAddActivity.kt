package com.zgdj.djframe.activity.work

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.constant.Constant.CHOICE_FROM_ALBUM_REQUEST_CODE
import com.zgdj.djframe.constant.Constant.TAKE_PHOTO_REQUEST_CODE
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.CustomerDialog
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_progress_real_time_add.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 新增实时进度
 */
class ProgressRealTimeAddActivity : BaseNormalActivity() {
    private lateinit var values: MutableList<String> //标段值
    private lateinit var bidsId: MutableList<Int> //标段id
    private var cameraUtils: CameraUtils? = null //相机utils
    private var pictureFlag: Boolean = false // 图片是否存在标识
    private var dialog: CustomerDialog? = null
    private var loadingDialog: LoadingDialog? = null//loading

    //入参
    private var sectionId: String = "" //标段编号
    private var actualDate: String = "" //填报日期
    private var userId: String = "" //用户ID
    private var picFile: File? = null //上传的图片
    private var remark: String = "" //备注 [ 不是必传参数 ]

    override fun initData(bundle: Bundle?) {
        values = bundle!!.getStringArrayList("bidsValues")
        bidsId = bundle.getIntegerArrayList("bidsId")
        // 去掉全部
        values[0] = "请选择标段"
        bidsId[0] = 0
    }

    override fun bindLayout(): Int {
        return R.layout.activity_progress_real_time_add
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "新增实时进度"
        initSpinnerView()
        //初始化loading
        loadingDialog = LoadingDialog(this)
    }

    override fun doBusiness() {
        // 日期选择
        progress_text_date.setOnClickListener { showDatePickerDialog() }
        // 填报人
        progress_text_curname.text = SPUtils.getInstance().getString(Constant.KEY_USER_NICK)
        userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)

        //照片
        cameraUtils = CameraUtils(this)
        progress_btn_add_img.setOnClickListener {
            if (pictureFlag) { //存在
                Utils.jumpPictureForView(this, picFile!!)
            } else {
                checkPermission()
            }
        }

        //提交
        progress_btn_ok.setOnClickListener {
            //判断标段
            if (sectionId.isEmpty() || sectionId == "0") {
                ToastUtils.showShort("请选择标段")
                return@setOnClickListener
            }
            //判断日期
            if (progress_text_date.text.isNullOrEmpty()) {
                ToastUtils.showShort("请选择日期")
                return@setOnClickListener
            }
            //判断图片
            if (!pictureFlag) {
                ToastUtils.showShort("请添加图片")
                return@setOnClickListener
            }
            //备注
            remark = progress_edit_remark.text.toString()
            //请求
            uploadImageTask()
        }

    }

    override fun onWidgetClick(view: View?) {

    }

    //初始化spinner
    private fun initSpinnerView() {
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line)
        spinnerAdapter.addAll(values)
        progress_spinner.adapter = spinnerAdapter
        progress_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Logger.w("position:$position")
                sectionId = bidsId[position].toString()
            }
        }
    }

    //日期控件
    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val myFormat = "yyyy-MM-dd"
                    val sdf = SimpleDateFormat(myFormat)
                    progress_text_date.text = sdf.format(cal.time)
                    actualDate = sdf.format(cal.time)
                }
        DatePickerDialog(this@ProgressRealTimeAddActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    //权限问题
    private fun checkPermission() {
        MPermissionUtils.requestPermissionsResult(this, 1,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                object : MPermissionUtils.OnPermissionListener {
                    override fun onPermissionGranted() {
                        cameraUtils!!.showDialog()
                    }

                    override fun onPermissionDenied() {
                        ToastUtils.showShort("没有权限，无法使用相机功能！")
                    }

                })
    }

    //接收权限回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //图片回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TAKE_PHOTO_REQUEST_CODE -> {//拍照
                    picFile = cameraUtils!!.photoFile
                    changeImageSize(true)
                    val options = RequestOptions()
                            .fitCenter()
                            .transform(RoundedCorners(Utils.dp2px(this, 6)))
                    Glide.with(this)
                            .load(picFile)
                            .apply(options)
                            .into(progress_btn_add_img)
                }
                CHOICE_FROM_ALBUM_REQUEST_CODE -> { //相册
                    val uri = data!!.data
                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = this.contentResolver.query(uri, proj, null, null, null)
                    val actualImageColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    val path = cursor.getString(actualImageColumnIndex)
                    picFile = File(path)
                    changeImageSize(true)
                    val options = RequestOptions()
                            .fitCenter()
                            .transform(RoundedCorners(Utils.dp2px(this, 6)))
                    Glide.with(this)
                            .load(picFile)
                            .apply(options)
                            .into(progress_btn_add_img)

                }
            }
        }
    }

    //更改图片大小
    private fun changeImageSize(changeable: Boolean) {
        val params = progress_btn_add_img.layoutParams
        if (changeable) { //有图时
            params.height = Utils.dp2px(this, 100)
            params.width = Utils.dp2px(this, 100)
            progress_btn_add_img.layoutParams = params
            progress_btn_del.visibility = View.VISIBLE
            pictureFlag = true
            progress_btn_del.setOnClickListener {
                if (dialog == null) {
                    dialog = CustomerDialog(this, R.layout.dialog_form_approval)
                    dialog!!.setDlgIfClick(true)
                }
                dialog!!.setOnCustomerViewCreated { window, _ ->
                    val title = window.findViewById<TextView>(R.id.dialog_text_title)
                    val refuse = window.findViewById<Button>(R.id.dialog_btn_refuse)
                    val ok = window.findViewById<Button>(R.id.dialog_btn_ok)
                    title.text = "是否删除当前图片？"
                    refuse.setOnClickListener {
                        // 否
                        dialog!!.dismissDlg()
                    }
                    ok.setOnClickListener {
                        // 是
                        dialog!!.dismissDlg()
                        picFile = null
                        changeImageSize(false)
                    }
                }

                dialog!!.showDlg()
            }
        } else { //没有时
            params.height = Utils.dp2px(this, 60)
            params.width = Utils.dp2px(this, 60)
            progress_btn_add_img.layoutParams = params
            progress_btn_del.visibility = View.GONE
            progress_btn_add_img.setImageResource(R.drawable.icon_add)
            pictureFlag = false
        }
    }


    //图片上传
    private fun uploadImageTask() {
        loadingDialog!!.setMessage("图片上传...")
        loadingDialog!!.show()
        val params = RequestParams(Constant.URL_QUALITY_EVALUATIOHN_UPLOAD_SINGE_IMG)
        params.addHeader("token", token)
        params.addBodyParameter("file", picFile)
        params.addBodyParameter("module", "progress")
        params.addBodyParameter("use", "record")
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                loadingDialog!!.dismiss()
            }

            override fun onSuccess(result: String?) {
                Logger.json("图片上传", result)
                val jObject = JSONObject(result)
                val code = jObject.getInt("code")
                when (code) {
                    1 -> //成功
                        addProgressTask(jObject.getString("id"), jObject.getString("path"))
                    -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                    else -> ToastUtils.showShort(jObject.getString("msg"))
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {

            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })
    }

    //新增进度报表请求
    private fun addProgressTask(attachment_id: String, path: String) {
        loadingDialog!!.setMessage("提交数据...")
        loadingDialog!!.show()
        val params = RequestParams(Constant.URL_WORK_REAL_TIME_PROGRESS_ADD)
        params.addHeader("token", token)
        params.addBodyParameter("section_id", sectionId)//标段id
        params.addBodyParameter("actual_date", actualDate)//日期
        params.addBodyParameter("user_id", userId)//用户id
        params.addBodyParameter("attachment_id", attachment_id)//图片id
        params.addBodyParameter("path", path)//图片地址
        params.addBodyParameter("remark", remark)//备注
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                loadingDialog!!.dismiss()
            }

            override fun onSuccess(result: String?) {
                Logger.json(result!!)
                val jObject = JSONObject(result)
                val code = jObject.getInt("code")
                when (code) {
                    1 -> { //成功
                        ToastUtils.showShort("添加成功")
                        NotifyListenerMangager.getInstance().nofityContext("refresh",
                                "ProgressRealTimeActivity")
                        finish()
                    }
                    -2 -> ToastUtils.showShort(jObject.getString("msg"))
                    else -> ToastUtils.showShort(jObject.getString("msg"))
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })

    }

}

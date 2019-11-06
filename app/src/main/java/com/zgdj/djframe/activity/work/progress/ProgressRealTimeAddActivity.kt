package com.zgdj.djframe.activity.work.progress

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.WindowManager
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.other.PhotoViewActivity
import com.zgdj.djframe.adapter.GlidImageSelectorAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.ImageBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.constant.Constant.CHOICE_FROM_ALBUM_REQUEST_CODE
import com.zgdj.djframe.constant.Constant.TAKE_PHOTO_REQUEST_CODE
import com.zgdj.djframe.utils.*
import com.zgdj.djframe.view.LoadingDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_progress_real_time_add.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

/**
 * 新增实时进度
 */
class ProgressRealTimeAddActivity : BaseNormalActivity() {
    private var bidsId: Int = 0 //标段id
    private lateinit var cameraUtils: CameraUtils //相机utils
    private lateinit var loadingDialog: LoadingDialog//loading
    private var imageAdapter: GlidImageSelectorAdapter? = null

    //入参
    private var actualDate: String = "" //填报日期
    private var userId: String = "" //用户ID
    private var remark: String = "" //备注 [ 不是必传参数 ]

    override fun initData(bundle: Bundle?) {
        bidsId = bundle?.getInt("bidsId", bidsId) ?: bidsId
        // 去掉全部
    }

    override fun bindLayout(): Int {
        return R.layout.activity_progress_real_time_add
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "新增实时进度"
        //初始化loading
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        layout_progress.visibility = View.GONE
        loadingDialog = LoadingDialog(this)
        cameraUtils = CameraUtils(this) //照片
        initImageAdapter()
    }

    override fun doBusiness() {
        // 日期选择
        progress_text_date.setOnClickListener { showDatePickerDialog() }
        // 填报人
        progress_text_curname.text = SPUtils.getInstance().getString(Constant.KEY_USER_NICK)
        userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)

        //提交
        progress_btn_ok.setOnClickListener {
            //判断日期
            if (progress_text_date.text.isNullOrEmpty()) {
                ToastUtils.showShort("请选择日期")
                return@setOnClickListener
            }
            //判断图片
            if (imageAdapter?.dataList.orEmpty().size == 1) {
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

    fun initImageAdapter() {
        imageAdapter = GlidImageSelectorAdapter(this)
        imageAdapter?.dataList = arrayListOf(null)
        recycler_view.adapter = imageAdapter
        recycler_view.layoutManager = GridLayoutManager(this, 3)
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
                    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
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
                        cameraUtils.showDialog()
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
                    imageAdapter?.add(0, cameraUtils.photoFile.path)
                }
                CHOICE_FROM_ALBUM_REQUEST_CODE -> { //相册
                    val uri = data!!.data
                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = this.contentResolver.query(uri, proj, null, null, null)
                    val actualImageColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    val path = cursor.getString(actualImageColumnIndex)
                    imageAdapter?.add(0, path)
                }
            }
        }
    }


    //图片上传
    @SuppressLint("CheckResult")
    private fun uploadImageTask() {
        loadingDialog.setMessage("图片上传...")
        loadingDialog.show()
        var isSuccess = true
        Observable
                .create<List<Int>> {
                    val list = arrayListOf<Int>()
                    try {
                        imageAdapter?.dataList?.forEach {
                            if (it != null) {
                                val params = RequestParams(Constant.URL_QUALITY_EVALUATIOHN_UPLOAD_SINGE_IMG)
                                params.addHeader("token", token)
                                params.addBodyParameter("file", File(it))
                                params.addBodyParameter("module", "progress")
                                params.addBodyParameter("use", "record")
                                val str = x.http().postSync(params, String::class.java)
                                val jObject = JSONObject(str)
                                val code = jObject.getInt("code")
                                when (code) {
                                    1 -> //成功
                                        list.add(jObject.getInt("id"))
                                    //addProgressTask(jObject.getString("id"), jObject.getString("path"))
                                    -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                                    else -> isSuccess = false
                                }
                            }
                        }
                        it.onNext(list)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isSuccess) {
                        addProgressTask(it.joinToString())
                    } else {
                        loadingDialog.dismiss()
                    }
                }


    }

    fun imageOnClick(data: String?) {
        if (data.isNullOrBlank()) {
            checkPermission()
        } else {
            val list = imageAdapter?.dataList?.filterNotNull().orEmpty()
            val imageList = list.map { ImageBean(it, 2) }
            val intent = Intent(this, PhotoViewActivity::class.java)
            intent.putExtra(PhotoViewActivity.KEY_IMAGE_POSITION, list.indexOf(data))
            intent.putExtra(PhotoViewActivity.KEY_IMAGE_LIST, imageList as Serializable)
            startActivity(intent)
        }
    }

    //新增进度报表请求
    private fun addProgressTask(imageIds: String) {
        loadingDialog.setMessage("提交数据...")
        loadingDialog.show()
        val params = RequestParams(Constant.URL_WORK_REAL_TIME_PROGRESS_ADD_MULTI_IMAGE)
        params.addHeader("token", token)
        params.addBodyParameter("division_id", bidsId.toString())//标段id
        params.addBodyParameter("actual_date", actualDate)//日期
        params.addBodyParameter("user_id", userId)//用户id
        params.addBodyParameter("file_ids", imageIds)//图片id
        params.addBodyParameter("remark", remark)//备注
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                loadingDialog.dismiss()
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

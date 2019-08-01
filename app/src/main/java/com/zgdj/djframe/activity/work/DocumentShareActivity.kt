package com.zgdj.djframe.activity.work

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.jhj.flowtaglayout.TagFlowLayout
import com.zgdj.djframe.R
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.model.DocumentUserModel
import com.zgdj.djframe.utils.delete
import com.zgdj.djframe.utils.toArrayList
import com.zgdj.djframe.utils.toast
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_document_share.*
import kotlinx.android.synthetic.main.layout_flow_tag.view.*
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

class DocumentShareActivity : BaseNormalActivity() {

    private var key = -0x00100000
    private lateinit var loadingDialog: LoadingDialog //loading dialog
    private var widthList: ArrayList<DocumentUserModel.DataBean>? = null

    override fun initData(bundle: Bundle?) {
        key = bundle?.getInt("documentId", key) ?: key
        if (key == -0x00100000) {
            throw IllegalArgumentException("传参错误")
        }

    }

    override fun bindLayout(): Int {
        return R.layout.activity_document_share
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "共享人员"
        iv_add.setOnClickListener {
            val intent = Intent(this, DocumentShareSelectorActivity::class.java)
            intent.putExtra("data", widthList)
            startActivityForResult(intent, 1000)
        }
        loadingDialog = LoadingDialog(this)
        loadingDialog.setMessage("加载中...")
    }

    override fun doBusiness() {
        getWhitelist()

    }

    override fun onWidgetClick(view: View?) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            val list = data?.getParcelableArrayListExtra<DocumentUserModel.DataBean>("data")
            widthList = list?.map { DocumentUserModel.DataBean(it.id.toInt() - 10000, it.name, it.pid, it.admin_group_id, it.children) }.toArrayList()
            val str = widthList?.joinToString { it.id.toString() }
            add(str ?: "")
        }
    }

    private fun getWhitelist() {
        if (!loadingDialog.isShowing) loadingDialog.show()
        val params = RequestParams(Constant.URL_WORK_DOCUMENT_SHARE_GET)
        params.addBodyParameter("id", key.toString())//用户id
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                if (loadingDialog.isShowing) loadingDialog.dismiss()
            }

            override fun onSuccess(result: String?) {

                if (!result.isNullOrBlank()) {
                    try {
                        val model = Gson().fromJson<DocumentUserModel>(result, DocumentUserModel::class.java)
                        if (model.code == 1) {
                            widthList = model.data.filterNotNull().toArrayList()
                            widthListShow()
                        } else if (model.code == -1 && model.msg == "没有白名单") {
                            widthList = arrayListOf()
                            widthListShow()
                        } else {
                            toast(model.msg)
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
    }

    fun add(str: String) {
        if (!loadingDialog.isShowing) loadingDialog.show()
        val params = RequestParams(Constant.URL_WORK_DOCUMENT_SHARE_ADD)
        params.addBodyParameter("id", key.toString())//用户id
        params.addBodyParameter("admin_id", str)//用户id
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                if (loadingDialog.isShowing) loadingDialog.dismiss()
            }

            override fun onSuccess(result: String?) {
                val jObject = JSONObject(result)
                val msg = jObject.getString("msg")
                toast(msg)
                widthListShow()
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })

    }

    private fun widthListShow() {
        val layout = findViewById<TagFlowLayout<DocumentUserModel.DataBean>>(R.id.flow_layout)
        layout
                .setDataList(widthList.toArrayList())
                .setLayoutRes(R.layout.layout_flow_tag, object : TagFlowLayout.OnCustomListener {
                    override fun onLayout(view: View, pos: Int) {
                        view.text_view.text = widthList.orEmpty()[pos].name
                        view.iv_delete.setOnClickListener {
                            delete(Constant.URL_WORK_DOCUMENT_SHARE_DEL, "是否删除${widthList.orEmpty()[pos].name}？",
                                    "id" to key.toString(), "admin_id" to widthList.orEmpty()[pos].id.toString()) {
                                getWhitelist()
                            }
                        }
                    }
                })
    }


}
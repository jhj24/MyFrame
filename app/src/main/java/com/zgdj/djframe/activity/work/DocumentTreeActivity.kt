package com.zgdj.djframe.activity.work

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.DocumentTreeAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.DocumentTreeBean
import com.zgdj.djframe.bean.Result
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.ToastUtils
import com.zgdj.djframe.utils.closeKeyboard
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_standard_tree.*
import org.json.JSONException
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.util.*

class DocumentTreeActivity : BaseNormalActivity() {
    private lateinit var loadingDialog: LoadingDialog
    private var adapter: DocumentTreeAdapter? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_standard_tree
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        loadingDialog = LoadingDialog(this)
        loadingDialog.setMessage("加载中...")
        recycler_evaluation.layoutManager = LinearLayoutManager(this)
        //recycler_evaluation.adapter = mTreeAdapter
        layout_evaluation_refresh.isEnableLoadMore = false;
        layout_evaluation_refresh.isEnableRefresh = false;
        title = "图册管理"
        adapter = DocumentTreeAdapter(this)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        recycler_evaluation.setOnTouchListener { v, event ->
            closeKeyboard(v)
            false
        }
        search()
    }

    private fun search() {
        search_view.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //完成自己的事件
                adapter?.search(search_view.text.toString())
            }
            false
        }
    }

    override fun doBusiness() {
        if (!loadingDialog.isShowing) loadingDialog.show()
        recycler_evaluation.adapter = adapter
        val params = RequestParams(Constant.URL_WORK_DOCUMENT_TREE)
        params.addHeader("token", token)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String) {
                Logger.json("子类列表", result)
                if (!result.isEmpty()) {
                    try {

                        val bean = Gson().fromJson<Result<List<DocumentTreeBean>>>(result, object : TypeToken<Result<List<DocumentTreeBean>>>() {}.type)
                        if (bean.code == 1 && bean.data.isNotEmpty()) {
                            adapter?.setDocumentId(bean.data.first().id.toInt())
                            val dataList = bean.data.first().children
                            if (dataList == null || dataList.size == 0) {
                                layout_evaluation_refresh.visibility = View.GONE
                            } else {
                                layout_evaluation_refresh.visibility = View.VISIBLE
                                adapter?.dataList = dataList as ArrayList<DocumentTreeBean>
                                adapter?.notifyDataSetChanged()
                            }

                        } else if (bean.code == 2) {
                            ToastUtils.showShort(Constant.TOKEN_LOST)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {
                ex.printStackTrace()
            }

            override fun onCancelled(cex: Callback.CancelledException) {

            }

            override fun onFinished() {
                if (loadingDialog.isShowing) loadingDialog.dismiss()
            }
        })

    }

    override fun onWidgetClick(view: View?) {
    }

}
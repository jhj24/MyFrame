package com.zgdj.djframe.activity.work

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.MultiTreeAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.Result
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.model.DocumentUserModel
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.ToastUtils
import com.zgdj.djframe.utils.toArrayList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_standard_tree.*
import org.json.JSONException
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.util.*

class DocumentShareSelectorActivity : BaseNormalActivity() {
    private var selectedItemList: List<DocumentUserModel.DataBean>? = null
    private var dataList: List<DocumentUserModel.DataBean>? = null

    override fun initData(bundle: Bundle?) {
        selectedItemList = bundle?.getParcelableArrayList("data")

    }

    override fun bindLayout(): Int {
        return R.layout.activity_standard_tree
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        recycler_evaluation.layoutManager = LinearLayoutManager(this)
        //recycler_evaluation.adapter = mTreeAdapter
        layout_evaluation_refresh.isEnableLoadMore = false;
        layout_evaluation_refresh.isEnableRefresh = false;
        title = "组织机构"
        setRightOnclick("确定") {
            //添加操作
            val intent = Intent()
            val list = getSelectedItems()
            intent.putExtra("data", list)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun doBusiness() {

        val params = RequestParams(Constant.URL_WORK_DOCUMENT_SHARE_TREE)
        params.addHeader("token", token)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String) {
                Logger.json("子类列表", result)
                if (!result.isEmpty()) {
                    try {
                        val bean = Gson().fromJson<Result<List<DocumentUserModel.DataBean>>>(result, object : TypeToken<Result<List<DocumentUserModel.DataBean>>>() {}.type)
                        if (bean.code == 1) {
                            val dataList = bean.data.first().children
                            if (dataList == null || dataList.size == 0) {
                                layout_evaluation_refresh.visibility = View.GONE
                            } else {
                                layout_evaluation_refresh.visibility = View.VISIBLE
                                initDataList(dataList.toArrayList())
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

            }
        })

    }

    override fun onWidgetClick(view: View?) {
    }

    @SuppressLint("CheckResult")
    fun initDataList(dataList: ArrayList<DocumentUserModel.DataBean>) {
        this.dataList = dataList
        Observable
                .create<ArrayList<DocumentUserModel.DataBean>> {
                    recursionCheckState(dataList)
                    it.onNext(dataList)
                }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    val adapter = MultiTreeAdapter(this)
                    recycler_evaluation.adapter = adapter
                    adapter.dataList = dataList
                    adapter.notifyDataSetChanged()
                }

    }

    fun getSelectedItems(): ArrayList<DocumentUserModel.DataBean> {
        val list: ArrayList<DocumentUserModel.DataBean> = arrayListOf()
        parseSelectedItem(dataList.toArrayList(), list)
        return list
    }


    /**
     * 获取所有被选中的item
     */
    private fun parseSelectedItem(dataList: List<DocumentUserModel.DataBean>, list: ArrayList<DocumentUserModel.DataBean>) {
        dataList.forEach { data ->
            if (data.isRoot && data.children?.isNotEmpty() == true) {
                parseSelectedItem(data.children, list)
            }
            if (!data.isRoot && data.isChecked && list.indexOf(data) == -1) {
                list.add(data)
            }
        }
    }

    /**
     * 利用递归，当所有的子节点都被选中的时候则父节点被选中
     */
    private fun recursionCheckState(dataList: List<DocumentUserModel.DataBean>): Boolean {
        var allChildrenChecked = true
        dataList.forEach { data ->
            if (!data.isRoot) {
                allChildrenChecked = setBeanCheckState(data) && allChildrenChecked
            } else if (data.children?.isNotEmpty() == true) {
                data.isChecked = recursionCheckState(data.children)
                allChildrenChecked = data.isChecked && allChildrenChecked
            } else {
                data.isChecked = false
                allChildrenChecked = data.isChecked && allChildrenChecked
            }
        }
        if (dataList.isEmpty()) {
            allChildrenChecked = false
        }
        return allChildrenChecked
    }

    /**
     * 当被选中的list中包含data时，设置data被选中
     */
    private fun setBeanCheckState(data: DocumentUserModel.DataBean): Boolean {
        selectedItemList?.forEach { item ->
            if (item.id.toInt() == data.id.toInt() - 10000 && item.name == data.name) {
                data.isChecked = true
                return true
            }
        }
        return false
    }
}
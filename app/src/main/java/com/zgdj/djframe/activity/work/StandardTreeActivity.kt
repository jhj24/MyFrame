package com.zgdj.djframe.activity.work

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.StandardTreeAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.Result
import com.zgdj.djframe.bean.StandardTreeBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_standard_tree.*
import org.json.JSONException
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.util.*

class StandardTreeActivity : BaseNormalActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_standard_tree
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        recycler_evaluation.layoutManager = LinearLayoutManager(this)
        //recycler_evaluation.adapter = mTreeAdapter
        layout_evaluation_refresh.isEnableLoadMore = false;
        layout_evaluation_refresh.isEnableRefresh = false;
        title = "工程标准与规范"


    }

    override fun doBusiness() {
        val adapter = StandardTreeAdapter(this)
        recycler_evaluation.adapter = adapter
        val params = RequestParams(Constant.URL_WORK_STANDARD_TREE)
        params.addHeader("token", token)
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String) {
                Logger.json("子类列表", result)
                if (!result.isEmpty()) {
                    try {
                        val bean = Gson().fromJson<Result<List<StandardTreeBean>>>(result, object : TypeToken<Result<List<StandardTreeBean>>>() {}.type)
                        if (bean.code == 1) {
                            val dataList = bean.data.first().children
                            if (dataList == null || dataList.size == 0) {
                                layout_evaluation_refresh.visibility = View.GONE
                            } else {
                                layout_evaluation_refresh.visibility = View.VISIBLE
                                adapter.dataList = dataList as ArrayList<StandardTreeBean>
                                adapter.notifyDataSetChanged()
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


}
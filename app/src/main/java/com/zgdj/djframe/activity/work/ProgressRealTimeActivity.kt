package com.zgdj.djframe.activity.work

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.ProgressRealTimeAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.interf.INotifyListener
import com.zgdj.djframe.model.ProgressBidsModel
import com.zgdj.djframe.model.ProgressListModel
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.NotifyListenerMangager
import com.zgdj.djframe.utils.SPUtils
import com.zgdj.djframe.utils.ToastUtils
import com.zgdj.djframe.view.ListViewDialog
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_progress_real_time.*
import kotlinx.android.synthetic.main.layout_empty.*
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.util.*

/**
 * 实时进度
 */
class ProgressRealTimeActivity : BaseNormalActivity(), INotifyListener {

    private lateinit var adapter: ProgressRealTimeAdapter
    private lateinit var list: MutableList<ProgressListModel.DataBean>
    private lateinit var loadingDialog: LoadingDialog //loading dialog
    private lateinit var footerView: View
    private lateinit var listDialog: ListViewDialog
    private lateinit var values: MutableList<String> //标段值
    private lateinit var bidsId: MutableList<Int> //标段id
    private var userId = "" //用户id
    private val pageSize = 10  //一页数量
    private var pageIndex = 1  //当前页码
    private var sectionId = 0 //所选标段id

    override fun initData(bundle: Bundle?) {
        userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_progress_real_time
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        setRightOnclick("添加") {
            //添加操作
            if (values.size > 0) {
                val bundle = Bundle()
                bundle.putStringArrayList("bidsValues", values as ArrayList<String>?)
                bundle.putIntegerArrayList("bidsId", bidsId as ArrayList<Int>?)
                jumpToInterface(ProgressRealTimeAddActivity::class.java, bundle)
            }
        }
        listDialog = ListViewDialog(this)
        NotifyListenerMangager.getInstance().registerListener(this, "ProgressRealTimeActivity")
    }

    override fun doBusiness() {
        title = "实时进度"

        setRefreshAndLoad()
        initListInfo()
        setBids()
    }

    override fun onWidgetClick(view: View?) {

    }


    //设置上下刷新及loading
    private fun setRefreshAndLoad() {
        // 上下拉刷新
        layout_refresh.isEnableLoadMore = false //禁止底部加载
        layout_refresh.setRefreshHeader(ClassicsHeader(this))
        layout_refresh.setOnRefreshListener {
            //下拉刷新
            pageIndex = 1
            getListInfoTask()
        }
        layout_refresh.setRefreshFooter(ClassicsFooter(this))
        layout_refresh.setOnLoadMoreListener {
            //加载更多
            pageIndex++
            getListInfoTask()
        }
        loadingDialog = LoadingDialog(this)
        loadingDialog.setMessage("加载中...")
    }

    //设置标段方法
    private fun setBids() {
        //获取标段列表
        getBidsListTask()
        // 标段选择
        listDialog.setCallBack { position, _ ->
            progress_text_bids_select.text = values[position]
            sectionId = bidsId[position]
        }
        // dialog选择
        progress_text_bids_select.setOnClickListener {
            listDialog.show()
        }
        //查询按钮
        progress_btn_ok.setOnClickListener {
            pageIndex = 1
            getListInfoTask()
        }
    }

    //获取标段选项请求
    private fun getBidsListTask() {
        if (!loadingDialog.isShowing) loadingDialog.show()
        val params = RequestParams(Constant.URL_WORK_REAL_TIME_PROGRESS_SELECTOR)
        params.addHeader("token", token)
        params.addBodyParameter("id", userId)//用户id
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                if (loadingDialog.isShowing) loadingDialog.dismiss()
                getListInfoTask()
            }

            override fun onSuccess(result: String?) {
                Logger.json(result!!)
                val model = Gson().fromJson<ProgressBidsModel>(result, ProgressBidsModel::class.java)
                when {
                    model.code == 1 -> { //成功
                        values = mutableListOf()
                        bidsId = mutableListOf()
                        model.sectionArr.forEach {
                            values.add(it.name)
                            bidsId.add(it.sectionId)
                        }
                        listDialog.setListData(values, 0)
                        progress_text_bids_select.text = values[0]
                    }
                    model.code == -2 -> ToastUtils.showShort(model.msg)
                    else -> ToastUtils.showShort(model.msg)
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {

            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {

            }

        })

    }

    //初始化list信息
    private fun initListInfo() {
        list = mutableListOf()
        footerView = View.inflate(this, R.layout.view_recycler_fooder, null)
        adapter = ProgressRealTimeAdapter(list, R.layout.item_recycler_progress_real_time)
        progress_recycler.adapter = adapter
        progress_recycler.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
//        getListInfoTask() //网络获取数据
    }

    //获取列表信息
    private fun getListInfoTask() {
        if (!loadingDialog.isShowing) loadingDialog.show()
        val params = RequestParams(Constant.URL_WORK_REAL_TIME_PROGRESS_INFO)
        params.addHeader("token", token)
        params.addBodyParameter("id", userId)//用户id
        params.addBodyParameter("sectionId", sectionId.toString())//用户id
        params.addBodyParameter("pagesize", pageSize.toString())//
        params.addBodyParameter("curpage", pageIndex.toString())//
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                if (loadingDialog.isShowing) loadingDialog.dismiss()
                if (pageIndex == 1) { //下拉刷新
                    layout_refresh.finishRefresh()
                } else { //加载更多
                    layout_refresh.finishLoadMore()
                }
            }

            override fun onSuccess(result: String?) {
                Logger.json("列表信息", result!!)
                val model = Gson().fromJson<ProgressListModel>(result, ProgressListModel::class.java)
                if (model.code != 1) {
                    if (model.code == -2) {
                        ToastUtils.showShort(Constant.TOKEN_LOST)
                    } else { //失败
                        ToastUtils.showShort("获取信息失败")
                    }
                } else { //列表成功
                    //数据是否为空
                    if (model.totalnum == 0) {
                        layout_refresh.visibility = View.GONE
                        layout_empty.visibility = View.VISIBLE
                        layout_refresh.isEnableLoadMore = false
                        layout_refresh.isEnableRefresh = false
                        list.clear()
                        adapter.setData(list)
                        adapter.notifyDataSetChanged()
                        adapter.footerView = null

                    } else {
                        layout_refresh.visibility = View.VISIBLE
                        layout_empty.visibility = View.GONE
                        layout_refresh.isEnableRefresh = true
                        // 是否有下一页
                        layout_refresh.isEnableLoadMore = model.totalnum > pageIndex * pageSize
                        //footer提示
                        adapter.footerView = if (model.totalnum > 0 && model.totalnum < pageIndex * pageSize) footerView else null
                        if (pageIndex == 1) list.clear()

                        list.addAll(model.data)
                        adapter.setData(list)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })

    }

    //消息接受
    override fun notifyContext(obj: Any?) {
        if (obj == "refresh") { //刷新列表
            pageIndex = 1
            getListInfoTask()
        }
    }

}

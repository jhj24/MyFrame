package com.zgdj.djframe.activity.work

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.DocumentFileAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.DocumentFileBean
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.interf.INotifyListener
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.NotifyListenerMangager
import com.zgdj.djframe.utils.ToastUtils
import com.zgdj.djframe.view.ListViewDialog
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_standard_list.*
import kotlinx.android.synthetic.main.layout_empty.*
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

class DocumentFileActivity : BaseNormalActivity(), INotifyListener {


    private lateinit var listDialog: ListViewDialog
    private lateinit var loadingDialog: LoadingDialog //loading dialog
    private lateinit var footerView: View
    private lateinit var adapter: DocumentFileAdapter
    private lateinit var list: MutableList<DocumentFileBean.DataBean>

    private val pageSize = 10  //一页数量
    private var pageIndex = 1  //当前页码
    private var key = -0x00100000


    override fun initData(bundle: Bundle?) {
        key = bundle?.getInt("documentId", key) ?: key
        if (key == -0x00100000) {
            throw IllegalArgumentException("传参错误")
        }
    }

    override fun bindLayout(): Int {
        return R.layout.activity_standard_list
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "图册管理"
        listDialog = ListViewDialog(this)
        list = mutableListOf()
        footerView = View.inflate(this, R.layout.view_recycler_fooder, null)
        adapter = DocumentFileAdapter(list, R.layout.item_recycler_document_file)
        progress_recycler.adapter = adapter
        progress_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        NotifyListenerMangager.getInstance().registerListener(this, "DocumentFileActivity")
        search()
    }

    private fun search() {
        search_view.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //完成自己的事件
                getListInfoTask(search_view.text.toString())
            }
            false
        }
        search_view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    getListInfoTask()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun doBusiness() {
        setRefreshAndLoad()
        getListInfoTask()
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


    private fun getListInfoTask(search: String = "") {
        if (!loadingDialog.isShowing) loadingDialog.show()
        val params = RequestParams(Constant.URL_WORK_DOCUMENT_LIST)
        params.addHeader("token", token)
        params.addBodyParameter("selfid", key.toString())//用户id
        params.addBodyParameter("search", search)//用户id
        params.addBodyParameter("pagesize", pageSize.toString())//
        params.addBodyParameter("page", pageIndex.toString())//

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
                val model = Gson().fromJson<DocumentFileBean>(result, DocumentFileBean::class.java)
                if (model.code != 1) {
                    if (model.code == -2) {
                        ToastUtils.showShort(Constant.TOKEN_LOST)
                    } else { //失败
                        ToastUtils.showShort("获取信息失败")
                    }
                } else { //列表成功
                    //数据是否为空
                    if (model.totalnumber == 0) {
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
                        layout_refresh.isEnableLoadMore = model.totalnumber > pageIndex * pageSize
                        //footer提示
                        adapter.footerView = if (model.totalnumber > 0 && model.totalnumber < pageIndex * pageSize) footerView else null
                        if (pageIndex == 1) list.clear()

                        list.addAll(model.data)
                        adapter.setData(list)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
                Log.w("xxx", cex)
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                Log.w("xxx", ex)
            }

        })

    }

    override fun notifyContext(obj: Any?) {
        if (obj == "refresh") { //刷新列表
            pageIndex = 1
            getListInfoTask()
        }
    }
}
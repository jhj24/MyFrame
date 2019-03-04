package com.zgdj.djframe.activity.message

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.ExecutorAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.model.MessageExecutorModel
import com.zgdj.djframe.utils.Logger
import com.zgdj.djframe.utils.RegexUtils
import com.zgdj.djframe.utils.SPUtils
import com.zgdj.djframe.utils.ToastUtils
import com.zgdj.djframe.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_choice_of_executor.*
import kotlinx.android.synthetic.main.layout_empty.*
import kotlinx.android.synthetic.main.layout_home_tab.*
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * 选择执行人
 */
class ChoiceOfExecutorActivity : BaseNormalActivity() {
    private var isLeft: Boolean = false //是否在左边tab
    private var id: String? = null
    private var pageNum: Int = 1 //页码
    private var totalpage: Int = 0// 总页数
    private var listData: MutableList<MessageExecutorModel.DataBean>? = null
    private lateinit var adapter: ExecutorAdapter
    private var loadingDialog: LoadingDialog? = null// loading
    private lateinit var tabLeft: TextView
    private lateinit var tabRight: TextView
    private var searchContent = ""//保存搜索的内容
    private var executorId = "" // 执行人id


    override fun initData(bundle: Bundle?) {
        id = SPUtils.getInstance().getString(Constant.KEY_USER_ID)
        executorId = intent.getStringExtra("executorId")
    }

    override fun bindLayout(): Int {
        return R.layout.activity_choice_of_executor
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) //默认不弹出软键盘
        home_text_tab_left.text = "常用联系人"
        home_text_tab_right.text = "全部联系人"

        loadingDialog = LoadingDialog(this)
        loadingDialog!!.setMessage("努力加载...")
        loadingDialog!!.setSpinnerType(0)

        tabLeft = findViewById(R.id.home_text_tab_left)
        tabRight = findViewById(R.id.home_text_tab_right)

    }

    override fun doBusiness() {
        title = "选择执行人"
        // set recycler ExecutorAdapter
        listData = mutableListOf()
        adapter = ExecutorAdapter(listData, R.layout.item_recycler_executor)
        adapter.setRadioChecked(executorId)
        executor_recycler.adapter = adapter
        executor_recycler.layoutManager = LinearLayoutManager(this)
        adapter.setOnItemClickListener { _, position ->
            setResultFun(position)
        }
        adapter.setRadioClick(object : ExecutorAdapter.RadioClick {
            override fun onClick(view: View, pos: Int) = setResultFun(pos)
        })
        // set refresh
        executor_refresh.isEnableLoadMore = false //关闭加载更多
        executor_refresh.setRefreshHeader(ClassicsHeader(this)) //设置头部样式
        executor_refresh.setOnRefreshListener {
            setTabState(isLeft)//下拉刷新
        }
        executor_refresh.setRefreshFooter(BallPulseFooter(this)) //设置底部样式
        executor_refresh.setOnLoadMoreListener {
            pageNum++
            getExecutorListTask("") //加载更多
        }
        //常用联系人
        tabLeft.setOnClickListener { setTabState(true) }
        //全部联系人
        tabRight.setOnClickListener { setTabState(false) }
        // 默认点待办任务
        tabLeft.performClick()

        //搜索框功能
        executor_edit_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Logger.i("搜索：" + s.toString())
                searchContent = s.toString()
                setTabState(isLeft, s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    //
    private fun setResultFun(position: Int) {
        val intent = Intent()
        intent.putExtra("executorName", listData!![position].nickname)
        intent.putExtra("executorId", listData!![position].id)
        setResult(1, intent)
        finish()
    }

    override fun onWidgetClick(view: View?) {
    }

    //设置tab 状态
    private fun setTabState(isLeft: Boolean) {
        setTabState(isLeft, searchContent)
    }

    //设置tab 状态
    private fun setTabState(isLeft: Boolean, search: String) {
        val white = ContextCompat.getColor(this, R.color.white_2)
        val blue = ContextCompat.getColor(this, R.color.colorPrimary)
        listData!!.clear()
        adapter.setData(listData!!)
        adapter.notifyDataSetChanged()
        this.isLeft = isLeft
        this.pageNum = 1
        if (isLeft) { //left
            getExecutorListTask(search)
            tabLeft.setTextColor(white)
            tabRight.setTextColor(blue)
            tabLeft.setBackgroundResource(R.drawable.shape_tab_left_select)
            tabRight.setBackgroundResource(R.drawable.shape_tab_right_normal)
        } else { // right
            getExecutorListTask(search)
            tabLeft.setTextColor(blue)
            tabRight.setTextColor(white)
            tabLeft.setBackgroundResource(R.drawable.shape_tab_left_normal)
            tabRight.setBackgroundResource(R.drawable.shape_tab_right_select)
        }

    }

    // 常用联系人 && 全部联系人
    private fun getExecutorListTask(search: String) {
        loadingDialog!!.show() //显示loading
        adapter.removeFooterView() // 去掉底线

        var url = if (isLeft) Constant.URL_MESSAGE_GET_OFTEN_EXECUTOR else Constant.URL_MESSAGE_GET_ALL_EXECUTOR
        val params = RequestParams(url)
        params.addHeader("token", token)
        if (isLeft)
            params.addBodyParameter("id", id)//当前人id
        params.addBodyParameter("curpage", pageNum.toString())//页码
        params.addBodyParameter("pagesize", "10")//一页数量
        params.addBodyParameter("search", search)//搜索内容
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
                //结束上下刷新
                executor_refresh.finishRefresh()
                executor_refresh.finishLoadMore()
                loadingDialog!!.dismiss()
            }

            override fun onSuccess(result: String?) {
                if (!RegexUtils.isNull(result)) {
                    Logger.json("常联系人", result)
                    val model = Gson().fromJson(result, MessageExecutorModel::class.java)
                    when {
                        model.code == 1 -> { //获取成功
                            totalpage = model.totalpage //总页数
                            val totalnum = model.totalnum // 总数量
                            executor_refresh.isEnableLoadMore = totalpage > pageNum  //是否可以加载更多
                            if (!executor_refresh.isEnableLoadMore) { //到底了！
                                if (totalnum == 0) {
                                    adapter.removeFooterView()
                                    executor_refresh.visibility = View.GONE
                                    layout_empty.visibility = View.VISIBLE
                                } else {
                                    adapter.footerView = layoutInflater.inflate(R.layout.view_recycler_fooder, null)
                                    executor_refresh.visibility = View.VISIBLE
                                    layout_empty.visibility = View.GONE
                                }
                            } else {//还有下一页
                                adapter.removeFooterView()
                                executor_refresh.visibility = View.VISIBLE
                                layout_empty.visibility = View.GONE
                            }
                            if (pageNum == 1) {
                                listData = model.data
                            } else {
                                listData!!.addAll(model.data)
                            }
                            adapter.setData(listData!!)
                            adapter.notifyDataSetChanged()
                        }
                        model.code == -2 -> ToastUtils.showShort(Constant.TOKEN_LOST)
                        else -> ToastUtils.showShort("获取联系人数据失败")
                    }
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }
        })
    }

}

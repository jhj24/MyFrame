package com.zgdj.djframe.activity.work

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zgdj.djframe.R
import com.zgdj.djframe.adapter.DocumentFileChildAdapter
import com.zgdj.djframe.base.BaseNormalActivity
import com.zgdj.djframe.bean.DocumentFileBean
import com.zgdj.djframe.interf.INotifyListener
import com.zgdj.djframe.utils.NotifyListenerMangager
import com.zgdj.djframe.utils.toArrayList
import com.zgdj.djframe.view.ListViewDialog
import kotlinx.android.synthetic.main.activity_document_file_child.*

class DocumentFileChildActivity : BaseNormalActivity(), INotifyListener {


    private var dataList: ArrayList<DocumentFileBean.DataBean>? = null
    private var data: DocumentFileBean.DataBean? = null
    private lateinit var listDialog: ListViewDialog
    private lateinit var adapter: DocumentFileChildAdapter


    override fun initData(bundle: Bundle?) {
        data = bundle?.getParcelable<DocumentFileBean.DataBean>("data")
        dataList = data?.children.toArrayList()
    }

    override fun bindLayout(): Int {
        return R.layout.activity_document_file_child
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        title = "图纸管理"
        listDialog = ListViewDialog(this)
        adapter = DocumentFileChildAdapter(dataList, R.layout.item_recycler_document_file_child)
        layout_refresh.isEnableLoadMore = false;
        layout_refresh.isEnableRefresh = false;
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        NotifyListenerMangager.getInstance().registerListener(this, "DocumentFileChildActivity")
        setRightOnclick("添加") {
            val intent = Intent(this, DocumentFileChildEditActivity::class.java)
            intent.putExtra("code", data?.picture_number)
            intent.putExtra("id", data?.id ?: -1)
            startActivityForResult(intent, 1000)
        }
    }


    override fun doBusiness() {
        getListInfoTask()
    }

    override fun onWidgetClick(view: View?) {
    }


    private fun getListInfoTask() {
        if (dataList == null || dataList?.size == 0) {
            layout_refresh.visibility = View.GONE
        } else {
            layout_refresh.visibility = View.VISIBLE
            adapter.setData(dataList.toArrayList())
            adapter.notifyDataSetChanged()
        }

    }

    override fun notifyContext(obj: Any?) {
        if (dataList?.map { it.id.toString() }?.contains(obj) == true) {
            val data = dataList?.find { it.id == obj.toString().toInt() }
            dataList?.remove(data)
            getListInfoTask()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            val bean = data?.getParcelableExtra<DocumentFileBean.DataBean>("data")
            if (bean != null) {
                dataList?.add(0, bean)
                adapter.setData(dataList.toArrayList())
                adapter.notifyDataSetChanged()
            }
        }
    }
}
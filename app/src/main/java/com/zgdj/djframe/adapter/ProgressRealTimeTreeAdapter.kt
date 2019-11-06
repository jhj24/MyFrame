package com.zgdj.djframe.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.work.progress.ProgressRealTimeActivity
import com.zgdj.djframe.base.tree.BaseSingleTreeAdapter
import com.zgdj.djframe.bean.ProgressTreeBean
import com.zgdj.djframe.utils.toast
import kotlinx.android.synthetic.main.item_tree_view_leaf.view.*

class ProgressRealTimeTreeAdapter(val mCon: Context) : BaseSingleTreeAdapter<ProgressTreeBean, ProgressRealTimeTreeAdapter.ItemViewHolder>() {
    private var documentId: Int = 1
    override val context: Context
        get() = mCon
    override val reminder: String
        get() = ""

    fun setDocumentId(id: Int) {
        this.documentId = id
    }

    override fun onBindItemHolder(holder: ItemViewHolder, data: ProgressTreeBean, position: Int) {
        val dp10 = (context.resources.displayMetrics.density * 10).toInt()
        with(holder.itemView) {
            tvName.text = data.name
            val paddingLeft = dp10 + dataList[position].itemLevels * 2 * dp10
            llParent.setPadding(paddingLeft, 0, 0, 0)
            ivNode.rotation = if (data.isShowChildren) 90f else 0f
            ivNode.setOnClickListener {
                itemViewOnClick(data)
            }
            holder.itemView.setOnClickListener {
                if (data.isRoot) {
                    mCon.toast(data.name)
                } else {
                    val intent = Intent(mCon, ProgressRealTimeActivity::class.java)
                    intent.putExtra("bidsId", data.id.toInt())
                    mCon.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            ItemViewHolder(inflater.inflate(R.layout.item_tree_view_root, parent, false))
        } else {
            ItemViewHolder(inflater.inflate(R.layout.item_tree_view_leaf, parent, false))
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
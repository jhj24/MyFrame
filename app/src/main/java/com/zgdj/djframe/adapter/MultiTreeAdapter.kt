package com.zgdj.djframe.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zgdj.djframe.R
import com.zgdj.djframe.base.tree.BaseMultiTreeAdapter
import com.zgdj.djframe.model.DocumentUserModel
import kotlinx.android.synthetic.main.item_tree_view_root.view.*

/**
 * Created by jhj on 17-9-19.
 */
class MultiTreeAdapter(private val cont: Context) : BaseMultiTreeAdapter<DocumentUserModel.DataBean, MultiTreeAdapter.ItemViewHolder>() {


    override val context: Context
        get() = cont

    override val reminder: String
        get() = "暂无数据"

    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            ItemViewHolder(inflater.inflate(R.layout.item_tree_view_root, parent, false))
        } else {
            ItemViewHolder(inflater.inflate(R.layout.item_tree_view_leaf, parent, false))
        }
    }

    override fun onBindItemHolder(holder: ItemViewHolder, data: DocumentUserModel.DataBean, position: Int) {
        val dp10 = (context.resources.displayMetrics.density * 10).toInt()
        with(holder.itemView) {
            tvName.text = data.name
            ivCheck.visibility = View.VISIBLE
            if (selectedItem?.name == data.name && selectedItem?.id == data.id) {
                data.isChecked = true
            }
            if (data.isChecked) {
                ivCheck.setImageResource(R.drawable.ic_checked)
            } else {
                ivCheck.setImageResource(R.drawable.ic_uncheck)
            }
            val paddingLeft = dp10 + dataList[position].itemLevels * 2 * dp10
            llParent.setPadding(paddingLeft, 0, 0, 0)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val data = itemView.tag as DocumentUserModel.DataBean
                itemViewOnClick(data)
            }
            itemView.ivCheck.setOnClickListener {
                val data = itemView.tag as DocumentUserModel.DataBean
                checkboxOnClick(data)
            }
        }
    }

}
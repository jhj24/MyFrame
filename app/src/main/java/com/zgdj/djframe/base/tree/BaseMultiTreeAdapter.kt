package com.zgdj.djframe.base.tree

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Toast
import com.zgdj.djframe.bean.IBaseTree
import java.util.*

/**
 * 树型数据多选Adapter
 * Created by jhj on 17-9-19.
 */
abstract class BaseMultiTreeAdapter<T : IBaseTree<T>, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {

    abstract val context: Context
    abstract val reminder: String

    var dataList: ArrayList<T> = arrayListOf()
    var selectedItem: T? = null

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].isRoot) {
            1
        } else {
            0
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: H, position: Int) {
        val data = dataList[position]
        if (selectedItem?.name == data.name && selectedItem?.id == data.id) {
            data.isChecked = true
        }
        holder.itemView.tag = data
        onBindItemHolder(holder, data, position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H =
            onCreateItemHolder(parent, viewType)


    fun itemViewOnClick(data: T) {
        if (!data.isRoot) {
            checkboxOnClick(data)
        } else {
            if (data.children?.isNotEmpty() == true) {
                if (data.isShowChildren) {
                    val children: MutableList<T> = mutableListOf()
                    getItemAllChildren(data, children)
                    dataList.removeAll(children)
                    notifyDataSetChanged()
                } else {
                    val position = dataList.indexOf(data)
                    setChildrenLevels(data)
                    dataList.addAll(position + 1, data.children)
                    notifyItemRangeInserted(position + 1, data.children.size)
                    notifyItemChanged(position)
                }
                data.isShowChildren = !data.isShowChildren
            } else {
                Toast.makeText(context, data.name + reminder, Toast.LENGTH_SHORT).show()
            }


        }
    }


    /**
     * 对于根节点，如果根节点的checkbox被选中或者被取消则器子节点也是选中或者被取消
     * 对于子节点只对该节点进行操作
     */
    fun checkboxOnClick(data: T) {
        val position = dataList.indexOf(data)
        data.isChecked = !data.isChecked
        if (data.isRoot) {
            val count = setChildrenChecked(data)
            notifyItemRangeChanged(position, count + 1)
        } else {
            notifyItemRangeChanged(position, 1)
        }
    }

    /**
     * 获取根节点的所有子节点，并统一设置子节点是否被选中
     */
    private fun setChildrenChecked(data: T): Int {
        if (data.children == null) return 0
        var count = data.children.size
        for (bean in data.children) {
            bean.isChecked = data.isChecked
            count += setChildrenChecked(bean)
        }
        return count
    }

    /**
     * 获取item下所有子类
     */
    private fun getItemAllChildren(bean: T, list: MutableList<T>) {
        if (bean.children != null) {
            list.addAll(bean.children)
            for (data in bean.children) {
                data.isShowChildren = false
                getItemAllChildren(data, list)
            }
        }
    }

    /**
     * 设置item显示级别
     */
    private fun setChildrenLevels(bean: T) {
        if (bean.children == null) return
        for (data in bean.children) {
            data.itemLevels = bean.itemLevels + 1
        }
    }


    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): H
    abstract fun onBindItemHolder(holder: H, data: T, position: Int)
}
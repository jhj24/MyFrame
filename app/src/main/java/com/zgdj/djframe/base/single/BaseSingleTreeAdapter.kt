package com.zgdj.djframe.base.single

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Toast
import com.zgdj.djframe.bean.IBaseTree
import java.util.*

/**
 * 树型数据基础adapter
 * Created by jhj on 17-9-6.
 */
abstract class BaseSingleTreeAdapter<T : IBaseTree<T>, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {

    abstract val context: Context
    abstract val reminder: String

    var dataList: ArrayList<T> = arrayListOf()
    lateinit var list: ArrayList<T>
    var selectedItem: T? = null

    override fun getItemCount(): Int = dataList.size


    /**
     * 当　item　为根节点时。type　为１
     */
    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].isRoot) {
            1
        } else {
            0
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H =
            onCreateItemHolder(parent, viewType)

    override fun onBindViewHolder(holder: H, position: Int) {
        val data = dataList[position]
        holder.itemView?.tag = data
        onBindItemHolder(holder, data, position)
    }

    abstract fun onBindItemHolder(holder: H, data: T, position: Int)
    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): H

    /**
     * 对外公开方法，设置itemView点击事件，（树型数据是打开，关闭还是选择）
     */
    fun itemViewOnClick(bean: T) {
        if (bean.isRoot) {
            if (bean.children != null && bean.children.size > 0) {
                val position = dataList.indexOf(bean)
                if (bean.isShowChildren) {
                    val children: MutableList<T> = mutableListOf()
                    getItemAllChildren(bean, children)
                    dataList.removeAll(children)
                    notifyDataSetChanged()
                } else {

                    setChildrenLevels(bean)
                    dataList.addAll(position + 1, bean.children)
                    notifyItemRangeInserted(position + 1, bean.children.size)
                    notifyItemChanged(position)
                }
                bean.isShowChildren = !bean.isShowChildren
            } else {
                Toast.makeText(context, bean.name + reminder, Toast.LENGTH_SHORT).show()
            }
        } else {
            onCheckedChanged(bean)
        }
    }


    /**
     *  如果该item被选中，遍历List，找出被勾选的其他item，设置为false
     */
    private fun onCheckedChanged(bean: T) {
        val position = dataList.indexOf(bean)
        bean.isChecked = !bean.isChecked
        notifyItemRangeChanged(position, 1)
        if (bean.isChecked) {
            selectedItem = bean
            list.forEach {
                if (it.isChecked && it.id != bean.id && it.name != bean.name) {
                    it.isChecked = false
                    notifyItemRangeChanged(dataList.indexOf(it), 1)
                }
            }
        } else {
            selectedItem = null
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

}
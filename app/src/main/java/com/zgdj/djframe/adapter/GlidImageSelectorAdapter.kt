package com.zgdj.djframe.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zgdj.djframe.R
import com.zgdj.djframe.activity.work.progress.ProgressRealTimeAddActivity
import com.zgdj.djframe.view.CustomerDialog
import kotlinx.android.synthetic.main.item_recycler_picture.view.*
import java.io.File

class GlidImageSelectorAdapter(val context: ProgressRealTimeAddActivity) : RecyclerView.Adapter<GlidImageSelectorAdapter.ItemViewHolder>() {

    var dataList = arrayListOf<String?>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_recycler_picture, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {
        val data = dataList[p1]
        p0.itemView.let {
            if (dataList[p1].isNullOrBlank()) {


            } else {
                it.progress_btn_del.visibility = View.VISIBLE
                it.progress_btn_del.setOnClickListener {
                    val dialog = CustomerDialog(context, R.layout.dialog_form_approval)
                    dialog.setDlgIfClick(true)

                    dialog.setOnCustomerViewCreated { window, _ ->
                        val title = window.findViewById<TextView>(R.id.dialog_text_title)
                        val refuse = window.findViewById<Button>(R.id.dialog_btn_refuse)
                        val ok = window.findViewById<Button>(R.id.dialog_btn_ok)
                        title.text = "是否删除当前图片？"
                        refuse.setOnClickListener {
                            dialog.dismissDlg()
                        }
                        ok.setOnClickListener {
                            // 是
                            dialog.dismissDlg()
                            val index = dataList.indexOf(data)
                            dataList.remove(data)
                            notifyItemRemoved(index)
                        }
                    }

                    dialog.showDlg()


                }
                Glide.with(context)
                        .load(File(data))
                        .into(it.progress_btn_add_img)
            }
            it.setOnClickListener {
                context.imageOnClick(data)
            }
        }


    }

    fun add(i: Int, path: String?) {
        dataList.add(i, path)
        notifyItemInserted(i)
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
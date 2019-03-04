package com.zgdj.djframe.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Button
import android.widget.TextView
import com.zgdj.djframe.R
import com.zgdj.djframe.base.rv.BaseViewHolder
import com.zgdj.djframe.base.rv.adapter.SingleAdapter
import com.zgdj.djframe.constant.Constant
import com.zgdj.djframe.model.ProgressListModel
import com.zgdj.djframe.utils.NotifyListenerMangager
import com.zgdj.djframe.utils.SPUtils
import com.zgdj.djframe.utils.ToastUtils
import com.zgdj.djframe.utils.Utils
import com.zgdj.djframe.view.CustomerDialog
import org.json.JSONObject
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * description:实时进度adapter
 * author: Created by Mr.Zhang on 2018/7/23
 */
class ProgressRealTimeAdapter(list: MutableList<ProgressListModel.DataBean>?, layoutId: Int) :
        SingleAdapter<ProgressListModel.DataBean>(list, layoutId) {
    private var dialog: CustomerDialog? = null


    @SuppressLint("SetTextI18n")
    override fun bind(holder: BaseViewHolder?, data: ProgressListModel.DataBean?) {
        val title = holder!!.getView<TextView>(R.id.item_tv_progress_bids) //标题
        val newspaper = holder.getView<TextView>(R.id.item_tv_progress_newspaper) //填报人
        val date = holder.getView<TextView>(R.id.item_tv_progress_date) //日期
        val reMarks = holder.getView<TextView>(R.id.item_tv_progress_remarks) //备注
        val see = holder.getView<TextView>(R.id.item_tv_progress_see) //查看
        val delete = holder.getView<TextView>(R.id.item_tv_progress_delete) //删除

        // 赋值
        title.text = data!!.section_name
        newspaper.text = "填报人：${data.user_name}"
        date.text = "日期：${data.actual_date}"
        reMarks.text = "备注：${data.remark}"
        //操作
        see.setOnClickListener {
            if (data.path.isNotEmpty()) Utils.jumpPictureForView(mContext, data.path)
        }

        delete.setOnClickListener {
            if (dialog == null) {
                dialog = CustomerDialog(mContext as Activity, R.layout.dialog_form_approval)
                dialog!!.setDlgIfClick(true)
            }
            dialog!!.setOnCustomerViewCreated { window, _ ->
                val title = window.findViewById<TextView>(R.id.dialog_text_title)
                val refuse = window.findViewById<Button>(R.id.dialog_btn_refuse)
                val ok = window.findViewById<Button>(R.id.dialog_btn_ok)
                title.text = "是否删除当前进度？"
                refuse.setOnClickListener {
                    // 否
                    dialog!!.dismissDlg()
                }
                ok.setOnClickListener {
                    // 是
                    dialog!!.dismissDlg()
                    delProgressTask(data.id.toString())
                }
            }

            dialog!!.showDlg()
        }

    }

    //删除item 请求
    private fun delProgressTask(id: String) {
        val params = RequestParams(Constant.URL_WORK_REAL_TIME_PROGRESS_DELETE)
        val token = SPUtils.getInstance().getString(Constant.KEY_TOKEN)
        params.addHeader("token", token)
        params.addBodyParameter("actual_id", id)// 表单 id
        x.http().post(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                val jsonObjects = JSONObject(result)
                val code = jsonObjects.getInt("code")
                val msg = jsonObjects.getString("msg")
                ToastUtils.showShort(msg)
                if (code == 1) {//删除成功
                    NotifyListenerMangager.getInstance().nofityContext("refresh",
                            "ProgressRealTimeActivity")
                } else if (code == -2) {
                    ToastUtils.showShort(Constant.TOKEN_LOST)
                }
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
            }

        })
    }


}


package com.zgdj.djframe.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.QualityEvaluationControlContentBean;
import com.zgdj.djframe.utils.Utils;

import java.util.List;

/**
 * description:控制点列表Adapter
 * author: Created by ShuaiQi_Zhang on 2018/5/29
 * version:1.0
 */
public class QualityContentAdapter extends SingleAdapter<QualityEvaluationControlContentBean.DataBean> {


    public QualityContentAdapter(List<QualityEvaluationControlContentBean.DataBean> list, int layoutId) {
        super(list, layoutId);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(BaseViewHolder holder, QualityEvaluationControlContentBean.DataBean data) {
        TextView name = holder.getView(R.id.item_quality_text_name);
        TextView state = holder.getView(R.id.item_quality_text_state);
        TextView point = holder.getView(R.id.item_quality_text_point);
        TextView pos = holder.getView(R.id.item_quality_text_position);

        name.setText("控制点名称：" + data.getName());
        String status = "未知";
        int statusColor = R.color.new_black;
        if (data.getStatus() == 0) { //状态
            status = "未执行";
            statusColor = R.color.red;
        } else if (data.getStatus() == 1) {
            status = "已执行";
            statusColor = R.color.qualified;
        }
        state.setText("控制点状态：" + status);
        Utils.setTextColor(mContext, state.getText().toString(), state, statusColor, 6, state.length());

        point.setText("控制点编号：" + data.getCode());
        pos.setText("" + (position + 1));
    }

    //获取列表数据
    public List<QualityEvaluationControlContentBean.DataBean> getData() {
        return mData;
    }
}

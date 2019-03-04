package com.zgdj.djframe.adapter;

import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.DynamicBean;

import java.util.List;

/**
 * description: 工程动态Adapter
 * author: Created by ShuaiQi_Zhang on 2018/4/27
 * version: 1.0
 */
public class HomeDynamicAdapter extends SingleAdapter<DynamicBean> {


    public HomeDynamicAdapter(List<DynamicBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, DynamicBean data) {
        TextView title = holder.getView(R.id.item_tv_dynamic_title);
        TextView progress = holder.getView(R.id.item_tv_dynamic_progress);
        TextView date = holder.getView(R.id.item_tv_dynamic_date);

        title.setText(data.getTitle());
        progress.setText(data.getFlag());
        date.setText(data.getDate());


    }
}

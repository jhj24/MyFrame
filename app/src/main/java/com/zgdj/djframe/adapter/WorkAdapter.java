package com.zgdj.djframe.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.WorkBean;

import java.util.List;

/**
 * description: 工作页面recycle 适配器
 * author: Created by ShuaiQi_Zhang on 2018/5/30
 * version:1.0
 */
public class WorkAdapter extends SingleAdapter<WorkBean> {


    public WorkAdapter(List<WorkBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, WorkBean data) {
        TextView name = holder.getView(R.id.item_work_text_name);
        name.setText(data.getName());

        ImageView imageView = holder.getView(R.id.item_work_img);
        imageView.setImageResource(data.getResId());
    }
}

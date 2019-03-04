package com.zgdj.djframe.adapter;

import android.view.View;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.ProjectProgressBean;

import java.util.List;

/**
 * description: 首页 --> 工程进度 图表 ---> Tab
 * author: Created by ShuaiQi_Zhang on 2018/4/28
 * version:1.0
 */
public class HomeTabAdapter extends SingleAdapter<ProjectProgressBean> {
    private List<ProjectProgressBean> list;

    public HomeTabAdapter(List<ProjectProgressBean> list, int layoutId) {
        super(list, layoutId);
        this.list = list;
    }

    // 点击切换
    public void setChose(int position) {
        for (int i = 0; i < list.size(); i++) {
            this.list.get(i).setChose(false);
        }
        this.list.get(position).setChose(true);
        notifyDataSetChanged();
    }

    @Override
    protected void bind(BaseViewHolder holder, ProjectProgressBean data) {
        TextView title = holder.getView(R.id.item_tv_chart_tab_title);
        View flag = holder.getView(R.id.item_line_chart_tab_flag);

        title.setText(data.getTitle());
        if (data.isChose()) {
            flag.setVisibility(View.VISIBLE);
        } else {
            flag.setVisibility(View.INVISIBLE);
        }
    }
}

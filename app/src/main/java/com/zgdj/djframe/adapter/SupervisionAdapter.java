package com.zgdj.djframe.adapter;

import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.SupervisionBean;

import java.util.List;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/4
 * version:
 */
public class SupervisionAdapter extends SingleAdapter<SupervisionBean.MessageBean> {


    public SupervisionAdapter(List<SupervisionBean.MessageBean> list, int layoutId) {
        super(list, layoutId);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setDatas(List<SupervisionBean.MessageBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override
    protected void bind(BaseViewHolder holder, SupervisionBean.MessageBean data) {

    }
}

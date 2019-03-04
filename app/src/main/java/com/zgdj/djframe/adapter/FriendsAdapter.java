package com.zgdj.djframe.adapter;

import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.FriendsBean;

import java.util.List;

/**
 * description: 朋友圈列表Adapter
 * author: Created by ShuaiQi_Zhang on 2018/5/2
 * version:1.0
 */
public class FriendsAdapter extends SingleAdapter<FriendsBean> {

    public FriendsAdapter(List<FriendsBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, FriendsBean data) {

    }
}

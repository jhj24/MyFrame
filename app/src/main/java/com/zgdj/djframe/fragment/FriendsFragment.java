package com.zgdj.djframe.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.FriendsAdapter;
import com.zgdj.djframe.base.BaseFragment;
import com.zgdj.djframe.bean.FriendsBean;
import com.zgdj.djframe.utils.FragmentUtils;
import com.zgdj.djframe.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * description: 朋友圈
 * author: Created by ShuaiQi_Zhang on 2018/4/19
 * version: 1.0
 */
public class FriendsFragment extends BaseFragment implements FragmentUtils.OnBackClickListener {

    private RecyclerView recycler_friends;//列表
    private List<FriendsBean> friendsBeans;//bean
    private FriendsAdapter friendsAdapter;

    public static FriendsFragment newInstance() {
        Bundle args = new Bundle();
        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_friends;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        view.findViewById(R.id.img_friends_send).setOnClickListener(this);
        recycler_friends = view.findViewById(R.id.recycler_friends);
    }

    @Override
    public void doBusiness() {
        friendsBeans = new ArrayList<>();
        friendsBeans.add(new FriendsBean());
        friendsBeans.add(new FriendsBean());
        friendsBeans.add(new FriendsBean());
        friendsAdapter = new FriendsAdapter(friendsBeans, R.layout.item_recycler_friends);
        recycler_friends.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_friends.setAdapter(friendsAdapter);
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.img_friends_send://发布
                ToastUtils.showShort("发布消息！");
                break;
        }
    }

    @Override
    public boolean onBackClick() {
        return false;
    }


}

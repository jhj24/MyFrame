package com.zgdj.djframe.activity.other;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseBackActivity;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.base.rv.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseBackActivity {
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    public void initData(Bundle bundle) {

        for (int i = 0; i < 40; i++) {
            mList.add("列表 ： " + i);
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_recycler_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        getToolBar().setTitle("RecyclerView Demo"); //设置标题
//        closeScrollToolBar();// 关闭滚动ToolBar

        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        mRecyclerView.setAdapter(myAdapter = new MyAdapter(mList, R.layout.item_recycler_test));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);//解决滑动冲突

        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    @Override
    public void doBusiness() {

    }


    @Override
    public void onWidgetClick(View view) {

    }

    class MyAdapter extends SingleAdapter<String> {

        public MyAdapter(List<String> list, @LayoutRes int layoutId) {
            super(list, layoutId);
        }

        @Override
        protected void bind(BaseViewHolder holder, String data) {
            TextView textView = holder.getView(R.id.item_text_title);
            textView.setText(data);
        }
    }


}

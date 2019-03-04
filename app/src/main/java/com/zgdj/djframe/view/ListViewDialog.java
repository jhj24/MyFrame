package com.zgdj.djframe.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.DialogListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 列表式弹框
 * author: Created by ShuaiQi_Zhang on 2018/5/29
 * version: 1.0
 */
public class ListViewDialog extends Dialog {
    private final Context mContext;
    private RecyclerView mListView;
    //    private ArrayAdapter<String> stringArrayAdapter;
    private DialogListAdapter adapter;
    private List<String> mList;
    private onItemClick back;
    private int code; //请求code

    public ListViewDialog(Context context) {
        super(context);
        mContext = context;
        initView();
//        initListView();
    }

    private void initView() {
        View contentView = View.inflate(mContext, R.layout.content_dialog, null);
        mListView = contentView.findViewById(R.id.lv);
        mListView.setLayoutManager(new LinearLayoutManager(mContext));
        setContentView(contentView);
        mList = new ArrayList<>();
        adapter = new DialogListAdapter(mList, R.layout.item_recycler_dialog);
//        stringArrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1);
        mListView.setAdapter(adapter);
       /* mListView.setOnItemClickListener((parent, view, position, id) -> {
            if (back != null) {
                back.onClick(position, code);
            }
            dismiss();
        });*/
        adapter.setOnItemClickListener((view, position) -> {
            if (back != null) {
                back.onClick(position, code);
            }
            dismiss();
        });
    }

    // 设置list数据
    public void setListData(List<String> listData, int code) {
        this.code = code;
        mList = listData;
        adapter.setData(mList);
        adapter.notifyDataSetChanged();
//        stringArrayAdapter.clear();
//        stringArrayAdapter.addAll(listData);
//        stringArrayAdapter.notifyDataSetChanged();
    }

    //设置回调
    public void setCallBack(onItemClick back) {
        this.back = back;
    }

    public interface onItemClick {
        void onClick(int position, int code);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            return;
        }
        setHeight();
    }

    private void setHeight() {
        Window window = getWindow();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (window.getDecorView().getHeight() >= (int) (displayMetrics.heightPixels * 0.6)) {
            attributes.height = (int) (displayMetrics.heightPixels * 0.6);
        }
        window.setAttributes(attributes);
    }

}

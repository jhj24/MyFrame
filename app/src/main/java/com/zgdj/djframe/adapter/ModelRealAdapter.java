package com.zgdj.djframe.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.other.WebViewActivity;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.ModelRealBean;

import java.util.List;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/23
 * version:
 */
public class ModelRealAdapter extends SingleAdapter<ModelRealBean> {

    private String type;

    public ModelRealAdapter(List<ModelRealBean> list, int layoutId, String type) {
        super(list, layoutId);
        this.type = type;
    }

    @Override
    protected void bind(BaseViewHolder holder, ModelRealBean data) {
        TextView title = holder.getView(R.id.item_model_real_text_title);
        title.setText(data.getTitle());
        RecyclerView recyclerView = holder.getView(R.id.item_model_real_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ModelRealChildrenAdapter childrenAdapter = new ModelRealChildrenAdapter(data.getList(),
                R.layout.item_recycler_model_real_child);
        recyclerView.setAdapter(childrenAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        childrenAdapter.setOnItemClickListener((view, position) -> {
            if (!type.equals("real")){
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("key_url", data.getList().get(position).getUrl());
                intent.putExtra("type", type);
                mContext.startActivity(intent);
            }else {
                Uri uri = Uri.parse(data.getList().get(position).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(uri);
                mContext.startActivity(intent);
            }


           /* ;*/
        });

    }
}

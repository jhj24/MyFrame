package com.zgdj.djframe.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.ContentEntity;

import java.util.List;

/**
 * description: 首页recycleView
 * author: Created by ShuaiQi_Zhang on 2018/4/27
 * version: 1.0
 */
public class ContentAdapter extends SingleAdapter<ContentEntity> {

    public ContentAdapter(List<ContentEntity> list, @LayoutRes int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, ContentEntity data) {
        TextView textView = holder.getView(R.id.item_content_title);
        textView.setText(data.getTitle());
        ImageView imageView = holder.getView(R.id.item_content_icon);
        imageView.setImageResource(data.getImgRes());
    }
}

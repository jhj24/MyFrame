package com.zgdj.djframe.adapter;

import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;

import java.util.List;

/**
 * description: 列表dialog
 * author: Created by Mr.Zhang on 2018/7/25
 */
public class DialogListAdapter extends SingleAdapter<String> {

    public DialogListAdapter(List<String> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, String data) {
        TextView title = holder.getView(R.id.item_dialog_title);
        title.setText(data);

    }
}

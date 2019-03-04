package com.zgdj.djframe.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.other.PDFActivity;
import com.zgdj.djframe.activity.other.PhotoViewActivity;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.ImageBean;
import com.zgdj.djframe.bean.MessageListItemBean;
import com.zgdj.djframe.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 收文详情 --  附件列表
 * author: Created by ShuaiQi_Zhang on 2018/6/6
 * version:1.0
 */
public class MessageEnclosureAdapter extends SingleAdapter<MessageListItemBean.AttachmentBean> {


    public MessageEnclosureAdapter(List<MessageListItemBean.AttachmentBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, MessageListItemBean.AttachmentBean data) {
        TextView name = holder.getView(R.id.item_tv_message_enclosure_name);//文件名称
        name.setText(data.getName());

        TextView btnSee = holder.getView(R.id.item_tv_message_enclosure_see);//查看
        btnSee.setOnClickListener(v -> { //查看
            String url = Utils.tranFormURL(data.getUrl());
            if (data.getFileext().equals("jpg") || data.getFileext().equals("png") ||
                    data.getFileext().equals("gif")) {
                List<ImageBean> imgList = new ArrayList<>();
                imgList.add(new ImageBean(url, 2));
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoViewActivity.KEY_IMAGE_LIST, (Serializable) imgList);
                Intent intent = new Intent(mContext, PhotoViewActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            } else if (data.getFileext().equals("pdf")) {
                Intent intent = new Intent(mContext, PDFActivity.class);
                intent.putExtra("key_url", url);
                intent.putExtra("file_type", data.getFileext());
                intent.putExtra("file_name", data.getName() + ".pdf");
                mContext.startActivity(intent);
            } else if (data.getFileext().equals("doc") || data.getFileext().equals("docx") || data.getFileext().equals("txt")) {
                Intent intent = new Intent(mContext, PDFActivity.class);
                intent.putExtra("key_url", url);
                intent.putExtra("file_id", data.getId() + "");
                intent.putExtra("file_type", data.getFileext());
                intent.putExtra("file_name", data.getName() + "." + data.getFileext());
                mContext.startActivity(intent);
            }
        });
    }
}

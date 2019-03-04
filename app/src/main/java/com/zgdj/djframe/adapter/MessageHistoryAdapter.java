package com.zgdj.djframe.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.model.MessageHistoryModel;
import com.zgdj.djframe.utils.Utils;

import java.util.List;

/**
 * description: 消息历史列表Adapter
 * author: Created by Mr.Zhang on 2018/6/26
 */
public class MessageHistoryAdapter extends SingleAdapter<MessageHistoryModel.DataBean> {

    public MessageHistoryAdapter(List<MessageHistoryModel.DataBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, MessageHistoryModel.DataBean data) {
        TextView user = holder.getView(R.id.item_message_history_tv_user); //审批人
        TextView date = holder.getView(R.id.item_message_history_tv_date); //审批日期
        TextView result = holder.getView(R.id.item_message_history_tv_result); //审批结果
        TextView suggest = holder.getView(R.id.item_message_history_tv_suggestion); //审批意见


        // set
        if (!TextUtils.isEmpty(data.getNickname()))
            user.setText(data.getNickname());
        if (!TextUtils.isEmpty(data.getCreate_time())) {
            long time = Long.valueOf(data.getCreate_time());
            date.setText(Utils.getTime2Date(time));
        }
        if (!TextUtils.isEmpty(data.getResult())) {
            result.setText(data.getResult());
            //设置状态颜色
            if (data.getResult().equals("未通过")) {
                result.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            } else if (data.getResult().equals("待提交")) {
                result.setTextColor(ContextCompat.getColor(mContext, R.color.qualified));
            } else if (data.getResult().equals("完成")) {
                result.setTextColor(ContextCompat.getColor(mContext, R.color.bids_4));
            }
        }
        if (!TextUtils.isEmpty(data.getMark())) {
            suggest.setText(data.getMark());
        } else {
            suggest.setText("无");
        }

    }
}

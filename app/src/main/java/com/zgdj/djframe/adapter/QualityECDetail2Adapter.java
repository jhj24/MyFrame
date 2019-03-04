package com.zgdj.djframe.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.QualityEvaluationDetailScanBean;
import com.zgdj.djframe.utils.Utils;

import java.util.List;

/**
 * description:质量验评详情列表 Adapter
 * author: Created by ShuaiQi_Zhang on 2018/5/31
 * version: 1.0
 */
public class QualityECDetail2Adapter extends SingleAdapter<QualityEvaluationDetailScanBean> {


    public QualityECDetail2Adapter(List<QualityEvaluationDetailScanBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, QualityEvaluationDetailScanBean data) {
        TextView text1 = holder.getView(R.id.item_quality_detail_text_01);//填报人
        TextView text2 = holder.getView(R.id.item_quality_detail_text_02);//填报日期
        TextView text3 = holder.getView(R.id.item_quality_detail_text_03);//当前审批人
        TextView text4 = holder.getView(R.id.item_quality_detail_text_04);//审批状态
        if (data.getData() != null && data.getData().size() > 0) {
            QualityEvaluationDetailScanBean.DataBean dataBean = data.getData().get(0);//只有一项
            String info = "填报人：未知";
            if (!TextUtils.isEmpty(dataBean.getNickname()))
                info = "填报人：" + dataBean.getNickname();
            Utils.setTextColor(mContext, info, text1, R.color.middle_black, 4, info.length());

            String info2 = "填报日期：未知";
            if (dataBean.getCreate_time() > 0) {
                info2 = "填报日期：" + Utils.getTime2Date(dataBean.getCreate_time());
            }
            Utils.setTextColor(mContext, info2, text2, R.color.middle_black, 5, info2.length());

            String info3 = "当前审批人：未知";
            if (!TextUtils.isEmpty(dataBean.getCurrentname()))
                info3 = "当前审批人：" + dataBean.getCurrentname();
            Utils.setTextColor(mContext, info3, text3, R.color.middle_black, 6, info3.length());

            String info4 = "审批状态：未知";
            int color = R.color.new_black;//颜色
            if (!TextUtils.isEmpty(dataBean.getApprovestatus())) {
                switch (dataBean.getApprovestatus()) {
                    case "0"://待提交
                        info4 = "审批状态：待提交";
                        color = R.color.red;
                        break;
                    case "1"://审批中
                        info4 = "审批状态：审批中";
                        color = R.color.bids_excellent;
                        break;
                    case "2"://已完成
                        info4 = "审批状态：已完成";
                        color = R.color.bids_total;
                        break;
                }
            }
            Utils.setTextColor(mContext, info4, text4, color, 5, info4.length());
        }
    }


}

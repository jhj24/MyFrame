package com.zgdj.djframe.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.message.MessageApprovalActivity;
import com.zgdj.djframe.activity.message.MessageDetailsActivity;
import com.zgdj.djframe.activity.message.MessageHistoryActivity;
import com.zgdj.djframe.activity.message.MessageRefuseApprovalActivity;
import com.zgdj.djframe.activity.message.MessageRemindActivity;
import com.zgdj.djframe.activity.message.MessageWarmingActivity;
import com.zgdj.djframe.activity.other.WebH5Activity;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.MessageListBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.SPUtils;
import com.zgdj.djframe.view.CustomerDialog;

import java.util.List;

/**
 * description: 消息Adapter
 * author: Created by ShuaiQi_Zhang on 2018/5/2
 * version:1.0
 */
public class MessageAdapter extends SingleAdapter<MessageListBean.PageArrayBean> {
    private CustomerDialog dialog;

    public MessageAdapter(List<MessageListBean.PageArrayBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, MessageListBean.PageArrayBean data) {
        TextView title = holder.getView(R.id.item_tv_message_title);
        TextView time = holder.getView(R.id.item_tv_message_time);
        TextView type = holder.getView(R.id.item_tv_message_type);
        TextView send = holder.getView(R.id.item_tv_message_send);
        TextView state = holder.getView(R.id.item_tv_message_state);
        TextView history = holder.getView(R.id.item_tv_message_history);
        TextView see = holder.getView(R.id.item_tv_message_see);
        View line1 = holder.getView(R.id.item_message_line_1);
        View line2 = holder.getView(R.id.item_message_line_2);


        title.setText(data.getTask_name());
//        content.setText(data.getContent());
        time.setText("接收日期：" + data.getCreate_time());
        type.setText("任务类型：" + data.getTask_category());
        send.setText("发送人：" + data.getSender());

        //获取值
        String messageType = data.getType();
        String unit_id = data.getUint_id();
        String form_name = data.getForm_info().getForm_name();
        boolean supervisor = data.getForm_info().isSupervisor();
        String currentStep = data.getForm_info().getCurrentStep();
        String id = data.getForm_info().getId(); //什么id？
        String cpr_id = data.getForm_info().getCpr_id();
        String formId = data.getId();//表单id
        /**
         * messageType: 1为收发文，2为单元管控, 3为表单退回,
         * 4月计划提醒, 5月填报提醒, 6年中填报提醒, 7年中滞后提醒,
         * 8总填报提醒, 9总预警提醒, 66年尾填报提醒, 77年尾滞后提醒
         */
        if (data.getStatus().equals("1")) { //待办
            state.setText("处理");
//            state.setBackgroundResource(R.drawable.shape_rectangle_frame_blue);
            state.setTextColor(ContextCompat.getColor(mContext, R.color.bids_1));
            //判断是否有历史和查看 - 待办
            if (messageType.equals("1") || messageType.equals("4") || messageType.equals("5")
                    || messageType.equals("6") || messageType.equals("7") || messageType.equals("8")
                    || messageType.equals("9") || messageType.equals("66") || messageType.equals("77")) {
                //隐藏 - 历史、查看
                history.setVisibility(View.GONE);
                see.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
            } else if (messageType.equals("2")) {
                //隐藏 - 历史
                history.setVisibility(View.GONE);
                see.setVisibility(View.VISIBLE);
                line1.setVisibility(View.GONE);
                line2.setVisibility(View.VISIBLE);
            } else if (messageType.equals("3")) {
                //都有
                history.setVisibility(View.VISIBLE);
                see.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.VISIBLE);
            }
        } else { //已办
            state.setText("查看");
//            state.setBackgroundResource(R.drawable.shape_rectangle_frame_green);
            state.setTextColor(ContextCompat.getColor(mContext, R.color.bids_4));
            //隐藏 - 历史、查看
            history.setVisibility(View.GONE);
            see.setVisibility(View.GONE);
            // line
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
        }

        // 待办.处理 or 已办. 查看
        state.setOnClickListener(v -> {
            if (data.getStatus().equals("1")) { //待办
                // --- 处理
                if (messageType.equals("1")) {//收文
                    jumWriting(unit_id, true);
                } else if (messageType.equals("2") || messageType.equals("3")) { // H5
                    //添加一个dialog
                    showDialog(unit_id, form_name, supervisor);
                } else if (messageType.equals("4") || messageType.equals("5") ||
                        messageType.equals("6") || messageType.equals("8") || messageType.equals("9") ||
                        messageType.equals("66")) { //提醒类
                    jumpRemind(formId, unit_id, messageType, data.getTask_category(), 1);
                } else if (messageType.equals("7") || messageType.equals("77")) {
                    //警告类
                    jumpWarming(formId, unit_id, messageType, data.getTask_category(), 1);
                }

            } else { // -- 查看
                if (messageType.equals("1")) {//收文
                    jumWriting(unit_id, false);
                } else if (messageType.equals("4") || messageType.equals("5") ||
                        messageType.equals("6") || messageType.equals("8") || messageType.equals("9") ||
                        messageType.equals("66")) { //提醒类
                    jumpRemind(formId, unit_id, messageType, data.getTask_category(), 2);
                } else if (messageType.equals("7") || messageType.equals("77")) {
                    //警告类
                    jumpWarming(formId, unit_id, messageType, data.getTask_category(), 2);
                } else { // H5
                    jumpWeb(cpr_id, id, currentStep);
                }
            }

        });

        //待办.历史
        history.setOnClickListener(v -> mContext.startActivity(new Intent(mContext,
                MessageHistoryActivity.class).putExtra("key_message_history_unit_id", data.getUint_id())));

        //待办.查看
        see.setOnClickListener(v -> jumpWeb(cpr_id, id, currentStep));


    }

    //跳转H5页面
    private void jumpWeb(String cpr_id, String id, String currentStep) {
        boolean isView = !currentStep.equals("0");
        String h5URL = Constant.URL_MESSAGE_LIST_ITEM_H5 + "?cpr_id=" + cpr_id + "&id=" + id
                + "&currentStep=" + currentStep + "&isView=" + isView;
        String userName = SPUtils.getInstance().getString(Constant.KEY_USER_NAME); //用户名称
        String userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID);//用户id
        // 设置cookie
        WebH5Activity.synCookies(mContext, h5URL, "admin=" + userId);
        Bundle bundle = new Bundle();
        bundle.putString("key_url", h5URL);
        mContext.startActivity(new Intent(mContext, WebH5Activity.class)
                .putExtras(bundle));
    }

    //跳转收发文页面
    private void jumWriting(String unit_id, boolean isLeft) {
        Bundle bundle = new Bundle();
        bundle.putString("major_key", unit_id);
        bundle.putBoolean("MessageType", isLeft);
        mContext.startActivity(new Intent(mContext, MessageDetailsActivity.class)
                .putExtras(bundle));
    }

    //跳转到提醒类页面
    private void jumpRemind(String id, String unit_id, String type, String title, int taskType) {
        Bundle bundle = new Bundle();
        bundle.putString("MessageId", id);
        bundle.putString("MessageUnitId", unit_id);
        bundle.putString("MessageType", type);
        bundle.putString("MessageTitle", title);
        bundle.putInt("MessageTaskType", taskType);
        mContext.startActivity(new Intent(mContext, MessageRemindActivity.class)
                .putExtras(bundle));
    }


    //跳转到警告类页面
    private void jumpWarming(String id, String unit_id, String type, String title, int taskType) {
        Bundle bundle = new Bundle();
        bundle.putString("MessageId", id);
        bundle.putString("MessageUnitId", unit_id);
        bundle.putString("MessageType", type);
        bundle.putString("MessageTitle", title);
        bundle.putInt("MessageTaskType", taskType);
        mContext.startActivity(new Intent(mContext, MessageWarmingActivity.class)
                .putExtras(bundle));
    }

    //是否通过验证dialog
    private void showDialog(String unit_id, String form_name, boolean isJurisdiction) {
        if (dialog == null) {
            dialog = new CustomerDialog((Activity) mContext, R.layout.dialog_form_approval);
            dialog.setDlgIfClick(true);
        }
        dialog.setOnCustomerViewCreated((window, dlg) -> {
            Button refuse = window.findViewById(R.id.dialog_btn_refuse);
            Button ok = window.findViewById(R.id.dialog_btn_ok);
            refuse.setOnClickListener(v -> { // 不通过
                dialog.dismissDlg();
                mContext.startActivity(new Intent(mContext, MessageRefuseApprovalActivity.class)
                        .putExtra("formID", unit_id));

            });
            ok.setOnClickListener(v -> {// 通过
                dialog.dismissDlg();
                Logger.w("formID:" + unit_id + ";form_name:" + form_name + ";supervisor:" + isJurisdiction);
                mContext.startActivity(new Intent(mContext, MessageApprovalActivity.class)
                        .putExtra("formID", unit_id)
                        .putExtra("form_name", form_name)
                        .putExtra("supervisor", isJurisdiction));
            });
        });

        dialog.showDlg();
    }

}

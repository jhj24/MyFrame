package com.zgdj.djframe.activity.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.MessageHistoryAdapter;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.model.MessageHistoryModel;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表 -  查看历史
 */
public class MessageHistoryActivity extends BaseNormalActivity {
    private TextView taskName;//任务名称
    private TextView projectName;//工程名称
    private TextView plieNum;//起止桩号
    private TextView altitude;//高程
    private RecyclerView recyclerView;//列表
    private MessageHistoryAdapter adapter;
    private List<MessageHistoryModel.DataBean> databean;
    private String unitId;//表单id

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_message_history;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        taskName = view.findViewById(R.id.history_tv_task_name);
        projectName = view.findViewById(R.id.history_tv_project_name);
        plieNum = view.findViewById(R.id.history_tv_zhuanghao);
        altitude = view.findViewById(R.id.history_tv_gaocheng);
        recyclerView = view.findViewById(R.id.history_recycler);


    }

    @Override
    public void doBusiness() {
        setTitle("查看历史");
        databean = new ArrayList<>();
        adapter = new MessageHistoryAdapter(databean, R.layout.item_recycler_message_history);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        // 获取id
        unitId = getIntent().getStringExtra("key_message_history_unit_id");
        if (!TextUtils.isEmpty(unitId)) getHistoryTask();

    }

    @Override
    public void onWidgetClick(View view) {

    }

    //获取历史信息Task
    private void getHistoryTask() {
        RequestParams params = new RequestParams(Constant.URL_MESSAGE_HISTORY_INFO);
        params.addHeader("token", token);
        params.addBodyParameter("form_id", unitId);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logger.json("历史信息task：", result);
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    MessageHistoryModel model = gson.fromJson(result, MessageHistoryModel.class);
                    int code = model.getCode();
                    if (code == 1) { //成功
                        databean.addAll(model.getData());
                        adapter.setData(databean);
                        adapter.notifyDataSetChanged();
                        // set info
                        MessageHistoryModel.BasedataBean basedataBean = model.getBasedata();
                        if (basedataBean != null) {
                            taskName.setText(basedataBean.getTaskName());
                            projectName.setText(basedataBean.getDwName());
                            altitude.setText(basedataBean.getPileNo());
                            plieNum.setText(basedataBean.getAltitude());
                        }

                    } else if (code == -2) {
                        ToastUtils.showShort(Constant.TOKEN_LOST);
                    } else {
                        ToastUtils.showShort("获取数据失败");
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

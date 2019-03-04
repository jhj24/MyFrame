package com.zgdj.djframe.activity.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.MessageEnclosureAdapter;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.bean.MessageListItemBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.fragment.MessageFragment;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.NotifyListenerMangager;
import com.zgdj.djframe.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息详情 -- 待办
 */
public class MessageDetailsActivity extends BaseNormalActivity {
    private TextView textFileName;//文件名称
    private TextView textFileDate;//文件日期
    private TextView textSend;//发件人
    private TextView textCompany;//来文单位
    private TextView textRemarks;//备注、
    private RecyclerView recyclerEnclosure;//附件列表
    private MessageEnclosureAdapter enclosureAdapter;
    private Button btnOk; //签收
    private Button btnRefuse; //拒收


    // 入参值
    private String major_key;
    private boolean type = true;//待办 & 已办

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            major_key = bundle.getString("major_key");
            type = bundle.getBoolean("MessageType", true);
            Logs.debug("major_key:" + major_key);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_message_details;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        textFileName = view.findViewById(R.id.message_details_text_name);
        textFileDate = view.findViewById(R.id.message_details_text_date);
        textSend = view.findViewById(R.id.message_details_text_send);
        textCompany = view.findViewById(R.id.message_details_text_company);
        textRemarks = view.findViewById(R.id.message_details_text_remarks);
        recyclerEnclosure = view.findViewById(R.id.message_details_recycler);
        btnOk = view.findViewById(R.id.message_details_btn_ok);
        btnRefuse = view.findViewById(R.id.message_details_btn_refuse);

        btnOk.setOnClickListener(this);
        btnRefuse.setOnClickListener(this);

        if (!type) {
            btnOk.setVisibility(View.GONE);
            btnRefuse.setVisibility(View.GONE);
        }
    }

    @Override
    public void doBusiness() {
        setTitle("收文处理");
        enclosureAdapter = new MessageEnclosureAdapter(new ArrayList<>(), R.layout.item_recycler_message_enclosure);
        recyclerEnclosure.setAdapter(enclosureAdapter);
        recyclerEnclosure.setLayoutManager(new LinearLayoutManager(this));
        recyclerEnclosure.setNestedScrollingEnabled(false);
        getMessageDetailsTask();

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.message_details_btn_ok: //签收
                solveMessageState("3");
                break;

            case R.id.message_details_btn_refuse: //拒收
                solveMessageState("4");
                break;
        }

    }

    //获取消息详情task
    private void getMessageDetailsTask() {
        RequestParams params = new RequestParams(Constant.URL_MESSAGE_LIST_ITEM);
        params.addHeader("token", token);
        params.addBodyParameter("major_key", major_key);
        params.addBodyParameter("see_type", "1");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("getMessageDetailsTask:" + result);
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    MessageListItemBean itemBean = gson.fromJson(result, MessageListItemBean.class);
                    if (!TextUtils.isEmpty(itemBean.getFile_name())) //文件名称
                        textFileName.setText(itemBean.getFile_name());
                    if (!TextUtils.isEmpty(itemBean.getDate())) //文件日期
                        textFileDate.setText(itemBean.getDate());
                    if (!TextUtils.isEmpty(itemBean.getSend_name())) //发件人
                        textSend.setText(itemBean.getSend_name());
                    if (!TextUtils.isEmpty(itemBean.getUnit_name())) //来文单位
                        textCompany.setText(itemBean.getUnit_name());
                    if (!TextUtils.isEmpty(itemBean.getRemark())) //备注
                        textRemarks.setText(itemBean.getRemark());


                    List<MessageListItemBean.AttachmentBean> list = itemBean.getAttachment();
                    if (list.size() > 0) {
                        enclosureAdapter.setData(list);
                        enclosureAdapter.notifyDataSetChanged();
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

    //处理消息状态 - 签收、拒收 =  3: 签收 / 4: 拒收
    private void solveMessageState(String state) {
        RequestParams params = new RequestParams(Constant.URL_MESSAGE_SOLVE_STATE);
//        params.addHeader(Constant.COOKIE_KEY, Constant.COOKIE_ID);
        params.addHeader("token", token);
        params.addBodyParameter("major_key", major_key);
        params.addBodyParameter("status", state);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("处理消息：" + result);
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.has("code")) {
                            int code = object.getInt("code");
                            if (code == 1) { //成功
                                ToastUtils.showShort(state.equals("3") ? "签收成功" : "拒收成功");
                                NotifyListenerMangager.getInstance().nofityContext(MessageFragment.ONREFRESH, MessageFragment.TAG);
                                finish();
                            } else if (code == -2) {
                                ToastUtils.showShort(Constant.TOKEN_LOST);
                            } else {
                                ToastUtils.showShort(state.equals("3") ? "签收失败" : "拒收失败");
                            }
                        }

//                        if (object.has("msg")) {
//                            ToastUtils.showShort(object.getString("msg"));
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
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

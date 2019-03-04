package com.zgdj.djframe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.zgdj.djframe.base.BaseActivity;
import com.zgdj.djframe.bean.MessageListItemBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.fragment.MessageFragment;
import com.zgdj.djframe.fragment.Root0Fragment;
import com.zgdj.djframe.fragment.Root3Fragment;
import com.zgdj.djframe.fragment.WorkFragment;
import com.zgdj.djframe.interf.INotifyListener;
import com.zgdj.djframe.jpush.ExampleUtil;
import com.zgdj.djframe.jpush.LocalBroadcastManager;
import com.zgdj.djframe.utils.BottomNavigationViewHelper;
import com.zgdj.djframe.utils.FragmentUtils;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.NotifyListenerMangager;
import com.zgdj.djframe.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;


/**
 * 主页
 */
public class MainActivity extends BaseActivity implements INotifyListener {

    private static MainActivity mainActivity;
    private BottomNavigationView navigation;
    private Fragment[] mFragments = new Fragment[4];
    private int curIndex;
    public static boolean isForeground = false;
    public static final String TAG = "MainActivity";
    private String type = null;


    public static MainActivity getInstance() {
        return mainActivity;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex");
        }
        mainActivity = this;
        navigation = findViewById(R.id.navigation_fragment);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        mFragments[0] = Root0Fragment.newInstance();
        mFragments[1] = WorkFragment.newInstance();
        mFragments[2] = MessageFragment.newInstance();
        mFragments[3] = Root3Fragment.newInstance();

        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fragment_container, curIndex);


    }

    @Override
    public void doBusiness() {
        registerMessageReceiver();
        NotifyListenerMangager.getInstance().registerListener(this, TAG);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_fragment_zero:
                showCurrentFragment(0);
                return true;
            case R.id.navigation_fragment_one:
                showCurrentFragment(1);
                return true;
            case R.id.navigation_fragment_two:
                showCurrentFragment(2);
                return true;
            case R.id.navigation_fragment_three:
                showCurrentFragment(3);
                return true;
        }
        return false;
    };


    private void showCurrentFragment(int index) {
        FragmentUtils.showHide(curIndex = index, mFragments);
    }

    /**
     * 获取当前fragment下标
     *
     * @return
     */
    public int getCurrentIndex() {
        return curIndex;
    }

    @Override
    public void onWidgetClick(View view) {

    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        NotifyListenerMangager.getInstance().unRegisterListener(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.zgdj.djframe.jpush.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void notifyContext(Object obj) {
        getNotifyInfoTask(obj.toString());
    }

    //major_key:40,type:收文,see_type:2 - 收文
    // id:593,type:单元工程,CurrentApproverId:1,CurrentStep:1,cpr_id:14307     -H5
    //获取消息要显示的信息
    private void getNotifyInfoTask(String result) {
        Logs.debug("请求接口获取数据展示在通知栏:" + result);
        String major_key = null;
        String see_type = null;
        //单元工程
        String cpr_id = "";
        String id = "";
        String currentStep = "";

        if (result.contains(",")) {
            String[] content = result.split(",");
            if (content.length > 2) {
                if (content[1].contains("type")) {
                    type = content[1].split(":")[1]; // 取值
                    Log.e("type:", type);
                    if (type.equals("收文")) {
                        if (content[0].contains("major_key")) {
                            major_key = content[0].split(":")[1]; // 取值
                            Log.e("major_key:", major_key);
                        }
                        if (content[2].contains("see_type")) {
                            see_type = content[2].split(":")[1]; // 取值
                            Log.e("see_type:", see_type);
                        }
                        getMessageDetailsTask(major_key, see_type);
                    } else if (type.equals("单元工程")) {
                        if (content[4].contains("cpr_id")) {
                            cpr_id = content[4].split(":")[1]; // 取值
                            Log.e("cpr_id:", cpr_id);
                        }
                        if (content[0].contains("id")) {
                            id = content[0].split(":")[1]; // 取值
                            Log.e("id:", id);
                        }

                        if (content[3].contains("currentStep")) {
                            currentStep = content[3].split(":")[1]; // 取值
                            Log.e("currentStep:", currentStep);
                        }

                        boolean isView = !currentStep.equals("0");

                        String url = Constant.URL_MESSAGE_LIST_ITEM_H5 + "?cpr_id=" + cpr_id + "&id=" + id
                                + "&currentStep=" + currentStep + "&isView=" + isView;
                        JSONObject object = new JSONObject();
                        try {
                            object.put("url", url);
                            object.put("type", "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        sendNotification("新消息", "单元工程", object);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }


        }


    }

    // 获取消息详细task
    private void getMessageDetailsTask(String major_key, String see_type) {
        RequestParams params = new RequestParams(Constant.URL_MESSAGE_LIST_ITEM);
//        params.addHeader(Constant.COOKIE_KEY, Constant.COOKIE_ID);
        String token = SPUtils.getInstance().getString(Constant.KEY_TOKEN);
        params.addHeader("token", token);
        params.addBodyParameter("major_key", major_key);
        params.addBodyParameter("see_type", see_type);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("请求成功：" + result);
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    MessageListItemBean itemBean = gson.fromJson(result, MessageListItemBean.class);
                    JSONObject object = new JSONObject();
                    try {
                        object.put("messageId", itemBean.getId() + "");
                        object.put("type", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendNotification("新消息·收文", itemBean.getFile_name(), object);
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


    //发送通知栏消息
    private void sendNotification(String title, String content, JSONObject object) {
        JPushLocalNotification notification = new JPushLocalNotification();
        notification.setTitle(title);
        notification.setContent(content);
        notification.setNotificationId(1);
        String extras = new Gson().toJson(object);
        Logs.debug("extras:" + extras);
        notification.setExtras(extras);
        JPushInterface.addLocalNotification(MainActivity.this, notification);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Logs.debug("Main:MessageReceiver" + intent.getAction());
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    Logs.debug(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }


}

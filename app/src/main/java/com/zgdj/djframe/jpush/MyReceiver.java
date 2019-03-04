package com.zgdj.djframe.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.zgdj.djframe.MainActivity;
import com.zgdj.djframe.activity.message.MessageDetailsActivity;
import com.zgdj.djframe.activity.other.WebH5Activity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.NotifyListenerMangager;
import com.zgdj.djframe.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                String notifactionContent = bundle.getString(JPushInterface.EXTRA_ALERT);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                NotifyListenerMangager.getInstance().nofityContext(notifactionContent, MainActivity.TAG);
                if (notifactionId != 1)
                    JPushInterface.clearNotificationById(context, notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
                String keys = bundle.getString(JPushInterface.EXTRA_EXTRA);
                String major_key;
                String type;
                JSONObject object = new JSONObject(keys);
                if (object.has("nameValuePairs")) {
                    JSONObject jsonObject = object.getJSONObject("nameValuePairs");

                    if (jsonObject.has("type")) {
                        type = jsonObject.getString("type");
                        if (type.equals("0")) { //收文
                            major_key = jsonObject.getString("messageId");
                            //打开自定义的Activity
                            Intent i = new Intent(context, MessageDetailsActivity.class);
                            i.putExtra("major_key", major_key);
                            context.startActivity(i);
                        } else if (type.equals("1")) { //单元工程
                            String url = jsonObject.getString("url");
                            String userName = SPUtils.getInstance().getString(Constant.KEY_USER_NAME); //用户名称
                            String userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID);//用户id
                            // 设置cookie
                            WebH5Activity.synCookies(context, url, userName + "=" + userId);
                            Intent intent1 = new Intent(context, WebH5Activity.class);
                            intent1.putExtra("key_url", url);
                            context.startActivity(intent1);
                        } else {
                            Logs.debug("点击notification，type类型出错！！！");
                        }
                    }

                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);


            } else {
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.d(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");

                    }
//                    getNotificationInfoTask(json.optString(JPushInterface.EXTRA_ALERT));
                } catch (JSONException e) {
                    Log.d(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }


    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }


}

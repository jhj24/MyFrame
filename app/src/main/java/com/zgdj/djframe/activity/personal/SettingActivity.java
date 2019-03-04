package com.zgdj.djframe.activity.personal;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zgdj.djframe.MainActivity;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.jpush.TagAliasOperatorHelper;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.RegexUtils;
import com.zgdj.djframe.utils.SPUtils;
import com.zgdj.djframe.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;

import cn.jpush.android.api.JPushMessage;

/**
 * 设置页面
 */
public class SettingActivity extends BaseNormalActivity {
    private int userId = 0;//用户id

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        view.findViewById(R.id.setting_btn_logout).setOnClickListener(this);


    }

    @Override
    public void doBusiness() {
        setTitle("设置");
        userId = Integer.parseInt(SPUtils.getInstance().getString(Constant.KEY_USER_ID));
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.setting_btn_logout: //退出登录
                showDialog();
                break;
        }

    }

    // 退出登录
    private void logoutTask() {
        RequestParams params = new RequestParams(Constant.URL_LOGOUT);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!RegexUtils.isNull(result)) {
                    Logs.debug("退出登录：" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.has("code")) {
                            int code = object.getInt("code");
                            if (code == 1) { //成功
                                ToastUtils.showShort("退出成功！");
                                //清理缓存
                                SPUtils.getInstance().clear();
                                // 清理极光别名
                                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                                tagAliasBean.action = TagAliasOperatorHelper.ACTION_DELETE;
                                TagAliasOperatorHelper.sequence = userId;
                                tagAliasBean.alias = "";
                                tagAliasBean.isAliasAction = true;
                                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), TagAliasOperatorHelper.sequence, tagAliasBean);
                                TagAliasOperatorHelper.getInstance().onAliasOperatorResult(SettingActivity.this, new JPushMessage() {
                                    @Override
                                    public String getAlias() {
                                        //跳转到登录页
                                        jumpToInterface(LoginActivity.class);
                                        //关闭页面
                                        if (!MainActivity.getInstance().isFinishing())
                                            MainActivity.getInstance().finish();
                                        finish();
                                        return super.getAlias();
                                    }
                                });
                            } else {//失败
                                ToastUtils.showShort("退出失败！");
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort("退出失败！");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //提示框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提醒");
        builder.setMessage("是否退出登录？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialog, which) -> logoutTask());
        builder.show();
    }
}

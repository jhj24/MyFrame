package com.zgdj.djframe.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import com.zgdj.djframe.MainActivity;
import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.personal.LoginActivity;
import com.zgdj.djframe.base.BaseActivity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.RegexUtils;
import com.zgdj.djframe.utils.SPUtils;

/**
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity {


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        //定义全屏参数 、隐藏状态栏
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);

        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness() {
        handler.sendEmptyMessageDelayed(0, 2000);// 延迟2秒
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID);
            if (RegexUtils.isNull(userId)) { //去登录
                jumpToInterface(LoginActivity.class);
            } else { //去主页
                jumpToInterface(MainActivity.class);
            }
            finish();
        }
    };

    @Override
    public void onWidgetClick(View view) {

    }
}

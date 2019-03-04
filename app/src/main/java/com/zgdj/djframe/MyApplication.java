package com.zgdj.djframe;


import com.baidu.mapapi.SDKInitializer;
import com.zgdj.djframe.base.BaseApplication;
import com.zgdj.djframe.utils.Utils;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * author: Created by ShuaiQi_Zhang on 2018/4/18
 * version:1.0
 */
public class MyApplication extends BaseApplication {

    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
        x.Ext.init(this);//xUtils框架初始化
        x.Ext.setDebug(true);//xUtils debug开启
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this);
        //极光推送 设置调试模式
        JPushInterface.setDebugMode(false);
        //初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        JPushInterface.init(this);
        //禁止推送notification 弹出
        JPushInterface.clearAllNotifications(this);
    }


}

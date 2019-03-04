package com.zgdj.djframe.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.zgdj.djframe.utils.BarUtils;
import com.zgdj.djframe.utils.ToastUtils;


/**
 * description: Activity基础类
 * author: Created by ShuaiQi_Zhang on 2018/4/18
 * version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    /**
     * 当前 Activity 渲染的视图 View
     */
    protected View contentView;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    protected BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        BarUtils.setStatusBarLightMode(this, true);
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        setBaseView(bindLayout());
        initView(savedInstanceState, contentView);
        doBusiness();
    }

    /**
     * 设置Activity View
     *
     * @param layoutId
     */
    protected void setBaseView(@LayoutRes int layoutId) {
        setContentView(contentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }


    /**
     * 跳转到某个界面（无参数）
     *
     * @param clazz 需要跳转到的界面（activity）
     */
    public void jumpToInterface(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 跳转到某个界面（传数据）
     *
     * @param clazz  需要跳转到的界面（activity）
     * @param bundle 传递的数据
     */
    public void jumpToInterface(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * dialog取消时的监听
     *
     * @param listener
     */
    public void addDialogDismiss(DialogInterface.OnCancelListener listener) {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.setOnCancelListener(listener);
//        }
    }

    /**
     * 耗时操作加载完成时调用的方法（子类根据不同显示行为重写）
     */
    public void onFinishLoading(int type) {
        // hideLoadingDialog();
    }

    /**
     * 耗时操作加载时调用的方法（子类根据不同显示行为重写）
     */
    public void onLoading(int type) {
        //showLoadingDialog();
    }

    /**
     * 网络异常时调用的方法（子类根据不同显示行为重写）
     */
    public void onNetError(int type) {
//        Utils.toast("网络异常");
        ToastUtils.showShort("网络异常");
    }

    /**
     * 取消耗时操作时调用的方法
     */
    public void onCancel(int type) {
//        Utils.toast("请求已经取消");
        ToastUtils.showShort("请求已经取消");
    }

    /**
     * 无数据的调用方法
     */
    public void noData(int type) {
        //Utils.toast("无数据");
    }


}

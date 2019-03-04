package com.zgdj.djframe.net.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;

import com.zgdj.djframe.MyApplication;
import com.zgdj.djframe.base.BaseActivity;

import java.lang.ref.WeakReference;

import io.reactivex.subscribers.DisposableSubscriber;

import static com.zgdj.djframe.net.retrofit.HttpResult.NO_CONTENT;
import static com.zgdj.djframe.net.retrofit.HttpResult.SERVICE_ERROR;
import static com.zgdj.djframe.net.retrofit.ProgressHandler.NET_ERROR;
import static com.zgdj.djframe.net.retrofit.RxNet.NetProxy.ApiException.USER_NO_LOGIN;


/**
 * Created by LiXiaoSong on 2016/10/21.
 *
 * @Describe
 */

public class NetSubscriber<T> extends DisposableSubscriber<T> implements ProgressHandler.ProgressCancelListener {
    private NetCallback callBack;
    private ProgressHandler handler;
    private boolean isShowDialog;
    private WeakReference<Context> context;
    private int hasMorePage;//是否有更多页，用于列表结构
    private int type;//本次请求的标识位

    /**
     * 可设置是否显示loading对话框的网络请求
     *
     * @param callBack
     * @param context
     * @param isShowDialog 是否需要显示loading对话框
     */
    public NetSubscriber(NetCallback callBack, Context context, boolean isShowDialog) {
        this.callBack = callBack;
        handler = new ProgressHandler(context, false, this);
        this.context = new WeakReference<Context>(context);
        this.isShowDialog = isShowDialog;
    }

    public NetSubscriber(NetCallback callBack, Context context, int type) {
        this.callBack = callBack;
        handler = new ProgressHandler(context, false, this);
        this.context = new WeakReference<Context>(context);
        this.isShowDialog = true;
        this.type = type;
    }

    public void setMorePage(int hasMorePage) {
        this.hasMorePage = hasMorePage;
    }

    /**
     * 显示loading对话框的网络请求
     *
     * @param callBack 网络结果回调
     * @param context  本次的上下文
     */
    public NetSubscriber(NetCallback callBack, Context context) {
        this.callBack = callBack;
        handler = new ProgressHandler(context, false, this);
        this.context = new WeakReference<Context>(context);
        isShowDialog = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowDialog) {
            Message message = handler.obtainMessage();
            message.arg1 = type;
            message.what = ProgressHandler.MESSAGE_SHOW;
            message.sendToTarget();
            callBack.onLoading();
        }
        if (!isNetworkAvailable()) {
            Message message = handler.obtainMessage();
            message.arg1 = type;
            message.what = NET_ERROR;
            message.sendToTarget();
            if (context.get() instanceof BaseActivity) {
                ((BaseActivity) context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onNetError(type);
                    }
                });
            } else {
                callBack.onNetError(type);
            }
            callBack.onLoadFinish();
        }
        if (context.get() instanceof BaseActivity) {
            ((BaseActivity) context.get()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBack.onStart();
                }
            });
        } else {
            callBack.onStart();
        }
    }

    @Override
    public void onComplete() {
        if (isShowDialog) {
            Message message = handler.obtainMessage();
            message.arg1 = type;
            message.what = ProgressHandler.MESSAGE_HIDE;
            message.sendToTarget();
            callBack.onLoadFinish();
        }
    }

    @Override
    public void onError(final Throwable e) {
        //e.printStackTrace();
        if (!isNetworkAvailable()) {
            return;//断网问题已经在onStart处理了
        }
        Message message = handler.obtainMessage();
        message.arg1 = type;
        message.what = ProgressHandler.MESSAGE_HIDE;
        message.sendToTarget();
        callBack.onLoadFinish();
        if (e instanceof RxNet.NetProxy.ApiException) {
            if (((RxNet.NetProxy.ApiException) e).getCode() == USER_NO_LOGIN) {
                if (this.context != null && this.context.get() instanceof BaseActivity) {
                    //踢掉功能
//                    ((BaseActivity) context.get()).getSingleLogin();
                    return;
                }
            } else if (((RxNet.NetProxy.ApiException) e).getCode() == SERVICE_ERROR) {//服务器异常
                callBack.onError(SERVICE_ERROR, e.getMessage());
                return;
            } else if (((RxNet.NetProxy.ApiException) e).getCode() == NO_CONTENT) {//无数据
                Message no_content = handler.obtainMessage();
                no_content.arg1 = type;
                no_content.what = ProgressHandler.NO_CONTENT;
                no_content.sendToTarget();
                callBack.noData(type);
                callBack.noData(type, e.getMessage());//用于某些特殊情况处理
            } else {
                callBack.onError(((RxNet.NetProxy.ApiException) e).getCode(), e.getMessage());
            }

        } else {
            callBack.onError(NET_ERROR, e.getMessage());
            return;
        }

    }

    @Override
    public void onNext(T t) {
        callBack.getData(t, hasMorePage == 1);
    }

    @Override
    public void cancelDialog() {
        if (!isDisposed()) {
            dispose();
            Message message = handler.obtainMessage();
            message.arg1 = type;
            message.what = ProgressHandler.CANCEL;
            message.sendToTarget();
        }
    }

    public static abstract class NetCallback<T> {
        public abstract void getData(T t, boolean hasMorePage);

        public void onError(int code, String errorMsg) {
        }

        public void onStart() {

        }

        public void noData(int type) {

        }

        public void noData(int type, String message) {

        }

        public void onNetError(int type) {

        }

        public void onLoading() {

        }

        public void onLoadFinish() {

        }
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */

    private boolean isNetworkAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

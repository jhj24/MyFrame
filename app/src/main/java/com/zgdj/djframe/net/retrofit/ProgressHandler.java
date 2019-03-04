package com.zgdj.djframe.net.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.zgdj.djframe.base.BaseActivity;


/**
 * Created by LiXiaoSong on 2016/10/21.
 *
 * @Describe 网络loading框
 */

public class ProgressHandler extends Handler {
    public static final int MESSAGE_SHOW = 1;
    public static final int MESSAGE_HIDE = 2;
    public static final int NO_CONTENT = 3;
    public static final int NET_ERROR = 4;
    public static final int CANCEL = 5;
    private ProgressDialog dialog;
    private Context context;
    private boolean cancelable;
    private ProgressCancelListener listener;

    /**
     * @param context    上下文必须是继承BaseActivity的方法
     * @param cancelable
     * @param listener
     */
    public ProgressHandler(Context context, boolean cancelable, ProgressCancelListener listener) {
        this.context = context;
        this.cancelable = cancelable;
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_HIDE:
                dismissDialog(msg.arg1);
                break;
            case MESSAGE_SHOW:
                initAndShowDialog(msg.arg1);
                break;
            case NO_CONTENT:
                noData(msg.arg1);
                break;
            case NET_ERROR:
                netError(msg.arg1);
                break;
            case CANCEL:
                cancel(msg.arg1);
                break;
        }
    }

    private void initAndShowDialog(int type) {
        if (context != null && context instanceof BaseActivity) {
            ((BaseActivity) context).onLoading(type);
            ((BaseActivity) context).addDialogDismiss(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    listener.cancelDialog();
                }
            });
        }
    }

    private void dismissDialog(int type) {
        if (context != null && context instanceof BaseActivity) {
            if (context != null && context instanceof BaseActivity) {
                ((BaseActivity) context).onFinishLoading(type);
            }
        }
    }

    private void noData(int type) {
        if (context != null && context instanceof BaseActivity) {
            ((BaseActivity) context).noData(type);
            ((BaseActivity) context).onFinishLoading(type);
        }
    }

    private void netError(int type) {
        if (context != null && context instanceof BaseActivity) {
            ((BaseActivity) context).onNetError(type);
            ((BaseActivity) context).onFinishLoading(type);
        }
    }

    private void cancel(int type) {
        if (context != null && context instanceof BaseActivity) {
            ((BaseActivity) context).onCancel(type);
            ((BaseActivity) context).onFinishLoading(type);
        }
    }

    public interface ProgressCancelListener {
        void cancelDialog();
    }
}

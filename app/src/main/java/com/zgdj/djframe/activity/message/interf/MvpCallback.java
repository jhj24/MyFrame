package com.zgdj.djframe.activity.message.interf;

import org.xutils.common.Callback;

/**
 * MVP网络请求回调
 */
public interface MvpCallback {
    /**
     * 数据请求成功
     *
     * @param data 请求到的数据
     */
    void onSuccess(String data);

    /**
     * 网络请求时，主动取消
     */
    void onCancelled(Callback.CancelledException cex);

    /**
     * 网络错误
     */
    void onError(Throwable ex);

    /**
     * 网络结束时
     */
    void onFinished();
}

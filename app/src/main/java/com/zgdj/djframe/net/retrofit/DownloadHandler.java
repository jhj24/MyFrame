package com.zgdj.djframe.net.retrofit;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by LiXiaoSong on 2017/2/20.
 *
 * @Describe
 */

public abstract class DownloadHandler {
    protected abstract void sendMessage(ProgressBean progressBean);

    protected abstract void handleMessage(Message message);

    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler {

        private DownloadHandler mProgressHandler;
        public ResponseHandler(DownloadHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }
}

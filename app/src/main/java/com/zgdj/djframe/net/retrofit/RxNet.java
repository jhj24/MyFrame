package com.zgdj.djframe.net.retrofit;

import android.content.Context;
import android.util.SparseIntArray;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.DownloadUtil;

import org.reactivestreams.Subscriber;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LiXiaoSong on 2016/10/19.
 *
 * @Describe 基于Retrofit和RxJava的网络框架
 * 使用方式 RxNet.INSTANCES.getxxx().addAttachment(XXX)...exec(callback,context);
 */

public enum RxNet {
    INSTANCES;
    //private final String BASE_URL = "http://10.1.10.160:8180/appapi/";//开发版地址
    //private final String BASE_URL = "http://10.2.1.92:8088/appapi/";//开发版地址
//    private final String BASE_URL = "http://60.247.59.81:8088/appapi/";//外网地址
    //public static final String BASE_URL = "http://10.2.1.194:8088/appapi/";//测试URL
//    public static final String BASE_URL = "https://appapi.5i5j.com/appapi/";//安全域名
//    public static final String BASE_URL = "http://10.2.1.96:8088/appapi/";//96环境

    //new
    private static final String BASE_URL ="http://192.168.1.73";


    private Retrofit retrofit;
    private Retrofit downloadRetrofit;
    public static final int LOADING = 1;
    public static final int FINISH = 2;
    public static final int FAIL = 3;

    RxNet() {
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(chain -> {
                    /**
                     * 添加必要头部
                     */
//                    String token = SharePreferenceUtil.USER.getString(SharePreferenceUtil.USER_KEY.token, "");
//                    String token = SPUtils.getInstance().getString(Constant.TOKEN);

                    Request request = chain.request().newBuilder()
                            .addHeader("PHPSESSID", Constant.TOKEN)
                            .build();
                    try {
                        return chain.proceed(request);
                    } catch (SocketTimeoutException exception) {
                        throw new NetProxy.ApiException("网络数据获取超时", NetProxy.ApiException.NET_TIME_OUT_ERROR);
                    }
                }).
                addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).
                connectTimeout(50, TimeUnit.SECONDS).
                build();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public RequestInterface generateRequest() {
        return retrofit.create(RequestInterface.class);
    }

    /**
     * 用于特殊处理下载内容的request
     *
     * @return
     */
    public RequestInterface generateDownloadRequest() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://msoftdl.360.cn");
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        return retrofitBuilder
                .client(builder.build())
                .build().create(RequestInterface.class);
    }

    private <T extends Serializable> NetProxy<T> generateProxy(Flowable<HttpResult<T>> observable) {
        return new NetProxy<>(observable);//本次请求的代理
    }

    private <T extends Serializable> NetProxy<T> generateProxy(Flowable<HttpResult<T>> observable, int type, int queueCount) {
        return new NetProxy<>(observable, queueCount, type);//本次请求的代理
    }

    public DownloadProxy generateDownloadProxy(Flowable<ResponseBody> observable) {
        return new DownloadProxy(observable);
    }

    ////////////////////////////////////////////////网络具体接口start/////////////////////////////////////////////////////
    private boolean isQueue = false;
    private SparseIntArray countMap;

    public void beginQueue() {
        if (countMap == null) {
            countMap = new SparseIntArray();
        }
        for (int i = 0; i < countMap.size(); i++) {
            countMap.put(i, 0);
        }
        isQueue = true;
    }

    public void finishQueue() {
        if (countMap == null) {
            countMap = new SparseIntArray();
        }
        for (int i = 0; i < countMap.size(); i++) {
            countMap.put(i, 0);
        }
        isQueue = false;
    }

    public <T extends Serializable> NetProxy<T> generateInterface(Flowable<HttpResult<T>> observable) {
        return generateProxy(observable);
    }

    public <T extends Serializable> NetProxy<T> generateQueueInterface(int queueType, Flowable<HttpResult<T>> observable) {
        if (isQueue) {
            int currentCount = countMap.get(queueType, 0);
            currentCount++;
            countMap.put(queueType, currentCount);
            return generateProxy(observable, queueType, currentCount);
        }
        return generateProxy(observable);
    }


    public DownloadProxy downloadApk(DownloadProgressHandler handler) {
        ProgressHelper.setProgressHandler(handler);
        RequestInterface requestInterface = generateDownloadRequest();
        return generateDownloadProxy(requestInterface.downloadFile());
    }
    ////////////////////////////////////////////////end//////////////////////////////////////////////////////

    /**
     * 下载文件代理类
     */
    public static class DownloadProxy {

        private Flowable<ResponseBody> downloadObservable;//下载大文件用
        private long percentage = 0;

        public DownloadProxy(Flowable<ResponseBody> observable) {
            this.downloadObservable = observable;
        }

        public void download(final String fileName, final WriteFileCallBack callback) {
            downloadObservable.
                    map(new Function<ResponseBody, InputStream>() {
                        @Override
                        public InputStream apply(@NonNull ResponseBody responseBody) {
                            if (responseBody != null) {
                                return responseBody.byteStream();
                            } else {
                                throw new NetProxy.ApiException("下载内容异常", NetProxy.ApiException.NET_ERROR);
                            }
                        }
                    }).subscribeOn(Schedulers.io()).
                    observeOn(Schedulers.computation()).
                    map(new Function<InputStream, Object>() {
                        @Override
                        public Object apply(@NonNull InputStream inputStream) {
                            try {
                                return DownloadUtil.writeFile2Disk(inputStream, DownloadUtil.createFile(x.app(), fileName));
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new NetProxy.ApiException("write downloadfile error", NetProxy.ApiException.OTHER_ERROR);
                            }
                        }
                    }).
                    observeOn(Schedulers.computation()).
                    subscribe(s -> callback.writeFinish((String) s), throwable -> {
                        callback.writeError(throwable.getMessage());
                    });
        }

        /**
         * 下载文件完成，写文件成功
         */
        public interface WriteFileCallBack {
            void writeFinish(String filePath);

            void writeError(String msg);
        }
    }

    /**
     * 网络代理类
     */
    public static class NetProxy<T extends Serializable> {
        private Flowable<HttpResult<T>> observable;
        private int queueCount = 0;
        private int currentType = 0;

        public NetProxy(Flowable<HttpResult<T>> observable) {
            this.observable = observable;
            queueCount = 0;
            currentType = 0;
        }

        public NetProxy(Flowable<HttpResult<T>> observable, int queueCount, int currentType) {
            this.observable = observable;
            this.queueCount = queueCount;
            this.currentType = currentType;
        }

        /**
         * 增加中间处理逻辑
         *
         * @return
         */
        public NetProxy<T> addAttachment(final ObserverCallBack callBack) {
            if (callBack != null)
                callBack.getObserver(observable);
            return this;
        }

        @SuppressWarnings("UnChecked")
        public void exec(NetSubscriber.NetCallback<T> callBack, Context context) {
            toScheduler(observable, new NetSubscriber<>(callBack, context));

        }//执行本次网络请求，并且得到结果

        /**
         * 执行请求，并设定本次请求标志位，在错误等情况时可用
         *
         * @param callBack
         * @param context
         * @param type
         */
        public DisposableSubscriber<T> exec(NetSubscriber.NetCallback<T> callBack, Context context, int type) {
            return toScheduler(observable, new NetSubscriber<>(callBack, context, type));
        }

        private <T extends Serializable> DisposableSubscriber<T> toScheduler(Flowable<HttpResult<T>> observable, final Subscriber<T> subscriber) {
            observable.subscribeOn(Schedulers.io()).
                    unsubscribeOn(Schedulers.io()).
                    observeOn(Schedulers.computation()).filter(tHttpResult -> {
                if (RxNet.INSTANCES.isQueue) {
                    //说明当前是最新的队列
//不是最新队列，舍弃本次发射
                    return queueCount >= RxNet.INSTANCES.countMap.get(currentType, queueCount);
                } else {
                    return true;
                }
            }).map(o -> {
                if (o == null) {//网络地址错误，服务器错误等等
                    throw new ApiException("", ApiException.E_400_TO_500);
                } else {
                    switch (o.getStatus()) {
                        case HttpResult.STATUS_OK:
                            return o.getData();
                        case HttpResult.FORBIDDEN:
                            throw new ApiException(o.getMsg(), HttpResult.FORBIDDEN);
                        case HttpResult.UNAUTHORIZED:
                            throw new ApiException(o.getMsg(), HttpResult.UNAUTHORIZED);
                        case HttpResult.NO_CONTENT:
                            throw new ApiException(o.getMsg(), HttpResult.NO_CONTENT);
                        case HttpResult.SERVICE_ERROR:
                            throw new ApiException(o.getMsg(), HttpResult.SERVICE_ERROR);
                        default:
                            throw new ApiException(o.getMsg(), ApiException.SERVICE_ERROR);
                    }
                }

            }).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            return (DisposableSubscriber<T>) subscriber;
        }

        static class ApiException extends RuntimeException {
            public static final int NO_WIFI = -2049;
            public static final int E_400_TO_500 = -3049;
            public static final int SERVICE_ERROR = -3000;
            /**
             * 用户没有登陆
             */
            public static final int USER_NO_LOGIN = -2001;
            public static final int NET_ERROR = -2048;
            public static final int NET_TIME_OUT_ERROR = -2059;
            /**
             * 其他异常
             */
            public static final int OTHER_ERROR = -1111;
            private int code;//错误代码

            public ApiException(String msg, int code) {
                super(msg);
                this.code = code;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

        }

        public interface ObserverCallBack<T> {
            void getObserver(Flowable<T> observable);
        }
    }
}


package com.zgdj.djframe.net.retrofit;


import java.io.Serializable;

/**
 * Created by LiXiaoSong on 2016/10/20.
 *
 * @Describe
 */

public class HttpResult<T extends Serializable> implements Serializable {
//    {
//        "status":"200",
//            "msg":"OK",
//            "data":{
//
//    }
//    }
    /**
     * 返回成功
     */
    public static final int STATUS_OK = 200;
    public static final int NO_CONTENT = 204;
    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;
    /**
     * 被禁止
     */
    public static final int FORBIDDEN = 403;
    /**
     * 服务器异常
     */
    public static final int SERVICE_ERROR = 500;
    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

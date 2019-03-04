package com.zgdj.djframe.activity.message.interf;

import com.zgdj.djframe.constant.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * MVP 数据处理部分
 */
public class MvpModel {

    //年计划
    public static void getRemindOfYearDetails(String token, String id, String uint_id,
                                              String warn_type, MvpCallback callback) {
        getPlanTask(Constant.URL_MESSAGE_YEAR_REMIND_DETAILS, token, id, uint_id, warn_type, callback);
    }

    //月计划
    public static void getRemindOfMonthDetails(String token, String id, String uint_id,
                                               String warn_type, MvpCallback callback) {
        getPlanTask(Constant.URL_MESSAGE_MONTH_REMIND_DETAILS, token, id, uint_id, warn_type, callback);
    }

    //总计划
    public static void getRemindOfTotalDetails(String token, String id, String uint_id,
                                               String warn_type, MvpCallback callback) {
        getPlanTask(Constant.URL_MESSAGE_TOTAL_REMIND_DETAILS, token, id, uint_id, warn_type, callback);
    }

    /**
     * 提醒类 网络请求
     *
     * @param token     token值
     * @param id        消息表的主键
     * @param uint_id   关联表的主键
     * @param warn_type 1 表示月计划提醒 2表示月填报提醒
     */
    private static void getPlanTask(String url, String token, String id, String uint_id,
                                    String warn_type, MvpCallback callback) {
        RequestParams params = new RequestParams(url);
        params.addHeader("token", token);
        params.addBodyParameter("id", id);
        params.addBodyParameter("uint_id", uint_id);
        params.addBodyParameter("warn_type", warn_type);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }
        });

    }


    /**
     * 处理计划提醒 网络请求
     *
     * @param token token值
     * @param id    消息表的主键
     */
    public static void checkPlanTask(String token, String id, MvpCallback callback) {
        RequestParams params = new RequestParams(Constant.URL_MESSAGE_CHECK_REMIND_PLAN);
        params.addHeader("token", token);
        params.addBodyParameter("id", id);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                callback.onFinished();
            }
        });

    }

}

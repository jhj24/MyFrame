package com.zgdj.djframe.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zgdj.djframe.activity.other.PhotoViewActivity;
import com.zgdj.djframe.bean.ImageBean;
import com.zgdj.djframe.constant.Constant;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *     author:
 *                                      ___           ___           ___         ___
 *         _____                       /  /\         /__/\         /__/|       /  /\
 *        /  /::\                     /  /::\        \  \:\       |  |:|      /  /:/
 *       /  /:/\:\    ___     ___    /  /:/\:\        \  \:\      |  |:|     /__/::\
 *      /  /:/~/::\  /__/\   /  /\  /  /:/~/::\   _____\__\:\   __|  |:|     \__\/\:\
 *     /__/:/ /:/\:| \  \:\ /  /:/ /__/:/ /:/\:\ /__/::::::::\ /__/\_|:|____    \  \:\
 *     \  \:\/:/~/:/  \  \:\  /:/  \  \:\/:/__\/ \  \:\~~\~~\/ \  \:\/:::::/     \__\:\
 *      \  \::/ /:/    \  \:\/:/    \  \::/       \  \:\  ~~~   \  \::/~~~~      /  /:/
 *       \  \:\/:/      \  \::/      \  \:\        \  \:\        \  \:\         /__/:/
 *        \  \::/        \__\/        \  \:\        \  \:\        \  \:\        \__\/
 *         \__\/                       \__\/         \__\/         \__\/
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : utils about initialization
 * </pre>
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private static LinkedList<Activity> sActivityList = new LinkedList<>();

    private static ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 格式化文件URL -- \\ 转成 \
    public static String tranFormURL(String url) {
        if (url.contains("\\")) {
            url = url.replace("\\", "/");
        }
        return addBaseHttpURL(url);
    }

    // 添加url - IP
    private static String addBaseHttpURL(String url) {
        if (url.contains(Constant.BASE_URL)) {
            return url;
        } else {
            return Constant.BASE_URL + url;
        }
    }

    //添加url -- http头部
    public static String addHttpURL(String url) {
        if (url.contains("http://") || url.contains("https://")) {
            return url;
        } else if (url.startsWith("http:")) {
            url = url.replace("http:", "http://");
            return url;
        } else if (url.startsWith("https:")) {
            url = url.replace("https:", "https://");
            return url;
        } else {
            return "http://" + url;
        }
    }


    /**
     * 单图片查看
     *
     * @param context
     * @param url
     */
    public static void jumpPictureForView(Context context, String url) {
        List<ImageBean> imgList = new ArrayList<>();
        imgList.add(new ImageBean(Utils.tranFormURL(url), 2));
        Bundle bundle = new Bundle();
        bundle.putSerializable(PhotoViewActivity.KEY_IMAGE_LIST, (Serializable) imgList);
        bundle.putInt(PhotoViewActivity.KEY_IMAGE_POSITION, 0);
        context.startActivity(new Intent(context, PhotoViewActivity.class).putExtras(bundle));
    }

    /**
     * 单图片查看
     *
     * @param context
     * @param path
     */
    public static void jumpPictureForView(Context context, File path) {
        List<ImageBean> imgList = new ArrayList<>();
        imgList.add(new ImageBean(path.toString(), 2));
        Bundle bundle = new Bundle();
        bundle.putSerializable(PhotoViewActivity.KEY_IMAGE_LIST, (Serializable) imgList);
        bundle.putInt(PhotoViewActivity.KEY_IMAGE_POSITION, 0);
        context.startActivity(new Intent(context, PhotoViewActivity.class).putExtras(bundle));
    }


    /**
     * 修改部分字体颜色
     *
     * @param info     内容
     * @param textView text对象
     * @param color    修改的颜色
     * @param start    开始位置
     * @param end      截止位置
     */
    public static void setTextColor(Context context, String info, TextView textView, @ColorRes int color, int start, int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(info);
        ForegroundColorSpan span = new ForegroundColorSpan(ContextCompat.getColor(context, color));
        builder.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 时间戳转换成具体时间形式
     */
    public static String getTime2Date(long timeMillis) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(timeMillis * 1000);
        return time;
    }

    /**
     * 代码设置shape 背景颜色
     *
     * @param context
     * @param view
     * @param color
     */
    public static void setShapeBgColor(Context context, View view, @ColorRes int color) {
        GradientDrawable myGrad = (GradientDrawable) view.getBackground();
        myGrad.setColor(ContextCompat.getColor(context, color));
    }


    /**
     * 获取设备宽度和高度
     *
     * @param paramContext
     * @return
     */
    public static int[] getDeviceWH(Context paramContext) {
        int[] arrayOfInt = new int[2];
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        arrayOfInt[0] = i;
        arrayOfInt[1] = j;
        return arrayOfInt;
    }


    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(@NonNull final Context context) {
        Utils.sApplication = (Application) context.getApplicationContext();
        Utils.sApplication.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param application application
     */
    public static void init(@NonNull final Application application) {
        Utils.sApplication = application;
        Utils.sApplication.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    static void setTopActivity(final Activity activity) {
        if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
        if (sActivityList.contains(activity)) {
            if (!sActivityList.getLast().equals(activity)) {
                sActivityList.remove(activity);
                sActivityList.addLast(activity);
            }
        } else {
            sActivityList.addLast(activity);
        }
    }

    public static LinkedList<Activity> getActivityList() {
        return sActivityList;
    }
}

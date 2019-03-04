package com.zgdj.djframe.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgdj.djframe.view.CustomerDialog;


/**
 * 创建人：jinfangxing
 * 创建时间：2017/5/9
 * 功能描述：对话框工具类
 */

public class DialogUtils {

    private DialogUtils() {

    }

    /***
     * 显示系统对话框
     * @param context
     * @param title
     * @param content
     * @param positiveButtonTxt
     * @param nagativeButtonTxt
     * @param onPositiveButtonClickListener
     * @param onNagativeButtonClickListener
     */
    public static AlertDialog showDialog(Context context, String title, String
            content, String
                                                 positiveButtonTxt,
                                         String nagativeButtonTxt, DialogInterface.OnClickListener
                                                 onPositiveButtonClickListener,
                                         DialogInterface.OnClickListener
                                                 onNagativeButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        //监听下方button点击事件
        builder.setPositiveButton(positiveButtonTxt, onPositiveButtonClickListener);
        builder.setNegativeButton(nagativeButtonTxt, onNagativeButtonClickListener);
        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    /***
     * 自定义对话框
     */
    private static class CustomDialogInterface implements CustomerDialog.CustomerViewInterface {

        private Context mContext;

        private int mLayoutResId;//布局id

        private int mTitleResId;//标题控件id

        private int mContentResId;//内容控件id

        private int positiveButtonResId;//右侧button id

        private int nagativeButtonResId;//左侧button id

        private String mTitle;

        private String mContent;//内容

        private String mPositiveButtonTxt;//右侧按钮文本

        private String mNagativeButtonTxt;//左侧按钮文本

        private View.OnClickListener mOnPositiveButtonClickListener;//右侧按钮点击事件

        private View.OnClickListener mOnNagativeButtonClickListener;//左侧按钮点击事件


        public CustomDialogInterface() {

        }

        /***
         *
         * @param layoutResId
         * @param title
         * @param content
         * @param positiveButtonTxt
         * @param nagativeButtonTxt
         * @param onPositiveButtonClickListener
         * @param onNagativeButtonClickListener
         */
        public CustomDialogInterface(Context context, int layoutResId, int titleResId, int
                contentResId,
                                     int nagativeButtonResId, int positiveButtonResId, String
                                             title, String content,
                                     String positiveButtonTxt, String nagativeButtonTxt,
                                     View.OnClickListener onPositiveButtonClickListener,
                                     View.OnClickListener onNagativeButtonClickListener) {
            this.mContext = context;
            this.mLayoutResId = layoutResId;
            this.mTitleResId = titleResId;
            this.mContentResId = contentResId;
            this.nagativeButtonResId = nagativeButtonResId;
            this.positiveButtonResId = positiveButtonResId;
            this.mTitle = title;
            this.mContent = content;
            this.mPositiveButtonTxt = positiveButtonTxt;
            this.mNagativeButtonTxt = nagativeButtonTxt;
            this.mOnNagativeButtonClickListener = onNagativeButtonClickListener;
            this.mOnPositiveButtonClickListener = onPositiveButtonClickListener;
        }

        @Override
        public void getCustomerView(Window window, AlertDialog dlg) {
            try {
                TextView title = window.findViewById(mTitleResId);
                TextView content = window.findViewById(mContentResId);
                title.setText(mTitle);//设置标题
                title.setTextSize(18);
                title.setVisibility(View.VISIBLE);
                content.setText(mContent);
                LinearLayout.LayoutParams paramsTitle = (LinearLayout.LayoutParams) title
                        .getLayoutParams();
                paramsTitle.leftMargin = paramsTitle.rightMargin = Utils.dp2px(mContext, 50);
                paramsTitle.topMargin = 40;
                paramsTitle.bottomMargin = 0;
                title.setLayoutParams(paramsTitle);
                LinearLayout.LayoutParams paramsContent = (LinearLayout.LayoutParams) title
                        .getLayoutParams();
                paramsContent.topMargin = 36;
                content.setLayoutParams(paramsContent);
                TextView nagaTiveView = window.findViewById(nagativeButtonResId);
                nagaTiveView.setText(mNagativeButtonTxt);
                TextView positionView = window.findViewById(positiveButtonResId);
                positionView.setText(mPositiveButtonTxt);
                nagaTiveView.setOnClickListener(new OnNagativeButtonClickListener(window, dlg,
                        mOnNagativeButtonClickListener));
                positionView.setOnClickListener(new OnPositiveButtonClickListener(window, dlg,
                        mOnPositiveButtonClickListener));
            } catch (Exception e) {
                throw new RuntimeException("对话框控件类型转换错误");
            }
        }
    }

    /***
     * 对话框右侧按钮监听
     */
    public static class OnPositiveButtonClickListener implements View.OnClickListener {

        private Window mWindow;

        private AlertDialog mAlertDialog;

        private View.OnClickListener mOnClickListener;

        public OnPositiveButtonClickListener() {

        }

        public OnPositiveButtonClickListener(Window window, AlertDialog alertDialog
                , View.OnClickListener onClickListener) {
            this.mWindow = window;
            this.mAlertDialog = alertDialog;
            this.mOnClickListener = onClickListener;
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                if (mAlertDialog != null) {
                    mAlertDialog.dismiss();
                }
                mOnClickListener.onClick(v);
            }
        }
    }

    /***
     * 对话框左侧按钮监听
     */
    public static class OnNagativeButtonClickListener implements View.OnClickListener {

        private Window mWindow;

        private AlertDialog mAlertDialog;

        private View.OnClickListener mOnClickListener;

        public OnNagativeButtonClickListener() {

        }

        public OnNagativeButtonClickListener(Window window, AlertDialog alertDialog
                , View.OnClickListener onClickListener) {
            this.mWindow = window;
            this.mAlertDialog = alertDialog;
            this.mOnClickListener = onClickListener;
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                if (mAlertDialog != null) {
                    mAlertDialog.dismiss();
                }
                mOnClickListener.onClick(v);
            }
        }
    }

    /***
     * 显示自定义对话框
     * @param context
     * @param layoutResId  资源文件id
     */
    public static CustomerDialog showCustomDialog(Context context, int layoutResId, int
            titleResId, int contentResId,
                                                  int nagativeButtonResId, int
                                                          positiveButtonResId, String
                                                          title, String content,
                                                  String positiveButtonTxt, String
                                                          nagativeButtonTxt,
                                                  View.OnClickListener
                                                          onPositiveButtonClickListener,
                                                  View.OnClickListener
                                                          onNagativeButtonClickListener) {
        //自定义dialog
        CustomerDialog dialog = new CustomerDialog((Activity) context, layoutResId);
        dialog.setDlgIfClick(true);
        //点击
        dialog.setOnCustomerViewCreated(new CustomDialogInterface(context, layoutResId,
                titleResId, contentResId, nagativeButtonResId, positiveButtonResId,
                title, content, positiveButtonTxt, nagativeButtonTxt, onPositiveButtonClickListener,
                onNagativeButtonClickListener));
        dialog.showDlg();
        return dialog;
    }
}

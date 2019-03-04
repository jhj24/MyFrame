package com.zgdj.djframe.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zgdj.djframe.R;
import com.zgdj.djframe.utils.Utils;


/**
 * 自定义Dialog 可以自定义Dialog的布局，以及定义布局上的监听事件，不影响界面上键盘的弹出
 *
 * @author lixiaosong
 */
public class CustomerDialog {
    private Activity context;
    private int res;
    private CustomerViewInterface listener;
    private AlertDialog dlg;
    private boolean isCancelable;
    private int styleId = R.style.alert_dialog;
    private boolean inEditTextModel = false;

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    /**
     * dialog中，需求在编辑EditText弹出软键盘时将该标记设置为true
     * 需在showDlg前调用
     *
     * @param inEditTextModel
     */
    public void setEditTextModel(boolean inEditTextModel) {
        this.inEditTextModel = inEditTextModel;
    }

    /**
     * @param context 与dialog关联的上下文
     * @param res     自定义dialog的资源id
     */
    public CustomerDialog(Activity context, int res) {
        this.context = context;
        this.res = res;
    }

    /**
     * 调用这个构造方法之后必须调用init方法
     */
   /* public CustomerDialog() {

    }*/

    public void init(Activity context, int res) {
        this.context = context;
        this.res = res;
    }

    public boolean isShowing() {
        return dlg != null && dlg.isShowing();
    }

    /**
     * 获取当前的dlg
     *
     * @return
     */
    public Dialog getDlg() {
        return dlg;
    }

    /**
     * 在调用这个方法之前最好先调用setOnCustomerViewCreated来控制dialog自定义界面上的内容
     */
    public void showDlg() {
        dlg = new AlertDialog.Builder(context, styleId).create();
        dlg.setCanceledOnTouchOutside(true);
        dlg.setCancelable(isCancelable);
        dlg.show();
        Window window = dlg.getWindow();
        // 下面的清除flag主要是为了在dialog中有editText时弹出软件盘所用。
        if (inEditTextModel) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        window.setContentView(res);
        if (listener != null) {
            listener.getCustomerView(window, dlg);
        }
    }

    public void showDlgFullScreen() {
        int[] wh = Utils.getDeviceWH(context);
        int height = wh[1] - context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        dlg = new AlertDialog.Builder(context, styleId).create();
        dlg.setCanceledOnTouchOutside(true);
        dlg.setCancelable(isCancelable);
        dlg.show();
        Window window = dlg.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);
        // 下面的清除flag主要是为了在dialog中有editText时弹出软件盘所用。
        if (inEditTextModel) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(res);
        if (listener != null) {
            listener.getCustomerView(window, dlg);
        }
    }

    /**
     * 在调用这个方法之前最好先调用setOnCustomerViewCreated来控制dialog自定义界面上的内容(带显示位置）
     */
    public void showDlgWithGravity(int gravity) {
        dlg = new AlertDialog.Builder(context, styleId).create();
        dlg.setCanceledOnTouchOutside(true);
        dlg.setCancelable(isCancelable);
        dlg.show();
        Window window = dlg.getWindow();
        // 下面的清除flag主要是为了在dialog中有editText时弹出软件盘所用。
        if (inEditTextModel) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setContentView(res);
        window.setGravity(gravity);
        if (listener != null) {
            listener.getCustomerView(window, dlg);
        }
    }

    /**
     * 白海超：用在底部联系经纪人全屏显示
     * 在调用这个方法之前最好先调用setOnCustomerViewCreated来控制dialog自定义界面上的内容(带显示位置）
     */
    public void showDlgWithMatchParent() {
        dlg = new AlertDialog.Builder(context, styleId).create();
        dlg.setCanceledOnTouchOutside(true);
        dlg.setCancelable(isCancelable);
        dlg.show();
        Window window = dlg.getWindow();
        // 下面的清除flag主要是为了在dialog中有editText时弹出软件盘所用。
        if (inEditTextModel) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setContentView(res);
        window.setGravity(Gravity.BOTTOM);
        if (listener != null) {
            listener.getCustomerView(window, dlg);
        }
    }

    public void setDlgIfClick(boolean ifClick) {
        isCancelable = ifClick;
    }

    public void dismissDlg() {
        if (dlg != null) {
            dlg.dismiss();
        }
    }

    public interface CustomerViewInterface {
        void getCustomerView(final Window window, final AlertDialog dlg);
    }

    public void setOnCustomerViewCreated(CustomerViewInterface listener) {
        this.listener = listener;
    }
}

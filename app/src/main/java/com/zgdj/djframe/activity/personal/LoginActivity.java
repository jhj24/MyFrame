package com.zgdj.djframe.activity.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zgdj.djframe.MainActivity;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseActivity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.jpush.ExampleUtil;
import com.zgdj.djframe.jpush.TagAliasOperatorHelper;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.SPUtils;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.jpush.android.api.JPushMessage;


/**
 * 登录页
 */
public class LoginActivity extends BaseActivity {
    private EditText name;//账号
    private EditText possWord;//密码
    private String userName;
    private String userPWD;
    private LoadingDialog loadingDialog;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        view.findViewById(R.id.btn_login_ok).setOnClickListener(this);
        name = view.findViewById(R.id.edit_login_user_name);
        possWord = view.findViewById(R.id.edit_login_user_psd);

    }

    @Override
    public void doBusiness() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setSpinnerType(0);
        loadingDialog.setMessage("正在登录...");
    }

    @Override
    public void onWidgetClick(View view) {

        switch (view.getId()) {
            case R.id.btn_login_ok:
//                getLoginTask();
//                setAlias(name.getText().toString().trim());
//                setPushAlias(name.getText().toString().trim());
                checkLogin();
                break;
        }

    }

    //账号密码 -- 判空之类
    private void checkLogin() {
        userName = name.getText().toString().trim();
        userPWD = possWord.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showShort("用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(userPWD)) {
            ToastUtils.showShort("密码不能为空！");
            return;
        }
        // 密码必须 6 - 16 位
        if (userPWD.length() < 6 || userPWD.length() > 16) {
            ToastUtils.showShort("密码必须 6 - 16位 ！");
            return;
        }
        //执行登录请求
        getLoginTask();
        //弹出loading
        loadingDialog.show();
    }


    //登录
    // {"code":"1","msg":"您已经登录","id":1,"token":"28c8edde3d61a0411511d3b1866f0636"}
    private void getLoginTask() {
        RequestParams params = new RequestParams(Constant.URL_LOGIN);
        params.addParameter("name", userName);
        params.addParameter("password", userPWD);
        params.setConnectTimeout(20 * 1000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.json("登录: ", result);
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject object = new JSONObject(result);
                        String msg = object.getString("msg");
                        if (object.has("code")) {//
                            String code = object.getString("code");
                            if ("1".equals(code)) { //code == 1  成功
                                ToastUtils.showShort("登录成功！");
                                if (object.has("cookiekey")) {
                                    String cookieValues = object.getString("cookiekey");
                                    SPUtils.getInstance().put(Constant.COOKIE_KEY, "PHPSESSID=" + cookieValues);
                                }
                                if (object.has("id")) {
                                    SPUtils.getInstance().put(Constant.KEY_USER_ID, object.getString("id"));
                                }
                                if (object.has("token")) {
                                    SPUtils.getInstance().put(Constant.KEY_TOKEN, object.getString("token"));
                                }
                                //保存用户名
                                SPUtils.getInstance().put(Constant.KEY_USER_NAME, userName);
                                //设置昵称
                                if (object.has("nickname"))
                                    SPUtils.getInstance().put(Constant.KEY_USER_NICK, object.getString("nickname"));
                                //设置极光别名
                                setAlias(userName, object.getInt("id"));
//                                TagAliasOperatorHelper.getInstance().
                            } else {
                                ToastUtils.showShort(msg);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    ToastUtils.showShort("登录失败！");
                }

            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Logs.debug("onError: " + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                loadingDialog.dismiss();
            }
        });
    }


    private void setAlias(String alias, int userId) {
//        String alias = ""; //别名
        if (TextUtils.isEmpty(alias)) {
            //别名不能为空
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            //只能是数字,英文字母和中文
            return;
        }

        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        TagAliasOperatorHelper.sequence = userId;
        tagAliasBean.alias = alias;
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), TagAliasOperatorHelper.sequence, tagAliasBean);
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(this, new JPushMessage() {
            @Override
            public String getAlias() {
                loadingDialog.dismiss();
                jumpToInterface(MainActivity.class);
                finish();
                return super.getAlias();
            }
        });
    }

}

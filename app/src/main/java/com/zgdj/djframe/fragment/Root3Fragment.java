package com.zgdj.djframe.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.personal.SettingActivity;
import com.zgdj.djframe.base.BaseFragment;
import com.zgdj.djframe.bean.PersonalInfoBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.FragmentUtils;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.RegexUtils;
import com.zgdj.djframe.utils.SPUtils;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Objects;


/**
 * description: 个人中心
 * author: Created by ShuaiQi_Zhang on 2018/4/19
 * version: 1.0
 */
public class Root3Fragment extends BaseFragment implements FragmentUtils.OnBackClickListener {

    private ImageView img_head;//头像
    private TextView nick;//昵称
    private TextView tvPos;//岗位
    private TextView sex;//性别
    private TextView wechat;//微信号
    private TextView mobile;//手机号
    private TextView tele;//办公电话
    private TextView mail;//邮箱.
    private ImageButton setting;//设置页面

    private boolean isSuccess = false;//获取数据成功标识
    public static Root3Fragment newInstance() {
        Bundle args = new Bundle();
        Root3Fragment fragment = new Root3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override

    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_root_3;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        (img_head = view.findViewById(R.id.img_head)).setOnClickListener(this);
        view.findViewById(R.id.personal_btn_setting).setOnClickListener(this);
        nick = view.findViewById(R.id.tv_personal_name);
        tvPos = view.findViewById(R.id.personal_tv_position);
        sex = view.findViewById(R.id.personal_tv_sex);
        wechat = view.findViewById(R.id.personal_tv_wechat);
        mobile = view.findViewById(R.id.personal_tv_mobile);
        tele = view.findViewById(R.id.personal_tv_tele);
        mail = view.findViewById(R.id.personal_tv_mail);
    }


    @Override
    public void doBusiness() {
        getPersonalInfoTask();
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.personal_btn_setting: //设置
                jumpToInterface(SettingActivity.class);
                break;
            case R.id.img_head: //头像
//                jumpToInterface(LoginActivity.class);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isSuccess) {
            getPersonalInfoTask();
        }
    }

    @Override
    public boolean onBackClick() {
        return false;
    }

    //获取个人信息
    private void getPersonalInfoTask() {
        String userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID);
        RequestParams params = new RequestParams(Constant.URL_PERSONAL_INFO);
        params.addHeader("token", token);
        params.addBodyParameter("id", userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logs.debug("个人中心：" + result);
                if (!RegexUtils.isNull(result)) {
                    Gson gson = new Gson();
                    PersonalInfoBean bean = gson.fromJson(result, PersonalInfoBean.class);
                    if (bean.getCode() == 1) { //成功
                        isSuccess = true;
                        PersonalInfoBean.AdminInfoBean infoBean = bean.getAdmin_info();
                        if (!RegexUtils.isNull(infoBean.getNickname())) //昵称
                            nick.setText(infoBean.getNickname());
                        if (!RegexUtils.isNull(infoBean.getPosition())) //岗位
                            tvPos.setText(infoBean.getPosition());
                        if (!RegexUtils.isNull(infoBean.getGender())) //性别
                            sex.setText(infoBean.getGender());
                        if (!RegexUtils.isNull(infoBean.getWechat())) //微信号
                            wechat.setText(infoBean.getWechat());
                        if (!RegexUtils.isNull(infoBean.getMobile())) //手机号
                            mobile.setText(infoBean.getMobile());
                        if (!RegexUtils.isNull(infoBean.getTele())) //办公电话
                            tele.setText(infoBean.getTele());
                        if (!RegexUtils.isNull(infoBean.getMail())) //邮箱
                            mail.setText(infoBean.getMail());
                        if (!RegexUtils.isNull(infoBean.getThumb())) { //头像
                            String headUrl = Utils.tranFormURL(infoBean.getThumb());
                            // 加载头像
                            RequestOptions options = RequestOptions
                                    .bitmapTransform(new CircleCrop())//圆形
                                    .placeholder(R.drawable.icon_head_default)
                                    .error(R.drawable.icon_head_default);

                            Glide.with(Objects.requireNonNull(getActivity()))
                                    .load(headUrl)
                                    .apply(options)
                                    .into(img_head);
                           /* GlideApp.with(Objects.requireNonNull(getActivity()))
//                                    .asBitmap()
                                    .load(headUrl)
                                    .placeholder(R.drawable.icon_head_default)
                                    .error(R.drawable.icon_head_default)
                                    .centerCrop()
                                    .into(img_head);*/
                        }

                    } else if (bean.getCode() == -2) {//token失效
                        ToastUtils.showShort(Constant.TOKEN_LOST);
                    } else {//失败
                        Logs.debug("个人信息失败 - ");
                    }
                } else {
                    Logs.debug("个人信息失败");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}

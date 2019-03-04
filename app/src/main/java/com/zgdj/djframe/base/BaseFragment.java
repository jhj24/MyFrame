package com.zgdj.djframe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.SPUtils;

/**
 * description: Fragment基础类
 * author: Created by ShuaiQi_Zhang on 2018/4/18
 * version:1.0
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    private static final String TAG = "BaseFragment";
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected View contentView;
    protected BaseActivity mActivity;
    private long lastClick = 0;
    protected String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = SPUtils.getInstance().getString(Constant.KEY_TOKEN);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(bindLayout(), null);
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        initView(savedInstanceState, contentView);
        doBusiness();
    }

    @Override
    public void onDestroyView() {
        if (contentView != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) onWidgetClick(view);
    }


    /**
     * 跳转到某个界面（无参数）
     *
     * @param clazz 需要跳转到的界面（activity）
     */
    public void jumpToInterface(Class clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 跳转到某个界面（传数据）
     *
     * @param clazz  需要跳转到的界面（activity）
     * @param bundle 传递的数据
     */
    public void jumpToInterface(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

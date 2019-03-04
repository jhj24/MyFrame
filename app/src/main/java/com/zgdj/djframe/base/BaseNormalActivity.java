package com.zgdj.djframe.base;

import android.support.annotation.DrawableRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zgdj.djframe.MyApplication;
import com.zgdj.djframe.R;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.BarUtils;
import com.zgdj.djframe.utils.SPUtils;

/**
 * description: 正经 Activity
 * author: Created by ShuaiQi_Zhang on 2018/4/26
 * version:1.0
 */
public abstract class BaseNormalActivity extends BaseActivity {

    protected CoordinatorLayout rootLayout;
    protected Toolbar mToolbar;
    protected AppBarLayout abl;
    protected FrameLayout flActivityContainer;
    protected ImageButton btnRight;//右侧按钮
    protected TextView textRight;//右侧按钮- text
    protected TextView toolbar_title;
    protected String token;


    @Override
    protected void setBaseView(int layoutId) {
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_normal, null);
        setContentView(contentView);

        toolbar_title = findViewById(R.id.toolbar_title);
        rootLayout = findViewById(R.id.root_layout);
        btnRight = findViewById(R.id.btnRight);
        textRight = findViewById(R.id.textRight);
        abl = findViewById(R.id.abl);
        mToolbar = findViewById(R.id.toolbar);
        flActivityContainer = findViewById(R.id.activity_container);
        flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutId, flActivityContainer, false));
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getToolBar().setDisplayHomeAsUpEnabled(true);
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(MyApplication.getInstance(), R.color.white), 0);
        BarUtils.setStatusBarLightMode(this, true);
        BarUtils.addMarginTopEqualStatusBarHeight(rootLayout);
        // 获取token
        token = SPUtils.getInstance().getString(Constant.KEY_TOKEN);
    }

    //设置右侧按钮 -- 图
    protected void setRightOnclick(@DrawableRes int img, CallBack back) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setImageResource(img);
        btnRight.setOnClickListener(v -> back.Onclick());
    }

    //设置右侧按钮 -- 字
    protected void setRightOnclick(String rightText, CallBack back) {
        textRight.setVisibility(View.VISIBLE);
        textRight.setText(rightText);
        textRight.setOnClickListener(v -> back.Onclick());
    }

    @Override
    public void setTitle(CharSequence title) {
        if (toolbar_title != null)
            toolbar_title.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected ActionBar getToolBar() {
        return getSupportActionBar();
    }

    protected interface CallBack {
        void Onclick();
    }
}

package com.zgdj.djframe.base;

import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.r0adkll.slidr.Slidr;
import com.zgdj.djframe.MyApplication;
import com.zgdj.djframe.R;
import com.zgdj.djframe.utils.BarUtils;

/**
 * description: 滑动退出Activity
 * author: Created by ShuaiQi_Zhang on 2018/4/18
 * version:1.0
 */
public abstract class BaseBackActivity extends BaseActivity {

    protected CoordinatorLayout rootLayout;
    protected Toolbar mToolbar;
    protected AppBarLayout abl;
    protected FrameLayout flActivityContainer;

    @Override
    protected void setBaseView(@LayoutRes int layoutId) {
        Slidr.attach(this);
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_back, null);
        setContentView(contentView);

        rootLayout = findViewById(R.id.root_layout);
        abl = findViewById(R.id.abl);
        mToolbar = findViewById(R.id.toolbar);
        flActivityContainer = findViewById(R.id.activity_container);
        flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutId, flActivityContainer, false));
        setSupportActionBar(mToolbar);
        getToolBar().setDisplayHomeAsUpEnabled(true);
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(MyApplication.getInstance(), R.color.colorPrimary), 0);
        BarUtils.addMarginTopEqualStatusBarHeight(rootLayout);
    }


    /**
     * 关闭toolBar 滚动效果
     */
    protected void closeScrollToolBar() {
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) mToolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        mToolbar.setLayoutParams(params);

       /* CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) abl.getLayoutParams();
        params.setBehavior(new AppBarLayout.Behavior());
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });*/
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



}

package com.zgdj.djframe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * description: 滑动时标题变色view
 * author: Created by ShuaiQi_Zhang on 2018/4/28
 * version: 1.0
 */
public class ScrollChangeScrollView extends ScrollView {

    public ScrollChangeScrollView(Context context) {
        super(context);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChangeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);

        }
    }

    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;

    }

    private OnScrollListener onScrollListener;

    /**
     * 滚动的回调接口
     *
     * @author xiaanming
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         *
         * @param scrollY
         */
        void onScroll(int scrollY);
    }
}

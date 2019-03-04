package com.zgdj.djframe.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/4/27
 * version:
 */
public class HomeWorkAdapter extends PagerAdapter {

    private List<View> mViewList;

    public HomeWorkAdapter(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {//必须实现
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {//必须实现
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        container.removeView(mViewList.get(position));
    }

}

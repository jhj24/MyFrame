package com.zgdj.djframe.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zgdj.djframe.R;
import com.zgdj.djframe.bean.ImageBean;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/8
 * version:
 */
public class ImageAdapter extends PagerAdapter {
    private List<ImageBean> imageUrls;
    private Activity activity;
    private int currIndex;


    public ImageAdapter(List<ImageBean> imageUrls, AppCompatActivity activity, int index) {
        this.imageUrls = imageUrls;
        this.activity = activity;
        this.currIndex = index;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageBean bean = imageUrls.get(position);
        View view = LayoutInflater.from(activity).inflate(R.layout.item_photo_view_image, null);
        PhotoView photoView = view.findViewById(R.id.item_photo_view);
        switch (bean.getType()) {
            case 1: //int
                Glide.with(activity).load(bean.getResId()).into(photoView);
                break;
            case 2: //url or file
                Glide.with(activity).load(bean.getUrl()).into(photoView);
                break;
            case 3: //uri
                Glide.with(activity).load(bean.getUri()).into(photoView);
                break;
        }
//        Glide.with(activity).load(url).into(photoView);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

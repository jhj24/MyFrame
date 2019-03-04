package com.zgdj.djframe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zgdj.djframe.R;

import java.util.List;

/**
 * description: 画廊Adapter
 * author: Created by ShuaiQi_Zhang on 2018/4/27
 * version:1.0
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyHolder> {
    private final String TAG = "GalleryAdapter";
    private Context mContext;
    private List<Integer> mDatas;
    private ViewGroup mParent;

    public GalleryAdapter(Context mContext, List<Integer> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.d(TAG, "onAttachedToRecyclerView");
        this.mParent = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder" + " width = " + parent.getWidth());
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.MyHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder" + "--> position = " + position);
        // 需要增加此代码修改每一页的宽高
        //GalleryAdapterHelper.newInstance().setItemLayoutParams(mParent, holder.itemView, position, getItemCount());
        holder.mView.setImageResource(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public final ImageView mView;

        public MyHolder(View itemView) {
            super(itemView);

            Log.d(TAG, "MyHolder");

            mView = itemView.findViewById(R.id.iv_photo);
        }
    }

    /**
     * 获取position位置的resId
     *
     * @param position
     * @return
     */
    public int getResId(int position) {
        return mDatas == null ? 0 : mDatas.get(position);
    }
}

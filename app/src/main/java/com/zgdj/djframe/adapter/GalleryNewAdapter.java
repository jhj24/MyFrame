package com.zgdj.djframe.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.ImageBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.DialogUtils;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.SPUtils;
import com.zgdj.djframe.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * description: 新一代画廊
 * author: Created by ShuaiQi_Zhang on 2018/5/22
 * version:
 */
public class GalleryNewAdapter extends SingleAdapter<ImageBean> {

    public GalleryNewAdapter(List<ImageBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, ImageBean data) {
        ImageView img = holder.getView(R.id.item_img);
        ImageView delete = holder.getView(R.id.item_img_delete);
        if (position == getItemCount() - 1) {//最后一个
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img.getLayoutParams();
            params.height = Utils.dp2px(mContext, 60);
            params.width = Utils.dp2px(mContext, 60);
            params.leftMargin = Utils.dp2px(mContext, 16);
            params.rightMargin = Utils.dp2px(mContext, 16);
            params.topMargin = Utils.dp2px(mContext, 10 + 6);
            params.bottomMargin = Utils.dp2px(mContext, 10);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            img.setLayoutParams(params);
            delete.setVisibility(View.GONE);
        } else {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) img.getLayoutParams();
            params.height = Utils.dp2px(mContext, 80);
            params.width = Utils.dp2px(mContext, 80);
            params.leftMargin = Utils.dp2px(mContext, 10);
            params.rightMargin = Utils.dp2px(mContext, 0);
            params.topMargin = Utils.dp2px(mContext, 10);
            params.bottomMargin = Utils.dp2px(mContext, 10);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            img.setLayoutParams(params);
            delete.setVisibility(View.VISIBLE);
        }
        switch (data.getType()) {
            case 1: //int
                Glide.with(mContext).load(data.getResId()).into(img);
                break;
            case 2: //url or file
                Glide.with(mContext).load(data.getUrl()).into(img);
                break;
            case 3: //uri
                Glide.with(mContext).load(data.getUri()).into(img);
                break;
        }


    }

    @Override
    protected void bindCustomViewHolder(BaseViewHolder holder, int position) {
        super.bindCustomViewHolder(holder, position);

        ImageView delete = holder.getView(R.id.item_img_delete);
        delete.setOnClickListener(v -> {//删除图片

            DialogUtils.showDialog(mContext, "删除图片", "是否删除图片？"
                    , "确定", "取消",
                    (dialog, which) -> {
                        deleteImageTask(mData.get(position).getImgId(), position);
                        notifyDataSetChanged();
                    }, null).show();
        });
    }

    //删除图片
    private void deleteImageTask(String id, int pos) {
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_DELETE_IMG);
        String token = SPUtils.getInstance().getString(Constant.KEY_TOKEN);
        params.addHeader("token", token);
        params.addBodyParameter("id", id);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("删除图片：" + result);
                mData.remove(pos);
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

    //获取数据
    public List<ImageBean> getDatas() {
        return mData;
    }
}

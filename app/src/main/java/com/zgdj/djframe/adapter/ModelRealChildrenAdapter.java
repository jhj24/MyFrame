package com.zgdj.djframe.adapter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.ModelRealBean;
import com.zgdj.djframe.utils.BitmapUtil;
import com.zgdj.djframe.utils.Utils;

import java.util.List;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/23
 * version:
 */
public class ModelRealChildrenAdapter extends SingleAdapter<ModelRealBean.ChildrenBean> {

    public ModelRealChildrenAdapter(List<ModelRealBean.ChildrenBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, ModelRealBean.ChildrenBean data) {

        TextView date = holder.getView(R.id.item_model_real_child_text_date);
        TextView area = holder.getView(R.id.item_model_real_child_text_area);
        ImageView imageView = holder.getView(R.id.item_model_real_child_img);


        date.setText("拍摄日期：" + data.getDate());
        if (!TextUtils.isEmpty(data.getArea()))
            area.setText("面积：" + data.getArea() + "平方千米");
        else
            area.setText("面积：未知");


        Bitmap bitmap = BitmapUtil.roundBitmapByShader(
                BitmapUtil.ResToBitmap(data.getResId(), mContext),
                200, 200, Utils.dp2px(mContext, 6));
        imageView.setImageBitmap(bitmap);

    }
}

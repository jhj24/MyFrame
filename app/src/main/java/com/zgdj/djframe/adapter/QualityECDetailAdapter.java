package com.zgdj.djframe.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.other.PDFViewerActivity;
import com.zgdj.djframe.activity.other.PhotoViewActivity;
import com.zgdj.djframe.base.rv.BaseViewHolder;
import com.zgdj.djframe.base.rv.adapter.SingleAdapter;
import com.zgdj.djframe.bean.ImageBean;
import com.zgdj.djframe.bean.QualityEvaluationDetailScan2Bean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.utils.Utils;
import com.zgdj.djframe.view.LoadingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * description:质量验评详情-- 附件资料 Adapter
 * author: Created by ShuaiQi_Zhang on 2018/5/31
 * version:
 */
public class QualityECDetailAdapter extends SingleAdapter<QualityEvaluationDetailScan2Bean.DataBean> {


    public QualityECDetailAdapter(List<QualityEvaluationDetailScan2Bean.DataBean> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void bind(BaseViewHolder holder, QualityEvaluationDetailScan2Bean.DataBean data) {
        TextView name = holder.getView(R.id.item_quality_detail_text_name);
        TextView btnDownload = holder.getView(R.id.item_quality_detail_text_download); //下载
        TextView btnSee = holder.getView(R.id.item_quality_detail_text_see); //查看

        if (data != null) {
            name.setText(data.getName());
            //格式判断
            if (!TextUtils.isEmpty(data.getFileext())) {
                //显示查看按钮
                btnSee.setVisibility(View.VISIBLE);
                switch (data.getFileext()) {
                    case "zip":
                        Drawable drawableLeft1 = mContext.getResources().getDrawable(
                                R.drawable.icon_zip);
                        name.setCompoundDrawablesWithIntrinsicBounds(drawableLeft1,
                                null, null, null);
                        //隐藏查看按钮
                        btnSee.setVisibility(View.INVISIBLE);
                        break;
                    case "xls":
                        Drawable drawableLeft2 = mContext.getResources().getDrawable(
                                R.drawable.icon_xls);
                        name.setCompoundDrawablesWithIntrinsicBounds(drawableLeft2,
                                null, null, null);
                        break;
                    case "pdf":
                        Drawable drawableLeft3 = mContext.getResources().getDrawable(
                                R.drawable.icon_pdf);
                        name.setCompoundDrawablesWithIntrinsicBounds(drawableLeft3,
                                null, null, null);
                        break;
                    case "jpg":
                        Drawable drawableLeft4 = mContext.getResources().getDrawable(
                                R.drawable.icon_jpg);
                        name.setCompoundDrawablesWithIntrinsicBounds(drawableLeft4,
                                null, null, null);
                        break;
                    case "docx":
                    case "doc":
                        Drawable drawableLeft5 = mContext.getResources().getDrawable(
                                R.drawable.icon_docx);
                        name.setCompoundDrawablesWithIntrinsicBounds(drawableLeft5,
                                null, null, null);
                        break;
                }
            }
            // 下载
            btnDownload.setOnClickListener(v -> {
                if (TextUtils.isEmpty(data.getFilepath())) return;
                String imgURL = Utils.addHttpURL(Utils.tranFormURL(data.getFilepath())); //图片
                downloadFile(imgURL, Constant.BASE_PATH + File.separator + data.getName());
            });
            //查看
            btnSee.setOnClickListener(v -> {
                if (TextUtils.isEmpty(data.getFilepath())) return;
                String imgURL = Utils.addHttpURL(Utils.tranFormURL(data.getFilepath())); //图片
                if (data.getFileext().equals("jpg") || data.getFileext().equals("png") ||
                        data.getFileext().equals("gif")) {
                    List<ImageBean> imgList = new ArrayList<>();
                    imgList.add(new ImageBean(imgURL, 2));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(PhotoViewActivity.KEY_IMAGE_LIST, (Serializable) imgList);
                    bundle.putInt(PhotoViewActivity.KEY_IMAGE_POSITION, 0);
                    mContext.startActivity(new Intent(mContext, PhotoViewActivity.class).putExtras(bundle));
                } else if (data.getFileext().equals("pdf") || data.getFileext().equals("xls") ||
                        data.getFileext().equals("docx") || data.getFileext().equals("doc")) {
                    Intent intent = new Intent(mContext, PDFViewerActivity.class);
                    intent.putExtra("key_url", imgURL);
                    intent.putExtra("file_name", data.getName());
                    intent.putExtra("file_id", data.getId() + "");
                    mContext.startActivity(intent);
                }
            });
        }
    }


    //下载文件
    private void downloadFile(final String url, String path) {
        LoadingDialog loadingDialog = new LoadingDialog(mContext);
        loadingDialog.setSpinnerType(0);
        RequestParams requestParams = new RequestParams(url);
        requestParams.setAutoRename(false);//取消自动命名
        requestParams.setSaveFilePath(path);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
                loadingDialog.show();
                loadingDialog.setMessage("下载中...");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }

            @Override
            public void onSuccess(File result) {
                ToastUtils.showShort("下载完成！\n 下载路径：" + Constant.BASE_PATH);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showShort("下载失败，请检查网络和SD卡！");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                loadingDialog.dismiss();
            }
        });
    }
}

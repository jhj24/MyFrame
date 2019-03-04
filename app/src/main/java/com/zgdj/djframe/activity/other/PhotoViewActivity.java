package com.zgdj.djframe.activity.other;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.ImageAdapter;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.bean.ImageBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.LoadingDialog;
import com.zgdj.djframe.view.PhotoViewPager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片浏览器
 */
public class PhotoViewActivity extends BaseNormalActivity {

    private PhotoViewPager mViewPager;
    private int currentPosition;
    private ImageAdapter adapter;
    private TextView instruction;

    private List<ImageBean> imgs = new ArrayList<>();
    public static String KEY_IMAGE_LIST = "imageList";
    public static String KEY_IMAGE_POSITION = "imageList_position";

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            imgs = (List<ImageBean>) bundle.getSerializable(KEY_IMAGE_LIST);
            currentPosition = bundle.getInt(KEY_IMAGE_POSITION);
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_photo_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

        mViewPager = view.findViewById(R.id.photo_viewpager);
        instruction = view.findViewById(R.id.photo_tv_instruction);

    }

    @Override
    public void doBusiness() {
        setTitle("图片查看");

        adapter = new ImageAdapter(imgs, this, currentPosition);
        mViewPager.setAdapter(adapter);
        instruction.setText((currentPosition + 1) + " / " + imgs.size());
        mViewPager.setCurrentItem(currentPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                instruction.setText((position + 1) + " / " + imgs.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //下载文件
    private void downloadFile(final String url, String path) {
        LoadingDialog loadingDialog = new LoadingDialog(PhotoViewActivity.this);
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

    @Override
    public void onWidgetClick(View view) {

    }
}

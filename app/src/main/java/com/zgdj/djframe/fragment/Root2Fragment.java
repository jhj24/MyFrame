package com.zgdj.djframe.fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseFragment;
import com.zgdj.djframe.utils.CameraUtils;
import com.zgdj.djframe.utils.FragmentUtils;
import com.zgdj.djframe.utils.MPermissionUtils;
import com.zgdj.djframe.utils.ToastUtils;

import static android.app.Activity.RESULT_OK;
import static com.zgdj.djframe.constant.Constant.CHOICE_FROM_ALBUM_REQUEST_CODE;
import static com.zgdj.djframe.constant.Constant.TAKE_PHOTO_REQUEST_CODE;


/**
 * description: fragment2
 * author: Created by ShuaiQi_Zhang on 2018/4/19
 * version: 1.0
 */
public class Root2Fragment extends BaseFragment implements FragmentUtils.OnBackClickListener {

    private ImageView imageView;
    private CameraUtils cameraUtils;//相机

    public static Root2Fragment newInstance() {
        Bundle args = new Bundle();
        Root2Fragment fragment = new Root2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_root_2;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        view.findViewById(R.id.button).setOnClickListener(this);
        imageView = view.findViewById(R.id.imageView);

    }

    @Override
    public void doBusiness() {
        cameraUtils = new CameraUtils(getActivity());
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                checkPermission();
                break;
        }
    }

    @Override
    public boolean onBackClick() {
        return false;
    }


    //权限问题
    private void checkPermission() {
        MPermissionUtils.requestPermissionsResult(Root2Fragment.this, 3,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        cameraUtils.showDialog();
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtils.showShort("没有权限，无法使用相机功能！");
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == TAKE_PHOTO_REQUEST_CODE) {//拍照
                    Glide.with(getActivity()).load(cameraUtils.getPhotoUri()).into(imageView);
                } else if (requestCode == CHOICE_FROM_ALBUM_REQUEST_CODE) {//相册
                    if (data != null) {
                        Uri uri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
                        int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String path = cursor.getString(actual_image_column_index);
                        Glide.with(getActivity()).load(path).into(imageView);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

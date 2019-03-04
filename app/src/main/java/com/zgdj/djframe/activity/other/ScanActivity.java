package com.zgdj.djframe.activity.other;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseBackActivity;
import com.zgdj.djframe.utils.ToastUtils;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * 二维码页面
 */
public class ScanActivity extends BaseBackActivity implements QRCodeView.Delegate {

    private QRCodeView mQRCodeView;
    private ConstraintLayout mainLayout;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();//打开相机
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();//开始扫描
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_scan;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        setTitle("扫一扫");
//        closeScrollToolBar();
        mainLayout = view.findViewById(R.id.layout_scan_main);
        mQRCodeView = view.findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);

    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    //震动提醒
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onScanQRCodeSuccess(String result) { //扫描成功
        ToastUtils.showShort(result);
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {//扫描失败
        ToastUtils.showShort("打开相机出错!");
    }
}

package com.zgdj.djframe.activity.other;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.view.View;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.utils.fingerprint.AuthCallback;
import com.zgdj.djframe.utils.fingerprint.CryptoObjectHelper;

/**
 * 指纹识别
 */
public class FingerprintActivity extends BaseNormalActivity {

    private FingerprintManagerCompat fingerprintManager;
    private CancellationSignal cancellationSignal;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_fingerprint;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        setTitle("指纹验证");
    }

    @Override
    public void doBusiness() {
        // Using the Android Support Library v4
        fingerprintManager = FingerprintManagerCompat.from(this);
        if (fingerprintManager.isHardwareDetected()) {//检查硬件是否支持指纹
            Logs.debug("支持...");
            //当前设备必须是处于安全保护中的
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                if (keyguardManager.isKeyguardSecure()) {//检查当前设备是不是处于安全保护中
                    Logs.debug("安全保护中");
                    if (fingerprintManager.hasEnrolledFingerprints()) {//设备是否设置了指纹
                        Logs.debug("设备存在指纹");
                        try {
                            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
                            AuthCallback authCallback = new AuthCallback(handler);
                            cancellationSignal = new CancellationSignal();
                            fingerprintManager.authenticate(cryptoObjectHelper.buildCryptoObject(),
                                    0, cancellationSignal, authCallback, null);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Logs.debug("你的设备必须是使用屏幕锁保护的，这个屏幕锁可以是password，PIN或者图案都行");
                }
            }
        } else {
            ToastUtils.showShort("不支持 -_- ");
        }
    }


    //
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.MSG_AUTH_SUCCESS://成功
                    ToastUtils.showShort("验证成功~ ");
                    break;
                case Constant.MSG_AUTH_FAILED://失败
                    ToastUtils.showShort("验证失败");
                    break;
                case Constant.MSG_AUTH_ERROR://错误
                    handleErrorCode(msg.arg1);
                    break;
                case Constant.MSG_AUTH_HELP://手速太快
                    handleHelpCode(msg.arg1);
                    break;
            }
        }
    };

    private void handleErrorCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
//                setResultInfo(R.string.ErrorCanceled_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
//                setResultInfo(R.string.ErrorHwUnavailable_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
//                setResultInfo(R.string.ErrorLockout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
//                setResultInfo(R.string.ErrorNoSpace_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
//                setResultInfo(R.string.ErrorTimeout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
//                setResultInfo(R.string.ErrorUnableToProcess_warning);
                break;
        }
    }

    private void handleHelpCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ACQUIRED_GOOD:
//                setResultInfo(R.string.AcquiredGood_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_IMAGER_DIRTY:
//                setResultInfo(R.string.AcquiredImageDirty_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_INSUFFICIENT:
//                setResultInfo(R.string.AcquiredInsufficient_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_PARTIAL:
//                setResultInfo(R.string.AcquiredPartial_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_FAST:
//                setResultInfo(R.string.AcquiredTooFast_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_SLOW:
//                setResultInfo(R.string.AcquiredToSlow_warning);
                break;
        }
    }


    @Override
    public void onWidgetClick(View view) {


    }

    @Override
    protected void onDestroy() {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
        super.onDestroy();

    }
}

package com.zgdj.djframe.utils.fingerprint;

import android.os.Handler;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import com.zgdj.djframe.constant.Constant;


/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/18
 * version:
 */
public class AuthCallback extends FingerprintManagerCompat.AuthenticationCallback {

    private Handler handler = null;

    public AuthCallback(Handler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        //这个接口会再系统指纹认证出现不可恢复的错误的时候才会调用，
        // 并且参数errorCode就给出了错误码，标识了错误的原因。
        // 这个时候app能做的只能是提示用户重新尝试一遍。
        super.onAuthenticationError(errMsgId, errString);

        if (handler != null) {
            handler.obtainMessage(Constant.MSG_AUTH_ERROR, errMsgId, 0).sendToTarget();
        }
    }


    @Override
    public void onAuthenticationFailed() {
        //这个接口会在系统指纹认证失败的情况的下才会回调。
        // 注意这里的认证失败和上面的认证错误是不一样的，虽然结果都是不能认证。
        // 认证失败是指所有的信息都采集完整，并且没有任何异常，但是这个指纹和之前注册的指纹是不相符的；
        super.onAuthenticationFailed();

        if (handler != null) {
            handler.obtainMessage(Constant.MSG_AUTH_FAILED).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        //上面的认证失败是认证过程中的一个异常情况，我们说那种情况是因为出现了不可恢复的错误，
        // 而我们这里的OnAuthenticationHelp方法是出现了可以恢复的异常才会调用的。
        // 什么是可以恢复的异常呢？一个常见的例子就是：手指移动太快，
        // 当我们把手指放到传感器上的时候，如果我们很快地将手指移走的话，
        // 那么指纹传感器可能只采集了部分的信息，因此认证会失败。但是这个错误是可以恢复的，
        // 因此只要提示用户再次按下指纹，并且不要太快移走就可以解决。
        super.onAuthenticationHelp(helpMsgId, helpString);
        if (handler != null) {
            handler.obtainMessage(Constant.MSG_AUTH_HELP, helpMsgId, 0).sendToTarget();
        }
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);

        if (handler != null) {
            handler.obtainMessage(Constant.MSG_AUTH_SUCCESS).sendToTarget();
        }

       /* try {
            result.getCryptoObject().getCipher().doFinal();

            if (handler != null) {
                handler.obtainMessage(Constant.MSG_AUTH_SUCCESS).sendToTarget();
            }
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }*/
    }


}

package com.zgdj.djframe.unity3d;

import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.ToastUtils;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/5/24
 * version:
 */
public class ConnectUnityToAndroid {


    public static void showToast(String name) {
        ToastUtils.showShort("连接Unity成功！！！" + name);
        Logs.debug("showTime !!!" + name);
    }
}

package com.zgdj.djframe.unity3d;

import android.content.ContextWrapper;

import com.unity3d.player.UnityPlayer;

/**
 * description: 重写UnityPlayer ,防止把应用杀死了！！！混蛋
 * author: Created by ShuaiQi_Zhang on 2018/4/25
 * version:
 */
public class MyUnityPlayer extends UnityPlayer {
    public MyUnityPlayer(ContextWrapper contextWrapper) {
        super(contextWrapper);
    }

    /**
     * 不执行父类方法
     */
    @Override
    protected void kill() {
        //        super.kill();
    }
}
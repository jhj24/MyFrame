package com.zgdj.djframe.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unity3d.player.UnityPlayer;
import com.zgdj.djframe.unity3d.UnityUtil;
import com.zgdj.djframe.utils.Logs;

/**
 * description:
 * author: Created by ShuaiQi_Zhang on 2018/4/25
 * version:
 */
public class UnityFragment extends Fragment {

    View playerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        playerView = UnityUtil.mUnityPlayer.getView();
        //具体参数 跟自己公司Unity开发人员协商
        //第一个参数是unity那边的挂载脚本名字
        //第二个参数是 unity提供的方法名
        //第三个参数是 自己要给unity传的值
//        My_unity.mUnityPlayer.UnitySendMessage("Main Camera", "Id", "1");

        UnityPlayer.currentActivity.runOnUiThread(() -> {
            UnityPlayer.UnitySendMessage("Main Camera", "getInfo", "名称88");
            Logs.debug("UnityFragment --- info ");
        });

        return playerView;
    }
}

package com.zgdj.djframe.activity.other;

import android.os.Bundle;
import android.view.View;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.utils.Utils;
import com.zgdj.djframe.view.PaletteView;

/**
 * 画板签名
 */

public class PaletteActivity extends BaseNormalActivity {

    private PaletteView paletteView;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_palette;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        paletteView = view.findViewById(R.id.paletteView);
        view.findViewById(R.id.btn_clear).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {
        setTitle("画板");
        paletteView.setMode(PaletteView.Mode.DRAW);//设置成画笔
        paletteView.setPenColor(R.color.normal_black);//笔颜色
        paletteView.setPenRawSize(Utils.dp2px(this, 12));//笔宽度
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear://清空
                paletteView.clear();
                break;
        }
    }
}

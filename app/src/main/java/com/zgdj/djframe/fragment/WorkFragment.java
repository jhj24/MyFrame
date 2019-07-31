package com.zgdj.djframe.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.work.DocumentTreeActivity;
import com.zgdj.djframe.activity.work.ModelAllActivity;
import com.zgdj.djframe.activity.work.ModelRealActivity;
import com.zgdj.djframe.activity.work.ProgressRealTimeActivity;
import com.zgdj.djframe.activity.work.QualityControlActivity;
import com.zgdj.djframe.activity.work.QualityEvaluationActivity;
import com.zgdj.djframe.activity.work.StandardTreeActivity;
import com.zgdj.djframe.adapter.WorkAdapter;
import com.zgdj.djframe.base.BaseFragment;
import com.zgdj.djframe.bean.WorkBean;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.MPermissionUtils;
import com.zgdj.djframe.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 工作模块
 * author: Created by ShuaiQi_Zhang on 2018/5/17
 * version: 1.0
 */
public class WorkFragment extends BaseFragment {

    private RecyclerView recyclerModel;//模型管理
    private RecyclerView recyclerQuality;//质量管理
    private RecyclerView recyclerProgress;//进度管理
    private RecyclerView recyclerDocument;//图册
    private RecyclerView recyclerStandard;//标准与规范
    private List<WorkBean> modelList;//模型list
    private List<WorkBean> qualityList;//模型list
    private List<WorkBean> progressList;//进度list
    private List<WorkBean> documentList;//进度list
    private List<WorkBean> standardList;//进度list

    public static WorkFragment newInstance() {
        Bundle args = new Bundle();
        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_work;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        recyclerModel = view.findViewById(R.id.work_recycler_model);
        recyclerQuality = view.findViewById(R.id.work_recycler_quality);
        recyclerProgress = view.findViewById(R.id.work_recycler_progress);
        recyclerDocument = view.findViewById(R.id.work_recycler_document);
        recyclerStandard = view.findViewById(R.id.work_recycler_standard);


//        view.findViewById(R.id.work_btn_quality_control).setOnClickListener(this);//质量管控
//        view.findViewById(R.id.work_btn_model_all).setOnClickListener(this);//全景模型
//        view.findViewById(R.id.work_btn_model_real).setOnClickListener(this);//实景模型
//        view.findViewById(R.id.work_btn_quality_evaluation).setOnClickListener(this);//质量验评


    }

    @Override
    public void doBusiness() {
        modelList = new ArrayList<>();
        modelList.add(new WorkBean("全景模型", R.drawable.ic_model_all));
        modelList.add(new WorkBean("实景模型", R.drawable.ic_model_real));
        setRecycler(recyclerModel, modelList, 1);

        qualityList = new ArrayList<>();
//        qualityList.add(new WorkBean("质量管控", R.drawable.ic_quality_control));
        qualityList.add(new WorkBean("质量验评", R.drawable.ic_quality_evalutation));
        setRecycler(recyclerQuality, qualityList, 2);

        progressList = new ArrayList<>();
        progressList.add(new WorkBean("实时进度", R.drawable.icon_progress));
        setRecycler(recyclerProgress, progressList, 3);


        documentList = new ArrayList<>();
        documentList.add(new WorkBean("图册管理", R.drawable.icon_progress));
        setRecycler(recyclerDocument, documentList, 4);

        standardList = new ArrayList<>();
        standardList.add(new WorkBean("工程标准与规范", R.drawable.icon_progress));
        setRecycler(recyclerStandard, standardList, 5);

    }


    //设置RecyclerView
    private void setRecycler(RecyclerView recycler, List<WorkBean> list, int type) {
        WorkAdapter adapter = new WorkAdapter(list, R.layout.item_recycler_work);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler.setNestedScrollingEnabled(false);
        Logger.w("type:" + type + "; size:" + list.size() + ";adapter:" + adapter + "; recycler:" + recycler);
        adapter.setOnItemClickListener((view, position) -> {
            if (type == 1) { // 模型管理
                switch (position) {
                    case 0: // 全景模型
//                        Bundle bundle = new Bundle();
//                        bundle.putString("key_url", Constant.URL_MODEL_ALL);
//                        jumpToInterface(WebViewActivity.class, bundle);
                        jumpToInterface(ModelAllActivity.class);
                        break;
                    case 1://实景模型
                        jumpToInterface(ModelRealActivity.class);
                        break;
                }

            } else if (type == 2) { //质量管理
                switch (position) {
                  /*  case 0: //质量管控
                        checkPermission();
                        break;*/
                    case 0://质量验评
                        jumpToInterface(QualityEvaluationActivity.class);
                        break;
                }

            } else if (type == 3) { //进度管理
                switch (position) {
                    case 0: //实时进度
                        jumpToInterface(ProgressRealTimeActivity.class);
                        break;

                }

            } else if (type == 4) { //图纸文档管理
                switch (position) {
                    case 0: //图册管理
                        jumpToInterface(DocumentTreeActivity.class);
                        break;

                }

            } else if (type == 5) { //工程库管理
                switch (position) {
                    case 0: //工程标准与规范
                        jumpToInterface(StandardTreeActivity.class);
                        break;

                }

            } else {
                Logs.debug("type出错~");
            }
        });
    }

    @Override
    public void onWidgetClick(View view) {

    }


    //检查权限
    private void checkPermission() {
        String[] permissions = {
                Manifest.permission.CAMERA //相机权限
        };
        MPermissionUtils.requestPermissionsResult(WorkFragment.this, 2, permissions,
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        jumpToInterface(QualityControlActivity.class);
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtils.showShort("扫描二维码需要打开相机的权限");
                    }
                });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

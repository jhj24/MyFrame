package com.zgdj.djframe.activity.work;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.other.WebH5Activity;
import com.zgdj.djframe.adapter.HomeTabAdapter;
import com.zgdj.djframe.adapter.QualityECDetail2Adapter;
import com.zgdj.djframe.adapter.QualityECDetailAdapter;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.bean.ProjectProgressBean;
import com.zgdj.djframe.bean.QualityEvaluationDetailScan2Bean;
import com.zgdj.djframe.bean.QualityEvaluationDetailScanBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.LoadingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 质量验评 -- 内容 -- 详情
 */
public class QualityEvaluationContentDetailActivity extends BaseNormalActivity {

    private RecyclerView reyclerTab;
    private HomeTabAdapter tabAdapter;
    private List<ProjectProgressBean> tabBean;

    private RecyclerView recyclerList;
    // 扫描件 && 在线填报
    private QualityECDetail2Adapter detail2Adapter;
    private List<QualityEvaluationDetailScanBean> beanList;
    //附件资料
    private QualityECDetailAdapter detailAdapter;
    private List<QualityEvaluationDetailScan2Bean.DataBean> beanList0;
    // id
    private String unitId;
    private String controlId;
    // loading
    private LoadingDialog loadingDialog;
    // empty
    private LinearLayout layout_empty;

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            unitId = bundle.getString("UnitId");
            controlId = bundle.getString("ControlId");
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_quality_evaluation_content_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        reyclerTab = view.findViewById(R.id.quality_detail_recycler_tab);
        recyclerList = view.findViewById(R.id.quality_detail_recycler_list);
        layout_empty = view.findViewById(R.id.layout_empty);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.setMessage("努力加载...");
        loadingDialog.setSpinnerType(0);

    }

    @Override
    public void doBusiness() {
        setTitle("详情");
        // set tab
        setRecyclerTab();
        //默认加载
        getListTask(Constant.URL_QUALITY_EVALUATIOHN_DETAIL_SCAN, 0);
    }

    // set tab
    private void setRecyclerTab() {
        tabBean = new ArrayList<>();
        tabBean.add(new ProjectProgressBean("扫描件回传", true));
        tabBean.add(new ProjectProgressBean("附件资料", false));
        tabBean.add(new ProjectProgressBean("在线填报", false));
        tabAdapter = new HomeTabAdapter(tabBean, R.layout.item_recycler_chart_tab);
        reyclerTab.setNestedScrollingEnabled(false);
        reyclerTab.setLayoutManager(new GridLayoutManager(this, 3));
        reyclerTab.setAdapter(tabAdapter);
        tabAdapter.setOnItemClickListener((view, position) -> {//点击
            tabAdapter.setChose(position);
            switch (position) {
                case 0: //
                    getListTask(Constant.URL_QUALITY_EVALUATIOHN_DETAIL_SCAN, 0);
                    break;
                case 1: //
                    getListTask(Constant.URL_QUALITY_EVALUATIOHN_DETAIL_ENCLOSURE, 1);
                    break;
                case 2: //
                    getListTask(Constant.URL_QUALITY_EVALUATIOHN_DETAIL_FORM, 2);
                    break;
            }
        });
    }

    //附件资料 && 扫描件 列表
    private void setRecyclerList1(String result) {

        if (beanList != null) {
            beanList.clear();
            if (detail2Adapter != null) {
                detail2Adapter.setData(beanList);
                detail2Adapter.notifyDataSetChanged();
            }
        }

        if (!TextUtils.isEmpty(result)) {
            Gson gson = new Gson();
            beanList0 = new ArrayList<>();
            QualityEvaluationDetailScan2Bean bean = gson.fromJson(result, QualityEvaluationDetailScan2Bean.class);
            if (bean.getData().size() > 0) {
                layout_empty.setVisibility(View.GONE);
                recyclerList.setVisibility(View.VISIBLE);
                beanList0.addAll(bean.getData());
                detailAdapter = new QualityECDetailAdapter(beanList0, R.layout.item_reycler_quality_detail_0);
                recyclerList.setAdapter(detailAdapter);
                recyclerList.setLayoutManager(new LinearLayoutManager(this));
            }
        } else {
            if (beanList0 != null) {
                beanList0.clear();
                if (detailAdapter != null) {
                    detailAdapter.setData(beanList0);
                    detailAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    // 在线填报
    private void setRecyclerList(int type, String result) {
        beanList = new ArrayList<>();

        if (!TextUtils.isEmpty(result)) {
            Gson gson = new Gson();
            QualityEvaluationDetailScanBean bean = gson.fromJson(result, QualityEvaluationDetailScanBean.class);
            if (bean.getData().size() > 0) {
                layout_empty.setVisibility(View.GONE);
                recyclerList.setVisibility(View.VISIBLE);
                beanList.add(bean);
                detail2Adapter = new QualityECDetail2Adapter(beanList, R.layout.item_reycler_quality_detail_2);
                recyclerList.setAdapter(detail2Adapter);
                recyclerList.setLayoutManager(new LinearLayoutManager(this));
                detail2Adapter.setOnItemClickListener((view, position) -> {
                    if (!TextUtils.isEmpty(beanList.get(position).getHerf())) {
                        Bundle bundle = new Bundle();
                        String baseUrl = beanList.get(position).getHerf();
                        bundle.putString("key_url", baseUrl + "?cpr_id=" + beanList.get(position).getCpr_id() + "&id=" + beanList.get(position).getData().get(0).getId() + "&currentStep=null&isView=true");
                        jumpToInterface(WebH5Activity.class, bundle);
                    } else {
                        ToastUtils.showShort("链接出错！！！");
                    }
                });
            }
        } else {
            beanList.clear();
            detail2Adapter.setData(beanList);
            detail2Adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onWidgetClick(View view) {

    }

    //扫描回传 &&  附件资料 && 在线填报 task
    private void getListTask(String url, int type) {
        loadingDialog.show();
        layout_empty.setVisibility(View.VISIBLE);
        recyclerList.setVisibility(View.GONE);

        RequestParams params = new RequestParams(url);
//        params.addBodyParameter(Constant.KEY_TOKEN, token);
        params.addHeader("token", token);
        params.addBodyParameter("unit_id", unitId); // 段号id
        params.addBodyParameter("control_id", controlId); // 控制点id
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) { // QualityEvaluationDetailScanBean
                Logs.debug("result:" + type);
                Logger.json(result);
                switch (type) {
                    case 0: //扫描回传
//                        setRecyclerList(0, result);
                        setRecyclerList1(result);
                        break;
                    case 1://附件资料
                        setRecyclerList1(result);
                        break;
                    case 2: //在线填报
                        setRecyclerList(2, result);
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
}

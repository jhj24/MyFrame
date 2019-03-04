package com.zgdj.djframe.activity.work;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.bean.QualityEvaluationList1Bean;
import com.zgdj.djframe.bean.QualityEvaluationList2Bean;
import com.zgdj.djframe.bean.QualityEvaluationList3Bean;
import com.zgdj.djframe.bean.treebean.BranchNode;
import com.zgdj.djframe.bean.treebean.BranchViewBinder;
import com.zgdj.djframe.bean.treebean.LeafNode;
import com.zgdj.djframe.bean.treebean.LeafViewBinder;
import com.zgdj.djframe.bean.treebean.RootNode;
import com.zgdj.djframe.bean.treebean.RootViewBinder;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.treeview.LayoutItem;
import com.zgdj.djframe.view.treeview.TreeNode;
import com.zgdj.djframe.view.treeview.TreeViewAdapter;
import com.zgdj.djframe.view.treeview.TreeViewBinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 质量验评
 */
public class QualityEvaluationActivity extends BaseNormalActivity {

    private SmartRefreshLayout refreshLayout; //刷新layout
    private RecyclerView recyclerView;
    // 树形列表
    private List<TreeNode> treeNodes;
    private TreeViewAdapter treeViewAdapter;
    private List<QualityEvaluationList2Bean> listBean;//子列表
    // empty
    private LinearLayout layout_empty;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_quality_evaluation;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        refreshLayout = view.findViewById(R.id.layout_evaluation_refresh);
        layout_empty = view.findViewById(R.id.layout_empty);
        recyclerView = view.findViewById(R.id.recycler_evaluation);
    }

    @Override
    public void doBusiness() {
        // 禁止上下拉刷新
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
        setTitle("质量验评");

        initTreeView();
        getQualityListTask();
    }

    @Override
    public void onWidgetClick(View view) {

    }

    //初始化树形列表相关
    private void initTreeView() {
        treeNodes = new ArrayList<>();
        treeViewAdapter = new TreeViewAdapter(treeNodes, Arrays.asList(new RootViewBinder(), new BranchViewBinder(), new LeafViewBinder()), this) {
            @Override
            public void toggleClick(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                if (isOpen) {
                    LayoutItem item = treeNode.getValue();
                    if (item instanceof RootNode) { // 最外层
                        String sId = ((RootNode) item).getSectionId();
                        getQualityChildrenListTask(treeNode, sId);
                    } else if (item instanceof BranchNode) {
//                        treeViewAdapter.lastToggleClickToggle();
                        String sId = ((BranchNode) item).getSectionId();
                        if (treeNode.getLevel() == 3) {//最外层
                            getQualityGrandsonListTask(sId, treeNode);
                        } else {
                            filterBranchList(treeNode, listBean, sId);
                        }
                    } else if (item instanceof LeafNode) {


                    }
                } else {
                    treeViewAdapter.lastToggleClickToggle();
                }
            }

            @Override
            public void toggled(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                viewHolder.findViewById(R.id.ivNode).setRotation(isOpen ? 90 : 0);
            }

            @Override
            public void checked(TreeViewBinder.ViewHolder viewHolder, View view, boolean checked, TreeNode treeNode) {

            }

            @Override
            public void itemClick(TreeViewBinder.ViewHolder viewHolder, View view, TreeNode treeNode) {
                String name = null;
                LayoutItem item = treeNode.getValue();
                if (item instanceof RootNode) { // 最外层
                    name = ((RootNode) item).getName();
                } else if (item instanceof BranchNode) {
                    name = ((BranchNode) item).getName();
                } else if (item instanceof LeafNode) { //跳到内容页
                    name = ((LeafNode) item).getName();
                    String sId = ((LeafNode) item).getId();
                    Bundle bundle = new Bundle();
                    bundle.putString("QualityId", sId);
                    bundle.putString("QualityType", ((LeafNode) item).getType());
                    bundle.putString("QualityName", ((LeafNode) item).getName());
                    jumpToInterface(QualityEvaluationContentActivity.class, bundle);
                }
                Toast.makeText(QualityEvaluationActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        };
        recyclerView.setAdapter(treeViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //获取质量列表 - 最外层列表
    private void getQualityListTask() {
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_1);
        params.addHeader("token", token);
//        params.addBodyParameter(Constant.KEY_TOKEN, token);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logs.debug("获取质量列表:" + result);
                if (!result.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("section");
                            setTreeNodes(jsonArray.toString());
                        } else if (code == -2) {
                            ToastUtils.showShort(Constant.TOKEN_LOST);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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

            }
        });
    }


    //获取质量子类列表 --  第二、三、四、五等层
    private void getQualityChildrenListTask(TreeNode treeNode, String sectionId) {
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_2);
        params.addHeader("token", token);
        params.addBodyParameter("section_id", sectionId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.json("子类列表", result);
                if (!result.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("division");
                            setBranchNodes(treeNode, jsonArray.toString(), sectionId);
                        } else if (code == -2) {
                            ToastUtils.showShort(Constant.TOKEN_LOST);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //获取质量孙子类列表
    private void getQualityGrandsonListTask(String sectionId, TreeNode fifthNode) {
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_3);
        params.addHeader("token", token);
//        params.addBodyParameter(Constant.KEY_TOKEN, token);
        params.addBodyParameter("division_id", sectionId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logger.json("孙子类", result);
                if (!result.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            setLeafNodes(jsonArray.toString(), fifthNode);
                        } else if (code == -2) {
                            ToastUtils.showShort(Constant.TOKEN_LOST);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

            }
        });
    }

    //设置最外层列表
    private void setTreeNodes(String info) {
        if (info != null && !info.isEmpty()) {
            Gson gson = new Gson();
            Type qualityList = new TypeToken<List<QualityEvaluationList1Bean>>() {
            }.getType();
            List<QualityEvaluationList1Bean> listBean = gson.fromJson(info, qualityList);
            //整理成树形图
            if (listBean.size() > 0) {
                layout_empty.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < listBean.size(); i++) {
                    TreeNode<RootNode> firstNode = new TreeNode<>(new RootNode(listBean.get(i).getName()
                            , listBean.get(i).getId()));
                    treeNodes.add(firstNode);
                }
                treeViewAdapter.notifyData(treeNodes);
            }
        }
    }

    //设置第二、三、四等层列表
    //TreeNode : 父类节点
    private void setBranchNodes(TreeNode treeNode, String info, String pid) {
        if (info != null && !info.isEmpty()) {
            Gson gson = new Gson();
            Type qualityList = new TypeToken<List<QualityEvaluationList2Bean>>() {
            }.getType();
            listBean = new ArrayList<>();
            listBean = gson.fromJson(info, qualityList);
            //整理成树形图
            filterList(treeNode, listBean, pid);
        }
    }

    // 网络请求获取数据 - 1
    private void filterList(TreeNode treeNode, List<QualityEvaluationList2Bean> list, String pid) {
        treeNode.getChildNodes().clear();//清空数据
        // pid = 0 最外层
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j).getPid().equals("0")) {//最外层
                TreeNode<BranchNode> onceNode = new TreeNode<>(new BranchNode(list.get(j).getD_name(), list.get(j).getId()));
                for (int i = 0; i < list.size(); i++) { //第二层
                    if (list.get(j).getId().equals(list.get(i).getPid())) {
                        TreeNode<BranchNode> secondNode = new TreeNode<>(new BranchNode(list.get(i).getD_name(), list.get(i).getId()));
                        onceNode.addChild(secondNode);
                    }
                }
                treeNode.addChild(onceNode);
            }
        }
        treeViewAdapter.lastToggleClickToggle();
    }

    //本地数据 -2
    private void filterBranchList(TreeNode secondNode, List<QualityEvaluationList2Bean> list, String pid) {
        secondNode.getChildNodes().clear();
        for (int j = 0; j < list.size(); j++) {
            if (pid.equals(list.get(j).getPid())) { //第三层
                TreeNode<BranchNode> thirdNode = new TreeNode<>(new BranchNode(list.get(j).getD_name(), list.get(j).getId()));
                secondNode.addChild(thirdNode);
                for (int k = 0; k < list.size(); k++) {//第四层
                    if (list.get(j).getId().equals(list.get(k).getPid())) {
                        TreeNode<BranchNode> fourthNode = new TreeNode<>(new BranchNode(list.get(k).getD_name(), list.get(k).getId()));
                        thirdNode.addChild(fourthNode);
                    }

                }
            }
        }
        treeViewAdapter.lastToggleClickToggle();
    }

    //设置叶子点
    private void setLeafNodes(String info, TreeNode fifthNode) {
        if (info != null && !info.isEmpty()) {
            Gson gson = new Gson();
            Type qualityList = new TypeToken<List<QualityEvaluationList3Bean>>() {
            }.getType();
            List<QualityEvaluationList3Bean> list = gson.fromJson(info, qualityList);
//            Logs.debug("孙子：" + list.size());
            if (list.size() > 0) {
                filterLeafList(fifthNode, list);
            } else {
                ToastUtils.showShort("暂无数据...");
            }
        }
        treeViewAdapter.lastToggleClickToggle();
    }

    //过滤数据添加到叶子上
    private void filterLeafList(TreeNode fifthNode, List<QualityEvaluationList3Bean> list) {
        fifthNode.getChildNodes().clear();//日常清理
        for (QualityEvaluationList3Bean bean : list) {
            String name = bean.getEl_start() + bean.getEl_cease() + bean.getPile_number() + bean.getSite();
            TreeNode<LeafNode> fourthNode = new TreeNode<>(new LeafNode(name, String.valueOf(bean.getId()), bean.getEn_type()));
            fifthNode.addChild(fourthNode);
        }
    }

    //读取方法
    public String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = QualityEvaluationActivity.this.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

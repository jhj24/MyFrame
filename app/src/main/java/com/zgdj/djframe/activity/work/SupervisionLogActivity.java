package com.zgdj.djframe.activity.work;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseBackActivity;
import com.zgdj.djframe.bean.SupervisionBean;
import com.zgdj.djframe.bean.treebean.BranchNode;
import com.zgdj.djframe.bean.treebean.BranchViewBinder;
import com.zgdj.djframe.bean.treebean.LeafNode;
import com.zgdj.djframe.bean.treebean.LeafViewBinder;
import com.zgdj.djframe.bean.treebean.RootNode;
import com.zgdj.djframe.bean.treebean.RootViewBinder;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.RegexUtils;
import com.zgdj.djframe.view.treeview.LayoutItem;
import com.zgdj.djframe.view.treeview.TreeNode;
import com.zgdj.djframe.view.treeview.TreeViewAdapter;
import com.zgdj.djframe.view.treeview.TreeViewBinder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 监理日志
 */
public class SupervisionLogActivity extends BaseBackActivity {

    private TextView textUser;//选择用户：全部、自己、其他
    private TextView textTime;//时间
    private RecyclerView recycler_supervision;//列表
    private SmartRefreshLayout refreshLayout;//刷新layout
    //    private List<SupervisionBean.MessageBean> beanList;
//    private SupervisionAdapter adapter;
    // 树形列表
    private List<TreeNode> treeNodes;
    private TreeViewAdapter treeViewAdapter;


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_supervision_log;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        setTitle("监理日志");
        recycler_supervision = view.findViewById(R.id.recycler_supervision);
        refreshLayout = view.findViewById(R.id.layout_supervision_refresh);
        textUser = view.findViewById(R.id.tv_supervision_select_user);
        textTime = view.findViewById(R.id.tv_supervision_select_time);

    }

    @Override
    public void doBusiness() {
//        beanList = new ArrayList<>();
//        adapter = new SupervisionAdapter(beanList, R.layout.item_recycler_supervision);
//        recycler_supervision.setAdapter(adapter);
//        recycler_supervision.setLayoutManager(new LinearLayoutManager(this));


        refreshLayout.setOnRefreshListener(refreshLayout -> {
            resetDatas(stringJson);
//            getListTask();
            refreshLayout.finishRefresh(2000);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
        });
        initTreeView();
        //  获取数据
//        getListTask();
        resetDatas(stringJson);
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
                treeViewAdapter.lastToggleClickToggle();
               /* if (isOpen) {
                    addNewNode(treeNode);
                } else {
                    treeViewAdapter.lastToggleClickToggle();
                }*/
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
                if (item instanceof RootNode) {
                    name = ((RootNode) item).getName();
                } else if (item instanceof BranchNode) {
                    name = ((BranchNode) item).getName();
                } else if (item instanceof LeafNode) {
                    name = ((LeafNode) item).getName();
                }
                Toast.makeText(SupervisionLogActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        };
        recycler_supervision.setAdapter(treeViewAdapter);
        recycler_supervision.setLayoutManager(new LinearLayoutManager(this));

    }

    private void addNewNode(final TreeNode treeNode) {
//        autoProgress(true);
        new Handler().postDelayed(() -> {
            String name = null;
            LayoutItem item = treeNode.getValue();
            if (item instanceof RootNode) {
                name = ((RootNode) item).getName();
            } else if (item instanceof BranchNode) {
                name = ((BranchNode) item).getName();
            } else if (item instanceof LeafNode) {
                name = ((LeafNode) item).getName();
            }
            TreeNode<LeafNode> leafNode1 = new TreeNode<>(new LeafNode(name + "新增叶1"));
            TreeNode<LeafNode> leafNode2 = new TreeNode<>(new LeafNode(name + "新增叶2"));
            List<TreeNode> list = treeNode.getChildNodes();
            boolean hasLeaf = false;
            for (TreeNode child : list) {
                if (child.getValue() instanceof LeafNode) {
                    hasLeaf = true;
                    break;
                }
            }
            if (!hasLeaf) {
                treeNode.addChild(leafNode1);
                treeNode.addChild(leafNode2);
            }
//                autoProgress(false);
            treeViewAdapter.lastToggleClickToggle();
        }, 200);
    }

    String stringJson = "{\n" +
            "    \"status\": \"200\",\n" +
            "    \"msg\": \"OK\",\n" +
            "    \"message\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"丰宁抽水蓄能电站\",\n" +
            "            \"pid\": 0,\n" +
            "            \"date\": null,\n" +
            "            \"year\": \"\",\n" +
            "            \"month\": \"\",\n" +
            "            \"day\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 282,\n" +
            "            \"name\": \"2018年\",\n" +
            "            \"pid\": 1,\n" +
            "            \"date\": null,\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"\",\n" +
            "            \"day\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 283,\n" +
            "            \"name\": \"04月\",\n" +
            "            \"pid\": 282,\n" +
            "            \"date\": null,\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"04\",\n" +
            "            \"day\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 285,\n" +
            "            \"name\": \"23日\",\n" +
            "            \"pid\": 283,\n" +
            "            \"date\": \"2018-04-23 10:21:32\",\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"04\",\n" +
            "            \"day\": \"23\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 286,\n" +
            "            \"name\": \"25日\",\n" +
            "            \"pid\": 283,\n" +
            "            \"date\": \"2018-04-25 17:49:24\",\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"04\",\n" +
            "            \"day\": \"25\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 289,\n" +
            "            \"name\": \"26日\",\n" +
            "            \"pid\": 283,\n" +
            "            \"date\": \"2018-04-26 17:48:57\",\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"04\",\n" +
            "            \"day\": \"26\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 290,\n" +
            "            \"name\": \"27日\",\n" +
            "            \"pid\": 283,\n" +
            "            \"date\": \"2018-04-27 13:49:22\",\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"04\",\n" +
            "            \"day\": \"27\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 291,\n" +
            "            \"name\": \"05月\",\n" +
            "            \"pid\": 282,\n" +
            "            \"date\": null,\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"05\",\n" +
            "            \"day\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 293,\n" +
            "            \"name\": \"04日\",\n" +
            "            \"pid\": 291,\n" +
            "            \"date\": \"2018-05-04 09:56:08\",\n" +
            "            \"year\": \"2018\",\n" +
            "            \"month\": \"05\",\n" +
            "            \"day\": \"04\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    /**
     * 网络请求
     */
    private void getListTask() {
        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_SUPERVISION);
        params.addBodyParameter("interfacePassword", "123abc");//addParameter
        params.addBodyParameter("action", "supervisionLog");
        params.addBodyParameter("op", "getalldata");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logs.debug("result:" + result);
                resetDatas(result);
//                SupervisionBean bean = new Gson().fromJson(result, SupervisionBean.class);
//                adapter.setDatas(bean.getMessage());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                refreshLayout.finishRefresh();
//                refreshLayout.finishLoadMore();
            }
        });
    }


    // 重置数据
    private void resetDatas(String result) {
        if (!RegexUtils.isNull(result)) {
            //记录年月日 list
            List<SupervisionBean.MessageBean> listDay = new ArrayList<>();
            List<SupervisionBean.MessageBean> listMonth = new ArrayList<>();
            List<SupervisionBean.MessageBean> listYear = new ArrayList<>();
            SupervisionBean bean = new Gson().fromJson(result, SupervisionBean.class);
            List<SupervisionBean.MessageBean> beanList = bean.getMessage();
            if (beanList.size() > 0) {
                for (int i = 0; i < beanList.size(); i++) {
                    String day = beanList.get(i).getDay();
                    if (!RegexUtils.isNull(day)) {//天
                        listDay.add(beanList.get(i));
                    } else { // 月 & 年 & 工程
                        String month = beanList.get(i).getMonth();
                        if (!RegexUtils.isNull(month)) {//月
                            listMonth.add(beanList.get(i));
                        } else { //年 & 工程
                            String year = beanList.get(i).getYear();
                            if (!RegexUtils.isNull(year)) {//年
                                listYear.add(beanList.get(i));
                            } else { //工程

                            }
                        }
                    }
                }
                //合并数据 并添加到树形列表
                mergeDatas(listYear, listMonth, listDay);
            }
        }
    }

    //合并数据
    private void mergeDatas(List<SupervisionBean.MessageBean> listYear,
                            List<SupervisionBean.MessageBean> listMonth,
                            List<SupervisionBean.MessageBean> listDay) {
        treeNodes.clear();
        for (int i = 0; i < listYear.size(); i++) {//年
            TreeNode<RootNode> rootNode = new TreeNode<>(new RootNode(listYear.get(i).getYear() + "年"));
            for (int j = 0; j < listMonth.size(); j++) {//月
                if (listYear.get(i).getYear().equals(listMonth.get(j).getYear())) {//对比 年、 月
                    TreeNode<BranchNode> branchNode1 = new TreeNode<>(new BranchNode(listMonth.get(j).getMonth() + "月"));
                    for (int k = 0; k < listDay.size(); k++) {//日
                        if (listMonth.get(j).getMonth().equals(listDay.get(k).getMonth())) {//对比 月、日
                            TreeNode<LeafNode> leafNode = new TreeNode<>(new LeafNode(listDay.get(k).getDay() + "日"));
                            branchNode1.addChild(leafNode);
                        }
                    }
                    rootNode.addChild(branchNode1);
                }
            }
            treeNodes.add(rootNode);
        }
        treeViewAdapter.notifyData(treeNodes);
    }


}

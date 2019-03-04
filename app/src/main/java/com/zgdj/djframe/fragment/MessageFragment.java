package com.zgdj.djframe.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zgdj.djframe.MainActivity;
import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.MessageAdapter;
import com.zgdj.djframe.base.BaseFragment;
import com.zgdj.djframe.bean.MessageListBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.interf.INotifyListener;
import com.zgdj.djframe.utils.FragmentUtils;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.NotifyListenerMangager;
import com.zgdj.djframe.utils.SPUtils;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.LoadingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * description: fragment1
 * author: Created by ShuaiQi_Zhang on 2018/4/19
 * version: 1.0
 */
public class MessageFragment extends BaseFragment implements FragmentUtils.OnBackClickListener, INotifyListener {

    public static final String TAG = "MessageFragment";
    public static final String ONREFRESH = "onRefresh";
    private SmartRefreshLayout refreshLayout;//刷新Layout
    private RecyclerView recycler_message;//消息列表
    private MessageAdapter messageAdapter;//adapter
    private List<MessageListBean.PageArrayBean> messageBeans;//消息beans
    // tab
    private TextView tabLeft, tabRight;
    private boolean isLeft = true;
    private int pageIndex = 1;//页码
    // loading
    private LoadingDialog loadingDialog;
    //footer view
    private View footer;
    // empty layout
    private LinearLayout layout_empty;

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        refreshLayout = view.findViewById(R.id.layout_refresh);
        layout_empty = view.findViewById(R.id.layout_empty);
        recycler_message = view.findViewById(R.id.recycler_message);
        tabLeft = view.findViewById(R.id.home_text_tab_left);
        tabRight = view.findViewById(R.id.home_text_tab_right);
        footer = getLayoutInflater().inflate(R.layout.view_recycler_fooder, null);
    }

    @Override
    public void doBusiness() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setMessage("努力加载...");
        loadingDialog.setSpinnerType(0);
        refreshLayout.setEnableLoadMore(true);//禁止底部加载
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setOnRefreshListener(refreshLayout -> { //刷新
            setTabState(isLeft);
        });
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()));
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {//加载更多
            pageIndex++;
            if (isLeft) {
                getMessageListTask("1", pageIndex + "");
            } else {
                getMessageListTask("2", pageIndex + "");
            }

        });

        messageBeans = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageBeans, R.layout.item_recycler_message);
        recycler_message.setAdapter(messageAdapter);
        recycler_message.setLayoutManager(new LinearLayoutManager(getActivity()));
        setTabBar();
        //注册通知
        NotifyListenerMangager.getInstance().registerListener(this, TAG);
        //刷新下列表
        setTabState(isLeft);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (MainActivity.getInstance().getCurrentIndex() == 2)
//            setTabState(isLeft);
    }

    //设置Tab按钮
    private void setTabBar() {
        tabLeft.setText("待办任务");
        tabRight.setText("已办任务");
        tabLeft.setOnClickListener(v -> setTabState(true));
        tabRight.setOnClickListener(v -> setTabState(false));
        // 默认点待办任务
        tabLeft.performClick();
    }

    //设置tab 状态
    private void setTabState(boolean isLeft) {
        int white = ContextCompat.getColor(getActivity(), R.color.white_2);
        int blue = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        messageBeans.clear();
        messageAdapter.setData(messageBeans);
        messageAdapter.notifyDataSetChanged();
        this.isLeft = isLeft;
        this.pageIndex = 1;
        if (isLeft) { //left
            getMessageListTask("1", pageIndex + "");
            tabLeft.setTextColor(white);
            tabRight.setTextColor(blue);
            tabLeft.setBackgroundResource(R.drawable.shape_tab_left_select);
            tabRight.setBackgroundResource(R.drawable.shape_tab_right_normal);
        } else { // right
            getMessageListTask("2", pageIndex + "");
            tabLeft.setTextColor(blue);
            tabRight.setTextColor(white);
            tabLeft.setBackgroundResource(R.drawable.shape_tab_left_normal);
            tabRight.setBackgroundResource(R.drawable.shape_tab_right_select);
        }

    }


    //获取消息列表
    private void getMessageListTask(String status, String page) {
        loadingDialog.show();
        messageAdapter.removeFooterView();
        String userId = SPUtils.getInstance().getString(Constant.KEY_USER_ID);
        RequestParams params = new RequestParams(Constant.URL_MESSAGE_LIST);
        params.addHeader("token", token);
        params.addBodyParameter("status", status); //查询不同的状态：1 未执行；2 已执行
        params.addBodyParameter("id", userId); //当前登录人的id
        params.addBodyParameter("count", "10"); //每页条数
        params.addBodyParameter("page", page); //页码
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logger.json(result);
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    MessageListBean bean = gson.fromJson(result, MessageListBean.class);
                    if (bean.getCode() == 1) {
                        if (bean.getPageCount() < 10 * pageIndex) {
                            refreshLayout.setEnableLoadMore(false);//加载到底部了
                            if (bean.getPageCount() > 0)
                                messageAdapter.setFooterView(footer);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                            messageAdapter.removeFooterView();
                        }
                        if (bean.getPageCount() > 0) {
                            layout_empty.setVisibility(View.GONE);
                            refreshLayout.setVisibility(View.VISIBLE);
                            messageBeans.addAll(bean.getPageArray());
                            messageAdapter.setData(messageBeans);
                            messageAdapter.notifyDataSetChanged();
                        } else {
                            layout_empty.setVisibility(View.VISIBLE);
                            refreshLayout.setVisibility(View.GONE);
                        }
                    } else if (bean.getCode() == -2) {
                        ToastUtils.showShort(Constant.TOKEN_LOST);
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
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                loadingDialog.dismiss();
            }
        });

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public boolean onBackClick() {
        return false;
    }

    @Override
    public void notifyContext(Object obj) {
        //通知刷新列表
        if (obj == ONREFRESH) {
            setTabState(isLeft);
        }
    }

    @Override
    public void onDestroy() {
        NotifyListenerMangager.getInstance().unRegisterListener(this);
        super.onDestroy();
    }
}
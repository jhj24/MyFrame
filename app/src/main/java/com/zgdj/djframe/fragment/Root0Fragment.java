package com.zgdj.djframe.fragment;

import android.Manifest;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.other.FingerprintActivity;
import com.zgdj.djframe.activity.other.PaletteActivity;
import com.zgdj.djframe.activity.other.ScanActivity;
import com.zgdj.djframe.activity.work.SupervisionLogActivity;
import com.zgdj.djframe.adapter.ContentAdapter;
import com.zgdj.djframe.adapter.HomeWorkAdapter;
import com.zgdj.djframe.base.BaseFragment;
import com.zgdj.djframe.bean.BarChartBean;
import com.zgdj.djframe.bean.BarChartMonthBean;
import com.zgdj.djframe.bean.ContentEntity;
import com.zgdj.djframe.bean.LineChartBean;
import com.zgdj.djframe.bean.LineChartDatasBean;
import com.zgdj.djframe.bean.LineChartYearBean;
import com.zgdj.djframe.bean.ProjectProgressBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.FragmentUtils;
import com.zgdj.djframe.utils.GlideImageLoader;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.MPermissionUtils;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.utils.Utils;
import com.zgdj.djframe.view.ListViewDialog;
import com.zgdj.djframe.view.ScrollChangeScrollView;
import com.zgdj.djframe.view.SearchView;
import com.zgdj.djframe.view.chart.BarChart02View;
import com.zgdj.djframe.view.chart.LineChart01View;
import com.zgdj.djframe.view.gallery.GalleryRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * description: 首页
 * author: Created by ShuaiQi_Zhang on 2018/4/19
 * version: 1.0
 */
public class Root0Fragment extends BaseFragment implements FragmentUtils.OnBackClickListener,
        GalleryRecyclerView.OnItemClickListener {

    private Banner banner;//轮播图控件
    private List<Integer> images; //图片集合
    private List<String> titles;//标题集合
    private RefreshLayout layout_refresh;//刷新控件
    private SearchView search_view;//搜索框
    private LinearLayout layout_main_search_bottom;//搜索栏layout底布局
    private RelativeLayout layout_main_search;// 搜索栏layout
    private ScrollChangeScrollView layout_main_scroll;//大佬layout
    private LinearLayout layout_main_view;//二级layout

    public static Root0Fragment newInstance() {
        Bundle args = new Bundle();
        Root0Fragment fragment = new Root0Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_root_0;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        view.findViewById(R.id.img_home_scan).setOnClickListener(this);
        layout_refresh = view.findViewById(R.id.layout_refresh);
        search_view = view.findViewById(R.id.search_view);
        layout_main_search = view.findViewById(R.id.layout_main_search);
        layout_main_search_bottom = view.findViewById(R.id.layout_main_search_bottom);
        layout_main_scroll = view.findViewById(R.id.layout_main_scroll);
        layout_main_view = view.findViewById(R.id.layout_main_view);
        banner = view.findViewById(R.id.banner);
        vp_work = view.findViewById(R.id.vp_work);
        layoutWork = view.findViewById(R.id.layout_work_dot);
        layoutHomeTab_1 = view.findViewById(R.id.layout_home_tab_1);
        textLeft_1 = layoutHomeTab_1.findViewById(R.id.home_text_tab_left);
        textRight_1 = layoutHomeTab_1.findViewById(R.id.home_text_tab_right);
        layoutHomeTab = view.findViewById(R.id.layout_home_tab);
        textLeft = layoutHomeTab.findViewById(R.id.home_text_tab_left);
        textRight = layoutHomeTab.findViewById(R.id.home_text_tab_right);
        layout_project_progress = view.findViewById(R.id.layout_project_progress);
        layout_project_progress_frame = view.findViewById(R.id.layout_project_progress_frame);

    }

    @Override
    public void doBusiness() {
        //搞事情-
        initRefreshLayout();
        intViewPager();
        setSearchBar();
        setProgressChart();
        setBannerData();
    }

    private int layoutSearchBarHeight;//搜索栏高度
    private int layoutScrollHeight;//ScrollView高度
    private int layoutChartTabHeight;//图表Tab高度
    private int recordChartTabHeight;//记录tab显示出来的高度

    private LinearLayout layoutHomeTab_1;
    private LinearLayout layoutHomeTab;
    private TextView textLeft, textLeft_1;
    private TextView textRight, textRight_1;

    //设置搜索框 - 透明度渐变 && 报表Tab
    private void setSearchBar() {

        GradientDrawable layoutGrad = (GradientDrawable) layout_main_search.getBackground();
        GradientDrawable searchGrad = (GradientDrawable) search_view.getBackground();
        //测量layout 高度
        layout_main_search_bottom.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= 16) {
                            layout_main_search_bottom.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            layout_main_search_bottom.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                        layoutSearchBarHeight = layout_main_search_bottom.getHeight(); // 获取高度
                    }
                });
        //测量 ScrollView 高度
        layout_main_view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= 16) {
                            layout_main_view.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            layout_main_view.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                        layoutScrollHeight = layout_main_view.getHeight(); // 获取高度
                    }
                });
        //测量 图表Layout 高度
        layout_project_progress.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= 16) {
                            layout_project_progress.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            layout_project_progress.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                        layoutChartTabHeight = layout_project_progress.getHeight(); // 获取高度
                    }
                });

        //设置滑动
        layout_main_scroll.setOnScrollListener(scrollY -> {
            //当滑动的距离 <= searchView 高度的时候,改变背景色的透明度，达到渐变的效果
            if (scrollY <= layoutSearchBarHeight) {
                float scale = (float) layoutSearchBarHeight / 255;
                float alpha = scrollY / scale; //
                // # f8f8f8
                layout_main_search_bottom.setBackgroundColor(Color.argb((int) (alpha),
                        255, 255, 255));//白色
                layoutGrad.setColor(Color.parseColor("#f8f8f8"));
                searchGrad.setColor(Color.parseColor("#f8f8f8"));
            } else {
                layout_main_search_bottom.setBackgroundColor(Color.argb(255,
                        255, 255, 255));//白色
                layoutGrad.setColor(Color.parseColor("#ececec"));
                searchGrad.setColor(Color.parseColor("#ececec"));
                // 滑动悬浮置顶效果
                int tabY = recordChartTabHeight = layoutScrollHeight - layoutChartTabHeight - layoutSearchBarHeight;
                if (scrollY >= tabY) {
                    layoutHomeTab_1.setVisibility(View.VISIBLE);
                } else {
                    layoutHomeTab_1.setVisibility(View.GONE);
                }
            }

        });

    }

    //初始化RefreshLayout
    private void initRefreshLayout() {
        layout_refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent,
                                       int offset, int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);

                if (offset > 0) {//如果下拉值大于0，说明在刷新
                    layout_main_search_bottom.setVisibility(View.GONE);
                } else {
                    layout_main_search_bottom.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                super.onHeaderFinish(header, success);

            }
        });
        layout_refresh.setOnRefreshListener(refreshLayout ->
                layout_refresh.finishRefresh(2000)
        );
        layout_refresh.setEnableRefresh(false);//禁止顶部刷新
        layout_refresh.setEnableLoadMore(false);//禁止底部加载
    }


    /*********************************** Banner start *******************************************/
    //初始化banner相关
    private void initBanner() {
        View v1 = new View(getActivity());
        v1.setBackgroundColor(Color.RED);

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）/**/
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(position -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("key_url", position % 2 == 0 ? Constant.URL_MODEL_ALL : Constant.URL_MODEL_TILT);
//            jumpToInterface(JPushActivity.class);
        });
    }

    //设置测试数据
    private void setBannerData() {
        images = new ArrayList<>();
        titles = new ArrayList<>();
        images.add(R.drawable.banner_01);
        images.add(R.drawable.banner_02);
        images.add(R.drawable.banner_03);
        images.add(R.drawable.banner_04);
        images.add(R.drawable.banner_05);
        titles.add("title 1");
        titles.add("title 2");
        titles.add("title 3");
        titles.add("title 4");
        titles.add("title 5");
        initBanner();
    }

    /*********************************** Banner end  *********************************************/


    /*********************************** 工作 start **********************************************/

    private ViewPager vp_work;
    private List<View> mViewList;
    private HomeWorkAdapter workAdapter;
    private LinearLayout layoutWork;
    private View mPreSelectedBt;
    private String[] titleOne = {"监理日志", "监理指令", "现场图片", "巡检记录",
            "抽检记录", "旁站记录", "安全巡检", "更多"};
    private int[] iconOne = {R.drawable.log, R.drawable.flag, R.drawable.photo, R.drawable.norecord,
            R.drawable.record_choujian, R.drawable.pangzhan, R.drawable.safe, R.drawable.more};
    private ContentAdapter contentAdapter;
    private String[] titleTwo = {"文件管理", "图纸管理", "通讯录"};
    private int[] iconTwo = {R.drawable.file, R.drawable.file_drawing, R.drawable.phone};
    private String[] titleThree = {"质量管控", "质量验评", "工序报工"};
    private int[] iconThree = {R.drawable.icon_5, R.drawable.star, R.drawable.icon_7};

    //设置ViewPager
    private void intViewPager() {
        //pager 1 相关内容
        View rootOne = setPagerRecycler(1, titleOne, iconOne);
        // pager 2相关
        View rootTwo = setPagerRecycler(2, titleTwo, iconTwo);
        // pager 3 相关
        View rootThree = setPagerRecycler(3, titleThree, iconThree);

        mViewList = new ArrayList<>();
        //可以按照需求进行动态创建Layout,这里暂用静态的xml layout
        mViewList.add(rootOne);
        mViewList.add(rootTwo);
        mViewList.add(rootThree);
        workAdapter = new HomeWorkAdapter(mViewList);
        vp_work.setAdapter(workAdapter);
        //添加dot导航
        for (int i = 0; i < mViewList.size(); i++) {
            View bt = new View(getActivity());
            int wh = Utils.dp2px(getActivity(), 10);
            bt.setLayoutParams(new LinearLayout.LayoutParams(wh, wh));
            bt.setBackgroundResource(R.drawable.dot_normal);
            layoutWork.addView(bt);
        }
        vp_work.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mPreSelectedBt != null) {
                    mPreSelectedBt.setBackgroundResource(R.drawable.dot_normal);
                }
                View currentBt = layoutWork.getChildAt(position);
                currentBt.setBackgroundResource(R.drawable.dot_select);
                mPreSelectedBt = currentBt;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    // 设置recycler item
    private View setPagerRecycler(int pagerIndex, String[] titles, int[] imgs) {
        //动态设置Item高度 暂未写
        //   ------- 占位 -------
        View pagerOne = getLayoutInflater().inflate(R.layout.pager_work_1, null);
        RecyclerView recycler_1 = pagerOne.findViewById(R.id.pager_rv_work_1);
        List<ContentEntity> mListOne = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ContentEntity entity = new ContentEntity();
            entity.setTitle(titles[i]);
            entity.setImgRes(imgs[i]);
            mListOne.add(entity);
        }
        contentAdapter = new ContentAdapter(mListOne, R.layout.item_recycler_content);
        recycler_1.setAdapter(contentAdapter);
        recycler_1.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        contentAdapter.setOnItemClickListener((view, position) -> {
            switch (pagerIndex) {
                case 1:
                    switch (position) {
                        case 0://监理日志
                            jumpToInterface(SupervisionLogActivity.class);
                            break;
                        case 1://监理指令
                            jumpToInterface(FingerprintActivity.class);
                            break;
                        case 2://现场图片
                            jumpToInterface(PaletteActivity.class);
                            break;
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        });

        return pagerOne;
    }

    /************************************* 工作 end  ******************************************/


    /***************************** 工程进度 start  *********************************************/
    private RelativeLayout layout_project_progress;
    private FrameLayout layout_project_progress_frame;

    private void setProgressChart() {
        List<ProjectProgressBean> progressBeanList = new ArrayList<>();
        progressBeanList.add(new ProjectProgressBean("验收情况统计", true));
        progressBeanList.add(new ProjectProgressBean("趋势分布图", false));

        textLeft_1.setOnClickListener(v -> textLeft.performClick());
        textLeft.setOnClickListener(v -> setTabState(true));

        textRight_1.setOnClickListener(v -> textRight.performClick());
        textRight.setOnClickListener(v -> setTabState(false));

        textLeft_1.performClick();//默认加载第一个

    }


    //设置tab 状态
    private void setTabState(boolean isLeft) {
        int white = ContextCompat.getColor(getActivity(), R.color.white_2);
        int blue = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        if (isLeft) { //left
            setColumnarChartView();

            textLeft.setTextColor(white);
            textLeft_1.setTextColor(white);
            textRight.setTextColor(blue);
            textRight_1.setTextColor(blue);

            textLeft_1.setBackgroundResource(R.drawable.shape_tab_left_select);
            textLeft.setBackgroundResource(R.drawable.shape_tab_left_select);
            textRight.setBackgroundResource(R.drawable.shape_tab_right_normal);
            textRight_1.setBackgroundResource(R.drawable.shape_tab_right_normal);
        } else { // right
            setLineChartView();

            textLeft.setTextColor(blue);
            textLeft_1.setTextColor(blue);
            textRight.setTextColor(white);
            textRight_1.setTextColor(white);

            textLeft_1.setBackgroundResource(R.drawable.shape_tab_left_normal);
            textLeft.setBackgroundResource(R.drawable.shape_tab_left_normal);
            textRight.setBackgroundResource(R.drawable.shape_tab_right_select);
            textRight_1.setBackgroundResource(R.drawable.shape_tab_right_select);
        }

    }


    /***************************** 工程进度 end  ***********************************************/


    /******************************** 图表 start ************************************************/

    private LineChart01View lineChart01View;
    private List<LineChartBean> lineChartBeans;
    private TextView textYearSelect; //年度查询
    private TextView textTypeSelect; // 标段选择
    private ListViewDialog listViewDialog;//弹框list
    private TextView btnSelect;//查询按钮
    private List<String> dateList;//年份列表
    private List<String> otherList;//选择不同的标段
    private TextView lineChartTitle;//标题
    private List<Integer> lineChartData;//数据
    private int indexYear;//年代下标
    private int indexOther;//标段下标
    private LinearLayout layoutChartType; // 添加标段layout

    //折线图
    private void setLineChartView() {
        //scroll
        if (layoutHomeTab_1.getVisibility() == View.VISIBLE)
            layout_main_scroll.scrollTo(0, recordChartTabHeight);
        // init views
        View view = getLayoutInflater().inflate(R.layout.view_line_chart, null);
        layout_project_progress_frame.removeAllViews();
        layout_project_progress_frame.addView(view);
        listViewDialog = new ListViewDialog(getActivity());
        lineChart01View = view.findViewById(R.id.chart_line_view);
        textYearSelect = view.findViewById(R.id.line_chart_text_year_select);
        textTypeSelect = view.findViewById(R.id.line_chart_text_type_select);
        btnSelect = view.findViewById(R.id.line_chart_btn_ok);
        lineChartTitle = view.findViewById(R.id.chart_title);
        layoutChartType = view.findViewById(R.id.line_chart_layout_type);

        dateList = new ArrayList<>();
        otherList = new ArrayList<>();
        lineChartData = new ArrayList<>();

        textYearSelect.setOnClickListener(v -> {
            if (dateList.size() > 0) {
                listViewDialog.setListData(dateList, 10);
                listViewDialog.show();
            }
        });
        textTypeSelect.setOnClickListener(v -> {
            if (otherList.size() > 0) {
                listViewDialog.setListData(otherList, 11);
                listViewDialog.show();
            }
        });
        listViewDialog.setCallBack((position, code) -> {
            if (code == 10) {//年度查询
                textYearSelect.setText(dateList.get(position) + "年");
                indexYear = position;
            } else if (code == 11) {
                textTypeSelect.setText(otherList.get(position));
                indexOther = position;
            }
        });
        btnSelect.setOnClickListener(v -> { //查询
            if (dateList.size() > 0) {
                getLineChartTask(dateList.get(indexYear));
                lineChartTitle.setText(dateList.get(indexYear) + "年度质量验评趋势分布图");
            }
        });
        //获取年份
        getLineChartYearTask();
    }

    // 获取折线图 -- 年份
    private void getLineChartYearTask() {
        RequestParams params = new RequestParams(Constant.URL_LINE_CHART_YEAR);
        params.addHeader("token", token);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("年份：" + result);
                if (!TextUtils.isEmpty(result)) {
                    dateList.clear();
                    Gson gson = new Gson();
                    LineChartYearBean yearBean = gson.fromJson(result, LineChartYearBean.class);
                    if (yearBean.getCode() == 1) {
                        dateList.addAll(yearBean.getData());
                        //默认第一个
                        textYearSelect.setText(dateList.get(0) + "年");
                        lineChartTitle.setText(dateList.get(0) + "年度质量验评趋势分布图");
                        getLineChartTask(dateList.get(0));
                        textTypeSelect.setText(otherList.get(0));//默认
                    } else if (yearBean.getCode() == -2) {
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

            }
        });
    }

    // 获取折线图信息
    private void getLineChartTask(String year) {
        RequestParams params = new RequestParams(Constant.URL_LINE_CHART_INFO);
        params.addHeader("token", token);
        params.addBodyParameter("year", year);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    otherList.clear();
                    LineChartDatasBean datasBean = gson.fromJson(result, LineChartDatasBean.class);
                    if (datasBean.getCode() == 1) {
                        otherList.add("全部");
                        otherList.addAll(datasBean.getData().getSection());//标段
                        lineChartData = datasBean.getData().getForm_result_result();//
                        lineChartBeans = getLineData(lineChartData, datasBean.getData().getSection());
                        lineChart01View.selectBids(indexOther, lineChartBeans);
                    } else if (datasBean.getCode() == -2) {
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

            }
        });
    }

    //整理数据
    private List<LineChartBean> getLineData(List<Integer> list, List<String> titles) {
        List<LineChartBean> lineBeans = new ArrayList<>();
        int[] colors = {ContextCompat.getColor(getActivity(), R.color.bids_1),
                ContextCompat.getColor(getActivity(), R.color.bids_2),
                ContextCompat.getColor(getActivity(), R.color.bids_3),
                ContextCompat.getColor(getActivity(), R.color.bids_4),
                ContextCompat.getColor(getActivity(), R.color.rainbow_red),
                ContextCompat.getColor(getActivity(), R.color.rainbow_green)};

        Drawable[] dots = {ContextCompat.getDrawable(getActivity(), R.drawable.shape_dot_green),
                ContextCompat.getDrawable(getActivity(), R.drawable.shape_dot_yellow),
                ContextCompat.getDrawable(getActivity(), R.drawable.shape_dot_red),
                ContextCompat.getDrawable(getActivity(), R.drawable.shape_dot_blue),
                ContextCompat.getDrawable(getActivity(), R.drawable.shape_dot_red)};

        layoutChartType.removeAllViews();//清空下view
        for (int i = 0; i < titles.size(); i++) {
            List<Integer> list1 = list.subList(0 + i * 12, 12 + i * 12);
            LinkedList<Double> dataSeries = new LinkedList<>();
            for (int j = 0; j < list1.size(); j++) {
                dataSeries.add(Double.valueOf(list1.get(j)));
            }
            lineBeans.add(new LineChartBean(dataSeries, titles.get(i), colors[i]));
            // 添加标段
            TextView textView = new TextView(getActivity());
            textView.setText(titles.get(i));
            textView.setTextSize(14);
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.new_black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = Utils.dp2px(getActivity(), 20);
            textView.setLayoutParams(params);
            Drawable drawable = dots[i];
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, drawable, null, null);
            layoutChartType.addView(textView);
        }
        return lineBeans;
    }


    private BarChart02View barChart;
    private TextView spinner;
    private List<String> chartMonthList;
    private List<String> chartMonthDesList;
    private TextView barChartTitle;
    private HorizontalScrollView chartLayoutTable;//表格布局layout

    private void setColumnarChartView() { //柱状 & 表格
        //scroll
        if (layoutHomeTab_1.getVisibility() == View.VISIBLE)
            layout_main_scroll.scrollTo(0, recordChartTabHeight);
        // init views
        View view = getLayoutInflater().inflate(R.layout.view_pie_chart, null);
        layout_project_progress_frame.removeAllViews();
        layout_project_progress_frame.addView(view);
        barChartTitle = view.findViewById(R.id.chart_title);
        chartLayoutTable = view.findViewById(R.id.chart_layout_table);
        listViewDialog = new ListViewDialog(getActivity());
        chartMonthList = new ArrayList<>();
        chartMonthDesList = new ArrayList<>();
        spinner = view.findViewById(R.id.chart_text_spinner);
        spinner.setOnClickListener(v -> {
            if (chartMonthList.size() > 0) {
                listViewDialog.setListData(chartMonthList, 30);
                listViewDialog.show();
            }
        });
        getBarChartMonthTask(view);
        barChart = view.findViewById(R.id.mBarChart);
        //回调
        listViewDialog.setCallBack((position, code) -> {
            if (code == 30) {
                spinner.setText(chartMonthList.get(position).replace("-", "年") + "月");//选择时间
                barChartTitle.setText(chartMonthList.get(0).replace("-", "年")
                        + "月单元工程质量验收情况统计");
                getBarAndTableInfoTask(chartMonthDesList.get(position), view);
            }
        });
    }

    //获取月份
    private void getBarChartMonthTask(View view) {
        RequestParams params = new RequestParams(Constant.URL_CHART_GET_MONTH);
        params.addHeader("token", token);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logs.debug("获取月份：" + result);
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    BarChartMonthBean bean = gson.fromJson(result, BarChartMonthBean.class);
                    if (bean.getCode() == 1) {
                        if (bean.getData().size() > 0) {
                            chartMonthList.addAll(bean.getData());
                            spinner.setText(bean.getData().get(0).replace("-", "年") + "月");//选择时间
                            barChartTitle.setText(bean.getData().get(0).replace("-", "年")
                                    + "月单元工程质量验收情况统计");
                            chartMonthDesList.addAll(bean.getMonth());
                            getBarAndTableInfoTask(bean.getMonth().get(0), view);
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

            }
        });

    }

    //获取柱状图和表格信息
    private void getBarAndTableInfoTask(String timeSlot, View view) {
        RequestParams params = new RequestParams(Constant.URL_CHART_AND_TALBE_INFO);
        params.addHeader("token", token);
        params.addBodyParameter("time_slot", timeSlot);//时间段
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("获取柱状图和表格信息:" + result);
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    BarChartBean chartBean = gson.fromJson(result, BarChartBean.class);
                    if (chartBean.getCode() == 1) {
                        int addWidth = (chartBean.getData().getSection().size() - 4) * Utils.dp2px(getContext(), 40);
                        if (addWidth < 0)
                            addWidth = 0;
                        barChart.setAddWidth(addWidth);
                        // 添加label 标签
                        barChart.chartLabels(chartBean.getData().getSection());
                        //添加数据
                        barChart.setChartData(getBarChartData(chartBean.getData()));
                        // 添加表格数据
                        setTableContent(view, chartBean.getData().getSection(), chartBean.getData());
                    } else if (chartBean.getCode() == -2) {
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

            }
        });

    }


    //整理数据 -- 柱状
    private List<List<Double>> getBarChartData(BarChartBean.DataBean bean) {
        List<List<Double>> lists = new ArrayList<>();
        List<Double> aList = new LinkedList<>(); // 总计
        List<Double> bList = new LinkedList<>(); // 合格
        List<Double> cList = new LinkedList<>(); // 优良
        List<BarChartBean.DataBean.FormResultResultBean> dataBeans = bean.getForm_result_result();
        for (int i = 0; i < dataBeans.size(); i++) {
            BarChartBean.DataBean.FormResultResultBean.SectionRateNumberBean numberBean = dataBeans.get(i).getSection_rate_number();
            aList.add((double) numberBean.getTotal());
            bList.add((double) numberBean.getQualified_number());
            cList.add((double) numberBean.getExcellent_number());
        }
        lists.add(aList);
        lists.add(bList);
        lists.add(cList);
        return lists;
    }

    //设置表格数据
    private void setTableContent(View view, List<String> titles, BarChartBean.DataBean bean) {
        TableRow tableRow1 = view.findViewById(R.id.table_title_content);//标题
        TableRow tableRow2 = view.findViewById(R.id.table_title_content2);//合格列表
        TableRow tableRow3 = view.findViewById(R.id.table_title_content3);//优良列表
        TableRow tableRow4 = view.findViewById(R.id.table_title_content4);//验收列表
        TableRow tableRow5 = view.findViewById(R.id.table_title_content5);//验收列表

        List<BarChartBean.DataBean.FormResultResultBean> dataBeans = bean.getForm_result_result();
        if (tableRow1.getChildCount() > 2) { //直接使用
            for (int i = 0; i < tableRow1.getChildCount(); i++) { //从1开始
                TextView content2 = (TextView) tableRow2.getChildAt(i + 1);
                TextView content3 = (TextView) tableRow3.getChildAt(i + 1);
                TextView content4 = (TextView) tableRow4.getChildAt(i + 1);
                TextView content5 = (TextView) tableRow5.getChildAt(i + 1);
                content2.setText(dataBeans.get(i).getSection_rate_number().getQualified_number() + "");
                content3.setText(dataBeans.get(i).getSection_rate_number().getExcellent_number() + "");
                content4.setText(dataBeans.get(i).getSection_rate_number().getTotal() + "");
                content5.setText(dataBeans.get(i).getExcellent() + "%");
                Logs.debug("i:" + i);
                Logs.debug("text1:" + dataBeans.get(i).getSection_rate_number().getQualified_number());
                Logs.debug("text2:" + dataBeans.get(i).getSection_rate_number().getExcellent_number());
                Logs.debug("text3:" + dataBeans.get(i).getSection_rate_number().getTotal());
                Logs.debug("text4:" + dataBeans.get(i).getExcellent() + "%");

            }
        } else { //创建子类
            int padding = Utils.dp2px(getActivity(), 8);
            for (int i = 0; i < titles.size(); i++) {
                // 标题
                TextView title = new TextView(getActivity());
                title.setText(titles.get(i));
                title.setTextColor(Color.WHITE);
                title.setTextSize(12);
                title.setGravity(Gravity.CENTER);
                title.setSingleLine();
                title.setPadding(0, padding, 0, padding);
                title.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                // 第一列
                TextView content2 = new TextView(getActivity());
                content2.setText(dataBeans.get(i).getSection_rate_number().getQualified_number() + "");
                content2.setTextColor(ContextCompat.getColor(getActivity(), R.color.new_black));
                content2.setTextSize(12);
                content2.setGravity(Gravity.CENTER);
                content2.setPadding(0, padding, 0, padding);
                content2.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.line_edit));

                // 第二列
                TextView content3 = new TextView(getActivity());
                content3.setText(dataBeans.get(i).getSection_rate_number().getExcellent_number() + "");
                content3.setTextColor(ContextCompat.getColor(getActivity(), R.color.new_black));
                content3.setTextSize(12);
                content3.setGravity(Gravity.CENTER);
                content3.setPadding(0, padding, 0, padding);
                content3.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.line_edit));

                // 第三列
                TextView content4 = new TextView(getActivity());
                content4.setText(dataBeans.get(i).getSection_rate_number().getTotal() + "");
                content4.setTextColor(ContextCompat.getColor(getActivity(), R.color.new_black));
                content4.setTextSize(12);
                content4.setGravity(Gravity.CENTER);
                content4.setPadding(0, padding, 0, padding);
                content4.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.line_edit));

                // 第四列
                TextView content5 = new TextView(getActivity());
                content5.setText(dataBeans.get(i).getExcellent() + "%");
                content5.setTextColor(ContextCompat.getColor(getActivity(), R.color.new_black));
                content5.setTextSize(12);
                content5.setGravity(Gravity.CENTER);
                content5.setPadding(0, padding, 0, padding);
                content5.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.line_edit));


                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.width = Utils.dp2px(getActivity(), 80);
                params.height = TableRow.LayoutParams.WRAP_CONTENT;

                title.setLayoutParams(params);
                content2.setLayoutParams(params);
                content3.setLayoutParams(params);
                content4.setLayoutParams(params);
                content5.setLayoutParams(params);

                tableRow1.addView(title);//
                tableRow2.addView(content2);//
                tableRow3.addView(content3);//
                tableRow4.addView(content4);//
                tableRow5.addView(content5);//

            }
        }
    }


    /******************************** 图表 end  ************************************************/


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) { //可见
            //开始轮播
            banner.startAutoPlay();
        } else { //不可见
            //结束轮播
            banner.stopAutoPlay();
        }
    }


    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.img_home_scan: //扫一扫
                checkPermission();
                break;
        }

    }

    @Override
    public boolean onBackClick() {
        return false;
    }

    //检查权限
    private void checkPermission() {
        String[] permissions = {
                Manifest.permission.CAMERA //相机权限
        };
        MPermissionUtils.requestPermissionsResult(Root0Fragment.this, 2, permissions,
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        jumpToInterface(ScanActivity.class);
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtils.showShort("扫描二维码需要打开相机和闪光灯的权限");
                    }
                });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onItemClick(View view, int position) {//画廊点击事件

    }
}

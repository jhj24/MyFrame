package com.zgdj.djframe.activity.work;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.ModelRealAdapter;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.bean.ModelRealBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 实景模型
 */
public class ModelRealActivity extends BaseNormalActivity {

    private RecyclerView recyclerView;
    private ModelRealAdapter adapter;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_model_real;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        recyclerView = view.findViewById(R.id.model_real_recycler);

    }

    @Override
    public void doBusiness() {
        setTitle("实景模型");
        adapter = new ModelRealAdapter(getData(), R.layout.item_recycler_model_real, "real");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private List<ModelRealBean> getData() {
        List<ModelRealBean> list = new ArrayList<>();
        // 拦砂坝
        ModelRealBean bean = new ModelRealBean();
        bean.setTitle("拦砂坝");
        List<ModelRealBean.ChildrenBean> list0 = new ArrayList<>();
        list0.add(new ModelRealBean.ChildrenBean("2017/4/19", "", "http://183.196.236.186:1333/2017/lanshaba0419/APP", R.drawable.lansha1));
        list0.add(new ModelRealBean.ChildrenBean("2017/8/1", "", "http://183.196.236.186:1333/2017/lanshaba0801/APP", R.drawable.lansha2));
        list0.add(new ModelRealBean.ChildrenBean("2017/11/8", "", "http://183.196.236.186:1333/2017/lanshaba1108/APP/#%2F", R.drawable.lansha3));
        list0.add(new ModelRealBean.ChildrenBean("2017/4/12", "", "http://183.196.236.186:1333/2018/lanshaba0412/app/#%2F", R.drawable.lansha4));
        list0.add(new ModelRealBean.ChildrenBean("2017/5/7", "", "http://183.196.236.186:1333/2018/lanshaba-180507/3MX/APP", R.drawable.lansha5));
        list0.add(new ModelRealBean.ChildrenBean("2018/6/15", "", "http://183.196.236.186:1333/2018/lanshaba-180615/App", R.drawable.lansha6));
        list0.add(new ModelRealBean.ChildrenBean("2018/9/28", "", "http://183.196.236.186:1333/2018/lanshaba-20180928/App/#%2F", R.drawable.lansha7));
        list0.add(new ModelRealBean.ChildrenBean("2019/5/14", "", "http://183.196.236.186:1333/2019/lanshaba-20190514/APP", R.drawable.lansha8));
        list0.add(new ModelRealBean.ChildrenBean("2019/6/28", "", "http://183.196.236.186:1333/2019/lanshaba-20190628/APP", R.drawable.lansha9));
        bean.setList(list0);
        // 业主营地
        ModelRealBean bean1 = new ModelRealBean();
        bean1.setTitle("业主营地");
        List<ModelRealBean.ChildrenBean> list1 = new ArrayList<>();
        list1.add(new ModelRealBean.ChildrenBean("2017/4/15", "", "http://183.196.236.186:1333/2017/yezhuyingdi0415/APP", R.drawable.yezhu1));
        list1.add(new ModelRealBean.ChildrenBean("2017/8/3", "", "http://183.196.236.186:1333/2017/yezhuyingdi0803/APP", R.drawable.yezhu2));
        list1.add(new ModelRealBean.ChildrenBean("2018/4/1", "", "http://183.196.236.186:1333/2018/yezhuyingdi0401/app/#%2F", R.drawable.yezhu3));
        list1.add(new ModelRealBean.ChildrenBean("2018/5/4", "", "http://183.196.236.186:1333/2018/yezhuyingdi-180504/3MX/APP", R.drawable.yezhu4));
        list1.add(new ModelRealBean.ChildrenBean("2018/6/5", "", "http://183.196.236.186:1333/2018/yezhuyingdi-180605/APP", R.drawable.yezhu5));
        list1.add(new ModelRealBean.ChildrenBean("2018/9/3", "", "http://183.196.236.186:1333/2018/yezhuyingdi-20180903/App/#%2F", R.drawable.yezhu6));
        list1.add(new ModelRealBean.ChildrenBean("2018/10/14", "", "http://183.196.236.186:1333/2018/yezhuyingdi-20181014/App/#%2F", R.drawable.yezhu7));
        list1.add(new ModelRealBean.ChildrenBean("2019/5/13", "", "http://183.196.236.186:1333/2019/yezhuyingdi20190513/APP", R.drawable.yezhu8));
        list1.add(new ModelRealBean.ChildrenBean("2019/6/19", "", "http://183.196.236.186:1333/2019/yezhuyingdi-20190619/APP", R.drawable.yezhu9));
        list1.add(new ModelRealBean.ChildrenBean("2019/7/15", "", "http://183.196.236.186:1333/2019/yezhuyingdi-20190715/APP", R.drawable.yezhu10));
        bean1.setList(list1);
        // 拦河坝
        ModelRealBean bean2 = new ModelRealBean();
        bean2.setTitle("拦河坝");
        List<ModelRealBean.ChildrenBean> list2 = new ArrayList<>();
        list2.add(new ModelRealBean.ChildrenBean("2017/8/9", "", "http://183.196.236.186:1333/2017/lanheba0809/APP", R.drawable.lanhe1));
        list2.add(new ModelRealBean.ChildrenBean("2017/11/8", "", "http://183.196.236.186:1333/2017/lanheba1108/APP/#%2F", R.drawable.lanhe2));
        list2.add(new ModelRealBean.ChildrenBean("2018/4/12", "", "http://183.196.236.186:1333/2018/lanheba0412/app/#%2F", R.drawable.lanhe3));
        list2.add(new ModelRealBean.ChildrenBean("2018/5/6", "", "http://183.196.236.186:1333/2018/lanheba-180506/3MX/APP", R.drawable.lanhe4));
        list2.add(new ModelRealBean.ChildrenBean("2018/6/12", "", "http://183.196.236.186:1333/2018/lanheba-180612/App", R.drawable.lanhe5));
        list2.add(new ModelRealBean.ChildrenBean("2018/9/28", "", "http://183.196.236.186:1333/2018/lanheba-20180928/App/#%2F", R.drawable.lanhe6));
        list2.add(new ModelRealBean.ChildrenBean("2019/6/27", "", "http://183.196.236.186:1333/2019/lanheba-20190627/APP", R.drawable.lanhe7));
        list2.add(new ModelRealBean.ChildrenBean("2019/7/24", "", "http://183.196.236.186:1333/2019/lanheba-20190724/APP", R.drawable.lanhe8));
        bean2.setList(list2);

        // 下水库进出水口
        ModelRealBean bean3 = new ModelRealBean();
        bean3.setTitle("下水库进出水口");
        List<ModelRealBean.ChildrenBean> list3 = new ArrayList<>();
        list3.add(new ModelRealBean.ChildrenBean("2017/4/15", "", "http://183.196.236.186:1333/2017/xskjcsk0415/APP", R.drawable.xiashui1));
        list3.add(new ModelRealBean.ChildrenBean("2017/11/12", "", "http://183.196.236.186:1333/2017/xiashuikujinchushuikou1112/APP/#%2F", R.drawable.xiashui3));
        list3.add(new ModelRealBean.ChildrenBean("2018/4/2", "", "http://183.196.236.186:1333/2018/xiashuikujinchushuikou0402/app/#%2F", R.drawable.xiashui2));
        list3.add(new ModelRealBean.ChildrenBean("2018/5/8", "", "http://183.196.236.186:1333/2018/xiashuikujinchushuikou-180508/3MX/APP", R.drawable.xiashui4));
        list3.add(new ModelRealBean.ChildrenBean("2018/6/6", "", "http://183.196.236.186:1333/2018/xiashuikujinchushuikou-180606/App", R.drawable.xiashui5));
        list3.add(new ModelRealBean.ChildrenBean("2018/10/18", "", "http://183.196.236.186:1333/2018/xiashuiku20181018/App/#%2F", R.drawable.xiashui6));
        list3.add(new ModelRealBean.ChildrenBean("2019/6/25", "", "http://183.196.236.186:1333/2019/xiashuikujinchushuikou-20190625/APP", R.drawable.xiashui7));
        list3.add(new ModelRealBean.ChildrenBean("2019/7/18", "", "http://183.196.236.186:1333/2019/xiashuikujinchushuikou-20190718/APP", R.drawable.xiashui8));
        bean3.setList(list3);

        // 开关站
        ModelRealBean bean4 = new ModelRealBean();
        bean4.setTitle("开关站");
        List<ModelRealBean.ChildrenBean> list4 = new ArrayList<>();
        list4.add(new ModelRealBean.ChildrenBean("2017/4/18", "", "http://183.196.236.186:1333/2017/kaiguanzhan0418/APP", R.drawable.kaiguan1));
        list4.add(new ModelRealBean.ChildrenBean("2017/8/9", "", "http://183.196.236.186:1333/2017/kaiguanzhan0809/APP/#%2F", R.drawable.kaiguan2));
        list4.add(new ModelRealBean.ChildrenBean("2018/4/11", "", "http://183.196.236.186:1333/2018/kaiguanzhan0411/app/#%2F", R.drawable.kaiguan3));
        list4.add(new ModelRealBean.ChildrenBean("2018/5/10", "", "http://183.196.236.186:1333/2018/kaiguanzhan-180510/3MX/APP", R.drawable.kaiguan4));
        list4.add(new ModelRealBean.ChildrenBean("2018/6/21", "", "http://183.196.236.186:1333/2018/kaiguanzhan-180621/App", R.drawable.kaiguan5));
        list4.add(new ModelRealBean.ChildrenBean("2018/10/8", "", "http://183.196.236.186:1333/2018/kaiguanzhan-20181008/App/#%2F", R.drawable.kaiguan6));
        list4.add(new ModelRealBean.ChildrenBean("2019/6/25", "", "http://183.196.236.186:1333/2019/kaiguanzhan-20190625/APP", R.drawable.kaiguan7));
        list4.add(new ModelRealBean.ChildrenBean("2019/7/18", "", "http://183.196.236.186:1333/2019/kaiguanzhan-20190718/APP", R.drawable.kaiguan8));
        bean4.setList(list4);

        // 调压井平台
        ModelRealBean bean5 = new ModelRealBean();
        bean5.setTitle("调压井平台");
        List<ModelRealBean.ChildrenBean> list5 = new ArrayList<>();
        list5.add(new ModelRealBean.ChildrenBean("2017/5/14", "", "http://183.196.236.186:1333/2017/tiaotajingpingtai0514/APP", R.drawable.tiaoyajing1));
        list5.add(new ModelRealBean.ChildrenBean("2017/8/10", "", "http://183.196.236.186:1333/2017/tiaoyajing0809/APP", R.drawable.tiaoyajing2));
        list5.add(new ModelRealBean.ChildrenBean("2018/4/9", "", "http://183.196.236.186:1333/2018/tiaoyajing0409/app/#%2F", R.drawable.tiaoyajing3));
        list5.add(new ModelRealBean.ChildrenBean("2018/5/8", "", "http://183.196.236.186:1333/2018/tiaoyajing-180508/3MX/APP", R.drawable.tiaoyajing4));
        list5.add(new ModelRealBean.ChildrenBean("2018/6/12", "", "http://183.196.236.186:1333/2018/tiaoyajingpingtai-180612/App", R.drawable.tiaoyajing6));
        list5.add(new ModelRealBean.ChildrenBean("2019/7/2", "", "http://183.196.236.186:1333/2019/yinshuitiaoyajing-20190702/APP", R.drawable.tiaoyajing7));
        list5.add(new ModelRealBean.ChildrenBean("2019/7/26", "", "http://183.196.236.186:1333/2019/yinshuitiaoyajing-20190726/APP", R.drawable.tiaoyajing8));
        bean5.setList(list5);

        // 上库大坝
        ModelRealBean bean6 = new ModelRealBean();
        bean6.setTitle("上库大坝");
        List<ModelRealBean.ChildrenBean> list6 = new ArrayList<>();
        list6.add(new ModelRealBean.ChildrenBean("2017/4/16", "0.2", "http://183.196.236.186:1333/2017/sskdb0416/APP", R.drawable.shangkudaba1));
        list6.add(new ModelRealBean.ChildrenBean("2017/8/10", "0.2", "http://183.196.236.186:1333/2017/shangkudaba0809/APP", R.drawable.shangkudaba2));
        list6.add(new ModelRealBean.ChildrenBean("2017/11/9", "", "http://183.196.236.186:1333/2017/shangshuiku1109/APP/#%2F", R.drawable.shangkudaba3));
        bean6.setList(list6);
        // 上水库进出水口
        ModelRealBean bean7 = new ModelRealBean();
        bean7.setTitle("上水库进出水口");
        List<ModelRealBean.ChildrenBean> list7 = new ArrayList<>();
        list7.add(new ModelRealBean.ChildrenBean("2017/4/16", "", "http://183.196.236.186:1333/2017/sskjcsk0416/APP", R.drawable.shangshuiku1));
        list7.add(new ModelRealBean.ChildrenBean("2017/11/9", "", "http://183.196.236.186:1333/2017/shangshuiku1109/APP/#%2F", R.drawable.shangkudaba3));
        bean7.setList(list7);

        // 上库大坝及进出水口
        ModelRealBean bean8 = new ModelRealBean();
        bean8.setTitle("上库大坝及进出水口");
        List<ModelRealBean.ChildrenBean> list8 = new ArrayList<>();
        list8.add(new ModelRealBean.ChildrenBean("2018/4/16", "", "http://183.196.236.186:1333/2018/shangshuiku0416/app/#%2F", R.drawable.shangxia1));
        list8.add(new ModelRealBean.ChildrenBean("2018/5/9", "", "http://183.196.236.186:1333/2018/shangshuiku-180509/3MX/APP", R.drawable.shangxia2));
        list8.add(new ModelRealBean.ChildrenBean("2018/6/24", "", "http://183.196.236.186:1333/2018/shangshuiku-180624/App", R.drawable.shangxia3));
        list8.add(new ModelRealBean.ChildrenBean("2019/6/27", "", "http://183.196.236.186:1333/2019/shangshuikudabajijinchushuikou-20190627/APP", R.drawable.shangxia4));
        list8.add(new ModelRealBean.ChildrenBean("2019/7/25", "", "http://183.196.236.186:1333/2019/shangshuikudabajijinchushuikou-20190725/APP", R.drawable.shangxia5));
        bean8.setList(list8);

        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);

        return list;
    }

    @Override
    public void onWidgetClick(View view) {

    }
}

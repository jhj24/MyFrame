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
        adapter = new ModelRealAdapter(getData(), R.layout.item_recycler_model_real);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private List<ModelRealBean> getData() {
        List<ModelRealBean> list = new ArrayList<>();
        // 拦砂坝
        ModelRealBean bean = new ModelRealBean();
        bean.setTitle("拦砂坝");
        List<ModelRealBean.ChildrenBean> list0 = new ArrayList<>();
        list0.add(new ModelRealBean.ChildrenBean("2017/4/19", "0.5", "https://map.bhidi.com/model/2017/lanshaba0419/APP", R.drawable.ic_model_1));
        list0.add(new ModelRealBean.ChildrenBean("2017/8/1", "0.5", "https://map.bhidi.com/model/2017/lanshaba0801/APP", R.drawable.ic_model_2));
        list0.add(new ModelRealBean.ChildrenBean("2018/4/1", "0.5", "https://map.bhidi.com/model/2017/lanshaba0801/APP", R.drawable.ic_model_3));
        list0.add(new ModelRealBean.ChildrenBean("2018/6/15", "", "https://map.bhidi.com/model/2018/lanshaba-180615/App", R.drawable.ic_model_4));
        bean.setList(list0);
        // 业主营地
        ModelRealBean bean1 = new ModelRealBean();
        bean1.setTitle("业主营地");
        List<ModelRealBean.ChildrenBean> list1 = new ArrayList<>();
        list1.add(new ModelRealBean.ChildrenBean("2017/4/15", "0.2", "https://map.bhidi.com/model/2017/yezhuyingdi0415/APP", R.drawable.ic_model_21));
        list1.add(new ModelRealBean.ChildrenBean("2017/8/3", "0.2", "https://map.bhidi.com/model/2017/yezhuyingdi0803/APP", R.drawable.ic_model_22));
        list1.add(new ModelRealBean.ChildrenBean("2018/4/16", "0.4", "https://map.bhidi.com/model/2018/shangshuiku0416/app/#%2F", R.drawable.ic_model_23));
        list1.add(new ModelRealBean.ChildrenBean("2018/6/5", "", "https://map.bhidi.com/model/2018/yezhuyingdi-180605/APP", R.drawable.ic_model_24));
        bean1.setList(list1);
        // 拦河坝
        ModelRealBean bean2 = new ModelRealBean();
        bean2.setTitle("拦河坝");
        List<ModelRealBean.ChildrenBean> list2 = new ArrayList<>();
        list2.add(new ModelRealBean.ChildrenBean("2017/8/9", "0.2", "https://map.bhidi.com/model/2017/lanshaba0801/APP", R.drawable.ic_model_31));
        list2.add(new ModelRealBean.ChildrenBean("2018/4/9", "0.2", "https://map.bhidi.com/model/2018/tiaoyajing0409/app/#%2F", R.drawable.ic_model_32));
        list2.add(new ModelRealBean.ChildrenBean("2018/6/12", "", "https://map.bhidi.com/model/2018/lanheba-180612/App", R.drawable.ic_model_33));
        bean2.setList(list2);

        // 下水库进出水口
        ModelRealBean bean3 = new ModelRealBean();
        bean3.setTitle("下水库进出水口");
        List<ModelRealBean.ChildrenBean> list3 = new ArrayList<>();
        list3.add(new ModelRealBean.ChildrenBean("2017/4/15", "0.2", "https://map.bhidi.com/model/2017/xskjcsk0415/APP", R.drawable.ic_model_41));
        list3.add(new ModelRealBean.ChildrenBean("2018/4/2", "0.2", "https://map.bhidi.com/model/2018/xiashuikujinchushuikou0402/app/#%2F", R.drawable.ic_model_42));
        list3.add(new ModelRealBean.ChildrenBean("2018/6/6", "", "https://map.bhidi.com/model/2018/xiashuikujinchushuikou-180606/App", R.drawable.ic_model_43));
        bean3.setList(list3);

        // 开关站
        ModelRealBean bean4 = new ModelRealBean();
        bean4.setTitle("开关站");
        List<ModelRealBean.ChildrenBean> list4 = new ArrayList<>();
        list4.add(new ModelRealBean.ChildrenBean("2017/4/18", "0.1", "https://map.bhidi.com/model/2017/kaiguanzhan0418/APP", R.drawable.ic_model_51));
        list4.add(new ModelRealBean.ChildrenBean("2017/8/9", "0.1", "https://map.bhidi.com/model/2017/kaiguanzhan0809/APP/#%2F", R.drawable.ic_model_52));
        list4.add(new ModelRealBean.ChildrenBean("2018/4/12", "0.1", "https://map.bhidi.com/model/2018/lanshaba0412/app/#%2F", R.drawable.ic_model_53));
        list4.add(new ModelRealBean.ChildrenBean("2018/6/21", "", "https://map.bhidi.com/model/2018/kaiguanzhan-180621/App", R.drawable.ic_model_54));
        bean4.setList(list4);

        // 调压井平台
        ModelRealBean bean5 = new ModelRealBean();
        bean5.setTitle("调压井平台");
        List<ModelRealBean.ChildrenBean> list5 = new ArrayList<>();
        list5.add(new ModelRealBean.ChildrenBean("2017/5/14", "0.1", "https://map.bhidi.com/model/2017/tiaotajingpingtai0514/APP", R.drawable.ic_model_61));
        list5.add(new ModelRealBean.ChildrenBean("2017/8/10", "0.1", "https://map.bhidi.com/model/2017/tiaoyajing0809/APP", R.drawable.ic_model_62));
        list5.add(new ModelRealBean.ChildrenBean("2018/4/12", "0.1", "https://map.bhidi.com/model/2018/lanheba0412/app/#%2F", R.drawable.ic_model_63));
        list5.add(new ModelRealBean.ChildrenBean("2018/6/12", "", "https://map.bhidi.com/model/2018/tiaoyajingpingtai-180612/App", R.drawable.ic_model_64));
        bean5.setList(list5);

        // 上库大坝
        ModelRealBean bean6 = new ModelRealBean();
        bean6.setTitle("上库大坝");
        List<ModelRealBean.ChildrenBean> list6 = new ArrayList<>();
        list6.add(new ModelRealBean.ChildrenBean("2017/4/16", "0.2", "https://map.bhidi.com/model/2017/sskdb0416/APP", R.drawable.ic_model_71));
        list6.add(new ModelRealBean.ChildrenBean("2017/8/10", "0.2", "https://map.bhidi.com/model/2017/tiaoyajing0809/APP", R.drawable.ic_model_72));
        bean6.setList(list6);
        // 上水库进出水口
        ModelRealBean bean7 = new ModelRealBean();
        bean7.setTitle("上水库进出水口");
        List<ModelRealBean.ChildrenBean> list7 = new ArrayList<>();
        list7.add(new ModelRealBean.ChildrenBean("2017/4/16", "0.2", "https://map.bhidi.com/model/2017/sskjcsk0416/APP", R.drawable.ic_model_81));
        bean7.setList(list7);

        // 上库大坝及进出水口
        ModelRealBean bean8 = new ModelRealBean();
        bean8.setTitle("上库大坝及进出水口");
        List<ModelRealBean.ChildrenBean> list8 = new ArrayList<>();
        list8.add(new ModelRealBean.ChildrenBean("2017/11/9", "0.2", "https://map.bhidi.com/model/2017/shangshuiku1109/APP/#%2F", R.drawable.ic_model_91));
        list8.add(new ModelRealBean.ChildrenBean("2018/4/11", "0.5", "https://map.bhidi.com/model/2018/kaiguanzhan0411/app/#%2F", R.drawable.ic_model_92));
        list8.add(new ModelRealBean.ChildrenBean("2018/6/24", "", "https://map.bhidi.com/model/2018/shangshuiku-180624/App", R.drawable.ic_model_93));
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

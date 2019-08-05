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
 * 全景模型
 */
public class ModelAllActivity extends BaseNormalActivity {
    private RecyclerView recyclerView;
    private ModelRealAdapter adapter;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_model_all;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        recyclerView = view.findViewById(R.id.model_all_recycler);
    }

    @Override
    public void doBusiness() {
        setTitle("全景模型");
        adapter = new ModelRealAdapter(getData(), R.layout.item_recycler_model_real);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scrollToPosition(0);

    }

    private List<ModelRealBean> getData() {
        List<ModelRealBean> list = new ArrayList<>();

        // 业主营地
        ModelRealBean bean1 = new ModelRealBean();
        bean1.setTitle("业主营地");
        List<ModelRealBean.ChildrenBean> list1 = new ArrayList<>();
        list1.add(new ModelRealBean.ChildrenBean("2017年4月", "", "http://720yun.com/t/l53lzgq64zinm65ou6", R.drawable.ic_model_all_1_704));
        list1.add(new ModelRealBean.ChildrenBean("2017年5月", "", "http://720yun.com/t/4d9erg4d9qt75qmzfw", R.drawable.ic_model_all_1_705));
        list1.add(new ModelRealBean.ChildrenBean("2017年8月", "", "http://720yun.com/t/5ylmd9pqrqs4zyj9iw", R.drawable.ic_model_all_1_708));
        list1.add(new ModelRealBean.ChildrenBean("2017年11月", "", "http://720yun.com/t/qqw7r2opwdhjn7r9i9", R.drawable.ic_model_all_1_711));
        list1.add(new ModelRealBean.ChildrenBean("2018年4月", "", "http://720yun.com/t/3myq63xareuyje3ahg", R.drawable.ic_model_all_1_804));
        list1.add(new ModelRealBean.ChildrenBean("2018年5月", "", "http://720yun.com/t/m4r7n2r7o2cxnoz5cd", R.drawable.ic_model_all_1_805));
        list1.add(new ModelRealBean.ChildrenBean("2018年6月", "", "https://720yun.com/t/ga274xg8joh5ag39h9", R.drawable.ic_model_all_1_806));
        list1.add(new ModelRealBean.ChildrenBean("2018年8月", "", "https://720yun.com/t/wla75r9z84hnj387sq", R.drawable.ic_model_all_1_808));
        list1.add(new ModelRealBean.ChildrenBean("2018年9月", "", "https://720yun.com/t/3malrldwrrcy3lmocg", R.drawable.ic_model_all_1_809));
        list1.add(new ModelRealBean.ChildrenBean("2018年10月", "", "https://720yun.com/t/859oon9q92fdj5gmf4", R.drawable.ic_model_all_1_8010));
        list1.add(new ModelRealBean.ChildrenBean("2019年4月", "", "https://720yun.com/t/ayjxw3ygdot6nm8yhj", R.drawable.ic_model_all_1_904));
        list1.add(new ModelRealBean.ChildrenBean("2019年5月", "", "https://720yun.com/t/gynerx97ymizwa8jf9", R.drawable.ic_model_all_1_905));
        list1.add(new ModelRealBean.ChildrenBean("2019年6月", "", "https://720yun.com/t/ozwo49ze9ainjpr2c7", R.drawable.ic_model_all_1_906));
        list1.add(new ModelRealBean.ChildrenBean("2019年7月", "", "https://720yun.com/t/qz6ex8pd6oup5ny2s9", R.drawable.ic_model_all_1_907));
        bean1.setList(list1);

        // 上库大坝及进出水口
        ModelRealBean bean8 = new ModelRealBean();
        bean8.setTitle("上库大坝及进出水口");
        List<ModelRealBean.ChildrenBean> list8 = new ArrayList<>();
        list8.add(new ModelRealBean.ChildrenBean("2017年5月", "", "http://720yun.com/t/z4ploooyraf578w9hr", R.drawable.ic_model_all_2_705));
        list8.add(new ModelRealBean.ChildrenBean("2017年9月", "", "http://720yun.com/t/94owq3ygd5clz6w7i8", R.drawable.ic_model_all_2_709));
        list8.add(new ModelRealBean.ChildrenBean("2017年11月", "", "http://720yun.com/t/pp82j76g2dsayjxruj", R.drawable.ic_model_all_2_711));
        list8.add(new ModelRealBean.ChildrenBean("2018年4月", "", "http://720yun.com/t/ao5nrng335tlmr9zij", R.drawable.ic_model_all_2_804));
        list8.add(new ModelRealBean.ChildrenBean("2018年5月", "", "http://720yun.com/t/jlgnnygw391n3z26uy", R.drawable.ic_model_all_2_805));
        list8.add(new ModelRealBean.ChildrenBean("2018年6月", "", "https://720yun.com/t/nodzn7pxrjuan4jlua", R.drawable.ic_model_all_2_806));
        list8.add(new ModelRealBean.ChildrenBean("2018年8月", "", "https://720yun.com/t/6lgjrgze39tnydm6sj", R.drawable.ic_model_all_2_808));
        list8.add(new ModelRealBean.ChildrenBean("2019年4月", "", "https://720yun.com/t/9ejlyqaeojh9jz4df8", R.drawable.ic_model_all_2_904));
        list8.add(new ModelRealBean.ChildrenBean("2019年6月", "", "https://720yun.com/t/pz2z2x6joahlqyzmij", R.drawable.ic_model_all_2_906));
        bean8.setList(list8);

        // 调压井平台
        ModelRealBean bean5 = new ModelRealBean();
        bean5.setTitle("调压井平台");
        List<ModelRealBean.ChildrenBean> list5 = new ArrayList<>();
        list5.add(new ModelRealBean.ChildrenBean("2017年5月", "", "http://720yun.com/t/5yl3ed2x3qs4zyj9iw", R.drawable.ic_model_all_3_705));
        list5.add(new ModelRealBean.ChildrenBean("2017年9月", "", "http://720yun.com/t/nodqodez9jtan4jlua", R.drawable.ic_model_all_3_709));
        list5.add(new ModelRealBean.ChildrenBean("2017年11月", "", "http://720yun.com/t/o6mw6n52qah7pogyf7", R.drawable.ic_model_all_3_711));
        list5.add(new ModelRealBean.ChildrenBean("2018年4月", "", "http://720yun.com/t/ga27a4nrzmc5ag39h9", R.drawable.ic_model_all_3_804));
        list5.add(new ModelRealBean.ChildrenBean("2018年5月", "", "http://720yun.com/t/2dwpddop42cawy64ux", R.drawable.ic_model_all_3_805));
        list5.add(new ModelRealBean.ChildrenBean("2018年6月", "", "https://720yun.com/t/z4pqzww4wyc578w9hr", R.drawable.ic_model_all_3_806));
        list5.add(new ModelRealBean.ChildrenBean("2018年8月", "", "https://720yun.com/t/x7gonoze59frqlwzh3", R.drawable.ic_model_all_3_808));
        list5.add(new ModelRealBean.ChildrenBean("2019年7月", "", "https://720yun.com/t/3nrmap9d9x0o9j2wtg", R.drawable.ic_model_all_3_907));
        bean5.setList(list5);

        // 下水库进出水口
        ModelRealBean bean3 = new ModelRealBean();
        bean3.setTitle("下水库进出水口");
        List<ModelRealBean.ChildrenBean> list3 = new ArrayList<>();
        list3.add(new ModelRealBean.ChildrenBean("2017年5月", "", "http://720yun.com/t/e2pg2zrrl70d463ztx", R.drawable.ic_model_all_4_705));
        list3.add(new ModelRealBean.ChildrenBean("2017年9月", "", "http://720yun.com/t/x7oq5gpp351r68q9c3", R.drawable.ic_model_all_4_709));
        list3.add(new ModelRealBean.ChildrenBean("2017年11月", "", "http://720yun.com/t/nodqxrg4pjhan4jlua", R.drawable.ic_model_all_4_711));
        list3.add(new ModelRealBean.ChildrenBean("2018年4月", "", "http://720yun.com/t/94owxnjdw5ilz6w7i8", R.drawable.ic_model_all_4_804));
        list3.add(new ModelRealBean.ChildrenBean("2018年5月", "", "http://720yun.com/t/x7oq5m7lx6tr68q9c3", R.drawable.ic_model_all_4_805));
        list3.add(new ModelRealBean.ChildrenBean("2018年6月", "", "https://720yun.com/t/854nzj24wrsdo8j9t4", R.drawable.ic_model_all_4_806));
        list3.add(new ModelRealBean.ChildrenBean("2018年8月", "", "https://720yun.com/t/3m2mm7yo8xuy3lmocg", R.drawable.ic_model_all_4_808));
        list3.add(new ModelRealBean.ChildrenBean("2018年10月", "", "https://720yun.com/t/gazogooderc5342zs9", R.drawable.ic_model_all_4_8010));
        list3.add(new ModelRealBean.ChildrenBean("2019年4月", "", "https://720yun.com/t/9ejlydq55zu9jz4df8", R.drawable.ic_model_all_4_904));
        list3.add(new ModelRealBean.ChildrenBean("2019年6月", "", "https://720yun.com/t/9ew3we6rdzh9jz4df8", R.drawable.ic_model_all_4_906));
        bean3.setList(list3);

        // 拦砂坝
        ModelRealBean bean = new ModelRealBean();
        bean.setTitle("拦砂坝");
        List<ModelRealBean.ChildrenBean> list0 = new ArrayList<>();
        list0.add(new ModelRealBean.ChildrenBean("2017年5月", "", "http://720yun.com/t/wl4qq6pz8l0nqyj9uq", R.drawable.ic_model_all_5_705));
        list0.add(new ModelRealBean.ChildrenBean("2017年8月", "", "http://720yun.com/t/pp822pwd4jhayjxruj", R.drawable.ic_model_all_5_708));
        list0.add(new ModelRealBean.ChildrenBean("2017年11月", "", "http://720yun.com/t/854yy6yz6ecdo8j9t4", R.drawable.ic_model_all_5_711));
        list0.add(new ModelRealBean.ChildrenBean("2018年4月", "", "http://720yun.com/t/e2pgnl3xmgsd463ztx", R.drawable.ic_model_all_5_804));
        list0.add(new ModelRealBean.ChildrenBean("2018年5月", "", "http://720yun.com/t/ga29qexmloh5ag39h9", R.drawable.ic_model_all_5_805));
        list0.add(new ModelRealBean.ChildrenBean("2018年6月", "", "https://720yun.com/t/nod9r6o43juan4jlua", R.drawable.ic_model_all_5_806));
        list0.add(new ModelRealBean.ChildrenBean("2018年8月", "", "https://720yun.com/t/z43pgz3y2yi5w4qmsr", R.drawable.ic_model_all_5_808));
        list0.add(new ModelRealBean.ChildrenBean("2018年9月", "", "https://720yun.com/t/z4rqm97qw4c5w4qmsr", R.drawable.ic_model_all_5_809));
        list0.add(new ModelRealBean.ChildrenBean("2019年5月", "", "https://720yun.com/t/ayjewwq68qh6nm8yhj", R.drawable.ic_model_all_5_905));
        list0.add(new ModelRealBean.ChildrenBean("2019年6月", "", "https://720yun.com/t/43ydmg2ge4s4g5ja1w", R.drawable.ic_model_all_5_906));
        bean.setList(list0);

        // 拦河坝
        ModelRealBean bean2 = new ModelRealBean();
        bean2.setTitle("拦河坝");
        List<ModelRealBean.ChildrenBean> list2 = new ArrayList<>();
        list2.add(new ModelRealBean.ChildrenBean("2017年8月", "", "http://720yun.com/t/5ylmx7r9apu4zyj9iw", R.drawable.ic_model_all_6_708));
        list2.add(new ModelRealBean.ChildrenBean("2017年11月", "", "http://720yun.com/t/z4pje42ymyf578w9hr", R.drawable.ic_model_all_6_711));
        list2.add(new ModelRealBean.ChildrenBean("2018年4月", "", "http://720yun.com/t/nod2r9o6gjhan4jlua", R.drawable.ic_model_all_6_804));
        list2.add(new ModelRealBean.ChildrenBean("2018年5月", "", "http://720yun.com/t/94o87qwm2lslz6w7i8", R.drawable.ic_model_all_6_805));
        list2.add(new ModelRealBean.ChildrenBean("2018年6月", "", "https://720yun.com/t/94opynqxxjulz6w7i8", R.drawable.ic_model_all_6_806));
        list2.add(new ModelRealBean.ChildrenBean("2018年8月", "", "https://720yun.com/t/dq3y6xx9qwf38q62uq", R.drawable.ic_model_all_6_808));
        list2.add(new ModelRealBean.ChildrenBean("2018年9月", "", "https://720yun.com/t/6lm5489oaz0nydm6sj", R.drawable.ic_model_all_6_809));
        list2.add(new ModelRealBean.ChildrenBean("2019年6月", "", "https://720yun.com/t/ly9yp9ae5au2lmwp16", R.drawable.ic_model_all_6_906));
        bean2.setList(list2);


        // 开关站
        ModelRealBean bean4 = new ModelRealBean();
        bean4.setTitle("开关站");
        List<ModelRealBean.ChildrenBean> list4 = new ArrayList<>();
        list4.add(new ModelRealBean.ChildrenBean("2017年8月", "", "http://720yun.com/t/ao5no2oejrclmr9zij", R.drawable.ic_model_all_7_708));
        list4.add(new ModelRealBean.ChildrenBean("2017年9月", "", "http://720yun.com/t/r2yx2qqa2jtx5ol2cw", R.drawable.ic_model_all_7_709));
        list4.add(new ModelRealBean.ChildrenBean("2018年4月", "", "http://720yun.com/t/e2pg233lnq0d463ztx", R.drawable.ic_model_all_7_804));
        list4.add(new ModelRealBean.ChildrenBean("2018年5月", "", "http://720yun.com/t/e2pg2jwlzatd463ztx", R.drawable.ic_model_all_7_805));
        list4.add(new ModelRealBean.ChildrenBean("2018年6月", "", "https://720yun.com/t/jlg399alw3tn3z26uy", R.drawable.ic_model_all_7_806));
        list4.add(new ModelRealBean.ChildrenBean("2018年8月", "", "https://720yun.com/t/qqorzwpwp7hjr6wph9", R.drawable.ic_model_all_7_808));
        list4.add(new ModelRealBean.ChildrenBean("2019年6月", "", "https://720yun.com/t/zzl5lm3zrdsmx7johr", R.drawable.ic_model_all_7_906));
        bean4.setList(list4);

        list.add(bean1);
        list.add(bean8);
        list.add(bean5);
        list.add(bean3);
        list.add(bean);
        list.add(bean2);
        list.add(bean4);

        return list;
    }

    @Override
    public void onWidgetClick(View view) {

    }
}

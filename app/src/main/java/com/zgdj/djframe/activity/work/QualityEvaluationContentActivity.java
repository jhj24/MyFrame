package com.zgdj.djframe.activity.work;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zgdj.djframe.R;
import com.zgdj.djframe.adapter.QualityContentAdapter;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.bean.QualityEvaluationControlContentBean;
import com.zgdj.djframe.bean.QualityEvaluationControlListBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 单元管控 -- 内容页
 */
public class QualityEvaluationContentActivity extends BaseNormalActivity {

    private Spinner spinner;
    private RecyclerView recyclerView;
    private QualityContentAdapter contentAdapter;
    private List<QualityEvaluationControlContentBean.DataBean> beanList;
    private List<String> spinnerTitle;
    private List<String> spinnerId;
    private String id;//上级获取的id
    private String type;//上级获取的type
    private String name;//上级获取的name
    private TextView textResult;//验评结果
    private TextView textDate;//验评日期
    private TextView fileName;//控制点名称
    // loading
    private LoadingDialog loadingDialog;

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getString("QualityId");
            type = bundle.getString("QualityType");
            name = bundle.getString("QualityName");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_quality_evaluation_content;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        spinner = view.findViewById(R.id.quality_content_spinner);
        recyclerView = view.findViewById(R.id.quality_content_recycler);
        textResult = view.findViewById(R.id.quality_content_text_result);
        textDate = view.findViewById(R.id.quality_content_text_date);
        fileName = view.findViewById(R.id.quality_content_text_file_name);
        if (!name.isEmpty())
            fileName.setText(name);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.setMessage("努力加载...");
        loadingDialog.setSpinnerType(0);

        beanList = new ArrayList<>();
        contentAdapter = new QualityContentAdapter(beanList, R.layout.item_recycler_quality_content);
        recyclerView.setAdapter(contentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentAdapter.setOnItemClickListener((view1, position) -> { //点击
            Bundle bundle = new Bundle();
            bundle.putString("UnitId", contentAdapter.getData().get(position).getDivision_id() + "");
            bundle.putString("ControlId", contentAdapter.getData().get(position).getControl_id() + "");
            jumpToInterface(QualityEvaluationContentDetailActivity.class, bundle);
        });
    }

    @Override
    public void doBusiness() {
        setTitle("质量验评");
        getControlPointTask();
        getControlDateTask();

    }

    @Override
    public void onWidgetClick(View view) {

    }


    //获取控制点-请求
    private void getControlPointTask() {
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_COTROL);
//        params.addBodyParameter(Constant.KEY_TOKEN, token);
        params.addHeader("token", token);
        params.addBodyParameter("unit_id", type);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logger.json("控制点", result);
                if (!result.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            setSpinnerData(jsonArray.toString());
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

    //整理spinner 数据
    private void setSpinnerData(String result) {
        if (result != null && !result.isEmpty()) {
            Gson gson = new Gson();
            Type qualityList = new TypeToken<List<QualityEvaluationControlListBean>>() {
            }.getType();
            List<QualityEvaluationControlListBean> list = gson.fromJson(result, qualityList);
            if (list != null && list.size() > 0) {
                spinnerTitle = new ArrayList<>();
                spinnerId = new ArrayList<>();
                spinnerTitle.add("全部");
                spinnerId.add("");
                for (QualityEvaluationControlListBean bean : list) {
                    spinnerTitle.add(bean.getName());
                    spinnerId.add(bean.getId());
                }
                // 为下拉列表定义一个适配器，使用到上面定义的turtleList
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, spinnerTitle);
                // 为适配器设置下拉列表下拉时的菜单样式，有好几种样式，请根据喜好选择
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                // 将适配器添加到下拉列表上
                spinner.setAdapter(adapter);
                // 为下拉框设置事件的响应
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getControlListTask(spinnerId.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Logs.debug("onNothingSelected");
                    }
                });
            } else {
                // 默认加载列表
                getControlListTask("");
                ToastUtils.showShort("控制点无数据...");
            }

        }
    }


    //获取控制列表 - 请求
    private void getControlListTask(String controlId) {
        loadingDialog.show();
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_COTROL_CONTENT);
        params.addHeader("token", token);
        params.addBodyParameter("en_type", type); // 段号类型
        params.addBodyParameter("unit_id", id); // 段号id
        params.addBodyParameter("nm_id", controlId); // 控制点id（选填）
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logger.json("getControlListTask:", result);
                setRecyclerData(result);
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

    //获取验评结果 、验评日期
    //{"msg":"success","evaluateDate":0,"evaluateResult":0}
    //  evaluateResult: 0(未验评)、1（不合格）、2（合格）、3（优良）
    private void getControlDateTask() {
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_CONTENT_DATE);
//        params.addBodyParameter(Constant.KEY_TOKEN, token);
        params.addHeader("token", token);
        params.addBodyParameter("unit_id", id); // 段号id
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Logs.debug("验评日期:" + result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        String msg = jsonObject.getString("msg");
                        if (code == 1) {//成功了
                            String evaluateResult = jsonObject.getString("evaluateResult");
                            String evaluateDate = jsonObject.getString("evaluateDate");
                            switch (evaluateResult) {
                                case "0"://未验评
                                    textResult.setText("验评结果：未验评");//验评结果
                                    break;
                                case "1"://不合格
                                    textResult.setText("验评结果：不合格");//验评结果
                                    break;
                                case "2"://合格
                                    textResult.setText("验评结果：合格");//验评结果
                                    break;
                                case "3"://优良
                                    textResult.setText("验评结果：优良");//验评结果
                                    break;
                            }
                            textDate.setText("验评日期：" + evaluateDate);
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


    //设置列表
    private void setRecyclerData(String result) {
        if (result != null && !result.isEmpty()) {
            Gson gson = new Gson();
            Type qualityList = new TypeToken<QualityEvaluationControlContentBean>() {
            }.getType();
            QualityEvaluationControlContentBean bean = gson.fromJson(result, qualityList);
            if (bean.getCode() == 1) {
                beanList = bean.getData();
                if (beanList != null && beanList.size() > 0) {
                    contentAdapter.setData(beanList);
                    contentAdapter.notifyDataSetChanged();
                } else {
                    contentAdapter.setData(null);
                    contentAdapter.notifyDataSetChanged();
                    ToastUtils.showShort("暂无数据");
                }
            } else if (bean.getCode() == -2) {
                ToastUtils.showShort(Constant.TOKEN_LOST);
            }
        }
    }

}

package com.zgdj.djframe.activity.work;

import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.SPUtils;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * 质量管控
 */
public class QualityControlActivity extends BaseNormalActivity implements QRCodeView.Delegate {

    private QRCodeView mQRCodeView;
    // loading
    private LoadingDialog loadingDialog;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();//打开相机
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();//开始扫描
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_quality_control;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        mQRCodeView = view.findViewById(R.id.zbarview);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.setMessage("努力加载...");
        loadingDialog.setSpinnerType(0);

    }

    @Override
    public void doBusiness() {
        setTitle("质量管控");
        mQRCodeView.setDelegate(this);
    }

    //震动提醒
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Logs.debug("result:" + result);
        if (result.contains("http://")) {//扫描成功了
            vibrate();
            ToastUtils.showShort("扫描成功");
            getFromInfoTask(result);
//            Bundle bundle = new Bundle();
//            bundle.putString("key_url", result);
//            jumpToInterface(WebH5Activity.class);
//            finish();
        } else {
            mQRCodeView.startSpot();
        }
    }


    // 获取表单信息
    private void getFromInfoTask(String url) {
        loadingDialog.show();
        String token = SPUtils.getInstance().getString(Constant.KEY_TOKEN);
        RequestParams params = new RequestParams(url);
        params.addBodyParameter(Constant.KEY_TOKEN, token);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    try {
                        Logger.json("表单信息", result);
                        JSONObject object = new JSONObject(result);
                        if (object.has("basedata")) {//
                            JSONObject dataObject = object.getJSONObject("basedata");
                            String title = dataObject.getString("title");
                            String sectionName = dataObject.getString("sectionName");
                            String dwName = dataObject.getString("dwName");
                            String fbName = dataObject.getString("fbName");
                            String dyName = dataObject.getString("dyName");
                            String unitId = dataObject.getString("unitId");
                            String procedureName = dataObject.getString("procedureName");
                            String cpr_id = dataObject.getString("cpr_id");
                            String status = dataObject.getString("status");

                            List<Entity> entityList = new ArrayList<>();
                            if (object.has("file_path_array")) {//图片
                                JSONArray array = object.getJSONArray("file_path_array");
                                if (array.length() > 0) {
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object1 = array.getJSONObject(i);
                                        Entity entity = new Entity();
                                        entity.setFilepath(object1.getString("filepath"));
                                        entity.setId(object1.getString("id"));
                                        entityList.add(entity);
                                    }
                                }
                            }

                            Logs.debug("title:" + title);
                            Logs.debug("sectionName:" + sectionName);
                            Logs.debug("dwName:" + dwName);
                            Logs.debug("fbName:" + fbName);
                            Logs.debug("dyName:" + dyName);
                            Logs.debug("unitId:" + unitId);
                            Logs.debug("procedureName:" + procedureName);
                            Logs.debug("cpr_id:" + cpr_id);
                            Logs.debug("status:" + status);
                            Logs.debug("entityList:" + entityList.size());

                            Bundle bundle = new Bundle();
                            bundle.putString("title", title);
                            bundle.putString("sectionName", sectionName);
                            bundle.putString("dwName", dwName);
                            bundle.putString("fbName", fbName);
                            bundle.putString("dyName", dyName);
                            bundle.putString("unitId", unitId);
                            bundle.putString("procedureName", procedureName);
                            bundle.putString("cpr_id", cpr_id);
                            bundle.putString("status", status);
                            bundle.putSerializable("entityList", (Serializable) entityList);
                            jumpToInterface(QualityControlWriteActivity.class, bundle);
                            finish();
                        }
                    } catch (Exception e) {
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
                loadingDialog.dismiss();
            }
        });
    }

    // 表单信息 返回图片 - file_path_array
    public static class Entity implements Serializable {
        private String filepath;
        private String id;

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.showShort("打开相机出错!");
    }
}

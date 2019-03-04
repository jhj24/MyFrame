package com.zgdj.djframe.activity.work;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zgdj.djframe.R;
import com.zgdj.djframe.activity.other.PhotoViewActivity;
import com.zgdj.djframe.adapter.GalleryNewAdapter;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.bean.ImageBean;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.CameraUtils;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.MPermissionUtils;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.utils.Utils;
import com.zgdj.djframe.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 填写质量管控信息
 */
public class QualityControlWriteActivity extends BaseNormalActivity implements View.OnLongClickListener {
    private RecyclerView recyclerImple;//控制点执行情况
    private RecyclerView recyclerData;//图像资料
    private CameraUtils cameraUtils;
    private CameraUtils cameraUtils2;

    private GalleryNewAdapter adapter1;
    private GalleryNewAdapter adapter2;

    // 信息
    private TextView formName;//表单名称
    private TextView bdName;//标段名称
    private TextView unitProject;//单位工程
    private TextView fbProject;//分部工程
    private TextView companyProject;//单位工程
    private TextView companyProjectBids;//单位工程标段
    private TextView workProcedure;//工序
    private TextView workStatus;//控制点状态
    // info
    private String title;
    private String sectionName;
    private String dwName;
    private String fbName;
    private String dyName;
    private String unitId;
    private String procedureName;
    private String cpr_id;//控制点id
    private String status;//控制点状态
    private List<QualityControlActivity.Entity> entityList;

    //loading
    private LoadingDialog loadingDialog;


    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            title = bundle.getString("title");
            sectionName = bundle.getString("sectionName");
            dwName = bundle.getString("dwName");
            fbName = bundle.getString("fbName");
            dyName = bundle.getString("dyName");
            unitId = bundle.getString("unitId");
            procedureName = bundle.getString("procedureName");
            cpr_id = bundle.getString("cpr_id");
            status = bundle.getString("status");
            entityList = (List<QualityControlActivity.Entity>) bundle.getSerializable("entityList");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_quality_contol_write;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        recyclerImple = view.findViewById(R.id.quality_recycler_implement);
        recyclerData = view.findViewById(R.id.quality_recycler_data);

        formName = view.findViewById(R.id.quality_control_text_from_name);
        bdName = view.findViewById(R.id.quality_control_text_bd_name);
        unitProject = view.findViewById(R.id.quality_control_text_unit_project);
        fbProject = view.findViewById(R.id.quality_control_text_fb_project);
        companyProject = view.findViewById(R.id.quality_control_text_company_project);
        companyProjectBids = view.findViewById(R.id.quality_control_text_company_project_bids);
        workProcedure = view.findViewById(R.id.quality_control_text_work_procedure);
        workStatus = view.findViewById(R.id.quality_control_text_work_status);
    }

    @Override
    public void doBusiness() {
        setTitle("质量管控表单");
        setRightOnclick("完成", () -> {
            ToastUtils.showShort("提交完成");
            finish();
        });
        cameraUtils = new CameraUtils(this);
        cameraUtils2 = new CameraUtils(this);
        adapter1 = setRecycler(recyclerImple, 1);
        adapter2 = setRecycler(recyclerData, 2);

        // 赋值
        formName.setText(title);
        bdName.setText(sectionName);
        unitProject.setText(dwName);
        fbProject.setText(fbName);
        companyProject.setText(dyName);
        companyProjectBids.setText(unitId);
        workProcedure.setText(procedureName);

        //设置控制点状态
        if (status.equals("1")) {//已执行
            workStatus.setText("已执行");
            Utils.setTextColor(this, workStatus.getText().toString(),
                    workStatus, R.color.qualified, 0, 3);
        } else if (status.equals("0")) {
            workStatus.setText("未执行");
            Utils.setTextColor(this, workStatus.getText().toString(),
                    workStatus, R.color.red, 0, 3);
        }

        // 设置长按监听
        formName.setOnLongClickListener(this);
        bdName.setOnLongClickListener(this);
        unitProject.setOnLongClickListener(this);
        fbProject.setOnLongClickListener(this);
        companyProject.setOnLongClickListener(this);
        companyProjectBids.setOnLongClickListener(this);
        workProcedure.setOnLongClickListener(this);

        //set loading
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setSpinnerType(0);

        //加载图片
        for (QualityControlActivity.Entity entity : entityList) {
            showImg(entity.getFilepath(), adapter2, entity.getId());
        }

    }


    // 设置recycler
    private GalleryNewAdapter setRecycler(RecyclerView recycler, int flag) {
        List<ImageBean> mList = new ArrayList<>();
        mList.add(new ImageBean(R.drawable.icon_add, 1));
        GalleryNewAdapter adapter = new GalleryNewAdapter(mList, R.layout.item_recycler_gallery);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(QualityControlWriteActivity.this,
                LinearLayoutManager.HORIZONTAL, false));
        adapter.setOnItemClickListener((view, position) -> {
            if (position == mList.size() - 1) {//添加图片
                checkPermission(flag);
            } else { //查看图片
                List<ImageBean> imgList = new ArrayList<>(mList);
                imgList.remove(imgList.size() - 1);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoViewActivity.KEY_IMAGE_LIST, (Serializable) imgList);
                bundle.putInt(PhotoViewActivity.KEY_IMAGE_POSITION, position);
                jumpToInterface(PhotoViewActivity.class, bundle);
            }

        });
        return adapter;
    }

    private void addRecyclerData(GalleryNewAdapter adapter, ImageBean bean) {
        List<ImageBean> list = adapter.getDatas(); //当前数据
        list.add(list.size() - 1, bean);
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onWidgetClick(View view) {

    }


    //权限问题
    private void checkPermission(int tag) {
        MPermissionUtils.requestPermissionsResult(this, 3,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (tag == 1) { // 执行点情况
                            cameraUtils.showDialog(10, 11);
                        } else if (tag == 2) { //图像资料
                            cameraUtils2.showDialog(20, 21);
                        }

                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtils.showShort("没有权限，无法使用相机功能！");
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                switch (requestCode) {
                    case 10: //执行情况 -- 拍照
//                        addRecyclerData(adapter1, new ImageBean(3, cameraUtils.getPhotoUri().toString()));
                        uploadImageTask(1, adapter1, cameraUtils.getPhotoFile());
                        break;
                    case 11: //执行情况 -- 相册
                        if (data != null) {
                            Uri uri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
                            int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            String path = cursor.getString(actual_image_column_index);
//                            addRecyclerData(adapter1, new ImageBean(path, 2));
                            uploadImageTask(1, adapter1, new File(path));
                        }
                        break;
                    case 20: //图像资料 -- 拍照
//                        addRecyclerData(adapter2, new ImageBean(3, cameraUtils2.getPhotoUri().toString()));
                        uploadImageTask(4, adapter2, cameraUtils2.getPhotoFile());
                        break;
                    case 21: //图像资料 -- 相册
                        if (data != null) {
                            Uri uri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};
                            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
                            int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            String path = cursor.getString(actual_image_column_index);
//                            addRecyclerData(adapter2, new ImageBean(path, 2));
                            uploadImageTask(4, adapter2, new File(path));
                        }
                        break;


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.quality_control_text_from_name://表单名称
                ToastUtils.showShort(formName.getText());
                break;
            case R.id.quality_control_text_bd_name://标段名称
                ToastUtils.showShort(bdName.getText());
                break;
            case R.id.quality_control_text_unit_project://单位工程
                ToastUtils.showShort(unitProject.getText());
                break;
            case R.id.quality_control_text_fb_project://分部工程
                ToastUtils.showShort(fbProject.getText());
                break;
            case R.id.quality_control_text_company_project://单元工程
                ToastUtils.showShort(companyProject.getText());
                break;
            case R.id.quality_control_text_company_project_bids://单元工程段号
                ToastUtils.showShort(companyProjectBids.getText());
                break;
            case R.id.quality_control_text_work_procedure://工序
                ToastUtils.showShort(workProcedure.getText());
                break;
        }
        return false;
    }

    //图片上传
    // type : 1 拍照扫描 ； 4 图像资料
    private void uploadImageTask(int type, GalleryNewAdapter adapter, File file) {
        loadingDialog.setMessage("上传图片...");
        loadingDialog.show();
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_UPLOAD_SINGE_IMG);
        params.addHeader("token", token);
        params.addBodyParameter("file", file);
        params.addBodyParameter("module", "quality");
        params.addBodyParameter("use", "element");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("图片上传:" + result);
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.has("src")) {//图片路径
                            String src = object.getString("src");
                            if (object.has("id")) {
                                String imgId = object.getString("id");
                                setBindingTask(cpr_id, imgId,
                                        System.currentTimeMillis() + "",
                                        type + "", src, adapter);
                            }
                        } else {
                            ToastUtils.showShort("上传图片失败");
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
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * 图片进行绑定
     *
     * @param cpr_id   ： 控制点id
     * @param att_id   ： 图片id
     * @param filename ： 随便写
     * @param type     :  拍照扫描 1 ； 图像资料 4
     */
    private void setBindingTask(String cpr_id, String att_id, String filename, String type,
                                String src, GalleryNewAdapter adapter) {
        loadingDialog.setMessage("绑定图片...");
        loadingDialog.show();
        RequestParams params = new RequestParams(Constant.URL_QUALITY_EVALUATIOHN_IMAGE_SET_BINDING);
        params.addHeader("token", token);
        params.addBodyParameter("cpr_id", cpr_id);
        params.addBodyParameter("att_id", att_id);
        params.addBodyParameter("filename", filename);
        params.addBodyParameter("type", type);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Logs.debug("图片绑定：" + result);
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.has("code")) {//图片路径
                            String code = object.getString("code");
                            if ("1".equals(code)) {// 成功
                                showImg(src, adapter, att_id);
                            } else if ("-2".equals(code)) {
                                ToastUtils.showShort(Constant.TOKEN_LOST);
                            } else {
                                ToastUtils.showShort("图片绑定失败！");
                            }
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
                loadingDialog.dismiss();
            }
        });

    }


    // 显示图片
    private void showImg(String src, GalleryNewAdapter adapter, String imgId) {
        if (src.contains("\\")) {
            src = src.replace("\\", "/");
            Logs.debug("src:" + Utils.addHttpURL(src));
            ImageBean imageBean = new ImageBean(Utils.addHttpURL(src), 2);
            imageBean.setImgId(imgId);
            //显示图片
            addRecyclerData(adapter, imageBean);
        }

    }


}

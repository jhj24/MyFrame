package com.zgdj.djframe.activity.other;

import android.Manifest;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;

import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.constant.Constant;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.utils.MPermissionUtils;
import com.zgdj.djframe.utils.RegexUtils;
import com.zgdj.djframe.utils.ToastUtils;
import com.zgdj.djframe.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


public class PDFActivity extends BaseNormalActivity implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener {

    private String loadUrl;
    private PDFView pdfView;
    private String fileName;//文件名称
    private String fileType;//文件类型
    private String fileId;//文件ID

    private LoadingDialog loadingDialog;


    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            loadUrl = bundle.getString("key_url");
            fileName = bundle.getString("file_name");
            fileType = bundle.getString("file_type");
            fileId = bundle.getString("file_id");
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web_pdf;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        pdfView = view.findViewById(R.id.pdfView);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setSpinnerType(0);

    }

    @Override
    public void doBusiness() {
        setTitle("查看PDF文档");
        checkPermission();
        //下载按钮
        setRightOnclick("下载", this::downloadPDF);

//        loadUrl = "http://file.chmsp.com.cn/colligate/file/00100000224821.pdf";
//        displayFromFile1(loadUrl, "test.pdf");
    }

    @Override
    public void onWidgetClick(View view) {

    }

    //检查权限
    private void checkPermission() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE //写入权限
        };
        MPermissionUtils.requestPermissionsResult(this, 2, permissions,
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (fileType.equals("pdf")) { //pdf直接查看
                            displayFromFile1(loadUrl, fileName);
                            Logs.debug("loadUrl:" + loadUrl);
                        } else { //转换格式
                            transform2PdfTask();
                        }
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtils.showShort("需要下载PDF到本地！");
                    }
                });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 获取打开网络的pdf文件
     *
     * @param fileUrl
     * @param fileName
     */
    private void displayFromFile1(String fileUrl, String fileName) {
        Logs.debug("读取文档：" + fileUrl);
        if (fileName.contains("\\.")) {
            fileName = fileName.split("\\.")[0] + ".pdf";
        }
        Logs.debug("fileName：" + fileName);
        loadingDialog.setMessage("读取文档...");
        loadingDialog.show();
        pdfView.fileFromLocalStorage(this, this, this, fileUrl, fileName);   //设置pdf文件地址

    }

    /**
     * 下载文件
     */
    private void downloadPDF() {
        Logger.w("loadUrl:" + loadUrl);
        if (RegexUtils.isNull(loadUrl)) {
            ToastUtils.showShort("下载失败");
            return;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fengning" + File.separator;
//            FileUtils.createFileCatalogue(path);
//            path += fileName;
            RequestParams requestParams = new RequestParams(loadUrl);
            // 为RequestParams设置文件下载后的保存路径
            requestParams.setSaveFilePath(Constant.BASE_PATH + File.separator + fileName);
            // 下载完成后自动为文件命名
            requestParams.setAutoRename(false);
            x.http().get(requestParams, new Callback.ProgressCallback<File>() {

                @Override
                public void onSuccess(File result) {
                    Logs.debug("下载成功");
                    ToastUtils.showShort("下载成功  \n  下载路径：" + Constant.BASE_PATH);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Logs.debug("下载失败");
                    ToastUtils.showShort("下载失败");
//                    mProgressDialog.dismiss();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Logs.debug("取消下载");
//                    mProgressDialog.dismiss();
                }

                @Override
                public void onFinished() {
                    Logs.debug("结束下载");
                    loadingDialog.dismiss();
                }

                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {
                    loadingDialog.setMessage("正在下载...");
                    loadingDialog.show();
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    // 当前的下载进度和文件总大小
                    Logs.debug("正在下载中：" + (int) total + ",current:" + current);
                }
            });
        }
    }


    /**
     * 转换成PDF格式
     */
    private void transform2PdfTask() {
        if (RegexUtils.isNull(fileId)) return;
        loadingDialog.setMessage("努力加载...");
        loadingDialog.show();
        RequestParams params = new RequestParams(Constant.URL_TRANSFORM_TO_PDF);
        params.addHeader("token", token);
        params.addBodyParameter("file_id", fileId); //文件id
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                if (!RegexUtils.isNull(result)) {
                    Logs.debug("格式转换：" + result);
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.has("code")) {
                            if (object.getInt("code") == 1) {//成功
                                if (object.has("path")) {
                                    displayFromFile1("http://" + object.getString("path"), fileName);//fileName
                                }
                            }else if (object.getInt("code") == -2) {
                                ToastUtils.showShort(Constant.TOKEN_LOST);
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


    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void loadComplete(int nbPages) {
//        Toast.makeText(this, "加载完成" + nbPages, Toast.LENGTH_SHORT).show();
        loadingDialog.dismiss();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}

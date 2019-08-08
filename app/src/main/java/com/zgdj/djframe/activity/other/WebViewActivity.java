package com.zgdj.djframe.activity.other;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseActivity;
import com.zgdj.djframe.utils.Logs;
import com.zgdj.djframe.view.LoadingDialog;

/**
 * WebView 加载模型（横向）
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;
    private String url;
    private WebSettings mWebSettings;
    private LoadingDialog loadingDialog;
    private String type;

    @Override
    public void initData(Bundle bundle) {
        url = bundle.getString("key_url");
        type = bundle.getString("type");
    }

    @Override
    public int bindLayout() {
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        return R.layout.activity_web_view;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        webView = view.findViewById(R.id.webView);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setSpinnerType(0);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void doBusiness() {
        mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return event.getAction() == MotionEvent.ACTION_UP;
            }
        });

        /*// 特别注意
        // 每个 Application 只调用一次 WebSettings.setAppCachePath() 和   WebSettings.setAppCacheMaxSize()
        String cacheDirPath = this.getFilesDir().getAbsolutePath() + "cache/";
        // 1. 设置缓存路径
        mWebSettings.setAppCachePath(cacheDirPath);
        // 2. 设置缓存大小
        mWebSettings.setAppCacheMaxSize(200 * 1024 * 1024);
        // 3. 开启Application Cache存储机制
        mWebSettings.setAppCacheEnabled(true);*/


        webView.loadUrl(url);
        //设置不用系统浏览器打开,直接显示在当前Webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient() {

            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                Logs.debug("标题在这里：" + title);
//                mtitle.setText(title);
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
//                loading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
//                loading.setText(progress);
                }
            }
        });

        //设置WebViewClient类
        webView.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Logs.debug("开始加载了");
//                beginLoading.setText("开始加载了");
                loadingDialog.show();
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                Logs.debug("加载结束：" + url);
                loadingDialog.dismiss();
//                endLoading.setText("结束加载了");

            }
        });


    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onDestroy() {
        if (webView != null) { //销毁Webview
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }
}

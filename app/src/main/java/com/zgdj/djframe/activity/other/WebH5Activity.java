package com.zgdj.djframe.activity.other;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zgdj.djframe.R;
import com.zgdj.djframe.base.BaseNormalActivity;
import com.zgdj.djframe.utils.Logger;
import com.zgdj.djframe.view.LoadingDialog;

/**
 * 加载H5页面
 */
public class WebH5Activity extends BaseNormalActivity {
    private WebView mWebView;
    private String url;
    private LoadingDialog loadingDialog;
    private static boolean isSupportCookie = false;//是否支持cookie
    private static Context mContext;
    private static String mCookie;

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            url = bundle.getString("key_url");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web_h5;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        mWebView = view.findViewById(R.id.mWebView);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setSpinnerType(0);
    }


    //设置cookie
    public static void synCookies(Context context, String url, String cookie) {

        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();//先清空一下cookie
        cookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
        Logger.i("cookie打印：" + cookieManager.getCookie(url));

        // get
        mContext = context;
        mCookie = cookie;
        //不需要交互了
        setSupportCookies(false);
    }

    //设置是否支持cookie
    private static void setSupportCookies(boolean is) {
        isSupportCookie = is;
    }

    //清理cookie
    private void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void doBusiness() {
        setTitle("网页浏览");
        if (TextUtils.isEmpty(url)) return;
        mWebView.loadUrl(url);
        setDefaultWebSettings(mWebView);
        removeJavascriptInterfaces(mWebView);
        //设置WebViewClient
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Logger.i("shouldOverrideUrlLoading:" + url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialog.dismiss();
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingDialog.show();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if (isSupportCookie) {
                    synCookies(mContext, url, mCookie);
                    Logger.i("加载cookie：" + url);
                }
            }
        });

        //设置WebChromeClient类
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // setTitle(title); //设置标题
                // android 6.0 以下通过title获取
               /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("404") || title.contains("500") || title.contains("Error")) {
                        view.loadUrl("about:blank");// 避免出现默认的错误界面
//                        view.loadUrl(mErrorUrl);
                    }
                }*/
            }


        });
    }

    //默认设置
    public void setDefaultWebSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //允许js代码
        webSettings.setJavaScriptEnabled(true);
        //允许SessionStorage/LocalStorage存储
        webSettings.setDomStorageEnabled(true);
        //禁用放缩
        webSettings.setDisplayZoomControls(true);
        webSettings.setBuiltInZoomControls(true);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //10M缓存，api 18后，系统自动管理。
        webSettings.setAppCacheMaxSize(100 * 1024 * 1024);
        //允许缓存，设置缓存位置
        webSettings.setAppCacheEnabled(false);
        webSettings.setAppCachePath(getDir("appcache", 0).getPath());
        //允许WebView使用File协议
        webSettings.setAllowFileAccess(true);
        //不保存密码
        webSettings.setSavePassword(false);
        //设置UA
//        webSettings.setUserAgentString(webSettings.getUserAgentString() + " kaolaApp/" + AppUtils.getVersionName());
//        //移除部分系统JavaScript接口
//        KaolaWebViewSecurity.removeJavascriptInterfaces(webView);
        //自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
    }


    //JavaScript，务必做好安全措施，防止远程执行漏洞
    @TargetApi(11)
    private static final void removeJavascriptInterfaces(WebView webView) {
        try {
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
    }

    @Override
    public void onWidgetClick(View view) {

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) { //销毁Webview
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
            removeCookie(this);
        }

        super.onDestroy();
    }
}

package com.example.crosswalkdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author LinKun EmployeeID:151750
 * @email : linkun199011@163.com
 * @time : 2016/4/25 11:08
 */
public class AndroidWebViewActivity extends Activity {

    private WebView mWebView;
    private String mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_webview);
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString("mUrl");
        System.out.println(">>> URL is = " + mUrl);
        mWebView = (WebView) findViewById(R.id.xwalkWebView);
        //支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;// true表示此事件在此处被处理，不需要再广播
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 有页面跳转时被回调
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 页面跳转结束后被回调
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // 出错
            }
        });

        mWebView.loadUrl(mUrl);
    }

}

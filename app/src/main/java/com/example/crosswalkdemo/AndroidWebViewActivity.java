package com.example.crosswalkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
        mWebView=(WebView)findViewById(R.id.xwalkWebView);
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

        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }
}

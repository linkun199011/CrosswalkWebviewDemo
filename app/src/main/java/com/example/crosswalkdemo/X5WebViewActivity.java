package com.example.crosswalkdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * @author LinKun EmployeeID:151750
 * @email : linkun199011@163.com
 * @time : 2016/4/27 17:50
 */
public class X5WebViewActivity extends Activity {
    private WebView mWebView;
    private String mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x5_webview);
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString("mUrl");
        System.out.println(">>> URL is = " + mUrl);
        mWebView = (WebView) findViewById(R.id.x5_webview);
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
        mWebView.setWebViewClient(new WebViewClient() {
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

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mWebView.canGoBack()) {
                mWebView.goBack(); //goBack()表示返回WebView的上一页面
                System.out.println("goBack called!!!!!!!");
                return true;
            }
            else { // 如果webview无可回退，则keycode_back交由系统处理
                super.onKeyDown(keyCode, event);
                System.out.println("system onKeyDown called!!!!!!!");
                this.finish();
            }
        }
        return false;
    }


}

package com.example.crosswalkdemo;

import android.app.Activity;
import android.os.Bundle;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

/**
 * @author LinKun EmployeeID:151750
 * @email : linkun199011@163.com
 * @time : 2016/4/21 15:40
 */
public class XWalkWebViewActivity extends Activity {
    private XWalkView xWalkWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xwalk_webview);

        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString("mUrl");
        System.out.println(">>> URL is = " + mUrl);
        xWalkWebView=(XWalkView)findViewById(R.id.xwalkWebView);
        xWalkWebView.load(mUrl, null);

        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkWebView != null) {
            xWalkWebView.pauseTimers();
            xWalkWebView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkWebView != null) {
            xWalkWebView.resumeTimers();
            xWalkWebView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xWalkWebView != null) {
            xWalkWebView.onDestroy();
        }
    }
}

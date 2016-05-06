package com.example.crosswalkdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.crosswalkdemo.js.WebJSActivity;
import com.example.crosswalkdemo.utils.FirstLoadingX5Service;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinKun EmployeeID:151750
 * @email : linkun199011@163.com
 * @time : 2016/4/21 15:40
 */
public class MainActivity extends Activity {
    private String mUrl; // get the URL by selecting the specific spinner item.
    private String mUrlAny; // get the URL by input the url into EditText
    private static final String URL0 = "http://krakenbenchmark.mozilla.org/kraken-1.1/driver.html";
    private static final String URL1 = "http://octane-benchmark.googlecode.com/svn/latest/index.html";
    //private static final String URL2 = "http://www.webkaka.com/webspeed/";
    private static final String URL2 = "file:///android_asset/youku.html";

    private static final String URL3 = "http://jupiter909.com/mark/jsrt.html";
    private static final String URL4 = "http://fooo.fr/~vjeux/epita/raytracer/raytracer.html#portal";
    private static final String URL5 = "http://html5test.com/";

    private Spinner mSpinner;
    private Button mButtonXWalk;
    private Button mButtonAny;
    private Button mButtonWebView;
    private Button mButtonX5;
    private EditText mEditTextUrl;
    private Button mButtonXWalk2;
    private Button mButtonAny2;
    private Button mButtonWebView2;
    private Button mButtonX52;
    //Js test
    private Button mButtonX5Js;
    //load local html
    private Button mButtonLocal;

    private List<String> mDataList;
    private ArrayAdapter<String> mArrDapter;

    private Context mContext = null;

    // for API 23+,you need to request the read/write permissions even if they are already in your manifest.
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        mContext = this;
        verifyStoragePermissions(MainActivity.this);
        preinitX5WebCore();
        preinitX5WithService();// 此方法必须在非主进程执行才会有效果
    }
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void initView() {
        mSpinner = (Spinner) findViewById(R.id.spinner);
        // use XWalk webview to access the website
        mButtonXWalk = (Button) findViewById(R.id.btn_xwalk);
        mButtonXWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mUrl", mUrl);
                Intent intent = new Intent(MainActivity.this, XWalkWebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        // use Android any browser to access the website
        mButtonAny = (Button) findViewById(R.id.btn_any);
        mButtonAny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mUrl);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });
        // use Android webview to access the website
        mButtonWebView = (Button) findViewById(R.id.btn_webview);
        mButtonWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mUrl", mUrl);
                Intent intent = new Intent(MainActivity.this, AndroidWebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mButtonX5 = (Button) findViewById(R.id.btn_x5);
        mButtonX5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mUrl", mUrl);
                Intent intent = new Intent(MainActivity.this, X5WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mEditTextUrl = (EditText) findViewById(R.id.et_url);
        mButtonXWalk2 = (Button) findViewById(R.id.btn_xwalk2);
        mButtonXWalk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlAny = mEditTextUrl.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("mUrl", mUrlAny);
                Intent intent = new Intent(MainActivity.this, XWalkWebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mButtonAny2 = (Button) findViewById(R.id.btn_any2);
        mButtonAny2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlAny = mEditTextUrl.getText().toString();
                Uri uri = Uri.parse(mUrlAny);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });
        mButtonWebView2 = (Button) findViewById(R.id.btn_webview2);
        mButtonWebView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlAny = mEditTextUrl.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("mUrl", mUrlAny);
                Intent intent = new Intent(MainActivity.this, AndroidWebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mButtonX52 = (Button) findViewById(R.id.btn_x5_2);
        mButtonX52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUrlAny = mEditTextUrl.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("mUrl", mUrlAny);
                Intent intent = new Intent(MainActivity.this, X5WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        mButtonX5Js = (Button) findViewById(R.id.btn_X5_Js);
        mButtonX5Js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebJSActivity.class);
                startActivity(intent);
            }
        });

        mButtonLocal = (Button) findViewById(R.id.btn_webview_local);
        mButtonLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoadLocalHtmlActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        //Url Data
        mDataList = new ArrayList<String>();
        mDataList.add(URL0);
        mDataList.add(URL1);
        mDataList.add(URL2);
        mDataList.add(URL3);
        mDataList.add(URL4);
        mDataList.add(URL5);

        //适配器
        mArrDapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mDataList);
        //设置样式
        mArrDapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mSpinner.setAdapter(mArrDapter);
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mUrl = mDataList.get(arg2);
                if(arg2 == 2) {//其他浏览器无法访问本地资源
                    mButtonAny.setEnabled(false);// 不可用
                }
                else {
                    mButtonAny.setEnabled(true);
                }
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                mUrl = mDataList.get(0);
                arg0.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 开启额外进程 服务 预加载X5内核， 此操作必须在主进程调起X5内核前进行，否则将不会实现预加载
     */
    private void preinitX5WithService() {
        System.out.println(">>>>>>>>>>>preinitX5WithService");
        Intent intent = new Intent(mContext, FirstLoadingX5Service.class);
        startService(intent);
    }

    /**
     * X5内核在使用preinit接口之后，对于首次安装首次加载没有效果
     * 实际上，X5webview的preinit接口只是降低了webview的冷启动时间；
     * 因此，现阶段要想做到首次安装首次加载X5内核，必须要让X5内核提前获取到内核的加载条件
     */
    private void preinitX5WebCore() {
        System.out.println(">>>>>>>>>>>preinitX5WebCore");
        if (!QbSdk.isTbsCoreInited()) {// preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
            System.out.println(">>>>>>>>>>>QbSdk.isTbsCoreInited()");
            QbSdk.preInit(MainActivity.this, null);// 设置X5初始化完成的回调接口
            // 第三个参数为true：如果首次加载失败则继续尝试加载；
        }

    }

    /*private QbSdk.PreInitCallback myCallback = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished() {// 当X5webview 初始化结束后的回调
            new WebView(mContext);
            //MainActivity.this.isX5WebViewEnabled = true;
        }

        @Override
        public void onCoreInitFinished() {
        }
    };*/





}

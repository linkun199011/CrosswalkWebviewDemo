package com.example.crosswalkdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LinKun EmployeeID:151750
 * @email : linkun199011@163.com
 * @time : 2016/4/21 15:40
 */
public class MainActivity extends Activity {
    private String mUrl; // get the URL by selecting the specific spinner item.
    private static final String URL0 = "http://krakenbenchmark.mozilla.org/kraken-1.1/driver.html";
    private static final String URL1 = "http://octane-benchmark.googlecode.com/svn/latest/index.html";
    private static final String URL2 = "http://www.webkaka.com/webspeed/";
    private static final String URL3 = "http://jupiter909.com/mark/jsrt.html";
    private static final String URL4 = "http://fooo.fr/~vjeux/epita/raytracer/raytracer.html#portal";
    private static final String URL5 = "http://html5test.com/";

    private Spinner mSpinner;
    private Button mButtonXWalk;
    private Button mButtonDefault;
    private List<String> mDataList;
    private ArrayAdapter<String> mArrDapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
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
        // use Android default webview to access the website
        mButtonDefault = (Button) findViewById(R.id.btn_default);
        mButtonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mUrl);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
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
        mArrDapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mDataList);
        //设置样式
        mArrDapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mSpinner.setAdapter(mArrDapter);
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mUrl = mDataList.get(arg2);
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



}

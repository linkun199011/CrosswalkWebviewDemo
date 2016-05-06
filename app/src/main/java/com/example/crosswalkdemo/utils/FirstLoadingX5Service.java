package com.example.crosswalkdemo.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * @author LinKun EmployeeID:151750
 * @email : linkun199011@163.com
 * @time : 2016/5/3 14:41
 */
public class FirstLoadingX5Service extends Service {

    private static final String TAG = "FirstLoadingX5Service";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        //Log.i(TAG, ">>>> service is start");
        System.out.println(">>>> service is start");
        QbSdk.preInit(this); //这里必须启用非主进程的service来预热X5内核
        super.onCreate();
    }

}
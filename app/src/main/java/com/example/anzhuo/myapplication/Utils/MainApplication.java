package com.example.anzhuo.myapplication.Utils;

import android.app.Application;

import com.tencent.connect.auth.QQAuth;

/**
 * Created by anzhuo on 2016/9/28.
 */
public class MainApplication extends Application {
    public static QQAuth mQQAuth;

    public static AppConfig appConfig;

    public void onCreate(){
        super.onCreate();
        appConfig=new AppConfig(getApplicationContext());
    }
}

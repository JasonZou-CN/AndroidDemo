package com.jasonzou.demo.jpush;

import android.app.Application;

/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/7/3
 * Time:15:35
 * Desc:略
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);*/

    }
}

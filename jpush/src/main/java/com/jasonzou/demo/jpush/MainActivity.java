package com.jasonzou.demo.jpush;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);

        JMessageClient.register("jasonzou", "jasonzou", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("JMessageClien注册", "gotResult: s="+s+" i="+i);
                JMessageClient.login("jasonzou", "jasonzou", new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        Log.i("JMessageClient登录", "gotResult: jasonzou");
                        Log.i("JMessageClient登录", "gotResult: [" + responseCode + "," + responseMessage + "]");
                    }
                });
            }
        });



        /*JMessageClient.login("13330834333", "13330834333", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                            Log.i("JMessageClient登录", "gotResult: 13330834333");
                Log.i("JMessageClient登录", "gotResult: [" + responseCode + "," + responseMessage + "]");
            }
        });*/
    }
}

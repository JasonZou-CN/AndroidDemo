package com.jasonzou.howaboutimageshow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 /1024);
        Log.d("TAG", "Max memory is " + maxMemory + "MB");
    }
}

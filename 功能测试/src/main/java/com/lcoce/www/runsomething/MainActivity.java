package com.lcoce.www.runsomething;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lcoce.www.runsomething.utils.Utils;

public class MainActivity extends Activity {

    private MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
    }

    public void onclick(View view) {
        Utils.notifySomething(this,this.getClass());
    }
}

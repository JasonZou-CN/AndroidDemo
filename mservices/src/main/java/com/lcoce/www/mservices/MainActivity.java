package com.lcoce.www.mservices;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    Activity mContext = this;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            Log.d(TAG, "onServiceConnected: componentName=" + componentName);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: componentName=" + componentName);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        Log.d(TAG, "start: ");
        startService(new Intent(mContext, MyService.class));
    }

    public void stop(View view) {
        Log.d(TAG, "stop: ");
        stopService(new Intent(mContext, MyService.class));
    }

    public void bind(View view) {
        Log.d(TAG, "bind: ");
        Bundle data = new Bundle();
        data.putString("component",this.getClass().getName());
        Intent intent = new Intent(mContext, MyService.class);
        intent.putExtras(data);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    public void unbind(View view) {
        Log.d(TAG, "unbind: ");
        unbindService(serviceConnection);
    }
}

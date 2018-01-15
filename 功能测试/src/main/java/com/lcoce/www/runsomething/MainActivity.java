package com.lcoce.www.runsomething;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import java.util.Random;

public class MainActivity extends Activity {

    private MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
    }

    public void onclick(View view) {
        notifySomething();
    }

    private void notifySomething() {
        int random = new Random().nextInt();
        /*Android O*/
        if (Build.VERSION.SDK_INT >= 26) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            PendingIntent pintent = PendingIntent.getActivity(this, 0, intent, 0);
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            /*Android O：通知控制-通知类别*/
            NotificationChannel mChannel = new NotificationChannel(getPackageName(), "App通知", NotificationManager.IMPORTANCE_MAX);
            mChannel.setDescription("通知类别描述：紧急通知");
            mChannel.enableLights(true);//是否在桌面icon右上角展示小红点
            mChannel.setLightColor(Color.RED);//小红点颜色
            mChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setSound(sound, null);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);

            Notification.Builder builder = new Notification.Builder(MainActivity.this, getPackageName());
            builder.setTicker("新通知");
            builder.setContentTitle("Android O 通知：" + "(" + random + "条新消息)");
            builder.setContentText("这是一条逗你玩的消息");
            builder.setSmallIcon(R.drawable.notification_3);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification));
            builder.setAutoCancel(true);
            builder.setContentIntent(pintent);
            builder.setNumber(3); //久按桌面图标时允许的此条通知的数量

            Notification notification = builder.build();
            mNotificationManager.notify(random, notification);
        } else {
            //构建一个Intent
            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.putExtra("name", "");
            //封装一个Intent(设置ChatList - android:launchMode="singleInstance")
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setTicker("新通知")//第一次提示消息的时候显示在通知栏上;
                    .setSmallIcon(R.drawable.notification_3).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification)).setContentTitle("通知：" + "(" + random + "条新消息)").setContentText("通知内容：...").setAutoCancel(true)//点击后，自动消失
                    .setContentIntent(pendingIntent).setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            //        mBuilder.setVibrate(new long[]{0, 200, 300}).setSound(sound).setLights(Color.RED, 1000, 1000);


            //获取通知管理器对象
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //一个手机号一个通知
            mNotificationManager.notify(random + "", 0, mBuilder.build());//Tag区分Notification，
        }
    }
}

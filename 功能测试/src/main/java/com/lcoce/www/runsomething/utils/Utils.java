package com.lcoce.www.runsomething.utils;

import android.annotation.SuppressLint;
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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lcoce.www.runsomething.BuildConfig;
import com.lcoce.www.runsomething.R;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * @项目 AndroidDemo
 * @类名 com.lcoce.www.runsomething.utils
 * @描述 略
 * @创建人 jasonzou
 * @创建时间 2018/1/15 09:56
 */
public class Utils {

    /**
     * 文件上传
     */
    public static void uploadFile() {
        new Thread() {
            @Override
            public void run() {
                String end = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                String newName = "image.jpg";
                String uploadFile = "/storage/emulated/0/rongxinlaw.lcoce.lawyer/img_cache/https:__oa.lcoce.com_file_case_20171227_09163e67db446231636a81811e6f6374.jpg";

            /*头像上传*/
                String actionUrl = "https://oa.lcoce.com/api2/Lawyer/modifyAvatar";
                try {
                    URL url = new URL(actionUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
            /* 设置传送的method=POST */
                    con.setRequestMethod("POST");
            /* setRequestProperty */
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            /* 设置DataOutputStream */
                    DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            /*基本参数*/
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; " + "name=\"id\"" + end);
                    ds.writeBytes(end);
                    ds.writeBytes("194" + end);

            /*文件参数*/
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\"" + newName + "\"" + end);
                    ds.writeBytes(end);
            /* 取得文件的FileInputStream */
                    FileInputStream fStream = new FileInputStream(uploadFile);
            /* 设置每次写入1024bytes */
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;
            /* 从文件读取数据至缓冲区 */
                    while ((length = fStream.read(buffer)) != -1) {
            /* 将资料写入DataOutputStream中 */
                        ds.write(buffer, 0, length);
                    }
                    ds.writeBytes(end);
                    ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
                    fStream.close();
                    ds.flush();
            /* 取得Response内容 */
                    InputStream is = con.getInputStream();
                    int ch;
                    StringBuffer b = new StringBuffer();
                    while ((ch = is.read()) != -1) {
                        b.append((char) ch);
                    }
            /* 将Response显示于Dialog */
                    if (BuildConfig.DEBUG)
                        Log.d("MainActivity", ("上传成功" + b.toString().trim()));
            /* 关闭DataOutputStream */
                    ds.close();
                } catch (Exception e) {
                    if (BuildConfig.DEBUG)
                        Log.d("MainActivity", ("上传失败" + e));
                }
            }
        }.start();
    }

    /**
     * 系统通知
     */
    public static void notifySomething(Context ctx, Class clz) {
        int random = new Random().nextInt();

        //构建一个Intent
        Intent resultIntent = new Intent(ctx, clz);
        resultIntent.putExtra("name", "");
        //封装一个Intent(设置ChatList - android:launchMode="singleInstance")
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //获取通知管理器对象
        NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        /*通知内容*/
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx, ctx.getPackageName());
        mBuilder.setTicker("新通知");//第一次提示消息的时候显示在通知栏上;
        mBuilder.setSmallIcon(R.drawable.notification_3);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.notification));
        mBuilder.setContentTitle("Android O 通知：" + "(" + random + "条新消息)");
        mBuilder.setContentText("这是一条逗你玩的消息");
        mBuilder.setAutoCancel(true);//点击后，自动消失
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setNumber(random); //久按桌面图标时允许的此条通知的数量
        //        mBuilder.setVibrate(new long[]{0, 200, 300}).setSound(sound).setLights(Color.RED, 1000, 1000);

        /*Android O*/
        if (Build.VERSION.SDK_INT >= 26) {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            /*Android O：通知控制-通知类别*/
            @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel(ctx.getPackageName(), "App通知", NotificationManager.IMPORTANCE_MAX);
            mChannel.setDescription("通知类别描述：紧急通知");
            mChannel.enableLights(true);//是否在桌面icon右上角展示小红点
            mChannel.setLightColor(Color.RED);//小红点颜色
            mChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setSound(sound, null);
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        }
        //一个手机号一个通知
        mNotificationManager.notify(random + "", 0, mBuilder.build());//Tag区分Notification，
    }

}

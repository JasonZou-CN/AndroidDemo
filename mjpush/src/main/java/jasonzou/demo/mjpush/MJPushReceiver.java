package jasonzou.demo.mjpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import cn.jpush.android.api.JPushInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 处理一些广播-收到自定义消息...
 * 发送自定义通知
 */
public class MJPushReceiver extends BroadcastReceiver {
    private static final String TAG = "MJPushReceiver";
    NotificationManager mNotificationManager;
    Context ctx;

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;
        Log.i(TAG, "onReceive: action:" + intent.getAction());
        mNotificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String json = bundle.getString(JPushInterface.EXTRA_MESSAGE);//自定义消息内容
            createCustomNotification(JsonUtils.getNotification(json));
            Log.i(TAG, "接受到推送下来的自定义消息: " + json);
        } else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.i(TAG, "JPush用户首次注册成功! RegistrationID:" + regId);
        } else {
            Log.i(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 创建自定义通知栏
     *
     * @param data
     */
    public void createCustomNotification(MyNotificaion data) {
        Intent intent = new Intent(ctx, MainActivity.class);
        //如果第二次获取并且请求码相同,如果原来已解决创建了这个PendingIntent,则复用这个类,并更新intent
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent contentIntent = PendingIntent.getActivity(ctx.getApplicationContext(), 3, intent, flag);

        Notification notification = new Notification(R.drawable.jpush_richpush_progressbar, "新消息", System.currentTimeMillis());
        notification.contentView = getRemoteView(data);
        notification.contentIntent = contentIntent;
        //打开通知
        mNotificationManager.notify(0, notification);//相同ID用于更新通知数据
    }

    /**
     * 自定义通知栏-布局-创建RemoteViews,3.0之后版本使用
     *
     * @return
     */
    private RemoteViews getRemoteView(MyNotificaion data) {
        RemoteViews remoteViews = new RemoteViews(ctx.getPackageName(), R.layout.m_notification_layout);
        remoteViews.setTextViewText(R.id.title, data.title);

        String time = analysisDateToString(data.time);
        remoteViews.setTextViewText(R.id.time, time);
        remoteViews.setTextViewText(R.id.text, data.content);
        return remoteViews;
    }

    /**
     * 解析时间-> 通知栏要求的时间描述
     *
     * @param datetime
     * @return
     */
    public static String analysisDateToString(long datetime) {
        StringBuffer mDatetime = new StringBuffer();
        long currentTime = new Date().getTime();
        long dur = currentTime - datetime;
        Date date = new Date(datetime);
        if (dur < 0) {//定时消息
            // TODO: 2017/7/5  时间戳的处理
        } else if (dur >= 0 && dur < 1 * 24 * 3600 * 1000) {
            mDatetime.append("今天");
            mDatetime.append(" " + date.getHours() + ":" + date.getMinutes());
        } else if (dur >= 1 * 24 * 3600 * 1000 && dur < 2 * 24 * 3600 * 1000) {//一天前
            mDatetime.append("昨天");
            mDatetime.append(" " + date.getHours() + ":" + date.getMinutes());

        } else if (dur >= 2 * 24 * 3600 * 1000 && dur < 3 * 24 * 3600 * 1000) {//两天前
            mDatetime.append("前天");
            mDatetime.append(" " + date.getHours() + ":" + date.getMinutes());

        } else {//超过四天--具体日期
            mDatetime.append(new SimpleDateFormat("MM/dd HH:mm").format(date));
        }
        return mDatetime.toString();
    }


}

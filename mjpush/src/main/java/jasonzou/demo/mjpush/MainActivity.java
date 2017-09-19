package jasonzou.demo.mjpush;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.*;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Message;
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

        String registraId = JPushInterface.getRegistrationID(this);
        Log.i("JPushInterface.XX", "onCreate: RegistrationID=" + registraId);

        JMessageClient.register("18380430507", "18380430507", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("JMessageClient.register", "gotResult: {" + i + "," + s + "}");
                //                {898001,user exist}
                if (i == 898001)
                    JMessageClient.login("18380430507", "18380430507", new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            Log.i("JMessageClient.login", "gotResult: {" + i + "," + s + "}");

                        }
                    });
            }
        });

        JMessageClient.registerEventReceiver(this);
    }

    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();

        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                textContent.getText();
                Log.i("onEvent(MessageEvent)", "onEvent: content=" + textContent.getText());
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent) msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()) {
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                    case group_info_updated://since 2.2.1
                        //群信息变更事件
                        break;
                }
                break;
        }
    }
}

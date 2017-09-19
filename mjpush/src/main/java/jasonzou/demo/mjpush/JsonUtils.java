package jasonzou.demo.mjpush;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/7/5
 * Time:11:29
 * Desc:JSON处理工具类
 */

public class JsonUtils {
    /**
     * 解析服务器端自定义消息 -> 自定义通知
     *
     * @param json
     * @return
     */
    public static MyNotificaion getNotification(String json) {
        MyNotificaion mNotification = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            int opt = jsonObject.getInt("opt");
            switch (opt) {
                case 0://自定义消息
                    String message = jsonObject.getString("message");
                    MyNotificaion myNotificaion = new Gson().fromJson(message, MyNotificaion.class);
                    return myNotificaion;
                default:
                    return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

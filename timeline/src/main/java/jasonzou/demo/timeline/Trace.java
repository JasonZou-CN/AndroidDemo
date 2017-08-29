package jasonzou.demo.timeline;

/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/8/29
 * Time:23:20
 * Desc:略
 */
public class Trace {
    /**
     * 时间
     */
    public String acceptTime;
    /**
     * 描述
     */
    public String acceptStation;

    public Trace() {
    }

    public Trace(String acceptTime, String acceptStation) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }
}

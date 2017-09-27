package jasonzou.demo.db;

import android.app.Application;
import jasonzou.demo.db.annotation.AnnotationHandler;

/**
 * Created with Android Studio
 * User:邹旭
 * Date:2017/9/28
 * Time:0:01
 * Desc:略
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AnnotationHandler.initTable();
    }
}

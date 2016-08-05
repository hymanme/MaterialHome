package com.hymane.materialhome;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by hymanme on 2016/1/5 0005.
 * Application
 */
public class BaseApplication extends Application {
    public final static String TAG = "BaseApplication";
    public final static boolean DEBUG = true;
    private static BaseApplication application;
    private static int mainTid;

//    static {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainTid = android.os.Process.myTid();
    }

    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return application;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainTid() {
        return mainTid;
    }

    /**
     * 退出应运程序
     */
    public static void quiteApplication() {
        System.exit(0);
    }
}

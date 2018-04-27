package com.mizhi.videoplayermanagertest;

import android.app.Application;
import android.content.Context;

/**
 * 类描述：
 *
 * @Author 许少东
 * Created at 2018/4/27.
 */

public class MyApplication extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}

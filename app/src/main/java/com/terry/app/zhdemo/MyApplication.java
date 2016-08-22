package com.terry.app.zhdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Taozi on 2016/7/9.
 */
public class MyApplication extends Application {

    private static Context context;

    public static Context getContext() {

        return context;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
}

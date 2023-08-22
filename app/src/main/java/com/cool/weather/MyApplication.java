package com.cool.weather;

import android.app.Application;


public class MyApplication extends Application {
    public static MyApplication instance = null;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}

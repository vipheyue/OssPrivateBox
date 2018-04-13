package com.example.heyue.ossprivatebox;

import android.app.Application;

public class MyApp extends Application {
    public static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

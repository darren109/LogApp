package com.app.darren.logapp;

import android.app.Application;

import com.darren.loglibs.ToolLog;


/**
 * Created by zhaokaiqiang on 15/11/14.
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToolLog.init(true,"IApplication:app");
    }
}

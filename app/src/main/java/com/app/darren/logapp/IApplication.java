package com.app.darren.logapp;

import android.app.Application;

import com.darren.loglibs.ToolLog;


/**
 * Created by darren on 15/11/14.
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        ToolLog.init(this, "IApplication:app");
        ToolLog.init(this, true, true, "IApplication:app");
    }
}

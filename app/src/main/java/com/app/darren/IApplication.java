package com.app.darren;

import android.app.Application;

import com.darren.litesuits.android.log.LogReader;
import com.darren.loglibs.ToolLog;
import com.darren.loglibs.exception.CrashHandler;
import com.darren.loglibs.utils.FileLogUtils;


/**
 * Created by zhaokaiqiang on 15/11/14.
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToolLog.init(true,"IApplication:app");
        CrashHandler.getInstance().init(getApplicationContext());
        // 收集设备参数信息
        FileLogUtils.initData(getApplicationContext(), "com.app.darren.logapp", true);
        LogReader.startCatchLog("com.app.darren.logapp"); 
    }
}

package com.app.darren.logapp;

import android.app.Application;
import android.widget.Toast;

import com.darren.loglibs.ToolLog;


/**
 * Created by darren on 15/11/14.
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initToolLog();
    }

    /**
     * init log
     */
    private void initToolLog() {
        ToolLog.init(this, BuildConfig.IS_DEBUG, BuildConfig.IS_SAVE_DEBUG, "darren");
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                ToolLog.e("darren", "uncaughtException crash", e);
                Toast.makeText(IApplication.this, "crash,Look log!", Toast.LENGTH_LONG).show();
                ToolLog.log(e);
                ToolLog.i("local log path:" + ToolLog.getLocalLogFile().getAbsolutePath());
                ToolLog.i("local backup log path:" + ToolLog.getLocalLogBackupFile().getAbsolutePath());
            }
        });
        ToolLog.i("local log path:" + ToolLog.getLocalLogFile().getAbsolutePath());
        ToolLog.i("local backup log path:" + ToolLog.getLocalLogBackupFile().getAbsolutePath());
    }
}

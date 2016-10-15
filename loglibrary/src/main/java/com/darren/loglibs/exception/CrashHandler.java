package com.darren.loglibs.exception;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.darren.loglibs.Constant;
import com.darren.loglibs.ToolLog;
import com.darren.loglibs.utils.FileLogUtils;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by Administrator on 2016/2/15.
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    // CrashHandler 实例
    private static CrashHandler INSTANCE = new CrashHandler();

    // 程序的 Context 对象
    private Context mContext;

    // 系统默认的 UncaughtException 处理类
    private UncaughtExceptionHandler mDefaultHandler;


    /**
     * 保证只有一个 CrashHandler 实例
     */
    private CrashHandler() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;

        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                ToolLog.e(TAG, "error : ", e);
            }

            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        // 保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }


    /**
     * 保存错误信息到文件中 *
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        try {
            String path = FileLogUtils.getExternalStoragePath();
            String lineTxt = FileLogUtils.CrashrExceptionInfo(ex);
            ToolLog.e(TAG, lineTxt);
            FileLogUtils.writeTxtToFile(FileLogUtils.pingStr(TAG + "/E", lineTxt), path, Constant.ERRLOG);
            return path + File.separator + Constant.ERRLOG;
        } catch (Exception e) {
            ToolLog.e(TAG, "an error occured while writing file...", e);
        }

        return null;
    }
}
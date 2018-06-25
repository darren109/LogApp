package com.darren.loglibs;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.darren.loglibs.log.BaseLog;
import com.darren.loglibs.log.FileLog;
import com.darren.loglibs.log.JsonLog;
import com.darren.loglibs.log.XmlLog;
import com.darren.loglibs.utils.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is a Log tool，with this you can the following
 *
 * @author Darren
 */
public class ToolLog {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM-dd/HH:mm:ss.SSS");

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "ToolLog";
    private static final String SUFFIX = ".java";

    public static final int JSON_INDENT = 4;
    public static final int V = 0x01;
    public static final int D = 0x02;
    public static final int I = 0x03;
    public static final int W = 0x04;
    public static final int E = 0x05;
    private static final int JSON = 0x07;
    private static final int XMl = 0x08;

    private static final int STACK_TRACE_INDEX = 5;

    private static String mGlobalTag;
    private static boolean mIsGlobalTagEmpty = true;

    private static boolean IS_SHOW_LOG = true;
    private static boolean IS_SAVE_LOG = true;
    private static Context context;

    public static void init(Context context, String tag) {
        init(context, true, true, tag);
    }

    public static void init(Context context, boolean isShowLog, boolean isSaveLog, String tag) {
        ToolLog.context = context;
        IS_SHOW_LOG = isShowLog;
        IS_SAVE_LOG = isSaveLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
    }

    public static Context getContext() {
        return context;
    }

    public static void v() {
        printLog(V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(V, null, msg);
    }

    public static void v(String tag, Object... objects) {
        printLog(V, tag, objects);
    }

    public static void d() {
        printLog(D, null, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(D, null, msg);
    }

    public static void d(String tag, Object... objects) {
        printLog(D, tag, objects);
    }

    public static void i() {
        printLog(I, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(I, null, msg);
    }

    public static void i(String tag, Object... objects) {
        printLog(I, tag, objects);
    }

    public static void w() {
        printLog(W, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(W, null, msg);
    }

    public static void w(String tag, Object... objects) {
        printLog(W, tag, objects);
    }

    public static void e() {
        printLog(E, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(E, null, msg);
    }

    public static void e(String tag, Object... objects) {
        printLog(E, tag, objects);
    }

    public static void json(String jsonFormat) {
        printLog(JSON, null, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        printLog(JSON, tag, jsonFormat);
    }

    public static void xml(String xml) {
        printLog(XMl, null, xml);
    }

    public static void xml(String tag, String xml) {
        printLog(XMl, tag, xml);
    }

    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    public static void log(Object object) {
        printFileLog(null, object);
    }

    public static void log(String tag, Object... objects) {
        printFileLog(tag, objects);
    }

    private static void printLog(int type, String tagStr, Object... objects) {

        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
                BaseLog.printDefault(type, tag, headString + msg);
                break;
            case JSON:
                JsonLog.printJson(tag, msg, headString);
                break;
            case XMl:
                XmlLog.printXml(tag, msg, headString);
                break;
        }
    }
    
    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {
        if (!IS_SAVE_LOG) {
            return;
        } else if (context == null) {
            e(tagStr, "Tool Log init () method is not called,Or context is null");
            return;
        }
        String[] contents = wrapperContent(tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        FileLog.printFile(tag, targetDirectory, fileName, headString, msg);
    }

    private static void printFileLog(String tagStr, Object... objects) {
        if (!IS_SAVE_LOG) {
            return;
        } else if (context == null) {
            e(tagStr, "Tool Log init () method is not called,Or context is null");
            return;
        }
        String[] contents = wrapperContent(tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        FileLog.printFileLog(tag, headString, msg);
    }

    /**
     * 获取本地日志
     */
    public static File getLocalLogFile() {
        return new File(Util.getProperCacheDir(context, FileLog.getFileLogPath()), File.separator + FileLog.getLocalFileName());
    }

    /**
     * 获取本地日志备份
     */
    public static File getLocalLogBackupFile() {
        return new File(Util.getProperCacheDir(context, FileLog.getFileLogPath()), File.separator + FileLog.getLocalBackupFileName());
    }

    /**
     * 生成Log日志。前缀信息格式：日期时间+当前线程名+文件名+行号+方法名
     */
    private static String[] wrapperContent(String tagStr, Object... objects) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[STACK_TRACE_INDEX];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        String tag = (tagStr == null ? className : tagStr);

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT;
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag;
        }

        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String headString = "[" + SIMPLE_DATE_FORMAT.format(new Date()) + "-" + Thread.currentThread().getName() + "]" +
                "[(" + className + ":" + lineNumber + ")#" + methodNameShort + "]";

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ")
                            .append(object instanceof Throwable ? Log.getStackTraceString((Throwable) object) : object.toString())
                            .append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? NULL : object instanceof Throwable ? Log.getStackTraceString((Throwable) object) : object.toString();
        }
    }


}

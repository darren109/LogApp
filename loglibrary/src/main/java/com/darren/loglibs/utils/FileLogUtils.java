package com.darren.loglibs.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.darren.loglibs.Constant;
import com.darren.litesuits.android.log.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FileLogUtils {


    private static final String TAG = "FileUtils";
    private static String packages;
    static boolean IS_SAVE = false;
    /**
     * 用来存储设备信息和异常信息
     */
    private static Map<String, String> infos = new HashMap<String, String>();
    /**
     * 日志路径
     */
    private static String externalStoragePath;
    private static String externalFileStoragePath;

    public static String getExternalStoragePath() {
        return externalStoragePath;
    }

    public static File getExternalStoragePathDir() {
        return new File(externalStoragePath);
    }

    public static String getExternalFileStoragePathDir() {
        return externalFileStoragePath;
    }

    public static File getExternalFileStoragePath() {
        return new File(externalFileStoragePath);
    }

    private static void init() {
        if (ExistSDCard() && getSDFreeSize() > 10 && getSDFreeSize() * 1.0 / getSDAllSize() > 0.1) {
            externalFileStoragePath = Environment.getExternalStoragePublicDirectory(packages).getPath();
            externalStoragePath = externalFileStoragePath + File.separator + "Log/";
        } else {
            externalFileStoragePath = Environment.getDataDirectory().getPath() + File.separator + packages;
            externalStoragePath = externalFileStoragePath + File.separator + "Log/";
        }
        Log.i("externalStoragePath==>", externalStoragePath);
    }


    public static void initData(Context context, String page, boolean save) {
        packages = page;
        IS_SAVE = save;
        init();
        writeTxtToFile("Excepution content", externalStoragePath, Constant.ERRLOG);
        writeTxtToFile(collectDeviceInfo(context), externalStoragePath, Constant.ERRLOG);
        writeTxtToFile("KLog content", externalStoragePath, Constant.LOGLOG);
        writeTxtToFile("All Log content", externalStoragePath, Constant.LOG);

    }

    /**
     * 拼接 date + packages +tag + str
     *
     * @param tag
     * @param str
     * @return
     */
    public static String pingStr(String tag, String str) {
        return MyDate.getDateEN() + " " + packages + " " + tag + ":" + str;
    }

    /**
     * 将字符串写入到文本文件中
     *
     * @param strcontent write to file of content.
     * @param filePath   file path
     * @param fileName   file name
     */
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        if (IS_SAVE) {
            //生成文件夹之后，再生成文件，不然会出错
            makeFilePath(filePath, fileName);
            String strFilePath = filePath + File.separator + fileName;
            // 每次写入时，都换行写
            String strContent = strcontent + "\r\n";
            try {
                File file = new File(strFilePath);
                if (!file.exists()) {
                    Log.d("LogFile", "Create the file:" + strFilePath);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(strContent.getBytes(Constant.CHARSET));
                raf.close();
            } catch (Exception e) {
                Log.e("TestFile", "Error on write File:" + e);
            }
        }
    }

    /**
     * 生成文件
     *
     * @param filePath file path
     * @param fileName file name
     * @return file
     */
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + File.separator + fileName);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + file.getPath());
                file.createNewFile();
            }
        } catch (Exception e) {
            Log.e(TAG, "create file is false!", e);
        }
        return file;
    }

    /**
     * 生成文件夹
     *
     * @param filePath file path
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.e("make root directory is error:", e + "");
        }
    }

    /**
     * SD卡是否存在
     *
     * @return
     */
    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * SD卡剩余空间
     *
     * @return
     */
    public static long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }

    /**
     * SD卡总容量
     *
     * @return
     */
    public static long getSDAllSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; //单位MB
    }


    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public static String collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(MyDate.getDateEN() + "--" + key + ":" + value + "\r\n");
            Log.d(TAG, MyDate.getDateEN() + "--" + key + ":" + value);
        }
        return sb.toString();
    }

    /**
     * 处理错误信息
     *
     * @param ex
     * @return 信息
     */
    public static String CrashrExceptionInfo(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            printWriter.append("\n\t\t\t\t\t");//换行
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
                printWriter.append("\n\t\t\t\t\t");//换行
            }
            printWriter.close();
            String eresult = writer.toString();
            sb.append(eresult);
            Log.d(TAG, eresult);
            String result = sb.toString();
            return result;
        } catch (Exception e) {
            Log.e(TAG, "处理异常信息", e);
        }
        return null;
    }
}
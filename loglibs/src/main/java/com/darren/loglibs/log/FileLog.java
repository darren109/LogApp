package com.darren.loglibs.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.darren.loglibs.utils.ExecutorCompat;
import com.darren.loglibs.ToolLog;
import com.darren.loglibs.utils.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileLock;
import java.util.Random;


/**
 * Created by Darren on 15/11/18.
 */
public class FileLog {
    private static final String TAG = FileLog.class.getSimpleName();
    private static final long FILE_MAX_LENGTH = 1024 * 1024;//1M 大小
    private static final String FILE_PREFIX = "ToolLog_";
    private static final String FILE_FORMAT = ".log";

    private static String getFileName() {
        Random random = new Random();
        return FILE_PREFIX + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + FILE_FORMAT;
    }

    /**
     * Save to file.
     *
     * @param tag
     * @param targetDirectory
     * @param fileName
     * @param headString
     * @param msg
     */
    public static void printFile(final String tag, final File targetDirectory, String fileName, final String headString, final String msg) {
        fileName = (fileName == null || fileName.length() == 0) ? getFileName() : fileName;
        final String finalFileName = fileName;
        ExecutorCompat.execute(new Runnable() {
            @Override
            public void run() {
                if (save(tag, targetDirectory, finalFileName, msg)) {
                    Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + finalFileName);
                } else {
                    Log.e(tag, headString + "save log fails !");
                }
            }
        });
    }


    /**
     * Save log.
     *
     * @param tag
     * @param headString
     * @param msg
     */
    public static void printFileLog(final String tag, final String headString, final String msg) {
        ExecutorCompat.execute(new Runnable() {
            @Override
            public void run() {
                save(tag, headString + " " + msg);
            }
        });
    }

    /**
     * Save to file.
     *
     * @param tag
     * @param dic
     * @param fileName
     * @param msg
     * @return
     */
    private static boolean save(String tag, File dic, String fileName, String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 || hasWritePermission(ToolLog.getContext())) {
            int count = 0;
            while (!dic.exists()) {
                count++;
                dic.mkdir();
                if (count > 10) {
                    break;
                }
                if (dic.exists()) {
                    ToolLog.d(tag, "make directory >>>" + dic.getAbsolutePath());
                }
            }
            File file = new File(dic, fileName);
            try {
                OutputStream outputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                outputStreamWriter.write(msg);
                outputStreamWriter.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        return false;
    }


    /**
     * Save log.
     *
     * @param tag
     * @param msg
     */
    private static void save(String tag, String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 || hasWritePermission(ToolLog.getContext())) {
            File cacheFile = Util.getProperCacheDir(ToolLog.getContext(), "Log");
            File localLog = new File(cacheFile.getAbsolutePath() + File.separator + "local.log");
            if (!cacheFile.exists()) {
                if (!cacheFile.mkdir()) return;
            }
            RandomAccessFile lockFile = null;
            FileLock lock = null;
            BufferedWriter bufferedWriter = null;

            //添加锁
            try {
                lockFile = new RandomAccessFile(new File(cacheFile, "local.lock"), "rw");
                //获取锁
                lock = lockFile.getChannel().lock();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localLog, true)));
                String message = msg;
                message = message + "\n";
                bufferedWriter.write(message);
                bufferedWriter.flush();
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                    bufferedWriter = null;
                }
                if (localLog.length() > FILE_MAX_LENGTH) {
                    File localBackup = new File(cacheFile, "local_backup.log");
                    if (localBackup.exists() && localBackup.isFile()) {
                        localBackup.delete();
                    }
                    localLog.renameTo(localBackup);
                }
            } catch (Exception e) {
                ToolLog.e(tag, e);
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        ToolLog.e(tag, "local log close io exception");
                    }
                }
                if (lock != null && lock.isValid()) {
                    try {
                        lock.release();
                    } catch (IOException e) {
                        ToolLog.e(tag, "release lock io exception");
                    }
                }
                if (lockFile != null) {
                    try {
                        lockFile.close();
                    } catch (IOException e) {
                        ToolLog.e(tag, "close lock file io exception");
                    }
                }
            }


        }
    }

    private static boolean hasWritePermission(Context var0) {
        try {
            PackageInfo var1 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] var2 = var1.requestedPermissions;
            if (var2 != null) {
                String[] var3 = var2;
                int var4 = var2.length;
                for (int var5 = 0; var5 < var4; ++var5) {
                    String var6 = var3[var5];
                    if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(var6)) {
                        return true;
                    }
                }
            }
        } catch (Exception var7) {
            ToolLog.e(TAG, var7);
        }
        ToolLog.e(TAG, "Application of no access permissions[android.permission.WRITE_EXTERNAL_STORAGE]");
        return false;
    }

}

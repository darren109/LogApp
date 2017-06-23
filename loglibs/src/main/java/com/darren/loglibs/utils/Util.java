package com.darren.loglibs.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by Darren on 15/12/11.
 */
public class Util {
    public static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═════════════════════════════════════════════════════════════════════");
        }
    }

    /**
     * Get the proper cache directory
     *
     * @param context
     * @return
     */
    public static File getProperCacheDir(Context context, String type) {
        File[] files = getExternalCacheDirs(context);
        File file = null;
        if (files != null) {
            for (File f : files) {
                if (f != null && isDirectoryWritable(f.getAbsolutePath())) {
                    file = f;
                    break;
                }
            }
        }

        if (file == null) {
            File f = context.getExternalCacheDir();
            if (f != null && isDirectoryWritable(f.getAbsolutePath())) {
                file = f;
            }
        }

        if (file == null) {
            file = context.getCacheDir();
        }

        if (type != null) {
            file = new File(file.getAbsolutePath() + File.separator + type);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return file;
    }

    public static boolean isDirectoryWritable(String directory) {
        File file = new File(directory);
        if (file.exists() && !file.isDirectory()) { // file is file, not folder
            return false;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            try {
                File temp = new File(file.getAbsolutePath() + File.separator + "test_temp.txt");
                if (temp.exists()) {
                    temp.delete();
                }
                temp.createNewFile();
                temp.delete();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static File[] getExternalCacheDirs(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return ContextCompatKitKat.getExternalCacheDirs(context);
        } else {
            return new File[]{context.getExternalCacheDir()};
        }
    }

    @TargetApi(19)
    static class ContextCompatKitKat {
        public static File[] getExternalCacheDirs(Context context) {
            return context.getExternalCacheDirs();
        }
    }
}

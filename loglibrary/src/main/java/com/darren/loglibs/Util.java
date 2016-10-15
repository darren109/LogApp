package com.darren.loglibs;

import android.text.TextUtils;
import android.util.Log;

import com.darren.loglibs.utils.FileLogUtils;

/**
 * Created by Darren on 15/12/11.
 */
public class Util {

    public static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }

    public static void printLine(String tag, boolean isTop) {
        String str;
        if (isTop) {
            str = "╔═════════════════════════════════════════════════════════════════════";
        } else {
            str = "╚═════════════════════════════════════════════════════════════════════";
        }
        Log.d(tag, str);
        FileLogUtils.writeTxtToFile(FileLogUtils.pingStr(tag + "/D", str), FileLogUtils.getExternalStoragePath(), Constant.LOGLOG);
    }

}

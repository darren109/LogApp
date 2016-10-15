package com.darren.loglibs.log;

import android.util.Log;

import com.darren.loglibs.Constant;
import com.darren.loglibs.ToolLog;
import com.darren.loglibs.utils.FileLogUtils;


/**
 * Created by Darren on 15/11/18.
 */
public class BaseLog {

    public static void printDefault(int type, String tag, String msg) {

        int index = 0;
        int maxLength = 4000;
        int countOfSub = msg.length() / maxLength;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                printSub(type, tag, sub);
                index += maxLength;
            }
            printSub(type, tag, msg.substring(index, msg.length()));
        } else {
            printSub(type, tag, msg);
        }
    }

    private static void printSub(int type, String tag, String sub) {
        String oType = "";
        switch (type) {
            case ToolLog.V:
                oType = "/V";
                Log.v(tag, sub);
                break;
            case ToolLog.D:
                oType = "/D";
                Log.d(tag, sub);
                break;
            case ToolLog.I:
                oType = "/I";
                Log.i(tag, sub);
                break;
            case ToolLog.W:
                oType = "/W";
                Log.w(tag, sub);
                break;
            case ToolLog.E:
                oType = "/E";
                Log.e(tag, sub);
                break;
            case ToolLog.A:
                oType = "/WTF";
                Log.wtf(tag, sub);
                break;
            default:
                oType = "";
        }
        FileLogUtils.writeTxtToFile(FileLogUtils.pingStr(tag + oType, "║ " + sub), FileLogUtils.getExternalStoragePath(), Constant.LOGLOG);
    }

}

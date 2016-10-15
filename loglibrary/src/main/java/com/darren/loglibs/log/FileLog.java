package com.darren.loglibs.log;

import android.util.Log;

import com.darren.loglibs.Constant;
import com.darren.loglibs.utils.FileLogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by Darren on 15/11/18.
 */
public class FileLog {

    public static void printFile(String tag, File targetDirectory, String fileName, String headString, String msg) {

        fileName = (fileName == null) ? getFileName() : fileName;
        String str;
        if (save(tag, targetDirectory, fileName, msg)) {
            str = headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName;
        } else {
            str = headString + "save log fails !";
        }
        Log.d(tag, str);
        FileLogUtils.writeTxtToFile(FileLogUtils.pingStr(tag + "/D", "║ " + str), FileLogUtils.getExternalStoragePath(), Constant.LOGLOG);
    }

    private static boolean save(String tag, File dic, String fileName, String msg) {
        int count = 0;
        while (!dic.exists()) {
            count++;
            dic.mkdir();
            if (count > 10) {
                break;
            }
        }
        if (dic.exists()) {
            String str = "make directory >>>" + dic.getAbsolutePath();
            Log.d(tag, str);
            FileLogUtils.writeTxtToFile(FileLogUtils.pingStr(tag + "/D", "║ " + str), FileLogUtils.getExternalStoragePath(), Constant.LOGLOG);
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

    private static String getFileName() {
        Random random = new Random();
        return "KLog_" + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + ".txt";
    }

}

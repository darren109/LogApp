package com.darren.loglibs.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class UIUtil {

    /**
     * 膨胀指定的布局资源
     */
    public static View inflate(Context context, int resId) {
        return LayoutInflater.from(context).inflate(
                resId, null);
    }

    /**
     * 膨胀指定的布局资源
     */
    public static View inflate(Context context, int resId, ViewGroup parent, boolean flag) {
        return LayoutInflater.from(context).inflate(
                resId, parent, flag);
    }

    /**
     * 获取文字
     */
    public static String getString(Context context, int resId) {
        return context.getString(resId);
    }

}

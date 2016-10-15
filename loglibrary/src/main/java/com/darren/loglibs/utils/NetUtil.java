package com.darren.loglibs.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	/**
	 * 判断网络是否可用
	 */
	public static boolean networkAvailable(Context context){
		ConnectivityManager  manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo info  = manager.getActiveNetworkInfo();  //有效的，活跃的网络信息
		
		if (manager!=null && info!=null){
			if (info.getTypeName().equals("WIFI")){
				System.out.println("在WIFI状态");
			}else if (info.getTypeName().equals("MOBILE")){ //移动
				System.out.println("在3G/4G流量状态");
			}
			return true;
		}
		return false;
	}
}

package com.yunxiang.shopkeeper.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.biz.AppBiz;

public class AndroidUtils {
	
	public static void show(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	public static  int getThisAppVersionInt(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		int version = 0;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			String versionString = packInfo.versionName;
			String end = versionString.substring(versionString.length()-1);
			version = StringUtils.getNumber(versionString);
			if(end.equals("T") || end.equals("t")){
				version -= 2;
			}
			//version = Double.valueOf(packInfo.versionName);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return version;
	}

	/**
	 * desc: 获取当前版本
	 * auther:jiely
	 * create at 2015/11/24 10:00
	 */
	public static  String getThisAppVersion() {
		Context context = TApplication.context;
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			String versionString = packInfo.versionName;
			return versionString;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * desc: APP版本升级
	 * auther:jiely
	 * create at 2015/11/4 11:06
	 */
	public static void updateAPP(Context context){
		try {
			//获取服务器中app的版本号
			if(!HttpUtils.isWifi(context)){
				return;
			}
			AppBiz appBiz = new AppBiz(context);//.downApkAndInstall();
			appBiz.downApkAndInstall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

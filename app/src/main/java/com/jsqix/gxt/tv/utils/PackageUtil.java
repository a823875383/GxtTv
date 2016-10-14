package com.jsqix.gxt.tv.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

public class PackageUtil {
	final static String TAG = PackageUtil.class.getSimpleName();

	public static String getDevice(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getDeviceId();
	}

	public static String getPhone(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String phone = mTelephonyMgr.getLine1Number();
		return StringUtils.isEmpty(phone) == true ? "" : phone.replace("+86",
				"");
	}

	public static String getPtype() {
		return android.os.Build.MODEL;
	}

	public static String getImsi(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getSubscriberId();
	}

	public static String getOsid(Context context) {
		return Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);
	}

	public static String getMac(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wifi.getConnectionInfo().getMacAddress();
	}

	public static String getIpAddr(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		int ipAddress = wifi.getConnectionInfo().getIpAddress();
		if (ipAddress == 0)
			return null;
		return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
				+ (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
	}

	/*
	 * 检测是否安装了指定应用
	 */
	public static boolean isAvilible(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++) {
			if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
				return true;
		}
		return false;
	}

	/*
	 * 打开apk
	 */
	public static void startApk(Context context, String packageName) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(
				packageName);
		if (intent != null) {
			context.startActivity(intent);
		} else {
			Log.v(TAG, "失败");
		}
	}
}

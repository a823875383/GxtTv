package com.jsqix.gxt.tv.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.Context;

import java.util.List;

public class ProcessUtils {

	/**
	 * 用来应用是否运行
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isTopActivity(Context context) {
		String packageName = context.getPackageName();
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			System.out.println("---------------包名-----------"
					+ tasksInfo.get(0).topActivity.getPackageName());
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity
					.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用来应用是否后台运行
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackgroundRunning(Context context) {
		String processName = context.getPackageName();
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		KeyguardManager keyguardManager = (KeyguardManager) context
				.getSystemService(Activity.KEYGUARD_SERVICE);

		if (activityManager == null)
			return false;
		// get running application processes
		List<RunningAppProcessInfo> processList = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo process : processList) {
			if (process.processName.startsWith(processName)) {
				boolean isBackground = process.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
						&& process.importance != RunningAppProcessInfo.IMPORTANCE_VISIBLE;
				boolean isLockedState = keyguardManager
						.inKeyguardRestrictedInputMode();// 锁屏状态
				if (isBackground || isLockedState)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	/**
	 * 用来应用是否后台运行
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackgroundRunning(Context context,
			String packageName) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		KeyguardManager keyguardManager = (KeyguardManager) context
				.getSystemService(Activity.KEYGUARD_SERVICE);
		if (activityManager == null)
			return false;
		// get running application processes
		List<RunningAppProcessInfo> processList = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo process : processList) {
			if (process.processName.equals(packageName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用来判断服务是否后台运行
	 * 
	 * @param context
	 * @param className
	 *            判断的服务名字
	 * @return true 在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean IsRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				IsRunning = true;
				break;
			}
		}
		return IsRunning;
	}
}

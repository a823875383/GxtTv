package com.jsqix.gxt.tv.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class SdUtils {
	/**
	 * 获取外置SD卡路径
	 * 
	 * @return
	 */
	public static List<String> getSDCardPaths() {
		List<String> sdcardPaths = new ArrayList<String>();
		String cmd = "cat /proc/mounts";
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
		try {
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

			String lineStr;
			while ((lineStr = inBr.readLine()) != null) {
				// 获得命令执行后在控制台的输出信息
				Log.i("CommonUtil:getSDCardPath", lineStr);

				String[] temp = TextUtils.split(lineStr, " ");
				// 得到的输出的第二个空格后面是路径
				String result = temp[1];
				File file = new File(result);
				if (file.isDirectory() && file.canRead() && file.canWrite()) {
					Log.d("directory can read can write:",
							file.getAbsolutePath());
					// 可读可写的文件夹未必是sdcard，我的手机的sdcard下的Android/obb文件夹也可以得到
					sdcardPaths.add(result);

				}

				// 检查命令是否执行失败。
				if (p.waitFor() != 0 && p.exitValue() == 1) {
					// p.exitValue()==0表示正常结束，1：非正常结束
					Log.e("CommonUtil:getSDCardPath", "命令执行失败!");
				}
			}
			inBr.close();
			in.close();
		} catch (Exception e) {
			Log.e("CommonUtil:getSDCardPath", e.toString());

			sdcardPaths.add(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
		}

		optimize(sdcardPaths);
		for (Iterator iterator = sdcardPaths.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			Log.e("清除过后", string);
		}
		Logger gLogger = Logger.getLogger("SdUtils");
		gLogger.info(new Gson().toJson(sdcardPaths));
		return sdcardPaths;
	}

	private static void optimize(List<String> sdcaredPaths) {
		if (sdcaredPaths.size() == 0) {
			return;
		}
		int index = 0;
		while (true) {
			if (index >= sdcaredPaths.size() - 1) {
				String lastItem = sdcaredPaths.get(sdcaredPaths.size() - 1);
				for (int i = sdcaredPaths.size() - 2; i >= 0; i--) {
					if (sdcaredPaths.get(i).contains(lastItem)) {
						sdcaredPaths.remove(i);
					}
				}
				return;
			}

			String containsItem = sdcaredPaths.get(index);
			for (int i = index + 1; i < sdcaredPaths.size(); i++) {
				if (sdcaredPaths.get(i).contains(containsItem)) {
					sdcaredPaths.remove(i);
					i--;
				}
			}

			index++;
		}

	}

	/**
	 * 获取USB路径
	 * 
	 * @return
	 */
	public static List<String> getUsbPaths(String path) {
		List<String> sdcardPaths = new ArrayList<String>();
		File file = new File(path);
		if (file.exists()) {
			File[] subFile = file.listFiles();
			for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
				if (subFile[iFileLength].isDirectory()) {
					sdcardPaths.add(subFile[iFileLength].getAbsolutePath());
					// File child[] = subFile[iFileLength].listFiles();
					// if (child.length > 0) {
					// sdcardPaths.add("/mnt/usb/"
					// + subFile[iFileLength].getName());
					// }
				}
			}
		}
		return sdcardPaths;
	}

	public final static String USBROOT = "/mnt/usb";
}

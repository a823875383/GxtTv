package com.jsqix.gxt.tv.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Constant {
	public static String downUrl = "";
	private int width;
	private int height;
	static Constant instance;
	public static final String DATE_FORMAT = "yyyyMMdd";
	public static final String TIME_FORMAT = "HHmmss";
	public static final String DATETIME_FORMAT = DATE_FORMAT + TIME_FORMAT;

	public Constant(Context context) {
		width = ((Activity) context).getWindowManager().getDefaultDisplay()
				.getWidth();
		height = ((Activity) context).getWindowManager().getDefaultDisplay()
				.getHeight();
		Logger logger = SDLogUtils.getLogger("Constant");
		logger.info("width:" + width + "\t" + "height:" + height);
	}

	public static Constant getInstance(Context context) {
		if (instance == null)
			instance = new Constant(context);
		return instance;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/*
	 * Get the TV MacAddress
	 */
	public static String getMacAddress(int channel) {
		String aMac = "";
		if (channel == Channel.BOX) {
			aMac= ChipInfo.getChipIDHex();
		} else {
			try {
				aMac = loadFileAsString("/sys/class/net/eth0/address");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (aMac==null||aMac.length() == 0)
			aMac = getMACAddress(null);

		return aMac;
	}

	private static String loadFileAsString(String filePath)
			throws IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath),
				2 * 1024);
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	/**
	 * Returns MAC address of the given interface name.
	 * 
	 * @param interfaceName
	 *            eth0, wlan0 or NULL=use first interface
	 * @return mac address or empty string
	 */
	@SuppressLint("NewApi")
	public static String getMACAddress(String interfaceName) {
		int API_VER_9 = 9;
		if (android.os.Build.VERSION.SDK_INT < API_VER_9)
			return "";

		try {
			List<NetworkInterface> interfaces = Collections
					.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName))
						continue;
				}
				byte[] mac = intf.getHardwareAddress();
				if (mac == null)
					continue;
				StringBuilder buf = new StringBuilder();
				for (int idx = 0; idx < mac.length; idx++)
					buf.append(String.format("%02X:", mac[idx]));
				if (buf.length() > 0)
					buf.deleteCharAt(buf.length() - 1);
				return buf.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // for now eat exceptions
		return "";
		/*
		 * try { // this is so Linux hack return
		 * loadFileAsString("/sys/class/net/" +interfaceName +
		 * "/address").toUpperCase().trim(); } catch (IOException ex) { return
		 * null; }
		 */
	}

	public static String getCurrentDatetimeString() {
		return dateToString(new Date(), DATETIME_FORMAT);
	}

	public static String dateToString(Date date, String pattern) {
		if (date == null)
			return null;
		SimpleDateFormat dateFormat = getDateFormat(pattern);
		return dateFormat.format(date);
	}

	private static SimpleDateFormat getDateFormat(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern,
				Locale.getDefault());
		simpleDateFormat.setTimeZone(TimeZone.getDefault()); // Use UTC timezone
																// throughout
		return simpleDateFormat;
	}

	public static Bitmap Create2DCode(String str, int size)
			throws WriterException {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, size, size);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}

			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 在二维码上绘制头像
	 */
	public static void createQRCodeBitmapWithPortrait(Bitmap qr, Bitmap portrait) {

		int QRCODE_SIZE = (qr.getWidth() + qr.getHeight()) / 2;
		// 头像图片的大小
		int portrait_W = portrait.getWidth() > QRCODE_SIZE / 5 ? QRCODE_SIZE / 5
				: portrait.getWidth();
		int portrait_H = portrait.getHeight() > QRCODE_SIZE / 5 ? QRCODE_SIZE / 5
				: portrait.getHeight();

		// 设置头像要显示的位置，即居中显示
		int left = (QRCODE_SIZE - portrait_W) / 2;
		int top = (QRCODE_SIZE - portrait_H) / 2;
		int right = left + portrait_W;
		int bottom = top + portrait_H;
		Rect rect1 = new Rect(left, top, right, bottom);

		// 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
		Canvas canvas = new Canvas(qr);

		// 设置我们要绘制的范围大小，也就是头像的大小范围
		Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
		// 开始绘制
		canvas.drawBitmap(portrait, rect2, rect1, null);
	}

	public interface Channel {
		int TV = 1;
		int BOX = 2;
	}
}

package com.jsqix.gxt.tv.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;

public class BitmapHelp {

	static BitmapUtils bitmapUtils;
	static ImageLoader imageLoader;
	final static int memoryCacheSize = 1024 * 1024 * 4; // 4MB
	final static int diskCacheSize = 1024 * 1024 * 10; // 10M
	final static long defaultCacheExpiry = 1000L * 60 * 60 * 24 * 10; // 10 days

	public static BitmapUtils getBitmap(Context context) {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(context,
					Environment.getExternalStorageDirectory() + File.separator
							+ "dianwo" + File.separator + "client"
							+ File.separator + "acahe" + File.separator
							+ "bitmapsdcash", memoryCacheSize, diskCacheSize);
			bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
			bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
					.getScreenSize(context).scaleDown(3));
			bitmapUtils.configDefaultShowOriginal(false);
			bitmapUtils.configMemoryCacheEnabled(true);
			bitmapUtils.configDiskCacheEnabled(true);
			bitmapUtils.configThreadPoolSize(5);
			bitmapUtils.configDefaultCacheExpiry(defaultCacheExpiry);
			// instance.configDefaultReadTimeout(1000);
		}
		return bitmapUtils;
	}

	public static ImageLoader getImage(Context context) {
		if (imageLoader == null) {
			imageLoader = ImageLoader.getInstance();
			DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
					.cacheInMemory(false) // 设置图片不缓存于内存中
					.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的质量
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 设置图片的缩放类型，该方法可以有效减少内存的占用
					.build();
			ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
					context).threadPoolSize(5)
					.defaultDisplayImageOptions(defaultOptions)
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new WeakMemoryCache())
					.memoryCacheSize(4 * 1024 * 1024)
					.diskCacheSize(10 * 1024 * 1024).diskCacheFileCount(100)
					.writeDebugLogs().build();
			imageLoader.init(configuration);
		}
		return imageLoader;
	}
	
	public static boolean isImage(String filepath) {
		String type = getMIMEType(filepath);
		if ("image".equals(type)) {
			return true;
		}
		return false;
	}
	public static boolean isVideo(String filepath) {
		String type = getMIMEType(filepath);
		if ("video".equals(type)||"audio".equals(type)) {
			return true;
		}
		return false;
	}
	
	private static String getMIMEType(String filepath) {
		String type = "";
		// 获得文件扩展名
		String end = filepath.substring(filepath.lastIndexOf(".") + 1,
				filepath.length()).toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4") || end.equals("avi")
				|| end.equals("rmvb") || end.equals("flv") || end.equals("mov")
				|| end.equals("mkv")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image";
		} else if (end.equals("apk")) {
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}
		return type;
	}
	public static String getImagePath(Context context, String url) {
		// TODO Auto-generated method stub
		if (url == null)
			return "";
		String imagePath = "";
		String fileName = "";

		if (url != null && url.length() != 0) {
			fileName = url.substring(url.lastIndexOf("/") + 1);
		}

		com.jsqix.gxt.tv.utils.FileCacheUtils fileCacheUtils = new com.jsqix.gxt.tv.utils.FileCacheUtils(context);
		String absolutePath = fileCacheUtils.getCacheDirectory();
		// filename 图片的名字
		imagePath = absolutePath + "/" + fileName;
		File dir = new File(absolutePath);
		if (!dir.exists()) {
			dir.mkdir();
		} else {
			// 视情况清除部分缓存
			fileCacheUtils.removeCache(absolutePath);
		}
		File file = new File(absolutePath, fileName);//
		if (!file.exists()) {
			com.jsqix.gxt.tv.utils.ImageDownloader.getIntance().downloadFile(url, file);
			return "";
		} else {
			return imagePath;
		}

	}
}

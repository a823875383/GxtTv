package com.jsqix.gxt.tv.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @author Rowand jj 下载图片的工具类 通过downloadImage方法下载图片，并将图片保存到缓存中（使用线程池）。
 *         对下载得到的图片交由一个回调接口OnImageDownloadListener处理 通过showCacheImage方法获取缓存中的图片
 */
public class ImageDownloader {
	/**
	 * 下载image的线程池
	 */
	private ExecutorService mImageThreadPool = null;

	/**
	 * 线程池中线程的数量
	 */
	private static final int THREAD_NUM = 5;

	private Context context;
	private static ImageDownloader downloader;

	/**
	 * 构造器
	 * 
	 * @param context
	 */
	public ImageDownloader(Context context) {
		this.context = context;
	}

	public ImageDownloader() {

	}

	public static ImageDownloader getIntance() {
		if (downloader == null) {
			downloader = new ImageDownloader();
		}
		return downloader;
	}

	/**
	 * 
	 * @param url
	 * @param listener
	 * @return
	 */
	public void downloadFile(final String url, final File file) {

		getThreadPool().execute(new Runnable()// 从线程池中获取一个线程执行下载操作并将下载后的图片加到文件缓存和内存缓存
				{
					@Override
					public void run() {
//						getImageFromUrl(url, file);// 从网络上下载图片
						HttpUtils http = new HttpUtils();
						String sdPath=file.getParent()+"/temp"+file.getName();
						http.download(url,sdPath,true,true, new RequestCallBack<File>() {

							@Override
							public void onSuccess(ResponseInfo<File> arg0) {
								Log.v("download", "sucess");
								arg0.result.renameTo(file);
							}


							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								Log.v("download", "fail");
							}

							
						});

					}
				});

		// }
	}

	/**
	 * 获取线程池实例
	 */
	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					mImageThreadPool = Executors.newFixedThreadPool(THREAD_NUM);
				}
			}
		}
		return mImageThreadPool;
	}

	/**
	 * 从url中获取bitmap
	 * 
	 * @param url
	 * @return
	 */
	public void getImageFromUrl(String url, File file) {
		try {
//			byte[] data = readInputStream(getRequest(url));
//			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			Bitmap bitmap=BitmapFactory.decodeStream(getRequest(url));
			bitmap.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(file));
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
		} catch (Exception e) {
			// Log.e(TAG, e.toString());
		}
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	private InputStream getRequest(String path) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		if (conn.getResponseCode() == 200) {
			Log.v("conn requst", "sucess");
			return conn.getInputStream();
		}
		Log.v("conn requst", "fail");
		return null;
	}

}
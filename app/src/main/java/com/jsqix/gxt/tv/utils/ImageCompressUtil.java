package com.jsqix.gxt.tv.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;

public class ImageCompressUtil {
	/**
	 * 通过降低图片的质量来压缩图片
	 * 
	 * @param bmp
	 *            要压缩的图片
	 * @param maxSize
	 *            压缩后图片大小的最大值,单位KB
	 * @return 压缩后的图片
	 */
	public static Bitmap compressByQuality(Bitmap bitmap, int maxSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(CompressFormat.JPEG, quality, baos);
		System.out.println("图片压缩前大小：" + baos.toByteArray().length + "byte");
		while (baos.toByteArray().length / 1024 > maxSize) {
			quality -= 10;
			baos.reset();
			bitmap.compress(CompressFormat.PNG, quality, baos);
			System.out.println("质量压缩到原来的" + quality + "%时大小为："
					+ baos.toByteArray().length + "byte");
		}
		System.out.println("图片压缩后大小：" + baos.toByteArray().length + "byte");
		bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
				baos.toByteArray().length);
		return bitmap;
	}

	/**
	 * 通过压缩图片的尺寸来压缩图片大小
	 * 
	 * @param pathName
	 *            图片的完整路径
	 * @param targetWidth
	 *            缩放的目标宽度
	 * @param targetHeight
	 *            缩放的目标高度
	 * @return 缩放后的图片
	 */
	public static Bitmap compressBySize(String pathName, int targetWidth,
			int targetHeight) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
		Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
		// 得到图片的宽度、高度；
		int imgWidth = opts.outWidth;
		int imgHeight = opts.outHeight;
		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		if (widthRatio > 1 || widthRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内容；
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(pathName, opts);
		return bitmap;
	}

	/**
	 * 通过压缩图片的尺寸来压缩图片大小
	 * 
	 * @param bitmap
	 *            要压缩图片
	 * @param targetWidth
	 *            缩放的目标宽度
	 * @param targetHeight
	 *            缩放的目标高度
	 * @return 缩放后的图片
	 */
	public static Bitmap compressBySize(Bitmap bitmap, int targetWidth,
			int targetHeight) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			bitmap.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
				baos.toByteArray().length, opts);
		opts.inJustDecodeBounds = false;
		// 得到图片的宽度、高度；
		int imgWidth = opts.outWidth;
		int imgHeight = opts.outHeight;
		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		if (widthRatio > 1 && heightRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内存；
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
				baos.toByteArray().length, opts);
		return bitmap;
	}

	public static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		newOpts.inPreferredConfig = Config.RGB_565;// 降低图片从ARGB888到RGB565
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 通过压缩图片的尺寸来压缩图片大小，通过读入流的方式，可以有效防止网络图片数据流形成位图对象时内存过大的问题；
	 * 
	 * @param InputStream
	 *            要压缩图片，以流的形式传入
	 * @param targetWidth
	 *            缩放的目标宽度
	 * @param targetHeight
	 *            缩放的目标高度
	 * @return 缩放后的图片
	 * @throws IOException
	 *             读输入流的时候发生异常
	 */
	public static Bitmap compressBySize(InputStream is, int targetWidth,
			int targetHeight) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int len = 0;
		while ((len = is.read(buff)) != -1) {
			baos.write(buff, 0, len);
		}

		byte[] data = baos.toByteArray();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
				opts);
		// 得到图片的宽度、高度；
		int imgWidth = opts.outWidth;
		int imgHeight = opts.outHeight;
		// 分别计算图片宽度、高度与目标宽度、高度的比例；取大于该比例的最小整数；
		int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
		int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
		if (widthRatio > 1 && widthRatio > 1) {
			if (widthRatio > heightRatio) {
				opts.inSampleSize = widthRatio;
			} else {
				opts.inSampleSize = heightRatio;
			}
		}
		// 设置好缩放比例后，加载图片进内存；
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		return bitmap;
	}

	public static String getImagePath(String url) {
		// TODO Auto-generated method stub
		if (url == null)
			return "";
		String imagePath = "";
		String fileName = "";

		if (url != null && url.length() != 0) {
			fileName = url.substring(url.lastIndexOf("/") + 1);
		}

		String absolutePath = android.os.Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/"
				+ "zl400" + "/" + "img";
		// filename 图片的名字
		imagePath = absolutePath + "/" + fileName;
		// Log.i(TAG, "imagePath = " + imagePath);
		File dir = new File(absolutePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File file = new File(absolutePath, fileName);//
		if (!file.exists()) {
			// Log.i(TAG, "file ������ ");

			try {
				byte[] data = readInputStream(getRequest(url));
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);

				bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(
						file));

				imagePath = file.getAbsolutePath();
				// Log.i(TAG, "imagePath : file.getAbsolutePath() = " +
				// imagePath);

			} catch (Exception e) {
				// Log.e(TAG, e.toString());
			}
		}
		return imagePath;
	}

	public static InputStream getRequest(String path) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		if (conn.getResponseCode() == 200) {
			return conn.getInputStream();
		}
		return null;
	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
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
}

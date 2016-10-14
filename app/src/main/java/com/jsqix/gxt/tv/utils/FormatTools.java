package com.jsqix.gxt.tv.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FormatTools {
	private static FormatTools tools = new FormatTools();

	private FormatTools() {
	}

	public static FormatTools getInstance() {
		if (tools == null) {
			tools = new FormatTools();
			return tools;
		}
		return tools;
	}

	// 将byte[]转换成InputStream
	public InputStream Byte2InputStream(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return bais;
	}

	// 将InputStream转换成byte[]
	public byte[] InputStream2Bytes(InputStream is) {
		try {
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			is.close();
			return outSteam.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 将Bitmap转换成InputStream
	public InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// 将Bitmap转换成InputStream
	public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// 将InputStream转换成Bitmap
	public Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	// Drawable转换成InputStream
	public InputStream Drawable2InputStream(Drawable d) {
		Bitmap bitmap = this.drawable2Bitmap(d);
		return this.Bitmap2InputStream(bitmap);
	}

	// InputStream转换成Drawable
	public Drawable InputStream2Drawable(InputStream is) {
		Bitmap bitmap = this.InputStream2Bitmap(is);
		return this.bitmap2Drawable(bitmap);
	}

	// Drawable转换成byte[]
	public byte[] Drawable2Bytes(Drawable d) {
		Bitmap bitmap = this.drawable2Bitmap(d);
		return this.Bitmap2Bytes(bitmap);
	}

	// byte[]转换成Drawable
	public Drawable Bytes2Drawable(byte[] b) {
		Bitmap bitmap = this.Bytes2Bitmap(b);
		return this.bitmap2Drawable(bitmap);
	}

	// Bitmap转换成byte[]
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// byte[]转换成Bitmap
	public Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	// Drawable转换成Bitmap
	public Bitmap drawable2Bitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
								: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	// Bitmap转换成Drawable
	public Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}

	public Bitmap getBitmapFromResource(Resources res, int id, int width,
			int height) {
		if (null != res) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeResource(res, id, opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeResource(res, id, opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Bitmap getBitmapFromStream(InputStream dst, int width, int height) {
		if (null != dst) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(dst, null, opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeStream(dst, null, opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Bitmap getBitmapFromFile(File dst, int width, int height) {
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 质量压缩方法：
	 * 
	 * @param image
	 * @return
	 */
	public Bitmap compressImage(Bitmap image, int maxsize) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 90;
		while (maxsize > 0 && baos.toByteArray().length / 1024 > maxsize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 质量压缩方法：
	 * 
	 * @param image
	 * @return
	 */
	public ByteArrayOutputStream compressByQuality(Bitmap image, int maxsize) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 90;
		while (maxsize > 0 && baos.toByteArray().length / 1024 > maxsize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		return baos;
	}

	/**
	 * 按比例大小压缩
	 * 
	 * @param image
	 * @return
	 */
	public Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
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
		return bitmap;
	}
}
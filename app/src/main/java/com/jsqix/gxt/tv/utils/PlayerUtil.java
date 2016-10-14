package com.jsqix.gxt.tv.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.jsqix.gxt.tv.BDVideoViewActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerUtil {

	private static PlayerUtil mInstence;
	private static Context activity;
	private long time;
	// private final long DELAY_TIME = 2 * 60 * 1000;
	private final long DELAY_TIME = 45 * 1000;
	private Timer timer = null;
	private boolean isRun = false;

	private TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
			long delay = System.currentTimeMillis() - time;
			if (delay > DELAY_TIME) {
				mHandler.sendEmptyMessage(0);
			}
		}
	};

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (timer == null)
				return;
			stopTask();
			Intent intent = new Intent(activity, BDVideoViewActivity.class);
			activity.startActivity(intent);
		};
	};

	private PlayerUtil() {
		time = System.currentTimeMillis();
	}

	public static PlayerUtil getInstence(Context activity) {
		if (mInstence == null) {
			mInstence = new PlayerUtil();
		}
		PlayerUtil.activity = activity;
		return mInstence;
	}

	public void refreshTime() {
		time = System.currentTimeMillis();
	}

	public void startTask() {
		if (isRun) {
			return;
		}
		if (timerTask == null) {
			timerTask = new TimerTask() {

				@Override
				public void run() {
					long delay = System.currentTimeMillis() - time;
					if (delay > DELAY_TIME) {
						mHandler.sendEmptyMessage(0);
					}
				}
			};
		}
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(timerTask, 0, 1 * 1000);
		isRun = true;
	}

	public void stopTask() {
		if (timer != null) {
			timer.cancel();
			timer = null;
			isRun = false;
		}
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
			isRun = false;
		}
	}

}

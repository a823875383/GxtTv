package com.jsqix.gxt.tv.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.downapk.DownServeStart;
import com.jsqix.gxt.tv.utils.SDLogUtils;

import org.apache.log4j.Logger;

public class PollingService extends Service {

	public static final String ACTION = "com.qinshun.service.PollingService";
	final String TAG = getClass().getName();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// new PollingThread().start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Logger gLogger = SDLogUtils.getLogger("GetUpdateState");
		gLogger.debug("check app");
		Log.i("GetUpdateState", "检查更新");
		new DownServeStart(getApplicationContext()).execute(intent,gLogger);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * Polling thread 模拟向Server轮询的异步线程
	 * 
	 * @Author Ryan
	 * @Create 2013-7-13 上午10:18:34
	 */

	class PollingThread extends Thread {
		@Override
		public void run() {
			// 检查更新
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// 要做的事情
			super.handleMessage(msg);
		}

	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Service:onDestroy");
	}

}
package com.jsqix.gxt.tv.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.jsqix.gxt.tv.recieve.InstallReceiver;


public class BroadCastService extends Service {
	private InstallReceiver receiver;
	AlarmManager mAlarmManager = null;
	PendingIntent mPendingIntent = null;

	@Override
	public void onCreate() {
		Intent intent = new Intent(getApplicationContext(),
				BroadCastService.class);
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mPendingIntent = PendingIntent.getService(this, 0, intent,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		long now = System.currentTimeMillis();
		mAlarmManager.setInexactRepeating(AlarmManager.RTC, now, 1000,
				mPendingIntent);
		super.onCreate();
		startScreenBroadcastReceiver();
	}

	private void startScreenBroadcastReceiver() {
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(Intent.ACTION_PACKAGE_ADDED);
		// filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		// filter.addDataScheme("package");
		 this.receiver = new InstallReceiver();
		// this.registerReceiver(this.receiver, filter);
		registerReceiver(receiver, new IntentFilter(Intent.ACTION_TIME_TICK));

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}
}

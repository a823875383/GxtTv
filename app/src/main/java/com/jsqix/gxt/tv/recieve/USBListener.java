package com.jsqix.gxt.tv.recieve;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jsqix.gxt.tv.BDVideoViewActivity;

public class USBListener extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		if (arg1.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)||arg1.getAction().equals(Intent.ACTION_MEDIA_EJECT)) {//挂载||卸载
			BDVideoViewActivity activity = BDVideoViewActivity.getsInstence();
			if (activity != null) {
				activity.getAdList().getList();
			}
		}
	}
}

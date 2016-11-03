package com.jsqix.gxt.tv.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class InstallReceiver extends BroadcastReceiver {
	private InstallReceiverListener listener;

	public void onReceive(Context c, Intent intent) {
		Log.v("GetUpdateState", intent.getAction());
		boolean state = false;
		if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED"))
			state = true;
		else if (intent.getAction().equals(
				"android.intent.action.PACKAGE_REMOVED")) {
			state = false;
		}
		String packageName = intent.getDataString();

		if ((packageName != null) && (packageName.length() != 0)
				&& (packageName.startsWith("package"))) {
			packageName = packageName.substring(8);
			if (packageName.equalsIgnoreCase("com.jsqx.dianwotv")
					&& state == true) {

			}
			// if ((packageName.equalsIgnoreCase("com.jsqx.dianwotv"))
			// && (this.listener != null))
			// this.listener.notifyInstallState(state);
		}
	}

	public void setIPOSUtilsListener(InstallReceiverListener listener) {
		this.listener = listener;
	}
}
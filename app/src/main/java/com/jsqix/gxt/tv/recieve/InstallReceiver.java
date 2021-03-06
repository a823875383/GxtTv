package com.jsqix.gxt.tv.recieve;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jsqix.gxt.tv.update.PackageUtils;
import com.jsqix.gxt.tv.update.ShellUtils.CommandResult;
import com.jsqix.gxt.tv.utils.ProcessUtils;

public class InstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context c, Intent intent) {

        Log.v("GetUpdateState", intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            if (!ProcessUtils.isRunning(c)) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        CommandResult commandResult = PackageUtils.startApk(
                                c.getPackageName(),
                                c.getPackageName() + ".activity.SplashActivity", true);
                        Log.e("GetUpdateState",
                                new StringBuilder()
                                        .append("installSilent result:")
                                        .append(commandResult.result)
                                        .append(", installSilent successMsg:")
                                        .append(commandResult.successMsg)
                                        .append(", ErrorMsg:")
                                        .append(commandResult.errorMsg)
                                        .toString());

                    }
                }).start();
            }
        }
    }

}
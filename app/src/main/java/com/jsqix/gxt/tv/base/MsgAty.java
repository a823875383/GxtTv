package com.jsqix.gxt.tv.base;


import android.app.Activity;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.jsqix.gxt.tv.BuildConfig;
import com.jsqix.gxt.tv.recieve.JPushReceiver;
import com.jsqix.gxt.tv.service.ScreenCapService;
import com.jsqix.gxt.tv.utils.ACache;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.PollingUtils;
import com.jsqix.gxt.tv.utils.ProcessUtils;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dongqing on 2016/10/24.
 */

public abstract class MsgAty extends Activity {
    private MessageReceiver mMessageReceiver;
    public ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aCache = ACache.get(this);
        registerMessageReceiver();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(JPushReceiver.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (JPushReceiver.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String message = intent.getStringExtra("message");
                try {
                    JSONObject object = new JSONObject(message);
                    if (ProcessUtils.isTopActivity(MsgAty.this)) {
                        if (StringUtils.toInt(object.get("dictate_type")) == 1004) {
                            PollingUtils.stopPollingService(MsgAty.this, ScreenCapService.class, ScreenCapService.DS_ACTION);
                            int interval = StringUtils.toInt(object.get("interval"));
                            aCache.put(KeyUtils.S_TIME, interval + "");
                            PollingUtils.startPollingService(MsgAty.this, AlarmManager.RTC, TimeUtils.getFirstTime(interval), interval >= 1 ? interval * 60 : TimeUtils.SCREENSHOT, ScreenCapService.class, ScreenCapService.DS_ACTION);
                        }

                        executeMessage(StringUtils.toInt(object.get("dictate_type")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    protected abstract void executeMessage(int instructions);

    /**
     * 开始实时截图服务
     */
    public void actualShot() {
        Intent i = new Intent(this, ScreenCapService.class);
        i.setAction(ScreenCapService.SS_ACTION);
        startService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ("jpush".equals(BuildConfig.FLAVOR)) {//极光
            JPushInterface.onResume(this);
        } else if ("baidu".equals(BuildConfig.FLAVOR)) {//百度云
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ("jpush".equals(BuildConfig.FLAVOR)) {//极光
            JPushInterface.onPause(this);
        } else if ("baidu".equals(BuildConfig.FLAVOR)) {//百度云
        }
    }
}

package com.jsqix.gxt.tv.activity;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushManager;
import com.jsqix.gxt.tv.BuildConfig;
import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.base.BaseAty;
import com.jsqix.gxt.tv.service.ScreenCapService;
import com.jsqix.gxt.tv.utils.Constant;
import com.jsqix.gxt.tv.utils.JPushUtil;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.PollingUtils;
import com.jsqix.gxt.tv.utils.SDLogUtils;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SplashActivity extends BaseAty {
    final String TAG = SplashActivity.class.getSimpleName();
    final static int MSG_SET_ALIAS = 0x6666, MSG_SET_TAGS = 0x8888;

    public static SplashActivity istance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        istance = this;
    }

    @Override
    protected void executeMessage(int instructions) {

    }


    @Override
    public void initView() {
        //应用启动时开始启动定时截屏服务
        PollingUtils.stopPollingService(this, ScreenCapService.class, ScreenCapService.DS_ACTION);
        int interval = StringUtils.toInt(aCache.getAsString(KeyUtils.S_TIME));
        PollingUtils.startPollingService(this, AlarmManager.RTC, TimeUtils.getFirstTime(interval), interval >= 1 ? interval * 60 : TimeUtils.SCREENSHOT, ScreenCapService.class, ScreenCapService.DS_ACTION);

        setAliasAndTag();
    }

    public void setAliasAndTag() {
        String alias = JPushUtil.getUUID(this, "", Constant.Channel.TV);
        if (TextUtils.isEmpty(alias)) {
            Log.i(TAG, "alias empty");
            SDLogUtils.getLogger("Splash").error("alias empty");
            return;
        }
        if (!JPushUtil.isValidTagAndAlias(alias)) {
            Log.i(TAG, "alias not valid");
            SDLogUtils.getLogger("Splash").error("alias not valid");
            return;
        }
        if ("jpush".equals(BuildConfig.FLAVOR)) {//极光
            setJpushAlias(alias);

        } else if ("baidu".equals(BuildConfig.FLAVOR)) {//百度云
            setBDTag(alias);

        }
    }

    private void setBDTag(String alias) {
        List<String> tags = new ArrayList<String>();
        tags.add(alias);
        PushManager.setTags(this, tags);
    }


    private void setJpushAlias(String alias) {
        SDLogUtils.getLogger("Splash").error("set alias");
        // 调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Log.d(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), null,
                            (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    SDLogUtils.getLogger("Splash").error(logs);
                    aCache.put(KeyUtils.MAC, Constant.getMacAddress(Constant.Channel.TV).replace(":", ""));
                    aCache.put(KeyUtils.TOKEN, JPushUtil.getUUID(SplashActivity.this, "", Constant.Channel.TV));
                    Log.i(TAG, logs);
                    jump2Login();
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    SDLogUtils.getLogger("Splash").error(logs);
                    if (JPushUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(
                                mHandler.obtainMessage(MSG_SET_TAGS, tags),
                                1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }

    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    SDLogUtils.getLogger("Splash").error(logs);
                    aCache.put(KeyUtils.MAC, Constant.getMacAddress(Constant.Channel.TV).replace(":", ""));
                    aCache.put(KeyUtils.TOKEN, JPushUtil.getUUID(SplashActivity.this, "", Constant.Channel.TV));
                    Log.i(TAG, logs);
                    jump2Login();
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    SDLogUtils.getLogger("Splash").error(logs);
                    if (JPushUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(
                                mHandler.obtainMessage(MSG_SET_ALIAS, alias),
                                1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }

    };

    public void jump2Login() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}

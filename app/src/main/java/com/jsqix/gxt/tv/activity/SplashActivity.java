package com.jsqix.gxt.tv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.jsqix.gxt.tv.R;
import com.jsqix.gxt.tv.base.BaseAty;
import com.jsqix.gxt.tv.utils.Constant;
import com.jsqix.gxt.tv.utils.JPushUtil;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.SDLogUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class SplashActivity extends BaseAty {
    final String TAG = SplashActivity.class.getSimpleName();
    final static int MSG_SET_ALIAS = 0x6666, MSG_SET_TAGS = 0x8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAlias();
    }

    private void setAlias() {
        String alias = JPushUtil.getUUID(this, "", Constant.Channel.BOX);
        if (TextUtils.isEmpty(alias)) {
            Log.i(TAG, "alias empty");
            SDLogUtils.getLogger(TAG).info("alias empty");
            return;
        }
        if (!JPushUtil.isValidTagAndAlias(alias)) {
            Log.i(TAG, "alias not valid");
            SDLogUtils.getLogger(TAG).info("alias not valid");
            return;
        }
        SDLogUtils.getLogger(TAG).info("set alias");
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
                    SDLogUtils.getLogger(TAG).info("Set tag and alias success");
                    aCache.put(KeyUtils.TOKEN,JPushUtil.getUUID(SplashActivity.this, "",Constant.Channel.BOX));
                    Log.i(TAG, logs);
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    SDLogUtils.getLogger(TAG).info("Failed to set alias and tags due to timeout. Try again after 60s.");
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
                    aCache.put(KeyUtils.TOKEN,JPushUtil.getUUID(SplashActivity.this, "",Constant.Channel.BOX));
                    Log.i(TAG, logs);
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
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
}

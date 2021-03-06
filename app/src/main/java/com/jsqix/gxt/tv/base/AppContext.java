package com.jsqix.gxt.tv.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.jsqix.gxt.tv.BuildConfig;
import com.jsqix.gxt.tv.service.BroadCastService;
import com.jsqix.gxt.tv.service.SilentUpdateService;
import com.jsqix.gxt.tv.utils.BaiduUtils;
import com.jsqix.gxt.tv.utils.CrashHandler;
import com.jsqix.gxt.tv.utils.DeviceUtils;
import com.jsqix.gxt.tv.utils.PollingUtils;
import com.jsqix.gxt.tv.utils.SDLogUtils;
import com.tencent.bugly.crashreport.CrashReport;

import org.apache.log4j.Logger;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;

public class AppContext extends Application {
    private Stack<Activity> activityStack;
    private static AppContext instance;

    public AppContext() {
        super();
    }

    @Override
    public void onCreate() {
        // 本地bug日志
        CrashHandler.getInstance().init(this);
        //腾讯bugly
        CrashReport.initCrashReport(this, "7d6521e11b", true);

        configLog();
        // 电视需要
        Intent i = new Intent(this, BroadCastService.class);
        startService(i);
        // 推送
        if ("jpush".equals(BuildConfig.FLAVOR)) {//极光
            JPushInterface.setDebugMode(BuildConfig.DEBUG);
            JPushInterface.init(this);
        } else if ("baidu".equals(BuildConfig.FLAVOR)) {//百度云
            PushManager.startWork(getApplicationContext(),
                    PushConstants.LOGIN_TYPE_API_KEY,
                    BaiduUtils.getMetaValue(this, "baidu_push_key"));
        }
        //开启自动更新功能
        PollingUtils.startPollingService(this, 1 * 60, SilentUpdateService.class, SilentUpdateService.ACTION);
        // 打印设备信息
        printDevice();

        super.onCreate();
    }

    private void printDevice() {
        Logger logger = SDLogUtils.getLogger("Device");
        logger.info("deviceInf:" + DeviceUtils.printDeviceInf());
    }

    private void configLog() {
        SDLogUtils.init();
    }

    public static AppContext getInstance() {
        if (null == instance) {
            instance = new AppContext();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}

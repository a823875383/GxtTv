package com.jsqix.gxt.tv.base;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.jsqix.gxt.tv.service.BroadCastService;
import com.jsqix.gxt.tv.service.ScreenCapService;
import com.jsqix.gxt.tv.utils.CrashHandler;
import com.jsqix.gxt.tv.utils.DeviceUtils;
import com.jsqix.gxt.tv.utils.PollingUtils;
import com.jsqix.gxt.tv.utils.SDLogUtils;
import com.jsqix.gxt.tv.utils.TimeUtils;

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
        // bug日志
        CrashHandler.getInstance().init(this);
        configLog();
        // 电视需要
        Intent i = new Intent(this, BroadCastService.class);
        startService(i);
        // 推送
        // JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        // 打印设备信息
        printDevice();
        //应用启动时开始启动定时截屏服务
        PollingUtils.startPollingService(this, AlarmManager.RTC, TimeUtils.getFirstTime(), TimeUtils.SCREENSHOT, ScreenCapService.class, ScreenCapService.DS_ACTION);

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

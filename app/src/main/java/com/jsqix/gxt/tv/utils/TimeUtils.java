package com.jsqix.gxt.tv.utils;

import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by dongqing on 2016/10/25.
 * 定时任务的时间
 */

public class TimeUtils {
    public final static long CHECK_UPDATE = 5 * 60 * 60;
    public final static long SCREENSHOT = 30 * 60;//半小时截图
    public final static long SCREEN_UPLOAD = 3 * 60 * 60;
    public final static long GET_ADS = 5 * 60 * 1000;//5分钟获取广告

    public static long getFirstTime(int minus) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然会有8个小时的时间差
        Log.v("TimeUtils now", DateUtil.dateToString(calendar.getTime(), DateUtil.DATETIME_FORMAT_DEFAUlt));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int time = hour * 60 + minute;//时间数
        if (minus > 1) {
            calendar.add(Calendar.MINUTE, minus - time % minus);
        } else {
            calendar.add(Calendar.MINUTE, 1);
        }
        // 选择的定时时间
        long selectTime = calendar.getTimeInMillis();
        Log.v("TimeUtils after", DateUtil.dateToString(calendar.getTime(), DateUtil.DATETIME_FORMAT_DEFAUlt));
        return selectTime;
    }

    public static long getFirstTime() {
        return getFirstTime(30);
    }
}

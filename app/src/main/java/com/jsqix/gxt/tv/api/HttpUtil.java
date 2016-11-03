package com.jsqix.gxt.tv.api;

import com.jsqix.gxt.tv.jni.UAD;

public class HttpUtil {

    public final static String ANDRID_SDK_KEY = UAD.getParaMd5Key();

    private final static String IP = UAD.getTestIp();
    public final static String LOGIN = IP + "login";
    public final static String SET_DEVICE_NAME = IP + "setDeviceName";
    public final static String GET_HOME = IP + "getIndex";
    public final static String GET_ADVERT = IP + "getAdvert";
    public final static String UP_SCREENSHOT = IP + "upldScreenshot";
    public final static String CHECK_VERSION = IP + "getLatestAPPVersion";
}

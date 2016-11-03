package com.jsqix.gxt.tv.jni;

/**
 * Created by dongqing on 2016/11/2.
 */

public class UAD {
    public static native String getParaMd5Key();

    public static native String getPswMd5Key();

    public static native String getRequestIp();

    public static native String getTestIp();

    static {
        System.loadLibrary("gxt");
    }
}

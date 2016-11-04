package com.jsqix.gxt.tv.utils;

import android.os.Build;
import android.util.Log;

public class DeviceUtils {

    public final static String PRODUCT = "rtd298x_tv038";
    public final static String BOARD = "rtd298x";
    public final static String BRAND = "SONIQ_98";

    final static String TAG = DeviceUtils.class.getSimpleName();


    public static String getDeviceProduct() {
        return android.os.Build.PRODUCT;
    }

    public static String getDeviceBoard() {
        return android.os.Build.BOARD;
    }

    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    public static String printDeviceInf() {
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCT ").append(android.os.Build.PRODUCT).append("\n");
        sb.append("BOARD ").append(android.os.Build.BOARD).append("\n");
        sb.append("SDK ").append(android.os.Build.VERSION.SDK_INT).append("\n");
        sb.append("ANDROID ").append(android.os.Build.VERSION.RELEASE).append("\n");
        sb.append("BOOTLOADER ").append(android.os.Build.BOOTLOADER)
                .append("\n");
        sb.append("BRAND ").append(android.os.Build.BRAND).append("\n");
        sb.append("CPU_ABI ").append(android.os.Build.CPU_ABI).append("\n");
        sb.append("CPU_ABI2 ").append(android.os.Build.CPU_ABI2).append("\n");
        sb.append("DEVICE ").append(android.os.Build.DEVICE).append("\n");
        sb.append("DISPLAY ").append(android.os.Build.DISPLAY).append("\n");
        sb.append("FINGERPRINT ").append(android.os.Build.FINGERPRINT)
                .append("\n");
        sb.append("HARDWARE ").append(android.os.Build.HARDWARE).append("\n");
        sb.append("HOST ").append(android.os.Build.HOST).append("\n");
        sb.append("ID ").append(android.os.Build.ID).append("\n");
        sb.append("MANUFACTURER ").append(android.os.Build.MANUFACTURER)
                .append("\n");
        sb.append("MODEL ").append(android.os.Build.MODEL).append("\n");
        sb.append("PRODUCT ").append(android.os.Build.PRODUCT).append("\n");
        sb.append("RADIO ").append(android.os.Build.RADIO).append("\n");
        sb.append("SERIAL ").append(android.os.Build.SERIAL).append("\n");
        sb.append("TAGS ").append(android.os.Build.TAGS).append("\n");
        sb.append("TIME ").append(android.os.Build.TIME).append("\n");
        sb.append("TYPE ").append(android.os.Build.TYPE).append("\n");
        sb.append("USER ").append(android.os.Build.USER).append("\n");
        Log.i(TAG, sb.toString());
        return sb.toString();
    }
}

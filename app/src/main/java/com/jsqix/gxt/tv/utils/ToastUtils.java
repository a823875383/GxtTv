package com.jsqix.gxt.tv.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dongqing on 2016/10/21.
 */

public class ToastUtils {
    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}

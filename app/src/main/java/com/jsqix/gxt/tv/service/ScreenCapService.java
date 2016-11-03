package com.jsqix.gxt.tv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.jsqix.gxt.tv.api.ApiClient;
import com.jsqix.gxt.tv.api.HttpUtil;
import com.jsqix.gxt.tv.obj.BaseObj;
import com.jsqix.gxt.tv.utils.ACache;
import com.jsqix.gxt.tv.utils.DateUtil;
import com.jsqix.gxt.tv.utils.FileCacheUtils;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.jsqix.gxt.tv.utils.ScreentShotUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScreenCapService extends Service {

    public static final String DS_ACTION = "com.qinshun.service.ScreenCapService.ds";
    public static final String SS_ACTION = "com.qinshun.service.ScreenCapService.ss";
    final String TAG = getClass().getName();
    private String imgPath = "";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && (intent.getAction() == DS_ACTION || intent.getAction() == SS_ACTION)) {
            //判断是否是定时截屏还是实时截屏
            FileCacheUtils fileCacheUtils = new FileCacheUtils(getApplicationContext());
            String regular = fileCacheUtils.getScreenRegular();
            String now = fileCacheUtils.getScreenNow();
            File dirRegular = new File(regular);
            File dirNow = new File(now);
            File[] files = dirNow.listFiles();
            if (files != null) {
                dirNow.delete();//删除实时截图中的文件
            }
            if (!dirNow.exists()) {
                dirNow.mkdirs();
            }
            if (!dirRegular.exists()) {
                dirRegular.mkdirs();
            } else {
                // 视情况清除部分缓存
                fileCacheUtils.removeCache(regular);
            }
            final boolean screenshot = intent.getAction() == DS_ACTION ? false : true;
            if (screenshot == false) {//定时截图
                imgPath = regular + "/" + DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS") + ".png";
            } else {//实时截图
                imgPath = now + "/" + DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS") + ".png";
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    ScreentShotUtil.getInstance().takeScreenshot(ScreenCapService.this, imgPath);
                    Log.i("ScreenCapService", imgPath);
                    //截图需要上传图片
                    final File file = new File(imgPath);
                    if (file.exists()) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("deviceId", ACache.get(ScreenCapService.this).getAsString(KeyUtils.S_ID));
                        String hmac = ApiClient.getSignAfter(map, HttpUtil.ANDRID_SDK_KEY);
                        map.put("hmac", hmac);

                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.configRequestRetryCount(3);
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("file", new File(imgPath), "image/*");
                        Iterator<Map.Entry<String, Object>> strings = map.entrySet().iterator();
                        while (strings.hasNext()) {
                            Map.Entry<String, Object> entry = strings.next();
                            params.addBodyParameter(entry.getKey(), entry.getValue() + "");
                        }
                        System.out.println(HttpUtil.UP_SCREENSHOT);
                        httpUtils.send(HttpRequest.HttpMethod.POST, HttpUtil.UP_SCREENSHOT, params, new RequestCallBack<String>() {
                            public void onSuccess(ResponseInfo<String> var1) {

                                BaseObj baseObj = new Gson().fromJson(var1.result, BaseObj.class);
                                if (baseObj != null && baseObj.getCode().equals("000")) {
                                    //上传成功
                                    Log.v("", "success");
                                    deleFile(file.getParent(), imgPath);
                                } else {
                                    //上传失败
                                    Log.v("", "failure");
                                }
                            }

                            public void onFailure(HttpException var1, String var2) {
                                //上传失败
                                Log.v("", "failure");
                            }
                        });
                    }
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void deleFile(String path, String delFile) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            int removeFactor = files.length;
            for (int j = 0; j < removeFactor; j++) {
                if (delFile.equals(path + files[j].getName())) {
                    files[j].delete();
                }
            }

        }
    }
}
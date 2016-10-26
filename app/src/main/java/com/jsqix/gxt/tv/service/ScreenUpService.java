package com.jsqix.gxt.tv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.jsqix.gxt.tv.api.ApiClient;
import com.jsqix.gxt.tv.api.HttpUtil;
import com.jsqix.gxt.tv.utils.ACache;
import com.jsqix.gxt.tv.utils.FileCacheUtils;
import com.jsqix.gxt.tv.utils.KeyUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dongqing on 16/9/19.
 */
public class ScreenUpService extends Service {
    public final static String ACTION = "com.qinshun.service.ScreenUpService";
    final static String TAG = ScreenUpService.class.getSimpleName();
    private String dir;
    private List<String> upFiles;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileCacheUtils fileCacheUtils = new FileCacheUtils(getApplicationContext());
        dir = fileCacheUtils.getScreenRegular();
        upload();
        return super.onStartCommand(intent, flags, startId);
    }

    synchronized private void upload() {
        List<String> files = getSrcFileName(dir);
        if (files.size() > 0) {
            if (files.size() > 5) {
                upFiles = new ArrayList<String>(files.subList(0, 5));
            } else {
                upFiles = new ArrayList<String>(files);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceId", ACache.get(this).getAsString(KeyUtils.S_ID));
        String hmac = ApiClient.getSignAfter(map, HttpUtil.ANDRID_SDK_KEY);
        map.put("hmac", hmac);
        if (upFiles != null && upFiles.size() > 0) {
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.configTimeout(100 * 1000);
//            httpUtils.configRequestRetryCount(3);
            RequestParams params = new RequestParams();
            for (int i = 0; i < upFiles.size(); i++) {
                params.addBodyParameter("mfile" + i, new File(upFiles.get(i)), "image/*");
            }

            Iterator<Map.Entry<String, Object>> strings = map.entrySet().iterator();
            while (strings.hasNext()) {
                Map.Entry<String, Object> entry = strings.next();
                params.addBodyParameter(entry.getKey(), entry.getValue() + "");
            }

            System.out.println(HttpUtil.UP_SCREENSHOT);
            httpUtils.send(HttpRequest.HttpMethod.POST, HttpUtil.UP_SCREENSHOT, params, new RequestCallBack<Object>() {
                @Override
                public void onSuccess(ResponseInfo<Object> responseInfo) {
                    Log.v("", "success");
                    //上传成功，递归上传
                    deleFile(dir, upFiles);
                    if (getSrcFileName(dir).size() > 0) {
                        upload();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.v("", "failure");
                    //上传失败重新上传
//                    upload();
                }
            });
        }
    }

    private List<String> getSrcFileName(String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        File dir = new File(path);
        File[] files = dir.listFiles();
        List<String> fileNames = new ArrayList<String>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                fileNames.add(path + files[i].getName());
                Log.d(TAG, fileNames.get(i));
            }
        }
        return fileNames;
    }

    private void deleFile(String path, List<String> del) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        File dir = new File(path);
        for (int i = 0; i < del.size(); i++) {
            File[] files = dir.listFiles();
            if (files != null) {
                int removeFactor = files.length;
                for (int j = 0; j < removeFactor; j++) {
                    if (del.get(i).equals(path + files[j].getName())) {
                        files[j].delete();
                    }
                }

            }
        }
    }
}

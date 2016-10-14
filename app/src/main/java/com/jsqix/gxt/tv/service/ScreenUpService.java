package com.jsqix.gxt.tv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.jsqix.gxt.tv.utils.FileCacheUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongqing on 16/9/19.
 */
public class ScreenUpService extends Service {
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

    private void upload() {
        List<String> files = getSrcFileName(dir);
        if (files.size() > 0) {
            if (files.size() > 5) {
                upFiles = new ArrayList<String>(files.subList(0, 5));
            }
        }
        if (upFiles != null && upFiles.size() > 0) {
            HttpUtils httpUtils = new HttpUtils();
            RequestParams params = new RequestParams();
            for (int i = 0; i < upFiles.size(); i++) {
                params.addBodyParameter("file" + i, new File(upFiles.get(i)));
            }
            httpUtils.send(HttpRequest.HttpMethod.POST, "", params, new RequestCallBack<Object>() {
                @Override
                public void onSuccess(ResponseInfo<Object> responseInfo) {
                    //上传成功，递归上传
                    deleFile(dir, upFiles);
                    upload();
                }

                @Override
                public void onFailure(HttpException e, String s) {

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

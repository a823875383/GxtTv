package com.jsqix.gxt.tv.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class JSONPost extends AsyncTask<String, String, String> {
    String response = "", resultString = "";
    int resultCode = 0;

    Context context;
    InterfaceJSONPost mListener;
    Map<String, Object> postMap;

    /**
     * @param context
     * @param params
     * @param listener
     */
    public JSONPost(Context context, Map<String, Object> params,
                    InterfaceJSONPost listener) {
        this.postMap = params;
        String hmac = ApiClient.getSignAfter(params, HttpUtil.ANDRID_SDK_KEY);
        postMap.put("hmac", hmac);
        this.mListener = listener;
        this.context = context;
    }

    public abstract void onPreExecute();

    public String doInBackground(String... urls) {
        return httpPost(urls);
    }

    public void onPostExecute(String response) {
        if (mListener != null) {
            mListener.postCallback(resultCode, response);
        }
    }

    @SuppressLint("NewApi")
    public String httpPost(String... urls) {
        try {
            for (String url : urls) {
                XutilsRequst(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("response is " + e.getMessage());
        }
        System.out.println("response is " + resultString);
        return resultString;
    }

    private void XutilsRequst(String url) throws Exception {
        HttpUtils http = new HttpUtils();
        http.configTimeout(30 * 1000);
        RequestParams params = new RequestParams();
        Iterator<Entry<String, Object>> strings = postMap.entrySet().iterator();
        while (strings.hasNext()) {
            Entry<String, Object> entry = strings.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List<?>) {
                List imgs = (List) value;
                for (Object img : imgs) {
                    File file = new File(img + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                }
            } else {
                if (key.contains("img")) {
                    File file = new File(value + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                } else {
                    params.addBodyParameter(key, value + "");
                }
            }
        }
        ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.POST, url, params);
        response = responseStream.readString();
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

}

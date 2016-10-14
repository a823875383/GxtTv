package com.jsqix.gxt.tv.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class JSONGet extends AsyncTask<String, String, String> {
    //	private final String PUSH_URL = "http://192.168.1.91:8888/api/";
    String response = "";
    int resultCode = 0;
    InterfaceJSONGet mListener;
    Context context;
    Map<String, Object> postMap;

    // Constructor
    public JSONGet(Context context, Map<String, Object> params, InterfaceJSONGet listener) {
        this.mListener = listener;
        this.postMap = params;
        this.context = context;
    }

    public abstract void onPreExecute();

    public void onPostExecute(String response) {
        if (mListener != null)
            mListener.getCallback(resultCode, response);
    }

    public String doInBackground(String... urls) {
        return getJSON(urls);
    }

    public String getJSON(String... urls) {
        try {
            for (String url : urls) {
                String getUrl = makeGetMessage(ApiClient.IP + url, postMap);
                XutilsRequst(getUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("response is " + e.getMessage());
        }

        System.out.println("response is " + response);
        return response;
    }

    private void XutilsRequst(String url) throws Exception {
        HttpUtils http = new HttpUtils();
        http.configTimeout(30 * 1000);
        ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.GET, url);
        response = responseStream.readString();
    }

    public void setResultCode(int code) {
        this.resultCode = code;
    }


    public static String makeGetMessage(String srcUrl, Map<String, Object> data)
            throws IOException {
        String url = null;
        if (data != null) {
            Log.v("XXXXX", "--------------------------");
            Log.i("签名前----", data.toString());
            String data2 = getSignAfter(data, HttpUtil.ANDRID_SDK_KEY);
            Log.i("签名后----", data2);
            data.put("hmac", data2);
            data2 = makePostData2(data);

            url = srcUrl + "?" + data2;
            Log.i("发送的请求----", url);
        } else {
            url = srcUrl;
        }
        return url;
    }

    private static String makePostData2(Map<String, Object> data)
            throws UnsupportedEncodingException {

        StringBuilder postData = new StringBuilder();

        for (String name : data.keySet()) {
            postData.append(name);
            postData.append('=');
            if (data.get(name) == null) {
                postData.append("");
            } else {
                postData.append(URLEncoder.encode(
                        URLEncoder.encode(data.get(name) + "", "utf-8"),
                        "utf-8"));
            }
            postData.append('&');
        }

        return postData.deleteCharAt(postData.lastIndexOf("&")).toString();
    }

    /**
     * map进行排序
     *
     * @param argMap
     * @return
     */
    private static String getSignAfter(Map<String, Object> argMap, String key) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        for (Entry<String, Object> tMap : argMap.entrySet()) {
            treeMap.put(tMap.getKey(), tMap.getValue() + "");
        }
        StringBuffer ret = new StringBuffer();
        for (Entry<String, String> map : treeMap.entrySet()) {
            System.out.println(map.getKey());
            ret.append(map.getKey());
            ret.append("=");
            String value = map.getValue();
            if (value == null)
                value = "";
            ret.append(value);
            ret.append("&");
        }
        if (ret.toString().endsWith("&")) {
            ret.delete(ret.toString().length() - 1, ret.toString().length());
        }
        String sign = getSign(ret.toString(), key);
        return sign;
    }

    /**
     * 签名
     *
     * @param signAfter
     * @return
     */
    private static String getSign(String signAfter, String key) {
        Log.v("加密前：", signAfter);
        String sign = Md5.getMD5(signAfter + key, "utf-8");
        Log.v("加密后：", sign);
        return sign;
    }

}

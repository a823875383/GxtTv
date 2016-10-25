package com.jsqix.gxt.tv.api;

import android.content.Context;
import android.os.AsyncTask;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Map;

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
                String getUrl = ApiClient.makeGetMessage(url, postMap);
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
}

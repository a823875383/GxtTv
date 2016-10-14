package com.jsqix.gxt.tv.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class JSONPost extends AsyncTask<String, String, String> {
	String response = "",  resultString = "";
	int resultCode = 0;

	Context context;
	InterfaceJSONPost mListener;
	Map<String, Object> postMap;

	/**
	 * 
	 * @param context
	 * @param params
	 * @param listener
	 */
	public JSONPost(Context context, Map<String, Object>params,
			InterfaceJSONPost listener) {
		this.postMap = params;
		String hmac = getSignAfter(params, HttpUtil.ANDRID_SDK_KEY);
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
				String postUrl  = ApiClient.IP + url;
				XutilsRequst(postUrl);
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
		ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.POST, url,params);
		response = responseStream.readString();
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 * map进行排序
	 * 
	 * @param argMap
	 * @return
	 */
	private static String getSignAfter(Map<String, Object> argMap,String key) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		for (Entry<String, Object> tMap : argMap.entrySet()) {
			treeMap.put(tMap.getKey(), tMap.getValue()+"");
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

package com.jsqix.gxt.tv.api.sceneryApi;

import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.jsqix.gxt.tv.api.InterfaceJSONGet;
import com.jsqix.gxt.tv.api.JSONGet;
import com.jsqix.gxt.tv.utils.SDLogUtils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BaiDuLocationHelper implements InterfaceJSONGet {

	private Context context;

	private LocationClient mLocationClient;

	private MyLocationListener mMyLocationListener;

	private Vibrator mVibrator;

	private String cityName = null;

	public BaiDuLocationHelper(Context context) {
		this.context = context;
		initLocation();
	}

	public void initLocation() {
		mLocationClient = new LocationClient(context);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mVibrator = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		option.setAddrType("all");
		mLocationClient.setLocOption(option);
	}

	public void startLocation() {
		if (mLocationClient != null) {
			mLocationClient.start();
		}
	}

	public void stopLocation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}

	/**
	 * 实现实位回调监听
	 */
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			String resultStr = location.getCity();
			if (resultStr != null) {
				Logger gLogger = SDLogUtils.getLogger("BDLocation");
				gLogger.info("Latitude:" + location.getLatitude());
				gLogger.info("Longitude:" + location.getLongitude());
				gLogger.info("City:" + resultStr);
				if (resultStr.endsWith("市")) {
					resultStr = resultStr.substring(0, resultStr.length() - 1);
					cityName = resultStr;
				}
			} else {
				getIPAddr();

			}
		}
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	private void getIPAddr() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeStamp", System.currentTimeMillis());
		JSONGet get = new JSONGet(context, map, this) {

			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub

			}
		};
		get.execute("getClientIp");

	}

	private void getLocateAddr(final String ip) {
		new AsyncTask<String, String, String>() {

			@Override
			protected String doInBackground(String... params) {
				String response = "";
				try {
					for (String url : params) {
						HttpClient httpClient = new DefaultHttpClient();
						String getUrl = url
								+ "?http://api.map.baidu.com/location/ip?ak=1Pavd5KcVSyhQCmjWlRp2Qp0&ip="
								+ ip
								+ "&coor=bd09ll&mcode=20:75:7B:00:D3:21:A9:CC:08:64:5A:A5:B0:2E:2D:CA:99:E7:42:43;com.jsqix.gxt.tv";
						HttpGet httpGet = new HttpGet(getUrl);
						InputStream content = httpClient.execute(httpGet)
								.getEntity().getContent();

						BufferedReader buffer = new BufferedReader(
								new InputStreamReader(content));
						String s = "";

						while ((s = buffer.readLine()) != null
								&& !Thread.interrupted()) {
							response += s;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				return response;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject content = jsonObject.getJSONObject("content");
					JSONObject point = content.getJSONObject("point");
					String resultStr = content.getJSONObject("address_detail")
							.getString("city");
					Logger gLogger = SDLogUtils.getLogger("BDLocation");
					gLogger.info("Latitude:" + point.getString("x"));
					gLogger.info("Longitude:" + point.getString("y"));
					gLogger.info("City:" + resultStr);
					if (resultStr != null) {
						if (resultStr.endsWith("市")) {
							resultStr = resultStr.substring(0,
									resultStr.length() - 1);
							cityName = resultStr;
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.execute("http://api.map.baidu.com/location/ip");

	}

	@Override
	public void getCallback(int resultCode, String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			if ("000".equals(jsonObject.getString("code"))) {
				String ipAddr = jsonObject.getString("obj");
				getLocateAddr(ipAddr);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}

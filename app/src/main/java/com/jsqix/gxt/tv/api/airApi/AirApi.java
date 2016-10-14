package com.jsqix.gxt.tv.api.airApi;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AirApi {
	public static void main(String[] args) {
//		FzSearch();
		// GetRefundAndEndorseRule();
	}

	/**
	 * post
	 */
	public static String GetRefundAndEndorseRule(JSONObject data) {
		String str = "";
		try {
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.format(new Date());
			ApiAuthUtil apiAuthUtil = new ApiAuthUtil("Flight", "Query",
					"GetRefundAndEndorseRule",
					"dbd36c84-6cd9-42c5-8ed6-39d03d0019e3", "2e0aec2c155dcb95",
					date, 92813);
			String url = apiAuthUtil
					.getApiURL("http://tcopenapi.17usoft.com/flight/local/Query/GetRefundAndEndorseRule");
			System.out.println(url);
			JSONObject obj = new JSONObject();
//			JSONObject data = new JSONObject();
			data.put("account", "40EB7E5BF55D56832EA2AF507AA9780B");
			data.put("channel", "1");
			data.put("passengerType", "1");
//			data.put("sysPrice", "3160");
//			data.put("sysSecondPrice", "0");
//			data.put("sysBasePrice", "3160");
//			data.put("fdPrice", "3160");
//			data.put("payPrice", "3160");
//			data.put("paySecondPrice", "0");
//			data.put("flightNo", "MU5102");
//			data.put("airCompany", "MU");
//			data.put("cabinSeat", "F");
//			data.put("baseCabinSeat", "F");
//			data.put("takeoffPort", "BJS");
//			data.put("arrivalPort", "SHA");
//			data.put("discount", "100");
//			data.put("takeoffDate", DateUtils.opDate(1));
//			data.put("ticketDate", DateUtils.getNow());
//			data.put("merchantId", "10763706");
//			data.put("policyId", "20184478");
//			data.put("policyType", "0");
//			data.put("subPolicyType", "0");
//			data.put("shapeType", "0");
//			data.put("fzProductCode", "1305070021");
			obj.put("data", data);
			URL urls = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urls
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.connect();

			PrintWriter pw = new PrintWriter(connection.getOutputStream());
			pw.print(new String(obj.toString().getBytes(), "utf-8"));
			pw.flush();
			InputStream in = connection.getInputStream(); // 取锟斤拷锟斤拷值
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "utf-8"));
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
			str=buffer.toString();
			System.out.println(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * post
	 */
	public static String FzSearch(JSONObject condition) {
		String str = "";
		try {
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.format(new Date());
			ApiAuthUtil apiAuthUtil = new ApiAuthUtil("Flight", "Query",
					"FzSearch", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
					"2e0aec2c155dcb95", date, 92813);
			String url = apiAuthUtil
					.getApiURL("http://tcopenapi.17usoft.com/flight/local/Query/FzSearch");
			System.out.println(url);
			JSONObject obj = new JSONObject();
			// JSONObject condition=new JSONObject();
			// condition.put("takeoffDate",DateUtils.opDate(3));
			// condition.put("takeoffAirportCode","NAY");
			// condition.put("arrivalAirportCode","SHA");
			// condition.put("queryType","1");
			condition.put("userIP", "127.0.0.1");
			obj.put("queryCondition", condition);
			URL urls = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urls
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.connect();

			PrintWriter pw = new PrintWriter(connection.getOutputStream());
			pw.print(new String(obj.toString().getBytes(), "utf-8"));
			pw.flush();
			InputStream in = connection.getInputStream(); // 取锟斤拷锟斤拷值
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "utf-8"));
			while ((str = reader.readLine()) != null) {
				buffer.append(str);
			}
			str = buffer.toString();
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}

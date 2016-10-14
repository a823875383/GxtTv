package com.jsqix.gxt.tv.api.hotelApi;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class HotelApi {
	public static String GetProvinceList() {
		Map<String, Object> param = new TreeMap<String, Object>();
		Map<String, Object> body = new TreeMap<String, Object>();
		String url = "";
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		url = "http://tcopenapi.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetProvinceList");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetProvinceList", "ReqTime=" + date };

		String[] sortedArray = Tools.BubbleSort(originalArray);

		String checkvalue = Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		return send(url, param, body);
	}

	public static String GetCityListByProvinceId(Map<String, Object> body) {
		Map<String, Object> param = new TreeMap<String, Object>();
		String url = "";
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		url = "http://tcopenapi.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetCityListByProvinceId");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetCityListByProvinceId", "ReqTime=" + date };

		String[] sortedArray = Tools.BubbleSort(originalArray);

		String checkvalue =Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		// body.put("provinceId", "16");// 苏州
		return send(url, param, body);
	}

	public static String GetCountyListByCityId(Map<String, Object> body) {
		Map<String, Object> param = new TreeMap<String, Object>();
		// Map<String, Object> body = new TreeMap<String, Object>();
		String url = "";
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		url = "http://tcopenapi.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetCountyListByCityId");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetCountyListByCityId", "ReqTime=" + date };

		String[] sortedArray =Tools.BubbleSort(originalArray);

		String checkvalue = Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		// body.put("cityId", "321");
		return send(url, param, body);
	}

	public static String GetHotelList(Map<String, Object> body) {
		Map<String, Object> param = new TreeMap<String, Object>();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		String url = "http://tcopenapi.17usoft.com/handlers/hotel/QueryHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetHotelList");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetHotelList", "ReqTime=" + date };

		String[] sortedArray = Tools.BubbleSort(originalArray);

		String checkvalue = Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		// body.put("cityId", "229");// 地区226 苏州 229 无锡
		// body.put("comeDate", DateUtils.opDate(1));// 入驻日期
		// body.put("leaveDate", DateUtils.opDate(3));// 离开日期
		// body.put("clientIp", "127.0.0.1");
		// // body.put("sortType", "2");
		// // body.put("priceRange", "200,");
		// body.put("searchFields",
		// "hotelName,address,nearby,chainName,starRatedName");
		// body.put("keyword", "瑞雅");
		// body.put("page", "1");
		// body.put("pageSize", "10");

		return send(url, param, body);

	}

	public static String GetHotelImageList(Map<String, Object> body) {
		Map<String, Object> param = new TreeMap<String, Object>();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		String url = "http://tcopenapi.17usoft.com/handlers/hotel/QueryHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetHotelImageList");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetHotelImageList", "ReqTime=" + date };

		String[] sortedArray = Tools.BubbleSort(originalArray);

		String checkvalue = Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		// body.put("hotelId", "10583");//
		// body.put("page","1");//
		// body.put("pageSize","5");//
		return send(url, param, body);
	}

	public static String GetHotelDetail(Map<String, Object> body) {
		Map<String, Object> param = new TreeMap<String, Object>();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		String url = "http://tcopenapi.17usoft.com/handlers/hotel/QueryHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetHotelDetail");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetHotelDetail", "ReqTime=" + date };

		String[] sortedArray = Tools.BubbleSort(originalArray);

		String checkvalue = Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		// body.put("hotelId", "10583");//
		return send(url, param, body);
	}

	public static String GetHotelRooms(Map<String, Object> body) {
		Map<String, Object> param = new TreeMap<String, Object>();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		String url = "http://tcopenapi.17usoft.com/handlers/hotel/QueryHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetHotelRooms");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetHotelRooms", "ReqTime=" + date };

		String[] sortedArray = Tools.BubbleSort(originalArray);

		String checkvalue = Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		// body.put("hotelId", "10583");//
		// body.put("comeDate",DateUtils.opDate(1));//
		// body.put("leaveDate",DateUtils.opDate(3));//
		return send(url, param, body);
	}

	public static String GetHotelRoomsWithGuaranteePolicy(
			Map<String, Object> body) {
		Map<String, Object> param = new TreeMap<String, Object>();
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
				.format(new Date());
		String url = "http://tcopenapi.17usoft.com/handlers/hotel/QueryHandler.ashx";
		System.out.println(url);
		param.put("version", "20111128102912");
		param.put("accountID", "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3");
		param.put("serviceName", "GetHotelRoomsWithGuaranteePolicy");
		param.put("reqTime", date);
		String[] originalArray = { "Version=20111128102912",
				"AccountID=dbd36c84-6cd9-42c5-8ed6-39d03d0019e3",
				"ServiceName=GetHotelRoomsWithGuaranteePolicy",
				"ReqTime=" + date };

		String[] sortedArray = Tools.BubbleSort(originalArray);

		String checkvalue = Tools.GetMD5ByArray(sortedArray,
				"2e0aec2c155dcb95", "utf-8");
		param.put("digitalSign", checkvalue);
		// body.put("hotelId", "10583");//
		// body.put("page","1");//
		// body.put("pageSize","5");//
		return send(url, param, body);
	}

	/**
	 * 发送请求
	 */
	public static String send(String url, Map<String, Object> param,
			Map<String, Object> body) {
		String str = "";
		try {
			StringBuffer sbf = new StringBuffer();
			sbf.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			sbf.append("<request>");
			sbf.append("<header>");
			for (String key : param.keySet()) {
				sbf.append("<" + key + ">" + param.get(key) + "</" + key + ">");
			}
			sbf.append("</header>");
			sbf.append("<body>");
			for (String key : body.keySet()) {
				sbf.append("<" + key + ">" + body.get(key) + "</" + key + ">");
			}
			sbf.append("</body>");
			sbf.append("</request>");
			System.out.println(sbf);
			URL urls = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setRequestProperty("Content-Length", ""
					+ sbf.toString().getBytes().length);
			conn.setRequestProperty("Content-Language", "en-US");
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// 获取输出流
			OutputStream os = conn.getOutputStream();
			os.write(sbf.toString().getBytes());
			os.flush();
			if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				StringBuffer buffer = new StringBuffer();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, "utf-8"));
				while ((str = reader.readLine()) != null) {
					buffer.append(str);
				}
				in.close();
				str = new String(buffer.toString().getBytes(), "utf-8");
				Log.v("response", str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void main(String[] args) {

	}
}

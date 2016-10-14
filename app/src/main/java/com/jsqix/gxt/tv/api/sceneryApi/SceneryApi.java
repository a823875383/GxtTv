package com.jsqix.gxt.tv.api.sceneryApi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jsqix.gxt.tv.enu.Contacts;
import com.jsqix.gxt.tv.utils.NetworkTools;
import com.jsqix.gxt.tv.utils.StringUtils;
import com.jsqix.gxt.tv.utils.XmlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public abstract class SceneryApi extends AsyncTask<String, String, String> {

	private String TAG = "SceneryApi";

	public static final String URL_SCENERY = "http://tcopenapi.17usoft.com/handlers/scenery/queryhandler.ashx";

	public static final String URL_ADDRESS = "http://tcopenapi.17usoft.com/Handlers/General/AdministrativeDivisionsHandler.ashx";

	public static final String IMG_URL = "http://pic3.40017.cn/scenery/destination/";

	private static final String VERSION = "20111128102912";

	private static final String ACCKEY = "2e0aec2c155dcb95";

	private static final String ACCID = "dbd36c84-6cd9-42c5-8ed6-39d03d0019e3";

	/**
	 * 各接口的resultCode
	 */
	public static final int RESULTCODE_GETSENERYLIST = 0X000101;

	public static final int RESULTCODE_GETCITIES = 0X000102;

	public static final int RESULTCODE_GETSCENERYDETAIL = 0X000103;

	public static final int RESULTCODE_GETSCENERYIMGLIST = 0X000104;

	public static final int RESULTCODE_GETSCENERYTICKET = 0X000105;

	public static final int RESULTCODE_SEARCHCITY = 0X000106;

	private static int resultCode = 0;

	private Context context;

	private String URL;

	private InterfaceXMLPost mListener;

	private Hashtable<String, String> ht = new Hashtable<String, String>();

	public SceneryApi(Context context, String url,int resultCode,InterfaceXMLPost listener) {
		this.mListener = listener;
		this.URL = url;
		this.resultCode = resultCode;
		this.context = context;
	}

	public abstract void onPreExecute();

	public HashMap<String, String> getHeadMap(String serviceName) {
		HashMap<String, String> hm = new HashMap<String, String>(); // 将参数放入Hashtable中，便于操作

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();

		SimpleDateFormat ymdhmsf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		String reqTime = ymdhmsf.format(date);

		hm.put("version", VERSION); // 接口协议版本号，详见接口协议文档
		hm.put("accountID", ACCID); // API帐户ID(小写)，待申请审批通过后发放
		hm.put("serviceName", serviceName); // 调用接口的方法名称
		hm.put("reqTime", reqTime); // 当前日期
		hm.put("accountKey", ACCKEY); // API帐户密钥，待申请审批通过后发放
		try {
			String checkvalue = createDigitalSign(hm);
			hm.put("digitalSign", createDigitalSign(hm));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}

	public String getParmsStr(Map<String, String> head, Map<String, Object> body) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sbf.append("<request>");
		sbf.append("<header>");
		for (String key : head.keySet()) {
			if (!"accountKey".equals(key)) {
				sbf.append("<" + key + ">" + head.get(key) + "</" + key + ">");
			}
		}
		sbf.append("</header>");
		sbf.append("<body>");
		for (String key : body.keySet()) {
			sbf.append("<" + key + ">" + body.get(key) + "</" + key + ">");
		}
		sbf.append("</body>");
		sbf.append("</request>");
		return sbf.toString();
	}

	@Override
	protected String doInBackground(String... params) {
		String ret = null;
		try {
			ret = callUrl(URL, params[0]);
		} catch (Exception e) {
			Toast.makeText(context, "请求服务器异常", Toast.LENGTH_LONG).show();
			mListener.onFailure(resultCode, ret, Contacts.RET_CODE_FAILURE);
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	protected void onPostExecute(String response) {
		if (StringUtils.isEmpty(response)) {
			if (NetworkTools.isNetworkAvailable(context)) {
				Toast.makeText(context, "亲，没有网络哦！请检查网络设置！", Toast.LENGTH_LONG)
						.show();
				mListener.onFailure(resultCode, "", Contacts.RET_CODE_NONET);
			} else {
				Toast.makeText(context, "服务器未响应！", Toast.LENGTH_LONG).show();
				mListener.onFailure(resultCode, "",
						Contacts.RET_CODE_NORESPONSE);
			}
		} else {
			Map<String, Object> xmlMap = XmlUtils.Dom2Map(response);
			JSONObject ret = null;
			if (xmlMap != null) {
				try {
					ret = new JSONObject(new Gson().toJson(xmlMap));
					JSONObject header = ret.getJSONObject("header");
					if ("0000".equals(header.get("rspCode"))) {
						JSONObject body = ret.getJSONObject("body");
						mListener.onSuccess(resultCode, body.toString());
						Log.v("response", header.getString("rspDesc"));
					} else if("0001".equals(header.get("rspCode"))){
						mListener.onFailure(resultCode, ret.toString(),
								Contacts.RET_CODE_NODATA);
					}else {
						Toast.makeText(context, header.getString("rspDesc"),
								Toast.LENGTH_LONG).show();
						mListener.onFailure(resultCode, ret.toString(),
								Contacts.RET_CODE_FAILURE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(context, "数据出错", Toast.LENGTH_LONG).show();
					mListener.onFailure(resultCode, response.toString(),
							Contacts.RET_CODE_DATAERROR);
				}
			} else {
				Toast.makeText(context, "数据出错", Toast.LENGTH_LONG).show();
				mListener.onFailure(resultCode, response.toString(),
						Contacts.RET_CODE_DATAERROR);
			}
		}
	}
	/**
	 * 请求服务器URL地址返回数据POST方式
	 * 
	 * @param url
	 *            远程访问的地址
	 * @param data
	 *            参数
	 * @return // * 远程页面调用结果
	 * @throws Exception
	 */
	public static String callUrl(String url, String data) throws Exception {
		HttpURLConnection conn = null;
		String content = "";

		URL getUrl = new URL(url);
		conn = (HttpURLConnection) getUrl.openConnection();

		conn.setConnectTimeout(3000);
		conn.setReadTimeout(3000);

		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.setInstanceFollowRedirects(true);
		conn.setRequestProperty("Content-Type", "text/plain");

		byte[] bdata = data.getBytes("utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(bdata.length));
		conn.setRequestProperty("Content-Language", "en-US");
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(bdata);
		out.flush();
		out.close();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		String inputLine;

		while ((inputLine = reader.readLine()) != null) {
			content += inputLine;
		}
		reader.close();
		conn.disconnect();

		return content;
	}

	/**
	 * 数组排序（冒泡排序法）
	 * 
	 * @param originalArray
	 *            待排序字符串数组
	 * @return 经过冒泡排序过的字符串数组
	 */
	public static String[] bubbleSort(String[] originalArray) {
		int i, j; // 交换标志
		String temp;
		Boolean exchange;

		for (i = 0; i < originalArray.length; i++) // 最多做R.Length-1趟排序
		{
			exchange = false; // 本趟排序开始前，交换标志应为假

			for (j = originalArray.length - 2; j >= i; j--) {
				if (originalArray[j + 1].compareTo(originalArray[j]) < 0)// 交换条件
				{
					temp = originalArray[j + 1];
					originalArray[j + 1] = originalArray[j];
					originalArray[j] = temp;

					exchange = true; // 发生了交换，故将交换标志置为真
				}
			}

			if (!exchange) // 本趟排序未发生交换，提前终止算法
			{
				break;
			}
		}

		return originalArray;
	}

	/**
	 * 创建数字签名
	 * 
	 * @param hm
	 *            存放数字签名参数的HashMap
	 * @return String DigitalSign
	 * @throws Exception
	 */
	public static String createDigitalSign(HashMap<String, String> hm)
			throws Exception {
		if (!hm.containsKey("accountKey")
				|| hm.get("accountKey").toString().trim().length() == 0) {
			throw new Exception("缺少API帐户密钥");
		}
		String accountKey = hm.get("accountKey").toString().trim(); // API帐户密钥

		String version = ""; // 版本号
		String serviceName = ""; // 调用接口的方法名称
		String accountId = ""; // API帐户密钥
		String reqTime = ""; // 当前日期

		if (hm.containsKey("version")) {
			version = hm.get("version");
		}

		if (hm.containsKey("serviceName")) {
			serviceName = hm.get("serviceName");
		}

		if (hm.containsKey("accountID")) {
			accountId = hm.get("accountID");
		}

		if (hm.containsKey("reqTime")) {
			reqTime = hm.get("reqTime");
		}

		String[] originalArray = { "Version=" + version,
				"AccountID=" + accountId, "ServiceName=" + serviceName,
				"ReqTime=" + reqTime };
		String[] sortedArray = bubbleSort(originalArray);
		String digitalSing = getMD5ByArray(sortedArray, accountKey, "utf-8");
		return digitalSing;

	}

	/**
	 * MD5 哈希运算
	 * 
	 * @param input
	 *            待计算MD5哈希值的输入字符串
	 * @param charset
	 *            输入字符串的字符集
	 * @return 输入字符串的MD5哈希值
	 */
	public static String getMD5(String input, String charset) {

		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(input.getBytes(charset));
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节

			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16
											// 进制需要 32 个字符

			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节转换成 16
											// 进制字符的转换

				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
															// >>>
															// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取字符数组的MD5哈希值
	 * 
	 * @param sortedArray
	 *            待计算MD5哈希值的输入字符数组
	 * @param key
	 *            密钥
	 * @param charset
	 *            输入字符串的字符集
	 * @return 输入字符数组的MD5哈希值
	 */
	public static String getMD5ByArray(String[] sortedArray, String key,
			String charset) {
		// 构造待md5摘要字符串
		StringBuilder prestr = new StringBuilder();

		for (int i = 0; i < sortedArray.length; i++) {
			if (i == sortedArray.length - 1) {
				prestr.append(sortedArray[i]);
			} else {
				prestr.append(sortedArray[i] + "&");
			}
		}
		prestr.append(key);// 此处key为上面的innerKey

		return getMD5(prestr.toString(), charset);
	}

	public static JSONArray getJsonArray(JSONObject parentJsonObj,
			String arrayName) throws JSONException {
		JSONArray array = new JSONArray();
		String arrStr = parentJsonObj.getString(arrayName).toString();
		if (arrStr.charAt(0) == '[' && arrStr.toString().charAt(arrStr.length() - 1) == ']') {
			array = parentJsonObj.getJSONArray(arrayName);
		} else {
			array.put(0, parentJsonObj.getJSONObject(arrayName));
		}
		return array;
	}
}

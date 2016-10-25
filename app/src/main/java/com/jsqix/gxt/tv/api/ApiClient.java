package com.jsqix.gxt.tv.api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import static com.jsqix.gxt.tv.api.HttpUtil.ANDRID_SDK_KEY;

public class ApiClient {

    public static String makeGetMessage(String srcUrl, Map<String, Object> data)
            throws IOException {
        String url = null;
        if (data != null) {
            Log.v("XXXXX", "--------------------------");
            Log.i("签名前----", data.toString());
            String data2 = getSignAfter(data, ANDRID_SDK_KEY);
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
                postData.append(URLEncoder.encode((String) data.get(name), "utf-8"));
            }
            postData.append('&');
        }

        return postData.deleteCharAt(postData.lastIndexOf("&")).toString();
    }

    /**
     * 签名
     *
     * @param signAfter
     * @return
     */
    private static String getSign(String signAfter, String key) {

        String sign = Md5.getMD5(signAfter + key, "utf-8");

        return sign;
    }

    /**
     * 登录签名
     *
     * @param signAfter
     * @return
     */
    private static String getSign(StringBuffer signAfter, String key) {

        String sign = Md5.getMD5(signAfter + key, "utf-8");

        return sign;
    }

    /**
     * map进行排序
     *
     * @param argMap
     * @return
     */
    public static String getSignAfter(Map<String, Object> argMap, String key) {
        String sign = "";
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        for (Entry<String, Object> tMap : argMap.entrySet()) {
            treeMap.put(tMap.getKey(), tMap.getValue() + "");
        }
        StringBuffer ret = new StringBuffer();
        for (Entry<String, String> map : treeMap.entrySet()) {
            System.out.print(map.getKey());
            ret.append(map.getKey());
            ret.append("=");
            String value = (String) map.getValue();
            System.out.println("	" + value);
            if (value == null)
                value = "";
            ret.append(value);
            ret.append("&");
        }
        if (ret.toString().endsWith("&")) {
            ret.delete(ret.toString().length() - 1, ret.toString().length());
        }
        sign = getSign(ret.toString(), key);

        // sign = getSign(argMap.get("params") + argMap.get("timestamp"), key);
        return sign;
    }

    private static String _MakeURL(String p_url, Map<String, Object> params) {
        StringBuilder url = new StringBuilder(p_url);
        if (url.indexOf("?") < 0)
            url.append('?');
        for (String name : params.keySet()) {
            url.append('&');
            url.append(name);
            url.append('=');
            url.append(String.valueOf(params.get(name)));
        }

        return url.toString().replace("?&", "?");
    }

    /**
     * 流转字符串
     *
     * @param is
     * @return
     */

    private static String Stream2String(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is),
                16 * 1024); // 强制缓存大小为16KB，一般Java类默认为8KB
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) { // 处理换行符
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

}

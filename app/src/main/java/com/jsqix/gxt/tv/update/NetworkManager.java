package com.jsqix.gxt.tv.update;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class NetworkManager {
    public static final String TAG = "NetworkManager";
    private int connectTimeout = 30000;
    private int readTimeout = 30000;
    private java.net.Proxy mProxy = null;
    private Context mContext;
    private boolean isCanceled = false;
    private static final String encoding = "UTF-8";

    public NetworkManager(Context context) {
        this.mContext = context;
        setDefaultHostnameVerifier();
    }

    public void cancel() {
        this.isCanceled = true;
    }

    public String sendAndWaitResponse(String strReqData, String strUrl) {
        detectProxy();

        String strResponse = null;
        HttpURLConnection httpConnect = null;

        System.out.println(strUrl);
        System.out.println(strReqData);
        try {
            URL url = new URL(strUrl);

            if (this.mProxy != null)
                httpConnect = (HttpURLConnection) url.openConnection(this.mProxy);
            else {
                httpConnect = (HttpURLConnection) url.openConnection();
            }
            httpConnect.setConnectTimeout(this.connectTimeout);
            httpConnect.setReadTimeout(this.readTimeout);
            httpConnect.setDoOutput(true);
            httpConnect.setDoInput(true);
            httpConnect.addRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");

            httpConnect.connect();

            OutputStream os = httpConnect.getOutputStream();
            os.write(strReqData.getBytes("UTF-8"));
            os.flush();

            InputStream content = httpConnect.getInputStream();
            try {
                String str1 = convertStreamToString(content);

                httpConnect.disconnect();

                return str1;
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {
            httpConnect.disconnect();
        }

        System.out.println(strResponse);
        return strResponse;
    }

    public String sendAndWaitResponseByGet(String strUrl, String version, String plat, String ua) {
        detectProxy();

        String strResponse = null;
        ArrayList pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("ver", version));
        pairs.add(new BasicNameValuePair("plat", plat));
        pairs.add(new BasicNameValuePair("ua", ua));

        HttpURLConnection httpConnect = null;
        try {
            UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "utf-8");
            URL url = new URL(strUrl);
            url.getFile();

            if (this.mProxy != null)
                httpConnect = (HttpURLConnection) url.openConnection(this.mProxy);
            else {
                httpConnect = (HttpURLConnection) url.openConnection();
            }
            httpConnect.setConnectTimeout(this.connectTimeout);
            httpConnect.setRequestMethod("GET");
            httpConnect.setReadTimeout(this.readTimeout);
            httpConnect.setDoOutput(true);
            httpConnect.addRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            httpConnect.connect();

            OutputStream os = httpConnect.getOutputStream();
            p_entity.writeTo(os);
            os.flush();

            InputStream content = httpConnect.getInputStream();
            strResponse = convertStreamToString(content);
            Log.d("NetworkManager", "response " + strResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpConnect.disconnect();
        }
        return strResponse;
    }

    public String sendAndWaitResponseByPost(String strReqData, String strUrl) {
        detectProxy();

        String strResponse = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 30000);
        try {
            Log.d("NetworkManager", "Req:" + strReqData + "\r\n");
            HttpPost httpPost = new HttpPost(strUrl);
            StringEntity entity = new StringEntity(strReqData, "UTF-8");

            httpPost.addHeader("Content-Type", "text/xml;Charset=UTF-8");
            httpPost.setEntity(entity);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream content = httpEntity.getContent();
                try {
                    strResponse = new String(readByteArrayFromStream(content), "UTF-8");
                } catch (Exception e) {
                    Log.e("NetworkManager", e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.e("NetworkManager", e.getMessage());
        }

        Log.d("NetworkManager", "Resp:" + strResponse + "\r\n");
        return strResponse;
    }

    public boolean urlDownloadToFile(Context context, String strurl, String path) {
        boolean bRet = false;
        detectProxy();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strurl);
            if (this.mProxy != null)
                conn = (HttpURLConnection) url.openConnection(this.mProxy);
            else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setConnectTimeout(this.connectTimeout);
            conn.setReadTimeout(this.readTimeout);
            conn.setDoInput(true);

            conn.connect();
            InputStream is = conn.getInputStream();

            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            byte[] temp = new byte[1024];
            int i = 0;
            while (((i = is.read(temp)) > 0) && (!this.isCanceled)) {
                fos.write(temp, 0, i);
            }

            fos.close();
            is.close();

            bRet = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return bRet;
    }

    public boolean urlDownloadToFile(Context context, String strurl, String path, String apkName) {
        boolean bRet = false;
        detectProxy();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strurl);
            if (this.mProxy != null)
                conn = (HttpURLConnection) url.openConnection(this.mProxy);
            else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setConnectTimeout(this.connectTimeout);
            conn.setReadTimeout(this.readTimeout);
            conn.setDoInput(true);

            conn.connect();
            InputStream is = conn.getInputStream();

            FileOutputStream fos = context.openFileOutput(apkName,
                    Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);

            byte[] temp = new byte[1024];
            int i = 0;
            while (((i = is.read(temp)) > 0) && (!this.isCanceled)) {
                fos.write(temp, 0, i);
            }

            fos.close();
            is.close();

            bRet = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return bRet;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static byte[] readByteArrayFromStream(InputStream inputStream)
            throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        while ((length = inputStream.read(buffer)) > 0) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        byteArrayOutputStream.flush();
        inputStream.close();

        byte[] result = byteArrayOutputStream.toByteArray();

        byteArrayOutputStream.close();

        return result;
    }

    private void detectProxy() {
        ConnectivityManager cm = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if ((ni != null) && (ni.isAvailable()) && (ni.getType() == 0)) {
            String proxyHost = android.net.Proxy.getDefaultHost();
            int port = android.net.Proxy.getDefaultPort();
            if (proxyHost != null) {
                InetSocketAddress sa = new InetSocketAddress(proxyHost, port);
                this.mProxy = new java.net.Proxy(Proxy.Type.HTTP, sa);
            }
        }
    }

    private void setDefaultHostnameVerifier() {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }
}
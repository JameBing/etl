package com.wangjunneil.schedule.utility;

import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

/**
 * 描述信任SSL 发送https请求工具类
 *
 * @author Administrator
 * @date 2016/9/8
 */
public class HttpsUtil {
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https get请求
     * @param url
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String get(String url) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        StringBuffer sb = new StringBuffer();

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.connect();
        if ("200".equals(conn.getResponseCode())){

        }

        InputStream is = conn.getInputStream();
        byte[] buff = new byte[1024];
        int len;
        while ((len = is.read(buff)) != -1) {
            sb.append(new String(buff, 0, len));
        }
        is.close();
        conn.disconnect();
        return sb.toString();
    }

    /**
     * https get请求
     * @param url
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String getFormZ8(String url) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        StringBuffer sb = new StringBuffer();

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.connect();
        if (400 == conn.getResponseCode()){
            sb.append("{\"errorMsg\":\"参数不合法\"}");
        } else if (401 == conn.getResponseCode()){
            sb.append("{\"errorMsg\":\"Token错误或过期\"}");
        } else if (404 == conn.getResponseCode()){
            sb.append("{\"errorMsg\":\"资源不存在\"}");
        } else if (500 == conn.getResponseCode()){
            sb.append("{\"errorMsg\":\"服务内部错误\"}");
        } else {
            InputStream is = conn.getInputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = is.read(buff)) != -1) {
                sb.append(new String(buff, 0, len));
            }
            is.close();
        }
        conn.disconnect();
        return sb.toString();
    }

    /**
     * https post请求
     * @param url
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static String post(String url, String param) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        StringBuffer sb = new StringBuffer();

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.connect();

        DataOutputStream dos=new DataOutputStream(conn.getOutputStream());
        dos.writeBytes(param);
        dos.flush();
        dos.close();

        System.out.println(conn.getResponseCode() + "==" + conn.getResponseMessage());

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        br.close();
        conn.disconnect();
        return sb.toString();
    }
}

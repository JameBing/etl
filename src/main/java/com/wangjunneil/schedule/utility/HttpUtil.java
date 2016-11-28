package com.wangjunneil.schedule.utility;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by wangjun on 8/1/16.
 */
public final class HttpUtil {
    private HttpUtil() {}
    public static String get(String urlPath) {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset","utf-8");
            conn.setRequestMethod("POST");
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {

            }

            InputStream is = conn.getInputStream();

            byte[] buff = new byte[1024];
            int len;
            while ((len = is.read(buff)) != -1) {
                sb.append(new String(buff, 0, len));
            }
            is.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * post方式请求，参数默认不转码
     * @param urlStr
     * @param param  注意顺序 [参数,格式，timeout,readtime]
     * @return
     */
    public static String post(String urlStr, String... param) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type",(param.length>1)?param[1]:"application/x-www-form-urlencoded");
            con.setConnectTimeout((param.length > 2) ? Integer.parseInt(param[2]) : 60 * 1000);
            con.setReadTimeout((param.length>3)?Integer.parseInt(param[3]):60*1000);
            con.connect();

            DataOutputStream dos=new DataOutputStream(con.getOutputStream());
            dos.writeBytes(param[0]);
            dos.flush();
            dos.close();
            int responseCode = con.getResponseCode();
//            if(responseCode == HttpURLConnection.HTTP_OK||responseCode == HttpURLConnection.HTTP_ACCEPTED||responseCode == HttpURLConnection.HTTP_CREATED)
//            {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            return sb.toString();
//            }else {
//                //
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    ////////////////////////////////////////////////////
    public static String elmGet(String urlPath) {
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset","utf-8");
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {

            }

            InputStream is = conn.getInputStream();

            byte[] buff = new byte[1024];
            int len;
            while ((len = is.read(buff)) != -1) {
                sb.append(new String(buff, 0, len));
            }
            is.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * put方式请求，参数默认不转码
     * @param urlStr
     * @param param  注意顺序 [参数,格式，timeout,readtime]
     * @return
     */
    public static String elmPut(String urlStr, String... param) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("PUT");

            con.setRequestProperty("Content-Type",(param.length>1)?param[1]:"application/x-www-form-urlencoded");
            con.setConnectTimeout((param.length > 2) ? Integer.parseInt(param[2]) : 60 * 1000);
            con.setReadTimeout((param.length>3)?Integer.parseInt(param[3]):60*1000);
            con.connect();

            DataOutputStream dos=new DataOutputStream(con.getOutputStream());
            dos.writeBytes(param[0]);
            dos.flush();
            dos.close();
            int responseCode = con.getResponseCode();
//            if(responseCode == HttpURLConnection.HTTP_OK||responseCode == HttpURLConnection.HTTP_ACCEPTED||responseCode == HttpURLConnection.HTTP_CREATED)
//            {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            return sb.toString();
//            }else {
//                //
//            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

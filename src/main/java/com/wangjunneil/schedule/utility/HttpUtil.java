package com.wangjunneil.schedule.utility;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import org.eclipse.jetty.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
<<<<<<< HEAD
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                br.close();
                return sb.toString();
=======
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
>>>>>>> 60c7926829de2186cf4f8eeced49bc5fc1f079d3
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * post方式请求，参数默认不转码
     * @param param  注意顺序 [url,参数,contentType,charset,timeout,readtime,平台标识]
     * @return
     */
    public static String post2(String... param) throws ScheduleException{
        String urlStr = param[0],
            pars = param.length>1?param[1]:"",
            contentType = (param.length>2 && !StringUtil.isEmpty(param[2]))?param[2]:"application/x-www-form-urlencoded",
            charset =  (param.length>3 && !StringUtil.isEmpty(param[3]))?param[3]:"utf-8",
            //boundary = "--ZYETL1234567890--",
            platform = (param.length>6 && !StringUtil.isEmpty(param[6]))?param[6]:"";
        int timeout = (param.length > 4 && !StringUtil.isEmpty(param[4])) ? Integer.parseInt(param[4]) : 60 * 1000,
            readTimeout = (param.length>5 && !StringUtil.isEmpty(param[5]))?Integer.parseInt(param[5]):60*1000;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type",(contentType.toLowerCase().equals("multipart/form-data")?("multipart/form-data;boundary="+ Constants.BOUNDARY):contentType) + ";" + charset);
            con.setRequestProperty("contentType", "application/json");
            con.setConnectTimeout(timeout);
            con.setReadTimeout(readTimeout);
            con.connect();
//            switch (contentType.toLowerCase()){
//                case "multipart/form-data":
//                    BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
//                    StringBuilder sb = new StringBuilder();
//                    out.write(pars.getBytes());
//                    break;
//                case "application/x-www-form-urlencoded":
//                    DataOutputStream dos=new DataOutputStream(con.getOutputStream());
//                    dos.writeBytes(param[0]);
//                    dos.flush();
//                    dos.close();
//                    break;
//                case "application/json":
//
//                    //待实现
//                    break;
//                default:
//                    break;
//            }
            BufferedOutputStream out = new BufferedOutputStream(con.getOutputStream());
            out.write(pars.getBytes());
            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK||responseCode == HttpURLConnection.HTTP_ACCEPTED||responseCode == HttpURLConnection.HTTP_CREATED)
            {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            return sb.toString();
            }else {
                throw new ScheduleException(platform, "ScheduleException",MessageFormat.format("http error:{0}", String.valueOf(responseCode)),pars,new Throwable().getStackTrace());
//              switch (platform){
//                  case Constants.PLATFORM_WAIMAI_BAIDU:
//
//                      break;
//                  case Constants.PLATFORM_WAIMAI_JDHOME:
//                      break;
//                  case Constants.PLATFORM_WAIMAI_ELEME:
//                      break;
//                  case Constants.PLATFORM_WAIMAI_MEITUAN:
//                      break;
//                  default:
//                      break;
//              }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new ScheduleException(platform, "MalformedURLException",e.getMessage(),pars,e.getStackTrace());
        } catch (IOException e) {
            throw new ScheduleException(platform, "IOException",e.getMessage(),pars,e.getStackTrace());
        }
    }

   //请求Rest服务
   public static String httpRest(String url,String method,String params,Map<String,?> uriVars){
       String result = "";
       HttpHeaders headers = new HttpHeaders();//创建一个头部对象
       //设置contentType
       headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
       //设置我们的请求信息，第一个参数为请求Body,第二个参数为请求头信息
       HttpEntity<String> strEntity = new HttpEntity<String>(params,headers);
       RestTemplate restTemplate  = new RestTemplate();
       switch (method.toUpperCase()){

<<<<<<< HEAD
           case "POST":
               //完整的方法签名为：postForObject(String url, Object request, Class<String> responseType, Object... uriVariables) ，最后的uriVariables用来拓展我们的请求参数内容。
                result = restTemplate.postForObject(url,strEntity,String.class,uriVars==null?new HashMap<String, String>():uriVars);
               break;
           case "GET":
                result = restTemplate.getForObject(url,String.class,uriVars==null?new HashMap<String, String>():uriVars);
               break;
           case "PUT":
               restTemplate.put(url,strEntity,uriVars==null?new HashMap<String, String>():uriVars);
               result = "success";
               break;
           case "DELETE":
               restTemplate.delete(url,uriVars,uriVars==null?new HashMap<String, String>():uriVars);
               result = "success";
               break;
           default:break;
       }
       return  result;
   }
=======

>>>>>>> 60c7926829de2186cf4f8eeced49bc5fc1f079d3
}

package com.wangjunneil.schedule.service;


import org.apache.http.HttpClientConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <code>MtFacadeService</code>类美团接口API的调用
 *
 * Created by liuxin on 2016-11-10.
 */
public class MtFacadeService {

    //门店开业
    public static final String kyurl = "http://test.waimaiopen.meituan.com/api/v1/poi/open";
    //门店歇业
    public static final String xyurl = "http://test.waimaiopen.meituan.com/api/v1/poi/close";


    /**
     * 门店开业
     * @parama app_poi_code - APP方门店id
     */
    public static String createShop(String code)
    {
        URL url = null;
        String params="app_poi_code="+code;
        StringBuilder builder=new StringBuilder();
        try {
            url=new URL(kyurl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(params.getBytes("utf-8"));
            outputStream.flush();
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp=null;
            while ((temp=reader.readLine())!=null)
            {
                builder.append(temp);
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 门店歇业
     * @params app_poi_code - APP方门店id
     */
    public static String deleteShop(String code)
    {
        URL url = null;
        String params = "app_poi_code"+code;
        StringBuilder builder = new StringBuilder();
        try {
            url=new URL(xyurl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(params.getBytes("utf-8"));
            outputStream.flush();
            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String temp=null;
            while ((temp=reader.readLine())!=null)
            {
                builder.append(temp);
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }





    //测试
    public static void main(String[] ar)
    {
        System.out.println(createShop("12233"));
    }
}

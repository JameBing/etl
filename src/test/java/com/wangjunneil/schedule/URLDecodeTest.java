package com.wangjunneil.schedule;


import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Jame on 2017/3/21.
 */
public class URLDecodeTest {

        public static void main(String[] args) throws UnsupportedEncodingException {
            // 将application/x-www-from-urlencoded字符串转换成普通字符串
            String keyWord = URLDecoder.decode("%E6%9D%8E%E5%88%9A", "UTF-8");
            System.out.println(keyWord);

            // 将普通字符创转换成application/x-www-from-urlencoded字符串
            String urlString = URLEncoder.encode("http://58.32.191.54:8080/mark/tuangou/callBack", "UTF-8");
            System.out.println(urlString);
        }
}

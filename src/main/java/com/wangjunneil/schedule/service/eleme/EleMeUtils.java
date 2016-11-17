package com.wangjunneil.schedule.service.eleme;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
/**
 * Created by admin on 2016/11/17.
 */
public class EleMeUtils {
    //将输入参数排序并用&连接
    private static String concatParams(Map<String, String> params2) throws UnsupportedEncodingException {
        Object[] key_arr = params2.keySet().toArray();
        Arrays.sort(key_arr);
        String str = "";

        for (Object key : key_arr) {
            String val = params2.get(key);
            key = URLEncoder.encode(key.toString(), "UTF-8");
            val = URLEncoder.encode(val, "UTF-8");
            str += "&" + key + "=" + val;
        }

        return str.replaceFirst("&", "");
    }
    //将byte类型的结果转化为HexString
    private static String byte2hex(byte[] b) {
        StringBuffer buf = new StringBuffer();
        int i;

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }
    //通过给定的url, params, consumer_secret计算出sig
    public static String genSig(String pathUrl, Map<String, String> params,
                                String consumerSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = concatParams(params);
        str = pathUrl + "?" + str + consumerSecret;

        MessageDigest md = MessageDigest.getInstance("SHA1");
        return byte2hex(md.digest(byte2hex(str.getBytes("UTF-8")).getBytes()));
    }
}

package com.wangjunneil.schedule.utility;

import java.util.*;

/**
 * 描述
 *
 * @author Administrator
 * @date 2016/9/6
 */
public class StringUtil {

    /**
     * 对参数名称按 ascii 码升序排列,key=value形式
     * @param reqParam
     * @return
     */
    public static String retParamAsc(String reqParam) {
        //key 值按 ascii 码升序排列
        Map<String,Object> paramMap = new TreeMap<String, Object>(
            new Comparator<String>() {
                public int compare(String obj1, String obj2) {
                    // 升序排序
                    return obj1.compareTo(obj2);
                }
            });
        String[] params = reqParam.split("&");
        for (String param : params) {
            String key = param.substring(0,param.indexOf("="));
            String value = param.substring(param.indexOf("=")+1,param.length());
            paramMap.put(key,value);
        }
        Set<String> keySet = paramMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuffer sb = new StringBuffer();
        while (iter.hasNext()) {
            String key = iter.next();
            if (!"".equals(sb.toString())) {
                sb.append("&");
            }
            sb.append(key + "=" + paramMap.get(key));
        }
        return sb.toString();
    }

    /**
     * 对参数名称按 ascii 码升序排列，key+value形式
     * @param reqParam
     * @return
     */
    public static String retParamAscAdd(String reqParam) {
        //key 值按 ascii 码升序排列
        Map<String,Object> paramMap = new TreeMap<String, Object>(
            new Comparator<String>() {
                public int compare(String obj1, String obj2) {
                    // 升序排序
                    return obj1.compareTo(obj2);
                }
            });
        String[] params = reqParam.split("&");
        for (String param : params) {
            String key = param.substring(0,param.indexOf("="));
            String value = param.substring(param.indexOf("=")+1,param.length());
            paramMap.put(key,value);
        }
        Set<String> keySet = paramMap.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuffer sb = new StringBuffer();
        while (iter.hasNext()) {
            String key = iter.next();
            /*if (!"".equals(sb.toString())) {
                sb.append("&");
            }*/
            sb.append(key + paramMap.get(key));//key+value
        }
        return sb.toString();
    }


    public static String ascii2native ( String asciicode )
    {
        String[] asciis = asciicode.split("\\\\u");
        String nativeValue = asciis[0];
        try
        {
            for ( int i = 1; i < asciis.length; i++ )
            {
                String code = asciis[i];
                nativeValue += (char) Integer.parseInt (code.substring (0, 4), 16);
                if (code.length () > 4)
                {
                    nativeValue += code.substring (4, code.length ());
                }
            }
        }
        catch (NumberFormatException e)
        {
            return asciicode;
        }
        return nativeValue;
    }

    public static boolean isEmpty(Object o) {
        if (null == o || "".equals(o.toString())) {
            return true;
        } else {
            return false;
        }
    }

}

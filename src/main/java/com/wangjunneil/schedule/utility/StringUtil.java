package com.wangjunneil.schedule.utility;



import com.wangjunneil.schedule.common.ElemaException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
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

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(Object... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (Object value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    //map对象转字符串
    public static String getUrlParamsByMap(Map<String, Object> map) throws IOException {

        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, Object> entry : entries) {
            String name = entry.getKey();
            Object  value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (StringUtil.areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value.toString(), "UTF-8"));
            }
        }
        return query.toString();
    }

    //list内置分页
    public static <T> List<T> setListPageDate(Integer begin, Integer end, List<T> list) {
        List<T> pageList = null;
        // 设置内置分页数据
        if (list != null && begin != null && end != null) {
            Integer maxSize = end;
            Integer dataSize = list.size();
            if (maxSize < dataSize) {
                pageList = list.subList(begin, maxSize);
            } else {
                pageList = list.subList(begin, list.size());
            }
        }
        return pageList;
    }


    //将obj转为map
    public static Map getMap(Object obj) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        if(obj == null)
            return null;
        Map<String, String> map = new HashMap<String, String>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            if (StringUtil.areNotEmpty(key, value)) {
                map.put(key, value+"");
            }
        }
        return map;
    }

    public static String getUrlParamsByObject(Object obj) throws ElemaException {
        try {
            return getUrlParamsByMap( getMap(obj));
        }catch ( Exception ex) {
            throw new ElemaException("数据转换出错!", ex);
        }
    }
}

package com.wangjunneil.schedule.common;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import com.wangjunneil.schedule.entity.baidu.SysParams;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class BaiDuException extends Exception  {

    public BaiDuException(){

    }

    private String innerExName;
    private String message;
    private String requestStr;
    private String stackInfo;


    public String getStackInfo(){
        return this.stackInfo;
    }

    public String getInnerExName(){
        return this.innerExName;
    }

    public String getMessage(){
        return this.message;
    }

    public String getRequestStr(){
        return this.requestStr;
    }

    public BaiDuException(String innerExName,String message,String requestStr,StackTraceElement[] traces) {
        this.innerExName = innerExName;
        this.requestStr = requestStr;
        this.message = message;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < traces.length; i++) {
            StackTraceElement element = traces[i];
            sb.append(element.toString() + "\n");
        }
        this.stackInfo = sb.toString();
    }
}

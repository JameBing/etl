package com.wangjunneil.schedule.common;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import com.wangjunneil.schedule.entity.baidu.SysParams;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class BaiDuException extends Exception  {

    public BaiDuException(){}
    private String requestBody;

    private void setRequestBody(String requestBody){
        this.requestBody = requestBody;
    }

    public String getRequestBody(){
        return this.requestBody;
    }

    public BaiDuException(String message,String requestBody,Exception ex) {
         super(message);
         this.requestBody = requestBody;
    }
}

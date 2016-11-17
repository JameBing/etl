package com.wangjunneil.schedule.common;

import com.wangjunneil.schedule.service.baidu.BaiDuApiService;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class BaiDuException extends Exception  {


    public BaiDuException(String message,Exception ex) {
         super(message);
    }
}

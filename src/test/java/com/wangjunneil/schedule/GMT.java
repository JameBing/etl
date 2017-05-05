package com.wangjunneil.schedule;

import com.wangjunneil.schedule.utility.DateTimeUtil;

import java.util.Date;

/**
 * Created by Jame on 2017/5/5.
 */
public class GMT {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(DateTimeUtil.dateSetTimeZone(date, "yyyy-MM-dd HH:mm:ss"));
    }
}

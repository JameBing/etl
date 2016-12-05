package com.wangjunneil.schedule.entity.baidu;

/**
 * Created by admin on 2016-12-02.
 */
//营业时间
public class BusinessTime {

    //开始时间;格式:HH:MM
    private String start;

    //结束时间
    private String end;

    public void setStart(String start){
        this.start = start;
    }

    public String getStart(){
        return this.start;
    }

    public void setEnd(String end){
        this.end = end;
    }

    public String getEnd(){
        return this.end;
    }
}

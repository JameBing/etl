package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class AvailableTime {

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    public void  setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public void  setEndTime(String endTime){
        this.endTime = endTime;
    }

    public  String getEndTime(){
        return this.endTime;
    }
}

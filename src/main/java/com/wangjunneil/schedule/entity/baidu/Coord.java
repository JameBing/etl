package com.wangjunneil.schedule.entity.baidu;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class Coord {

    //送餐地址百度经度
    private String longitude;

    //送餐地址百度纬度
    private String latitude;

    public void setLongitude(String longitude1){
        this.latitude = longitude1;
    }

    public String getLongitude(){
        return this.longitude;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLatitude(){
        return this.latitude;
    }
}



package com.wangjunneil.schedule.entity.baidu;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class CoordAmap {

    //送餐地址高德经度
    private String longitude;

    //送餐地址高德纬度
    private String latitude;

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLongitude(){
        return  this.longitude;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLatitude(){
        return  this.latitude;
    }

}

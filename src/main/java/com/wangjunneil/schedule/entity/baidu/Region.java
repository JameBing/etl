package com.wangjunneil.schedule.entity.baidu;

import com.wangjunneil.schedule.entity.common.FlowNum;

/**
 * Created by yangwanbin on 2016-12-02.
 */
//配送范围
public class Region {

    //配送区域坐标点经度
    private Float longitude;

    //配送区域坐标点纬度
    private Float latitude;

    public void setLongitude(Float longitude){
        this.longitude = longitude;
    }

    public Float getLongitude(){
        return this.longitude;
    }

    public void setLatitude(Float latitude){
        this.latitude = latitude;
    }

    public Float getLatitude(){
        return  this.latitude;
    }
}

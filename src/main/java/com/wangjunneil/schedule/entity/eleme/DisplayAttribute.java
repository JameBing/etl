package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by admin on 2016/11/28.
 */
public class DisplayAttribute {
    private List<Times> times;

    public String getTimes() {
        Gson gson = new Gson();
        return gson.toJson(times);
    }

    public void setTimes(List<Times> times) {
        this.times = times;
    }
}

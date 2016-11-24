package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/11/23.
 */
public class FoodsDetail {
    @SerializedName("foods")
    private List<Foods> foods;
    @SerializedName("get_failed")
    private Failed getfailed;

    public List<Foods> getFoods() {
        return foods;
    }

    public void setFoods(List<Foods> foods) {
        this.foods = foods;
    }

    public Failed getGetfailed() {
        return getfailed;
    }

    public void setGetfailed(Failed getfailed) {
        this.getfailed = getfailed;
    }
}

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/11/24.
 */
public class Body {
    @SerializedName("food")
    private Foods food;
    @SerializedName("group")
    private List<List<Group>> group;
    @SerializedName("extra")
    private List<Extra> extra;
    @SerializedName("abandoned_extra")
    private Object abandonedextra;

    public Foods getFood() {
        return food;
    }

    public void setFood(Foods food) {
        this.food = food;
    }


    public List<List<Group>> getGroup() {
        return group;
    }

    public void setGroup(List<List<Group>> group) {
        this.group = group;
    }

    public List<Extra> getExtra() {
        return extra;
    }

    public void setExtra(List<Extra> extra) {
        this.extra = extra;
    }

    public Object getAbandonedextra() {
        return abandonedextra;
    }

    public void setAbandonedextra(Object abandonedextra) {
        this.abandonedextra = abandonedextra;
    }
}

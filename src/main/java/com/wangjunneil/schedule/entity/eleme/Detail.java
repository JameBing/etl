package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/11/22.
 */
public class Detail {
    @SerializedName("group")
    private List<List<Group>> group;
    @SerializedName("extra")
    private List<Extra> extra;
    @SerializedName("abandoned_extra")
    private Object abandonedextra;

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

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/11/24.
 */
public class Labels {
    //是否招牌菜
    @SerializedName("is_featured")
    private int isfeatured;
    //是否配菜
    @SerializedName("is_gum")
    private int isgum;
    //是否新菜
    @SerializedName("is_new")
    private int isnew;
    //是否辣
    @SerializedName("is_spicy")
    private int isspicy;

    public int getIsfeatured() {
        return isfeatured;
    }

    public void setIsfeatured(int isfeatured) {
        this.isfeatured = isfeatured;
    }

    public int getIsgum() {
        return isgum;
    }

    public void setIsgum(int isgum) {
        this.isgum = isgum;
    }

    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
    }

    public int getIsspicy() {
        return isspicy;
    }

    public void setIsspicy(int isspicy) {
        this.isspicy = isspicy;
    }
}

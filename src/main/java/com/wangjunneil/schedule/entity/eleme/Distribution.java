package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/12/8.
 */
public class Distribution {
    @SerializedName("eleme_order_id")
    private String elemeorderid;
    @SerializedName("records")
    private List<Records> records;
    @SerializedName("eleme_order_ids_not_existed")
    private List<Object> elemeorderidsnotexisted;
    @SerializedName("order_delivery_info_not_existed")
    private List<Object> orderdeliveryinfonotexisted;

    public String getElemeorderid() {
        return elemeorderid;
    }

    public void setElemeorderid(String elemeorderid) {
        this.elemeorderid = elemeorderid;
    }

    public List<Records> getRecords() {
        return records;
    }

    public void setRecords(List<Records> records) {
        this.records = records;
    }

    public List<Object> getElemeorderidsnotexisted() {
        return elemeorderidsnotexisted;
    }

    public void setElemeorderidsnotexisted(List<Object> elemeorderidsnotexisted) {
        this.elemeorderidsnotexisted = elemeorderidsnotexisted;
    }

    public List<Object> getOrderdeliveryinfonotexisted() {
        return orderdeliveryinfonotexisted;
    }

    public void setOrderdeliveryinfonotexisted(List<Object> orderdeliveryinfonotexisted) {
        this.orderdeliveryinfonotexisted = orderdeliveryinfonotexisted;
    }
}

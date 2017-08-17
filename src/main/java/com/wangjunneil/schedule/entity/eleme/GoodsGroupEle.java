package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;
import eleme.openapi.sdk.api.entity.order.OGoodsItem;
import eleme.openapi.sdk.api.enumeration.order.OOrderDetailGroupType;

import java.util.List;

/**
 * Created by Jame on 2017/8/9.
 */
public class GoodsGroupEle {
    @SerializedName("deliverFee")
    private String name;
    @SerializedName("deliverFee")
    private OOrderDetailGroupType type;
    @SerializedName("deliverFee")
    private List<OGoodsItem> items;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OOrderDetailGroupType getType() {
        return this.type;
    }

    public void setType(OOrderDetailGroupType type) {
        this.type = type;
    }

    public List<OGoodsItem> getItems() {
        return this.items;
    }

    public void setItems(List<OGoodsItem> items) {
        this.items = items;
    }
}

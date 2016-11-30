package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yangwanbin on 2016-11-15.
 */
@Document(collection = "sync.baidu.shop")
public class Shop {
    @SerializedName("id")
    private String shopId;

    @SerializedName("baiduId")
    private String baiduShopId;
    private String name;
    public String getShopId() {
        return shopId;
    }
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setBaiduShopId(String baiduShopId){
        this.baiduShopId = baiduShopId;
    }

    public String getBaiduShopId(){
        return this.baiduShopId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

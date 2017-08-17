package com.wangjunneil.schedule.entity.eleme;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Jame on 2017/8/10.
 */
@Document(collection = "sync.waimai.shop")
public class ShopEle {
    private String platForm;
    private String shopId;
    private String sellerId;
    private String shopName;
    private String city;

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

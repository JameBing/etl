package com.wangjunneil.schedule.entity.jdhome;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
@Document(collection = "sync.jdhome.stock")
public class QueryStockRequest {
    //京东到家门店编码
    private String stationNo;
    //京东到家商品编码
    private Long skuId;
    //0门店上架 1门店下架
    private Integer doSale;

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getDoSale() {
        return doSale;
    }

    public void setDoSale(Integer doSale) {
        this.doSale = doSale;
    }
}

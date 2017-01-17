package com.wangjunneil.schedule.entity.jdhome;

/**
 * @author yangyongbing
 * @since 2017/1/11.
 */
public class BaseStockCenterRequest {
    private long skuId;    //京东商品编码
    private String stationNo;  //京东门店编码

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }
}

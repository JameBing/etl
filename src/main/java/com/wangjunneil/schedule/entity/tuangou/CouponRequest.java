package com.wangjunneil.schedule.entity.tuangou;

/**
 * @author yangyongbing
 * @since on 2017/3/20.
 */
public class CouponRequest {
    //团购卷码
    public String couponCode;

    //门店编号
    public String ePoiId;

    //验卷数量
    public int count;

    //第三方ERP订单号
    public String eOrderId;

    //日期
    public String date;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getePoiId() {
        return ePoiId;
    }

    public void setePoiId(String ePoiId) {
        this.ePoiId = ePoiId;
    }

    public String geteOrderId() {
        return eOrderId;
    }

    public void seteOrderId(String eOrderId) {
        this.eOrderId = eOrderId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

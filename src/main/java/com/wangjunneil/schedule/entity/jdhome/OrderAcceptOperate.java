package com.wangjunneil.schedule.entity.jdhome;

/**
 * @author yangyongbing
 * @since 2016/11/17.
 */
public class OrderAcceptOperate {
    private String orderId; //订单Id
    private Boolean isAgreed; //true 接单 false不接单
    private String operator;//操作人
    private String shopId;//门店Id

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getIsAgreed() {
        return isAgreed;
    }

    public void setIsAgreed(Boolean isAgreed) {
        this.isAgreed = isAgreed;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}

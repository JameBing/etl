package com.wangjunneil.schedule.entity.jdhome;

/**
 * @author yangyongbing
 * @since 2016/11/16.
 * 订单折扣Entity
 */
public class OrderDiscountDTO {
    private Long orderId;//订单主表订单id
    private Long adjustId;//调整单记录id（0:原单商品明细;非0:调整单id 或者 确认单id)
    private Long skuId;//	京东SKUID
    private String skuIds;//记录参加活动的sku数组
    private Integer discountType;//优惠类型(1:优惠码;3:优惠劵;4:满减;5:满折;6:首单优惠)
    private Integer discountDetailType;//小优惠类型(优惠码(1:满减;2:立减;3:满折;);优惠券(1:满减;2:立减;3:免运费劵;4:运费优惠N元;))
    private String discountCode;//优惠券编号
    private Long discountPrice;//优惠金额

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAdjustId() {
        return adjustId;
    }

    public void setAdjustId(Long adjustId) {
        this.adjustId = adjustId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public Integer getDiscountDetailType() {
        return discountDetailType;
    }

    public void setDiscountDetailType(Integer discountDetailType) {
        this.discountDetailType = discountDetailType;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }
}

package com.wangjunneil.schedule.entity.jdhome;

/**
 * @author yangyongbing
 * @since 2016/11/16.
 * 订单商品信息
 */
public class OrderProductDTO {
    private Long orderId;//订单主表订单id
    private Long adjustId;//调整单记录id（0:原单商品明细;非0:调整单id 或者 确认单id)
    private Long skuId;//京东SKUID
    private String skuName;//商品的名称+SKU规格
    private String skuIdIsv;//商家SKUID
    private Long skuSpuId;//京东内部商品ID(一个spu下有多个sku比如尺码或颜色不同，spu相同，sku不同)
    private Long skuJdPrice;//京东到家销售价
    private Integer skuCount;//下单数量
    private Boolean isGift;//0:默认值非赠品;1:赠品
    private Integer adjustMode;//调整方式(0:默认值，没调整，原订单明细;1:新增;2:删除;3:修改数量)
    private String upcCode;//商品upc码
    private Long skuStorePrice;//门店价
    private Long skuCostPrice;//成本价
    private Integer PromotionType;//商品级别促销类型(1、无优惠;2、秒杀;3、单品直降;4、限时抢购)
    private String skuTaxRate;//税率
    private Long promotionId;//促销ID

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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuIdIsv() {
        return skuIdIsv;
    }

    public void setSkuIdIsv(String skuIdIsv) {
        this.skuIdIsv = skuIdIsv;
    }

    public Long getSkuSpuId() {
        return skuSpuId;
    }

    public void setSkuSpuId(Long skuSpuId) {
        this.skuSpuId = skuSpuId;
    }

    public Long getSkuJdPrice() {
        return skuJdPrice;
    }

    public void setSkuJdPrice(Long skuJdPrice) {
        this.skuJdPrice = skuJdPrice;
    }

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public Integer getAdjustMode() {
        return adjustMode;
    }

    public void setAdjustMode(Integer adjustMode) {
        this.adjustMode = adjustMode;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public Long getSkuStorePrice() {
        return skuStorePrice;
    }

    public void setSkuStorePrice(Long skuStorePrice) {
        this.skuStorePrice = skuStorePrice;
    }

    public Long getSkuCostPrice() {
        return skuCostPrice;
    }

    public void setSkuCostPrice(Long skuCostPrice) {
        this.skuCostPrice = skuCostPrice;
    }

    public Integer getPromotionType() {
        return PromotionType;
    }

    public void setPromotionType(Integer promotionType) {
        PromotionType = promotionType;
    }

    public String getSkuTaxRate() {
        return skuTaxRate;
    }

    public void setSkuTaxRate(String skuTaxRate) {
        this.skuTaxRate = skuTaxRate;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }
}

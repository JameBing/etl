package com.wangjunneil.schedule.entity.jdhome;

/**
 * @author yangyongbing
 * @since 2016/11/16.
 * 订单扩展Entity
 */
public class OrderExtend {
    private Long orderId;//订单主表订单id
    private Integer buyerCoordType;//收货人地址坐标类型(1:gps;2:sogou经纬度;3:baidu经纬度;4:mapbar经纬度;5:google经纬度（腾讯、高德坐标）;6:sogou墨卡托)
    private Double buyerLng;//收货人地址坐标经度
    private Double buyerLat;//收货人地址坐标纬度
    private String orderInvoiceType;//发票类型
    private String orderInvoiceTitle;//发票抬头
    private String orderInvoiceContent;//发票内容
    private String orderBuyerRemark;//订单买家备注
    private String businessTag;//业务标识，用英文分号分隔（订单打标写入此字段，如one.dingshida）
    private String equipmentId;//设备id
    private String buyerPoi;//用户poi
    private Integer businessTagId;//行业标签id;
    private String artificerPortraitUrl;//技师头像URL

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getBuyerCoordType() {
        return buyerCoordType;
    }

    public void setBuyerCoordType(Integer buyerCoordType) {
        this.buyerCoordType = buyerCoordType;
    }

    public Double getBuyerLng() {
        return buyerLng;
    }

    public void setBuyerLng(Double buyerLng) {
        this.buyerLng = buyerLng;
    }

    public Double getBuyerLat() {
        return buyerLat;
    }

    public void setBuyerLat(Double buyerLat) {
        this.buyerLat = buyerLat;
    }

    public String getOrderInvoiceType() {
        return orderInvoiceType;
    }

    public void setOrderInvoiceType(String orderInvoiceType) {
        this.orderInvoiceType = orderInvoiceType;
    }

    public String getOrderInvoiceTitle() {
        return orderInvoiceTitle;
    }

    public void setOrderInvoiceTitle(String orderInvoiceTitle) {
        this.orderInvoiceTitle = orderInvoiceTitle;
    }

    public String getOrderInvoiceContent() {
        return orderInvoiceContent;
    }

    public void setOrderInvoiceContent(String orderInvoiceContent) {
        this.orderInvoiceContent = orderInvoiceContent;
    }

    public String getOrderBuyerRemark() {
        return orderBuyerRemark;
    }

    public void setOrderBuyerRemark(String orderBuyerRemark) {
        this.orderBuyerRemark = orderBuyerRemark;
    }

    public String getBusinessTag() {
        return businessTag;
    }

    public void setBusinessTag(String businessTag) {
        this.businessTag = businessTag;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getBuyerPoi() {
        return buyerPoi;
    }

    public void setBuyerPoi(String buyerPoi) {
        this.buyerPoi = buyerPoi;
    }

    public Integer getBusinessTagId() {
        return businessTagId;
    }

    public void setBusinessTagId(Integer businessTagId) {
        this.businessTagId = businessTagId;
    }

    public String getArtificerPortraitUrl() {
        return artificerPortraitUrl;
    }

    public void setArtificerPortraitUrl(String artificerPortraitUrl) {
        this.artificerPortraitUrl = artificerPortraitUrl;
    }
}

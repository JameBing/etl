package com.wangjunneil.schedule.entity.baidu;


import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwanbin on 2016-11-16.
 */

public class Order {

    //百度订单编号
    @SerializedName("order_id")
    private String orderId;

    //商家订单编号
    @SerializedName("source_order_id")
    private String sourceOrderId;

    //是否立即送餐，1 是 2 否
    @SerializedName("send_immediately")
    private Integer sendImmediately;

    //订单当日流水号
    @SerializedName("order_index")
    private Integer orderIndex;

    //送达时间类型 1定时达 2限时达
    @SerializedName("expect_time_mode")
    private Integer expectTimeMode;

    //期望送达时间,立即配送值为1,非立即配送为时间戳
    @SerializedName("send_time")
    private String sendTime;

    //取餐时间
    @SerializedName("pickup_time")
    private String pickupTime;

    //到店时间
    @SerializedName("atshop_time")
    private String atshopTime;

    //骑士到店时间
    @SerializedName("delivery_time")
    private String deliveryTime;

    //骑士取餐号
    @SerializedName("delivery_phone")
    private String deliveryPhone;

    //完成时间
    @SerializedName("finished_time")
    private String finishedTime;

    //确认时间
    @SerializedName("confirm_time")
    private String confirmTime;

    //取消时间
    @SerializedName("cancel_time")
    private String cancelTime;

    //配送费，单位：分
    @SerializedName("send_fee")
    private Integer sendFee;

    //餐盒费，单位：分
    @SerializedName("package_fee")
    private Integer packageFee;

    //优惠总金额，单位：分
    @SerializedName("discount_fee")
    private Integer discountFee;

    //商户实收总价，单位：分
    @SerializedName("shop_fee")
    private Integer shopFee;


    //订单总价，单位：分
    @SerializedName("total_fee")
    private Integer totalFee;

    //用户实付总价，单位：分
    @SerializedName("user_fee")
    private Integer userFee;
    //支付类型，1 下线 2 在线
    @SerializedName("pay_type")
    private String payType;

    //是否需要发票 1:是 2:否
    @SerializedName("need_invoice")
    private Integer needInvoice;

    //发票抬头
    @SerializedName("invoice_title")
    private String invoiceTitle;

    //订单备注
    private String remark;

    //物流 1 百度 2 自配送
    @SerializedName("delivery_party")
    private Integer deliveryParty;

    //订单创建时间
    @SerializedName("create_time")
    private Integer createTime;

    @SerializedName("pay_status")
    private String payStatus;

    //订单状态
    private Integer status;

    //订单状态
    @SerializedName("type")
    private  String  type;

    //取消原因
    @SerializedName("reason")
    private  String reason;

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId(){
        return this.orderId;
    }

    public void setSourceOrderId(String sourceOrderId){
        this.sourceOrderId = sourceOrderId;
    }

    public String getSourceOrderId(){
        return  this.sourceOrderId;
    }

    public void setSendImmediately(Integer sendImmediately){
        this.sendImmediately = sendImmediately;
    }

    public Integer getSendImmediately(){
        return this.sendImmediately;
    }

    public void setSendTime(String sendTime){
        this.sendTime = sendTime;
    }

    public String getSendTime(){
        return  this.sendTime;
    }

    public  void setSendFee(Integer sendFee){
        this.sendFee = sendFee;
    }

    public Integer getSendFee(){
        return  this.sendFee;
    }

    public void setPackageFee(Integer packageFee){
        this.packageFee = packageFee;
    }

    public Integer getPackageFee(){
        return this.packageFee;
    }

    public void setDiscountFee(Integer discountFee){
        this.discountFee = discountFee;
    }

    public Integer getDiscountFee(){
        return this.discountFee;
    }

    public void setShopFee(Integer shopFee){
        this.shopFee = shopFee;
    }

    public Integer getShopFee(){
        return this.shopFee;
    }

    public void setTotalFee(Integer totalFee){
        this.totalFee = totalFee;
    }

    public Integer getTotalFee(){
        return  this.totalFee;
    }

    public void setUserFee(Integer userFee){
        this.userFee = userFee;
    }

    public Integer getUserFee(){
        return this.userFee;
    }

    public void setPayType(String payType){
        this.payType = payType;
    }

    public String getPayType(){
        return this.payType;
    }

    public void setNeedInvoice(Integer needInvoice){
        this.needInvoice = needInvoice;
    }

    public Integer getNeedInvoice(){
        return this.needInvoice;
    }

    public void setInvoiceTitle(String invoiceTitle){
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceTitle(){
        return this.invoiceTitle;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return this.remark;
    }

    public Integer getDeliveryParty() {
        return deliveryParty;
    }
    public void setDeliveryParty(Integer deliveryParty) {
        this.deliveryParty = deliveryParty;
    }

    public void setCreateTime(Integer createTime){
        this.createTime = createTime;
    }

    public Integer getCreateTime(){
        return this.createTime;
    }

    public  void setPayStatus(String payStatus){
        this.payStatus = payStatus;
    }

    public String getPayStatus(){
        return this.payStatus;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return this.status;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Integer getExpectTimeMode() {
        return expectTimeMode;
    }

    public void setExpectTimeMode(Integer expectTimeMode) {
        this.expectTimeMode = expectTimeMode;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getAtshopTime() {
        return atshopTime;
    }

    public void setAtshopTime(String atshopTime) {
        this.atshopTime = atshopTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;}
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

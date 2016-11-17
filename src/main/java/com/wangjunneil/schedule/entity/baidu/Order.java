package com.wangjunneil.schedule.entity.baidu;


import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwanbin on 2016-11-16.
 */

public class Order {

    //百度订单编号
    @SerializedName("order_id")
    private String orderId;

    //是否立即送餐，1 是 2 否
    @SerializedName("send_immediately")
    private int sendImmediately;

    //期望送达时间,立即配送值为1,非立即配送为时间戳
    @SerializedName("send_time")
    private String sendTime;

    //配送费，单位：分
    @SerializedName("send_fee")
    private int sendFee;

    //餐盒费，单位：分
    @SerializedName("package_fee")
    private int packageFee;

    //优惠总金额，单位：分
    @SerializedName("discount_fee")
    private int discountFee;

    //商户实收总价，单位：分
    @SerializedName("shop_fee")
    private int shopFee;


    //订单总价，单位：分
    @SerializedName("total_fee")
    private int totalFee;

    //用户实付总价，单位：分
    @SerializedName("user_fee")
    private int userFee;
    //支付类型，1 下线 2 在线
    @SerializedName("pay_type")
    private String payType;

    //是否需要发票 1:是 2:否
    @SerializedName("need_invoice")
    private int needInvoice;

    //发票抬头
    @SerializedName("invoice_title")
    private String invoiceTitle;

    //订单备注
    private String remark;

    //物流 1 百度 2 自配送
    @SerializedName("delivery_party")
    private int deliveryParty;

    //订单创建时间
    @SerializedName("create_time")
    private int createTime;

    @SerializedName("pay_status")
    private String payStatus;

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId(){
        return "14347012309352";
    }

    public void setSendImmediately(int sendImmediately){
        this.sendImmediately = sendImmediately;
    }

    public int getSendImmediately(){
        return this.sendImmediately;
    }

    public void setSendTime(String sendTime){
        this.sendTime = sendTime;
    }

    public String getSendTime(){
        return  this.sendTime;
    }

    public  void setSendFee(int sendFee){
        this.sendFee = sendFee;
    }

    public int getSendFee(){
        return  this.sendFee;
    }

    public void setPackageFee(int packageFee){
        this.packageFee = packageFee;
    }

    public int getPackageFee(){
        return this.packageFee;
    }

    public void setDiscountFee(int discountFee){
        this.discountFee = discountFee;
    }

    public int getDiscountFee(){
        return this.discountFee;
    }

    public void setShopFee(int shopFee){
        this.shopFee = shopFee;
    }

    public int getShopFee(){
        return this.shopFee;
    }

    public void setTotalFee(int totalFee){
        this.totalFee = totalFee;
    }

    public int getTotalFee(){
        return  this.totalFee;
    }

    public void setUserFee(int userFee){
        this.userFee = userFee;
    }

    public int getUserFee(){
        return this.userFee;
    }

    public void setPayType(String payType){
        this.payType = payType;
    }

    public String getPayType(){
        return this.payType;
    }

    public void setNeedInvoice(int needInvoice){
        this.needInvoice = needInvoice;
    }

    public int getNeedInvoice(){
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

    public void setDeliveryParty(int deliveryParty){
        this.deliveryParty = deliveryParty;
    }

    public int getDeliveryParty(){
        return this.deliveryParty;
    }

    public void setCreateTime(int createTime){
        this.createTime = createTime;
    }

    public int getCreateTime(){
        return this.createTime;
    }

    public  void setPayStatus(String payStatus){
        this.payStatus = payStatus;
    }

    public String getPayStatus(){
        return this.payStatus;
    }
}

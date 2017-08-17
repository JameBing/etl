package com.wangjunneil.schedule.entity.eleme;

import eleme.openapi.sdk.api.entity.order.OActivity;
import eleme.openapi.sdk.api.entity.order.OGoodsGroup;
import eleme.openapi.sdk.api.enumeration.order.InvoiceType;
import eleme.openapi.sdk.api.enumeration.order.OOrderRefundStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by yangyb on 2017/8/9.
 */
public class OrderEle {
    private String address;
    private Date createdAt;
    private Date activeAt;
    private double deliverFee;
    private Date deliverTime;
    private String description;
    private List<OGoodsGroup> groups;
    private String invoice;
    private boolean book;
    private boolean onlinePaid;
    private String id;
    private List<String> phoneList;
    private long shopId;
    private String openId;
    private String shopName;
    private int daySn;
    private String status;
    private OOrderRefundStatus refundStatus;
    private int userId;
    private double totalPrice;
    private double originalPrice;
    private String consignee;
    private String deliveryGeo;
    private String deliveryPoiAddress;
    private boolean invoiced;
    private double income;
    private double serviceRate;
    private double serviceFee;
    private double hongbao;
    private double packageFee;
    private double activityTotal;
    private double shopPart;
    private double elemePart;
    private boolean downgraded;
    private Date secretPhoneExpireTime;
    private List<OActivity> orderActivities;
    private InvoiceType invoiceType;
    private String taxpayerId;


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getActiveAt() {
        return this.activeAt;
    }

    public void setActiveAt(Date activeAt) {
        this.activeAt = activeAt;
    }

    public double getDeliverFee() {
        return this.deliverFee;
    }

    public void setDeliverFee(double deliverFee) {
        this.deliverFee = deliverFee;
    }

    public Date getDeliverTime() {
        return this.deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OGoodsGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(List<OGoodsGroup> groups) {
        this.groups = groups;
    }

    public String getInvoice() {
        return this.invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public boolean getBook() {
        return this.book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public boolean getOnlinePaid() {
        return this.onlinePaid;
    }

    public void setOnlinePaid(boolean onlinePaid) {
        this.onlinePaid = onlinePaid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPhoneList() {
        return this.phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public long getShopId() {
        return this.shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getDaySn() {
        return this.daySn;
    }

    public void setDaySn(int daySn) {
        this.daySn = daySn;
    }

    public boolean isBook() {
        return book;
    }

    public boolean isOnlinePaid() {
        return onlinePaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isInvoiced() {
        return invoiced;
    }

    public boolean isDowngraded() {
        return downgraded;
    }

    public OOrderRefundStatus getRefundStatus() {
        return this.refundStatus;
    }

    public void setRefundStatus(OOrderRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getOriginalPrice() {
        return this.originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getConsignee() {
        return this.consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getDeliveryGeo() {
        return this.deliveryGeo;
    }

    public void setDeliveryGeo(String deliveryGeo) {
        this.deliveryGeo = deliveryGeo;
    }

    public String getDeliveryPoiAddress() {
        return this.deliveryPoiAddress;
    }

    public void setDeliveryPoiAddress(String deliveryPoiAddress) {
        this.deliveryPoiAddress = deliveryPoiAddress;
    }

    public boolean getInvoiced() {
        return this.invoiced;
    }

    public void setInvoiced(boolean invoiced) {
        this.invoiced = invoiced;
    }

    public double getIncome() {
        return this.income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getServiceRate() {
        return this.serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public double getServiceFee() {
        return this.serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getHongbao() {
        return this.hongbao;
    }

    public void setHongbao(double hongbao) {
        this.hongbao = hongbao;
    }

    public double getPackageFee() {
        return this.packageFee;
    }

    public void setPackageFee(double packageFee) {
        this.packageFee = packageFee;
    }

    public double getActivityTotal() {
        return this.activityTotal;
    }

    public void setActivityTotal(double activityTotal) {
        this.activityTotal = activityTotal;
    }

    public double getShopPart() {
        return this.shopPart;
    }

    public void setShopPart(double shopPart) {
        this.shopPart = shopPart;
    }

    public double getElemePart() {
        return this.elemePart;
    }

    public void setElemePart(double elemePart) {
        this.elemePart = elemePart;
    }

    public boolean getDowngraded() {
        return this.downgraded;
    }

    public void setDowngraded(boolean downgraded) {
        this.downgraded = downgraded;
    }

    public Date getSecretPhoneExpireTime() {
        return this.secretPhoneExpireTime;
    }

    public void setSecretPhoneExpireTime(Date secretPhoneExpireTime) {
        this.secretPhoneExpireTime = secretPhoneExpireTime;
    }

    public List<OActivity> getOrderActivities() {
        return this.orderActivities;
    }

    public void setOrderActivities(List<OActivity> orderActivities) {
        this.orderActivities = orderActivities;
    }

    public InvoiceType getInvoiceType() {
        return this.invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getTaxpayerId() {
        return this.taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }
}

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/11/21.
 */
public class Order {
    @SerializedName("address")
    private String address;
    @SerializedName("consignee")
    private String consignee;
    @SerializedName("created_at")
    private Date createdat;
    @SerializedName("active_at")
    private Date activeat;
    @SerializedName("deliver_fee")
    private String deliverfee;
    @SerializedName("deliver_time")
    private Date delivertime;
    @SerializedName("description")
    private String description;
    @SerializedName("detail")
    private Detail detail;
    @SerializedName("invoice")
    private String invoice;
    @SerializedName("is_book")
    private int isbook;
    @SerializedName("is_online_paid")
    private int isonlinepaid;
    @SerializedName("order_id")
    private String orderid;
    @SerializedName("phone_list")
    private List phonelist;
    @SerializedName("tp_restaurant_id")
    private String tprestaurantid;
    @SerializedName("restaurant_id")
    private String restaurantid;
    @SerializedName("inner_id")
    private String innerid;
    @SerializedName("restaurant_name")
    private String restaurantname;
    @SerializedName("restaurant_number")
    private int restaurantnumber;
    @SerializedName("status_code")
    private int statuscode;
    @SerializedName("refund_code")
    private int refundcode;
    @SerializedName("total_price")
    private int totalprice;
    @SerializedName("original_price")
    private int originalprice;
    @SerializedName("user_id")
    private String userid;
    @SerializedName("user_name")
    private String username;
    @SerializedName("delivery_geo")
    private String deliverygeo;

    @Override
    public String toString() {
        return "Order{" +
            "address='" + address + '\'' +
            ", consignee='" + consignee + '\'' +
            ", createdat=" + createdat +
            ", activeat=" + activeat +
            ", deliverfee='" + deliverfee + '\'' +
            ", delivertime=" + delivertime +
            ", description='" + description + '\'' +
            ", detail=" + detail +
            ", invoice='" + invoice + '\'' +
            ", isbook=" + isbook +
            ", isonlinepaid=" + isonlinepaid +
            ", orderid='" + orderid + '\'' +
            ", phonelist=" + phonelist +
            ", tprestaurantid='" + tprestaurantid + '\'' +
            ", restaurantid='" + restaurantid + '\'' +
            ", innerid='" + innerid + '\'' +
            ", restaurantname='" + restaurantname + '\'' +
            ", restaurantnumber=" + restaurantnumber +
            ", statuscode=" + statuscode +
            ", refundcode=" + refundcode +
            ", totalprice=" + totalprice +
            ", originalprice=" + originalprice +
            ", userid='" + userid + '\'' +
            ", username='" + username + '\'' +
            ", deliverygeo='" + deliverygeo + '\'' +
            '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getActiveat() {
        return activeat;
    }

    public void setActiveat(Date activeat) {
        this.activeat = activeat;
    }

    public String getDeliverfee() {
        return deliverfee;
    }

    public void setDeliverfee(String deliverfee) {
        this.deliverfee = deliverfee;
    }

    public Date getDelivertime() {
        return delivertime;
    }

    public void setDelivertime(Date delivertime) {
        this.delivertime = delivertime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public int getIsbook() {
        return isbook;
    }

    public void setIsbook(int isbook) {
        this.isbook = isbook;
    }

    public int getIsonlinepaid() {
        return isonlinepaid;
    }

    public void setIsonlinepaid(int isonlinepaid) {
        this.isonlinepaid = isonlinepaid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public List getPhonelist() {
        return phonelist;
    }

    public void setPhonelist(List phonelist) {
        this.phonelist = phonelist;
    }

    public String getTprestaurantid() {
        return tprestaurantid;
    }

    public void setTprestaurantid(String tprestaurantid) {
        this.tprestaurantid = tprestaurantid;
    }

    public String getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(String restaurantid) {
        this.restaurantid = restaurantid;
    }

    public String getInnerid() {
        return innerid;
    }

    public void setInnerid(String innerid) {
        this.innerid = innerid;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public int getRestaurantnumber() {
        return restaurantnumber;
    }

    public void setRestaurantnumber(int restaurantnumber) {
        this.restaurantnumber = restaurantnumber;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public int getRefundcode() {
        return refundcode;
    }

    public void setRefundcode(int refundcode) {
        this.refundcode = refundcode;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(int originalprice) {
        this.originalprice = originalprice;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeliverygeo() {
        return deliverygeo;
    }

    public void setDeliverygeo(String deliverygeo) {
        this.deliverygeo = deliverygeo;
    }
}

package com.wangjunneil.schedule.entity.eleme;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/11/25.
 */
public class Restaurant {
    @SerializedName("restaurant_name")
    private String restaurantname;//店铺名字
    @SerializedName("city_id")
    private int cityid;//所在城市id
    @SerializedName("restaurant_id")
    private String restaurantid;//店铺id
    @SerializedName("packing_fee")
    private String packingfee;//包装费
    @SerializedName("address_text")
    private String addresstext;//地址
    @SerializedName("is_time_ensure")
    private int istimeensure;//保证
    @SerializedName("is_premium")
    private int ispremium;//保险
    @SerializedName("is_open")
    private int isopen;//开/关店
    @SerializedName("invoice")
    private int invoice;//发票
    @SerializedName("flavors")
    private String flavors;//口味
    @SerializedName("is_phone_hidden")
    private int isphonehidden;//隐藏手机号
    @SerializedName("payment_method")
    private int paymentmethod;//付款方式
    @SerializedName("is_valid")
    private int isvalid;//是否有效
    @SerializedName("close_description")
    private String closedescription;//关闭描述
    @SerializedName("no_agent_fee_total")
    private int noagentfeetotal;//代理费用
    @SerializedName("latitude")
    private Double latitude;//纬度
    @SerializedName("serving_time")
    private List<String> servingtime;//服务时间
    @SerializedName("city_code")
    private String citycode;//城市代码
    @SerializedName("online_payment")
    private int onlinepayment;//在线付款
    @SerializedName("description")
    private String description;//描述
    @SerializedName("open_time_bitmap")
    private String opentimebitmap;//位图
    @SerializedName("inner_id")
    private String innerid;//内部id
    @SerializedName("promotion_info")
    private String promotioninfo;//推广
    @SerializedName("invoice_min_amount")
    private int invoiceminamount;//发票数量
    @SerializedName("recent_food_popularity")
    private int recentfoodpopularity;//最近普及食物
    @SerializedName("time_ensure_full_description")
    private String timeensurefulldescription;//未知
    @SerializedName("deliver_spent")
    private int deliverspent;//配送
    @SerializedName("num_ratings")
    private List<Integer> numratings;//餐厅评价
    @SerializedName("deliver_times")
    private List<String> delivertimes;//交货时间
    @SerializedName("mobile")
    private String mobile;//手机
    @SerializedName("order_mode")
    private int ordermode;//订单模式
    @SerializedName("restaurant_url")
    private String restauranturl;//店铺网址
    @SerializedName("longitude")
    private Double longitude;//经度
    @SerializedName("book_time_bitmap")
    private String booktimebitmap;//本次位图
    @SerializedName("image_url")
    private String imageurl;//图片网址
    @SerializedName("support_online")
    private Boolean supportonline;//支持在线
    @SerializedName("is_bookable")
    private int isbookable;//是否可预订
    @SerializedName("phone_list")
    private List<String> phonelist;//联系列表
    @SerializedName("busy_level")
    private int busylevel;//
    @SerializedName("agent_fee")
    private int agentfee;//代理费

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(String restaurantid) {
        this.restaurantid = restaurantid;
    }

    public String getPackingfee() {
        return packingfee;
    }

    public void setPackingfee(String packingfee) {
        this.packingfee = packingfee;
    }

    public String getAddresstext() {
        return addresstext;
    }

    public void setAddresstext(String addresstext) {
        this.addresstext = addresstext;
    }

    public int getIstimeensure() {
        return istimeensure;
    }

    public void setIstimeensure(int istimeensure) {
        this.istimeensure = istimeensure;
    }

    public int getIspremium() {
        return ispremium;
    }

    public void setIspremium(int ispremium) {
        this.ispremium = ispremium;
    }

    public int getIsopen() {
        return isopen;
    }

    public void setIsopen(int isopen) {
        this.isopen = isopen;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public String getFlavors() {
        return flavors;
    }

    public void setFlavors(String flavors) {
        this.flavors = flavors;
    }

    public int getIsphonehidden() {
        return isphonehidden;
    }

    public void setIsphonehidden(int isphonehidden) {
        this.isphonehidden = isphonehidden;
    }

    public int getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(int paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public int getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public String getClosedescription() {
        return closedescription;
    }

    public void setClosedescription(String closedescription) {
        this.closedescription = closedescription;
    }

    public int getNoagentfeetotal() {
        return noagentfeetotal;
    }

    public void setNoagentfeetotal(int noagentfeetotal) {
        this.noagentfeetotal = noagentfeetotal;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<String> getServingtime() {
        return servingtime;
    }

    public void setServingtime(List<String> servingtime) {
        this.servingtime = servingtime;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public int getOnlinepayment() {
        return onlinepayment;
    }

    public void setOnlinepayment(int onlinepayment) {
        this.onlinepayment = onlinepayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpentimebitmap() {
        return opentimebitmap;
    }

    public void setOpentimebitmap(String opentimebitmap) {
        this.opentimebitmap = opentimebitmap;
    }

    public String getInnerid() {
        return innerid;
    }

    public void setInnerid(String innerid) {
        this.innerid = innerid;
    }

    public String getPromotioninfo() {
        return promotioninfo;
    }

    public void setPromotioninfo(String promotioninfo) {
        this.promotioninfo = promotioninfo;
    }

    public int getInvoiceminamount() {
        return invoiceminamount;
    }

    public void setInvoiceminamount(int invoiceminamount) {
        this.invoiceminamount = invoiceminamount;
    }

    public int getRecentfoodpopularity() {
        return recentfoodpopularity;
    }

    public void setRecentfoodpopularity(int recentfoodpopularity) {
        this.recentfoodpopularity = recentfoodpopularity;
    }

    public String getTimeensurefulldescription() {
        return timeensurefulldescription;
    }

    public void setTimeensurefulldescription(String timeensurefulldescription) {
        this.timeensurefulldescription = timeensurefulldescription;
    }

    public int getDeliverspent() {
        return deliverspent;
    }

    public void setDeliverspent(int deliverspent) {
        this.deliverspent = deliverspent;
    }

    public List<Integer> getNumratings() {
        return numratings;
    }

    public void setNumratings(List<Integer> numratings) {
        this.numratings = numratings;
    }

    public List<String> getDelivertimes() {
        return delivertimes;
    }

    public void setDelivertimes(List<String> delivertimes) {
        this.delivertimes = delivertimes;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOrdermode() {
        return ordermode;
    }

    public void setOrdermode(int ordermode) {
        this.ordermode = ordermode;
    }

    public String getRestauranturl() {
        return restauranturl;
    }

    public void setRestauranturl(String restauranturl) {
        this.restauranturl = restauranturl;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getBooktimebitmap() {
        return booktimebitmap;
    }

    public void setBooktimebitmap(String booktimebitmap) {
        this.booktimebitmap = booktimebitmap;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Boolean getSupportonline() {
        return supportonline;
    }

    public void setSupportonline(Boolean supportonline) {
        this.supportonline = supportonline;
    }

    public int getIsbookable() {
        return isbookable;
    }

    public void setIsbookable(int isbookable) {
        this.isbookable = isbookable;
    }

    public List<String> getPhonelist() {
        return phonelist;
    }

    public void setPhonelist(List<String> phonelist) {
        this.phonelist = phonelist;
    }

    public int getBusylevel() {
        return busylevel;
    }

    public void setBusylevel(int busylevel) {
        this.busylevel = busylevel;
    }

    public int getAgentfee() {
        return agentfee;
    }

    public void setAgentfee(int agentfee) {
        this.agentfee = agentfee;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "restaurantname='" + restaurantname + '\'' +
            ", cityid=" + cityid +
            ", restaurantid='" + restaurantid + '\'' +
            ", packingfee='" + packingfee + '\'' +
            ", addresstext='" + addresstext + '\'' +
            ", istimeensure=" + istimeensure +
            ", ispremium=" + ispremium +
            ", isopen=" + isopen +
            ", invoice=" + invoice +
            ", flavors='" + flavors + '\'' +
            ", isphonehidden=" + isphonehidden +
            ", paymentmethod=" + paymentmethod +
            ", isvalid=" + isvalid +
            ", closedescription='" + closedescription + '\'' +
            ", noagentfeetotal=" + noagentfeetotal +
            ", latitude=" + latitude +
            ", servingtime=" + servingtime +
            ", citycode='" + citycode + '\'' +
            ", onlinepayment=" + onlinepayment +
            ", description='" + description + '\'' +
            ", opentimebitmap='" + opentimebitmap + '\'' +
            ", innerid='" + innerid + '\'' +
            ", promotioninfo='" + promotioninfo + '\'' +
            ", invoiceminamount=" + invoiceminamount +
            ", recentfoodpopularity=" + recentfoodpopularity +
            ", timeensurefulldescription='" + timeensurefulldescription + '\'' +
            ", deliverspent=" + deliverspent +
            ", numratings=" + numratings +
            ", delivertimes=" + delivertimes +
            ", mobile='" + mobile + '\'' +
            ", ordermode=" + ordermode +
            ", restauranturl='" + restauranturl + '\'' +
            ", longitude=" + longitude +
            ", booktimebitmap='" + booktimebitmap + '\'' +
            ", imageurl='" + imageurl + '\'' +
            ", supportonline=" + supportonline +
            ", isbookable=" + isbookable +
            ", phonelist=" + phonelist +
            ", busylevel=" + busylevel +
            ", agentfee=" + agentfee +
            '}';
    }
}

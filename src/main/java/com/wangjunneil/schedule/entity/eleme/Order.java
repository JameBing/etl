package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/11/21.
 */
@Document(collection = "sync.eleme.order")
public class Order {
    //原价
    @SerializedName("original_price")
    private Double originalprice;
    //餐厅名字
    @SerializedName("restaurant_name")
    private String restaurantname;
    //退款代码
    @SerializedName("refund_code")
    private int refundcode;
    //状态码
    @SerializedName("status_code")
    private int statuscode;
    //餐厅id
    @SerializedName("restaurant_id")
    private String restaurantid;
    //服务费
    @SerializedName("service_fee")
    private double servicefee;
    //客户名字
    @SerializedName("consignee")
    private String consignee;
    //发票
    @SerializedName("invoice")
    private String invoice;
    //描述
    @SerializedName("description")
    private String description;
    //用户id
    @SerializedName("user_id")
    private String userid;
    //经纬度
    @SerializedName("delivery_geo")
    private String deliverygeo;
    //详情
    @SerializedName("detail")
    private Body detail;
    //活跃时间
    @SerializedName("active_at")
    private Date activeat;
    //发票
    @SerializedName("invoiced")
    private String invoiced;
    //用户名
    @SerializedName("user_name")
    private String username;
    //快递费
    @SerializedName("deliver_fee")
    private Double deliverfee;
    @SerializedName("is_book")
    private int isbook;
    //餐厅部分费用
    @SerializedName("restaurant_part")
    private Double restaurantpart;
    //交付时间
    @SerializedName("deliver_time")
    private Date delivertime;
    //订单ID
    @SerializedName("order_id")
    private String orderid;
    //eleme部分费用
    @SerializedName("eleme_part")
    private Double elemepart;
    //收入
    @SerializedName("income")
    private Double income;
    //餐厅编号
    @SerializedName("restaurant_number")
    private String restaurantnumber;
    //配送地址
    @SerializedName("address")
    private String address;
    //红包
    @SerializedName("hongbao")
    private Double hongbao;
    //是否在线支付
    @SerializedName("is_online_paid")
    private int isonlinepaid;
    //交货地址
    @SerializedName("delivery_poi_address")
    private String deliverypoiaddress;
    //总价
    @SerializedName("total_price")
    private Double totalprice;
    //创建于
    @SerializedName("created_at")
    private Date createdat;
    //服务率
    @SerializedName("service_rate")
    private Double servicerate;
    //包装费
    @SerializedName("package_fee")
    private Double packagefee;
    //活动总数
    @SerializedName("activity_total")
    private int activitytotal;
    //联系列表
    @SerializedName("phone_list")
    private List phonelist;

    public Double getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(Double originalprice) {
        this.originalprice = originalprice;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public int getRefundcode() {
        return refundcode;
    }

    public void setRefundcode(int refundcode) {
        this.refundcode = refundcode;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(String restaurantid) {
        this.restaurantid = restaurantid;
    }

    public double getServicefee() {
        return servicefee;
    }

    public void setServicefee(double servicefee) {
        this.servicefee = servicefee;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeliverygeo() {
        return deliverygeo;
    }

    public void setDeliverygeo(String deliverygeo) {
        this.deliverygeo = deliverygeo;
    }

    public Body getDetail() {
        return detail;
    }

    public void setDetail(Body detail) {
        this.detail = detail;
    }

    public Date getActiveat() {
        return activeat;
    }

    public void setActiveat(Date activeat) {
        this.activeat = activeat;
    }

    public String getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(String invoiced) {
        this.invoiced = invoiced;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getDeliverfee() {
        return deliverfee;
    }

    public void setDeliverfee(Double deliverfee) {
        this.deliverfee = deliverfee;
    }

    public int getIsbook() {
        return isbook;
    }

    public void setIsbook(int isbook) {
        this.isbook = isbook;
    }

    public Double getRestaurantpart() {
        return restaurantpart;
    }

    public void setRestaurantpart(Double restaurantpart) {
        this.restaurantpart = restaurantpart;
    }

    public Date getDelivertime() {
        return delivertime;
    }

    public void setDelivertime(Date delivertime) {
        this.delivertime = delivertime;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Double getElemepart() {
        return elemepart;
    }

    public void setElemepart(Double elemepart) {
        this.elemepart = elemepart;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public String getRestaurantnumber() {
        return restaurantnumber;
    }

    public void setRestaurantnumber(String restaurantnumber) {
        this.restaurantnumber = restaurantnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getHongbao() {
        return hongbao;
    }

    public void setHongbao(Double hongbao) {
        this.hongbao = hongbao;
    }

    public int getIsonlinepaid() {
        return isonlinepaid;
    }

    public void setIsonlinepaid(int isonlinepaid) {
        this.isonlinepaid = isonlinepaid;
    }

    public String getDeliverypoiaddress() {
        return deliverypoiaddress;
    }

    public void setDeliverypoiaddress(String deliverypoiaddress) {
        this.deliverypoiaddress = deliverypoiaddress;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Double getServicerate() {
        return servicerate;
    }

    public void setServicerate(Double servicerate) {
        this.servicerate = servicerate;
    }

    public Double getPackagefee() {
        return packagefee;
    }

    public void setPackagefee(Double packagefee) {
        this.packagefee = packagefee;
    }

    public int getActivitytotal() {
        return activitytotal;
    }

    public void setActivitytotal(int activitytotal) {
        this.activitytotal = activitytotal;
    }

    public List getPhonelist() {
        return phonelist;
    }

    public void setPhonelist(List phonelist) {
        this.phonelist = phonelist;
    }
}

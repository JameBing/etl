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
    //退单状态
    @SerializedName("refund_code")
    private int refundcode;
    //订单状态
    @SerializedName("status_code")
    private int statuscode;
    //餐厅id
    @SerializedName("restaurant_id")
    private String restaurantid;
    //服务费
    @SerializedName("service_fee")
    private Double servicefee;
    //收货人
    @SerializedName("consignee")
    private String consignee;
    //发票抬头
    @SerializedName("invoice")
    private String invoice;
    //订单备注
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
    //生效时间
    @SerializedName("active_at")
    private Date activeat;
    //是否需要发票
    @SerializedName("invoiced")
    private int invoiced;
    //用户名
    @SerializedName("user_name")
    private String username;
    //配送费
    @SerializedName("deliver_fee")
    private Double deliverfee;
    //是否预订单
    @SerializedName("is_book")
    private int  isbook;
    //店铺承担活动费用
    @SerializedName("restaurant_part")
    private Double restaurantpart;
    //送餐时间
    @SerializedName("deliver_time")
    private Date delivertime;
    //订单ID
    @SerializedName("order_id")
    private Long orderid;
    //饿了么承担活动费用
    @SerializedName("eleme_part")
    private Double elemepart;
    //店铺实收
    @SerializedName("income")
    private Double income;
    //餐厅当日订单序号
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
    private int  isonlinepaid;
    //顾客送餐详情地址
    @SerializedName("delivery_poi_address")
    private String deliverypoiaddress;
    //总价
    @SerializedName("total_price")
    private Double totalprice;
    //下单时间
    @SerializedName("created_at")
    private Date createdat;
    //服务率
    @SerializedName("service_rate")
    private Double servicerate;
    //包装费
    @SerializedName("package_fee")
    private Double packagefee;
    //订单活动总额
    @SerializedName("activity_total")
    private Double activitytotal;
    //联系列表
    @SerializedName("phone_list")
    private List phonelist;
    @SerializedName("distribution")
    private Distribution distribution;
    //配送状态（仅第三方配送）
    @SerializedName("deliver_status")
    private int deliverstatus;
    //饿了么内部餐厅id,提交给业务人员绑定时使用的ID
    @SerializedName("inner_id")
    private String innerid;


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

    public Double getServicefee() {
        return servicefee;
    }

    public void setServicefee(Double servicefee) {
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

    public int getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(int invoiced) {
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

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
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

    public Double getActivitytotal() {
        return activitytotal;
    }

    public void setActivitytotal(Double activitytotal) {
        this.activitytotal = activitytotal;
    }

    public List getPhonelist() {
        return phonelist;
    }

    public void setPhonelist(List phonelist) {
        this.phonelist = phonelist;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    public int getDeliverstatus() {
        return deliverstatus;
    }

    public void setDeliverstatus(int deliverstatus) {
        this.deliverstatus = deliverstatus;
    }

    public String getInnerid() {
        return innerid;
    }

    public void setInnerid(String innerid) {
        this.innerid = innerid;
    }
}

package com.wangjunneil.schedule.entity.meituan;

import com.google.gson.annotations.SerializedName;
import com.sankuai.meituan.waimai.opensdk.vo.OrderExtraParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderFoodDetailParam;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Administrator on 2016-12-7.
 */
@Document(collection = "sync.meituan.order")
public class OrderInfo {

    @SerializedName("order_id")
    private Long orderid;
    @SerializedName("wm_order_id_view")
    private Long wmorderidview;
    @SerializedName("app_poi_code")
    private String apppoicode;
    @SerializedName("wm_poi_name")
    private String wmpoiname;
    @SerializedName("wm_poi_address")
    private String wmpoiaddress;
    @SerializedName("wm_poi_phone")
    private String wmpoiphone;
    @SerializedName("recipient_address")
    private String recipientaddress;
    @SerializedName("recipient_phone")
    private String recipientphone;
    @SerializedName("recipient_name")
    private String recipientname;
    @SerializedName("shipping_fee")
    private Float shippingfee;
    @SerializedName("total")
    private Double total;
    @SerializedName("original_price")
    private Double originalprice;
    @SerializedName("caution")
    private String caution;
    @SerializedName("shipper_phone")
    private String shipperphone;
    @SerializedName("status")
    private Integer status;
    @SerializedName("city_id")
    private Long cityid;
    @SerializedName("has_invoiced")
    private Integer hasinvoiced;
    @SerializedName("invoice_title")
    private String invoicetitle;
    @SerializedName("ctime")
    private Long ctime;
    @SerializedName("utime")
    private Long utime;
    @SerializedName("delivery_time")
    private Long deliverytime;
    @SerializedName("is_third_shipping")
    private Integer isthirdshipping;
    @SerializedName("pay_type")
    private Integer paytype;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("day_seq")
    private Integer dayseq;
    @SerializedName("is_favorites")
    private Boolean isfavorites;
    @SerializedName("is_poi_first_order")
    private Boolean ispoifirstorder;
    @SerializedName("dinners_number")
    private Integer dinnersnumber;
    @SerializedName("logistics_code")
    private String logisticscode;
    @SerializedName("poi_receive_detail")
    private String poireceivedetail;
    @SerializedName("detail")
    private List<OrderFoodDetailParam> detail;
    @SerializedName("extras")
    private List<OrderExtraParam> extras;


    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getWmorderidview() {
        return wmorderidview;
    }

    public void setWmorderidview(Long wmorderidview) {
        this.wmorderidview = wmorderidview;
    }

    public String getApppoicode() {
        return apppoicode;
    }

    public void setApppoicode(String apppoicode) {
        this.apppoicode = apppoicode;
    }

    public String getWmpoiname() {
        return wmpoiname;
    }

    public void setWmpoiname(String wmpoiname) {
        this.wmpoiname = wmpoiname;
    }

    public String getWmpoiaddress() {
        return wmpoiaddress;
    }

    public void setWmpoiaddress(String wmpoiaddress) {
        this.wmpoiaddress = wmpoiaddress;
    }

    public String getWmpoiphone() {
        return wmpoiphone;
    }

    public void setWmpoiphone(String wmpoiphone) {
        this.wmpoiphone = wmpoiphone;
    }

    public String getRecipientaddress() {
        return recipientaddress;
    }

    public void setRecipientaddress(String recipientaddress) {
        this.recipientaddress = recipientaddress;
    }

    public String getRecipientphone() {
        return recipientphone;
    }

    public void setRecipientphone(String recipientphone) {
        this.recipientphone = recipientphone;
    }

    public String getRecipientname() {
        return recipientname;
    }

    public void setRecipientname(String recipientname) {
        this.recipientname = recipientname;
    }

    public Float getShippingfee() {
        return shippingfee;
    }

    public void setShippingfee(Float shippingfee) {
        this.shippingfee = shippingfee;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(Double originalprice) {
        this.originalprice = originalprice;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getShipperphone() {
        return shipperphone;
    }

    public void setShipperphone(String shipperphone) {
        this.shipperphone = shipperphone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCityid() {
        return cityid;
    }

    public void setCityid(Long cityid) {
        this.cityid = cityid;
    }

    public Integer getHasinvoiced() {
        return hasinvoiced;
    }

    public void setHasinvoiced(Integer hasinvoiced) {
        this.hasinvoiced = hasinvoiced;
    }

    public String getInvoicetitle() {
        return invoicetitle;
    }

    public void setInvoicetitle(String invoicetitle) {
        this.invoicetitle = invoicetitle;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    public Long getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(Long deliverytime) {
        this.deliverytime = deliverytime;
    }

    public Integer getIsthirdshipping() {
        return isthirdshipping;
    }

    public void setIsthirdshipping(Integer isthirdshipping) {
        this.isthirdshipping = isthirdshipping;
    }

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDayseq() {
        return dayseq;
    }

    public void setDayseq(Integer dayseq) {
        this.dayseq = dayseq;
    }

    public Boolean getIsfavorites() {
        return isfavorites;
    }

    public void setIsfavorites(Boolean isfavorites) {
        this.isfavorites = isfavorites;
    }

    public Boolean getIspoifirstorder() {
        return ispoifirstorder;
    }

    public void setIspoifirstorder(Boolean ispoifirstorder) {
        this.ispoifirstorder = ispoifirstorder;
    }

    public Integer getDinnersnumber() {
        return dinnersnumber;
    }

    public void setDinnersnumber(Integer dinnersnumber) {
        this.dinnersnumber = dinnersnumber;
    }

    public String getLogisticscode() {
        return logisticscode;
    }

    public void setLogisticscode(String logisticscode) {
        this.logisticscode = logisticscode;
    }

    public String getPoireceivedetail() {
        return poireceivedetail;
    }

    public void setPoireceivedetail(String poireceivedetail) {
        this.poireceivedetail = poireceivedetail;
    }

    public List<OrderFoodDetailParam> getDetail() {
        return detail;
    }

    public void setDetail(List<OrderFoodDetailParam> detail) {
        this.detail = detail;
    }

    public List<OrderExtraParam> getExtras() {
        return extras;
    }

    public void setExtras(List<OrderExtraParam> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
            "orderid=" + orderid +
            ", wmorderidview=" + wmorderidview +
            ", apppoicode='" + apppoicode + '\'' +
            ", wmpoiname='" + wmpoiname + '\'' +
            ", wmpoiaddress='" + wmpoiaddress + '\'' +
            ", wmpoiphone='" + wmpoiphone + '\'' +
            ", recipientaddress='" + recipientaddress + '\'' +
            ", recipientphone='" + recipientphone + '\'' +
            ", recipientname='" + recipientname + '\'' +
            ", shippingfee=" + shippingfee +
            ", total=" + total +
            ", originalprice=" + originalprice +
            ", caution='" + caution + '\'' +
            ", shipperphone='" + shipperphone + '\'' +
            ", status=" + status +
            ", cityid=" + cityid +
            ", hasinvoiced=" + hasinvoiced +
            ", invoicetitle='" + invoicetitle + '\'' +
            ", ctime=" + ctime +
            ", utime=" + utime +
            ", deliverytime=" + deliverytime +
            ", isthirdshipping=" + isthirdshipping +
            ", paytype=" + paytype +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", dayseq=" + dayseq +
            ", isfavorites=" + isfavorites +
            ", ispoifirstorder=" + ispoifirstorder +
            ", dinnersnumber=" + dinnersnumber +
            ", logisticscode='" + logisticscode + '\'' +
            ", poireceivedetail='" + poireceivedetail + '\'' +
            ", detail=" + detail +
            ", extras=" + extras +
            '}';
    }
}

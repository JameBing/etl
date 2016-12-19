package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.PrintWriter;

/**
 * Created by yangwanbin on 2016-11-15.
 */
@Document(collection = "sync.baidu.shop")
public class Shop {
    @SerializedName("shop_id")
    private String shopId;

    @SerializedName("baidu_shop_id")
    private String baiduShopId;

    private String name;

    //供应商ID
    @SerializedName("supplier_id")
    private String supplierId;

    //商户图片
    @SerializedName("shop_logo")
    private String shopLogo;

    //商户所在省ID
    private String province;

    //商户所在市ID
    private String city;

    //商户所在区县ID
    private String county;

    //商户地址
    private String address;

    //分类
    private Categorys[] categorys;

    //商户电话,座机必须填写区号
    private String phone;

    //客服电话
    @SerializedName("service_phone")
    private String servicePhone;

    //经度;为空时自动根据address字段获取
    private String longitude;

    //纬度
    private String latitude;

    //坐标类型;bdll:百度经纬度;amap:高德经纬度
    @SerializedName("coord_type")
    private String coordType;

    //配送区域信息数组
    @SerializedName("delivery_region")
    private DeliveryRegion[] deliveryRegions;

    //营业时间数组
    @SerializedName("business_time")
    private BusinessTime[] businessTimes;

    //提前下单时间;单位:分钟;预留字段,暂不生效;
    @SerializedName("book_ahead_time")
    private String bookAheadTime;

    //是否可以提供发票;1:是;2:否;
    @SerializedName("invoice_support")
    private String invoiceSupport;

    //餐盒费;单位:分;
    @SerializedName("package_box_price")
    private String packageBoxPrice;

    //商户代码;
    @SerializedName("shop_code")
    private String shopCode;

    //业态字段;商超和水果生鲜类商户使用,影响前端商户排序;
    @SerializedName("business_form_id")
    private String businessFormId;

    @SerializedName("sys_status")
    private  Integer sysStatus;

    @SerializedName("business_stauts")
    private  Integer businessStauts;

    public Integer getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
    }

    public Integer getBusinessStauts() {
        return businessStauts;
    }

    public void setBusinessStauts(Integer businessStauts) {
        this.businessStauts = businessStauts;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setBaiduShopId(String baiduShopId){
        this.baiduShopId = baiduShopId;
    }

    public String getBaiduShopId(){
        return this.baiduShopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupplierId(String supplierId){
        this.supplierId = supplierId;
    }

    public String getSupplierId(){
        return this.supplierId;
    }

    public  void setShopLogo(String shopLogo){
        this.shopLogo = shopLogo;
    }

    public String getShopLogo(){
        return  this.shopLogo;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public String getProvince(){
        return this.province;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public  void setCounty(String county){
        this.county = county;
    }

    public String getCounty(){
        return this.county;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void setCategorys(Categorys[] categorys){
        this.categorys = categorys;
    }

    public Categorys[] getCategorys(){
        return this.categorys;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return  this.phone;
    }

    public void setServicePhone(String servicePhone){
        this.servicePhone = servicePhone;
    }

    public String getServicePhone(){
        return this.servicePhone;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLongitude(){
        return this.longitude;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLatitude(){
        return  this.latitude;
    }

    public void setCoordType(String coordType){
        this.coordType = coordType;
    }

    public String getCoordType(){
        return this.coordType;
    }

    public void setDeliveryRegions(DeliveryRegion[] deliveryRegions){
        this.deliveryRegions = deliveryRegions;
    }

    public DeliveryRegion[] getDeliveryRegions(){
        return this.deliveryRegions;
    }

    public void setBusinessTimes(BusinessTime[] businessTimes){
        this.businessTimes = businessTimes;
    }

    public BusinessTime[] getBusinessTimes(){
        return this.businessTimes;
    }

    public void setBookAheadTime(String bookAheadTime){
        this.bookAheadTime = bookAheadTime;
    }

    public String getBookAheadTime(){
        return  this.bookAheadTime;
    }

    public void setInvoiceSupport(String invoiceSupport){
        this.invoiceSupport = invoiceSupport;
    }

    public String getInvoiceSupport(){
        return this.invoiceSupport;
    }

    public void setPackageBoxPrice(String packageBoxPrice){
        this.packageBoxPrice = packageBoxPrice;
    }

    public String getPackageBoxPrice(){
        return this.packageBoxPrice;
    }

    public void setShopCode(String shopCode){
        this.shopCode = shopCode;
    }

    public String getShopCode(){
        return this.shopCode;
    }

    public void setBusinessFormId(String businessFormId){
        this.businessFormId = businessFormId;
    }

    public String getBusinessFormId(){
        return this.businessFormId;
    }
}

package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/28.
 */
public class RestaurantRequest {
    /***********更新餐厅营业信息属性*************/
    //开关店铺
    private int is_open;
    /************更新餐厅基本信息属性***********/
    //餐厅地址
    private String address_text;
    //经纬度用,隔开
    private String geo;
    //配送费
    private int agent_fee;
    //关闭描述
    private String close_description;
    //配送额外说明
    private String deliver_description;
    //餐厅简介
    private String description;
    //餐厅名字
    private String name;
    //是否接受预定
    private int is_bookable;
    //餐厅营业时间，多个时间段用逗号隔开
    private String open_time;
    //餐厅联系号码
    private String phone;
    //餐厅公告
    private String promotion_info;
    //餐厅logo
    private String logo_image_hash;
    //是否支持开发票
    private int invoice;
    //支持的最小发票金额
    private float invoice_min_amount;
    //满xx元免配送费
    private String no_agent_fee_total;
    //餐厅是否有效
    private String is_valid;
    //订单打包费
    private float packing_fee=-1;
    /************设置配送范围***********/


    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public int getAgent_fee() {
        return agent_fee;
    }

    public void setAgent_fee(int agent_fee) {
        this.agent_fee = agent_fee;
    }

    public String getClose_description() {
        return close_description;
    }

    public void setClose_description(String close_description) {
        this.close_description = close_description;
    }

    public String getDeliver_description() {
        return deliver_description;
    }

    public void setDeliver_description(String deliver_description) {
        this.deliver_description = deliver_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIs_bookable() {
        return is_bookable;
    }

    public void setIs_bookable(int is_bookable) {
        this.is_bookable = is_bookable;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPromotion_info() {
        return promotion_info;
    }

    public void setPromotion_info(String promotion_info) {
        this.promotion_info = promotion_info;
    }

    public String getLogo_image_hash() {
        return logo_image_hash;
    }

    public void setLogo_image_hash(String logo_image_hash) {
        this.logo_image_hash = logo_image_hash;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public float getInvoice_min_amount() {
        return invoice_min_amount;
    }

    public void setInvoice_min_amount(float invoice_min_amount) {
        this.invoice_min_amount = invoice_min_amount;
    }

    public String getNo_agent_fee_total() {
        return no_agent_fee_total;
    }

    public void setNo_agent_fee_total(String no_agent_fee_total) {
        this.no_agent_fee_total = no_agent_fee_total;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public float getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(float packing_fee) {
        this.packing_fee = packing_fee;
    }
}

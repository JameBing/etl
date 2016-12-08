package com.wangjunneil.schedule.entity.meituan;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author liuxin
 * @since 2016/11/17.
 * 订单实体OrderInfo
 */
@Document(collection = "sync.meituan.order")
public class OrderInfo {

    //订单ID(数据库中请用bigint(20)存储此字段)
    private long order_id;

    //订单展示ID
    private long wm_order_id_view;

    //APP方商家ID
    private String app_poi_code;

    //美团商家名称
    private String wm_poi_name;

    //美团商家地址
    private String wm_poi_address;

    //美团商家电话
    private String wm_poi_phone;

    //收件人地址
    private String recipient_address;

    //收件人电话
    private String recipient_phone;

    //收件人姓名
    private String recipient_name;

    //门店配送费
    private float shipping_fee;

    //总价
    private double total;

    //原价
    private double original_price;

    //忌口或备注
    private String caution;

    //送餐员电话
    private String shipper_phone;

    //订单状态
    private int status;

    //城市ID
    private long city_id;

    //是否开发票
    private int has_invoiced;

    //发票抬头
    private String invoiced_title;

    //创建时间
    private long ctime;

    //更新时间
    private long utime;

    //用户预计送达时间 “立即送达”时为0
    private long delivery_time;

    //是否是第三方配送平台配送（0：否；1：是）
    private int is_third_shipping;

    //实际送餐地址纬度
    private double latitude;

    //时间送餐地址经度
    private double longitude;

    //----------------------------------------- detail明细 -----------------------------------------------
    private List<DetailInfo> detail;

    //----------------------------------------- extras明细 -----------------------------------------------
    private List<ExtrasInfo> extras;

    //用户下单时间
    private long order_send_time;

    //商户收到时间
    private long order_receive_time;

    //商户确认时间
    private long order_confirm_time;

    //订单取消时间
    private long order_cancel_time;

    //订单完成时间
    private long order_completed_time;

    //配送订单状态code，若is_mt_logistics不为1则此字段为空
    private int logistics_status;

    //配送方id
    private long logistics_id;

    //配送方名称
    private String logistics_name;

    //配送单下单时间
    private long logistics_send_time;

    //配送单确认时间
    private long logistics_confirm_time;

    //配送单取消时间
    private long logistics_cancel_time;

    //骑手取单时间
    private long logistics_fetch_time;

    //配送单完成时间
    private long logistics_completed_time;

    //骑手姓名
    private String logistics_dispatcher_name;

    //骑手电话
    private String logistics_dispatcher_mobile;




    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public long getWm_order_id_view() {
        return wm_order_id_view;
    }

    public void setWm_order_id_view(long wm_order_id_view) {
        this.wm_order_id_view = wm_order_id_view;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getWm_poi_name() {
        return wm_poi_name;
    }

    public void setWm_poi_name(String wm_poi_name) {
        this.wm_poi_name = wm_poi_name;
    }

    public String getWm_poi_address() {
        return wm_poi_address;
    }

    public void setWm_poi_address(String wm_poi_address) {
        this.wm_poi_address = wm_poi_address;
    }

    public String getWm_poi_phone() {
        return wm_poi_phone;
    }

    public void setWm_poi_phone(String wm_poi_phone) {
        this.wm_poi_phone = wm_poi_phone;
    }

    public String getRecipient_address() {
        return recipient_address;
    }

    public void setRecipient_address(String recipient_address) {
        this.recipient_address = recipient_address;
    }

    public String getRecipient_phone() {
        return recipient_phone;
    }

    public void setRecipient_phone(String recipient_phone) {
        this.recipient_phone = recipient_phone;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public float getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(float shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getShipper_phone() {
        return shipper_phone;
    }

    public void setShipper_phone(String shipper_phone) {
        this.shipper_phone = shipper_phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

    public int getHas_invoiced() {
        return has_invoiced;
    }

    public void setHas_invoiced(int has_invoiced) {
        this.has_invoiced = has_invoiced;
    }

    public String getInvoiced_title() {
        return invoiced_title;
    }

    public void setInvoiced_title(String invoiced_title) {
        this.invoiced_title = invoiced_title;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public long getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(long delivery_time) {
        this.delivery_time = delivery_time;
    }

    public int getIs_third_shipping() {
        return is_third_shipping;
    }

    public void setIs_third_shipping(int is_third_shipping) {
        this.is_third_shipping = is_third_shipping;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<DetailInfo> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailInfo> detail) {
        this.detail = detail;
    }

    public List<ExtrasInfo> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasInfo> extras) {
        this.extras = extras;
    }

    public long getOrder_send_time() {
        return order_send_time;
    }

    public void setOrder_send_time(long order_send_time) {
        this.order_send_time = order_send_time;
    }

    public long getOrder_receive_time() {
        return order_receive_time;
    }

    public void setOrder_receive_time(long order_receive_time) {
        this.order_receive_time = order_receive_time;
    }

    public long getOrder_confirm_time() {
        return order_confirm_time;
    }

    public void setOrder_confirm_time(long order_confirm_time) {
        this.order_confirm_time = order_confirm_time;
    }

    public long getOrder_cancel_time() {
        return order_cancel_time;
    }

    public void setOrder_cancel_time(long order_cancel_time) {
        this.order_cancel_time = order_cancel_time;
    }

    public long getOrder_completed_time() {
        return order_completed_time;
    }

    public void setOrder_completed_time(long order_completed_time) {
        this.order_completed_time = order_completed_time;
    }

    public int getLogistics_status() {
        return logistics_status;
    }

    public void setLogistics_status(int logistics_status) {
        this.logistics_status = logistics_status;
    }

    public long getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(long logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public long getLogistics_send_time() {
        return logistics_send_time;
    }

    public void setLogistics_send_time(long logistics_send_time) {
        this.logistics_send_time = logistics_send_time;
    }

    public long getLogistics_confirm_time() {
        return logistics_confirm_time;
    }

    public void setLogistics_confirm_time(long logistics_confirm_time) {
        this.logistics_confirm_time = logistics_confirm_time;
    }

    public long getLogistics_cancel_time() {
        return logistics_cancel_time;
    }

    public void setLogistics_cancel_time(long logistics_cancel_time) {
        this.logistics_cancel_time = logistics_cancel_time;
    }

    public long getLogistics_fetch_time() {
        return logistics_fetch_time;
    }

    public void setLogistics_fetch_time(long logistics_fetch_time) {
        this.logistics_fetch_time = logistics_fetch_time;
    }

    public long getLogistics_completed_time() {
        return logistics_completed_time;
    }

    public void setLogistics_completed_time(long logistics_completed_time) {
        this.logistics_completed_time = logistics_completed_time;
    }

    public String getLogistics_dispatcher_name() {
        return logistics_dispatcher_name;
    }

    public void setLogistics_dispatcher_name(String logistics_dispatcher_name) {
        this.logistics_dispatcher_name = logistics_dispatcher_name;
    }

    public String getLogistics_dispatcher_mobile() {
        return logistics_dispatcher_mobile;
    }

    public void setLogistics_dispatcher_mobile(String logistics_dispatcher_mobile) {
        this.logistics_dispatcher_mobile = logistics_dispatcher_mobile;
    }
}

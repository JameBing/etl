package com.wangjunneil.schedule.entity.meituan;

import com.sankuai.meituan.waimai.opensdk.vo.OrderExtraParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderFoodDetailParam;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Administrator on 2016-12-7.
 */
@Document(collection = "sync.meituan.order")
public class OrderInfo {
    private String app_order_code;
    private String app_poi_code;
    private Double avg_send_time;
    private String caution;
    private Long city_id;
    private Long ctime;
    private Integer day_seq;
    private Long delivery_time;
    private List<OrderFoodDetailParam> detail;
    private Integer dinners_number;
    private Integer expect_deliver_time;
    private List<OrderExtraParam> extras;
    private Integer has_invoiced;
    private String invoice_title;
    private Boolean is_favorites;
    private Boolean is_poi_first_order;
    private Integer is_pre;
    private Integer is_third_shipping;
    private Double latitude;
    private Integer logistics_cancel_time;
    private String logistics_code;
    private Integer logistics_completed_time;
    private Integer logistics_confirm_time;
    private String logistics_dispatcher_mobile;
    private String logistics_dispatcher_name;
    private Integer logistics_fetch_time;
    private Integer logistics_id;
    private String logistics_name;
    private Integer logistics_send_time;
    private Integer logistics_status;
    private Double longitude;
    private Long order_cancel_time;
    private Long order_completed_time;
    private Long order_confirm_time;
    private Long order_id;
    private Long order_receive_time;
    private Long order_send_time;
    private Double original_price;
    private Integer pay_done_time;
    private Integer pay_status;
    private Integer pay_type;
    private Integer paying_time;
    private String poi_receive_detail;
    private String recipient_address;
    private String recipient_name;
    private String recipient_phone;
    private Integer refund_apply_time;
    private Integer refund_complete_time;
    private Integer refund_confirm_time;
    private Integer refund_reject_time;
    private String remark;
    private String result;
    private String shipper_phone;
    private Float shipping_fee;
    private Integer shipping_type;
    private Long source_id;
    private Integer status;
    private Double total;
    private Integer unpaid_time;
    private Long utime;
    private Long wm_order_id_view;
    private String wm_poi_address;
    private Long wm_poi_id;
    private String wm_poi_name;
    private String wm_poi_phone;

    public String getApp_order_code() {
        return app_order_code;
    }

    public void setApp_order_code(String app_order_code) {
        this.app_order_code = app_order_code;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public Double getAvg_send_time() {
        return avg_send_time;
    }

    public void setAvg_send_time(Double avg_send_time) {
        this.avg_send_time = avg_send_time;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Integer getDay_seq() {
        return day_seq;
    }

    public void setDay_seq(Integer day_seq) {
        this.day_seq = day_seq;
    }

    public Long getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Long delivery_time) {
        this.delivery_time = delivery_time;
    }

    public List<OrderFoodDetailParam> getDetail() {
        return detail;
    }

    public void setDetail(List<OrderFoodDetailParam> detail) {
        this.detail = detail;
    }

    public Integer getDinners_number() {
        return dinners_number;
    }

    public void setDinners_number(Integer dinners_number) {
        this.dinners_number = dinners_number;
    }

    public Integer getExpect_deliver_time() {
        return expect_deliver_time;
    }

    public void setExpect_deliver_time(Integer expect_deliver_time) {
        this.expect_deliver_time = expect_deliver_time;
    }

    public List<OrderExtraParam> getExtras() {
        return extras;
    }

    public void setExtras(List<OrderExtraParam> extras) {
        this.extras = extras;
    }

    public Integer getHas_invoiced() {
        return has_invoiced;
    }

    public void setHas_invoiced(Integer has_invoiced) {
        this.has_invoiced = has_invoiced;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public Boolean getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(Boolean is_favorites) {
        this.is_favorites = is_favorites;
    }

    public Boolean getIs_poi_first_order() {
        return is_poi_first_order;
    }

    public void setIs_poi_first_order(Boolean is_poi_first_order) {
        this.is_poi_first_order = is_poi_first_order;
    }

    public Integer getIs_pre() {
        return is_pre;
    }

    public void setIs_pre(Integer is_pre) {
        this.is_pre = is_pre;
    }

    public Integer getIs_third_shipping() {
        return is_third_shipping;
    }

    public void setIs_third_shipping(Integer is_third_shipping) {
        this.is_third_shipping = is_third_shipping;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getLogistics_cancel_time() {
        return logistics_cancel_time;
    }

    public void setLogistics_cancel_time(Integer logistics_cancel_time) {
        this.logistics_cancel_time = logistics_cancel_time;
    }

    public String getLogistics_code() {
        return logistics_code;
    }

    public void setLogistics_code(String logistics_code) {
        this.logistics_code = logistics_code;
    }

    public Integer getLogistics_completed_time() {
        return logistics_completed_time;
    }

    public void setLogistics_completed_time(Integer logistics_completed_time) {
        this.logistics_completed_time = logistics_completed_time;
    }

    public Integer getLogistics_confirm_time() {
        return logistics_confirm_time;
    }

    public void setLogistics_confirm_time(Integer logistics_confirm_time) {
        this.logistics_confirm_time = logistics_confirm_time;
    }

    public String getLogistics_dispatcher_mobile() {
        return logistics_dispatcher_mobile;
    }

    public void setLogistics_dispatcher_mobile(String logistics_dispatcher_mobile) {
        this.logistics_dispatcher_mobile = logistics_dispatcher_mobile;
    }

    public String getLogistics_dispatcher_name() {
        return logistics_dispatcher_name;
    }

    public void setLogistics_dispatcher_name(String logistics_dispatcher_name) {
        this.logistics_dispatcher_name = logistics_dispatcher_name;
    }

    public Integer getLogistics_fetch_time() {
        return logistics_fetch_time;
    }

    public void setLogistics_fetch_time(Integer logistics_fetch_time) {
        this.logistics_fetch_time = logistics_fetch_time;
    }

    public Integer getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(Integer logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public Integer getLogistics_send_time() {
        return logistics_send_time;
    }

    public void setLogistics_send_time(Integer logistics_send_time) {
        this.logistics_send_time = logistics_send_time;
    }

    public Integer getLogistics_status() {
        return logistics_status;
    }

    public void setLogistics_status(Integer logistics_status) {
        this.logistics_status = logistics_status;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getOrder_cancel_time() {
        return order_cancel_time;
    }

    public void setOrder_cancel_time(Long order_cancel_time) {
        this.order_cancel_time = order_cancel_time;
    }

    public Long getOrder_completed_time() {
        return order_completed_time;
    }

    public void setOrder_completed_time(Long order_completed_time) {
        this.order_completed_time = order_completed_time;
    }

    public Long getOrder_confirm_time() {
        return order_confirm_time;
    }

    public void setOrder_confirm_time(Long order_confirm_time) {
        this.order_confirm_time = order_confirm_time;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getOrder_receive_time() {
        return order_receive_time;
    }

    public void setOrder_receive_time(Long order_receive_time) {
        this.order_receive_time = order_receive_time;
    }

    public Long getOrder_send_time() {
        return order_send_time;
    }

    public void setOrder_send_time(Long order_send_time) {
        this.order_send_time = order_send_time;
    }

    public Double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
    }

    public Integer getPay_done_time() {
        return pay_done_time;
    }

    public void setPay_done_time(Integer pay_done_time) {
        this.pay_done_time = pay_done_time;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public Integer getPaying_time() {
        return paying_time;
    }

    public void setPaying_time(Integer paying_time) {
        this.paying_time = paying_time;
    }

    public String getPoi_receive_detail() {
        return poi_receive_detail;
    }

    public void setPoi_receive_detail(String poi_receive_detail) {
        this.poi_receive_detail = poi_receive_detail;
    }

    public String getRecipient_address() {
        return recipient_address;
    }

    public void setRecipient_address(String recipient_address) {
        this.recipient_address = recipient_address;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_phone() {
        return recipient_phone;
    }

    public void setRecipient_phone(String recipient_phone) {
        this.recipient_phone = recipient_phone;
    }

    public Integer getRefund_apply_time() {
        return refund_apply_time;
    }

    public void setRefund_apply_time(Integer refund_apply_time) {
        this.refund_apply_time = refund_apply_time;
    }

    public Integer getRefund_complete_time() {
        return refund_complete_time;
    }

    public void setRefund_complete_time(Integer refund_complete_time) {
        this.refund_complete_time = refund_complete_time;
    }

    public Integer getRefund_confirm_time() {
        return refund_confirm_time;
    }

    public void setRefund_confirm_time(Integer refund_confirm_time) {
        this.refund_confirm_time = refund_confirm_time;
    }

    public Integer getRefund_reject_time() {
        return refund_reject_time;
    }

    public void setRefund_reject_time(Integer refund_reject_time) {
        this.refund_reject_time = refund_reject_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getShipper_phone() {
        return shipper_phone;
    }

    public void setShipper_phone(String shipper_phone) {
        this.shipper_phone = shipper_phone;
    }

    public Float getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(Float shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public Integer getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(Integer shipping_type) {
        this.shipping_type = shipping_type;
    }

    public Long getSource_id() {
        return source_id;
    }

    public void setSource_id(Long source_id) {
        this.source_id = source_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getUnpaid_time() {
        return unpaid_time;
    }

    public void setUnpaid_time(Integer unpaid_time) {
        this.unpaid_time = unpaid_time;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    public Long getWm_order_id_view() {
        return wm_order_id_view;
    }

    public void setWm_order_id_view(Long wm_order_id_view) {
        this.wm_order_id_view = wm_order_id_view;
    }

    public String getWm_poi_address() {
        return wm_poi_address;
    }

    public void setWm_poi_address(String wm_poi_address) {
        this.wm_poi_address = wm_poi_address;
    }

    public Long getWm_poi_id() {
        return wm_poi_id;
    }

    public void setWm_poi_id(Long wm_poi_id) {
        this.wm_poi_id = wm_poi_id;
    }

    public String getWm_poi_name() {
        return wm_poi_name;
    }

    public void setWm_poi_name(String wm_poi_name) {
        this.wm_poi_name = wm_poi_name;
    }

    public String getWm_poi_phone() {
        return wm_poi_phone;
    }

    public void setWm_poi_phone(String wm_poi_phone) {
        this.wm_poi_phone = wm_poi_phone;
    }

    public String toString() {
        return "OrderDetailParam [app_order_code=\'" + this.app_order_code + '\'' + ", app_poi_code=\'" + this.app_poi_code + '\'' + ", avg_send_time=" + this.avg_send_time + ", caution=\'" + this.caution + '\'' + ", city_id=" + this.city_id + ", ctime=" + this.ctime + ", day_seq=" + this.day_seq + ", delivery_time=" + this.delivery_time + ", detail=" + this.detail + ", dinners_number=" + this.dinners_number + ", expect_deliver_time=" + this.expect_deliver_time + ", extras=" + this.extras + ", has_invoiced=" + this.has_invoiced + ", invoice_title=\'" + this.invoice_title + '\'' + ", is_favorites=" + this.is_favorites + ", is_poi_first_order=" + this.is_poi_first_order + ", is_pre=" + this.is_pre + ", is_third_shipping=" + this.is_third_shipping + ", latitude=" + this.latitude + ", logistics_cancel_time=" + this.logistics_cancel_time + ", logistics_code=\'" + this.logistics_code + '\'' + ", logistics_completed_time=" + this.logistics_completed_time + ", logistics_confirm_time=" + this.logistics_confirm_time + ", logistics_dispatcher_mobile=\'" + this.logistics_dispatcher_mobile + '\'' + ", logistics_dispatcher_name=\'" + this.logistics_dispatcher_name + '\'' + ", logistics_fetch_time=" + this.logistics_fetch_time + ", logistics_id=" + this.logistics_id + ", logistics_name=\'" + this.logistics_name + '\'' + ", logistics_send_time=" + this.logistics_send_time + ", logistics_status=" + this.logistics_status + ", longitude=" + this.longitude + ", order_cancel_time=" + this.order_cancel_time + ", order_completed_time=" + this.order_completed_time + ", order_confirm_time=" + this.order_confirm_time + ", order_id=" + this.order_id + ", order_receive_time=" + this.order_receive_time + ", order_send_time=" + this.order_send_time + ", original_price=" + this.original_price + ", pay_done_time=" + this.pay_done_time + ", pay_status=" + this.pay_status + ", pay_type=" + this.pay_type + ", paying_time=" + this.paying_time + ", poi_receive_detail=\'" + this.poi_receive_detail + '\'' + ", recipient_address=\'" + this.recipient_address + '\'' + ", recipient_name=\'" + this.recipient_name + '\'' + ", recipient_phone=\'" + this.recipient_phone + '\'' + ", refund_apply_time=" + this.refund_apply_time + ", refund_complete_time=" + this.refund_complete_time + ", refund_confirm_time=" + this.refund_confirm_time + ", refund_reject_time=" + this.refund_reject_time + ", remark=\'" + this.remark + '\'' + ", result=\'" + this.result + '\'' + ", shipper_phone=\'" + this.shipper_phone + '\'' + ", shipping_fee=" + this.shipping_fee + ", shipping_type=" + this.shipping_type + ", source_id=" + this.source_id + ", status=" + this.status + ", total=" + this.total + ", unpaid_time=" + this.unpaid_time + ", utime=" + this.utime + ", wm_order_id_view=" + this.wm_order_id_view + ", wm_poi_address=\'" + this.wm_poi_address + '\'' + ", wm_poi_id=" + this.wm_poi_id + ", wm_poi_name=\'" + this.wm_poi_name + '\'' + ", wm_poi_phone=\'" + this.wm_poi_phone + '\'' + ']';
    }
}

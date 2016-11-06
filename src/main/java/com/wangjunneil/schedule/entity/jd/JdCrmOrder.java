package com.wangjunneil.schedule.entity.jd;

import com.jd.open.api.sdk.domain.order.CouponDetail;
import com.jd.open.api.sdk.domain.order.ItemInfo;
import com.jd.open.api.sdk.domain.order.UserInfo;
import com.jd.open.api.sdk.domain.order.VatInvoiceInfo;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 8/2/16.
 */
@Document(collection = "sync.jd.order")
public class JdCrmOrder {
    private String order_id;            // 订单id
    private String order_source;        // 订单来源
    private String customs;             // 保税区信息
    private String customs_model;       // 保税模型：直邮，保税集货，保税备货
    private String vender_id;           // 商家id
    private String pay_type;            // 支付方式（1货到付款, 2邮局汇款, 3自提, 4在线支付, 5公司转账, 6银行卡转账）
    private String order_total_price;   // 订单总金额
    private String order_seller_price;  // 订单货款金额（订单总金额-商家优惠金额）
    private String order_payment;       // 用户应付金额
    private String freight_price;       // 商品的运费
    private String seller_discount;     // 商家优惠金额
    private String order_state;         // 订单状态（英文）
    private String order_state_remark;  // 订单状态说明（中文）
    private String delivery_type;       // 送货（日期）类型（1-只工作日送货(双休日、假日不用送);2-只双休日、假日送货(工作日不用送);3-工作日、双休日与假日均可送货;其他值-返回“任意时间”）
    private String invoice_info;        // 发票信息 “invoice_info: 不需要开具发票”下无需开具发票；其它返回值请正常开具发票
    private String order_remark;        // 买家下单时订单备注
    private Date order_start_time;    // 下单时间
    private String order_end_time;      // 结单时间 如返回信息为“0001-01-01 00:00:00”和“1970-01-01 00:00:00”，可认为此订单为未完成状态
    private String modified;            // 订单更新时间
    private String vender_remark;       // 商家订单备注（不大于500字符） 可选字段，需要在输入参数optional_fields中写入才能返回
    private String balance_used;        // 余额支付金额 可选字段，需要在输入参数optional_fields中写入才能返回
    private String payment_confirm_time;    // 付款确认时间 如果没有付款时间 默认返回0001-01-01 00:00:00 可选字段，需要在输入参数optional_fields中写入才能返回
    private String waybill;             // 运单号(当厂家自送时运单号可为空，不同物流公司的运单号用|分隔，如果同一物流公司有多个运单号，则用英文逗号分隔) 可选字段，需要在输入参数optional_fields中写入才能返回
    private String logistics_id;        // 物流公司ID 可选字段，需要在输入参数optional_fields中写入才能返回
    private String parent_order_id;     // 父订单号 可选字段，需要在输入参数optional_fields中写入才能返回
    private String pin;                 // 买家的账号信息 可选字段，需要在输入参数optional_fields中写入才能返回
    private String return_order;        // 售后订单标记 0:不是换货订单 1返修发货,直接赔偿,客服补件 2售后调货 可选字段，需要在输入参数optional_fields中写入才能返回

    private UserInfo consignee_info;                // 收货人基本信息
    private VatInvoiceInfo vat_invoice_info;        // 增值税发票 可选字段，需要在输入参数optional_fields中写入才能返回
    private List<ItemInfo> item_info_list;          //
    private List<CouponDetail> coupon_detail_list;  // 优惠详细信息

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_source() {
        return order_source;
    }

    public void setOrder_source(String order_source) {
        this.order_source = order_source;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public String getCustoms_model() {
        return customs_model;
    }

    public void setCustoms_model(String customs_model) {
        this.customs_model = customs_model;
    }

    public String getVender_id() {
        return vender_id;
    }

    public void setVender_id(String vender_id) {
        this.vender_id = vender_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(String order_total_price) {
        this.order_total_price = order_total_price;
    }

    public String getOrder_seller_price() {
        return order_seller_price;
    }

    public void setOrder_seller_price(String order_seller_price) {
        this.order_seller_price = order_seller_price;
    }

    public String getOrder_payment() {
        return order_payment;
    }

    public void setOrder_payment(String order_payment) {
        this.order_payment = order_payment;
    }

    public String getFreight_price() {
        return freight_price;
    }

    public void setFreight_price(String freight_price) {
        this.freight_price = freight_price;
    }

    public String getSeller_discount() {
        return seller_discount;
    }

    public void setSeller_discount(String seller_discount) {
        this.seller_discount = seller_discount;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_state_remark() {
        return order_state_remark;
    }

    public void setOrder_state_remark(String order_state_remark) {
        this.order_state_remark = order_state_remark;
    }

    public String getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getInvoice_info() {
        return invoice_info;
    }

    public void setInvoice_info(String invoice_info) {
        this.invoice_info = invoice_info;
    }

    public String getOrder_remark() {
        return order_remark;
    }

    public void setOrder_remark(String order_remark) {
        this.order_remark = order_remark;
    }

    public Date getOrder_start_time() {
        return order_start_time;
    }

    public void setOrder_start_time(Date order_start_time) {
        this.order_start_time = DateTimeUtil.addHour(order_start_time, 8);
    }

    public String getOrder_end_time() {
        return order_end_time;
    }

    public void setOrder_end_time(String order_end_time) {
        this.order_end_time = order_end_time;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getVender_remark() {
        return vender_remark;
    }

    public void setVender_remark(String vender_remark) {
        this.vender_remark = vender_remark;
    }

    public String getBalance_used() {
        return balance_used;
    }

    public void setBalance_used(String balance_used) {
        this.balance_used = balance_used;
    }

    public String getPayment_confirm_time() {
        return payment_confirm_time;
    }

    public void setPayment_confirm_time(String payment_confirm_time) {
        this.payment_confirm_time = payment_confirm_time;
    }

    public String getWaybill() {
        return waybill;
    }

    public void setWaybill(String waybill) {
        this.waybill = waybill;
    }

    public String getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getParent_order_id() {
        return parent_order_id;
    }

    public void setParent_order_id(String parent_order_id) {
        this.parent_order_id = parent_order_id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getReturn_order() {
        return return_order;
    }

    public void setReturn_order(String return_order) {
        this.return_order = return_order;
    }

    public UserInfo getConsignee_info() {
        return consignee_info;
    }

    public void setConsignee_info(UserInfo consignee_info) {
        this.consignee_info = consignee_info;
    }

    public VatInvoiceInfo getVat_invoice_info() {
        return vat_invoice_info;
    }

    public void setVat_invoice_info(VatInvoiceInfo vat_invoice_info) {
        this.vat_invoice_info = vat_invoice_info;
    }

    public List<ItemInfo> getItem_info_list() {
        return item_info_list;
    }

    public void setItem_info_list(List<ItemInfo> item_info_list) {
        this.item_info_list = item_info_list;
    }

    public List<CouponDetail> getCoupon_detail_list() {
        return coupon_detail_list;
    }

    public void setCoupon_detail_list(List<CouponDetail> coupon_detail_list) {
        this.coupon_detail_list = coupon_detail_list;
    }
}

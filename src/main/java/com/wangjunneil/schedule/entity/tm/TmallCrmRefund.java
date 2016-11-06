package com.wangjunneil.schedule.entity.tm;

import com.taobao.api.domain.RefundRemindTimeout;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by wangjun on 9/6/16.
 */
@Document(collection = "sync.tmall.refund")
public class TmallCrmRefund {
    private long  tid;          // 淘宝交易单号
    private long  oid;          // 子订单号。如果是单笔交易oid会等于tid
    private long  refund_id;    // 退款单号
    private long  cs_status;    // 不需客服介入1;需要客服介入2;客服已经介入3;客服初审完成 4;客服主管复审失败5;客服处理完成6;
    private long  advance_status;   // 退款先行垫付默认的未申请状态 0;退款先行垫付申请中 1;退款先行垫付，垫付完成 2;退款先行垫付，卖家拒绝收货 3;退款先行垫付，垫付关闭 4;退款先行垫付，垫付分账成功 5;
    private String shipping_type;   // 物流方式.可选值:free(卖家包邮),post(平邮),express(快递),ems(EMS).
    private String split_taobao_fee;    // 分账给淘宝的钱
    private String split_seller_fee;    // 分账给卖家的钱
    private String alipay_no;       // 支付宝交易号
    private String total_fee;       // 交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
    private String buyer_nick;      // 买家昵称
    private String seller_nick;     // 卖家昵称
    private Date created;           // 退款申请时间。格式:yyyy-MM-dd HH:mm:ss
    private Date modified;          // 更新时间。格式:yyyy-MM-dd HH:mm:ss
    private String order_status;    // 退款对应的订单交易状态。可选值TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款) WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功) TRADE_CLOSED(交易关闭) TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭) ALL_WAIT_PAY(包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY) ALL_CLOSED(包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO) 取自"http://open.taobao.com/dev/index.php/%E4%BA%A4%E6%98%93%E7%8A%B6%E6%80%81"
    private String status;          //退款状态。可选值WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款) CLOSED(退款关闭) SUCCESS(退款成功)
    private String good_status;     //货物状态。可选值BUYER_NOT_RECEIVED (买家未收到货) BUYER_RECEIVED (买家已收到货) BUYER_RETURNED_GOODS (买家已退货)
    private Boolean has_good_return;  //买家是否需要退货。可选值:true(是),false(否)
    private String refund_fee;      //退还金额(退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分
    private String payment;        //支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分
    private String reason;         //退款原因
    private String desc;           //退款说明
    private String title;          //商品标题
    private String price;          //商品价格。精确到2位小数;单位:元。如:200.07，表示:200元7分
    private Number num;            //商品购买数量
    private Date good_return_time;  //退货时间。格式:yyyy-MM-dd HH:mm:ss
    private String company_name;   //物流公司名称
    private String sid;            //退货运单号
    private String address;       //卖家收货地址
    private RefundRemindTimeout refund_remind_timeout;  //退款超时结构RefundRemindTimeout
    private Number num_iid;       //申请退款的商品数字编号
    private String refund_phase; //退款阶段，可选值：onsale/aftersale
    private Number refund_version;  //退款版本号（时间戳）

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public long getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(long refund_id) {
        this.refund_id = refund_id;
    }

    public long getCs_status() {
        return cs_status;
    }

    public void setCs_status(long cs_status) {
        this.cs_status = cs_status;
    }

    public long getAdvance_status() {
        return advance_status;
    }

    public void setAdvance_status(long advance_status) {
        this.advance_status = advance_status;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getSplit_taobao_fee() {
        return split_taobao_fee;
    }

    public void setSplit_taobao_fee(String split_taobao_fee) {
        this.split_taobao_fee = split_taobao_fee;
    }

    public String getSplit_seller_fee() {
        return split_seller_fee;
    }

    public void setSplit_seller_fee(String split_seller_fee) {
        this.split_seller_fee = split_seller_fee;
    }

    public String getAlipay_no() {
        return alipay_no;
    }

    public void setAlipay_no(String alipay_no) {
        this.alipay_no = alipay_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    public String getSeller_nick() {
        return seller_nick;
    }

    public void setSeller_nick(String seller_nick) {
        this.seller_nick = seller_nick;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGood_status() {
        return good_status;
    }

    public void setGood_status(String good_status) {
        this.good_status = good_status;
    }

    public Boolean getHas_good_return() {
        return has_good_return;
    }

    public void setHas_good_return(Boolean has_good_return) {
        this.has_good_return = has_good_return;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Number getNum() {
        return num;
    }

    public void setNum(Number num) {
        this.num = num;
    }

    public Date getGood_return_time() {
        return good_return_time;
    }

    public void setGood_return_time(Date good_return_time) {
        this.good_return_time = good_return_time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RefundRemindTimeout getRefund_remind_timeout() {
        return refund_remind_timeout;
    }

    public void setRefund_remind_timeout(RefundRemindTimeout refund_remind_timeout) {
        this.refund_remind_timeout = refund_remind_timeout;
    }

    public Number getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(Number num_iid) {
        this.num_iid = num_iid;
    }

    public String getRefund_phase() {
        return refund_phase;
    }

    public void setRefund_phase(String refund_phase) {
        this.refund_phase = refund_phase;
    }

    public Number getRefund_version() {
        return refund_version;
    }

    public void setRefund_version(Number refund_version) {
        this.refund_version = refund_version;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public String getOperation_contraint() {
        return operation_contraint;
    }

    public void setOperation_contraint(String operation_contraint) {
        this.operation_contraint = operation_contraint;
    }

    private String sku;           //商品SKU信息
    private String attribute;    //退款扩展属性
    private String outer_id;     //商品外部商家编码
    private String operation_contraint;  //    退款约束，可选值：cannot_refuse（不允许操作），refund_onweb（需要到网页版操作）




}

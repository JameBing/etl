package com.wangjunneil.schedule.entity.z8;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by xuzhicheng on 2016/9/19.
 */
@Document(collection = "sync.z8.order")
public class Z8CrmOrder {
    public Long id;   //订单号
    public String url;   //链接
    public String seller_id;    //卖家ID
    public String seller_nickname;   //卖家昵称
    public String order_price;  //订单总金额
    public String goods_price;  //商品总金额
    public String discount_price;   //店铺优惠金额
    public String postage;  //邮费
    public String oos;     //是否超卖, 0-正常 1-超卖
    public String status;  //订单状态, 1-待付款 2-等待发货 3-已发货(待确认收货) 5-交易成功 7-交易关闭
    public String deliver_time_limit;     //发货时长,1天,2天,3天,7天
    public String seller_comment;   //卖家备注
    public String buyer_comment;   //买家留言,买家备注
    public Date created_at;     //拍下时间,下单时间
    public Date close_time;     //订单关闭时间（原因:买家取消订单/超过未支付/因为售后完成）
    public String close_reason;    //订单关闭原因 1-买家取消订单 2-超时未支付 3-买家申请退款 4-其他
    public Date updated_at;     //订单更新时间
    public Date express_time;   //发货时间
    public Date pay_time;       //付款时间
    public String express_no;  //运单号
    public String express_company;   //快递公司
    public String nickname;     //买家昵称
    public int seller_comment_type;     //卖家备注类型, 0 无备注,1-红色,2-黄色,3-绿色,4-蓝色,5-紫色
    public Invoice_info invoice;        //发票信息
    public Address_info address;        //地址信息
    public List<Products_info> products;        //商品信息
//    public String buyer_pid;        //买家身份证号（仅海淘订单使用）
//    public String  out_request_no;      //报关流水号（仅海淘订单使用）


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_nickname() {
        return seller_nickname;
    }

    public void setSeller_nickname(String seller_nickname) {
        this.seller_nickname = seller_nickname;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getOos() {
        return oos;
    }

    public void setOos(String oos) {
        this.oos = oos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliver_time_limit() {
        return deliver_time_limit;
    }

    public void setDeliver_time_limit(String deliver_time_limit) {
        this.deliver_time_limit = deliver_time_limit;
    }

    public String getSeller_comment() {
        return seller_comment;
    }

    public void setSeller_comment(String seller_comment) {
        this.seller_comment = seller_comment;
    }

    public String getBuyer_comment() {
        return buyer_comment;
    }

    public void setBuyer_comment(String buyer_comment) {
        this.buyer_comment = buyer_comment;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getClose_time() {
        return close_time;
    }

    public void setClose_time(Date close_time) {
        this.close_time = close_time;
    }

    public String getClose_reason() {
        return close_reason;
    }

    public void setClose_reason(String close_reason) {
        this.close_reason = close_reason;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getExpress_time() {
        return express_time;
    }

    public void setExpress_time(Date express_time) {
        this.express_time = express_time;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSeller_comment_type() {
        return seller_comment_type;
    }

    public void setSeller_comment_type(int seller_comment_type) {
        this.seller_comment_type = seller_comment_type;
    }

    public Invoice_info getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice_info invoice) {
        this.invoice = invoice;
    }

    public Address_info getAddress() {
        return address;
    }

    public void setAddress(Address_info address) {
        this.address = address;
    }

    public List<Products_info> getProducts() {
        return products;
    }

    public void setProducts(List<Products_info> products) {
        this.products = products;
    }
}

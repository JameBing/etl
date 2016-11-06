//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wangjunneil.schedule.entity.tm;

import com.taobao.api.domain.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "sync.tmall.order")
public class TmallCrmOrder {
    private Long tid;//交易编号 (父订单的交易编号)【***】
    private String payment;//实付金额【***】
    private String post_fee;//邮费【***】
    private String status;//交易状态 【***】
    private String buyer_message;
    private Boolean has_buyer_message;//判断订单是否有买家留言，有买家留言返回true，否则返回false【***】
    private String receiver_name;//收货人的姓名【***】
    private String receiver_country;//收货人国籍 【***】
    private String receiver_state;//收货人的所在省份【***】
    private String receiver_city;//收货人的所在城市 注：因为国家对于城市和地区的划分的有：省直辖市和省直辖县级行政区（区级别的）划分的，淘宝这边根据这个差异保存在不同字段里面比如：广东广州：广州属于一个直辖市是放在的receiver_city的字段里面；而河南济源：济源属于省直辖县级行政区划分，是区级别的，放在了receiver_district里面 建议：程序依赖于城市字段做物流等判断的操作，最好加一个判断逻辑：如果返回值里面只有receiver_district参数，该参数作为城市
    private String receiver_district;//收货人的所在地区 注：因为国家对于城市和地区的划分的有：省直辖市和省直辖县级行政区（区级别的）划分的，淘宝这边根据这个差异保存在不同字段里面比如：广东广州：广州属于一个直辖市是放在的receiver_city的字段里面；而河南济源：济源属于省直辖县级行政区划分，是区级别的，放在了receiver_district里面 建议：程序依赖于城市字段做物流等判断的操作，最好加一个判断逻辑：如果返回值里面只有receiver_district参数，该参数作为城市
    private String receiver_town;//收货人街道地址 【***】
    private String receiver_address;//收货人的详细地址【***】
    private String receiver_mobile;//收货人的手机号码【***】
    private String receiver_phone;//收货人的电话号码 【***】
    private String receiver_zip;//收货人的邮编
    private String seller_nick;//卖家昵称
    private String pic_path;//商品图片绝对途径
    private Boolean seller_rate;//卖家是否已评价
    private Date consign_time;//卖家发货时间
    private String received_payment;//卖家实际收到的支付宝打款金额
    private String order_tax_fee;//天猫国际官网直供主订单关税税费
    private String shop_pick;//门店自提，总店发货，分店取货的门店自提订单标识
    private Long num;//商品购买数量。取值范围：大于零的整数,对于一个trade对应多个order的时候（一笔主订单，对应多笔子订单），num=0，num是一个跟商品关联的属性，一笔订单对应多比子订单的时候，主订单上的num无意义。
    private Long num_iid;//商品数字编号
    private String title;//交易标题，以店铺名作为此标题的值。注:taobao.trades.get接口返回的Trade中的title是商品名称
    private String type;//交易类型列表，同时查询多种交易类型可用逗号分隔。
    private String price;//商品价格
    private String discount_fee;//建议使用trade.promotion_details查询系统优惠系统优惠金额（如打折，VIP，满就送等）
    private String total_fee;//商品金额（商品价格乘以数量的总金额）
    private Date created;//交易创建时间
    private Date pay_time;//付款时间
    private Date modified;//交易修改时间
    private Date end_time;//交易结束时间
    private Long seller_flag;//卖家备注旗帜
    private String buyer_nick;//买家昵称
    private String credit_card_fee;//使用信用卡支付金额数
    private String step_trade_status;//分阶段付款的订单状态
    private String step_paid_fee;//分阶段付款的已付金额
    private String mark_desc;//订单出现异常问题的时候，给予用户的描述,没有异常的时候，此值为空
    private String shipping_type;//创建交易时的物流方式
    private String adjust_fee;//卖家手工调整金额
    private String trade_from;//交易内部来源
    private Boolean buyer_rate;//买家是否已评价
    private String rx_audit_status;//处方药未审核状态
    private Boolean post_gate_declare;//邮关订单
    private Boolean cross_bonded_declare;//跨境订单

    private List<Order> orders;//订单明细
    private List<PromotionDetail> promotion_details;
    private List<ServiceOrder> service_orders;
    private List<LogisticsTag> service_tags;
    private long refund_id;//退款单ID


    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPost_fee() {
        return post_fee;
    }

    public void setPost_fee(String post_fee) {
        this.post_fee = post_fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public Boolean getHas_buyer_message() {
        return has_buyer_message;
    }

    public void setHas_buyer_message(Boolean has_buyer_message) {
        this.has_buyer_message = has_buyer_message;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_country() {
        return receiver_country;
    }

    public void setReceiver_country(String receiver_country) {
        this.receiver_country = receiver_country;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public void setReceiver_state(String receiver_state) {
        this.receiver_state = receiver_state;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public void setReceiver_city(String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public String getReceiver_district() {
        return receiver_district;
    }

    public void setReceiver_district(String receiver_district) {
        this.receiver_district = receiver_district;
    }

    public String getReceiver_town() {
        return receiver_town;
    }

    public void setReceiver_town(String receiver_town) {
        this.receiver_town = receiver_town;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public void setReceiver_zip(String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public String getSeller_nick() {
        return seller_nick;
    }

    public void setSeller_nick(String seller_nick) {
        this.seller_nick = seller_nick;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public Boolean getSeller_rate() {
        return seller_rate;
    }

    public void setSeller_rate(Boolean seller_rate) {
        this.seller_rate = seller_rate;
    }

    public Date getConsign_time() {
        return consign_time;
    }

    public void setConsign_time(Date consign_time) {
        this.consign_time = consign_time;
    }

    public String getReceived_payment() {
        return received_payment;
    }

    public void setReceived_payment(String received_payment) {
        this.received_payment = received_payment;
    }

    public String getOrder_tax_fee() {
        return order_tax_fee;
    }

    public void setOrder_tax_fee(String order_tax_fee) {
        this.order_tax_fee = order_tax_fee;
    }

    public String getShop_pick() {
        return shop_pick;
    }

    public void setShop_pick(String shop_pick) {
        this.shop_pick = shop_pick;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(Long num_iid) {
        this.num_iid = num_iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount_fee() {
        return discount_fee;
    }

    public void setDiscount_fee(String discount_fee) {
        this.discount_fee = discount_fee;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Long getSeller_flag() {
        return seller_flag;
    }

    public void setSeller_flag(Long seller_flag) {
        this.seller_flag = seller_flag;
    }

    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    public String getCredit_card_fee() {
        return credit_card_fee;
    }

    public void setCredit_card_fee(String credit_card_fee) {
        this.credit_card_fee = credit_card_fee;
    }

    public String getStep_trade_status() {
        return step_trade_status;
    }

    public void setStep_trade_status(String step_trade_status) {
        this.step_trade_status = step_trade_status;
    }

    public String getStep_paid_fee() {
        return step_paid_fee;
    }

    public void setStep_paid_fee(String step_paid_fee) {
        this.step_paid_fee = step_paid_fee;
    }

    public String getMark_desc() {
        return mark_desc;
    }

    public void setMark_desc(String mark_desc) {
        this.mark_desc = mark_desc;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getAdjust_fee() {
        return adjust_fee;
    }

    public void setAdjust_fee(String adjust_fee) {
        this.adjust_fee = adjust_fee;
    }

    public String getTrade_from() {
        return trade_from;
    }

    public void setTrade_from(String trade_from) {
        this.trade_from = trade_from;
    }

    public Boolean getBuyer_rate() {
        return buyer_rate;
    }

    public void setBuyer_rate(Boolean buyer_rate) {
        this.buyer_rate = buyer_rate;
    }

    public String getRx_audit_status() {
        return rx_audit_status;
    }

    public void setRx_audit_status(String rx_audit_status) {
        this.rx_audit_status = rx_audit_status;
    }

    public Boolean getPost_gate_declare() {
        return post_gate_declare;
    }

    public void setPost_gate_declare(Boolean post_gate_declare) {
        this.post_gate_declare = post_gate_declare;
    }

    public Boolean getCross_bonded_declare() {
        return cross_bonded_declare;
    }

    public void setCross_bonded_declare(Boolean cross_bonded_declare) {
        this.cross_bonded_declare = cross_bonded_declare;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<PromotionDetail> getPromotion_details() {
        return promotion_details;
    }

    public void setPromotion_details(List<PromotionDetail> promotion_details) {
        this.promotion_details = promotion_details;
    }

    public List<ServiceOrder> getService_orders() {
        return service_orders;
    }

    public void setService_orders(List<ServiceOrder> service_orders) {
        this.service_orders = service_orders;
    }

    public List<LogisticsTag> getService_tags() {
        return service_tags;
    }

    public void setService_tags(List<LogisticsTag> service_tags) {
        this.service_tags = service_tags;
    }

    public long getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(long refund_id) {
        this.refund_id = refund_id;
    }
}

package com.wangjunneil.schedule.common;

/**
 * Created by wangjun on 7/28/16.
 */
public final class Constants {


    private Constants() {}

    /*
     * 各接入平台关键字定义
     */
    public static final String PLATFORM_JD = "jd";
    public static final String PLATFORM_JP = "jp";
    public static final String PLATFORM_TM = "tmall";
    public static final String PLATFORM_Z800 = "z8";




    /**
     * 京东授权连接
     */
    public static final String JD_LINK_TOKEN_URL = "https://oauth.jd.com/oauth/authorize?response_type=code&client_id={0}&redirect_uri={1}&state={2}";

    /**
     * 京东请求token连接
     */
    public static final String JD_REQUEST_TOKEN_URL = "https://oauth.jd.com/oauth/token?grant_type=authorization_code&client_id={0}&redirect_uri={1}&code={2}&state={3}&client_secret={4}";

    /**
     * 京东刷新token连接
     */
    public static final String JD_REFRESH_TOKEN_URL = "https://oauth.jd.com/oauth/token?client_id={0}&client_secret={1}&grant_type=refresh_token&refresh_token={2}";

    /**
     * 京东rest请求地址
     */
    public static final String JD_SERVICE_URL = "https://api.jd.com/routerjson";

    /**
     * 京东订单可选字段
     */
//    public static final String JD_ORDER_OPTIONAL_FIELD = "vender_remark,balance_used,payment_confirm_time,waybill,logistics_id,pin,return_order,vat_invoice_info";
    public static final String JD_ORDER_OPTIONAL_FIELD = "order_id,order_source,customs,customs_model,vender_id,pay_type,order_total_price,order_seller_price,order_payment,freight_price,seller_discount,order_state,order_state_remark,delivery_type,invoice_info,order_remark,order_start_time,order_end_time,modified,consignee_info,item_info_list,coupon_detail_list,vender_remark,balance_used,payment_confirm_time,waybill,logistics_id,vat_invoice_info,parent_order_id,pin,return_order,order_type,store_order";

    /**
     * 京东同步的订单状态
     */
    public static final String JD_SYNC_ORDER_STATE = "WAIT_SELLER_STOCK_OUT,WAIT_GOODS_RECEIVE_CONFIRM,FINISHED_L,TRADE_CANCELED,LOCKED,PAUSE";

    public static final String TMALL_REQUEST_TOKEN_URL = "https://oauth.taobao.com/token?grant_type=authorization_code&view=web&client_id={0}&redirect_uri={1}&code={2}&state={3}&client_secret={4}";

    /**
     * 天猫请求授权地址
     */
    public static final String TMALL_LINK_TOKEN_URL = "https://oauth.taobao.com/authorize?response_type=code&view=web&client_id={0}&redirect_uri={1}&state={2}";


    /**
     * 天猫rest请求地址
     */
    public static final String TMALL_SERVICE_URL = "http://gw.api.taobao.com/router/rest";

    /**
     * 天猫订单可选字段
     */
    public static final String TMALL_ORDER_OPTIONAL_FIELD = "tid,payment,post_fee,discount_fee,adjust_fee,total_fee,received_payment," +
        "status,seller_nick,title,type,buyer_nick,buyer_message,receiver_name,receiver_country,receiver_state,receiver_city,receiver_district," +
        "receiver_town,receiver_address,receiver_mobile,receiver_phone,receiver_zip,created,pay_time,consign_time,modified,end_time,shipping_type," +
        "trade_from,orders,promotion_details";

    /**
     * 天猫退货详单可选字段
     */
    public static final String TMALL_REFUND_OPTIONAL_FIELD = "shipping_type,cs_status,advance_status,split_taobao_fee,split_seller_fee," +
        "refund_id,tid,oid,alipay_no,total_fee,buyer_nick,seller_nick,created,modified,order_status,status,good_status,has_good_return," +
        "refund_fee,payment,reason,desc,title,num,good_return_time,company_name,sid,address,refund_remind_timeout,num_iid," +
        "refund_phase,refund_version,sku,outer_id,operation_contraint";

    /**
     * 天猫退货列表可选字段
     */
    public static final String TMALL_REFUNDS_OPTIONAL_FIELD = "refund_id,tid,oid,alipay_no,total_fee,buyer_nick,seller_nick," +
        "created,modified,order_status,status,good_status,has_good_return,refund_fee,reason,desc,title,num,company_name,sid," +
        "refund_phase,refund_version,sku,outer_id,operation_contraint";

    public static final String TMALL_LOGUSTICS_OPTIONAL_FIELD = "id,code,name,reg_mail_no";

    /**
     * 天猫消息订阅地址
     */
    public static final String TMALL_MESSAGE_LISTEN_ADDR = "ws://mc.api.taobao.com";

    public static final String TMALL_TOPIC_TRADE_SUCCESS = "taobao_trade_TradeSuccess";
    public static final String TMALL_TOPIC_TRADE_CREATE = "taobao_trade_TradeCreate";
    public static final String TMALL_TOPIC_TRADE_CLOSE = "taobao_trade_TradeClose";
    public static final String TMALL_TOPIC_TRADE_BUYERPAY = "taobao_trade_TradeBuyerPay";
    public static final String TMALL_TOPIC_TRADE_SELLERSHIP = "taobao_trade_TradeSellerShip";
    //退款部分消息
    public static final String TMALL_TOPIC_REFUND_CREATED = "taobao_refund_RefundCreated";
    public static final String TMALL_TOPIC_REFUND_SELLERAGREEMENT = "taobao_refund_RefundSellerAgreeAgreement";
    public static final String TMALL_TOPIC_REFUND_BUYERRETURNGOODS = "taobao_refund_RefundBuyerReturnGoods";
    public static final String TMALL_TOPIC_REFUND_SELLERREFUSE = "taobao_refund_RefundSellerRefuseAgreement";
    public static final String TMALL_TOPIC_REFUND_CLOSE = "taobao_refund_RefundClosed";
    public static final String TMALL_TOPIC_REFUND_SUCCESS = "taobao_refund_RefundSuccess";

    public static final String TMALL_MESSAGE_TYPE_QUANTITY = "updateQuantity";


    /**
     * 卷皮请求token地址
     */
    public static final String JP_LINK_TOKEN_URL = "http://open.juanpi.com/erpapi/authorize";

    public static final String JP_AUTHORIZE_SCOPE = "order_list,order_info,get_express,sgoods_info,sgoods_list,send_goods,get_unid,get_goods_inventory,update_goods_inventory,aftersale_list,aftersale_detail,aftersale_refusereason,update_address,aftersale_handle";

    /**
     * 折800请求token地址
     */
    public static final String Z8_REQUEST_TOKEN_URL = "https://openapi.zhe800.com/oauth/token";//测试环境host配置：1.202.245.246=>openapi.zhe800.com，生产环境不需要配置

    public static final String Z8_URL = "https://openapi.zhe800.com/api/erp/v2/";
}

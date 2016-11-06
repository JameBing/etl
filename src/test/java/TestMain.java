import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import com.wangjunneil.schedule.common.Constants;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 8/4/16.
 */
public class TestMain {

    private static final String appkey = "23431719";
    private static final String secret = "308a1c2563be288a03c1b1eae31beeda";
//    private static final String sessionKey = "6200604fe0bfhjbbc1cd7b2f4494405a8d51f23d92f98ec2232897825";
    private static final String sessionKey = "6200a2782e809876086680778904ef5f658ZZ3dea0aaa152232897825";
    private static final String sessionKey2 = "6201720fe585cd488a4896e6f8d7ZZd8e6f4a2728f7e3072541280589";
    public static void main(String[] args) throws Exception {
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
//        factory.setBrokerURL("tcp://192.168.1.180:61616");
//        factory.setUserName("admin");
//        factory.setPassword("admin");
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//        cachingConnectionFactory.setSessionCacheSize(10);
//        cachingConnectionFactory.setTargetConnectionFactory(factory);
//        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
//        jmsTemplate.send("NotifyCallBack", new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage textMessage = session.createTextMessage();
//                textMessage.setText("sss");
//                return textMessage;
//            }
//        });

        //TmcClient client = new TmcClient("app_key", "app_secret", "default"); // 关于default参考消息分组说明
        /*TmcClient client = new TmcClient("23431719", "308a1c2563be288a03c1b1eae31beeda", "taobao_trade_TradeCreate"); // 关于default参考消息分组说明
        client.setMessageHandler(new MessageHandler() {
            public void onMessage(Message message, MessageStatus status) {
                try {
                    System.out.println(message.getContent());
                    System.out.println(message.getTopic());
                } catch (Exception e) {
                    e.printStackTrace();
                    status.fail(); // 消息处理失败回滚，服务端需要重发
                    // 重试注意：不是所有的异常都需要系统重试。
                    // 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发
                    // 对于，由于网络问题，权限问题导致的失败，可重试。
                    // 重试时间 5分钟不等，不要滥用，否则会引起雪崩
                }
            }
        });
        client.connect("ws://mc.api.taobao.com"); // 消息环境地址：ws://mc.api.tbsandbox.com/*/

        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);

        //对已订阅的消息进行授权
//        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
/*        TmcUserPermitRequest req = new TmcUserPermitRequest();
        req.setTopics("taobao_trade_TradeCreate,taobao_trade_TradeBuyerPay,taobao_trade_TradeSellerShip,taobao_trade_TradeSuccess,taobao_trade_TradeClose" +
            ",taobao_refund_RefundCreated,taobao_refund_RefundSellerAgreeAgreement,taobao_refund_RefundBuyerReturnGoods,taobao_refund_RefundSellerRefuseAgreement,taobao_refund_RefundClosed,taobao_refund_RefundSuccess");//
        TmcUserPermitResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/

        //获取用户已开通消息
        //TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
/*
        TmcUserGetRequest req = new TmcUserGetRequest();
        req.setFields("user_nick,topics,user_id,is_valid,created,modified");
        req.setNick("齐彩办公专营店");
        //req.setUserPlatform("tbUIC");
        TmcUserGetResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        //{"tmc_user_get_response":{"tmc_user":{"created":"2016-08-22 13:48:10","is_valid":true,"modified":"2016-09-22 15:20:55","topics":{"string":["taobao_trade_TradeCreate","taobao_trade_TradeClose","taobao_trade_TradeBuyerPay","taobao_trade_TradeSellerShip","taobao_trade_TradeSuccess","taobao_refund_RefundCreated","taobao_refund_RefundSellerAgreeAgreement","taobao_refund_RefundSellerRefuseAgreement","taobao_refund_RefundBuyerReturnGoods","taobao_refund_RefundClosed","taobao_refund_RefundSuccess"]},"user_id":2232897825,"user_nick":"齐彩办公专营店"},"request_id":"ze24go7x9sn0"}}
*/

        //获取自定义用户分组列表
        /*TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
        TmcGroupsGetRequest req = new TmcGroupsGetRequest();
        //req.setGroupNames("defualt");
        //req.setPageNo(1L);
        //req.setPageSize(40L);
        TmcGroupsGetResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());*/

        //查询物流公司信息
        /*TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
        LogisticsCompaniesGetRequest req = new LogisticsCompaniesGetRequest();
        req.setFields("id,code,name,reg_mail_no");
        //req.setIsRecommended(true);
        //req.setOrderMode("offline");
        LogisticsCompaniesGetResponse rsp = client.execute(req);
        List<LogisticsCompany> list =  rsp.getLogisticsCompanies();
        System.out.println(list.size());
        for(LogisticsCompany info : list){
            System.out.println(info.getId()+","+info.getCode()+","+info.getName()+","+info.getRegMailNo());
        }*/

        //根据外部ID取商品
/*        ItemsCustomGetRequest req = new ItemsCustomGetRequest();
        req.setOuterId("B3500");
        req.setFields("num_iid,outer_id,title,nick,price,num,is_virtual,sku");
        ItemsCustomGetResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/

        //获取单个商品详细信息
/*        ItemSellerGetRequest req = new ItemSellerGetRequest();
        req.setFields("num_iid,outer_id,title,nick,price,num,is_virtual,sku");
        req.setNumIid(539130012662L);
        ItemSellerGetResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/

/*        //SKU库存修改
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
        SkusQuantityUpdateRequest req = new SkusQuantityUpdateRequest();
        req.setNumIid(537465897158L);
        req.setType(1L);
//        req.setSkuidQuantities("3208280995138:100");
        req.setOuteridQuantities("10010:20");
        SkusQuantityUpdateResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/

        //宝贝/SKU库存修改
/*        ItemQuantityUpdateRequest req = new ItemQuantityUpdateRequest();
//        req.setNumIid(45286663876L);更新无sku的情况
//        req.setQuantity(140L);//140

        req.setNumIid(537465897158L);//对应的测试商品
//        req.setSkuId(3208280995138L);
        req.setOuterId("000579-01");
        req.setQuantity(101L);//宝贝含有销售属性，不能直接修改商品数量

        req.setType(1L);
        ItemQuantityUpdateResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/


/*        //通过外部id修改库存
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
        ItemsCustomGetRequest req = new ItemsCustomGetRequest();
        req.setOuterId("000579");
        req.setFields("num_iid,sku");
        ItemsCustomGetResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());
        List<Item> list =  rsp.getItems();
        if (null != list && list.size()>0) {
            Item item = list.get(0);
            System.out.println(item.getNumIid() + "==" + item.getSkus().get(0).getSkuId());
            SkusQuantityUpdateRequest req2 = new SkusQuantityUpdateRequest();
            req2.setNumIid(item.getNumIid());
            req2.setType(1L);
//        req2.setSkuidQuantities(item.getSkus().get(0).getSkuId().toString()+":100");
            req2.setOuteridQuantities(item.getSkus().get(0).getOuterId()+":30");
            SkusQuantityUpdateResponse rsp2 = client.execute(req2, sessionKey);
            System.out.println(rsp2.getBody());
        }*/

        //查询详单
//        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
        /*TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setFields(Constants.TMALL_ORDER_OPTIONAL_FIELD);
        req.setTid(2211721977540346L);
        TradeFullinfoGetResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/
//        Trade trade = rsp.getTrade();
//        String messageJson = JSONObject.toJSONString(trade);
//        System.out.println(messageJson);


        /**********************退款*********************/
        //查询卖家收到的买家退款订单
/*
        RefundsReceiveGetRequest req = new RefundsReceiveGetRequest();
        req.setFields(Constants.TMALL_REFUND_OPTIONAL_FIELD);
//        req.setStatus("WAIT_SELLER_AGREE");
        req.setType("fixed");
        //req.setStartModified(DateTimeUtil.parseDateString("2000-01-01 00:00:00"));
        //req.setEndModified(DateTimeUtil.parseDateString("2000-01-01 00:00:00"));
        req.setPageNo(1L);
        req.setPageSize(10L);
        req.setUseHasNext(true);
        RefundsReceiveGetResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());
*/

        //单笔退款详情
        /*RefundGetRequest req = new RefundGetRequest();
        req.setFields(Constants.TMALL_REFUND_OPTIONAL_FIELD);
        req.setRefundId(104781373664603L);
        RefundGetResponse rsp = client.execute(req, sessionKey2);
        System.out.println(rsp.getBody());*/

        //审核退款单【退款退货场景api】?
       /* String refundId = "114041260232735"; //refundId为退款编号
        String refundVersion = "1476696527000";
        RpRefundReviewRequest req = new RpRefundReviewRequest();
        req.setRefundId(Long.valueOf(refundId));
        req.setOperator("齐彩办公专营店:风信子");//审核人姓名
        req.setRefundPhase("onsale");
        req.setRefundVersion(Long.valueOf(refundVersion));
        req.setResult(true);//审核是否可用于批量退款，可选值：true（审核通过），false（审核不通过或反审核）
        req.setMessage("同意退款");//审核留言
        // req.setResult(false);//审核是否可用于批量退款，可选值：true（审核通过），false（审核不通过或反审核）
//        req.setMessage("不同意退款");//审核留言
        RpRefundReviewResponse rsp = client.execute(req, sessionKey2);
        System.out.println(rsp.getBody());*/
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"2101","sub_msg":"操作状态错误","request_id":"z28yfhto78v1"}}

        //同意退款【退款退货场景api】?
        RpRefundsAgreeRequest req = new RpRefundsAgreeRequest();
        String refundId = "104781373664603"; //refundId为退款编号
        String refundVersion = "1476761977000";
        String amount = "1";  //amount为退款金额（以分为单位）
        String phase = "onsale";   //可选值为：onsale, aftersale，天猫退款必值，淘宝退款不需要传
        String code = "111111";
        req.setCode(code);
        String refund_infos = refundId +"|" + amount + "|" + refundVersion + "|" + phase;
//        System.out.println(refund_infos);
        req.setRefundInfos(refund_infos);
        RpRefundsAgreeResponse rsp = client.execute(req, sessionKey2);
        System.out.println(rsp.getBody());
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.invalid-permission:not_sub_user","sub_msg":"必须为子账号操作","request_id":"rxmjijwfwljb"}}

        //卖家同意退货【退款退货场景api】?
/*        String refundId = "109687631996380"; //refundId为退款编号
        String refundVersion = "1474451348000";
        RpReturngoodsAgreeRequest req = new RpReturngoodsAgreeRequest();
        req.setRefundId(Long.valueOf(refundId));
        req.setName("张三");
        req.setAddress("浙江省杭州市西湖区XX路XX号XX小区");
        req.setPost("310000");
        req.setTel("05718848388");
        req.setMobile("18628219999");
        req.setRemark("没有问题，同意退货");
        req.setRefundPhase("onsale");
        req.setRefundVersion(Long.valueOf(refundVersion));
        req.setSellerAddressId(123456L);
        RpReturngoodsAgreeResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"PY-101","sub_msg":"查询物流信息出错","request_id":"13ozyzpzel8r7"}}*/


        //卖家拒绝退款
        // 卖家拒绝单笔退款（包含退款和退款退货）交易，要求如下： 1. 传入的refund_id和相应的tid, oid必须匹配 2. 如果一笔订单只有一笔子订单，则tid必须与oid相同 3. 只有卖家才能执行拒绝退款操作
        // 4. 以下三种情况不能退款：卖家未发货；7天无理由退换货；网游订单
//        System.out.println(new FileItem("F:/tmallRefund.png").getFileName());
//        System.out.println(new FileItem("F:/tmallRefund.png").getMimeType());
//        System.out.println(new FileItem("F:/tmallRefund.png").getFileLength());
//        tmallRefund.png
//        application/octet-stream
//        30721
/*        String orderStatus = "order_status";
        String refundId = "109687631996380"; //refundId为退款编号
        String refundVersion = "1474451348000";
//        String tid = "";
//        String oid = "";
        RefundRefuseRequest req = new RefundRefuseRequest();
        req.setRefundId(Long.valueOf(refundId));
        req.setRefuseMessage("拒绝退款");
//        req.setTid(Long.valueOf(tid));
//        req.setOid(Long.valueOf(oid));
        req.setRefuseProof(new FileItem("F:/tmallRefund.png"));//拒绝退款时的退款凭证，一般是卖家拒绝退款时使用的发货凭证，最大长度130000字节，支持的图片格式：GIF, JPG, PNG。天猫退款为必填项。支持的文件类型：gif,jpg,png
        req.setRefundPhase("onsale");//可选值为：售中：onsale，售后：aftersale，天猫退款为必填项。
        req.setRefundVersion(Long.valueOf(refundVersion));//退款版本号，天猫退款为必填项。
        req.setRefuseReasonId(118L);//可选
        RefundRefuseResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/

        //卖家拒绝退货【退款退货场景api】?
/*
        String refundId = "109687631996380"; //refundId为退款编号
        String refundVersion = "1474451348000";
        RpReturngoodsRefuseRequest req = new RpReturngoodsRefuseRequest();
        req.setRefundId(Long.valueOf(refundId));
        req.setRefundPhase("onsale");
        req.setRefundVersion(Long.valueOf(refundVersion));
        req.setRefuseProof(new FileItem("F:/tmallRefund.png"));//拒绝退货凭证图片，必须图片格式，大小不能超过5M
//        req.setRefuseReasonId(999L);//可选
        RpReturngoodsRefuseResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.invalid-parameter","sub_msg":"未发货状态下不能操作拒绝退款/退款退货申请,businessId109687631996380 refundType:200","request_id":"46h2jdeab2vo"}}
*/

        //获取拒绝原因列表
/*        String refundId = "106206407540810"; //refundId为退款编号
        RefundRefusereasonGetRequest req = new RefundRefusereasonGetRequest();
        req.setRefundId(Long.valueOf(refundId));
        req.setFields("reason_id,reason_text,reason_tips");
        req.setRefundPhase("aftersale");
        RefundRefusereasonGetResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.invalid-permission:refund_status is invalid or refund is out of date","sub_msg":"当前退款状态不能操作拒绝退款","request_id":"1476e9b6lf00p"}}
        //{"refund_refusereason_get_response":{"reasons":{"reason":[{"reason_id":118,"reason_text":"已经影响商品完好"},{"reason_id":103,"reason_text":"买家使用到付或平邮"},{"reason_id":1040,"reason_text":"退货商品不全、空包"},{"reason_id":106,"reason_text":"与买家协商换货"},{"reason_id":1047,"reason_text":"退货商品与订单商品不一致"},{"reason_id":104,"reason_text":"未收到退货，快递还在途中"},{"reason_id":105,"reason_text":"买家退货单号错误或无走件记录"}]},"request_id":"r4kh2p17apui"}}

        //查询默认退货地址
/*        LogisticsAddressSearchRequest req = new LogisticsAddressSearchRequest();
        req.setRdef("cancel_def");
        LogisticsAddressSearchResponse rsp = client.execute(req, sessionKey);
        System.out.println(rsp.getBody());*/
        //{"logistics_address_search_response":{"addresses":{"address_result":[{"addr":"二郎科技新城科技大道588号","area_id":500107,"cancel_def":true,"city":"重庆市","contact_id":754268848,"contact_name":"齐彩重庆总仓","country":"九龙坡区","get_def":true,"memo":"","mobile_phone":"4009103833","phone":"","province":"重庆","seller_company":"齐彩办公专营店","send_def":false,"zip_code":"400039"}]},"request_id":"44o12lx4vsst"}}


/*        //查询指定时间范围内的所有创建的订单
        String startDate = "2016-08-01 00:00:00";
        String endDate = "2016-08-01 23:59:59";
        TradesSoldGetRequest req = new TradesSoldGetRequest();
        req.setFields(Constants.TMALL_ORDER_OPTIONAL_FIELD);
        req.setStartCreated(StringUtils.parseDateTime(startDate));
        req.setEndCreated(StringUtils.parseDateTime(endDate));
        req.setExtType("service");
        req.setPageNo(1L);
        req.setPageSize(100L);
        req.setUseHasNext(true);
        TradesSoldGetResponse rsp = client.execute(req, sessionKey);
        List<Trade> tradeList = new ArrayList<Trade>();
//        System.out.println(rsp.getHasNext()+"=="+rsp.getBody());
        List<Trade> subList = rsp.getTrades();
        tradeList = subList;
        if (rsp.getHasNext()){//判断是否需要继续查询
            tradeList = queryList(client, tradeList, subList, startDate);
        }
        System.out.println(tradeList.size());
        System.out.println(JSONArray.toJSONString(tradeList));*/
    }

    private static List queryList(TaobaoClient client,List tradeList, List<Trade> list, String startDate) throws com.taobao.api.ApiException {
        if (list.size()>0){
            Date endTime = list.get(list.size()-1).getCreated();
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.SECOND, -1);//获得当前100条数据中最后一条数据时间前一秒的时间
            Date date = c.getTime();

            TradesSoldGetRequest req = new TradesSoldGetRequest();
            req.setFields(Constants.TMALL_ORDER_OPTIONAL_FIELD);
            req.setStartCreated(StringUtils.parseDateTime(startDate));
            req.setEndCreated(date);
            req.setExtType("service");
            req.setPageNo(1L);
            req.setPageSize(100L);
            req.setUseHasNext(true);
            TradesSoldGetResponse rsp2 = client.execute(req, sessionKey);
            List<Trade> subList = rsp2.getTrades();
            for (Trade trade: subList) {
                tradeList.add(trade);
            }
            if (rsp2.getHasNext()){//判断是否需要继续查询
                tradeList = queryList(client, tradeList, subList, startDate);
            }
        }
        return tradeList;
    }

}

package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.FileItem;
import com.taobao.api.domain.Order;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.request.LogisticsOnlineSendRequest;
import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducer;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.tm.*;
import com.wangjunneil.schedule.service.tm.TmallApiService;
import com.wangjunneil.schedule.service.tm.TmallInnerService;
import com.wangjunneil.schedule.utility.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TmallFacadeService implements MessageListener {

    private static Logger log = LoggerFactory.getLogger(Constants.PLATFORM_TM);

    @Autowired
    private TmallInnerService tmallInnerService;

    @Autowired
    private TmallApiService tmallApiService;

    //@Autowired
    private QueueMessageProducer messageProducer;

    // --------------------------------------------------------------------------------------------------- public method

    public void callback(String code, String state, Cfg cfg) {
        String tokenUrl = MessageFormat.format(Constants.TMALL_REQUEST_TOKEN_URL,
            cfg.getAppKey(), cfg.getCallback(), code, state, cfg.getAppSecret());
        String returnJson = HttpUtil.get(tokenUrl);
        TmallAccessToken tmallAccessToken = JSONObject.parseObject(returnJson, TmallAccessToken.class);
        tmallInnerService.addAccessToken(tmallAccessToken);
    }

    public void startListening(Cfg cfg) throws ScheduleException {
        tmallApiService.startListening(cfg, (message, messageStatus) -> {
            try {
                String topic = message.getTopic();      // 获取天猫消息类型
                boolean isSendMessage = true;           // 是否推送消息到目的地

                TmallOrderMessage orderMessage = null;  // 订单消息实体定义
                TmallCrmOrder tmallCrmOrder = null;     // 订单明细实体定义

                switch (topic) {
                    case Constants.TMALL_TOPIC_TRADE_CREATE:
                        orderMessage = parseTmallMessage(message);
                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        isSendMessage = false;
                        log.info("\t[TRADE_CREATE]\t\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_TRADE_CLOSE:
                        orderMessage = parseTmallMessage(message);
                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        isSendMessage = false;
                        log.info("\t[TRADE_CLOSE]\t\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_TRADE_BUYERPAY:
                        orderMessage = parseTmallMessage(message);
                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
//                        isSendMessage = !exceptionOrderProcess(orderMessage, tmallCrmOrder);//直接发送消息，不校验outerskuid及outeriid情况
                        log.info("\t[TRADE_BUYERPAY]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_TRADE_SELLERSHIP:
                        orderMessage = parseTmallMessage(message);
                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        log.info("\t[TRADE_SELLERSHIP]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_TRADE_SUCCESS:
                        orderMessage = parseTmallMessage(message);
                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        log.info("\t[TRADE_SUCCESS]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_REFUND_CREATED:
                        orderMessage = parseTmallMessage(message);
                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        // 退款单处理
                        tmallInnerService.addRefundOrder(refundOrderProcess(cfg, orderMessage.getContent().getRefund_id()));
                        isSendMessage = false;  // TODO 退款单暂未发送MQ
                        log.info("\t[REFUND_CREATED]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_REFUND_SELLERAGREEMENT:
                        orderMessage = parseTmallMessage(message);
                        //tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        // 退款单处理
                        tmallInnerService.addSyncRefundOrder(refundOrderProcess(cfg, orderMessage.getContent().getRefund_id()));
                        isSendMessage = false;  // TODO 退款单暂未发送MQ
                        log.info("\t[REFUND_SELLER_AGREEMENT]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_REFUND_BUYERRETURNGOODS:
                        orderMessage = parseTmallMessage(message);
                        //tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        // 退款单处理
                        tmallInnerService.addSyncRefundOrder(refundOrderProcess(cfg, orderMessage.getContent().getRefund_id()));
                        isSendMessage = false;  // TODO 退款单暂未发送MQ
                        log.info("\t[REFUND_BUYER_RETURNGOODS]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_REFUND_SELLERREFUSE:
                        orderMessage = parseTmallMessage(message);
                        //tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        // 退款单处理
                        tmallInnerService.addSyncRefundOrder(refundOrderProcess(cfg, orderMessage.getContent().getRefund_id()));
                        isSendMessage = false;  // TODO 退款单暂未发送MQ
                        log.info("\t[REFUND_SELLERREFUSE]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_REFUND_SUCCESS:
                        orderMessage = parseTmallMessage(message);
//                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        // 退款单处理
                        tmallInnerService.addSyncRefundOrder(refundOrderProcess(cfg, orderMessage.getContent().getRefund_id()));
                        isSendMessage = false;  // TODO 退款单暂未发送MQ
                        log.info("\t[REFUND_SUCCESS]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    case Constants.TMALL_TOPIC_REFUND_CLOSE:
                        orderMessage = parseTmallMessage(message);
//                        tmallCrmOrder = syncOrderDetail(cfg, orderMessage.getContent().getTid());
                        // 退款单处理
                        tmallInnerService.addSyncRefundOrder(refundOrderProcess(cfg, orderMessage.getContent().getRefund_id()));
                        isSendMessage = false;  // TODO 退款单暂未发送MQ
                        log.info("\t[REFUND_CLOSE]\tmessage " + orderMessage.getContent().getTid());
                        break;
                    default:
                        break;
                }

                if (tmallCrmOrder == null) return;

                //只发测试商品的消息
                /*boolean isSend = false;
                List<Order> orderList = tmallCrmOrder.getOrders();
                for (Order orderInfo : orderList) {
                    if ("TMtest001".equals(orderInfo.getOuterIid()) && "TMtest001-01".equals(orderInfo.getOuterSkuId())) {
                        isSend = true;
                        break;
                    }
                }
                if (isSend) {//TODO 只发测试商品的消息*/
                if (isSendMessage && null != tmallCrmOrder) {    // 订单消息发送到消息队列中
                    String messageJson = JSONObject.toJSONString(tmallCrmOrder);
                    messageProducer.sendTmallOrderMessage(messageJson);
                }

                    /* 持久化 存储订单消息流 存储订单实体详情*/
                tmallInnerService.addOrderMessage(orderMessage);
                tmallInnerService.addSyncOrder(tmallCrmOrder);
//                }
            } catch (Exception e) {
                log.error(e.toString());
                messageStatus.fail();
            }
        });
    }

    public int syncOrderByCond(Cfg cfg, TmallOrderRequest orderRequest) throws ScheduleException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<TmallCrmOrder> orders = new ArrayList<>();
        /* 默认从第一页开始同步查询, 递归查询的结果保存在trades中 */
        tmallApiService.syncOrderByCond(orderRequest, orders, 1L);
        if (log.isInfoEnabled())
            log.info("End the order synchronization summary size { " + orders.size() + " }");

        List<TmallCrmOrder> crmOrders = tmallInnerService.historyOrder(orderRequest);
        if (log.isInfoEnabled())
            log.info("End the order local db query summary size { " + crmOrders.size() + " }");

        List<TmallCrmOrder> needSupplementOrders = new ArrayList<>();
        List<TmallCrmOrder> needSendMsgList = new ArrayList<>();//统计需要发送的消息列表
        orders.stream().forEach(t -> {
            long count = crmOrders.stream()
                .filter(p -> t.getTid().equals(p.getTid()) && t.getStatus().equals(p.getStatus()))
                .count();

            if (count == 0) {   // 需补单
                // 判断订单行是否有退款单
                List<Long> refundIds = supplementRefund(cfg, t.getOrders());

                //对订单状态限制，只向mq发送待发货，已发货待接收，已完成状态的订单 add by zhangfei 20160929
                if ("WAIT_SELLER_SEND_GOODS,WAIT_BUYER_CONFIRM_GOODS,TRADE_FINISHED".contains(t.getStatus())) {
                    String messageJson = JSONObject.toJSONString(t);
                    messageProducer.sendTmallOrderMessage(messageJson);
                    needSendMsgList.add(t);
                }
                needSupplementOrders.add(t);
                tmallInnerService.addSyncOrder(t);//修改为单条更新，防止已存库的订单不同状态时产生多条记录

                if (log.isInfoEnabled())  {
                    StringBuffer sb = new StringBuffer("the synchronization order [" + t.getTid() + "] ");
                    if (refundIds.size() != 0)  sb.append("refundIds ").append(refundIds).append(' ');
                    sb.append("supplement finished");
                    log.info(sb.toString());
                    sb = null;
                }
            } else {

            }
        });

        int size = needSupplementOrders.size();
//        if (size != 0)
//            tmallInnerService.addSyncOrders(needSupplementOrders);

        if (log.isInfoEnabled())
            log.info("End supplement order operation, need supplement sum " + size + ", waste time "+ stopWatch.getTotalTimeSeconds() + " second");

//        return size;
        return needSendMsgList.size();//修改为实际发送的消息数
    }

    /**
     * 单条手工补单
     * @param cfg
     * @param tid
     * @throws ScheduleException
     */
    public void syncOrderById(Cfg cfg, Long tid) throws ScheduleException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TmallCrmOrder tmallCrmOrder = syncOrderDetail(cfg, tid);
        //对订单状态限制，只向mq发送待发货，已发货待接收，已完成状态的订单 add by zhangfei 20161204
        if ("WAIT_SELLER_SEND_GOODS,WAIT_BUYER_CONFIRM_GOODS,TRADE_FINISHED".contains(tmallCrmOrder.getStatus())) {
            String messageJson = JSONObject.toJSONString(tmallCrmOrder);
            messageProducer.sendTmallOrderMessage(messageJson);
        }
        log.info("Manual supplement order operation, tid " + tid.toString());
    }

    /**
     * 提供日期范围内查询订单方法：本地拉订单数据使用
     * @param orderRequest
     * @return
     * @throws ScheduleException
     */
    public int queryAllByCond(TmallOrderRequest orderRequest) throws ScheduleException {
        List<TmallCrmOrder> crmOrders = tmallInnerService.historyOrder(orderRequest);
        if (log.isInfoEnabled())
            log.info("End the order local db query summary size { " + crmOrders.size() + " }");

        //本地日志方式拉订单数据为客户手工做Excel使用
        /*//订单号，支付金额，发货时间，商品名称，商品外部skuid，实际销售价格，数量
        log.info("\t订单号\t订单支付金额\t交易状态" +
            "\t订单行信息\t运送方式\t发货时间\t快递单号\t快递公司\t商品名称\t外部skuId\t商品数量\t商品价格\t实付金额\t商品金额（商品价格乘以数量的总金额）\t");
        crmOrders.stream().forEach(t -> {
            String splitStr = "2775299287207084,2776694502762184,2372093363471826,2777320508848267,2777307507070155,2371656191742733,2776277311728063,2776353496712752,2776194893005867,2372778973162149,2776044511110283,2776987109085788,2774593084651876,2774493685246677,2776636508109887,2870294434918520,2371277599272348,2775740695708277,2371666569369748,2776549507656264,2870313018841908,2775686896812991,2371257990161328,2768375910952083,2775450910072575,2776360106964262,2775337510391366,2870211215551300,2371117396703942,2869840635523724,2371440565361330,2775067112441252,2870004637103013,2371017192191231,2372098976587631,2774969699077992,2869418801639118,2369759972832249,2773370886031868,2774661518403263,2869377605399012,2866905630900120,2371078975457133,2774318917847196,2368593581286846,2865847806833909,2775283709995553,2371984786031348,2768310880297487,2368187996065846,2769860714837484,2772913687808278,2368291165771825,2773381100514955,2771359111102082,2369553989495745,2771785686574892,2370125586662428,2368664798698343,2768715317552299,2868068629951023,2768929106889090,2769038286145473,2770010900154268,2371894589131039,2772766285804780,2772773084993756,2774233698403756,2774063715408966,2370887360226834,2774785100421270,2772488681101270,2772467481569177,2869341011917324,2371360978912238,2772349287145363,2773637710104962,2773750693391771,2773580515829287,2772252085742160,2370665362523737,2773555717022796,2868775201025004,2772191281613278,2870505427315212,2870434820949018,2773366890128763,2767276300720451,2767747499637752,2767685508438358,2367807775583840,2367113589767836,2868977430020424,2774049505941369,2868930637344110,2371194577547536,2773106294487756,2868414804411203,2868792419588116,2771333483344476,2870061821296319,2773522705765768,2868252203196308,2771152888980870,2772430710773091,2868141207361211,2773222904124880,2770910486473287,2868514214571718,2868336030605119,2772824101996162,2772033699156162,2867863601483904,2772725100525855,2369741762558126,2771713297811551,2771659497439375,2770146888730681,2867550000015300,2369519764512427,2369510369862427,2867544407291313,2771279716405897,2370396586872039,2769722484631786,2869189023459417,2867338804899115,2771789300761066,2770963495475273,2866828419079216,2368796995218927,2369961572633541,2770550511693772,2770603092966367,2369925786855142,2866950000297411,2866945002865407,2368502597160425,2770085112587574,2770990904503272,2770024517653350,2770022310062879,2770908302060388,2770002910067068,2768549882172691,2769888710636197,2868529424216011,2867207211908110,2867148439691924,2866633605021521,2368626960287927,2866599201730120,2866967611996709,2769461896465058,2866932610261924,2769294315411073,2770134905506061,2767717489124599,2769113116156455,2367977395560428,2866795234382222,2768939716615584,2367851392842427,2365926199059033,2764395093134652,2765165491871799,2765168306157275,2767426686725880,2867968225345423,2866104000274815,2368139766884729,2768623114080766,2866022207368902,2866033000758623,2769217704756190,2866017606547408,2368971789820445,2768487694556989,2768485091280662,2766755110628885,2866429037561605,2769152901850359,2866360218386419,2365330790546542,2766835685628660,2769103304817377,2766780482917199,2769063709835787,2768185714076570,2766768284933565,2768235299673894,2768159910692550,2866237034173010,2368752181321044,2866222838862208,2766557881213287,2766117489278357,2866177818655508,2368526171208426,2867420029718718,2368679781788935,2768669108093292,2368489978520935,2368483371235228,2866125836738109,2367632969607639,2766192087123485,2767477715573592,2366988378171947,2768304108073854,2765984480625069,2368253772211525,2768163106165764,2368210779956049,2767241315445764,2367013194222147,2865789437817909,2865782437017908,2366965995222242,2767085918834359,2865455032961717,2767083298587691,2767808904506282,2767714905528186,2368177382544748,2766781116657274,2367164965089643,2765126684492752,2765084280598285,2865367232280005,2367786372697634,2766305497885887,2366974764537445,2864100414809012,2865323010680709,2864682805431924,2366749567549349,2865074834640817,2765965095975495,2765938298008180,2766711300638999,2765467719978988,2366424765591347,2766127100510190,2367036774502335,2763229084509368,2764562711188795,2764652692558795,2764533311205553,2865598426191311,2863811006441316,2366657772772435,2863715001106811,2764227319639567,2865454229522724,2365882768262435,2762782080181094,2864099039950400,2365452395631833,2762744480815688,2865106627753124,2762118880151561,2861286829214424,2852121636372318,2853371627242318,2851583239552318,2840739216552318,2833466624302318,2830258807462318,2815691031940909,2698057633870909,2560976073443527,2608678107553798,2771970622595274,2606900685383798,2770019432765274,2771970623855274,2653290052862318,2497415865683527,2347342931488063,2499964050373527,2535243860863527,2535620873533527,2402297132218672,2333521516401376,2400927351188672,2336829751378063,2499597270473527,2540368258603527,2419658314579485,2336788151018063,2335151534348672,2332721354998063";
            String[] str = splitStr.split(",");
            boolean flag = true;
            for (int i=0; i<str.length;i++){
                if (t.getTid().toString().equals(str[i])){
                    flag = false;
                    break;
                }
            }
            if (flag) {
                List<Order> orders = t.getOrders();
                orders.stream().forEach(p -> {
                    log.info("\t" + t.getTid().toString() + "\t" + t.getPayment() + "\t" + t.getStatus()
                        + "\t订单行信息\t" + p.getShippingType() + "\t" + p.getConsignTime() + "\t" + p.getInvoiceNo() + "\t" + p.getLogisticsCompany() + "\t" + p.getTitle() + "\t" + p.getOuterSkuId() + "\t" + p.getNum().toString() + "\t" + p.getPrice() + "\t" + p.getPayment() + "\t" + p.getTotalFee() + "\t");
                });
            }
        });
        return crmOrders.size();*/

        //测试时间范围内本地订单入中台
        List<TmallCrmOrder> needSendMsgList = new ArrayList<>();//统计需要发送的消息列表
        crmOrders.stream().forEach(t -> {
            //对订单状态限制，只向mq发送待发货，已发货待接收，已完成状态的订单 add by zhangfei 20161204
            if ("WAIT_SELLER_SEND_GOODS,WAIT_BUYER_CONFIRM_GOODS,TRADE_FINISHED".contains(t.getStatus())) {
                String messageJson = JSONObject.toJSONString(t);
                messageProducer.sendTmallOrderMessage(messageJson);
                needSendMsgList.add(t);
            }
        });
        return needSendMsgList.size();
    }

    @Override
    public void onMessage(javax.jms.Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = null;

            try {
                text = textMessage.getText();
                JSONObject jsonObject = JSONObject.parseObject(text);
                String type = jsonObject.getString("type");
                if (type == null || "".equals(type)) {
                    log.warn("message content can not find [type] field and ignore it");
                    return;
                }

                switch (type) {
                    // {type:"updateQuantity", content:{updateType:2,channel:"pos",deatil:[{skuId:"000601",num:-2},{skuId:"000503",num:-1}]}}
                    case Constants.TMALL_MESSAGE_TYPE_QUANTITY:
                        String detail = jsonObject.getString("content");
                        TmallQuantityMessage quantityMessage = JSONObject.parseObject(detail, TmallQuantityMessage.class);
                        updateSkuQuantity(quantityMessage);
                        break;
                    default:
                        if (log.isWarnEnabled())
                            log.warn("unknown message type [" + type + "] and ignore it" );
                }

                System.out.println(textMessage.getText());
            } catch (Exception e) {
                if (log.isErrorEnabled())
                    log.error(e.toString() + ", message " + text);
            }
        } else {
            log.warn("illegal message content ignore it");
        }
    }



    public void stopListening(Cfg cfg) {
        tmallApiService.stopListening(cfg);
    }

    public String orderOutStock(LogisticsOnlineSendRequest request) throws ScheduleException {
        return tmallApiService.orderOutStock(request);
    }

    public String orderDummySend(long tid) throws ScheduleException {
        return tmallApiService.orderDummySend(tid);
    }

    public String logisticsList() throws ScheduleException {
        return tmallApiService.logisticsList();
    }

    public String skuQuantityUpdate(String outerId, String outerSkuId, String num) throws ScheduleException {
        return tmallApiService.skuQuantityUpdate(outerId, outerSkuId, num);
    }

    // --------------------------------------------------------------------------------------------------- private method

    private boolean exceptionOrderProcess(TmallOrderMessage orderMessage, TmallCrmOrder tmallCrmOrder) {
        boolean hasExceptionOrder = false;

        List<String> exceptionIds = tmallCrmOrder.getOrders()
            .stream().filter(order -> order.getOuterSkuId() == null || "".equals(order.getOuterSkuId()))
            .map(Order::getSkuId)
            .collect(Collectors.toList());

        if (exceptionIds.size() != 0) {
            orderMessage.setStatus("cann't find outSkuId: " + exceptionIds);
            hasExceptionOrder = true;
        }

        return hasExceptionOrder;
    }

    private TmallOrderMessage parseTmallMessage(Message message) {
        TmallOrderMessage orderMessage = new TmallOrderMessage();
        orderMessage.setId(message.getId());
        orderMessage.setUserId(message.getUserId());
        orderMessage.setTopic(message.getTopic());
        orderMessage.setPubAppKey(message.getPubAppKey());
        orderMessage.setPubTime(message.getPubTime());
        orderMessage.setOutgoingTime(message.getOutgoingTime());
        orderMessage.setUserNick(message.getUserNick());
        TmallOrderContent orderContent = JSONObject.parseObject(message.getContent(), TmallOrderContent.class);
        orderMessage.setContent(orderContent);
        return  orderMessage;
    }

    private TmallCrmOrder syncOrderDetail(Cfg cfg, long tid) throws ScheduleException {
        return tmallApiService.syncOrderById(cfg, tid);
    }

    private TmallCrmRefund refundOrderProcess(Cfg cfg, long refundId) throws ScheduleException {
        return tmallApiService.getRefundById(cfg, refundId);
    }

    public String getHistoryOrder(TmallOrderRequest tmallOrderRequest, Page<TmallCrmOrder> page) {
        Page<TmallCrmOrder> returnPage = tmallInnerService.getHistoryOrder(tmallOrderRequest, page);
        return JSONObject.toJSONString(returnPage);
    }

    public String getRefundOrder(Map<String, String> paramMap, Page<TmallCrmRefund> page){
        Page<TmallCrmRefund> returnPage = tmallInnerService.getRefundOrder(paramMap, page);
        return JSONObject.toJSONString(returnPage);
    }

    public String getControlStatus(){
        Cfg cfg = tmallInnerService.getTmCfg();
        return JSONObject.toJSONString(cfg);
    }

    private List<Long> supplementRefund(Cfg cfg, List<Order> orders) {
        List<Long> refundIds = new ArrayList<>();
        orders.stream().forEach(order ->  {
            if (order.getRefundId() != null) { // 存在退款单
                try {
                    TmallCrmRefund refundOrder = refundOrderProcess(cfg, order.getRefundId());
                    tmallInnerService.addRefundOrder(refundOrder);
                    refundIds.add(refundOrder.getRefund_id());
                } catch (ScheduleException e) {
                    // 忽略补单错误
                    log.error(e.toString());
                }
            }
        });
        return refundIds;
    }

    private void updateSkuQuantity(TmallQuantityMessage quantityMessage) {

    }

    public String refundReview(Map<String, String> paramMap) throws ScheduleException {
        return tmallApiService.refundReview(paramMap);
    }

    public String refundAgree(Map<String, String> paramMap) throws ScheduleException {
        return tmallApiService.refundAgree(paramMap);
    }

    public String sellerAddrList() throws ScheduleException {
        return tmallApiService.sellerAddrList();
    }

    public String returnGoodsAgree(Map<String, String> paramMap) throws ScheduleException {
        return tmallApiService.returnGoodsAgree(paramMap);
    }

    public String getRefuseReasonList(Map<String, String> paramMap) throws ScheduleException {
        return tmallApiService.getRefuseReasonList(paramMap);
    }

    public String refundRefuse(Map<String, String> paramMap, FileItem fileItem) throws ScheduleException {
        TmallCrmRefund refund = tmallInnerService.getRefundOrderById(paramMap.get("refundId"));
        paramMap.put("tId",String.valueOf(refund.getTid()));
        paramMap.put("oId",String.valueOf(refund.getOid()));
        return tmallApiService.refundRefuse(paramMap, fileItem);
    }

    public String returnGoodsRefuse(Map<String, String> paramMap, FileItem fileItem) throws ScheduleException {
        return tmallApiService.returnGoodsRefuse(paramMap, fileItem);
    }
}

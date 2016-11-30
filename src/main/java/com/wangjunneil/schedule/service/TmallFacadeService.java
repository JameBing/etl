package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.FileItem;
import com.taobao.api.domain.Order;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.request.LogisticsOnlineSendRequest;
import com.wangjunneil.schedule.activemq.QueueMessageProducer;
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

    @Autowired
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
                        isSendMessage = !exceptionOrderProcess(orderMessage, tmallCrmOrder);
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
//        List<TmallCrmOrder> needSendMsgList = new ArrayList<>();//统计需要发送的消息列表
        orders.stream().forEach(t -> {
            long count = crmOrders.stream()
                .filter(p -> t.getTid().equals(p.getTid()) && t.getStatus().equals(p.getStatus()))
                .count();

            if (count == 0) {   // 需补单
                // 判断订单行是否有退款单
                List<Long> refundIds = supplementRefund(cfg, t.getOrders());

                //对订单状态限制，只向mq发送待发货，已发货待接收，已完成状态的订单 add by zhangfei 20160929
                /*if ("WAIT_SELLER_SEND_GOODS,WAIT_BUYER_CONFIRM_GOODS,TRADE_FINISHED".contains(t.getStatus())) {
                    String messageJson = JSONObject.toJSONString(t);
                    messageProducer.sendTmallOrderMessage(messageJson);
                    needSendMsgList.add(t);
                }*/
                needSupplementOrders.add(t);

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
        if (size != 0)
            tmallInnerService.addSyncOrders(needSupplementOrders);

        if (log.isInfoEnabled())
            log.info("End supplement order operation, need supplement sum " + size + ", waste time "+ stopWatch.getTotalTimeSeconds() + " second");

        return size;
//        return needSendMsgList.size();//TODO 暂时修改为实际发送的消息数
    }

    public int queryAllByCond(TmallOrderRequest orderRequest) throws ScheduleException {
        List<TmallCrmOrder> crmOrders = tmallInnerService.historyOrder(orderRequest);
        if (log.isInfoEnabled())
            log.info("End the order local db query summary size { " + crmOrders.size() + " }");
        //订单号，支付金额，发货时间，商品名称，商品外部skuid，实际销售价格，数量
        log.info("\t订单号\t订单支付金额\t交易状态" +
            "\t订单行信息\t运送方式\t发货时间\t快递单号\t快递公司\t商品名称\t外部skuId\t商品数量\t商品价格\t实付金额\t商品金额（商品价格乘以数量的总金额）\t");
        crmOrders.stream().forEach(t -> {
            List<Order> orders = t.getOrders();
            orders.stream().forEach(p -> {
                log.info("\t" + t.getTid().toString() + "\t" + t.getPayment() + "\t" + t.getStatus()
                    + "\t订单行信息\t" + p.getShippingType() + "\t" + p.getConsignTime() + "\t" + p.getInvoiceNo() + "\t" + p.getLogisticsCompany() + "\t" + p.getTitle() + "\t" + p.getOuterSkuId() + "\t" + p.getNum().toString() + "\t" + p.getPrice() + "\t" + p.getPayment() + "\t" + p.getTotalFee() + "\t");
            });

        });
        return crmOrders.size();
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

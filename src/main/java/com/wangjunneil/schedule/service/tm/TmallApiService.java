package com.wangjunneil.schedule.service.tm;

import com.taobao.api.*;
import com.taobao.api.domain.*;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.tm.*;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.utility.AmountUtils;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Created by wangjun on 8/1/16.
 */
@Service
public class TmallApiService {

    private static Logger log = LoggerFactory.getLogger(Constants.PLATFORM_TM);

    @Autowired
    private SysFacadeService sysFacadeService;

    private TmcClient client;

    public TmcClient getClient() {
        return client;
    }

    public void setClient(TmcClient client) {
        this.client = client;
    }

    public void startListening(Cfg cfg, MessageHandler messageHandler) throws ScheduleException {
        client = new TmcClient(cfg.getAppKey(), cfg.getAppSecret(), "default");
        client.setMessageHandler(messageHandler);
        try {
            client.connect(Constants.TMALL_MESSAGE_LISTEN_ADDR);
        } catch (LinkException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    public void stopListening(Cfg cfg) {
        //TmcClient client = new TmcClient(cfg.getAppKey(), cfg.getAppSecret(), "default");
        client.close();
    }

//    public List<TmallCrmOrder> syncAllOrder(Cfg cfg) {
//        List<TmallCrmOrder> crmOrders = new ArrayList<>();
//
//        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
//        TradesSoldGetRequest req = new TradesSoldGetRequest();
//        req.setFields(Constants.TMALL_ORDER_OPTIONAL_FIELD);
//
//
//        return crmOrders;
//    }

    public TmallCrmOrder syncOrderById(Cfg cfg, long tid) throws ScheduleException {
        TmallAccessToken token = sysFacadeService.getTmToken();

        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
        req.setFields(Constants.TMALL_ORDER_OPTIONAL_FIELD);
        req.setTid(tid);

        try {
            TradeFullinfoGetResponse response = client.execute(req, token.getAccess_token());
            validateStatus(response);

            Trade trade = response.getTrade();
            return adapterTmallOrder(trade);
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    public TmallCrmRefund getRefundById(Cfg cfg, long refundId) throws ScheduleException {
        TmallAccessToken token = sysFacadeService.getTmToken();

        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        RefundGetRequest req = new RefundGetRequest();
        req.setFields(Constants.TMALL_REFUND_OPTIONAL_FIELD);
        req.setRefundId(refundId);

        try {
            RefundGetResponse rsp = client.execute(req, token.getAccess_token());
            Refund refund = rsp.getRefund();

            TmallCrmRefund tmallCrmRefund = new TmallCrmRefund();
            tmallCrmRefund.setTid(refund.getTid());
            tmallCrmRefund.setOid(refund.getOid());
            tmallCrmRefund.setRefund_id(refund.getRefundId());
            tmallCrmRefund.setCs_status(refund.getCsStatus());
            tmallCrmRefund.setAdvance_status(refund.getAdvanceStatus());
            tmallCrmRefund.setShipping_type(refund.getShippingType());
            tmallCrmRefund.setSplit_taobao_fee(refund.getSplitTaobaoFee());
            tmallCrmRefund.setSplit_seller_fee(refund.getSplitSellerFee());
            tmallCrmRefund.setAlipay_no(refund.getAlipayNo());
            tmallCrmRefund.setTotal_fee(refund.getTotalFee());
            tmallCrmRefund.setBuyer_nick(refund.getBuyerNick());
            tmallCrmRefund.setSeller_nick(refund.getSellerNick());
            tmallCrmRefund.setCreated(refund.getCreated());
            tmallCrmRefund.setModified(refund.getModified());
            tmallCrmRefund.setOrder_status(refund.getOrderStatus());
            tmallCrmRefund.setStatus(refund.getStatus());
            tmallCrmRefund.setGood_status(refund.getGoodStatus());
            tmallCrmRefund.setHas_good_return(refund.getHasGoodReturn());
            tmallCrmRefund.setRefund_fee(refund.getRefundFee());
            tmallCrmRefund.setPayment(refund.getPayment());
            tmallCrmRefund.setReason(refund.getReason());
            tmallCrmRefund.setDesc(refund.getDesc());
            tmallCrmRefund.setTitle(refund.getTitle());
            tmallCrmRefund.setPrice(refund.getPrice());
            tmallCrmRefund.setNum(refund.getNum());
            tmallCrmRefund.setGood_return_time(refund.getGoodReturnTime());
            tmallCrmRefund.setCompany_name(refund.getCompanyName());
            tmallCrmRefund.setSid(refund.getSid());
            tmallCrmRefund.setAddress(refund.getAddress());
            tmallCrmRefund.setRefund_remind_timeout(refund.getRefundRemindTimeout());
            tmallCrmRefund.setNum_iid(refund.getNumIid());
            tmallCrmRefund.setRefund_phase(refund.getRefundPhase());
            tmallCrmRefund.setRefund_version(refund.getRefundVersion());

            return tmallCrmRefund;
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    public String orderOutStock(LogisticsOnlineSendRequest request) throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();

        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        try {
            LogisticsOnlineSendResponse response = client.execute(request, token.getAccess_token());
            validateStatus(response);
            return response.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    public String orderDummySend(long tid) throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();

        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        LogisticsDummySendResponse rsp;
        try {
            LogisticsDummySendRequest req = new LogisticsDummySendRequest();
            req.setTid(tid);
            rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    public String skuQuantityUpdate(String outerId, String outerSkuId, String num) throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();

        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        SkusQuantityUpdateResponse rsp2 = null;
        try {
            ItemsCustomGetRequest req = new ItemsCustomGetRequest();
            req.setOuterId(outerId);
            req.setFields("num_iid,sku");
            ItemsCustomGetResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            Long numIid = null;
            Long skuId = null;
            List<Item> list =  rsp.getItems();
            if (null != list && list.size()>0) {
                Item item = list.get(0);
                numIid = item.getNumIid();
//                List<Sku> skuList = item.getSkus();
//                for (int i=0; i<skuList.size(); i++) {
//                    Sku sku = skuList.get(i);
//                    if (outerSkuId.equals(sku.getOuterId())) {
//                        skuId = sku.getSkuId();
//                        break;
//                    }
//                }
            }
            SkusQuantityUpdateRequest req2 = new SkusQuantityUpdateRequest();
            req2.setNumIid(numIid);//商品数字ID
            req2.setType(1L);//库存更新方式，可选。1为全量更新，2为增量更新。
//            req2.setSkuidQuantities(skuId.toString()+":"+num);//sku库存批量修改入参，用于指定一批sku和每个sku的库存修改值
            req2.setOuteridQuantities(outerSkuId + ":" + num);//skuIdQuantities为空的时候用该字段通过outerId来指定sku和其库存修改值
            rsp2 = client.execute(req2, token.getAccess_token());
            validateStatus(rsp2);
            return rsp2.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * ----------------------------------------------------------------------------------------------------------------
     *  根据条件全量同步天猫订单列表, 由于每次最多获取100条订单, 所以此方法为递归方法, 根据响应判断是否还有下一页为标志
     *  位进行递归查询, 直到 soldGetResponse.getHasNext() 为false时完成查询结果。
     * ----------------------------------------------------------------------------------------------------------------
     *
     * @param orderRequest          订单条件请求对象
     * @param orders                用于存储递归查询的订单对象
     * @param pageNo                查询的页数
     * @throws ScheduleException    调用查询接口可能出现的异常对象
     * ----------------------------------------------------------------------------------------------------------------
     */
    public void syncOrderByCond(TmallOrderRequest orderRequest, List<TmallCrmOrder> orders, long pageNo) throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();

        Date startDate = DateTimeUtil.parseDateString(orderRequest.getStartDate());
        Date endDate = DateTimeUtil.parseDateString(orderRequest.getEndDate());
        String orderState = orderRequest.getOrderState();

        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        try {
            TradesSoldGetRequest soldGetRequest = new TradesSoldGetRequest();
            soldGetRequest.setFields(Constants.TMALL_ORDER_OPTIONAL_FIELD);
            soldGetRequest.setStartCreated(startDate);
            soldGetRequest.setEndCreated(endDate);
            soldGetRequest.setExtType("service");
            if (orderState != null && !"".equals(orderState))
                soldGetRequest.setStatus(orderState);
            soldGetRequest.setPageNo(pageNo);
            soldGetRequest.setPageSize(100L);
            soldGetRequest.setUseHasNext(true);

            TradesSoldGetResponse soldGetResponse = client.execute(soldGetRequest, token.getAccess_token());
            validateStatus(soldGetResponse);

            soldGetResponse.getTrades().stream().forEach(p -> {
                orders.add(adapterTmallOrder(p));
            });

            if (log.isInfoEnabled())
                log.info("pageNo " + pageNo + " sync order size " + soldGetResponse.getTrades().size());

            if (soldGetResponse.getHasNext())
                syncOrderByCond(orderRequest, orders, ++pageNo);
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    public String logisticsList() throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        LogisticsCompaniesGetRequest req = new LogisticsCompaniesGetRequest();
        req.setFields("id,code,name,reg_mail_no");
        try {
            LogisticsCompaniesGetResponse rsp = client.execute(req);
            validateStatus(rsp);
            // List<LogisticsCompany> logisticsCompanies = rsp.getLogisticsCompanies();
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    private void validateStatus(TaobaoResponse taobaoResponse) throws ApiException {
        String errorCode = taobaoResponse.getErrorCode();
        String subCode = taobaoResponse.getSubCode();
        if (errorCode != null || subCode != null) {
            String msg = taobaoResponse.getMsg();
            String subMsg = taobaoResponse.getSubMsg();

            StringBuffer errorMessage = new StringBuffer(subCode);
            errorMessage.append(", ").append(subMsg);
            throw new ApiException(errorMessage.toString());
        }
    }

    private TmallCrmOrder  adapterTmallOrder(Trade trade) {
        TmallCrmOrder tmallCrmOrder = new TmallCrmOrder();
        tmallCrmOrder.setTid(trade.getTid());
        tmallCrmOrder.setPayment(trade.getPayment());
        tmallCrmOrder.setPost_fee(trade.getPostFee());
        tmallCrmOrder.setStatus(trade.getStatus());
        tmallCrmOrder.setBuyer_message(trade.getBuyerMessage());
        tmallCrmOrder.setHas_buyer_message(trade.getHasBuyerMessage());
        tmallCrmOrder.setReceiver_name(trade.getReceiverName());
        tmallCrmOrder.setReceiver_country(trade.getReceiverCountry());
        tmallCrmOrder.setReceiver_state(trade.getReceiverState());
        tmallCrmOrder.setReceiver_city(trade.getReceiverCity());
        tmallCrmOrder.setReceiver_district(trade.getReceiverDistrict());
        tmallCrmOrder.setReceiver_town(trade.getReceiverTown());
        tmallCrmOrder.setReceiver_address(trade.getReceiverAddress());
        tmallCrmOrder.setReceiver_mobile(trade.getReceiverMobile());
        tmallCrmOrder.setReceiver_phone(trade.getReceiverPhone());
        tmallCrmOrder.setReceiver_zip(trade.getReceiverZip());
        tmallCrmOrder.setReceived_payment(trade.getReceivedPayment());
        tmallCrmOrder.setSeller_nick(trade.getSellerNick());
        tmallCrmOrder.setSeller_rate(trade.getSellerRate());
        tmallCrmOrder.setSeller_flag(trade.getSellerFlag());
        tmallCrmOrder.setPic_path(trade.getPicPath());
        tmallCrmOrder.setConsign_time(trade.getConsignTime());
        tmallCrmOrder.setOrder_tax_fee(trade.getOrderTaxFee());
        tmallCrmOrder.setShop_pick(trade.getShopPick());
        tmallCrmOrder.setNum(trade.getNum());
        tmallCrmOrder.setNum_iid(trade.getNumIid());
        tmallCrmOrder.setTitle(trade.getTitle());
        tmallCrmOrder.setType(trade.getType());
        tmallCrmOrder.setPrice(trade.getPrice());
        tmallCrmOrder.setDiscount_fee(trade.getDiscountFee());
        tmallCrmOrder.setTotal_fee(trade.getTotalFee());
        tmallCrmOrder.setCreated(trade.getCreated());
        tmallCrmOrder.setPay_time(trade.getPayTime());
        tmallCrmOrder.setModified(trade.getModified());
        tmallCrmOrder.setEnd_time(trade.getEndTime());
        tmallCrmOrder.setBuyer_nick(trade.getBuyerNick());
        tmallCrmOrder.setCredit_card_fee(trade.getCreditCardFee());
        tmallCrmOrder.setStep_trade_status(trade.getStepTradeStatus());
        tmallCrmOrder.setStep_paid_fee(trade.getStepPaidFee());
        tmallCrmOrder.setMark_desc(trade.getMarkDesc());
        tmallCrmOrder.setShipping_type(trade.getShippingType());
        tmallCrmOrder.setAdjust_fee(trade.getAdjustFee());
        tmallCrmOrder.setTrade_from(trade.getTradeFrom());
        tmallCrmOrder.setBuyer_rate(trade.getBuyerRate());
        tmallCrmOrder.setRx_audit_status(trade.getRxAuditStatus());
        tmallCrmOrder.setPost_gate_declare(trade.getPostGateDeclare());
        tmallCrmOrder.setCross_bonded_declare(trade.getCrossBondedDeclare());
        tmallCrmOrder.setOrders(trade.getOrders());
        tmallCrmOrder.setPromotion_details(trade.getPromotionDetails());
        tmallCrmOrder.setService_orders(trade.getServiceOrders());
        tmallCrmOrder.setService_tags(trade.getServiceTags());
        return tmallCrmOrder;
    }

    /**
     * 审核退款单
     * @param paramMap
     * @return
     * @throws ScheduleException
     */
    public String refundReview(Map<String, String> paramMap) throws ScheduleException  {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        RpRefundReviewRequest req = new RpRefundReviewRequest();
        req.setRefundId(Long.valueOf(paramMap.get("refundId")));
        req.setOperator(paramMap.get("operator"));//审核人姓名
        req.setRefundPhase(paramMap.get("refundPhase"));
        req.setRefundVersion(Long.valueOf(paramMap.get("refundVersion")));
        req.setResult(Boolean.valueOf(paramMap.get("result")));//审核是否可用于批量退款，可选值：true（审核通过），false（审核不通过或反审核）
        req.setMessage(paramMap.get("message"));//审核留言
        try {
            RpRefundReviewResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * 同意退款
     * @param paramMap
     * @return
     * @throws ScheduleException
     */
    public String refundAgree(Map<String, String> paramMap) throws ScheduleException  {
        String refundId = paramMap.get("refundId");
        String amount = AmountUtils.convertY2F(paramMap.get("amount"));  //amount为退款金额（以分为单位）
        String refundVersion = paramMap.get("refundVersion");
        String phase = paramMap.get("refundPhase");   //可选值为：onsale, aftersale，天猫退款必值，淘宝退款不需要传
        String refund_infos = refundId +"|" + amount + "|" + refundVersion + "|" + phase;
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        RpRefundsAgreeRequest req = new RpRefundsAgreeRequest();
        req.setRefundInfos(refund_infos);
        if (null != paramMap.get("code") && !"".equals(paramMap.get("code").toString())){
            req.setCode(paramMap.get("code").toString());
        }
        try {
            RpRefundsAgreeResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * 获取卖家退货地址
     * @return
     * @throws ScheduleException
     */
    public String sellerAddrList() throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        LogisticsAddressSearchRequest req = new LogisticsAddressSearchRequest();
        req.setRdef("cancel_def");
        try {
            LogisticsAddressSearchResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * 同意退货
     * @param paramMap
     * @return
     * @throws ScheduleException
     */
    public String returnGoodsAgree(Map<String, String> paramMap) throws ScheduleException  {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        RpReturngoodsAgreeRequest req = new RpReturngoodsAgreeRequest();
        req.setRefundId(Long.valueOf(paramMap.get("refundId")));
        req.setName(paramMap.get("name"));
        req.setAddress(paramMap.get("address"));
        req.setPost(paramMap.get("post"));
        req.setTel(paramMap.get("tel"));
        req.setMobile(paramMap.get("mobile"));
        req.setRemark(paramMap.get("remark"));
        req.setRefundPhase(paramMap.get("refundPhase"));
        req.setRefundVersion(Long.valueOf(paramMap.get("refundVersion")));
        req.setSellerAddressId(Long.valueOf(paramMap.get("sellerAddressId")));
        try {
            RpReturngoodsAgreeResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * 获取拒绝原因列表
     * @return
     * @throws ScheduleException
     */
    public String getRefuseReasonList(Map<String, String> paramMap) throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        RefundRefusereasonGetRequest req = new RefundRefusereasonGetRequest();
        req.setRefundId(Long.valueOf(paramMap.get("refundId")));
        req.setFields("reason_id,reason_text,reason_tips");
        req.setRefundPhase(paramMap.get("refundPhase"));
        try {
            RefundRefusereasonGetResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * 拒绝退款（包含退款和退款退货）
     * @param paramMap
     * @return
     * @throws ScheduleException
     */
    public String refundRefuse(Map<String, String> paramMap, FileItem fileItem) throws ScheduleException  {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        RefundRefuseRequest req = new RefundRefuseRequest();
        req.setRefundId(Long.valueOf(paramMap.get("refundId")));
        req.setTid(Long.valueOf(paramMap.get("tId")));
        req.setOid(Long.valueOf(paramMap.get("oId")));
        req.setRefuseMessage(paramMap.get("refuseMessage"));//长度2-200
        req.setRefuseProof(fileItem);//拒绝退款时的退款凭证，一般是卖家拒绝退款时使用的发货凭证，最大长度130000字节，支持的图片格式：GIF, JPG, PNG。天猫退款为必填项。支持的文件类型：gif,jpg,png
        req.setRefundPhase(paramMap.get("refundPhase"));
        req.setRefundVersion(Long.valueOf(paramMap.get("refundVersion")));
        if (null != paramMap.get("refuseReasonId") && "".equals(paramMap.get("refuseReasonId"))) {
            req.setRefuseReasonId(Long.valueOf(paramMap.get("refuseReasonId")));
        }
        try {
            RefundRefuseResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**
     * 拒绝退货
     * @param paramMap
     * @param fileItem
     * @return
     * @throws ScheduleException
     */
    public String returnGoodsRefuse(Map<String, String> paramMap, FileItem fileItem) throws ScheduleException  {
        Cfg cfg = sysFacadeService.findTmCfg();
        TmallAccessToken token = sysFacadeService.getTmToken();
        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, cfg.getAppKey(), cfg.getAppSecret());
        RpReturngoodsRefuseRequest req = new RpReturngoodsRefuseRequest();
        req.setRefundId(Long.valueOf(paramMap.get("refundId")));
        req.setRefuseProof(fileItem);//拒绝退货凭证图片，必须图片格式，大小不能超过5M
        req.setRefundPhase(paramMap.get("refundPhase"));
        req.setRefundVersion(Long.valueOf(paramMap.get("refundVersion")));
        if (null != paramMap.get("refuseReasonId") && "".equals(paramMap.get("refuseReasonId"))) {
            req.setRefuseReasonId(Long.valueOf(paramMap.get("refuseReasonId")));
        }
        try {
            RpReturngoodsRefuseResponse rsp = client.execute(req, token.getAccess_token());
            validateStatus(rsp);
            return rsp.getBody();
        } catch (ApiException e) {
            throw new ScheduleException(e.getMessage());
        }
    }

//    private List<Trade> queryOrderList(TaobaoClient client, List<Trade> tradeList, List<Trade> list, TmallAccessToken token, String startDate, String status) throws com.taobao.api.ApiException {
//        if (list.size()>0){
//            Date endTime = list.get(list.size()-1).getCreated();
//            Calendar c = Calendar.getInstance();
//            c.setTime(endTime);
//            c.add(Calendar.SECOND, -1);//获得当前100条数据中最后一条数据时间前一秒的时间
//            Date date = c.getTime();
//
//            TradesSoldGetRequest req = new TradesSoldGetRequest();
//            req.setFields(Constants.TMALL_ORDER_OPTIONAL_FIELD);
//            req.setStartCreated(StringUtils.parseDateTime(startDate));
//            req.setEndCreated(date);
//            req.setExtType("service");
//            if (!StringUtil.isEmpty(status))
//                req.setStatus(status);
//
//            req.setPageNo(1L);
//            req.setPageSize(100L);
//            req.setUseHasNext(true);
//            TradesSoldGetResponse rsp2 = client.execute(req, token.getAccess_token());
//            validateStatus(rsp2);
//            List<Trade> subList = rsp2.getTrades();
//            for (Trade trade: subList) {
//                tradeList.add(trade);
//            }
//            if (rsp2.getHasNext())//判断是否需要继续查询
//                tradeList = queryOrderList(client, tradeList, subList, token, startDate, status);
//        }
//        return tradeList;
//    }


}

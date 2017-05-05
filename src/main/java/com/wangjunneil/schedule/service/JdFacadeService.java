package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONObject;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.crm.CrmMemberService.CrmMember;
import com.jd.open.api.sdk.domain.crm.CrmMemberService.CrmMemberResult;
import com.jd.open.api.sdk.domain.seller.ShopSafService.ShopJosResult;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.jd.open.api.sdk.response.crm.CrmMemberSearchResponse;
import com.jd.open.api.sdk.response.order.OrderSopOutstorageResponse;
import com.jd.open.api.sdk.response.seller.SellerVenderInfoGetResponse;
import com.jd.open.api.sdk.response.seller.VenderShopQueryResponse;
import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducer;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.jd.*;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.service.jd.JdApiService;
import com.wangjunneil.schedule.service.jd.JdInnerService;
import com.wangjunneil.schedule.service.jd.JobService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>JdFacadeService</code>类提供简明一致的接口处理,隐藏子系统的复杂性,主要封装数据库操作及接口API调用的部分
 * 逻辑代码
 *
 * @author <a href="mailto:wangjunneil@gmail.com">jun.wang</a>
 * @see com.wangjunneil.schedule.service.jd.JdInnerService
 * @see com.wangjunneil.schedule.service.jd.JdApiService
 */
@Service
public class JdFacadeService {

    private static Logger log = Logger.getLogger(JdFacadeService.class.getName());

    @Autowired
    private JdInnerService jdInnerService;

    @Autowired
    private JdApiService jdApiService;

    @Autowired
    private SysInnerService sysInnerService;

    @Autowired
    private JobService jobService;

    //@Autowired
    private QueueMessageProducer messageProducer;

    // --------------------------------------------------------------------------------------------------- public method

    /**
     * 获取京东商家运营的店铺基本信息
     *
     * @return  JSON格式的错误信息或者店铺基本信息
     */
    public String getOnlineShop() {
        // 从mongodb中获取京东店铺信息
        JdOnlineStore onlineStore = jdInnerService.getOnlineShop();
        Cfg cfg = jdInnerService.getJdCfg();
        onlineStore.setJdScheduleStatus(cfg.getJdScheduleStatus());
        String returnJson = null;
        // 若mongodb没有京东店铺信息则执行接口调用
        if (onlineStore == null) {
            try {
                // 调用店铺信息查询接口
                VenderShopQueryResponse shopQueryResponse = jdApiService.shopQueryRequest();
                ShopJosResult result = shopQueryResponse.getShopJosResult();

                // 组装内部实体对象
                onlineStore = new JdOnlineStore();
                onlineStore.setBrief(result.getBrief());
                onlineStore.setCate_main(result.getCategoryMain());
                onlineStore.setCate_main_name(result.getShopName());
                onlineStore.setLogo_url(result.getLogoUrl());
                onlineStore.setOpen_time(result.getOpenTime());
                onlineStore.setShop_id(result.getShopId());
                onlineStore.setShop_name(result.getShopName());
                onlineStore.setVender_id(result.getVenderId());

                // 调用查询商家基本信息接口以获取商家类型
                SellerVenderInfoGetResponse venderInfoGetResponse = jdApiService.venderInfoRequest();
                int colType = venderInfoGetResponse.getVenderInfoResult().getColType();
                onlineStore.setCol_type(colType);

                // 存储到mongodb中为下次使用
                jdInnerService.addOnlineShop(onlineStore);
                returnJson = JSONObject.toJSONString(onlineStore);
            } catch (JdException e) {
                log.error(e.toString());
                returnJson = "{\"error\":\"调用京东接口错误\",\"errorMessage\":\"" + e.toString() + "\"}";
            }
        }

        returnJson = JSONObject.toJSONString(onlineStore);
        return returnJson;
    } // end method getOnlineShop

    public String getCrmMember(JdMemberRequest JdMemberRequest, Page<JdCrmMember> page) {
        Page<JdCrmMember> returnPage = jdInnerService.getCrmMember(JdMemberRequest, page);
        return JSONObject.toJSONString(returnPage);
    } // end method getOnlineShop

    /**
     * 京东订单出库服务
     *
     * @param request 订单出库请求对象
     * @return JSON格式的错误信息或者出库结果
     */
    public String orderOutStock(OrderSopOutstorageRequest request) throws JdException {
        String returnJson = null;
        OrderSopOutstorageResponse response = jdApiService.orderOutStock(request);
        returnJson = response.getMsg();
        return returnJson;
    } // end method getOnlineShop

    public String getHistoryOrder(JdOrderRequest jdOrderRequest, Page<JdCrmOrder> page) {
        Page<JdCrmOrder> returnPage = jdInnerService.getHistoryOrder(jdOrderRequest, page);
        return JSONObject.toJSONString(returnPage);
    }

    public List<JdCrmOrder> syncOrder() {
        /* 存储等待出库的订单链 */
        List<JdCrmOrder> waitOutStockOrders = new ArrayList<>();
        /* 存储等待出库、确认收货、完成和取消的订单链 */
        List<JdCrmOrder> crmOrders = new ArrayList<>();
        // 调用检索订单接口服务
        jdApiService.orderSearchReuest(crmOrders);
        if (crmOrders == null || crmOrders.size() == 0)
            return null;

        JdCrmOrder jdCrmOrder = null;
        String orderState = null;
        crmOrders.stream().forEach(p -> {
            // 等待出库的订单需要同步到中台
            if ("WAIT_SELLER_STOCK_OUT".equals(p.getOrder_state()))
                waitOutStockOrders.add(p);
        });

        // 更新同步的京东订单到mongodb
        int size = crmOrders.size();
        if (size != 0) {
            jdInnerService.addSyncOrder(crmOrders);
            log.info("the new batch of orders(" + crmOrders.size() + ") store completion");

            // int waitOutStockSize = waitOutStockOrders.size();
            // long waitConfirmSize = crmOrders.stream().filter(p -> p.getOrder_state().equals("WAIT_GOODS_RECEIVE_CONFIRM")).count();
            // long finishSize = crmOrders.stream().filter(p -> p.getOrder_state().equals("FINISHED_L")).count();
            // long cancelSize = crmOrders.stream().filter(p -> p.getOrder_state().equals("TRADE_CANCELED")).count();

            // log.info("");
        }

        return waitOutStockOrders;
    } // end method syncOrder

    public List<JdCrmMember> syncParty(int whenDayBefore) {
        // 调用检索会员接口服务
        CrmMemberSearchResponse memberSearchResponse = jdApiService.memberSearchReuest(whenDayBefore);
        if (memberSearchResponse == null)
            return null;

        CrmMemberResult crmMemberResult = memberSearchResponse.getCrmMemberResult();
        if (crmMemberResult.getTotalResult()==0)
            return null;

        CrmMember[] crmMembers = crmMemberResult.getCrmMembers();
        List<JdCrmMember> list = new ArrayList<>();
        JdCrmMember jdCrmMember = null;
        for (CrmMember crmMember : crmMembers) {
            jdCrmMember = new JdCrmMember();
            jdCrmMember.setCustomer_pin(crmMember.getCustomerPin());
            jdCrmMember.setGrade(crmMember.getGrade());
            jdCrmMember.setTrade_count(crmMember.getTradeCount());
            jdCrmMember.setTrade_amount(crmMember.getTradeAmount());
            jdCrmMember.setClose_trade_count(crmMember.getCloseTradeCount());
            jdCrmMember.setClose_trade_amount(crmMember.getCloseTradeAmount());
            jdCrmMember.setItem_num(crmMember.getItemNum());
            jdCrmMember.setAvg_price(crmMember.getAvgPrice());
            jdCrmMember.setLast_trade_time(crmMember.getLastTradeTime());
            list.add(jdCrmMember);
        }

        // 更新同步的京东会员到mongodb
        int size = list.size();
        if (size != 0) {
            jdInnerService.addSyncParty(list);
            log.info("the new batch of partys(" + list.size() + ") store completion");
        }

        return list;
    } // end method syncParty

    /**
     * 京东授权回调处理,接受京东传入的code,换取有效的token信息
     *
     * @param code  回调code码
     * @param state 续传state
     */
    public void callback(String code, String state) {
        Cfg cfg = sysInnerService.findCfg(Constants.PLATFORM_JD);
        String appKey = cfg.getAppKey();
        String appSecret = cfg.getAppSecret();
        String callbackUrl = cfg.getCallback();

        // 拼接请求地址
        String tokenUrl = MessageFormat.format(Constants.JD_REQUEST_TOKEN_URL, appKey, callbackUrl, code, state, appSecret);
        // Get请求获取token
        String returnJson = HttpUtil.get(tokenUrl);

        // token入库
        JdAccessToken jdAccessToken = JSONObject.parseObject(returnJson, JdAccessToken.class);
        jdAccessToken.setUsername(state);
        jdInnerService.addAccessToken(jdAccessToken);
    }

    public String cleanSchedule(){
        jdInnerService.delJob("syncOrder");
        return null;
    }

    //补单
    public int syncOrderByCond(Cfg cfg, JdOrderRequest orderRequest) throws ScheduleException, JdException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<JdCrmOrder> orders = new ArrayList<>();
        /* 默认从第一页开始同步查询, 递归查询的结果保存在trades中 */
        jdApiService.orderSearchReuestByCond(orderRequest.getStartDate(), orderRequest.getEndDate(), orders);
        if (log.isInfoEnabled())
            log.info("End the order synchronization summary size { " + orders.size() + " }");

        List<JdCrmOrder> crmOrders = jdInnerService.historyOrder(orderRequest);
        if (log.isInfoEnabled())
            log.info("End the order local db query summary size { " + crmOrders.size() + " }");

        List<JdCrmOrder> needSupplementOrders = new ArrayList<>();
        List<JdCrmOrder> needSendMsgList = new ArrayList<>();//统计需要发送的消息列表
        orders.stream().forEach(t -> {
            long count = crmOrders.stream()
                .filter(p -> t.getOrder_id().equals(p.getOrder_id()) && t.getOrder_state().equals(p.getOrder_state()))
                .count();

            if (count == 0) {   // 需补单
                // 判断订单行是否有退款单
                //List<Long> refundIds = supplementRefund(cfg, t.getOrders());

                //对订单状态限制，只向mq发送待发货状态的订单 add by zhangfei 20160929
                if ("WAIT_SELLER_STOCK_OUT".contains(t.getOrder_state())) {
                    List<JdCrmOrder> list = new ArrayList<JdCrmOrder>();//每次只发一条订单
                    list.add(t);
                    String messageJson = JSONObject.toJSONString(list);
//                    log.info("messageJson：" + messageJson);
                    messageProducer.sendJDOrderMessage(messageJson);

                    needSendMsgList.add(t);
                }
                needSupplementOrders.add(t);

                if (log.isInfoEnabled())  {
                    StringBuffer sb = new StringBuffer("the synchronization order [" + t.getOrder_id() + "] ");
                    //if (refundIds.size() != 0)  sb.append("refundIds ").append(refundIds).append(' ');
                    sb.append("supplement finished");
                    log.info(sb.toString());
                    sb = null;
                }
            } else {

            }
        });

        int size = needSupplementOrders.size();
        if (size != 0)
//            jdInnerService.addSyncOrders(needSupplementOrders);
            jdInnerService.addSyncOrder(needSupplementOrders);//防止同步任务异常时，补单重复

        if (log.isInfoEnabled())
            log.info("End supplement order operation, need supplement sum " + size + ", need send msg sum " + needSendMsgList.size() + ", waste time "+ stopWatch.getTotalTimeSeconds() + " second");

//        return size;
        return needSendMsgList.size();//实际发送的消息数
    }
}

package com.wangjunneil.schedule.service.jd;

import com.alibaba.fastjson.JSONObject;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.request.crm.CrmMemberSearchRequest;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.jd.open.api.sdk.request.seller.SellerVenderInfoGetRequest;
import com.jd.open.api.sdk.request.seller.VenderShopQueryRequest;
import com.jd.open.api.sdk.response.crm.CrmMemberSearchResponse;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.jd.open.api.sdk.response.order.OrderSopOutstorageResponse;
import com.jd.open.api.sdk.response.seller.SellerVenderInfoGetResponse;
import com.jd.open.api.sdk.response.seller.VenderShopQueryResponse;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.jd.JdCrmOrder;
import com.wangjunneil.schedule.entity.jd.JdOrderRequest;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Job;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjun on 8/1/16.
 */
@Service
public class JdApiService {

    private static Logger log = Logger.getLogger(JdApiService.class.getName());

    @Autowired
    private JdInnerService jdInnerService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SysFacadeService sysFacadeService;

    public VenderShopQueryResponse shopQueryRequest() throws JdException {
        VenderShopQueryRequest shopQueryRequest = new VenderShopQueryRequest();
        VenderShopQueryResponse shopQueryResponse = getJdClient().execute(shopQueryRequest);
        return shopQueryResponse;
    }

    public SellerVenderInfoGetResponse venderInfoRequest() throws JdException {
        SellerVenderInfoGetRequest venderInfoGetRequest = new SellerVenderInfoGetRequest();
        SellerVenderInfoGetResponse venderInfoGetResponse = getJdClient().execute(venderInfoGetRequest);
        return venderInfoGetResponse;
    }

    public void orderSearchReuest(List<JdCrmOrder> list) {
        String nowTime = DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss");
        String condTime = null;

        Job job = jobService.getJob("syncOrder");
        if (job == null) {
            // 首次同步, 以当前时间向前1天为开始时间, 当前时间为截止时间
            condTime = DateTimeUtil.preDateString("yyyy-MM-dd HH:mm:ss", -1);
        } else {
            // 非首次同步, 上次同步时间为开始时间, 当前时间为截止时间
            Date preExecuteTime = job.getExecuteTime();
//            condTime = DateTimeUtil.dateSetTimeZone(preExecuteTime, "yyyy-MM-dd HH:mm:ss");
            condTime = DateTimeUtil.dateFormat(preExecuteTime, "yyyy-MM-dd HH:mm:ss");//本地入库job时间不需要-8小时
        }
        Date now = new Date();
        job = new Job("syncOrder", now);
        try {
            queryList(condTime, nowTime, list, 1, 0);//处理多页情况
            job.setStatus("success");
        } catch (JdException e) {
            log.error(e.toString());

            job.setStatus("failure");
            job.setMsg(e.getMessage());
            return;
        } finally {
            jobService.addJob(job);
            log.info("job update. " + condTime + " - " + nowTime);
        }
    }

    private void queryList(String startDate, String endDate, List list,int page,int totalSize) throws com.jd.open.api.sdk.JdException {
        OrderSearchRequest request = new OrderSearchRequest();
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setDateType("1");
        request.setOrderState(Constants.JD_SYNC_ORDER_STATE);  // 只同步等待出库的订单
        request.setOptionalFields(Constants.JD_ORDER_OPTIONAL_FIELD);
        // 首次同步时间为一天的数据, 默认认为一天的交易量没有达到100个订单数 目
        // 非首次同步为5分钟的订单量, 默认认为5分钟内不能达到100个订单数目
        request.setPage(String.valueOf(page));
        request.setPageSize("100");
        OrderSearchResponse orderSearchResponse = getJdClient().execute(request);
        String code = orderSearchResponse.getCode();
        if (!"0".equals(code))
            throw new JdException(orderSearchResponse.getEnDesc());
        int pageNum = 1;
        if (totalSize == 0) {
            totalSize = orderSearchResponse.getOrderInfoResult().getOrderTotal();
        }
        List<OrderSearchInfo> tempList = orderSearchResponse.getOrderInfoResult().getOrderInfoList();
        tempList.stream().forEach(p -> {
                JdCrmOrder jdCrmOrder = new JdCrmOrder();
                jdCrmOrder.setOrder_id(p.getOrderId());
                jdCrmOrder.setOrder_source(p.getOrderSource());
                jdCrmOrder.setVender_id(p.getVenderId());
                jdCrmOrder.setPay_type(p.getPayType());
                jdCrmOrder.setOrder_total_price(p.getOrderTotalPrice());
                jdCrmOrder.setOrder_seller_price(p.getOrderSellerPrice());
                jdCrmOrder.setOrder_payment(p.getOrderPayment());
                jdCrmOrder.setFreight_price(p.getFreightPrice());
                jdCrmOrder.setSeller_discount(p.getSellerDiscount());
                jdCrmOrder.setOrder_state_remark(p.getOrderStateRemark());
                jdCrmOrder.setDelivery_type(p.getDeliveryType());
                jdCrmOrder.setInvoice_info(p.getInvoiceInfo());
                jdCrmOrder.setOrder_remark(p.getOrderRemark());
                jdCrmOrder.setOrder_start_time(DateTimeUtil.addHour(DateTimeUtil.parseDateString(p.getOrderStartTime()), -8));//解决绑定数据库是时区不同，时间多8小时问题
                jdCrmOrder.setOrder_end_time(p.getOrderEndTime());
                jdCrmOrder.setModified(p.getModified());
                jdCrmOrder.setVender_remark(p.getVenderRemark());
                jdCrmOrder.setBalance_used(p.getBalanceUsed());
                jdCrmOrder.setPayment_confirm_time(p.getPaymentConfirmTime());
                jdCrmOrder.setWaybill(p.getWaybill());
                jdCrmOrder.setLogistics_id(p.getLogisticsId());
                jdCrmOrder.setParent_order_id(p.getParentOrderId());
                jdCrmOrder.setPin(p.getPin());
                jdCrmOrder.setReturn_order(p.getReturnOrder());
                jdCrmOrder.setConsignee_info(p.getConsigneeInfo());
                jdCrmOrder.setVat_invoice_info(p.getVatInvoiceInfo());
                jdCrmOrder.setItem_info_list(p.getItemInfoList());
                jdCrmOrder.setCoupon_detail_list(p.getCouponDetailList());
                jdCrmOrder.setOrder_state(p.getOrderState());
                list.add(jdCrmOrder);
        });
        pageNum = totalSize/100;
        if ( totalSize%100 > 0) {
            pageNum += 1;
        }
        if (page < pageNum) {
            queryList(startDate, endDate, list, ++page, totalSize);
        }
    }

    //补单
    public void orderSearchReuestByCond(String startDate, String endDate,List<JdCrmOrder> crmOrders) throws JdException {
        queryList(startDate, endDate, crmOrders, 1, 0);//处理多页情况
    }


    public CrmMemberSearchResponse memberSearchReuest(int whenDayBefore) {
        String nowTime = DateTimeUtil.nowDateString("yyyy-MM-dd");
        String condTime = null;

        Job job = jobService.getJob("syncParty");
        if (job == null) {
            // 首次同步, 以当前时间向前whenDayBefore天为开始时间, 当前时间为截止时间
            condTime = DateTimeUtil.preDateString("yyyy-MM-dd", whenDayBefore);
        } else {
            // 非首次同步, 上次同步时间为开始时间, 当前时间为截止时间
            Date preExecuteTime = job.getExecuteTime();
            condTime = DateTimeUtil.dateFormat(preExecuteTime, "yyyy-MM-dd");
        }

        CrmMemberSearchRequest request=new CrmMemberSearchRequest();
        /*request.setCustomerPin( "jingdong" );
        request.setGrade( "jingdong" );
        request.setMinLastTradeTime( "2012-12-12 12:12:12" );
        request.setMaxLastTradeTime( "2012-12-12 12:12:12" );
        request.setMinTradeCount( 123 );
        request.setMaxTradeCount( 123 );
        request.setAvgPrice( 123 );
        request.setMinTradeAmount( 123 );*/
//        request.setMinLastTradeTime(condTime);
//        request.setMaxLastTradeTime(nowTime);
        request.setCurrentPage(1);
        request.setPageSize(100);

        // TODO 这里暂未考虑超出100条订单的情况
        // 首次同步时间为一天的数据, 默认认为一天的交易量没有达到100个订单数目
        // 非首次同步为5分钟的订单量, 默认认为5分钟内不能达到100个订单数目
        Date now = new Date();
        job = new Job("syncParty", now);
        try {
            CrmMemberSearchResponse crmMemberSearchResponse = getJdClient().execute(request);
            String code = crmMemberSearchResponse.getCode();
            if (!"0".equals(code))
                throw new JdException(crmMemberSearchResponse.getEnDesc());

            job.setStatus("success");
            return crmMemberSearchResponse;
        } catch (JdException e) {
            log.error(e.toString());

            job.setStatus("failure");
            job.setMsg(e.getMessage());

            return null;
        } finally {
            jobService.addJob(job);
            log.info("job update. " + condTime + " ~ " + nowTime);
        }
    }

    public OrderSopOutstorageResponse orderOutStock(OrderSopOutstorageRequest request) throws JdException {
        OrderSopOutstorageResponse outstorageResponse = getJdClient().execute(request);
        String code = outstorageResponse.getCode();
        if (!"0".equals(code))
            throw new JdException(outstorageResponse.getEnDesc());
        return outstorageResponse;
    }


    private JdClient getJdClient() throws JdException {
        JdAccessToken token = jdInnerService.getAccessToken();
        Cfg cfg = jdInnerService.getJdCfg();

        if (token == null) {
            log.error("jd token_info is empty, authorize has not finished.");
            throw new JdException("商户还未进行授权操作");
        }

        String refreshToken = token.getRefresh_token();
        String appKey = cfg.getAppKey();
        String appSecret = cfg.getAppSecret();

        Date now = new Date();
        Date expireDate = token.getExpire_Date();
        if (expireDate.before(now)) {   // 若当前时间小于失效时间则刷新token
            //需要重新授权
            //log.error("重新授权");//TODO
            //throw new JdException("商户还未进行授权操作");
            /*String tokenUrl = MessageFormat.format(Constants.JD_REFRESH_TOKEN_URL, appKey, appSecret, refreshToken);
            String returnJson = HttpUtil.get(tokenUrl);
            JSONObject jsonObject = JSONObject.parseObject(returnJson);
            if (!"0".equals(jsonObject.getString("code"))) {
                log.error(jsonObject.getString("error_description"));
                throw new JdException("重新请求token失败");
            }
            token = JSONObject.parseObject(returnJson, JdAccessToken.class);
            jdInnerService.addAccessToken(token);*/
        }

        String accessToken = token.getAccess_token();
        JdClient jdClient = new DefaultJdClient(Constants.JD_SERVICE_URL, accessToken, appKey, appSecret);
        return jdClient;
    }

}

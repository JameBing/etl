package com.wangjunneil.schedule.service.jd;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.controller.jd.JdController;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Job;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.jd.*;
import com.wangjunneil.schedule.entity.jd.JdCrmOrder;
import com.wangjunneil.schedule.entity.jd.JdOrderRequest;
import com.wangjunneil.schedule.entity.jd.JdCrmOrder;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.String;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Service
public class JdInnerService {

    private static Logger log = Logger.getLogger(JdInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    public JdAccessToken getAccessToken() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_JD));
        JdAccessToken jdAccessToken = mongoTemplate.findOne(query, JdAccessToken.class);
        return jdAccessToken;
    }

    public void addAccessToken(JdAccessToken jdAccessToken) {
        // 计算token到期时间
        long time = Long.parseLong(jdAccessToken.getTime());
        int expire_in = jdAccessToken.getExpires_in();
        Date expireDate = DateTimeUtil.getExpireDate(time, expire_in);
        jdAccessToken.setExpire_Date(expireDate);

        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_JD));
        Update update = new Update()
            .set("access_token", jdAccessToken.getAccess_token())
            .set("expires_in", jdAccessToken.getExpires_in())
            .set("refresh_token", jdAccessToken.getRefresh_token())
            .set("token_type", jdAccessToken.getToken_type())
            .set("time", jdAccessToken.getTime())
            .set("uid", jdAccessToken.getUid())
            .set("user_nick", jdAccessToken.getUser_nick())
            .set("expire_Date", jdAccessToken.getExpire_Date())
            .set("username", jdAccessToken.getUsername());
        mongoTemplate.upsert(query, update, JdAccessToken.class);
    }

    public Cfg getJdCfg() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_JD));
        Cfg cfg = mongoTemplate.findOne(query, Cfg.class);
        return cfg;
    }

    public JdOnlineStore getOnlineShop() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_JD));
        JdOnlineStore jdOnlineStore = mongoTemplate.findOne(query, JdOnlineStore.class);
        return jdOnlineStore;
    }

    public void addOnlineShop(JdOnlineStore jdOnlineStore) {
        mongoTemplate.insert(jdOnlineStore);
    }

    public void addSyncOrders(List<JdCrmOrder> orders) {
        mongoTemplate.insertAll(orders);
    }

    public void addSyncOrder(List<JdCrmOrder> orders) {
        for (JdCrmOrder order : orders) {
            Query query = new Query(Criteria.where("order_id").is(order.getOrder_id()));
            Update update = new Update().set("_class", JdCrmOrder.class.getName())
                .set("order_source", order.getOrder_source())
                .set("customs", order.getCustoms())
                .set("customs_model", order.getCustoms_model())
                .set("vender_id", order.getVender_id())
                .set("pay_type", order.getPay_type())
                .set("order_total_price", order.getOrder_total_price())
                .set("order_seller_price", order.getOrder_seller_price())
                .set("order_payment", order.getOrder_payment())
                .set("freight_price", order.getFreight_price())
                .set("seller_discount", order.getSeller_discount())
                .set("order_state", order.getOrder_state())
                .set("delivery_type", order.getDelivery_type())
                .set("invoice_info", order.getInvoice_info())
                .set("order_remark", order.getOrder_remark())
                .set("order_start_time", order.getOrder_start_time())
                .set("payment_confirm_time", order.getPayment_confirm_time())
                .set("order_end_time", order.getOrder_end_time())
                .set("modified", order.getModified())
                .set("consignee_info", order.getConsignee_info())
                .set("item_info_list", order.getItem_info_list())
                .set("coupon_detail_list", order.getCoupon_detail_list());
            mongoTemplate.upsert(query, update, JdCrmOrder.class);
        }
    }

    public void addSyncParty(List<JdCrmMember> list) {
        for (JdCrmMember member : list) {
            Query query = new Query(Criteria.where("customer_pin").is(member.getCustomer_pin()));
            Update update = new Update().set("_class", JdCrmMember.class.getName())
                .set("grade", member.getGrade())
                .set("trade_count", member.getTrade_count())
                .set("trade_amount", member.getTrade_amount())
                .set("close_trade_count", member.getClose_trade_count())
                .set("close_trade_amount", member.getClose_trade_amount())
                .set("item_num", member.getItem_num())
                .set("avg_price", member.getAvg_price())
                .set("last_trade_time", member.getLast_trade_time());
            mongoTemplate.upsert(query, update, JdCrmMember.class);
        }
    }

    public Page<JdCrmOrder> getHistoryOrder(JdOrderRequest jdOrderRequest, Page<JdCrmOrder> page) {
        Criteria criatira = new Criteria();
        criatira.where("1").is("1");
        String orderId = jdOrderRequest.getOrderId();
        if (orderId != null && !"".equals(orderId))
            criatira.and("order_id").is(orderId);
        String orderState = jdOrderRequest.getOrderState();
        if (orderState != null && !"".equals(orderState))
            criatira.and("order_state").is(orderState);
        String telephone = jdOrderRequest.getTelephone();
        if (telephone != null && !"".equals(telephone))
            criatira.and("consignee_info.telephone").is(telephone);
        String startDate = jdOrderRequest.getStartDate();
        String endDate = jdOrderRequest.getEndDate();
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate))
            criatira.and("order_start_time")
                .gte(DateTimeUtil.formatDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
                .lt(DateTimeUtil.formatDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

        Criteria criatiraSub = new Criteria();
        criatiraSub.where("1").is("1");
        String productName = jdOrderRequest.getProductName();
        if (productName != null && !"".equals(productName))
            criatiraSub.and("skuName").regex(".*?" + productName + ".*");
            //criatira.and("item_info_list").elemMatch(Criteria.where("skuName").regex(".*?" + productName + ".*"));
        String skuId = jdOrderRequest.getSkuId();
        if (skuId != null && !"".equals(skuId))
            criatiraSub.and("skuId").is(skuId);
            //criatira.and("item_info_list").elemMatch(Criteria.where("skuId").is(skuId));
        criatira.and("item_info_list").elemMatch(criatiraSub);
        // 查询条件定义
        Query query = new Query(criatira).limit(page.getPageSize()).skip((page.getCurrentPage() - 1) * page.getPageSize()).with(new Sort(Sort.Direction.DESC, "modified"));
        // 计算总记录数
        long count = mongoTemplate.count(query, JdCrmOrder.class);
        page.setTotalNum(count);

        List<JdCrmOrder> orders = mongoTemplate.find(query, JdCrmOrder.class);
        page.setPageDataList(orders);

        return page;
    }

    public List<JdCrmOrder> historyOrder(JdOrderRequest orderRequest) {
        Criteria criatira = new Criteria();
        criatira.and("order_start_time")
            .gte(DateTimeUtil.parseDateString(orderRequest.getStartDate()))
            .lt(DateTimeUtil.parseDateString(orderRequest.getEndDate()));
        return mongoTemplate.find(new Query(criatira), JdCrmOrder.class);
    }

    public Page<JdCrmMember> getCrmMember(JdMemberRequest JdMemberRequest, Page<JdCrmMember> page) {
        Criteria criatira = new Criteria();
        /*criatira.where("1").is("1");
         String customerPin = JdMemberRequest.getCustomerPin();
        if (customerPin != null && !"".equals(customerPin))
            criatira.and("customer_pin").is(customerPin);
        String grade = JdMemberRequest.getGrade();
        if (grade != null && !"".equals(grade)){
            JSONArray jsonArray = (JSONArray) JSONArray.parse(grade);
            criatira.and("grade").in(jsonArray);
        }
        String minTradeTime = JdMemberRequest.getMinTradeTime();
        String maxTradeTime = JdMemberRequest.getMaxTradeTime();
        if (minTradeTime != null && !"".equals(minTradeTime) && maxTradeTime != null && !"".equals(maxTradeTime))
            criatira.and("last_trade_time")
                .gte(DateTimeUtil.formatDateString(minTradeTime, "yyyy-MM-dd HH:mm:ss"))
                .lt(DateTimeUtil.formatDateString(maxTradeTime, "yyyy-MM-dd HH:mm:ss"));
        String minTradeCount = JdMemberRequest.getMinTradeCount();
        String maxTradeCount = JdMemberRequest.getMaxTradeCount();
        if (minTradeCount != null && !"".equals(minTradeCount) && maxTradeCount != null && !"".equals(maxTradeCount))
            criatira.and("trade_count")
                .gte(minTradeCount)
                .lt(maxTradeCount);
        String avePrice = JdMemberRequest.getAvgPrice();
        if (avePrice != null && !"".equals(avePrice))
            criatira.and("avg_price").gte(avePrice);
        String minTradeAmount  = JdMemberRequest.getMinTradeAmount();
        if (minTradeAmount != null && !"".equals(minTradeAmount))
            criatira.and("trade_amount").gte(minTradeAmount);*/
        // 查询条件定义
        Query query = new Query(criatira).limit(page.getPageSize()).skip((page.getCurrentPage() - 1) * page.getPageSize());
        // 计算总记录数
        long count = mongoTemplate.count(query, JdCrmMember.class);
        page.setTotalNum(count);

        List<JdCrmMember> orders = mongoTemplate.find(query, JdCrmMember.class);
        page.setPageDataList(orders);

        return page;
    }

    public void delJob(String oprType){
        Query query = new Query(Criteria.where("oprType").is(oprType));
        mongoTemplate.remove(query,Job.class);
    }
}

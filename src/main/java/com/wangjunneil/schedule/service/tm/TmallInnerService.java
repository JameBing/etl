package com.wangjunneil.schedule.service.tm;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.tm.*;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Service
public class TmallInnerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addAccessToken(TmallAccessToken tmallAccessToken) {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_TM));
        Update update = new Update()
            .set("access_token", tmallAccessToken.getAccess_token())
            .set("token_type", tmallAccessToken.getAccess_token())
            .set("expires_in", tmallAccessToken.getExpires_in())
            .set("refresh_token", tmallAccessToken.getRefresh_token())
            .set("re_expires_in", tmallAccessToken.getRe_expires_in())
            .set("r1_expires_in", tmallAccessToken.getR1_expires_in())
            .set("r2_expires_in", tmallAccessToken.getR2_expires_in())
            .set("w1_expires_in", tmallAccessToken.getW1_expires_in())
            .set("w2_expires_in", tmallAccessToken.getW2_expires_in())
            .set("taobao_user_nick", tmallAccessToken.getTaobao_user_nick())
            .set("taobao_user_id", tmallAccessToken.getTaobao_user_id())
            .set("sub_taobao_user_id", tmallAccessToken.getSub_taobao_user_id())
            .set("sub_taobao_user_nick", tmallAccessToken.getSub_taobao_user_nick());
        mongoTemplate.upsert(query, update, TmallAccessToken.class);
    }

    public void addOrderMessage(TmallOrderMessage message) {
//        Query query = new Query(Criteria.where("id").is(message.getId()));
//        Update update = new Update()
//            .set("id", message.getId())
//            .set("userId", message.getUserId())
//            .set("topic", message.getTopic())
//            .set("pubAppKey", message.getPubAppKey())
//            .set("pubTime", message.getPubTime())
//            .set("outgoingTime", message.getOutgoingTime())
//            .set("userNick", message.getUserNick())
//            .set("content", message.getContent())
//            .set("status", message.getStatus());
        mongoTemplate.insert(message);
    }

//    public TmallOrderMessage findLastOrderMessage() {
//        Query query = new Query().with(new Sort(Sort.Direction.DESC, "outgoingTime")).limit(1);
//        TmallOrderMessage message = mongoTemplate.findOne(query, TmallOrderMessage.class);
//        return message;
//    }

    public void addSyncOrders(List<TmallCrmOrder> orders) {
        mongoTemplate.insertAll(orders);
    }

    public void addSyncOrder(TmallCrmOrder tmallCrmOrder) {
        Query query = new Query(Criteria.where("tid").is(tmallCrmOrder.getTid()));
        Update update = new Update()
            .set("tid", tmallCrmOrder.getTid())
            .set("payment", tmallCrmOrder.getPayment())
            .set("post_fee", tmallCrmOrder.getPost_fee())
            .set("status", tmallCrmOrder.getStatus())
            .set("buyer_message", tmallCrmOrder.getBuyer_message())
            .set("has_buyer_message", tmallCrmOrder.getHas_buyer_message())
            .set("receiver_name", tmallCrmOrder.getReceiver_name())
            .set("receiver_country", tmallCrmOrder.getReceiver_country())
            .set("receiver_state", tmallCrmOrder.getReceiver_state())
            .set("receiver_city", tmallCrmOrder.getReceiver_city())
            .set("receiver_district", tmallCrmOrder.getReceiver_district())
            .set("receiver_town", tmallCrmOrder.getReceiver_town())
            .set("receiver_address", tmallCrmOrder.getReceiver_address())
            .set("receiver_mobile", tmallCrmOrder.getReceiver_mobile())
            .set("receiver_phone", tmallCrmOrder.getReceiver_phone())
            .set("receiver_zip", tmallCrmOrder.getReceiver_zip())
            .set("seller_nick", tmallCrmOrder.getSeller_nick())
            .set("pic_path", tmallCrmOrder.getPic_path())
            .set("seller_rate", tmallCrmOrder.getSeller_rate())
            .set("consign_time", tmallCrmOrder.getConsign_time())
            .set("received_payment", tmallCrmOrder.getReceived_payment())
            .set("order_tax_fee", tmallCrmOrder.getOrder_tax_fee())
            .set("shop_pick", tmallCrmOrder.getShop_pick())
            .set("num", tmallCrmOrder.getNum())
            .set("num_iid", tmallCrmOrder.getNum_iid())
            .set("title", tmallCrmOrder.getTitle())
            .set("type", tmallCrmOrder.getType())
            .set("price", tmallCrmOrder.getPrice())
            .set("discount_fee", tmallCrmOrder.getDiscount_fee())
            .set("total_fee", tmallCrmOrder.getTotal_fee())
            .set("created", tmallCrmOrder.getCreated())
            .set("pay_time", tmallCrmOrder.getPay_time())
            .set("modified", tmallCrmOrder.getModified())
            .set("end_time", tmallCrmOrder.getEnd_time())
            .set("seller_flag", tmallCrmOrder.getSeller_flag())
            .set("buyer_nick", tmallCrmOrder.getBuyer_nick())
            .set("credit_card_fee", tmallCrmOrder.getCredit_card_fee())
            .set("step_trade_status", tmallCrmOrder.getStep_trade_status())
            .set("step_paid_fee", tmallCrmOrder.getStep_paid_fee())
            .set("mark_desc", tmallCrmOrder.getMark_desc())
            .set("shipping_type", tmallCrmOrder.getShipping_type())
            .set("adjust_fee", tmallCrmOrder.getAdjust_fee())
            .set("trade_from", tmallCrmOrder.getTrade_from())
            .set("buyer_rate", tmallCrmOrder.getBuyer_rate())
            .set("rx_audit_status", tmallCrmOrder.getRx_audit_status())
            .set("post_gate_declare", tmallCrmOrder.getPost_gate_declare())
            .set("cross_bonded_declare", tmallCrmOrder.getCross_bonded_declare())
            .set("orders", tmallCrmOrder.getOrders())
            .set("promotion_details", tmallCrmOrder.getPromotion_details())
            .set("service_orders", tmallCrmOrder.getService_orders())
            .set("service_tags", tmallCrmOrder.getService_tags())
            .set("refund_id", tmallCrmOrder.getRefund_id());

        mongoTemplate.upsert(query, update, TmallCrmOrder.class);
    }

    public Page<TmallCrmOrder> getHistoryOrder(TmallOrderRequest tmallOrderRequest, Page<TmallCrmOrder> page) {
        Criteria criatira = new Criteria();
        criatira.where("1").is("1");
        String tId = tmallOrderRequest.gettId();
        if (tId != null && !"".equals(tId))
            criatira.and("tid").is(Long.valueOf(tId));
        String orderState = tmallOrderRequest.getOrderState();
        if (orderState != null && !"".equals(orderState))
            criatira.and("status").is(orderState);
        String telephone = tmallOrderRequest.getTelephone();
        if (telephone != null && !"".equals(telephone))
            criatira.and("receiver_mobile").is(telephone);
        String startDate = tmallOrderRequest.getStartDate();
        String endDate = tmallOrderRequest.getEndDate();
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate))
            criatira.and("created")
                .gte(DateTimeUtil.formatDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
                .lt(DateTimeUtil.formatDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

        Criteria criatiraSub = new Criteria();
        criatiraSub.where("1").is("1");
        String productName = tmallOrderRequest.getProductName();
        if (productName != null && !"".equals(productName))
            criatiraSub.and("title").regex(".*?" + productName + ".*");
        String skuId = tmallOrderRequest.getSkuId();
        if (skuId != null && !"".equals(skuId))
            criatiraSub.and("skuId").is(skuId);
        criatira.and("orders").elemMatch(criatiraSub);
        // 查询条件定义
        Query query = new Query(criatira).limit(page.getPageSize()).skip((page.getCurrentPage() - 1) * page.getPageSize())
            .with(new Sort(Sort.Direction.DESC, "modified"));
        // 计算总记录数
        long count = mongoTemplate.count(query, TmallCrmOrder.class);
        page.setTotalNum(count);

        List<TmallCrmOrder> orders = mongoTemplate.find(query, TmallCrmOrder.class);
        page.setPageDataList(orders);

        return page;
    }

    public List<TmallCrmOrder> historyOrder(TmallOrderRequest orderRequest) {
        Criteria criatira = new Criteria();
        criatira.and("created")
            .gte(DateTimeUtil.parseDateString(orderRequest.getStartDate()))
            .lt(DateTimeUtil.parseDateString(orderRequest.getEndDate()));
        return mongoTemplate.find(new Query(criatira), TmallCrmOrder.class);
    }

    public Page<TmallCrmRefund> getRefundOrder(Map<String,String> paramMap, Page<TmallCrmRefund> page) {
        Criteria criatira = new Criteria();
        criatira.where("1").is("1");
        String tId = paramMap.get("tId");
        if (tId != null && !"".equals(tId)) {
            criatira.and("tid").is(Long.valueOf(tId));
        }
        String refundId = paramMap.get("refundId");
        if (refundId != null && !"".equals(refundId) && !"all".equals(refundId)) {
            criatira.and("refund_id").is(Long.valueOf(refundId));
        }
        String status = paramMap.get("status");
        if (status != null && !"".equals(status)) {
            criatira.and("status").is(status);
        }
        String title = paramMap.get("title");
        if (title != null && !"".equals(title)) {
            criatira.and("title").is(title);
        }
        String sid = paramMap.get("sid");
        if (sid != null && !"".equals(sid)) {
            criatira.and("sid").is(Long.valueOf(sid));
        }
        String startDate = paramMap.get("startDate");
        String endDate = paramMap.get("endDate");
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate))
            criatira.and("created")
                .gte(DateTimeUtil.formatDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
                .lt(DateTimeUtil.formatDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

        Criteria criatiraSub = new Criteria();
        criatiraSub.where("1").is("1");
        // 查询条件定义
        Query query = new Query(criatira).limit(page.getPageSize()).skip((page.getCurrentPage() - 1) * page.getPageSize()).with(new Sort(Sort.Direction.DESC, "modified"));
        // 计算总记录数
        long count = mongoTemplate.count(query, TmallCrmRefund.class);
        page.setTotalNum(count);

        List<TmallCrmRefund> orders = mongoTemplate.find(query, TmallCrmRefund.class);
        page.setPageDataList(orders);

        return page;
    }

    public TmallCrmRefund getRefundOrderById(String refundId) {
        Query query = new Query(Criteria.where("refund_id").is(Long.valueOf(refundId)));
        TmallCrmRefund crmRefund = mongoTemplate.findOne(query, TmallCrmRefund.class);
        return crmRefund;
    }

    public void addRefundOrder(TmallCrmRefund refund){
        mongoTemplate.insert(refund);
    }

    public void addSyncRefundOrder(TmallCrmRefund refund) {
        Query query = new Query(Criteria.where("refund_id").is(refund.getRefund_id()));
        Update update = new Update()
            .set("tid", refund.getTid())
            .set("oid", refund.getOid())
            .set("refund_id", refund.getRefund_id())
            .set("cs_status", refund.getCs_status())
            .set("advance_status", refund.getAdvance_status())
            .set("shipping_type", refund.getShipping_type())
            .set("split_taobao_fee", refund.getSplit_taobao_fee())
            .set("split_seller_fee", refund.getSplit_seller_fee())
            .set("alipay_no", refund.getAlipay_no())
            .set("total_fee", refund.getTotal_fee())
            .set("buyer_nick", refund.getBuyer_nick())
            .set("seller_nick", refund.getSeller_nick())
            .set("created", refund.getCreated())
            .set("modified", refund.getModified())
            .set("order_status", refund.getOrder_status())
            .set("status", refund.getStatus())
            .set("good_status", refund.getGood_status())
            .set("has_good_return", refund.getHas_good_return())
            .set("refund_fee", refund.getRefund_fee())
            .set("payment", refund.getPayment())
            .set("reason", refund.getReason())
            .set("desc", refund.getDesc())
            .set("title", refund.getTitle())
            .set("price", refund.getPrice())
            .set("num", refund.getNum())
            .set("good_return_time", refund.getGood_return_time())
            .set("company_name", refund.getCompany_name())
            .set("sid", refund.getSid())
            .set("address", refund.getAddress())
            .set("refund_remind_timeout", refund.getRefund_remind_timeout())
            .set("num_iid", refund.getNum_iid())
            .set("refund_phase", refund.getRefund_phase())
            .set("refund_version", refund.getRefund_version())
            .set("sku", refund.getSku())
            .set("attribute", refund.getAttribute())
            .set("outer_id", refund.getOuter_id())
            .set("operation_contraint", refund.getOperation_contraint());
        mongoTemplate.upsert(query, update, TmallCrmRefund.class);
    }

    public Cfg getTmCfg(){
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_TM));
        return mongoTemplate.findOne(query, Cfg.class);
    }

}

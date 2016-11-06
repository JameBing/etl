package com.wangjunneil.schedule.service.z8;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.z8.Z8AccessToken;
import com.wangjunneil.schedule.entity.z8.Z8CrmOrder;
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
public class Z8InnerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addAccessToken(Z8AccessToken z8AccessToken) {
        Query query = new Query(Criteria.where("platform").is("z8"));
        Update update = new Update()
            .set("access_token", z8AccessToken.getAccess_token())
            .set("token_type", z8AccessToken.getAccess_token())
            .set("expires_in", z8AccessToken.getExpires_in())
            .set("refresh_token", z8AccessToken.getRefresh_token())
            .set("re_expires_in", z8AccessToken.getRe_expires_in())
            .set("user_id", z8AccessToken.getUser_id())
            .set("user_nick", z8AccessToken.getUser_nick());
        mongoTemplate.upsert(query, update, Z8AccessToken.class);
    }

    public Z8AccessToken getAccessToken() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_Z800));
        Z8AccessToken z8AccessToken = mongoTemplate.findOne(query, Z8AccessToken.class);
        return z8AccessToken;
    }

    public Cfg getZ8Cfg() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_Z800));
        Cfg cfg = mongoTemplate.findOne(query, Cfg.class);
        return cfg;
    }

    public void addSyncOrder(List<Z8CrmOrder> orders) {
        for (Z8CrmOrder order : orders) {
            Query query = new Query(Criteria.where("id").is(order.getId()));
            Update update = new Update().set("_class", Z8CrmOrder.class.getName())
                .set("id", order.getId())
                .set("url", order.getUrl())
                .set("seller_id", order.getSeller_id())
                .set("seller_nickname", order.getSeller_nickname())
                .set("order_price", order.getOrder_price())
                .set("goods_price", order.getGoods_price())
                .set("discount_price", order.getDiscount_price())
                .set("postage", order.getPostage())
                .set("oos", order.getOos())
                .set("status", order.getStatus())
                .set("deliver_time_limit", order.getDeliver_time_limit())
                .set("seller_comment", order.getSeller_comment())
                .set("buyer_comment", order.getBuyer_comment())
                .set("created_at", order.getCreated_at())
                .set("close_time", order.getClose_time())
                .set("close_reason", order.getClose_time())
                .set("updated_at", order.getUpdated_at())
                .set("express_time", order.getExpress_time())
                .set("pay_time", order.getPay_time())
                .set("express_no", order.getExpress_no())
                .set("express_company", order.getExpress_company())
                .set("nickname", order.getNickname())
                .set("seller_comment_type", order.getSeller_comment_type())
                .set("invoice", order.getInvoice())
                .set("address", order.getAddress())
                .set("products", order.getProducts());
            mongoTemplate.upsert(query, update, Z8CrmOrder.class);
        }
    }

    public Page<Z8CrmOrder> getHistoryOrder(Map<String,String> paramMap, Page<Z8CrmOrder> page) {
        Criteria criatira = new Criteria();
        criatira.where("1").is("1");

        String orderId = paramMap.get("orderId");
        if (orderId != null && !"".equals(orderId) && !"all".equals(orderId)) {
            criatira.and("id").is(Long.valueOf(orderId));
        }
        String telephone = paramMap.get("telephone");
        if (telephone != null && !"".equals(telephone)) {
            criatira.and("address.phone").is(telephone);
        }
        String orderState = paramMap.get("orderState");
        if (orderState != null && !"".equals(orderState)) {
            criatira.and("status").is(orderState);
        }

        String startDate = paramMap.get("startDate");
        String endDate = paramMap.get("endDate");
        if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate))
            criatira.and("created_at")
                .gte(DateTimeUtil.formatDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
                .lt(DateTimeUtil.formatDateString(endDate, "yyyy-MM-dd HH:mm:ss"));

        Criteria criatiraSub1 = new Criteria();
        criatiraSub1.where("1").is("1");
        String skuId = paramMap.get("skuId");
        if (skuId != null && !"".equals(skuId)) {
            criatira.and("products.sku_id").is(skuId);
        }
        String productName = paramMap.get("productName");
        if (productName != null && !"".equals(productName)) {
            criatiraSub1.and("name").is(productName);
        }
        criatira.and("products").elemMatch(criatiraSub1);

        // 查询条件定义
        Query query = new Query(criatira).limit(page.getPageSize()).skip((page.getCurrentPage() - 1) * page.getPageSize()).with(new Sort(Sort.Direction.DESC, "modified"));
        // 计算总记录数
        long count = mongoTemplate.count(query, Z8CrmOrder.class);
        page.setTotalNum(count);

        List<Z8CrmOrder> orders = mongoTemplate.find(query, Z8CrmOrder.class);
        page.setPageDataList(orders);

        return page;
    }

    public List<Z8CrmOrder> historyOrder(Map<String, String> paramMap) {
        Criteria criatira = new Criteria();
        criatira.and("created_at")
            .gte(DateTimeUtil.parseDateString(paramMap.get("startDate")))
            .lt(DateTimeUtil.parseDateString(paramMap.get("endDate")));
        return mongoTemplate.find(new Query(criatira), Z8CrmOrder.class);
    }

    public void addSyncOrders(List<Z8CrmOrder> orders) {
        mongoTemplate.insertAll(orders);
    }

}

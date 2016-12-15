package com.wangjunneil.schedule.service.meituan;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.jdhome.OrderInfoDTO;
import com.wangjunneil.schedule.entity.meituan.OrderInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liuxin
 * @since 2016-11-17.
 */
@Service
public class MeiTuanInnerService {

    private static Logger log = Logger.getLogger(MeiTuanInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    //把获取到的订单信息插入到sync.meituan.order
    public void insertAllOrder(OrderInfo orders)
    {
        mongoTemplate.insert(orders);
    }


    //修改订单状态
    public void updateStatus(String orderId,int status){
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_MEITUAN).where("platformOrderId").is(orderId));
        Update update = new Update().set("order.status",status);
        mongoTemplate.upsert(query,update, OrderWaiMai.class);
    }
}

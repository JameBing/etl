package com.wangjunneil.schedule.service.mt;

import com.wangjunneil.schedule.entity.mt.OrderInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuxin
 * @since 2016-11-17.
 */
@Service
public class MtInnerService {

    private static Logger log = Logger.getLogger(MtInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    //把获取到的订单信息插入到sync.mt.order
    public void insertAllOrder(OrderInfo orders)
    {
        mongoTemplate.insert(orders);
    }


}

package com.wangjunneil.schedule.service.meituan;

import com.wangjunneil.schedule.entity.mt.OrderInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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


}

package com.wangjunneil.schedule.service.baidu;

import com.wangjunneil.schedule.entity.baidu.Order;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by yangwanbin on 2016-11-15.
 */
@Service
public class BaiDuInnerService {

    private static Logger log = Logger.getLogger(BaiDuInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    public void AddSyncBaiDuOrder(Order order){


    }

}

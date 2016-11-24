package com.wangjunneil.schedule.service.eleme;

import com.wangjunneil.schedule.entity.eleme.Order;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/11/21.
 */
@Service
public class EleMeInnerService {
    private static Logger log = Logger.getLogger(EleMeInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    public void AddSyncBaiDuOrder(Order order){


    }
}

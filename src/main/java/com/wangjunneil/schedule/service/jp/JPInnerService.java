package com.wangjunneil.schedule.service.jp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


/**
 *
 * Created by wangjun on 7/28/16.
 */
@Service
public class JPInnerService {

    @Autowired
    private MongoTemplate mongoTemplate;


}

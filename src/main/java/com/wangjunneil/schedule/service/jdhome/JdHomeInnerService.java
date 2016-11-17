package com.wangjunneil.schedule.service.jdhome;

import com.wangjunneil.schedule.entity.jdhome.OrderInfoDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
@Service
public class JdHomeInnerService {
    private static Logger log = Logger.getLogger(JdHomeInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addSyncOrders(List<OrderInfoDTO> orders) {
        mongoTemplate.insertAll(orders);
    }
}

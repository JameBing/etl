package com.wangjunneil.schedule.service.baidu;

import com.wangjunneil.schedule.common.BaiDuException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.baidu.Body;
import com.wangjunneil.schedule.entity.baidu.Order;
import com.wangjunneil.schedule.entity.baidu.Shop;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.*;

/**
 * Created by yangwanbin on 2016-11-15.
 */
@Service
public class BaiDuInnerService {

    private static Logger log = Logger.getLogger(BaiDuInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    //insert or update
    public void updSyncBaiDuOrder(Body body) throws ScheduleException{

        Query  query = new Query(Criteria.where("order_id").is(body.getOrder().getOrderId()));
        Update update = new Update().set("shop",body.getShop())
                                    .set("order",body.getOrder())
                                    .set("user",body.getUser())
                                    .set("products",body.getProducts())
                                    .set("discount",body.getDiscount());
        mongoTemplate.upsert(query, update, Body.class);
    }

    //批量更新订单状态(根据订单号)
    public  int updSyncBaiDuOrderStastus(String ids,int status){

        Query query = new Query();
        Criteria criteria = new Criteria();
        List<String> listIds = new ArrayList<String>();
        Collections.addAll(listIds,ids.split(","));
        listIds.forEach((id)->{
            criteria.orOperator(new Criteria().where("order_id").is(id));
        });
        query.addCriteria(criteria);
        Update update = new Update().set("order.$.status",status);
       return mongoTemplate.updateMulti(query,update,Body.class).getN();
    }

    //多条件查询（完全匹配）
    public List<Body> findBodies(Map<String,Object[]> map) throws ScheduleException{

        Query query = new Query();
        Criteria criteria = new Criteria();

            map.forEach((s1, s2) -> {
                criteria.andOperator(new Criteria().where(s1).in(s2));
            });

        query.addCriteria(criteria);
        List<Body> bodies = mongoTemplate.find(query,Body.class);
        return  bodies;
    }

}

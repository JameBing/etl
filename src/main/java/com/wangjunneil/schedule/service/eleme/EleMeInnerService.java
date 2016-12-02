package com.wangjunneil.schedule.service.eleme;

import com.google.gson.Gson;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.eleme.Order;
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
import java.util.Map;

/**
 * Created by admin on 2016/11/21.
 */
@Service
public class EleMeInnerService {
    private static Logger log = Logger.getLogger(EleMeInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    //insert or update
    public void updSyncElemeOrder(Order data) throws ScheduleException{
        Query  query = new Query(Criteria.where("order_id").is(data.getOrderid()));
        Update update = new Update().set("order",data.getOrderid());
        mongoTemplate.upsert(query, update, Order.class);
    }

    //批量更新订单状态(根据订单号)
    public int updSyncElemeOrderStastus(String ids,int status){
        Query query = new Query();
        Criteria criteria = new Criteria();
        List<String> listIds = new ArrayList<String>();
        Collections.addAll(listIds, ids.split(","));
        listIds.forEach((id)->{
            criteria.orOperator(new Criteria().where("order_id").is(id));
        });
        query.addCriteria(criteria);
        Update update = new Update().set("order.$.status",status);
        return mongoTemplate.updateMulti(query,update,Order.class).getN();
    }

    //多条件查询（完全匹配）
    public List<Order> findBodies(Map<String,Object[]> map) throws ScheduleException{

        Query query = new Query();
        Criteria criteria = new Criteria();

        map.forEach((s1, s2) -> {
            criteria.andOperator(new Criteria().where(s1).in(s2));
        });

        query.addCriteria(criteria);
        List<Order> bodies = mongoTemplate.find(query,Order.class);
        return  bodies;
    }

}

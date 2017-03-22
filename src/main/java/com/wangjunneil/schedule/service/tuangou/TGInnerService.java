package com.wangjunneil.schedule.service.tuangou;

import com.wangjunneil.schedule.entity.tuangou.AccessToken;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yangyongbing
 * @since 2017/3/20.
 */
@Service
public class TGInnerService {

    private static Logger log = Logger.getLogger(TGInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    //添加或更新token
    public void addToken(String token,String ePoiId){
        // 计算token到期时间
        String expireDate = DateTimeUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss");
        Query query = new Query(Criteria.where("ePoiId").is(ePoiId));
        Update update = new Update()
            .set("appAuthToken", token)
            .set("ePoiId", ePoiId)
            .set("date",expireDate);
        mongoTemplate.upsert(query,update, AccessToken.class);
    }

    //获取token
    public AccessToken findToken(String ePoiId){
        Query query = new Query(Criteria.where("ePoiId").is(ePoiId));
        AccessToken token = mongoTemplate.findOne(query, AccessToken.class);
        return token;
    }

}

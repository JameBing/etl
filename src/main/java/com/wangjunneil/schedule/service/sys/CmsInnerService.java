package com.wangjunneil.schedule.service.sys;

import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by wangjun on 8/8/16.
 */
@Service
public class CmsInnerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addUser(User user) throws ScheduleException {
        String username = user.getUsername();

        User tempUser = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), User.class);
        if (tempUser != null)
            throw new ScheduleException("user [" + username + "] already exist");

        mongoTemplate.insert(user);
    }

    public User findUser(User user) {
        Criteria criatira = new Criteria();
        String username = user.getUsername();
        if (username != null && !"".equals(username))
            criatira.and("username").is(username);

        Query query = new Query(criatira);
        return mongoTemplate.findOne(query, User.class);
    }

    public User login(User user) {
        Criteria criatira = new Criteria();
        criatira.andOperator(Criteria.where("username").is(user.getUsername()), Criteria.where("password").is(user.getPassword()));
        Query query = new Query(criatira);
        return mongoTemplate.findOne(query, User.class);
    }

    public List<User> findAllUser(){
        Query query = new Query();
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

    public void delUser(User user) throws ScheduleException{
        String username = user.getUsername();
        User tempUser = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),User.class);
        if (tempUser == null){
            throw  new ScheduleException("user [" + username + "] does not exist ");
        }else{
            mongoTemplate.remove(tempUser);
        }
    }


}

package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.sys.User;
import com.wangjunneil.schedule.service.sys.CmsInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangjun on 8/8/16.
 */
@Service
public class CmsFacadeService {

    private static Logger log = Logger.getLogger(CmsFacadeService.class.getName());

    @Autowired
    private CmsInnerService cmsInnerService;

    public User login(User user) {
        return cmsInnerService.findUser(user);
    }

    public String addUser(User user) {
        user.setCreateTime(DateTimeUtil.now());
        user.setUid(System.currentTimeMillis());
        if (user.getExpireIn() != 0) {
            user.setExpireTime(DateTimeUtil.addDayNow(user.getExpireIn()));
        }

        try {
            cmsInnerService.addUser(user);
            return "{\"status\":0}";
        } catch (ScheduleException e) {
            log.error(e.toString());

            return "{\"status\":\"1\", \"errorMsg\":\"" + e.getMessage() + "\"}";
        }
    }

    public User findUser(User user) {
        return cmsInnerService.findUser(user);
    }

    public String findAllUser(){
        List<User> users = cmsInnerService.findAllUser();
        return JSONObject.toJSONString(users);
    }

    public String delUser(User user){
        try {
            cmsInnerService.delUser(user);
            return "{\"status\":0}";
        } catch (ScheduleException e) {
            log.error(e.toString());
            return "{\"status\":\"1\", \"errorMsg\":\"" + e.getMessage() + "\"}";
        }
    }

}

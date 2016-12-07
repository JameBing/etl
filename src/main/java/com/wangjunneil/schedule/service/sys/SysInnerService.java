package com.wangjunneil.schedule.service.sys;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.baidu.Data;
import com.wangjunneil.schedule.entity.common.FlowNum;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.entity.jp.JPAccessToken;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.entity.tm.TmallAccessToken;
import com.wangjunneil.schedule.entity.z8.Z8AccessToken;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.eclipse.jetty.util.security.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by wangjun on 8/8/16.
 */
@Service
public class SysInnerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Cfg findCfg(String platform) {
        Query query = new Query(Criteria.where("platform").is(platform));
        Cfg cfg = mongoTemplate.findOne(query, Cfg.class);
        return cfg;
    }

    public List<Cfg> findAllCfg() {
        return mongoTemplate.findAll(Cfg.class);
    }

    public JdAccessToken getJdToken() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_JD));
        JdAccessToken jdAccessToken = mongoTemplate.findOne(query, JdAccessToken.class);
        return jdAccessToken;
    }

    public TmallAccessToken getTmToken() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_TM));
        TmallAccessToken tmallAccessToken = mongoTemplate.findOne(query, TmallAccessToken.class);
        return tmallAccessToken;
    }

    public Z8AccessToken getZ8Token(){
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_Z800));
        Z8AccessToken z8AccessToken = mongoTemplate.findOne(query, Z8AccessToken.class);
        return z8AccessToken;
    }

    public JPAccessToken getJPToken() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_JP));
        JPAccessToken jpAccessToken = mongoTemplate.findOne(query, JPAccessToken.class);
        return jpAccessToken;
    }

    public void addJPAccessToken(JPAccessToken accessToken) {
        mongoTemplate.insert(accessToken);
    }

    public void addPlatform(Cfg cfg,String editType)throws ScheduleException{
        String platform = cfg.getPlatform();
        if("edit".equals(editType)){
            mongoTemplate.remove(new Query(Criteria.where("platform").is(platform)),Cfg.class);
        }
        Cfg tempCfg = mongoTemplate.findOne(new Query(Criteria.where("platform").is(platform)), Cfg.class);
//        if (tempCfg != null)
//            throw new ScheduleException("cfg [" + tempCfg + "] already exist");
        mongoTemplate.remove(tempCfg);
        mongoTemplate.insert(cfg);
    }
    public void addPlatformStatus(Status status, String platformType, Boolean scheduleState){
        //查询数据库获取当前平台的数据
        Query query = new Query(Criteria.where("platform").is(platformType));
        Cfg cfg = mongoTemplate.findOne(query,Cfg.class);
        //判断京东调度状态获取上一次的开启时间/关闭时间
        if(scheduleState && Constants.PLATFORM_JD.equals(platformType) && cfg.getJdScheduleStatus() != null){
            Date oldShutdownTime = cfg.getJdScheduleStatus().getShutdownTime();
            status.setShutdownTime(oldShutdownTime);
        }else if(Constants.PLATFORM_JD.equals(platformType) && cfg.getJdScheduleStatus() != null){
            Date oldStartupTime = cfg.getJdScheduleStatus().getStartupTime();
            status.setStartupTime(oldStartupTime);
        }

        //判断天猫调度状态获取上一次的开启时间/关闭时间
        if(scheduleState && Constants.PLATFORM_TM.equals(platformType) && cfg.getTmScheduleStatus() != null){
            Date oldShutdownTime = cfg.getTmScheduleStatus().getShutdownTime();
            status.setShutdownTime(oldShutdownTime);
        }else if(Constants.PLATFORM_TM.equals(platformType) && cfg.getTmScheduleStatus() != null){
            Date oldStartupTime = cfg.getTmScheduleStatus().getStartupTime();
            status.setStartupTime(oldStartupTime);
        }

        //判断折800调度状态获取上一次的开启时间/关闭时间
        if(scheduleState && Constants.PLATFORM_Z800.equals(platformType) && cfg.getTmScheduleStatus() != null){
            Date oldShutdownTime = cfg.getTmScheduleStatus().getShutdownTime();
            status.setShutdownTime(oldShutdownTime);
        }else if(Constants.PLATFORM_Z800.equals(platformType) && cfg.getTmScheduleStatus() != null){
            Date oldStartupTime = cfg.getTmScheduleStatus().getStartupTime();
            status.setStartupTime(oldStartupTime);
        }

        Update update = new Update();
        if(Constants.PLATFORM_JD.equals(platformType)){
            update.set("jdScheduleStatus", status);
        }else if(Constants.PLATFORM_TM.equals(platformType)){
            update.set("tmScheduleStatus", status);
        }else if(Constants.PLATFORM_Z800.equals(platformType)){
            update.set("z8ScheduleStatus", status);
        }
        mongoTemplate.upsert(query, update, Cfg.class);

//        mongoTemplate.remove(new Query(Criteria.where("platform").is(platformType)),Cfg.class);
//        if(Constants.PLATFORM_JD.equals(platformType)){
//            cfg.setJdScheduleStatus(status);
//        }else if(Constants.PLATFORM_TM.equals(platformType)){
//            cfg.setTmScheduleStatus(status);
//        }
//        mongoTemplate.insert(cfg);
    }


    public void delPlatform(Cfg cfg) throws ScheduleException{
        String platform = cfg.getPlatform();
        if(Constants.PLATFORM_JD.equals(platform)){
            mongoTemplate.remove(new Query(Criteria.where("platform").is(platform)),Cfg.class);
            mongoTemplate.remove(new Query(Criteria.where("platform").is(platform)),JdAccessToken.class);
        }else if(Constants.PLATFORM_TM.equals(platform)){
            mongoTemplate.remove(new Query(Criteria.where("platform").is(platform)),Cfg.class);
            mongoTemplate.remove(new Query(Criteria.where("platform").is(platform)),TmallAccessToken.class);
        }else{
            mongoTemplate.remove(new Query(Criteria.where("platform").is(platform)),Cfg.class);
        }
    }

    public void initializeReset(Status status){
        Update update_jd = new Update();
        Query query_jd = new Query(Criteria.where("platform").is("jd"));
        Cfg cfg_jd = mongoTemplate.findOne(query_jd,Cfg.class);
        if(cfg_jd != null) {
            Status status_jd = cfg_jd.getJdScheduleStatus();
            if(status_jd != null) {
                status_jd.setStartup(false);
                update_jd.set("jdScheduleStatus", status_jd);
                mongoTemplate.upsert(query_jd, update_jd, Cfg.class);
            }
        }

        Update update_tmall = new Update();
        Query query_tmall = new Query(Criteria.where("platform").is("tmall"));
        Cfg cfg_tmall= mongoTemplate.findOne(query_tmall,Cfg.class);
        if(cfg_tmall != null) {
            Status status_tmall = cfg_tmall.getTmScheduleStatus();
            if(status_tmall != null) {
                status_tmall.setStartup(false);
                update_tmall.set("tmScheduleStatus", status_tmall);
                mongoTemplate.upsert(query_tmall, update_tmall, Cfg.class);
            }
        }

    }

    /*=======================================外卖平台=========================================================*/
    //获取订单流水号
    public int getSerialNum(String date,String module){
        int intRresult = 1,curNo = 0;
        Query query = new Query();
        Criteria criteria = new Criteria().where("date").is(date).where("module").is(module);
        query.addCriteria(criteria);
        List<FlowNum> list = mongoTemplate.find(query, FlowNum.class);
        if (list.size()<1){
           curNo = 1;
        }else {
            intRresult = list.get(0).getFlowNum();
            curNo = intRresult + 1;
        }
        //新增
        Update update = new Update().set("date",DateTimeUtil.nowDateString("yyyyMMdd"))
            .set("module",module)
            .set("flowNum",curNo);
        mongoTemplate.upsert(query,update,FlowNum.class);
        return intRresult;
    }


    //订单插入
    public void updSynWaiMaiOrder(OrderWaiMai orderWaiMai) throws  ScheduleException{
        Query  query = new Query(Criteria.where("platfrom").is(orderWaiMai.getPlatformOrderId()).where("platformOrderId"));
        Update update = new Update()
            .set("platfrom", orderWaiMai.getPlatfrom())
            .set("shopId", orderWaiMai.getShopId())
            .set("orderId",orderWaiMai.getOrderId())
            .set("platformOrderId",orderWaiMai.getPlatformOrderId())
            .set("order",orderWaiMai.getOrder());
        mongoTemplate.upsert(query, update, OrderWaiMai.class);
    }

}

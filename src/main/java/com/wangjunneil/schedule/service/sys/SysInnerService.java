package com.wangjunneil.schedule.service.sys;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.baidu.Data;
import com.wangjunneil.schedule.entity.common.FlowNum;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.OrderWaiMaiHistory;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.entity.jp.JPAccessToken;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.entity.tm.TmallAccessToken;
import com.wangjunneil.schedule.entity.z8.Z8AccessToken;
import com.wangjunneil.schedule.entity.z8.Z8CrmOrder;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.OrderUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.eclipse.jetty.util.security.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    //订单查询
    public  OrderWaiMai findOrderWaiMai(String platform,String platformOrderId){
        Query query = new Query(Criteria.where("platform").is(platform).where("platformOrderId").is(platformOrderId));
        OrderWaiMai orderWaiMai = mongoTemplate.findOne(query,OrderWaiMai.class);
        return  orderWaiMai;
    }

    //订单插入
    public void updSynWaiMaiOrder(OrderWaiMai orderWaiMai) throws ScheduleException{
        Query  query = new Query(Criteria.where("platform").is(orderWaiMai.getPlatformOrderId()).where("platformOrderId").is(orderWaiMai.getPlatformOrderId()));
        Update update = new Update()
            .set("platform", orderWaiMai.getPlatform())
            .set("shopId", orderWaiMai.getShopId())
            .set("sellerShopId",orderWaiMai.getSellerShopId())
            .set("orderId", orderWaiMai.getOrderId())
            .set("platformOrderId",orderWaiMai.getPlatformOrderId())
            .set("order",orderWaiMai.getOrder())
            .set("createTime",orderWaiMai.getCreateTime());
            mongoTemplate.upsert(query, update, OrderWaiMai.class);
    }

    //修改外卖订单
    public void updateWaiMaiOrder(String orderId,Object order){
        Query query = new Query(Criteria.where("platformOrderId").is(orderId).and("platform").is(Constants.PLATFORM_WAIMAI_JDHOME));
        Update update = new Update().set("order", order);
        mongoTemplate.updateFirst(query, update, OrderWaiMai.class).getN();
    }

    //日志插入
    public void updSynLog(Log log){
        Query query = new Query(Criteria.where("logId").is(log.getLogId()));
        Update update = new Update().set("logId",log.getLogId())
                                                                   .set("title",log.getTitle())
                                                                   .set("type",log.getType())
                                                                   .set("platform",log.getPlatform())
                                                                   .set("message",log.getMessage())
                                                                   .set("request",log.getRequest())
                                                                   .set("catchExName",log.getCatchExName())
                                                                   .set("innerExName",log.getInnerExName())
                                                                   .set("stackInfo",log.getStackInfo())
                                                                   .set("dateTime",log.getDateTime());
         mongoTemplate.upsert(query,update,Log.class);
    }

    //查询当日订单
    public Page<OrderWaiMai> getNowDayOrder(Map<String,String> paramMap, Page<OrderWaiMai> page) {
        Criteria criatira = new Criteria();
        criatira.where("1").is("1");

        String sellerId = paramMap.get("sellerId");
        if (sellerId != null && !"".equals(sellerId)) {
            criatira.and("sellerShopId").is(sellerId);
        }

        String shopId = paramMap.get("shopId");
        if (shopId != null && !"".equals(shopId)) {
            criatira.and("shopId").is(shopId);
        }

        String platformOrderId = paramMap.get("platformOrderId");
        if (platformOrderId != null && !"".equals(platformOrderId)) {
            criatira.and("platformOrderId").is(platformOrderId);
        }

        String orderId = paramMap.get("orderId");
        if (orderId != null && !"".equals(orderId) && !"all".equals(orderId)) {
            criatira.and("orderId").is(orderId);
        }
        String orderStatus = paramMap.get("orderStatus");
        if (orderStatus != null && !"".equals(orderStatus)) {
            int baidu = OrderUtil.tranBdOrderStatus(Integer.parseInt(orderStatus));
            int jdhome = OrderUtil.tranJHOrderStatus(Integer.parseInt(orderStatus));
            int meituan = OrderUtil.tranMTOrderStatus(Integer.parseInt(orderStatus));
            int eleme = OrderUtil.tranELOrderStatus(Integer.parseInt(orderStatus));
            criatira.orOperator(Criteria.where("order.order.type").is(String.valueOf(baidu)),
                Criteria.where("order.orderStatus").is(jdhome),
                Criteria.where("order.status").is(meituan),
                Criteria.where("order.statuscode").is(eleme));
        }

        String platform = paramMap.get("platform");
        if (platform != null && !"".equals(platform)) {
            criatira.and("platform").is(platform);
        }

        String receive = paramMap.get("receive");
        if (receive != null && !"".equals(receive)) {
            if("0".equals(receive)){
                criatira.orOperator(Criteria.where("isReceived").is(null),Criteria.where("isReceived").is(Integer.parseInt(receive)));
            }else {
                criatira.and("isReceived").is(Integer.parseInt(receive));
            }
        }
        //默认日期今天
        Calendar calendar = Calendar.getInstance(); calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date todayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date endStart = calendar.getTime();
        criatira.and("createTime").gte(todayStart).lte(endStart);

        // 查询条件定义
        Query query = new Query(criatira).limit(page.getPageSize()).skip((page.getCurrentPage() - 1) * page.getPageSize()).with(new Sort(Sort.Direction.DESC, "createTime"));
        // 计算总记录数
        long count = mongoTemplate.count(query, OrderWaiMai.class);
        page.setTotalNum(count);

        List<OrderWaiMai> orders = mongoTemplate.find(query, OrderWaiMai.class);
        page.setPageDataList(orders);
        return page;
    }

    //查询历史订单
    public Page<OrderWaiMaiHistory> getHistoryOrder(Map<String,String> paramMap, Page<OrderWaiMaiHistory> page) {
        Criteria criatira = new Criteria();
        criatira.where("1").is("1");

        String sellerId = paramMap.get("sellerId");
        if (sellerId != null && !"".equals(sellerId)) {
            criatira.and("sellerShopId").is(sellerId);
        }

        String shopId = paramMap.get("shopId");
        if (shopId != null && !"".equals(shopId)) {
            criatira.and("shopId").is(shopId);
        }

        String platformOrderId = paramMap.get("platformOrderId");
        if (platformOrderId != null && !"".equals(platformOrderId)) {
            criatira.and("platformOrderId").is(platformOrderId);
        }

        String orderId = paramMap.get("orderId");
        if (orderId != null && !"".equals(orderId) && !"all".equals(orderId)) {
            criatira.and("orderId").is(orderId);
        }
        String orderStatus = paramMap.get("orderStatus");
        if (orderStatus != null && !"".equals(orderStatus)) {
            int baidu = OrderUtil.tranBdOrderStatus(Integer.parseInt(orderStatus));
            int jdhome = OrderUtil.tranJHOrderStatus(Integer.parseInt(orderStatus));
            int meituan = OrderUtil.tranMTOrderStatus(Integer.parseInt(orderStatus));
            int eleme = OrderUtil.tranELOrderStatus(Integer.parseInt(orderStatus));
            criatira.orOperator(Criteria.where("order.order.type").is(String.valueOf(baidu)),
                Criteria.where("order.orderStatus").is(jdhome),
                Criteria.where("order.status").is(meituan),
                Criteria.where("order.statuscode").is(eleme));
        }

        String platform = paramMap.get("platform");
        if (platform != null && !"".equals(platform)) {
            criatira.and("platform").is(platform);
        }

        String receive = paramMap.get("receive");
        if (receive != null && !"".equals(receive)) {
            if("0".equals(receive)){
                criatira.orOperator(Criteria.where("isReceived").is(null),Criteria.where("isReceived").is(Integer.parseInt(receive)));
            }else {
                criatira.and("isReceived").is(Integer.parseInt(receive));
            }
        }

        String startDate = paramMap.get("startDate");
        String endDate = paramMap.get("endDate");
        if(startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)){
            criatira.and("createTime")
                .gte(DateTimeUtil.formatDateString(startDate, "yyyy-MM-dd HH:mm:ss"))
                .lte(DateTimeUtil.formatDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
        }

        // 查询条件定义
        Query query = new Query(criatira).limit(page.getPageSize()).skip((page.getCurrentPage() - 1) * page.getPageSize()).with(new Sort(Sort.Direction.DESC, "createTime"));
        // 计算总记录数
        long count = mongoTemplate.count(query, OrderWaiMaiHistory.class);
        page.setTotalNum(count);

        List<OrderWaiMaiHistory> orders = mongoTemplate.find(query, OrderWaiMaiHistory.class);
        page.setPageDataList(orders);
        return page;
    }
}

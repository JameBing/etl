package com.wangjunneil.schedule.job.impl;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.OrderWaiMaiHistory;
import com.wangjunneil.schedule.entity.common.PushRecord;
import com.wangjunneil.schedule.job.JobFactory;
import com.wangjunneil.schedule.job.JobService;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.baidu.BaiDuInnerService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import com.wangjunneil.schedule.service.jdhome.JdHomeInnerService;
import com.wangjunneil.schedule.service.meituan.MeiTuanInnerService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Job
 * Created by yangyb on 2017-12-11.
 * 获取骑手15分钟未接单的订单
 */
@Service("orderSync2Crm2Zt")
public class OrderSync2Crm2ZtJobServiceImpl implements JobService {

    @Resource
    private JobFactory jobFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SysInnerService sysInnerService;

    @Autowired
    private SysFacadeService sysFacadeService;

    @Autowired
    private BaiDuInnerService baiDuInnerService;

    @Autowired
    private JdHomeInnerService jdHomeInnerService;

    @Autowired
    private MeiTuanInnerService meiTuanInnerService;

    @Autowired
    private EleMeInnerService eleMeInnerService;

    private static Logger logger = Logger.getLogger(OrderSync2Crm2ZtJobServiceImpl.class.getName());

    //Job名称
    public static final String JOB_NAME =  "OrderSync2Crm2ZtJobServiceImpl";

    //Job描述
    public static final String JOB_DESC = "同步外卖订单到CRM&中台";

    //触发器类型
    public static final String SCHEDULEBUILDER_TYPE =  "CRON";

    //定时设置
    public static final String TRIGGER_EXPRESSION = "0 0 0/1 * * ?";

    //Job 锁
    private static boolean JOB_LOCK = false;

    @Override
    public void startScheduler() {
    }

    @Override
    public String startJob(String expression) {
        try {
           logger.info("========启动服务开始=========");
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.setJobFactory(jobFactory);
            JobDetail job = null;
            Trigger trigger = null;
            ScheduleBuilder builder = null;
            String triggerName = "Job_" + JOB_NAME;
            String jobName = "Job_" + JOB_NAME;
            boolean requireTrigger = true;
            job = JobBuilder.newJob(OrderSync2Crm2ZtJobServiceImpl.class)
                .withIdentity(jobName, "AQ_Job_Group")
                .build();
            if (requireTrigger){
                switch (SCHEDULEBUILDER_TYPE.toUpperCase()){
                    case "SIMPLE":  //简单触发 每隔一段时间触发一次
                        builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(Integer.valueOf(expression)).repeatForever();
                        break;
                    case "CRON":    //固定时间触发
                        builder = CronScheduleBuilder.cronSchedule(expression);
                        break;
                    case "CALENDAR":
                    default:
                        break;
                }
                trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, "AQ_Job_Group")
                    .startNow()
                    .withSchedule(builder)
                    .build();
            }
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
            logger.info("========启动服务结束=========");
        }catch (Exception e){
        }
        return JOB_NAME.concat(",").concat(SCHEDULEBUILDER_TYPE).concat(",").concat(TRIGGER_EXPRESSION);
    }

    @Override
    public String stopJob(boolean waitForJobsToComplete) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.setJobFactory(jobFactory);
            if (scheduler.isShutdown()) return null;
             scheduler.shutdown(true);
            logger.info("========停止服务=========");
        }catch (Exception e){}
        return JOB_NAME;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Log log1 = null;
        logger.info("============================"+ DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss")+"同步外卖订单2CRM&ZT....===============");
        if (!JOB_LOCK) {
            //查询当天订单

            /*Criteria criteria1 = new Criteria();
            criteria1.where("1").is("1");
            Criteria criteria2 = new Criteria();
            criteria2.where("1").is("1");

            criteria1.andOperator(Criteria.where("type").is(1).and("status").is(0));//CRM
            criteria2.andOperator(Criteria.where("type").is(2).and("status").is(0));//ZT
            Query query1 = new Query(criteria1);
            Query query2 = new Query(criteria2);
            List<PushRecord> orderCrmList = mongoTemplate.find(query1, PushRecord.class);
            List<PushRecord> orderZtList = mongoTemplate.find(query2, PushRecord.class);
            //CRM
            if (orderCrmList != null && orderCrmList.size() > 0) {
                for (int i = 0; i < orderCrmList.size(); i++) {
                    PushRecord pushRecord = orderCrmList.get(i);
                    logger.info("==========推送状态为0的订单to Crm start=============");
                    sysFacadeService.push2Crm(pushRecord.getOrderId());
                    logger.info("==========推送状态为0的订单to Crm end=============");
                }
            }
            //ZT
            if (orderZtList != null && orderZtList.size() > 0) {
                for (int i = 0; i < orderZtList.size(); i++) {
                    PushRecord pushRecord = orderZtList.get(i);
                    logger.info("==========推送状态为0的订单to Zt start=============");
                    sysFacadeService.push2ZT(pushRecord.getOrderId());
                    logger.info("==========推送状态为0的订单to Zt end=============");
                }
            }*/

            //推送mq宕机订单
            /*Criteria criteria1 = new Criteria();
            criteria1.where("1").is("1");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date todayStart = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date endStart = calendar.getTime();
            criteria1.and("createTime").gte(todayStart).lte(endStart);
            Query query1 = new Query(criteria1);
            List<OrderWaiMai> orderBD = mongoTemplate.find(query1, OrderWaiMai.class);
            if(orderBD!=null && orderBD.size()>0){
                for (int i = 0; i < orderBD.size(); i++) {
                    OrderWaiMai orderWaiMai  = orderBD.get(i);
                    PushRecord pushRecord = sysInnerService.getPushRecord(orderWaiMai.getOrderId());
                    if(!StringUtil.isEmpty(pushRecord)){
                        continue;
                    }
                    sysInnerService.addPushRecords(orderWaiMai.getOrderId(),1);
                }
            }*/

           //推送历史数据 暂时使用
            Criteria criteria1 = new Criteria();
            criteria1.where("1").is("1");
            String start = "2018-03-13 00:00:00";
            String end = "2018-03-13 23:59:59";
            Date todayStart = DateTimeUtil.formatDateString(start,"yyyy-MM-dd HH:mm:ss");
            Date endStart = DateTimeUtil.formatDateString(end,"yyyy-MM-dd HH:mm:ss");
            criteria1.and("createTime").gte(todayStart).lte(endStart);
            //criteria1.andOperator(Criteria.where("orderId").is("W8001099170428005668"));
            Query query1 = new Query(criteria1);

            //历史表订单
            /*List<OrderWaiMaiHistory> orders = mongoTemplate.find(query1, OrderWaiMaiHistory.class);
            if (orders != null && orders.size() > 0) {
                for (int i = 0; i < orders.size(); i++) {
                    OrderWaiMaiHistory history  = orders.get(i);
                    OrderWaiMai orderWaiMai = new OrderWaiMai();
                    orderWaiMai.setSellerShopId(history.getSellerShopId());
                    orderWaiMai.setPlatformOrderId(history.getPlatformOrderId());
                    orderWaiMai.setPlatform(history.getPlatform());
                    orderWaiMai.setCreateTime(history.getCreateTime());
                    orderWaiMai.setIsReceived(history.getIsReceived());
                    orderWaiMai.setOrderId(history.getOrderId());
                    orderWaiMai.setOrder(history.getOrder());
                    orderWaiMai.setShopId(history.getShopId());
                    logger.info("==========推送状态为0的订单to Crm start=============");
                    sysFacadeService.pushHistoryOrder2Crm(orderWaiMai);
                    logger.info("==========推送状态为0的订单to Crm end=============");
                }
            }*/
            //实时表订单
            List<OrderWaiMai> orders = mongoTemplate.find(query1, OrderWaiMai.class);
            if (orders != null && orders.size() > 0) {
                for (int i = 0; i < orders.size(); i++) {
                    OrderWaiMai orderWaiMai  = orders.get(i);
                   /* PushRecord pushRecord = sysInnerService.getPushRecord(orderWaiMai.getOrderId());
                    if(!StringUtil.isEmpty(pushRecord)){
                        continue;
                    }*/
                    logger.info("==========推送状态为0的订单to Crm start=============");
                    sysFacadeService.pushHistoryOrder2Crm(orderWaiMai);
                    logger.info("==========推送状态为0的订单to Crm end=============");
                }
            }

            JOB_LOCK = false;
            logger.info("============================" + DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss") + "同步外卖订单2CRM&ZT....===============");
        }
    }
}

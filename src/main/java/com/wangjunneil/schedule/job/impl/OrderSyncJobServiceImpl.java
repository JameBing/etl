package com.wangjunneil.schedule.job.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.OrderWaiMaiHistory;
import com.wangjunneil.schedule.job.JobFactory;
import com.wangjunneil.schedule.job.JobService;
import com.wangjunneil.schedule.service.SysFacadeService;
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
import java.text.MessageFormat;
import java.util.*;

import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Job
 * Created by 杨大山 on 2017-04-11.
 * 同步订单数据
 */
@Service("orderSync")
public class OrderSyncJobServiceImpl implements JobService {

    @Resource
    private JobFactory jobFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SysFacadeService sysFacadeService;

    private static Logger logger = Logger.getLogger(OrderSyncJobServiceImpl.class.getName());

    //Job名称
    public static final String JOB_NAME =  "OrderSyncJobServiceImpl";

    //Job描述
    public static final String JOB_DESC = "同步每天历史订单";

    //触发器类型
    public static final String SCHEDULEBUILDER_TYPE =  "CRON";

    //定时设置
    public static final String TRIGGER_EXPRESSION = "50 59 23 * * ?";

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
            job = JobBuilder.newJob(OrderSyncJobServiceImpl.class)
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
            logger.info("========启动服务开始=========");
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
        logger.info("============================"+ DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss")+"同步订单任务开始执行....===============");
        if (!JOB_LOCK){
            //查询当天订单
            Calendar calendar = Calendar.getInstance(); calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date todayStart = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date endStart = calendar.getTime();
            Query query = new Query(Criteria.where("createTime").gte(todayStart).lte(endStart));
            List<OrderWaiMai> nowOrderList = mongoTemplate.find(query, OrderWaiMai.class);
            //同步当天历史订单
            if(nowOrderList!=null && nowOrderList.size()>0){
                List<OrderWaiMaiHistory> histories = new ArrayList<>();
                for(OrderWaiMai orderWaiMai :nowOrderList){
                    OrderWaiMaiHistory history = new OrderWaiMaiHistory();
                    history.setPlatform(orderWaiMai.getPlatform());
                    history.setOrderId(orderWaiMai.getOrderId());
                    history.setOrder(orderWaiMai.getOrder());
                    history.setCreateTime(orderWaiMai.getCreateTime());
                    history.setIsReceived(orderWaiMai.getIsReceived());
                    history.setPlatformOrderId(orderWaiMai.getPlatformOrderId());
                    history.setSellerShopId(orderWaiMai.getSellerShopId());
                    history.setShopId(orderWaiMai.getShopId());
                    histories.add(history);
                }
                try {
                    mongoTemplate.insertAll(histories);
                }catch (Exception ex){
                    log1 = sysFacadeService.functionRtn.apply(ex);
                    log1.setLogId(new Date()+"同步历史订单");
                    log1.setTitle("同步当天历史订单失败！！");
                    sysFacadeService.updSynLog(log1);
                    return;
                }
                //删除订单表当天数据
                mongoTemplate.remove(query, OrderWaiMai.class);
            }
        }
        JOB_LOCK = false;
        logger.info("============================" + DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss") + "同步订单任务结束执行....===============");
    }
}

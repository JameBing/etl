package com.wangjunneil.schedule.job.impl;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.PushRecord;
import com.wangjunneil.schedule.job.JobFactory;
import com.wangjunneil.schedule.job.JobService;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.baidu.BaiDuInnerService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import com.wangjunneil.schedule.service.jdhome.JdHomeInnerService;
import com.wangjunneil.schedule.service.meituan.MeiTuanInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
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
 * Created by 杨大山 on 2017-04-11.
 * 获取骑手15分钟未接单的订单
 */
@Service("orderSync2Crm2Zt")
public class OrderSync2Crm2ZtJobServiceImpl implements JobService {

    @Resource
    private JobFactory jobFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

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

            Criteria criteria1 = new Criteria();
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
            }
            JOB_LOCK = false;
            logger.info("============================" + DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss") + "同步外卖订单2CRM&ZT....===============");
        }
    }
}

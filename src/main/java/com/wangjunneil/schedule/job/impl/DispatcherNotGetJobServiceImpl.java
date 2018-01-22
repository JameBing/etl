package com.wangjunneil.schedule.job.impl;

import com.mongodb.DBObject;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.OrderWaiMaiHistory;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Job
 * Created by yyb on 2017-04-11.
 * 获取骑手15分钟未接单的订单
 */
@Service("dispatcherNotGet")
public class DispatcherNotGetJobServiceImpl implements JobService {

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

    private static Logger logger = Logger.getLogger(DispatcherNotGetJobServiceImpl.class.getName());

    //Job名称
    public static final String JOB_NAME =  "DispatcherNotGetJobServiceImpl";

    //Job描述
    public static final String JOB_DESC = "实时抓取骑手未接单的订单";

    //触发器类型
    public static final String SCHEDULEBUILDER_TYPE =  "CRON";

    //定时设置
    public static final String TRIGGER_EXPRESSION = "0 0/2 * * * ?";

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
            job = JobBuilder.newJob(DispatcherNotGetJobServiceImpl.class)
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
        logger.info("============================"+ DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss")+"查询骑手未接单订单任务开始执行....===============");
        if (!JOB_LOCK) {
            //查询当天订单
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date todayStart = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date endStart = calendar.getTime();

            Criteria criteria1 = new Criteria();
            criteria1.where("1").is("1");
            criteria1.and("createTime").gte(todayStart).lte(endStart);
            Criteria criteria2 = new Criteria();
            criteria2.where("1").is("1");
            criteria2.and("createTime").gte(todayStart).lte(endStart);
            Criteria criteria3 = new Criteria();
            criteria3.where("1").is("1");
            criteria3.and("createTime").gte(todayStart).lte(endStart);
            Criteria criteria4 = new Criteria();
            criteria4.where("1").is("1");
            criteria4.and("createTime").gte(todayStart).lte(endStart);

            criteria1.andOperator(Criteria.where("order.order.status").is(5).and("platform").is(Constants.PLATFORM_WAIMAI_BAIDU).and("order.order.sendImmediately").is(1));//百度
            criteria2.andOperator(Criteria.where("order.orderStatus").is(32000).and("platform").is(Constants.PLATFORM_WAIMAI_JDHOME));//京东
            criteria3.andOperator(Criteria.where("order.status").is(4).and("platform").is(Constants.PLATFORM_WAIMAI_MEITUAN).and("order.deliverytime").is(0));//美团
            criteria4.andOperator(Criteria.where("order.status").is("valid").and("platform").is(Constants.PLATFORM_WAIMAI_ELEME).and("order.book").is(false));//饿了么
            Query query1 = new Query(criteria1);
            Query query2 = new Query(criteria2);
            Query query3 = new Query(criteria3);
            Query query4 = new Query(criteria4);
            List<OrderWaiMai> orderBD = mongoTemplate.find(query1, OrderWaiMai.class);
            List<OrderWaiMai> orderJD = mongoTemplate.find(query2, OrderWaiMai.class);
            List<OrderWaiMai> orderMT = mongoTemplate.find(query3, OrderWaiMai.class);
            List<OrderWaiMai> orderEL = mongoTemplate.find(query4, OrderWaiMai.class);
            //骑手未接单列表百度
            if (orderBD != null && orderBD.size() > 0) {
                for (int i = 0; i < orderBD.size(); i++) {
                    OrderWaiMai orderWaiMai = orderBD.get(i);
                    Long time = orderWaiMai.getCreateTime().getTime();
                    Long nowTime = new Date().getTime();
                    if (nowTime - time >= 900000) {
                        logger.info("==========推送骑手未接单订单百度=============");
                        sysFacadeService.topicMessageOrderStatus(orderWaiMai.getPlatform(), -100, orderWaiMai.getPlatformOrderId(), null, orderWaiMai.getSellerShopId(), null);
                        try {
                            //修改推送单状态
                            logger.info("==========修改骑手未接单状态-百度=============");
                            baiDuInnerService.updSyncBaiDuOrderStastus(orderWaiMai.getPlatformOrderId(), 55);
                            logger.info("==========修改骑手未接单状态结束-百度=============");
                        } catch (Exception ex) {
                            log1 = sysFacadeService.functionRtn.apply(ex);
                            log1.setLogId(new Date() + "骑手未接单订单百度");
                            log1.setTitle("骑手未接单订单百度！！");
                            sysFacadeService.updSynLog(log1);
                            return;
                        }
                    }
                }
            }
            //骑手未接单列表京东
            if (orderJD != null && orderJD.size() > 0) {
                for (int i = 0; i < orderJD.size(); i++) {
                    OrderWaiMai orderWaiMai = orderJD.get(i);
                    Long time = orderWaiMai.getCreateTime().getTime();
                    Long nowTime = new Date().getTime();
                    if (nowTime - time >= 900000) {
                        logger.info("==========推送骑手未接单订单京东=============");
                        sysFacadeService.topicMessageOrderStatus(orderWaiMai.getPlatform(), -100, orderWaiMai.getPlatformOrderId(), null, orderWaiMai.getSellerShopId(), null);
                        try {
                            //修改推送单状态
                            logger.info("==========修改骑手未接单状态-京东=============");
                            jdHomeInnerService.updateStatus(orderWaiMai.getPlatformOrderId(), 55);
                            logger.info("==========修改骑手未接单状态结束-京东=============");
                        } catch (Exception ex) {
                            log1 = sysFacadeService.functionRtn.apply(ex);
                            log1.setLogId(new Date() + "骑手未接单订单京东");
                            log1.setTitle("骑手未接单订单京东！！");
                            sysFacadeService.updSynLog(log1);
                            return;
                        }
                    }
                }
            }

            //骑手未接单列表美团
            if (orderMT != null && orderMT.size() > 0) {
                for (int i = 0; i < orderMT.size(); i++) {
                    OrderWaiMai orderWaiMai = orderMT.get(i);
                    Long time = orderWaiMai.getCreateTime().getTime();
                    Long nowTime = new Date().getTime();
                    if (nowTime - time >= 900000) {
                        logger.info("==========推送骑手未接单订单美团=============");
                        sysFacadeService.topicMessageOrderStatus(orderWaiMai.getPlatform(), -100, orderWaiMai.getPlatformOrderId(), null, orderWaiMai.getSellerShopId(), null);
                        try {
                            //修改推送单状态
                            logger.info("==========修改骑手未接单状态-美团=============");
                            meiTuanInnerService.updateStatus(orderWaiMai.getPlatformOrderId(), 55);
                            logger.info("==========修改骑手未接单状态结束-美团=============");
                        } catch (Exception ex) {
                            log1 = sysFacadeService.functionRtn.apply(ex);
                            log1.setLogId(new Date() + "骑手未接单订单美团");
                            log1.setTitle("骑手未接单订单美团！！");
                            sysFacadeService.updSynLog(log1);
                            return;
                        }
                    }
                }
            }

            //骑手未接单列表饿了么
            if (orderEL != null && orderEL.size() > 0) {
                for (int i = 0; i < orderEL.size(); i++) {
                    OrderWaiMai orderWaiMai = orderEL.get(i);
                    Long time = orderWaiMai.getCreateTime().getTime();
                    Long nowTime = new Date().getTime();
                    if (nowTime - time >= 900000) {
                        logger.info("==========推送骑手未接单订单饿了么=============");
                        sysFacadeService.topicMessageOrderStatus(orderWaiMai.getPlatform(), -100, orderWaiMai.getPlatformOrderId(), null, orderWaiMai.getSellerShopId(), null);
                        try {
                            //修改推送单状态
                            logger.info("==========修改骑手未接单状态-饿了么=============");
                            eleMeInnerService.updSyncElemeOrderStastus(orderWaiMai.getPlatformOrderId(), "55");
                            logger.info("==========修改骑手未接单状态结束-饿了么=============");
                        } catch (Exception ex) {
                            log1 = sysFacadeService.functionRtn.apply(ex);
                            log1.setLogId(new Date() + "骑手未接单订单饿了么");
                            log1.setTitle("骑手未接单订单饿了么！！");
                            sysFacadeService.updSynLog(log1);
                            return;
                        }
                    }
                }
            }
            JOB_LOCK = false;
            logger.info("============================" + DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss") + "查询骑手未接单订单任务结束执行....===============");
        }
    }
}

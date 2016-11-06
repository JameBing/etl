package com.wangjunneil.schedule.controller.jp;

import com.wangjunneil.schedule.crontab.JdOrderJob;
import com.wangjunneil.schedule.crontab.JpOrderJob;
import com.wangjunneil.schedule.service.JPFacadeService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Controller
@RequestMapping("/jp")
public class JPController {

    private static Logger log = Logger.getLogger(JPController.class.getName());

    @Autowired
    private JPFacadeService jpFacadeService;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private JpOrderJob jpOrderJob;

    @RequestMapping(value = "/getHistoryOrder.php")
    public String getHistoryOrder(PrintWriter out, HttpServletResponse resp, HttpServletRequest req) {
        return null;
    }

    @RequestMapping(value = "/startListening.php")
    public String startListening(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey("jpOrderJob");

        if (scheduler.checkExists(jobKey)) {    // 若已经存在此任务则尝试恢复启动状态
            scheduler.resumeJob(jobKey);
        } else {    // 若不存在则创建新的订单调度任务
            JobDataMap jobData = new JobDataMap();
            jobData.put("jpOrderJob", jpOrderJob);
            JobDetail notifyJob = JobBuilder.newJob(JdOrderJob.class).setJobData(jobData).withIdentity(jobKey).build();

            SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
            trigger.setName("jpOrderJob-trigger");
            trigger.setJobDetail(notifyJob);
            trigger.setRepeatInterval(60000);
            trigger.afterPropertiesSet();

            scheduler.scheduleJob(notifyJob, trigger.getObject());
            scheduler.start();
        }
        return null;
    }

    @RequestMapping(value = "/stopListening.php")
    public String stopListening(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws Exception {
        JobKey jobKey = JobKey.jobKey("jdOrderJob");
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (scheduler.checkExists(jobKey))
            scheduler.pauseJob(jobKey);
        return null;
    }

    @RequestMapping(value = "/changeScheduleTime.php")
    public String changeScheduleTime(PrintWriter out, HttpServletResponse resp, HttpServletRequest req) throws Exception {
        long interval = Long.parseLong(req.getParameter("interval"));
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey("jdOrderJob");
        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        }

        JobDataMap jobData = new JobDataMap();
        jobData.put("jpOrderJob", jpOrderJob);
        JobDetail notifyJob = JobBuilder.newJob(JdOrderJob.class).setJobData(jobData).withIdentity(jobKey).build();

        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setName("jpOrderJob-trigger");
        trigger.setJobDetail(notifyJob);
        trigger.setRepeatInterval(interval);
        trigger.afterPropertiesSet();

        scheduler.scheduleJob(notifyJob, trigger.getObject());
        scheduler.start();
        return null;
    }
}

package com.wangjunneil.schedule.controller.z8;

import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducer;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.crontab.Z8OrderJob;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.entity.z8.Z8CrmOrder;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.Z8FacadeService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by wangjun on 9/8/16.
 */
@Controller
@RequestMapping("/zhe800")
public class Z8Controller {

    private static Logger log = Logger.getLogger(Z8Controller.class.getName());

    //@Autowired
    private Z8FacadeService z8FacadeService;

    @Autowired
    private SysFacadeService sysFacadeService;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    //@Autowired
    private QueueMessageProducer producter;

    @Autowired
    private Z8OrderJob z8OrderJob;

    // --------------------------------------------------------------------------------------------------- public method

    @RequestMapping(value = "/callback")
    public String callback(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String error = req.getParameter("error");
        if (error != null) {
            String errorDesc = req.getParameter("error_description");
            log.error(errorDesc);

            resp.sendRedirect("/console/main.html#/error?message=" + errorDesc);
            return null;
        }

        String code = req.getParameter("code");
        String state = req.getParameter("state");
        log.info("code="+code+";state="+state);//TODO
        try {
            Cfg cfg = sysFacadeService.findZ8Cfg();
//            z8FacadeService.callback(code, state, cfg);

            resp.sendRedirect("/console/main.html");
        } catch (Exception e) {
            log.error(e.toString());
            resp.sendRedirect("/console/main.html#/error?message=Zhe800 authorize callback error");
        }

        return null;
    }

    @RequestMapping(value = "/controlStatus.php")
    public String controlStatus(PrintWriter out) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey("z8OrderJob");
        if (!scheduler.checkExists(jobKey)) {    // 若不存在此任务
            out.println("{\"z8ScheduleStatus\":{\"startup\":false}}");
        } else {
            String returnJson = z8FacadeService.getControlStatus();
            out.println(returnJson);
        }
        out.close();
        return null;
    }

    @RequestMapping(value = "/startListening.php")
    public String startListening(PrintWriter out) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey("z8OrderJob");

        if (scheduler.checkExists(jobKey)) {    // 若已经存在此任务则尝试恢复启动状态
            scheduler.resumeJob(jobKey);
        } else {    // 若不存在则创建新的订单调度任务
            JobDataMap jobData = new JobDataMap();
            jobData.put("z8FacadeService", z8FacadeService);
            jobData.put("producter", producter);
            JobDetail notifyJob = JobBuilder.newJob(Z8OrderJob.class).setJobData(jobData).withIdentity(jobKey).build();

            SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
            trigger.setName("z8OrderJob-trigger");
            trigger.setJobDetail(notifyJob);
            trigger.setRepeatInterval(60000);
            trigger.afterPropertiesSet();

            scheduler.scheduleJob(notifyJob, trigger.getObject());
            scheduler.start();
        }
        Status status = new Status();
        sysFacadeService.addPlatformStatus(status, Constants.PLATFORM_Z800, true);
        out.println("{\"status\":0}");
        out.close();
        return null;
    }

    @RequestMapping(value = "/stopListening.php")
    public String stopListening(PrintWriter out) throws Exception {
        JobKey jobKey = JobKey.jobKey("z8OrderJob");
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.pauseJob(jobKey);
        Status status = new Status();
        sysFacadeService.addPlatformStatus(status, Constants.PLATFORM_Z800, false);
        log.info("[END] schedule sync Z800 order");
        out.println("{\"status\":0}");
        out.close();
        return null;
    }

    @RequestMapping(value = "/getHistoryOrder.php")
    public String getHistoryOrder(PrintWriter out, HttpServletRequest request){
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("skuId", request.getParameter("skuId"));
        paramMap.put("orderId", request.getParameter("orderId"));
        paramMap.put("orderState", request.getParameter("orderState"));
        paramMap.put("productName", request.getParameter("productName"));
        paramMap.put("telephone", request.getParameter("telephone"));
        paramMap.put("startDate", request.getParameter("startDate"));
        paramMap.put("endDate", request.getParameter("endDate"));

        Page<Z8CrmOrder> page = new Page<>();
        String currentPage = request.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = request.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson = z8FacadeService.getHistoryOrder(paramMap, page);
        System.out.println(returnJson);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/fixOrder.php")
    public String fixOrder(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        String startDate    = req.getParameter("startDate");
        String endDate      = req.getParameter("endDate");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);

        Cfg cfg = sysFacadeService.findZ8Cfg();
        int size = z8FacadeService.syncOrderByCond(paramMap);
        String returnJson = "{\"status\":1,\"message\":" + size + "}";

        out.println(returnJson);
        out.close();
        return null;
    }


























    /**
     * 对已订阅的消息进行授权
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @param request   客户端请求对象
     * @return
     */
//    @RequestMapping(value = "/tmcUserPermit")
//    public String tmcUserPermit(PrintWriter out, HttpServletResponse resp, HttpServletRequest request) {
//        resp.setCharacterEncoding("utf-8");
//
//        String appkey = "23431719";
//        String secret = "308a1c2563be288a03c1b1eae31beeda";
//        String sessionKey = "620121400fa99c65443679ZZe287124c49b7d574a21a9152232897825";
//
//        String topics = request.getParameter("topics");
//        //对已订阅的消息进行授权
//        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
//        TmcUserPermitRequest req = new TmcUserPermitRequest();
//        req.setTopics(topics);//消息主题列表，用半角逗号分隔。当用户订阅的topic是应用订阅的子集时才需要设置，不设置表示继承应用所订阅的所有topic，一般情况建议不要设置。
//        TmcUserPermitResponse rsp = null;
//        try {
//            rsp = client.execute(req, sessionKey);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        out.println(rsp.getBody());
//        out.close();
//        return null;
//    }

}

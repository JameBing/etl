package com.wangjunneil.schedule.controller.jd;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducer;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.crontab.JdOrderJob;
import com.wangjunneil.schedule.entity.jd.JdCrmMember;
import com.wangjunneil.schedule.entity.jd.JdMemberRequest;
import com.wangjunneil.schedule.entity.sys.*;
import com.wangjunneil.schedule.entity.jd.JdCrmOrder;
import com.wangjunneil.schedule.entity.jd.JdOrderRequest;
import com.wangjunneil.schedule.service.JdFacadeService;
import com.wangjunneil.schedule.service.SysFacadeService;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Controller
@RequestMapping("/jd")
public class JdController {

    private static Logger log = Logger.getLogger(JdController.class.getName());

    @Autowired
    private JdFacadeService jdFacadeService;

    @Autowired
    private JdOrderJob jdOrderJob;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private SysFacadeService sysFacadeService;

    @Autowired
    private QueueMessageProducer producter;

    // --------------------------------------------------------------------------------------------------- public method

    /**
     * 获取京东商家运营的店铺基本信息
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return  JSON格式的错误信息或者店铺基本信息
     */
    @RequestMapping(value = "/jdControl.php", method = RequestMethod.GET)
    public String jdControl(PrintWriter out, HttpServletResponse resp) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey("jdOrderJob");
        if (!scheduler.checkExists(jobKey)) {    // 若不存在此任务
            out.println("{\"jdScheduleStatus\":{\"startup\":false}}");
        } else {
            String returnJson = jdFacadeService.getOnlineShop();
            out.println(returnJson);
        }
        out.close();
        return null;
    }

    @RequestMapping(value = "/startListening.php")
    public String startListening(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey("jdOrderJob");

        if (scheduler.checkExists(jobKey)) {    // 若已经存在此任务则尝试恢复启动状态
            scheduler.resumeJob(jobKey);
        } else {    // 若不存在则创建新的订单调度任务
            JobDataMap jobData = new JobDataMap();
            jobData.put("jdFacadeService", jdFacadeService);
            jobData.put("producter", producter);
            JobDetail notifyJob = JobBuilder.newJob(JdOrderJob.class).setJobData(jobData).withIdentity(jobKey).build();

            SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
            trigger.setName("jdOrderJob-trigger");
            trigger.setJobDetail(notifyJob);
            trigger.setRepeatInterval(60000);
            trigger.afterPropertiesSet();

            scheduler.scheduleJob(notifyJob, trigger.getObject());
            scheduler.start();
        }
        Status status = new Status();
        sysFacadeService.addPlatformStatus(status, Constants.PLATFORM_JD, true);
        return null;
    }

    @RequestMapping(value = "/stopListening.php")
    public String stopListening(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws Exception {
        JobKey jobKey = JobKey.jobKey("jdOrderJob");
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.pauseJob(jobKey);
        Status status = new Status();
        sysFacadeService.addPlatformStatus(status, Constants.PLATFORM_JD, false);
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
        jobData.put("jdOrderJob", jdOrderJob);
        JobDetail notifyJob = JobBuilder.newJob(JdOrderJob.class).setJobData(jobData).withIdentity(jobKey).build();

        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setName("jdOrderJob-trigger");
        trigger.setJobDetail(notifyJob);
        trigger.setRepeatInterval(interval);
        trigger.afterPropertiesSet();

        scheduler.scheduleJob(notifyJob, trigger.getObject());
        scheduler.start();
        Status status = new Status();
        sysFacadeService.addPlatformStatus(status, Constants.PLATFORM_JD, true);
        return null;
    }

    @RequestMapping(value = "/cleanSchedule.php")
    public String cleanSchedule(PrintWriter out, HttpServletResponse resp, HttpServletRequest req){
        String job = jdFacadeService.cleanSchedule();
        out.println("{\"status\":0}");
        out.close();
        return null;
    }

    /**
     * 获取京东历史订单数据
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return  JSON格式的错误信息或者店铺基本信息
     */
    @RequestMapping(value = "/getHistoryOrder.php")
    public String getHistoryOrder(PrintWriter out, HttpServletResponse resp, HttpServletRequest req) {
        JdOrderRequest jdOrderRequest = new JdOrderRequest();
        jdOrderRequest.setSkuId(req.getParameter("skuId"));
        jdOrderRequest.setOrderId(req.getParameter("orderId"));
        jdOrderRequest.setOrderState(req.getParameter("orderState"));
        jdOrderRequest.setProductName(req.getParameter("productName"));
        jdOrderRequest.setTelephone(req.getParameter("telephone"));
        jdOrderRequest.setStartDate(req.getParameter("startDate"));
        jdOrderRequest.setEndDate(req.getParameter("endDate"));
        // jdOrderRequest.setWayBill(req.getParameter("wayBill")); // TODO 运单号暂时有问题

        // 构建分页对象
        Page<JdCrmOrder> page = new Page<>();
        String currentPage = req.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = req.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson = jdFacadeService.getHistoryOrder(jdOrderRequest, page);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/getCrmMember.php")
    public String getCrmMember(PrintWriter out, HttpServletResponse resp, HttpServletRequest req) {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=UTF-8");

        // 构建条件请求对象
        JdMemberRequest jdMemberRequest = new JdMemberRequest();
        /*jdMemberRequest.setCustomerPin(req.getParameter("customerPin"));
        jdMemberRequest.setGrade(req.getParameter("grade"));
        jdMemberRequest.setMinTradeTime(req.getParameter("minTradeTime"));
        jdMemberRequest.setMaxTradeTime(req.getParameter("maxTradeTime"));
        jdMemberRequest.setMinTradeCount(req.getParameter("minTradeCount"));
        jdMemberRequest.setMaxTradeCount(req.getParameter("maxTradeCount"));
        jdMemberRequest.setAvgPrice(req.getParameter("avePrice"));
        jdMemberRequest.setMinTradeAmount(req.getParameter("minTradeAmount"));*/

        // 构建分页对象
        Page<JdCrmMember> page = new Page<>();
        String currentPage = req.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = req.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson = jdFacadeService.getCrmMember(jdMemberRequest, page);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/callback")
    public String callback(PrintWriter out, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        resp.setCharacterEncoding("utf-8");

        // 先获取error判断是否出错
        String error = req.getParameter("error");
        if (error != null) {
            String errorDesc = req.getParameter("error_description");
            log.error("callback error, " + errorDesc);

            resp.sendRedirect("/console/main.html#/error?message=" + errorDesc);
            return null;
        }

        String code = req.getParameter("code");
        String state = req.getParameter("state");

        try {
            // 调用回调处理
            jdFacadeService.callback(code, state);

            // 重定向到首页
            resp.sendRedirect("/console/main.html");
        } catch (Exception e) {
            log.error(e.toString());
            resp.sendRedirect("/console/main.html#/error?message=JD authorize callback error");
        }

        return null;
    }

    @RequestMapping(value = "/outStock", method = RequestMethod.GET)
    public String outStock(PrintWriter out, HttpServletResponse resp, HttpServletRequest req) throws JdException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=UTF-8");

        String logisticsId = req.getParameter("logisticsId");
        String wayBill = req.getParameter("wayBill");
        String orderId = req.getParameter("orderId");
        String tradeNo = req.getParameter("tradeNo");

        OrderSopOutstorageRequest request = new OrderSopOutstorageRequest();
        request.setLogisticsId(logisticsId);
        request.setWaybill(wayBill);
        request.setOrderId(orderId);
        request.setTradeNo(tradeNo);

        String returnJson = jdFacadeService.orderOutStock(request);
        out.println(returnJson);
        out.close();
        return null;
    }

    //补单
    @RequestMapping(value = "/fixOrder.php")
    public String fixOrder(PrintWriter out, HttpServletRequest req) throws ScheduleException, JdException {
        String startDate    = req.getParameter("startDate");
        String endDate      = req.getParameter("endDate");

        JdOrderRequest orderRequest = new JdOrderRequest();
        orderRequest.setStartDate(startDate);
        orderRequest.setEndDate(endDate);

        Cfg cfg = sysFacadeService.findJdCfg();
        int size = jdFacadeService.syncOrderByCond(cfg, orderRequest);
        String returnJson = "{\"status\":1,\"message\":" + size + "}";

        out.println(returnJson);
        out.close();
        return null;
    }



}

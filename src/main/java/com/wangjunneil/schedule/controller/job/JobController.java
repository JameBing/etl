package com.wangjunneil.schedule.controller.job;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wangjunneil.schedule.bootstrap.ScheduleMain;
import com.wangjunneil.schedule.job.JobService;
import org.eclipse.jetty.util.Fields;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Created by 杨大山 on 2017-04-07.
 */
@Controller
@RequestMapping("/sys")
public class JobController {

    @Resource(name = "orderSync")
    private  JobService jobServiceOrderSync;

    @Resource(name = "dispatcherNotGet")
    private  JobService jobServiceDispatcherNotGet;

    @Resource(name = "orderSync2Crm2Zt")
    private  JobService jobServiceOrderSync2Crm2Zt;


    //开启Job
    @RequestMapping(value = "/scheduler/job/{name}/start", method = RequestMethod.GET)
    public String startJob(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {

        Field field = this.getClass().getDeclaredField("jobService".concat(name));
        field.setAccessible(true);
        Method method = field.getType().getDeclaredMethod("startJob", new Class[]{String.class});
        method.invoke(field.get(this), ScheduleMain.props.getProperty("job.".concat(name).concat(".expression")));
        out.println("{\"result\":1}");
        return null;
    }

    //停止Job
    @RequestMapping(value = "/scheduler/job/{name}/stop", method = RequestMethod.GET)
    public String stopJob(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
        Field field = this.getClass().getDeclaredField("jobService".concat(name));
        field.setAccessible(true);
        Method method = field.getType().getDeclaredMethod("stopJob", new Class[]{boolean.class});
        method.invoke(field.get(this), true);
        out.println("{\"result\":1}");
        return null;
    }

}

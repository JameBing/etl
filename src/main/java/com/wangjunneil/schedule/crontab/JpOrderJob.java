package com.wangjunneil.schedule.crontab;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * Created by wangjun on 9/1/16.
 */
@Component
public class JpOrderJob implements Job {

    private static Logger log = LoggerFactory.getLogger("jp_log");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new java.util.Date());
    }
}

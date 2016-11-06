package com.wangjunneil.schedule.crontab;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by wangjun on 9/7/16.
 */
public abstract class OrderJob implements Job {

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        taskExecute(ctx.getJobDetail().getJobDataMap());
    }

    public abstract void taskExecute(JobDataMap jobDataMap);
}

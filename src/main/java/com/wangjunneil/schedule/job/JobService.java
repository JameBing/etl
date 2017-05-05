package com.wangjunneil.schedule.job;

import org.quartz.Job;

/**
 * Created by 杨大山 on 2017-04-07.
 * Quart 任务调度器中 Job 接口
 */

public interface JobService extends Job {

    //开启任务调度器
    void startScheduler();

    //开启任务
    String startJob(String expression);

    //关闭任务
    String stopJob(boolean waitForJobsToComplete);
}

package com.wangjunneil.schedule.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Created by 杨大山 on 2017-04-12.
 * 自动注入spring容器托管的对象到Quartz中的Job
 */
@Component("jobFactory")
public class JobFactory extends SpringBeanJobFactory  implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    @Resource(name = "orderSync")
    public JobService orderSyncJobService;

    @Resource(name = "dispatcherNotGet")
    public JobService dispatcherNotGetJobService;

    @Resource(name = "orderSync2Crm2Zt")
    public JobService orderSync2Crm2ZtJobService;

    @Resource(name = "orderSync2Crm2ZtException")
    public JobService orderSync2Crm2ZtExceptionJobService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        //进行注入
        applicationContext.getAutowireCapableBeanFactory().autowireBean(jobInstance);
        return jobInstance;
    }
}

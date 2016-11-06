package com.wangjunneil.schedule.bootstrap;

import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.service.SysFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PostConstruct;

/**
 * Created by xuzhicheng on 2016/9/12.
 */
public class Initialize implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SysFacadeService sysFacadeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            Status status = new Status();
            sysFacadeService.initializeReset(status);
        }
    }
}

package com.wangjunneil.schedule.crontab;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.activemq.QueueMessageProducer;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.jd.JdCrmOrder;
import com.wangjunneil.schedule.service.JdFacadeService;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * Created by wangjun on 9/1/16.
 */
@Component
public class JdOrderJob extends OrderJob {

    private static Logger log = LoggerFactory.getLogger(Constants.PLATFORM_JD);

    @Override
    public void taskExecute(JobDataMap jobDataMap) {
        JdFacadeService jdFacadeService = (JdFacadeService) jobDataMap.get("jdFacadeService");
        QueueMessageProducer producer= (QueueMessageProducer) jobDataMap.get("producter");

        log.info("[BEG] schedule sync JD order");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<JdCrmOrder> orders = jdFacadeService.syncOrder();
        if (orders == null || orders.size() == 0) { // 没有新的订单增加
            log.info("it has no new orders be schedule, waiting for ......");
        } else {
            int size = orders.size();
            //循环取数据
            for (int i = 0; i < size; i++) {
                List<JdCrmOrder> list = orders.subList(0, 1);//每次只发一条订单
                String messageJson = JSONObject.toJSONString(list);
                log.info("messageJson：" + messageJson);
                producer.sendJDOrderMessage(messageJson);
                // 剔除
                orders.subList(0, 1).clear();
            }
            log.info("order notify message(" + size + ") send finished");
        }

        stopWatch.stop();
        log.info("[END] schedule sync JD order, waster time " + stopWatch.getTotalTimeSeconds() + " second");
    }
}

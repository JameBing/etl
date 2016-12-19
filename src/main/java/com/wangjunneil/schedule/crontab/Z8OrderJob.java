package com.wangjunneil.schedule.crontab;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducer;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.z8.Z8CrmOrder;
import com.wangjunneil.schedule.service.Z8FacadeService;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * Created by xuzhicheng on 2016/9/20.
 */
@Component
public class Z8OrderJob extends OrderJob {

    private static Logger log = LoggerFactory.getLogger(Constants.PLATFORM_Z800);

    @Override
    public void taskExecute(JobDataMap jobDataMap) {
        Z8FacadeService z8FacadeService = (Z8FacadeService) jobDataMap.get("z8FacadeService");
        QueueMessageProducer producer= (QueueMessageProducer) jobDataMap.get("producter");

        log.info("[BEG] schedule sync Z800 order");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Z8CrmOrder> orders = z8FacadeService.syncOrder();
        if (orders == null || orders.size() == 0) { // 没有新的订单增加
            log.info("it has no new orders be schedule, waiting for ......");
         } else {
            int size = orders.size();
            //循环取数据
            for (int i = 0; i < size; i++) {
                List<Z8CrmOrder> list = orders.subList(0, 1);//每次只发一条订单
                String messageJson = JSONObject.toJSONString(list);
                producer.sendJDOrderMessage(messageJson);
                // 剔除
                orders.subList(0, 1).clear();
            }
            if (log.isInfoEnabled())
                log.info("order notify message(" + size + ") send finished");
        }

        stopWatch.stop();
        log.info("[END] schedule sync Z800 order, waster time " + stopWatch.getTotalTimeSeconds() + " second");
    }
}

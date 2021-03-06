package com.wangjunneil.schedule.activemq.Queue;

import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by wangjun on 8/2/16.
 */
public class QueueMessageConsumer implements MessageListener {

    @Autowired
    private SysInnerService sysInnerService;

    @Override
    public void onMessage(Message message) {
        //TextMessage textMessage = (TextMessage) message;
        MapMessage mapMessage = (MapMessage) message;
        System.out.println("Queue接收来自CRM生产者的消息："+mapMessage);
        try {
            String orderId = mapMessage.getString("orderId");
            System.out.println("订单Id:"+orderId);
            if(!StringUtil.isEmpty(orderId)){
                //修改状态为1
                sysInnerService.updatePushRecords(orderId,1);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

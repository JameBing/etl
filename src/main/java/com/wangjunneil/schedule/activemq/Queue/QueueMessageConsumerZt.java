package com.wangjunneil.schedule.activemq.Queue;

import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;

/**
 * Created by wangjun on 8/2/16.
 */
public class QueueMessageConsumerZt implements MessageListener {

    @Autowired
    private SysInnerService sysInnerService;

    @Override
    public void onMessage(Message message) {
        //TextMessage textMessage = (TextMessage) message;
        TextMessage textMessage = (TextMessage) message;
        System.out.println("Queue接收来自ZT生产者的消息："+textMessage);
        try {
            String orderId = textMessage.getText();
            System.out.println("订单Id:"+orderId);
            if(!StringUtil.isEmpty(orderId)){
                //修改状态为1
                sysInnerService.updatePushRecords(orderId,2);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

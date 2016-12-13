package com.wangjunneil.schedule.activemq;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by yangwanbin on 2016-12-10.
 */

@Component
public class TopicMessageConsumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("TopicConsumer接收来自生产者的消息：");
        System.out.println(message.getClass().getName());
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)message;
            try {
                System.out.println("TopicConsumer接收到的消息是：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

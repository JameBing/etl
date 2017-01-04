package com.wangjunneil.schedule.activemq.Topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author yangyongbing
 * @since 2017/1/4.
 */
@Component
public class EkpMessageProducer {
    private JmsTemplate ekpJmsTemplate;
    //Send TextMessage
    public void sendMessage(final Destination destination,  String message){
        System.out.println("===================Start:EKP生产者发送了一条TextMessage消息===================================");
        System.out.println(message);
        ekpJmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message);
                return textMessage;
            }
        });
        System.out.println("===================EKP生产者 End===================================");
    }

    public void setEkpJmsTemplate(JmsTemplate ekpJmsTemplate) {this.ekpJmsTemplate = ekpJmsTemplate; }
}

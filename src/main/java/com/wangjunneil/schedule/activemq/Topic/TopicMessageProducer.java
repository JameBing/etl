package com.wangjunneil.schedule.activemq.Topic;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by yangwanbin on 2016-12-10.
 */
//MQ Topic 消息生产者
                @Component
                public class TopicMessageProducer {

                  private JmsTemplate topicJmsTemplate;
                    //Send TextMessage
                    public void sendMessage(final Destination destination,  String message){
                        System.out.println("===================Start:生产者发送了一条TextMessage消息===================================");
                        System.out.println(message);
                        topicJmsTemplate.send(destination, new MessageCreator() {
                            @Override
                            public Message createMessage(Session session) throws JMSException {
                                TextMessage textMessage = session.createTextMessage(message);
                            //   textMessage.setJMSReplyTo(destination);
                               return textMessage;
                            }
                        });
                       System.out.println("===================End===================================");
    }

                  //Send Json Object
                     public void sendMessage(final  Destination destination,JSONObject jsonObject) {
                         System.out.println("===================Start:生产者发送了一条TextMessage消息===================================");
                         System.out.println(jsonObject);
                         topicJmsTemplate.send(destination, new MessageCreator() {
                             @Override
                             public Message createMessage(Session session) throws JMSException {

                                 ObjectMessage textMessage = session.createObjectMessage(jsonObject);
                                 textMessage.setJMSReplyTo(destination);
                                 return textMessage;
                             }
                         });
                         System.out.println("===================End===================================");
                     }
    public void setTopicJmsTemplate(JmsTemplate  topicJmsTemplate) {
        this.topicJmsTemplate = topicJmsTemplate;
    }
}

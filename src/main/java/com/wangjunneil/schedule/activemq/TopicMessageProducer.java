package com.wangjunneil.schedule.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

                    public void sendMessage(final Destination destination,  String message){

                        System.out.println("===================Start:生产者发送了一条消息===================================");
                        System.out.println(message);
                        topicJmsTemplate.send(destination, new MessageCreator() {
                            @Override
                            public Message createMessage(Session session) throws JMSException {
                                TextMessage textMessage = session.createTextMessage(message);
                               textMessage.setJMSReplyTo(destination);
                               return textMessage;
                            }
                        });
                       System.out.println("===================End:生产者发送了一条消息===================================");
    }

    public void setTopicJmsTemplate(JmsTemplate  topicJmsTemplate) {
        this.topicJmsTemplate = topicJmsTemplate;
    }
}

package com.wangjunneil.schedule.activemq.Topic;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by yangwanbin on 2016-12-10.
 */
//MQ Topic 消息生产者
                @Component
                public class TopicMessageProducer{

                  private static Logger log = Logger.getLogger(TopicMessageProducer.class.getName());

                  private JmsTemplate topicJmsTemplate;
                    //Send TextMessage
                    public void sendMessage(final Destination destination,  String message,String selector){
                        log.info("===================Start:生产者发送了一条TextMessage消息===================================");
                        log.info(message);
                        topicJmsTemplate.send(destination, new MessageCreator() {
                            @Override
                            public Message createMessage(Session session) throws JMSException {
                              //TextMessage textMessage = session.createTextMessage(message+ ( StringUtil.isEmpty(selector)?"":(",receiver:'".concat(selector).concat("'"))));
                                TextMessage textMessage = session.createTextMessage(message);
                                textMessage.setStringProperty("Selector",selector);
                            //   textMessage.setJMSReplyTo(destination);
                               return textMessage;
                            }
                        });
                       log.info("===================End===================================");
    }

                  //Send Json Object
                     public void sendMessage(final  Destination destination,JSONObject jsonObject,String selector) {
                         log.info("===================Start:生产者发送了一条TextMessage消息===================================");
                         log.info(jsonObject);
                         topicJmsTemplate.send(destination, new MessageCreator() {
                             @Override
                             public Message createMessage(Session session) throws JMSException {
                                 //TextMessage textMessage = session.createTextMessage(new Gson().toJson(jsonObject) + ( StringUtil.isEmpty(selector)?"":(",receiver:".concat(selector))));
                                 TextMessage textMessage = session.createTextMessage(new Gson().toJson(jsonObject) );
                                 textMessage.setStringProperty("Selector",selector);
                                 textMessage.setJMSReplyTo(destination);
                                 return textMessage;
                             }
                         });
                         log.info("===================End===================================");
                     }
    public void setTopicJmsTemplate(JmsTemplate  topicJmsTemplate) {
        this.topicJmsTemplate = topicJmsTemplate;
    }
}

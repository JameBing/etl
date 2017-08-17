package com.wangjunneil.schedule.activemq.Topic;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by yangwanbin on 2017-01-04.
 */
@Component
public class TopicMessageProducerAsync implements Runnable {

    private static Logger log = Logger.getLogger(TopicMessageProducerAsync.class.getName());
    private JmsTemplate topicJmsTemplate;
    private  Destination destination ;
    private JSONObject jsonObject ;
    private String selector ;
    private String message ;
    private boolean isMessage = true;

    @Override
    public void run() {
        synchronized (this){
            log.info("===================Start:生产者发送了一条TextMessage消息===================================");
            System.out.print("===================Start:生产者发送了一条TextMessage消息===================================");
            log.info(message);
            System.out.print(message);
            topicJmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = null;
                    if (isMessage){
                        textMessage = session.createTextMessage(message);
                    }else {
                        textMessage =  session.createTextMessage(new Gson().toJson(jsonObject) );//如果是jsonObject转为String发送
                    }
                    textMessage.setStringProperty("Selector",selector);
                    textMessage.setJMSExpiration(180000);
                    //   textMessage.setJMSReplyTo(destination);
                    return textMessage;
                }
            });
            log.info("===================End===================================");
        }

    }

    public void  init(String message,String selector){
         this.message = message;
        this.selector = selector;
    }

    public    void  init(JSONObject jsonObject1,String selector){
        this.jsonObject = jsonObject1;
        this.selector = selector;
    }

    public void setTopicJmsTemplate(JmsTemplate topicJmsTemplate){
        this.topicJmsTemplate = topicJmsTemplate;
    }

    public void  setDestination(Destination destination){
        this.destination = destination;
    }

    public void  setMessage(boolean isMessage){
        this.isMessage = isMessage;
    }

}

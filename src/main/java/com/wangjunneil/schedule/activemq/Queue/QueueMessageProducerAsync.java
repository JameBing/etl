package com.wangjunneil.schedule.activemq.Queue;
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
public class QueueMessageProducerAsync implements Runnable {

    private static Logger log = Logger.getLogger(QueueMessageProducerAsync.class.getName());
    private JmsTemplate queueJmsTemplate;
    private Destination destination ;
    private JSONObject jsonObject ;
    private String selector ;
    private String message ;
    private boolean isMessage = true;

    @Override
    public void run() {
        synchronized (this){
            log.info("===================Start:生产者发送了一条TextMessage消息===================================");
            log.info(""+jsonObject);
            queueJmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = null;
                    if (isMessage){
                        textMessage = session.createTextMessage(jsonObject.toJSONString());
                    }else {
                        textMessage =  session.createTextMessage(new Gson().toJson(jsonObject) );//如果是jsonObject转为String发送
                    }
                    return textMessage;
                }
            });
            log.info("===================End===================================");
        }

    }

    public void  init(String message,String selector){
         this.message = message;
       /* this.selector = selector;*/
    }

    public  void  init(JSONObject jsonObject1,String selector){
        this.jsonObject = jsonObject1;
        /*this.selector = selector;*/
    }

    public void setQueueJmsTemplate(JmsTemplate queueJmsTemplate) {
        this.queueJmsTemplate = queueJmsTemplate;
    }

    public void  setDestination(Destination destination){
        this.destination = destination;
    }

    public void  setMessage(boolean isMessage){
        this.isMessage = isMessage;
    }

}

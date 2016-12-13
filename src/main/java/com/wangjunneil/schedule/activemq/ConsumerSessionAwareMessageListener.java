package com.wangjunneil.schedule.activemq;

import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Destination;
/**
 * Created by admin on 2016-12-10.
 */
//消费者回复message监听
public class ConsumerSessionAwareMessageListener implements SessionAwareMessageListener<TextMessage> {

    private  Destination destination;

    @Override
    public void onMessage(TextMessage message, Session session) throws JMSException {

    }

    public Destination getDestination(){
        return  this.destination;
    }

    public void setDestination(Destination destination){
        this.destination = destination;
    }
}

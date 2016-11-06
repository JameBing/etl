package com.wangjunneil.schedule.activemq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 *
 * Created by wangjun on 7/27/16.
 */
public class QueueMessageProducer {

    private JmsTemplate jmsTemplate;

    private Destination jdOrderQueue;

    private Destination tmallOrderQueue;

    private Destination jpOrderQueue;

    private Destination z8OrderQueue;

    public void setTmallOrderQueue(Destination tmallOrderQueue) { this.tmallOrderQueue = tmallOrderQueue; }

    public void setJdOrderQueue(Destination jdOrderQueue) { this.jdOrderQueue = jdOrderQueue; }

    public void setJpOrderQueue(Destination jpOrderQueue) {
        this.jpOrderQueue = jpOrderQueue;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setZ8OrderQueue(Destination z8OrderQueue) {
        this.z8OrderQueue = z8OrderQueue;
    }

    public void sendJDOrderMessage(String message) {
        jmsTemplate.send(jdOrderQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(message);
                return textMessage;
            }
        });
    }

    public void sendTmallOrderMessage(String message) {
        jmsTemplate.send(tmallOrderQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(message);
                return textMessage;
            }
        });
    }

    public void sendZ8OrderMessage(String message) {
        jmsTemplate.send(z8OrderQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(message);
                return textMessage;
            }
        });
    }


}

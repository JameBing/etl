package com.wangjunneil.schedule.activemq.Queue;


import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.*;


/**
 * Created by admin on 2016-12-10.
 */
public class ConsumerMessageListener implements MessageListener{

    private MessageConverter messageConverter;

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("接收到一个纯文本消息(ConsumerMessageListener):".concat(((TextMessage) message).getText()));
            } else if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;

            } else if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //getter
    public MessageConverter getMessageConverter(){
        return messageConverter;
    }

    //setter
    public void setMessageConverter(MessageConverter messageConverter){
        this.messageConverter = messageConverter;
    }
}

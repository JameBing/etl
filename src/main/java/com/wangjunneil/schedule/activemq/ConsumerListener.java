package com.wangjunneil.schedule.activemq;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Created by yangwanbin on 2016-12-12.
 */
public class ConsumerListener {

    public void handleMessage(String message) {
        System.out.println("ConsumerListener通过handleMessage接收到一个纯文本消息，消息内容是：" + message);
    }

    /**
     * 当返回类型是非null时MessageListenerAdapter会自动把返回值封装成一个Message，然后进行回复
     * @param message
     * @return
     */
    public String receiveMessage(String message) {
        System.out.println("ConsumerListener通过receiveMessage接收到一个纯文本消息，消息内容是：" + message);
        return "这是ConsumerListener对象的receiveMessage方法的返回值。";
    }

    public void receiveMessage(ObjectMessage message) throws JMSException {
        System.out.println(message.getObject());
    }


    public static void main(String[] args) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("myTopic.messages");
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    TextMessage tm = (TextMessage) message;
                    try {
                        System.out.println("Received message: " + tm.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

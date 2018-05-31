package com.jtd.recharge.bean;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
/**
 * Created by liyabin on 2017/12/16.
 */
public class  ActiveMq {
    //日志对象
    private static Log logger = LogFactory.getLog(ActiveMq.class);

    //启动mq连接 发送消息
    public static void start(String loginName,String pwd, String url, String mqName,final String message){
        //连接账号
        String userName = loginName;
        //连接密码
        String password = pwd;
        //连接地址
        String brokerURL = url;
        //connection的工厂
        ConnectionFactory factory;
        //连接对象
        Connection connection = null;
        //一个操作会话
        Session session;
        //目标队列
        Destination destination;
        //生产者
        MessageProducer producer;
        try {
            //根据用户名，密码，url创建一个连接工厂
            factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
            //从工厂中获取一个连接
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //连接了一个队列,如果这个队列不存在，将会被创建
            destination = session.createQueue(mqName);
            //从session中，获取一个消息生产者
            producer = session.createProducer(destination);
            //设置生产者的模式，有两种可选
            //DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            //发送消息
            sendMessage(session, producer,message);
            //提交会话
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendMessage(Session session, MessageProducer messageProducer,String msg) throws Exception{
        logger.info("*************数据开始存入mq中*************");
        //创建一条文本消息
        TextMessage message = session.createTextMessage(msg);
        //通过消息生产者发出消息
        messageProducer.send(message);
        logger.info("推送的状态详情: "+msg);
        logger.info("*************数据存入mq中结束*************");
    }
}


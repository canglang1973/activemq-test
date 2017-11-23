package com.canglang.activemq.openwire;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author leitao.
 * @time: 2017/11/6  10:13
 * @version: 1.0
 * @description:
 **/
public class JMSProducer {
    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static  final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    //发送的消息数量
    private static final int SENDNUM = 10;

    public static void  main(String[] args){
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection=null;//连接
        Session session;//会话 接受或者发送消息的线程
        Destination destination;//消息目的地
        MessageProducer messageProducer;//消息生产者;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEURL);
        try {
            //获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            //创建一个名为"hello world"的消息队列
            destination = session.createQueue("hello");
            //创建消息生成者
            messageProducer = session.createProducer(destination);
            //发送消息
            sendMessage(session,messageProducer);

//            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 发送消息
     */
    public  static  void  sendMessage(Session session,MessageProducer messageProducer) throws  Exception{
        for (int i = 0; i < JMSProducer.SENDNUM; i++) {
            //创建一条文本消息
            TextMessage message = session.createTextMessage("ActiveMQ 发送消息" +i);
            System.out.println("发送消息：Activemq 发送消息" + i);
            //通过消息生产者发出消息
            messageProducer.send(message);
        }
    }
}

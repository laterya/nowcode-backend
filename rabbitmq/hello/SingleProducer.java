package com.yp.nowcode.rabbitmq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class SingleProducer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂（ConnectionFactory对象），这个工厂对象可以用于创建到RabbitMQ服务端的连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 下面三个有默认值
//        factory.setUsername();
//        factory.setPassword();
//        factory.setPort();

        // 建立连接、创建频道
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 创建消息队列，各参数的含义：durable: 是否持久化，exclusive：是否独占，autoDelete：是否自动删除,other：队列的其他参数
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 发送消息
            String message = "Hello World!";
            // props:消息的其他属性，如路由头等，boyd:消息体，这就是消息的两个组成部分
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
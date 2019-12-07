package com.qianfeng.smsplatform.monitor.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitChannelUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("rabbitmq.qfjava.cn");
        connectionFactory.setPort(8800);
        connectionFactory.setUsername("Five");
        connectionFactory.setPassword("123");
        connectionFactory.setVirtualHost("/Five");
        return connectionFactory.newConnection();
    }
}

package com.qianfeng.smsplatform.search.config;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

import static com.qianfeng.smsplatform.common.constants.RabbitMqConsants.TOPIC_SMS_GATEWAY;
import static com.qianfeng.smsplatform.common.constants.RabbitMqConsants.TOPIC_SMS_SEND_LOG;

/*
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".                                                                                    
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；


*裴少泊的修仙之路
*描述：
*/
@SpringBootApplication
public class RabbitMqConfig {
    @Bean
    public Standard_Report report(){
        return new Standard_Report();
    }

    @Bean
    public static ConnectionFactory connectionFactory(){
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("rabbitmq.qfjava.cn");
        connectionFactory.setPort(8800);
        connectionFactory.setUsername("Five");
        connectionFactory.setPassword("123");
        connectionFactory.setVirtualHost("/Five");
        return connectionFactory;
    }


}

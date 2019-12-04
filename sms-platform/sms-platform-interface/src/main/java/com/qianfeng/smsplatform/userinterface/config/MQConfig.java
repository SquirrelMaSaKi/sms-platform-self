package com.qianfeng.smsplatform.userinterface.config;

import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ---  2019/12/4 --- 10:19
 * --天神佑我：写代码，无BUG
 */
@Configuration
public class MQConfig {
     @Bean
    public Queue queue() {
         return new Queue(RabbitMqConsants.TOPIC_PRE_SEND);
     }
}

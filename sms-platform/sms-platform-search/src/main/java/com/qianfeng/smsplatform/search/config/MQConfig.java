package com.qianfeng.smsplatform.search.config;

import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author damon
 * @Classname MQConfig
 * @Date 2019/12/6 15:33
 * @Description 如果MQ中没有这两个队列，那么会去创建这两个队列
 */
@Configuration
public class MQConfig {
    @Bean
    public Queue queue() {
        return new Queue(RabbitMqConsants.TOPIC_PRE_SEND);
    }
    @Bean
    public Queue queue1() {
        return new Queue(RabbitMqConsants.TOPIC_PUSH_SMS_REPORT);
    }

}

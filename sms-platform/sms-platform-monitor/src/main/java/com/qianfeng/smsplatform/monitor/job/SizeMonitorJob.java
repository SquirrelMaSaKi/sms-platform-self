package com.qianfeng.smsplatform.monitor.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.qianfeng.smsplatform.monitor.feign.ChannelFeign;
import com.qianfeng.smsplatform.monitor.util.RabbitChannelUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 监控通道id队列中信息的条数
 */
public class SizeMonitorJob implements SimpleJob {
    @Autowired
    private ChannelFeign channelFeign;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${alert.gatewayQueue.size}")
    private long size;

    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            Connection connection = RabbitChannelUtil.getConnection();
            Channel channel = connection.createChannel();
            List<Long> channelIds = channelFeign.getChannelIds();
            System.err.println(channelIds.toString() + "-----" + size);
            //System.err.println(channel.messageCount(RabbitMqConsants.TOPIC_SMS_GATEWAY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

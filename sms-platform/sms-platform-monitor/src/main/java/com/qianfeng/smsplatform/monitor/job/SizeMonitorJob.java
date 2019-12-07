package com.qianfeng.smsplatform.monitor.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
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
            if (channelIds != null && channelIds.size()>0) {
                for (Long channelId : channelIds) {
                    long counts = channel.messageCount(RabbitMqConsants.TOPIC_SMS_GATEWAY + channelId);
                    if (counts > size) {
                        System.err.println("队列"+RabbitMqConsants.TOPIC_SMS_GATEWAY + channelId+"数量超额了");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.qianfeng.smsplatform.monitor.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.monitor.feign.ChannelFeign;
import com.qianfeng.smsplatform.monitor.util.RabbitChannelUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

/**
 * 监控通道id队列中信息的条数
 */
public class SizeMonitorJob implements SimpleJob {
    @Autowired
    private ChannelFeign channelFeign;

    @Value("${alert.gatewayQueue.size}")
    private long size;

    @Override
    public void execute(ShardingContext shardingContext) {
        Logger logger = LoggerFactory.getLogger(SizeMonitorJob.class);
        try {
            Connection connection = RabbitChannelUtil.getConnection();
            Channel channel = connection.createChannel();
            List<Long> channelIds = channelFeign.getChannelIds();
            for (Long channelId : channelIds) {
                long counts = channel.messageCount(RabbitMqConsants.TOPIC_SMS_GATEWAY + channelId);
                if (counts > size) {
                    logger.error("下发网关队列："+RabbitMqConsants.TOPIC_SMS_GATEWAY + channelId+"，积压严重，请排查");
                }
            }
        } catch (Exception e) {
            //logger.info("下发网关通道初始化不完全，请排查");
        }
    }
}

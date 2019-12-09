package com.qianfeng.smsplatform.monitor.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.monitor.feign.CacheFeign;
import com.qianfeng.smsplatform.monitor.feign.ChannelFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ReportFailMonitorJob implements SimpleJob {
    @Autowired
    private ChannelFeign channelFeign;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private CacheFeign cacheFeign;

    @Override
    public void execute(ShardingContext shardingContext) {
        Logger logger = LoggerFactory.getLogger(ReportFailMonitorJob.class);

        List<Standard_Report> reports = channelFeign.findAll();

        if (reports != null && reports.size() > 0) {
            logger.error("有发送给客户的回执Report状态报告发送失败，正在重新放入队列发送");
            for (Standard_Report report : reports) {
                report.setSendCount(0);
                amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_PUSH_SMS_REPORT, report);
                Map<Object, Object> map = cacheFeign.hget(CacheConstants.CACHE_PREFIX_CLIENT + report.getClientID());
                logger.error("发送失败消息重新入队列>>>>用户名称："+map.get("corpname")+"，用户ID："+report.getClientID()+"，手机号："+report.getMobile());
            }
            //清空数据库
            channelFeign.deleteReport();
        }
    }
}

package com.qianfeng.smsplatform.monitor.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.qianfeng.smsplatform.monitor.job.FeeMonitorJob;
import com.qianfeng.smsplatform.monitor.job.ReportFailMonitorJob;
import com.qianfeng.smsplatform.monitor.job.SizeMonitorJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleJobConfig {
    @Autowired
    private ZookeeperRegistryCenter regCenter;

    /**
     * 实现自己的job，这里一个是监听费用fee，一个是监听通道数量size
     */
    @Bean
    public SimpleJob feeSimpleJob() {
        return new FeeMonitorJob();
    }

    @Bean
    public SimpleJob sizeSimpleJob() {
        return new SizeMonitorJob();
    }

    @Bean
    public SimpleJob reportFailJob() {
        return new ReportFailMonitorJob();
    }

    /**
     * 将自己的FeeMonitorJob加入调度执行
     */
    @Bean(initMethod = "init")
    public JobScheduler feeSimpleJobScheduler(final SimpleJob feeSimpleJob,
                                              @Value("${monitorFeeJob.cron}") final String cron,
                                              @Value("${monitorFeeJob.shardingTotalCount}") final int shardingTotalCount){
        return new SpringJobScheduler(feeSimpleJob, regCenter, getLiteJobConfiguration(feeSimpleJob.getClass(), cron, shardingTotalCount));
    }

    @Bean(initMethod = "init")
    public JobScheduler sizeSimpleJobScheduler(final SimpleJob sizeSimpleJob,
                                               @Value("${monitorQueueSizeJob.cron}") final String cron,
                                               @Value("${monitorQueueSizeJob.shardingTotalCount}") final int shardingTotalCount) {
        return new SpringJobScheduler(sizeSimpleJob, regCenter, getLiteJobConfiguration(sizeSimpleJob.getClass(), cron, shardingTotalCount));
    }

    @Bean(initMethod = "init")
    public JobScheduler reportFailJobScheduler(final SimpleJob reportFailJob,
                                               @Value("${monitorReportJob.cron}") final String cron,
                                               @Value("${monitorReportJob.shardingTotalCount}") final int shardingTotalCount) {
        return new SpringJobScheduler(reportFailJob, regCenter, getLiteJobConfiguration(reportFailJob.getClass(), cron, shardingTotalCount));
    }


    /**
     * 作业配置
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
                                                         final int shardingTotalCount) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).build(),
                jobClass.getCanonicalName())).overwrite(true).build();
    }
}

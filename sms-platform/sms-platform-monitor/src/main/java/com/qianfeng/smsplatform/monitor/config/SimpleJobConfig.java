package com.qianfeng.smsplatform.monitor.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.qianfeng.smsplatform.monitor.job.FeeMonitorJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleJobConfig {
    @Autowired
    private ZookeeperRegistryCenter regCenter;

    /**
     * 实现自己的job
     */
    @Bean
    public SimpleJob feeSimpleJob() {
        return new FeeMonitorJob();
    }

    /**
     * 将自己的FeeMonitorJob加入调度执行
     */
    @Bean(initMethod = "init")
    public JobScheduler feeSimpleJobScheduler(final SimpleJob feeSimpleJob,
                                              @Value("${monitorFeeJob.cron}") final String cron,
                                              @Value("${monitorFeeJob.shardingTotalCount}") final int shardingTotalCount,
                                              @Value("${monitorFeeJob.shardingItemParameters}") final String shardingItemParameters){
        return new SpringJobScheduler(feeSimpleJob, regCenter, getLiteJobConfiguration(feeSimpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters));
    }

    /**
     * 作业配置
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
                                                         final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(),
                jobClass.getCanonicalName())).overwrite(true).build();
    }
}

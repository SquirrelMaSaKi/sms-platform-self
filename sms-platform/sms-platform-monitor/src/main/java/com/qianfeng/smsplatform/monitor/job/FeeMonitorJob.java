package com.qianfeng.smsplatform.monitor.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.monitor.feign.CacheFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

/**
 * 监控费用Job
 */
public class FeeMonitorJob implements SimpleJob {
    @Autowired
    private CacheFeign cacheFeign;

    @Value("${alert.fee}")
    private long fee_alert;

    @Override
    public void execute(ShardingContext shardingContext) {
        Set<String> fees = cacheFeign.keys(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE + "*");
        if (fees != null && fees.size() > 0) {
            for (String fee : fees) {
                System.err.println(fee);
//            long fee_client = Long.valueOf(fee);
//            if (fee_client < fee_alert) {
//                //费用低于100元报警
//                System.err.println("用户费用低于100元");
//            }
            }
        }
    }
}

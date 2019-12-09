package com.qianfeng.smsplatform.monitor.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.monitor.feign.CacheFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
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
        Logger logger = LoggerFactory.getLogger(FeeMonitorJob.class);
        Set<String> fees = cacheFeign.keys(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE + "*");
        if (fees != null && fees.size() > 0) {
            for (String fee : fees) {
                String clientId = fee.substring(fee.indexOf(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE) + 18);
                long fee_client = Long.valueOf(cacheFeign.getString(fee));
                if (fee_client < fee_alert) {
                    Map<Object, Object> map = cacheFeign.hget(CacheConstants.CACHE_PREFIX_CLIENT + clientId);
                    //费用低于100元报警
                    logger.error("用户名称："+map.get("corpname")+"，用户ID："+clientId+"，用户手机号："+map.get("mobile")+"，账户余额低于100元，请充值");
                }
            }
        }
    }
}

package com.qianfeng.smsplatform.monitor.feign;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FeignFallBack implements CacheFeign, ChannelFeign {
    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public Set<String> keys(String pattern) {
        return null;
    }

    @Override
    public Map<Object, Object> hget(String key) {
        return null;
    }

    @Override
    public List<Long> getChannelIds() {
        return null;
    }

    @Override
    public List<Standard_Report> findAll() {
        return null;
    }

    @Override
    public int deleteReport() {
        return 0;
    }
}

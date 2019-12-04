package com.qianfeng.smsplatform.userinterface.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * ---  2019/12/3 --- 8:34
 * --天神佑我：写代码，无BUG
 */
@FeignClient("CACHE-SERVICE")
public interface CacheServcie {
    @RequestMapping(value = "/cache/hget/{key}")
    public Map<String, Object> hGet(@PathVariable String key);
}

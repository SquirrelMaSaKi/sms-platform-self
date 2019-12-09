package com.qianfeng.smsplatform.monitor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "CACHE-SERVICE", fallback = FeignFallBack.class)
public interface CacheFeign {
    @RequestMapping("/cache/string/get/{key}")
    String getString(@PathVariable("key") String key);

    @RequestMapping(value = "/cache/keys")
    Set<String> keys(@RequestParam("pattern") String pattern);

    @RequestMapping("/cache/hget/{key}")
    Map<Object, Object> hget(@PathVariable("key") String key);
}

package com.qianfeng.smsplatform.monitor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value = "CACHE-SERVICE")
public interface CacheFeign {
    @RequestMapping("/cache/string/get/{key}")
    String getString(@PathVariable("key") String key);

    @RequestMapping(value = "/cache/keys", method = RequestMethod.GET)
    Set<String> keys(@RequestParam("pattern") String pattern);
}

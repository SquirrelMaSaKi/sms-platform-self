package com.qianfeng.smsplatform.webmaster.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * fallBack熔断
 */
@FeignClient(value = "CACHE-SERVICE", fallback = FeignFallBack.class)
public interface CacheFeign {
    @RequestMapping("/cache/hmset/{key}")
    void setHashMapByMap(@PathVariable("key") String key,@RequestParam("map") Map<String, Object> map);

    @DeleteMapping("/cache/delete/{keys}")
    void del(@PathVariable("keys") String... keys);

    //不能忘记/cache，必须路径补全
    @RequestMapping("/cache/string/set/{key}/{value}")
    void setString(@PathVariable("key") String key, @PathVariable("value") String value);

    @RequestMapping("/cache/hget/{key}")
    Map<String, Object> hGet(@PathVariable("key") String key);

    @RequestMapping("/cache/string/get/{key}")
    String getString(@PathVariable("key") String key);

    @RequestMapping("/cache/string/set/object/{key}/{value}")
     void setStringObject(@PathVariable("key") String key, @PathVariable("value") Object value);
}

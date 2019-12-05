package com.qianfeng.smsplatform.webmaster.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "CACHE-SERVICE")
public interface CacheFeign {
    @RequestMapping("/cache/hmset/{key}")
    void setHashMapByMap(@PathVariable("key") String key,@RequestParam("map") Map<String, Object> map);

    @RequestMapping("/cache/string/{key}/{value}")
    void listSet(@PathVariable("key") String key, @PathVariable("value") String value);

    @RequestMapping("/cache/string/{key}/{value}/{expireTime}")
    Boolean set(@PathVariable("key") String key, @PathVariable("value") Object value,@PathVariable("expireTime") int expireTime);

    @DeleteMapping("/cache/delete/{keys}")
    void del(@PathVariable("keys") String... keys);

    @RequestMapping("/cache/string/expire/{key}/{seconds}")
    boolean expire(@PathVariable("key") String key, @PathVariable("seconds") long seconds);

    @RequestMapping("/cache/string/incr/{key}/{delta}")
    long incr(@PathVariable("key") String key, @PathVariable("delta") long delta);

    @RequestMapping("/cache/string/decr/{key}/{delta}")
    long decr(@PathVariable("key") String key, @PathVariable("delta") long delta);

    @RequestMapping("/cache/string/{pattern}")
    Set<String> keys(@PathVariable("pattern") String pattern);

    //不能忘记/cache，必须路径补全
    @RequestMapping("/cache/string/set/{key}/{value}")
    void setString(@PathVariable("key") String key, @PathVariable("value") String value);

    @RequestMapping("/cache/hget/{key}")
    Map<String, Object> hGet(@PathVariable("key") String key);

    @RequestMapping("/cache/string/get/{key}")
    String getString(@PathVariable("key") String key);
}

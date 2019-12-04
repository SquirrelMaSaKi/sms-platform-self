package com.qianfeng.smsplatform.webmaster.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "CACHE-SERVICE")
public interface CacheFeign {
    @RequestMapping("/cache/hset/{key}/{param_json}")
    void hmset(@PathVariable("key") String key, @PathVariable("param_json") String param_json);

    @RequestMapping("/cache/string/{key}/{value}")
    void listSet(@PathVariable("key") String key, @PathVariable("value") String value);

    @RequestMapping("/cache/string/{key}/{value}/{expireTime}")
    Boolean set(@PathVariable("key") String key, @PathVariable("value") Object value,@PathVariable("expireTime") int expireTime);

    @RequestMapping("/cache/delete/{keys}")
    void del(@PathVariable("keys") String... keys);

    @RequestMapping("/cache/string/expire/{key}/{seconds}")
    boolean expire(@PathVariable("key") String key, @PathVariable("seconds") long seconds);

    @RequestMapping("/cache/string/incr/{key}/{delta}")
    long incr(@PathVariable("key") String key, @PathVariable("delta") long delta);

    @RequestMapping("/cache/string/decr/{key}/{delta}")
    long decr(@PathVariable("key") String key, @PathVariable("delta") long delta);

    @RequestMapping("/cache/string/{pattern}")
    Set<String> keys(@PathVariable("pattern") String pattern);

    @RequestMapping("/cache/hget/{key}")
    Map<String, Object> hGet(@PathVariable("key") String key);
}

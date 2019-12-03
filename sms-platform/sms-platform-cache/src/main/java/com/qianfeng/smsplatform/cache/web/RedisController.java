package com.qianfeng.smsplatform.cache.web;

import com.alibaba.fastjson.JSONObject;
import com.qianfeng.smsplatform.cache.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @author damon
 * @Classname RedisController
 * @Date 2019/12/3 10:09
 * @Description TODO
 */
@RestController
@RequestMapping(value = "/cache")
@Api(value = "缓存controller", tags = {"缓存操作接口"})
public class RedisController {

    @Autowired
    private CacheService cacheService;

    @ApiOperation(value = "HashMap查询接口")
    @GetMapping("/hget/{key}")
    public Map<Object, Object> hget(@ApiParam(name = "key",value = "输入hashmap的key",defaultValue = "damon_test") @PathVariable String key){
        return cacheService.hmget(key);
    }

    @ApiOperation(value = "HashMap插入接口")
    @PostMapping("/hset/{key}/{param_json}")
    public void hmset(@ApiParam(name = "key",value = "输入hashmap的key",defaultValue = "damon_test") @PathVariable String key,
                      @ApiParam(name = "param_json",value = "输入hashmap的map",defaultValue = "{\"name\":\"damon\",\"age\":\"18\"}") @PathVariable String param_json){
        JSONObject map = JSONObject.parseObject(param_json);
        cacheService.hmset(key,map);
    }

    @ApiOperation(value = "String插入接口")
    @PostMapping("/string/{key}/{value}")
    public void listSet(@ApiParam(name = "key",value = "输入String的key",defaultValue = "five_String") @PathVariable String key,
                        @ApiParam(name = "value",value = "输入String的value",defaultValue = "test1") @PathVariable String value){
        cacheService.set(key,value);
    }

    @ApiOperation(value = "String查询接口")
    @GetMapping("/string/{key}")
    public String getList(@ApiParam(name = "key",value = "输入list的key",defaultValue = "five_String") @PathVariable String key){
        return cacheService.get(key);
    }

    @RequestMapping("/string/{key}/{value}/{expireTime}")
    public Boolean set(@PathVariable String key, @PathVariable Object value,@PathVariable int expireTime){
        return cacheService.set(key, value, expireTime);
    }

    @DeleteMapping("/delete/{keys}")
    public void del(@PathVariable String... keys){
        cacheService.del(keys);
    }

    @RequestMapping("/string/expire/{key}/{seconds}")
    public boolean expire(String key, long seconds){
        return cacheService.expire(key,seconds);
    }

    @RequestMapping("/string/getexpire/{key}")
    public long getExpire(String key){
        return cacheService.getExpire(key);
    }

    @RequestMapping("/string/incr/{key}/{delta}")
    public long incr(String key, long delta){
        return cacheService.incr(key,delta);
    }

    @RequestMapping("/string/decr/{key}/delta")
    public long decr(String key, long delta){
        return cacheService.decr(key, delta);
    }

    @RequestMapping("/string/{pattern}")
    public Set<String> keys(String pattern){
        return cacheService.keys(pattern);
    }


}

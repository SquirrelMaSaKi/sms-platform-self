package com.qianfeng.smsplatform.cache.web;

import com.alibaba.fastjson.JSONObject;
import com.qianfeng.smsplatform.cache.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @RequestMapping("/hget/{key}")
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

    @ApiOperation(value = "List插入接口")
    @PostMapping("/list/{key}/{value}")
    public void listSet(@ApiParam(name = "key",value = "输入list的key",defaultValue = "five_list_") @PathVariable String key,
                        @ApiParam(name = "value",value = "输入list的value",defaultValue = "damon_test") @PathVariable String list){


    }


    
}

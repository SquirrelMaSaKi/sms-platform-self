package com.qianfeng.smsplatform.cache.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.cache.CacheServiceApplication;
import com.qianfeng.smsplatform.cache.common.constants.CacheConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;

/**
 * @author damon
 * @Classname CacheServiceImplTest
 * @Date 2019/12/3 10:01
 * @Description TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CacheServiceApplication.class)
@WebAppConfiguration
public class CacheServiceImplTest {

    @Autowired
    ObjectMapper objectMapper;


    /*private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }*/

    @Autowired CacheService cacheService;


    @Test
    public void test(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("ipddress", "127.0.0.1");
        map.put("pwd", "155659099E18D8983C522D8FC91BB09E");
        map.put("isreturnstatus",1);
        HashMap<String, Object> map5 = new HashMap<>();
        map.put("ipddress", "127.0.0.2");
        map.put("pwd", "155659092356GOINE3C522D8FC91BB09E");
        map.put("isreturnstatus",1);
        cacheService.hmset(CacheConstants.CACHE_PREFIX_CLIENT + "client-01",map);
        cacheService.hmset(CacheConstants.CACHE_PREFIX_CLIENT + "client-02",map5);

        //=================
        cacheService.set(CacheConstants.CACHE_PREFIX_PHASE + "1370101", "8&58");
        cacheService.set(CacheConstants.CACHE_PREFIX_PHASE + "1370102", "8&59");
        cacheService.set(CacheConstants.CACHE_PREFIX_PHASE + "1370104", "8&60");

        //===============
        cacheService.set(CacheConstants.CACHE_PREFIX_BLACK + "13800131000","1");
        cacheService.set(CacheConstants.CACHE_PREFIX_BLACK + "13800132000","2");
        cacheService.set(CacheConstants.CACHE_PREFIX_BLACK + "13800133000","3");
        cacheService.set(CacheConstants.CACHE_PREFIX_BLACK + "13800134000","4");

        //================
        cacheService.set(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE + "15368952 ","1000000");
        cacheService.set(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE + "15324342 ","1000000");
        cacheService.set(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE + "51235423 ","1000000");

        //==========
        HashMap<String, Object> map2 = new HashMap<>();
        map.put("extendnumber", "123");
        map.put("channelid", "1");
        map.put("price",50);

        HashMap<String, Object> map3 = new HashMap<>();
        map.put("extendnumber", "124");
        map.put("channelid", "2");
        map.put("price",100);

        cacheService.hmset(CacheConstants.CACHE_PREFIX_ROUTER + "1001",map2);
        cacheService.hmset(CacheConstants.CACHE_PREFIX_ROUTER + "1002",map3);

        //=======
        cacheService.set(CacheConstants.CACHE_PREFIX_DIRTYWORDS + "卧槽", "1");
        cacheService.set(CacheConstants.CACHE_PREFIX_DIRTYWORDS + "尼玛", "1");
        cacheService.set(CacheConstants.CACHE_PREFIX_DIRTYWORDS + "文科状元", "1");

    }


    @Test
    public void testSet(){
        HashMap map = new HashMap();
        map.put("pwd","666");
        map.put("ipAddress","10.9.21.230");
        map.put("url","http://10.9.21.230:9091/client/receive");
        map.put("isReturnStatus","1");

        cacheService.hmset("9278",map );
    }

    @Test
    public void get(){
        String aaaa = cacheService.get("aaaa");
        System.err.println(aaaa);
    }

    @Test
    public void set(){
        cacheService.set("aaadfefa", "aaa", 60);
    }
}

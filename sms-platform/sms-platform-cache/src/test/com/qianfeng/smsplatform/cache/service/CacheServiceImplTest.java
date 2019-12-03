package com.qianfeng.smsplatform.cache.service;

import com.qianfeng.smsplatform.cache.CacheServiceApplication;
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

    public void test(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("ipddress", "127.0.0.1");
        map.put("pwd", "155659099E18D8983C522D8FC91BB09E");
        map.put("isreturnstatus",1);
        map.put("ipddress", "127.0.0.2");
        map.put("pwd", "155659092356GOINE3C522D8FC91BB09E");
        map.put("isreturnstatus",1);
        cacheService.hmset("client-01",map);
        cacheService.hmset("client-02",map);

        //=================
        cacheService.set("PHASE:1370101", "8&58");
        cacheService.set("PHASE:1370102", "8&59");
        cacheService.set("PHASE:1370104", "8&60");

        //===============
        cacheService.set("BLACK:13800131000","1");
        cacheService.set("BLACK:13800132000","2");
        cacheService.set("BLACK:13800133000","3");
        cacheService.set("BLACK:13800134000","4");

        //================
        cacheService.set("CUSTOMER_FEE:15368952 ","1000000");
        cacheService.set("CUSTOMER_FEE:15324342 ","1000000");
        cacheService.set("CUSTOMER_FEE:51235423 ","1000000");

        //==========
        HashMap<String, Object> map2 = new HashMap<>();
        map.put("extendnumber", "123");
        map.put("channelid", "1");
        map.put("price",50);

        HashMap<String, Object> map3 = new HashMap<>();
        map.put("extendnumber", "124");
        map.put("channelid", "2");
        map.put("price",100);

        cacheService.hmset("ROUTER:1001",map2);
        cacheService.hmset("ROUTER:1002",map3);

        //=======


    }
}

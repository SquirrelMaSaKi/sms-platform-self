package com.qianfeng.smsplatform.cache.service;

import com.qianfeng.smsplatform.cache.CacheServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

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
    private CacheService cacheService;

    @Test
    public void testSetAndGet(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("boy","damon");
        map.put("girl","lin");
        cacheService.hmset("damon-test",map);
        Map<Object, Object> hmget = cacheService.hmget("damon-test");
        Map<Object, Object> map2 = cacheService.hmget("damon-test");
        System.err.println();
    }


}

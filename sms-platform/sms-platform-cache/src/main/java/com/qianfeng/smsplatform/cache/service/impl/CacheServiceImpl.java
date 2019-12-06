package com.qianfeng.smsplatform.cache.service.impl;

import com.qianfeng.smsplatform.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author damon
 * @Classname CacheServiceImpl
 * @Date 2019/12/3 9:33
 * @Description TODO
 */
@Service
public class CacheServiceImpl implements CacheService {

    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean set(String key, Object value, int expireTime) {
        redisTemplate.opsForValue().set(key,value);
        return true;
    }

    @Override
    public Boolean set(String key, String value, int expireTime) {
        redisTemplate.opsForValue().set(key,value,expireTime, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Boolean set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
        return true;
    }

    @Override
    public Boolean set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
        return true;
    }

    @Override
    public String get(String key) {
        return  ""+redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getAndSet(String key, String value) {

        return null;
    }

    @Override
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    @Override
    public void del(String... keys) {
        List<String> keylist = Arrays.asList(keys);
        redisTemplate.delete(keylist);
    }

    @Override
    public boolean expire(String key, long seconds) {
        return redisTemplate.expire(key,seconds,TimeUnit.SECONDS);
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public long incr(String key, long delta) {
        Object o = redisTemplate.opsForValue().get(key);
        if(o instanceof Number){
            return redisTemplate.opsForValue().increment(key, delta);
        }
        throw new RuntimeException("用户余额不为数字。");
    }

    @Override
    public long decr(String key, long delta) {
        Object o = redisTemplate.opsForValue().get(key);
        if(o instanceof Number){
            return redisTemplate.opsForValue().increment(key, delta);
        }
        throw new RuntimeException("用户余额不为数字。");
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            hashOperations.put(key,stringObjectEntry.getKey(),stringObjectEntry.getValue()+"");
        }
        return true;
    }
}

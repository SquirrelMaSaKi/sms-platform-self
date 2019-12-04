package com.qianfeng.smsplatform.userinterface.feign;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ---  2019/12/3 --- 20:14
 * --天神佑我：写代码，无BUG
 */
@Component
public class CacheServcieFallBack implements CacheServcie {

    @Override
    public Map<Object, Object> hGet(String key) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status", "500");
        map.put("message", "服务繁忙请重试");
        return map;
    }
}

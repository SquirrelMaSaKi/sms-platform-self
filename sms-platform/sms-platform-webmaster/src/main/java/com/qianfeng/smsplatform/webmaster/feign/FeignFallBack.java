package com.qianfeng.smsplatform.webmaster.feign;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component  //必须添加这个注解,否则无法创建这个对象,就无法给feign客户端注入这个对象
public class FeignFallBack implements CacheFeign{
    @Override
    public void setHashMapByMap(String key, Map<String, Object> map) {

    }

    @Override
    public void del(String... keys) {

    }

    @Override
    public void setString(String key, String value) {

    }

    @Override
    public Map<String, Object> hGet(String key) {
        return null;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public void setStringObject(String key, Object value) {

    }
}

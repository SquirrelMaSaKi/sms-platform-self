package com.qianfeng.smsplatform.webmaster;

import com.alibaba.fastjson.JSONObject;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TClientBusiness;
import com.qianfeng.smsplatform.webmaster.service.ChannelService;
import com.qianfeng.smsplatform.webmaster.service.ClientBusinessService;
import com.qianfeng.smsplatform.webmaster.util.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SmsPlatformWebManagerApplication.class)
@WebAppConfiguration
public class SmsPlatformWebManagerTest {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private ClientBusinessService clientBusinessService;

    @Autowired
    private CacheFeign cacheFeign;

    private Logger logger = LoggerFactory.getLogger(SmsPlatformWebManagerTest.class);

    @Test
    public void testFindAllChannelIds() {
        List<Long> ids = channelService.TChannel_allIds();
        for (Long id : ids) {
            System.err.println("channel的id是：" + id);
        }
    }

    @Test
    public void testAddClient() {
        TClientBusiness tClientBusiness = new TClientBusiness();
        tClientBusiness.setCorpname("aaa");
        tClientBusiness.setUsercode("gp");
        tClientBusiness.setPwd("gp");
        tClientBusiness.setIpaddress("127.0.0.1");
        tClientBusiness.setIsreturnstatus((byte) 1);
        tClientBusiness.setReceivestatusurl("http://localhost:8099/receive/status");
        tClientBusiness.setPriority((byte) 0);
        tClientBusiness.setUsertype((byte) 2);
        tClientBusiness.setState(1);
        tClientBusiness.setMobile("13456785678");
        Map<String, String> stringStringMap = JsonUtils.objectToMap(tClientBusiness);
        String s = JSONObject.toJSONString(stringStringMap);
        System.out.println(s);
        System.out.println(CacheConstants.CACHE_PREFIX_CLIENT+1+"");
        cacheFeign.hmset(CacheConstants.CACHE_PREFIX_CLIENT+3+"", s);

        //int i = clientBusinessService.addClientBusiness(tClientBusiness);
        //log.debug("插入数据：" + i);
    }

    @Test
    public void testGetObject() {
        Map<String, Object> stringObjectMap = cacheFeign.hGet(CacheConstants.CACHE_PREFIX_CLIENT + "client-01");
        for (Map.Entry<String, Object> stringObjectEntry : stringObjectMap.entrySet()) {
            String key = stringObjectEntry.getKey();
            String value = (String) stringObjectEntry.getValue();
            System.err.println(key);
            System.err.println(value);
        }
    }
}

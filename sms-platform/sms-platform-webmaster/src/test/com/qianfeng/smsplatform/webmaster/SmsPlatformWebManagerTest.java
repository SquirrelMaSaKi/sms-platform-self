package com.qianfeng.smsplatform.webmaster;

import com.alibaba.fastjson.JSONObject;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dao.TClientChannelMapper;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TClientBusiness;
import com.qianfeng.smsplatform.webmaster.pojo.TClientChannel;
import com.qianfeng.smsplatform.webmaster.service.ChannelService;
import com.qianfeng.smsplatform.webmaster.service.ClientBusinessService;
import com.qianfeng.smsplatform.webmaster.service.ClientChannelService;
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
    private ClientChannelService clientChannelService;

    @Autowired
    private TClientChannelMapper tClientChannelMapper;

    @Autowired
    private CacheFeign cacheFeign;

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
        tClientBusiness.setId((long) 15);
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
        Map<String, Object> stringStringMap = JsonUtils.object2Map(tClientBusiness);
        cacheFeign.setHashMapByMap(CacheConstants.CACHE_PREFIX_CLIENT+tClientBusiness.getId(), stringStringMap);
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

    @Test
    public void testFindClientChannelByClientId() {
//        TClientChannel byClientId = clientChannelService.findByClientId((long) 0);
//        System.err.println(byClientId.getPrice());
        TClientChannel tClientChannel = tClientChannelMapper.selectByClientId((long) 0);
        System.err.println(tClientChannel.getPrice());
    }
}

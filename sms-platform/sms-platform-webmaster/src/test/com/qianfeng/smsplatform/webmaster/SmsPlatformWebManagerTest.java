package com.qianfeng.smsplatform.webmaster;

import com.qianfeng.smsplatform.webmaster.service.ChannelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SmsPlatformWebManagerApplication.class)
@WebAppConfiguration
public class SmsPlatformWebManagerTest {
    @Autowired
    private ChannelService channelService;

    @Test
    public void testFindAllChannelIds() {
        List<Long> ids = channelService.TChannel_allIds();
        for (Long id : ids) {
            System.err.println("channel的id是：" + id);
        }
    }
}

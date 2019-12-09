package com.qianfeng.smsplatform.webmaster;

import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dao.TClientChannelMapper;
import com.qianfeng.smsplatform.webmaster.dao.TInstMapper;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.*;
import com.qianfeng.smsplatform.webmaster.service.*;
import com.qianfeng.smsplatform.webmaster.util.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private TClientChannelMapper tClientChannelMapper;

    @Autowired
    private CacheFeign cacheFeign;

    @Autowired
    private BlackService blackService;
    @Autowired
    private ClientBusinessService clientBusinessService;
    @Autowired
    private DirtywordService dirtywordService;
    @Autowired
    private PhaseService phaseService;
    @Autowired
    private TInstMapper tInstMapper;
    @Autowired
    private ClientChannelService clientChannelService;


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


    /**
     * Redis数据预热
     */
    @Test
    public void preHeatRedis() {
        //黑名单
        List<TBlackList> tBlackLists = blackService.findAll();
        for (TBlackList tBlackList : tBlackLists) {
            String string = cacheFeign.getString(CacheConstants.CACHE_PREFIX_BLACK + tBlackList.getMobile());
            if (string==null || string.trim().length() == 0 || string.equals("null")) {
                cacheFeign.setString(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile(), "1");
            }
        }

        //用户
        List<TClientBusiness> tClientBusinesses = clientBusinessService.findAll();
        for (TClientBusiness tClientBusiness : tClientBusinesses) {
            Map<String, Object> map = cacheFeign.hGet(CacheConstants.CACHE_PREFIX_CLIENT + tClientBusiness.getId());
            if (map==null || map.size() == 0) {
                Map<String, Object> map1 = JsonUtils.object2Map(tClientBusiness);
                cacheFeign.setHashMapByMap(CacheConstants.CACHE_PREFIX_CLIENT+tClientBusiness.getId(), map1);
            }
        }

        //脏词
        List<TDirtyword> tDirtywords = dirtywordService.findAll();
        for (TDirtyword tDirtyword : tDirtywords) {
            String string = cacheFeign.getString(CacheConstants.CACHE_PREFIX_DIRTYWORDS + tDirtyword.getDirtyword());
            if (string==null || string.trim().length()==0 || string.equals("null")) {
                cacheFeign.setString(CacheConstants.CACHE_PREFIX_DIRTYWORDS+tDirtyword.getDirtyword(), "1");
            }
        }

        //号段
        List<TPhase> tPhases = phaseService.findALL();
        for (TPhase message : tPhases) {
            Long provId = message.getProvId();
            TInst tInst1 = tInstMapper.selectByPrimaryKey(provId);
            message.setProvName(tInst1.getAreaname());
            Long cityId = message.getCityId();
            TInstExample tInstExample = new TInstExample();
            TInstExample.Criteria criteria = tInstExample.createCriteria();
            criteria.andIdEqualTo(cityId);
            criteria.andParentidEqualTo(provId);
            List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
            if(tInsts!=null&&tInsts.size()>0){
                TInst tInst = tInsts.get(0);
                message.setCityName(tInst.getAreaname());
            }

            //批量放入缓存
            String string = cacheFeign.getString(CacheConstants.CACHE_PREFIX_PHASE+message.getPhase());
            if (string==null || string.trim().length()==0 || string.equals("null")) {
                cacheFeign.setString(CacheConstants.CACHE_PREFIX_PHASE+message.getPhase(), provId+"&"+cityId);
            }
        }

        //路由信息
        List<TClientChannel> tClientChannels = clientChannelService.findAll();
        for (TClientChannel tClientChannel : tClientChannels) {
            Long clientid = tClientChannel.getClientid();
            TClientBusiness tClientBusiness = clientBusinessService.findById(clientid);
            String corpname = tClientBusiness.getCorpname();
            tClientChannel.setCorpname(corpname);
            long channelid = (long)tClientChannel.getChannelid();
            TChannel tChannel= channelService.findById(channelid);
            String channelname = tChannel.getChannelname();
            tClientChannel.setChannelname(channelname);

            //同步到缓存
            Map<String, Object> stringObjectMap = cacheFeign.hGet(CacheConstants.CACHE_PREFIX_ROUTER + tClientChannel.getClientid());
            if (stringObjectMap == null || stringObjectMap.size() == 0) {
                Map<String, Object> stringObjectMap1 = JsonUtils.object2Map(tClientChannel);
                cacheFeign.setHashMapByMap(CacheConstants.CACHE_PREFIX_ROUTER+tClientChannel.getClientid(), stringObjectMap1);
            }
        }
    }
}

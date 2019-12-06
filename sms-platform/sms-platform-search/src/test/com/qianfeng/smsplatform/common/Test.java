package com.qianfeng.smsplatform.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.search.SearchServiceApplication;
import com.qianfeng.smsplatform.search.model.Standard_Submit;
import com.qianfeng.smsplatform.search.service.SearchApi;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.HashMap;

/**
 * @author damon
 * @Classname Test
 * @Date 2019/12/4 19:46
 * @Description TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class Test {

    @Autowired
    private SearchApi searchApi;
    @Autowired
    ObjectMapper objectMapper;

    @org.junit.Test
    public void testCreateIndex() throws Exception {
        searchApi.createIndex();
    }

    @org.junit.Test
    public void testDeleteIndex() throws Exception {
        searchApi.deleteIndex("sms_submit_log_five");
    }

    @org.junit.Test
    public void testInsert() throws Exception {
        Standard_Submit bean = new Standard_Submit();
        bean.setClientID(17700000);
        bean.setSrcNumber("15180452349");
        bean.setMessagePriority((short)12);
        bean.setSrcSequenceId(12348765);
        bean.setGatewayID(7777);
        bean.setProductID(7777);
        bean.setDestMobile("7777777");
        bean.setMessageContent("88888");
        bean.setErrorCode("500");
        bean.setSendTime(new Date());
        bean.setOperatorId(7777);
        bean.setProvinceId(2357);
        bean.setCityId(5);
        bean.setSource(1);
        String json = objectMapper.writeValueAsString(bean);
        searchApi.add(json);
    }

    @org.junit.Test
    public void testGet() throws Exception {
        HashMap map = new HashMap();
        map.put("cityId",5);
        String json = objectMapper.writeValueAsString(map);
        searchApi.search(json);
    }
}

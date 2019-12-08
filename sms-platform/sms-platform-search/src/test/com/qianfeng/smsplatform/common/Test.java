package com.qianfeng.smsplatform.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.SearchServiceApplication;
import com.qianfeng.smsplatform.search.service.SearchApi;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        bean.setClientID(17700005);
        bean.setSrcNumber("15180452349");
        bean.setMessagePriority((short)11);
        bean.setSrcSequenceId(12348765);
        bean.setGatewayID(7777);
        bean.setProductID(7777);
        bean.setDestMobile("7777777");
        bean.setMessageContent("88888");
        bean.setErrorCode("500");
        bean.setSendTime(new Date());
        bean.setOperatorId(9999);
        bean.setProvinceId(9999);
        bean.setCityId(5);
        bean.setSource(9);
        String json = objectMapper.writeValueAsString(bean);
        /*for (int i = 0; i < 15; i++) {
            bean.setProvinceId(i);
            searchApi.add(json);
        }*/
        searchApi.add(json);
    }

    @org.junit.Test
    public void testGet() throws Exception {
        HashMap map = new HashMap();
        map.put("aaa","aa");
        String json = objectMapper.writeValueAsString(map);
        System.err.println("=====================================");
        System.err.println("===============es总数据数==================");
        System.err.println(searchApi.getCount(json));
        List<Map> search = searchApi.search(json);
        System.err.println();
    }
}
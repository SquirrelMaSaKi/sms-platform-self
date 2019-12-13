package com.qianfeng.smsplatform.common;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.SearchServiceApplication;
import com.qianfeng.smsplatform.search.dto.SmsStatusDTO;
import com.qianfeng.smsplatform.search.service.SearchApi;
import com.qianfeng.smsplatform.search.util.SearchPojo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
        bean.setClientID(0);
        bean.setDestMobile("15180451544");
        bean.setMessageContent("测试2");
        bean.setSendTime(new Date());
        // bean.setErrorCode("500");
        // bean.setSrcNumber("2135234");
        // bean.setSrcSequenceId(12348765);
        // bean.setGatewayID(8888);
        // bean.setProductID(8888);
        // bean.setOperatorId(8888);
        // bean.setProvinceId(8888);
        // bean.setCityId(8);
        // bean.setSource(8);
        String json = objectMapper.writeValueAsString(bean);
        searchApi.add(json);
    }

    @org.junit.Test
    public void testGet() throws Exception {
        SearchPojo searchPojo = new SearchPojo();
        // searchPojo.setKeyword("祖国");
        searchPojo.setStartTime(0L);
        searchPojo.setEndTime(System.currentTimeMillis());
        searchPojo.setStart(0);
        searchPojo.setRows(100);
        String json = objectMapper.writeValueAsString(searchPojo);
        List<Map> search = searchApi.search(json);
        System.err.println();
    }

    @org.junit.Test
    public void test() throws IOException, ParseException {
        SmsStatusDTO smsStatusDTO = new SmsStatusDTO();
        String s = JSON.toJSONString(smsStatusDTO);
        Map<String, Long> stringLongMap = searchApi.stataStatSendStatus(s);
        System.err.println();
    }
}
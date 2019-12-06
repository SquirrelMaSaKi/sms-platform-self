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
        bean.setErrorCode("404");
        bean.setMsgid("3294875");
        bean.setSendTime(new Date());
        String json = objectMapper.writeValueAsString(System.currentTimeMillis());
        searchApi.add(json);
    }


}

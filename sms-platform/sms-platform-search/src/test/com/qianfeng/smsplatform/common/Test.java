package com.qianfeng.smsplatform.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.SearchServiceApplication;
import com.qianfeng.smsplatform.search.service.SearchApi;
import com.qianfeng.smsplatform.search.util.SearchPojo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
        bean.setClientID(17700004);
        bean.setSrcNumber("1518045234");
        bean.setMessagePriority((short)11);
        bean.setSrcSequenceId(12348765);
        bean.setGatewayID(8888);
        bean.setProductID(8888);
        bean.setDestMobile("8888888");
        bean.setMessageContent("新华社澳门12月8日电 题：推进“一国两制”实践在澳门行稳致远——访澳门特区候任行政长官贺一诚\n" +
                "“希望中央政府及各部门能够继续给予澳门特区适当的帮助，更大贡献。" +
                "”贺一诚说。（完）");
        bean.setErrorCode("500");
        bean.setSendTime(new Date());
        bean.setOperatorId(8888);
        bean.setProvinceId(8888);
        bean.setCityId(8);
        bean.setSource(8);
        String json = objectMapper.writeValueAsString(bean);
        for (int i = 1; i < 11; i++) {
            bean.setSrcNumber("1518045234" + i);
            searchApi.add(json);
        }
        searchApi.add(json);
    }

    @org.junit.Test
    public void testGet() throws Exception {
        SearchPojo searchPojo = new SearchPojo();
        // searchPojo.setKeyword("88888");
        searchPojo.setStartTime(0L);
        searchPojo.setEndTime(System.currentTimeMillis());
        searchPojo.setStart(1);
        searchPojo.setRows(10);
        String json = objectMapper.writeValueAsString(searchPojo);
        List<Map> search = searchApi.search(json);
        System.err.println();
    }

    public void test(){
        Date a = new Date();
        long l = Long.parseLong(a.toString());
        System.err.println(l);
    }
}
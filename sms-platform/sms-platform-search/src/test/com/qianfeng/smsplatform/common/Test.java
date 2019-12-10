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
        bean.setClientID(17700005);
        bean.setSrcNumber("1518045234");
        bean.setMessagePriority((short)11);
        bean.setSrcSequenceId(12348765);
        bean.setGatewayID(7777);
        bean.setProductID(7777);
        bean.setDestMobile("7777777");
        bean.setMessageContent("新华社澳门12月8日电 题：推进“一国两制”实践在澳门行稳致远——访澳门特区候任行政长官贺一诚\n" +
                "对于建设以中华文化为主流、多元文化共存的交流合作基地，贺一诚充满信心。化交流传统，不同文化相对立。“澳门将进一步做好文化遗产保护，推动中西文化交流。”\n" +
                "\n" +
                "10月底，全国人大常委会公布了关于授权澳门特别行政区对横琴口岸澳方口岸区及相关延伸区实施管辖的决定。贺一:" +
                        ""+
                "“希望中央政府及各部门能够继续给予澳门特区适当的帮助，促进澳门维护好繁荣稳定。澳门也将努力为国家发展大局作出更大贡献。”贺一诚说。（完）");
        bean.setErrorCode("500");
        bean.setSendTime(new Date());
        bean.setOperatorId(9999);
        bean.setProvinceId(9999);
        bean.setCityId(5);
        bean.setSource(9);
        String json = objectMapper.writeValueAsString(bean);
        for (int i = 29; i < 40; i++) {
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
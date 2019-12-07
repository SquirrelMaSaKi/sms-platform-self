package com.qianfeng.smsplatform.search.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.search.model.Standard_Submit;
import com.qianfeng.smsplatform.search.service.SearchApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author damon
 * @Classname MessageListener
 * @Date 2019/12/6 15:51
 * @Description TODO
 */
@Component
@Slf4j
public class MessageListener {

    @Autowired
    private SearchApi searchApi;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 监听队列
     * @param message
     */
    @RabbitListener(queues = RabbitMqConsants.TOPIC_PRE_SEND,concurrency ="10")
    public void queueListener(Standard_Submit message) throws Exception {
        System.err.println("=====================================================");
        //        Standard_Submit standard_submit = objectMapper.readValue(message, Standard_Submit.class);
        //        json字符串转对象或者转map用,如果从队列中收到的为对象,就不用转了
        String json = objectMapper.writeValueAsString(message);
        searchApi.add(json);
        log.error("-----------------------------------");
        log.error("====================================================================");
        log.error(json);
    }

}

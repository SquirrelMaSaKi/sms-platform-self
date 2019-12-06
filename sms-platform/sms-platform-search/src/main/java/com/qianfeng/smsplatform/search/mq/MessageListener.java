package com.qianfeng.smsplatform.search.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.search.model.Standard_Submit;
import com.qianfeng.smsplatform.search.service.SearchApi;
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
public class MessageListener {

    @Autowired
    private SearchApi searchApi;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 监听队列
     * @param message
     */
    @RabbitListener(queues = RabbitMqConsants.TOPIC_PUSH_SMS_REPORT,concurrency ="10")
    public void queueListener(Standard_Submit message) throws Exception {
        String json = objectMapper.writeValueAsString(message);
        searchApi.add(json);
    }

}

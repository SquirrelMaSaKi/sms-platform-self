package com.qianfeng.smsplatform.search.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
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
     * 监听下发日志队列
     * 采用多线程的方式消费对象
     *
     * @param message
     */
    @RabbitListener(queues = RabbitMqConsants.TOPIC_SMS_SEND_LOG, concurrency = "10")
    public void sendLoglistener(Standard_Submit message) throws Exception {
        //json字符串转对象或者转map用, 如果从队列中收到的为对象, 就不用转了

        //Standard_Submit standard_submit = objectMapper.readValue(message, Standard_Submit.class);
        log.debug("接收到的消息:{}",message);
        String json = objectMapper.writeValueAsString(message);
        searchApi.add(json);
    }

    /**
     * 更新状态报告
     * @param report
     * @throws Exception
     */
    @RabbitListener(queues = RabbitMqConsants.TOPIC_PUSH_SMS_REPORT, concurrency = "10")
    public void reportQueueListener(Standard_Report report) throws Exception {
        Standard_Submit submit = new Standard_Submit();
        submit.setDestMobile(report.getMobile());
        submit.setReportState(report.getState());
        submit.setErrorCode(report.getErrorCode());
        submit.setSrcNumber(report.getSrcID()+"");
        submit.setClientID((int) report.getClientID());
        submit.setMsgid(report.getMsgId());
        // 调用search模块
        searchApi.update(submit);
    }

}

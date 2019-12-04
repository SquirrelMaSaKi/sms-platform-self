package com.qianfeng.smsplatform.userinterface.servlet;

import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Report;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ---  2019/12/4 --- 19:17
 * --天神佑我：写代码，无BUG
 */
@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/test01")
    public String test01(){
        Standard_Report report = new Standard_Report();
        report.setClientID(9277);
        report.setMobile("18166037969");
        report.setErrorCode("200");
        report.setSrcID(9278);
        amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_PUSH_SMS_REPORT,report);
        return "haole";
    }
}

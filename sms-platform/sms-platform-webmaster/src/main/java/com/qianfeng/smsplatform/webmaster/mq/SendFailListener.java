package com.qianfeng.smsplatform.webmaster.mq;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.webmaster.service.TReportService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendFailListener {
    @Autowired
    private TReportService tReportService;

    //监听发送失败队列
    @RabbitListener()
    public void getSendFailMessage(Standard_Report report) {

    }
}

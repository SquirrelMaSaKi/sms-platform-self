package com.qianfeng.smsplatform.userinterface.mq;

import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.userinterface.feign.CacheServcie;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * ---  2019/12/3 --- 19:32
 * --天神佑我：写代码，无BUG
 */
//@Component
public class PushMessageListener {

    @Autowired
    private CacheServcie cacheServcie;

    //监听push对列
    @RabbitListener(queues = RabbitMqConsants.TOPIC_SMS_SEND_LOG,concurrency ="10")
    public void getPushStatus(Standard_Submit message) {
        System.err.println("11111111" + message);
        Integer source = message.getSource();

        if (source == 0) {
            return;
        }

        int clientID = message.getClientID();
        String id = clientID + "";
        Map<Object, Object> map = cacheServcie.hGet(id);
        String url = (String)map.get("url");
    }

}

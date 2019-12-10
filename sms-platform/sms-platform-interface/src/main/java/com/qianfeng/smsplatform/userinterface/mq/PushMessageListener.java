package com.qianfeng.smsplatform.userinterface.mq;

import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.userinterface.feign.CacheServcie;
import com.qianfeng.smsplatform.userinterface.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.qianfeng.smsplatform.common.constants.CacheConstants.CACHE_PREFIX_CLIENT;

/**
 * ---  2019/12/3 --- 19:32
 * --天神佑我：写代码，无BUG
 */
@Slf4j
@Component
public class PushMessageListener {

    @Autowired
    private CacheServcie cacheServcie;


    @Autowired
    private AmqpTemplate amqpTemplate;
    //监听push对列
    @RabbitListener(queues = RabbitMqConsants.TOPIC_PUSH_SMS_REPORT,concurrency ="10")
    public void getPushStatus(Standard_Report message) {
        log.error("22323" + message);
        long clientID = message.getClientID();
        String id = CACHE_PREFIX_CLIENT + clientID;
        Map<Object, Object> map = cacheServcie.hGet(id);
        String isReturnStatus = (String) map.get("isreturnstatus");

        //判断是否需要发送
        if (!"1".equals(isReturnStatus)) {
            return;
        }

        String url = (String)map.get("receivestatusurl");
        long srcID = message.getSrcID();
        String errorCode = message.getErrorCode();
        int state = message.getState();
        String mobile = message.getMobile();

        //取出要传过去的参数
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("srcID", srcID+"");
        map1.put("errorCode", errorCode);
        map1.put("state", state+"");
        map1.put("mobile", mobile);
        String s = HttpClientUtil.doPost(url, map1);
        System.err.println("推送状态>>>>>>"+s);

        //发送不成功，重新进对列
        if ("error".equalsIgnoreCase(s)) {
            message.setSendCount(message.getSendCount() + 1);
            //如果推送了两次都么有发过去  就进推送失败对列
            if (message.getSendCount() > 2) {
                amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_PUSH_ERROR, message);
                return;
            }
            //重新进PUSH对列  重新发送
            amqpTemplate.convertAndSend(RabbitMqConsants.TOPIC_PUSH_SMS_REPORT, message);
            System.err.println("压入PUSH栈");
        }
    }

}

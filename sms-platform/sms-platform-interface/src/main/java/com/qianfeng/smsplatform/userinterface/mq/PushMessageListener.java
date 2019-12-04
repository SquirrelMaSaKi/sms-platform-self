package com.qianfeng.smsplatform.userinterface.mq;

import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.userinterface.feign.CacheServcie;
import com.qianfeng.smsplatform.userinterface.utils.HttpClientUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ---  2019/12/3 --- 19:32
 * --天神佑我：写代码，无BUG
 */
@Component
public class PushMessageListener {

    @Autowired
    private CacheServcie cacheServcie;

    //监听push对列
    @RabbitListener(queues = RabbitMqConsants.TOPIC_PUSH_SMS_REPORT,concurrency ="10")
    public void getPushStatus(Standard_Report message) {
        System.err.println("11111111" + message);
        long clientID = message.getClientID();
        String id = clientID + "";
        Map<Object, Object> map = cacheServcie.hGet(id);
        String isReturnStatus = (String) map.get("isReturnStatus");
        if (!isReturnStatus.equals("1")) {
            return;
        }
        String url = (String)map.get("url");
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
        System.err.println(">>>>>>"+s);
    }

}

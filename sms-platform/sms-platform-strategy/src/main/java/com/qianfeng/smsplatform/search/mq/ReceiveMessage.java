package com.qianfeng.smsplatform.search.mq;

import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.service.FilterService;
import com.qianfeng.smsplatform.search.service.Impl.BlackFilterService;
import com.qianfeng.smsplatform.search.service.Impl.DirtyFilterService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Map;

import static com.qianfeng.smsplatform.common.constants.RabbitMqConsants.*;
import static com.qianfeng.smsplatform.common.constants.StrategyConstants.STRATEGY_ERROR_DIRTYWORDS;

/*
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；


*裴少泊的修仙之路
*描述：
*/
@Slf4j
@Component
@RabbitListener(queues = TOPIC_PRE_SEND,concurrency = "10")   //多线程
public class ReceiveMessage {
    @Autowired
    private Map<String, FilterService> filterServicesMap;    //所有实现了FilterService接口的类对象都会在map中,key为servicename,value为对象

    @Autowired
    private SendMessage send;

    @Value("${smsplatform.filters}")
    private String str;   //配置文件中获取出的为string字符串,需要自行分割

    @RabbitHandler
    public void receive(Standard_Submit message) throws Exception {
//        Standard_Submit standard_submit = objectMapper.readValue(message, Standard_Submit.class);
//        json字符串转对象或者转map用,如果从队列中收到的为对象,就不用转了


//        System.out.println("收到了消息===>"+standard_submit);
        log.error("errorcode:" + message.getErrorCode());
        log.error(message.getMessageContent());
        log.error("result",String.valueOf(message.getClientID()));

        String[] split = str.split(",");


        for (int i = 0; i < split.length; i++) {
//          split[i]是配置文件或redis中的过滤器中一一获取servicename即过滤器名字
            message = filterServicesMap.get(split[i]).filtrate(message);   //通过获取到的名字也就是key,去获取value(value是对应service的对象)
            if (message.getErrorCode() != null) {
                System.out.println("写入下发日志");
                send.sendMessage(TOPIC_SMS_SEND_LOG, message);
                return;
            }
        }
        System.out.println("传入网关队列");
        send.sendMessage(TOPIC_SMS_GATEWAY,message);


    }
}

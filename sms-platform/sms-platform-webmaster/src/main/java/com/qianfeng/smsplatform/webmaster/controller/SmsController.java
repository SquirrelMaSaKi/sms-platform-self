package com.qianfeng.smsplatform.webmaster.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qianfeng.smsplatform.common.constants.RabbitMqConsants;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.webmaster.dto.SmsDTO;
import com.qianfeng.smsplatform.webmaster.pojo.TAdminUser;
import com.qianfeng.smsplatform.webmaster.service.AdminUserService;
import com.qianfeng.smsplatform.webmaster.util.R;
import com.qianfeng.smsplatform.webmaster.util.ShiroUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class SmsController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private AdminUserService adminUserService;

    @ResponseBody
    @RequestMapping("/sys/sms/save")
    public R addBlack(@RequestBody SmsDTO smsDTO) throws JsonProcessingException {
        String mobile = smsDTO.getMobile();
        String content = smsDTO.getContent();
        Integer userId = ShiroUtils.getUserId();
        TAdminUser tAdminUser = adminUserService.findById(userId);
        String[] mobiles = mobile.split("\\n");
        for (int i = 0; i < mobiles.length; i++) {
            Standard_Submit submit = new Standard_Submit();
            submit.setClientID(tAdminUser.getClientid()); //客户端id
            submit.setDestMobile(mobiles[i]); //手机号
            submit.setMessageContent(content); //内容
            submit.setSource(2);
            submit.setSendTime(new Date());
            rabbitTemplate.convertAndSend(RabbitMqConsants.TOPIC_PRE_SEND, submit);
        }
        return R.ok();
    }
}

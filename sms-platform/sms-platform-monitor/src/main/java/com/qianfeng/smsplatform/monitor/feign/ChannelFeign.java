package com.qianfeng.smsplatform.monitor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "SMS-PLATFORM-WEBMASTER")
public interface ChannelFeign {
    @RequestMapping("/sys/channel/ids")
    List<Long> getChannelIds();
}

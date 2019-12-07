package com.qianfeng.smsplatform.monitor.feign;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "SMS-PLATFORM-WEBMASTER")
public interface ChannelFeign {
    @RequestMapping("/sys/channel/ids")
    List<Long> getChannelIds();

    @RequestMapping("/sys/report")
    List<Standard_Report> findAll();

    @RequestMapping("/sys/delete")
    int deleteReport();
}

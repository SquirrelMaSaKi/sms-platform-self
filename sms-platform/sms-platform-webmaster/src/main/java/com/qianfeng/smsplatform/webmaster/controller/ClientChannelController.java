package com.qianfeng.smsplatform.webmaster.controller;

import com.qianfeng.smsplatform.webmaster.dto.DataGridResult;
import com.qianfeng.smsplatform.webmaster.dto.QueryDTO;
import com.qianfeng.smsplatform.webmaster.pojo.TClientChannel;
import com.qianfeng.smsplatform.webmaster.service.ClientChannelService;
import com.qianfeng.smsplatform.webmaster.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClientChannelController {

    @Autowired
    private ClientChannelService clientChannelService;

    @ResponseBody
    @RequestMapping("/sys/clientchannel/list")
    public DataGridResult findClientChannel(QueryDTO queryDTO) {
        return clientChannelService.findByPage(queryDTO);
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/del")
    public R delClientChannel(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            clientChannelService.delClientChannel(id);
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TClientChannel tClientChannel = clientChannelService.findById(id);
        return R.ok().put("clientchannel", tClientChannel);
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/save")
    public R addClientChannel(@RequestBody TClientChannel tClientChannel) {
        //这里需要判断，因为路由更改的时候，选择的客户是ClientID，一旦重复，则redis中会出现问题
        //我们要保证这个用户必须注册了这个平台，这个判断主要针对的是管理员，这个客户必须接入了客户管理才可以
        //进行重复排除处理
        TClientChannel tClientChannel_origin = clientChannelService.findByClientId(tClientChannel.getClientid());
        if (tClientChannel_origin != null) {
            return R.error("添加失败，不要重复");
        }
        int i = clientChannelService.addClientChannel(tClientChannel);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping("/sys/clientchannel/update")
    public R updateClientChannel(@RequestBody TClientChannel tClientChannel) {
        //原始数据
        TClientChannel tClientChannel_origin = clientChannelService.findByClientId(tClientChannel.getId());
        if (tClientChannel_origin.getClientid() == tClientChannel.getClientid()) {
            int i = clientChannelService.updateClientChannel(tClientChannel);
            return i > 0 ? R.ok() : R.error("修改失败");
        }
        return R.error("修改失败，不要重复");
    }
}

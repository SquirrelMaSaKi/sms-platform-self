package com.qianfeng.smsplatform.webmaster.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dto.DataGridResult;
import com.qianfeng.smsplatform.webmaster.dto.QueryDTO;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TClientBusiness;
import com.qianfeng.smsplatform.webmaster.service.ClientBusinessService;
import com.qianfeng.smsplatform.webmaster.util.JsonUtils;
import com.qianfeng.smsplatform.webmaster.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ClientBusinessController {
    @Autowired
    private CacheFeign cacheFeign;
    @Autowired
    private ClientBusinessService clientBusinessService;

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/list")
    public DataGridResult findClientBusiness(QueryDTO queryDTO) {
        return clientBusinessService.findByPage(queryDTO);
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/del")
    public R delClientBusiness(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            clientBusinessService.delClientBusiness(id);
        }
        return R.ok();
    }


    @ResponseBody
    @RequestMapping("/sys/clientbusiness/info/{id}")
    public R findById(@PathVariable("id") Long id) {
        TClientBusiness tClientBusiness = clientBusinessService.findById(id);
        return R.ok().put("clientbusiness", tClientBusiness);
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/save")
    public R addClientBusiness(@RequestBody TClientBusiness tClientBusiness) {
        int i = clientBusinessService.addClientBusiness(tClientBusiness);
        return i > 0 ? R.ok() : R.error("添加失败");
    }

    @ResponseBody
    @RequestMapping("/sys/clientbusiness/update")
    public R updateClientBusiness(@RequestBody TClientBusiness tClientBusiness) {
        int i = clientBusinessService.updateClientBusiness(tClientBusiness);
        return i > 0 ? R.ok() : R.error("修改失败");
    }
    @ResponseBody
    @RequestMapping("/aaa")
    public R updateClientBusiness1(@RequestBody TClientBusiness tClientBusiness) {
        TClientBusiness tt = new TClientBusiness();
        tt.setCorpname("aaa");
        tt.setUsercode("gp");
        tt.setPwd("gp");
        tt.setIpaddress("127.0.0.1");
        tt.setIsreturnstatus((byte) 1);
        tt.setReceivestatusurl("http://localhost:8099/receive/status");
        tt.setPriority((byte) 0);
        tt.setUsertype((byte) 2);
        tt.setState(1);
        tt.setMobile("13456785678");
        Map<String, String> stringStringMap = JsonUtils.objectToMap(tt);
        String s = JSONObject.toJSONString(stringStringMap);
        System.out.println(s);
        System.out.println(CacheConstants.CACHE_PREFIX_CLIENT+1+"");
        cacheFeign.hmset(CacheConstants.CACHE_PREFIX_CLIENT+1+"", s);
        return R.ok();
    }



}

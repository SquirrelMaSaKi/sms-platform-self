package com.qianfeng.smsplatform.search.controller;

import com.qianfeng.smsplatform.search.service.SearchApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author damon
 * @Classname SearchController
 * @Date 2019/12/7 15:47
 * @Description TODO
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchApi searchApi;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Map> searchLog(@RequestParam("paras") String paras) throws Exception {
        return searchApi.search(paras);
    }

    @RequestMapping(value = "/searchcount", method = RequestMethod.POST)
    public Long searchLogCount(@RequestParam("paras") String paras) throws Exception {
        return  searchApi.getCount(paras);
    }

    @RequestMapping(value = "/statStatus", method = RequestMethod.POST)
    public Map<String, Long> statSendStatus(@RequestParam("paras") String paras) throws Exception {
        return searchApi.stataStatSendStatus(paras);
    }
}

package com.qianfeng.smsplatform.webmaster.controller;

import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TAdminUser;
import com.qianfeng.smsplatform.webmaster.service.api.SearchService;
import com.qianfeng.smsplatform.webmaster.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;



/**
 * 搜索服务
 *
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

   @Autowired
   private CacheFeign cacheFeign;


    @RequestMapping("/sys/search/list")
    public TableData smssearch(SearchPojo criteria) {
        TAdminUser userEntity = ShiroUtils.getUserEntity();
        Integer clientid = userEntity.getClientid();
        if(clientid!=0){//非管理员只能查自己
            criteria.setClientID(clientid);
        }
        criteria.setHighLightPostTag("</font>");
        criteria.setHighLightPreTag("<font style='color:red'>");
        String str = JsonUtil.getJSON(criteria);
        Long count = searchService.searchLogCount(str);
        if (count != null && count > 0) {
            List<Map> list = searchService.searchLog(str);
            for (Map map : list) {
                String clientID = String.valueOf(map.get("clientID"));

               Map<String, Object> hmget = cacheFeign.hGet(CacheConstants.CACHE_PREFIX_CLIENT + clientID);
                String corpname = (String) hmget.get("corpname");
                map.put("corpname",corpname);
                Object sendTime1 = map.get("sendTime");
                if(!StringUtils.isEmpty(sendTime1)) {
                    Long sendTime = null;
                    try {
                        //因为sendTime中数据很多都是空的，所以捕获一下异常
                        sendTime = Long.parseLong(sendTime1.toString());
                        String sendTimeStr = DateUtils.longToStr(sendTime);
                        map.put("sendTimeStr", sendTimeStr);
                    } catch (NumberFormatException e) {
                        map.put("sendTimeStr", "");
                    }
                }else {
                    map.put("sendTimeStr", "");
                }
            }
            return new TableData(count, list);
        }
        return new TableData(0, null);
    }



}

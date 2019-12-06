package com.qianfeng.smsplatform.search.service.Impl;

import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.feign.CacheService;
import com.qianfeng.smsplatform.search.service.FilterService;
import com.qianfeng.smsplatform.search.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.qianfeng.smsplatform.common.constants.CacheConstants.*;
import static com.qianfeng.smsplatform.common.constants.StrategyConstants.STRATEGY_ERROR_LIMIT;

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
@Service("LimitFilter")
public class LimitFilterService implements FilterService {
    @Autowired
    private CacheService cacheService;

    @Override
    public Standard_Submit filtrate(Standard_Submit message) {
        String messageContent = message.getMessageContent();
        int clientID = message.getClientID();
        String destMobile = message.getDestMobile();
        String key1 = CACHE_PREFIX_SMS_LIMIT_FIVE_MINUTE + MD5Util.md5(messageContent + clientID + destMobile);
        String key2 = CACHE_PREFIX_SMS_LIMIT_HOUR + MD5Util.md5(messageContent + clientID + destMobile);
        String key3 = CACHE_PREFIX_SMS_LIMIT_DAY + MD5Util.md5(messageContent + clientID + destMobile);
        String countMinute = cacheService.findByKey(key1);
        String countHour = cacheService.findByKey(key2);
        String countDay = cacheService.findByKey(key3);

        if (countMinute != null && !countMinute.equals("null")) {   //fastjson太傻逼，redis存的都是"null"字符串，而不是null
            int countMinute2 = Integer.parseInt(countMinute);
            if (countMinute2 < 3) {
                cacheService.setLimitTime(key1, String.valueOf(countMinute2 + 1), (int) System.currentTimeMillis() / 1000 + 5*60);
                return message;
            } else {
                message.setErrorCode(STRATEGY_ERROR_LIMIT);
                return message;
            }
        } else {
            cacheService.setLimitTime(key1, String.valueOf(1),  60);
            return message;
        }



    }
}

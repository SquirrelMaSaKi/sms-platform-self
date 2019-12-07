package com.qianfeng.smsplatform.search.service.Impl;

import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.feign.CacheService;
import com.qianfeng.smsplatform.search.service.FilterService;
import com.qianfeng.smsplatform.search.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LimitFilterService implements FilterService {
    @Autowired
    private CacheService cacheService;


    @Override
    public Standard_Submit filtrate(Standard_Submit message) {
        String messageContent = message.getMessageContent();
        int clientID = message.getClientID();
        String destMobile = message.getDestMobile();
        String keyMinute = CACHE_PREFIX_SMS_LIMIT_FIVE_MINUTE + MD5Util.md5(messageContent + clientID + destMobile);
        String keyHour = CACHE_PREFIX_SMS_LIMIT_HOUR + MD5Util.md5(messageContent + clientID + destMobile);
        String keyDay = CACHE_PREFIX_SMS_LIMIT_DAY + MD5Util.md5(messageContent + clientID + destMobile);


        Check(10, keyDay, message, 1 * 24 * 60 * 60);
        Check(5, keyHour, message, 1 * 60 * 60);
        Check(3, keyMinute, message, 5 * 60);

        return message;
    }


    public void Check(int count, String key, Standard_Submit message, int expiretime) {
        String result=String.valueOf(cacheService.findByKey2(key));

        if (result != null && !result.equals("null")) {
            long result1 = Long.parseLong(result);
            if (result1 < count) {
                cacheService.addOrsub(key, 1);
            } else {
                log.info("超过上限次数");
                message.setErrorCode(STRATEGY_ERROR_LIMIT);
            }
        } else {
            cacheService.setLimitTime(key, 1, expiretime);
        }

    }

}


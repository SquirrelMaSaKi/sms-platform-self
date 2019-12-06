package com.qianfeng.smsplatform.search.service.Impl;

import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.feign.CacheService;
import com.qianfeng.smsplatform.search.service.FilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.qianfeng.smsplatform.common.constants.CacheConstants.CACHE_PREFIX_CUSTOMER_FEE;
import static com.qianfeng.smsplatform.common.constants.StrategyConstants.STRATEGY_ERROR_FEE;

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
@Service("MoneyFilter")
public class MoneyFilterService implements FilterService {
    @Autowired
    private CacheService cacheService;

    @Override
    public Standard_Submit filtrate(Standard_Submit message) {
        String key = CACHE_PREFIX_CUSTOMER_FEE + message.getClientID();
        String fee = cacheService.findByKey(key);
        log.error("余额" + fee);
        long l=0;
        if (fee!=null && !fee.equals("null")) {
            l= Long.parseLong(fee);

            if (l - 50 >= 0) {
                cacheService.setFee(key, String.valueOf(l - 50));
                log.error("余额过滤器：余额充足");
                return message;
            } else {
                message.setErrorCode(STRATEGY_ERROR_FEE);
                return message;
            }
        }
        message.setErrorCode(STRATEGY_ERROR_FEE);
        return message;
    }
}

package com.qianfeng.smsplatform.search.service.Impl;

import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.feign.CacheService;
import com.qianfeng.smsplatform.search.service.FilterService;
import com.qianfeng.smsplatform.search.util.CheckPhone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.qianfeng.smsplatform.common.constants.CacheConstants.CACHE_PREFIX_PHASE;

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
@Service("PhaseFilter")
public class PhaseFilterService implements FilterService {
    @Resource
    private CacheService cacheService;

    @Override
    public Standard_Submit filtrate(Standard_Submit message) {
        String number = message.getDestMobile();
        String phase=null;
        if (number.length()>7) {
            phase = number.substring(0, 7);  //含前不含后,截取号码号段
        } else{
            log.error("号码长度不够7位，不合法");
            return message;
        }

        String key = CACHE_PREFIX_PHASE + phase;  //拼接key
        String provAndcity = cacheService.findByKey(key);
        log.error("号段信息" + provAndcity);


        if (provAndcity != null) {
            String[] split = provAndcity.split("&");

            boolean chinaMobilePhoneNum = CheckPhone.isChinaMobilePhoneNum(number);  //移动?
            boolean chinaTelecomPhoneNum = CheckPhone.isChinaTelecomPhoneNum(number); //电信?
            boolean chinaUnicomPhoneNum = CheckPhone.isChinaUnicomPhoneNum(number);  //联通?

            //补全运营商信息
            if (chinaMobilePhoneNum == true) {
                message.setOperatorId(1);
            } else if (chinaTelecomPhoneNum == true) {
                message.setOperatorId(3);
            } else if (chinaUnicomPhoneNum == true) {
                message.setOperatorId(2);
            }

            //补全号段省市信息
            message.setProvinceId(Integer.parseInt(split[0]));
            message.setCityId(Integer.parseInt(split[1]));
            return message;
        } else {
            log.error("此号码不合法");  //没有设置错误码，不会终止过滤器链
            return message;
        }
    }
}

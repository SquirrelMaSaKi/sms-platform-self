package com.qianfeng.smsplatform.search.service.Impl;

import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.feign.CacheService;
import com.qianfeng.smsplatform.search.service.FilterService;
import com.qianfeng.smsplatform.search.util.AnalyzerUtil;
import com.qianfeng.smsplatform.search.util.fenciqi.IKAnalyzer4Lucene7;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.qianfeng.smsplatform.common.constants.CacheConstants.CACHE_PREFIX_DIRTYWORDS;
import static com.qianfeng.smsplatform.common.constants.StrategyConstants.STRATEGY_ERROR_DIRTYWORDS;

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
@Service("DirtyFilter")
public class DirtyFilterService implements FilterService {
//    private String[] str={"卧槽","造反","打劫","撕票"};
    @Autowired

    private CacheService cacheService;

    @Override
    public Standard_Submit filtrate(Standard_Submit message) {
        String messageContent = message.getMessageContent();
        Analyzer ik = new IKAnalyzer4Lucene7();
        List list = null;
        try {
            TokenStream ts = ik.tokenStream("content", messageContent);
            System.out.println("IKAnalyzer中文分词器 细粒度切分，中文分词效果：");
            list = AnalyzerUtil.doToken(ts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(list);

        for (int i = 0; i < list.size(); i++) {
            String key=CACHE_PREFIX_DIRTYWORDS+list.get(i);
            System.out.println(key);
            System.out.println("脏词查询为"+cacheService.findByKey(key));
               if(cacheService.findByKey(key)!=null && !cacheService.findByKey(key).equals(
                       "null")) {
                   message.setErrorCode(STRATEGY_ERROR_DIRTYWORDS);
                   log.error("说脏话了");
                   return message;
               }
            }
        log.error("通过脏词过滤器");
        return message;
    }
}

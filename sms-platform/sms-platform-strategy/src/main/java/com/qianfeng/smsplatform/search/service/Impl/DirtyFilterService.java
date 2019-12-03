package com.qianfeng.smsplatform.search.service.Impl;

import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.service.FilterService;
import com.qianfeng.smsplatform.search.util.AnalyzerUtil;
import com.qianfeng.smsplatform.search.util.fenciqi.IKAnalyzer4Lucene7;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
@Service("DirtyFilter")
public class DirtyFilterService implements FilterService {
    private String[] str={"卧槽","造反","打劫","撕票"};

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
            for (int j = 0; j < str.length; j++) {
                if (list.get(i).equals(str[j])) {
                    System.out.println("你输入了不法词汇");
                    message.setErrorCode(STRATEGY_ERROR_DIRTYWORDS);
                    return message;
                }
            }
        }
        return message;
    }
}

package com.qianfeng.smsplatform.search.util.fenciqi;

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
import java.io.IOException;

import com.qianfeng.smsplatform.search.util.fenciqi.IKAnalyzer4Lucene7;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;


/**
 * IKAnalyzer分词器集成测试:
 * 细粒度切分：把词分到最细
 * 智能切分：根据词库进行拆分符合我们的语言习惯
 *
 * @author THINKPAD
 *
 */
public class IKAnalyzerTest {
    private static void doToken(TokenStream ts) throws IOException {
        ts.reset();
        CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(cta.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();
    }

    public static void main(String[] args) throws IOException {

        //String etext = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
        String chineseText = "厉害了我的国播出率一路飙升，国民爱国情怀高涨";
        /**
         * ikanalyzer 中文分词器 因为Analyzer的createComponents方法API改变了 需要我们自己实现
         * 分析器IKAnalyzer4Lucene7和分词器IKTokenizer4Lucene7
         */
        // IKAnalyzer 细粒度切分
        try (Analyzer ik = new IKAnalyzer4Lucene7()) {
//            TokenStream ts = ik.tokenStream("content", etext);
//            System.out.println("IKAnalyzer中文分词器 细粒度切分，英文分词效果：");
//            doToken(ts);
            TokenStream ts2 = ik.tokenStream("content", chineseText);
            System.out.println("IKAnalyzer中文分词器 细粒度切分，中文分词效果：");
            doToken(ts2);
        }

        // IKAnalyzer 智能切分
        try (Analyzer ik = new IKAnalyzer4Lucene7(true);) {
//            TokenStream ts = ik.tokenStream("content", etext);
//            System.out.println("IKAnalyzer中文分词器 智能切分，英文分词效果：");
//            doToken(ts);
            TokenStream  ts2 = ik.tokenStream("content", chineseText);
            System.out.println("IKAnalyzer中文分词器 智能切分，中文分词效果：");
            doToken(ts2);
        }
    }
}
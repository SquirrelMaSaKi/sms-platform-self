package com.qianfeng.smsplatform.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.common.model.Standard_Submit;
import com.qianfeng.smsplatform.search.SearchServiceApplication;
import com.qianfeng.smsplatform.search.service.SearchApi;
import com.qianfeng.smsplatform.search.util.SearchPojo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author damon
 * @Classname Test
 * @Date 2019/12/4 19:46
 * @Description TODO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class Test {

    @Autowired
    private SearchApi searchApi;
    @Autowired
    ObjectMapper objectMapper;

    @org.junit.Test
    public void testCreateIndex() throws Exception {
        searchApi.createIndex();
    }

    @org.junit.Test
    public void testDeleteIndex() throws Exception {
        searchApi.deleteIndex("sms_submit_log_five");
    }

    @org.junit.Test
    public void testInsert() throws Exception {
        Standard_Submit bean = new Standard_Submit();
        bean.setClientID(17700005);
        bean.setSrcNumber("1518045234");
        bean.setMessagePriority((short)11);
        bean.setSrcSequenceId(12348765);
        bean.setGatewayID(7777);
        bean.setProductID(7777);
        bean.setDestMobile("7777777");
        bean.setMessageContent("新华社澳门12月8日电 题：推进“一国两制”实践在澳门行稳致远——访澳门特区候任行政长官贺一诚\n" +
                "\n" +
                "新华社记者周文其、刘畅、郭鑫\n" +
                "\n" +
                "“回归祖国20年来，澳门实现繁荣稳定的一大根本保障在于全面准确理解和贯彻‘一国两制’方针。今后，我将继续团结澳门社会各界，推进‘一国两制’实践在澳门行稳致远，将澳门建设得更加繁荣美好。”澳门特区候任行政长官贺一诚说。\n" +
                "\n" +
                "贺一诚近日接受新华社记者专访，就如何推动澳门经济适度多元发展、扎实做好民生工作、参与粤港澳大湾区建设、推进爱国爱澳教育等问题回答了记者的提问。\n" +
                "\n" +
                "贺一诚表示，将履行好特区行政长官的责任，带领新一届特区政府依法施政，努力推动协同奋进、变革创新，开展好各项工作，积极融入国家发展大局。\n" +
                "\n" +
                "（小标题）坚守爱国爱澳核心价值、推进“一国两制”行稳致远\n" +
                "\n" +
                "1957年出生于澳门的贺一诚，亲历了回归以来澳门日新月异的发展历程。他强调，澳门回归后的一个重大实践经验，是社会各界高度认同“一国两制”，并一致认为要继续推进“一国两制”在澳门行稳致远。\n" +
                "\n" +
                "回归祖国后，澳门经济一改颓势，本地生产总值（GDP）由回归之初的519亿澳门元增加到2018年的4447亿澳门元。\n" +
                "\n" +
                "“得益于全面准确理解和贯彻‘一国两制’方针，澳门保持了和谐稳定。这为经济快速增长、民生持续改善创造了良好的社会氛围。”贺一诚说。\n" +
                "\n" +
                "谈及澳门的“一国两制”经验，贺一诚说，一个关键因素是在何厚铧、崔世安两位行政长官的带领下，澳门特别行政区政府不断落实好中央政府对澳门提出的各项要求。\n" +
                "\n" +
                "贺一诚曾担任澳门特区第五、第六届立法会主席。他说，特区立法会是以爱国爱澳议员为主体，大多数议员都明白，提出不同意见是为了监督特区政府、配合政府更好施政，而不是盲目反对政府。\n" +
                "\n" +
                "“从小学到高中，澳门大部分学校也正确执行了爱国爱澳教育方针，坚持使用爱国爱澳教材，定期举行升国旗等仪式，不断增强青年人的民族自豪感和国家认同感。”他说。\n" +
                "\n" +
                "爱国爱澳是澳门社会的核心价值，已深入到各阶段的教育中。贺一诚表示，澳门教育界人士提出本地应当设立一个爱国爱澳教育基地，以便于青年人能够更好地认识到近代以来中华民族逐渐走向富强的过程。“我会认真考虑这一建议。培育爱国爱澳精神不是一朝一夕的事情，需要长期坚持不懈。”\n" +
                "\n" +
                "“面向未来，澳门和谐稳定的社会环境、爱国爱澳传统及国家情怀是不能变的，不然会对自身造成极大的负面冲击。”他说。\n" +
                "\n" +
                "贺一诚指出，虽然澳门社会团体众多、社会结构多元，不过各界有着共同目标，就是一定要建设好澳门、贯彻落实好“一国两制”。“因此，我相信‘一国两制’未来在澳门必将继续行得通、办得到、得人心。”\n" +
                "\n" +
                "（小标题）推动经济适度多元化、优化民生资源投入\n" +
                "\n" +
                "澳门经济结构比较单一，博彩业所占比重较大。近年来，中央和澳门特区政府持续推动经济适度多元可持续发展。贺一诚指出，特区政府已经在这一方面投入了很多资源，新一届特区政府会努力将资源转化为相关产业，推动经济结构持续调整优化。\n" +
                "\n" +
                "贺一诚长期经营实业，曾担任澳门中华总商会副会长，对澳门经济有深入的了解。他指出，澳门经济多元化是适度的多元化，哪些产业适合在澳门发展，是特区政府需要思考的问题。\n" +
                "\n" +
                "“澳门的月工资水平已达约2万澳门元，大大高于东南亚一些发展中经济体，发展劳动密集型产业缺乏竞争力。澳门一定要加大科技方面投入，培育具有高附加值的产业，这才是适度多元化的正确方向。”贺一诚说。\n" +
                "\n" +
                "他表示，中医药产业具有科技含量、产值很高。特区政府已经在横琴投入了大量资金用于中医药产业建设，未来，将认真考虑如何加强企业培育与产业配套，推动中医药产业快速发展，带动澳门对葡语系国家、东南亚国家的出口增长。\n" +
                "\n" +
                "贺一诚说，博彩业给澳门带来了很大的税收收入，对医疗、教育、养老等福利事业发挥着支撑作用，其他行业暂时没有办法立刻取代博彩业。澳门将继续推动博彩业健康发展。\n" +
                "\n" +
                "在参选政纲中，贺一诚提出了涵盖公共治理、经济多元、民生建设、人才培养和文化合作的五大施政构想，对民生领域着墨甚多。贺一诚说，经过回归20年来的发展，特区政府在民生福利方面的开支已经达到了较高水平。在此基础上，更重要的是如何合理、高效地用好开支。\n" +
                "\n" +
                "谈到澳门居民普遍关心的住房问题，贺一诚表示，一段时间以来，澳门实施了一定规模的填海造陆工程。填砂在海上沉降后，已经达到建造房屋的要求。新一届特区政府计划加大基础设施投资，持续增加住房供应，回应民生诉求。\n" +
                "\n" +
                "（小标题）做好粤港澳大湾区重要一员 积极融入国家发展大局\n" +
                "\n" +
                "今年初出台的《粤港澳大湾区发展规划纲要》将澳门列为粤港澳大湾区4个中心城市之一，将发挥核心引擎作用。贺一诚表示，这一规划纲要让澳门进一步认清了自身定位和发展机遇，对澳门未来进一步发展起到强大的促进作用。\n" +
                "\n" +
                "“大湾区内城市之间绝非零和博弈，而是彼此优势互补，共同将大湾区建设成创新能力突出、要素流动顺畅、生态环境优美的国际一流湾区。”贺一诚说。\n" +
                "\n" +
                "澳门是大湾区内人口最少、陆地面积最小的城市。对于如何发挥自身所长、与大湾区其他城市互利共赢，贺一诚有着深入的思考：“从根本上看，澳门要把握好‘一个中心、一个平台、一个基地’的定位，积极融入国家大局。”\n" +
                "\n" +
                "就建设世界旅游休闲中心，贺一诚说，澳门正积极推动旅游经济交流与合作，与大湾区城市共同探索“一程多站”式旅游，解决本地旅游承载力有限的问题。\n" +
                "\n" +
                "就搭建中国与葡语国家商贸合作服务平台，贺一诚说，澳门与一些葡语系国家的文化背景、法律制度相似，经贸联系较为密切。未来，澳门会发挥好这些优势，增进中国与葡语国家的交流合作。\n" +
                "\n" +
                "对于建设以中华文化为主流、多元文化共存的交流合作基地，贺一诚充满信心。他说，澳门有着深厚的中西文化交流传统，不同文化相处融洽。例如，许多家庭长辈与年轻人宗教信仰不同，但不会因此而情绪对立。“澳门将进一步做好文化遗产保护，推动中西文化交流。”\n" +
                "\n" +
                "10月底，全国人大常委会公布了关于授权澳门特别行政区对横琴口岸澳方口岸区及相关延伸区实施管辖的决定。贺一诚表示，这一授权充分体现了中央对澳门融入国家发展大局的高度支持，对于推进粤港澳大湾区建设、加强澳门与内地基础设施互联互通、促进人员和货物等生产要素便捷流动具有重大意义。\n" +
                "\n" +
                "“希望中央政府及各部门能够继续给予澳门特区适当的帮助，促进澳门维护好繁荣稳定。澳门也将努力为国家发展大局作出更大贡献。”贺一诚说。（完）");
        bean.setErrorCode("500");
        bean.setSendTime(new Date());
        bean.setOperatorId(9999);
        bean.setProvinceId(9999);
        bean.setCityId(5);
        bean.setSource(9);
        String json = objectMapper.writeValueAsString(bean);
        for (int i = 29; i < 40; i++) {
            bean.setSrcNumber("1518045234" + i);
            searchApi.add(json);
        }
        searchApi.add(json);
    }

    @org.junit.Test
    public void testGet() throws Exception {
        SearchPojo searchPojo = new SearchPojo();
        // searchPojo.setKeyword("88888");
        searchPojo.setStartTime(0L);
        searchPojo.setEndTime(System.currentTimeMillis());
        searchPojo.setStart(1);
        searchPojo.setRows(10);
        String json = objectMapper.writeValueAsString(searchPojo);
        List<Map> search = searchApi.search(json);
        System.err.println();
    }
}
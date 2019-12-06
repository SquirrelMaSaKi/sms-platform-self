package com.qianfeng.smsplatform.search.util;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author damon
 * @Classname SearchUtil
 * @Date 2019/12/4 16:18
 * @Description TODO
 */

public class SearchUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static void buildMapping(String typeName, CreateIndexRequest request) throws IOException {


        XContentBuilder builder = JsonXContent.contentBuilder().startObject()
                .startObject("properties")

                .startObject("cityId")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("clientID")
                .field("type","long")
                .field("index","true")
                .endObject()

                .startObject("destMobile")
                .field("type","keyword")
                .field("index","true")
                .endObject()

                .startObject("errorCode")
                .field("type","text")
                .field("index","true")
                .endObject()

                .startObject("gatewayID")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("messageContent")
                .field("type", "text")
                .field("analyzer","ik_max_word")
                .field("index","true")
                .endObject()

                .startObject("messagePriority")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("msgid")
                .field("type", "keyword")
                .field("index","true")
                .endObject()

                .startObject("operatorId")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("productID")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("provinceId")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("reportErrorCode")
                .field("type", "keyword")
                .field("index","true")
                .endObject()

                .startObject("reportState")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("sendTime")
                .field("type", "date")
                .field("index","true")
                .endObject()

                .startObject("source")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .startObject("srcNumber")
                .field("type", "keyword")
                .field("index","true")
                .endObject()

                .startObject("srcSequenceId")
                .field("type", "long")
                .field("index","true")
                .endObject()

                .endObject()
                .endObject();
        request.mapping(typeName,builder);
    }

    public static SearchSourceBuilder getSearchSourceBuilder(Map map) throws ParseException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //用户传递的条件可能会有这个会有那个,这些条件可能对应的是不同的查询条件
        //比如 开始和结束时间对应的是range查询,appkey是term查询,甚至可能还有请求正文的内容查询应该match
        // 因为用户可能一次性输入多个条件,那按照正常来说,他应该是期望结果满足所有条件,所以还应该有bool查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //如果这个appkey不等于空代表有这个查询条件
        Object cityId = map.get("cityId");
        Object clientID = map.get("clientID");
        TermQueryBuilder cityIdTerm = null;
        TermQueryBuilder clientIDTerm = null;

        if (cityId != null) {
            cityIdTerm = new TermQueryBuilder("cityId", cityId.toString());
            boolQueryBuilder.must(cityIdTerm);
        }
        if (clientID != null) {
            clientIDTerm = new TermQueryBuilder("clientID", clientID.toString());
            boolQueryBuilder.must(clientIDTerm);
        }
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }

}

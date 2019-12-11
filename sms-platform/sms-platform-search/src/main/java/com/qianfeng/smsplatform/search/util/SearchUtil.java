package com.qianfeng.smsplatform.search.util;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author damon
 * @Classname SearchUtil
 * @Date 2019/12/4 16:18
 * @Description TODO
 */

public class SearchUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


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

    public static SearchSourceBuilder getSearchSourceBuilder(SearchPojo searchPojo) throws ParseException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //如果这个appkey不等于空代表有这个查询条件
        Object messageContent = searchPojo.getKeyword();
        Object startTime = searchPojo.getStartTime();
        Object endTime = searchPojo.getEndTime();
        Object mobile = searchPojo.getMobile();
        Object clientID = searchPojo.getClientID();
        MatchQueryBuilder keywordTerm = null;
        RangeQueryBuilder receiveTimeQuery = null;
        TermQueryBuilder srcNumberTerm = null;
        TermQueryBuilder clientIDTerm = null;

        //号码查询
        if(mobile != null && mobile !=""){
            srcNumberTerm = new TermQueryBuilder("destMobile",mobile.toString());
            boolQueryBuilder.must(srcNumberTerm);
        }
        //关键字查询
        if (messageContent != null && messageContent !="") {
            keywordTerm = QueryBuilders.matchQuery("messageContent", messageContent.toString());
            boolQueryBuilder.must(keywordTerm);
        }
        //时间间隔查询
        if (startTime != null & endTime != null) {
            //如果开始和结束时间都有，那么从es中查询sendTime。
            receiveTimeQuery = QueryBuilders.rangeQuery("sendTime").from(searchPojo.getStartTime()).to(searchPojo.getEndTime());
            boolQueryBuilder.must(receiveTimeQuery);
        } else if (startTime != null & endTime == null) {
            Date start = simpleDateFormat.parse(startTime.toString());
            receiveTimeQuery = QueryBuilders.rangeQuery("sendTime").gte(start.getTime());
            boolQueryBuilder.must(receiveTimeQuery);
        } else if (startTime == null & endTime != null) {
            Date end = simpleDateFormat.parse(endTime.toString());
            receiveTimeQuery = QueryBuilders.rangeQuery("sendTime").lte(end.getTime());
            boolQueryBuilder.must(receiveTimeQuery);
        }
        //根据clientID查询
        if (clientID != null & clientID!="") {
            clientIDTerm = new TermQueryBuilder("clientID", clientID.toString());
            boolQueryBuilder.must(clientIDTerm);
        }
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }

}
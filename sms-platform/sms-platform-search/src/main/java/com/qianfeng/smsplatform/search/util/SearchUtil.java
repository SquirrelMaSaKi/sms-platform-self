package com.qianfeng.smsplatform.search.util;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;
import java.text.SimpleDateFormat;

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

}

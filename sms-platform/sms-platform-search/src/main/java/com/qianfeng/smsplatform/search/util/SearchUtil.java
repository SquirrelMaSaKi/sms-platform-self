package com.qianfeng.smsplatform.search.util;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;

/**
 * @author damon
 * @Classname SearchUtil
 * @Date 2019/12/4 16:18
 * @Description TODO
 */
public class SearchUtil {
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
                .field("type", "text")
                .field("index","true")
                .endObject()
                .startObject("platformTotalTime")
                .field("type", "long")
                .field("index","true")
                .endObject()
                .startObject("requestContent")
                .field("type", "text")
                .field("index","true")
                //.field("analyzer","ik_max_word")
                .endObject()
                .startObject("errorCode")
                .field("type", "keyword")
                .field("index","true")
                .endObject()
                .startObject("receiveTime")
                .field("type", "date")
                .field("index","true")
                .endObject()
                .startObject("createTime") //es开始插入数据的时间,因为插入时间不一定和处理完成的时间一致,而且我们也只记录了请求收到的时间,不是消息什么时候写入到es中的时间,所以我们需要这么一个时间
                .field("type", "date")
                .field("index","true")
                .endObject()
                .endObject()
                .endObject();
        request.mapping(typeName,builder);
    }

}

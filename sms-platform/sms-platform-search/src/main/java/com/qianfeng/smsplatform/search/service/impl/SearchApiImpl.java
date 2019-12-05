package com.qianfeng.smsplatform.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.search.service.SearchApi;
import com.qianfeng.smsplatform.search.util.SearchUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author damon
 * @Classname SearchApi
 * @Date 2019/12/4 15:34
 * @Description TODO
 */
@Service
public class SearchApiImpl implements SearchApi {

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(SearchApiImpl.class);
    @Value("${elasticsearch.index.name}")
    private String indexName;
    @Value("${elasticsearch.index.type}")
    private String typeName;

    @Autowired
    private RestHighLevelClient client;


    @Override
    public void createIndex() throws Exception {
        //判断是否已经存在该索引
        //完成创建
        if(!existIndex(indexName)){
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            //测试使用，实际开发中，副分片应该有两--->三个，每个主分片应该是20G——40G，写多读少，所以分片更小效率更高。
            createIndexRequest.settings(Settings.builder().put("number_of_replicas", 1).put("number_of_shards", 5).build());
            SearchUtil.buildMapping(typeName,createIndexRequest);
            client.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        }else{
            logger.error("创建失败，索引已存在");
        }

    }

    @Override
    public void deleteIndex(String indexName) throws Exception {
        if(existIndex(indexName)){
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            AcknowledgedResponse delete =  client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            logger.error("删除index的结果是：{}",objectMapper.writeValueAsString(delete));
        }else {
            logger.error("删除失败，不存在指定索引");
        }
    }

    public boolean existIndex(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indexName);
        return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }
}

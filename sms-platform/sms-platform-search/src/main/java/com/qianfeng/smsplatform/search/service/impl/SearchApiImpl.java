package com.qianfeng.smsplatform.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.smsplatform.search.service.SearchApi;
import com.qianfeng.smsplatform.search.util.SearchUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    @Override
    public void add(String json) throws Exception {
        IndexRequest indexRequest = new IndexRequest(indexName, typeName);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        logger.error("插入数据结果是:{}", objectMapper.writeValueAsString(response));
    }

    @Override
    public List<Map> search(String params) throws Exception {
        List list = new ArrayList();
        //和查询数量一样,根据条件查询结果,但是我们可能会有分页,可能还有高亮
        //将用户传递的json转换为map
        Map value = objectMapper.readValue(params, Map.class);
        SearchSourceBuilder builder = SearchUtil.getSearchSourceBuilder(value);

        //如果传递了分页参数 应该有分页,我们规定用户传递 start和end两个参数过来代表分页
        //但是如果数据太多,用户又没有传递分页,咋办,我们应该有默认长度
        Object start = value.get("start");
        Object end = value.get("end");
        if (start == null) {
            start = 1;
        } else {
            try {
                start = Integer.parseInt(start.toString());
            } catch (Exception e) {
                start = 1;
                e.printStackTrace();
            }
        }

        if (end == null) {
            end = 10;
        } else {
            try {
                end = Integer.parseInt(end.toString());
            } catch (Exception e) {
                end = 10;
                e.printStackTrace();
            }
        }

        builder.from((Integer.parseInt(start.toString()) - 1) * Integer.parseInt(end.toString(), Integer.parseInt(end.toString())));
        //应该要设置高亮数据,理论上高亮的标签应该是用户传递的,当然我们也有默认的
        //如果传递了请求参数才设置高亮
        if (value.get("requestContent") != null) {
            //高亮的前缀和后缀
            Object highlightpretag = value.get("highlightpretag");
            Object highlightposttag = value.get("highlightposttag");

            if (highlightposttag == null || "".equalsIgnoreCase(highlightposttag.toString().trim())) {
                highlightposttag = "</span>";
            }
            if (highlightpretag == null || "".equalsIgnoreCase(highlightpretag.toString().trim())) {
                highlightpretag = "<span color='green'>";
            }
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("requestContent");
            highlightBuilder.preTags(highlightpretag.toString());
            highlightBuilder.postTags(highlightposttag.toString());
            builder.highlighter(highlightBuilder);
        }


        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(builder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        //处理结果
        //命中的数据
        SearchHits hits = response.getHits();
        //获取到数据数组
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            //以json的方式获取数据
            String source = searchHit.getSourceAsString();
            //这是原始的数据
            Map data = objectMapper.readValue(source, Map.class);
            //可能会有高亮数据,获取高亮相关的数据
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField field = highlightFields.get("requestContent");
            if (field != null) {
                //有高亮
                Text[] fragments = field.getFragments();
                if (fragments != null) {
                    //高亮内容
                    String hlcontent = fragments[0].string();
                    //替换原始数据为高亮数据
                    data.put("requestContent", hlcontent);
                }
            }
            list.add(data);
        }
        return list;
    }

    @Override
    public long getCount(String params) throws Exception {
        //将用户传递的json转换为map
        Map value = objectMapper.readValue(params, Map.class);
        SearchSourceBuilder builder = SearchUtil.getSearchSourceBuilder(value);
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(builder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return response.getHits().getTotalHits();
    }


    /**
     * 判断是否存在索引
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean existIndex(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indexName);
        return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }
}

package com.qianfeng.smsplatform.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author damon
 * @Classname ElasticSearchConfig
 * @Date 2019/12/4 15:53
 * @Description TODO
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${spring.data.elasticsearch.host}")
    private String hostName;
    @Value("${spring.data.elasticsearch.port}")
    private int port;

    @Bean
    public RestHighLevelClient getClient(){
        RestClientBuilder builder = RestClient.builder(new HttpHost(hostName,port));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

}

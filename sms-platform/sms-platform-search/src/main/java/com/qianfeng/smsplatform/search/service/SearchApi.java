package com.qianfeng.smsplatform.search.service;

/**
 * @author damon
 * @Classname SearchApi
 * @Date 2019/12/4 15:33
 * @Description TODO
 */
public interface SearchApi {

    /**
     * 创建 es 索引， 如果存在则抛出索引已存在异常
     * @throws Exception
     */
    void createIndex() throws Exception;

    /**
     * 删除es 索引， 为空时抛出异常
     * @throws Exception
     */
    void deleteIndex(String indexName) throws Exception;
}
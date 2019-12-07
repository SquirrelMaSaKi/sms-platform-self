package com.qianfeng.smsplatform.search.service;

import com.qianfeng.smsplatform.common.model.Standard_Submit;

import java.util.List;
import java.util.Map;

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

    /**
     * 写入日志到ES
     * @param json
     * @throws Exception
     */
    void add(String json)throws Exception;

    /**
     * 查询数据,因为看能会有很多条,所以是集合,每个的数据是json类型的key vlaue数据,可以用map存放
     * @param params
     * @throws 
     * @return
     */
    List<Map> search(String params) throws Exception;

    /**
     * 获取数据条数
     * @param params
     * @return
     * @throws Exception
     */
    long getCount(String params) throws Exception;

    /**
     * 更新数据报告
     * @param submit
     * @throws Exception
     */
    void update(Standard_Submit submit) throws Exception;
}
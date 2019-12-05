package com.qianfeng.smsplatform.webmaster.dao;

import com.qianfeng.smsplatform.webmaster.pojo.TClientChannel;
import com.qianfeng.smsplatform.webmaster.pojo.TClientChannelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TClientChannelMapper {
    long countByExample(TClientChannelExample example);

    int deleteByExample(TClientChannelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TClientChannel record);

    int insertSelective(TClientChannel record);

    List<TClientChannel> selectByExample(TClientChannelExample example);

    TClientChannel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TClientChannel record, @Param("example") TClientChannelExample example);

    int updateByExample(@Param("record") TClientChannel record, @Param("example") TClientChannelExample example);

    int updateByPrimaryKeySelective(TClientChannel record);

    int updateByPrimaryKey(TClientChannel record);

    TClientChannel selectByClientId(Long clientid);
}
package com.qianfeng.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dao.TBlackListMapper;
import com.qianfeng.smsplatform.webmaster.dto.DataGridResult;
import com.qianfeng.smsplatform.webmaster.dto.QueryDTO;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TBlackList;
import com.qianfeng.smsplatform.webmaster.pojo.TBlackListExample;
import com.qianfeng.smsplatform.webmaster.service.BlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BlackServiceImpl implements BlackService {

    @Autowired
    private TBlackListMapper tBlackListMapper;

    @Autowired
    private CacheFeign cacheFeign;


    @Override
    public int addBlack(TBlackList tBlackList) {
        //更新数据库
        int i = tBlackListMapper.insertSelective(tBlackList);
        //更新缓存
        cacheFeign.setString(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile(), "1");
        return i;
    }

    @Override
    public int delBlack(Long id) {
        TBlackList tBlackList = findById(id);
        //删除缓存中数据
        cacheFeign.del(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile());
        return tBlackListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateBlack(TBlackList tBlackList) {
        //查出旧数据信息
        TBlackList origin_tBlack = tBlackListMapper.selectByPrimaryKey(tBlackList.getId());

        //删除缓存
        cacheFeign.del(CacheConstants.CACHE_PREFIX_BLACK+origin_tBlack.getMobile());

        //更新数据库
        int i =  tBlackListMapper.updateByPrimaryKey(tBlackList);

        //更新缓存
        cacheFeign.setString(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile(), "1");
        return i;
    }

    @Override
    public TBlackList findById(Long id) {
        return tBlackListMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TBlackList> findAll() {
        return tBlackListMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(),queryDTO.getLimit());
        TBlackListExample example = new TBlackListExample();
        String sort = queryDTO.getSort();
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause("id");
        }
        List<TBlackList> tBlackLists = tBlackListMapper.selectByExample(example);

        //批量放入缓存
        for (TBlackList tBlackList : tBlackLists) {
            String string = cacheFeign.getString(CacheConstants.CACHE_PREFIX_BLACK + tBlackList.getMobile());
            if (string==null || string.trim().length() == 0) {
                cacheFeign.setString(CacheConstants.CACHE_PREFIX_BLACK+tBlackList.getMobile(), "1");
            }
        }

        PageInfo<TBlackList> info = new PageInfo<>(tBlackLists);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total,tBlackLists);
        return result;
    }
}

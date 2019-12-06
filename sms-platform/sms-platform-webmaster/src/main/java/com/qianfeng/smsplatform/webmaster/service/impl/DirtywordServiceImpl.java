package com.qianfeng.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dao.TDirtywordMapper;
import com.qianfeng.smsplatform.webmaster.dto.DataGridResult;
import com.qianfeng.smsplatform.webmaster.dto.QueryDTO;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TDirtyword;
import com.qianfeng.smsplatform.webmaster.pojo.TDirtywordExample;
import com.qianfeng.smsplatform.webmaster.service.DirtywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DirtywordServiceImpl implements DirtywordService {

    @Autowired
    private TDirtywordMapper tDirtywordMapper;

    @Autowired
    private CacheFeign cacheFeign;


    @Override
    public int addDirtyword(TDirtyword tDirtyword) {
        //添加到数据库
        int i = tDirtywordMapper.insertSelective(tDirtyword);

        //添加到缓存
        cacheFeign.setString(CacheConstants.CACHE_PREFIX_DIRTYWORDS+tDirtyword.getDirtyword(), "1");
        return i;
    }

    @Override
    public int delDirtyword(Long id) {
        TDirtyword tDirtyword = findById(id);
        //删除缓存
        cacheFeign.del(CacheConstants.CACHE_PREFIX_DIRTYWORDS+tDirtyword.getDirtyword());
        return tDirtywordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateDirtyword(TDirtyword tDirtyword) {
        //获取原始数据，删除缓存中数据
        TDirtyword tDirtyword_origin = tDirtywordMapper.selectByPrimaryKey(tDirtyword.getId());
        cacheFeign.del(CacheConstants.CACHE_PREFIX_DIRTYWORDS+tDirtyword_origin.getDirtyword());

        //更新数据库
        int i = tDirtywordMapper.updateByPrimaryKey(tDirtyword);

        //更新缓存
        cacheFeign.setString(CacheConstants.CACHE_PREFIX_DIRTYWORDS+tDirtyword.getDirtyword(), "1");
        return i;
    }

    @Override
    public TDirtyword findById(Long id) {
        return tDirtywordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TDirtyword> findAll() {
        return tDirtywordMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(),queryDTO.getLimit());
        TDirtywordExample example = new TDirtywordExample();
        String sort = queryDTO.getSort();
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause("id");
        }
        List<TDirtyword> tDirtywords = tDirtywordMapper.selectByExample(example);

        //同步到缓存
        for (TDirtyword tDirtyword : tDirtywords) {
            String string = cacheFeign.getString(CacheConstants.CACHE_PREFIX_DIRTYWORDS + tDirtyword.getDirtyword());
            if (string==null || string.trim().length()==0 || string.equals("null")) {
                cacheFeign.setString(CacheConstants.CACHE_PREFIX_DIRTYWORDS+tDirtyword.getDirtyword(), "1");
            }
        }

        PageInfo<TDirtyword> info = new PageInfo<>(tDirtywords);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total,tDirtywords);
        return result;
    }


}

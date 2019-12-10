package com.qianfeng.smsplatform.webmaster.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dao.TClientBusinessMapper;
import com.qianfeng.smsplatform.webmaster.dto.DataGridResult;
import com.qianfeng.smsplatform.webmaster.dto.QueryDTO;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TClientBusiness;
import com.qianfeng.smsplatform.webmaster.pojo.TClientBusinessExample;
import com.qianfeng.smsplatform.webmaster.service.ClientBusinessService;
import com.qianfeng.smsplatform.webmaster.util.JsonUtils;
import com.qianfeng.smsplatform.webmaster.util.MD5Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class ClientBusinessServiceImpl implements ClientBusinessService {

    @Autowired
    private TClientBusinessMapper tClientBusinessMapper;

    @Autowired
    private CacheFeign cacheFeign;


    @Override
    public int addClientBusiness(TClientBusiness tClientBusiness) {
        String pwd = tClientBusiness.getPwd();
        String build = MD5Builder.build(pwd, "UTF-8");
        String md5PASS = build.toUpperCase();
        tClientBusiness.setPwd(md5PASS);

        //先更新到数据库
        int i = tClientBusinessMapper.insertSelective(tClientBusiness);

        //转为map
        Map<String, Object> map = JsonUtils.object2Map(tClientBusiness);

        //然后客户信息缓存更新
        cacheFeign.setHashMapByMap(CacheConstants.CACHE_PREFIX_CLIENT+tClientBusiness.getId(), map);
        return i;
    }

    @Override
    public int delClientBusiness(Long id) {
        //先删除缓存中数据
        cacheFeign.del(CacheConstants.CACHE_PREFIX_CLIENT+id);

        //然后删除数据库
        int i = tClientBusinessMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int updateClientBusiness(TClientBusiness tClientBusiness) {
        //先更新数据库
        int i =  tClientBusinessMapper.updateByPrimaryKey(tClientBusiness);

        //更新缓存
        Map<String, Object> map = JsonUtils.object2Map(tClientBusiness);
        cacheFeign.setHashMapByMap(CacheConstants.CACHE_PREFIX_CLIENT+tClientBusiness.getId(), map);
        return i;
    }

    @Override
    public TClientBusiness findById(Long id) {
        return tClientBusinessMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<TClientBusiness> findAll() {
        return tClientBusinessMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TClientBusinessExample example = new TClientBusinessExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TClientBusiness> tClientBusinesses = tClientBusinessMapper.selectByExample(example);
//        for (TClientBusiness tClientBusiness : tClientBusinesses) {
//            Long id = tClientBusiness.getId();
//            Integer paidValueStr  = (Integer)cacheService.getObject("CUSTOMER_FEE:" +id);
//            if(!StringUtils.isEmpty(paidValueStr)){
//                int i = paidValueStr/1000;
//                tClientBusiness.setPaidValueStr(i+"元");
//            }
//
//            //批量放入缓存
//            Map<String, Object> map = cacheFeign.hGet(CacheConstants.CACHE_PREFIX_CLIENT + id);
//            if (map==null || map.size() == 0) {
//                Map<String, Object> map1 = JsonUtils.object2Map(tClientBusiness);
//                cacheFeign.setHashMapByMap(CacheConstants.CACHE_PREFIX_CLIENT+id, map1);
//            }
//        }
        PageInfo<TClientBusiness> info = new PageInfo<>(tClientBusinesses);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, tClientBusinesses);
        return result;
    }


}

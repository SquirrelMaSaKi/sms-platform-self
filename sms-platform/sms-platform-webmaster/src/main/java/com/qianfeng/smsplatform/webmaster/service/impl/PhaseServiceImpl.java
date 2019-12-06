package com.qianfeng.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dao.TInstMapper;
import com.qianfeng.smsplatform.webmaster.dao.TPhaseMapper;
import com.qianfeng.smsplatform.webmaster.dto.DataGridResult;
import com.qianfeng.smsplatform.webmaster.dto.QueryDTO;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TInst;
import com.qianfeng.smsplatform.webmaster.pojo.TInstExample;
import com.qianfeng.smsplatform.webmaster.pojo.TPhase;
import com.qianfeng.smsplatform.webmaster.pojo.TPhaseExample;
import com.qianfeng.smsplatform.webmaster.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PhaseServiceImpl implements PhaseService {

    @Autowired
    private TPhaseMapper tPhaseMapper;

    @Autowired
    private TInstMapper tInstMapper;

    @Autowired
    private CacheFeign cacheFeign;


    @Override
    public int addPhase(TPhase tPhase) {
        //更新数据库
        int i = tPhaseMapper.insertSelective(tPhase);

        //更新缓存
        cacheFeign.setString(CacheConstants.CACHE_PREFIX_PHASE+tPhase.getPhase(), tPhase.getProvId()+"&"+tPhase.getCityId());
        return i;
    }

    @Override
    public int delPhase(Long id) {
        //更新缓存
        TPhase tPhase = findById(id);
        cacheFeign.del(CacheConstants.CACHE_PREFIX_PHASE+tPhase.getPhase());
        //删除数据库
        return tPhaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updatePhase(TPhase tPhase) {
        //更新缓存
        //更新数据库，这里注意，由于号段修改，但是数据库中的id不变，而redis中的key是号段拼接的
        //会出现一种情况，直接setString导致旧数据依然存在，所以要先删除
        //所以先不要插入数据库先从数据库中查出旧数据信息
        TPhase tPhase_origin = tPhaseMapper.selectByPrimaryKey(tPhase.getId());

        //将其从缓存中删除
        cacheFeign.del(CacheConstants.CACHE_PREFIX_PHASE+tPhase_origin.getPhase());

        //更新数据库
        int i= tPhaseMapper.updateByPrimaryKey(tPhase);

        //更新缓存
        cacheFeign.setString(CacheConstants.CACHE_PREFIX_PHASE+tPhase.getPhase(), tPhase.getProvId()+"&"+tPhase.getCityId());
        return i;
    }

    @Override
    public TPhase findById(Long id) {
        return tPhaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TPhase> findALL() {
        return tPhaseMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(), queryDTO.getLimit());
        TPhaseExample example = new TPhaseExample();
        String sort = queryDTO.getSort();
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause("id");
        }
        List<TPhase> messages = tPhaseMapper.selectByExample(example);
        for (TPhase message : messages) {
            Long provId = message.getProvId();
            TInst tInst1 = tInstMapper.selectByPrimaryKey(provId);
            message.setProvName(tInst1.getAreaname());
            Long cityId = message.getCityId();
            TInstExample tInstExample = new TInstExample();
            TInstExample.Criteria criteria = tInstExample.createCriteria();
            criteria.andIdEqualTo(cityId);
            criteria.andParentidEqualTo(provId);
            List<TInst> tInsts = tInstMapper.selectByExample(tInstExample);
            if(tInsts!=null&&tInsts.size()>0){
                TInst tInst = tInsts.get(0);
                message.setCityName(tInst.getAreaname());
            }

            //批量放入缓存
            String string = cacheFeign.getString(CacheConstants.CACHE_PREFIX_PHASE+message.getPhase());
            if (string==null || string.trim().length()==0 || string.equals("null")) {
                cacheFeign.setString(CacheConstants.CACHE_PREFIX_PHASE+message.getPhase(), provId+"&"+cityId);
            }
        }

        PageInfo<TPhase> info = new PageInfo<>(messages);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total, messages);
        return result;
    }
}

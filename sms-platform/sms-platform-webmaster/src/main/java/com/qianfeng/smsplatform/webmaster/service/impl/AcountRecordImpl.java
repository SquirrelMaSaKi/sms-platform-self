package com.qianfeng.smsplatform.webmaster.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.smsplatform.common.constants.CacheConstants;
import com.qianfeng.smsplatform.webmaster.dao.TAcountRecordMapper;
import com.qianfeng.smsplatform.webmaster.dao.TClientBusinessMapper;
import com.qianfeng.smsplatform.webmaster.dto.DataGridResult;
import com.qianfeng.smsplatform.webmaster.dto.QueryDTO;
import com.qianfeng.smsplatform.webmaster.feign.CacheFeign;
import com.qianfeng.smsplatform.webmaster.pojo.TAcountRecord;
import com.qianfeng.smsplatform.webmaster.pojo.TAcountRecordExample;
import com.qianfeng.smsplatform.webmaster.pojo.TClientBusiness;
import com.qianfeng.smsplatform.webmaster.service.AcountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

@Service
public class AcountRecordImpl implements AcountRecordService {

    @Autowired
    private TAcountRecordMapper tAcountRecordMapper;

    @Autowired
    private TClientBusinessMapper tClientBusinessMapper;

    @Autowired
    private CacheFeign cacheFeign;


    //初始化费用数据到缓存
    @Override
    public int addAcount(TAcountRecord tAcountRecord) {
        Integer paidvalue = tAcountRecord.getPaidvalue()*1000;//转为厘
        tAcountRecord.setPaidvalue(paidvalue);
        tAcountRecord.setCreatetime(new Date());
        int i = tAcountRecordMapper.insertSelective(tAcountRecord);

        //更新缓存
        //必须从redis查询到余额，然后进行加和，再放入到redis
        String string_fee = cacheFeign.getString(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE + tAcountRecord.getClientid());
        if (string_fee == null || string_fee.trim().length()==0 || string_fee.equals("null") ) {
            string_fee = "0";
        }
        long fee_origin = Long.valueOf(string_fee);
        long fee = fee_origin + tAcountRecord.getPaidvalue();
        cacheFeign.setStringObject(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE+tAcountRecord.getClientid(), fee);
        return i;
    }

    @Override
    public int delAcount(Long id) {
        //删除缓存
        TAcountRecord tAcountRecord = findById(id);
        cacheFeign.del(CacheConstants.CACHE_PREFIX_CUSTOMER_FEE+tAcountRecord.getClientid());
        return tAcountRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateAcount(TAcountRecord tAcountRecord) {
        //更新数据库
        int i = tAcountRecordMapper.updateByPrimaryKey(tAcountRecord);
        return i;
    }

    @Override
    public TAcountRecord findById(Long id) {
        return tAcountRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TAcountRecord> findAll() {
        return tAcountRecordMapper.selectByExample(null);
    }

    @Override
    public DataGridResult findByPage(QueryDTO queryDTO) {
        PageHelper.offsetPage(queryDTO.getOffset(),queryDTO.getLimit());
        TAcountRecordExample example = new TAcountRecordExample();
        String sort = queryDTO.getSort();
        if(!StringUtils.isEmpty(sort)){
            example.setOrderByClause("id");
        }
        List<TAcountRecord> tAcountRecords = tAcountRecordMapper.selectByExample(example);
        for (TAcountRecord tAcountRecord : tAcountRecords) {
            Long clientid = tAcountRecord.getClientid();
            TClientBusiness tClientBusiness = tClientBusinessMapper.selectByPrimaryKey(clientid);
            String corpname = tClientBusiness.getCorpname();
            tAcountRecord.setCorpname(corpname);
            Integer paidvalue = tAcountRecord.getPaidvalue();
            tAcountRecord.setPaidvalue(paidvalue/1000);
        }
        PageInfo<TAcountRecord> info = new PageInfo<>(tAcountRecords);
        long total = info.getTotal();
        DataGridResult result = new DataGridResult(total,tAcountRecords);
        return result;
    }
}

package com.qianfeng.smsplatform.webmaster.service.impl;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.webmaster.dao.TReportMapper;
import com.qianfeng.smsplatform.common.model.ReportDTO;
import com.qianfeng.smsplatform.webmaster.service.TReportService;
import com.qianfeng.smsplatform.webmaster.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TReportServiceImpl implements TReportService {
    @Autowired
    private TReportMapper tReportMapper;

    @Override
    public int insert(Standard_Report report) {
        return tReportMapper.insert(report);
    }

    @Override
    public List<Standard_Report> findAll() {
        return tReportMapper.findAll();
    }

    @Override
    public int deleteAll() {
        return tReportMapper.deleteAll();
    }
}

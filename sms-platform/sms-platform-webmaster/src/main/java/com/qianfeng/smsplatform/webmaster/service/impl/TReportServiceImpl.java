package com.qianfeng.smsplatform.webmaster.service.impl;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.webmaster.dao.TReportMapper;
import com.qianfeng.smsplatform.webmaster.service.TReportService;
import org.springframework.beans.factory.annotation.Autowired;

public class TReportServiceImpl implements TReportService {
    @Autowired
    private TReportMapper tReportMapper;

    @Override
    public int insert(Standard_Report report) {
        return tReportMapper.insert(report);
    }
}

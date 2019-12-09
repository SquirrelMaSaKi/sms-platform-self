package com.qianfeng.smsplatform.webmaster.service;

import com.qianfeng.smsplatform.common.model.Standard_Report;

import java.util.List;

public interface TReportService {
    int insert(Standard_Report report);

    List<Standard_Report> findAll();

    int deleteAll();
}

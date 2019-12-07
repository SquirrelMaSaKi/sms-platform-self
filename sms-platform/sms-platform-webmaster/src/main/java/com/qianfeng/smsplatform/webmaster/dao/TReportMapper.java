package com.qianfeng.smsplatform.webmaster.dao;

import com.qianfeng.smsplatform.common.model.Standard_Report;

import java.util.List;

public interface TReportMapper {
    int insert(Standard_Report report);

    List<Standard_Report> findAll();

    int deleteAll();
}

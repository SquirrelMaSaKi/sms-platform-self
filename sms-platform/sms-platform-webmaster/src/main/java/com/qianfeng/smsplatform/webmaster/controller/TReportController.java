package com.qianfeng.smsplatform.webmaster.controller;

import com.qianfeng.smsplatform.common.model.Standard_Report;
import com.qianfeng.smsplatform.webmaster.service.TReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TReportController {
    @Autowired
    private TReportService tReportService;

    @RequestMapping("/sys/report")
    public List<Standard_Report> findAll() {
        return tReportService.findAll();
    }

    @RequestMapping("/sys/delete")
    public int deleteReport() {
        return tReportService.deleteAll();
    }
}

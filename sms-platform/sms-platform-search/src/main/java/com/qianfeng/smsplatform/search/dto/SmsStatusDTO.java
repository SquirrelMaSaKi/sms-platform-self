package com.qianfeng.smsplatform.search.dto;

/**
 * @author damon
 * @Classname SmsStatusDTO
 * @Date 2019/12/9 11:37
 * @Description TODO
 */

import lombok.Data;

@Data
public class SmsStatusDTO {
    private Integer clientID;
    private Long startTime;
    private Long endTime;
}


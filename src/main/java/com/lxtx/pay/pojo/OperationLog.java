package com.lxtx.pay.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class OperationLog {
    private Long id;
    private Integer appId;
    private String userName;
    private String module;
    private String operation;
    private String requestUrl;
    private String requestMethod;
    private String requestParams;
    private Integer status;
    private String message;
    private Long executeTime;
    private String operationIp;
    private String userAgent;
    private String sessionId;
    private Date createTime;
}

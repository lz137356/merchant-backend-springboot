package com.lxtx.pay.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class OnlineUser {
    private Long id;
    private Integer appId;
    private String userName;
    private String sessionId;
    private String loginIp;
    private String userAgent;
    private Date loginTime;
    private Date lastActiveTime;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

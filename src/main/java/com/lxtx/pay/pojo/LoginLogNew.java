package com.lxtx.pay.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class LoginLogNew {
    private Long id;
    private Integer appId;
    private String userName;
    private String loginIp;
    private Integer status;
    private String message;
    private String loginType;
    private String userAgent;
    private String sessionId;
    private Date createTime;
}

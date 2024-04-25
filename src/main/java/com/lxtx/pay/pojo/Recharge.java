package com.lxtx.pay.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class Recharge {
    private long id;
    private long appId;
    private String uAmount;
    private long status;
    private String faitAmount;
    private long payLogId;
    private Date createTime;
    private Date finishTime;
    private String rate;
    private String proof;
    private String operator;
    private String comment;
}

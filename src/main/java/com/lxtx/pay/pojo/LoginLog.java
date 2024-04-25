package com.lxtx.pay.pojo;


import java.util.Date;


public class LoginLog {
    private  int Id;
    private int AppId;
    private  String RemoteIp;
    private String LogType;
    private String Details;
    private String OperationTarget;
    private Date CreateTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRemoteIp() {
        return RemoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        RemoteIp = remoteIp;
    }

    public String getLogType() {
        return LogType;
    }

    public void setLogType(String logType) {
        LogType = logType;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getOperationTarget() {
        return OperationTarget;
    }

    public void setOperationTarget(String operationTarget) {
        OperationTarget = operationTarget;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public int getAppId() {
        return AppId;
    }

    public void setAppId(int appId) {
        AppId = appId;
    }
}

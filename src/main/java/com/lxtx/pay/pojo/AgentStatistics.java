package com.lxtx.pay.pojo;

import java.util.Date;

public class AgentStatistics {

    private Integer Id;
    //商户名
    private String UserName;
    //日期
    private Date StatDay;
    //订单创建数量
    private Integer PayCrecount;
    //支付总额
    private Long PayAmount;
    //提现创建数量
    private Integer WdCrecount;
    //提现成功数量
    private Integer  WdSuscount;
    //提现总额
    private Long  WithdrawAmount;

    //商户id
    private Integer AppId;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Date getStatDay() {
        return StatDay;
    }

    public void setStatDay(Date statDay) {
        StatDay = statDay;
    }

    public Integer getPayCrecount() {
        return PayCrecount;
    }

    public void setPayCrecount(Integer payCrecount) {
        PayCrecount = payCrecount;
    }

    public Long getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(Long payAmount) {
        PayAmount = payAmount;
    }

    public Integer getWdCrecount() {
        return WdCrecount;
    }

    public void setWdCrecount(Integer wdCrecount) {
        WdCrecount = wdCrecount;
    }

    public Integer getWdSuscount() {
        return WdSuscount;
    }

    public void setWdSuscount(Integer wdSuscount) {
        WdSuscount = wdSuscount;
    }

    public Long getWithdrawAmount() {
        return WithdrawAmount;
    }

    public void setWithdrawAmount(Long withdrawAmount) {
        WithdrawAmount = withdrawAmount;
    }

    public Integer getAppId() {
        return AppId;
    }

    public void setAppId(Integer appId) {
        AppId = appId;
    }

    @Override
    public String toString() {
        return "AgentStatistics{" +
                "Id=" + Id +
                ", UserName='" + UserName + '\'' +
                ", StatDay=" + StatDay +
                ", PayCrecount=" + PayCrecount +
                ", PayAmount=" + PayAmount +
                ", WdCrecount=" + WdCrecount +
                ", WdSuscount=" + WdSuscount +
                ", WithdrawAmount=" + WithdrawAmount +
                ", AppId=" + AppId +
                '}';
    }
}

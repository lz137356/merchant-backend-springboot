package com.lxtx.pay.pojo;

public class Cpstat {
    private Integer Id;
    private int AppId;
    private int StatDay;
    private Integer PayAmount;
    private  Integer PayFee;
    private Integer WithdrawAmount;
    private Integer WithdrawFee;



    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getAppId() {
        return AppId;
    }

    public void setAppId(int appId) {
        AppId = appId;
    }

    public int getStatDay() {
        return StatDay;
    }

    public void setStatDay(int statDay) {
        StatDay = statDay;
    }

    public Integer getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(Integer payAmount) {
        PayAmount = payAmount;
    }

    public Integer getPayFee() {
        return PayFee;
    }

    public void setPayFee(Integer payFee) {
        PayFee = payFee;
    }

    public Integer getWithdrawAmount() {
        return WithdrawAmount;
    }

    public void setWithdrawAmount(Integer withdrawAmount) {
        WithdrawAmount = withdrawAmount;
    }

    public Integer getWithdrawFee() {
        return WithdrawFee;
    }

    public void setWithdrawFee(Integer withdrawFee) {
        WithdrawFee = withdrawFee;
    }
}

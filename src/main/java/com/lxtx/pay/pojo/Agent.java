package com.lxtx.pay.pojo;

public class Agent {
    private Integer Id;
    private String UserName;
    private String UserPass;
    private Integer  PayFeeFix;
    private Long PayFeeRate;
    private Integer WithdrawFeeFix;
    private Long  WithdrawFeeRate;
    private Integer Status;

    private Long TotalPay;
    private Long TotalPayFee;
    private Long TotalWithdraw;
    private Long  TotalWithdrawfee;
    private Long  Remain;
    private Long  CanOver;

    private Integer AppId;

    public Integer getAppId() {
        return AppId;
    }

    public void setAppId(Integer appId) {
        AppId = appId;
    }

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

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public Integer getPayFeeFix() {
        return PayFeeFix;
    }

    public void setPayFeeFix(Integer payFeeFix) {
        PayFeeFix = payFeeFix;
    }

    public Long getPayFeeRate() {
        return PayFeeRate;
    }

    public void setPayFeeRate(Long payFeeRate) {
        PayFeeRate = payFeeRate;
    }

    public Integer getWithdrawFeeFix() {
        return WithdrawFeeFix;
    }

    public void setWithdrawFeeFix(Integer withdrawFeeFix) {
        WithdrawFeeFix = withdrawFeeFix;
    }

    public Long getWithdrawFeeRate() {
        return WithdrawFeeRate;
    }

    public void setWithdrawFeeRate(Long withdrawFeeRate) {
        WithdrawFeeRate = withdrawFeeRate;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Long getTotalPay() {
        return TotalPay;
    }

    public void setTotalPay(Long totalPay) {
        TotalPay = totalPay;
    }

    public Long getTotalPayFee() {
        return TotalPayFee;
    }

    public void setTotalPayFee(Long totalPayFee) {
        TotalPayFee = totalPayFee;
    }

    public Long getTotalWithdraw() {
        return TotalWithdraw;
    }

    public void setTotalWithdraw(Long totalWithdraw) {
        TotalWithdraw = totalWithdraw;
    }

    public Long getTotalWithdrawfee() {
        return TotalWithdrawfee;
    }

    public void setTotalWithdrawfee(Long totalWithdrawfee) {
        TotalWithdrawfee = totalWithdrawfee;
    }

    public Long getRemain() {
        return Remain;
    }

    public void setRemain(Long remain) {
        Remain = remain;
    }

    public Long getCanOver() {
        return CanOver;
    }

    public void setCanOver(Long canOver) {
        CanOver = canOver;
    }
}


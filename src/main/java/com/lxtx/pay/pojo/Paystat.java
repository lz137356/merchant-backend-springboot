package com.lxtx.pay.pojo;

public class Paystat {


    private Integer Id;
    private int PayTypeId;
    private int StatDay;
    private long PayAmount;
    private long ThirdFee;
    private  long PayThirdFee;
    private long WithdrawAmount;
    private long WithdrawThirdFee;
    private String name;

    public long getPayThirdFee() {
        return PayThirdFee;
    }

    public void setPayThirdFee(long payThirdFee) {
        PayThirdFee = payThirdFee;
    }

    public long getWithdrawAmount() {
        return WithdrawAmount;
    }

    public void setWithdrawAmount(long withdrawAmount) {
        WithdrawAmount = withdrawAmount;
    }

    public long getWithdrawThirdFee() {
        return WithdrawThirdFee;
    }

    public void setWithdrawThirdFee(long withdrawThirdFee) {
        WithdrawThirdFee = withdrawThirdFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getPayTypeId() {
        return PayTypeId;
    }

    public void setPayTypeId(int payTypeId) {
        PayTypeId = payTypeId;
    }

    public int getStatDay() {
        return StatDay;
    }

    public void setStatDay(int statDay) {
        StatDay = statDay;
    }

    public long getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(long payAmount) {
        PayAmount = payAmount;
    }

    public long getThirdFee() {
        return ThirdFee;
    }

    public void setThirdFee(long thirdFee) {
        ThirdFee = thirdFee;
    }

    public void setThirdFee(Integer thirdFee) {
        ThirdFee = thirdFee;
    }
}

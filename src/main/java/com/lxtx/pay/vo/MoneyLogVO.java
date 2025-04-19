package com.lxtx.pay.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MoneyLogVO {

    private Integer id;

    private Integer appId;

    private Integer type;

    private String detail;

    private Integer sceneInfo;

    private Long frontMoney;

    private Long money;

    private String orderId;

    private Long queenMoney;

    private String details;

    private Date createTime;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getSceneInfo() {
        return sceneInfo;
    }

    public void setSceneInfo(Integer sceneInfo) {
        this.sceneInfo = sceneInfo;
    }

    public Long getFrontMoney() {
        return frontMoney;
    }

    public void setFrontMoney(Long frontMoney) {
        this.frontMoney = frontMoney;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getQueenMoney() {
        return queenMoney;
    }

    public void setQueenMoney(Long queenMoney) {
        this.queenMoney = queenMoney;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

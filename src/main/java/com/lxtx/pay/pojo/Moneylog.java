package com.lxtx.pay.pojo;

import java.util.Date;

public class Moneylog {


    private Integer Id;
    private int AppId;
    private int Type;
    private Integer SceneInfo;
    private int FrontMoney;
    private int  Money;
    private int  QueenMoney;
    private String Details;
    private Date CreateTime;

    private String Notes;

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
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

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public Integer getSceneInfo() {
        return SceneInfo;
    }

    public void setSceneInfo(Integer sceneInfo) {
        SceneInfo = sceneInfo;
    }

    public int getFrontMoney() {
        return FrontMoney;
    }

    public void setFrontMoney(int frontMoney) {
        FrontMoney = frontMoney;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public int getQueenMoney() {
        return QueenMoney;
    }

    public void setQueenMoney(int queenMoney) {
        QueenMoney = queenMoney;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }
}

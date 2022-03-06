package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.MoneylogHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentMoneylogAction extends  BaseAction{

    private MoneylogHandler moneylogHandler;


    public void queryAllmoneylog(){

        List<Object> all = moneylogHandler.getAll();

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();

        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",all.size());
        map.put("data",all);
        String s1 = gson.toJson(map);
        output(s1);


    }





    public MoneylogHandler getMoneylogHandler() {
        return moneylogHandler;
    }

    public void setMoneylogHandler(MoneylogHandler moneylogHandler) {
        this.moneylogHandler = moneylogHandler;
    }
}

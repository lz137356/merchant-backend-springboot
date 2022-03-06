package com.lxtx.pay.action;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.WithdrawstatHandler;
import com.lxtx.pay.pojo.Paystat;
import com.lxtx.pay.pojo.Withdrawstat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawstatAction extends BaseAction{

    private WithdrawstatHandler withdrawstatHandler;


    public void querywithdrawstat(){

        Map map = new HashMap();
        //String WithdrawTypeId =   servletRequest.getParameter("WithdrawTypeId");
        //String statda =  servletRequest.getParameter("statDay");
        //long Id = 1;
//        Integer WithdrawTypeId = 2;
        //int StatDay = 20201202;
        /* if(!"".equals(WithdrawTypeId)&& Id!=null){
              int paytypeid = Integer.parseInt(WithdrawTypeId);
              map.put("paytypeid",paytypeid);
          }*/
       /* if(!"".equals(statda)&& statda!=null){
           int statDay =  Integer.parseInt(statda);
           map.put("statDay",statDay);
        }*/
        //map.put("Id",Id);
        String typeid = this.servletRequest.getParameter("typeid");
        if (!"".equals(typeid)&&typeid!=null){
            int WithdrawTypeId = Integer.parseInt(typeid);
            map.put("WithdrawTypeId",WithdrawTypeId);

        }
        String earlyDate = this.servletRequest.getParameter("earlyDate");
        String lateDate = this.servletRequest.getParameter("lateDate");

        List<Withdrawstat> list =new ArrayList<>();
        if (earlyDate!=null&&!"".equals(earlyDate)&&lateDate!=null&&!"".equals(lateDate)){
            if (earlyDate.equals(lateDate)){
                map.put("StatDay",earlyDate);
                list = withdrawstatHandler.querywithdrawstat(map);
            }else {
                map.put("earlyDate",earlyDate);
                map.put("lateDate",lateDate);
                list = withdrawstatHandler.querywithdrawstatdates(map);
            }
        }else if (earlyDate==null||"".equals(earlyDate)){
            map.put("StatDay",lateDate);
            list = withdrawstatHandler.querywithdrawstat(map);
        }else {
            map.put("StatDay",earlyDate);
            list = withdrawstatHandler.querywithdrawstat(map);
        }

        //map.put("StatDay",StatDay);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m=new HashMap();
        m.put("code",0);
        m.put("msg","");
        m.put("count",list.size());
        m.put("data",list);
        String s1 = gson.toJson(m);
        output(s1);

    }



    public WithdrawstatHandler getWithdrawstatHandler() {
        return withdrawstatHandler;
    }

    public void setWithdrawstatHandler(WithdrawstatHandler withdrawstatHandler) {
        this.withdrawstatHandler = withdrawstatHandler;
    }
}

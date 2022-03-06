package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.PaystatHandler;
import com.lxtx.pay.pojo.Paystat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaystatAction extends BaseAction{

    private PaystatHandler paystatHandler;
    public void allquery(){
        Map map = new HashMap();
        String typeid = this.servletRequest.getParameter("typeid");
        if (!"".equals(typeid)&&typeid!=null){
            int PayTypeId = Integer.parseInt(typeid);
            map.put("PayTypeId",PayTypeId);
        }
        String earlyDate = this.servletRequest.getParameter("earlyDate");
        String lateDate = this.servletRequest.getParameter("lateDate");
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        List<Paystat> list =new ArrayList<>();
        if (earlyDate!=null&&!"".equals(earlyDate)&&lateDate!=null&&!"".equals(lateDate)){
            if (earlyDate.equals(lateDate)){
                map.put("StatDay",earlyDate);
                list = paystatHandler.querypaystat(map);
            }else {
                map.put("earlyDate",earlyDate);
                map.put("lateDate",lateDate);
                list = paystatHandler.querypaystatdates(map);
            }
        }else if (earlyDate==null||"".equals(earlyDate)){
            map.put("StatDay",lateDate);
            list = paystatHandler.querypaystat(map);
        }else {
            map.put("StatDay",earlyDate);
            list = paystatHandler.querypaystat(map);
        }

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m=new HashMap();
        m.put("code",0);
        m.put("msg","");
        m.put("count",list.size());
        m.put("data",list);
        String s1 = gson.toJson(m);
        System.out.println(s1);
        output(s1);
    }
    //
    public void querypaystat(){
        Map map = new HashMap();
        String typeid = this.servletRequest.getParameter("typeid");
        if (!"".equals(typeid)&&typeid!=null){
            int PayTypeId = Integer.parseInt(typeid);
            map.put("PayTypeId",PayTypeId);
        }
        String earlyDate = this.servletRequest.getParameter("earlyDate");
        String lateDate = this.servletRequest.getParameter("lateDate");
        List<Paystat> list =new ArrayList<>();
        if (earlyDate!=null&&!"".equals(earlyDate)&&lateDate!=null&&!"".equals(lateDate)){
            if (earlyDate.equals(lateDate)){
                map.put("StatDay",earlyDate);
                list = paystatHandler.querypaystat(map);
            }else {
                map.put("earlyDate",earlyDate);
                map.put("lateDate",lateDate);
                list = paystatHandler.querypaystatdates(map);
            }
        }else if (earlyDate==null||"".equals(earlyDate)){
            map.put("StatDay",lateDate);
            list = paystatHandler.querypaystat(map);
        }else {
            map.put("StatDay",earlyDate);
            list = paystatHandler.querypaystat(map);
        }
        //  map.put("StatDay",StatDay);
        paystatHandler.querypaystat(map);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m=new HashMap();
        m.put("code",0);
        m.put("msg","");
        m.put("count",list.size());
        m.put("data",list);
        String s1 = gson.toJson(m);
        System.out.println(s1);
        output(s1);

    }
    public PaystatHandler getPaystatHandler() {
        return paystatHandler;
    }
    public void setPaystatHandler(PaystatHandler paystatHandler) {
        this.paystatHandler = paystatHandler;
    }
}

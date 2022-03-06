package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.PayTypeHandler;
import com.lxtx.pay.pojo.PayType;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayTypeAction  extends BaseAction{


    public void selectOne(){
        String id = this.servletRequest.getParameter("Id");
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        PayType select = payTypeHandler.select(id);
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",1);
        map.put("data",select);
        String s1 = gson.toJson(map);
        output(s1);
    }

    public void queryAll() throws IOException {
        List all = payTypeHandler.getAll();
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

    public void addPayType(){
        PayType payType = new PayType();
        String Name = this.servletRequest.getParameter("Name");
        String Params = this.servletRequest.getParameter("Params");
        String StatusStr = this.servletRequest.getParameter("Status");
        String FeeFixStr = this.servletRequest.getParameter("FeeFix");
        String FeeRateStr = this.servletRequest.getParameter("FeeRate");
        String SortStr = this.servletRequest.getParameter("Sort");
        String Types = this.servletRequest.getParameter("Types");
        String WorkTime = this.servletRequest.getParameter("WorkTime");
        String Range = this.servletRequest.getParameter("Range");
        String Currency = this.servletRequest.getParameter("Currency");
        payType.setName(Name);
        payType.setParams(Params);
        if (!"".equals(StatusStr)&&StatusStr!=null){
            payType.setStatus(Integer.parseInt(StatusStr));

        }
        if (!"".equals(FeeFixStr)&&FeeFixStr!=null){
            payType.setFeefix(Integer.parseInt(FeeFixStr));

        }
        if (!"".equals(FeeRateStr)&&FeeRateStr!=null){
            payType.setFeerate(Double.parseDouble(FeeRateStr));

        }
        if (!"".equals(SortStr)&&SortStr!=null){
            payType.setSort(Integer.parseInt(SortStr));
        }
        payType.setTotalfee(0);
        payType.setTypes(Types);
        payType.setWorktime(WorkTime);
        payType.setRange(Range);
        Date datetime=new Date();
        payType.setCreatetime(datetime);
        payType.setCurrency(Currency);
        payTypeHandler.insert(payType);
        output(String.valueOf(1));
    }
    public void update(){
        PayType payType = new PayType();
        String id = this.servletRequest.getParameter("id");
        String Name = this.servletRequest.getParameter("Name");
        String Params = this.servletRequest.getParameter("Params");
        String StatusStr = this.servletRequest.getParameter("Status");
        String FeeFixStr = this.servletRequest.getParameter("FeeFix");
        String FeeRateStr = this.servletRequest.getParameter("FeeRate");
        String SortStr = this.servletRequest.getParameter("Sort");
        String Types = this.servletRequest.getParameter("Types");
        String WorkTime = this.servletRequest.getParameter("WorkTime");
        String Range = this.servletRequest.getParameter("Range");
        String Currency = this.servletRequest.getParameter("Currency");
        if (!"".equals(id)&&id!=null){
            payType.setId(Integer.parseInt(id));
        }
        payType.setName(Name);
        payType.setParams(Params);
        if (!"".equals(StatusStr)&&StatusStr!=null){
            payType.setStatus(Integer.parseInt(StatusStr));

        }
        if (!"".equals(FeeFixStr)&&FeeFixStr!=null){
            payType.setFeefix(Integer.parseInt(FeeFixStr));
        }
        if (!"".equals(FeeRateStr)&&FeeRateStr!=null){
            payType.setFeerate(Double.parseDouble(FeeRateStr));

        }
        if (!"".equals(SortStr)&&SortStr!=null){
            payType.setSort(Integer.parseInt(SortStr));
        }
        payType.setTotalfee(0);
        payType.setTypes(Types);
        payType.setWorktime(WorkTime);
        payType.setRange(Range);
        Date datetime=new Date();
        payType.setCreatetime(datetime);
        payType.setCurrency(Currency);
        payTypeHandler.update(payType);
        output(String.valueOf(1));
    }
    public void delete(){
        String id = this.servletRequest.getParameter("id");
        int delete = payTypeHandler.deleteById(Integer.parseInt(id));
        output(String.valueOf(delete));
    }
    private PayTypeHandler payTypeHandler;
    public PayTypeHandler getPayTypeHandler() {
        return payTypeHandler;
    }
    public void setPayTypeHandler(PayTypeHandler payTypeHandler) {
        this.payTypeHandler = payTypeHandler;
    }
}

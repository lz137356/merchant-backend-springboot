package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.WithdrawTypeHandler;
import com.lxtx.pay.pojo.PayType;
import com.lxtx.pay.pojo.WithdrawType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawTypeAction extends BaseAction {

    private WithdrawTypeHandler withdrawTypeHandler;

    public WithdrawTypeHandler getWithdrawTypeHandler() {
        return withdrawTypeHandler;
    }

    public void setWithdrawTypeHandler(WithdrawTypeHandler withdrawTypeHandler) {
        this.withdrawTypeHandler = withdrawTypeHandler;
    }

    public void queryAll() throws IOException {

        List all = withdrawTypeHandler.getAll();
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
        System.out.println(s1);

    }
    public void addWithdrawType(){
        WithdrawType withdrawType=new WithdrawType();
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
        withdrawType.setName(Name);
        withdrawType.setParams(Params);
        if (!"".equals(StatusStr)&&StatusStr!=null){
            withdrawType.setStatus(Integer.parseInt(StatusStr));

        }
        if (!"".equals(FeeFixStr)&&FeeFixStr!=null){
            withdrawType.setFeefix(Integer.parseInt(FeeFixStr));

        }
        if (!"".equals(FeeRateStr)&&FeeRateStr!=null){
            withdrawType.setFeerate(new BigDecimal(FeeRateStr));

        }
        if (!"".equals(SortStr)&&SortStr!=null){
            withdrawType.setSort(Integer.parseInt(SortStr));
        }
        withdrawType.setTotalfee(0);
        withdrawType.setTypes(Types);
        withdrawType.setWorktime(WorkTime);
        withdrawType.setRange(Range);
        Date datetime=new Date();
        withdrawType.setCreatetime(datetime);
        withdrawType.setCurrency(Currency);
        withdrawTypeHandler.insert(withdrawType);
        output(String.valueOf(1));
    }
    public void update(){
        WithdrawType withdrawType = new WithdrawType();
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
            withdrawType.setId(Integer.parseInt(id));
        }
        withdrawType.setName(Name);
        withdrawType.setParams(Params);
        if (!"".equals(StatusStr)&&StatusStr!=null){
            withdrawType.setStatus(Integer.parseInt(StatusStr));

        }
        if (!"".equals(FeeFixStr)&&FeeFixStr!=null){
            withdrawType.setFeefix(Integer.parseInt(FeeFixStr));
        }
        if (!"".equals(FeeRateStr)&&FeeRateStr!=null){
            withdrawType.setFeerate(new BigDecimal(FeeRateStr));

        }
        if (!"".equals(SortStr)&&SortStr!=null){
            withdrawType.setSort(Integer.parseInt(SortStr));
        }
        withdrawType.setTotalfee(0);
        withdrawType.setTypes(Types);
        withdrawType.setWorktime(WorkTime);
        withdrawType.setRange(Range);
        Date datetime=new Date();
        withdrawType.setCreatetime(datetime);
        withdrawType.setCurrency(Currency);
        withdrawTypeHandler.update(withdrawType);
        output(String.valueOf(1));
    }
    public void delete(){
        String id = this.servletRequest.getParameter("id");
        int delete = withdrawTypeHandler.deleteById(Integer.parseInt(id));
        output(String.valueOf(delete));
    }

}

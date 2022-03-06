package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.CpBankHandler;
import com.lxtx.pay.pojo.Agent;
import com.lxtx.pay.pojo.CpBank;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.utils.PageUtils;
import sun.management.resources.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpBankAction extends BaseAction {


    private CpBankHandler cpBankHandler;



    public void queryAll(){
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        Map map=new HashMap();
      if (cpInfo!=null){

          map.put("appId",cpInfo.getAppId());

      }


        List<CpBank> cpBanks = cpBankHandler.queryAll(map);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m = new HashMap();
        m.put("code", 0);
        m.put("msg", "成功");
        m.put("count", cpBanks.size());
        m.put("data", cpBanks);
        String s1 = gson.toJson(m);
        output(s1);


    }


    public void querycpbank() {
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        Map map = new HashMap();
        if (cpInfo != null) {
            Integer appId = cpInfo.getAppId();
            int rowIndex = PageUtils.getRowIndex(page, limit);
            map.put("rowIndex", rowIndex);
            map.put("limit", limit);
            map.put("appId", appId);
            List<CpBank> cp = cpBankHandler.querycpbank(map);
            Integer count = cpBankHandler.querycount(map);
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.serializeNulls();
            Gson gson = gsonbuilder.create();
            Map m = new HashMap();
            m.put("code", 0);
            m.put("msg", "成功");
            m.put("count", count);
            m.put("data", cp);
            String s1 = gson.toJson(m);
            output(s1);
        }
    }
    //修改
    public void updatecpbank(){
        CpBank cpBank = new CpBank();
        String Id =   servletRequest.getParameter("Id");
        String AppId =   servletRequest.getParameter("AppId");
        String TxType =   servletRequest.getParameter("TxType");
        String BankCardType =   servletRequest.getParameter("BankCardType");
        String BankCode =   servletRequest.getParameter("BankCode");
        String BankCardNo =   servletRequest.getParameter("BankCardNo");
        String BankCardName =   servletRequest.getParameter("BankCardName");
        String Mobile =   servletRequest.getParameter("Mobile");
        String CreditValid =   servletRequest.getParameter("CreditValid");
        String CreditCvv =   servletRequest.getParameter("CreditCvv");
        String Email =   servletRequest.getParameter("Email");
        String Upi =   servletRequest.getParameter("Upi");

        if (!"".equals(BankCode)&&BankCode!=null){
            cpBank.setBankCode(BankCode);

        }if (!"".equals(AppId)&&AppId!=null){
            cpBank.setAppId(Integer.parseInt(AppId));

        }
        if (!"".equals(TxType)&&TxType!=null){
            cpBank.setTxType(Integer.parseInt(TxType));

        }
        if (!"".equals(BankCardType)&&BankCardType!=null){
            cpBank.setBankCardType(Integer.parseInt(BankCardType));

        } if (!"".equals(BankCardNo)&&BankCardNo!=null){
            cpBank.setBankCardNo(BankCardNo);

        }
        if (!"".equals(BankCardName)&&BankCardName!=null){
            cpBank.setBankCardName(BankCardName);

        }
        if (!"".equals(Mobile)&&Mobile!=null){
            cpBank.setMobile(Mobile);

        }
        if (!"".equals(CreditCvv)&&CreditCvv!=null){
            cpBank.setCreditCvv(CreditCvv);

        }
        if (!"".equals(Email)&&Email!=null){
            cpBank.setEmail(Email);

        }
        if (!"".equals(Upi)&&Upi!=null){
            cpBank.setUpi(Upi);

        }
        cpBank.setCreditValid("1");
        cpBankHandler.update(cpBank);
        output("1");
    }

    //新增
    public void addcpbank(){
       CpBank cpBank = new CpBank();
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");

        String TxType =   servletRequest.getParameter("TxType");
        String BankCardType =   servletRequest.getParameter("BankCardType");
        String BankCode =   servletRequest.getParameter("BankCode");
        String BankCardNo =   servletRequest.getParameter("BankCardNo");
        String BankCardName =   servletRequest.getParameter("BankCardName");
        String Mobile =   servletRequest.getParameter("Mobile");
        String CreditValid =   servletRequest.getParameter("CreditValid");
        String CreditCvv =   servletRequest.getParameter("CreditCvv");
        String Email =   servletRequest.getParameter("Email");
        String Upi =   servletRequest.getParameter("Upi");
        if(cpInfo!=null){
            cpBank.setAppId(cpInfo.getAppId());

        }
        if (!"".equals(TxType)&&TxType!=null){
           cpBank.setTxType(Integer.parseInt(TxType));

        }
         if (!"".equals(BankCardNo)&&BankCardNo!=null){
            cpBank.setBankCardNo(BankCardNo);

        }
        if (!"".equals(BankCardName)&&BankCardName!=null){
            cpBank.setBankCardName(BankCardName);

        }
        cpBank.setBankCode(BankCode==null?"0":BankCode);
        cpBank.setBankCardType(BankCardType==null?0:Integer.parseInt(BankCardType));
        cpBank.setMobile(Mobile==null?"0":Mobile);
        cpBank.setCreditCvv(CreditCvv==null?"0":CreditCvv);
        cpBank.setEmail(Email==null?"0":Email);
        cpBank.setUpi(Upi==null?"0":Upi);
        cpBank.setCreditValid(CreditValid==null?"0":CreditValid);
        cpBankHandler.insert(cpBank);
        output("1");
    }

    //修改
    public void update(){
        CpBank cpBank = new CpBank();
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        String Id =   servletRequest.getParameter("Id");
        String TxType =   servletRequest.getParameter("TxType");
        String BankCardType =   servletRequest.getParameter("BankCardType");
        String BankCode =   servletRequest.getParameter("BankCode");
        String BankCardNo =   servletRequest.getParameter("BankCardNo");
        String BankCardName =   servletRequest.getParameter("BankCardName");
        String Mobile =   servletRequest.getParameter("Mobile");
        String CreditValid =   servletRequest.getParameter("CreditValid");
        String CreditCvv =   servletRequest.getParameter("CreditCvv");
        String Email =   servletRequest.getParameter("Email");
        String Upi =   servletRequest.getParameter("Upi");

        /*if (!"".equals(BankCode)&&BankCode!=null){

        }*/
        if(cpInfo!=null){
            cpBank.setAppId(cpInfo.getAppId());

        }
        if (!"".equals(Id)&&Id!=null){
            cpBank.setId(Integer.parseInt(Id));

        }

        if (!"".equals(TxType)&&TxType!=null){
            cpBank.setTxType(Integer.parseInt(TxType));

        }
       /* if (!"".equals(BankCardType)&&BankCardType!=null){
            cpBank.setBankCardType(Integer.parseInt(BankCardType));

        }*/ if (!"".equals(BankCardNo)&&BankCardNo!=null){
            cpBank.setBankCardNo(BankCardNo);

        }
        if (!"".equals(BankCardName)&&BankCardName!=null){
            cpBank.setBankCardName(BankCardName);

        }
        cpBank.setBankCardType(0);
        cpBank.setMobile("0");
        cpBank.setCreditCvv("0");
        cpBank.setEmail("0");
        cpBank.setUpi("0");
        cpBank.setCreditValid("0");
        cpBank.setBankCode(BankCode);
        cpBank.setCreditValid("1");
        cpBankHandler.update(cpBank);


        output("1");
    }

    //删除银行卡信息
    public void deletcprbank(){
        String id = this.servletRequest.getParameter("Id");
        int delete = cpBankHandler.deleteById(Integer.parseInt(id));
        output(String.valueOf(delete));
    }

    public CpBankHandler getCpBankHandler() {
        return cpBankHandler;
    }

    public void setCpBankHandler(CpBankHandler cpBankHandler) {
        this.cpBankHandler = cpBankHandler;
    }
}


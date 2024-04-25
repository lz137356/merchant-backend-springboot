package com.lxtx.pay.helper;

import com.lxtx.pay.handler.CpstatOwnHandler;
import com.lxtx.pay.handler.WithdrawOrderHandler;
import com.lxtx.pay.pojo.CpstatOwn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CpstatOwnHelper extends BaseHelper {


    private CpstatOwnHandler cpstatownHandler;

    private WithdrawOrderHandler withdraworderHandler;


    @Override
    protected void realInit() {
//        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
//        Date date=new Date();
//        String day = sf.format(date);
//        Map paramMap=new HashMap();
//        Integer d = Integer.valueOf(day);
//        System.out.println("d:::::::::::::::::::::"+d);
//        paramMap.put("statday",d);
//        CpstatOwn cpstatown1 = withdraworderHandler.cpstatown(paramMap);
//        CpstatOwn cpstatown = cpstatownHandler.selectOne(paramMap);
//
//
//
//
//        if (cpstatown1!=null){
//            System.out.println("cp1!=null");
//
//
//            if (cpstatown!=null){
//                CpstatOwn newcpstatOwn=new CpstatOwn();
//                Object statday = paramMap.get("statday");
//                newcpstatOwn.setStatDay(Integer.parseInt(statday.toString()));
//                newcpstatOwn.setId(cpstatown.getId());
//                newcpstatOwn.setWithdrawAmount(cpstatown1.getWithdrawAmount());
//                newcpstatOwn.setWithdrawFee(cpstatown1.getWithdrawFee());
//                newcpstatOwn.setTodayWithdrawNum(cpstatown1.getTodayWithdrawNum());
//                cpstatownHandler.update(newcpstatOwn);
//
//
//
//            }else {
//                CpstatOwn newcpstatown =new CpstatOwn();
//                    newcpstatown.setPayAmount(0);
//                    newcpstatown.setPayFee(0);
//                    System.out.println("getWithdrawAmount:::::::"+cpstatown1);
//                    newcpstatown.setWithdrawAmount(cpstatown1.getWithdrawAmount());
//                    newcpstatown.setWithdrawFee(cpstatown1.getWithdrawFee());
//                    newcpstatown.setTodayWithdrawNum(cpstatown1.getTodayWithdrawNum());
//                    newcpstatown.setStatDay(Integer.parseInt(day));
//                    cpstatownHandler.insert(newcpstatown);
//
//            }
//
//
//
//
//        }else {
//            System.out.println("cp1==null");
//        }
//



    }

    public WithdrawOrderHandler getWithdraworderHandler() {
        return withdraworderHandler;
    }

    public void setWithdraworderHandler(WithdrawOrderHandler withdraworderHandler) {
        this.withdraworderHandler = withdraworderHandler;
    }

    public CpstatOwnHandler getCpstatownHandler() {
        return cpstatownHandler;
    }

    public void setCpstatownHandler(CpstatOwnHandler cpstatownHandler) {
        this.cpstatownHandler = cpstatownHandler;
    }
}

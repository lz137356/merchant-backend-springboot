package com.lxtx.pay.helper;

import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.CpstatHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Cpstat;

public class CpstatHelper extends BaseHelper{

    private Cpstat todatCpstat=new Cpstat();
    private Cpstat yesterdayCpstat=new Cpstat();
    private CpInfo cpInfo=new CpInfo();
    private int todayCount=0;
    private int yesterdayCount=0;
    private CpstatHandler cpstatHandler;
    private CpInfoHandler cpInfoHandler;

    public CpInfoHandler getCpInfoHandler() {
        return cpInfoHandler;
    }

    public void setCpInfoHandler(CpInfoHandler cpInfoHandler) {
        this.cpInfoHandler = cpInfoHandler;
    }

    public CpstatHandler getCpstatHandler() {
        return cpstatHandler;
    }

    public void setCpstatHandler(CpstatHandler cpstatHandler) {
        this.cpstatHandler = cpstatHandler;
    }

    @Override
    protected void realInit() {

        System.out.println("!!!!!!!!!!");



    }



}

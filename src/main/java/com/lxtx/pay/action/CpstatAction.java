package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.CpstatHandler;
import com.lxtx.pay.handler.PaylogHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.Cpstat;
import com.lxtx.pay.pojo.Paylog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpstatAction extends BaseAction{

    private CpstatHandler cpstatHandler;
    private PaylogHandler paylogHandler;
    private CpInfoHandler cpInfoHandler;



    public void queryAll(){
       Map conmap=new HashMap();
        CpInfo cpInfo = (CpInfo) this.servletRequest.getSession().getAttribute("cpInfo");
        int appId = cpInfo.getAppId();
        conmap.put("appId",appId);
        String datestr = this.servletRequest.getParameter("date");
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));

        if(datestr!=null && !datestr.isEmpty()){
            String[] range = datestr.split("~");
            String replace1 = range[0].replace("-", "").replace(" ","");
            String replace2 = range[1].replace("-", "").replace(" ","");



            conmap.put("startDay",Integer.parseInt(replace1));
            conmap.put("endDay",Integer.parseInt(replace2));
        }
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<Cpstat> all = cpstatHandler.querycpstat(page,limit,conmap);
        int count = cpstatHandler.querycpstatcount(conmap);
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",count);
        map.put("data",all);
        String s1 = gson.toJson(map);
        output(s1);
    }
    public void cpstatquary(){
        Map map = new HashMap();
       List<Cpstat> ll =  cpstatHandler.getAll();
        //List<Cpstat> list = cpstatHandler.querycpstat(map);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m=new HashMap();
        m.put("code",0);
        m.put("msg","");
        m.put("count",ll.size());
        m.put("data",ll);
        String s1 = gson.toJson(m);
        output(s1);
    }

    public void queryByDate(){

        CpInfo cp = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");


        //创建一个日期对象
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        //格式化为日期/时间字符串

        Date d2 = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        String yesterday=sdf.format(d2);
        Map yesmap = new HashMap();
        yesmap.put("appId",cp.getAppId());
        yesmap.put("statday",yesterday);
        Cpstat cpstat2 = cpstatHandler.queryByDate(yesmap);
      /*  int appid2=0;

        if (appid!=null&&!"".equals(appid)){
           appid2= Integer.parseInt(appid);
        }*/
       // CpInfo cpInfo=new CpInfo();
        CpInfo pa = cpInfoHandler.select(cp.getAppId());
        Long lastPay = pa.getLastPay();
        Long lastPayFee = pa.getLastPayFee();
        Long lastPayFeeMonth = pa.getLastPayFeeMonth();
        Integer lastPayNum = pa.getLastPayNum();

        Integer yestodayPayNum = pa.getYestodayPayNum();
        Long lastPayRequest = pa.getLastPayRequest();
        Long lastPayRequestMonth = pa.getLastPayRequestMonth();
        Long lastPayNumMonth = pa.getLastPayNumMonth();
        Long totalPayRequest = pa.getTotalPayRequest();
        long totalPayFee = pa.getTotalPayFee();
        Long totalPay = pa.getTotalPay();
        Integer totalPayNum = pa.getTotalPayNum();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m=new HashMap();
        m.put("code",0);
        m.put("msg","");
        m.put("tdcount",totalPayNum-lastPayNum);
            m.put("tdamount",totalPay-lastPay);
            m.put("tdfee",totalPayFee-lastPayFee);
        m.put("yescount",yestodayPayNum);
           if (cpstat2!=null){
               m.put("yesamount",cpstat2.getPayAmount());
               m.put("yesfee",cpstat2.getPayFee());
           }else {
               m.put("yesamount",0);
               m.put("yesfee",0);
           }
        m.put("cpInfo",pa);
       // m.put("totaldata",pa);
        m.put("totalcount",totalPayNum);
        String s1 = gson.toJson(m);
        output(s1);
    }








    public CpInfoHandler getCpInfoHandler() {
        return cpInfoHandler;
    }

    public void setCpInfoHandler(CpInfoHandler cpInfoHandler) {
        this.cpInfoHandler = cpInfoHandler;
    }

    public PaylogHandler getPaylogHandler() {
        return paylogHandler;
    }

    public void setPaylogHandler(PaylogHandler paylogHandler) {
        this.paylogHandler = paylogHandler;
    }

    public CpstatHandler getCpstatHandler() {
        return cpstatHandler;
    }

    public void setCpstatHandler(CpstatHandler cpstatHandler) {
        this.cpstatHandler = cpstatHandler;
    }
}

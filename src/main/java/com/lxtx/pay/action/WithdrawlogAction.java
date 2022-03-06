package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.WithdrawlogHandler;
import com.lxtx.pay.pojo.*;
import com.lxtx.pay.utils.PageUtils;
import org.apache.xml.serialize.Printer;
import org.ralasafe.db.sql.xml.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WithdrawlogAction extends BaseAction{

    private Withdrawlog withdrawlog;
    private WithdrawlogHandler withdrawlogHandler;
    private  static SimpleDateFormat sdf = new SimpleDateFormat();
    //今天的
    public  void withdrawlogtoday(){
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        Map paramMap=new HashMap();
        if(cpInfo!=null){
            paramMap.put("appId",cpInfo.getAppId());
        }
        Withdrawlog w =  withdrawlogHandler.withdrawlogtoday(paramMap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("data",w);
        String s1 = gson.toJson(map);
        System.out.println("json:::::"+s1);
        output(s1);

    }
    //一周的
    public  void withdrawlogaweek(){
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        Map paramMap=new HashMap();
        if(cpInfo!=null){
            paramMap.put("appId",cpInfo.getAppId());
        }
        Withdrawlog w =  withdrawlogHandler.withdrawlogaweek(paramMap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",w);
        map.put("data",w);
        String s1 = gson.toJson(map);
        System.out.println("json:::::"+s1);
        output(s1);

    }
    //昨天的
    public  void withdrawlogyesterday(){
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        Map paramMap=new HashMap();
        if(cpInfo!=null){
            paramMap.put("appId",cpInfo.getAppId());
        }
        Withdrawlog ll = withdrawlogHandler.withdrawlogyesterday(paramMap);
        System.out.println("111111111111111"+ll);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",ll);
        map.put("data",ll);
        String s1 = gson.toJson(map);
        System.out.println("json:::::"+s1);
        output(s1);

    }




    public void queryAll(){

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<Withdrawlog> all = withdrawlogHandler.getAll();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",all.size());
        map.put("data",all);
        String s1 = gson.toJson(map);
        output(s1);

    }

    public  void quarywithdraw(){
        String id =  servletRequest.getParameter("id");
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<Withdrawlog> all =  withdrawlogHandler.quarywithdraw(Long.parseLong(id));
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",all.size());
        map.put("data",all);
        String s1 = gson.toJson(map);
        output(s1);
    }

    public void Allwithdrawlog(){
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        String id = this.servletRequest.getParameter("Id");
        String OrderId = this.servletRequest.getParameter("OrderId");
        String Type = this.servletRequest.getParameter("Type");
        String Status = this.servletRequest.getParameter("Status");
        String date = this.servletRequest.getParameter("date");
        String typeid = this.servletRequest.getParameter("typeid");
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));

        Map paramMap=new HashMap();
        if (id!=null&&!"".equals(id)){
            paramMap.put("Id",id);
        }if (OrderId!=null&&!"".equals(OrderId)){
            paramMap.put("OrderId",OrderId);
        }if (Type!=null&&!"".equals(Type)){
            paramMap.put("Type",Type);
        }
        if (typeid!=null&&!"".equals(typeid)){
            paramMap.put("typeid",typeid);
        }
        if (Status!=null&&!"".equals(Status)){
            paramMap.put("Status",Status);
        }
        if(date!=null && !date.isEmpty()){
            String[] range = date.split("~");
            String date1=range[0].trim()+" 00:00:00";
            String date2=range[1].trim()+" 24:00:00";
            paramMap.put("startTime",date1);
            paramMap.put("endTime",date2);
        }
        if (typeid!=null&&!"".equals(typeid)){
            paramMap.put("typeid",typeid);
        }

        if(cpInfo!=null){
            paramMap.put("appId",cpInfo.getAppId());
            GsonBuilder gsonbuilder = new GsonBuilder();
            gsonbuilder.serializeNulls();
            Gson gson = gsonbuilder.create();
            List<Withdrawlog> allByPage = withdrawlogHandler.Allwithdrawlog(page,limit,paramMap);
            int count = withdrawlogHandler.Allwithdrawlogcount(paramMap);
            Map map=new HashMap();
            map.put("code",0);
            map.put("msg","");
            map.put("count",count);
            map.put("data",allByPage);
            String s1 = gson.toJson(map);
            output(s1);
        }else {

        }


    }
    public void queryAllwithdrawlog(){
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<Withdrawlog> all = withdrawlogHandler.getAll();
        List<Withdrawlog> allByPage = withdrawlogHandler.queryAllByPageAndLimit(page,limit);
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",all.size());
        map.put("data",allByPage);
        String s1 = gson.toJson(map);
        output(s1);

    }
    public void queryAllByPageAndLimit(){
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        List<Withdrawlog> all = withdrawlogHandler.getAll();
        List<Withdrawlog> allByPage = withdrawlogHandler.queryAllByPageAndLimit(page,limit);
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",all.size());
        map.put("data",allByPage);
        String s1 = gson.toJson(map);
        output(s1);

    }

    public void querycondition() throws ParseException {
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        Map conmap = new HashMap();
        String idstr = this.servletRequest.getParameter("Id");
        String type = this.servletRequest.getParameter("typeid");
        String orderId =  this.servletRequest.getParameter("OrderId");
        String statuss = this.servletRequest.getParameter("Status");
        String createTime =this.servletRequest.getParameter("CreateTime");

        int rowIndex = PageUtils.getRowIndex(page, limit);
        conmap.put("rowIndex",rowIndex);
        conmap.put("limit",limit);
        if (!"".equals(idstr)&&idstr!=null){
            Long id = Long.parseLong(idstr);
            conmap.put("id",id);
        }
        if (!"".equals(type)&&type!=null){

            conmap.put("typeid",  Integer.parseInt(type));
        }
        conmap.put("orderId",orderId);

        if(!"".equals(statuss)&& statuss != null){
            int status=  Integer.parseInt(statuss);
            conmap.put("status",status);
        }
        List<Withdrawlog> list=new ArrayList<>();
        String earlyDate = this.servletRequest.getParameter("earlyDate");
        String lateDate = this.servletRequest.getParameter("lateDate");

        if (earlyDate!=null&&!"".equals(earlyDate)&&lateDate!=null&&!"".equals(lateDate)){
            if (earlyDate.equals(lateDate)){
                conmap.put("CreateDay",earlyDate);
                list = withdrawlogHandler.querycondition(conmap);
            }else {
                conmap.put("earlyDate",earlyDate);
                conmap.put("lateDate",lateDate);
                list = withdrawlogHandler.querywithdrawlogdates(conmap);
            }
        }else if (earlyDate==null||"".equals(earlyDate)){
            conmap.put("CreateDay",lateDate);
            list = withdrawlogHandler.querycondition(conmap);
        }else {
            conmap.put("CreateDay",earlyDate);
            list = withdrawlogHandler.querycondition(conmap);
        }
        int count =  withdrawlogHandler.withdrawcount(conmap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map m = new HashMap() ;
        m.put("code",0);
        m.put("msg","");
        m.put("count",count);
        m.put("data",list);
        String s1 = gson.toJson(m);
        output(s1);

    }


    public Withdrawlog getWithdrawlog() {
        return withdrawlog;
    }

    public void setWithdrawlog(Withdrawlog withdrawlog) {
        this.withdrawlog = withdrawlog;
    }

    public WithdrawlogHandler getWithdrawlogHandler() {
        return withdrawlogHandler;
    }

    public void setWithdrawlogHandler(WithdrawlogHandler withdrawlogHandler) {
        this.withdrawlogHandler = withdrawlogHandler;
    }



}

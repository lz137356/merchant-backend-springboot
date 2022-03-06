package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.AgentHandler;
import com.lxtx.pay.pojo.Agent;
import com.lxtx.pay.pojo.AgentStatistics;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentAction extends  BaseAction{
    private AgentHandler agentHandler;


    public void queryOne() throws ServletException, IOException {
        String userName = this.servletRequest.getParameter("username");
        String password = this.servletRequest.getParameter("password");
        
        Map map=new HashMap();
        if (userName!=null&&!"".equals(userName)){
            map.put("UserName",userName);
        }
        if (password!=null&&!"".equals(password)){
            map.put("UserPass",password);
        }
        Map resmap=new HashMap();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();



        Agent agent = agentHandler.queryOne(map);
        if (agent==null){
            resmap.put("code","0");
            resmap.put("msg","此用户不存在");
        }else {
            resmap.put("code","1");
            resmap.put("msg","");

        }
        output(gson.toJson(resmap));


    }

    public void login() throws ServletException, IOException {




        String userName = this.servletRequest.getParameter("username");
        String password = this.servletRequest.getParameter("password");
        System.out.println(userName);
        System.out.println(password);
        Map map=new HashMap();
        if (userName!=null&&!"".equals(userName)){
            map.put("UserName",userName);
        }
        if (password!=null&&!"".equals(password)){
            map.put("UserPass",password);
        }
        Map resmap=new HashMap();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Agent agent = agentHandler.queryOne(map);
        if (agent==null){
            resmap.put("code","0");
            resmap.put("msg","此用户不存在");
            this.servletRequest.setAttribute("code",0);
            this.servletResponse.sendRedirect("../login.jsp");
        }else {
            if (agent.getUserPass().equals(password)){
                this.servletRequest.getSession().setAttribute("Agent",agent);
                this.servletRequest.getRequestDispatcher("/agent/index.jsp").forward(  this.servletRequest, this.servletResponse);
            }else {
                this.servletResponse.sendRedirect("../login.jsp");

            }
        }
    }
    //新增
    public void addagent(){
        Agent agent = new Agent();
        String userName =   servletRequest.getParameter("UserName");
        String userpass =   servletRequest.getParameter("UserPass");
        String PayFeeFix =   servletRequest.getParameter("PayFeeFix");
        String PayFeeRate =   servletRequest.getParameter("PayFeeRate");
        String WithdrawFeeFix =   servletRequest.getParameter("WithdrawFeeFix");
        String WithdrawFeeRate =   servletRequest.getParameter("WithdrawFeeRate");
        String status =   servletRequest.getParameter("Status");
        if (!"".equals(userName)&&userName!=null){
            agent.setUserName(userName);

        }
        if (!"".equals(userpass)&&userpass!=null){
            agent.setUserPass(userpass);

        }
        if (!"".equals(PayFeeFix)&&PayFeeFix!=null){
            agent.setPayFeeFix(Integer.parseInt(PayFeeFix));

        }
        if (!"".equals(PayFeeRate)&&PayFeeRate!=null){
            agent.setPayFeeRate(Long.parseLong(PayFeeRate));

        }if (!"".equals(WithdrawFeeFix)&&WithdrawFeeFix!=null){
            agent.setWithdrawFeeFix(Integer.parseInt(WithdrawFeeFix));

        }
        if (!"".equals(WithdrawFeeRate)&&WithdrawFeeRate!=null){
            agent.setWithdrawFeeRate(Long.parseLong(WithdrawFeeRate));

        }
        if (!"".equals(status)&&status!=null){
            agent.setStatus(Integer.parseInt(status));

        }
        agent.setTotalPay((long) 0);
        agent.setTotalPayFee((long) 0);
        agent.setTotalWithdraw((long) 0);
        agent.setTotalWithdrawfee((long) 0);
        agent.setRemain((long) 0);
        agent.setCanOver((long) 0);
        agentHandler.insert(agent);
        output("1");
    }


    public void updateagent(){
        String id =   servletRequest.getParameter("Id");
        Agent agent = new Agent(); //agentHandler.select(id);
        String userName =   servletRequest.getParameter("UserName");
        String userpass =   servletRequest.getParameter("UserPass");
        String PayFeeFix =   servletRequest.getParameter("PayFeeFix");
        String PayFeeRate =   servletRequest.getParameter("PayFeeRate");
        String WithdrawFeeFix =   servletRequest.getParameter("WithdrawFeeFix");
        String WithdrawFeeRate =   servletRequest.getParameter("WithdrawFeeRate");
        String status =   servletRequest.getParameter("Status");
//        String Remain=servletRequest.getParameter("Remain");
//        String CanOver=servletRequest.getParameter("CanOver");

        if (!"".equals(id)&&id!=null){
            agent.setId(Integer.parseInt(id));

        }
        if (!"".equals(userName)&&userName!=null){
            agent.setUserName(userName);

        }
        if (!"".equals(userpass)&&userpass!=null){
            agent.setUserPass(userpass);

        }
        if (!"".equals(PayFeeFix)&&PayFeeFix!=null){
            agent.setPayFeeFix(Integer.parseInt(PayFeeFix));

        }
        if (!"".equals(PayFeeRate)&&PayFeeRate!=null){
            agent.setPayFeeRate(Long.parseLong(PayFeeRate));

        }if (!"".equals(WithdrawFeeFix)&&WithdrawFeeFix!=null){
            agent.setWithdrawFeeFix(Integer.parseInt(WithdrawFeeFix));

        }
        if (!"".equals(WithdrawFeeRate)&&WithdrawFeeRate!=null){
            agent.setWithdrawFeeRate(Long.parseLong(WithdrawFeeRate));

        }
        if (!"".equals(status)&&status!=null){
            agent.setStatus(Integer.parseInt(status));

        }

        int list = agentHandler.update(agent);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count","");
        map.put("data",list);
        String s1 = gson.toJson(map);
        output(s1);

    }



    //查询所有
    public void allagent(){
        List<Agent> agent =  agentHandler.getAll();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",agent.size());
        map.put("data",agent);
        String s1 = gson.toJson(map);
        output(s1);
        System.out.println(s1);
    }

    //条件查询
    public void  queryAllagent(){
        String Status = this.servletRequest.getParameter("status");
        String UserName = this.servletRequest.getParameter("UserName");

        Map map = new HashMap();
        if (Status!=null&&!"".equals(Status)){
            int status=Integer.parseInt(Status);
            map.put("status",status);
        }
        if (UserName!=null&&!"".equals(UserName)){
            int userName=Integer.parseInt(UserName);
            map.put("UserName",userName);
        }
        List<Agent> list =  agentHandler.queryAllagent(map);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        map.put("code",0);
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        String s1 = gson.toJson(map);
        output(s1);
    }

    public void selectWithCpinfo(){
        Map conmap = new HashMap();
        String idstr = this.servletRequest.getParameter("Id");
        String appIdstr = this.servletRequest.getParameter("AppId");
        if (idstr!=null&&!"".equals(idstr)){
            int id=Integer.parseInt(idstr);
            conmap.put("id",id);
        }
        if (appIdstr!=null&&!"".equals(appIdstr)){
            int appid=Integer.parseInt(appIdstr);
            conmap.put("appid",appid);
        }
        List<Agent> list =  agentHandler.selectWithCpinfo(conmap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        String s1 = gson.toJson(map);
        output(s1);


    }
    public void deleteagent(){
        String Id =   servletRequest.getParameter("Id");
        Integer id =  agentHandler.deleteagent(Integer.parseInt(Id));
        output(String.valueOf(id));
    }

    public void updateStatus(){
        String statusstr = this.servletRequest.getParameter("Status");
        String idstr = this.servletRequest.getParameter("Id");
        Agent agent=new Agent();
        if (idstr!=null&&!"".equals(idstr)){
            agent.setId(Integer.parseInt(idstr));
        }
        if (statusstr!=null&&!"".equals(statusstr)){
            agent.setStatus(Integer.parseInt(statusstr));
        }

        int update = agentHandler.updateStatus(agent);
        if (update==1){
            output("1");
        }else {
            output("0");
        }

    }

    public void agentStatistics(){
        List<AgentStatistics> list = agentHandler.agentStatistics();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        String s1 = gson.toJson(map);
        System.out.println(s1);
        output(s1);


    }
    public AgentHandler getAgentHandler() {
        return agentHandler;
    }

    public void setAgentHandler(AgentHandler agentHandler) {
        this.agentHandler = agentHandler;
    }
}

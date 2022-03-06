package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.*;
import com.lxtx.pay.pojo.CpInfo;
import com.opensymphony.xwork2.ActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpInfoAction extends BaseAction{
    private CpInfoHandler cpInfoHandler;
    private CpstatHandler cpstatHandler;
    private PaylogHandler paylogHandler;
    private LoginLogHandler loginLogHandler;
    private AgentHandler agentHandler;

    public AgentHandler getAgentHandler() {
        return agentHandler;
    }

    public void setAgentHandler(AgentHandler agentHandler) {
        this.agentHandler = agentHandler;
    }

    public LoginLogHandler getLoginLogHandler() {
        return loginLogHandler;
    }

    public void setLoginLogHandler(LoginLogHandler loginLogHandler) {
        this.loginLogHandler = loginLogHandler;
    }




    //显示密码
    public void  cppassword(){

        //String password = "123456";
        //String cpInfo = "123456";
       String password =  servletRequest.getParameter("password");
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        if(password.equals(cpInfo.getUserPass())){
            System.out.println("密码成功"+111111);
        }else {
            System.out.println("密码错误");
        }



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

    //添加CpInfo数据
    public void add(CpInfo cpInfo) {
        cpInfoHandler.insert(cpInfo);
    }

    //删除指定id的数据
    public void delete(int appId){
        cpInfoHandler.delete(appId);
    }

    //打印指定id的CpInfo数据
    public void select(){
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        int appId = cpInfo.getAppId();

        CpInfo select = cpInfoHandler.select(appId);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        output(gson.toJson(select));

    }

    //打印所有CpInfo数据
    public void queryAll() throws IOException {
        List all = cpInfoHandler.getAll();
        Gson gson=new Gson();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",all.size());
        map.put("data",all);
        String s1 = gson.toJson(map);
        output(s1);
    }



    //修改指定id的CpInfo数据
    public void update() {
        CpInfo cpInfo=new CpInfo();
        String appId = this.servletRequest.getParameter("AppId");

        String status = this.servletRequest.getParameter("Status");
        if (appId!=null&&!"".equals(appId)){
            cpInfo.setAppId(Integer.parseInt(appId));
        }

        if (status!=null&&!"".equals(status)){
            cpInfo.setStatus(Integer.parseInt(status));
        }
        int update = cpInfoHandler.update(cpInfo);
        if (update==1){
            output("1");
        }else {
            output("0");
        }
    }

    /**
     * 修改当前登录商户密码
     */
    public void resetPassword(){
        CpInfo cpInfo = (CpInfo) ActionContext.getContext().getSession().get("cpInfo");
        String password = this.servletRequest.getParameter("password");
        cpInfo.setUserPass(password);
        int update = cpInfoHandler.update(cpInfo);
        if (update==1){
            ActionContext.getContext().getSession().remove("cpInfo");
            output("1");
        }else {
            output("0");
        }
    }

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }
    public CpInfoHandler getCpInfoHandler() {
        return cpInfoHandler;
    }

    public void setCpInfoHandler(CpInfoHandler cpInfoHandler) {
        this.cpInfoHandler = cpInfoHandler;
    }

    public void setPaystatHandler(PaystatHandler paystatHandler) {
    }

    public void setWithdrawstatHandler(WithdrawstatHandler withdrawstatHandler) {
    }

}
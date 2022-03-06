package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.LoginLogHandler;
import com.lxtx.pay.handler.UserHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.LoginLog;
import com.lxtx.pay.pojo.User;
import com.lxtx.pay.utils.PageUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class LoginLogAction extends BaseAction  {
    private LoginLogHandler loginLogHandler;


    public LoginLogHandler getLoginLogHandler() {
        return loginLogHandler;
    }

    public void setLoginLogHandler(LoginLogHandler loginLogHandler) {
        this.loginLogHandler = loginLogHandler;
    }

    /**
     * 分页
     *
     */
    public void page(){
        CpInfo cpInfo = (CpInfo) servletRequest.getSession().getAttribute("cpInfo");
        int page = Integer.parseInt(this.servletRequest.getParameter("page"));
        int limit = Integer.parseInt(this.servletRequest.getParameter("limit"));
        int rowIndex = PageUtils.getRowIndex(page, limit);
        Map paramMap=new HashMap();
        paramMap.put("AppId",cpInfo.getAppId());
        paramMap.put("rowIndex",rowIndex);
        paramMap.put("limit",limit);
        List<LoginLog> list = loginLogHandler.findByPage(paramMap);
        int total = loginLogHandler.countTotal(paramMap);
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",total);
        map.put("data",list==null?new ArrayList<>():list);
        String s1 = gson.toJson(map);
        output(s1);
    }
}

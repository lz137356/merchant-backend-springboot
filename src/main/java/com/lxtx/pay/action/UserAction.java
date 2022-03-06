package com.lxtx.pay.action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lxtx.pay.handler.UserHandler;
import com.lxtx.pay.pojo.Agent;
import com.lxtx.pay.pojo.User;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class UserAction  extends BaseAction  {
    private UserHandler userHandler;


    public void queryOne(){
        String userName = this.servletRequest.getParameter("username");
        User user = new User();
        user.setUsername(userName);
        User login = userHandler.login(user);
        if (login==null){
            output("0");
        }else {
            output("1");
        }


    }



    public void   login() throws ServletException, IOException {
        String userName = this.servletRequest.getParameter("username");
        String password = this.servletRequest.getParameter("password");
        String rolestr = this.servletRequest.getParameter("role");
        System.out.println("rolestr:::"+rolestr);

       User user=new User();
        if (userName!=null&&!"".equals(userName)){
            user.setUsername(userName);
        }
        if (password!=null&&!"".equals(password)){
           user.setPassword(password);
        }
        Map resmap=new HashMap();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        User user1 = userHandler.login(user);
        if (user1==null){
            this.servletRequest.setAttribute("code",0);
            this.servletResponse.sendRedirect("../login.jsp");
        }else {
            if (user1.getPassword().equals(password)){
                this.servletRequest.getSession().setAttribute("user",user1);
                this.servletRequest.getRequestDispatcher("/manage/index.jsp").forward(  this.servletRequest, this.servletResponse);
            }else {
                this.servletResponse.sendRedirect("../login.jsp");

            }
        }










//        User user=new User();
//        user.setUsername(userName);
//        User login = userHandler.login(user);
//        System.out.println(userName);
//        System.out.println("login:::::"+login);
//        Date date=new Date();
//        if (login!=null){
//            boolean equals = login.getPassword().equals(password);
//            if (equals){
//                Integer role = login.getRole();
//                login.setLogintime(date);
//                if (role==1){
//                    this.servletRequest.getSession().setAttribute("appId",login.getAppId());
//                    this.servletRequest.getSession().setAttribute("cpinfo",login);
//                    this.servletRequest.getRequestDispatcher("/manage/index.jsp").forward(  this.servletRequest, this.servletResponse);
//                }else if (role==2){
//                    this.servletRequest.getSession().setAttribute("appId",login.getAppId());
//                    this.servletRequest.getSession().setAttribute("cpinfo",login);
////                    return "merchantslogin";
//                    this.servletRequest.getRequestDispatcher("/merchants/index.jsp").forward(  this.servletRequest, this.servletResponse);
//                }
//            }
//            else {
//                output("0");
//            }
//        }
    }

    public void queryAllByRole(){
        String rolestr = this.servletRequest.getParameter("role");




        List list=new ArrayList();
        if (rolestr!=null&&!"".equals(rolestr)){
            int role=Integer.parseInt(rolestr);
            list = userHandler.queryAllByRole(role);

        }
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        Map map=new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        String s1 = gson.toJson(map);
        output(s1);


    }

    public void adduser(){
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();
        String userName =   servletRequest.getParameter("username");
        String userpassword =   servletRequest.getParameter("password");
        String role =   servletRequest.getParameter("role");
        String status =   servletRequest.getParameter("status");
        String appid=this.servletRequest.getParameter("appId");
        Map map=new HashMap();




        if (!"".equals(userName)&&userName!=null){
            User user1 = new User();
            user1.setUsername(userName);
            User login = userHandler.login(user1);
            if (login!=null){
                map.put("code",0);
                map.put("msg","该用户已存在，请重新起名。");
                output(gson.toJson(map));
                return;
            }else {
                if (!"".equals(userpassword)&&userpassword!=null){
                    user1.setPassword(userpassword);

                }
                if (!"".equals(role)&&role!=null){
                    user1.setRole(Integer.parseInt(role));

                }
                if (!"".equals(status)&&status!=null){
                    user1.setStatus(Integer.parseInt(status));

                }if (!"".equals(appid)&&appid!=null){
                    user1.setAppId(Integer.parseInt(appid));

                }
                user1.setCreatetime(new Date());
                user1.setLogintime(null);
                user1.setSalt("");
                userHandler.insert(user1);
                map.put("code",1);
                map.put("msg","添加成功。");
                output(gson.toJson(map));
            }
        }


    }
    public void updateuser(){
        User user = new User();
        String userId =   servletRequest.getParameter("userId");
        String userName =   servletRequest.getParameter("username");
        String userpassword =   servletRequest.getParameter("password");
        String role =   servletRequest.getParameter("role");
        String status =   servletRequest.getParameter("status");
        String appid = servletRequest.getParameter("appId");
        if (!"".equals(userId)&&userId!=null){
            user.setUserId(Integer.parseInt(userId));
        }
        if (!"".equals(userName)&&userName!=null){
            user.setUsername(userName);
        }
        if (!"".equals(userpassword)&&userpassword!=null){
            user.setPassword(userpassword);
        }
        if (!"".equals(role)&&role!=null){
            user.setRole(Integer.parseInt(role));

        }
        if (!"".equals(status)&&status!=null){
            user.setStatus(Integer.parseInt(status));

        }if (!"".equals(appid)&&appid!=null){
            user.setAppId(Integer.parseInt(appid));
        }

        userHandler.update(user);


    }


    public void deleteuser(){
        String userId =   servletRequest.getParameter("userId");
        int id =  userHandler.deleteuser(Integer.parseInt(userId));
        output(String.valueOf(id));
    }


    public String execute() throws IOException {
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/html");


        return "user";
    }




    public void queryAll() throws IOException {


        User user=new User();
        user.setUserId(1);
        com.lxtx.pay.pojo.User select = userHandler.select(user);
        System.out.println(select.toString());


    }


    public UserHandler getUserHandler() {
        return userHandler;
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }
}

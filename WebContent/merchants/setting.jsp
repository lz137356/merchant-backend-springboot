<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/12/15
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商户配置</title>
    <style>
        .content>div{
            padding: 20px;
        }
        .label-info{
            font-size: 14px;
            display: inline-block;
            width: 70px;
            margin-right: 50px;
        }
        .content button{
            margin-left: 125px;
            background-color: red;
            border: 0px;
            padding: 8px;
            border-radius: 10%;
            color: white;
        }
        .content button:focus{
            outline: none;
        }
    </style>
</head>
<body>
   <div>
       <div style="padding: 20px;">商户配置</div>
       <div style="padding: 20px;background-color: #f9fafc;margin: 20px;" class="content">
           <div style="color: #7990a0;font-size: 18px;">商户信息</div>
           <div>
               <span class="label-info">登录密码</span>
               <span><input id="password" type="password" style="height: 28px;"/></span>
           </div>
           <div>
               <button onclick="save()">保存</button>
           </div>
           <div style="color: #7990a0;font-size: 18px;">费率信息（ 1为100% ）</div>
           <div style="display: flex;">
               <span class="label-info">复制卡号转卡</span>
               <span style="align-self: center;"><input type="text" disabled style="height: 28px;" value="0.1000"/></span>
           </div>
           <div style="display: flex;">
               <span class="label-info" style="align-self: center;">UPI</span>
               <span><input type="text" disabled style="height: 28px;" value="0.4000"/></span>
           </div>

       </div>
   </div>
</body>
<script src="<%=request.getContextPath()%>/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
<script>
    function save(){
        let password = $('#password').val()
        if(password=='' || password == null){
            return false;
        }
        $.post("<%=request.getContextPath()%>/cpinfo/resetPassword.action",{
            password:$('#password').val()
        },function (res){
           if(res==1){
               // layer.msg('保存成功！');
               setTimeout(function(){
                   parent.location.reload();
               },1000)
           }else{
               // layer.msg('保存失败！');
           }
        },"json")
    }
</script>
</html>

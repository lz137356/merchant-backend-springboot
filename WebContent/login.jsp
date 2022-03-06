<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>登录</title>
    <style>
        html, body {
            height: 100%;
        }

    </style>

    <script src="js/jquery-1.7.2.js" type="text/javascript"></script>
    <script>

        $(function () {
            var ul = $("#ul")
            ul.empty();
            ul.append(
                "<li>\n" +
                "<div style=\"width: 100%;height: 28%;margin-top: 0;\">\n" +
                "<label style=\"text-align: left;color: rgb(201,199,199)\" >User Name:</label>\n" +
                "<input id=\"username\" name=\"username\" style=\"padding-left:6px;width: 97%;height: 32px;margin-top: 2%;border-radius: 5px;border:1px solid rgb(92,92,92)\">\n" +
                "</div>\n" +
                "</li>\n" +
                "<li style=\"margin-top: 20px;\">\n" +
                    "<div style=\"width: 100%;height: 28%;margin-top: 0;\">\n" +
                        "<label style=\"text-align: left;color: rgb(201,199,199)\" >Pass Word:</label>\n" +
                        "<input type='password' id=\"password\" name=\"password\" style=\"padding-left:6px;width: 97%;height: 32px;margin-top: 2%;border-radius: 5px;border: 1px solid rgb(92,92,92)\">\n" +
                    "</div>\n" +
                "</li>\n" +
                "<li style=\"margin-top: 20px;\">\n" +
                    "<div style=\"width: 100%;height: 28%;margin-top: 0;\">\n" +
                        "<label style=\"text-align: left;color: rgb(201,199,199)\" >Google验证码:</label>\n" +
                        "<input placeholder='未设置时可为空'  id=\"googleCode\" name=\"googleCode\" style=\"padding-left:6px;width: 97%;height: 32px;margin-top: 2%;border-radius: 5px;border: 1px solid rgb(92,92,92)\">\n" +
                    "</div>\n" +
                "</li>\n" +
                "<li>\n" +
                "                </li>\n" +
                "                <li>\n" +
                "                    <div style=\"width: 100%;height: 28%;margin-top: 30px;\">\n" +
                "                        <input  id=\"login\" type=\"button\" value=\"Login In\"  style=\"width: 99%;height: 40px;border-radius: 15px;background-color: rgb(167,54,54);color: white;text-align: center\">\n" +
                "                    </div>\n" +
                "                </li>")

            var login = $("#login")
            login.click(function () {
                var loginform = $("#loginform")
                var ser = loginform.serialize();
                var role = $("#role:checked").val();

                loginform.attr("action", "./cpinfo/login")
                loginform.submit();

            })
        })
        $(function () {
        })
        $(document).keyup(function (event) {
            if (event.keyCode == 13) {
                $("#login").click()
            }
        });

    </script>
</head>
<body background="img/loginback.jpg" style="background-size: 100%;background-repeat: no-repeat;">
<div style="position: absolute;left: 50%;top:10%;margin-left: -125px">
    <label STYLE="font-size: 22px;color: white">TOP STAND</label>
    <label STYLE="font-size: 22px;color: white">PAYMENT</label>


</div>
<div style="position: absolute;margin-left: -200px;margin-top: -100px;left: 50%;top: 30%;;width: 400px;height: 350px;border-radius: 20px;background-color: rgb(32,32,32)">
    <div style="width: 80%;height: 80%;margin-left: 10%;margin-right: 10%;margin-top: 8%">
        <form id="loginform" name="loginform" method="post">
            <ul id="ul" style="list-style: none">

            </ul>
        </form>
    </div>
</div>


</body>
</html>
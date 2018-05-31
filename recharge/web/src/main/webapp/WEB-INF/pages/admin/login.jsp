<%--
  Created by IntelliJ IDEA.
  User: 登录页面 lihuimin
  Date: 2016/11/23
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信管理控制台</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/assets/css/amazeui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/z-zhuce.css">
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        body {
            background: url(${pageContext.request.contextPath}/static/images/z_denglu.png);
            background-size: 100% 100%;
        }

        .z_content {
            width: 400px;
            height: 400px;
            left: 50%;
            top: 50%;
            margin-left: -200px;
            margin-top: -160px;
            color: #fff;
            background: rgba(0, 0, 0, 0.5);
            padding: 50px 30px 0 48px;
        }

        .z_content h3 {
            font-size: 20px;
        }


        td a {
            display: inline-block;
            color: #18bd9c;
            margin-top: 10px;
        }

        .z_register {
            float: left;
        }

        .z_login {
            float: right;
        }

        .buttons input {
            margin-top: 50px;
        }

    </style>

</head>

<body>

<div class="container">
    <!--     nav   -->
    <div class="z_content">
        <h3>云通信平台登录</h3>
        <form id="reg-form" action="/admin/LoginOk.do" method="post">
            <table>
                <tr>
                    <td>用户名:</td>
                    <td>
                        <input name="adminName" type="text" id="adminName">
                        <%--<span style="color: white;font-weight: bolder;">${error}</span>--%>
                    </td>
                </tr>
                <tr>
                    <td>密码:</td>
                    <td>
                        <input name="password" type="password" id="password">
                    </td>
                </tr>
            </table>
            <div class="buttons" style="margin-top: 25px;text-align: center;">
                <input value="登 录" type="button" id="btn" style="margin-bottom: 10px;">
                <span id="msg_login">${msg}</span>
            </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js "></script>
<script src="${pageContext.request.contextPath}/static/js/amazeui.min.js "></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js "></script>
<script type="text/javascript">
    $(function (){
        $("#btn").click(function (){
            $("#msg_login").text("");
            var username = $("#adminName").val();
            var password = $("#password").val();
            if(username != "" && password != ""){
                $("#reg-form").submit();
            }else{
                $("#msg_login").text("用户名或密码不能为空");
            }
        });

        document.onkeydown=function mykeyDown(e){
            //compatible IE and firefox because there is not event in firefox
            e = e||event;
            if(e.keyCode == 13) {
                $("#btn").click(); //调用登录按钮的登录事件
            }
        }
    });

</script>
</html>

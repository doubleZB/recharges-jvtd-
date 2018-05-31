<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User:lhm忘记密码找回密码第二步
  Date: 2016/12/29
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script>
    var path = "${path}";
</script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link href="${path}/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link href="${path}/static/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/static/css/headfirst.css">
    <script>
        document.writeln("<!DOCTYPE html");
        document.writeln("<head>");
        document.writeln("<style>");
        document.writeln("</style>");
        document.writeln("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        document.writeln("<title></title>");
        document.writeln("</head>");
        document.writeln("<body >");
        document.writeln("<header>");
        document.writeln("<div class='nav'>");
        document.writeln("<a class = 'nav_1' href='"+path+"/homePage/indexPage.jsp'><img src='"+path+"/homePage/images/logo.png'></a>");
        document.writeln("<div class='nav_2' style='font-size: 16px;'>");
        document.writeln("合作&nbsp;&nbsp;&nbsp;共赢");
        document.writeln("</div>");
        document.writeln("</header>");
        document.writeln(" ");
        document.writeln("</body>");
        document.writeln("</html>");
        document.writeln("");
    </script>
    <style>
        .container {
            margin: 0;
            padding: 0;
            width: inherit;
            min-width: 1000px;
            height: 100%;
            position: relative;
        }
        .z_content {
            position: absolute;
            width: 780px;
            left: 50%;
            top: 50%;
            margin-left: -390px;
            margin-top: -270px;
            color: #666;
        }

        .z_first {
            width: 100%;
            height: 40px;
            margin-bottom: 40px;
        }

        .z_first img {
            display: block;
            width: 762px;
            margin: 0 auto;
            margin-top: 60px;
        }

        .z_content h3 {
            font-size: 18px;
            font-weight: 100;
            text-indent: 18px;
            border-left: 4px solid #f67901;
            display: block;
            margin: 0 auto!important;
        }

        form {
            border-top: 1px solid #f67901;
            display: block;
            margin: 25px auto 0 auto;
            position: relative;
        }

        .one {
            overflow: hidden;
            margin-top: 27px;
        }

        .one span {
            float: left;
            width: 100px;
            height: 50px;
            line-height: 50px;
            text-align: right;
            margin-right: 20px;
        }

        .one>div {
            float: left;
            width: 310px;
            height: 50px;
            border: 1px solid #ccc;
        }

        .one>div img {
            width: 20px;
            height: 25px;
            margin: 0 10px;
        }

        .one>div input {
            width: 100%;
            height: 100%;
            border: 0;
            font-size: 14px;
            padding-left: 20px;
        }

        .s_box {
            margin-top: 60px;
            width: 430px;
            margin: 0 auto;
            overflow: hidden;
        }

        .z_btn {
            float: right;
            display: block;
            width: 315px;
            height: 50px;
            border: 0;
            border-radius: 6px;
            background: #f67901;
            color: #fff;
            font-size: 18px;
            margin-top: 30px;
        }
        .msg_password{
            position: absolute;
            right: 0px;
            top: 230px;
        }
        .msg_passwords{
            position: absolute;
            right: 0px;
            top: 305px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="z_content">
        <h3>密码找回</h3>
        <form id="reg-form" action="" method="post">
            <span class="z_error msg_password"></span>
            <span class="z_error msg_passwords"></span>
            <div class="z_first"><img src="${path}/static/images/zhmm2.png" alt=""></div>
            <div class="s_box">
                <div class="one" style="margin:0">
                    <span>用户名：</span>
                    <div style="line-height: 50px;border:0;">
                        <table>
                            <c:forEach var="list" items="${list}">
                                <tr>
                                    <span id="userName">${list.userName}</span>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>

                <div class="one">
                    <span>新密码：</span>
                    <div>
                        <input class="am-form-field col-lg-10 col-md-9 col-xs-8" id="newPassword" onblur="Password()" type="password" placeholder="4~10位、字母开头、还允许数字、特殊字符">
                    </div>
                </div>

                <div class="one">
                    <span>确认新密码：</span>
                    <div>
                        <input class="am-form-field col-lg-10 col-md-9 col-xs-8" type="password" id="newAgainPassword" onblur="AgainPassword()" placeholder="再次输入新密码">
                    </div>
                </div>

                <button type="button" class="z_btn" id="Add" onclick="submitPassword()">提交</button>
            </div>
        </form>
    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js "></script>
<script src="${path}/static/js/headfirst.js"></script>
<script>
    $(".msg_password").css("color","red");
    $(".msg_passwords").css("color","red");
    //验证密码
    function Password(){
        var Password=$("#newPassword").val();
        var password = /^[a-zA-Z][a-zA-Z0-9_]{3,9}$/;
        if(password.test(Password)){
            $(".msg_password").text("");
            $('#Add').attr('disabled',false);
        }else if(Password == ""){
            $(".msg_password").text("请输入密码");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_password").text("密码4~10位,以字母开头!");
            $('#Add').attr('disabled',true);
        }
    }
    //二次验证
    function AgainPassword(){
        var Password=$("#newAgainPassword").val();
        var password = /^[a-zA-Z][a-zA-Z0-9_]{3,9}$/;
        if(password.test(Password)){
            $(".msg_passwords").text("");
            $('#Add').attr('disabled',false);
        }else if(Password == ""){
            $(".msg_passwords").text("请输入密码");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_passwords").text("密码4~10位,以字母开头!");
            $('#Add').attr('disabled',true);
        }
    }
    function submitPassword(){
        var userName=$("#userName").text();
        var newPassword=$("#newPassword").val();
        var newAgainPassword=$("#newAgainPassword").val();
        if(newPassword==''){
            alert("输入密码不能为空！");
            return;
        }
        if(newAgainPassword==''){
            alert("确认密码不能为空！");
            return;
        }
        if(newPassword==newAgainPassword){
            $.ajax({
                url:"${pageContext.request.contextPath }/admin/findPassword.do",
                type:"post",
                data:{
                    userName:userName,
                    password:newAgainPassword
                },
                dataType:"json",
                success:function(data){
                    if(data){
                        alert("恭喜你修改密码成功，请重新登录！");
                        location.href="${path}/admin/login.do"
                    }else{
                        alert("抱歉，修改失败！");
                    }
                }
            });
        }else{
            alert("两次输入密码不一样，请核对后重新输入！");
        }
    }
</script>
</body>
</html>

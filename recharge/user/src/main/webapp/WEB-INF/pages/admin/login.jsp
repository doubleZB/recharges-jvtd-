<%--
  Created by IntelliJ IDEA.
  User: 登录页面 lihuimin
  Date: 2016/11/30
  Time: 17:12
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
    <%--<%@include file="/WEB-INF/pages/common/head.jsp"%>--%>
    <link href="${path}/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link href="${path}/static/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/css/headfirst.css">
    <script type="text/javascript" src="${path}/homePage/js/head.js"></script>
        <style>
            .container {
                margin: 0;
                padding: 0;
                width: inherit;
                min-width: 1000px;
                height: 100%;
                position: relative;
                background: url(${pageContext.request.contextPath}/static/images/login.jpg) no-repeat;
                -webkit-background-size:100% 100%;;
                background-size:100% 100%;
            }
            .active {
                border-bottom: 3px solid #323232;
                color: #323232;
            }
            .z_content {
                position: absolute;
                width: 470px;
                left: 50%;
                top: 50%;
                margin-left: -245px;
                margin-top: -270px;
                color: #666;
            }
            .z_first {
                width: 100%;
                height: 90px;
                margin-bottom: 40px;
                margin-top:70px;
            }
            .z_first img {
                width: 100%;
                height: 100%;
            }
            .z_content h3 {
                font-size: 18px;
                font-weight: 100;
                text-indent: 18px;
                color: #f67901;
                border-left: 4px solid #f67901;
                display: block;
                margin: 0 auto!important;
            }
            form {
                border-top: 1px solid #f67901;
                display: block;
                margin: 25px auto 0 auto;
            }
            .one {
                overflow: hidden;
                margin-top: 27px;
            }
            .two {
                overflow: hidden;
                line-height:35px;
            }
            .one span {
                float: left;
                width: 70px;
                height: 35px;
                line-height: 35px;
                text-align: right;
                margin-right: 10px;
            }
            .one>div {
                float: left;
                width: 230px;
                height: 35px;
                border: 1px solid #ccc;
            }
            .one>div img {
                width: 20px;
                height: 25px;
                margin: 0 10px;
            }
            .one>div input {
                width: 80%;
                height: 100%;
                border: 0;
                font-size: 14px;
            }
            .z_btn {
                display: block;
                width: 315px;
                height: 50px;
                border: 0;
                border-radius: 6px;
                background: #f67901;
                color: #fff;
                font-size: 18px;
            }
            .z_jzmm {
                margin: 10px 0;
                font-size: 12px;
                color: #999999;
                float: left;
            }
            /*修改checkbox默认样式*/
            input[type=checkbox] {
                display: inline-block;
                vertical-align: text-bottom;
                width: 12px;
                height: 12px;
                -webkit-appearance: none;
                margin-right: .15rem;
                border: 0;
                background-color: transparent;
                outline: none;
            }
            input[type=checkbox]:after {
                content: "";
                display: block;
                width: 12px;
                height: 12px;
                background-image: url(${path}/static/images/z_check.png);
                background-size: 100% 100%;
                border: 0;
                background-color: transparent;
                outline: none;
            }
            input[type=checkbox]:checked:after {
                content: "";
                display: block;
                width: 12px;
                height: 12px;
                background-image: url(${path}/static/images/z_checkC.png);
                background-size: 100% 100%;
                border: 0;
                background-color: transparent;
                outline: none;
            }
            .z_forget {
                margin: 10px 0;
                text-align: right;
            }
            .z_forget a {
                color: #ff6600;
                font-size: 14px;
                text-decoration: underline;
            }
            .s_login {
                margin:0 auto;
                width: 320px;
            }
            label {
                font-weight: 500;
            }
            .s_forget {
                float: right;
                font-size: 12px;
                margin: 10px 0;
                text-decoration: underline!important;
                color: #333333;
            }
            .nav_13 a{
                font-size:14px!important;
            }
    </style>
</head>
<body>
<div class="container">
    <div class="z_content">
        <div class="z_first"><img src="${pageContext.request.contextPath}/static/images/z_login.png" alt=""></div>
        <div class="s_login">
            <h3>商户登陆</h3>
        <form id="reg-form" action="/admin/loginOk.do" method="post">
            <div class="one">
                <span>用户名：</span>
                <div>
                    <img src="${pageContext.request.contextPath}/static/images/z_user.png" alt="">
                    <input name="userName" type="text" id="userName" placeholder="请输入用户名">
                </div>
            </div>

            <div class="one">
                <span>密码：</span>
            <div>
                <img src="${pageContext.request.contextPath}/static/images/z_passd.png" alt="">
                <input  placeholder="请输入密码" id="password" name="password" type="password">
            </div>
            </div>
            <div class="two">
                <p class="z_jzmm">
                    <input type="checkbox" id="z_remember">
                    <label for="z_remember">记住用户名</label>
                </p>
                <a class="s_forget " href="${path}/admin/forgetPassword.do">忘记密码？</a>
            </div>

            <div class="buttons" style="margin-top: 5px;text-align: center;">
                <button type="button" class="z_btn" id="btn">立即登录</button>
                <span id="msg_login">${msg}</span>
            </div>
            <p class="z_forget">
                <a href="${pageContext.request.contextPath}/admin/register.do">没有账号，立即注册</a>
            </p>
        </form>
        </div>
    </div>
</div>
</body>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js "></script>
<script src="${path}/static/js/headfirst.js"></script>
<script type="text/javascript">
    $(function(){
    $(".nav_13 a").eq(0).addClass("active");
});
    $(function (){
        $("#btn").click(function (){
            $("#msg_login").text("");
            var username = $("#userName").val();
            var password = $("#password").val();
            if(username != "" && password != ""){
                //判断用户是不是关闭状态登录的
                $.ajax({
                    url:"${pageContext.request.contextPath}/admin/checkUserNameStatus.do",
                    type:"post",
                    data:{
                        userName:username,
                        password:password
                    },
                    dataType:"json",
                    success:function(data){
                        if(data==0){
                            alert("抱歉，您的用户状态已经关闭，请咨询相关业务人员");
                        }else{
                            $("#reg-form").submit();
                        }
                    }
                })
            }else{
                $("#msg_login").text("用户名或密码不能为空");
            }
        });
        document.onkeydown=function mykeyDown(e){

            e = e||event;
            if(e.keyCode == 13) {
                $("#btn").click(); //调用登录按钮的登录事件
            }
        }
    });

</script>
</html>

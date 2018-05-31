<%--
  User: 王彩霞
  Date: 2017/4/13
  Time: 14:32
  To change this template use File | Settings | File Templates.
  注册页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<script>
    var path = "${path}";
</script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
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
            background: url(${pageContext.request.contextPath}/static/images/register.jpg) no-repeat;
            -webkit-background-size:100% 100%;;
            background-size:100% 100%;
        }

        .z_content {
            position: absolute;
            width: 555px;
            left: 50%;
            top: 50%;
            margin-left: -297px;
            margin-top: -270px;
            color: #666;
        }

        .z_first img {
            width: 100%;
            height: 100%;
        }

        .z_content h3 {
            font-size: 18px;
            font-weight: 100;
            text-indent: 18px;
            border-left: 4px solid #f67901;
            display: block;
            margin: 0 auto !important;
            width:70%;
        }

        form {
            width: 70%;
            border-top: 1px solid #f67901;
            display: block;
            margin: 18px auto 0 auto;
        }

        .one {
            overflow: hidden;
            margin-top: 20px;
        }
        .one span {
            float: left;
            width: 110px;
            height: 35px;
            line-height: 35px;
            text-align: right;
            margin-right: 10px;
        }
        .one > div {
            float: left;
            width: 230px;
            height: 35px;
            border: 1px solid #ccc;
        }
        .one > div img {
            width: 20px;
            height: 25px;
            margin: 0 10px;
        }
        .one > div input {
            width: 80%;
            height: 100%;
            border: 0;
            font-size: 14px;
        }

        .z_btn {
            display: block;
            width: 315px;
            height: 45px;
            border: 0;
            border-radius: 6px;
            background: #f67901;
            color: #fff;
            font-size: 18px;
            margin: 30px auto 0 auto;
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

        .z_forget {
            margin: 10px 0;
            text-align: center;
            color: #666;
            font-size: 14px;
        }

        .z_forget a {
            font-size: 14px;
            text-decoration: underline;
        }

        .s_login {
            float: left;
            width: 550px;
            margin-top:70px;
        }

        .s_yzm {
            float: left;
            height: 35px;
            width: 115px;
            border: 1px solid #ccc;
            padding-left: 20px;
        }

        .one .s_hqyzm {
            float: left;
            width: 105px;
            height: 35px;
            line-height: 35px;
            text-align: center;
            border: 1px solid #f67901;
            color: #fff;
            cursor: pointer;
            background: #f67901;

            margin-left: 10px;
        }

        label {
            font-weight: 500;
        }

        .one .yan,.one .username,.one .userCnName,.one .password,.one .repeatPassword,.one .phone{
            float: left;
            width: inherit;
            text-indent: 88px;
            font-size: 12px;
            color:red;
            margin-right: 0px;
            height:20px;
            line-height: 20px;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="z_content">
        <div class="s_login">
            <h3>快速注册</h3>
            <form id="reg-form" action="" method="post">
                <div class="one">
                    <span>用 &nbsp;户 名：</span>
                    <div>
                        <img src="${pageContext.request.contextPath}/static/images/z_user.png" alt="">
                        <input type="text" id="userName" name="userName" placeholder="请输入用户名" onblur="checkUserName()">
                    </div>
                    <span class="username"></span>
                </div>
                <div class="one">
                    <span>用户简称：</span>
                    <div>
                        <img src="${pageContext.request.contextPath}/static/images/z_user.png" alt="">
                        <input type="text" id="userCnName" name="userCnName" placeholder="请输入用户简称名" onblur="checkUserCnName()">
                    </div>
                    <span class="userCnName"></span>
                </div>
                <div class="one">
                    <span>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
                    <div>
                        <img src="${pageContext.request.contextPath}/static/images/z_passd.png" alt="">
                        <input type="password" id="passWord" name="password" placeholder="请输入密码" onblur="checkPassword()">
                    </div>
                    <span class="password"></span>
                </div>
                <div class="one">
                    <span>确认密码：</span>
                    <div>
                        <img src="${pageContext.request.contextPath}/static/images/z_passd.png" alt="">
                        <input type="password" id="repeatPassword" placeholder="请再次输入密码" onblur="AgainPassword()">
                    </div>
                    <span class="repeatPassword"></span>
                </div>
                <div class="one">
                    <span>绑定手机：</span>
                    <div>
                        <img src="${pageContext.request.contextPath}/static/images/z_passd.png" alt="">
                        <input type="text" id="phoneNumber" name="mobile" placeholder="请输入手机号" onblur="checkPhoneNumber();">
                    </div>
                    <span class="phone"></span>
                </div>
                <div class="one">
                    <span>验 &nbsp;证 码：</span>
                    <input class="s_yzm" type="text" placeholder="短信验证码" id="phoneCodeText">
                    <input class="s_hqyzm" id="phoneCode" type="button" onclick="verifyCode()" value="获取验证码"/>
                    <span class="yan"></span>
                </div>

                <button type="button" id="register" class="z_btn">立即注册</button>
                <p class="z_forget">
                    立即注册表示同意接受<a href="${pageContext.request.contextPath}/register/messageAgreement.do">《云通信商户协议》</a>
                </p>
            </form>
        </div>
    </div>

</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js "></script>
<script src="${path}/static/js/headfirst.js"></script>

<script>
    $(function(){
        $(".nav_13 a").eq(1).addClass("active");

        //新闻下拉
        $(".navnav").mouseenter(function () {
            $(this).find(".xiala").css("display", "block");
            $(this).find(".xiala1").css("display", "block");
            $(this).find(".xiala2").css("display", "block");
        })
        $(".navnav").mouseleave(function () {
            $(this).find(".xiala").css("display", "none");
            $(this).find(".xiala1").css("display", "none");
            $(this).find(".xiala2").css("display", "none");
        })
    });
    //验证用户名唯一性
    function checkUserName(){
        var userName=$("#userName").val();
        var cnName = /^[a-zA-Z][a-zA-Z0-9_]{3,9}$/;
//        /^[A-Za-z]{2,6}$/
        if(cnName.test(userName)){
            $.ajax({
                url:"${pageContext.request.contextPath}/register/checkUserName.do",
                type:"post",
                data:{
                    userName:userName
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".username").text("已有该用户简称，请重新输入");
                        userName = data.length;
                        return;
                    }else{
                        $(".username").text("");
                    }
                }
            });

        }else if(userName.trim() == ""){
            $(".username").text("请输入用户名称");
        }else{
            $(".username").text("请正确填写用户名4~10位，以字母开头!");
        }
    }
    //验证商户简称唯一
    function checkUserCnName(){
        var CnName=$("#userCnName").val();
        var cnName = /^[\u4e00-\u9fa5]{2,6}$/;
        if(cnName.test(CnName.trim())){
            $.ajax({
                url:"${pageContext.request.contextPath }/register/checkUserName.do",
                type:"post",
                data:{
                    userCnName:CnName.trim()
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".userCnName").text("已有该商户简称，请重新输入");
                        return;
                    }else{
                        $(".userCnName").text("");
                    }
                }
            });
        }else if(CnName.trim() == ""){
            $(".userCnName").text("请输入商户简称");
        }else{
            $(".userCnName").text("请正确填写简称2~6汉字");
        }
    }
    //验证密码格式
    function checkPassword(){
        var password=$("#passWord").val();
        var passwordReg =  /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,10}$/;
        if(passwordReg.test(password)){
            $(".password").text("");
        }else if(password == ""){
            $(".password").text("请输入密码");
        }else{
            $(".password").text("请正确填写密码4~10位");
        }
    }
    //二次验证
    function AgainPassword() {
        var password = $("#passWord").val();
        var repeatPassword = $("#repeatPassword").val();
        var passwordRep =  /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,10}$/;
        if(repeatPassword ==""){
            $(".repeatPassword").text("请输入密码");
            return;
        }else  if(!passwordRep.test(repeatPassword)){
            $(".repeatPassword").text("请正确填写密码4~10位");
        } else  if (repeatPassword!=password){
            $(".repeatPassword").text("请输入与第一次相同的密码！");
            return;
        }   else{
            $(".repeatPassword").text("");

        }
    }
    //验证手机号
    function checkPhoneNumber(){
        var phoneNumber=$("#phoneNumber").val();
        var phoneReg =/^1[3|4|5|7|8]\d{9}$/;
        if(phoneNumber.trim() == ""){
            $(".phone").text("请输入手机号");
            $("#phoneCode").prop("disabled",true);
        }else if(!phoneReg.test(phoneNumber)){
            $(".phone").text("请正确填写手机号");
            $("#phoneCode").prop("disabled",true);
        }else{
            $.ajax({
                url:"${pageContext.request.contextPath}/register/checkUserName.do",
                type:"post",
                data:{
                    mobile:phoneNumber
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".phone").text("该手机号已经存在，请重新输入");
                        $("#phoneCode").prop("disabled",true);
                        return;
                    }else{
                        $(".phone").text("");
                        $("#phoneCode").prop("disabled",false);
                    }
                }
            });
        }
    }
   //获取验证码
    var count = 60;
    var c;
    /*倒计时*/
    var checkCodes = $("#phoneCode");
    function changeTime() {
        if (count == 0) {
            $("#phoneCode").prop("disabled",false);
            $("#phoneNumber").prop("disabled",false);
            $("#phoneCode").val("重新获取");
            count = 60;
        }
        else {
            $("#phoneCode").prop("disabled",true);
            $("#phoneCode").val("已发送("+ count + "s)");
            count--;
            setTimeout(
                function() {
                    changeTime();

                }, 1000);
        }
    }
    //获取验证码
    function verifyCode(){
        $(".yan").text("");
        var userName=$("#userName").val();
        var cnName =/^[a-zA-Z][a-zA-Z0-9_]{3,9}$/;
        if(cnName.test(userName)){
            $.ajax({
                url:"${pageContext.request.contextPath}/register/checkUserName.do",
                type:"post",
                data:{
                    userName:userName
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".username").text("已有该用户，请重新输入");
                        userName = data.length;
                        return;
                    }else{
                        $(".username").text("");
                    }
                }
            });
        }else if(userName.trim() == ""){
            $(".username").text("请输入用户名称");
        }else{
            $(".username").text("请正确填写用户名2~6英文");
        }
        checkUserCnName();
        var password = $("#passWord").val();
        var repeatPassword = $("#repeatPassword").val();
        var passwordRep =  /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,10}$/;
        if(passwordRep.test(password)){
            $(".password").text("");
        }else if(password == ""){
            $(".password").text("请输入密码");
        }else{
            $(".password").text("请正确填写密码4~10位，允许字母数字特殊字符,以字母开头!");
        }
        if(repeatPassword ==""){
            $(".repeatPassword").text("请输入密码");
            return;
        }else  if(!passwordRep.test(repeatPassword)){
            $(".repeatPassword").text("请正确填写密码4~10位，允许字母数字特殊字符,以字母开头!");
        } else  if (repeatPassword!=password){
            $(".repeatPassword").text("请输入与第一次相同的密码！");
            return;
        }  else{
            $(".repeatPassword").text("");
        }

        var Mobile=$("#phoneNumber").val();
        var reg = /^1[3|4|5|7|8]\d{9}$/;
        if (Mobile == "") {
            $(".phone").text("请输入手机号");
            return;
        } else if (!reg.test(Mobile)) {
            $(".phone").text("请正确填写手机号");
            return;
        } else {
            $.ajax({
                url:"${pageContext.request.contextPath}/register/checkUserName.do",
                type:"post",
                data:{
                    mobile:Mobile
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".phone").text("该手机号已经存在，请重新输入");
                        return;
                    }else{
                        $(".phone").text("");
                        changeTime();
                        $.ajax({
                            url:"${pageContext.request.contextPath }/admin/findCode.do",
                            type:"post",
                            data:{
                                mobile:Mobile
                            },
                            dataType:"json",
                            success:function(data){
                                c=data;
                                setTimeout(function() {
                                    $(".yan").text("验证码失效请重新获取！");
                                }, 61000);
                            }
                        });
                    }
                }
            });

        }
    }
    //注册
    $("#register").click(function(){
        var code =$("#phoneCodeText").val();
        if($("#userName").val().trim()==""){
            $(".username").text("用户名不能为空");
            return;
        }else if($("#userCnName").val().trim()==""){
                $(".userCnName").text("用户简称不能为空");
                return;
        } else if( $("#passWord").val().trim()==""  ){
            $(".password").text("密码不能为空");
            return;
        }else if($("#repeatPassword").val().trim()==""){
            $(".repeatPassword").text("重复密码不能为空");
            return;
        }else if( $("#phoneNumber").val().trim()==""){
            $(".phone").text("手机号不能为空");
            return;
        }else if( $("#phoneCodeText").val().trim()==""){
            $(".phone").text("验证码不能为空");
            return;
        }else if(parseInt(code)==parseInt(c)){
            $.ajax({
                url:"${pageContext.request.contextPath }/register/add.do",
                type:"post",
                data:$("#reg-form").serialize(),
                dataType:"json",
                success:function (data){
                    if(data){
                        alert("恭喜你注册成功！");
                        location.href="${pageContext.request.contextPath }/admin/login.do";
                    }else{
                        alert("抱歉注册失败！");
                        location.reload();
                    }
                }
            });
        }else if(parseInt(code)!=parseInt(c)){
            $(".yan").text("验证码填写错误");
        }
    });
</script>
</body>

</html>


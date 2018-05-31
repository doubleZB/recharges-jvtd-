<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User:lhm忘记密码找回密码第一步
  Date: 2016/12/29
  Time: 9:40
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
        .s_yzm {
            float: left;
            height: 50px;
            width: 190px;
            border: 1px solid #ccc;
            padding-left: 20px;
            font-size: 14px;
        }
        .one .s_hqyzm {
            float: right;
            width: 115px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            border: 1px solid #f67901;
            color: #f67901;
            cursor: pointer;
            border-radius: 5px;
            background: #fff;
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
        .msg_mobile{
            position: absolute;
            right: 0px;
            top: 150px;
        }
    </style>
</head>
<body>
<div class="container">
    <!--     nav   -->
    <div class="z_content">
        <h3>找回密码</h3>
        <form id="reg-form">
            <span class="z_error msg_mobile"></span>
            <div class="z_first"><img src="${path}/static/images/zhmm1.png" alt=""></div>
            <div class="s_box">
                <div class="one" style="margin:0">
                    <span>绑定手机号：</span>
                    <div>
                        <input type="text" name="mobile" id="mobile" onblur="checkMobles()" placeholder="请输入正确的绑定手机号">
                    </div>
                </div>
                <div class="z_yzm one">
                    <span>短信验证码：</span>
                    <input class="s_yzm" type="text" id="code" placeholder="请输入验证码">
                    <input class="s_hqyzm" type="button" id="checkCode" value="获取验证码">
                </div>
                <span class="z_error z_code"></span>
                <button type="button" class="z_btn" id="next" onclick="NextOne()"> 下一步</button>
            </div>
        </form>
    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js "></script>
<script src="${path}/static/js/headfirst.js"></script>

<script>
    $(".z_code").css("color","red");
    $(".msg_mobile").css("color","red");
    $("#checkCode").attr("disabled",true);
    //验证手机号
    function checkMobles(){
        var Mobile=$("#mobile").val();
        var mobile = /^1[3|4|5|7|8]\d{9}$/;
        if(mobile.test(Mobile)&&Mobile.length==11){
            $.ajax({
                url:"${path}/admin/checkMoble.do",
                type:"post",
                async:true,
                data:{
                    mobile:Mobile
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        userName=data[0].userName;
                        $(".msg_mobile").text("");
                        $('#next').attr('disabled',false);
                        $("#checkCode").attr("disabled",false);
                    }else{
                        $(".msg_mobile").text("输入手机号不存在，请重新输入");
                        $('#next').attr('disabled',true);
                        $("#checkCode").attr("disabled",true);
                    }
                }
            });
        }else if(Mobile == ""){
            $(".msg_mobile").text("请输入手机号");
            $('#next').attr('disabled',true);
        }else{
            $(".msg_mobile").text("请正确填写您的手机号码");
            $('#next').attr('disabled',true);
        }
    }
    var count = 60;
    var c;
    //获取验证码
    /*倒计时*/
    var checkCodes = document.getElementById("checkCode");
    function changeTime() {
        if (count == 0) {
            checkCodes.disabled = 0;
            checkCodes.value = "重新获取";
            count = 60;
        }
        else {
            checkCodes.disabled = 1;
            checkCodes.value= "已发送("+ count + "s)";
            count--;
            setTimeout(
                function() { changeTime();
                    $("#next").attr("disabled",false);
            }, 1000);
        }
    }
    checkCodes.onclick = function(){
        var Mobile=$("#mobile").val();
        var reg = /^1[3|4|5|7|8]\d{9}$/;
        if (Mobile == "") {
            alert("请输入手机号！");
            return;
        } else if (Mobile.length != 11 || !reg.test(Mobile)) {
            alert("请输入正确的手机号！");
            return;
        }else{
            changeTime();
            $.ajax({
                url:"${pageContext.request.contextPath }/admin/findCode.do",
                //async:false,
                type:"post",
                data:{
                    mobile:Mobile
                },
                dataType:"json",
                success:function(data){
                    c=data;
                    setTimeout(function() {
                        alert("验证码失效请重新获取！");
                        $("#next").attr("disabled",true);
                    }, 61000);
                }
            });
        }
    }
    //下一步
    function NextOne(){
        var Mobile=$("#mobile").val();
        var code=$("#code").val();
        if(Mobile==''){
            alert("请输入绑定手机号！");
            return;
        }if(code==''){
            alert("请输入验证码！");
            return;
        }if(code==c){
            location.href="${path}/admin/nextOne.do?userName="+userName;
        }else{
            alert("输入的验证码有误请重新输入！");
        }
    }
</script>
</body>

</html>
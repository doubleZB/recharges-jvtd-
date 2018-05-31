<%--
  Created by IntelliJ IDEA.
  User: lhm 密码管理
  Date: 2016/12/7
  Time: 9:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <link rel="stylesheet" href="${path}/static/css/z-zhanghuxinxi.css">
</head>
<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="col-lg-12">
            <div class="col-lg-12 m-box m-market" style="padding:0;">
                <div class="box-title">
                    密码管理
                </div>
                <div class="box-content">
                    <ul class="z_cz_tab">
                        <li class="active">登录密码</li>
                        <li>支付密码</li>
                        <input id="payPasswordAll" type="hidden" value="${users.payPassword}" >
                    </ul>
                    <!--    登录密码    -->
                    <div class="col-lg-12 col-md-12 col-xs-12 z_cz_tabContent" style="display:block;">
                        <form class="am-form " id="addUserForm" style="margin-top: 20px!important;">
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">原密码：</label>
                                    <input class="am-form-field col-lg-11 col-md-10 col-xs-9" id="userId" type="hidden" value="${users.id}" >
                                    <input class="am-form-field col-lg-11 col-md-10 col-xs-9" id="oldPassword" type="password"  placeholder="请输入原密码" >
                                    <span class="warn"></span>
                                </div>

                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">新密码：</label>
                                    <input class="am-form-field col-lg-11 col-md-10 col-xs-9" id="newPassword"  type="password" placeholder="请输入4~10位新密码">
                                    <span class="new_warn"></span>
                                </div>

                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">再输入密码：</label>
                                    <input class="am-form-field col-lg-11 col-md-10 col-xs-9" id="againPassword"  type="password" placeholder="请再次输入4~10位密码">
                                    <span class="again_warn"></span>
                                </div>
                        </form>
                        <div class="am-form-group">
                            <label class="col-lg-2 col-md-3 col-xs-4"></label>
                            <button class="am-btn am-btn-warning" onclick="submitPassword()" style="width: 120px;margin: auto;background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">提交</button>
                        </div>
                    </div>
                </div>
                <!--     支付密码     -->
                <div class="col-lg-12 col-md-12 col-xs-12 z_cz_tabContent" style="display:none;">
                    <!--  首次设置  -->
                    <form class="am-form" style="display:block;display: none;" id="fistShow">
                        <fieldset>
                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">设置密码：</label>
                                <input class="am-form-field col-lg-11 col-md-10 col-xs-9" id="userIds" type="hidden" value="${users.id}" >
                                <input class="am-form-field col-lg-10 col-md-9 col-xs-8" placeholder="请输入4~10位密码" name="payPassword" id="payPassword" type="password" onblur="checkPayPassword()">
                                <span class="msg_password"></span>
                            </div>

                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">确认密码：</label>
                                <input class="am-form-field col-lg-10 col-md-9 col-xs-8" placeholder="请输入4~10位密码" name="payPassword" id="againPayPassword" type="password" onblur="checkAgainPayPassword()">
                                <span class="msg_again_password"></span>
                            </div>
                            <p>
                                <label class="col-lg-2 col-md-3 col-xs-4"></label>
                                <button type="button" id="payAdd"  class="z_pFirst am-btn am-btn-default" onclick="fistPayPassword()"  style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">提交</button>
                            </p>
                        </fieldset>
                    </form>

                    <!--   重新绑定状态   -->
                    <form class="am-form" style="display:block;display: none;margin-top: 0px!important;" id="addUserPayForm" >
                        <fieldset>
                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">原密码：</label>
                                <input class="am-form-field col-lg-11 col-md-10 col-xs-9" id="userIdTwo" type="hidden" value="${users.id}" >
                                <input class="am-form-field col-lg-10 col-md-9 col-xs-8" placeholder="请输入原密码" name="payPassword" id="oldPayPassword" type="password" >
                                <span class="z_warn"></span>
                            </div>

                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">新密码：</label>
                                <input class="am-form-field col-lg-10 col-md-9 col-xs-8" placeholder="请输入4~10位新密码" name="payPassword" id="newPayPassword" type="password">
                                <span class="z_warn"></span>
                            </div>

                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">再输入密码：</label>
                                <input class="am-form-field col-lg-10 col-md-9 col-xs-8" name="payPassword" placeholder="请再次输入4~10位密码" id="twoPayPassword" type="password">
                                <span class="z_warn"></span>
                            </div>
                            <p>
                                <label class="col-lg-2 col-md-3 col-xs-4"></label>
                                <button type="button" onclick="twoPayPasswords()" class="z_pAgain am-btn am-btn-default"  style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">提交</button>
                            </p>
                        </fieldset>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<!--[if lte IE 8 ]>
<![endif]-->
<script src="${path}/static/js/z-mimaguanli.js"></script>
<script>
    $(".msg_password").css("color","red");
    $(".msg_again_password").css("color","red");

    var payPasswordAll = $("#payPasswordAll").val();
    if(payPasswordAll==''|| payPasswordAll==null){
        $("#fistShow").show();
        $("#addUserPayForm").hide();
    }else{
        $("#addUserPayForm").show();
        $("#fistShow").hide();

    }

    //修改密码
    function submitPassword(){
        var userId=$("#userId").val();
        var oldPassword =$("#oldPassword").val();
        var newPassword =$("#newPassword").val();
        var againPassword =$("#againPassword").val();
        if(oldPassword==''){
            layer.tips("请输入原始密码！","#oldPassword",1);
            return;
        }
        if(newPassword==''){
            layer.tips("请输入新密码！","#newPassword",1);
            return;
        }
        if(newPassword.length<4){
            layer.tips("新密码位数不够！","#newPassword",1);
            return;
        }
        if(newPassword.length>10){
            layer.tips("新密码位数太长！","#newPassword",1);
            return;
        }
        if(newPassword==''){
            layer.tips("请再次输入新密码！","#newPassword",1);
            return;
        }
        if(newPassword!=againPassword){
            layer.tips("两次新密码输入不一致！","#againPassword",1);
            return;
        }
        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "/admin/updatePassword",
            type:'POST',
            async:false,
            data:{
                "id":userId,
                "oldPassword":oldPassword,
                "newPassword":newPassword,
                "againPassword":againPassword
            },
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                layer.close(index);
                if(data.success){
                    document.getElementById("addUserForm").reset();
                    layer.msg(data.message, {icon: 1});
                    setTimeout(function(){
                        window.parent.location.href = "/admin/LoginOut.do";
                    },3000)
                }else{
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }

    //验证支付密码
    function checkPayPassword(){
        var payPassword=$("#payPassword").val();
        var password = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,10}$/;
        if(password.test(payPassword)){
            $(".msg_password").text("");
            $('#payAdd').attr('disabled',false);
        }else if(payPassword == ""){
            $(".msg_password").text("请输入密码");
            $('#payAdd').attr('disabled',true);
        }else{
            $(".msg_password").text("请正确填写密码4~10位，允许字母数字特殊字符");
            $('#payAdd').attr('disabled',true);
        }
    }
    //验证支付确认密码
    function checkAgainPayPassword(){
        var againPayPassword=$("#againPayPassword").val();
        var password = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,10}$/;
        if(password.test(againPayPassword)){
            $(".msg_again_password").text("");
            $('#payAdd').attr('disabled',false);
        }else if(againPayPassword == ""){
            $(".msg_again_password").text("请输入密码");
            $('#payAdd').attr('disabled',true);
        }else{
            $(".msg_again_password").text("请正确填写密码4~10位，允许字母数字特殊字符");
            $('#payAdd').attr('disabled',true);
        }
    }

    //首次设置支付密码
    function fistPayPassword(){
        var userId=$("#userIds").val();
        var payPassword = $("#payPassword").val();
        var againPayPassword = $("#againPayPassword").val();
        if(payPassword==''){
            alert("首次设置密码不能为空!");
            return;
        }else if(againPayPassword==''){
            alert("确认密码不能为空!");
            return;
        }else{
            if(payPassword==againPayPassword){
                var index = layer.load(0, {shade: [0.3,'#000']});
                $.ajax({
                    url: "/admin/updatePayPassword",
                    type:'POST',
                    async:false,
                    data:{
                        "id":userId,
                        "againPayPassword":againPayPassword
                    },
                    dataType:"json",
                    error:function(){
                        layer.close(index);
                    },
                    success: function(data){
                        layer.close(index);
                        if(data){
                            alert("恭喜你支付密码设置成功！");
                            location.reload();
                        }else{
                            alert("抱歉支付密码设置失败！");
                        }
                    }
                });
            }else{
                alert("两次密码不一致请重新输入!");
                return;
            }
        }
    }

    //再次设置支付密码
    function twoPayPasswords(){
        var userId=$("#userIdTwo").val();
        var oldPayPassword =$("#oldPayPassword").val();
        var newPayPassword =$("#newPayPassword").val();
        var twoPayPassword =$("#twoPayPassword").val();
        if(oldPayPassword==''){
            layer.tips("请输入原始密码！","#oldPayPassword",1);
            return;
        }
        if(newPayPassword==''){
            layer.tips("请输入新密码！","#newPayPassword",1);
            return;
        }
        if(newPayPassword.length<4){
            layer.tips("新密码位数不够！","#newPayPassword",1);
            return;
        }
        if(newPayPassword.length>10){
            layer.tips("新密码位数太长！","#newPayPassword",1);
            return;
        }
        if(twoPayPassword==''){
            layer.tips("请再次输入新密码！","#twoPayPassword",1);
            return;
        }
        if(newPayPassword!=twoPayPassword){
            layer.tips("两次新密码输入不一致！","#twoPayPassword",1);
            return;
        }
        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "/admin/updateTwoPassword",
            type:'POST',
            async:false,
            data:{
                "id":userId,
                "oldPayPassword":oldPayPassword,
                "newPayPassword":newPayPassword,
                "twoPayPassword":twoPayPassword
            },
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                layer.close(index);
                if(data.success){
                    document.getElementById("addUserPayForm").reset();
                    layer.msg(data.message, {icon: 1});
                }else{
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }
</script>
</body>
</html>

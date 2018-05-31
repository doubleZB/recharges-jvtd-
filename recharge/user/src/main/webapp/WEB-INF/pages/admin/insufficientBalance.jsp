<%--
  Created by IntelliJ IDEA.
  User: lhm 余额不足提醒
  Date: 2016/12/27
  Time: 15:04
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
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <![endif]-->

    <style>
        .am-form .am-form-group .am-form-field {
            font-size: 12px;
        }

        .z_remark p {
            text-indent: 210px;
        }
        .z_beizhu {
            width:90%;
            border: 1px solid #ccc;
            background: #eee;
            padding: 10px 20px;
            color: #a8a8a8;
            margin-left:20px;
            margin-top: 20px;
        }
    </style>
</head>

<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="col-lg-12">
            <div class="col-lg-12 m-box m-market" style="padding:0;">
                <div class="box-title">
                    余额不足提醒
                    <table style="display: none;">
                        <tr>
                            <td>id</td>
                            <td>状态</td>
                            <td>用户ID</td>
                            <td>手机号</td>
                            <td>报警阀值</td>
                        </tr>
                        <c:forEach var="m" items="${listTwo}">
                            <tr>
                                <td id="monitorId">${m.id}</td>
                                <td id="status">${m.status}</td>
                                <td id="monitorMobiles">${m.monitorMobile}</td>
                                <td id="monitorBalances">${m.monitorBalance}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <table style="display: none;">
                        <c:forEach var="b" items="${list}">
                            <tr>
                                <td id="userBalance">${b.userBalance}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="z_beizhu">
                    为保障线上业务正常运营，建议您设置余额提醒额度。
                </div>
                <form class="am-form " id="form">
                    <fieldset>
                        <div class="am-form-group">
                            <input class="am-form-field col-lg-10 col-md-9 col-xs-8" type="hidden" name="userId" id="userId" value="${users.id}">
                            <label class="col-lg-2 col-md-3 col-xs-4">余额提醒开关：</label>
                            <select id="doc-select-0" class="am-form-field col-lg-10 col-md-9 col-xs-8" name="status" >
                                <option value="2">关闭</option>
                                <option value="1">开启</option>
                            </select>
                        </div>
                        <div style="display: none" id="show">
                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">余额低于多少时提醒：</label>
                                <input class="am-form-field col-lg-10 col-md-9 col-xs-8" type="text" name="monitorBalance" id="monitorBalance" onblur="checkMonitorBalance();">
                                <label class="">&nbsp;&nbsp;元</label>
                                <span class="msg_balance"></span>
                            </div>

                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">短信接收手机号：</label>
                                <input class="am-form-field col-lg-10 col-md-9 col-xs-8"  onkeyup="JavaScript:this.value=this.value.replace(/，/ig,',');" type="text" name="monitorMobile" id="monitorMobile" onblur="checkMonitorMobile();"placeholder="多个手机号用“,”隔开,最多允许填写3个手机号">
                                <span class="msg_mobile"></span>
                            </div>
                        </div>
                        <p>
                            <label class="col-lg-2 col-md-3 col-xs-4"></label>
                            <button type="button" id="submitBalance" class="am-btn am-btn-default" onclick="submitBalances();" style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">保存</button>
                        </p>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>


<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<!--[if lte IE 8 ]>
<![endif]-->

</body>
<script>


    var status = $("#status").text();
    var monitorMobiles = $("#monitorMobiles").text();
    var monitorBalances = $("#monitorBalances").text();
    if(status==2){
        $("#show").hide();
    }else if (status==1){
        $("#doc-select-0").val(1);
        $("#monitorBalance").val(monitorBalances);
        $("#monitorMobile").val(monitorMobiles);
        $("#show").show();
    }


    $(".msg_balance").css("color","red");
    $(".msg_mobile").css("color","red");
    $(function(){
        $("#doc-select-0").change(function(){
            var text = $("#doc-select-0").val();
            console.log(text);
            if(text == 2){
                $("#show").hide();
            }else{
                $("#show").show();
            }
        });
    });
    //余额不足1000提醒
    function checkMonitorBalance(){
        var val = $("#monitorBalance").val();
        var monitorBalance = /^\d+$/;
        if(monitorBalance.test(val) && val<1000){
            $(".msg_balance").text("1000元以下太低，请重新考虑提醒额度");
            $('#submitBalance').attr('disabled',true);
        }else if($("#monitorBalance").val()==""){
            $(".msg_balance").text("提醒额度不能为空！");
            $('#submitBalance').prop('disabled',true);
        }else{
            $(".msg_balance").text("");
            $('#submitBalance').attr('disabled',false);
        }
    }


    var b;
    //验证手机号
    function checkMonitorMobile(){
        var Mobile=$("#monitorMobile").val();
        var mobile = /^[1][3578][0-9]{9}$/;
        if(mobile.test(Mobile)){
            $(".msg_mobile").text("");
            $('#submitBalance').attr('disabled',false);
        }else if(Mobile.length<1){
            $(".msg_mobile").text("请输入正确手机号!");
            $('#submitBalance').attr('disabled',true);
        }else{
           b= Mobile.split(",");
            if(b.length==2 && mobile.test(b[1])){
                if(b[0] == b[1]){
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled',true);
                }else{
                    $(".msg_mobile").text("");
                    $('#submitBalance').attr('disabled',false);
                }
            }else if(b.length==3 && mobile.test(b[2])){
                if(b[0] == b[2]){
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled',true);
                }else if(b[0] == b[1]){
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled',true);
                }else if(b[1] == b[2]){
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled',true);
                }else{
                    $(".msg_mobile").text("");
                    $('#submitBalance').attr('disabled',false);
                }
            }else if(b.length==4 && mobile.test(b[3])){
                $(".msg_mobile").text("最多允许填写3个手机号,请正确输入！");
                $('#submitBalance').attr('disabled',true);
            }else{
                $(".msg_mobile").text("请输入正确手机号,多个手机号用“,”分割");
                $('#submitBalance').attr('disabled',true);
            }
        }
    }
    //保存提醒
    function submitBalances(){
        var balance = /^\d+$/;
        var monitorId=$("#monitorId").text();
        var status= $("#doc-select-0").val();
        var userId=  $("#userId").val();
        var userBalance=$("#userBalance").text();
        var monitorBalance = $("#monitorBalance").val();
        var monitorMobile = $("#monitorMobile").val();
            if(status==2){
                $.ajax({
                    url: "/admin/userBalanceMonitor",
                    type:'POST',
                    async:false,
                    data:{
                        "id":monitorId,
                        "userId":userId,
                        "status":status
                    },
                    dataType:"json",
                    error:function(){
                        layer.close(index);
                    },
                    success: function(data){
                        layer.close(index);
                        if(data){
                            alert("恭喜你提交成功！");
                            location.reload();
                        }else{
                            alert("抱歉提交失败！");
                        }
                    }
                });
            }else{
                if(monitorBalance==''){
                    alert("阈值提醒额度不能为空!");
                    return;
                }
                if(balance.test(monitorBalance) && monitorBalance<1000){
                    alert("请正确填写您的提醒额度!");
                    return;
                }
                if(monitorMobile==''){
                    alert("短信接收手机号不能为空!");
                    return;
                }else{
                    var index = layer.load(0, {shade: [0.3,'#000']});
                    $.ajax({
                        url: "/admin/userBalanceMonitor",
                        type:'POST',
                        async:false,
                        data:{
                            "id":monitorId,
                            "userId":userId,
                            "status":status,
                            "monitorBalance":monitorBalance,
                            "monitorMobile":monitorMobile
                        },
                        dataType:"json",
                        error:function(){
                            layer.close(index);
                        },
                        success: function(data){
                            layer.close(index);
                            if(data){
                                alert("恭喜你提交成功！");
                                location.reload();
                            }else{
                                alert("抱歉提交失败！");
                            }
                        }
                    });
                }
    }
    }
</script>
</html>


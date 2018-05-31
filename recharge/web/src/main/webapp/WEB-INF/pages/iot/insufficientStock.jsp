<%--
  Created by IntelliJ IDEA.
  User: lhm 库存不足报警
  Date: 2016/12/27
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <%--<link rel="stylesheet" href="${path}/static/css/z-zhanghuxinxi.css">--%>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <![endif]-->

    <style>
        .am-form-label {
            font-weight: normal;
            width: 120px;
            padding-left: 0;
        }
        .am-form .am-form-group .am-form-field {
            font-size: 12px;
        }

        .z_remark p {
            text-indent: 210px;
        }

        .z_beizhu {
            width: 90%;
            border: 1px solid #ccc;
            background: #eee;
            padding: 10px 20px;
            color: #a8a8a8;
            margin-left: 20px;
            margin-top: 20px;
        }
    </style>
</head>

<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="col-lg-12">
            <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">库存预警设置</strong>
                </div>
            </div>
            <div class="col-lg-12 m-box m-market" style="padding:0;">
                <div class="z_beizhu" style="margin-bottom: 20px;">
                    为保障线上业务正常运营，建议您设置库存提醒阈值。
                </div>
                <form class="am-form " id="form">
                    <%--<fieldset>--%>
                    <div class="am-form-group">
                        <%--<input class="am-form-field col-lg-10 col-md-9 col-xs-8" type="hidden" name="userId" id="userId" value="${users.id}">--%>
                        <label class="am-form-label" style="width: 180px;text-align: right;float: left">库存提醒开关：</label>
                        <select id="open" class="am-form-field col-lg-10 col-md-9 col-xs-8" name="status"
                                style="width: 200px">
                            <option value=2>关闭</option>
                            <option value=1>开启</option>
                        </select>
                    </div>
                    <div id="show">
                        <div class="am-form-group">
                            <label class="am-form-label"
                                   style="width: 180px;text-align: right;float: left">库存低于多少时提醒：</label>
                            <input class="am-form-field col-lg-10 col-md-9 col-xs-8" style="width: 200px" type="hidden"
                                   name="stock" id="id">
                            <%--<input class="am-form-field col-lg-10 col-md-9 col-xs-8" style="width: 200px"  type="text" name="stock" id="stock" onblur="checkMonitorBalance();">--%>
                            <input class="am-form-field col-lg-10 col-md-9 col-xs-8" style="width: 200px" type="text"
                                   name="stock" id="stock">
                            <%--<label class="">&nbsp;&nbsp;元</label>--%>
                            <span class="msg_balance"></span>
                        </div>

                        <div class="am-form-group">
                            <label class="am-form-label"
                                   style="width: 180px;text-align: right;float: left">短信接收手机号：</label>
                            <input class="am-form-field col-lg-10 col-md-9 col-xs-8" style="width: 300px;"
                                   onkeyup="JavaScript:this.value=this.value.replace(/，/ig,',');" type="text"
                                   name="monitorMobile" id="monitorMobile" onblur="checkMonitorMobile();"
                                   placeholder="多个手机号用“,”隔开,最多允许填写3个手机号">
                            <span class="msg_mobile"></span>
                        </div>
                    </div>
                    <p>
                        <button type="button" id="submitBalance" class="am-btn am-btn-default"
                                onclick="submitBalances();"
                                style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;width: 120px;    margin-left: 175px;">
                            保存
                        </button>
                    </p>
                    <%--</fieldset>--%>
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
    $(function () {
        reloadPage();

    })
    function reloadPage(){
        $.ajax({
            url: "${pageContext.request.contextPath}/insufficient/list",
            type: 'POST',
            async: false,
            data: {},
            dataType: "json",
            error: function () {
                $(this).addClass("done");
            },
            success: function (data) {
//                debugger
                var obj = JSON.parse(data.configData);
                console.log(obj.open);
                if (obj.open=="true") {
                    $("#open").val(1);
                } else {
                    $("#open").val(2);
                }
                $("#stock").val(obj.threshold);
                $("#monitorMobile").val(obj.mobiles);
                $("#id").val(data.id);
            }
        })
    }

    function checkMonitorBalance() {
        var val = $("#stock").val();
        var stock = /^\d+$/;
        if (stock.test(val) && val < 1000) {
            $(".msg_balance").text("1000元以下太低，请重新考虑提醒额度");
            $('#submitBalance').attr('disabled', true);
        } else if ($("#stock").val() == "") {
            $(".msg_balance").text("提醒额度不能为空！");
            $('#submitBalance').prop('disabled', true);
        } else {
            $(".msg_balance").text("");
            $('#submitBalance').attr('disabled', false);
        }
    }


    var b;
    //验证手机号
    function checkMonitorMobile() {
        var Mobile = $("#monitorMobile").val();
        var mobile = /^[1][3578][0-9]{9}$/;
        if (mobile.test(Mobile)) {
            $(".msg_mobile").text("");
            $('#submitBalance').attr('disabled', false);
        } else if (Mobile.length < 1) {
            $(".msg_mobile").text("请输入正确手机号!");
            $('#submitBalance').attr('disabled', true);
        } else {
            b = Mobile.split(",");
            if (b.length == 2 && mobile.test(b[1])) {
                if (b[0] == b[1]) {
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled', true);
                } else {
                    $(".msg_mobile").text("");
                    $('#submitBalance').attr('disabled', false);
                }
            } else if (b.length == 3 && mobile.test(b[2])) {
                if (b[0] == b[2]) {
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled', true);
                } else if (b[0] == b[1]) {
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled', true);
                } else if (b[1] == b[2]) {
                    $(".msg_mobile").text("手机号码不能重复,请输入正确手机号!");
                    $('#submitBalance').attr('disabled', true);
                } else {
                    $(".msg_mobile").text("");
                    $('#submitBalance').attr('disabled', false);
                }
            } else if (b.length == 4 && mobile.test(b[3])) {
                $(".msg_mobile").text("最多允许填写3个手机号,请正确输入！");
                $('#submitBalance').attr('disabled', true);
            } else {
                $(".msg_mobile").text("请输入正确手机号,多个手机号用“,”分割");
                $('#submitBalance').attr('disabled', true);
            }
        }
    }
    //保存提醒
    function submitBalances() {
        var re = /^[0-9]+$/;
        var status = $("#open").val();
        var stock = $("#stock").val();
        var monitorMobile = $("#monitorMobile").val();
        var id = $("#id").val();
        if (status == 2) {
            status = "false";
        }
        if (status == 1) {
            status = "true";
        }
        if (stock == '') {
            layer.tips("阈值提醒库存不能为空!", "#stock", 1);
            return;
        } else if (!re.test(stock)) {
            layer.tips("请输入正确的阈值", "#stock", 1);
            return;
        }
//            if (balance.test(stock) && stock < 1000) {
//                alert("请正确填写您的提醒额度!");
//                return;
//            }
        if (monitorMobile == '') {
            layer.tips("短信接收手机号不能为空!", "#stock", 1);
            return;
        } else {
            var obj = {
                open: status,
                threshold: stock,
                mobiles: monitorMobile
            }
            var jsonObj = JSON.stringify(obj);
//                var json = JSON.stringify(obj);
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/insufficient/addStockAlarm",
                type: 'POST',
                async: false,
                data: {
                    id: id,
                    name: "stock_alarm",
                    configData: jsonObj
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message,{icon: 1})
                        reloadPage();
                    } else {
                        layer.msg(data.message,{icon: 2})
                    }
                }
            });
        }
//            }else{
//                if(stock==''){
//                    alert("阈值提醒库存不能为空!");
//                    return;
//                }
//                if(balance.test(stock) && stock<1000){
//                    alert("请正确填写您的提醒额度!");
//                    return;
//                }
//                if(monitorMobile==''){
//                    alert("短信接收手机号不能为空!");
//                    return;
//                }else{
//                    var index = layer.load(0, {shade: [0.3,'#000']});
//                    $.ajax({
//                        url: "/admin/userBalanceMonitor",
//                        type:'POST',
//                        async:false,
//                        data:{
//                            "id":monitorId,
//                            "userId":userId,
//                            "status":status,
//                            "stock":stock,
//                            "monitorMobile":monitorMobile
//                        },
//                        dataType:"json",
//                        error:function(){
//                            layer.close(index);
//                        },
//                        success: function(data){
//                            layer.close(index);
//                            if(data){
//                                alert("恭喜你提交成功！");
//                                location.reload();
//                            }else{
//                                alert("抱歉提交失败！");
//                            }
//                        }
//                    });
//                }
//        }
    }
</script>
</html>


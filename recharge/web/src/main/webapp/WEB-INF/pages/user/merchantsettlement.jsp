<%--
  Created by IntelliJ IDEA.
  User: 商户结算管理lihuimin
  Date: 2016/11/12
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <style>
        *{
            -moz-user-select: text !important;
            -webkit-user-select: text !important;
            -ms-user-select: text !important;
            user-select: text !important;
        }
        .am-u-sm-2{
            width: 140px;
        }
        form {
            margin-top: 20px;
        }
        #content{
            margin-top: 20px;
        }
        .am-btn{
            border-radius: 5px;
        }
        input,select{
            color:#848181;
        }
        .am-table>caption+thead>tr:first-child>td, .am-table>caption+thead>tr:first-child>th, .am- table>colgroup+thead>tr:first-child>td, .am-table>colgroup+thead>tr:first-child>th, .am- table>thead:first-child>tr:first-child>td, .am-table>thead:first-child>tr:first-child>th {
            border-top: 0;
            font-size: 1.4rem;
        }
        .am-table>tbody>tr>td, .am-table>tbody>tr>th, .am-table>tfoot>tr>td, .am-table>tfoot>tr>th, .am- table>thead>tr>td, .am-table>thead>tr>th {
            padding: .7rem;
            line-height: 1.6;
            vertical-align: top;
            border-top: 1px solid #ddd;
            font-size: 1.4rem;
        }
    </style>
</head>
<body>
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body">
        <div class="am-cf am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg" style="font-size: 14px;">商户结算信息配置</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel" id="tab2">
            <form class="am-form am-form-horizontal" id="empr">
                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">商户简称：</label>
                    <div style="width: 300px;float: left;">
                        <input type="hidden" name="id" id="userid">
                        <input type="hidden" name="userAllName" id="operate" value="${adminLoginUser.adminName}">
                        <input type="hidden" name="userName" id="userName">
                        <input type="text" name="userCnName" id="user_cn_name" onblur="checkAccount()">
                    </div>
                </div>
                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">账户名：</label>
                    <div style="width: 300px;float: left;margin-top: .6em;">
                        <p type="text" name="userName" id="user_name"></p>
                    </div>
                </div>
                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">是否允许授信：</label>
                    <div style="width: 300px;float: left;">
                        <p style="display:inline-block;margin:9px 30px 6px 0;">
                            <input type="hidden" name="userBalance" id="userbalance">
                            <input type="radio" name="isCredit" placeholder="" style="margin-right: 5px;" value="1">允许
                        </p>
                        <p style="display:inline-block;margin:9px 30px 6px 0;">
                            <input type="radio" name="isCredit" checked="checked" placeholder="" style="margin-right: 5px;" value="0">不允许
                        </p>
                    </div>
                </div>
                <div class="am-form-group sqed" style="display: none">
                    <label class="am-u-sm-2 am-form-label">授信额度：</label>
                    <div style="width: 300px;float: left;">
                        <input type="text" name="creditBalance" onblur="credit()" id="credit_balance" placeholder="不允许小数，最大数值不超过100万" style="width: 80%;display: inline-block;margin-right:6%;">元
                        </br>
                        <span  id="msg_credit"></span>
                    </div>
                </div>
            </form>
            <div class="am-form-group">
                <button class="am-btn am-btn-warning" id="empower" style="width: 120px;margin: auto;margin-left: 134px;" onclick="empower()">提交</button>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

</body>
<script>
    var vm = new Vue({
        el: "#content",
        data: {
            hd: "",
            yys: "",
            sf: "",
            json: [],
        }
    })

</script>
<script>
    $("#msg_credit").css("color","red");
    // 授权额度
    function credit(){
        var userbalance=$("#userbalance").val();
        var val = $("#credit_balance").val();
        var credit_balance = /^\d+$/;
        if(parseFloat(userbalance.trim())<0){
            if(credit_balance.test(val) && val>= Math.abs(userbalance.trim())  && val<=1000000){
                $("#msg_credit").text("");
                $('#empower').prop('disabled',false);
            }else if($("#credit_balance").val()==""){
                $("#msg_credit").text("授信额度不能为空！");
                $('#empower').prop('disabled',true);
                return;
            }else{
                $("#msg_credit").text("请正确填写授信额度！授信额度不小于透支额度！");
                $('#empower').prop('disabled',true);
                return;
            }
        }else{
            if(credit_balance.test(val.trim()) && val.trim()<=1000000){
                $("#msg_credit").text("");
                $('#empower').prop('disabled',false);
            }else if($("#credit_balance").val().trim()==""){
                $("#msg_credit").text("授信额度不能为空！");
                $('#empower').prop('disabled',true);
            }else{
                $("#msg_credit").text("授信额度不超过100万且不能是负数，小数！");
                $('#empower').prop('disabled',true);
            }
        }
    };

    //信息配置
    function checkAccount(){
        var user_cn_name=$("#user_cn_name").val();
        $.ajax({
            url: "${pageContext.request.contextPath}/user/checkeBalance.do",
            type:'POST',
            data:{
                userCnName:user_cn_name.trim()
            },
            dataType:"json",
            success: function(data){
                if( data.length == 0 ){
                    $("#user_name").text("该账号不存在！请核对后重新输入");
                    $('#empower').prop('disabled',true);
                    $("#user_name").css("color","red");
                }else if($("#user_cn_name").val() == ""){
                    $("#user_name").text(" ");
                    $('#empower').prop('disabled',false);
                }else{
                    $("#userid").val(data[0].id);
                    $("#userName").val(data[0].userName);
                    $("#user_name").text(data[0].userName);
                    $("#userbalance").val(data[0].userBalance);
                    $("#user_name").css("color","#000000");
                    $('#empower').prop('disabled',false);
                }
            }
        })
    }
    $(function() {
        $(":radio").click(function() {
            var val = $(this).val();
            var user_cn_name=$("#user_cn_name").val();
            if (val == 1) {
                $(".sqed").show();
                $('#empower').prop('disabled',true);
            } else {
                $(".sqed").hide();
                $('#empower').prop('disabled',false);
            }
        })
    })
    function empower(){
        if($("#user_cn_name").val().trim()==""){
            alert("请输入商户简称！")
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/user/empowerlist.do",
                type:"post",
                data:$("#empr").serialize(),
                dataType:"json",
                success:function (data){
                    layer.close(loadingIndex);
                    if(data){
                        alert("恭喜你配置成功");
                        location.reload();
                    }else{
                        alert("抱歉配置失败");
                        location.reload();
                    }
                }
            });
        }
    }
</script>
</html>

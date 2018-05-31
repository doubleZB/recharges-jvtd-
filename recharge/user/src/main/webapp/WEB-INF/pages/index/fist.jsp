<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.FileNotFoundException" %><%--
<%--
  Created by IntelliJ IDEA.
  User:客户资料
  Date: 2016/11/12
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String costChannelId = "";
    String flowChannelId = "";

    Properties pro = new Properties();
    String realpath = request.getRealPath("/WEB-INF/classes");
    try{
        //读取配置文件
        FileInputStream in = new FileInputStream(realpath+"/config.properties");
        pro.load(in);
    }
    catch(FileNotFoundException e){
        out.println(e);
    }
    catch(IOException e){out.println(e);}

//通过key获取配置文件
    flowChannelId = pro.getProperty("flowChannelId");
    costChannelId = pro.getProperty("costChannelId");
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <![endif]-->
    <style>
        .z_applic{
            height:380px;
        }
        .z_appContent{
            height:260px;
        }

        .z_info1 {
            background: #fff;
            padding-bottom: 10px;
        }
        .text{
            overflow: hidden;
            padding: 25px;
        }
        .p1{
            float: left;
            margin-right: 20px;
            line-height: 40px;
        }
        .p1 span{
            color: red;
        }
        .butt{
            float: left;
            margin: 0 auto;
            display: block;
            background: rgba(255, 186, 0, 1)!important;
            border-color: #ffba00!important;
            border: 0;
            color: #fff;
            font-size: 14px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            padding: 0 30px;
            border-radius:5px ;
        }
    </style>
</head>
<body>

<div class="container" style="padding:23px;">
    <!--    上半部分   -->
    <div class="row">
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-xs-6 padding-left">
                    <div class="z_info clearfix" style="height: 30%;">
                        <h3 class="z_info_tit">客户资料</h3>
                        <div class="col-lg-12" style="margin-top:10px;height:109px;line-height: 58px;">
                            <div class="col-lg-3 col-md-3 col-xs-3" style="text-align:center;">
                                <a href="javascript:" class="z_photo" onclick="">
                                    <img src="${path}/static/images/dog.png" alt="">
                                </a>
                            </div>
                            <div class="col-lg-8 col-md-8 col-xs-8">
                                <p class="z_name" style="color: #4c4c4c;">${users.userCnName}
                                    <c:forEach items="${listTwo}" var="auth">
                                        <span style="font-size: 12px;font-weight: 100;color: #fff;background:red;padding:5px;border-radius:5px ;margin-left:20px;display: none;margin-bottom: 2px;" id="statusOK">${auth.authState==3?"已认证":""}</span>
                                    </c:forEach>
                                </p>
                                <a href="<%=request.getContextPath()%>/admin/userMessage.do">管理个人信息</a>
                                <span id="goTo">
                                    <a style="margin-left:20px;" href="<%=request.getContextPath()%>/admin/userMessage.do">去认证</a>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-xs-6 padding-right">
                    <div class="z_info clearfix" style="height: 30%;">
                        <h3 class="z_info_tit">账户余额</h3>
                        <div class="col-lg-12" style="margin-top:13px;">
                            <table class="col-lg-12 col-md-12 col-xs-12"  style="font-weight: 100;text-indent:38px;">
                                <c:forEach items="${list}" var="b">
                                    <tr style="font-size: 14px;font-weight: 100;height: 35px;">
                                        <td>账户余额（元）：<span style="font-size: 16px;color: red;">${b.userBalance}</span></td>
                                    </tr>
                                    <tr style="font-size: 12px;font-weight: 100;height: 36px;">
                                        <td>授信（元）：<span style="color: red;">${b.creditBalance}</span></td>
                                    </tr>
                                    <tr style="font-size: 12px;font-weight: 100;height: 35px;">
                                        <td>借款（元）：<span  style="color: red;">${b.borrowBalance}</span></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--    下半部分    -->
    <div class="row" style="margin:20px 0;">
        <div class="col-lg-12">
            <div class="row z_info" style="height: 65%;clear: both;">
                <h3 class="z_info_tit">我的应用</h3>
                <div class="row">
                    <div class="col-lg-12 col-margin-bottom-box col-margin-top-box clearfix">
                        <div class="col-lg-3 col-md-4 col-xs-6 appHover one">
                            <a href="javascript:void(0)" class="activate">
                                <div class="z_applic clearfix z_hover">
                                    <div class="clearfix z_mine_title" >流量</div>
                                    <div class="z_appContent">
                                        <p >昨日交易额</p>
                                        <p class="z_orange" id="flowYesterdayMoney">0</p>
                                        <p>昨日订单数</p>
                                        <p class="z_orange"id="flowYesterdayOrder">0</p>
                                        <p>昨日成功率</p>
                                        <p class="z_orange"id="flowYesterdayRate">0</p>

                                    </div>
                                    <button class="btn btn-primary z_checkBtn kt1"  style="display: none" onclick="dredgeApply(1)">
                                        开通应用
                                    </button>
                                    <button class="btn btn-primary z_checkBtn ck1"  style="display: none" onclick="skip(1)">
                                        查看详情
                                    </button>
                                </div>
                            </a>
                        </div>
                        <div class="col-lg-3 col-md-4 col-xs-6 appHover two">
                            <a href="javascript:void(0)" class="activate">
                                <div class="z_applic clearfix z_hover">
                                    <div class="clearfix z_mine_title">话费</div>
                                    <div class="z_appContent">
                                        <p>昨日交易额</p>
                                        <p class="z_orange" id="tellYesterdayMoney">0</p>
                                        <p>昨日订单数</p>
                                        <p class="z_orange" id="tellYesterdayOrder">0</p>
                                        <p>昨日成功率</p>
                                        <p class="z_orange" id="tellYesterdayRate">0</p>
                                    </div>
                                    <button class="btn btn-primary z_checkBtn kt2" style="display: none" onclick="dredgeApply(2)">
                                        开通应用
                                    </button>
                                    <button class="btn btn-primary z_checkBtn ck2"  style="display: none" onclick="skip(2)">
                                        查看详情
                                    </button>
                                </div>
                            </a>
                        </div>
                        <div class="col-lg-3 col-md-4 col-xs-6 appHover three">
                            <a href="javascript:void(0)" class="activate">
                                <div class="z_applic clearfix z_hover">
                                    <div class="clearfix z_mine_title">视频会员</div>
                                    <div class="z_appContent">
                                        <p>昨日交易额</p>
                                        <p class="z_orange" id="videoYesterdayMoney">0</p>
                                        <p>昨日订单数</p>
                                        <p class="z_orange" id="videoYesterdayOrder">0</p>
                                        <p>昨日成功率</p>
                                        <p class="z_orange" id="videoYesterdayRate">0</p>
                                    </div>
                                    <button class="btn btn-primary z_checkBtn kt3" style="display: none" onclick="dredgeApply(6)">
                                        开通应用
                                    </button>
                                    <button class="btn btn-primary z_checkBtn ck3"  style="display: none" onclick="skip(6)">
                                        查看详情
                                    </button>
                                </div>
                            </a>
                        </div>
                        <div class="col-lg-3 col-md-4 col-xs-6 appHover three" id="card">
                            <a href="javascript:void(0)" class="activate">
                                <div class="z_applic clearfix z_hover">
                                    <div class="clearfix z_mine_title">物联网卡</div>
                                    <%--<div class="z_appContent">--%>
                                        <%--<p>昨日交易额</p>--%>
                                        <%--<p class="z_orange" id="cardYesterdayMoney">0</p>--%>
                                        <%--<p>昨日订单数</p>--%>
                                        <%--<p class="z_orange" id="cardYesterdayOrder">0</p>--%>
                                        <%--<p>昨日成功率</p>--%>
                                        <%--<p class="z_orange" id="cardYesterdayRate">0</p>--%>
                                    <%--</div>--%>
                                    <button class="btn btn-primary z_checkBtn kt4" style="display: none;margin-top: 260px" onclick="dredgeApply(7)">
                                        开通应用
                                    </button>
                                    <button class="btn btn-primary z_checkBtn ck4"  style="display: none;margin-top: 260px" onclick="skip(7)">
                                        查看详情
                                    </button>
                                </div>
                            </a>
                        </div>

                    </div>
                    <%--<p class="z_reminder">该板块将提供各应用关键统计数据，敬请期待！</p>--%>
                </div>
            </div>
        </div>
    </div>

    <!--    下半部分    -->
    <div class="row" style="margin:20px 0;">
        <div class="col-lg-12">
            <div class="row z_info1" style="">
                <h3 class="z_info_tit">我的消费</h3>
                <div class="text">
                    <p  class="p1">昨日消费总额：<span id="count"></span>元</p><button class="butt" onclick="skip(3)" style="background: #F37B1D!important;">查看账单</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>

    //根据用户ID去查询相对应的应用
    $.ajax({
        url: "${pageContext.request.contextPath}/index/selectUserAppByUserId",
        type:'POST',
        data:{},
        dataType:"json",
        success: function(data){
//            console.log(data)
            var arr=[];
            for(var i=0;i<data.length;i++){
                arr.push(data[i].appType)
            };
            $(".ck1").hide();
            $(".kt1").show();
            $(".ck2").hide();
            $(".kt2").show();
            $(".ck3").hide();
            $(".kt3").show();
            $(".ck4").hide();
            $(".kt4").show();
            for(var j=0;j<arr.length;j++){
//                console.log(arr[j])
                if(arr[j]==1){
                    $(".ck1").show();
                    $(".kt1").hide();
                }else if (arr[j]==2){
                    $(".ck2").show();
                    $(".kt2").hide();
                }else if (arr[j]==6){
                    $(".ck3").show();
                    $(".kt3").hide();
                }else if(arr[j]==7){
                    $("#card").hide();
                }
            }
        }
    });

    $(function(){
        getYesterdayCount();
        var statusOK =  $("#statusOK").text();
        if(statusOK=="已认证"){
            $("#goTo").hide();
            $("#statusOK").show();
        }else{
            $("#goTo").show();
            $("#statusOK").hide();
        }
    });

    $(function(){
        var loan = $("#loan").text().split("￥");
        var credit = $("#credit").text().split("￥");
        if(loan.splice(1,1) == 0.0000){
            $("#loan").parent().hide();
        };
        if(credit.splice(1,1) == 0.0000){
            $("#credit").parent().hide();
        };
    });

    function getYesterdayCount(){

        $.ajax({
            url: "${pageContext.request.contextPath}/userStatisticsDay/countUserYesterday",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                var obj=data;
                var flowMoney="";
                var tellMoney="";
                var videoMoney="";
                var cardMoney="";
                $(obj).each(function (i) {
                    if(obj[i].businessType==1){
                        flowMoney=obj[i].amount;
                        $("#flowYesterdayMoney").text(obj[i].amount);
                        $("#flowYesterdayOrder").text(obj[i].successOrderNum);
                        if(obj[i].successOrderNum==0){
                            $("#flowYesterdayRate").text(0);
                        }else{$("#flowYesterdayRate").text(obj[i].successRate);}

                    }else if(obj[i].businessType==2){
                        tellMoney=obj[i].amount;
                        $("#tellYesterdayMoney").text(obj[i].amount);
                        $("#tellYesterdayOrder").text(obj[i].successOrderNum);
                        if(obj[i].successOrderNum==0){
                            $("#tellYesterdayRate").text(0);
                        }else{$("#tellYesterdayRate").text(obj[i].successRate);}
                    }else if(obj[i].businessType==6){
                        videoMoney=obj[i].amount;
                        $("#videoYesterdayMoney").text(obj[i].amount);
                        $("#videoYesterdayOrder").text(obj[i].successOrderNum);
                        if(obj[i].successOrderNum==0){
                            $("#videoYesterdayRate").text(0);
                        }else{$("#videoYesterdayRate").text(obj[i].successRate);}
                    }else if (obj[i].businessType==7){
                        cardMoney=obj[i].amount;
                        $("#cardYesterdayMoney").text(obj[i].amount);
                        $("#cardYesterdayOrder").text(obj[i].successOrderNum);
                        if(obj[i].successOrderNum==0){
                            $("#cardYesterdayRate").text(0);
                        }else{$("#cardYesterdayRate").text(obj[i].successRate);}
                    }
                });
                $("#count").empty();
                $("#count").append(flowMoney+tellMoney);
            }
        });
    }

    //跳转
    function skip(param){
        if(param==1){
            window.location="<%=request.getContextPath()%>/flow/flowOverview.do?businessType=1";
        }else if(param==2){
            window.location="<%=request.getContextPath()%>/flow/teleOverview.do?businessType=2";
        } else if(param==6){
            window.location="<%=request.getContextPath()%>/video/videoOverview.do?businessType=6";
        } else if(param==3) {
            window.location="<%=request.getContextPath()%>/bill/billDetails.do";
        }

    }

    function dredgeApply(param){
        var isCache =1;
        var chargeType=1;
        if(param==1){
            $.ajax({
                url: "${pageContext.request.contextPath}/index/addUserApp",
                type:'POST',
                data:{
                    appType:param,
                    isCache:isCache,
                    chargeType:chargeType,
                    groupId:<%=flowChannelId%>
                },
                dataType:"json",
                success: function(data) {
                    if(data>0){
                        alert("恭喜你开通应用成功,请重新登录！");
                        parent.parentFunction();
                    }else{
                        alert("抱歉，开通应用失败！")
                    }
                }
            });
        }else if(param==2){
            $.ajax({
                url: "${pageContext.request.contextPath}/index/addUserApp",
                type:'POST',
                data:{
                    appType:param,
                    isCache:isCache,
                    chargeType:chargeType,
                    groupId:<%=costChannelId%>
                },
                dataType:"json",
                success: function(data) {
                    if(data>0){
                        alert("恭喜你开通应用成功,请重新登录！");
                        parent.parentFunction();
                    }else{
                        alert("抱歉，开通应用失败！")
                    }
                }
            });
        }else if(param==6){
            $.ajax({
                url: "${pageContext.request.contextPath}/index/addUserApp",
                type:'POST',
                data:{
                    appType:param,
                    isCache:isCache,
                    chargeType:chargeType,
                    groupId:<%=costChannelId%>
                },
                dataType:"json",
                success: function(data) {
                    if(data>0){
                        alert("恭喜你开通应用成功,请重新登录！");
                        parent.parentFunction();
                    }else{
                        alert("抱歉，开通应用失败！")
                    }
                }
            });
        }else if(param==7){
            $.ajax({
                url: "${pageContext.request.contextPath}/index/addUserApp",
                type:'POST',
                data:{
                    appType:param,
                    <%--isCache:isCache,--%>
                    <%--chargeType:chargeType,--%>
                    <%--groupId:<%=costChannelId%>--%>
                },
                dataType:"json",
                success: function(data) {
                    if(data>0){
                        alert("恭喜你开通应用成功,请重新登录！");
                        parent.parentFunction();
                    }else{
                        alert("抱歉，开通应用失败！")
                    }
                }
            });
        }
    }

</script>
</html>

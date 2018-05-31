<%--
  Created by IntelliJ IDEA.
  User: 首页 云通信商户平台
  Date: 2016/11/11
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>云通信</title>

    <%@include file="/WEB-INF/pages/common/head.jsp" %>
    <link href="${path}/static/css/index.css" rel="stylesheet">
    <%--<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>--%>
    <%--<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>--%>
</head>
<script>
    setTimeout(function () {
        alert("登录超时！请重新登录！")
        window.location.href = "<%=request.getContextPath()%>/admin/login.do";
    }, 1000 * 60 * 60 * 3);
    //二级菜单点击事件
    //流量话费
    //1、应用概览
    function hFist() {
        var flowGL = $("#flowGL").parent().children(":first").text();
        if (flowGL == "流量") {
            $("iframe").attr("src", "<%=request.getContextPath()%>/flow/flowOverview.do?businessType=1");
            return;
        }
    }
    //卡信息列表
    function cardCenter() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/cardCenter/index.do");
    }
    function iotSubOrder() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/iot/subOrder/index.do");
    }
    //2、单号充值
    function hSecond() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/flow/flowRechargeNumber.do");
    } //2、批量充值
    function hTwo() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/flow/flowBatchRecharge.do");
    }
    //3、充值记录
    function hThree() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/flow/floworderhistory.do");
    }
    //1、应用概览
    function hF() {
        var telGL = $("#telGL").parent().children(":first").text();
        if (telGL == "话费") {
            $("iframe").attr("src", "<%=request.getContextPath()%>/flow/teleOverview.do?businessType=2");
            return;
        }

    }
    //2、单号充值
    function hS() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/flow/teleRecharge.do");
    }
    //2、单号充值
    function vS() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/video/videoRecharge");
    }

    //3、批量充值
    function hT() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/flow/teleBatchRecharge.do");
    }
    //4、订单记录
    function hO() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/flow/telephonehistory.do");
    }
    //3、应用概览
    function videoOverview() {
        var videoGL = $("#videoGL").parent().children(":first").text();
        if (videoGL == "视频会员") {
            $("iframe").attr("src", "<%=request.getContextPath()%>/video/videoOverview.do?businessType=3");
            return;
        }
    }
    //4、订单记录
    function videoChat() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/video/videoOrder.do");
    }
    //账户信息
    function userMessage() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/admin/userMessage.do");
    }
    //子账户管理
    function subAccountManagement() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/user/subAccountManagement.do");
    }
    //密码管理
    function changePasd() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/admin/administrate.do");
    }
    //账单明细
    function financialOrder() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/bill/billDetails.do");
    }
    //银行卡转账
    function bankCardTransfer() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/finance/bankCardTransferUI.do");
    }
    //余额提醒
    function remind() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/admin/insufficientBalance.do");
    }
    function developer() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/developer/developeraccount.do");
    }
    function flowDocument() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/developer/flowDocument.do");
    }
    function cardDocument() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/developer/cardDocument.do");
    }

    function telephoneDocument() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/developer/telephoneDocument.do");
    }
    function videoDocument() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/developer/videoDocument.do");
    }
    function balanceEnquiry() {
        $("iframe").attr("src", "<%=request.getContextPath()%>/developer/balanceEnquiry.do");
    }
    function searchOrder() {
        $("iframe").attr("src", "https://sdk.qc600.cn/pc/shelfManagement.html?token=${users.token}");
        <%--$("iframe").attr("src", "http://merchant.qc600.cn/pc/shelfManagement.html?token=${users.token}");--%>
    }
    function shelvesManage() {
        $("iframe").attr("src", "https://sdk.qc600.cn/pc/query.html?token=${users.token}");
        <%--$("iframe").attr("src", "http://merchant.qc600.cn/pc/query.html?token=${users.token}");--%>
    }
</script>

<body>
<div class="z_wrapper">
    <!--    导航   -->
    <header>
        <div class="z_head">
            <div class="z_title">
                <img src="${path}/static/images/logo.png" alt="" style="margin-top:12px;">
            </div>
            <div class="z_nav">
                <ul>
                    <li><i class="fa fa-phone"></i>400-056-6681</li>
                    git
                    <li><i class="fa fa-user fa-lg"></i>${users.userName}</li>
                    <li class="z_bgNo"><i class="fa fa-sign-out"></i><a href="#" onclick="written()">退出</a></li>
                </ul>
            </div>
        </div>
    </header>
    <div class="z_content">
        <input type="hidden" value="${users.pId}" id="pId"/>
        <div class="z_left">
            <!--    侧边菜单    -->
            <div class="z_select">
                <table style="display: none">
                    <c:forEach items="${userApp}" var="app">
                        <tr>
                            <c:if test="${'4' eq app.appType}"><c:set var="flag" value="true"/></c:if>
                            <c:if test="${'5' eq app.appType}"><c:set var="diversion" value="true"/></c:if>
                            <td class="appType">${app.appType}</td>
                        </tr>
                    </c:forEach>
                </table>
                <dl>
                    <dt class="z_home"><i class="fa fa-home fa-lg"></i>首页</dt>
                </dl>
                <%--<dl id="card" style="display: none">--%>
                    <%--<dt><i class="fa fa-cloud-download"></i>物联网卡</dt>--%>
                    <%--<dd onclick="cardCenter();" id="cardCenter"><a href="#">卡信息列表</a></dd>--%>
                    <%--<dd onclick="iotSubOrder();" id="iotSubOrder"><a href="#">订单列表</a></dd>--%>
                <%--</dl>--%>

                <dl id="flow" style="display: none">
                    <dt><i class="fa fa-cloud-download"></i>流量</dt>
                    <dd onclick="hFist();" id="flowGL"><a href="#">应用概览</a></dd>
                    <dd onclick="hThree();"><a href="#">订单记录</a></dd>
                    <dd onclick="hSecond();"><a href="#">单号充值</a></dd>
                    <dd onclick="hTwo();"><a href="#">批量充值</a></dd>
                </dl>
                <dl id="charge" style="display: none">
                    <dt><i class="fa fa-envelope"></i>话费</dt>
                    <dd onclick="hF();" id="telGL"><a href="#">应用概览</a></dd>
                    <dd onclick="hO();"><a href="#">订单记录</a></dd>
                    <dd onclick="hS();"><a href="#">单号充值</a></dd>
                    <dd onclick="hT();"><a href="#">批量充值</a></dd>
                </dl>
                <dl id="video" style="display: none">
                    <dt><i class="fa fa-envelope"></i>视频会员</dt>
                    <dd onclick="videoOverview();" id="videoGL"><a href="#">应用概览</a></dd>
                    <dd onclick="videoChat();"><a href="#">订单记录</a></dd>
                    <dd onclick="vS();"><a href="#">充值模块</a></dd>
                </dl>
                <dl id="card" style="display: none">
                    <dt><i class="fa fa-cloud-download"></i>物联网卡</dt>
                    <dd onclick="cardCenter();" id="cardCenter"><a href="#">卡信息列表</a></dd>
                    <dd onclick="iotSubOrder();" id="iotSubOrder"><a href="#">订单列表</a></dd>
                    <%--<dd onclick="hThree();"><a href="#">订单记录</a></dd>--%>
                    <%--<dd onclick="hSecond();"><a href="#">单号充值</a></dd>--%>
                    <%--<dd onclick="hTwo();"><a href="#">批量充值</a></dd>--%>
                </dl>
                <dl>
                    <dt><i class="fa fa-rmb fa-lg"></i>财务</dt>
                    <dd onclick="financialOrder();"><a href="#">账单明细</a></dd>
                    <dd onclick="bankCardTransfer();"><a href="#">银行卡转账</a></dd>
                </dl>
                <dl>
                    <dt><i class="fa fa-user fa-lg"></i>账户</dt>
                    <dd onclick="subAccountManagement();" id="childAccount"><a href="#">子账户管理</a></dd>
                    <dd onclick="userMessage();"><a href="#">账户信息</a></dd>
                    <dd onclick="remind();"><a href="#">余额不足提醒</a></dd>
                    <dd onclick="changePasd();"><a href="#">密码管理</a></dd>
                </dl>
                <c:if test="${flag or diversion}">
                    <dl>
                        <dt><i class="fa fa-user fa-lg"></i>SDK商户</dt>
                        <c:if test="${flag}">
                            <dd onclick="searchOrder()"><a href="javascript:void(0);">货架管理</a></dd>
                        </c:if>
                        <dd onclick="shelvesManage()"><a href="javascript:void(0);">订单查询</a></dd>
                    </dl>
                </c:if>
                <dl id="z_developer">
                    <dt><i class="fa fa-cogs"></i>开发者</dt>
                    <dd onclick="developer()"><a href="#">开发者信息</a></dd>
                    <dd onclick="flowDocument()" id="flowDocument"><a href="#">流量接口文档</a></dd>
                    <dd onclick="cardDocument()" id="cardDocument"><a href="#">IOT接口文档</a></dd>
                    <dd onclick="telephoneDocument()" id="telephoneDocument"><a href="#">话费接口文档</a></dd>
                    <dd onclick="videoDocument()" id="videoDocument"><a href="#">视频会员文档</a></dd>
                    <dd onclick="balanceEnquiry()" id="balanceEnquiry"><a href="#">余额查询</a></dd>
                </dl>
            </div>
            <!--    二级菜单    -->
            <div class="z_selectNext">
                <ul></ul>
            </div>
        </div>
        <!--      右侧      -->
        <div class="z_right">
            <div class="z_rightWrapper">
                <!---------- 首页------------>
                <iframe width="100%" height="100%" src="<%=request.getContextPath()%>/index/fist.do"
                        frameborder="0"></iframe>
            </div>
        </div>
    </div>
</div>
<script>
    var appType = $(".appType").text();
    var pId = $("#pId").val();
    if (pId != "") {
        $("#childAccount").remove();
    } else {
        $("#childAccount").show();
    }
    var str;
    $(function () {
        for (var i = 0; i < appType.length; i++) {
            str = appType + ",";
            if (appType[i] == 1) {
                $("#flow").show();
            } else if (appType[i] == 2) {
                $("#charge").show();
            } else if (appType[i] == 6) {
                $("#video").show();
            } else if(appType[i] == 7){
                $("#card").show();
            }else{
                $("#charge").show();
                $("#flow").show();
                $("#video").show();
            }
        }
        dtClick();
    });

    function dtClick() {
        $(".z_home").click(function () {
            $("iframe").attr("src", "<%=request.getContextPath()%>/index/fist.do");
        });
        //侧边拦
        $(".z_select dd").css("display", "none");

        $(".z_select dt").click(function () {
            $(this).parent().find('dd').removeClass("menu_chioce");
            $(".menu_chioce").stop().slideUp();
            $(this).parent().find('dd').slideToggle();
            $(this).parent().find('dd').addClass("menu_chioce");
//                var a = str.split(",");
//                if(a[0]==1){
//                    $("#telephoneDocument").hide();
//                    $("#videoDocument").hide();
//                    return;
//                }else if(a[0]==2){
//                    $("#flowDocument").hide();
//                    $("#videoDocument").hide();
//                    return;
//                }else if(a[0]==6){
//                    $("#flowDocument").hide();
//                    $("#telephoneDocument").hide();
//                    return;
//                }else if(a[0]==12 || a[0]==21){
//                    $("#videoDocument").hide();
//                    return;
//                }else if(a[0]==16 || a[0]==61){
//                    $("#telephoneDocument").hide();
//                    return;
//                }else if(a[0]==26 || a[0]==62){
//                    $("#flowDocument").hide();
//                    return;
//                }
        })

        $(".z_select dd").each(function () {
            $(".z_select dd").click(function () {
                if ($(this).css("display") == "block") {
                    $(this).addClass("changeColor").siblings().removeClass("changeColor");
                    $(this).parent().siblings().find('dd').removeClass("changeColor");
                }
                ;
            });
        });
    };

    function written() {
        if (confirm("请确认是否退出！")) {
            window.location.href = "<%=request.getContextPath()%>/admin/LoginOut";
        }
    }
    function parentFunction() {
        window.location.href = "<%=request.getContextPath()%>/admin/LoginOut";
    }
</script>
</body>
</html>

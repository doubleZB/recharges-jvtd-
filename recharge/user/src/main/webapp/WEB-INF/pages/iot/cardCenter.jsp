<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/12/1
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link rel="stylesheet" href="${path}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/static/css/common.css">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/css/z-dingdanjilu.css">
    <style>
        label {
            display: inline-block;
            margin-bottom: 5px;
            font-weight: 100!important;
        }
        .am-table td {
            border-top: 1px solid #ddd!important;
        }
        .z_number{
            width:100px;
        }
        .texttable > thead > tr > th {
            padding:0!important;
            padding: 10px 0px !important;
        }
        .texttable > thead > tr > td {
            padding:0!important;
        }
        .am-form .am-form-group input{
            line-height:20px;
        }
        .am-form .am-form-group select {
            line-height:1.5;
        }
        .am-popup-bd {
            position: absolute;
            width: 100%;
            height: 100%;
        }
        .am-popup {
            -webkit-transition-property: -webkit-transform;
            transition-property: -webkit-transform;
            transition-property: transform;
            transition-property: transform,-webkit-transform;
            -webkit-transform: translateY(0%);
            -ms-transform: translateY(0%);
            transform: translateY(0%);
        }
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<%
    HttpSession s = request.getSession();
%>
<body style="height: 100%;width: 100%">

<div class="container" style="padding:23px;" id="content">
    <div class="row">
        <div class="col-lg-12">
            <div class="col-lg-12 m-box m-market" style="padding:0;">
                <div class="box-title" style="margin-bottom:30px;">卡信息列表</div>
                <div class="box-content">

                    <div class="col-lg-12 col-md-12 col-xs-12">
                        <!--  上半部分   -->
                        <div class="z_jl_tabContent">
                            <form class="am-form">
                                <fieldset>
                                    <!--   手机号输入   -->
                                    <div class="am-form-group">
                                        <input type="text" class="" id="ICCID" placeholder="ICCID">
                                        <input type="text" class="" id="MSISDN" placeholder="MSISDN">
                                        <select id="cardStatus">
                                            <option value="">卡状态</option>
                                            <c:forEach items="${cardStatusList}" var="pur">
                                                <option value="${pur.value}">${pur}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<span class="am-form-caret"></span>--%>
                                        <select class="z_select" id="operator">
                                            <option value="">运营商</option>
                                            <c:forEach items="${operatorList}" var="pur">
                                                <option value="${pur.value}">${pur}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<span class="am-form-caret"></span>--%>
                                        <button type="button"  class="am-btn am-btn-default am-radius am-fl am-margin-top-lg" onclick="reloadPage(1)" style="width:120px;background: #F37B1D;border-color:#F37B1D;margin-top: 0px;">查询</button>
                                    </div>
                                </fieldset>
                            </form>
                            <%--<div class="z_shu">--%>
                                <%--<!--   计数    -->--%>
                                <%--<p style="border-bottom:1px solid #ddd;">--%>
                                    <%--<a >记录条数： <span id="jilutiaoshu">0</span></a>--%>
                                    <%--<a >累计消费金额： <span id="leijijinge">0</span></a>--%>
                                    <%--<a href="javascript:void(0);" onclick="exportOrder()" style="color: #1c68f2;text-decoration: underline;">下载当前数据</a>--%>
                                <%--</p>--%>
                            <%--</div>--%>
                            <!--    表格   -->
                                <table class="am-table table-striped table-hover texttable" style="font-size: 14px;">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th class="z_number">ICCID</th>
                                    <th>MSISDN</th>
                                    <th>物理大小</th>
                                    <th>运营商</th>
                                    <th>流量套餐</th>
                                    <th>卡状态</th>
                                    <th>激活时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tr v-for="item in json">
                                    <td>{{item.id}}</td>
                                    <td>{{item.iccid}}</td>
                                    <td>{{item.msisdn}}</td>
                                    <td>{{item.cardSizeLiteral}}</td>
                                    <td>{{item.operatorLiteral}}</td>
                                    <td>{{item.productName}}</td>
                                    <td>{{item.statusLiteral}}</td>
                                    <td>{{item.activeTime.substring(0,19)}}</td>
                                    <td><a href="javascript:void(0);" onclick="openDetail({{item.id}})">查看</a></td>
                                </tr>
                            </table>
                            <div class="am-cf">
                                共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                                <div class="am-fr">
                                    <ul class="am-pagination" style="margin: 0">
                                        <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq-1}})">上一页</a></li>
                                        <li class="am-disabled"><a
                                                href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                        <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq+1}})">下一页</a></li>
                                        <li class="am-disabled"><a href="#">|</a></li>
                                        <li>
                                            <input type="text" id="gotoPage"
                                                   style="width: 30px;height: 30px;margin-bottom: 5px;"
                                                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                        </li>
                                        <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();"
                                                                 style="padding: .5rem .4rem;">GO</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="am-popup" id="my-popup" >
    <div class="am-popup-inner">
        <div class="am-popup-hd">
            <h4 class="am-popup-title">卡详情</h4>
            <span class="am-close am-close-spin" data-am-modal-close id="close">&times;</span>
        </div>
        <div class="am-popup-bd">
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">ICCID:</label>
                <span id="IccIdShow"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">MSISDN:</label>
                <span id="MSISDNShow"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">运营商:</label>
                <span id="operatorShow"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">流量套餐:</label>
                <span id="productName"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">卡状态:</label>
                <span id="cardStatusD"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">已用流量:</label>
                <span id="usedFlow"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">剩余流量:</label>
                <span id="residualFlow"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">总流量:</label>
                <span id="totalFlow"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">激活时间:</label>
                <span id="activeTime"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">卡尺寸:</label>
                <span id="cardSize"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">售价:</label>
                <span id="salePrice"></span>
            </div>
            <div class="am-list-news-bd">
                <label style="width: 80px;text-align: right">售价折扣:</label>
                <span id="saleCost"></span>
            </div>
        </div>
    </div>
</div>
<%--查看详情页--%>
<%--<div class="am-popup" id="my-popup1"  style="margin-top: -620px;">--%>
    <%--<div class="am-popup-inner">--%>
        <%--<div class="am-popup-hd">--%>
            <%--<h4 class="am-popup-title">卡详情</h4>--%>
            <%--<span data-am-modal-close--%>
                  <%--class="am-close">&times;</span>--%>
        <%--</div>--%>
        <%--<div class="am-popup-bd">--%>
            <%--<div data-am-widget="list_news" class="am-list-news am-list-news-default">--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>ICCID:</label>--%>
                    <%--<span id="IccIdShow"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>MSISDN:</label>--%>
                    <%--<span id="MSISDNShow"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>运营商:</label>--%>
                    <%--<span id="operatorShow"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>流量套餐:</label>--%>
                    <%--<span id="productName"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>卡状态:</label>--%>
                    <%--<span id="cardStatusD"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>已用流量:</label>--%>
                    <%--<span id="usedFlow"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>剩余流量:</label>--%>
                    <%--<span id="residualFlow"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>总流量:</label>--%>
                    <%--<span id="totalFlow"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>激活时间:</label>--%>
                    <%--<span id="activeTime"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>卡尺寸:</label>--%>
                    <%--<span id="cardSize"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>售价:</label>--%>
                    <%--<span id="salePrice"></span>--%>
                <%--</div>--%>
                <%--<div class="am-list-news-bd">--%>
                    <%--<label>售价折扣:</label>--%>
                    <%--<span id="saleCost"></span>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue1.js"></script>
<!--[if (gte IE 9)|!(IE)]><!-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="${path}/static/assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<!--<![endif]-->
<script>
    var pageInfo = {
        ts: 0,
        dq: 0,
        all: 0
    };
    var vm = new Vue({
        el: "#content",
        data: {
            json: [],
            page: pageInfo,
            item: {}
        },
        components: {
            "my-component": {
                template: "#myTemplate",
                data: function () {
                    return {
                        show1: true
                    }
                }
            }
        },
        methods: {}
    });

    $(function () {
        reloadPage(1);
    });
    function reloadPage(pageNumber) {
        if (pageNumber == 0) {
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var iccid = $("#ICCID").val().trim();
        var msisdn = $("#MSISDN").val().trim();
        var status = $("#cardStatus").val().trim();
        var operator = $("#operator").val().trim();
        var loadingIndex = layer.open({type: 3});
//        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/cardCenter/list?pageNumber=" + pageNumber + "&pageSize=10",
            type: 'POST',
            dataType: "json",
            async: false,
            data: {
                iccid: iccid,
                msisdn: msisdn,
                operator: operator,
                status: status
            },
            error: function () {
            },
            success: function (data) {
                pageInfo.ts = data.total;
                pageInfo.dq = data.pageNum;
                pageInfo.all = data.pages;
                vm.$set('json', data.list);
                layer.close(loadingIndex);
            }
        });
    }
    function gotoPage() {
        var pagenum = $("#gotoPage").val();
        if (pagenum == '') {
            layer.tips("请输入页数！", "#gotoPage", {tips: 3});
            return;
        }
        reloadPage(pagenum);
    }
    function openDetail(id) {
        $.ajax({
            url:"${pageContext.request.contextPath }/cardCenter/getCardById.do",
            type:"post",
            data:{id:id},
            dataType:"json",
            success:function (obj){
//                debugger
                if(obj.iccid!=null){$("#IccIdShow").html(obj.iccid)}
                if(obj.msisdn!=null){$("#MSISDNShow").html(obj.msisdn)}
                if(obj.operatorLiteral!=null){$("#operatorShow").html(obj.operatorLiteral)}
                if(obj.productName!=null){$("#productName").html(obj.productName)}
                if(obj.statusLiteral!=null){$("#cardStatusD").html(obj.statusLiteral)}
                if(obj.activeTime!=null){$("#activeTime").html(obj.activeTime)}
                if(obj.cardSizeLiteral!=null){$("#cardSize").html(obj.cardSizeLiteral)}
                if(obj.price!=null){$("#salePrice").html(obj.price)}
                if(obj.priceDiscount!=null){$("#saleCost").html(obj.priceDiscount)}
                if(obj.usedFlow!=null){$("#usedFlow").html(obj.usedFlow)}
                if(obj.leftFlow!=null){$("#residualFlow").html(obj.leftFlow)}
                if(obj.totalFlow!=null){$("#totalFlow").html(obj.totalFlow)}
                $("#my-popup").modal();
            }
        });
    }
    $("#close").click(function(){
        $("#my-popup").modal('hide');
    })
</script>
</body>
</html>

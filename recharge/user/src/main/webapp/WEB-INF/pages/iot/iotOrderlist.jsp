<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/12/1
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link rel="stylesheet" href="${path}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/static/css/common.css">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/css/z-dingdanjilu.css">
    <style>
        .am-table td {
            border-top: 1px solid #ddd !important;
        }

        .z_number {
            width: 100px;
        }

        .texttable > thead > tr > th {
            padding: 0 !important;
            padding: 10px 0px !important;
        }

        .texttable > thead > tr > td {
            padding: 0 !important;
        }

        .am-form .am-form-group input {
            line-height: 20px;
        }

        .am-form .am-form-group select {
            line-height: 1.5;
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
            transition-property: transform, -webkit-transform;
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
                <div class="box-title" style="margin-bottom:30px;">订单列表</div>
                <div class="box-content">

                    <div class="col-lg-12 col-md-12 col-xs-12">
                        <!--  上半部分   -->
                        <div class="z_jl_tabContent">
                            <form class="am-form">
                                <fieldset>
                                    <!--   手机号输入   -->
                                    <div class="am-form-group">
                                        <div>
                                            <input type="text" class="" id="parentSerialNum" placeholder="订单编号">
                                            <select id="subOrderStatus">
                                                <option value="">订单状态</option>
                                                <c:forEach items="${iotOrderStatusList}" var="pur">
                                                    <option value="${pur.value}">${pur}</option>
                                                </c:forEach>
                                            </select>
                                            <select id="iotProductId">
                                                <option value="">流量套餐</option>
                                                <c:forEach items="${iotProductList}" var="pur">
                                                    <option value="${pur.id}">${pur.name}</option>
                                                </c:forEach>
                                            </select>
                                            <select class="z_select" id="operator">
                                                <option value="">运营商</option>
                                                <c:forEach items="${operatorList}" var="pur">
                                                    <option value="${pur.value}">${pur}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div>
                                            <select class="z_select" id="Size"style="margin-top: 10px;">
                                                <option value="">选择物理大小</option>
                                                <c:forEach items="${cardSizeList}" var="pur">
                                                    <option value="${pur.value}">${pur}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="text" readonly id="startTime" placeholder="开始时间"
                                                   style="background: none;margin-top: 10px;">
                                            <input type="text" readonly id="endTime" placeholder="结束时间"
                                                   style="background: none;margin-top: 10px;">
                                            <button type="button"
                                                    class="am-btn am-btn-default am-radius am-fl am-margin-top-lg"
                                                    onclick="reloadPage(1)"
                                                    style="width:120px;background: #F37B1D;border-color:#F37B1D;margin-top: 10px;">
                                                查询
                                            </button>
                                        </div>
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
                                    <th>订单编号</th>
                                    <%--<th>子订单编号</th>--%>
                                    <th>流量套餐</th>
                                    <th>物理大小</th>
                                    <th>面值(元)</th>
                                    <th>售价折扣</th>
                                    <th>售价(元)</th>
                                    <th>数量</th>
                                    <th>总金额(元)</th>
                                    <th>运营商</th>
                                    <th>订单状态</th>
                                    <th>下单时间</th>
                                </tr>
                                </thead>
                                <tr v-for="item in json">
                                    <td>{{item.parentSerialNum}}</td>
                                    <%--<td>{{item.serialNum}}</td>--%>
                                    <td>{{item.productName}}</td>
                                    <td>{{item.cardSizeLiteral}}</td>
                                    <td>{{item.stdPrice}}</td>
                                    <td>{{item.priceDiscount}}</td>
                                    <td>{{item.price}}</td>
                                    <td>{{item.total}}</td>
                                    <td>{{item.amount}}</td>
                                    <td>{{item.operatorLiteral}}</td>
                                    <td>{{item.statusLiteral}}</td>
                                    <td>{{item.createTime.substring(0,19)}}</td>
                                </tr>
                            </table>
                            <div class="am-cf">
                                共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                                <div class="am-fr">
                                    <ul class="am-pagination" style="margin: 0">
                                        <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq-1}})">上一页</a>
                                        </li>
                                        <li class="am-disabled"><a
                                                href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                        <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq+1}})">下一页</a>
                                        </li>
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
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue1.js"></script>
<script src="${pageContext.request.contextPath}/static/js/laydate.js"></script>
<!--[if (gte IE 9)|!(IE)]><!-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="${path}/static/assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<!--<![endif]-->
<script>

    /* 用于日期特效的 */
    laydate.skin('molv');
    var start = {
        elem: '#startTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false,
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end = {
        elem: '#endTime',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false,
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(start);
    laydate(end);


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
        var createDate = $("#startTime").val();
        var endDate = $("#endTime").val();
        var status = $("#subOrderStatus").val();
//        var serialNum = $("#serialNum").val();
        var parentSerialNum = $("#parentSerialNum").val();
        var productId = $("#iotProductId").val();
        var size = $("#Size").val();
        var operator = $("#operator").val();
        if (createDate != "") {
            if (endDate == "") {
                layer.msg("请选择结束日期！");
                return;
            } else {
                createDate = createDate + " " + "00:00:00";
                endDate = endDate + " " + "23:59:59";
            }
        }
        var loadingIndex = layer.open({type: 3});
        $.ajax({
            url: "/iot/subOrder/list?pageNumber=" + pageNumber + "&pageSize=10",
            type: 'POST',
            dataType: "json",
            async: false,
            data: {
                "createDate": createDate,
                "endDate": endDate,
                "status": status,
//                "serialNum": serialNum,
                "parentSerialNum": parentSerialNum,
                "cardSizeLiteral": size,
                "flowProductId": productId,
                "operator": operator
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
            url: "${pageContext.request.contextPath }/cardCenter/getCardById.do",
            type: "post",
            data: {id: id},
            dataType: "json",
            success: function (obj) {
//                debugger
                if (obj.iccid != null) {
                    $("#IccIdShow").html(obj.iccid)
                }
                if (obj.msisdn != null) {
                    $("#MSISDNShow").html(obj.msisdn)
                }
                if (obj.operatorLiteral != null) {
                    $("#operatorShow").html(obj.operatorLiteral)
                }
                if (obj.productName != null) {
                    $("#productName").html(obj.productName)
                }
                if (obj.statusLiteral != null) {
                    $("#cardStatusD").html(obj.statusLiteral)
                }
                if (obj.activeTime != null) {
                    $("#activeTime").html(obj.activeTime)
                }
                if (obj.cardSizeLiteral != null) {
                    $("#cardSize").html(obj.cardSizeLiteral)
                }
                if (obj.price != null) {
                    $("#salePrice").html(obj.price)
                }
                if (obj.priceDiscount != null) {
                    $("#saleCost").html(obj.priceDiscount)
                }
                if (obj.usedFlow != null) {
                    $("#usedFlow").html(obj.usedFlow)
                }
                if (obj.leftFlow != null) {
                    $("#residualFlow").html(obj.leftFlow)
                }
                if (obj.totalFlow != null) {
                    $("#totalFlow").html(obj.totalFlow)
                }
                $("#my-popup").modal();
            }
        });
    }
    $("#close").click(function () {
        $("#my-popup").modal('hide');
    })
</script>
</body>
</html>

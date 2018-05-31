<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/20
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">

<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/yonghuliebiao.css">
</head>
<style>
    #content {
        margin-top: 20px;
    }

    .am-btn {
        border-radius: 5px;
    }

    input, select {
        color: #848181;
    }

    .am-table > caption + thead > tr:first-child > td, .am-table > caption + thead > tr:first-child > th, .am- table > colgroup + thead > tr:first-child > td, .am-table > colgroup + thead > tr:first-child > th, .am- table > thead:first-child > tr:first-child > td, .am-table > thead:first-child > tr:first-child > th {
        border-top: 0;
        font-size: 1.4rem;
    }

    .am-table > tbody > tr > td, .am-table > tbody > tr > th, .am-table > tfoot > tr > td, .am-table > tfoot > tr > th, .am- table > thead > tr > td, .am-table > thead > tr > th {
        padding: .7rem;
        line-height: 1.6;
        vertical-align: top;
        border-top: 1px solid #ddd;
        font-size: 1.4rem;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">订单管理</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form">
                        <div>
                        <div class="am-form-group">
                            <input type="text" id="parentSerialNum" class="am-form-field" placeholder="订单编号"
                                   style="width: 150px;float: left;">
                        </div>
                        <%--<div class="am-form-group">--%>
                            <%--<input type="hidden" id="serialNum" class="am-form-field" placeholder="子订单编号"--%>
                                   <%--style="width: 230px;float: left;">--%>
                        <%--</div>--%>
                        <div class="am-form-group">
                            <select style="width: 150px;float: left;" class="am-form-field" id="status">
                                <option value="">选择订单状态</option>
                                <c:forEach items="${iotOrderStatusList}" var="ope">
                                    <option value="${ope.value}">${ope}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select style="width: 150px;float: left;" class="am-form-field" id="iotProductId">
                                <option value="">选择流量套餐</option>
                                <c:forEach items="${iotProductList}" var="ope">
                                    <option value="${ope.id}">${ope.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select  style="width: 150px;float: left;" class="am-form-field" id="operator">
                                <option value="">选择运营商</option>
                                <c:forEach items="${operatorList}" var="ope">
                                    <option value="${ope.value}">${ope}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select  style="width: 150px;float: left;" class="am-form-field" id="Size">
                                <option value="">选择物理大小</option>
                                <c:forEach items="${cardSizeList}" var="ope">
                                    <option value="${ope.value}">${ope}</option>
                                </c:forEach>
                            </select>
                        </div>
                        </div>
                        <div>
                        <div class="am-form-group">
                            <input type="text" id="adminUserName" class="am-form-field" placeholder="销售人员"
                                   style="width: 150px;float: left; margin-top: 10px">
                        </div>
                        <div class="am-form-group demo1" >
                            <input class="am-form-field" name="orderTimeOne" id="createDate" placeholder="开始时间"
                            <%--style="width: 200px; margin-right: 1px; height: 35px; margin-top: -22px;"--%>
                                   style="width: 150px;float: left;margin-top: 10px;" readonly required>
                        </div>
                        <div class="am-form-group demo1" >
                            <input class="am-form-field" name="orderTimeOne" id="endDate" placeholder="结束时间"
                            <%--style="width: 200px; margin-right: 1px; height: 35px; margin-top: -22px;"--%>
                                   style="width: 150px;float: left;margin-top: 10px;" readonly required>
                        </div>
                        <div class="am-form-group">
                            <input type="text" id="clientName" class="am-form-field" placeholder="客户名称"
                                   style="width: 150px;float: left;margin-top: 10px;">
                        </div>
                        <button onclick="reloadPage(1);" class="am-btn am-btn-warning" style="width: 120px;margin-top: 10px;background-color: #0e90d2; border-color: #0e90d2;">查询</button>
                        </div>
                    </div>
                    <hr>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>订单编号</th>
                            <th>流量套餐</th>
                            <th>物理大小</th>
                            <th>面值(元)</th>
                            <th>售价折扣</th>
                            <th>售价(元)</th>
                            <th>数量</th>
                            <th>总金额(元)</th>
                            <th>客户名称</th>
                            <th>订单状态</th>
                            <th>销售人员</th>
                            <th>创建时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>{{item.parentSerialNum}}</td>
                            <td>{{item.productName}}</td>
                            <td>{{item.cardSizeLiteral}}</td>
                            <td>{{item.stdPrice}}</td>
                            <td>{{item.priceDiscount}}</td>
                            <td>{{item.price}}</td>
                            <td>{{item.total}}</td>
                            <td>{{item.amount}}</td>
                            <td>{{item.clientName}}</td>
                            <td>{{item.statusLiteral}}</td>
                            <td>{{item.adminUserName}}</td>
                            <td>{{item.createTime.substring(0,19)}}</td>
                        </tr>
                        </tbody>
                    </table>
                    <hr>
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
    <!-- content end -->
</div>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/laydate/laydate.js "></script>
<script>
    laydate({
        elem: "#createDate"
    });
    laydate({
        elem: "#endDate"
    });
</script>
<script>

    var pageInfo = {
        ts: 0,
        dq: 0,
        all: 0
    };
    var vm = new Vue({
        el: "#content",
        data: {
            lx: "",
            hd: "",
            yys: "",
            sf: "",
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
        $("#am-nav li").click(function () {
            $(".tab_list li").eq($(this).index()).show().siblings().hide();
        });
    });

    function reloadPage(pageNumber) {
        if (pageNumber == 0) {
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var createDate = $("#createDate").val();
        var endDate = $("#endDate").val();
        var clientName = $("#clientName").val();
        var adminUserName = $("#adminUserName").val();
        var orderSerialNum = $("#orderSerialNum").val();
        var status = $("#status").val();
        var serialNum = $("#serialNum").val();
        var parentSerialNum = $("#parentSerialNum").val();
        var productId = $("#iotProductId").val();
        var size = $("#Size").val();
        var operator = $("#operator").val();
        if(createDate!=""){
            if(endDate==""){
                layer.msg("请选择结束日期！");
                return;
            }else{
                createDate = createDate+" "+"00:00:00";
                endDate = endDate+" "+"23:59:59";
            }
        }
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotOrder/getIotOrderList?pageNumber=" + pageNumber + "&pageSize=10",
            type: 'POST',
            async: false,
            data: {
                "createDate": createDate,
                "endDate": endDate,
                "clientName": clientName,
                "adminUserName": adminUserName,
                "status": status,
                "serialNum": serialNum,
                "parentSerialNum": parentSerialNum,
                "cardSizeLiteral": size,
                "flowProductId": productId,
                "operator": operator
            },
            dataType: "json",
            error: function () {
                $(this).addClass("done");
            },
            success: function (data) {
                pageInfo.ts = data.total;
                pageInfo.dq = data.pageNum;
                pageInfo.all = data.pages;
                vm.$set('json', data.list);
                layer.close(index);
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

</script>


</body>

</html>

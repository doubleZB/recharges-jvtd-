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
                <li id="table1" class="am-active"><a href="#tab1">产品管理</a></li>
                <li id="table2"><a href="#tab2">新增</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <select style="width: 150px;float: left; height:35px" id="operator">
                                <option value="">选择运营商</option>
                                <c:forEach items="${operatorList}" var="ope">
                                    <option value="${ope.value}">${ope}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select style="width: 150px;float: left; height:35px" id="period">
                                <option value="">周期</option>
                                <c:forEach items="${periodList}" var="period">
                                    <option value="${period.value}">${period}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select style="width: 150px;float: left; height:35px" id="type">
                                <option value="">包类型</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.value}">${type}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button onclick="reloadPage(1);" class="am-btn am-btn-warning" style="width: 120px;background-color: #0e90d2; border-color: #0e90d2;">查询</button>
                    </div>
                    <hr>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>流量套餐</th>
                            <th>产品编码</th>
                            <th>面值</th>
                            <th>包大小</th>
                            <th>周期</th>
                            <th>包类型</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>{{item.name}}</td>
                            <td>{{item.code}}</td>
                            <td>{{item.price}}</td>
                            <td>{{item.flowPackageSizeLiteral}}</td>
                            <td>{{item.periodLiteral}}</td>
                            <td>{{item.typeLiteral}}</td>
                            <td>
                                <a class="am-btn am-btn-link nava" style="padding: 0;"
                                   onclick="shows({{item.id}})">修改</a>
                                <%--<a onclick="remove({{item.id}})" class="am-btn am-btn-link"--%>
                                   <%--style="padding: 0;color: #F37B1D;">删除</a>--%>
                            </td>
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
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal" id="addProductForm">
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">运营商：</label>
                            <select style="width: 200px;float: left;" id="cardOperator">
                                <option value="">选择运营商</option>
                                <c:forEach items="${operatorList}" var="ope">
                                    <option value="${ope.value}">${ope}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">包大小：</label>
                            <input id="flowPackageSize" name="contactName" type="text" class="am-form-field"
                                   style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">周期：</label>
                            <select style="width: 200px;float: left;" id="cardPeriod">
                                <option value="">周期</option>
                                <c:forEach items="${periodList}" var="period">
                                    <option value="${period.value}">${period}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px; ">包类型：</label>
                            <select style="width: 200px;float: left;" id="cardType">
                                <option value="">包类型</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.value}">${type}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">价格：</label>
                            <input id="price" name="contactMobile" type="text" class="am-form-field"
                                   style="width: 200px;float: left;">
                        </div>
                    </form>
                    <div class="am-form-group">
                        <button id="submitToAdd" class="am-btn am-btn-warning" style="width: 120px;margin-left: 120px;background-color: #0e90d2;
    border-color: #0e90d2;">提交</button>
                        <button class="am-btn am-btn-default" style="width: 120px;" id="close">返回</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<!--   //编辑-->
<div class="tab_wu" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">产品修改编辑</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" style="margin-top: 20px;">
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">运营商：</label>
            <span id="userloginNametoEdit"></span>
            <select style="width: 200px;float: left;" id="cardOperatorEdit">
                <c:forEach items="${operatorList}" var="ope">
                    <option value="${ope.value}">${ope}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <input type="hidden" id="productId">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">包大小：</label>
            <input id="flowPackageSizeEdit" name="contactName" type="text" class="am-form-field"
                   style="width: 200px;float: left;">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">周期：</label>
            <select style="width: 200px;float: left;" id="cardPeriodEdit">
                <c:forEach items="${periodList}" var="period">
                    <option value="${period.value}">${period}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">包类型：</label>
            <select style="width: 200px;float: left;" id="cardTypeEdit">
                <c:forEach items="${typeList}" var="type">
                    <option value="${type.value}">${type}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">价格：</label>
            <input id="priceEdit" name="contactMobile" type="text" class="am-form-field"
                   style="width: 200px;float: left;">
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" onclick="toEditAdminUser();" style="width: 120px;margin-left: 120px;background-color: #0e90d2; border-color: #0e90d2;">提交</button>
        <button class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
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
        $("#submitToAdd").click(function () {
//            debugger
            var cardOperator = $("#cardOperator").val();
            var flowPackageSize = $("#flowPackageSize").val();
            var cardPeriod = $("#cardPeriod").val();
            var cardType = $("#cardType").val();
            var price = $("#price").val();
            if (cardOperator == '') {
                layer.tips("请选择供应商", "#cardOperator", 1);
                return;
            }
            var reg = /^[1-9]\d*$/;
            if (!reg.test(flowPackageSize)){
                layer.tips("请输入正整数", "#flowPackageSize", 1);
                return;
            }
            if (cardPeriod == '') {
                layer.tips("请选择周期", "#cardPeriod", 1);
                return;
            }
            if (cardType == '') {
                layer.tips("请选择包类型", "#cardType", 1);
                return;
            }
            var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
            if (!exp.test(price)) {
                layer.tips("请输入正确的价格", "#price", 1);
                return;
            }
            index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/iotProduct/addOrUpdateProduct",
                type: 'POST',
                async: false,
                data: {
                    "cardOperator": cardOperator,
                    "flowPackageSize": flowPackageSize,
                    "cardPeriod": cardPeriod,
                    "cardType": cardType,
                    "price": price
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        $("#addProductForm").reset();
                        layer.msg(data.message, {icon: 1});
                        reloadPage(1);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        });
    });

    function shows(id) {
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotProduct/getProductById",
            type: 'POST',
            async: false,
            data: {"id": id},
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                $("#productId").val(data.id);
                $("#cardOperatorEdit").val(data.operator);
                $("#flowPackageSizeEdit").val(data.flowPackageSize);
                $("#cardPeriodEdit").val(data.period);
                $("#cardTypeEdit").val(data.type);
                $("#priceEdit").val(data.price);
                layer.close(index);
                $('.admin-content-body').hide();
                $('.tab_wu').show();
            }
        });

    }

    function hides() {
        $('.admin-content-body').show();
        $('.tab_wu').hide();
    }
    $("#close").click(function (){
        $("#table2").removeClass("am-active");
        $("#tab2").removeClass("am-active");
        $("#tab2").removeClass("am-in");
        $("#table1").addClass("am-active");
        $("#tab1").addClass("am-active");
        $("#tab1").addClass("am-in");
    })
    function reloadPage(pageNumber) {
        if (pageNumber == 0) {
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var operator = $("#operator").val();
        var period = $("#period").val();
        var type = $("#type").val();
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotProduct/getProductList?pageNumber=" + pageNumber + "&pageSize=10",
            type: 'POST',
            async: false,
            data: {
                "operator": operator,
                "period": period,
                "type": type
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
//    function remove(uid) {
//        layer.confirm('你确定删除此记录吗？', {
//            btn: ['确定', '取消'] //按钮
//        }, function () {
//            var index = layer.load(0, {shade: [0.3, '#000']});
//            $.ajax({
//                url: "/iotProduct/delProduct",
//                type: 'POST',
//                async: false,
//                data: {"productId": uid},
//                dataType: "json",
//                error: function () {
//                    layer.close(index);
//                },
//                success: function (data) {
//                    layer.close(index);
//                    if (data==1) {
//                        layer.msg("删除成功");
//                        location.reload();
//                    } else {
//                        layer.msg(data.message, {icon: 2});
//                    }
//                }
//            });
//        }, function () {
//            layer.closeAll();
//        });
//    }

    function toEditAdminUser() {
//        debugger
        var operator = $("#cardOperatorEdit").val();
        var flowPackageSize = $("#flowPackageSizeEdit").val();
        var period = $("#cardPeriodEdit").val();
        var type = $("#cardTypeEdit").val();
        var price = $("#priceEdit").val();
        var productId =$("#productId").val();
        if (operator == '') {
            layer.tips("请选择供应商", "#cardOperatorEdit", 1);
            return;
        }
        var reg = /^[1-9]\d*$/;
        if (!reg.test(flowPackageSize)){
            layer.tips("请输入包大小", "#flowPackageSizeEdit", 1);
            return;
        }
        if (period == '') {
            layer.tips("请选择周期", "#cardPeriodEdit", 1);
            return;
        }
        if (type == '') {
            layer.tips("请选择包类型", "#cardTypeEdit", 1);
            return;
        }
        var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
        if (!exp.test(price)) {
            layer.tips("请输入正确价格", "#priceEdit", 1);
            return;
        }
//        var test = /^[\u4e00-\u9fa5]{2,4}$/;
//        if (!test.test(userName)) {
//            layer.tips("用户姓名仅限2-4个汉字！", "#userNametoedit");
//            return;
//        }
//        if (password != '') {
//            if (password.length < 4) {
//                layer.tips("密码位数不够", "#userpasswordtoedit", 1);
//                return;
//            }
//            if (password.length > 10) {
//                layer.tips("密码位数太长", "#userpasswordtoedit", 1);
//                return;
//            }
//        }

        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotProduct/addOrUpdateProduct",
            type: 'POST',
            async: false,
            data: {
                "cardOperator": operator,
                "flowPackageSize": flowPackageSize,
                "cardPeriod": period,
                "cardType": type,
                "price": price,
                "productId": productId
            },
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                layer.close(index);
                if (data.success) {
                    layer.msg(data.message, {icon: 1})
                    reloadPage(1);
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }

</script>

</body>

</html>

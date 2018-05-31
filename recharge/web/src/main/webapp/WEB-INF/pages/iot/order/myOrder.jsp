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
                <li id="table1" class="am-active"><a href="#tab1">我的订单</a></li>
                <li id="table2"><a href="#tab2">新增</a></li>
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
                                   <%--style="width: 170px;float: left;">--%>
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
                                   style="width: 150px;float: left;margin-top: 10px;">
                        </div>
                        <div class="am-form-group demo1">
                            <input class="am-form-field" name="orderTimeOne" id="createDate" placeholder="开始时间"
                            <%--style="width: 200px; margin-right: 1px; height: 35px; margin-top: -22px;"--%>
                                   style="width: 150px;float: left;margin-top: 10px;" readonly required>
                        </div>
                        <div class="am-form-group demo1">
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
                            <th>操作</th>
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
                            <td>
                                <a v-if="item.status==8" href="javascript:void(0);" onclick="editDisplay({{item.id}})" class="am-btn am-btn-link" style="padding: 0;color: #0e90d2;">修改</a>
                                <a v-if="item.status==8 || item.status==0" href="javascript:void(0);" onclick="cancelOrder({{item.id}},{{item.status}})" class="am-btn am-btn-link" style="padding: 0;color: #0e90d2;">取消</a>
                                <a v-if="item.status!=0 && item.status!=3 && item.status!=6 && item.status!=7 && item.status!=8 && item.status != 11" onclick="refund({{item.id}},{{item.status}})" class="am-btn am-btn-link"style="padding: 0;color: #0e90d2;">退款</a>
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
                    <form class="am-form am-form-horizontal" id="addSupplierForm">
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">客户名称：</label>
                            <select style="width: 300px;float: left; " id="userId">
                                <option value="">选择客户</option>
                                <c:forEach items="${userList}" var="ope">
                                    <option value="${ope.id}">${ope.userCnName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">流量套餐：</label>
                            <select style="width: 300px;float: left;" id="productId">
                                <option value="">选择流量套餐</option>
                                <c:forEach items="${iotProductList}" var="ope">
                                    <option value="${ope.id}">${ope.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">物理大小：</label>
                            <select style="width: 300px;float: left;" id="size">
                                <option value="">选择物理大小</option>
                                <c:forEach items="${cardSizeList}" var="ope">
                                    <option value="${ope.value}">${ope}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">销售折扣：</label>
                            <input id="priceDiscount" name="contactMobile" type="text" class="am-form-field"
                                   style="width: 300px;float: left; height: auto ">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">订购数量：</label>
                            <input id="total" name="contactMobile" type="text" class="am-form-field"
                                   style="width: 300px;float: left;height: auto">
                            <%--<span  id="passstrength" ></span>--%>
                        </div>
                    </form>
                    <div class="am-form-group">
                        <button id="submitToAdd" class="am-btn am-btn-warning" style="width: 120px;margin-left: 120px;background-color: #0e90d2; border-color: #0e90d2;">
                            提交
                        </button>
                        <%--<a href="#tab1">返回</a>--%>
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
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">修改订单信息</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" style="margin-top: 20px;">
        <div class="am-form-group">
            <input id="subOrderIdE" name="subOrderIdE" type="hidden">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">客户名称：</label>
            <select style="width: 300px;float: left; " id="userIdE">
                <option value="">选择客户</option>
                <c:forEach items="${userList}" var="ope">
                    <option value="${ope.id}">${ope.userCnName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">流量套餐：</label>
            <select style="width: 300px;float: left;" id="productIdE">
                <option value="">选择流量套餐</option>
                <c:forEach items="${iotProductList}" var="ope">
                    <option value="${ope.id}">${ope.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px; height: 60px">物理大小：</label>
            <select style="width: 300px;float: left;" id="cardSizeE">
                <option value="">选择物理大小</option>
                <c:forEach items="${cardSizeList}" var="ope">
                    <option value="${ope.value}">${ope}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">销售折扣：</label>
            <input id="priceDiscountE" name="priceDiscountE" type="text" class="am-form-field"
                   style="width: 300px;float: left; height: auto ">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label" style="width: 190px;">订购数量：</label>
            <input id="totalE" name="totalE" type="text" class="am-form-field"
                   style="width: 300px;float: left;height: auto">
            <%--<span  id="passstrength" ></span>--%>
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" onclick="update();" style="width: 120px;margin-left: 120px;background-color: #0e90d2; border-color: #0e90d2;">提交
        </button>
        <button class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>
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
        $("#submitToAdd").click(function () {
            var userId = $("#userId").val();
            var productId = $("#productId").val();
            var priceDiscount = $("#priceDiscount").val();
            var size = $("#size").val();
            var total = $("#total").val();
            if (userId == '') {
                layer.tips("请选择客户", "#userId", 1);
                return;
            }
            if (productId == '') {
                layer.tips("请选择流量套餐", "#productId", 1);
                return;
            }
            if (size == '') {
                layer.tips("请选择物理大小", "#size", 1);
                return;
            }
            var count = /\b0(\.\d{1,2})\b/;
            var reg = /^[1-9]\d*$/;
            if (!count.test(priceDiscount)) {
                layer.tips("请输入0到1之间的两位小数", "#priceDiscount", 1);
                return;
            }
            if (!reg.test(total)) {
                layer.tips("请输入正整数", "#total", 1);
                return;
            }
            var index = layer.load(0, {shade: [0.3, '#000']});
            index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/iotMyOrder/addIotOrder",
                type: 'POST',
                async: false,
                data: {
                    "userId": userId,
                    "flowProductId": productId,
                    "size": size,
                    "priceDiscount": priceDiscount,
                    "total": total
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        $("#addSupplierForm").reset();
                        layer.msg("新增成功")
                        reloadPage(1);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        });
    });

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
        var createDate = $("#createDate").val();
        var endDate = $("#endDate").val();
        var clientName = $("#clientName").val();
        var adminUserName = $("#adminUserName").val();
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
            url: "/iotMyOrder/list?pageNumber=" + pageNumber + "&pageSize=10",
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

    function update() {
        var subOrderId = $("#subOrderIdE").val();
        var userId = $("#userIdE").val();
        var productId = $("#productIdE").val();
        var priceDiscount = $("#priceDiscountE").val();
        var size = $("#cardSizeE").val();
        var total = $("#totalE").val();
        if (userId == '') {
            layer.tips("请选择客户", "#userIdE", 1);
            return;
        }
        if (productId == '') {
            layer.tips("请选择流量套餐", "#productIdE", 1);
            return;
        }
        if (size == '') {
            layer.tips("请选择物理大小", "#cardSizeE", 1);
            return;
        }
        var count = /\b0(\.\d{1,2})\b/;
        var reg = /^[1-9]\d*$/;
        if (!count.test(priceDiscount)) {
            layer.tips("请输入0到1之间的两位小数包含1", "#priceDiscountE", 1);
            return;
        }
        if (!reg.test(total)) {
            layer.tips("请输入正整数", "#totalE", 1);
            return;
        }
        var index = layer.load(0, {shade: [0.3, '#000']});
        index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotMyOrder/updateIotOrder",
            type: 'POST',
            async: false,
            data: {
            	 id: subOrderId,
                "userId": userId,
                "flowProductId": productId,
                "size": size,
                "priceDiscount": priceDiscount,
                "total": total
            },
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                layer.close(index);
                if (data.success) {
                    $("#addSupplierForm").reset();
                    layer.msg("修改成功")
                    reloadPage(1);
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }
    //编辑回显
    function editDisplay(id){
        $.ajax({
            url:"${pageContext.request.contextPath }/iotMyOrder/getOrderById.do",
            type:"post",
            data:{id:id},
            dataType:"json",
            success:function (obj){
                $("#subOrderIdE").val(obj.id);
                $("#userIdE").val(obj.userId);
                $("#productIdE").val(obj.flowProductId);
                $("#cardSizeE").val(obj.size);
                $("#priceDiscountE").val(obj.priceDiscount);
                $("#totalE").val(obj.total);
                $('.admin-content-body').hide();
                $('.tab_wu').show();
            }
        });
    };


    function cancelOrder(id,currentStatus) {
        layer.confirm('你确定要取消该订单吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/iotMyOrder/cancelIotOrder",
                type: 'POST',
                async: false,
                data: {
                    "iotSubOrderId": id,
                    "currentStatus":currentStatus
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg("成功取消订单")
                        reloadPage(1);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        }, function () {
            layer.closeAll();
        });
    }
    function refund(id,status) {
        layer.confirm('确定退款吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/iot/handle/refund",
                type: 'POST',
                async: false,
                data: {
                    "iotSubOrderId": id,
                    "status":status
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message,{icon:1});
                        reloadPage(1);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        }, function () {
            layer.closeAll();
        });
    }
</script>


</body>

</html>

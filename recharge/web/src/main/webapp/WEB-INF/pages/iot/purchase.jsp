<%--
  Created by IntelliJ IDEA.
  User: lhm
  Date: 2017/1/22
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/WEB-INF/pages/common/head.jsp" %>
</head>
<body style="height: 100%">
<style>
    .am-form-label {
        font-weight: normal;
        width: 120px;
        padding-left: 0;
    }

    .am-table > caption + thead > tr:first-child > td, .am-table > caption + thead > tr:first-child > th, .am- table > colgroup + thead > tr:first-child > td, .am-table > colgroup + thead > tr:first-child > th, .am- table > thead:first-child > tr:first-child > td, .am-table > thead:first-child > tr:first-child > th {
        border-top: 0;
        font-size: 14px;
    }

    .am-table > tbody > tr > td, .am-table > tbody > tr > th, .am-table > tfoot > tr > td, .am-table > tfoot > tr > th, .am- table > thead > tr > td, .am-table > thead > tr > th {
        padding: .7rem;
        line-height: 1.6;
        vertical-align: top;
        border-top: 1px solid #ddd;
        font-size: 14px;
    }

    * {
        -moz-user-select: text !important;
        -webkit-user-select: text !important;
        -ms-user-select: text !important;
        user-select: text !important;
    }

</style>
<!-- content start -->
<div class="admin-content" id="content" v-cloak style="padding-top: 20px;">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li id="table1" class="am-active"><a href="#tab1">采购单管理</a></li>
                <li id="table2"><a href="#tab2">新增</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top:0;">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input type="text" name="orderSerialNum" id="orderSerialNum" class="am-form-field"
                                   placeholder="关联订单编号" style="width: 170px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="serialNum" id="serialNum" class="am-form-field" placeholder="采购单编号"
                                   style="width: 170px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="purchaseStatus" id="purchaseStatus"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="采购单状态">
                                <option value="">采购状态</option>
                                <c:forEach items="${purchaseStateList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="createrName" id="createrName" class="am-form-field"
                                   placeholder="创建人" style="width: 150px;float: left;">
                        </div>

                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;"
                                onclick="reloadPage(1)">查询
                        </button>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <tr>
                            <th>序号</th>
                            <th>采购单编号</th>
                            <th>物理大小</th>
                            <th>流量套餐</th>
                            <th>面值(元)</th>
                            <th>供应商</th>
                            <th>采购折扣</th>
                            <th>采购价(元)</th>
                            <th>采购数量</th>
                            <th>关联订单</th>
                            <th>采购状态</th>
                            <th>创建人</th>
                            <th>创建时间</th>
                            <td>操作</td>
                        </tr>
                        <tr v-for="item in json">
                            <td>{{item.id}}</td>
                            <td>{{item.serialNum}}</td>
                            <td>{{item.cardSizeLiteral}}</td>
                            <td>{{item.productName}}</td>
                            <td>{{item.stdPrice}}</td>
                            <td>{{item.supplyName}}</td>
                            <td>{{item.costDiscount}}</td>
                            <td>{{item.cost}}</td>
                            <td>{{item.total}}</td>
                            <td>{{item.orderSerialNum}}</td>
                            <td>{{item.purchaseStatusLiteral}}</td>
                            <td>{{item.createrName}}</td>
                            <td>{{item.createTime.substring(0,19)}}</td>
                            <td>
                                <a v-if="item.purchaseStatus == 1" href="###" class="bianji am-btn am-btn-link"
                                   style="padding: 0;color: #0e90d2;"
                                   onclick="cardInDisplay('{{item.serialNum}}')">入库</a>
                                <a v-if="item.purchaseStatus == 5" href="###" class="bianji am-btn am-btn-link"
                                   style="padding: 0;color: #0e90d2;" onclick="editDisplay('{{item.serialNum}}')">修改</a>
                                <a v-if="item.purchaseStatus != 2 && item.purchaseStatus != 3" href="###"
                                   class="bianji am-btn am-btn-link" style="padding: 0;color: #0e90d2;"
                                   onclick="cancel({{item.id}},{{item.purchaseStatus}})">取消</a>
                            </td>
                        </tr>
                    </table>

                    <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

                    <hr/>
                    <div class="am-cf">
                        共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="javascript:void(0);" onclick="reloadPage('{{page.dq-1}}');">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                <li><a href="javascript:void(0);" onclick="reloadPage('{{page.dq+1}}');">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" id="gotoPage"
                                           style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num"
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
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">供应商：</label><em>*</em>
                            <select style="width: 300px;float: left;" name="supplyId" id="supplyId"></select>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">流量套餐：</label><em>*</em>
                            <select style="width: 300px;float: left;" name="productId" id="productId"></select>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">物理大小：</label><em>*</em>
                            <select style="width: 300px;float: left;" name="cardSize" id="cardSize">
                                <c:forEach items="${cardSizeList}" var="cs">
                                    <option value="${cs.value}">${cs}</option>
                                </c:forEach>
                            </select>
                            <span class="msg_type"></span>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">成本折扣：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="costDiscount" id="costDiscount">
                                <span class="msg_name"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">采购数量：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="total" id="total">
                                <span class="msg_name"></span>
                            </div>
                        </div>
                    </form>
                    <div style="clear:both">
                        <button class="am-btn am-btn-warning" id="Add"
                                style="width: 120px;margin: auto;margin-left: 176px;background-color: #0e90d2; border-color: #0e90d2;"
                                onclick="insertPurchase()">提交
                        </button>
                        <button class="am-btn am-btn-default" style="width: 120px;" id="close">返回</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<!--//编辑-->
<div class="tab_wu" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" id="upaa" type="post">
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">供应商：</label><em>*</em>
            <select style="width: 300px;float: left;" name="supplyIdE" id="supplyIdE"></select>
            <input type="hidden" id="purchaseIdE">
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">流量套餐：</label><em>*</em>
            <select style="width: 300px;float: left;" name="productIdE" id="productIdE"></select>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">物理大小：</label><em>*</em>
            <select style="width: 300px;float: left;" name="cardSizeE" id="cardSizeE">
                <c:forEach items="${cardSizeList}" var="cs">
                    <option value="${cs.value}">${cs}</option>
                </c:forEach>
            </select>
            <span class="msg_type"></span>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">成本折扣：</label><em>*</em>
            <div style="width: 300px;float: left;">
                <input type="text" name="costDiscountE" id="costDiscountE">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">采购数量：</label><em>*</em>
            <div style="width: 300px;float: left;">
                <input type="text" name="totalE" id="totalE">
                <span class="msg_name"></span>
            </div>
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" id="update" style="width: 120px;margin: auto;margin-left: 176px;"
                onclick="updatePurchase()">修改
        </button>
        <button class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>
</div>

<!--//入库-->
<div class="tab_cardIn" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">入库</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal">
        <div class="am-form-group" style=" margin-top: 10px;">
            <label class="am-u-sm-2 am-form-label">采购单编号：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="purchaseSerialNumI" id="purchaseSerialNumI" disabled="disabled">
                <input type="hidden" id="purchaseIdI">
                <span class="msg_name"></span>
            </div>
        </div>

        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">供应商：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="supplyIdI" id="supplyIdI" disabled="disabled">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">采购状态：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="purchaseStatusLiteralI" id="purchaseStatusLiteralI" disabled="disabled">
                <input type="hidden" id="purchaseStatusI">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">流量套餐：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="productIdI" id="productIdI" disabled="disabled">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">物理大小：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="cardSizeI" id="cardSizeI" disabled="disabled">
                <span class="msg_name"></span>
            </div>
            <span class="msg_type"></span>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">成本折扣：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="costDiscountI" id="costDiscountI">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">采购数量：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="totalI" id="totalI" disabled="disabled">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">卡信息文件：</label><em>*</em>
            <div style="width: 300px;float: left;">
                <input type="file" name="importExcelI" id="importExcelI"
                       accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <a href="/static/upload/inputCard.xlsx"  style="margin-left: 120px">卡信息模板</a>
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" id="update" style="width: 120px;margin: auto;margin-left: 176px;"
                onclick="insertInReceipt()">提交
        </button>
        <button class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>
</div>
<script>

    $("#close").click(function () {
        $("#table2").removeClass("am-active");
        $("#tab2").removeClass("am-active");
        $("#tab2").removeClass("am-in");
        $("#table1").addClass("am-active");
        $("#tab1").addClass("am-active");
        $("#tab1").addClass("am-in");
    })
    $(function () {
        initPage();
    })

    var dataAll = {
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    function initPage() {
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/purchase/list?pageNumber=1&pageSize=10",
            type: 'POST',
            async: false,
            data: {},
            dataType: "json",
            error: function () {
                $(this).addClass("done");
            },
            success: function (data) {
                dataAll.json = data.list;
                dataAll.page.ts = data.total;
                dataAll.page.dq = data.pageNum;
                dataAll.page.all = data.pages;
            }
        });
        var vm = new Vue({
            el: "#content",
            data: dataAll
        });
    }


    function reloadPage(pageNum) {
        if (pageNum == 0) {
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }

        var createrName = $("#createrName").val();
        if (createrName != null) {
            createrName = createrName.trim();
        }
        var serialNum = $("#serialNum").val();
        if (serialNum != null) {
            serialNum = serialNum.trim();
        }
        var orderSerialNum = $("#orderSerialNum").val();
        if (orderSerialNum != null) {
            orderSerialNum = orderSerialNum.trim();
        }
        var purchaseStatus = $("#purchaseStatus").val();
        var loadingIndex = layer.open({type: 3});
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/purchase/list.do?pageNumber=" + pageNum + "&pageSize=10",
            type: 'POST',
            dataType: "json",
            async: false,
            data: {
                orderSerialNum: orderSerialNum,
                createrName: createrName,
                serialNum: serialNum,
                purchaseStatus: purchaseStatus
            },
            error: function () {
            },
            success: function (data) {
                if (data.list.length > 0) {
                    $(".sj").hide();
                    layer.close(loadingIndex);
                    dataAll.json = data.list;
                    dataAll.page.ts = data.total;
                    dataAll.page.dq = data.pageNum;
                    dataAll.page.all = data.pages;
                } else {
                    layer.close(loadingIndex);
                    dataAll.json = data.list;
                    dataAll.page.ts = 0;
                    dataAll.page.dq = 0;
                    dataAll.page.all = 0;
                    $(".sj").show();
                }
            }
        });
    }
    function gotoPage() {
        var pageNum = $("#gotoPage").val();
        if (pageNum == '') {
            layer.tips("请输入页数！", "#gotoPage", {tips: 3});
            return;
        }
        reloadPage(pageNum);
    }
</script>
<script>
    $(".msg_name").css("color", "red");
    $(".msg_type").css("color", "red");

    //查询供应商
    getSupply();
    //查询流量套餐
    getProduct();

    function getSupply() {
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/purchase/getSupply.do",
            type: "post",
            dataType: "json",
            success: function (obj) {
                $("#supplyId").empty();
                for (var i in obj) {
                    $("#supplyId").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
                }
                $("#supplyIdE").empty();
                for (var i in obj) {
                    $("#supplyIdE").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
                }
            }
        });
    }

    function getProduct() {
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/purchase/getProduct.do",
            type: "post",
            dataType: "json",
            success: function (obj) {
                $("#productId").empty();
                for (var i in obj) {
                    $("#productId").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
                }
                $("#productIdE").empty();
                for (var i in obj) {
                    $("#productIdE").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
                }
            }
        });
    }

    //添加采购单
    function insertPurchase() {
        var costDiscount = $("#costDiscount").val();
        var total = $("#total").val();
        var cardSize = $("#cardSize").val();
        var supplyId = $("#supplyId").val();
        var productId = $("#productId").val();

        var count = /\b0(\.\d{1,2})\b/;
        if (!count.test(costDiscount)) {
            layer.tips("请输入0到1之间的两位小数包含1", "#costDiscount", 1);
            return;
        }
        if ($("#costDiscount").val().trim() == "") {
            layer.tips("成本折扣不能为空", "#costDiscount", 1);
            return;
        } else if ($("#total").val() == "") {
            layer.tips("采购数量不能为空", "#total", 1);
            return;
        } else {
            var loadingIndex = layer.open({type: 3});
            $.ajax({
                url: "${pageContext.request.contextPath }/iot/purchase/addPurchase.do",
                type: "post",
                data: {
                    supplyId: supplyId,
                    flowProductId: productId,
                    costDiscount: costDiscount,
                    total: total,
                    cardSize: cardSize,
                },
                dataType: "json",
                success: function (data) {
                    layer.close(loadingIndex);
                    if (data) {
                        layer.msg("添加成功")
                        reloadPage(1);
                    } else {
                        alert("添加失败！");
                    }
                }
            });
        }
    }
    //修改采购单
    function updatePurchase() {
        var id = $("#purchaseIdE").val();
        var costDiscount = $("#costDiscountE").val();
        var total = $("#totalE").val();
        var cardSize = $("#cardSizeE").val();
        var supplyId = $("#supplyIdE").val();
        var productId = $("#productIdE").val();

        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/purchase/update.do",
            type: 'POST',
            async: false,
            data: {
                id: id,
                supplyId: supplyId,
                flowProductId: productId,
                costDiscount: costDiscount,
                total: total,
                cardSize: cardSize,
            },
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                layer.close(index);
                if (data.success) {
                    reloadPage(dataAll.page.dq);
                    layer.msg(data.message, {icon: 1});
                    hides();
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }

    //取消采购单
    function cancel(purchaseId, purchaseStatus) {
        layer.confirm('你确定要取消这笔采购单吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "${pageContext.request.contextPath }/iot/purchase/cancel.do",
                type: 'POST',
                async: false,
                data: {
                    purchaseId: purchaseId,
                    purchaseStatus: purchaseStatus
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message, {icon: 1});
                        reloadPage(dataAll.page.dq);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        }, function () {
            layer.closeAll();
        });
    }

    //回显采购单信息
    function cardInDisplay(serialNum) {
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/purchase/getIotPurchaseByNum",
            type: "post",
            data: {
                serialNum: serialNum
            },
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var obj = data.object[0];
                    $("#purchaseIdI").val(obj.id);
                    $("#purchaseSerialNumI").val(obj.serialNum);
                    $("#purchaseStatusI").val(obj.purchaseStatus);
                    $("#purchaseStatusLiteralI").val(obj.purchaseStatusLiteral);
                    $("#supplyIdI").val(obj.supplyName);
                    $("#productIdI").val(obj.productName);
                    $("#cardSizeI").val(obj.cardSizeLiteral);
                    $("#costDiscountI").val(obj.costDiscount);
                    $("#totalI").val(obj.total);
                    $('.admin-content-body').hide();
                    $('.tab_cardIn').show();
                    if (data.object.length == 2) {
                        if (data.object[1].total != null && data.object[1].total != 0) {
                            layer.tips("已入库" + data.object[1].total, "#totalI", {time: 0});
                        }
                    }
                } else {
                    alert(data.message);
                }

            }
        });
    };

    //添加入库单
    function insertInReceipt() {
        var purchaseSerialNum = $("#purchaseSerialNumI").val().trim();
        var purchaseId = $("#purchaseIdI").val().trim();
        var purchaseStatus = $("#purchaseStatusI").val().trim();
        var costDiscount = $("#costDiscountI").val().trim();

        var count = /\b0(\.\d{1,2})\b/;
        var reg = /^[1-9]\d*$/;
        if (!count.test(costDiscount)) {
            layer.tips("请输入0到1之间的两位小数包含1", "#costDiscountI", 1);
            return;
        }
        if (purchaseStatus != 1) {
            alert("该采购单的状态为[" + purchaseStatusLiteral + "],无法执行入库操作！");
            return;
        }
        if (purchaseSerialNum == "") {
            alert("入库单编号不能为空！");
            return;
        }
        if (purchaseId == "") {
            alert("未查询到该入库单!");
            return;
        }
        var loadingIndex = layer.open({type: 3});
        var form = new FormData();
        var files = $('#importExcelI').prop('files');
        form.append("importExcel", files[0]);
        form.append("purchaseId", purchaseId);
        form.append("costDiscount", costDiscount);
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/cardIn/addInReceipt.do",
            type: "post",
            data: form,
            contentType: false,
            processData: false,
            dataType: "json",
            success: function (data) {
                layer.closeAll();
                if (data.success) {
                    alert("恭喜你添加成功！");
                    reloadPage(1);
                } else {
                    alert("出错了！错误信息:" + data.message);
                    //location.reload();
                }
            }
        });
    }


    //编辑回显
    function editDisplay(serialNum) {
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/purchase/toUpdate.do",
            type: "post",
            data: {
                serialNum: serialNum
            },
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    var obj = data.object;
                    $("#purchaseIdE").val(obj.id);
                    $("[name='supplyIdE']").val(obj.supplyId);
                    $("[name='productIdE']").val(obj.flowProductId);
                    $("[name='cardSizeE']").val(obj.cardSize);
                    $("[name='costDiscountE']").val(obj.costDiscount);
                    $("[name='totalE']").val(obj.total);
                    if (obj.subOrderId != null) {
                        $("#productIdE").attr("disabled", true);
                        $("#cardSizeE").attr("disabled", true);
                        $("#costDiscountE").attr("disabled", true);
                    } else {
                        $("#productIdE").attr("disabled", false);
                        $("#cardSizeE").attr("disabled", false);
                        $("#costDiscountE").attr("disabled", false);
                    }
                    $('.admin-content-body').hide();
                    $('.tab_cardIn').hide();
                    $('.tab_wu').show();
                } else {
                    layer.msg(data.message, {icon: 2});
                }

            }
        });
    };


    function hides() {
        $('.admin-content-body').show();
        $('.tab_wu').hide();
        $('.tab_cardIn').hide();
        layer.closeAll();
    }
</script>
</body>
</html>

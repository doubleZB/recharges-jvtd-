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
<body>
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
    label {
        display: inline-block;
        margin-bottom: 5px;
        font-weight: 100!important;
    }

</style>
<!-- content start -->
<div class="admin-content" id="content" v-cloak style="padding-top: 20px;">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">卡信息管理</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top:0;">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input type="text" name="iccid" id="iccid" class="am-form-field" placeholder="ICCID"
                                   style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="msisdn" id="msisdn" class="am-form-field" placeholder="MSISDN"
                                   style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="orderSerialNum" id="orderSerialNum" class="am-form-field"
                                   placeholder="订单编号" style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="purchaseSerialNum" id="purchaseSerialNum" class="am-form-field"
                                   placeholder="采库单编号" style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="inReceiptSerialNum" id="inReceiptSerialNum" class="am-form-field"
                                   placeholder="入库单编号" style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="outReceiptSerialNum" id="outReceiptSerialNum" class="am-form-field"
                                   placeholder="出库单编号" style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="saleStatus" id="CardTimeType"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="时间类型">
                                <c:forEach items="${CardTimeTypeList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group demo1" style="margin-top: 20px">
                            <input class="am-form-field" name="orderTimeOne" id="createDate" placeholder="开始时间"
                                   style="width: 150px;float: left;" readonly required>
                        </div>
                        <div class="am-form-group demo1" style="margin-top: 20px">
                            <input class="am-form-field" name="orderTimeOne" id="endDate" placeholder="结束时间"
                                   style="width: 150px;float: left;" readonly required>
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="outReceiptSerialNum" id="userName" class="am-form-field"
                                   placeholder="客户" style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="operator" id="supplierName"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="供应商">
                                <option value="">供应商</option>
                                <c:forEach items="${supplyList}" var="pur">
                                    <option value="${pur.id}">${pur.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="saleStatus" id="saleStatus"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="销售状态">
                                <option value="">销售状态</option>
                                <c:forEach items="${saleStatusList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="status" id="status"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="采购单状态">
                                <option value="">卡状态</option>
                                <c:forEach items="${cardStatusList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="operator" id="operator"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="运营商">
                                <option value="">运营商</option>
                                <c:forEach items="${operatorList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="operator" id="product"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="流量套餐">
                                <option value="">流量套餐</option>
                                <c:forEach items="${productList}" var="pur">
                                    <option value="${pur.id}">${pur.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="operator" id="cardSizeS"
                                    style="width: 150px;float: left;padding: .5em;" placeholder="物理大小">
                                <option value="">物理大小</option>
                                <c:forEach items="${cardSizeList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="outReceiptSerialNum" id="costCount" class="am-form-field"
                                   placeholder="成本折扣" style="width: 150px;float: left;"
                                   <%--onkeyup="this.value=this.value.replace(/\b0(\.\d{1,2})\b/,'')"--%>
                                    onblur="check(event)"
                            />
                        </div>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;"
                                onclick="reloadPage(1)">查询
                        </button>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <tr>
                            <th>序号</th>
                            <th>ICCID</th>
                            <th>MSISDN</th>
                            <th>流量套餐</th>
                            <th>供应商</th>
                            <th>卡售价</th>
                            <th>卡状态</th>
                            <th>物理大小</th>
                            <th>客户名称</th>
                            <th>销售状态</th>
                            <th>成本折扣</th>
                            <th>入库时间</th>
                            <th>操作</th>
                        </tr>
                        <tr v-for="item in json">
                            <td>{{item.id}}</td>
                            <td>
                                <a href="javascript:void(0);" onclick="openDetail({{item.id}})">{{item.iccid}}</a></td>
                            <td>{{item.msisdn}}</td>
                            <td>{{item.productName}}</td>
                            <td>{{item.supplyName}}</td>
                            <td>{{item.price}}</td>
                            <td>{{item.statusLiteral}}</td>
                            <td>{{item.cardSizeLiteral}}</td>
                            <td>{{item.userName}}</td>
                            <td>{{item.saleStatusLiteral}}</td>
                            <td>{{item.costDiscount}}</td>
                            <td>{{item.createTime.substring(0,19)}}</td>
                            <td><a href="javascript:void(0);" onclick="openDetail({{item.id}})">查看</a></td>
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
                    <div style="clear:both">
                        <button class="am-btn am-btn-warning" id="Add"
                                style="width: 120px;margin: auto;margin-left: 176px;" onclick="insertInReceipt()">提交
                        </button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<div class="am-popup" id="my-popup"  style="display:none">
    <div class="am-popup-inner">
        <div class="am-popup-hd">
            <h4 class="am-popup-title">卡详情</h4>
            <span data-am-modal-close
                  class="am-close">&times;</span>
        </div>
        <div class="am-popup-bd">
            <div data-am-widget="list_news" class="am-list-news am-list-news-default">
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">ICCID:</label>
                    <span id="IccId"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">MSISDN:</label>
                    <span id="MSISDN"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">运营商:</label>
                    <span id="operatorShow"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">供应商:</label>
                    <span id="supplyName"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">流量套餐:</label>
                    <span id="productName"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">卡状态:</label>
                    <span id="cardStatus"></span>
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
                    <label style="width: 80px;text-align: right">销售状态:</label>
                    <span id="saleStatusShow"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">激活时间:</label>
                    <span id="activeTime"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">物理大小:</label>
                    <span id="cardSize"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">入库时间:</label>
                    <span id="inTime"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">出库时间:</label>
                    <span id="outTime"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">采购价:</label>
                    <span id="purchasePrice"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">采购折扣:</label>
                    <span id="purchaseCost"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">采购单编号:</label>
                    <span id="purchaseNum"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">售价:</label>
                    <span id="salePrice"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">售价折扣:</label>
                    <span id="saleCost"></span>
                </div>
                <div class="am-list-news-bd">
                    <label style="width: 80px;text-align: right">出库单编号:</label>
                    <span id="outNum"></span>
                </div>

            </div>
        </div>
    </div>
</div>

<script>
    laydate({
        elem: "#createDate"
    });
    laydate({
        elem: "#endDate"
    });
    $(function () {
        initPage();
    })

    function check(e) {
        var s = e.target.value;
//        console.log(s.length);
        var reg = /\b0(\.\d{1,2})\b/;
            if(!reg.test(s)){
                e.target.value = '';
            }
    }

    function openDetail(id) {
        $.ajax({
            url:"${pageContext.request.contextPath }/iot/cardCenter/getCardById.do",
            type:"post",
            data:{id:id},
            dataType:"json",
            success:function (obj){
                if(obj.iccid!=null){$("#IccId").html(obj.iccid)}
                if(obj.msisdn!=null){$("#MSISDN").html(obj.msisdn)}
                if(obj.operatorLiteral!=null){$("#operatorShow").html(obj.operatorLiteral)}
                if(obj.supplyName!=null){$("#supplyName").html(obj.supplyName)}
                if(obj.productName!=null){$("#productName").html(obj.productName)}
                if(obj.statusLiteral!=null){$("#cardStatus").html(obj.statusLiteral)}
                if(obj.saleStatusLiteral!=null){$("#saleStatusShow").html(obj.saleStatusLiteral)}
                if(obj.activeTime!=null){$("#activeTime").html(obj.activeTime)}
                if(obj.cardSizeLiteral!=null){$("#cardSize").html(obj.cardSizeLiteral)}
                if(obj.createTime!=null){$("#inTime").html(obj.createTime)}
                if(obj.outTime!=null){$("#outTime").html(obj.outTime)}
                if(obj.price!=null){$("#salePrice").html(obj.price)}
                if(obj.priceDiscount!=null){$("#saleCost").html(obj.priceDiscount)}
                if(obj.outReceiptSerialNum!=null){$("#outNum").html(obj.outReceiptSerialNum)}
                if(obj.purchaseSerialNum!=null){$("#purchaseNum").html(obj.purchaseSerialNum)}
                $("#usedFlow").html(obj.usedFlow)
                $("#residualFlow").html(obj.leftFlow)
                $("#totalFlow").html(obj.totalFlow)
                if(obj.cost!=null){$("#purchasePrice").html(obj.cost)}
                if(obj.costDiscount!=null){$("#purchaseCost").html(obj.costDiscount)}
                $('#my-popup').modal();
                usages(obj.iccid,obj.msisdn);
            }
        });
    }

    function usages(iccid,msisdn) {
        $.ajax({
            url:"${pageContext.request.contextPath }/iot/cardCenter/usages.do",
            type:"post",
            data:{
                iccid:iccid,
                msisdn:msisdn
            },
            dataType:"json",
            success:function (obj){
                if(obj.success){
                    if(obj.object.statusCode == '1000'){
                        $("#usedFlow").html(obj.object.usedFlow);
                        $("#residualFlow").html(obj.object.restFlow);
                        $("#totalFlow").html(obj.object.totalFlow);
                    }
                }
            }
        });
    }

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
            url: "${pageContext.request.contextPath}/iot/cardCenter/list?pageNumber=1&pageSize=10",
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
        var createDate = $("#createDate").val();
        var endDate = $("#endDate").val();
        var iccid = $("#iccid").val().trim();
        var msisdn = $("#msisdn").val().trim();
        var status = $("#status").val().trim();
        var operator = $("#operator").val().trim();
        var saleStatus = $("#saleStatus").val().trim();
        var purchaseSerialNum = $("#purchaseSerialNum").val().trim();
        var inReceiptSerialNum = $("#inReceiptSerialNum").val().trim();
        var outReceiptSerialNum = $("#outReceiptSerialNum").val().trim();
        var orderSerialNum = $("#orderSerialNum").val().trim();
        var flowProductId = $("#product").val();
        var userName = $("#userName").val();
        var supplyId = $("#supplierName").val();
        var cardTimeType = $("#CardTimeType").val();
        var cardSize = $("#cardSizeS").val();
        var costDiscount = $("#costCount").val();
        if(createDate!=""){
            if(endDate==""){
                layer.msg("请选择结束日期！");
                return;
            }else{
                createDate = createDate+" "+"00:00:00";
                endDate = endDate+" "+"23:59:59";
            }
        }
        var loadingIndex = layer.open({type: 3});
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/cardCenter/list.do?pageNumber=" + pageNum + "&pageSize=10",
            type: 'POST',
            dataType: "json",
            async: false,
            data: {
                iccid: iccid,
                msisdn: msisdn,
                operator: operator,
                status: status,
                saleStatus: saleStatus,
                inReceiptSerialNum: inReceiptSerialNum,
                outReceiptSerialNum: outReceiptSerialNum,
                purchaseSerialNum: purchaseSerialNum,
                orderSerialNum:orderSerialNum,
                createDate: createDate,
                endDate: endDate,
                userName:userName,
                flowProductId:flowProductId,
                supplyId:supplyId,
                cardTimeType:cardTimeType,
                cardSize:cardSize,
                costDiscount:costDiscount
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

</body>
</html>

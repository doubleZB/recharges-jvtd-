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

</style>
<!-- content start -->
<div class="admin-content" id="content" v-cloak style="padding-top: 20px;">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">物联网卡库存查询</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top:0;">
                    <form class="am-form-inline" role="form">
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
                            <select style="width: 150px;float: left;" class="am-form-field" id="iotProductId">
                                <option value="">选择流量套餐</option>
                                <c:forEach items="${iotProductList}" var="ope">
                                    <option value="${ope.id}">${ope.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;"
                                onclick="reloadPage(1)">查询
                        </button>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <tr>
                            <th>运营商</th>
                            <th>物理大小</th>
                            <th>流量套餐</th>
                            <th>库存量</th>
                            <th>操作</th>
                        </tr>
                        <tr v-for="item in json">
                            <td>{{item.operatorLiteral}}</td>
                            <td>{{item.cardSizeLiteral}}</td>
                            <td>{{item.productName}}</td>
                            <td>{{item.total}}</td>
                            <td><a href="javascript:void(0);" onclick="openDetail({{item.id}})">采购</a></td>
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

<div class="tab_wu" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">采购</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" id="upaa" type="post">
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">供应商：</label><em>*</em>
            <select style="width: 300px;float: left;" id="supplyIdE">
                <option value="">选择供应商</option>
                <c:forEach items="${iotSupplyList}" var="ope">
                    <option value="${ope.id}">${ope.name}</option>
                </c:forEach>
            </select>
            <input type="hidden" id="purchaseIdE" >
        </div>
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">流量套餐：</label><em>*</em>
            <select style="width: 300px;float: left;" id="productIdE" disabled>
                <option value="">选择流量套餐</option>
                <c:forEach items="${iotProductList}" var="ope">
                    <option value="${ope.id}">${ope.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">物理大小：</label><em>*</em>
            <select style="width: 300px;float: left;" name="cardSizeE" id="cardSizeE"  >
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
        <button class="am-btn am-btn-warning" id="update" style="width: 120px;margin: auto;margin-left: 176px;" onclick="insertPurchase()">提交</button>
        <button  class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>
</div>

<script>
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
            url: "${pageContext.request.contextPath}/iot/cardStock/list?pageNumber=1&pageSize=10",
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
        var operator = $("#operator").val().trim();
        var iotProductId = $("#iotProductId").val().trim();
        var loadingIndex = layer.open({type: 3});
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/cardStock/list.do?pageNumber=" + pageNum + "&pageSize=10",
            type: 'POST',
            dataType: "json",
            async: false,
            data: {
                operator: operator,
                flowProductId:iotProductId
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

    function openDetail(id){
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url:"${pageContext.request.contextPath }/iot/cardStock/getCard.do",
            type:"post",
            data:{
                id:id
            },
            dataType:"json",
            success:function(data){
                $("#supplyIdE").val(data[0].supplyId);
                $("#productIdE").val(data[0].flowProductId);
                $("#cardSizeE").val(data[0].cardSize);
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

    function insertPurchase(){
        var costDiscount=$("#costDiscountE").val();
        var total=$("#totalE").val();
        var cardSize = $("#cardSizeE").val();
        var supplyId = $("#supplyIdE").val();
        var productId = $("#productIdE").val();

        var count = /\b0(\.\d{1,2})\b/;
        if(!count.test(costDiscount)){
            layer.tips("请输入0到1之间的两位小数包含1", "#costDiscountE", 1);
            return;
        }
        if( $("#costDiscountE").val().trim()==""  ){
            layer.tips("成本折扣不能为空", "#costDiscountE", 1);
            return;
        }else if( $("#totalE").val()==""){
            layer.tips("采购数量不能为空", "#totalE", 1);
            return;
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/iot/purchase/addPurchase.do",
                type:"post",
                data:{
                    supplyId:supplyId,
                    flowProductId:productId,
                    costDiscount:costDiscount,
                    total:total,
                    cardSize:cardSize,
                },
                dataType:"json",
                success:function (data){
                    layer.close(loadingIndex);
                    if(data){
                        layer.msg("添加成功")
                        reloadPage(1);
                    }else{
                        alert("添加失败！");
                    }
                }
            });
        }
    }
</script>

</body>
</html>

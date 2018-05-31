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
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<body style="height: 100%">
<style>
    .am-form-label {
        font-weight: normal;
        width: 120px;
        padding-left: 0;
    }
    .am-table>caption+thead>tr:first-child>td, .am-table>caption+thead>tr:first-child>th, .am- table>colgroup+thead>tr:first-child>td, .am-table>colgroup+thead>tr:first-child>th, .am- table>thead:first-child>tr:first-child>td, .am-table>thead:first-child>tr:first-child>th {
        border-top: 0;
        font-size: 14px;
    }
    .am-table>tbody>tr>td, .am-table>tbody>tr>th, .am-table>tfoot>tr>td, .am-table>tfoot>tr>th, .am- table>thead>tr>td, .am-table>thead>tr>th {
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
                <li class="am-active"><a href="#tab1">出库单管理</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top:0;">
                    <form class="am-form-inline" role="form">
                        <div>
                        <div class="am-form-group">
                            <input  type="text" name="serialNum" id="serialNum" class="am-form-field" placeholder="出库单编号" style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <select  class="select1" name="cardSize" id="cardSize" style="width: 150px;float: left;padding: .5em;" placeholder="物理大小">
                                <option value="">物理大小</option>
                                <c:forEach items="${cardSizeList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="operator" id="operator"style="width: 150px;float: left;padding: .5em;" placeholder="运营商">
                                <option value="">运营商</option>
                                <c:forEach items="${operatorList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1" id="product" style="width: 150px;float: left;padding: .5em;" placeholder="流量套餐">
                                <option value="">流量套餐</option>
                                <c:forEach items="${productList}" var="pur">
                                    <option value="${pur.id}">${pur.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select class="select1"  id="outReceiptStatus" style="width: 150px;float: left;padding: .5em;" placeholder="出库单状态">
                                <option value="">状态</option>
                                <c:forEach items="${outReStatusList}" var="pur">
                                    <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        </div>
                        <div>
                        <div class="am-form-group demo1" style="margin-top: 20px">
                            <input class="am-form-field" name="orderTimeOne" id="beginTime" placeholder="开始时间"
                                   style="width: 150px;float: left;" readonly required>
                        </div>
                        <div class="am-form-group demo1" style="margin-top: 20px">
                            <input class="am-form-field" name="orderTimeOne" id="endTime" placeholder="结束时间"
                                   style="width: 150px;float: left;" readonly required>
                        </div>
                        <div class="am-form-group demo1" style="margin-top: 20px">
                            <input type="text" class="am-form-field"  id="userName" placeholder="客户名称"style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input  type="text" name="orderSerialNum" id="orderSerialNum" class="am-form-field" placeholder="订单编号" style="width: 150px;float: left;">
                        </div>
                        <%--<div class="am-form-group">--%>
                            <%--<input  type="hidden" name="subOrderSerialNum" id="subOrderSerialNum" class="am-form-field" placeholder="子订单编号" style="width: 200px;float: left;">--%>
                        <%--</div>--%>
                        <div class="am-form-group">
                                <input  type="text" name="createrName" id="createrName" class="am-form-field" placeholder="出库人" style="width: 150px;float: left;">
                        </div>

                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;" onclick="reloadPage(1)">查询</button>
                        </div>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <tr>
                            <th>序号</th>
                            <th>出库单编号</th>
                            <th>订单编号</th>
                            <th>客户</th>
                            <th>卡数量</th>
                            <th>流量套餐</th>
                            <th>物理大小</th>
                            <th>出库人</th>
                            <th>出库时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        <tr v-for="item in json">
                            <td>{{item.id}}</td>
                            <td>{{item.serialNum}}</td>
                            <td>{{item.orderSerialNum}}</td>
                            <td>{{item.customerName}}</td>
                            <td>{{item.total}}</td>
                            <td>{{item.productName}}</td>
                            <td>{{item.cardSizeLiteral}}</td>
                            <td>{{item.createrName}}</td>
                            <td>{{item.createTime.substring(0,19)}}</td>
                            <td>{{item.outReceiptStatusLiteral}}</td>
                            <td>
                                <a v-if="item.outReceiptStatus != 2" onclick="exportOutreceipt({{item.id}})" class="am-btn am-btn-link"style="padding: 0;color: #0e90d2;">卡明细</a>
                                <a v-if="item.outReceiptStatus == 0" onclick="picked({{item.id}})" class="am-btn am-btn-link"style="padding: 0;color: #0e90d2;">配货</a>
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
                                <li><a href="javascript:void(0);"  onclick="reloadPage('{{page.dq+1}}');">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();"  style="padding: .5rem .4rem;">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<script>
    laydate({
        elem: "#beginTime"
    });
    laydate({
        elem: "#endTime"
    });

    $(function(){
        initPage ();
    })
   
    var dataAll = {
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    function initPage (){
    $.ajax({
        url: "${pageContext.request.contextPath}/iot/outReceipt/list?pageNumber=1&pageSize=10",
        type:'POST',
        async:false,
        data:{},
        dataType:"json",
        error:function(){
            $(this).addClass("done");
        },
        success: function(data){
            dataAll.json = data.list;
            dataAll.page.ts = data.total;
            dataAll.page.dq = data.pageNum;
            dataAll.page.all = data.pages;
        }
    });
    var vm = new Vue({
        el: "#content",
        data:dataAll
    });
    }


    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var createrName=$("#createrName").val();
        if(createrName != null){
        	createrName = createrName.trim();
        }
        var serialNum=$("#serialNum").val();
        if(serialNum != null){
        	serialNum = serialNum.trim();
        }
        var orderSerialNum=$("#orderSerialNum").val();
        if(orderSerialNum != null){
        	orderSerialNum = orderSerialNum.trim();
        }
//        var subOrderSerialNum=$("#subOrderSerialNum").val();
//        if(subOrderSerialNum != null){
//        	subOrderSerialNum = subOrderSerialNum.trim();
//        }
        var cardSize = $("#cardSize").val();
        var operator = $("#operator").val();
        var product = $("#product").val();
        var outReceiptStatus = $("#outReceiptStatus").val();
        var userName = $("#userName").val();
        var beginTime=$("#beginTime").val();
        var endTime = $("#endTime").val();
        if(beginTime!=""){
            if(endTime==""){
                layer.msg("请选择结束日期！");
                return;
            }else{
                beginTime = beginTime+" "+"00:00:00";
                endTime = endTime+" "+"23:59:59";
            }
        }
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/outReceipt/list.do?pageNumber="+pageNum+"&pageSize=10",
            type:'POST',
            dataType:"json",
            async:false,
            data:{
            	createrName:createrName,
            	serialNum:serialNum,
            	orderSerialNum:orderSerialNum,
                customerName:userName,
                beginTime:beginTime,
                endTime:endTime,
                cardSize:cardSize,
                productId:product,
                outReceiptStatus:outReceiptStatus,
                operator:operator
            },
            error:function(){
            },
            success: function(data){
                if(data.list.length>0){
                    $(".sj").hide();
                    layer.close(loadingIndex);
                    dataAll.json = data.list;
                    dataAll.page.ts = data.total;
                    dataAll.page.dq = data.pageNum;
                    dataAll.page.all = data.pages;
                }else{
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
    function gotoPage(){
        var pageNum = $("#gotoPage").val();
        if(pageNum==''){
            layer.tips("请输入页数！","#gotoPage",{tips: 3});
            return;
        }
        reloadPage(pageNum);
    }
    function exportOutreceipt(outReceiptId){
        window.open("/iot/outReceipt/exportOutReceipt?outReceiptId="+outReceiptId)
    }
    
    function picked(id) {
        layer.confirm("确定已配货吗？", {
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/iot/outReceipt/picked",
                type: 'POST',
                async: false,
                data: {
                    id:id
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message,{icon:1});
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
</script>
<script>
    $(".msg_name").css("color","red");
    $(".msg_type").css("color","red");
   
</script>
</body>
</html>

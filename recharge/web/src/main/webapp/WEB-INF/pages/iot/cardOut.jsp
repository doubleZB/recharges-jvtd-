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
<body>
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
                <li class="am-active"><a href="#tab1">待出库卡管理</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top:0;">
                    <form class="am-form-inline" role="form">
                         <div class="am-form-group">
	                        <input  type="text" name="parentSerialNum" id="parentSerialNum" class="am-form-field" placeholder="订单编号" style="width: 170px;float: left;" onblur="toDisplay()">
	                     </div>
	                     <div class="am-form-group">
	                        <input  type="text" name="subSerialNum" id="subSerialNum" class="am-form-field" placeholder="子订单编号" style="width: 170px;float: left;" onblur="toDisplay()">
	                        <input type="hidden" id="subOrderId" >
	                     </div>
	                     <div class="am-form-group">
	                        <input  type="text" name="total" id="total" class="am-form-field" placeholder="订购数量" style="width: 150px;float: left;" disabled="disabled">
	                     </div>
	                     <div class="am-form-group">
	                        <input  type="text" name="productIdD" id="productIdD" class="am-form-field" placeholder="流量套餐" style="width: 150px;float: left;" disabled="disabled">
	                     </div>
	                     <div class="am-form-group">
	                        <input  type="text" name="cardSizeD" id="cardSizeD" class="am-form-field" placeholder="物理大小" style="width: 150px;float: left;" disabled="disabled">
	                     </div>
	                     <div class="am-form-group">
	                        <input  type="text" name="statusLiteralD" id="statusLiteralD" class="am-form-field" placeholder="订单状态" style="width: 150px;float: left;" disabled="disabled">
	                     </div>
                    </form>
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <select  class="select1" name="operator" id="operator" style="width: 150px;float: left;padding: .5em;" placeholder="运营商">
                                <option value="">运营商</option>
                                <c:forEach items="${operatorList}" var="pur">
                                        <option value="${pur.value}">${pur}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <select  class="select1" name="productId" id="productId" style="width: 150px;float: left;padding: .5em;" placeholder="流量套餐">
                            </select>
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
                            <input  type="text" name="iccid" id="iccid" class="am-form-field" placeholder="ICCID" style="width: 150px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input  type="text" name="msisdn" id="msisdn" class="am-form-field" placeholder="MSISDN" style="width: 150px;float: left;">
                        </div>
                        <input type="hidden" id="editDataSubData" value="{{ checkID | json }}">
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;" onclick="insertOutReceipt()">出库</button>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;" onclick="reloadPage(1)">查询</button>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <tr>
                            <th>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" class="checkall" @click="checked"> 序号
                                </label>
                            </th>
                            <th>ICCID</th>
                            <th>MSISDN</th>
                            <th>运营商</th>
                            <th>流量套餐</th>
                            <th>物理大小</th>
                            <th>成本折扣</th>
                            <th>供应商</th>
                            <th>卡状态</th>
                            <th>销售状态</th>
                            <th>入库时间</th>
                            <th>操作</th>
                        </tr>
                        <tr v-for="item in json">
                            <td>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" value="{{item.id}}" name="checkbox" v-model="checkID"  @click="checked1()">
                                    {{$index+1}}
                                </label>
                            </td>
                            <td>{{item.iccid}}</td>
                            <td>{{item.msisdn}}</td>
                            <td>{{item.operatorLiteral}}</td>
                            <td>{{item.productName}}</td>
                            <td>{{item.cardSizeLiteral}}</td>
                            <td>{{item.costDiscount}}</td>
                            <td>{{item.supplyName}}</td>
                            <td>{{item.statusLiteral}}</td>
                            <td>{{item.saleStatusLiteral}}</td>
                            <td>{{item.createTime.substring(0,19)}}</td>
                            <td><a href="javascript:void(0);" onclick="">查看</a></td>
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
	Array.prototype.unique = function () {
	    this.sort();
	    var re = [this[0]];
	    for (var i = 1; i < this.length; i++) {
	        if (this[i] !== re[re.length - 1]) {
	            re.push(this[i]);
	        }
	    }
	    return re;
	};
    $(function(){
        initPage ();
    })
   
    var dataAll = {
    	checkID: [],
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    
    
    function initPage (){
    $.ajax({
        url: "${pageContext.request.contextPath}/iot/cardOut/list?pageNumber=1&pageSize=10",
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
    var arr = [];
    var vm = new Vue({
        el: "#content",
        data:dataAll,
        methods: {
        	 checked: function () {
                 if ($(".checkall").prop("checked")) {
                     $("input[name='checkbox']").prop("checked", true);
                     for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                         arr.push($("input[name='checkbox']:checked").eq(i).val());
                     }
                     this.checkID = arr.unique();
                 } else {
                     this.checkID = [];
                     arr = [];
                     $("input[name='checkbox']").prop("checked", false);
                 }
             },
             checked1: function () {
                 if ($("input[name='checkbox']:checked").length == this.json.length) {
                     $(".checkall").prop("checked", true);
                 } else {
                     $(".checkall").prop("checked", false);
                 }
             }
        }
    });
    }


    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var iccid=$("#iccid").val().trim();
        var msisdn=$("#msisdn").val().trim();
        var flowProductId=$("#productId").val().trim();
        var cardSize=$("#cardSize").val().trim();
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/cardOut/list.do?pageNumber="+pageNum+"&pageSize=10",
            type:'POST',
            dataType:"json",
            async:false,
            data:{
            	iccid:iccid,
            	msisdn:msisdn,
            	flowProductId:flowProductId,
            	cardSize:cardSize
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
    
    //添加出库单
    function insertOutReceipt(){
    	var subSerialNum = $("#subSerialNum").val().trim();
    	var subOrderId = $("#subOrderId").val().trim();
    	var subData = $("#editDataSubData").val();
    	var iccid = $("#iccid").val().trim();
    	var msisdn = $("#msisdn").val().trim();
    	var operator = $("#operator").val().trim();
    	var productId = $("#productId").val().trim();
    	var cardSize = $("#cardSize").val().trim();
    	var total = $("#total").val().trim();
    	if(dataAll.page.ts == 0){
    		alert("库存不足!");
    		return;
    	}
    	if( subSerialNum ==""  ){
            layer.tips("子订单编号不能为空", "#subSerialNum", 1);
            return;
        }else if( subOrderId==""){
            layer.tips("未查询到该子订单", "#subSerialNum", 1);
            return;
        }else{
        	if(dataAll.page.ts < total){
        		var con=confirm("现有库存"+dataAll.page.ts+"小于订购数量"+total+",如果继续出库现有订单将被拆分"); //在页面上弹出对话框
        		if(con==false){
        			return;
        		}
        	}
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/iot/cardOut/addOutReceipt.do",
                type:"post",
                data:{
                	subOrderId:subOrderId,
                	subData:subData,
                	iccid:iccid,
                	msisdn:msisdn,
                	operator:operator,
                	flowProductId:productId,
                	cardSize:cardSize,
                	updateLimit:total
                },
                dataType:"json",
                success:function (data){
                    layer.close(loadingIndex);
                    if(data.success){
                        alert("出库成功！");
                        reloadPage(1);
                    }else{
                        alert("出错了！错误信息:"+data.message);
                        //location.reload();
                    }
                }
            });
        }
    }
    
    //回显采购单信息
    function toDisplay(){
    	var parentSerialNum = $("#parentSerialNum").val();
    	var subSerialNum = $("#subSerialNum").val();
	   	if( subSerialNum==""  && parentSerialNum=="" ){
	   		 return;
	   	}
        $.ajax({
            url:"${pageContext.request.contextPath }/iot/cardOut/toDisplay.do",
            type:"post",
            data:{
            	subSerialNum:subSerialNum,
            	parentSerialNum:parentSerialNum
            },
            dataType:"json",
            success:function (data){
            	var obj = data.object;
            	if(data.success){
            		$("#subOrderId").val(obj.id);
            		$("#productId").val(obj.flowProductId);
                	$("#cardSize").val(obj.size);
                	$("#total").val(obj.total);
                	
                	$("#parentSerialNum").val(obj.parentSerialNum);
                	$("#subSerialNum").val(obj.serialNum);
                	$("#productIdD").val(obj.productName);
                	$("#cardSizeD").val(obj.cardSizeLiteral);
                	$("#statusLiteralD").val(obj.statusLiteral);
            	}else{
            		alert(data.message);
            	}
            	
            }
        });
    };
    //查询流量套餐
    getProduct();
    function getProduct(){
      	 $.ajax({
      	        url:"${pageContext.request.contextPath }/iot/cardOut/getProduct.do",
      	        type:"post",
      	        dataType:"json",
      	        success:function(obj){
      	        	$("#productId").empty();
      	        	$("#productId").append("<option value=''>流量套餐</option>");
      	            for (var i in obj) {
      	                $("#productId").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
      	            }
      	        }
      	    });
      }
</script>

</body>
</html>

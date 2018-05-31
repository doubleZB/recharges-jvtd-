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
                <li class="am-active"><a href="#tab1">待审核采购单</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top:0;">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input  type="text" name="orderSerialNum" id="orderSerialNum" class="am-form-field" placeholder="关联订单编号" style="width: 170px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input  type="text" name="serialNum" id="serialNum" class="am-form-field" placeholder="采购单编号" style="width: 170px;float: left;">
                        </div>
                         <div class="am-form-group">
                                <input  type="text" name="createrName" id="createrName" class="am-form-field" placeholder="创建人" style="width: 150px;float: left;">
                        </div>

                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;" onclick="reloadPage(1)">查询</button>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <tr>
                            <th>序号</th>
                            <th>采购单编号</th>
                            <th>供应商</th>
                            <th>物理大小</th>
                            <th>流量套餐</th>
                            <th>面值(元)</th>
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
                            <td>{{item.supplyName}}</td>
                            <td>{{item.cardSizeLiteral}}</td>
                            <td>{{item.productName}}</td>
                            <td>{{item.stdPrice}}</td>
                            <td>{{item.costDiscount}}</td>
                            <td>{{item.cost}}</td>
                            <td>{{item.total}}</td>
                            <td>{{item.orderSerialNum}}</td>
                            <td>{{item.purchaseStatusLiteral}}</td>
                            <td>{{item.createrName}}</td>
                            <td>{{item.createTime.substring(0,19)}}</td>
                            <td>
                                <a href="###" class="bianji am-btn am-btn-link" style="padding: 0;" onclick="adopt({{item.id}})">通过</a>
                                <a href="###" class="bianji am-btn am-btn-link" style="padding: 0;" onclick="editDisplay({{item.id}})">驳回</a>
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
<div class="tab_wu">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal">
    		<div class="am-form-group">
             <input type="hidden" name="purchaseIdD" id="purchaseIdD">
             <label  class="am-u-sm-2 am-form-label">采购单编号：</label>
             <div style="width: 300px;float: left;">
                 <input type="text" name="serialNumD" id="serialNumD" disabled="true">
                 <span class="msg_name"></span>
             </div>
         </div>
         <div class="am-form-group">
             <label  class="am-u-sm-2 am-form-label">供应商：</label>
             <select style="width: 300px;float: left;" name="supplyIdD" id="supplyIdD" disabled="true"></select>
         </div>
         <div class="am-form-group">
             <label  class="am-u-sm-2 am-form-label">流量套餐：</label>
             <select style="width: 300px;float: left;" name="productIdD" id="productIdD" disabled="true"></select>
         </div>
         <div class="am-form-group">
             <label class="am-u-sm-2 am-form-label">物理大小：</label>
             <select style="width: 300px;float: left;" name="cardSizeD" id="cardSizeD"  disabled="true">
                 <c:forEach items="${cardSizeList}" var="cs">
                         <option value="${cs.value}">${cs}</option>
                 </c:forEach>
             </select>
             <span class="msg_type"></span>
         </div>
         <div class="am-form-group">
             <label class="am-u-sm-2 am-form-label">成本折扣：</label>
             <div style="width: 300px;float: left;">
                 <input type="text" name="costDiscountD" id="costDiscountD" disabled="true">
                 <span class="msg_name"></span>
             </div>
         </div>
         <div class="am-form-group">
             <label class="am-u-sm-2 am-form-label">采购数量：</label>
             <div style="width: 300px;float: left;">
                 <input type="text" name="totalD" id="totalD" disabled="true">
                 <span class="msg_name"></span>
             </div>
         </div>
         <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">审核意见：</label>
            <div style="width: 300px;float: left;">
	            <textarea id="remark" name="remark" type="text" class="am-form-field"></textarea>
            </div>
        </div>
     </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" id="update" style="width: 120px;margin: auto;margin-left: 50px;background-color: #0e90d2; border-color: #0e90d2;" onclick="updatePurchase(0)">提交</button>
        <button  class="am-btn am-btn-default" style="width: 120px;margin: auto;margin-left: 50px;" onclick="hides()">返回</button>
    </div>
</div>

<script>
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
        url: "${pageContext.request.contextPath}/iot/purchaseAudit/list?pageNumber=1&pageSize=10",
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
        var purchaseStatus=$("#purchaseStatus").val();
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/purchaseAudit/list.do?pageNumber="+pageNum+"&pageSize=10",
            type:'POST',
            dataType:"json",
            async:false,
            data:{
            	orderSerialNum:orderSerialNum,
            	createrName:createrName,
            	serialNum:serialNum,
            	purchaseStatus:purchaseStatus
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
</script>
<script>
    $(".msg_name").css("color","red");
    $(".msg_type").css("color","red");
    
    //查询供应商
    getSupply();
    //查询流量套餐
    getProduct();
    
   
    //修改采购单
    function updatePurchase(result){
    	var remark = $("#remark").val();
    	 if(remark.length==0){
             layer.tips("请输入审核意见", "#remark", 1);
             return;
         }
    	var id = $("#purchaseIdD").val();
        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/purchaseAudit/audit.do",
            type:'POST',
            async:false,
            data:{
            	id:id,
            	result:result,
            	remark:remark
            },
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                layer.close(index);
                if(data.success){
                    reloadPage(dataAll.page.dq);
                    layer.msg(data.message, {icon: 1});
                    hides();
                }else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }
    function adopt(id){
        var remark = null;
        var result=1;
        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/purchaseAudit/audit.do",
            type:'POST',
            async:false,
            data:{
            	id:id,
            	result:result,
            	remark:remark
            },
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                layer.close(index);
                if(data.success){
                    reloadPage(dataAll.page.dq);
                    layer.msg(data.message, {icon: 1});
                    hides();
                }else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }

    
    //编辑回显
    function editDisplay(id){
        $.ajax({
            url:"${pageContext.request.contextPath }/iot/purchaseAudit/toUpdate.do",
            type:"post",
            data:{id:id},
            dataType:"json",
            success:function (obj){
            	$("#purchaseIdD").val(obj.id);
            	$("[name='serialNumD']").val(obj.serialNum);
            	$("[name='supplyIdD']").val(obj.supplyId);
            	$("[name='productIdD']").val(obj.flowProductId);
                $("[name='cardSizeD']").val(obj.cardSize);
                $("[name='costDiscountD']").val(obj.costDiscount);
                $("[name='totalD']").val(obj.total);
                $('.admin-content-body').hide();
                $('.tab_wu').show();
            }
        });
    };
    
   
    //查询供应商
    getSupply();
    //查询流量套餐
    getProduct();
    
    function getSupply(){
    	 $.ajax({
    	        url:"${pageContext.request.contextPath }/iot/purchaseAudit/getSupply.do",
    	        type:"post",
    	        dataType:"json",
    	        success:function(obj){
    	        	$("#supplyId").empty();
    	            for (var i in obj) {
    	                $("#supplyId").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
    	            }
    	            $("#supplyIdD").empty();
    	            for (var i in obj) {
    	                $("#supplyIdD").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
    	            }
    	        }
    	    });
    }
    
    function getProduct(){
   	 $.ajax({
   	        url:"${pageContext.request.contextPath }/iot/purchaseAudit/getProduct.do",
   	        type:"post",
   	        dataType:"json",
   	        success:function(obj){
   	        	$("#productId").empty();
   	            for (var i in obj) {
   	                $("#productId").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
   	            }
   	            $("#productIdD").empty();
	            for (var i in obj) {
	                $("#productIdD").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
	            }
   	        }
   	    });
   }
    
    
    function hides() {
        $('.admin-content-body').show();
        $('.tab_wu').hide();
    }
</script>
</body>
</html>

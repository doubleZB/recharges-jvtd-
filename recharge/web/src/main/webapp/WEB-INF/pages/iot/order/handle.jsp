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
                <li class="am-active"><a href="#tab1">待处理订单</a></li>
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
                            <%--style="width: 200px;float: left;">--%>
                            <%--</div>--%>
                            <div class="am-form-group">
                                <select style="width: 150px;float: left;" class="am-form-field" id="status">
                                    <option value="">选择订单状态</option>
                                    <c:forEach items="${iotOrderStatusList}" var="ope">
                                        <c:if test="${ope.value}==0">

                                        </c:if>
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
                                <select style="width: 150px;float: left;" class="am-form-field" id="operator">
                                    <option value="">选择运营商</option>
                                    <c:forEach items="${operatorList}" var="ope">
                                        <option value="${ope.value}">${ope}</option>
                                    </c:forEach>
                                </select>
                            </div>


                            <div class="am-form-group">
                                <select style="width: 150px;float: left;" class="am-form-field" id="Size">
                                    <option value="">选择物理大小</option>
                                    <c:forEach items="${cardSizeList}" var="ope">
                                        <option value="${ope.value}">${ope}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div style="margin-bottom:10px; margin-top: 10px;">
                            <div class="am-form-group">
                                <input type="text" id="adminUserName" class="am-form-field" placeholder="销售人员"
                                       style="width: 150px;float: left;">
                            </div>
                            <div class="am-form-group demo1" style="margin-top: 20px">
                                <input class="am-form-field" name="orderTimeOne" id="createDate" placeholder="开始时间"
                                <%--style="width: 200px; margin-right: 1px; height: 35px; margin-top: -22px;"--%>
                                       style="width: 150px;float: left;margin-top: -20px;" readonly required>
                            </div>
                            <div class="am-form-group demo1" style="margin-top: 20px">
                                <input class="am-form-field" name="orderTimeOne" id="endDate" placeholder="结束时间"
                                <%--style="width: 200px; margin-right: 1px; height: 35px; margin-top: -22px;"--%>
                                       style="width: 150px;float: left;margin-top: -20px;" readonly required>
                            </div>
                            <div class="am-form-group">
                                <input type="text" id="clientName" class="am-form-field" placeholder="客户名称"
                                       style="width: 150px;float: left;">
                            </div>

                            <button onclick="reloadPage(1);" class="am-btn am-btn-warning"
                                    style="width: 120px;background-color: #0e90d2; border-color: #0e90d2;">查询
                            </button>
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
                                <a v-if="item.status==4" href="javascript:void(0);"
                                   onclick="queryCard('{{item.serialNum}}')" class="am-btn am-btn-link"
                                   style="padding: 0;color: #0e90d2;">查询库存</a>
                                <%--<a v-if="item.status==1" href="javascript:void(0);" onclick="purchase({{item.id}})" class="am-btn am-btn-link" style="padding: 0;color: #F37B1D;">采购</a>--%>
                                <a v-if="item.status==3" href="javascript:void(0);" onclick="payOrder({{item.id}})"
                                   class="am-btn am-btn-link" style="padding: 0;color: #0e90d2;">支付</a>
                                <a v-if="item.status==2" href="javascript:void(0);"
                                   onclick="queryCard('{{item.serialNum}}')" class="am-btn am-btn-link"
                                   style="padding: 0;color: #0e90d2;">出库</a>
                                <a v-if="item.status==9" href="javascript:void(0);"
                                   onclick="cardIn({{item.parentId}},{{item.id}})" class="am-btn am-btn-link"
                                   style="padding: 0;color: #0e90d2;">入库</a>
                                <a v-if="item.status==10" href="javascript:void(0);" onclick="delivery({{item.id}})"
                                   class="am-btn am-btn-link" style="padding: 0;color: #0e90d2;">配货</a>
                                <a v-if="item.status!=3 && item.status!=6 && item.status!=7 && item.status!=8 && item.status != 11"
                                   onclick="refund({{item.id}},{{item.status}})" class="am-btn am-btn-link"
                                   style="padding: 0;color: #0e90d2;">退款</a>
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
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<%--新建采购--%>
<div class="tab_purchase" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">新建采购单</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" style="margin-top: 20px;">
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">订单编号：</label>
            <input id="parentSerialNumP" name="costCount" type="text" class="am-form-field"
                   style="width: 300px;float: left; height: auto " readonly>
            <input id="currentOrderStatusP" name="currentOrderStatusP" type="hidden">
        </div>
        <%--<div class="am-form-group">--%>
        <%--<label  class="am-u-sm-2 am-form-label" style="width: 190px;">子订单编号：</label>--%>
        <%--<input id="serialNumP" name="serialNum" type="hidden" class="am-form-field"--%>
        <%--style="width: 300px;float: left; height: auto " readonly>--%>
        <%--<input id="currentOrderStatusP" name="currentOrderStatusP" type="hidden" >--%>
        <%--</div>--%>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">供应商：</label>
            <select style="width: 300px;float: left;" id="companyName">
                <option value="">选择供应商</option>
                <c:forEach items="${iotSupplyList}" var="ope">
                    <option value="${ope.id}">${ope.name}</option>
                </c:forEach>
            </select>
            <input type="hidden" id="iotSubOrderId">
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">流量套餐：</label>
            <select style="width: 300px;float: left;" id="productId" disabled>
                <option value="">选择流量套餐</option>
                <c:forEach items="${iotProductList}" var="ope">
                    <option value="${ope.id}">${ope.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">物理大小：</label>
            <select style="width: 300px;float: left;" id="cardSize" disabled>
                <option value="">选择物理大小</option>
                <c:forEach items="${cardSizeList}" var="ope">
                    <option value="${ope.value}">${ope}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">售价折扣：</label>
            <input id="priceDiscount" name="costCount" type="text" class="am-form-field"
                   style="width: 300px;float: left; height: auto " readonly>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">成本折扣：</label>
            <input id="Discount" name="costCount" type="text" class="am-form-field"
                   style="width: 300px;float: left; height: auto ">
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">采购数量：</label>
            <input id="totalEdit" name="total" type="text" class="am-form-field"
                   style="width: 300px;float: left;height: auto" readonly>
            <%--<span  id="passstrength" ></span>--%>
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" onclick="addPurchase();"
                style="width: 120px;margin-left: 120px;background-color: #0e90d2; border-color: #0e90d2;">提交
        </button>
        <button class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>
</div>

<!--入库单新建-->
<div class="tab_cardIn" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">新建入库单</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" style="margin-top: 20px;">
        <div class="am-form-group" style=" margin-top: 10px;">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">采购单编号：</label>
        <%--<label  class="am-u-sm-2 am-form-label" style="width: 190px;">采购单编号：</label>--%>
        <div style="width: 300px;float: left;">
            <input type="text" name="purchaseSerialNum" id="purchaseSerialNum" readonly>
            <input type="hidden" id="purchaseId">
            <input type="hidden" id="orderId">
            <input type="hidden" id="subOrderId">
            <span class="msg_name"></span>
        </div>
        </div>

        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px; ">供应商：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="supplyId" id="supplyId" disabled="disabled">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">采购状态：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="purchaseStatusLiteral" id="purchaseStatusLiteral" disabled="disabled">
                <input type="hidden" id="purchaseStatus">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">流量套餐：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="productId" id="productIdCardIn" disabled="disabled">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">物理大小：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="cardSize" id="cardSizeCardIn" disabled="disabled">
                <span class="msg_name"></span>
            </div>
            <span class="msg_type"></span>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">成本折扣：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="costDiscount" id="costDiscount">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">采购数量：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="total" id="total" disabled="disabled">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label" style="width: 190px;">卡信息文件：</label><em>*</em>
            <div style="width: 300px;float: left;">
                <input type="file" name="importExcel" id="importExcel"
                       accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                <span class="msg_name"></span>
            </div>
        </div>
        <div class="am-form-group">
            <a href="/static/upload/inputCard.xlsx" style="margin-left: 190px">卡信息模板</a>
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" id="Add"
                style="width: 120px;margin: auto;margin-left: 176px;background-color: #0e90d2; border-color: #0e90d2;"
                onclick="insertInReceipt()">提交
        </button>
        <button class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>
</div>
<!--出库查看库存的弹框-->
<div class="am-popup" id="listModal">
    <div class="am-popup-inner">
        <div class="am-popup-hd">
            <h4 class="am-popup-title">查询库存</h4>
            <span data-am-modal-close
                  class="am-close">&times;</span>
        </div>
        <div class="am-popup-bd">
            <form class="am-form-inline" role="form">
                <div>
                    <div class="am-form-group">
                        <input type="text" name="parentSerialNumD" id="parentSerialNumD" class="am-form-field"
                               placeholder="订单编号" style="width: 170px;float: left;" onblur="toDisplay()">
                        <input type="hidden" name="subSerialNumD" id="subSerialNumD" class="am-form-field"
                               placeholder="子订单编号" style="width: 170px;float: left;" onblur="toDisplay()">
                        <input type="hidden" name="subOrderIdD" id="subOrderIdD" class="am-form-field"
                               placeholder="流量套餐" style="width: 150px;float: left;" disabled="disabled">
                    </div>
                    <div class="am-form-group">
                        <input type="text" name="total" id="totalO" class="am-form-field" placeholder="出库数量"
                               style="width: 100px;float: left;">
                    </div>
                    <div class="am-form-group">
                        <select style="width: 150px;float: left;" class="am-form-field" id="supplyIdD"
                                onchange="reloadCardPage(1)">
                            <option value="">供应商</option>
                            <c:forEach items="${iotSupplyList}" var="pur">
                                <option value="${pur.id}">${pur.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="am-form-group">
                        <input type="text" name="totalD" id="totalD" class="am-form-field" placeholder="订购数量"
                               style="width: 100px;float: left;" disabled="disabled">
                    </div>
                    <div class="am-form-group">
                        <input type="text" name="productNameD" id="productNameD" class="am-form-field"
                               placeholder="流量套餐" style="width: 150px;float: left;" disabled="disabled">
                        <input type="hidden" name="productIdD" id="productIdD" class="am-form-field" placeholder="流量套餐"
                               style="width: 150px;float: left;" disabled="disabled">
                    </div>
                    <div class="am-form-group">
                        <input type="text" name="cardSizeLiteralD" id="cardSizeLiteralD" class="am-form-field"
                               placeholder="物理大小" style="width: 100px;float: left;" disabled="disabled">
                        <input type="hidden" name="cardSizeD" id="cardSizeD" class="am-form-field" placeholder="物理大小"
                               style="width: 100px;float: left;" disabled="disabled">
                    </div>
                    <div class="am-form-group">
                        <input type="text" name="priceDiscountD" id="priceDiscountD" class="am-form-field"
                               placeholder="售价折扣" style="width: 100px;float: left;" disabled="disabled">
                    </div>
                </div>
                <div>
                    <div class="am-form-group" style="margin-top: -20px">
                        <input type="hidden" id="editDataSubData" value="{{ checkID | json }}">
                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;margin-top: 25px"
                                onclick="insertOutReceipt()">出库
                        </button>
                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;margin-top: 25px"
                                onclick="purchase()" id="purchaseBtn">采购
                        </button>
                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin-top: 20px;background-color: #0e90d2; border-color: #0e90d2;margin-top: 25px"
                                onclick="toAllocate()" id="allocateBtn">待处理
                        </button>
                    </div>
                </div>
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
                    <th>供应商</th>
                    <th>卡状态</th>
                    <th>销售状态</th>
                    <th>成本折扣</th>
                    <th>入库时间</th>
                </tr>
                <tr v-for="item in json">
                    <td>
                        <label class="am-checkbox-inline">
                            <input type="checkbox" value="{{item.id}}" name="checkbox" v-model="checkID"
                                   @click="checked1()">
                            {{$index+1}}
                        </label>
                    </td>
                    <td>{{item.iccid}}</td>
                    <td>{{item.msisdn}}</td>
                    <td>{{item.operatorLiteral}}</td>
                    <td>{{item.productName}}</td>
                    <td>{{item.cardSizeLiteral}}</td>
                    <td>{{item.supplyName}}</td>
                    <td>{{item.statusLiteral}}</td>
                    <td>{{item.saleStatusLiteral}}</td>
                    <td>{{item.costDiscount}}</td>
                    <td>{{item.createTime.substring(0,19)}}</td>
                </tr>
            </table>

            <div class="cardsj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

            <hr/>
            <div class="am-cf">
                共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                <div class="am-fr">
                    <ul class="am-pagination" style="margin: 0">
                        <li><a href="javascript:void(0);" onclick="reloadCardPage('{{page.dq-1}}');">上一页</a></li>
                        <li class="am-disabled"><a
                                href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                        <li><a href="javascript:void(0);" onclick="reloadCardPage('{{page.dq+1}}');">下一页</a></li>
                        <li class="am-disabled"><a href="#">|</a></li>
                        <li>
                            <input type="text" id="gotoCardPage" style="width: 30px;height: 30px;margin-bottom: 5px;"
                                   id="goto-page-num"
                                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                        </li>
                        <li class="am-active"><a href="javascript:void(0);" onclick="gotoCardPage();"
                                                 style="padding: .5rem .4rem;">GO</a></li>
                    </ul>
                </div>
            </div>
        </div>
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

    var dataAll = {
        checkID: [],
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    var arr = [];
    var vmCard = new Vue({
        el: "#listModal",
        data: dataAll,
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


    function hides() {
        $('.admin-content-body').show();
        $('.tab_purchase').hide();
        $('.tab_cardIn').hide();
        layer.closeAll()
    }
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
        if (createDate != "") {
            if (endDate == "") {
                layer.msg("请选择结束日期！");
                return;
            } else {
                createDate = createDate + " " + "00:00:00";
                endDate = endDate + " " + "23:59:59";
            }
        }
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iot/handle/list?pageNumber=" + pageNumber + "&pageSize=10",
            type: 'POST',
            async: true,
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
    //修改提交
    function gotoPage() {
        var pagenum = $("#gotoPage").val();
        if (pagenum == '') {
            layer.tips("请输入页数！", "#gotoPage", {tips: 3});
            return;
        }
        reloadPage(pagenum);
    }
    //退款操作
    function refund(id, status) {
        layer.confirm('确定退款吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/iot/handle/refund",
                type: 'POST',
                async: true,
                data: {
                    "iotSubOrderId": id,
                    "status": status
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message, {icon: 1});
                        reloadPage(pageInfo.dq);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        }, function () {
            layer.closeAll();
        });
    }
    //配货操作
    function delivery(id) {
        layer.confirm('确定已配货吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/delivery/delivered",
                type: 'POST',
                async: true,
                data: {
                    "iotSubOrderId": id
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message, {icon: 1});
                        reloadPage(pageInfo.dq);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        }, function () {
            layer.closeAll();
        });
    }
    //支付操作
    function payOrder(id) {
        layer.confirm('确定支付吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/iotOrderAddmount/payment",
                type: 'POST',
                async: true,
                data: {
                    "iotSubOrderId": id
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message, {icon: 1});
                        reloadPage(pageInfo.dq);
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        }, function () {
            layer.closeAll();
        });
    }
    //采购操作
    function purchase() {
        var iotSubOrderId = $("#subOrderIdD").val();
        $("#iotSubOrderId").val(iotSubOrderId);
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotOrder/getOrderById",
            type: 'POST',
            async: true,
            data: {"id": iotSubOrderId},
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                $("#parentSerialNumP").val(data.parentSerialNum);
                $("#serialNumP").val(data.serialNum);
                $("#productId").val(data.flowProductId);
                $("#cardSize").val(data.size);
                $("#Discount").val(data.costDiscount);
                $("#priceDiscount").val(data.priceDiscount);
                $("#totalEdit").val(data.total);
                $("#currentOrderStatusP").val(data.status);
                layer.close(index);
                $('.admin-content-body').hide();
                $('#listModal').modal('close');
                $('.tab_purchase').show();
            }
        });
    }
    //采购订单
    function addPurchase() {
        var companyName = $("#companyName").val();
        var iotSubOrderId = $("#iotSubOrderId").val();
        var productId = $("#productId").val();
        var cardSize = $("#cardSize").val();
        var costCount = $("#Discount").val();
        var total = $("#totalEdit").val();
        var currentOrderStatus = $("#currentOrderStatusP").val();
        var priceDiscount = $("#priceDiscount").val();
        if (companyName == "") {
            layer.tips("请选择供应商", "#companyName", 1);
            return;
        }
        if (productId == "") {
            layer.tips("请选择流量套餐", "#productId", 1);
            return;
        }
        if (cardSize == "") {
            layer.tips("请选择物理大小", "#cardSize", 1);
            return;
        }
        var count = /\b0(\.\d{1,2})\b/;
        var reg = /^[1-9]\d*$/;
        if (!count.test(costCount)) {
            layer.tips("请输入0到1之间的两位小数包含1", "#Discount", 1);
            return;
        }
        if (!reg.test(total)) {
            layer.tips("请输入正整数", "#totalEdit", 1);
            return;
        }
        if (costCount > priceDiscount) {
            layer.tips("成本折扣不能大于售价折扣", "#Discount", 1);
            return;
        }
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotOrderPurchase/addPurchase",
            type: 'POST',
            async: false,
            data: {
                "companyName": companyName,
                "iotSubOrderId": iotSubOrderId,
                "productId": productId,
                "costCount": costCount,
                "total": total,
                "cardSize": cardSize,
                "currentOrderStatus": currentOrderStatus,
                "priceDiscount": priceDiscount
            },
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                layer.close(index);
                if (data.success) {
                    layer.msg(data.message, {icon: 1});
                    reloadPage(pageInfo.dq);
                    hides();
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });

    }
    /*入库回显*/
    function cardIn(orderId, subOrderId) {
        $("#subOrderId").val(subOrderId);
        $("#orderId").val(orderId);
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/iotOrderToBeIn/getPurchaseBySubOrderId",
            type: 'POST',
            async: true,
            data: {"subOrderId": subOrderId},
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                if (data.success) {
                    var obj = data.object[0];
                    if (obj.purchaseStatus > 1) {
                        layer.msg("只有待入库的采购单才可入库");
                        layer.close(index);
                        return
                    }
                    $("#purchaseId").val(obj.id);
                    $("#purchaseSerialNum").val(obj.serialNum);
                    $("#purchaseStatus").val(obj.purchaseStatus);
                    $("#purchaseStatusLiteral").val(obj.purchaseStatusLiteral);
                    $("#supplyId").val(obj.supplyName);
                    $("#productIdCardIn").val(obj.productName);
                    $("#cardSizeCardIn").val(obj.cardSizeLiteral);
                    $("#costDiscount").val(obj.costDiscount);
                    $("#total").val(obj.total);
                    layer.close(index);
                    $('.admin-content-body').hide();
                    $('.tab_cardIn').show();
                    if (data.object.length == 2) {
                        if (data.object[1].total != null && data.object[1].total != 0) {
                            layer.tips("已入库" + data.object[1].total, "#total", {time: 0});
                        }
                    }
                } else {
                    layer.close(index);
                    alert(data.message);
                }
            }
        });

    }
    //新建入库单
    function insertInReceipt() {
        var purchaseSerialNum = $("#purchaseSerialNum").val().trim();
        var orderId = $("#purchaseSerialNum").val().trim();
        var subOrderId = $("#subOrderId").val().trim();
        var purchaseId = $("#purchaseId").val().trim();
        var purchaseStatus = $("#purchaseStatus").val().trim();
        var purchaseStatusLiteral = $("#purchaseStatusLiteral").val().trim();
        var costDiscount = $("#costDiscount").val().trim();

        var count = /\b0(\.\d{1,2})\b/;
        var reg = /^[1-9]\d*$/;
        if (!count.test(costDiscount)) {
            layer.tips("请输入0到1之间的两位小数包含1", "#costDiscount", 1);
            return;
        }
        if (purchaseStatus > 1) {
            alert("该采购单的状态为[" + purchaseStatusLiteral + "]无法再入库！");
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
        var files = $('#importExcel').prop('files');
        form.append("importExcel", files[0]);
        form.append("purchaseId", purchaseId);
        form.append("subOrderId", subOrderId);
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
                    reloadPage(pageInfo.dq);
                    $('.admin-content-body').show();
                    $('.tab_cardIn').hide();
                } else {
                    alert("出错了！错误信息:" + data.message);
                }
            }
        });
    }
    //出库查库存
    function queryCard(subSerialNum) {
        $.ajax({
            url: "${pageContext.request.contextPath }/iot/cardOut/toDisplay.do",
            type: "post",
            data: {
                subSerialNum: subSerialNum,
            },
            dataType: "json",
            success: function (data) {
                var obj = data.object;
                if (data.success) {
//                    debugger
                    $("#parentSerialNumD").val(obj.parentSerialNum);
                    $("#subSerialNumD").val(obj.serialNum);
                    $("#subOrderIdD").val(obj.id);
                    $("#productIdD").val(obj.flowProductId);
                    $("#productNameD").val(obj.productName);
                    $("#cardSizeD").val(obj.size);
                    $("#cardSizeLiteralD").val(obj.cardSizeLiteral);
                    $("#totalD").val(obj.total);
                    $("#priceDiscountD").val(obj.priceDiscount);
                    if (obj.status == 2) {
                        $("#purchaseBtn").hide();
                    } else {
                        $("#purchaseBtn").show();
                    }
                    if (obj.status == 4) {
                        $("#allocateBtn").hide();
                    } else {
                        $("#allocateBtn").show();
                    }
                    reloadCardPage(1);
                    $(".checkall").prop("checked", false);
                    $("#listModal").modal();
                } else {
                    alert(data.message);
                }

            }
        });
    }

    //添加出库单
    function insertOutReceipt() {
        var subSerialNum = $("#subSerialNumD").val().trim();
        var subOrderId = $("#subOrderIdD").val().trim();
        var subData = $("#editDataSubData").val();
        var productId = $("#productIdD").val().trim();
        var cardSize = $("#cardSizeD").val().trim();
        var supplyId = $("#supplyIdD").val().trim();
        var total = $("#totalO").val().trim();
        if (total == '') {
            total = $("#totalD").val().trim();
        }
        if (dataAll.page.ts == 0) {
            alert("库存不足!");
            return;
        }
        if (subSerialNum == "") {
            layer.tips("子订单编号不能为空", "#subSerialNum", 1);
            return;
        } else if (subOrderId == "") {
            layer.tips("未查询到该子订单", "#subSerialNum", 1);
            return;
        } else {
            if (dataAll.page.ts < total) {
                var con = confirm("现有库存" + dataAll.page.ts + "小于订购数量" + total + ",如果继续出库现有订单将被拆分"); //在页面上弹出对话框
                if (con == false) {
                    return;
                }
            }
            var loadingIndex = layer.open({type: 3});
            $.ajax({
                url: "${pageContext.request.contextPath }/iot/cardOut/addOutReceipt.do",
                type: "post",
                data: {
                    subOrderId: subOrderId,
                    subData: subData,
                    flowProductId: productId,
                    cardSize: cardSize,
                    supplyId: supplyId,
                    updateLimit: total
                },
                dataType: "json",
                success: function (data) {
                    layer.close(loadingIndex);
                    if (data.success) {
                        alert("出库成功！");
                        reloadPage(pageInfo.dq);
                        $('#listModal').modal('close');
                    } else {
                        alert("出错了！错误信息:" + data.message);
                    }
                }
            });
        }
    }

    //去分配
    function toAllocate() {
        var subOrderIdD = $("#subOrderIdD").val();
        if (subOrderIdD == null) {
            layer.tips("请输入子订单编号", "#subOrderIdD", 1);
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath }/iotOrderOut/toAllocate.do",
            type: "post",
            data: {
                subOrderId: subOrderIdD
            },
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    alert("已返回待处理订单列表");
                    reloadPage(pageInfo.dq);
                    $('#listModal').modal('close');
                } else {
                    alert(data.message);
                }

            }
        });
    }

    function reloadCardPage(pageNum) {
        if (pageNum == 0) {
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var flowProductId = $("#productIdD").val().trim();
        var cardSize = $("#cardSizeD").val().trim();
        var priceDiscount = $("#priceDiscountD").val().trim();
        var supplyId = $("#supplyIdD").val().trim();
        var loadingIndex = layer.open({type: 3});
        $.ajax({
            url: "${pageContext.request.contextPath}/iot/cardOut/list.do?pageNumber=" + pageNum + "&pageSize=10",
            type: 'POST',
            dataType: "json",
            async: true,
            data: {
                flowProductId: flowProductId,
                cardSize: cardSize,
                limitPriceDiscount: priceDiscount,
                supplyId: supplyId
            },
            error: function () {
            },
            success: function (data) {
                if (data.list.length > 0) {
                    $(".cardsj").hide();
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
                    $(".cardsj").show();
                }
            }
        });
    }
    function gotoCardPage() {
        var pageNum = $("#gotoCardPage").val();
        if (pageNum == '') {
            layer.tips("请输入页数！", "#gotoCardPage", {tips: 3});
            return;
        }
        reloadCardPage(pageNum);
    }
</script>
</body>

</html>

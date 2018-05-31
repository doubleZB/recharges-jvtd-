<%--
  Created by IntelliJ IDEA.
  User: 充值流水查询
  Date: 2016/11/28
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<style>
    .checklist {
        border: 1px solid #dedede;
        width: 500px;
        overflow: auto;
        margin: 10px 120px;
        padding: 10px;
        padding-top: 5px;
    }

    .checklist div {
        float: left;
        display: block;
        margin-left: 10px;
    }
    html {
        moz-user-select: inherit;
        -moz-user-select: inherit;
        -o-user-select: inherit;
        -khtml-user-select: inherit;
        -webkit-user-select: inherit;
        -ms-user-select: inherit;
        user-select: inherit;
    }
</style>
<style>

    .am-table>caption+thead>tr:first-child>td, .am-table>caption+thead>tr:first-child>th, .am- table>colgroup+thead>tr:first-child>td, .am-table>colgroup+thead>tr:first-child>th, .am- table>thead:first-child>tr:first-child>td, .am-table>thead:first-child>tr:first-child>th {
        border-top: 0;
        font-size: 1.4rem;
    }
    .am-table>tbody>tr>td, .am-table>tbody>tr>th, .am-table>tfoot>tr>td, .am-table>tfoot>tr>th, .am- table>thead>tr>td, .am-table>thead>tr>th {
        padding: .7rem;
        line-height: 1.6;
        vertical-align: top;
        border-top: 1px solid #ddd;
        font-size: 1.4rem;
    }
    .am-tabs-bd {
        -moz-user-select: text !important;
        -webkit-user-select: text !important;
        -ms-user-select: text !important;
        user-select: text !important;
    }
</style>

<body>
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body" style="padding-top: 20px;">
        <div class="am-cf am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg" style="font-size: 14px;">充值流水查询</strong>
            </div>
        </div>
            <hr>
            <div style="width:100%;overflow-x: scroll;">
                <table class="am-table am-table-striped am-table-hover am-table-bordered" style="min-width: 1400px;">
                    <tr>
                        <td>序号</td>
                        <th>订单号</th>
                        <th>手机号</th>
                        <th>商品类型</th>
                        <th>运营商</th>
                        <th>省份</th>
                        <th>商品</th>
                        <th>支付金额</th>
                        <th>商户简称</th>
                        <th>商户订单号</th>
                        <th>订单来源</th>
                        <th>下单时间</th>
                        <th>状态</th>
                        <th>是否退款</th>
                    </tr>
                    <c:forEach items="${list}" var="o">
                        <tr>
                            <td>${o.id}</td>
                            <td>${o.orderNum}</td>
                            <td>${o.rechargeMobile}</td>
                            <td>${o.businessType==1?"流量":o.businessType==2?"话费":o.businessType==3?"视频会员":""}</td>
                            <td>${o.operator==8?"乐视":o.operator==7?"搜狐":o.operator==6?"腾讯":o.operator==5?"爱奇艺":o.operator==4?"优酷":o.operator==3?"电信":o.operator==2?"联通":o.operator==1?"移动":" "}</td>
                            <td>${o.value}</td>
                            <td>${o.packageSize}</td>
                            <td>${o.amount}</td>
                            <td>${o.userCnName}</td>
                            <td>${o.customId}</td>
                            <td>${o.source==1?"接口":o.source==2?"页面":""}</td>
                            <td>${o.orderTime.substring(0,19)}</td>
                            <td>${o.status==10?"充值待处理":o.status==8?"失败":o.status==7?"成功":o.status==6?"充值中":o.status==5?"待充值":o.status==1?"创建订单":" "}</td>
                            <td>${o.refundStatus==2?"退款失败":o.refundStatus==1?"退款成功":" "}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <h4>充值流水</h4>
        <div style="width: 100%;overflow-x: scroll;">
            <table class="am-table am-table-striped am-table-hover am-table-bordered" style="max-width: 1400px;">
                <tr>
                    <th>ID</th>
                    <th>流水号</th>
                    <th>手机号</th>
                    <th>充值金额/流量</th>
                    <th>提交时间</th>
                    <th>回值时间</th>
                    <th>供应商</th>
                    <th>状态</th>
                    <th>失败返回码</th>
                </tr>
            <c:forEach items="${listOne}" var="b">
                <tr>
                    <td>${b.id}</td>
                    <td>${b.channelNum}</td>
                    <td>${b.mobile}</td>
                    <td>${b.businessType==1?"流量":b.businessType==2?"话费":b.businessType==3?"视频会员":""}</td>
                    <td>${b.submitTime.substring(0,19)}</td>
                    <td>${b.returnTime}</td>
                    <td>${b.supplyName}</td>
                    <td>${b.status==4?"回执失败":b.status==3?"回执成功":b.status==2?"提交失败":b.status==1?"提交成功":""}</td>
                    <td>${b.returnRspcode}</td>
                </tr>
            </c:forEach>
            </table>

        </div>
</div>
</div>
</body>
<script>
</script>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lhm
  Date: 2017/4/18
  Time: 14:06
  To change this template use File | Settings | File Templates.
  商户充值审核
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>商户充值审核</title>
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${path}/static/css/authentication.css">
</head>
<style>
    li {
        list-style-type: none;
    }
    .am-modal-bd td {
        margin-top: 20px;
        display: inline-block;
    }

    address,
    blockquote,
    dl,
    fieldset,
    figure,
    hr,
    ol,
    p,
    pre,
    ul {
        margin-bottom: 0;
    }

    .list ul {
        padding: 0;
        margin: 0;
        height: 214px;
        overflow: auto;
    }

    .list li {
        padding: 10px 15px;
        border-bottom: 1px solid #dedede;
    }

    .list li:last-child {
        border-bottom: none;
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
</style>

<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">商户充值审核</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <div class="am-form-inline" role="form">
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" class="am-form-field"  id="adminName" placeholder="商户简称" style="width: 200px;float: left;">
                </div>
                <div class="am-form-group">
                    <select class="select1" style="width: 150px;float: left;" id="rechargeMode">
                        <option value="0">充值方式</option>
                        <option value="1">银行转账</option>
                        <option value="2">支付宝转账</option>
                        <option value="3">微信支付</option>
                    </select>
                </div>
                <div class="am-form-group">
                    <select class="select1" style="width: 150px;float: left;" id="auditState">
                        <option value="0">审核状态</option>
                        <option value="1">审核通过</option>
                        <option value="2">审核不通过</option>
                        <option value="3">未审核</option>
                    </select>
                </div>
                <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-top: 20px" onclick="selectChannelAddFund(1)">查询</button>
            </div>

            <hr>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>商户账号</th>
                    <th>商户简称</th>
                    <th>充值方式</th>
                    <th>转入账户</th>
                    <th>充值金额</th>
                    <th>审核状态</th>
                    <th>转账时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="userList">
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td>{{item.userName}}</td>
                    <td>{{item.backAccountName}}</td>
                    <td>{{item.payType==3?"微信支付":item.payType==2?"支付宝支付":item.payType==1?"银行转账":""}}</td>
                    <td>{{item.bankName}}</td>
                    <td>{{item.amount}}</td>
                    <td>{{item.auditState==1?"审核通过":item.auditState==2?"审核不通过":item.auditState==3?"未审核":""}}</td>
                    <td>{{item.payTime|time}}</td>
                    <td>
                        <a href="<%=request.getContextPath()%>/userMerchant/merchantRechargeAuditDetailsUI?id={{item.id}}">查看</a>
                    </td>
                </tr>
                </tbody>
            </table>
            <hr>
            <div class="am-cf">
                每页显示&nbsp;
                <select name="select" id="rangNum" onchange="rangNum()">
                    <option value="0">10</option>
                    <option value="1">20</option>
                    <option value="2">50</option>
                </select>&nbsp;条&nbsp;&nbsp; 共 <span id="countNum">{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                <div class="am-fr">
                    <ul class="am-pagination" style="margin: 0">
                        <li><a href="javascript:void(0);" onclick="selectChannelAddFund('{{page.dq-1}}');">上一页</a></li>
                        <li class="am-disabled"><a href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                        <li><a href="javascript:void(0);"  onclick="selectChannelAddFund('{{page.dq+1}}');">下一页</a></li>
                        <li class="am-disabled"><a href="#">|</a></li>
                        <li>
                            <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                        </li>
                        <li class="am-active"><a href="#" style="padding: .5rem .4rem;" onclick="gotoPage()">GO</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/assets/js/amazeui.min.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/laydate/laydate.js"></script>
<script>
//    <!-- 自定义filter名称为'time' -->
    Vue.filter('time',
            <!-- value 格式为13位unix时间戳 -->
            <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
            function (value) {
                if(value!=''&&value!=null){
                    var date = new Date(value);
                    Y = date.getFullYear(),
                            m = date.getMonth() + 1,
                            d = date.getDate(),
                            H = date.getHours(),
                            i = date.getMinutes(),
                            s = date.getSeconds();
                    if (m < 10) {
                        m = '0' + m;
                    }
                    if (d < 10) {
                        d = '0' + d;
                    }
                    if (H < 10) {
                        H = '0' + H;
                    }
                    if (i < 10) {
                        i = '0' + i;
                    }
                    if (s < 10) {
                        s = '0' + s;
                    }
                    <!-- 获取时间格式 2017-01-03 10:13:48 -->
                    var t = Y + '-' + m + '-' + d + ' ' + H + ':' + i + ':' + s;
                    <!-- 获取时间格式 2017-01-03 -->
                    //var t = Y + '-' + m + '-' + d;
                    return t;
                }else{
                    return '';
                }
            });
    var vm = new Vue({
        el: "#content",
        data: {
            json: [],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        },
        ready: function () {
        },
        methods: {}
    });
    $(function(){
       selectChannelAddFund(1);
    });



function selectChannelAddFund(param,param2){
    var pageNumber = param;
    var num = $("#rangNum").val();
    var numTwo="";
    if(num==0){
        numTwo=10;
    }else if(num==1){
        numTwo=20;
    }else if(num==2){
        numTwo=50;
    }else if(num==3){
        numTwo=100;
    }
    if(param2==undefined){
        param2=numTwo;
    }
    var pageSize = param2;
    var countNum = $("#countNum").val();
    var adminName = $("#adminName").val();
    var rechargeMode = $("#rechargeMode").val();
    var auditState = $("#auditState").val();
    $.ajax({
        url: "${pageContext.request.contextPath}/userMerchant/findUserOrderListByName",
        async:false,
        type:'POST',
        data:{"payType":rechargeMode,"pageNumber":pageNumber,"pageSize":pageSize,"auditState":auditState,"backAccountName":adminName.trim()},
        dataType:"json",
        error: function () {
            alert("数据加载错误");
        },
        success: function(data){
            var obj = data.list
            vm.json = obj;
            vm.page.ts=data.total;
            vm.page.dq=data.pageNum;
            vm.page.all=data.pages;
        }
    });

}
//跳页
function gotoPage(){
    var pageNum = $("#goto-page-num").val();
    if(pageNum==""){
        layer.msg("输入跳转页数");
        return;
    }else {
        selectChannelAddFund(pageNum);
    }
}
/**
 * 选择现实多少行
 */
function rangNum(){
    var num = $("#rangNum").val();
    var numTwo="";
    if(num==0){
        numTwo=10;
    }else if(num==1){
        numTwo=20;
    }else if(num==2){
        numTwo=50;
    }else if(num==3){
        numTwo=100;
    }
    var fist = 1;
    selectChannelAddFund(fist,numTwo);
}
</script>
</body>
</html>

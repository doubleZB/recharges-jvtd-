<%--
  Created by IntelliJ IDEA.
  User: 商户列表lihuimin
  Date: 2017/09/13
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<style>
    .am-tabs-bd {
         -moz-user-select: text !important;
         -webkit-user-select: text !important;
         -ms-user-select: text !important;
         user-select: text !important;
     }

    .am-btn{
        border-radius: 5px;
    }
    input,select{
        color:#848181;
    }
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
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body" style="padding-top: 20px;">
        <div class="am-tabs"  data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">商户管理</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form"  style="margin-top:0;">
                        <div class="am-form-group" style="margin-top:0;">
                            <input class="am-form-field" type="text" name="userCnName" id="userCnName3" value="${user.userCnName}" placeholder="商户简称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group" style="margin-top:0;">
                            <input class="am-form-field" type="text" name="userName" id="userName3" value="${user.userName}" placeholder="用户名" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group" style="margin-top:0;">
                                <c:if test="${user.status==1}">
                                    <select class="am-form-field" name="status" id="status3" style="width: 200px;float: left;">
                                             <option value="">状态</option>
                                            <option value="1"  selected = "selected">开启</option>
                                            <option value="0">关闭</option>
                                    </select>
                                </c:if>
                                <c:if test="${user.status==0}">
                                    <select class="am-form-field" name="status" id="status3" style="width: 200px;float: left;">
                                        <option value="">状态</option>
                                        <option value="1">开启</option>
                                        <option value="0"  selected = "selected">关闭</option>
                                    </select>
                                </c:if>
                                <c:if test="${user.status==null}">
                                    <select class="am-form-field" name="status" id="status3" style="width: 200px;float: left;">
                                    <option value="">状态</option>
                                    <option value="1">开启</option>
                                    <option value="0">关闭</option>
                                    </select>
                                </c:if>
                        </div>
                        <button class="am-btn am-btn-warning" type="submit" style="width: 120px;" onclick="reloadPage(1)">查询</button>
                    </div>
                    <form id="listPageform" action="<%=request.getContextPath()%>/user/marketsUserList.do?type=1" method="post">
                        <input type="hidden" name="userCnName" id="userCnName2">
                        <input type="hidden" name="userName" id="userName4">
                        <input type="hidden" name="status" id="status2">
                        <input type="hidden" name="pageNumber" id="pageNumber2">
                        <input type="hidden" name="pageSize" id="pageSize2">
                    </form>
                    <input type="hidden" name="pageNumber" id="pageNumber" value="">
                    <hr>
                    <div style="width: 100%;overflow-x: scroll;">
                    <table class="am-table am-table-striped" style="min-width: 1500px;">
                        <tr>
                            <td>序号</td>
                            <td>商户全称</td>
                            <td>商户简称</td>
                            <td>用户名</td>
                            <td>绑定手机号</td>
                            <td>联系人</td>
                            <td>联系人手机</td>
                            <td>授信额度（万元）</td>
                            <td>借款（万元）</td>
                            <td>账户余额</td>
                            <td>注册时间</td>
                            <td>状态</td>
                            <td>操作</td>
                        </tr>
                        <c:if test="${not empty list.list}">
                            <c:forEach items="${list.list}" var="u">
                                <tr>
                                    <td>${u.id}</td>
                                    <td>${u.userAllName}</td>
                                    <td>${u.userCnName}</td>
                                    <td>${u.userName}</td>
                                    <td>${u.mobile}</td>
                                    <td>${u.contacts}</td>
                                    <td>${u.contactsMobile}</td>
                                    <td>${u.creditBalance}</td>
                                    <td>${u.borrowBalance}</td>
                                    <td>${u.userBalance}</td>
                                    <td>${u.registerTimeFormat}</td>
                                    <td>${u.status==1?"开启":u.status==0?"关闭":""}</td>
                                    <td>
                                        <a href="<%=request.getContextPath()%>/user/userSun.do?userId=${u.id}&userCnName=${u.userCnName}" class="am-btn am-btn-link" style="padding: 0;">子账户</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                        <c:if test="${empty list.list}">
                            <span class="sj" style="color:red;margin-left: 550px;font-size:20px;">暂无数据</span>
                        </c:if>
                    </div>
                    <hr>
                    <div class="am-cf">
                        共 <span>${list.total}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>${list.pages}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="javascript:void(0);" onclick="reloadPage('${list.pageNum-1}');">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> ${list.pageNum} </span>/<span> ${list.pages} </span></a></li>
                                <li><a href="javascript:void(0);"  onclick="reloadPage('${list.pageNum+1}');">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();" style="padding: .5rem .4rem;">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
</body>
<script>

    //上一页 下一页
    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        $("#userCnName2").val($("#userCnName3").val());
        $("#userName4").val($("#userName3").val());
        $("#status2").val($("#status3").val());
        $("#pageNumber2").val(pageNum);
        $("#pageSize2").val('${list.pageSize}');
        $("#listPageform").submit();

    }

    //到几页
    function gotoPage(){
        var pagenum = $("#gotoPage").val();
        if(pagenum==''){
            layer.tips('请输入页数！', '#gotoPage', {tips: 3});
            return;
        }
        reloadPage(pagenum);
    }
</script>

</html>

<%--
  Created by IntelliJ IDEA.
  User: 商户列表lihuimin
  Date: 2016/11/11
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
                <li><a href="#tab2" id="toAdd">新增</a></li>
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
                        <div class="am-form-group" style="margin-top:0;">
                            <input class="am-form-field" type="text" name="sells" value="${user.sells}"  id="sells3" placeholder="销售代表" style="width: 200px;float: left;">
                        </div>
                        <button class="am-btn am-btn-warning" type="submit" style="width: 120px;" onclick="reloadPage(1)">查询</button>
                    </div>
                    <form id="listPageform" action="<%=request.getContextPath()%>/user/list.do?type=1" method="post">
                        <input type="hidden" name="userCnName" id="userCnName2">
                        <input type="hidden" name="userName" id="userName4">
                        <input type="hidden" name="status" id="status2">
                        <input type="hidden" name="sells" id="sells2">
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
                            <td>销售代表</td>
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
                                    <td>${u.sells}</td>
                                    <td>${u.registerTimeFormat}</td>
                                    <td>${u.status==1?"开启":u.status==0?"关闭":""}</td>
                                    <td>
                                        <a href="###" class="bianji am-btn am-btn-link" style="padding: 0;" onclick="editlist(${u.id})">修改</a>
                                        <a href="<%=request.getContextPath()%>/user/userApp.do?userId=${u.id}&userCnName=${u.userCnName}"class="am-btn am-btn-link use" style="padding: 0;">应用管理</a>
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
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal" id="commentForm" name="commentForm" >
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">商户全称：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="userAllName" id="userAllName" onblur="checkuserAllName()">
                                <input type="hidden" name="Name" id="operater" value="${adminLoginUser.adminName}">
                                <span class="msg_allName"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">商户简称：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="userCnName" id="userCnName" onblur="checkUserCnName()">
                                <span class="msg_cnName"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">账户名：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="userName" id="userName" onblur="OnlyUserName()">
                                <span class="msg_name"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">初始密码：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="password" id="password" onblur="checkPassword()">
                                <span class="msg_password"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">绑定手机：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="mobile" id="mobile" onblur="OnlyMobile()">
                                <span class="msg_mobile"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">联系人：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text"name="contacts" id="contacts" onblur="checkContacts()">
                                <span class="msg_contacts"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">联系人手机：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="contactsMobile" id="contactsMobile" onblur="OnlyContactsMobile()">
                                <span class="msg_contacts_mobile"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label  class="am-u-sm-2 am-form-label">销售代表：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="sells" id="sells" onblur="checkSells()">
                                <span class="msg_sells"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label  class="am-u-sm-2 am-form-label">状态：</label><em>*</em>
                            <select style="width: 300px;float: left;" name="status" id="status">
                                <option value="">状态</option>
                                <option value="1" selected = "selected">开启</option>
                                <option value="0">关闭</option>
                            </select>
                        </div>
                    </form>
                    <div class="am-form-group">
                        <button  class="am-btn am-btn-warning" id="Add" style="width: 120px;margin: auto;margin-left: 176px;" onclick="Add()">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<!--//编辑-->
<div class="tab_wu">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" id="upaa" type="post">
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">商户全称：</label>
            <div style="width: 300px;float: left;">
                <input type="hidden" name="id" id="id">
                <input type="hidden" name="Name" id="operate" value="${adminLoginUser.adminName}">
                <input type="text" name="userAllName" id="userAllNames" onblur="checkuserAllNames()" disabled="disabled">
                <span class="msg_allName"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">商户简称：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="userCnName" id="userCnNames" onblur="checkuserCnNames()" disabled="disabled">
                <span class="msg_cnName"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">账户名：</label>
            <div style="width: 300px;float: left;">
                <input type="hidden" name="userName" id="userName2">
                <input type="text" name="userName" id="userName1" disabled="disabled">
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">初始密码：</label>
            <div style="width: 300px;float: left;">
                <input type="password" name="password" id="passwords" onblur="checkPasswords()">
                <span class="msg_password"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">绑定手机：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="mobile" id="mobiles"  onblur="OnlyMobilet()">
                <span class="msg_mobile"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">联系人：</label>
            <div style="width: 300px;float: left;">
                <input type="text"name="contacts" id="contactss" onblur="checkContactss()">
                <span class="msg_contacts"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">联系人手机：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="contactsMobile" id="contactsMobiles"  onblur="OnlyContactsMobilet()">
                <span class="msg_contacts_mobile"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">销售代表：</label>
            <div style="width: 300px;float: left;">
                <input type="text" name="sells" id="sellss" onblur="checkSellss">
                <span class="msg_sells"></span>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">状态：</label>
            <select style="width: 300px;float: left;" name="status" id="status1">
                <option value="1">开启</option>
                <option value="0">关闭</option>
            </select>
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" id="update" style="width: 120px;margin: auto;margin-left: 176px;" onclick="Update()">修改</button>
    </div>
</div>
</body>
<script>
    $(function() {
        $(".bianji").on("click", function() {
            $('.admin-content-body').hide();
            $('.tab_wu').show();
            var shjc = $(this).parents("tr").find("td").eq(1).text();
            var zhm = $(this).parents("tr").find("td").eq(3).text();
            var lxr = $(this).parents("tr").find("td").eq(5).text();
            var lxrsj = $(this).parents("tr").find("td").eq(6).text();
            var xsdb = $(this).parents("tr").find("td").eq(7).text();
            $("#shjc").val(shjc);
            $("#zhm").val(zhm);
            $("#lxr").val(lxr);
            $("#lxrsj").val(lxrsj);
            $("#xsdb").val(xsdb);
        });
    })

    $(".msg_name").css("color","red");
    $(".msg_mobile").css("color","red");
    $(".msg_contacts_mobile").css("color","red");
    $(".msg_allName").css("color","red");
    $(".msg_cnName").css("color","red");
    $(".msg_password").css("color","red");
    $(".msg_contacts").css("color","red");
    $(".msg_sells").css("color","red");
    $(".error").css("color","red");

    //验证修改密码
    function checkPasswords(){
        var Passwords=$("#passwords").val();
        var password = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,10}$/;
        if(password.test(Passwords.trim())){
            $(".msg_password").text("");
            $('#update').attr('disabled',false);
        }else if(Passwords.trim() == ""){
            $(".msg_password").text("请输入密码");
            $('#update').attr('disabled',true);
        }else{
            $(".msg_password").text("请正确填写密码4~10位，允许字母数字特殊字符");
            $('#update').attr('disabled',true);
        }
    }
    //验证修改联系人手机号
    function OnlyContactsMobilet(){
        var Mobile=$("#contactsMobiles").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if(mobile.test(Mobile.trim())&&Mobile.length==11){
            $(".msg_contacts_mobile").text("");
            $('#update').attr('disabled',false);
        }else if(Mobile.trim() == ""){
            $(".msg_contacts_mobile").text("请输入手机号");
            $('#update').attr('disabled',true);
        }else{
            $(".msg_contacts_mobile").text("请正确填写您的手机号码");
            $('#update').attr('disabled',true);
        }
    }
    //修改验证联系人
    function checkContactss(){
        var Contacts=$("#contactss").val();
        var contacts = /^[\u4e00-\u9fa5]{2,4}$/;
        if(contacts.test(Contacts.trim())){
            $(".msg_contacts").text("");
            $('#update').attr('disabled',false);
        }else if(Contacts.trim() == ""){
            $(".msg_contacts").text("请输入联系人姓名");
            $('#update').attr('disabled',true);
        }else{
            $(".msg_contacts").text("请正确填写联系人2~4汉字");
            $('#update').attr('disabled',true);
        }
    }
    //修改验证销售代表
    function checkSellss(){
        var Sells=$("#sellss").val();
        var sells = /^[\u4e00-\u9fa5]{2,4}$/;
        if(sells.test(Sells.trim())){
            $(".msg_sells").text("");
            $('#update').attr('disabled',false);
        }else if(Sells.trim() == ""){
            $(".msg_sells").text("请输入销售代表");
            $('#update').attr('disabled',true);
        }else{
            $(".msg_sells").text("请正确填写销售代表2~4汉字");
            $('#update').attr('disabled',true);
        }
    }

    //验证账户名唯一
    function OnlyUserName(){
        var userName=$("#userName").val();
        var user_name = /^[a-zA-Z][a-zA-Z0-9]{3,9}$/;
        if(user_name.test(userName.trim())){
            $.ajax({
                url:"${pageContext.request.contextPath }/user/OnlyName.do",
                type:"post",
                data:{
                    userName:userName.trim()
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".msg_name").text("已有该用户名，请重新输入");
                        $('#Add').attr('disabled',true);
                        return;
                    }else{
                        $(".msg_name").text("");
                        $('#Add').attr('disabled',false);
                    }
                }
            });
        }else if(userName.trim() == ""){
            $(".msg_name").text("请输入用户名");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_name").text("请输入4~10个数字或字母并且不能以数字开头");
            $('#Add').attr('disabled',true);
        }
    }
    //验证商户全称
    function checkuserAllName(){
        var AllName=$("#userAllName").val();
        var allName = /^[\u4e00-\u9fa5]{2,20}$/;
        if(allName.test(AllName.trim())){
            $(".msg_allName").text("");
            $('#Add').attr('disabled',false);
        }else if(AllName.trim() == ""){
            $(".msg_allName").text("请输入商户全称");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_allName").text("请正确填写全称2~20汉字");
            $('#Add').attr('disabled',true);
        }
    }
    //验证商户简称唯一
    function checkUserCnName(){
        var CnName=$("#userCnName").val();
        var cnName = /^[\u4e00-\u9fa5]{2,10}$/;
        if(cnName.test(CnName.trim())){
            $.ajax({
                url:"${pageContext.request.contextPath }/user/OnlyName.do",
                type:"post",
                data:{
                    userCnName:CnName.trim()
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".msg_cnName").text("已有该商户简称，请重新输入");
                        $('#Add').attr('disabled',true);
                        return;
                    }else{
                        $(".msg_cnName").text("");
                        $('#Add').attr('disabled',false);
                    }
                }
            });
        }else if(CnName.trim() == ""){
            $(".msg_cnName").text("请输入商户简称");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_cnName").text("请正确填写简称2~10汉字");
            $('#Add').attr('disabled',true);
        }
    }
    //验证联系人
    function checkContacts(){
        var Contacts=$("#contacts").val();
        var contacts = /^[\u4e00-\u9fa5]{2,4}$/;
        if(contacts.test(Contacts.trim())){
            $(".msg_contacts").text("");
            $('#Add').attr('disabled',false);
        }else if(Contacts.trim() == ""){
            $(".msg_contacts").text("请输入联系人姓名");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_contacts").text("请正确填写联系人2~4汉字");
            $('#Add').attr('disabled',true);
        }
    }
    //验证销售代表
    function checkSells(){
        var Sells=$("#sells").val();
        var sells = /^[\u4e00-\u9fa5]{2,4}$/;
        if(sells.test(Sells.trim())){
            $(".msg_sells").text("");
            $('#Add').attr('disabled',false);
        }else if(Sells.trim() == ""){
            $(".msg_sells").text("请输入销售代表");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_sells").text("请正确填写销售代表2~4汉字");
            $('#Add').attr('disabled',true);
        }
    }

    //验证密码
    function checkPassword(){
        var Password=$("#password").val();
        var password = /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{4,10}$/;
        if(password.test(Password.trim())){
            $(".msg_password").text("");
            $('#Add').attr('disabled',false);
        }else if(Password.trim() == ""){
            $(".msg_password").text("请输入密码");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_password").text("请正确填写密码4~10位，允许字母数字特殊字符");
            $('#Add').attr('disabled',true);
        }
    }

    //验证联系人手机号
    function OnlyContactsMobile(){
        var Mobile=$("#contactsMobile").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if(mobile.test(Mobile.trim())&&Mobile.trim().length==11){
            $(".msg_contacts_mobile").text("");
            $('#Add').attr('disabled',false);
        }else if(Mobile.trim() == ""){
            $(".msg_contacts_mobile").text("请输入手机号");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_contacts_mobile").text("请正确填写您的手机号码");
            $('#Add').attr('disabled',true);
        }
    }

    //验证手机号唯一
    function OnlyMobile(){
        var Mobile=$("#mobile").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if(mobile.test(Mobile.trim())&&Mobile.trim().length==11){
            $.ajax({
                url:"${pageContext.request.contextPath }/user/OnlyName.do",
                //async:false,
                type:"post",
                data:{
                    mobile:Mobile.trim()
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        $(".msg_mobile").text("已有该手机号，请重新输入");
                        $('#Add').attr('disabled',true);
                    }else{
                        $(".msg_mobile").text("");
                        $('#Add').attr('disabled',false);
                    }
                }
            });
        }else if(Mobile.trim() == ""){
            $(".msg_mobile").text("请输入手机号");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_mobile").text("请正确填写您的手机号码");
            $('#Add').attr('disabled',true);
        }
    }

    //验证手机号
    function OnlyMobilet(){
        var Mobile=$("#mobiles").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if(mobile.test(Mobile.trim())&&Mobile.trim().length==11){
            $(".msg_mobile").text("");
            $('#update').attr('disabled',false);
        }else if(Mobile.trim() == ""){
            $(".msg_mobile").text("请输入手机号");
            $('#update').attr('disabled',true);
        }else{
            $(".msg_mobile").text("请正确填写您的手机号码");
            $('#update').attr('disabled',true);
        }
    }

    //上一页 下一页
    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        $("#userCnName2").val($("#userCnName3").val());
        $("#userName4").val($("#userName3").val());
        $("#status2").val($("#status3").val());
        $("#sells2").val($("#sells3").val());
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

    //添加
    function Add(){
        if($("#userAllName").val().trim()==""){
            alert("商户全称不能为空！");
            return;
        }else if( $("#userCnName").val().trim()==""  ){
            alert("商户简称不能为空！");
            return;
        }else if($("#userName").val().trim()==""){
            alert("账户名不能为空！");
            return;
        }else if( $("#password").val().trim()==""){
            alert("初始密码不能为空！");
            return;
        }else if( $("#mobile").val().trim()==""){
            alert("绑定手机不能为空！");
            return;
        }else if( $("#contacts").val().trim()=="" ){
            alert("联系人不能为空！");
            return;
        }else if( $("#contactsMobile").val().trim()==""){
            alert("联系人手机不能为空！");
            return;
        }else if( $("#sells").val().trim()==""){
            alert("销售代表不能为空！");
            return;
        }else if( $("#status").val().trim()==""){
            alert("状态不能为空！");
            return;
        }
        else if( $(".msg_allName").text()!=""){
            alert("请重新输入商户全称！");
            return;
        }else if( $(".msg_cnName").text()!=""){
            alert("请重新输入商户简称！");
            return;
        }else if( $(".msg_name").text()!=""){
            alert("请重新输入账户名！");
            return;
        }else if( $(".msg_password").text()!=""){
            alert("请重新输入密码！");
            return;
        }else if( $(".msg_mobile").text()!=""){
            alert("请重新输入绑定手机号！");
            return;
        }else if( $(".msg_contacts").text()!=""){
            alert("请重新输入联系人！");
            return;
        }else if( $(".msg_contacts_mobile").text()!=""){
            alert("请重新输入联系人手机！");
            return;
        }else if( $(".msg_sells").text()!=""){
            alert("请重新输入销售代表！");
            return;
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/user/add.do?",
                type:"post",
                data:$("#commentForm").serialize(),
                dataType:"json",
                success:function (data){
                    layer.close(loadingIndex);
                    if(data){
                        alert("恭喜你添加成功！");
                        location.href="${pageContext.request.contextPath }/user/list.do?type=0";
                    }else{
                        alert("抱歉添加失败！");
                        location.reload();
                    }
                }
            });
        }
    }
    //编辑回显
    function editlist(a){
        var pw=document.getElementById("passwords").value;
        if (pw.length>0)
        {
            document.getElementById("passwords").value="";
        }
        var id = a;
        $.ajax({
            url:"${pageContext.request.contextPath }/user/toUpdate.do",
            type:"post",
            data:{id:id},
            dataType:"json",
            success:function (obj){
                var user = obj.user;
                $("[name='id']").val(user.id);
                $("[name='userAllName']").val(user.userAllName);
                $("[name='userCnName']").val(user.userCnName);
                $("[name='userName']").val(user.userName);
                $("[name='mobile']").val(user.mobile);
                $("[name='contacts']").val(user.contacts);
                $("[name='contactsMobile']").val(user.contactsMobile);
                $("[name='sells']").val(user.sells);
                $("[name='status']").val(user.status);
            }
        });
    };
    //修改
    function Update(){
        var Mobile=$("#mobiles").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        if(mobile.test(Mobile.trim())&&Mobile.trim().length==11){
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/user/updateList.do",
                data:$("#upaa").serialize(),
                dataType:"json",
                type:"post",
                success:function(obj){
                    layer.close(loadingIndex);
                    if(obj){
                        alert("恭喜你修改成功!");
                        location.href="${pageContext.request.contextPath }/user/list.do?type=0";
                    }else{
                        alert("抱歉修改失败！");
                        location.reload();
                    }
                }
            });
        }else{
            alert("请正确填写手机号")
        }
    }


</script>

</html>

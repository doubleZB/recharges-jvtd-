<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anna
  Date: 2016-11-24
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">

<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/mimaxiugai.css">
</head>
<style>
    #content{
        margin-top: 20px;
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
    <div class="admin-content-body">
        <div class="am-tab-panel" id="tab2">
            <div class="am-cf am-padding-bottom-0 am-padding-top-0">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg" style="font-size: 14px;">密码修改</strong>
                </div>
            </div>
            <hr style="margin: 10px 0px 0px 0px;">
            <form class="am-form am-form-horizontal" id="addAdminUserForm" style="margin-top: 20px;">
                <div class="am-form-group">
                    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">用户姓名：</label>
                    <div style="width: 300px;float: left;margin-top: .6em;">
                        ${userInfo.name}
                    </div>
                </div>
                <div class="am-form-group">
                    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">登录名：</label>
                    <div style="width: 300px;float: left;margin-top: .6em;">
                        ${userInfo.adminName}
                    </div>
                </div>
                <div class="am-form-group">
                    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">原始密码：</label>
                    <div style="width: 300px;float: left;">
                        <input type="password" id="oldPswd" placeholder="输入原始密码">
                    </div>
                </div>
                <div class="am-form-group">
                    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">新密码：</label>
                    <div style="width: 300px;float: left;">
                        <input type="password" id="newPswd" placeholder="输入新密码">
                    </div>
                </div>
                <div class="am-form-group">
                    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">确认密码：</label>
                    <div style="width: 300px;float: left;">
                        <input type="password" id="newPswdag" placeholder="再次输入新密码">
                    </div>
                </div>
            </form>
            <div class="am-form-group">
                <button class="am-btn am-btn-warning" onclick="updatepswd()" style="width: 120px;margin: auto;margin-left: 106px;">提交</button>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script>
    function updatepswd(){
        var oldPswd =$("#oldPswd").val();
        var newPswd =$("#newPswd").val();
        var newPswdag =$("#newPswdag").val();
        if(oldPswd==''){
            layer.tips("请输入原始密码！","#oldPswd",1);
            return;
        }
        if(newPswd==''){
            layer.tips("请输入新密码！","#newPswd",1);
            return;
        }
        if(newPswd.length<4){
            layer.tips("新密码位数不够！","#newPswd",1);
            return;
        }
        if(newPswd.length>10){
            layer.tips("新密码位数太长！","#newPswd",1);
            return;
        }
        if(newPswdag==''){
            layer.tips("请再次输入新密码！","#newPswdag",1);
            return;
        }
        if(newPswd!=newPswdag){
            layer.tips("两次新密码输入不一致！","#newPswdag",1);
            return;
        }
        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "/sys/updatepswd",
            type:'POST',
            async:false,
            data:{
                "oldPswd":oldPswd,
                "newPswd":newPswd,
                "newPswdag":newPswdag
            },
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                layer.close(index);
                if(data.success){
                    document.getElementById("addAdminUserForm").reset();
                    layer.msg(data.message, {icon: 1});
                }else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }
</script>
</body>

</html>

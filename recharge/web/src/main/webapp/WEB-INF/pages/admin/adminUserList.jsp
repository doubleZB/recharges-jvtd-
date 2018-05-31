<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 用户管理
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/yonghuliebiao.css">
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
        <div class="am-tabs"  data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">用户管理</a></li>
                <li><a href="#tab2">新增</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input  type="text" id="userNameToFind" class="am-form-field" placeholder="用户登录名" style="width: 200px;float: left;">
                        </div>
                        <button onclick="reloadPage(1);" class="am-btn am-btn-warning" style="width: 120px;">查询</button>
                    </div>
                    <hr>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>用户姓名</th>
                            <th>用户登录名</th>
                            <th>所属角色</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>{{item.id}}</td>
                            <td>{{item.name}}</td>
                            <td>{{item.loginname}}</td>
                            <td>{{item.roleName}}</td>
                            <td>
                                <a class="am-btn am-btn-link nava" style="padding: 0;" onclick="shows({{item.id}})">编辑</a>
                                <a onclick="remove({{item.id}})" class="am-btn am-btn-link"
                                   style="padding: 0;color: #F37B1D;">删除</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <hr>
                    <div class="am-cf">
                        共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a  href="javascript:void(0)" onclick="reloadPage({{page.dq-1}})">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq+1}})">下一页</a></li>
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
                    <form class="am-form am-form-horizontal" id="addAdminUserForm">
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">用户姓名：</label>
                            <input  id="userName" name="userName" type="text" class="am-form-field" placeholder="2~4个汉字" style="width: 300px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">登录名：</label>
                            <input  id="loginName" name="loginName" type="text" class="am-form-field" placeholder="4~10个字母与数字的组合，需以字母开头" style="width: 300px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">密码：</label>
                            <input  id="password" name="password" type="password" class="am-form-field" placeholder="4~10位字母数字符号组合" style="width: 300px;float: left;">
                            <span  id="passstrength" ></span>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">确认密码：</label>
                            <input  id="passwordag" name="passwordag" type="password" class="am-form-field" placeholder="4~10位字母数字符号组合" style="width: 300px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">角色分配：</label>
                            <div>
                                <select id="userRole" name="userRole" class="am-form-field" style="width: 300px;float: left;">
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.id}">${role.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                    <div class="am-form-group">
                        <button id="submitToAdd" class="am-btn am-btn-warning" style="width: 120px;margin-left: 120px;">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<!--   //编辑-->
<div class="tab_wu" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">后台用户信息编辑修改</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" style="margin-top: 20px;">
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">登录名：</label>
            <span id="userloginNametoedit"></span>
            <input type="hidden" id="userIdtoedit" >
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">用户姓名：</label>
            <input  type="text" id="userNametoedit" class="am-form-field" placeholder="2~4个汉字" style="width: 300px;float: left;">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">密码：</label>
            <input  type="password" id="userpasswordtoedit" class="am-form-field" placeholder="4~10位字母数字符号组合" style="width: 300px;float: left;">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">角色分配：</label>
            <select id="userRoletoedit" name="userRole" class="am-form-field" style="width: 300px;float: left;">
                <c:forEach items="${roles}" var="role">
                    <option value="${role.id}">${role.name}</option>
                </c:forEach>
            </select>
        </div>
    </form>
    <div class="am-form-group">
        <button  class="am-btn am-btn-warning" onclick="toEditAdminUser();" style="width: 120px;margin-left: 120px;">提交</button>
        <button  class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script>

    var pageInfo={
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
        methods: {

        }

    });
    $(function() {
        $("#am-nav li").click(function() {
            $(".tab_list li").eq($(this).index()).show().siblings().hide();
        });
        $("#submitToAdd").click(function() {
            var userName =$("#userName").val();
            var loginName =$("#loginName").val();
            var password =$("#password").val();
            var passwordag =$("#passwordag").val();
            var userRole =$("#userRole").val();
            if(userName==''){
                layer.tips("用户姓名不能为空","#userName",1);
                return;
            }
            var test =/^[\u4e00-\u9fa5]{2,4}$/;
            if(!test.test(userName)){
                layer.tips("用户姓名仅限2-4个汉字！","#userName");
                return;
            }
            if(loginName==''){
                layer.tips("登录名不能为空","#loginName",1);
                return;
            }
            var   r   =   /^[a-zA-Z][a-zA-Z0-9]{3,9}$/;
            if(!r.test(loginName)){
                layer.tips("登录名为4~10个字母与数字的组合，需以字母开头","#loginName",1);
                return;
            }
            var index = layer.load(0, {shade: [0.3,'#000']});
            $.ajax({
                url: "/sys/checkAdminUserName",
                type:'POST',
                async:false,
                data:{"loginName":loginName},
                dataType:"text",
                error:function(){
                    layer.close(index);
                },
                success: function(data){
                    layer.close(index);
                    if(data=='T'){
                        layer.tips("登录名已存在，请换一个！","#loginName",1);
                        return;
                    }
                    if(password==''){
                        layer.tips("密码不能为空","#password",1);
                        return;
                    }
                    if(password.length<4){
                        layer.tips("密码位数不够","#password",1);
                        return;
                    }
                    if(password.length>10){
                        layer.tips("密码位数太长","#password",1);
                        return;
                    }
                    if(passwordag==''){
                        layer.tips("确认密码不能为空","#passwordag",1);
                        return;
                    }
                    if(passwordag!=password){
                        layer.tips("两次密码输入不一致","#passwordag",1);
                        return;
                    }
                    index = layer.load(0, {shade: [0.3,'#000']});
                    $.ajax({
                        url: "/sys/addAdminUser",
                        type:'POST',
                        async:false,
                        data:{
                            "loginName":loginName,
                            "userName":userName,
                            "password":password,
                            "passwordag":passwordag,
                            "roleId":userRole
                        },
                        dataType:"json",
                        error:function(){
                            layer.close(index);
                        },
                        success: function(data){
                            layer.close(index);
                            if(data.success){
                                document.getElementById("addAdminUserForm").reset();
                                $("#passstrength").text("");
                                reloadPage(pageInfo.dq);
                                layer.msg(data.message, {icon: 1});
                            }else {
                                layer.msg(data.message, {icon: 2});
                            }
                        }
                    });
                }
            });

        });
    });

    function selectGroup(checkbox, obj) {
        if ($(checkbox).prop('checked')) {
            $('input[name=' + obj + ']').prop('checked', true);
        } else {
            $('input[name=' + obj + ']').prop('checked', false);
        }
    }

    function shows(uid) {
        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "/sys/getAdminUserByid",
            type:'POST',
            async:false,
            data:{"uid":uid},
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                $("#userloginNametoedit").text(data.loginname);
                $("#userNametoedit").val(data.name);
                $("#userIdtoedit").val(data.id);
                $("#userRoletoedit").val(data.roleid);
                $("#userpasswordtoedit").val("");
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

    function reloadPage(pageNumber){
        if(pageNumber==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var userNameToFind = $("#userNameToFind").val();
        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "/sys/getAdminUser?pageNumber="+pageNumber+"&pageSize=10",
            type:'POST',
            async:false,
            data:{"longName":userNameToFind},
            dataType:"json",
            error:function(){
                $(this).addClass("done");
            },
            success: function(data){
                pageInfo.ts=data.total;
                pageInfo.dq=data.pageNum;
                pageInfo.all=data.pages;
                vm.$set('json',data.list);
                layer.close(index);
            }
        });
    }
    function gotoPage(){
        var pagenum = $("#gotoPage").val();
        if(pagenum==''){
            layer.tips("请输入页数！","#gotoPage",{tips: 3});
            return;
        }
        reloadPage(pagenum);
    }
    function remove(uid) {
        layer.confirm('你确定删除此记录吗？', {
            btn: ['确定','取消'] //按钮
         }, function(){
            var index = layer.load(0, {shade: [0.3,'#000']});
            $.ajax({
                url: "/sys/delAdminUser",
                type:'POST',
                async:false,
                data:{"uid":uid},
                dataType:"json",
                error:function(){
                    layer.close(index);
                },
                success: function(data){
                    layer.close(index);
                    if(data.success){
                        layer.msg(data.message, {icon: 1});
                        reloadPage(pageInfo.dq);
                    }else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
         }, function(){
            layer.closeAll();
         });
    }

    function toEditAdminUser(){
        var userName = $("#userNametoedit").val();
        var userId = $("#userIdtoedit").val();
        var roleid = $("#userRoletoedit").val();
        var password = $("#userpasswordtoedit").val();
        if(userName==''){
            layer.tips("用户姓名不能为空","#userNametoedit",1);
            return;
        }
        var test =/^[\u4e00-\u9fa5]{2,4}$/;
        if(!test.test(userName)){
            layer.tips("用户姓名仅限2-4个汉字！","#userNametoedit");
            return;
        }
        if(password!=''){
            if(password.length<4){
                layer.tips("密码位数不够","#userpasswordtoedit",1);
                return;
            }
            if(password.length>10){
                layer.tips("密码位数太长","#userpasswordtoedit",1);
                return;
            }
        }

        var index = layer.load(0, {shade: [0.3,'#000']});
        $.ajax({
            url: "/sys/toEditAdminUser",
            type:'POST',
            async:false,
            data:{
                "userId":userId,
                "userName":userName,
                "password":password,
                "roleId":roleid
            },
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                layer.close(index);
                if(data.success){
                    reloadPage(pageInfo.dq);
                    layer.msg(data.message, {icon: 1});
                    hides();
                }else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });
    }

</script>
<script>

    //下面的正则表达式建议各位收藏哦，项目上有可能会用得着
    $(function(){
        $('#password').blur(function(e) {
            var html='';
            //密码为八位及以上并且字母数字特殊字符三项都包括
            var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");

            //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等
            var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
            var enoughRegex = new RegExp("(?=.{4,}).*", "g");
            if (false == enoughRegex.test($(this).val())) {
                html='<label style="color: red">密码位数不够</label>';
            } else if (strongRegex.test($(this).val())) {
                html='密码强度：<label style="color: #00DD00">强</label>';
            } else if (mediumRegex.test($(this).val())) {
                html='密码强度：<label style="color: yellow">中</label>';
            } else {
                html='密码强度：<label style="color: red">弱</label>';
            }
            $('#passstrength').html(html);
            return true;
        });
    });
</script>

</body>

</html>

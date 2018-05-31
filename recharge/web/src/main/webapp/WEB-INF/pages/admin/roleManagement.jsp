<%--
  Created by IntelliJ IDEA.
  User: anna
  Date: 2016-11-24
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jueseliebiao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/ztree/css/zTreeStyle/zTreeStyle.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js "></script>
    <script src="${pageContext.request.contextPath}/static/ztree/js/jquery.ztree.core.js"></script>
    <script src="${pageContext.request.contextPath}/static/ztree/js/jquery.ztree.excheck.js"></script>
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
    <SCRIPT type="text/javascript">
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes =${menus};


        $(document).ready(function(){
            $.fn.zTree.init($("#quanxian"), setting, zNodes);
        });

    </SCRIPT>
</head>
<body>
<div class="admin-content" id="content">
    <div class="admin-content-body">
        <div class="am-tabs"  data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">角色管理</a></li>
                <li><a href="#tab2">新增</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input v-model="hd" type="text" id="roleName" class="am-form-field" placeholder="角色名称" style="width: 200px;float: left;">
                        </div>
                        <button  class="am-btn am-btn-warning" onclick="selectRoleByname();" style="width: 120px;">查询</button>
                    </div>
                    <hr>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>角色名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>{{item.id}}</td>
                            <td>{{item.name}}</td>
                            <td>
                                <a class="am-btn am-btn-link nava" style="padding: 0;" onclick="shows({{item.id}},'{{item.name}}')">权限配置</a>
                                <a onclick="delRole({{item.id}},'{{item.name}}');" class="am-btn am-btn-link"
                                   style="padding: 0;color: #F37B1D;">删除</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group">
                            <label  class="am-u-sm-2 am-form-label">角色名称：</label>
                            <input  id="roleNmae" type="text" class="am-form-field" placeholder="" style="width: 300px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label  class="am-u-sm-2 am-form-label">选择权限：</label>
                        </div>
                        <div>
                            <ul id="quanxian" class="ztree"></ul>
                        </div>

                    </form>
                        <div class="am-form-group">
                            <button  class="am-btn am-btn-warning" style="width: 120px;margin-left: 120px;" onclick="toAddRole();">提交</button>
                        </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script>
    var vm = new Vue({
        el: "#content",
        data: {
            lx: "",
            hd: "",
            yys: "",
            sf: "",
            json: ${roles},
            page: {
                ts: 4,
                dq: 1,
                all: 1
            },
            item: {}
        },
        methods: {
            remove: function (index) {
                var msg=confirm("你确定删除此记录吗？");
                if(msg == true){
                    this.json.splice(index, 1)
                }
            }
        }

    });

</script>
<script>
    $(function() {
        $("#am-nav li").click(function() {
            $(".tab_list li").eq($(this).index()).show().siblings().hide();
        })
    });
    function selectRoleByname(){
        var roleName=$("#roleName").val();
        var index = layer.open({type:3});
        $.ajax({
            url: "/sys/selectRoleByname",
            type:'POST',
            async:false,
            data:{"roleName":roleName},
            dataType:"json",
            error:function(){
                layer.close(index);
                layer.msg("出错了！");
            },
            success: function(data){
                layer.close(index);
                vm.$set("json",data);
            }
        });
    }
    function selectGroup(checkbox, obj) {
        if ($(checkbox).prop('checked')) {
            $('input[name=' + obj + ']').prop('checked', true);
        } else {
            $('input[name=' + obj + ']').prop('checked', false);
        }
    }

</script>

<!--   //编辑-->
<div class="tab_wu" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">编辑角色</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" style="margin-top: 20px;">
        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">角色名称：</label>
            <label  id="edtrolename" class="am-u-sm-2 am-form-label" style="color: #14a6ef;float: left;"></label>
            <input id="updataroleId" type="hidden" >
        </div>

        <div class="am-form-group">
            <label  class="am-u-sm-2 am-form-label">权限分配：</label>
        </div>
        <div>
            <ul id="quanxian1" class="ztree"></ul>
        </div>
    </form>
    <div class="am-form-group">
        <button  class="am-btn am-btn-warning" style="width: 120px;margin-left: 120px;" onclick="updataRole();">确认修改</button>
        <button  class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>
</div>

<script>
    function hides() {
        $('.admin-content-body').show();
        $('.tab_wu').hide();
    }
    function shows(roleID,rolename) {
        $("#edtrolename").html(rolename);
        $("#updataroleId").val(roleID);
        var index = layer.open({type:3});
        $.ajax({
            url: "/sys/getRoleMenus",
            type:'POST',
            async:false,
            data:{"roleID":roleID},
            dataType:"json",
            error:function(){
                layer.close(index);
                layer.msg("出错了！");
            },
            success: function(data){
                layer.close(index);
                $.fn.zTree.init($("#quanxian1"), setting, data);
            }
        });
        $('.admin-content-body').hide();
        $('.tab_wu').show();
    };
    $(function() {
        $("#am-nav1 li").click(function() {
            $(".tab_list1 li").eq($(this).index()).show().siblings().hide();
        })
    });
    function toAddRole(){
        var treeObj = $.fn.zTree.getZTreeObj("quanxian");
        var roleNmae = $("#roleNmae").val();
        var nodes = treeObj.getCheckedNodes(true);
        var menusId ='';
        $.each(nodes,function(i,item){
            menusId+=item.id;
            menusId+=",";
        });

        if(roleNmae==''){
            layer.tips("角色名称不能为空！","#roleNmae",1);
            return;
        }

        var index = layer.open({type:3});
        $.ajax({
            url: "/sys/isExistRole",
            type:'POST',
            async:false,
            data:{"rolename":roleNmae},
            dataType:"text",
            error:function(){
                layer.close(index);
                layer.msg("出错了！");
            },
            success: function(data){
                if("T"==data){
                    layer.close(index);
                    layer.tips("角色名称已存在！","#roleNmae",1);
                }else {
                    $.ajax({
                        url: "/sys/addRole",
                        type:'POST',
                        async:false,
                        data:{"rolename":roleNmae,"menusId":menusId},
                        dataType:"text",
                        error:function(){
                            layer.close(index);
                            layer.msg("出错了！");
                        },
                        success: function(data){
                            layer.close(index);
                            if("T"==data){
                                layer.msg("添加成功");
                            }else {
                                layer.msg("添加失败");
                            }
                        }
                    });
                }
            }
        });

    }

    function updataRole(){
        var treeObj = $.fn.zTree.getZTreeObj("quanxian1");
        var nodes = treeObj.getCheckedNodes(true);
        var roleID = $("#updataroleId").val();
        var menusId ='';
        $.each(nodes,function(i,item){
            menusId+=item.id;
            menusId+=",";
        });
        var index = layer.open({type:3});
        $.ajax({
            url: "/sys/updateRole",
            type:'POST',
            async:false,
            data:{"roleID":roleID,"menusId":menusId},
            dataType:"text",
            error:function(){
                layer.close(index);
                layer.msg("出错了！");
            },
            success: function(data){
                layer.close(index);
                if("T"==data){
                    layer.msg("修改成功");
                    setTimeout("window.location.reload(true)",2000);
                }else {
                    layer.msg("修改失败");
                }
            }
        });
    }

    function delRole(roleID,rolename){
        var msg ="删除角色将导致该角色用户无法使用相应后台功能，是否确认删除？";
        layer.confirm(msg, {
            btn: ['确定删除','取消'] //按钮
        }, function(){
            var index = layer.open({type:3});
            $.ajax({
                url: "/sys/delRole",
                type:'POST',
                async:false,
                data:{"roleID":roleID},
                dataType:"text",
                error:function(){
                    layer.close(index);
                    layer.msg("出错了！");
                },
                success: function(data){
                    layer.close(index);
                    if("T"==data){
                        layer.msg("删除成功", {icon: 1});
                        setTimeout("window.location.reload(true)",2000);
                    }else {
                        layer.msg("删除失败", {icon: 2});
                    }
                }
            });
        }, function(){
            layer.closeAll();
        });
    }

</script>
</body>
</html>

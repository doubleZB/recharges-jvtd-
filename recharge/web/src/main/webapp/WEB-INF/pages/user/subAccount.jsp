<%--
  Created by IntelliJ IDEA.
  User: 商户子账户查询
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
                <li class="am-active"><a href="#tab1">商户子账户查询</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form"  style="margin-top:0;">
                        <div class="am-form-group" style="margin-top:0;">
                            <input type="hidden"  name="id" id="userId" value="${userId}" style="width: 200px;float: left;">
                            <input type="hidden"  name="id" id="userCnNames" value="${userCnName}" style="width: 200px;float: left;">
                            <input class="am-form-field" id="userZhName" placeholder="主账户简称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group" style="margin-top:0;">
                            <input class="am-form-field" type="text" id="userCnName" placeholder="子账户简称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group" style="margin-top:0;">
                                <select class="am-form-field" id="status" style="width: 200px;float: left;">
                                    <option value="">状态</option>
                                    <option value="1">开启</option>
                                    <option value="0">关闭</option>
                                </select>
                        </div>
                        <button class="am-btn am-btn-warning" type="submit" style="width: 120px;" onclick="reloadPage(1)">查询</button>
                    </div>
                    <hr>
                    <div style="width: 100%;">
                        <table class="am-table am-table-striped" >
                            <tr>
                                <td>子账户ID</td>
                                <td>商户简称</td>
                                <td>用户名</td>
                                <td>绑定手机号</td>
                                <td>归属主账户</td>
                                <td>联系人</td>
                                <td>联系人手机</td>
                                <td>创建时间</td>
                                <td>余额</td>
                                <td>状态</td>
                            </tr>
                            <tr v-for="item in json">
                                <td>{{item.id}}</td>
                                <td >{{item.userCnName}}</td>
                                <td>{{item.userName}}</td>
                                <td>{{item.mobile}}</td>
                                <td>{{item.userAllName}}</td>
                                <td>{{item.contacts}}</td>
                                <td>{{item.contactsMobile}}</td>
                                <td>{{item.registerTimeFormat}}</td>
                                <td>{{item.userBalance}}</td>
                                <td>{{item.status==1?"开启":item.status==0?"关闭":""}}</td>
                            </tr>
                        </table>
                    </div>

                    <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

                    <hr>
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


</body>
<script>
    $(function(){
        $("#userZhName").val($("#userCnNames").val());
    })
    var dataAll = {
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    var userId=$("#userId").val();
    $.ajax({
        url: "/user/selectUserSunList?pageNumber=1&pageSize=10",
        type:'POST',
        dataType:"json",
        async:false,
        data:{
            userId:userId
        },
        error:function(){
        },
        success: function(data){
            if(data.list.length>0){
                $(".sj").hide();
                dataAll.json = data.list;
                dataAll.page.ts = data.total;
                dataAll.page.dq = data.pageNum;
                dataAll.page.all = data.pages;
            }else{
                dataAll.json = data.list;
                dataAll.page.ts = 0;
                dataAll.page.dq = 0;
                dataAll.page.all = 0;
                $(".sj").show();
            }
        }
    });

    var vm = new Vue({
        el: "#content",
        data:dataAll
    });
    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var userCnName=$("#userCnName").val();
        var userZhName=$("#userZhName").val();
        var status=$("#status").val();
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "/user/selectUserSunList?pageNumber="+pageNum+"&pageSize=10",
            type:'POST',
            dataType:"json",
            async:false,
            data:{
                userCnName:userCnName.trim(),
                userAllName:userZhName.trim(),
                status:status
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
</html>

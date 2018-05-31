<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/11
  Time: 9:30
  To change this template use File | Settings | File Templates.
  商户认证管理
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <title>商户认证管理</title>
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

    .checklist > div {
        float: left;
        display: block;
        margin-left: 10px;
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
<div class="admin-content" id="content" v-cloak style="font-size: 1.4rem;">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">商户认证管理</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <div class="am-form-inline" role="form">
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" class="am-form-field"  id="userName" placeholder="商户账号" style="width: 200px;float: left;">
                </div>
                <div class="am-form-group">
                    <select class="select1" id="userType" style="width: 200px;float: left;">
                        <option value="">商户类型</option>
                        <option value="1">企业</option>
                        <option value="2">个人</option>
                    </select>
                </div>
                <div class="am-form-group">
                    <select class="select1" id="authState" style="width: 200px;float: left;">
                        <option value="">认证状态</option>
                        <option value="1">未认证</option>
                        <option value="2">待审核</option>
                        <option value="3">已认证</option>
                        <option value="4">认证未通过</option>
                    </select>
                </div>
                <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-top: 20px" onclick="query()">查询
                </button>
            </div>
            <hr>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>商户ID</th>
                    <th>商户账号</th>
                    <th>商户类型</th>
                    <th>公司全称/姓名</th>
                    <th>认证状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td>{{item.id}}</td>
                    <td>{{item.userName}}</td>
                    <td>{{item.userType==1?"企业":item.userType==2?"个人":" "}}</td>

                    <td v-if="item.name==null">{{item.userAllName}}</td>
                    <td v-if="item.name!=null">{{item.name}}</td>

                    <td>{{item.authState==4?"认证未通过":item.authState==3?"已认证":item.authState==2?"待审核":item.authState==1?"未认证":" "}}</td>

                    <td v-if="item.userType==1"><a onclick="firmAttestation('{{item.id}}')" href="####">查看</a></td>
                    <td v-if="item.userType==2"><a onclick="personageAttestation('{{item.id}}')" href="#####">查看</a></td>
                </tr>
                </tbody>
            </table>

            <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

            <hr>
            <div class="am-cf">
                每页显示&nbsp;
                <select name="select" id="pageNumOne" onchange="query()">
                    <option value="10">10</option>
                    <option value="20">20</option>
                    <option value="50">50</option>
                </select>
                &nbsp;条&nbsp;&nbsp;共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                    <div class="am-fr">
                        <ul class="am-pagination" style="margin: 0">
                            <li><a href="javascript:void(0);" onclick="reloadPage('{{page.dq-1}}','{{page.sz}}');">上一页</a></li>
                            <li class="am-disabled"><a
                                    href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                            <li><a href="javascript:void(0);"  onclick="reloadPage('{{page.dq+1}}','{{page.sz}}')">下一页</a></li>
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
    <!-- content end -->
</div>
<script>

    //企业
    function firmAttestation(authId){
        location.href="${path}/user/firmAttestation.do?id="+authId;
    }
    //个人
    function personageAttestation(authId){
        location.href="${path}/user/personageAttestation.do?id="+authId;
    }

    var dataAll = {
        json: [],
        page: {
            ts: "",
            dq: "",
            all: "",
            sz:""
        }
    };

        $.ajax({
            url: "/user/selectUserAuthList?pageNumber=1&pageSize=10",
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
                dataAll.page.sz = data.pageSize;
                dataAll.page.all = data.pages;
            }
        });
    var vm = new Vue({
        el: "#content",
        data: dataAll
    })


    function query(){
        var pageNumOne= $("#pageNumOne").val();
        reloadPage(1,pageNumOne);
    }

    function reloadPage(pageNum,pageSize){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var userName=$("#userName").val();
        var userType=$("#userType").val();
        var authState=$("#authState").val();
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "/user/selectUserAuthList?pageNumber="+pageNum+"&pageSize="+pageSize,
            type:'POST',
            dataType:"json",
            async:false,
            data:{
                authState:authState,
                userName:userName.trim(),
                userType:userType
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
                    dataAll.page.sz = data.pageSize;
                    dataAll.page.all = data.pages;
                }else{
                    layer.close(loadingIndex);
                    dataAll.json = data.list;
                    dataAll.page.ts = 0;
                    dataAll.page.dq = 0;
                    dataAll.page.sz = 0;
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
</body>

</html>

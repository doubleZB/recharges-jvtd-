<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: 商户加款审核 lihuimin
  Date: 2016/11/12
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<style>
    *{
        -moz-user-select: text !important;
        -webkit-user-select: text !important;
        -ms-user-select: text !important;
        user-select: text !important;
    }
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
<div class="admin-content" id="content" style="margin-top: 0;">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <div class="am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top: 0;">
                    <div class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input type="text" name="userName" id="user_name" class="am-form-field" placeholder="商户名称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <select class="select1" name="addType" id="add_type" style="width: 200px;float: left;padding: .5em;" placeholder="加款类型">
                                <option value="">加款类型</option>
                                <option value="1">实收款</option>
                                <option value="2">借款</option>
                                <option value="3">还款</option>
                            </select>
                        </div>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;"onclick="reloadPage(1)" >查询</button>
                    </div>

                    <hr>
                    <div class="am-u-sm-12 am-u-md-6" style="margin-bottom:20px;padding-left: 0;">
                        <input type="hidden" name="adminId" id="adminId" value="${adminLoginUser.id}"/>
                        <input type="hidden" name="operate" id="operate" value="${adminLoginUser.adminName}">
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;" onclick="reviewedAdd()">审核通过</button>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;" onclick="reviewedDelete()">审核不通过</button>
                    </div>

                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>
                                <label class="am-checkbox-inline">
                                    <input style="margin-right:4px;" type="checkbox" class="checkall" @click="checked()">
                                </label>
                                序号</th>
                            <th>商户名称</th>
                            <th>加款类型</th>
                            <th>加款/借款金额</th>
                            <th>收款账户</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" name='checkbox' value="{{item.id}}" v-model="checkID" @click="clearcheck()" />{{item.id}}</label>
                            </td>
                            <td>{{item.userName}}</td>
                            <td>{{item.addType==1?"实收款":item.addType==2?"借款":item.addType==3?"还款":""}}</td>
                            <td>{{item.amount}}</td>
                            <td>{{item.receiveUser==3?"支付宝bjwwl779@126.com":item.receiveUser==2?"招商银行**** **** **** 0536":item.receiveUser==1?"浦发银行北京西直门支行":""}}</td>
                            <td>{{item.remark}}</td>
                        </tr>
                        </tbody>
                    </table>

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
                                    <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage()"  style="padding: .5rem .4rem;">GO</a></li>
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
    Array.prototype.unique = function () {
        this.sort();
        var re = [this[0]];
        for (var i = 1; i < this.length; i++) {
            if (this[i] !== re[re.length - 1]) {
                re.push(this[i]);
            }
        }
        return re;
    };
    var arr = [];
    var dataAll = {
        json: [],
        checkID:[],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    $.ajax({
        url: "/user/recordlist?pageNumber=1&pageSize=10",
        type:'POST',
        async:false,
        dataType:"json",
        error:function(){
            $(this).addClass("done");
        },
        success: function(data){
            dataAll.json = data.list;
            dataAll.page.ts = data.total;
            dataAll.page.dq = data.pageNum;
            dataAll.page.all = data.pages;
        }
    });
    var vm = new Vue({
        el: "#content",
        data:dataAll,
        methods:{
            checked: function () {
                if ($(".checkall").prop("checked")) {
                    $("input[name='checkbox']").prop("checked", true);
                    for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                        arr.push($("input[name='checkbox']:checked").eq(i).val());
                    }
                    this.checkID = arr.unique();
                } else {
                    this.checkID = [];
                    $("input[name='checkbox']").prop("checked", false);
                }
            },
            clearcheck:function(){
                if ($("input[name='checkbox']:checked").length == this.json.length) {
                    $(".checkall").prop("checked", true);
                } else {
                    $(".checkall").prop("checked", false);
                }
            }
        }
    });

    function reloadPage(pageNum){
        $(".checkall").prop("checked", false);
        dataAll.checkID = [];
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var user_name=$("#user_name").val();
        var add_type=$("#add_type").val();
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "/user/recordlist?pageNumber="+pageNum+"&pageSize=10",
            type:'POST',
            dataType:"json",
            async:false,
            data:{
                userName:user_name,
                addType:add_type
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
        var pagenum = $("#gotoPage").val();
        if(pagenum==''){
            layer.tips("请输入页数！","#gotoPage",{tips: 3});
            return;
        }
        reloadPage(pagenum);
    }
</script>
<script type="text/javascript">
    $('tbody .td_img').click(function() {
        $('#imgFrame').show();
        var _src = $(this).find('img').attr('src');
        $('#imgbox img').attr('src', _src);
    });

    $('#imgFrame').click(function() {
        $('#imgFrame').hide();
    });

    //审核通过
    function reviewedAdd(){
        var len = $("input[name='checkbox']:checked").length;
        if (len == 0) {
            alert("请选择想要修改的序号！");
        }else{
            var check=dataAll.checkID.toString();
            var adminid = $("#adminId").val();
            var operate = $("#operate").val();
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url: "${pageContext.request.contextPath}/user/Addrecordlist.do",
                type:'POST',
                dataType:"text",
                async:false,
                data:{
                    'ids':check,
                    adminId:adminid,
                    operate:operate.trim()
                },
               error:function(){
                },
                success: function(obj){
                    layer.close(loadingIndex);
                    alert(obj);
                    location.reload();
                }
            });
        }
    }

    //审核不通过
    function reviewedDelete(){
        var len = $("input[name='checkbox']:checked").length;
        var operate = $("#operate").val();
        if (len == 0) {
            alert("请选择想要修改的序号！");
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url: "${pageContext.request.contextPath}/user/Deletefundslist.do?operate="+operate,
                type:'POST',
                dataType:"json",
                async:false,
                data:{
                    id:dataAll.checkID.toString()
                },
                error:function(){
                },
                success: function(obj){
                    layer.close(loadingIndex);
                    if(obj){
                        alert("NO!");
                        location.reload();
                    }else{
                        alert("OK!");
                        location.reload();
                    }
                }
            });
        }

    }
</script>
</html>

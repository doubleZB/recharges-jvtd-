<%--
  Created by 商户应用管理新 IDEA.
  User: lyp
  Date: 2017/6/1
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${path}/static/css/haoduanguanli.css">
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<style>
    .change_btn {
        margin-top: 30px;
    }
    .am-u-sm-2 {
        width: 150px;
    }
    .am-form-label {
        font-weight: normal;
        width: 120px;
        padding-left: 0;
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
    * {
        -moz-user-select: text !important;
        -webkit-user-select: text !important;
        -ms-user-select: text !important;
        user-select: text !important;
    }
    .IptCon{
        width:300px;
        height:30px;
    }
    .IptCon > input{
        width:100%;
    }
    .posi{
        width:300px;
        position: absolute;
        left:0;
        top:32px;
        display: none;
        border:1px solid #ccc;
        background: #fff;
        height: auto;
        max-height: 140px;
        overflow-y: scroll;
    }
    .posi li{
        line-height: 35px;
        cursor: pointer;
    }
    .posi li span{
        padding-left:20px;
    }
    .qwe{
        display: block;
        width:100%;
        height:100%;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">号段管理</a></li>
                <li><a href="#tab2">新增</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input type="hidden" id="userId" value="${userId}">
                            <input type="hidden"  name="userCnName" id="userCnNameTwo" value="${userCnName}">
                            <input  type="text" name="userCnName" id="userCnName" class="am-form-field" placeholder="商户简称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input  type="text" name="userCnName" id="groupCnName" class="am-form-field" placeholder="货架组名称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <select  class="select1" name="appType" id="appType" style="width: 200px;float: left;padding: .5em;" placeholder="开通应用">
                                <option value="">开通应用</option>
                                <option value="1">流量</option>
                                <option value="2">话费</option>
                                <%--<option value="3">短信</option>--%>
                                <option value="4">SDK</option>
                                <option value="5">SDK导流</option>
                                <option value="6">视频会员</option>
                            </select>
                        </div>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;" onclick="selectUserApp(1)">查询</button>
                    </form>
                    <div class="change_btn">
                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin:auto;margin-top: 0px" onclick="batchUpdate()">修改适用货架
                        </button>
                    </div>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <thead>
                        <tr>
                            <th>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" class="checkall" @click="checked"> 序号
                                </label>
                            </th>
                            <th>商户ID</th>
                            <th>商户简称</th>
                            <th>开通应用</th>
                            <th>使用货架</th>
                            <th>开通时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json" id="trlist">
                            <td>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" value="{{item.id}},{{item.appType==1?'流量':item.appType==2?'话费':item.appType==3?'短信':item.appType==4?'SDK':'SDK导流'}}" name="checkbox" v-model="checkID" @click="listCheck">
                                    {{$index+1}}
                                </label>
                            </td>
                            <td>{{item.userId}}</td>
                            <td>{{item.userCnName}}</td>
                            <td><a href="#" onclick="Application('{{item.id}}')">{{item.appType==1?"流量":item.appType==2?"话费":item.appType==3?"短信":item.appType==4?"SDK":item.appType==5?"SDK导流":item.appType==6?"视频会员":""}}</a></td>
                            <td>{{item.groupId}}</td>
                            <td>{{item.updateTime.substring(0,19)}}</td>
                        </tr>
                        </tbody>
                    </table>

                    <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

                    <hr/>
                    <div class="am-cf">
                        每页显示&nbsp;
                        <select name="select" id="rangNum" onchange="rangNum()">
                            <option value="0">10</option>
                            <option value="1">20</option>
                            <option value="2">50</option>
                            <option value="3">100</option>
                        </select>&nbsp;条&nbsp;&nbsp;共 <span id="countNum">{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="javascript:void(0);" onclick="selectUserApp('{{page.dq-1}}');">上一页</a></li>
                                <li class="am-disabled"><a href="#"><span> {{page.dq}}</span>/<span>{{page.all}}</span></a></li>
                                <li><a href="javascript:void(0);"  onclick="selectUserApp('{{page.dq+1}}');">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>

                                <li class="am-active"><a href="#" style="padding: .5rem .4rem;" onclick="gotoPage()">GO</a></li>
                                <li>
                                    <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                </li>
                                <li class="am-disabled"><a href="#">页</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">商户简称：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="userCnName" id="userCnNames"  onblur="checkUserCnName()" placeholder="填写简称2~10汉字">
                                <span class="msg_name"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">开通应用：</label><em>*</em>
                            <select style="width: 300px;float: left;" name="appType" id="appTypes"  onchange="checkAppType()">
                                <option value="">选择开通应用</option>
                                <option value="1">流量</option>
                                <option value="2">话费</option>
                                <%--<option value="3">短信</option>--%>
                                <option value="4">SDK</option>
                                <option value="5">SDK导流</option>
                                <option value="6">视频会员</option>
                            </select>
                            <span class="msg_type"></span>
                        </div>
                        <div class="am-form-group">
                            <label  class="am-u-sm-2 am-form-label">适用货架：</label><em>*</em>
                            <div style="position: relative;float:left;">
                                <div class="IptCon">
                                    <input type="text" v-model="searchQuery" class="searchIpt">
                                </div>
                                <div class="posi" style="padding: 0;">
                                    <simple-grid :data-list="people" :columns="columns" :search-key="searchQuery">
                                    </simple-grid>
                                </div>
                            </div>
                            <template  id="grid-template">
                                <ul style="padding: 0;margin: 0;">
                                    <li v-for="(index,entry) in dataList | filterBy searchKey">
                                        <span v-for="col in columns" value="{{entry[col.ind]}}" class="qwe">{{entry[col.name]}}</span>
                                    </li>
                                </ul>
                            </template>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">充值方式：</label><em>*</em>
                            <div style="width: 300px;float: left;" id="chargeType">
                                <p style="display:inline-block;margin:9px 30px 6px 0;">
                                    <input type="radio" name="chargeType" value="1" style="margin-right: 5px;">接口
                                </p>
                                <p style="display:inline-block;margin:9px 30px 6px 0;">
                                    <input type="radio" name="chargeType"  value="2" style="margin-right: 5px;">页面
                                </p>
                                <p style="display:inline-block;margin:9px 30px 6px 0;">
                                    <input type="radio" name="chargeType"  value="3" style="margin-right: 5px;">接口和页面
                                </p>
                            </div>
                        </div>
                        <div>
                            <label class="am-u-sm-2 am-form-label">是否允许缓存：</label><em>*</em>
                            <div style="width: 300px;float: left;" id="isCache">
                                <p style="display:inline-block;margin:9px 30px 6px 0;">
                                    <input type="radio" name="isCache" checked="checked" value="1" style="margin-right: 5px;">允许
                                </p>
                                <p style="display:inline-block;margin:9px 30px 6px 0;">
                                    <input type="radio" name="isCache"  value="2" style="margin-right: 5px;">不允许
                                </p>
                            </div>
                        </div>
                    </form>
                    <div style="clear:both">
                        <button  class="am-btn am-btn-warning" id="Add"
                                 style="width: 120px;margin: auto;margin-left: 176px;" onclick="insertApp()">提交
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
    <!--修改试用货架弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">查看应用
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">开通应用：</label>
                        <select style="width: 300px;float: left;" name="appType" id="appTypeTwo" disabled="disabled">
                            <option value="1">流量</option>
                            <option value="2">话费</option>
                            <%--<option value="">短信</option>--%>
                            <option value="4">SDK</option>
                            <option value="5">SDK导流</option>
                        </select>
                    </div>
                    <div class="am-form-group">
                        <label  class="am-u-sm-2 am-form-label">适用货架：</label><em>*</em>
                        <div style="position: relative;float:left;">
                            <div class="IptCon">
                                <input type="text" v-model="searchQuery" class="searchIpt">
                            </div>
                            <div class="posi" style="padding: 0;">
                                <simple-grid :data-list="people" :columns="columns" :search-key="searchQuery">
                                </simple-grid>
                            </div>
                        </div>
                        <template  id="grid-template">
                            <ul style="padding: 0;margin: 0;">
                                <li v-for="(index,entry) in dataList | filterBy searchKey">
                                    <span v-for="col in columns" value="{{entry[col.ind]}}">{{entry[col.name]}}</span>
                                </li>
                            </ul>
                        </template>
                    </div>
                    <hr>
                </form>
                <div class="am-form-group">
                    <div>
                        <button type="button" class="am-btn am-btn-warning " style="width: 120px;margin: auto;" onclick="batchSubmit()">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/assets/js/amazeui.min.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/js/vue-resource.js"></script>
<script>
    var inds;
    $(".posi").on("click",$(".posi li span"),function(e){
        var strNode=e.target.innerHTML.split("li");
        if(strNode.length>2){
            return false;
        };
        e.stopPropagation();
        $(".IptCon>input").val(e.target.innerHTML);
        $(".posi").css("display","none");
        inds=e.target.getAttribute("value");
    });
    $(document).click(function(e){
        e.stopPropagation();
        $(".posi").css("display","none");
    })
    $(".searchIpt").click(function(e){
        e.stopPropagation();
    })
    $(".searchIpt").focus(function(){
        $(".posi").css("display","block");
    });
    Vue.component('simple-grid', {
        template: '#grid-template',
        props: ['dataList', 'columns', 'searchKey'],
    })


    $(function(){
        getGroupIds();
        var userCnNameTwo = $("#userCnNameTwo").val();
        $("#userCnName").val(userCnNameTwo);
        if(userId==""){
            $("#userCnName").val("");
        }
        selectUserApp(1);
    });
    function Application(id){
        location.href="${pageContext.request.contextPath}/user/application.do?id="+id;
    }
    var i;
    var vm = new Vue({
        el: "#content",
        data: {
            checkID: [],
            options1: [
                {text: '运营商', value: ''},
                {text: '移动', value: '1'},
                {text: '联通', value: '2'},
                {text: '电信', value: '3'}
            ],
            json: [

            ],
            searchQuery: '',
            columns: [{
                name: 'name',
                ind:'id'
            }],
            people: [],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        },
        ready: function () {
        },
        methods: {
            checked: function () {
                if ($(".checkall").prop("checked")) {
                    $("input[name='checkbox']").prop("checked", true);
                    for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                        this.checkID.push($("input[name='checkbox']:checked").eq(i).val());
                    }
                    this.checkID = this.checkID.unique();
                } else {
                    this.checkID = [];
                    $("input[name='checkbox']").prop("checked", false);
                }
            },
            listCheck: function () {
                if ($("input[name='checkbox']:checked").length == $("input[name='checkbox']").length) {
                    $(".checkall").prop("checked", true);
                } else {
                    $(".checkall").prop("checked", false);
                }
            },
        }
    });
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


    /**
     * 查询货架组名称
     * @type {string}
     */
    var GroupNames="";
    function getGroupIds(){
        $.ajax({
            url: "/user/getGroupId",
            type:'POST',
            dataType:"json",
            async:false,
            success: function(data){
                GroupNames=data;
            }
        });
    }
    /**
     * 数据查询
     * @param param
     */
    function selectUserApp(param,param2){
        $(".checkall").prop("checked", false);
        vm.checkID = [];
        var pageNumber = param;
        var num = $("#rangNum").val();
        var nums="";
        if(num==0){
            nums=10;
        }else if(num==1){
            nums=20;
        }else if(num==2){
            nums=50;
        }else if(num==3){
            nums=100;
        }
        if(param2==undefined){
            param2=nums;
        }
        var pageSize = param2;
        var countNum = $("#countNum").val();


        var userCnName=$("#userCnName").val();
        var appType=$("#appType").val();
        var groupCnName=$("#groupCnName").val();
        var loadingIndex = layer.open({type:3});
        var obj="";
        $.ajax({
            url: "${pageContext.request.contextPath}/user/userAppList",
            async:false,
            type:'POST',
            data:{userCnName:userCnName.trim(), appType:appType, groupCnName:groupCnName.trim(), "pageNumber":pageNumber,"pageSize":pageSize},
            dataType:"json",
            success: function(data){
                if(data.list.length>0){
                    $(".sj").hide();
                    layer.close(loadingIndex);
                    var obj=data.list;
                    $(obj).each(function (index) {
                        $(GroupNames).each(function (i){
                            if(data.list[index].groupId==GroupNames[i].id){
                                data.list[index].groupId=GroupNames[i].name;
                            }
                        });
                    });
                    vm.json = data.list;
                    vm.page.ts = data.total;
                    vm.page.dq = data.pageNum;
                    vm.page.all = data.pages;
                }else{
                    layer.close(loadingIndex);
                    vm.json = data.list;
                    vm.page.ts = 0;
                    vm.page.dq = 0;
                    vm.page.all = 0;
                    $(".sj").show();
                }
            }
        });
    };
    //跳页
    function gotoPage(){
        var pagenum = $("#goto-page-num").val();
        if(pagenum==""){
            layer.msg("输入跳转页数");
            return;
        }else {
            selectUserApp(pagenum);
        }
    }
    /**
     * 选择现实多少行
     */
    function rangNum(){
        var num = $("#rangNum").val();
        var nums="";
        if(num==0){
            nums=10;
        }else if(num==1){
            nums=20;
        }else if(num==2){
            nums=50;
        }else if(num==3){
            nums=100;
        }
        var fist = 1;
        selectUserApp(fist,nums);
    }

    $("input[name='chargeType']:eq(0)").attr("checked",true);
    $(".msg_name").css("color","red");
    $(".msg_type").css("color","red");
    //追加下拉框货架
    var groupId = $(".groupId").val();
    $.ajax({
        url:"${pageContext.request.contextPath }/user/toAddGroup.do",
        type:"post",
        data:{
            id:groupId
        },
        dataType:"json",
        success:function(obj){
            for (var i in obj) {
                $(".groupId").append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
            }
            vm.people=obj;
        }
    });

    var userId;
    //验证商户简名称
    function checkUserCnName(){
        var userCnNames=$("#userCnNames").val();
        var cnName = /^[\u4e00-\u9fa5]{2,10}$/;
        if(cnName.test(userCnNames.trim())){
            $.ajax({
                url:"${pageContext.request.contextPath }/user/OnlyName.do",
                type:"post",
                data:{
                    userCnName:userCnNames.trim()
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        userId = data[0].id;
                        $(".msg_name").text("");
                        $('#Add').attr('disabled',false);
                    }else{
                        $(".msg_name").text("该商户不存在，请重新输入！");
                        $('#Add').attr('disabled',true);
                        return;
                    }
                }
            });
        }else if(userCnNames.trim() == ""){
            $(".msg_name").text("请输入商户简称");
            $('#Add').attr('disabled',true);
        }else{
            $(".msg_name").text("请正确填写简称2~10汉字");
            $('#Add').attr('disabled',true);
        }
    }
    //验证商户应用
    function checkAppType(){
        var appTypes =$("#appTypes").val();
        if(appTypes==""){
            $(".msg_type").text("请选择商户应用！");
            $('#Add').attr('disabled',false);
            return;
        }else{
            $.ajax({
                url:"${pageContext.request.contextPath }/user/userAppByUserAppType.do",
                type:"post",
                data:{
                    appType:appTypes,
                    userId:userId
                },
                dataType:"json",
                success:function(data){
                    if(data){
                        $(".msg_type").text("该商户已经开通过此应用，请去修改该商户应用！");
                        $('#Add').attr('disabled',true);
                    }else{
                        $(".msg_type").text("");
                        $('#Add').attr('disabled',false);
                    }
                }
            });
            $(".msg_type").text("");
            $('#Add').attr('disabled',false);
        }
    }
    //添加应用
    function insertApp(){
        var appType=$("#appTypes").val();
        var chargeType = $("input[name='chargeType']:checked").val();
        var isCache = $("input[name='isCache']:checked").val();
        if( $("#userCnNames").val().trim()==""  ){
            alert("商户简称不能为空！");
            return;
        }else if( $("#appTypes").val()==""){
            alert("请选择开通应用！");
            return;
        }else if($(".IptCon>input").val()==""){
            inds=0;
            if(inds==0){
                layer.msg("请选择货架组");
                return;
            }
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/user/addUserApp.do",
                type:"post",
                data:{
                    userId:userId,
                    appType:appType,
                    groupId:inds,
                    chargeType:chargeType,
                    isCache:isCache
                },
                dataType:"json",
                success:function (data){
                    layer.close(loadingIndex);
                    if(data){
                        alert("恭喜你添加成功！");
                        $("#userCnNames").val("");
                        $("#appTypes").val("");
                        $(".groupId").val("");
                        $("input[name=chargeType][value=´1´]").attr("checked",true);
                        $("input[name=isCache][value=´1´]").attr("checked",true);
                        location.reload();
                    }else{
                        alert("抱歉添加失败！");
                        location.reload();
                    }
                }
            });
        }
    }

    //批量修改货架应用
    var addID= [] ; //应用id
    function batchUpdate(){
        var len = $("input[name='checkbox']:checked").length;
        if(len== 0){
            alert("请选择想要修改的数据！")
        }else{
            var check = vm.checkID.toString().split(',');
//            alert(check);
            var type= [];//；类型
            var ll= 0, hf =0;
            addID = [];
            for (var i=0;i<check.length ;i++ ){
                //匹配字符串中相同的
                if(check[i].indexOf("流量")>=0){
                    ll++;
                    type=1;
//                    console.log("流量类型"+type);
                }else if(check[i].indexOf("话费")>=0){
                    hf++;
                    type=2;
//                     console.log("话费类型"+type);
                }else{;
                    addID.push(check[i]);
                }
            }
//            console.log("应用id"+addID)
            if(ll>0&&hf>0){
                console.log("error")
                alert("请选择相同类型的应用！")
            }else{
                console.log("success")
                if(type==1){
                    $("#appTypeTwo").val(1);
                    $("#your-modal").modal();
                }else if(type==2){
                    $("#appTypeTwo").val(2);
                    $("#your-modal").modal();
                }
            }
        }
    }

    //批量修改货架应用提交
    function batchSubmit(){
        var appTypeTwo = $("#appTypeTwo").val();
        if($(".IptCon>input").val()==""){
            inds=0;
            if(inds==0){
                layer.msg("请选择货架组");
                return;
            }
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/user/updateUserApp.do",
                type:"post",
                data:{
                    appId:addID.toString(),
                    groupId:inds,
                    appType:appTypeTwo
                },
                dataType:"json",
                success:function (data){
                    layer.close(loadingIndex);
                    if(data){
                        alert("恭喜你修改成功！");
                        $(".groupId").val("");
                        location.reload();
                    }else{
                        alert("抱歉修改失败！");
                        location.reload();
                    }
                }
            });
        }
    }
</script>
</body>
</html>

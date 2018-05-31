<%--
  Created by IntelliJ IDEA.
  User: lhm商户修改应用
  Date: 2017/1/22
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<body>
<style>
    *{
        -moz-user-select: text !important;
        -webkit-user-select: text !important;
        -ms-user-select: text !important;
        user-select: text !important;
    }
    .am-form-label {
        font-weight: normal;
        width: 120px;
        padding-left: 0;
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
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0" style="margin-top: 20px;margin-bottom: 20px;">
                <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg" style="font-size:14px; ">商户修改应用</strong>
                </div>
            </div>
            <div class=" am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">商户简称：</label><em>*</em>
                            <div style="width: 300px;float: left;">
                                <input type="text" name="userCnName" id="userCnName" placeholder="填写简称2~6汉字" disabled="disabled">
                                <span class="msg_name"></span>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">开通应用：</label><em>*</em>
                            <select style="width: 300px;float: left;" name="appType" id="appType" disabled="disabled">
                                <option value="">开通应用</option>
                                <option value="1">流量</option>
                                <option value="2">话费</option>
                                <%--<option value="3">短信</option>--%>
                                <option value="4">SDK</option>
                                <option value="5">SDK导流</option>
                                <option value="6">视频会员</option>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label  class="am-u-sm-2 am-form-label">适用货架：</label><em>*</em>
                            <div id="app" style="position: relative;float:left;">
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
                                    <input type="radio" name="chargeType" checked="checked" value="1" style="margin-right: 5px;">接口
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
                                 style="width: 120px;margin: auto;margin-left: 176px;" onclick="updateApp()">提交
                        </button>
                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin:auto;margin-top: 0px;margin-left: 20px;" onclick="goBack()">返回
                        </button>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2"></div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
</body>
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
    var demo = new Vue({
        el: '#app',
        data: {
            searchQuery: '',
            columns: [{
                name: 'name',
                ind:'id'
            }],
            people: [],
        }
    })





    //返回
    function goBack(){
        location.href="${pageContext.request.contextPath }/user/userApp.do";
    }
    //回显
    var id = "${userApp.id}";
    $(function (){
        $.ajax({
            url:"${pageContext.request.contextPath }/user/updateApplication.do",
            type:"POST",
            data:{id:id},
            dataType:"json",
            success:function (obj){
                var userApp = obj.userApp;
                var list = obj.list;
//                console.log(list[3]);
                var str;
                for(var i=0;i<list.length;i++){
                    (function(i){
                        if(list[i].id==userApp.groupId){
                            str=list[i].name;
                        }
                    })(i)
                }
                 $(".searchIpt").val(str);
                demo.people=list;
                $("[name='id']").val(userApp.id);
                $("[name='userCnName']").val(userApp.userCnName);
                $("[name='appType']").val(userApp.appType);
//                $("[name='groupId'] option[value='"+userApp.groupId+"']").attr('selected',true);
                inds=userApp.groupId;
                $("[name='chargeType'][value='"+userApp.chargeType+"']").attr("checked", true);
                $("[name='isCache'][value='"+userApp.isCache+"']").attr("checked", true);
            }
        });
    });

    //修改
    function updateApp(){
        var appTypes=$("#appType").val();
        var chargeType = $("input[name='chargeType']:checked").val();
        var isCache = $("input[name='isCache']:checked").val();
        if($(".IptCon>input").val()==""){
            inds=0;
            if(inds==0){
                layer.msg("请选择货架组");
                return;
            }
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url:"${pageContext.request.contextPath }/user/updateApp.do",
                type:"post",
                data:{
                    id:id,
                    appType:appTypes,
                    groupId:inds,
                    chargeType:chargeType,
                    isCache:isCache
                },
                dataType:"json",
                success:function (data){
                    layer.close(loadingIndex);
                    if(data){
                        alert("恭喜你修改成功！");
                        location.href="${pageContext.request.contextPath }/user/userApp.do";
                    }else{
                        alert("抱歉修改失败！");
                        location.reload();
                    }
                }
            });
        }
    }
</script>
</html>

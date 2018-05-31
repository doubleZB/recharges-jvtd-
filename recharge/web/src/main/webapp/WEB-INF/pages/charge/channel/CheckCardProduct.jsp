<%--
  Created by IntelliJ IDEA.
  User: lhm
  Date: 2017/3/27
  Time: 17:04
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
    .checklist1 {
        border: 1px solid #dedede;
        width: 500px;
        overflow: auto;
        padding: 20px;
        padding-top: 5px;
        margin: 10px 120px;
    }

    .checklist1 > div {
        float: left;
        display: block;
        margin-left: 10px;
        margin-top: 10px;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">供应商支持卡品</a></li>
                <input type="hidden" value="${supplyId}" id="supplyId" >
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-btn-group">
                        <button type="button" class="am-btn am-btn-warning am-radius"><input type="text" value="1" id="Move">移动</button>
                        <button type="button" class="am-btn am-btn-warning am-radius" value="2"><input type="text" value="2" id="MoveTwo">联通</button>
                        <button type="button" class="am-btn am-btn-warning am-radius" value="3"><input type="text" value="3" id="MoveThree">电信</button>{{checkId | json}}
                    </div>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <tr>
                            <th>序号</th>
                            <th>面值/流量颗粒</th>
                            <th>产品编码</th>
                            <th>供货商产品编码</th>
                        </tr>
                        <tr v-for="item in json">
                            <td>{{$index+1}}</td>
                            <%--<td>{{item.id}}</td>--%>
                            <td>{{item.packageSize}}</td>
                            <td>{{item.positionCode}}</td>
                            <td>{{item.code}}</td>
                        </tr>
                    </table>

                    <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

                    <hr/>
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
    var dataAll = {
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    var vm = new Vue({
        el: "#content",
        data:dataAll
    });
    var id = $("#supplyId").val();
    $.ajax({
        url: "${pageContext.request.contextPath}/channels/selectChargeSupplyPosition?pageNumber=1&pageSize=10",
        async:false,
        type:'POST',
        data:{"supplyId":id},
        dataType:"json",
        error:function(){
        },
        success: function(data) {
            if(data.list.length>0){
                dataAll.json = data.list;
                dataAll.page.ts = data.total;
                dataAll.page.dq = data.pageNum;
                dataAll.page.all = data.pages;
            }
        }
    });
    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var loadingIndex = layer.open({type:3});
        var id = $("#supplyId").val();
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectChargeSupplyPosition?pageNumber="+pageNum+"&pageSize=10",
            async:false,
            type:'POST',
            data:{"supplyId":id},
            dataType:"json",
            error:function(){
            },
            success: function(data) {
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

    function movement(){
        var move = $("#Move").val();
        alert(move);
    }
</script>
</body>
</html>

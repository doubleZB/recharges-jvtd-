<%--
  Created by IntelliJ IDEA.
  User:  商户加款记录lihuimin
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
    * {
        margin: 0;
        padding: 0;
        list-style: none;
    }

    a,
    a:hover {
        text-decoration: none;
    }

    pre {
        font-family: '微软雅黑'
    }

    .box a {
        padding-right: 20px;
    }

    .demo1,
    .demo2
    {
        margin: 40px 0;

    }
    .demo1 input,
    .demo2 input{
        height: 35px;
        background-image:none;
        font-size: 14px;
        padding-left:10px ;
    }
    h3 {
        margin: 10px 0;
    }

</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" style="margin-top: 0;">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <div class="am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top: 0;">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group demo1">
                            <input class="inline laydate-icon"  name="timeStart" id="update_time" placeholder="开始时间" style="width:200px; margin-right:10px;">
                        </div>
                        <div class="am-form-group demo2">
                            <input class="inline laydate-icon" name="timeEnd" id="update_time1" placeholder="结束时间" style="width:200px;">
                        </div>
                        <div class="am-form-group">
                            <input  type="text" name="userCnName" id="user_cn_name" class="am-form-field" placeholder="商户简称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <select  class="select1" name="addType" id="add_type" style="width: 200px;float: left;padding: .5em;" placeholder="加款类型">
                                <option value="">加款类型</option>
                                <option value="1">实收款</option>
                                <option value="2">借款</option>
                                <option value="3">还款</option>
                            </select>
                        </div>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;" onclick="reloadPage(1)">查询</button>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>商户简称</th>
                            <th>加款类型</th>
                            <th>加款金额</th>
                            <th>余额变动</th>
                            <th>变动后余额</th>
                            <th>借款变动</th>
                            <th>变动后借款</th>
                            <th>收款账户</th>
                            <th>备注</th>
                            <th>审核时间</th>
                            <th>申请人</th>
                            <th>审核人</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>{{item.id}}</td>
                            <td>{{item.userCnName}}</td>
                            <td>{{item.addType==1?"实收款":item.addType==2?"借款":item.addType==3?"还款":""}}</td>
                            <td>{{item.amount}}</td>
                            <td>{{item.balanceChange}}</td>
                            <td>{{item.balanceNow}}</td>
                            <td>{{item.borrowChange}}</td>
                            <td>{{item.borrowNow}}</td>
                            <td>{{item.receiveUser==3?"支付宝bjwwl779@126.com":item.receiveUser==2?"招商银行**** **** **** 0536":item.receiveUser==1?"浦发银行北京西直门支行":""}}</td>
                            <td>{{item.remark}}</td>
                            <td>{{item.updateTime.substring(0,19)}}</td>
                            <td>{{item.proposerName}}</td>
                            <td>{{item.auditorName}}</td>
                            <td></td>
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
<div id="imgFrame">
    <div id="imgbox"> <img src="" /></div>
</div>

</body>
<script>
    var update_time = {
        elem: '#update_time',
        format: 'YYYY-MM-DD ',
        min: '2000-01-01 23:59:59', //设定最小日期为当前日期
        max: '2099-06-16 23:59:59', //最大日期
        istime: true,
        istoday: false,
        choose: function(datas){
            update_time1.min = datas; //开始日选好后，重置结束日的最小日期
            update_time1.update_time = datas //将结束日的初始值设定为开始日
        }
    };
    var update_time1 = {
        elem: '#update_time1',
        format: 'YYYY-MM-DD ',
        min: '2000-01-01 23:59:59',
        max: '2099-06-16 23:59:59',
        istime: true,
        istoday: false,
        choose: function(datas){
            update_time.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(update_time);
    laydate(update_time1);
</script>
<script>
    var dataAll = {
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    $.ajax({
        url: "/user/recordlistH?pageNumber=1&pageSize=10",
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
        data:dataAll
    });
    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var time1=$("#update_time").val();
        var time2=$("#update_time1").val();
        var user_cn_name=$("#user_cn_name").val();
        var add_type=$("#add_type").val();
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "/user/recordlistH?pageNumber="+pageNum+"&pageSize=10",
            type:'POST',
            dataType:"json",
            async:false,
            data:{
                timeStart:time1,
                timeEnd:time2,
                userCnName:user_cn_name.trim(),
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

</script>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lyp
  Date: 2017/5/24
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">

<head>
    <meta charset="utf-8">
    <title>供应商交易数据统计</title>
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${path}/static/css/jieguoweizhidingdan.css">
    <link rel="stylesheet" href="${path}/static/layer/skin/layer.css">
</head>
<style>
    #tab1 p {
        height: 40px;
        color: #666;
        background: #dedede;
        line-height: 40px;
        padding: 0 15px;
    }

    .gys_srarch {
        width: 150px;
        float: left;
    }
    .in_box{
        position: absolute;
        width: 164px;
        top: 35px;
        left: 0;
        background:#fff;
        max-height: 165px;
        overflow: auto;
    }
    .in_box ul{
        padding:0;
    }
    .in_box li{
        width: 200px;
        height: 30px;
        line-height: 30px;
        text-align: left;
        padding-left: 10px;
        border-bottom: 1px solid #dedede;
    }
    .in_box li:hover{
        background: #abcdef;
    }
</style>

<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body" @click.capture="bodyClick">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">供应商交易数据统计</strong>
            </div>
        </div>
        <hr>
        <div class="am-tab-panel am-active" id="tab1">
            <p>本统计数据不保障百分百精确。</p>
            <form class="am-form-inline" role="form">
                <div class="am-form-group" style="margin-top: 20px;position: relative;">
                    <input id="supplyName" type="text" class="am-form-field" placeholder="供应商" class="gys_srarch" @click="gysClick()" v-model="val" >
                    <div class="in_box" v-show="off">
                        <ul>
                            <li v-for="item in list | filterBy val" @click="liClick($event)">{{item.name}}</li>
                        </ul>
                    </div>
                </div>
                <div class="am-form-group">
                    <select id="businessType" class="select1" style="width: 150px;float: left;">
                        <option value="0">渠道类型</option>
                        <option value="1">流量</option>
                        <option value="2">话费</option>
                        <option value="3">视频会员</option>
                    </select>
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input id="updateTime" type="text" class="am-form-field" placeholder="选择日期" style="width: 150px;float: left;"
                           onclick="laydate({istime: true, format: 'YYYY-MM'})">
                </div>
            </form>
            <button type="submit" class="am-btn am-btn-warning" style="width: 150px;margin-top: 20px;" onclick="selectChannelAddFund(1)">查询
            </button>
            <hr>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>供应商</th>
                    <th>渠道类型</th>
                    <th>总订单数</th>
                    <th>成功笔数</th>
                    <th>成功交易金额</th>
                    <th>成功率</th>
                    <th>统计月份</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td>{{item.supplyId}}</td>
                    <td>{{item.businessType}}</td>
                    <td>{{item.tatalOrds}}</td>
                    <td>{{item.successNum}}</td>
                    <td>{{item.successMoney}}</td>
                    <td>{{item.successRate}}</td>
                    <td>{{item.updateTime}}</td>
                </tr>
                </tbody>
            </table>
            <hr>
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
                        <li><a href="javascript:void(0);" onclick="selectChannelAddFund('{{page.dq-1}}');">上一页</a></li>
                        <li class="am-disabled"><a href="#"><span> {{page.dq}}</span>/<span>{{page.all}}</span></a></li>
                        <li><a href="javascript:void(0);"  onclick="selectChannelAddFund('{{page.dq+1}}');">下一页</a></li>
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
    </div>
    <!-- content end -->
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/layer/layer.js"></script>
<script src="${path}/static/laydate/laydate.js"></script>
<script src="${path}/static/assets/js/amazeui.min.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script>


    $(function(){
        selectSupplierInit();
        selectChannelAddFund(1);
    });

    var vm = new Vue({
        el: "#content",
        data: {
            off:false,
            val:"",
            json: [],
            list:[

            ],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        },
        ready: function () {

        },
        methods: {
            gysClick:function () {
                this.off = true;
            },
            liClick:function (e) {
                var txt = $(e.target).text();
                $('.parent_list input').val(txt);
                this.off = false;
                this.val = txt;
            },
            bodyClick:function () {
                this.off = false;
            }
        }
    });

    /*
     查询供应商
     */
    var supplierName = "";
    function  selectSupplierInit(){
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectSupplier",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                supplierName = data;
                vm.list = data;
            }
        });
    };



    /**
     * 数据查询
     * @param param
     */
    function selectChannelAddFund(param,param2){

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


        var updateTime = $("#updateTime").val();
        var businessType = $("#businessType").val();
        var supplyName = $("#supplyName").val();
        var index = layer.open({type:3});
        var obj="";
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectChannelStatisticsDay",
            async:false,
            type:'POST',
            data:{"updateTime":updateTime,"businessType":businessType,"supplyName":supplyName,"pageNumber":pageNumber,"pageSize":pageSize},
            dataType:"json",
            success: function(data){
                layer.close(index);
                obj = data.list;
                $(obj).each(function (index) {
                    //匹配供应商
                    $(supplierName).each(function (i){
                        if(obj[index].supplyId==supplierName[i].id){
                            obj[index].supplyId=supplierName[i].name;
                        }
                    });
                    if(obj[index].businessType==1){
                        obj[index].businessType="流量";
                    }else if(obj[index].businessType==2){
                        obj[index].businessType="话费";
                    }else if(obj[index].businessType==3){
                        obj[index].businessType="视频会员";
                    }
                    var successRateString=(obj[index].successNum/obj[index].tatalOrds)*100;
                    obj[index].successRate=successRateString.toString().substr(0,5)+"%";
                });
                vm.json = obj;
                vm.page.ts=data.total;
                vm.page.dq=data.pageNum;
                vm.page.all=data.pages;
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
            selectChannelAddFund(pagenum);
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
        selectChannelAddFund(fist,nums);
    }
</script>
</body>

</html>

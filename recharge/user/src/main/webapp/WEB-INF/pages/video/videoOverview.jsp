<%--
  Created by IntelliJ IDEA.
  User: lhm
  Date: 2018/2/24
  Time: 9:43
  昨日关键数据
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link href="${path}/static/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link href="${path}/static/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link href="${path}/static/css/z-home.css" rel="stylesheet">
    <link href="${path}/static/css/z-liuliangchongzhi.css" rel="stylesheet">
    <script src="${path}/static/js/echarts.common.min.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .tabChange {
            overflow: auto;
            clear: both;
            border-left: 1px solid #ccc;
            padding: 0;
            margin-left: 44px;
        }

        .tabChange li {
            float: left;
            padding: 5px 28px;
            font-size: 14px;
            border-right: 1px solid #ccc;
            border-top: 1px solid #ccc;
            border-bottom: 1px solid #ccc;
            cursor: pointer;
        }

        .tabChange li.active {
            background: #ED801B;
            color: #fff;
            border-color: #ED801B;
        }

        .form_wrap {
            font-size: 14px;
            clear: both;
            overflow: auto;
        }

        .form_wrap select {
            font-size: 14px;
        }

        .form_wrap .am-checkbox {
            float: left;
        }

        .col-margin-top-box {
            margin-top: 30px;
        }

        .z_info_tit button {
            font-size: 14px;
            float: right;
            margin: 10px 24px 0 0;
            padding: 5px 15px;
            background: #ED801B;
            color: #fff;
            border:0;
        }

        table td {
            text-align: center;
        }
        .z_tody_record{
            height:62px;
        }
        .table>thead>tr>th{
            font-size:14px;
            font-weight:100;
        }
        .texttable > thead > tr > th{
            padding:8px!important;
        }
    </style>
</head>

<body>

<div class="container" style="padding:23px;">
    <!--    第一部分   -->
    <div class="row" style="margin:0  0 20px 0;">
        <div class="col-lg-12">
            <div class="row z_info" style="height: 240px;">
                <h3 class="z_info_tit">昨日关键数据</h3>
                <div class="row">
                    <div class="col-lg-12 col-margin-bottom-box col-margin-top-box">
                        <div style="margin-top:10px;">
                            <div class="col-lg-4 col-md-4 col-xs-4">
                                <div class="z_tody_record">
                                    <span id="telTrading_volume" class="z-all auto_center" style="font-size:40px;">0</span>
                                </div>
                                <p class="z_record_deco">
                                        <span>
                                            交易额（元）
                                        </span>
                                </p>
                            </div>
                            <div class="col-lg-4 col-md-4 col-xs-4">
                                <div class="z_tody_record">
                                    <span id="telOrder_quantity" class="auto_center" style="font-size:40px;">0</span>
                                </div>
                                <p class="z_record_deco">
                                        <span>
                                            订单数
                                        </span>
                                </p>
                            </div>
                            <div class="col-lg-4 col-md-4 col-xs-4">
                                <div class="z_tody_record">
                                    <span id="telSuccess_rate" class="auto_center" style="font-size:40px;">0.00%</span>
                                </div>
                                <p class="z_record_deco">
                                        <span>
                                            成功率
                                        </span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--    统计图    -->
    <div class="row col-margin-top z_tju">
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-12">
                    <div class="col-border clearfix col-margin-bottom">
                        <div class="z_info_tit">
                            充值统计图
                        </div>
                        <!--tab切换-->
                        <ul class="tabChange" id="tabChange">
                            <li class="active" onclick="videoAmount()">交易金额</li>
                            <li onclick="videoSumOrderNum()">订单数</li>
                            <li onclick="videoSuccessOrderNum()">成功率</li>
                        </ul>
                        <div class="col-lg-12" style="line-height:30px;height:30px;">
                            <form class="am-form form_wrap">
                                <fieldset class="am-g">
                                    <div class="am-form-group am-u-lg-2 am-u-md-2" style="margin-right:-15px;">
                                        <select id="doc-select-1">
                                            <option value="">选择数据时间</option>
                                            <option value="1">年数据</option>
                                            <option value="2">月数据</option>
                                        </select>
                                        <span class="am-form-caret"></span>
                                    </div>
                                    <div class="am-form-group am-u-lg-2 am-u-md-2" style="float:left;">
                                        <select id="doc-select-2">
                                        </select>
                                        <span class="am-form-caret"></span>
                                    </div>
                                </fieldset>
                            </form>
                        </div>
                        <div class="col-lg-12 col-md-12 col-xs-12" id="main" style="height:600px;">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--    统计报表    -->

    <div class="row z_tjbb" style="margin-top:20px;font-size:14px;">
        <div class="col-lg-12">
            <div class="col-border clearfix col-margin-bottom">
                <div class="z_info_tit">
                    详细报表
                    <button type="button" onclick="videoExcelExport()">下载表格</button>
                </div>
                <div class="col-lg-12 col-md-12 col-xs-12">
                    <div class="m-box">
                        <div class="box-content m-chart" style="min-height:356px;">
                            <table class="table table-striped table-hover texttable" width="100%">
                                <thead>
                                <tr>
                                    <th>订单时间</th>
                                    <th>成功订单数</th>
                                    <th>交易金额（元）</th>
                                </tr>
                                </thead>
                                <tbody id="userTeleDataList">
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js"></script>
<script src="${path}/static/js/flowApplication.js"></script>
<script type="text/javascript">
    $(function() {
        $(".tabChange li").each(function() {
            $(".tabChange li").click(function() {
                $(this).addClass("active").siblings().removeClass("active");
            });
        });
    });
    //数据表格下载
    function videoExcelExport(){
        var businessType=${businessType};
        var docSelect1 =$("#doc-select-1").val();
        var updateTime= $("#doc-select-2 option:checked").text();
        if(docSelect1==1) {
            window.open("<%=request.getContextPath()%>/userStatisticsMonth/downloadExcelYear?businessType=" + businessType + "&updateTime=" + updateTime);
        }else{
            window.open("<%=request.getContextPath()%>/userStatisticsDay/downloadExcelMonth?businessType=" + businessType + "&updateTime=" + updateTime);
        }
    };
</script>
<script type="text/javascript">
    $(function(){
        getYesterdayVideo();
        $("#doc-select-2").change(function(){
            listGetMonthData();
            $("#tabChange li").each(function(){
                if($(this).attr("class") == "active"){
                    var tabLiText=$(this).text();
                    if(tabLiText=="交易金额"){
                        videoAmount();
                        return
                    }
                    if(tabLiText=="订单数"){
                        videoSumOrderNum();
                        return;
                    }
                    if(tabLiText=="成功率"){
                        videoSuccessOrderNum();
                        return;
                    }
                }
            });
        });
    });
    //查询昨天视频会员的关键数据
    function getYesterdayVideo(){
        var businessType=${businessType};
        $.ajax({
            url: "${pageContext.request.contextPath}/userStatisticsDay/userStatisticsByUserId",
            type:'POST',
            dataType:"json",
            data:{businessType:businessType},
            error:function(){
                alert("数据加载错误");
            },
            success: function(data){
                //交易额，订单数，成功
                var telTrading_volume=$("#telTrading_volume").text(data.amount);
                var telOrder_quantity=$("#telOrder_quantity").text(data.sumOrderNum);
                if(data.successRate!=0){
                    var telSuccess_rate=$("#telSuccess_rate").text(data.successRate);
                }else {
                    var telSuccess_rate=$("#telSuccess_rate").text("0.00%");
                }
            }
        });
    }


    //根据月份查询本月每一天的数据;根据年份查询每一个月的数据
    function listGetMonthData(){
        var businessType=${businessType};
        var docSelect1 =$("#doc-select-1").val();
        if(docSelect1==""||docSelect1==null){
            alert("请选择数据时间");
            return;
        }
        if(docSelect1==1){
            //查询年数据
            var updateTime= $("#doc-select-2 option:selected").text();
            $.ajax({
                url:"${PageContext.request.contextPath}/userStatisticsMonth/userSelectByYear",
                type:"POST",
                dataType:"json",
                data:{updateTime:updateTime,businessType:businessType},
                success:function(resp){
                    var listString="";
                    $(resp.selectOutTimeList).each(function (index) {
                        if(resp.successOrderNumList[index]==0&&resp.amountList[index]==0){
                            resp.successOrderNumList[index]="--";
                            resp.amountList[index]="--";
                        }
                        listString +='<tr>'+
                                '<td class="z_times">'+resp.selectOutTimeList[index]+'</td>'+
                                '<td>'+resp.successOrderNumList[index]+'</td>'+
                                '<td>'+resp.amountList[index]+'</td>'+
                                '</tr>';
                    });
                    $("#userTeleDataList").empty();
                    $("#userTeleDataList").append(listString);
                }
            });
        }else if(docSelect1==2){
            //查询月数据
            var updateTime= $("#doc-select-2 option:selected").text();
            $.ajax({
                url:"${PageContext.request.contextPath}/userStatisticsDay/userSelectByMonth",
                type:"POST",
                dataType:"json",
                data:{updateTime:updateTime,businessType:businessType},
                success:function(resp){
                    var listString="";
                    $(resp.selectOutTimeList).each(function (index) {
                        if (resp.successOrderNumList[index] == 0 && resp.amountList[index] == 0) {
                            resp.successOrderNumList[index] = "--";
                            resp.amountList[index] = "--";
                        }
                        listString += '<tr>' +
                                '<td class="z_times">' + resp.selectOutTimeList[index] + '</td>' +
                                '<td>' + resp.successOrderNumList[index] + '</td>' +
                                '<td>' + resp.amountList[index] + '</td>' +
                                '</tr>';
                    });
                    $("#userTeleDataList").empty();
                    $("#userTeleDataList").append(listString);
                }
            });
        }
    }
    var myChart = echarts.init(document.getElementById('main'));
    var updateTime= $("#doc-select-2 option:selected").text();
    myChart.setOption({
                title: {
                    text: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: updateTime
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    boundaryGap: false,
                    data: [],
                    gridIndex: 0,
                    min: 0,
                    max: 31
                }],
                yAxis: [{
                    type: 'value'
                }],
                series: [{
                    name: '数据',
                    type: 'line',
                    stack: '总量',
                    areaStyle: {
                        normal: {}
                    },
                    data: []
                }]
            }
    );
    //交易金额按钮触发事件
    function videoAmount(){
        var updateTime= $("#doc-select-2 option:selected").text();
        if(updateTime==""||updateTime==null){
            alert("请选择数据时间");
        }
        videoAmountData(function (json) {
            myChart.hideLoading();
            myChart.setOption({
                tooltip: {
                    trigger: 'axis',
                    formatter: "{a} <br/>{b}: {c}"
                },
                xAxis : {
                    data : json.month
                }, series : [ {
                    data : json.count
                }]
            });
        });
    }
    function videoAmountData(cb) {
        var businessType=${businessType};
        var docSelect1 =$("#doc-select-1").val();
        var updateTime= $("#doc-select-2 option:selected").text();
        if(docSelect1==1){
            $.get('../userStatisticsMonth/userGraphSelectByYear?businessType='+businessType+'&updateTime='+updateTime).done(function(data){
                var json = $.parseJSON(data);
                cb(json);
            });
        }else if(docSelect1==2){
            $.get('../userStatisticsDay/userGraphSelectByMonth?businessType='+businessType+'&updateTime='+updateTime).done(function(data){
                var json = $.parseJSON(data);
                cb(json);
            });
        }

    }
    //订单数按钮触发事件
    function videoSumOrderNum(){
        var updateTime= $("#doc-select-2 option:selected").text();
        if(updateTime==""||updateTime==null){
            alert("请选择数据时间");
        }
        videoSumOrderNumData(function (json) {
            myChart.hideLoading();
            myChart.setOption({
                tooltip: {
                    trigger: 'axis',
                    formatter: "{a} <br/>{b}: {c}"
                },
                xAxis : {
                    data : json.month
                },
                series : [ {
                    data : json.count
                }]
            });
        });
    }
    function videoSumOrderNumData(cb) {
        var updateTime= $("#doc-select-2 option:selected").text();
        var businessType=${businessType};
        var docSelect1 =$("#doc-select-1").val();
        var updateTime= $("#doc-select-2 option:selected").text();
        if(docSelect1==1){
            $.get('../userStatisticsMonth/userGraphSelectByYearSumOrderNum?businessType='+businessType+'&updateTime='+updateTime).done(function(data){
                var json = $.parseJSON(data);
                cb(json);
            });
        }else if(docSelect1==2){
            $.get('../userStatisticsDay/userGraphSelectByMonthSumOrderNum?businessType='+businessType+'&updateTime='+updateTime).done(function(data){
                var json = $.parseJSON(data);
                cb(json);
            });
        }

    }
    //成功率按钮触发事件
    function videoSuccessOrderNum(){
        var updateTime= $("#doc-select-2 option:selected").text();
        if(updateTime==""||updateTime==null){
            alert("请选择数据时间");
        }
        videoSuccessOrderNumData(function (json) {
            myChart.setOption({
                tooltip: {
                    formatter: "{a} <br/>{b}: {c} %" // 这里是鼠标移上去的显示数据
                },
                xAxis : {
                    data : json.month
                },
                series : [ {
                    data : json.count
                }],
            });
        });
    }
    function videoSuccessOrderNumData(cb) {
        var businessType=${businessType};
        var docSelect1 =$("#doc-select-1").val();
        var updateTime= $("#doc-select-2 option:selected").text();
        if(docSelect1==1){
            $.get('../userStatisticsMonth/userGraphSelectByYearSuccessRate?businessType='+businessType+'&updateTime='+updateTime).done(function(data){
                var json = $.parseJSON(data);
                cb(json);
            });
        }else if(docSelect1==2){
            $.get('../userStatisticsDay/userGraphSelectByMonthSuccessRate?businessType='+businessType+'&updateTime='+updateTime).done(function(data){
                var json = $.parseJSON(data);
                cb(json);
            });
        }

    }
</script>
</body>
</html>

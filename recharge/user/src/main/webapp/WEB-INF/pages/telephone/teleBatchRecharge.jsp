<%--
  Created by IntelliJ IDEA.
  User: lyp
  Date: 2017/1/11
  Time: 14:31
  批量充值话费
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
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link href="${path}/static/css/common.css" rel="stylesheet">
    <link href="${path}/static/css/z-danhaochongzhi.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .z_upload {
            width: 100%;
            height: 40px;
            border-radius: 4px;
            border: 1px solid #aaa;
        }

        .z_beizhu {
            border: 1px solid #ccc;
            background: #eee;
            padding: 10px 20px;
            color: #a8a8a8;
        }

        .z_plcz {
            line-height: 80px;
            font-size: 14px;
            clear: both;
            overflow: auto;
            margin-top: 40px;
        }

        .z_cz_tabContent p button {
            width: 100px;
            height: 30px;
            padding: 0;
            line-height: 30px;
            margin-top: 10px;
            opacity: 1!important;
            margin-left: 238px;
        }

        .z_select div:nth-child(1) {
            text-align: right;
        }

        .z_select div:nth-child(2) select,#remark {
            width: 200px;
            height: 30px;
            text-align: center;
        }

        #remark{
            border:0;
            border:1px solid rgb(169, 169, 169);
            border-radius:2px;
        }
        .z_tongji {
            margin-bottom: 20px;
        }

        .z_money div:nth-child(1) {
            text-align: right;
        }

        .z_red {
            color: red;
            font-weight: 900;
        }
        .z_popup1{
            background:rgba(0,0,0,.5);
            width:100%;
            height:100%;
            position: absolute;
            left:0;
            top:0;
            display: none;
        }
        .z_pop1{
            width:300px;
            height:200px;
            overflow: auto;
            background: #fff;
            -webkit-border-radius:;
            -moz-border-radius:;
            border-radius:10px;
            position: absolute;
            left:50%;
            top:50%;
            margin-left:-155px;
            margin-top:-195px;
            padding:15px;
            text-align: center ;
        }

        .z_tongji{
            text-indent:88px;
            display: block;
            overflow: auto;
            clear: both;
            margin-bottom: 20px;
            padding-top:20px;
        }


        .z_popup {
            width: 100%;
            height: 100%;
            position: fixed;
            top: 0;
            left: 0;
            background: rgba(0, 0, 0, .5);
            display: none;
        }

        .z_pop {
            width: 300px;
            height: 200px;
            border-radius: 10px;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-left: -150px;
            margin-top: -150px;
            background: #fff;
            cursor: pointer;
        }

        .z_top {
            padding: 10px 15px;
            text-align: right;
            border-bottom: 1px solid #ccc;
        }

        .z_bottom,
        .z_bottomzhifu{
            text-align: center;
            padding: 20px 0;
        }

        .z_bottom button,
        .z_bottomzhifu button{
            width: 200px;
            height: 40px;
            line-height: 40px;
            border-radius: 10px;
            background: #428bca;
            border: 0;
            color: #fff;
        }

        .z_bottom input {
            width: 180px;
            height: 30px;
            border: 0;
            border: 1px solid #ccc;
        }



    </style>
</head>

<body>
<div class="container" style="padding:23px;">
    <div class="row">
        <div class="col-lg-12">
            <div class="m-box m-market">
                <div class="box-content">
                    <ul class="z_cz_tab">
                        <li class="active">批量充值</li>
                        <input  type="hidden" value="${users.id}" id="userId"/>
                        <input  type="hidden" value="${users.payPassword}" id="userPayPassword"/>
                    </ul>
                    <!--     批量充值    -->
                    <div class="col-lg-12 col-md-12 col-xs-12 z_cz_tabContent" style="display:block!important;">
                        <div class="z_beizhu">
                            如一次性交易数额较大，请确保余额充足。
                            <br/>暂不支持虚拟运营商充值。
                        </div>
                        <!--   上传批量充值文档   -->
                        <div class="am-form-group am-g doc-am-g z_plcz">
                                <span class="am-u-sm-4 am-u-md-3 am-u-lg-2" style="text-align:right;">
                                    <p style="line-height: 18px;">输入手机号：</p>
                                     <p style="line-height:20px;color:#a8a8a8;white-space: nowrap;">（一行一个，最多一千个。<br/>&nbsp;&nbsp;&nbsp;不能含有空格和回车符）</p>
                               </span>
                                <span class="am-u-sm-8 am-u-md-7 am-u-lg-10" style="margin-left: -4px;">
                                     <textarea name="" id="z_phoneNum" cols="30" rows="10" style="line-height:20px;" onchange="getMobilePhone()"></textarea>
                                </span>
                        </div>
                        <div class="z_tongji">
                            <span style="color: green;">充值记录数（<i id="count">0</i>）</span>
                            <span id="" style="color: green;">移动（<i id="chinaMobil">0</i>）</span>
                            <span style="color: green;">联通（<i id="chinaUnicom">0</i>）</span>
                            <span style="color: green;">电信（<i id="chinaTelecom">0</i>）</span>
                        </div>
                        <!--下拉-->
                        <div class="am-form-group am-g doc-am-g z_select">
                            <div class="am-u-sm-4 am-u-md-3 am-u-lg-2">移动：</div>
                            <div class="am-u-sm-8 am-u-md-7 am-u-lg-10">
                                <select id="doc-select-1" onchange="getAmount();">

                                </select>
                                <span class="am-form-caret"></span>
                            </div>
                        </div>
                        <div class="am-form-group am-g doc-am-g z_select">
                            <div class="am-u-sm-4 am-u-md-3 am-u-lg-2">联通：</div>
                            <div class="am-u-sm-8 am-u-md-7 am-u-lg-10">
                                <select id="doc-select-2" class="z_select" onchange="getAmount();">

                                </select>
                                <span class="am-form-caret"></span>
                            </div>
                        </div>
                        <div class="am-form-group am-g doc-am-g z_select">
                            <div class="am-u-sm-4 am-u-md-3 am-u-lg-2">电信：</div>
                            <div class="am-u-sm-8 am-u-md-7 am-u-lg-10">
                                <select id="doc-select-3" onchange="getAmount();">

                                </select>
                                <span class="am-form-caret"></span>
                            </div>

                        </div>
                        <div class="am-form-group am-g doc-am-g z_select">
                            <div class="am-u-sm-4 am-u-md-3 am-u-lg-2">备注：</div>
                            <div class="am-u-sm-8 am-u-md-7 am-u-lg-10">
                                <input type="text" id="remark"/>
                            </div>
                        </div>
                        <!--总金额-->
                        <div class="z_money">
                            <div class="am-u-sm-4 am-u-md-3 am-u-lg-2">需支付总金额：</div>
                            <div class="am-u-sm-8 am-u-md-7 am-u-lg-10">
                                <span class="z_red" id="countMoney">0</span>元
                            </div>
                        </div>
                        <div class="am-form-group am-g doc-am-g">
                            <div class="am-u-sm-4 am-u-md-3 am-u-lg-2"></div>
                            <div class="am-u-sm-8 am-u-md-7 am-u-lg-10">
                                <button type="button" class="btn btn-primary" onclick="payPasswordShow();" style="width: 120px;margin-top: 20px;">提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="z_popup1">
                <div class="z_pop1">

                </div>
            </div>
        </div>
    </div>
    <!--弹窗-->
    <div class="z_popup">
        <div class="z_pop">
            <div class="z_top">
                <span class="z_close" onclick="payPasswordHide();">X</span>
            </div>
            <div class="z_bottom" style="display:none;">
                <p>您未设置支付密码，设置之后
                    <br/>才可进行充值</p>
                <button type="button"  onclick="skip()">去设置支付密码</button>
            </div>
            <div class="z_bottomzhifu" style="display:block;">
                <p>
                    <span>支付密码：</span>
                    <span><input type="password"  id="pagePayPassword"></span>
                </p>
                <span id="bd-map"></span>
                <button type="button"   onclick="submitPayPassword()">提交</button>
            </div>
        </div>
    </div>
</div>

<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/z-yonghuchongzhi.js"></script>
<script src="${path}/static/js/layer.js"></script>
<script>
    $(function(){
        getProduct();
        $(".z_cz_tab li").unbind();
    });
    //显示支付菜单
    function payPasswordShow(){
        $("#bd-map").text("");
        if($("#z_phoneNum").val()==""){
            alert("请输入手机号");
            return ;
        }
        var number = $("#z_phoneNum").val().split("\n");
        if(number.length>999){
            alert("最大充值数1000");
            return ;
        }
        var chinaMobil=  $("#chinaMobil").text();
        var chinaUnicom =$("#chinaUnicom").text();
        var chinaTelecom=$("#chinaTelecom").text();
        var chinaMobilCode=  $("#doc-select-1").val();
        var chinaUnicomCode =$("#doc-select-2").val();
        var chinaTelecomCode=$("#doc-select-3").val();
        if(parseInt(chinaMobil)>0){
            if(chinaMobilCode==0){
                alert("请选择移动产品");
                return ;
            }
        }
        if(parseInt(chinaUnicom)>0){
            if(chinaUnicomCode==0){
                alert("请选择联通产品");
                return ;
            }
        }
        if(parseInt(chinaTelecom)>0){
            if(chinaTelecomCode==0){
                alert("请选择电信产品");
                return ;
            }
        }
        $(".z_popup").show();
        if($("#userPayPassword").val()==""){
            $(".z_bottom").show();
            $(".z_bottomzhifu").hide();
        }else{
            $(".z_bottom").hide();
            $(".z_bottomzhifu").show();
        }
    }

    //提交支付密码
    function submitPayPassword(){
        var pagePayPassword=$("#pagePayPassword").val();
        $.ajax({
            url: "${pageContext.request.contextPath}/Recharge/payPassword",
            data:{"pagePayPassword":pagePayPassword},
            async:false,
            type:'POST',
            dataType:"json",
            error:function(){
                alert("系统错误，请重新登录");
            },
            success: function(data){

                if(data){

                    $("#pagePayPassword").val("");
                    $(".z_popup").hide();
                    sendSumbitFlow();
                }else{
                    $("#bd-map").text("密码错误,支付失败").css('color','red');
                }
            }
        });
    }
    //隐藏支付窗
    function payPasswordHide(){
        $(".z_popup").hide();
    }
    $(function() {
        $("#z_phoneNum").blur(function() {
            var phoneNumber = $("#z_phoneNum").val();
            var number = phoneNumber.split("\n");
            var reg = /^1[34578]\d{9}$/;
            var last = number.pop();
            //判断最后一位是否为空
            if (last == "") {
                //验证手机号格式
                for (var i = 0; i < number.length; i++) {
                    if (reg.test(number[i]) == false) {
                        alert("第" + (i + 1) + "行号码有误！");
                        return ;
                    };
                };
            }
            if (last != "") {
                number.push(last);
                // alert(number);
                //验证手机号格式
                for (var i = 0; i < number.length; i++) {

                    if (reg.test(number[i]) == false) {
                        alert("第" + (i + 1) + "行号码有误！");
                        return ;
                    };
                };
            }
            //提示重复手机号码
            var res = [];
            number.sort();
            for (var i = 0; i < number.length;) {
                var count = 0;
                for (var j = i; j < number.length; j++) {
                    if (number[i] == number[j]) {
                        count++;
                    }
                }
                res.push([number[i], count]);
                i += count;
            }
            //res 二维数维中保存了 值和值的重复数
            var repeat = [];
            for (var i = 0; i < res.length; i++) {
                if (parseInt(res[i][1]) > 1 && reg.test(res[i][0])) {
//                    alert("值:" + res[i][0] + "重复次数:" + res[i][1])
                    repeat.push("此号码:" + res[i][0] + ",次数:" + res[i][1]+"<br/>");
                }
            };
            if(repeat != ""){
                pop(repeat);
            };
            //判断手机号运营商
            var mobiles=number.toString()+",";
            if(phoneNumber!=""){
                var inde = layer.open({type:3});
                $.ajax({
                    url: "/BatchRecharge/mobileJudge",
                    type:'POST',
                    data:{
                        mobiles:mobiles
                    },
                    dataType:"json",
                    error:function(){
                        layer.close(inde);
                        layer.msg("加载失败，请检查输入的手机号");
                    },
                    success: function(data){
                        layer.close(inde);
                        if(data!=null){
                            if(data.identification ==0) {
                                $("#count").text(data.chinaMobil + data.chinaUnicom + data.chinaTelecom);
                                $("#chinaMobil").text(data.chinaMobil);
                                $("#chinaUnicom").text(data.chinaUnicom);
                                $("#chinaTelecom").text(data.chinaTelecom);
                            }else if(data.identification>0){
                                var repeats = [];
                                var obj=data.list;
                                $(obj).each(function (i) {
                                    repeats.push("此号码:" + obj[i] + "，不支持充值"+"<br/>");
                                })
                                if(repeats != ""){
                                    pop(repeats);
                                };
                            }
                        }else{
                            $("#count").text(0);
                            $("#chinaMobil").text(0);
                            $("#chinaUnicom").text(0);
                            $("#chinaTelecom").text(0);
                        }
                    }
                });
            }else{
                //alert("请输入充值手机号");
                return;
            }
        });
    });
    function pop(p){
        $(".z_pop1").html(p);
        $(".z_popup1").show();
        $(".z_popup1").click(function(){
            $(this).hide();
        });
    };


    //获取用户流量产品
    function getProduct(){
        var appType=2;
        var ydhtml='<option value="0">移动</option>';
        var lthtml='<option value="0">联通</option>';
        var dxhtml='<option value="0">电信</option>';
        var userId=$("#userId").val();
        var businessType=2;
        $.ajax({
            url: "/developer/teldocuments",
            type:'POST',
            data:{
                userId:userId,
                appType:appType,
                businessType:businessType
            },
            dataType:"json",
            error:function(){
                alert("加载失败");
            },
            success: function(data){
                var obj=data;
                $(obj).each(function (i) {
                    var amount=obj[i].amount;
                    var discount=obj[i].discountPrice;
                    var count=amount*discount;
                    var countFloat="￥"+count.toFixed(3);
                    if(obj[i].operator==1){
                        var amount=obj[i].amount;
                        var discount=obj[i].discountPrice;
                        var count=amount*discount;
                        var countFloat="￥"+count.toFixed(3);
                        ydhtml+=' <option value="'+obj[i].code+'">'+obj[i].packageSize+'('+countFloat+')'+'</option>'
                    }
                    if(obj[i].operator==2){
                        var amount=obj[i].amount;
                        var discount=obj[i].discountPrice;
                        var count=amount*discount;
                        var countFloat="￥"+count.toFixed(3);
                        lthtml+=' <option value="'+obj[i].code+'">'+obj[i].packageSize+'('+countFloat+')'+'</option>'
                    }
                    if(obj[i].operator==3){
                        var amount=obj[i].amount;
                        var discount=obj[i].discountPrice;
                        var count=amount*discount;
                        var countFloat="￥"+count.toFixed(3);
                        dxhtml+=' <option value="'+obj[i].code+'">'+obj[i].packageSize+'('+countFloat+')'+'</option>'
                    }
                });
                $("#doc-select-1").empty();
                $("#doc-select-1").append(ydhtml);
                $("#doc-select-2").empty();
                $("#doc-select-2").append(lthtml);
                $("#doc-select-3").empty();
                $("#doc-select-3").append(dxhtml);
            }
        });
    }

    //获取手机号
    function getMobilePhone(){
        $("#z_phoneNum").val();
    }
    //计算花费钱数
    function getAmount(){
        var chinaMobilMoney=0;
        var chinaUnicomMoney=0;
        var chinaTelecomMoney=0;
        var chinaMobilCode=  $("#doc-select-1").val();
        var chinaUnicomCode =$("#doc-select-2").val();
        var chinaTelecomCode=$("#doc-select-3").val();
        if(chinaTelecomCode!=0) {
            var am = $("#doc-select-3").find("option:selected").text();
            if (am != "电信") {
                var f = am.split("￥");
                var s = f[1].split(")");
                chinaTelecomMoney = s[0];
            }
        }
        if(chinaMobilCode!=0){
            var yd = $("#doc-select-1").find("option:selected").text();
            if(yd != "移动"){
                var f = yd.split("￥");
                var s = f[1].split(")");
                chinaMobilMoney=s[0];
            };
        }
        if(chinaUnicomCode!=0) {
            var lt = $("#doc-select-2").find("option:selected").text();
            if (lt != "联通") {
                var f = lt.split("￥");
                var s = f[1].split(")");
                chinaUnicomMoney = s[0];
            }
        }
        var chinaMobilCount=$("#chinaMobil").text();
        var chinaUnicomCount=$("#chinaUnicom").text();
        var chinaTelecomCount= $("#chinaTelecom").text();
        var count=(chinaMobilCount*chinaMobilMoney)+(chinaUnicomCount*chinaUnicomMoney)+(chinaTelecomMoney*chinaTelecomCount);
        var floatMoney=count.toFixed(3);
        $("#countMoney").text(floatMoney);
    }


    //提交批量充值
    function sendSumbitFlow(){
        if( $("#z_phoneNum").val()!=""){
            var rep = [];
            var reg = /^1[34578]\d{9}$/;
            var number = $("#z_phoneNum").val().split("\n");
            if(number.length>999){
                alert("最大充值数1000");
                return ;
            }
            if(rep != ""){
                pop(rep);
                return ;
            };
            var remark=$("#remark").val();
            var chinaMobilCode=  $("#doc-select-1").val();
            var chinaUnicomCode =$("#doc-select-2").val();
            var chinaTelecomCode=$("#doc-select-3").val();
            var money = $("#countMoney").text();
            var urlName="tellRechargeUrl";
            if(chinaMobilCode==0){
                chinaMobilCode="";
            }
            if(chinaUnicomCode==0){
                chinaUnicomCode="";
            }
            if(chinaTelecomCode==0){
                chinaTelecomCode="";
            }
            $.ajax({
                url: "/BatchRecharge/sendSumbitFlow",
                type:'POST',
                data:{
                    chinaMobilCode:chinaMobilCode,
                    chinaUnicomCode:chinaUnicomCode,
                    chinaTelecomCode:chinaTelecomCode,
                    money:money,
                    urlName:urlName,
                    remark:remark
                },
                dataType:"json",
                error:function(){
                    alert("请重新登录");
                },
                success: function(data){
                    if(data==1000){
                        alert("提交成功");
                        $("#z_phoneNum").val("");
                        $("#remark").val("");
                        window.location.reload();
                    }
                    if(data==1010){
                        alert("金额不足，请充值");
                    }
                    if(data==1011){
                        alert("请检查移动的选择产品");
                    }
                    if(data==1012){
                        alert("请检查联通的选择产品");
                    }
                    if(data==1013){
                        alert("请检查电信的选择产品");
                    }
                }
            });
        }else{
            alert("请输入充值手机号");
            return;
        }
    }

    function skip(){
        window.location="<%=request.getContextPath()%>/admin/administrate.do";
    }
</script>
</body>
</html>

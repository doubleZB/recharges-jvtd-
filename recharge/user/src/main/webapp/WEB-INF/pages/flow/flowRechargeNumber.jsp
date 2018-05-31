<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2017/1/5
  Time: 14:25
  单个充值
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
<input  type="hidden" value="${users.id}" id="userId"/>
    <input  type="hidden" value="${users.payPassword}" id="userPayPassword"/>
    <div class="row">
        <div class="col-lg-12">
            <div class="m-box m-market">
                <div class="box-content">
                    <ul class="z_cz_tab">
                        <li class="active">单个充值</li>
                    </ul>
                    <!-- 单号充值     -->
                    <div class="col-lg-12 col-md-12 col-xs-12 z_cz_tabContent">

                        <!--   手机号输入   -->
                        <div class="z_phoneNumber" style="width:328px;">
                            <input type="text" value="" placeholder="输入手机号码" onblur="queryOperator()" id="mobile" maxlength="11">
                            <span id="address"></span>
                        </div>
                        <span id="prompt"></span>

                        <div class="z_taocan" id="productdisplay" style="width: 500px;">
                            <span class="label">10M</span>
                            <span class="label">30M</span>
                            <span class="label">70M</span>
                            <span class="label">150M</span>
                            <br>
                            <span class="label">500</span>
                            <span class="label">1G</span>
                        </div>
                        <div class="z_je">支付金额：<span id="money">0</span>元</div>
                        <p>
                            <button type="button" class="btn btn-primary" onclick="payPasswordShow();">提交</button>
                        </p>
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
                                <button type="button" onclick="skip()">去设置支付密码</button>
                            </div>
                            <div class="z_bottomzhifu" style="display:block;">
                                <p>
                                    <span>支付密码：</span>
                                    <span><input type="password"  id="pagePayPassword" style="border: 1px solid #ccc;border-radius: 3px;height: 30px;text-indent: 12px;"></span>
                                </p>
                                <span id="bd-map"></span>
                                <button type="button"   onclick="submitPayPassword()" style="width: 106px;height: 32px;line-height:32px;margin-top: 18px;">提交</button>
                            </div>
                        </div>
                    </div>
                </div>
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
        $(".z_cz_tab li").unbind();
    });
    //显示支付菜单
    function payPasswordShow(){
        $("#bd-map").text("");
        if($("#userId").val()==""){
            alert("用户超时从新登陆");
            return ;
        }
        if($("#mobile").val()==""){
            alert("请输入手机号");
            return ;
        }
        if(positionCode==""){
            alert("请选择充值产品");
            return ;
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
                    rechargeSumbit();
                }else{
                    $("#bd-map").text("密码错误,支付失败").css('color','red');
                    return;
                }
            }
        });
    }
    //隐藏支付窗
    function payPasswordHide(){
            $(".z_popup").hide();
    }
    function queryOperator(){
        var mobile =$("#mobile").val();
        var userId=$("#userId").val();
        var businessType=1;
        var appType=1;
        if(userId==""){
            alert("登录超时请重新登录");
        }
        if(mobile!=""){
        if(!(/^1[3456789]\d{9}$/.test(mobile))){
            $("#prompt").text("手机号码有误，请重填").css("color","red");
            $("#address").text("")
            return ;
        }else{
            $("#prompt").text("");
        $.ajax({
            url: "${pageContext.request.contextPath}/Recharge/mobileJugement",
            data:{"mobile":mobile,"userId":userId,"bussinessType":businessType,"appType":appType},
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                var html="";
                var objectNumSection = eval(data.numSection);
                if(objectNumSection==null){
                    alert("该手机号段不支持，请选择别的手机号");
                }else {
                    if(eval(data.identification)!=0){
                        alert("该手机号段不支持，请选择别的手机号");
                    }
                    $("#address").text( objectNumSection.mobileProvince+objectNumSection.mobileTypeName.substring(2,6) ).css("color", "#00DB00");
                    var objlist = eval(data.list);
                    if(objlist.length==0){
                        var htmltishi = '<span style="display: block; color: red;font-size: 2em;text-align: center;">该手机号段没有产品</span>';
                        $("#productdisplay").html(htmltishi);
                        $(".z_je span").text("0");
                    }else{
                        $(objlist).each(function(index) {
                            html +=' <span class="label">'+objlist[index].packagesSize+'<input type="hidden" value="'+objlist[index].positionCode+'"/><input type="hidden" value="'+objlist[index].amount+'"/><input type="hidden" value="'+objlist[index].discountPrice+'"/></span>';
                            $("#productdisplay").html(html);
                            tancan();
                        });
                    }
                }
            }
        });
        }
    }
    };
   var  positionCode="";
   function tancan(){
       //套餐点击
       $(".z_taocan span").each(function() {
           $(".z_taocan span").on("click", function() {
               $(this).addClass("z_xz").siblings().removeClass("z_xz");
           });
       });

       $(".z_taocan span").on("click", function() {
           positionCode = $(this).find("input").eq(0).val();
           var packagesSize = $(this).find("input").eq(1).val();
           var amount = $(this).find("input").eq(2).val();
           var stringMoney=packagesSize*amount;
           var floatMoney=stringMoney.toFixed(3);
           $(".z_je span").text(floatMoney);
       });
   };
    /**
     * 单号手机充值
     */
    function  rechargeSumbit(){
        var mobile =$("#mobile").val();
        var userId=$("#userId").val();
        var urlName="flowRechargeUrl";
        if(userId==""){
            alert("用户超时从新登陆");
            return ;
        }
        if(positionCode==""){
            alert("请选择充值产品");
            return ;
        }
        if(mobile==""){
            alert("请输入手机号");
            return ;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/Recharge/send",
            data:{"mobile":mobile,"userId":userId,"positionCode":positionCode,"urlName":urlName},
            async:false,
            type:'POST',
            dataType:"json",
            error:function(){
               alert("系统错误，请重新登录");
            },
            success: function(data){
                var code = data.statusCode;
                if(code=="1000"){
                    alert("提交成功，去订单明细查询");
                    $("#mobile").val("");
                    window.location.reload();
                }else{
                    var msg=data.statusMsg;
                    alert("提交失败"+code+","+msg);
                }
            }
        });
    }
    function skip(){
        window.location="<%=request.getContextPath()%>/admin/administrate.do";
    }
</script>
</body>
</html>

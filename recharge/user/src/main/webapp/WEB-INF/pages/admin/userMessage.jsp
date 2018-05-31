<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.FileNotFoundException" %><%--
  Created by IntelliJ IDEA.
  User: lhm  账户信息
  Date: 2016/12/29
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = "";
    String FileName = "";

    Properties pro = new Properties();
    String realpath = request.getRealPath("/WEB-INF/classes");
    try{
        //读取配置文件
        FileInputStream in = new FileInputStream(realpath+"/config.properties");
        pro.load(in);
    }
    catch(FileNotFoundException e){
        out.println(e);
    }
    catch(IOException e){out.println(e);}

//通过key获取配置文件
    path = pro.getProperty("certificate_image");
    FileName = pro.getProperty("FileName");
%>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <link rel="stylesheet" href="${path}/static/css/z-zhanghuxinxi.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <![endif]-->
    <style>
        .z_tc {
            width: 600px;
            height: 300px;
            position: absolute;
            left: 50%;
            top: 50%;
            margin-left: -300px;
            margin-top: -100px;
            background: #eee;
        }
        #fistShow input[name="userType"]{
            display: inline-block;
            width:13px;
            height:13px;
            vertical-align: text-top;
            float: none;
        }
        #userAllName,#businessLicenseNum,#authName,#identityCardNum{
            width:25%;
            height:35px;
        }
        .customerFile{
            width:25%;
            height:150px;
            border:1px solid #ccc;
            position: relative;
            background:url(${pageContext.request.contextPath}/static/images/z_add.png) no-repeat;
            background-size: 100% 100%;
            border: 1px solid #ccc;
            cursor: pointer;
        }
        .customerFile img{
            width:100%;
            height:100%;
            cursor: pointer;
        }
        .customerFile input::-webkit-file-upload-button {
            width: 99%;
            height: 99%;
            border: none;
            position: absolute;
            outline: 0;
            opacity: 0;
            cursor: pointer;
        }

        .customerFile input#customerFile {
            display: block;
            width: 99%;
            height: 99%;
            border: 0;
            vertical-align: middle;
            opacity: 0;
            cursor: pointer;
        }
    </style>
</head>

<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="col-lg-12">
            <div class="col-lg-12 m-box m-market" style="padding:0;">
                <div class="box-title">
                    账户信息
                </div>
                <div class="box-content">
                    <ul class="z_cz_tab">
                        <li class="active">基本信息</li>
                        <li>认证信息</li>
                    </ul>
                    <!--    基本信息    -->
                    <div class="col-lg-12 col-md-12 col-xs-12 z_cz_tabContent" style="display:block;">
                        <form class="am-form " style="margin-top:20px;">
                            <fieldset>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">商户账号：</label>
                                    <span style="display:inline-block;line-height:30px;">${users.userName}</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">商户全称：</label>
                                    <span style="display:inline-block;line-height:30px;">${users.userAllName}</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">绑定手机号：</label>
                                    <span style="display:inline-block;line-height:30px;">${users.mobile}</span>
                                    <span style="margin-left:15px;color:blue;text-decoration:underline;cursor:pointer;" class="z_again">重新绑定</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">联系人：</label>
                                    <span style="display:inline-block;line-height:30px;">${users.contacts}</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">联系人手机：</label>
                                    <span style="display:inline-block;line-height:30px;">${users.contactsMobile}</span>
                                </div>

                            </fieldset>
                        </form>
                    </div>

                    <div class="col-lg-12 col-md-12 col-xs-12 z_cz_tabContent" style="display:none;">
                        <!--  首次设置  -->
                        <form class="am-form" id="fistShow" style="display:none;">
                            <fieldset>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">商户类型：</label>
                                    <input class="am-form-field col-lg-11 col-md-10 col-xs-9" id="userIds" type="hidden" value="${users.id}" >
                                    <label style="margin-right: 15px;"><input name="userType" id="userTypeOne" type="radio" value="1" checked="checked">企业</label>
                                    <label><input name="userType" id="userTypeTwo" type="radio" value="2">个人</label>
                                </div>
                                <%--企业--%>
                                <div id="firm">
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4">公司全称：</label>
                                        <input class="am-form-field col-lg-10 col-md-9 col-xs-8" placeholder="请输入2~20汉字" name="userAllName" id="userAllName" value="${users.userAllName}">
                                    </div>
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4">营业执照编号：</label>
                                        <input class="am-form-field col-lg-10 col-md-9 col-xs-8" maxlength="30" placeholder="三证合一的企业填写统一社会信用代码" name="businessLicenseNum" id="businessLicenseNum" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
                                    </div>
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4"></label>
                                        <span style="color: #ccc;display: inline-block;width: 25%;">营业执照上传，支持jpg、gif、png、pdf格式，大小不超过2M。</span>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4"><span style="color: red">*</span>营业执照副本：</label>
                                        <div class="customerFile col-lg-8 col-md-7 col-xs-6">
                                            <img id="businessLicenseImage" name="businessLicenseImage" style="display: none">
                                            <input type="file" name="file" id="customerFile"
                                                   accept="image/jpg,image/jpeg,image/png"
                                                   onchange="asyncUpload('customerFile')"/>
                                        </div>
                                    </div>
                                    <p>
                                        <label class="col-lg-2 col-md-3 col-xs-4"></label>
                                        <button type="button" id="payAdd"  class="z_pFirst am-btn am-btn-default" onclick="firmSubmit()" style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">提交</button>
                                    </p>
                                </div>
                                <%--个人--%>
                                <div id="personal" style="display: none;">
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4">个人姓名：</label>
                                        <input class="am-form-field col-lg-10 col-md-9 col-xs-8" placeholder="" name="name" id="authName">
                                    </div>
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4">身份证号码：</label>
                                        <input class="am-form-field col-lg-10 col-md-9 col-xs-8" placeholder="" name="identityCardNum" id="identityCardNum">
                                    </div>
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4"></label>
                                        <span style="color: #ccc;display: inline-block;width: 25%;">上传本人手持身份证照片，身份证信息清晰完整，与上述姓名、身份证号码保持一致。照片支持jpg、gif、png、pdf格式，大小不超过2M。</span>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4"><span style="color: red">*</span>身份证正面：</label>
                                        <div class="customerFile col-lg-8 col-md-7 col-xs-6">
                                            <img id="identityCardFront" name="identityCardFront" style="display: none">
                                            <input type="file" name="file" id="customerFileTwo"
                                                   accept="image/jpg,image/jpeg,image/png"
                                                   onchange="asyncUploadJust('customerFileTwo')"/>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label class="col-lg-2 col-md-3 col-xs-4"><span style="color: red">*</span>身份证反面：</label>
                                        <div class="customerFile col-lg-8 col-md-7 col-xs-6">
                                            <img id="identityCardBack" name="identityCardBack" style="display: none">
                                            <input type="file" name="file" id="customerFileThree"
                                                   accept="image/jpg,image/jpeg,image/png"
                                                   onchange="asyncUploadAgainst('customerFileThree')"/>
                                        </div>
                                    </div>
                                    <p>
                                        <label class="col-lg-2 col-md-3 col-xs-4"></label>
                                        <button type="button" id="payAddTwo"  class="z_pFirst am-btn am-btn-default" onclick="personageSubmit()" style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">提交</button>
                                    </p>
                                </div>
                            </fieldset>
                        </form>

                        <form class="am-form" id="Two" style="display: none;margin-top: 20px;">
                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4" style="font-size: 24px;">认证状态：</label>
                                <span style="display:inline-block;line-height:30px;font-size: 24px;" id="approveStatus">${userAuth.authState==1?"未认证":userAuth.authState==2?"待审核":userAuth.authState==3?"认证通过":userAuth.authState==4?"认证未通过":" "}</span>
                                <span style="display:inline-block;line-height:30px;font-size: 16px;font-weight: 100;color: #fff;background:red;padding:5px;border-radius:5px ;margin-left:20px;display: none" id="approveStatusOk">已认证</span>
                                <span style="display:inline-block;line-height:30px;font-size: 16px;font-weight: 100;padding:5px;border-radius:5px ;margin-left:20px;display: none;border: 1px solid #4c88f5;cursor: pointer;color: #4c88f5;" id="approveStatusNo">重新认证</span>
                            </div>

                            <div  id="remarkOk" style="display: none">
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">原因：</label>
                                    <span style="display:inline-block;line-height:30px;">${userAuth.remark}</span>
                                </div>
                            </div>

                            <div class="am-form-group">
                                <label class="col-lg-2 col-md-3 col-xs-4">商户类型：</label>
                                <span style="display:inline-block;line-height:30px;" id="look">${userAuth.userType==1?"企业":userAuth.userType==2?"个人":" "}</span>
                            </div>

                            <div id="first" style="display: none">
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">公司全称：</label>
                                    <span style="display:inline-block;line-height:30px;">${users.userAllName}</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">营业执照编号：</label>
                                    <span style="display:inline-block;line-height:30px;">${userAuth.businessLicenseNum}</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">营业执照副本：</label>
                                    <span style="display:inline-block;line-height:30px;"><a href="javascript:;" class="btn1">查看</a></span>
                                    <div class="img1" style="display: none;">
                                        <img src="//<%=path%>/<%=FileName%>/${userAuth.businessLicenseImage}">
                                    </div>
                                </div>
                            </div>
                            <div id="second" style="display: none">
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">个人姓名：</label>
                                    <span style="display:inline-block;line-height:30px;">${userAuth.name}</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">身份证号码：</label>
                                    <span style="display:inline-block;line-height:30px;">${userAuth.identityCardNum}</span>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">手持身份证正面：</label>
                                    <span style="display:inline-block;line-height:30px;"><a href="javascript:;" class="btn1">查看</a></span>
                                    <div class="img1" style="display: none;">
                                        <img src="//<%=path%>/<%=FileName%>/${userAuth.identityCardFront}">
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label class="col-lg-2 col-md-3 col-xs-4">手持身份证反面：</label>
                                    <span style="display:inline-block;line-height:30px;"><a href="javascript:;" class="btn2">查看</a></span>
                                    <div class="img2" style="display: none;">
                                        <img src="//<%=path%>/<%=FileName%>/${userAuth.identityCardBack}">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!--弹窗-->
                    <div class="z_tc" style="display:none;">
                        <div style="width:100%;
                               height:40px;line-height:40px;text-align:right;border-bottom:1px solid #fff;">
                            <a href="javascript: void(0)" class="am-close am-close-spin z_close" data-am-modal-close>&times;</a>
                        </div>
                        <!--  初始状态  -->
                        <form class="am-form " style="margin-top:40px;">
                            <fieldset>
                                <div class="am-form-group">
                                    <input type="hidden" name="userId" value="${users.id}" id="userId">
                                    <label class="col-lg-3 col-md-4 col-xs-5">绑定手机号：</label>
                                    <input class="am-form-field col-lg-9 col-md-8 col-xs-7" id="phoneNumber" type="text" onchange="changeMoble()">
                                    <span class="z_warn"></span>
                                </div>

                                <div class="am-form-group">
                                    <label class="col-lg-3 col-md-4 col-xs-5"></label>
                                    <input class="am-form-field col-lg-9 col-md-8 col-xs-7" type="text" id="code">
                                    <input type="button" class="z_yzm am-btn am-btn-default" value="获取验证码" id="checkCode">
                                    <span class="z_code"></span>
                                </div>
                                <p>
                                    <label class="col-lg-3 col-md-4 col-xs-5"></label>
                                    <button type="button" class="am-btn am-btn-default" id="submitState" style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">提交</button>
                                </p>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<!--[if lte IE 8 ]>
<![endif]-->
<script src="${path}/static/js/upload.js"></script>
<script>
//    $(".z_code").css
$(function(){
    $('.btn1').on('click', function() {
        $('.img1').toggle();
    })
    $('.btn2').on('click', function() {
        $('.img2').toggle();
    })
   var userType = $("#look").text();
//    alert(userType);
    if(userType=="企业"){
        $("#first").show();
        $("#second").hide();
    }else{
        $("#first").hide();
        $("#second").show();
    }
    var approveStatus = $("#approveStatus").text();
    if(approveStatus=="认证未通过"){
        $("#remarkOk").show();
        $("#approveStatusOk").hide();
        $("#approveStatusNo").show();
        $("#approveStatusNo").on("click", function() {
            $("#fistShow").show();
            $("#Two").hide();
        })
    }else if(approveStatus=="认证通过"){
        $("#approveStatusOk").show();
        $("#remarkOk").hide();
        $("#approveStatusNo").hide();
    }
});

    $.ajax({
        url:"${pageContext.request.contextPath }/customer/selectUserAuthByID.do",
        type:"post",
        data:{},
        dataType:"json",
        success:function(data){
            if(data==1){
                $("#fistShow").hide();
                $("#Two").show();
            }else{
                $("#Two").hide();
                $("#fistShow").show();
            }
        }
    });


    function changeMoble(){
        var phoneNumber = $("#phoneNumber").val();
        var reg = /^1[3|4|5|7|8]\d{9}$/;
        if (phoneNumber == "") {
            $("#phoneNumber").siblings(".z_warn").text("请输入手机号！");
        } else if (phoneNumber.length != 11 && !reg.test(phoneNumber)) {
            $("#phoneNumber").siblings(".z_warn").text("请输入正确的手机号！");
        } else if (phoneNumber.length == 11 && reg.test(phoneNumber)) {
            $(".z_warn").text("");
            $(".z_yzm").css("opacity","1");
        }
    }
        //充值tab切换
        $(".z_cz_tab li").each(function() {
            $(".z_cz_tab li").on("click", function() {
                $(this).addClass("active").siblings().removeClass("active");
                if ($(this).index() == 0) {
                    $(".z_cz_tabContent").eq(0).show();
                    $(".z_cz_tabContent").eq(1).hide();
                } else {
                    $(".z_cz_tabContent").eq(0).hide();
                    $(".z_cz_tabContent").eq(1).show();
                }
            });
        });

        //提交按钮点击

    $("#submitState").click(function() {
        var phoneNumber = $("#phoneNumber").val();
        var userId = $("#userId").val();
        var code = $("#code").val();
        var reg = /^1[3|4|5|7|8]\d{9}$/;
        if (phoneNumber == "") {
            alert("请输入手机号！");
            return;
        } else if (phoneNumber.length != 11 && !reg.test(phoneNumber)) {
            alert("请输入正确的手机号！");
            return;
        } if(code == ""){
            alert("验证码不能为空！");
            return;
        }else if(code==c){
            $.ajax({
                url:"${pageContext.request.contextPath }/admin/updateUserMobile.do",
                type:"post",
                data:{
                    id:userId,
                    mobile:phoneNumber
                },
                dataType:"json",
                success:function(data){
                    if(data){
                        alert("恭喜你配置成功！");
                        location.reload();
                    }else{
                        alert("抱歉，配置失败！");
                    }
                }
            });
        }else{
            alert("验证码输入不正确，请重新输入！");
        }
    });

        //弹窗
        $(".z_again").click(function() {
            $(".z_tc").show();
        });
        $(".z_close").click(function() {
            $(".z_tc").hide();
        });

    var count = 60;
    var c;
    /*倒计时*/
    var checkCodes = document.getElementById("checkCode");
    function changeTime() {
        if (count == 0) {
            checkCodes.disabled = 0;
            checkCodes.value = "重新获取";
            count = 60;
        }
        else {
            checkCodes.disabled = 1;
            checkCodes.value= "已发送("+ count + "s)";
            count--;
            setTimeout(function() { changeTime();
                $("#submitState").attr("disabled",false);
            }, 1000);
        }
    }
    checkCodes.onclick = function(){
        var phoneNumber = $("#phoneNumber").val();
        var reg = /^1[3|4|5|7|8]\d{9}$/;
        if (phoneNumber == "") {
            alert("请输入手机号！");
            return;
        } else if (phoneNumber.length != 11 || !reg.test(phoneNumber)) {
            alert("请输入正确的手机号！");
            return;
        }else{
            changeTime();
            $.ajax({
                    url:"${pageContext.request.contextPath }/admin/findCode.do",
                    type:"post",
                    data:{
                        mobile:phoneNumber
                    },
                    dataType:"json",
                    success:function(data) {
                        c = data;
                        setTimeout(function() {
                            alert("验证码失效请重新获取！");
                            $("#submitState").attr("disabled",true);
                        }, 61000);
                    }
                });
        }
    };
    var businessLicenseImage;
    var licenseImage;
    var identityCardFront;
    var identityCardBack;
    //公司图片上传
    function asyncUpload(inp_id) {
        $("#" + inp_id).upload({
            url: '${pageContext.request.contextPath }/customer/customerUpload.do',
            params: {},
            dataType: 'json',
            onSend: function (obj, str) {
                return true;
            },
            onComplate: function (data) {
//                alert(data.reurl);
                businessLicenseImage=data.reurl;
                $("#businessLicenseImage").removeAttr("style");
                licenseImage = "//<%=path%>/<%=FileName%>/"+ data.reurl;
                $("#businessLicenseImage").attr("src",licenseImage);
            }
        });
        $("#" + inp_id).upload("ajaxSubmit");
    }

    //个人图片上传身份证正面
    function asyncUploadJust(inp_id) {
        $("#" + inp_id).upload({
            url: '${pageContext.request.contextPath }/customer/customerUpload.do',
            params: {},
            dataType: 'json',
            onSend: function (obj, str) {
                return true;
            },
            onComplate: function (data) {
//                alert(data.reurl);
                identityCardFront=data.reurl;
                $("#identityCardFront").removeAttr("style");
                licenseImage = "//<%=path%>/<%=FileName%>/"+ data.reurl;
                $("#identityCardFront").attr("src",licenseImage);
            }
        });
        $("#" + inp_id).upload("ajaxSubmit");
    }

    //个人图片上传身份证反面
    function asyncUploadAgainst(inp_id) {
        $("#" + inp_id).upload({
            url: '${pageContext.request.contextPath }/customer/customerUpload.do',
            params: {},
            dataType: 'json',
            onSend: function (obj, str) {
                return true;
            },
            onComplate: function (data) {
//                alert(data.reurl);
                identityCardBack=data.reurl;
                $("#identityCardBack").removeAttr("style");
                licenseImage = "//<%=path%>/<%=FileName%>/"+ data.reurl;
                $("#identityCardBack").attr("src",licenseImage);
            }
        });
        $("#" + inp_id).upload("ajaxSubmit");
    }



        //企业个人切换
        $("#fistShow input[name='userType']").each(function() {
            $("#fistShow input[name='userType']").click(function() {
                if($(this).val() == 1){
                    $("#firm").show();
                    $("#personal").hide();
                }else{
                    $("#firm").hide();
                    $("#personal").show();
                }
            });
        });

    //企业上传认证信息
    function firmSubmit(){
        var userTypeOne = $("#userTypeOne").val();
        var userAllName = $("#userAllName").val();
        var businessLicenseNum = $("#businessLicenseNum").val();
        if(userAllName.trim()==""){
            alert("请填写公司全称!");
            return;
        }
        if(businessLicenseNum.trim()==""){
            alert("请填写营业执照编号!");
            return;
        }
        if(businessLicenseImage==null){
            alert("请填写营业执照图片!");
            return;
        }else{
            $.ajax({
                url:"${pageContext.request.contextPath }/customer/insertUserAuthIdentification.do",
                type:"post",
                data:{
                    userAllName:userAllName,
                    userType:userTypeOne,
                    businessLicenseNum:businessLicenseNum,
                    businessLicenseImage:businessLicenseImage
                },
                dataType:"json",
                success:function(data) {
                    if(data){
                        alert("恭喜你已成功添加认证信息!");
                        location.reload();
                    }else{
                        alert("抱歉添加信息失败!");
                        location.reload();
                    }
                }
            });
        }
    }

    //个人上传认证信息
    function personageSubmit(){
        var userTypeTwo = $("#userTypeTwo").val();
        var authName = $("#authName").val();
        var identityCardNum = $("#identityCardNum").val();
        var isIDCard = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
        if(authName.trim()==""){
            alert("请填写个人姓名!");
            return;
        }else if(authName.trim().length>10){
            alert("姓名太长!");
            return;
        }else if(authName.trim().length<2){
            alert("姓名太短!");
            return;
        }
        if(identityCardNum.trim()==""){
            alert("请填写身份证号!");
            return;
        }else if(!isIDCard.test(identityCardNum.trim())){
            alert("请正确填写您的身份证号!");
            return;
        }
        if(identityCardFront==null){
            alert("请填写身份证正面图片!");
            return;
        }if(identityCardBack==null){
            alert("请填写身份证反面图片!");
            return;
        }else{
            $.ajax({
                url:"${pageContext.request.contextPath }/customer/insertUserAuthIdentification.do",
                type:"post",
                data:{
                    userType:userTypeTwo,
                    name:authName,
                    identityCardNum:identityCardNum,
                    identityCardFront:identityCardFront,
                    identityCardBack:identityCardBack
                },
                dataType:"json",
                success:function(data) {
                    if(data){
                        alert("恭喜你已成功添加认证信息!");
                        location.reload();
                    }else{
                        alert("抱歉添加信息失败!");
                        location.reload();
                    }
                }
            });
        }
    }
</script>

</body>

</html>

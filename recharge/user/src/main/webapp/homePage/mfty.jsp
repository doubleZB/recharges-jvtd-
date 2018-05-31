<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/13
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    var path = "${path}";
</script>
<html>
<head>
    <meta charset="utf-8">
    <title>免费体验</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

    <!-- Link Swiper's CSS -->
    <link href="css/sweetalert.css" rel="stylesheet" />
    <link href="css/base.css" rel="stylesheet" />
    <link href="css/common.css" rel="stylesheet" />
    <link href="css/common_sui.css" rel="stylesheet" />
    <script type="text/javascript" src="js/head5.js"></script>

    <!-- Demo styles -->
</head>

<body>
<!-- Swiper -->
<div class="swiper-container">
    <div class="swiper-wrapper">
        <div class="swiper-slide">
            <div class="mfty">
                <div class="mfty_1">
                    <p class="p1">免费体验云通信服务</p>
                    <p class="p2">
                        只需10秒提交基本信息，
                        <br>客服将第一时间与您联系，
                        <br>为您开通免费试用账号！我们的服务宗旨：速度！热情
                    </p>
                    <div class="tysq">
                        <p>体验申请</p>
                        <div>
                            <span>姓名：</span>
                            <input type="text" class="tyname">
                        </div>
                        <div>
                            <span>手机号：</span>
                            <input type="text" class="tyiphone" id="sj">
                        </div>
                        <div>
                            <span>验证码：</span>
                            <input type="text" style="width:70px;" class="tyyzm">
                            <button id="btns">获取验证码</button>
                            <input type="hidden" value="" id="hi">
                        </div>
                        <button class="ljsq">立即申请</button>
                    </div>
                </div>


            </div>
        </div>
    </div>
</div>

<!-- Swiper JS -->
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/common.js"></script>
<script src="js/sweetalert-dev.js"></script>
<script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fdf00213b49c7db02e291a5e08cd8c5d5' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
<script>
    $(function() {

        var y;
        var wait=60;
        var btn = document.getElementById("btns");
        function time(o) {
            if (wait == 0) {
                $("#hi").val("");
                //o.removeAttribute("disabled");
                o.innerHTML="获取验证码";
                wait = 60;
            } else { // www.jbxue.com
                //o.setAttribute("disabled", true);
                o.innerHTML="重新发送(" + wait + ")";
                wait--;
                setTimeout(function() {
                            time(o)
                        },
                        1000)
            }
        }

        document.getElementById("btns").onclick=function(){
            //alert(1);
            //return false;

            var hi=$("#hi").val();
            //time(btn);
            if(hi!=1){
                $("#hi").val("1");
                var tel=$("#sj").val();
                if(!tel.match(/^0?(13[0-9]|15[012356789]|18[0-9]|14[57]|17[06378])[0-9]{8}$/)){
                    $("#hi").val("")
                    swal({
                        title: "",
                        text: "手机号格式不对",
                        type: "error",
                        showCancelButton: false,
                        confirmButtonColor: "#ff5a4a",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    });
                    return false;
                }else{
                    var url="/site/mftyinfo.html";
                    var tel=$("#sj").val();
                    //time(btn);
                    $.post(url,{tel:tel},function(data){
                        if(data=="n"){
                            $("#hi").val("")
                            swal({
                                title: "",
                                text: "手机号已经被注册过了",
                                type: "warning",
                                showCancelButton: false,
                                confirmButtonColor: "#ff5a4a",
                                confirmButtonText: "确定",
                                closeOnConfirm: false
                            });
                            return false;
                        }else if(data=="no"){
                            $("#hi").val("")
                            swal({
                                title: "",
                                text: "短信发送失败",
                                type: "warning",
                                showCancelButton: false,
                                confirmButtonColor: "#ff5a4a",
                                confirmButtonText: "确定",
                                closeOnConfirm: false
                            });
                            return false;
                        }else{
                            time(btn);
                            y=data;
                        }
                    });
                }
            }
        }




//         $(".quer").click(function(){
//             $(".tan").hide();
//         })
        $(".ljsq").click(function() {
            var tyname = $(".tyname").val();
            var tyiphone = $(".tyiphone").val();
            var tyyzm = $(".tyyzm").val();
            var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            if (tyname == "") {
                swal({
                    title: "",
                    text: "请输入您的姓名",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#ff5a4a",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                });
                return false;
            } else if (tyiphone == "") {
                swal({
                    title: "",
                    text: "请输入手机号码",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#ff5a4a",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                });
                return false;
            } else if (!myreg.test(tyiphone)) {
                swal({
                    title: "",
                    text: "手机号码不正确",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#ff5a4a",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                });
                iphone = "";
                return false;
            } else
            if (tyyzm == "") {
                swal({
                    title: "",
                    text: "请输入验证码",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#ff5a4a",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                });
                return false;
            } else if(y!=tyyzm){

                swal({
                    title: "",
                    text: "验证码不正确",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#ff5a4a",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                });
                return false;

            }
            else{

                $.ajax({

                    type:"POST",
                    url:"/site/mftycreate.html",
                    data:{"name":tyname,"phone":tyiphone},
                    success:function(data){

                        if(data=="y"){

                            swal({
                                        title: "",
                                        text: "亲，恭喜您申请成功！\n客服人员将第一时间与您联系\n为您开通测试账号，请您保持电话畅通。\n咨询电话：400-056-6681",
                                        showCancelButton: false,
                                        confirmButtonColor: "#ff5a4a",
                                        confirmButtonText: "确定",
                                        closeOnConfirm: true
                                    },
                                    function() {
                                        location.href = "/mfty.html";
                                    });

                        }

                    }

                })

            }
        })
    })

</script>

</html>

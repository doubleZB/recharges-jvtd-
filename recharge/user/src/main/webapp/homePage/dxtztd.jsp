<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/13
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    var path = "${path}";
</script>
<html>
<head>
    <meta charset="UTF-8">
    <title>营销短信_销售短信,促销短信,短信营销平台,短信广告,短信通知-聚通达云通信</title>
    <meta name="keywords" content="营销短信,销售短信,促销短信,短信营销平台,短信广告,短信通知">
    <meta name="description" content="聚通达云通信营销短信平台可用于销售短信、促销短信、短信广告、短信通知的发布，短信平台接口稳定、速度快，不受时间和地域的限制，可在几秒时间内传播给用户，能更好的为客户提供短信服务。">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    <link href="css/base.css" rel="stylesheet" />
    <link href="css/common.css" rel="stylesheet" />
    <link href="css/common_wu.css" rel="stylesheet" />
    <link href="css/sweetalert.css" rel="stylesheet" />
    <link href="css/bootstrap.css" rel="stylesheet" />
    <script type="text/javascript" src="js/head1.js"></script>
    <script type="text/javascript" src="js/nav_right.js"></script>
</head>

<body>

<div class="dxtztd_main">
    <div class="banner">
        <div class="banner_main">
        </div>
    </div>
    <div class="yycj">
        <div class="yycj_cont">
            <h2>短信通知应用场景</h2>
            <ul class="ul1">
                <li>
                    <a href="javascript:;">
                        <span>订单通知</span>
                        <img src="images/dxtztd_img2.png" alt="">
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span>会员活动</span>
                        <img src="images/dxtztd_img3.png" alt="">
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span>积分消息</span>
                        <img src="images/dxtztd_img4.png" alt="">
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <span>获奖结果</span>
                        <img src="images/dxtztd_img5.png" alt="">
                    </a>
                </li>
            </ul>
            <div style="float: left;width: 50%;text-align: center;">
                <img src="images/dxtztd_img6.png" alt="">
            </div>
            <ul class="ul2" style="float: left;width: 25%;text-align: center;">
                <li>
                    <a href="javascript:;">
                        <img src="images/dxtztd_img7.png" alt="">
                        <span>优惠促销</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <img src="images/dxtztd_img8.png" alt="">
                        <span>预约确认</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <img src="images/dxtztd_img9.png" alt="">
                        <span>消费提醒</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:;">
                        <img src="images/dxtztd_img10.png" alt="">
                        <span>扣费信息</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="tiyan">
        <div class="tiyan_cont">
            <h2>5秒必达敢试下吗</h2>
            <p>
                <input id="iphone" type="text" placeholder="体验手机号" style="width: 280px;">
            </p>
            <p>
                <input id="yzm" type="text" placeholder="图形验证码">
                <img class="imgyzm" src="" alt=""  onclick="createVerify()" style="width:93px;">
            </p>
            <p class="help-block help-block-error">
            </p>
            <button class="ty">立即体验5秒必达</button>
        </div>
    </div>
    <div>
        <div class="col-md-12 col-lg-12">
            <h3 class="text-center" style="padding: 40px 0;">短信通知特点</h3>
        </div>
        <div class="dxtztd_row clearfix">
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/dxtztd_img12.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">速度快</h5>
                <p class="text-center">不受时间和地域的限制，可在几秒时间内传播给用户。 </p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/dxtztd_img13.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">成本低</h5>
                <p class="text-center">发布费用低廉，大大降低运营费用，减少成本。</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/dxtztd_img14.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">体验好</h5>
                <p class="text-center">提升用户好感度，增强用户体验 </p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/dxtztd_img15.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">粘度高</h5>
                <p class="text-center">维系老客户，开发新客户，可保持源源不断的客户流量</p>
            </div>
        </div>
    </div>
    <div class="about_main_div_zx">
        <h2 class="text-center">价格可低至0.04元/条起</h2>
        <div class="col-lg-12 col-md-12 text-center">
            <a href="http://dwz.cn/3Jo1MK" target="_Blank" class="about_main_div_button">报价咨询</a></div>
        <br style="clear:both"/>
        <br/>
        <br/>
        <br/>
    </div>

</div>

<!------------尾部------------------>

<script type="text/javascript" src="js/bottom.js"></script>
<!------------尾部结束----------------->
<script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fdf00213b49c7db02e291a5e08cd8c5d5' type='text/javascript'%3E%3C/script%3E"));
</script>

</body>

<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/nav_left.js"></script>
<script src="js/sweetalert-dev.js"></script>
<script>
    $(".ty").click(function () {
        var iphone = $("#iphone").val();
        var yzm = $("#yzm").val();
        var myreg = /^0?(13[0-9]|15[012356789]|18[0-9]|14[57]|17[06378])[0-9]{8}$/; //手机号
        if (!myreg.test(iphone)) {
            swal({
                title: "",
                text: "请输入正确的手机号",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#ff5a4a",
                confirmButtonText: "确定",
                closeOnConfirm: false
            });
            return false;
        } else if ($.trim(yzm) =="") {
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
        } else {
            var url = "/site/ajaxcode.html";
            $.post(url,{tel:iphone,code:yzm},function(da){
                if(da=='y'){
                    swal({
                        title: "",
                        text: "短信发送成功",
                        type: "success",
                        showCancelButton: false,
                        confirmButtonColor: "#ff5a4a",
                        confirmButtonText: "确定",
                        closeOnConfirm: true
                    });
                }else if(da=='no'){
                    swal({
                        title: "",
                        text: "您已经体验过了",
                        type: "error",
                        showCancelButton: false,
                        confirmButtonColor: "#ff5a4a",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    });
                    return false;
                }else if(da=='n'){
                    swal({
                        title: "",
                        text: "验证码错误",
                        type: "error",
                        showCancelButton: false,
                        confirmButtonColor: "#ff5a4a",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    });
                    return false;
                }else if(da=="NO"){
                    swal({
                        title: "",
                        text: "短信发送失败请刷新页面",
                        type: "error",
                        showCancelButton: false,
                        confirmButtonColor: "#ff5a4a",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    });
                    return false;
                }else{
                    swal({
                        title: "",
                        text: "数据错误请刷新页面",
                        type: "error",
                        showCancelButton: false,
                        confirmButtonColor: "#ff5a4a",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    });
                    return false;
                }
            })
        }
    })
</script>
<script type="text/javascript">
    function createVerify(){   //生成验证码
        $(".imgyzm").attr('src','/site/verify.html?thistime='+ Math.random());

    }
    createVerify();

    $(document).ready(function(){
        $('#loginform-verifycode-image').click(function(){
            this.src = '/site/captcha.html?v='+parseInt(Math.random()*10000000000000);
        })

    });
</script>
</html>

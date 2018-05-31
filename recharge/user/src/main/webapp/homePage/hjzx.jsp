<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/13
  Time: 17:43
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
    <title>呼叫中心</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    <link href="css/base.css" rel="stylesheet" />
    <link href="css/common.css" rel="stylesheet" />
    <link href="css/common_wu.css" rel="stylesheet" />
    <link href="css/bootstrap.css" rel="stylesheet" />
    <script type="text/javascript" src="js/head2.js"></script>
    <script type="text/javascript" src="js/nav_right.js"></script>
</head>

<body>

<div class="dxtztd_main hjzx_main">
    <img class="banner" src="images/hjzx_banner.jpg">
    <div class="fwfs clearfix">
        <h2>服务方式</h2>
        <ul class="clearfix">
            <li>
                <a>
                    <img src="images/hjzx_img1.png" alt="">
                    <h4>服务型呼叫中心</h4>
                    <p>
                        高品质的呼入服务，提高客户满意度，降低成本，改善变动费用，创造更高的价值。
                    </p>
                </a>
            </li>
            <li>
                <a>
                    <img src="images/hjzx_img2.png" alt="">
                    <h4>销售型呼叫中心</h4>
                    <p>
                        从市场开拓到顾客满意度调查，提供一站式的全方位服务，简单高效，提升销售团队战斗力。
                    </p>
                </a>
            </li>
        </ul>
    </div>
    <div style="background: #eaeaea;">
        <div class="col-md-12 col-lg-12">
            <h3 class="text-center" style="padding: 40px 0;font-size: 40px;font-weight: bold;">功能特性</h3>
        </div>
        <div class="dxtztd_row clearfix" style="background: none;">
            <div class="col-md-3 col-lg-3">
                <div class="div_img" style="height: 260px;">
                    <img src="images/hjzx_img8.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">呼叫与接听</h5>
                <p class="text-center">低资费、高质量、多类型的呼叫支持软电话、手机座机、IP电话的多平台接听方式</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img" style="height: 260px;">
                    <img src="images/hjzx_img9.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">IVR语音导航</h5>
                <p class="text-center">可自定义、实时控制IVR语音导航流程，实现按键菜单、呼叫转移、自动识别呼入号码等服务</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img" style="height: 260px;">
                    <img src="images/hjzx_img10.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">坐席管理</h5>
                <p class="text-center">提供座席上下班及忙闲状态、创建删除队列、呼叫转接、班长监听等座席相关管理接口</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img" style="height: 260px;">
                    <img src="images/hjzx_img11.png">
                </div>
                <h5 class="text-center" style="color: #000000;font-size: 20px;">通话录音</h5>
                <p class="text-center">根据需要自定义录音规则，并可通过API控制相关通话是否录音，平台还提供云端储存空间</p>
            </div>
        </div>
    </div>
    <div>
        <div class="col-md-12 col-lg-12">
            <h3 class="text-center" style="padding: 40px 0;font-size: 40px;font-weight: bold;">服务特色</h3>
        </div>
        <div class="dxtztd_row clearfix">
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/hjzx_img4.png">
                </div>
                <h5 class="text-center" style="color: #69acf7;font-size: 20px;">一省到底</h5>
                <p class="text-center">不仅节省搭建系统和硬件的费用，平台还有丰富、优质、优惠的运营商线路供您选择</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/hjzx_img5.png">
                </div>
                <h5 class="text-center" style="color: #69acf7;font-size: 20px;">统一管理</h5>
                <p class="text-center">无需组网，快速实现呼叫中心全国多点部署，统一号码接入，本地市话号呼入呼出。</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/hjzx_img6.png">
                </div>
                <h5 class="text-center" style="color: #69acf7;font-size: 20px;">可用性保证</h5>
                <p class="text-center">我们拥有专业的呼叫中心运营团队，以及多年的技术沉淀和经验积累</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/hjzx_img7.png">
                </div>
                <h5 class="text-center" style="color: #69acf7;font-size: 20px;">坐席数量可控</h5>
                <p class="text-center">根据业务的变化，随时增减座席数量，应对业务发展的波动。</p>
            </div>
        </div>
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

</html>


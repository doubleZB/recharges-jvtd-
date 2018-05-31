<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/13
  Time: 17:03
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
    <title>产品介绍-IM</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    <link href="css/base.css" rel="stylesheet" />
    <link href="css/common.css" rel="stylesheet" />
    <link href="css/common_wu.css" rel="stylesheet" />
    <link href="css/bootstrap.css" rel="stylesheet" />
    <script type="text/javascript" src="js/head1.js"></script>
    <script type="text/javascript" src="js/nav_right.js"></script>
</head>

<body>

<div class="im_main">
    <div class="banner">
        <img src="images/im_banner.jpg" alt="">
    </div>
    <div>
        <div class="col-md-12 col-lg-12">
            <h3 class="text-center">我们的优势</h3>
        </div>
        <div class="im_row clearfix">
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img1.png">
                </div>
                <h5 class="text-center" style="color: #ed836b;font-size: 20px;">稳定</h5>
                <p class="text-center">全国范围多个数据接入点，支持扩容，满足分布式高冗余业务需求，就近接入 </p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img2.png">
                </div>
                <h5 class="text-center" style="color: #52823d;font-size: 20px;">可靠</h5>
                <p class="text-center">消息增量同步，消息不丢、不重、有序，私有通讯协议，数据加密，安全可靠</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img3.png">
                </div>
                <h5 class="text-center" style="color: #2884c6;font-size: 20px;">方便</h5>
                <p class="text-center">SDK包体小，精细的API，详细的步骤式文档， UI组件集成简单 </p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img4.png">
                </div>
                <h5 class="text-center" style="color: #e51960;font-size: 20px;">灵活</h5>
                <p class="text-center">TCP通信包与IM业务包、音频业务包和视频业务包完全独立，客户可以根据自己的业务需求进行自由配置 </p>
            </div>
        </div>
    </div>
    <div>
        <div class="col-md-12 col-lg-12">
            <h3 class="text-center" style="color:#fff;">功能特性</h3>
        </div>
        <div class="im_row im_row1 clearfix">
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img6.png">
                </div>
                <h5 class="text-center" style="color: #fff;font-size: 20px;">数据自定义</h5>
                <p class="text-center">支持文本、图片、语音、视频或结构化数据，允许自定义数据打造出与众不同的应用产品 </p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img7.png">
                </div>
                <h5 class="text-center" style="color: #fff;font-size: 20px;">统一发放</h5>
                <p class="text-center">依赖平台统一分发，支持跨平台SDK集成，可快速集成至APP、Web页面</p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img8.png">
                </div>
                <h5 class="text-center" style="color: #fff;font-size: 20px;">离线缓存</h5>
                <p class="text-center">支持离线缓存信息，账号未上线时，可通过接口控制平台存储、上线下发消息 </p>
            </div>
            <div class="col-md-3 col-lg-3">
                <div class="div_img">
                    <img src="images/im_img9.png">
                </div>
                <h5 class="text-center" style="color: #fff;font-size: 20px;">推送服务</h5>
                <p class="text-center">支持集成系统推送服务以及第三方推送服务</p>
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
<script type="text/javascript" src="js/nav_left.js"></script>

</html>

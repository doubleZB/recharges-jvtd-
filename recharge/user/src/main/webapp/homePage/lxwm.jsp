<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/13
  Time: 17:47
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
    <title>联系我们-聚通达云通信</title>
    <meta name="keywords" content="联系我们,聚通达云通信,云通信,聚通达">
    <meta name="description" content="聚通达云通信隶属于北京聚通达科技股份有限公司，是国家高新技术企业，大家可以通过客服热线、在线留言、微信等方式与我们联系。">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    <link href="css/base.css" rel="stylesheet" />
    <link href="css/common.css" rel="stylesheet" />
    <link href="css/common_sui.css" rel="stylesheet" />
    <link href="css/common_Ealin.css" rel="stylesheet" />
    <link href="css/bootstrap.css" rel="stylesheet" />
    <script type="text/javascript" src="js/head4.js"></script>
    <script type="text/javascript" src="js/nav_right.js"></script>
</head>

<body>

<div class="sssp_main">
    <!--         <div><img src="/images/lxwm_img1.jpg" class="sssp_main_img"></div> -->
    <div class="mtbd_main_div1 col-md-12 col-lg-12">
        <div class="col-lg-1 col-md-1"></div>
        <div class="col-lg-10 col-md-10">
            <h3><i>&nbsp;&nbsp;</i>&nbsp;&nbsp;联系我们</h3>
            <!--                 <p>北京聚通达科技股份有限公司</p> -->
            <!--                 <p>商务合作：</p> -->
            <!--                 <p>姓名：肖宁波</p> -->
            <!--                 <p>QQ联系：657855889</p> -->
            <!--                 <p>联系电话：18602676602</p> -->
            <!--                 <p>邮箱地址：xiaonb@jvtd.cn</p> -->
            <!--                 <p>公司地址：北京朝阳区惠河南街1102号国粹苑A座四层4069-4078 -->
            <!-- </p> -->
            <span style="font-size:18px;"></span><br />
            <span style="font-size:18px;">北京聚通达科技股份有限公司</span><br />
            <span style="font-size:18px;">企业热线：400-056-6681</span><br />
            <p>
                <span style="font-size:18px;">企业邮箱：bd@jvtd.cn</span>
            </p>
            <p>
                <span style="font-size:18px;line-height:1.5;">公司地址：北京朝阳区惠河南街1102号国粹苑A座四层4069-4078</span>
            </p>
            <span style="font-size:18px;"></span><br />
            <div class="rig">
                <div class="">

                    <!--引用百度地图API-->

                    <style type="text/css">
                        html,
                        body {
                            margin: 0;
                            padding: 0;
                        }

                        .iw_poi_title {
                            color: #CC5522;
                            font-size: 14px;
                            font-weight: bold;
                            overflow: hidden;
                            padding-right: 13px;
                            white-space: nowrap;
                        }

                        .iw_poi_content {
                            font: 12px arial, sans-serif;
                            overflow: visible;
                            padding-top: 4px;
                            white-space: -moz-pre-wrap;
                            word-wrap: break-word;
                        }

                    </style>

                    <!--百度地图容器-->
                    <div style="width:60%;height:350px;border:#ccc solid 1px;" id="dituContent"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="col-lg-1 col-md-1"></div>
<br/ style="clear:both">
<!------------尾部------------------>

<script type="text/javascript" src="js/bottom.js"></script>
<!------------尾部结束----------------->
<script type="text/javascript">
    var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
    document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fdf00213b49c7db02e291a5e08cd8c5d5' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/ditu.js"></script>
<script type="text/javascript">
    //创建和初始化地图函数：
    function initMap() {
        createMap(); //创建地图
        setMapEvent(); //设置地图事件
        addMapControl(); //向地图添加控件
        addMarker(); //向地图中添加marker
    }

    //创建地图函数：
    function createMap() {
        var map = new BMap.Map("dituContent"); //在百度地图容器中创建一个地图
        var point = new BMap.Point(116.516542, 39.909453); //定义一个中心点坐标
        map.centerAndZoom(point, 17); //设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map; //将map变量存储在全局
    }

    //地图事件设置函数：
    function setMapEvent() {
        map.enableDragging(); //启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom(); //启用地图滚轮放大缩小
        map.enableDoubleClickZoom(); //启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard(); //启用键盘上下左右键移动地图
    }

    //地图控件添加函数：
    function addMapControl() {
        //向地图中添加缩放控件
        var ctrl_nav = new BMap.NavigationControl({
            anchor: BMAP_ANCHOR_TOP_LEFT,
            type: BMAP_NAVIGATION_CONTROL_LARGE
        });
        map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
        var ctrl_ove = new BMap.OverviewMapControl({
            anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
            isOpen: 1
        });
        map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
        var ctrl_sca = new BMap.ScaleControl({
            anchor: BMAP_ANCHOR_BOTTOM_LEFT
        });
        map.addControl(ctrl_sca);
    }

    //标注点数组
    var markerArr = [{
        title: "北京聚通达科技股份有限公司",
        content: "我的备注",
        point: "116.514503|39.909813",
        isOpen: 0,
        icon: {
            w: 21,
            h: 21,
            l: 0,
            t: 0,
            x: 6,
            lb: 5
        }
    }];
    //创建marker
    function addMarker() {
        for (var i = 0; i < markerArr.length; i++) {
            var json = markerArr[i];
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0, p1);
            var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point, {
                icon: iconImg
            });
            var iw = createInfoWindow(i);
            var label = new BMap.Label(json.title, {
                "offset": new BMap.Size(json.icon.lb - json.icon.x + 10, -20)
            });
            marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                borderColor: "#808080",
                color: "#333",
                cursor: "pointer"
            });

            (function() {
                var index = i;
                var _iw = createInfoWindow(i);
                var _marker = marker;
                _marker.addEventListener("click", function() {
                    this.openInfoWindow(_iw);
                });
                _iw.addEventListener("open", function() {
                    _marker.getLabel().hide();
                })
                _iw.addEventListener("close", function() {
                    _marker.getLabel().show();
                })
                label.addEventListener("click", function() {
                    _marker.openInfoWindow(_iw);
                })
                if (!!json.isOpen) {
                    label.hide();
                    _marker.openInfoWindow(_iw);
                }
            })()
        }
    }
    //创建InfoWindow
    function createInfoWindow(i) {
        var json = markerArr[i];
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>" + json.content + "</div>");
        return iw;
    }
    //创建一个Icon
    function createIcon(json) {
        var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(json.w, json.h), {
            imageOffset: new BMap.Size(-json.l, -json.t),
            infoWindowOffset: new BMap.Size(json.lb + 5, 1),
            offset: new BMap.Size(json.x, json.h)
        })
        return icon;
    }

    initMap(); //创建和初始化地图

</script>
<script src="js/common.js"></script>

</html>

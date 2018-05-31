<%--
  Date: 2017/4/13
  Time: 14:32
  To change this template use File | Settings | File Templates.
  余额查询
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/static/css/common.css">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/css/rechange.css">
</head>

<body>

<div class="container" style="padding:23px;">
    <div class="row">
        <div class="am-u-lg-12" style="padding:0;">
            <div class="am-u-lg-12 m-box m-market" style="padding:0;">
                <div class="am-u-lg-12 z_head">
                    <div class="am-u-lg-3 am-u-md-4">
                        <div class="z_imgBorder">
                            <img src="${path}/static/images/phone.png" alt="">
                        </div>
                    </div>
                    <div class="am-u-lg-5 am-u-md-6 z_describe">
                        <div class="am-u-lg-12 z_top" style="padding:0;">
                            <h1>余额查询</h1>
                            <span>支持HTTPS</span>
                        </div>
                        <div class="am-u-lg-12 z_data" style="padding:0;">
                            <div class="am-u-lg-12" style="padding:0;">
                                <p class="am-u-lg-6">接口状态：<span class="z_green">正常</span></p>
                                <p class="am-u-lg-6">接入服务商：<span class="z_fBlue">云通信</span></p>
                            </div>
                        </div>
                    </div>
                    <div class="am-u-lg-4 am-u-md-2"></div>
                </div>
                <div class="am-u-lg-12 z_content" style="padding: 0 40px;">
                    <div class="am-tabs" data-am-tabs>
                        <ul class="am-tabs-nav am-nav am-nav-tabs">
                            <li class="am-active"><a href="#tab1">API</a></li>
                        </ul>

                        <div class="am-tabs-bd">
                            <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                                <div class="am-u-lg-3 am-u-md-4" style="padding:0;">
                                    <ul class="z_leftList">
                                        <li class="active"><a href="javascript:;">1.余额查询</a></li>
                                    </ul>
                                </div>

                                <div class="am-u-lg-9 am-u-md-8 rightHeight">
                                    <!--流量充值 -->
                                    <div id="change1">
                                        <ul>
                                            <li>接口地址：http://api.yunpaas.cn/gateway/queryBalance</li>
                                            <li>请求方式：http post/get</li>
                                            <li>请求示例：http://api.yunpaas.cn/gateway/queryBalance?
                                                token=5bd75125b494496eb6fd547b98496dbe
                                            </li>
                                            <li>接口备注：流量充值</li>
                                        </ul>
                                        <p class="z_tabTit">请求参数说明:</p>
                                        <table class="am-table">
                                            <thead>
                                            <tr style="background:#eee;border-color:#eee!important;">
                                                <th>名称</th>
                                                <th>类型</th>
                                                <th>必填</th>
                                                <th>说明</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>token</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>token值,在开发者信息页查询</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <p class="z_tabTit">返回参数说明:</p>
                                        <table class="am-table">
                                            <thead>
                                            <tr style="background:#eee;border-color:#eee!important;">
                                                <th>名称</th>
                                                <th>类型</th>
                                                <th>说明</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>status</td>
                                                <td>string</td>
                                                <td>查询状态 1查询成功 2token不正确 3缺少必要参数 1099系统异常</td>
                                            </tr>
                                            <tr>
                                                <td>statusMsg</td>
                                                <td>string</td>
                                                <td>状态说明</td>
                                            </tr>
                                            <tr>
                                                <td>userBalance</td>
                                                <td>string</td>
                                                <td>余额</td>
                                            </tr>
                                            </tbody>
                                        </table>

                                        <p class="z_tabTit" style="font-weight:900;">JSON返回示例：</p>
                                        <div class="z_example">
                                            {"status":"1","statusMsg":"query success","userBalance":44.000}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/rechange.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script>
    $(function(){
        var _height = $(".rightHeight").height();
        $(".z_leftList").height(_height);
    });
    var vm = new Vue({
        el: "#tab3",
        data: {
            json: []
        }
    });
    function flownum() {
        var userId = $("#userId").val();
        var businessType = $("#businessType").val();
        var appType = 1;
        $.ajax({
            url: "/developer/flowdocuments",
            type: 'POST',
            data: {
                userId: userId,
                appType: appType,
                businessType: businessType
            },
            dataType: "json",
            error: function () {
                $(this).addClass("done");
            },
            success: function (data) {
                if (data.length > 0) {
                    $(".sj").hide();
                    vm.json = data;
                } else {
                    $(".sj").show();
                    vm.json = data;
                }
            }
        });
    };
</script>

</body>
</html>

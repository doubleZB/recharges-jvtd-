<%--
  Created by IntelliJ IDEA.
  User: 代码描述
  Date: 2016/12/13
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/static/css/common.css">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/css/paraiso.light.css">

    <style>
        body {
            background: none;
            font-size: 14px;
        }
        p {
            margin: 0;
            padding: 0;
        }

        .z_list {
            background: #fff;
            padding: 10px 25px;
        }

        .z_list li {
            line-height: 40px;
            font-size: 12px;
            color: #666;
        }

        .z_list li span {
            margin-right: 10px;
        }

        .z_list li span i {
            margin-right: 5px;
        }

        .z_codeTit {
            font-weight: 900;
        }

    </style>
</head>

<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="am-u-lg-12" style="padding:0;">
            <div class="am-u-lg-12 m-box m-market" style="padding:0;background:#eee;">
                <ul class="z_list">
                    <li>代码描述：基于PHP的聚合数据手机话费充值API调用代码示例</li>
                    <li>关联数据：<a href="#">手机话费充值</a></li>
                    <li>接口地址：<a href="#">http://www.juhe.cn/docs/api/id/85</a></li>
                    <li>
                        <span><i class="fa fa-bookmark-o"></i>JoneDwyane</span>
                        <span><i class="fa fa-bar-chart"></i>10466</span>
                        <span><i class="fa fa-clock-o"></i>2015-06-16 17:11:38</span>
                    </li>
                </ul>
                <div class="z_list">
                    <p>本代码是基于聚合数据的话费充值API实现的话费充值功能，使用前需要：</p>
                    <p>①：通过http://www.juhe.cn/docs/api/id/85申请一个appkey</p>
                </div>
                    <pre><code>
                        <span class="z_codeTit">一、引入封装的代码类</span>
                    <span>//----------------------------------
                    // 聚合数据-手机话费充值API调用示例代码
                    //----------------------------------
                    header('Content-type:text/html;charset=utf-8');
                    include 'class.juhe.recharge.php'; //引入文件</span>

                        <span class="z_codeTit">二、配置一些必须的参数</span>
                    <span>//接口基本信息配置
                    $appkey = '291bf7184**********************'; //从聚合申请的话费充值appkey
                    $openid = 'JH8d954266539************'; //注册聚合账号就会分配的openid，在个人中心可以查看
                    $recharge = new recharge($appkey,$openid);</span>

                        <span class="z_codeTit">三、检测手机号码以及面额是否可以充值</span>
                    <span>$telQueryRes =$recharge->telquery('18913511234',10); #可以选择的面额5、10、20、30、50、100、300
                    if($telQueryRes['error_code'] == '0'){
                        //正常获取到话费商品信息
                        $proinfo = $telQueryRes['result'];
                        /*
                        [cardid] => 191406
                        [cardname] => 江苏电信话费10元直充
                        [inprice] => 10.02
                        [game_area] => 江苏苏州电信
                        */
                       echo "商品ID：".$proinfo['cardid'].";
                       echo "商品名称：".$proinfo['cardname'].";
                       echo "进价：".$proinfo['inprice'].";
                       echo "手机归属地：".$proinfo['game_area'].";
                    }else{
                        //查询失败，可能维护、不支持面额等情况
                        echo $telQueryRes["error_code"].":".$telQueryRes['reason'];
                    }</span>

                        <span class="z_codeTit">四、根据手机号码以及面额查询商品信息</span>
                    <span>$orderid = '111111111'; //自己定义一个订单号，需要保证唯一
                    $telRechargeRes = $recharge->telcz('18913513535',5,$orderid); #可以选择的面额5、10、20、30、50、100、300
                    if($telQueryRes['error_code'] =='0'){
                        //提交话费充值成功，可以根据实际需求改写以下内容
                        echo "充值成功,订单号：".$telRechargeRes['result']['sporder_id'];
                        var_dump($telRechargeRes);
                    }else{
                        //提交充值失败，具体可以参考$telRechargeRes['reason']
                        var_dump($telRechargeRes);
                    }</span>

                    </code></pre>


            </div>

        </div>

    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/highlight.pack.js"></script>
<script>
    hljs.initHighlightingOnLoad();
</script>
</body>
</html>

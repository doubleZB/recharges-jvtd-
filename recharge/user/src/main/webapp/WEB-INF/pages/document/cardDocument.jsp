<%--
  Date: 2017/4/13
  Time: 14:32
  To change this template use File | Settings | File Templates.
  流量接口文档
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
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
                            <h1>物联网</h1>
                            <span>支持HTTPS</span>
                        </div>
                        <p class="am-u-lg-12" style="margin:10px 0 15px 0;font-size:12px;color:#666;padding:0;">物联网卡状态和流量查询</p>
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
                            <li><a href="#tab2">签名算法</a></li>
                            <%--<li onclick="flownum()"><a href="#tab3">流量包编码</a></li>--%>
                            <li><a href="#tab4">示例代码</a></li>
                        </ul>

                        <div class="am-tabs-bd">
                            <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                                <div class="am-u-lg-3 am-u-md-4" style="padding:0;">
                                    <ul class="z_leftList">
                                        <li class="active"><a href="javascript:;">1.查询设备信息</a></li>
                                        <li><a href="javascript:;">2.查询激活时间</a></li>
                                        <li><a href="javascript:;">3.查询流量</a></li>
                                    </ul>
                                </div>

                                <div class="am-u-lg-9 am-u-md-8 rightHeight">
                                    <!--查询设备信息 -->
                                    <div id="change1">
                                        <ul>
                                            <li>接口地址：http://api.yunpaas.cn/iotQuery/device</li>
                                            <li>请求方式：http post/get</li>
                                            <li>请求示例：http://api.yunpaas.cn/iotQuery/device?
                                                token=5bd75125b494496eb6fd547b98496dbe
                                                &msisdn=106476565&&iccid=898607B6191792616064
                                                &sign=e8f277b2b04a2be7a5757da7da18e0c9</li>
                                            <li>接口备注：查询设备信息</li>
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
                                            <tr>
                                                <td>iccid</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>IC卡的唯一识别码</td>
                                            </tr>
                                            <tr>
                                                <td>msisdn</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>MSISDN号码</td>
                                            </tr>
                                            <tr>
                                                <td>sign</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>校验值，请求参数按参数名升序排序, 将除sign外的请求参数名及参数值相互连接，例如：iccid89860616010049046952msisdn861064619018767token1234abc，并将token添加到拼接串的头尾，
                                                	再对该字符串进行SHA1运算，得到16进制小写字符串
                                                </td>
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
                                                <td>iccid</td>
                                                <td>string</td>
                                                <td>IC卡识别码</td>
                                            </tr>
                                            <tr>
                                                <td>imsi</td>
                                                <td>string</td>
                                                <td>国际移动用户识别码</td>
                                            </tr>
                                            <tr>
                                                <td>msisdn</td>
                                                <td>string</td>
                                                <td>MSISDN号</td>
                                            </tr>
                                            <tr>
                                                <td>imei</td>
                                                <td>string</td>
                                                <td>手机串号</td>
                                            </tr>
                                            <tr>
                                                <td>cardStatus</td>
                                                <td>string</td>
                                                <td>卡状态：未知,&nbsp;未激活,&nbsp;正常,&nbsp;停用,&nbsp;注销,&nbsp;测试</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <p class="z_tabTit z_fontWeight">状态码参照(statusCode)：</p>
                                        <table class="am-table">
                                            <thead>
                                            <tr style="background:#eee;border-color:#eee!important;">
                                                <th>错误码</th>
                                                <th>说明</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td>1000</td>
                                                <td>提交成功</td>
                                            </tr>
                                            <tr>
                                                <td>1001</td>
                                                <td>缺少必要参数</td>
                                            </tr>
                                            <tr>
                                                <td>1002</td>
                                                <td>手机号码不合法</td>
                                            </tr>
                                            <tr>
                                                <td>1003</td>
                                                <td>请求地址不合法</td>
                                            </tr>
                                            <tr>
                                                <td>1004</td>
                                                <td>用户不存在</td>
                                            </tr>
                                            <tr>
                                                <td>1005</td>
                                                <td>sign验证不通过</td>
                                            </tr>
                                            <tr>
                                                <td>1006</td>
                                                <td>帐户已经关闭</td>
                                            </tr>
                                            <tr>
                                                <td>1010</td>
                                                <td>帐户余额不足</td>
                                            </tr>
                                            <tr>
                                                <td>1011</td>
                                                <td>扣费失败</td>
                                            </tr>
                                            <tr>
                                                <td>1012</td>
                                                <td>非法ip</td>
                                            </tr>
                                            <tr>
                                                <td>1099</td>
                                                <td>系统异常</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <p class="z_tabTit" style="font-weight:900;">JSON返回示例：</p>
                                        <div class="z_example">
                                            {
                                            <br/>&nbsp;&nbsp;&nbsp;"statusCode": "1000",
                                            <br/>&nbsp;&nbsp;&nbsp;"statusMsg": "提交成功",
                                            <br/>&nbsp;&nbsp;&nbsp;"iccid": "5bd75125b494496eb6fd547b98496sls7",
                                            <br/>&nbsp;&nbsp;&nbsp;"msisdn": "o2016121416480303073133"，
                                            <br/>&nbsp;&nbsp;&nbsp;"cardStatus": "正常"
                                            <br/>
                                            }
                                        </div>
                                    </div>

                                    <!--查询激活时间-->
                                    <div id="change2">
                                        <ul>
                                            <li>接口地址：http://api.yunpaas.cn/iotQuery/dateActivated</li>
                                            <li>支持格式：json</li>
                                            <li>请求方式：http post/get</li>
                                            <li>请求示例：http://api.yunpaas.cn/iotQuery/dateActivated?
                                                token=5bd75125b494496eb6fd547b98496dbe
                                                &msisdn=106476565&&iccid=898607B6191792616064
                                                &sign=e8f277b2b04a2be7a5757da7da18e0c9</li>
                                            <li>接口备注：查询订单卡的激活时间</li>
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
                                            <tr>
                                                <td>iccid</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>IC卡的唯一识别码</td>
                                            </tr>
                                            <tr>
                                                <td>msisdn</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>MSISDN号码</td>
                                            </tr>
                                            <tr>
                                                <td>sign</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>校验值
                                                </td>
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
                                                <td>dateActivated</td>
                                                <td>string</td>
                                                <td>卡激活时间,格式yyyy-MM-dd HH:mm:ss</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <p class="z_tabTit" style="font-weight:900;">JSON返回示例：</p>
                                        <div class="z_example">
                                            {
                                            <br/>&nbsp;&nbsp;&nbsp;"dateActivated": "2018-02-03 14:22:22",
                                            <br/>}
                                        </div>
                                    </div>

                                    <!--查询流量接口 -->
                                    <div id="change3">
                                        <ul>
                                            <li>接口地址：http://api.yunpaas.cn/iotQuery/usages</li>
                                            <li>支持格式：json</li>
                                            <li>请求方式：http post/get</li>
                                            <li>请求示例：http://api.yunpaas.cn/iotQuery/usages?
                                                token=5bd75125b494496eb6fd547b98496dbe
                                                &msisdn=106476565&&iccid=898607B6191792616064
                                                &sign=e8f277b2b04a2be7a5757da7da18e0c9</li>
                                            <li>接口备注：查询卡的流量</li>
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
                                            <tr>
                                                <td>iccid</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>IC卡的唯一识别码</td>
                                            </tr>
                                            <tr>
                                                <td>msisdn</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>MSISDN号码</td>
                                            </tr>
                                            <tr>
                                                <td>sign</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>校验值
                                                </td>
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
                                                <td>usedFlow</td>
                                                <td>string</td>
                                                <td>已用流量(M)</td>
                                            </tr>
                                            <tr>
                                                <td>restFlow</td>
                                                <td>string</td>
                                                <td>剩余流量(M)</td>
                                            </tr>
                                            <tr>
                                                <td>totalFlow</td>
                                                <td>string</td>
                                                <td>总流量(M)</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <p class="z_tabTit" style="font-weight:900;">JSON返回示例：</p>
                                        <div class="z_example">
                                            {
                                            <br/>&nbsp;&nbsp;&nbsp;"usedFlow": "2.00",
                                            <br/>&nbsp;&nbsp;&nbsp;"restFlow": "8.00",
                                            <br/>&nbsp;&nbsp;&nbsp;"totalFlow": "10",
                                            <br/>}
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <%--描述签名算法--%>
                            <div class="am-tab-panel am-fade" id="tab2">
                                <span>
                                    <p style="text-indent:2em;">
                                       校验值，请求参数按参数名升序排序, 将除sign外的请求参数名及参数值相互连接，例如：iccid89860616010049046952msisdn861064619018767token1234abc，并将token添加到拼接串的头尾，
                                                	再对该字符串进行SHA1运算，得到16进制小写字符串
                                    </p>

                                </span>
                            </div>

                            <!--档位编码-->
                            <div class="am-tab-panel am-fade" id="tab3">
                                <p class="z_tabTit z_fontWeight">流量包编码：</p>
                                <input type="hidden" name="userId" id="userId" value="${users.id}">
                                <input type="hidden" name="businessType" id="businessType" value="1">
                                <table class="am-table">
                                    <tr style="background:#eee;border-color:#eee!important;">
                                        <th>流量包</th>
                                        <th>编码</th>
                                        <th>原价（元）</th>
                                        <th>运营商</th>
                                    </tr>
                                    <tr v-for="item in json">
                                        <td>{{item.packageSize}}</td>
                                        <td>{{item.code}}</td>
                                        <td>{{item.amount}}</td>
                                        <td>{{item.operator==3?"电信":item.operator==2?"联通":item.operator==1?"移动":" "}}</td>
                                    </tr>
                                </table>

                                <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

                            </div>
                            <!--示例代码-->
                            <div class="am-tab-panel am-fade" id="tab4">
                                <!--错误码格式说明-->
                                <p class="z_tabTit z_fontWeight">完整教学代码示例：</p>
                                <table class="am-table">
                                    <thead>
                                    <tr style="background:#eee;border-color:#eee!important;">
                                        <th>语言</th>
                                        <th>标题</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>JAVA</td>
                                        <td>
                                            <a class="z_fBlue" href="<%=request.getContextPath()%>/developer/cardJavaCode.do">物联网实例代码</a>
                                        </td>
                                    </tr>

                                    </tbody>
                                </table>
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
        el:"#tab3",
        data:{
            json:[]
        }
    });
    function flownum(){
        var userId=$("#userId").val();
        var businessType=$("#businessType").val();
        var appType=1;
        $.ajax({
            url: "/developer/flowdocuments",
            type:'POST',
            data:{
                userId:userId,
                appType:appType,
                businessType:businessType
            },
            dataType:"json",
            error:function(){
                $(this).addClass("done");
            },
            success: function(data){
                if(data.length>0){
                    $(".sj").hide();
                    vm.json = data;
                }else{
                    $(".sj").show();
                    vm.json = data;
                }
            }
        });
    };

</script>
</body>
</html>

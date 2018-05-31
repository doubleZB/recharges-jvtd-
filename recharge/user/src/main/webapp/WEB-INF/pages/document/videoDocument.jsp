<%--
  Created by IntelliJ IDEA.
  User: 视频会员接口文档
  Date: 2018/2/26
  Time: 11:02
  To change this template use File | Settings | File Templates.
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
                            <h1>视频会员充值</h1>
                            <span>支持HTTPS</span>
                        </div>
                        <p class="am-u-lg-12" style="margin:10px 0 15px 0;font-size:12px;color:#666;padding:0;">优酷爱奇艺腾讯视频会员快速充值</p>
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
                            <%--<li><a href="#tab2">错误码参照</a></li>--%>
                            <li onclick="videoNum()"><a href="#tab3">产品编码</a></li>
                            <li><a href="#tab4">示例代码</a></li>
                        </ul>

                        <div class="am-tabs-bd">
                            <div class="am-tab-panel am-fade am-in am-active" id="tab1">
                                <div class="am-u-lg-3 am-u-md-4" style="padding:0;">
                                    <ul class="z_leftList">
                                        <li class="active"><a href="javascript:;">1.视频会员直充接口</a></li>
                                        <li><a href="javascript:;">2.订单状态查询</a></li>
                                        <li><a href="javascript:;">3.状态回调接口</a></li>
                                    </ul>
                                </div>

                                <div class="am-u-lg-9 am-u-md-8 rightHeight">
                                    <!--流量充值 -->
                                    <div id="change1">
                                        <ul>
                                            <li>接口地址：http://api.yunpaas.cn/gateway/video/charge</li>
                                            <li>请求方式：http post/get</li>
                                            <li>请求示例：http://api.yunpaas.cn/gateway/video/charge?
                                                token=5bd75125b494496eb6fd547b98496dbe
                                                &mobile=13811118888&customId=5bd75125b494496eb6fd547b98496sls7
                                                &code=300001&operator=4
                                                &callbackUrl=您的回调地址
                                                &sign=e8f277b2b04a2be7a5757da7da18e0c9</li>
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
                                            <tr>
                                                <td>mobile</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>手机号</td>
                                            </tr>
                                            <tr>
                                                <td>customId</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>商户订单号</td>
                                            </tr>
                                            <tr>
                                                <td>code</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>产品编码</td>
                                            </tr>
                                            <tr>
                                                <td>operator</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>运营商4优酷 5爱奇艺 6腾讯 7搜狐 8乐视</td>
                                            </tr>
                                            <tr>
                                                <td>callbackUrl</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>回调地址</td>
                                            </tr>
                                            <tr>
                                                <td>sign</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>校验值，md5(token+mobile+customId+code+callbackUrl),
                                                    <br/>结果转为小写
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
                                                <td>statusCode</td>
                                                <td>string</td>
                                                <td>状态码</td>
                                            </tr>
                                            <tr>
                                                <td>statusMsg</td>
                                                <td>string</td>
                                                <td>状态说明</td>
                                            </tr>
                                            <tr>
                                                <td>customId</td>
                                                <td>string</td>
                                                <td>商户订单号</td>
                                            </tr>
                                            <tr>
                                                <td>orderNum</td>
                                                <td>string</td>
                                                <td>云通信平台订单号</td>
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
                                            <%--<tr>--%>
                                                <%--<td>1002</td>--%>
                                                <%--<td>手机号码不合法</td>--%>
                                            <%--</tr>--%>
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
                                                <td>1007</td>
                                                <td>档位编码不存在</td>
                                            </tr>
                                            <tr>
                                                <td>1008</td>
                                                <td>无可充货架</td>
                                            </tr>
                                            <tr>
                                                <td>1009</td>
                                                <td>无可充通道</td>
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
                                                <td>1014</td>
                                                <td>订单重复</td>
                                            </tr>
                                            <%--<tr>--%>
                                                <%--<td>1013</td>--%>
                                                <%--<td>手机号每天最多只能充值10次</td>--%>
                                            <%--</tr>--%>
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
                                            <br/>&nbsp;&nbsp;&nbsp;"customId": "5bd75125b494496eb6fd547b98496sls7",
                                            <br/>&nbsp;&nbsp;&nbsp;"orderNum": "o2016121416480303073133"
                                            <br/>
                                            }
                                        </div>
                                    </div>

                                    <!--订单状态查询-->
                                    <div id="change2">
                                        <ul>
                                            <li>接口地址：http://api.yunpaas.cn/gateway/video/queryOrder</li>
                                            <li>支持格式：json</li>
                                            <li>请求方式：http post/get</li>
                                            <li>请求示例：http://api.yunpaas.cn/gateway/video/queryOrder?
                                                customId=121416480303073133&token=0303073133982347</li>
                                            <li>接口备注：查询订单的最新状态，请确认订单成功提交后，再进行查询</li>
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
                                                <td>orderNum</td>
                                                <td>string</td>
                                                <td>否</td>
                                                <td>云通信平台订单号</td>
                                            </tr>
                                            <tr>
                                                <td>customId</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>商户订单号</td>
                                            </tr>
                                            <tr>
                                                <td>token</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>token值,开发者信息中获取</td>
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
                                                <td>状态: 1查询成功 2查询无数据 3token不正确 4缺少必要参数 5订单号不合法 1099系统异常</td>
                                            </tr>
                                            <tr>
                                                <td>statusCode</td>
                                                <td>string</td>
                                                <td>状态: 6充值中 7充值成功 8充值失败</td>
                                            </tr>
                                            <tr>
                                                <td>statusMsg</td>
                                                <td>string</td>
                                                <td>状态说明</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <p class="z_tabTit" style="font-weight:900;">JSON返回示例：</p>
                                        <div class="z_example">
                                            {
                                            <br/>&nbsp;&nbsp;&nbsp;"status": "1",
                                            <br/>&nbsp;&nbsp;&nbsp; "statusCode":"7",
                                            <br/>&nbsp;&nbsp;&nbsp; "statusMsg","充值成功"
                                            <br/>}
                                        </div>
                                    </div>

                                    <!--充值回调接口 -->
                                    <div id="change3">
                                        <ul>
                                            <li>推送地址：充值接口callbackUrl中的地址</li>
                                            <li>请求方式：http post/get</li>
                                            <li style="line-height:30px;">请求示例：http://abc.com?mobile=13811118888&status=7&customId=fb1ed32a9540c24b03cc0c06aabbb642&sign=fb1ed32a9540c24b03cc0c06aabbb642</li>
                                            <li style="line-height:30px;">接口备注：正式使用后建议在开发者信息中绑定IP白名单。<br/>
                                                <span class="z_red">
                                                    ***如果充值过程中，遇到http网络状态异常或错误码返回系统异常1099，请务必通过订单查询接口检测订单或联系客服，不要直接做失败处理，避免造成不必要的损失！！！</span></li>
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
                                                <td>mobile</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>手机号码</td>
                                            </tr>
                                            <tr>
                                                <td>status</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>充值状态 7充值成功 8充值失败</td>
                                            </tr>
                                            <tr>
                                                <td>customId</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>商户订单号</td>
                                            </tr>
                                            <tr>
                                                <td>orderNum</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>平台订单号</td>
                                            </tr>
                                            <tr>
                                                <td>sign</td>
                                                <td>string</td>
                                                <td>是</td>
                                                <td>校验值，md5(token+mobile+status+customId)，token在开发者信息查询</td>
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
                                                <td></td>
                                                <td></td>
                                                <td>处理成功请返回：SUCCESS</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <!--档位编码-->
                            <div class="am-tab-panel am-fade" id="tab3">
                                <p class="z_tabTit z_fontWeight">产品编码：</p>
                                <input type="hidden" name="userId" id="userId" value="${users.id}">
                                <input type="hidden" name="businessType" id="businessType" value="3">
                                <table class="am-table">
                                    <tr style="background:#eee;border-color:#eee!important;">
                                        <th>产品</th>
                                        <th>编码</th>
                                        <th>原价（元）</th>
                                        <th>运营商</th>
                                    </tr>
                                    <tr v-for="item in json">
                                        <td>{{item.packageSize}}</td>
                                        <td>{{item.code}}</td>
                                        <td>{{item.amount}}</td>
                                        <td>{{item.operator==8?"乐视":item.operator==7?"搜狐":item.operator==6?"腾讯":item.operator==5?"爱奇艺":item.operator==4?"优酷":" "}}</td>
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
                                            <a class="z_fBlue" href="<%=request.getContextPath()%>/developer/videoJavaCode.do">视频会员示例代码</a>
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
    function videoNum(){
        var userId=$("#userId").val();
        var businessType=$("#businessType").val();
        var appType=6;
        $.ajax({
            url: "/developer/videoDocuments",
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

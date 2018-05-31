<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/12/9
  Time: 20:49
  To change this template use File | Settings | File Templates.
  开发者信息
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-cn">
<%
    HttpSession s = request.getSession();
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/static/css/common.css">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">

    <style>
        body {
            background: none;
            font-size: 14px;
        }

        ul {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding-left: 0!important;
            padding-right: 0!important;
            overflow: hidden;
            margin-bottom: 0!important;
        }

        ul li {
            height: 40px;
            line-height: 40px;
        }

        .z_tit {
            height: 40px;
            line-height: 40px;
            text-indent: 15px;
            border-bottom: 1px solid #ddd;
        }

        .z_fBlue {
            color: deepskyblue;
        }

        ul li input,ul li select {
            text-indent: 10px;
            width: 100%;
            height: 38px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-top:6px;
        }

        .z_remarks {
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-top: 15px;
            background: #f7f9fa;
            color: deepskyblue;
            padding-top: 10px;
            padding-bottom: 10px;
        }

        .z_remarks p {
            text-indent: 15px;
            margin: 0;
            height: 30px;
            line-height: 30px;
        }

        .z_btn {
            width: 100px;
            height: 30px;
            text-align: center;
            border-radius: 5px;
            border: 0;
            background: deepskyblue;
            color: #fff;
            box-shadow: none;
            margin-top:10px;
        }

    </style>
</head>
<body>

<input type="hidden" value="<%=s.getAttribute("userId")%>" id="userId"/>
<div class="container" style="padding:23px;">
    <div class="row">
        <div class="am-u-lg-12" style="padding:0;">
            <div class="am-u-lg-12 m-box m-market" style="padding:0;">
                <div class="box-title">
                    开发者账号
                </div>
                <div class="am-u-lg-12" style="padding:15px;">
                    <ul class="am-u-lg-12 z_account">
                        <div class="z_tit">开发者账号</div>
                        <li class="am-u-lg-12">
                            <div class="am-u-lg-3 am-u-md-4">token：</div>
                            <div class="am-u-lg-9 am-u-md-8 z_fBlue" id="tokenId"></div>
                        </li>
                        <li class="am-u-lg-12">
                            <div class="am-u-lg-3 am-u-md-4">请求方法：</div>
                            <div class="am-u-lg-9 am-u-md-8 z_fBlue">Http Post</div>
                        </li>
                    </ul>
                    <ul class="am-u-lg-12 z_callback">
                        <li class="am-u-lg-12" style="height: 50px;line-height: 50px;margin-top: 15px;">
                            <div class="am-u-lg-3 am-u-md-4">IP鉴权地址：</div>
                            <div class="am-u-lg-6 am-u-md-5 z_fBlue">
                                <input type="text"  id="ipAddressId">
                            </div>
                            <div class="am-u-lg-3 am-u-md-3"></div>
                        </li>
                        <li class="am-u-lg-12" style="height: 50px;line-height: 50px;">
                            <div class="am-u-lg-3 am-u-md-4">回调推送次数：</div>
                            <div class="am-form-group am-u-lg-6 am-u-md-5">
                                <select id="pushSum">
                                    <option value="1">1次</option>
                                    <option value="2">2次</option>
                                    <option value="3">3次</option>
                                </select>
                                <span class="am-form-caret"></span>
                            </div>
                            <div class="am-u-lg-3 am-u-md-3"></div>
                        </li>
                        <li class="am-u-lg-12" style="height: 50px;line-height: 50px;">
                                <button class="z_btn am-block am-center" onclick="updateIp()"  style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;">提交</button>
                        </li>
                    </ul>

                    <div class="am-u-lg-12 z_remarks">
                        <div>备注:</div>
                        <p>1.接口采用http进行交互，支持POST方式提交。</p>
                        <p>2.返回报文统一使用JSON，编码为UTF-8,报文中某个可选参数的值为空，则该标签可以不出现。</p>
                        <p>3.IP鉴权地址为空表示不鉴权,多个ip地址以英文逗号分割。</p>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>


<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/layer/layer.js"></script>
<script>

    $(function(){
        getToken()//执行函数
    });
    function getToken(){
        var userId=$("#userId").val();
        $.ajax({
            url: "${pageContext.request.contextPath}/developer/getusertokenandsid",
            async:false,
            type:'POST',
            dataType:"json",
            data:{"userid":userId},
            error:function(){
                layer.msg("加载失败请重新登录");
            },
            success: function(data){
                $("#tokenId").empty();
                $("#tokenId").append(data.token);
                $("#pushSum").val(data.pushSum)
                if(data.ipAddress!=""){
                    $("#ipAddressId").empty();
                    $("#ipAddressId").val(data.ipAddress);

                }

            }
        });
    }

    function updateIp(){
        var userId=$("#userId").val();
        var ipAddress=$("#ipAddressId").val();
        var pushSum=$("#pushSum").val();
        $.ajax({
            url: "${pageContext.request.contextPath}/developer/updateuseripaddress",
            async:false,
            type:'POST',
            dataType:"json",
            data:{
                "userid":userId,
                "ipaddress":ipAddress,
                pushSum:pushSum
            },
            error:function(){
                layer.msg("很遗憾修改失败，从新修改吧");
            },
            success: function(data){
               if(data==1){
                   layer.msg("添加成功");
               }else{
                   layer.msg("添加失败");
               }
            }
        });
    }

</script>

</body>

</html>

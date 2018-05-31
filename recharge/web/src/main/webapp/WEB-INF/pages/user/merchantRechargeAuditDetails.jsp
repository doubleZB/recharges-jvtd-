<%--
  Created by IntelliJ IDEA.
  User: lhm
  Date: 2017/4/18
  Time: 14:14
  To change this template use File | Settings | File Templates.
  商户充值审核
--%>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Properties" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = "";
    String FileName = "";

    Properties pro = new Properties();
    String realpath = request.getRealPath("/WEB-INF/classes");
    try{
        //读取配置文件
        FileInputStream in = new FileInputStream(realpath+"/config.properties");
        pro.load(in);
    }
    catch(FileNotFoundException e){
        out.println(e);
    }
    catch(IOException e){out.println(e);}

//通过key获取配置文件
    path = pro.getProperty("certificate_image");
    FileName = pro.getProperty("FileName");
%>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${path}/static/css/authentication.css">
</head>
<style>
    #tab1 {
        margin-left: 100px;
        padding-bottom: 50px;
    }

    .td_first {
        text-align: right;
    }

    tr,
    td {
        font-size: 14px;
    }

    tr {
        line-height: 40px;
    }

    .btn {
        float: left;
    }

    .return {
        float: left;
        margin-top: 20px;
        line-height: 32px;
        margin-left: 20px;
    }
</style>

<body>
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">商户充值审核</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <table v-for="item in json">
                <tr>
                    <td class="td_first">商户账户：</td>
                    <td>{{item.userName}}</td>
                </tr>
                <tr>
                    <td class="td_first">银行户名：</td>
                    <td>{{item.backAccountName}}</td>
                </tr>
                <tr>
                    <td class="td_first">入账银行：</td>
                    <td>{{item.bankName}}</td>
                </tr>
                <tr>
                    <td class="td_first">转账金额：</td>
                    <td>{{item.amount}}</td>
                </tr>
                <tr>
                    <td class="td_first">转账时间：</td>
                    <td>{{item.payTime|time}}</td>
                </tr>
                <tr class="img2 ">
                    <td class="td_first">银行回单：</td>
                    <td><img id="Img" src="//<%=path%>/<%=FileName%>/{{item.bankCertificate}}" style="width:600px;height:500px;"></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="btn" v-show="btn">
                            <button onclick="checkPass(1);" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-top: 20px">审核通过
                            </button>
                            <button onclick="checkNo(2)" class="am-btn am-btn-warning" id="truebtn" style="width: 120px;margin:auto;margin-top: 20px">审核不通过
                            </button>
                        </div>
                        <a href="<%=request.getContextPath()%>/user/merchantAudit.do" class="return">返回</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <!-- content end -->
    <div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt" style="opacity: 1;">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">审核不通过原因</div>
            <div class="am-modal-bd">
                <input id="cause"  type="text" class="am-modal-prompt-input">
            </div>
            <div class="am-modal-footer" >
                <span onclick="submitText()" class="am-modal-btn" data-am-modal-confirm>确定</span>
                <span onclick="closeText()" class="am-modal-btn" data-am-modal-cancel>取消</span>
            </div>
        </div>
    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/assets/js/amazeui.min.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/js/vue-resource.js"></script>
<script>

    Vue.filter('time',
            <!-- value 格式为13位unix时间戳 -->
            <!-- 10位unix时间戳可通过value*1000转换为13位格式 -->
            function (value) {
                if(value!=''&&value!=null){
                    var date = new Date(value);
                    Y = date.getFullYear(),
                            m = date.getMonth() + 1,
                            d = date.getDate(),
                            H = date.getHours(),
                            i = date.getMinutes(),
                            s = date.getSeconds();
                    if (m < 10) {
                        m = '0' + m;
                    }
                    if (d < 10) {
                        d = '0' + d;
                    }
                    if (H < 10) {
                        H = '0' + H;
                    }
                    if (i < 10) {
                        i = '0' + i;
                    }
                    if (s < 10) {
                        s = '0' + s;
                    }
                    <!-- 获取时间格式 2017-01-03 10:13:48 -->
                    var t = Y + '-' + m + '-' + d + ' ' + H + ':' + i + ':' + s;
                    <!-- 获取时间格式 2017-01-03 -->
                    //var t = Y + '-' + m + '-' + d;
                    return t;
                }else{
                    return '';
                }
            });
    var vm = new Vue({
        el: "#tab1",
        data: {
            btn:false,
            json: [],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        },
        ready: function () {
            var _this = this;
            var userPayId= ${userPayId};
            $.ajax({
                url: "${pageContext.request.contextPath}/userMerchant/merchantRechargeAuditDetailsData",
                type: 'post',
                dataType: "json",
                data:{
                    'id':userPayId
                },
                error: function () {
                    alert("数据加载错误");
                },
                success: function (data) {
//                    alert(typeof (data[0].auditState));
                    vm.json = data;
                    if(data[0].auditState==3){
                        _this.btn = true;
                    }else{
                        _this.btn = false;
                    }
                }
            });
        },
        methods: {}
    });

    //审核通过
    function checkPass(auditState){
        var userPayId= ${userPayId};
       var checkCause="审核成功";
        $.ajax({
            url: "${pageContext.request.contextPath}/userMerchant/checkPassBank",
            type: 'post',
            dataType: "json",
            data:{
               id: userPayId,
                checkCause:checkCause,
                auditState:auditState
            },
            error: function () {
                alert("审核失败");
            },
            success: function (data) {
                if(data>0){
                    alert("审核成功");
                    window.location.href="<%=request.getContextPath()%>/user/merchantAudit.do";
                }else{
                    alert("审核失败");
                }
            }
        });
    }
    //审核未通过
    function checkNo(){
        $("#my-prompt").show();
    }
    //确定按钮
    function submitText(){
        var userPayId= ${userPayId};
        var checkCause=$("#cause").val();
         var userauditState=2;
        if(checkCause==""){
            alert("请输入审核不通过原因");
        }else{
            $.ajax({
                url: "${pageContext.request.contextPath}/userMerchant/checkPassBank",
                type: 'post',
                dataType: "json",
                data:{
                    id: userPayId,
                    checkCause:checkCause,
                    auditState:userauditState
                },
                error: function () {
                    alert("审核失败");
                },
                success: function (data) {
                    if(data){
                        alert("审核成功");
                        window.location.href="<%=request.getContextPath()%>/user/merchantAudit.do";
                        $("#my-prompt").hide();
                    }
                }
            });
        }
    }
    function closeText(){
        $("#my-prompt").hide();
    }
</script>
</body>
</html>

<%--
  Date: 2017/4/13
  Time: 14:32
  To change this template use File | Settings | File Templates.
 账户充值
--%>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.IOException" %>
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
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <link href="${pageContext.request.contextPath}/static/css/cardTransfer.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/static/js/laydate.js"></script>
</head>
<style>

    .form-groups input[type="text"],
    .form-groups select{
        width: 200px;
        height: 35px;
        border-radius: 5px;
        border: 1px solid #ccc;
        text-indent: 12px;
    }

</style>
<body>

<div class="container" style="padding:23px;">
    <div class="row">
        <div class="col-lg-12">
            <div class="m-box m-market">
                <!--标题-->
                <div class="box-title">账户充值</div>
                <div class="accountInfo">
                   <c:forEach items="${balanceList}" var="balance">
                       <span>账户余额：<i class="z_orange">${balance.userBalance}</i>元</span>
                       <span>授信：<i>${balance.creditBalance}</i>元</span>
                       <span>借款：<i>${balance.borrowBalance}</i>元</span>
                   </c:forEach>
                </div>
                <div class="box-content">
                    <ul class="z_cz_tab">
                        <!--<li>在线充值</li>-->
                        <!--<li>支付宝转账</li>-->
                        <li class="active" value="1">银行卡转账</li>
                    </ul>
                    <!--银行卡转账内容-->
                    <div class="col-lg-12 col-md-12 col-xs-12 z_cz_tabContent">
                        <div class="col-lg-12 col-md-12 col-xs-12 info">
                            <div class="form-groups">
                                <label>户名：</label>
                                <span>北京聚通达科技股份有限公司</span>
                            </div>
                            <div class="form-groups">
                                <label>开户行：</label>
                                <span>招商银行北京西三环支行</span>
                            </div>
                            <div class="form-groups">
                                <label>银行账号：</label>
                                <span>1109 2389 9410 302</span>
                            </div>
                        </div>
                        <!--提示-->
                        <p class="z_orange remind">为了您的充值金额快速入账，转账后请填写如下表单（务必与银行电子回单一致），财务审核通过即可入账。</p>
                        <form id="userPayOrder" enctype="multipart/form-data" method="post">
                            <div class="form-groups">
                                <label>银行户名：</label>
                                <input type="text" placeholder="付款人名称" id="backAccountName" name="backAccountName">
                                <span>该名称必须与实名认证的企业名称或法人名字保持一致，否则无法加款</span>
                            </div>
                            <div class="form-groups">
                                <label>入账银行：</label>
                                <select name="bankName" id="bankName">
                                    <option value="1">上海浦东发展银行股份有限公司北京西直门支行</option>
                                    <option value="2">上海浦东发展银行股份有限公司北京西直门支行</option>
                                </select>
                            </div>
                            <div class="form-groups">
                                <label>转账金额：</label>
                                <input type="text" placeholder="请输入转账金额" id="amount" name="amount">
                                <span>元</span>
                            </div>
                            <div class="form-groups">
                                <label>转账时间：</label>
                                <input type="text" placeholder="请选择转账时间" name="payTime" id="payTime">
                            </div>
                            <div class="form-groups">
                                <label>上传银行回单截图：</label>
                                <img id="identityCardFront" name="identityCardFront" style="display: none">
                                <input type="file" name="file" id="customerFile"
                                       accept="image/jpg,image/jpeg,image/png"
                                       onchange="uploadBankImg('customerFile')"/>
                                <p style="margin: 0;">
                                    <img src="" id="Img" alt="">
                                </p>
                            </div>
                            <div class="form-groups">
                                <label></label>
                                <input type="button" value="提交" class="btn" id="submitPayOrder" style="color: #fff!important;">
                            </div>
                        </form>
                        <div class="example form-groups">
                            <label style="vertical-align: top">银行回单示例：</label>
                            <span><img src="${path}/static/images/sl.png" alt=""></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript" src="${path}/static/js/upload.js"></script>
<script>
    /* 用于日期特效的 */
    laydate.skin('molv');
    laydate({
        elem: '#payTime',
        event: 'focus',
        format: 'YYYY/MM/DD hh:mm:ss',
        istime: true,
        istoday: true
    });
    var bankCertificate;
    function uploadBankImg(inp_id) {
        $("#" + inp_id).upload({
            url: '${path}/customer/customerUpload.do',
            params: {},
            dataType: 'json',
            onSend: function (obj, str) {
                return true;
            },
            onComplate: function (data) {
                bankCertificate=data.reurl;
                licenseImage = "//<%=path%>/<%=FileName%>/"+ data.reurl;
                $("#Img").attr("src",licenseImage);
            }
        });
        $("#" + inp_id).upload("ajaxSubmit");
    }
    //提交用户支付信息
    $("#submitPayOrder").click(function(){
        var payType=$(".z_cz_tab li").val();
        var backAccountName=$("#backAccountName").val();
        var bankName=$("#bankName").find("option:selected").text();
        var amount=$("#amount").val();
        var payTime=$("#payTime").val();
        var Img=$("#customerFile").val();
        var amountReg=/^([1-9]\d{1,6}|[1-9]\d{1,6}\.\d{1,3})$/;
        if(backAccountName==""){
            alert("请填写付款人名称");
        }else if(bankName==""){
            alert("请选择入账银行");
        }else if(amount==""){
            alert("请输入转账金额");
        }else if(!amountReg.test(amount)){
            alert("请输入小数位数不超过三位的有效位数为10为的金额");
        }else if(payTime=="") {
            alert("请选择转账时间");
        }else if(Img=="") {
            alert("请选择银行回单截图");
        }else{
            $.ajax({
                url:"${path}/finance/addUserPayOrder.do",
                type: "POST",
                data:{
                    payType:payType,
                    backAccountName:backAccountName,
                    bankName:bankName,
                    amount:amount,
                    payTime:payTime,
                    bankCertificate:bankCertificate
                },
                dataType:"json",
                success:function(data) {
                    if(data>0){
                        alert("添加成功");
                        $("#backAccountName").val(null);
                        $("#amount").val(null);
                        $("#payTime").val(null);
                        $("#customerFile").val(null);
                    }else{
                        alert("添加失败");
                        location.reload();
                    }
                }
            });
        }
    });
</script>
</body>
</html>


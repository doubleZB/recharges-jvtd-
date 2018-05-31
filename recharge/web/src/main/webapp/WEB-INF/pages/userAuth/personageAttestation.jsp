<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.FileNotFoundException" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/11
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>个人认证管理</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
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

    .img1,
    .img2 {
        display: none;
    }
</style>

<body>
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">商户认证审核</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <table>
                <c:forEach items="${list}" var="g">
                    <tr style="display: none">
                        <td id="authID">${g.id}</td>
                    </tr>
                    <tr>
                        <td class="td_first">商户类型：</td>
                        <td>${g.userType==1?"企业":g.userType==2?"个人":" "}</td>
                    </tr>
                    <tr>
                        <td class="td_first">个人姓名：</td>
                        <td>${g.name}</td>
                    </tr>
                    <tr>
                        <td class="td_first">身份证号码：</td>
                        <td>${g.identityCardNum}</td>
                    </tr>
                    <tr style="display: none">
                        <td class="td_first">审核状态：</td>
                        <td id="authState">${g.authState}</td>
                    </tr>
                    <tr>
                        <td class="td_first">身份证正面：</td>
                        <td><a href="####" class="btn1">查看</a></td>
                    </tr>
                    <tr class="img1">
                        <td class="td_first"></td>
                        <td><img src="//<%=path%>/<%=FileName%>/${g.identityCardFront}"></td>
                    </tr>
                    <tr>
                        <td class="td_first">身份证背面：</td>
                        <td><a href="####" class="btn2">查看</a></td>
                    </tr>
                    <tr class="img2">
                        <td class="td_first"></td>
                        <td><img src="//<%=path%>/<%=FileName%>/${g.identityCardBack}"></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td></td>
                    <td style="color:#A1A1A1;">审核要求：手持身份证照片信息全面、清晰，与本人基本相符；</br>姓名、身份证号码与上述填入项保持一致。</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="btn">
                            <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-top: 20px"v onclick="pass()">审核通过
                            </button>
                            <button type="button" class="am-btn am-btn-warning" id="truebtn" style="width: 120px;margin:auto;margin-top: 20px">审核不通过
                            </button>
                        </div>
                        <a href="${pageContext.request.contextPath }/user/authList.do" class="return">返回</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <!-- content end -->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">审核不通过原因
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <input type="text" name="cause" id="cause" class="am-modal-prompt-input">
                <hr>
                <div style="width: 135px;margin: auto;">
                    <button type="button" class="am-btn am-btn-warning" onclick="noPass()">提交</button>
                    <button type="button" class="am-btn am-btn-warning" onclick="abolish()">取消</button>
                </div>

            </div>
        </div>
    </div>
</div>
<script>
    $(function() {
        var authState = $("#authState").text();
        if(authState==3||authState==4){
            $(".btn").hide();
        }

        $('.btn1').on('click', function() {
            $('.img1').toggle();
        })
        $('.btn2').on('click', function() {
            $('.img2').toggle();
        })
        //弹框
        $('#truebtn').on('click', function() {
            $("#your-modal").modal("open")
        });
    })

    //取消
    function abolish(){
        $("#cause").val(null);
        location.href="${pageContext.request.contextPath }/user/authList.do";
    }
    function noPass(){
        //审核不通过
        var cause = $("#cause").val();
        if(cause.trim()==''){
            alert("请填写审核不通过原因！");
            return;
        }else{
            //审核不通过
            $.ajax({
                url:"${pageContext.request.contextPath }/user/AuditNotThrough.do",
                type:"post",
                data:{
                    id:authID,
                    cause:cause
                },
                dataType:"json",
                success:function(obj){
                    if(obj>0){
                        alert("恭喜你修改成功！");
                        location.href="${pageContext.request.contextPath }/user/authList.do";
                    }else{
                        alert("抱歉，修改失败！");
                    }
                }
            });
        }
    }
    //审核通过
    var authID = $("#authID").text();
    function pass(){
        $.ajax({
            url:"${pageContext.request.contextPath }/user/approveOK.do",
            type:"post",
            data:{
                id:authID
            },
            dataType:"json",
            success:function(obj){
                if(obj>0){
                    alert("恭喜你审核通过！");
                    location.href="${pageContext.request.contextPath }/user/authList.do";
                }else{
                    alert("审核出错！");
                }
            }
        });
    }

</script>
</body>

</html>

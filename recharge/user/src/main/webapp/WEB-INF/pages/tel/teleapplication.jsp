<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/6
  Time: 10:16
  客户基本资料.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <![endif]-->
    <style>
        .table tr{
            border:0;
        }
        .table>tbody>tr>th {
            border:0;
            text-align: center;
        }
        .table>tbody>tr>td {
            text-align: center;
            text-indent:0!important;
        }
        .z_source td{
            font-size:48px;
        }
        .z_source th{
            font-size:18px;
        }
    </style>
</head>

<body>

<div class="container" style="padding:23px;">
    <!--    第一部分   -->

    <!--    第二部分    -->
    <div class="row">
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-6 col-md-6 padding-left">
                    <div class="z_info clearfix">
                        <h3 class="z_info_tit">客户基本资料</h3>
                        <div class="col-lg-12" style="margin-top:10px;">
                            <div class="col-lg-3 col-md-3 col-xs-3" style="text-align:center;">
                                <a href="javascript:" class="z_photo" onclick="">
                                    <img src="${path}/static/images/dog.png" alt="">
                                </a>
                            </div>
                            <div class="col-lg-7 col-md-7 col-xs-7">
                                <p class="z_name">${admin_name}</p>
                                <div style="clear:both;"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 padding-right">
                    <div class="z_info clearfix">
                        <h3 class="z_info_tit">API接口信息</h3>
                        <div class="col-lg-12" style="margin-top:10px;line-height:85px;text-indent:28px;">
                            <button class="z_wan btn-primary btn z_H_btn1">API文档</button>
                            <button class="z_wan btn-primary btn z_H_btn1"><a href="#" style="color:#fff;">联系客服</a></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--    三部分    -->
    <div class="row" style="margin:20px 0;">
        <div class="col-lg-12">
            <div class="row z_info">
                <h3 class="z_info_tit">昨日发送概览</h3>
                <div class="row">
                    <div class="col-lg-12 col-margin-bottom-box col-margin-top-box">
                        <div style="margin-top:10px;">
                            <table class="table table-striped table-hover texttable z_source" width="100%" border=0>
                                <tr>
                                    <th>交易额</th>
                                    <th>订单数</th>
                                </tr>
                                <c:forEach items="${order}" var="s">
                                    <tr>
                                        <td>${s.numb}</td>
                                        <td>${s.num}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--    统计报表    -->

    <div class="row z_tjbb" style="margin-top:20px;font-size:14px;">
        <div class="col-lg-12">
            <div class="col-border clearfix col-margin-bottom">
                <div class="z_info_tit">
                    充值报表
                    <div class="z_dateTabs">
                        <button class="z_runChart active" data-type="">月</button>
                        <button class="z_runChart" data-type="">周</button>
                        <button class="z_runChart" data-type="">日</button>
                    </div>
                </div>
                <div class="col-lg-12 col-md-12 col-xs-12">
                    <div class="m-box">
                        <div class="box-content m-chart" style="min-height:356px;">
                            <table class="table table-striped table-hover texttable" width="100%">
                                <tr>
                                    <th>充值时间</th>
                                    <th>成功数</th>
                                    <th>交易金额</th>
                                </tr>
                                <c:forEach items="${list}" var="f">
                                    <tr>
                                        <td>${f.orderTime}</td>
                                        <td>${f.status}</td>
                                        <td>${f.amount}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        console.log($(".presentation"))
        $(".dropdown-menu li").each(function() {
            console.log(2)
            $(".dropdown-menu li").click(function() {
                var text = $(this).text();
                $("#dropdownMenu1").text(text);
            });
        });

    });

</script>
</body>
</html>

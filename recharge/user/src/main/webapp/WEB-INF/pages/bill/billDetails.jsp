<%--
  Date: 2017/4/13
  Time: 14:32
  To change this template use File | Settings | File Templates.
 账单明细
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/z-dingdanjilu.css">
    <style>
        .am-table td {
            border-top: 1px solid #ddd!important;
        }
        .z_payNum{
            width:200px;
        }
        .z_desc{
            width:250px;
        }
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

    <div class="container" style="padding:23px;">

        <div class="row">
            <div class="col-lg-12">
                <div class="col-lg-12 m-box m-market" style="padding:0;">
                    <div class="box-title">账单明细</div>
                    <div class="col-lg-12 col-md-12 col-xs-12" style="margin-top:25px;">
                        <!--  上半部分   -->
                        <div class="am-form">
                            <fieldset>
                                <!--   手机号输入   -->
                                <div class="am-form-group col-lg-12 col-md-12 col-xs-12">
                                    <input type="text" id="flowNum" placeholder="支付流水号">
                                    <input type="text" readonly id="startTime" placeholder="开始时间" style="background: none;">
                                    <input type="text" readonly id="endTime" placeholder="结束时间" style="background: none;">
                                </div>
                                <!--下拉-->
                                <div class="am-form-group col-lg-12 col-md-12 col-xs-12">
                                    <select id="inOrOut" >
                                        <option value="0">出入账</option>
                                        <option value="1">出账</option>
                                        <option value="2">入账</option>
                                    </select>
                                    <select id="costType">
                                        <option value="0">类别</option>
                                        <option value="1">充值</option>
                                        <option value="2">退款</option>
                                        <option value="3">消费</option>
                                        <option value="4">借款</option>
                                        <option value="5">转账</option>
                                        <option value="6">减款</option>
                                    </select>
                                    <button type="button" class="am-btn am-btn-default am-radius" onclick="reloadPage(1);" style="width:120px;background: #F37B1D;border-color:#F37B1D;">查询</button>
                                    <a href="javascript:void(0);" onclick="exportBalance()" style="color: #1c68f2;text-decoration: underline;">下载当前数据</a>
                                </div>

                            </fieldset>
                        </div>
                        <!--    表格   -->
                        <table class="am-table table-striped table-hover texttable" style="font-size: 14px;">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>支付流水号</th>
                                    <th>出入账</th>
                                    <th>类别</th>
                                    <th>描述</th>
                                    <th>金额(元)</th>
                                    <th>余额(元)</th>
                                    <th>时间</th>
                                </tr>
                            </thead>
                            <tbody id="dataTable">
                            </tbody>
                        </table>
                        <div class="am-cf">
                            共 <span id="totalNum">0</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span id="totalPages">0</span> 页
                            <div class="am-fr">
                                <ul class="am-pagination" style="margin: 0">
                                    <li><a  href="javascript:void(0);" onclick="prvPage();">上一页</a></li>
                                    <li class="am-disabled"><a href="#"><span id="countPage"> 0 </span>/<span id="allPage"> 0 </span></a></li>
                                    <li><a  href="javascript:void(0);" onclick="nextPage();">下一页</a></li>
                                    <li>
                                        <input type="text" id="inputPageNum" style="width: 26px;height: 26px;margin-bottom: 5px;border:1px solid #ccc;vertical-align:middle;" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                    </li>
                                        <li class="am-active"><a onclick="gotoPage();" style="padding: .5rem .4rem;">GO</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <input type="hidden" id="courrentPageNum" value="1">
        <input type="hidden" id="isLastPage" value="1">
    </div>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/laydate.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/amazeui.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <!--[if (gte IE 9)|!(IE)]><!-->
    <!--<![endif]-->
    <!--[if lte IE 8 ]>
        <script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
        <script src="assets/js/amazeui.ie8polyfill.min.js"></script>
        <![endif]-->
    <script>
        /* 用于日期特效的 */
        laydate.skin('molv');
        var start = {
            elem: '#startTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true,
            istoday: false,
            choose: function(datas){
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };
        var end = {
            elem: '#endTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true,
            istoday: false,
            choose: function(datas){
                start.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);

        function reloadPage(pageNum){
            var subData ={
                "flowNum":$("#flowNum").val(),
                "startTime":$("#startTime").val(),
                "endTime":$("#endTime").val(),
                "inOrOut":$("#inOrOut").val(),
                "costType":$("#costType").val()
            };
            var index = layer.load(0, {shade: [0.3,'#000']});
            $.ajax({
                url: "/bill/getBillDetails?pageSize=10&pageNum="+pageNum,
                type:'POST',
                async:false,
                data:subData,
                dataType:"json",
                error:function(){
                    layer.close(index);
                },
                success: function(data){
                    layer.close(index);
                    var html="";
                    if(data.list!=null){
                        $.each(data.list, function(index, item) {
                            html+="<tr>";
                            html+="<td>"+(index+1)+"</td>";
                            html+="<td class='z_payNum'>"+item.sequence+"</td>";
                            if(item.billType==1){
                                html+="<td>出账</td>";
                            }else {
                                html+="<td>入账</td>";
                            }

                            if(item.category==1){
                                html+="<td>充值</td>";
                            }else if(item.category==2){
                                html+="<td>退款</td>";
                            }else if(item.category==3){
                                html+="<td>消费</td>";
                            }else if(item.category==4){
                                html+="<td>借款</td>";
                            }else if(item.category==5){
                                html+="<td>转账</td>";
                            }else if(item.category==6){
                                html+="<td>减款</td>";
                            }
                            html+="<td class='z_desc'>"+item.description+"</td>";
                            html+="<td>"+item.amount+"</td>";
                            html+="<td>"+item.balance+"</td>";
                            html+="<td>"+item.dateStr+"</td>";
                            html+="</tr>";
                        });
                        $("#courrentPageNum").val(data.pageNum);
                        $("#isLastPage").val(data.pages);
                        $("#totalPages").text(data.pages);
                        $("#allPage").text(data.pages);
                        $("#countPage").text(data.pageNum);
                        $("#totalNum").text(data.total);
                    }else{
                        html+="<tr><td colspan='7'>查无结果！</td></tr>";
                    }
                    $("#dataTable").html("");
                    $("#dataTable").append(html);
                }
            });
        }
    function prvPage(){
        var courrentPageNum = $("#courrentPageNum").val();
        if(courrentPageNum==1){
            layer.msg("没有上一页");
            return;
        }
        reloadPage(courrentPageNum-1);
    }
    function nextPage(){
        var courrentPageNum = $("#courrentPageNum").val();
        var isLastPage = $("#isLastPage").val();
        if(parseInt(courrentPageNum)>=parseInt(isLastPage)){
            layer.msg("没有下一页");
            return;
        }
        var nextpage = parseInt(courrentPageNum)+1;
        reloadPage(nextpage);
    }
    function gotoPage(){
        var inputPageNum = $("#inputPageNum").val();
        if(inputPageNum==''){
            layer.tips("请输入页数！","#inputPageNum",{tips: 3});
            return;
        }
        var isLastPage = $("#isLastPage").val();
        if(parseInt(isLastPage)==0||inputPageNum>parseInt(isLastPage)){
            layer.msg("没有该页");
            return;
        }
        reloadPage(inputPageNum);
    }


    function exportBalance(){
       var  flowNum=$("#flowNum").val();
       var  startTime=$("#startTime").val();
       var endTime=$("#endTime").val();
       var inOrOut=$("#inOrOut").val();
       var costType=$("#costType").val();
        window.open("<%=request.getContextPath()%>/balanceExport/exportbalance?startTime="+startTime+"&endTime="+endTime+"&flowNum="+flowNum
                +"&inOrOut="+inOrOut+"&costType="+costType);
    }
    </script>

</body>

</html>

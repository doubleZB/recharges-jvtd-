<%--
  Created by IntelliJ IDEA.
  User: 商户账户余额 lihuimin
  Date: 2016/11/12
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>

</head>
<style>
    *{
        -moz-user-select: text !important;
        -webkit-user-select: text !important;
        -ms-user-select: text !important;
        user-select: text !important;
    }
    #content{
        margin-top: 20px;
    }
    .am-btn{
        border-radius: 5px;
    }
    input,select{
        color:#848181;
    }
    .am-table>caption+thead>tr:first-child>td, .am-table>caption+thead>tr:first-child>th, .am- table>colgroup+thead>tr:first-child>td, .am-table>colgroup+thead>tr:first-child>th, .am- table>thead:first-child>tr:first-child>td, .am-table>thead:first-child>tr:first-child>th {
        border-top: 0;
        font-size: 1.4rem;
    }
    .am-table>tbody>tr>td, .am-table>tbody>tr>th, .am-table>tfoot>tr>td, .am-table>tfoot>tr>th, .am- table>thead>tr>td, .am-table>thead>tr>th {
        padding: .7rem;
        line-height: 1.6;
        vertical-align: top;
        border-top: 1px solid #ddd;
        font-size: 1.4rem;
    }
    .fu div {
        float: left;
        line-height: 40px;
    }
.rela{
    position:relative;
    top: -23px;
    left: 38px;
}
    .rela .fa{
        position:absolute;
        right:25px;
        cursor: pointer;
    }
    .rela .fa-caret-up{
        top:8px;
    }
    .rela .fa-caret-down{
        top:16px;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" style="margin-top:0;">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <div class="am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1" style="padding-top: 0;">
                    <div class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input type="text" name="userCnName" id="userCnName3"  value="${balance.userCnName}" class="am-form-field" placeholder="商户简称" style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <input type="text" name="userName" id="userName3" class="am-form-field"  value="${balance.userName}" placeholder="账户名" style="width: 200px;float: left;">
                        </div>
                        <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;" onclick="reloadPage(1)">查询</button>
                    </div>
                    <form id="listPageform" action="<%=request.getContextPath()%>/user/balance.do?type1=1" method="post">
                        <input type="hidden" name="userCnName" id="userCnName">
                        <input type="hidden" name="userName" id="userName">
                        <input type="hidden" name="pageNumber" id="pageNumber2">
                        <input type="hidden" name="pageSize" id="pageSize2">
                        <input type="hidden" name="orderBalance" id="orderBalance" value="${balance.orderBalance}">
                        <input type="hidden" name="orderBorrow" id="orderBorrow" value="${balance.orderBorrow}">
                        <input type="hidden" name="orderCredit" id="orderCredit" value="${balance.orderCredit}">
                    </form>
                    <hr>
                    <div class="fu">
                        <div>账户余额 :
                            <c:if test="${not empty list.list}">
                                <c:forEach items="${listTwo}" var="a">
                                    <span>${a.userBalance}</span>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty list.list}">
                                <span>0.000</span>
                            </c:if>
                            (元)
                        </div>
                    </div>
                    <table class="am-table am-table-striped">
                        <tr>
                            <th>序号</th>
                            <th>商户简称</th>
                            <th>账户名</th>
                            <th>授信额度（元) <div  class="rela am-inline-block"><span class="fa fa-caret-up" onclick="orderPage(1,0,0)" ></span><span class="fa fa-caret-down" onclick="orderPage(2,0,0)"></span></div></th>
                            <th >借款（元）<div  class="rela am-inline-block"><span class="fa fa-caret-up" onclick="orderPage(0,1,0)"></span><span class="fa fa-caret-down" onclick="orderPage(0,2,0)"></span></div></th>
                            <th>账户余额（元）<div  class="rela am-inline-block"><span class="fa fa-caret-up" onclick="orderPage(0,0,1)"></span><span class="fa fa-caret-down" onclick="orderPage(0,0,2)"></span></div></th>
                            <th>操作</th>
                        </tr>
                        <c:if test="${not empty list.list}">
                            <c:forEach items="${list.list}" var="b">
                                <tr>
                                    <td class="userid">${b.id}</td>
                                    <td class="cn_name">${b.userCnName}</td>
                                    <td class="u_name">${b.userName}</td>
                                    <td>${b.creditBalance}</td>
                                    <td>${b.borrowBalance}</td>
                                    <td>${b.userBalance}</td>
                                    <td>
                                        <a href="###" class="jiakuan  am-btn am-btn-link" style="padding: 0;" onclick="Addblance(this)">加款</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                    <c:if test="${empty list.list}">
                        <span class="sj" style="color:red;margin-left: 550px;font-size:20px;">暂无数据</span>
                    </c:if>
                    <hr>
                    <div class="am-cf">
                        共 <span>${list.total}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>${list.pages}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="javascript:void(0);" onclick="reloadPage('${list.pageNum-1}');">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> ${list.pageNum} </span>/<span> ${list.pages} </span></a></li>
                                <li><a href="javascript:void(0);"  onclick="reloadPage('${list.pageNum+1}');">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();"  style="padding: .5rem .4rem;">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->

    <!--//编辑<-->
  <div class="tab_wu">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">商户加款</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <form class="am-form am-form-horizontal" style="margin-top: 20px;" id="audit">
            <div class="am-form-group">
                <label class="am-u-sm-2 am-form-label">账户名：</label>
                <div style="width: 300px;float: left;">
                    <input type="hidden" name="adminId" id="proposerId" value="${adminLoginUser.id}">
                    <input type="hidden" name="operate" id="operate" value="${adminLoginUser.adminName}">
                    <input type="hidden" name="id" id="userId">
                    <input type="hidden" name="userName" id="userNames">
                    <input type="text" name="userName" id="user_name" disabled="disabled">
                </div>
            </div>
            <div class="am-form-group">
                <label class="am-u-sm-2 am-form-label">商户简称：</label>
                <div style="width: 300px;float: left;">
                    <input type="text" name="userCnName" id="user_cn_name" disabled="disabled">
                </div>
            </div>
            <div class="am-form-group">
                <label class="am-u-sm-2 am-form-label">加款类型：</label>
                <div style="width: 300px;float: left;" class="addlx">
                    <p style="display:inline-block;margin:9px 30px 6px 0;">
                        <input type="radio" checked="checked" name="addType" id="addTypeOne" onclick="gathering()" style="margin-right: 5px;" value="1">实收款
                    </p>
                    <p style="display:inline-block;margin:9px 30px 6px 0;">
                        <input type="radio" name="addType" id="addTypeTwo"  onclick="giveBalance()"  style="margin-right: 5px;" value="2">借款
                    </p>
                    <p style="display:inline-block;margin:9px 30px 6px 0;">
                        <input type="radio" name="addType" id="addTypeThree" onclick="offsettingDeductions()"  style="margin-right: 5px;" value="3">还款
                    </p>
                </div>
            </div>
            <div class="am-form-group" id="gatherings">
                <label class="am-u-sm-2 am-form-label">到款账户：</label>
                <select style="width: 300px;float: left;" name="receiveUser" id="receiveUser">
                    <option value="2" selected="selected">招商银行**** **** **** 0302</option>
                    <option value="1">浦发银行北京西直门支行</option>
                    <option value="3">支付宝bjwwl779@126.com</option>
                </select>
            </div>
            <div class="am-form-group" id="cheques">
                <label class="am-u-sm-2 am-form-label">收款金额：</label>
                <div style="width: 300px;float: left;">
                    <input type="text" name="amount" onblur="Amount()"  id="amount" >
                    <span id="msg_amount"></span>
                </div>
            </div>
            <div class="am-form-group" style="display: none;" id="loan">
                <label class="am-u-sm-2 am-form-label">借款金额：</label>
                <div style="width: 300px;float: left;">
                    <input type="text" name="borrowMoney" id="borrowMoney" onblur="Loan()" placeholder="借款额度不能为小数" >
                    <span id="msg_amountloan"></span>
                </div>
            </div>
            <div class="am-form-group" style="display: none;" id="deductions">
                <label class="am-u-sm-2 am-form-label">还款金额：</label>
                <div style="width: 300px;float: left;">
                    <input type="text" name="deductionsMoney" id="deductionsMoney" onblur="subtract()" placeholder="还款额度不能大于余额" >
                    <span id="msg_deductions"></span>
                    <p>账户当前余额: <span id="usableMoney"></span> 元</p>
                    <p>账户当前借款: <span id="borrowMoneyOne"></span> 元</p>
                </div>
            </div>

            <div class="am-form-group">
                <label class="am-u-sm-2 am-form-label">备注：</label>
                <div style="width: 300px;float: left;">
                    <input type="text" class="checkName" name="remark" placeholder="非必填（不超过30字）" maxlength="30">
                </div>
            </div>
        </form>
      <div class="am-form-group">
          <button class="am-btn am-btn-warning" style="width: 120px;margin: auto;margin-left: 176px;"  onclick="editBalance()">提交</button>
      </div>
  </div>

</div>

</body>
<script>

    $("#msg_amountloan").css("color","red");
    $("#msg_amount").css("color","red");
    $("#msg_deductions").css("color","red");
    // 验证借款金额
    function Loan(){
        var val = $("#borrowMoney").val();
        var amount = /^\d+(\.\d{1,2})?$/;
        if(amount.test(val.trim())){
            $("#msg_amountloan").text("");
        }else{
            $("#msg_amountloan").text("请正确填写，借款额度不能是负数,允许两位小数点！");
        };
    };
    // 验证收款金额
    function Amount(){
        var gathering = $("#amount").val();
        var reNum=/^\d+(\.\d{1,2})?$/;
        if(reNum.test(gathering.trim())) {
            $("#msg_amount").text("");
        } else {
            $("#msg_amount").text("请正确填写,收款金额且不能为负数,允许两位小数点！");
        }
    };
    //验证还款金额
    function subtract(){
        var deductionsMoney = $("#deductionsMoney").val();
        var amount = /^\d+(\.\d{1,2})?$/;
        if(amount.test(deductionsMoney.trim())){
            if(deductionsMoney.trim()<=usableMoney){
                $("#msg_deductions").text("");
            }else{
                $("#msg_deductions").text("请正确填写,还款额度不能大于余额");
            }
        }else{
            $("#msg_deductions").text("请正确填写，还款金额不能是负数,允许两位小数点！");
        };
    }

    //收款
    function gathering(){
        $("#loan").hide();
        $("#deductions").hide();
        $("#cheques").show();
        $("#gatherings").show();
    }
    //借款
    function giveBalance(){
        $("#gatherings").hide();
        $("#receiveUser").val("");
        $("#cheques").hide();
        $("#deductions").hide();
        $("#loan").show();
    }
    //还款
    var usableMoney;
    var borrowMoneyOne;
    function offsettingDeductions(){
        $("#gatherings").hide();
        $("#receiveUser").val("");
        $("#loan").hide();
        $("#cheques").hide();
        $("#deductions").show();
        var userID =  $("#userId").val();
        //根据用户ID去查询账户余额
        $.ajax({
            url:"${pageContext.request.contextPath }/user/selectUserMasterAccount.do",
            type:"post",
            data:{userId:userID},
            dataType:"json",
            success:function(data){
                usableMoney = data[0].userBalance;
                $("#usableMoney").text(usableMoney)
               $("#borrowMoneyOne").text(data[0].borrowBalance);

            }
        });
    }


    function  orderPage(orderCredit,orderBorrow,orderBalance){
        $("#orderCredit").val(orderCredit);
        $("#orderBorrow").val(orderBorrow);
        $("#orderBalance").val(orderBalance);
        reloadPage(1);
    }

    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        $("#userCnName").val($("#userCnName3").val());
        $("#userName").val($("#userName3").val());
        $("#pageNumber2").val(pageNum);
        $("#pageSize2").val('${list.pageSize}');
        $("#listPageform").submit();
    }


    //到几页
    function gotoPage(){
        var pagenum = $("#gotoPage").val();
        if(pagenum==''){
            layer.tips("请输入页数！","#gotoPage",{tips: 3});
            return;
        }
        reloadPage(pagenum);
    }
    //商户信息加款回显
    function Addblance(a){
        $(".cn_name").each(function(index){
            $(".jiakuan").eq(index).click(function(){
                $("#user_name").val($(a).parent().siblings(".u_name").text());
                $("#userNames").val($(a).parent().siblings(".u_name").text());
                $("#user_cn_name").val($(a).parent().siblings(".cn_name").text());
                $("#userId").val($(a).parent().siblings(".userid").text());
            })
        })
    }
    //商户加款提交
    function editBalance(){
        var receiveVoucher=$("#receiveVoucher").val();
        var val = $("#amount").val();
        var borrowMoney = $("#borrowMoney").val();
        var deductionsMoney = $("#deductionsMoney").val();
        var amount = /^\d+(\.\d{1,2})?$/;
        if(deductionsMoney!="" && deductionsMoney>usableMoney){
            alert("还款金额不能大于余额！");
            return
        }
        if((amount.test(borrowMoney.trim()))|| amount.test(val.trim()) || amount.test(deductionsMoney.trim())){
            var loadingIndex = layer.open({type:3});
            //先去根据用户ID去查看待审核信息
            var userId = $("#userId").val();
            $.ajax({
                url:"${pageContext.request.contextPath }/user/selectUserBalanceRecordByUserId.do?",
                type:"post",
                data:{userId:userId},
                dataType:"json",
                async:false,
                success:function (obj){
                    layer.close(loadingIndex);
                    if(obj==1){
                        alert("您有未审核的记录，请先去审核！");
                        location.href="${pageContext.request.contextPath }/user/addfunds.do"
                    }else{
                        //添加到审核表中数据
                        $.ajax({
                            url:"${pageContext.request.contextPath }/user/toBalance.do?",
                            type:"post",
                            data:$("#audit").serialize(),
                            dataType:"json",
                            async:false,
                            success:function (obj){
                                layer.close(loadingIndex);
                                if(obj){
                                    alert("恭喜你添加成功");
                                    location.reload();
                                }else{
                                    alert("抱歉添加失败");
                                    location.reload();
                                }
                            }
                        });
                    }
                }
            });
        }else{
            alert("请正确填写加款、借款、还款金额！");
        }
    }

</script>
<script>
   $(function() {
        $("#file_upload").change(function() {
            var $file = $(this);
            var fileObj = $file[0];
            var windowURL = window.URL || window.webkitURL;
            var dataURL;
            var $img = $("#preview");

            if (fileObj && fileObj.files && fileObj.files[0]) {
                dataURL = windowURL.createObjectURL(fileObj.files[0]);
                $img.attr('src', dataURL);
            } else {
                dataURL = $file.val();
                var imgObj = document.getElementById("preview");
                // 两个坑:
                // 1、在设置filter属性时，元素必须已经存在在DOM树中，动态创建的Node，也需要在设置属性前加入到DOM中，先设置属性在加入，无效；
                // 2、src属性需要像下面的方式添加，上面的两种方式添加，无效；
                imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;

            }
        });

        $(".jiakuan").click(function() {
            $('.admin-content-body').hide();
            $('.tab_wu').show();
        })
    });

</script>
</html>

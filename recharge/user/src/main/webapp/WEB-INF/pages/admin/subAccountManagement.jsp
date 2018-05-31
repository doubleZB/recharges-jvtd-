<%--
  Created by IntelliJ IDEA.
  User: lhm子账户管理
  Date: 2017/3/8
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
    <style>
        .z_states {
            color: #ED801B;
        }
        *{
            font-size:14px;
        }
        .handle span {
            display: inline-block;
            color: #0e90d2;
            padding: 0 10px;
            cursor: pointer;
        }

        .createAccount {
            font-size: 14px;
            color: #0e90d2;
            float: right;
            cursor: pointer;
        }

        .am-form-group {
            margin-bottom: 10px;
        }

        #doc-modal-1 .am-form-group label,
        #doc-modal-2 .am-form-group label,
        #doc-modal-3 .am-form-group label {
            font-weight: 100;
            width: 30%;
            text-align: right;
            float: left;
            line-height: 30px;
        }

        #doc-modal-1 .am-form-group>input,
        #doc-modal-2 .am-form-group>input,
        #doc-modal-3 .am-form-group>input {
            width: 60%;
            float: left;
        }

        #doc-modal-1 .am-form-group button,
        #doc-modal-3 .am-form-group button {
            width: 40%;
            float: left;
        }

        #doc-modal-2 .am-form-group button {
            float: left;
            width: 20%;
            margin-right: 20px;
        }

        #doc-modal-2 .am-form-group span {
            width: 60%;
            display: inline-block;
            float: left;
            text-align: left;
            line-height: 30px;
        }

        #doc-modal-2 .am-form-group .radio_wrap {
            float: left;
            width: 60%;
        }

        #doc-modal-2 .am-form-group .am-radio {
            text-align: left;
        }

    </style>
</head>
<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="col-lg-12">
            <div class="col-lg-12 m-box m-market" style="padding:0;">
                <div class="box-title" style="margin-bottom:30px;">子账户管理
                    <span data-am-modal="{target: '#doc-modal-3', closeViaDimmer: 0, width: 400, height: 465}" class="createAccount">+创建子账户</span>
                </div>
                <div class="box-content">
                    <div class="col-lg-12 col-md-12 col-xs-12">
                        <!--  上半部分   -->
                        <div class="z_jl_tabContent">
                            <div class="am-form-group" style="border: 1px solid #ccc;background: #eee;padding: 10px 20px;color: #a8a8a8;">
                                <span>当前子账户数：<a id="count"></a>，最多可创建30个</span></br>
                                <span>子账户交易适用主账户开通应用及其规则</span>
                            </div>
                            <form class="am-form">
                                    <!--   手机号输入   -->
                                    <div class="am-form-group">
                                        <input type="text" id="userCnNameOne" name="userCnName"  placeholder="子账户名称">
                                        <select id="statusOne">
                                            <option value="">状态</option>
                                            <option value="1">开启</option>
                                            <option value="0">关闭</option>
                                        </select>
                                        <span class="am-form-caret"></span>
                                        <button type="button" class="am-btn am-btn-default am-radius" style="width: 120px;background: #F37B1D!important;border-color: #F37B1D!important;" onclick="reloadPage()">查询</button>
                                    </div>
                            </form>
                            <!--    表格   -->
                            <table class="am-table table-striped table-hover texttable">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th class="z_number">子账户ID</th>
                                    <th>子账户名称</th>
                                    <th>用户名</th>
                                    <th>绑定手机号</th>
                                    <th>联系人</th>
                                    <th>联系人手机</th>
                                    <th>创建时间</th>
                                    <th>状态</th>
                                    <th>余额（元）</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="tableOne">

                                </tbody>
                            </table>

                            <div class="am-cf">
                                <div class="sj" style="display: none; color: red;font-size: 2em;text-align: center;">没有找到数据</div>
                                共 <span id="total"></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span id="pageCount"></span> 页
                                <div class="am-fr">
                                    <ul class="am-pagination" style="margin: 0">
                                        <li><a href="javascript:void(0);" onclick="reloadPage(1)">上一页</a></li>
                                        <li class="am-disabled"><a href="#"><span id="page"></span>/<span id="sum"></span></a></li>
                                        <li><a href="javascript:void(0);" onclick="reloadPage(2)">下一页</a></li>
                                        <li>
                                            <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="gotoPage" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                        </li>
                                        <li class="am-active"><a href="javascript:void(0);" style="padding: .5rem .4rem;" onclick="reloadPage(3)">GO</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--创建子账户-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-3">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">创建子账户
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <form class="am-form" id="formAdd">
                    <fieldset>
                        <div class="am-form-group">
                            <label>*子账户名称：</label>
                            <input type="text" name="userCnName" id="userCnNameTwo" placeholder="2~20个汉字">
                        </div>
                        <div class="am-form-group">
                            <label>*用户名：</label>
                            <input type="text" id="userName" name="userName" placeholder="4~10位字母数字或其组合，字母开头">
                        </div>
                        <div class="am-form-group">
                            <label>*初始密码：</label>
                            <input type="text" id="password" name="password" placeholder="4~10位字母数字特殊字符，或其组合">
                        </div>
                        <div class="am-form-group">
                            <label>*绑定手机：</label>
                            <input type="text" id="mobile" name="mobile" placeholder="">
                        </div>
                        <div class="am-form-group">
                            <label>联系人：</label>
                            <input type="text" id="contacts" name="contacts" placeholder="">
                        </div>
                        <div class="am-form-group">
                            <label>联系人手机：</label>
                            <input type="text" id="contactsMobile" name="contactsMobile" placeholder="">
                        </div>
                        <div class="am-form-group">
                            <label>状态：</label>
                            <select id="statusTwo" name="status">
                                <option value="1">开启</option>
                                <option value="0">关闭</option>
                            </select>
                        </div>
                    </fieldset>
                </form>
                <div class="am-form-group">
                    <label></label>
                    <button type="button" onclick="accountCommit()" class="am-btn am-btn-default" style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;-webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;">提交</button>
                </div>
            </div>
        </div>
    </div>

    <!--编辑弹窗-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">编辑子账户
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <form class="am-form" id="updateForm">
                    <fieldset>
                        <div class="am-form-group">
                            <label>*子账户名称：</label>
                            <input type="text" name="userCnName" id="userCnNameThree" disabled="disabled" placeholder="2~20个汉字">
                        </div>
                        <div class="am-form-group">
                            <label>*用户名：</label>
                            <input type="text" id="userNameThree" name="userName" disabled="disabled" placeholder="4~10位字母数字或其组合，字母开头">
                        </div>
                        <div class="am-form-group">
                            <label>*初始密码：</label>
                            <input type="text" id="passwordThree" name="password" placeholder="4~10位字母数字特殊字符，或其组合">
                        </div>
                        <div class="am-form-group">
                            <label>*绑定手机：</label>
                            <input type="text" id="mobileThree" name="mobile" placeholder="">
                        </div>
                        <div class="am-form-group">
                            <label>联系人：</label>
                            <input type="text" id="contactsThree" name="contacts" placeholder="">
                        </div>
                        <div class="am-form-group">
                            <label>联系人手机：</label>
                            <input type="text" id="contactsMobileThree" name="contactsMobile" placeholder="">
                        </div>
                        <div class="am-form-group">
                            <label>状态：</label>
                            <select id="statusThree" name="status">
                                <option value="1">开启</option>
                                <option value="0">关闭</option>
                            </select>
                        </div>
                    </fieldset>
                </form>
                    <div class="am-form-group">
                        <label></label>
                        <button type="button" onclick="accountUpdate()" class="am-btn am-btn-default" style="background: rgba(28, 104, 242, 0.8)!important;border-color: rgba(28, 104, 242, 0.8)!important;color: #fff!important;-webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;"">提交</button>
                    </div>
            </div>
        </div>
    </div>

    <!--调拨款项弹窗-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-2">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">调拨款项
                <a href="javascript: void(0)" class="am-close am-close-spin close" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <form class="am-form" id="moneyForm">
                    <fieldset>
                        <div class="am-form-group">
                            <label>子账户：</label>
                            <span id="childName"></span>
                        </div>
                        <div class="am-form-group">
                            <label>调拨类型：</label>
                            <div class="radio_wrap">
                                <div class="am-radio" style="margin: 5px 0;">
                                    <input type="hidden" id="userIdFather">
                                    <input type="radio" name="radio" class="host" onclick="masterAccount()" checked="checked" style="width: auto;margin-right: 0;">从主账户划入子账户
                                </div>

                                <div class="am-radio" style="margin: 5px 0;">
                                    <input type="hidden" id="userId">
                                    <input type="radio" name="radio" class="child"  onclick="childAccount()" style="width: auto;margin-right: 0;">从子账户回归主账户
                                </div>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label>调拨金额：</label>
                            <input type="text" id="userBalance" name="userBalance" onchange="AllocateAmount()" placeholder="请填写调拨金额" value="">
                        </div>
                        <div class="">
                            <div style="text-align: left;margin-left: 30%;">可拨资金：<div id="usableMoney" style="display: inline-block"></div>元</div>
                        </div>
                        <div class="am-form-group">
                            <label></label>
                            <span style="color:red;" id="msgAmount"></span>
                        </div>
                    </fieldset>
                </form>
                <div class="am-form-group">
                    <label></label>
                    <button type="submit" class="am-btn am-btn-default" id="OK" onclick="AmountOK()" style="background: rgba(28, 104, 242, 0.8)!important;color: #fff!important;-webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;">确定</button>
                    <button type="button" class="am-btn am-btn-default cancel">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        reloadPage();
    });
    var totalNumber;
    function reloadPage(data){
        //几页
        var pageNumber=parseInt($("#page").text());
        //数量
        var sum=parseInt($("#sum").text())
        var pageNum;
        if(data==1){
            pageNum=pageNumber-1;
            if(pageNum==0){
                alert("当前为第一页");
                return ;
            }
        }else if(data==2){
            var pageNum=pageNumber+1;
            if(pageNum>sum){
                alert("当前为最后一页");
                return ;
            }
        }else if(data==3){
            pageNum=$("#gotoPage").val();
            if(pageNum==""){
                layer.msg("输入跳转页数");
                return;
            }
        }else if(data==undefined){
            pageNum=1;
        }


        var html="";
        var userCnNameOne=$("#userCnNameOne").val();
        var statusOne=$("#statusOne").val();
        var loadingIndex = layer.open({type:3});
        var length="";
        $.ajax({
            url: "${path}/user/selectAccountUserList.do",
            type:'POST',
            dataType:"json",
            async:false,
            data:{
                userCnName:userCnNameOne,
                status:statusOne,
                pageNumber:pageNum
            },
            error:function(){
                layer.close(loadingIndex);
                layer.msg("加载失败请重新登录");
            },
            success: function(data){
//                alert(data);
                layer.close(loadingIndex);
                var obj=data.list;
                length =obj.length;
              //  alert(length);
                $(obj).each(function (i) {
                    var index=i+1;
                    if(obj[i].status==1){
                        statusOne="开启";
                    }else if(obj[i].status==0){
                        statusOne="关闭";
                    }
                    html+=  ' <tr>'+
                            '<td >'+index+'</td>'+
                            '<td style="display: none">'+obj[i].id+'</td>'+
                            '<td>'+obj[i].pId+'</td>'+
                            '<td>'+obj[i].userCnName+'</td>'+
                            '<td>'+obj[i].userName+'</td>'+
                            '<td>'+obj[i].mobile+'</td>'+
                            '<td>'+obj[i].contacts+'</td>'+
                            '<td>'+obj[i].contactsMobile+'</td>'+
                            '<td>'+obj[i].registerTimeFormat+'</td>'+
                            '<td>'+statusOne+'</td>'+
                            '<td>'+obj[i].userBalance+'</td>'+
                            '<td class="handle"><span class="edit">编辑</span><span class="transferFunds">调拨款项</span></td>'+
                            '</tr>';
                });
                $("#tableOne").empty();
                $("#tableOne").append(html);
                $("#count").empty();
                $("#count").append(data.total);

                //编辑点击
                compile();
                //调拨款项点击
                appropriation();

                $("#total").empty();
                $("#total").append(data.total);
                $("#pageCount").empty();
                $("#pageCount").append(data.pages);
                $("#page").empty();
                $("#page").append(data.pageNum)
                $("#sum").empty();
                $("#sum").append(data.pages)
                if(data.list=="" ){
                    $("#tableOne").hide();
                    $(".sj").show();

                }else{
                    $("#tableOne").show();
                    $(".sj").hide();
                }
                totalNumber = data.total;
            }
        });
    }
</script>
<script type="text/javascript">
    var userId ;
    var userIdFather;
    //编辑点击
    function compile(){
        $(".edit").click(function() {
            $(this).attr("data-am-modal", "{target: '#doc-modal-1', closeViaDimmer: 0, width: 400, height: 465}");
            var trs = $(this).parent().siblings();
            //弹窗中的输入框
            var container = $("#doc-modal-1 input");
            //弹窗中的状态下拉
            var containerSelect = $("#doc-modal-1 select");
            var reg = /^1[34578]\d{9}$/;
            //获取表格数据添加到弹窗中

            userId = trs.eq(1).text();

            container.eq(0).val(trs.eq(3).text());
            container.eq(1).val(trs.eq(4).text());
            container.eq(3).val(trs.eq(5).text());
            container.eq(4).val(trs.eq(6).text());
            container.eq(5).val(trs.eq(7).text());
            if(trs.eq(9).text() == "开启"){
                containerSelect.val(1);
            }else{
                containerSelect.val(0);
            }
            $(".submit").click(function() {
                if (reg.test(container.eq(3).val()) && reg.test(container.eq(5).val())) {
                    //获取弹窗修改后数据添加到表格中
                    $(this).attr("data-am-modal-close", true);
                    trs.eq(3).text(container.eq(0).val());
                    trs.eq(4).text(container.eq(1).val());
                    trs.eq(5).text(container.eq(3).val());
                    trs.eq(6).text(container.eq(4).val());
                    trs.eq(7).text(container.eq(5).val());
                    trs.eq(9).text(containerSelect.eq(0).val());
                } else {
                    alert("请输入正确的手机号");
                }
            });
        });
    }

    //调拨款项点击
    function appropriation(){
        $(".transferFunds").click(function() {
            $(this).attr("data-am-modal", "{target: '#doc-modal-2', closeViaDimmer: 0, width: 400, height: 320}")
            var trs = $(this).parent().siblings();
            //弹窗中的输入框
            var container = $("#doc-modal-1 span");

            //子用户ID
            userId = trs.eq(1).text();
            //父用户ID
            userIdFather = trs.eq(2).text();

            $("#childName").text(trs.eq(3).text())

        });
    }

    $(function() {
        //取消点击
        $(".cancel").click(function() {
            $(this).attr("data-am-modal-close", true);
            $("#userBalance").val("");
            $(".host").prop("checked",true);
            $(".child").prop("checked",false);
            $("#msgAmount").text("");
            $("#OK").attr('disabled',false);
            masterAccount();
        });
        $(".close").click(function(){
            $("#userBalance").val("");
            $(".host").prop("checked",true);
            $(".child").prop("checked",false);
            $("#msgAmount").text("");
            $("#OK").attr('disabled',false);
            masterAccount();
        });
        $(".submit").click(function() {
            $(this).attr("data-am-modal-close", true);
        });

    });

    //添加子账户用户
    function accountCommit(){
        //账户简称
        var userCnNameTwo =$("#userCnNameTwo").val();
        var cnName = /^[\u4e00-\u9fa5]{2,20}$/;
        //用户名
        var userName = $("#userName").val();
        var name = /^[a-zA-Z][a-zA-Z0-9]{3,9}$/;
        //密码
        var Password=$("#password").val();

        //联系人
        var Contacts=$("#contacts").val();
        var contacts = /^[\u4e00-\u9fa5]{2,4}$/;

        //绑定手机号
        var MobileOne=$("#mobile").val();
        //联系人手机
        var ContactsMobile=$("#contactsMobile").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;

        //验证用户简称唯一
        if(cnName.test(userCnNameTwo.trim())){
            $.ajax({
                url:"${pageContext.request.contextPath }/user/checkOnlyName.do",
                type:"post",
                data:{
                    userCnName:userCnNameTwo
                },
                dataType:"json",
                success:function(data){
                    if(data.length>0){
                        alert("已有该子账户名称，请重新输入!");
                        return;
                    }else{
                        //验证用户名唯一
                        if(name.test(userName.trim())){
                            $.ajax({
                                url:"${pageContext.request.contextPath }/user/checkOnlyName.do",
                                type:"post",
                                data:{
                                    userName:userName
                                },
                                dataType:"json",
                                success:function(data){
                                    if(data.length>0){
                                        alert("已有该用户名，请重新输入!");
                                        return;
                                    }else{
                                        //判断密码
                                        if(Password.trim() == ""){
                                            alert("请输入密码!");
                                            return;
                                        } else if((Password.trim().length<4) || (Password.trim().length>10)){
                                            alert("密码为4~10位字母数字特殊字符，或其组合");
                                            return;
                                        }

                                        //判断手机号
                                        if(MobileOne.trim() == ""){
                                            alert("请输入手机号!");
                                            return;
                                        }else if(! (mobile.test(MobileOne.trim()) && MobileOne.trim().length==11)){
                                            alert("请正确填写您的手机号码!");
                                            return;
                                        }

                                        //判断联系人
                                        if(Contacts.trim()==""){
                                        }else if( !contacts.test(Contacts.trim())){
                                            alert("请正确填写联系人2~4汉字");
                                            return;
                                        }

                                        //判断联系人手机
                                        if(ContactsMobile.trim()==""){
                                        }else if(!(mobile.test(ContactsMobile.trim())&&ContactsMobile.trim().length==11)){
                                            alert("请正确填写联系人号码!");
                                            return;
                                        }
                                        if(parseInt(1)<=totalNumber || totalNumber<=parseInt(30)){
                                            $.ajax({
                                                url:"${pageContext.request.contextPath }/user/insertUserSon.do",
                                                type:"post",
                                                data:$("#formAdd").serialize(),
                                                dataType:"json",
                                                success:function(data){
                                                    if(data){
                                                        alert("恭喜你成功创建子账户！");
                                                        $("#formAdd input").val("");
                                                        location.reload();
                                                    }else{
                                                        alert("抱歉，创建子账户失败！");
                                                    }
                                                }
                                            });
                                        }else{
                                            alert("子账户已经达到上限,无法继续创建！");
                                        }

                                    }
                                }
                            });
                        }else if(userName.trim() == ""){
                            alert("请输入用户名!");
                            return;
                        }else{
                            alert("4~10位字母数字或其组合，字母开头!");
                            return;
                        }
                    }
                }
            });
        }else if(userCnNameTwo.trim() == ""){
            alert("请输入子账户名称!");
            return;
        }else{
            alert("请正确填写子账户名称2~20汉字!");
            return;
        };
    }

    //编辑子账户
    function accountUpdate(){
        //密码
        var Password=$("#passwordThree").val();

        //联系人
        var Contacts=$("#contactsThree").val();
        var contacts = /^[\u4e00-\u9fa5]{2,4}$/;

        //绑定手机号
        var MobileOne=$("#mobileThree").val();
        //联系人手机
        var ContactsMobile=$("#contactsMobileThree").val();
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;

        //判断密码
        if(Password.trim()==""){

        }else if((Password.trim().length<4) || (Password.trim().length>10)){
            alert("4~10位字母数字特殊字符，或其组合!");
            return;
        }
        //判断手机号
        if(MobileOne.trim() == ""){
            alert("请输入手机号!");
            return;
        }else if(! (mobile.test(MobileOne.trim()) && MobileOne.trim().length==11)){
            alert("请正确填写您的手机号码!");
            return;
        }
        //判断联系人
        if(Contacts.trim()==""){

        }else if( !contacts.test(Contacts.trim())){
            alert("请正确填写联系人2~4汉字");
            return;
        }
        //判断联系人手机
        if(ContactsMobile.trim()==""){

        }else if(!(mobile.test(ContactsMobile.trim())&&ContactsMobile.trim().length==11)){
            alert("请正确填写联系人号码!");
            return;
        }
//                        alert($("#updateForm").serialize());
        $.ajax({
            url:"${pageContext.request.contextPath }/user/updateUserSon.do?id="+userId,
            type:"post",
            data:$("#updateForm").serialize(),
            dataType:"json",
            success:function(data){
                if(data){
                    alert("恭喜你成功修改子账户！");
                    location.reload();
                }else{
                    alert("抱歉，修改子账户失败！");
                }
            }
        });
    }

    var usableMoney;
    var boss;
    masterAccount();
    //调拨款项从主账户划入子账户
    function masterAccount(){
        $.ajax({
            url:"${pageContext.request.contextPath }/user/selectUserMasterAccount.do",
            type:"post",
            data:{},
            dataType:"json",
            success:function(data){
                usableMoney = data[0].userBalance;
                $("#usableMoney").text(usableMoney)
                $("#userIdFather").val(userIdFather);
                boss=1;
            }
        });
    }

    //调拨款项从子账户回归主账户
    function childAccount(){
        $.ajax({
            url:"${pageContext.request.contextPath }/user/selectUserChildAccount.do",
            type:"post",
            data:{userId:userId},
            dataType:"json",
            success:function(data){
//                alert(data[0].userBalance)
                usableMoney = data[0].userBalance;
                $("#usableMoney").text(usableMoney);
                $("#userId").val(userId);
                boss=2;
            }
        });
    }

    //判断调拨金额
    function AllocateAmount(){
        var userBalance = $("#userBalance").val();
        var amount = /^\d+$/;
        if(amount.test(userBalance.trim()) && userBalance.trim()<=usableMoney){
            $("#msgAmount").text("");
            $("#OK").attr('disabled',false);
        }else{
            $("#msgAmount").text("拨款额度不可超过总资金并且不能为小数！");
            $("#OK").attr('disabled',true);
        };
    }

    //确定拨款
    function AmountOK(){
        var userBalance = $("#userBalance").val();
        var amount = /^\d+$/;
        if(userBalance.trim()==""){
            alert("请填写拨款金额!");
            return;
        }else if(!(amount.test(userBalance.trim()) && userBalance.trim()<=usableMoney)){
            alert("请正确填写，拨款额度不可超过总资金并且不能为小数！");
            return
        }else{
            //从主账户划入子账户
            if(boss==1){
                $.ajax({
                    url:"${pageContext.request.contextPath }/user/updateUserBalance.do",
                    type:"post",
                    data:{
                        userId:userIdFather,
                        pId:userId,
                        userBalance:userBalance
                    },
                    dataType:"json",
                    success:function(data){
                        if(data){
                            alert("恭喜你拨款成功！");
                            $("#userBalance").val("");
                            $(".host").prop("checked",true);
                            $(".child").prop("checked",false);
                            location.reload();
                        }else{
                            alert("抱歉，拨款失败！");
                        }
                    }
                });
            }else if (boss==2){
                //从子账户回归主账户
                $.ajax({
                    url:"${pageContext.request.contextPath }/user/updateUserBalanceSon.do",
                    type:"post",
                    data:{
                        userId:userIdFather,
                        pId:userId,
                        userBalance:userBalance
                    },
                    dataType:"json",
                    success:function(data){
                        if(data){
                            alert("恭喜你拨款成功！");
                            $("#userBalance").val("");
                            $(".host").prop("checked",true);
                            $(".child").prop("checked",false);
                            location.reload();
                        }else{
                            alert("抱歉，拨款失败！");
                        }
                    }
                });
            }

        }
    }
</script>
</body>
</html>

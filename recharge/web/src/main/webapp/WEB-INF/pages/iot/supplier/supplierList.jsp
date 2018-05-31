<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/20
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">

<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/yonghuliebiao.css">
</head>
<style>
    #content {
        margin-top: 20px;
    }

    .am-btn {
        border-radius: 5px;
    }

    input, select {
        color: #848181;
    }

    .am-table > caption + thead > tr:first-child > td, .am-table > caption + thead > tr:first-child > th, .am- table > colgroup + thead > tr:first-child > td, .am-table > colgroup + thead > tr:first-child > th, .am- table > thead:first-child > tr:first-child > td, .am-table > thead:first-child > tr:first-child > th {
        border-top: 0;
        font-size: 1.4rem;
    }

    .am-table > tbody > tr > td, .am-table > tbody > tr > th, .am-table > tfoot > tr > td, .am-table > tfoot > tr > th, .am- table > thead > tr > td, .am-table > thead > tr > th {
        padding: .7rem;
        line-height: 1.6;
        vertical-align: top;
        border-top: 1px solid #ddd;
        font-size: 1.4rem;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content">
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li id="table1" class="am-active"><a href="#tab1">供应商管理</a></li>
                <li id="table2"><a href="#tab2">新增</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input type="text" id="userNameToFind" class="am-form-field" placeholder="供应商名称"
                                   style="width: 150px;float: left;">
                        </div>
                        <button onclick="reloadPage(1);" class="am-btn am-btn-warning" style="width: 120px;background-color: #0e90d2; border-color: #0e90d2;">查询</button>
                    </div>
                    <hr>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>供应商名称</th>
                            <th>供应商等级</th>
                            <th>联系人</th>
                            <th>联系电话</th>
                            <th>拓展人员</th>
                            <th>地址</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>{{item.name}}</td>
                            <td>{{item.rankLiteral}}</td>
                            <td>{{item.contactName}}</td>
                            <td>{{item.contactMobile}}</td>
                            <td>{{item.businessName}}</td>
                            <td>{{item.address}}</td>
                            <td>
                                <a class="am-btn am-btn-link nava" style="padding: 0;"
                                   onclick="shows({{item.id}})">编辑</a>
                                <%--<a onclick="remove({{item.id}})" class="am-btn am-btn-link"--%>
                                <%--style="padding: 0;color: #F37B1D;">删除</a>--%>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <hr>
                    <div class="am-cf">
                        共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq-1}})">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq+1}})">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" id="gotoPage"
                                           style="width: 30px;height: 30px;margin-bottom: 5px;"
                                           onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();"
                                                         style="padding: .5rem .4rem;">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal" id="addSupplierForm">
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">供应商名称：</label>
                            <input id="name" name="name" type="text" class="am-form-field"
                                   style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">英文名称：</label>
                            <input id="enName" name="name" type="text" class="am-form-field"
                                   style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">供应商等级：</label>
                            <select style="width: 200px;float: left; height:35px" id="rank">
                                <option value="">选择供应商等级</option>
                                <c:forEach items="${rankList}" var="ope">
                                    <option value="${ope.value}">${ope}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">拓展人员：</label>
                            <input id="businessName" name="contactName" type="text" class="am-form-field"
                                   style="width: 200px;float: left;">
                        </div>

                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">联系人：</label>
                            <input id="contactName" name="contactName" type="text" class="am-form-field"
                                   style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">联系人电话：</label>
                            <input id="contactMobile" name="contactMobile" type="text" class="am-form-field"
                                   style="width: 200px;float: left;">
                            <%--<span  id="passstrength" ></span>--%>
                        </div>
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">地址：</label>
                            <textarea id="address" name="params" cols="30" rows="10"
                                      style="width: 200px;float: left;height: 80px;" maxlength="100"></textarea>
                        </div>

                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">接口参数：</label>
                            <textarea id="params" name="params" cols="30" rows="10"
                                      style="width: 200px;float: left;" maxlength="5000"></textarea>
                        </div>
                        <div class="am-form-group">
                            <button id="submitToAdd" class="am-btn am-btn-warning" style="width: 120px;background-color: #0e90d2; border-color: #0e90d2;">提交</button>
                            <%--<a href="#tab1">--%>
                            <button class="am-btn am-btn-default" style="width: 120px;" id="close">返回</button>
                            <%--</a>--%>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>

<!--   //编辑-->
<div class="tab_wu" style="display:none;">
    <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">修改供应商信息</strong>
        </div>
    </div>
    <hr style="margin: 10px 0px 0px 0px;">
    <form class="am-form am-form-horizontal" style="margin-top: 20px;">
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">供应商名称：</label>
            <input type="hidden" id="supplyId">
            <input id="oldName" type="hidden">
            <input id="oldEnName" type="hidden">
            <input id="nameEdit" name="name" type="text" class="am-form-field"
                   style="width: 200px;float: left;">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">英文名称：</label>
            <%--<input id="oldName" type="hidden">--%>
            <input id="enNameEdit" name="name" type="text" class="am-form-field"
                   style="width: 200px;float: left;" readonly>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">供应商等级：</label>
            <select style="width: 200px;float: left; height:35px" id="rankEdit">
                <option value="">选择供应商等级</option>
                <c:forEach items="${rankList}" var="ope">
                    <option value="${ope.value}">${ope}</option>
                </c:forEach>
            </select>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">拓展人员：</label>
            <input id="businessNameEdit" name="contactName" type="text" class="am-form-field"
                   style="width: 200px;float: left;">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">联系人：</label>
            <input id="contactNameEdit" name="contactName" type="text" class="am-form-field"
                   style="width: 200px;float: left;">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">联系人电话：</label>
            <input id="contactMobileEdit" name="contactMobile" type="text" class="am-form-field"
                   style="width: 200px;float: left;">
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">地址：</label>
            <textarea id="addressEdit" name="params" cols="30" rows="10"
                      style="width: 200px;float: left;height: 80px;" maxlength="100"></textarea>
        </div>
        <div class="am-form-group">
            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">接口参数：</label>
            <textarea id="paramsEdit" name="params" name=" " id="" cols="30" rows="10"
                      style="width: 200px;float: left;" maxlength="5000"></textarea>
        </div>
    </form>
    <div class="am-form-group">
        <button class="am-btn am-btn-warning" onclick="toEditAdminUser();" style="width: 120px;margin-left: 120px;background-color: #0e90d2; border-color: #0e90d2;">提交
        </button>
        <button class="am-btn am-btn-default" style="width: 120px;" onclick="hides()">返回</button>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script>

    var pageInfo = {
        ts: 0,
        dq: 0,
        all: 0
    };
    var vm = new Vue({
        el: "#content",
        data: {
            lx: "",
            hd: "",
            yys: "",
            sf: "",
            json: [],
            page: pageInfo,
            item: {}
        },
        components: {
            "my-component": {
                template: "#myTemplate",
                data: function () {
                    return {
                        show1: true
                    }
                }
            }
        },
        methods: {}

    });
    $(function () {
        reloadPage(1);
        $("#am-nav li").click(function () {
            $(".tab_list li").eq($(this).index()).show().siblings().hide();
        });
        $("#submitToAdd").click(function () {
            var name = $("#name").val();
            var enName = $("#enName").val();
            var rank = $("#rank").val();
            var contactName = $("#contactName").val();
            var contactMobile = $("#contactMobile").val();
            var params = $("#params").val();
            var address = $("#address").val();
            var businessName = $("#businessName").val();
            if (name == '') {
                layer.tips("供应商名称不能为空", "#name", 1);
                return false;
            }
            if (enName == '') {
                layer.tips("英文名称不能为空", "#enName", 1);
                return false;
            } else {
                var re = new RegExp("^[a-zA-Z]+$");
                if (!re.test(enName)) {
                    layer.tips("只能输入全英文", "#enName", 1);
                    return false;
                }
            }
            if (rank == '') {
                layer.tips("请选择供应商等级", "#rank", 1);
                return false;
            }
            if (contactName == '') {
                layer.tips("联系人姓名不能为空", "#contactName", 1);
                return false;
            }
            var phoneReg = /^1[3|4|5|7|8]\d{9}$/;
            if (!phoneReg.test(contactMobile)) {
                layer.tips("请正确填写手机号码", "#contactMobile", 1);
                return false;
            }
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/supplier/checkSupplierName",
                type: 'POST',
                async: false,
                data: {"name": name},
                dataType: "text",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data == 'T') {
                        layer.tips("供应商已存在，请换一个！", "#name", 1);
                        return false;
                    }
                    index = layer.load(0, {shade: [0.3, '#000']});
                    $.ajax({
                        url: "/supplier/checkEnName",
                        type: 'POST',
                        async: false,
                        data: {"enName": enName},
                        dataType: "text",
                        error: function () {
                            layer.close(index);
                        },
                        success: function (data) {
                            layer.close(index);
                            if (data == 'T') {
                                layer.tips("该英文名称已存在，请换一个！", "#enName", 1);
                                return false;
                            }
                            index = layer.load(0, {shade: [0.3, '#000']});
                            $.ajax({
                                url: "/supplier/addOrUpdateSupply",
                                type: 'POST',
                                async: false,
                                data: {
                                    "name": name,
                                    "enName": enName,
                                    "rank": rank,
                                    "contactName": contactName,
                                    "contactMobile": contactMobile,
                                    "params": params,
                                    "businessName":businessName,
                                    "address":address
                                },
                                dataType: "json",
                                error: function () {
                                    layer.close(index);
                                },
                                success: function (data) {
                                    layer.close(index);
                                    if (data.success) {
                                        $("#addSupplierForm").reset();
                                        layer.msg(data.message, {icon: 1});
                                        reloadPage(pageInfo.dq);
                                        returnList();
                                    } else {
                                        layer.msg(data.message, {icon: 2});
                                    }
                                }
                            });
                        }
                    })
                }
            });

        });
    });

    function shows(uid) {
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/supplier/getSupplierById",
            type: 'POST',
            async: false,
            data: {"supplyId": uid},
            dataType: "json",
            error: function () {
                layer.close(index);
            },
            success: function (data) {
                $("#nameEdit").val(data.name);
                $("#enNameEdit").val(data.enName);
                $("#rankEdit").val(data.rank);
                $("#oldName").val(data.name);
                $("#oldEnName").val(data.enName);
                $("#contactNameEdit").val(data.contactName);
                $("#contactMobileEdit").val(data.contactMobile);
                $("#paramsEdit").val(data.params);
                $("#addressEdit").val(data.address);
                $("#businessNameEdit").val(data.businessName);
                $("#supplyId").val(data.id);
                layer.close(index);
                $('.admin-content-body').hide();
                $('.tab_wu').show();
            }
        });

    }

    function hides() {
        $('.admin-content-body').show();
        $('.tab_wu').hide();
    }
    
    $("#close").click(returnList());
    
    function returnList(){
    	 $("#table2").removeClass("am-active");
         $("#tab2").removeClass("am-active");
         $("#tab2").removeClass("am-in");
         $("#table1").addClass("am-active");
         $("#tab1").addClass("am-active");
         $("#tab1").addClass("am-in");
    }

    function reloadPage(pageNumber) {
        if (pageNumber == 0) {
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var userNameToFind = $("#userNameToFind").val();
        var index = layer.load(0, {shade: [0.3, '#000']});
        $.ajax({
            url: "/supplier/getSupplierList?pageNumber=" + pageNumber + "&pageSize=10",
            type: 'POST',
            async: false,
            data: {"name": userNameToFind},
            dataType: "json",
            error: function () {
                $(this).addClass("done");
            },
            success: function (data) {
                pageInfo.ts = data.total;
                pageInfo.dq = data.pageNum;
                pageInfo.all = data.pages;
                vm.$set('json', data.list);
                layer.close(index);
            }
        });
    }
    function gotoPage() {
        var pagenum = $("#gotoPage").val();
        if (pagenum == '') {
            layer.tips("请输入页数！", "#gotoPage", {tips: 3});
            return;
        }
        reloadPage(pagenum);
    }

    function toEditAdminUser() {
//        debugger
        var oldName = $("#oldName").val();
        var name = $("#nameEdit").val();
        var rank = $("#rankEdit").val();
        var contactName = $("#contactNameEdit").val();
        var contactMobile = $("#contactMobileEdit").val();
        var params = $("#paramsEdit").val();
        var supplyId = $("#supplyId").val();
        var address = $("#addressEdit").val();
        var businessName = $("#businessNameEdit").val();
        if (name == '') {
            layer.tips("供应商名称不能为空", "#nameEdit", 1);
            return false;
        }
        if (rank == '') {
            layer.tips("请选择供应商等级", "#rank", 1);
            return false;
        }
        var test = /^[\u4e00-\u9fa5]{2,4}$/;
        if (contactName == '') {
            layer.tips("联系人不能为空", "#contactNameEdit", 1);
            return false;
        }

        if (contactMobile == '') {
            layer.tips("联系电话不能为空", "#contactMobileEdit", 1);
            return false;
        }
        if (!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(contactMobile))) {
            layer.tips("请输入正确的手机号", "#contactMobileEdit", 1);
            return false;
        }
        if (oldName == name) {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/supplier/addOrUpdateSupply",
                type: 'POST',
                async: false,
                data: {
                    "name": name,
                    "rank":rank,
                    "contactName": contactName,
                    "contactMobile": contactMobile,
                    "params": params,
                    "id": supplyId,
                    "businessName":businessName,
                    "address":address
                },
                dataType: "json",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.close(index);
                    if (data.success) {
                        layer.msg(data.message, {icon: 1});
                        reloadPage(pageInfo.dq);
                        hides();
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        } else {
            var index = layer.load(0, {shade: [0.3, '#000']});
            $.ajax({
                url: "/supplier/checkSupplierName",
                type: 'POST',
                async: false,
                data: {"name": name},
                dataType: "text",
                error: function () {
                    layer.close(index);
                },
                success: function (data) {
                    layer.closeAll();
                    if (data == 'T') {
                        layer.tips("供应商已存在，请换一个！", "#nameEdit", 2);
                        return false;
                    }
                    var index = layer.load(0, {shade: [0.3, '#000']});
                    $.ajax({
                        url: "/supplier/addOrUpdateSupply",
                        type: 'POST',
                        async: false,
                        data: {
                            "name": name,
                            "rank":rank,
                            "contactName": contactName,
                            "contactMobile": contactMobile,
                            "params": params,
                            "id": supplyId,
                            "businessName":businessName,
                            "address":address
                        },
                        dataType: "json",
                        error: function () {
                            layer.close(index);
                        },
                        success: function (data) {
                            layer.close(index);
                            if (data.success) {
                                layer.msg(data.message, {icon: 1});
                                reloadPage(1);
                            } else {
                                layer.msg(data.message, {icon: 2});
                            }
                        }
                    });
                }
            })
        }
    }

</script>
<script>

    //下面的正则表达式建议各位收藏哦，项目上有可能会用得着
    $(function () {
        $('#password').blur(function (e) {
            var html = '';
            //密码为八位及以上并且字母数字特殊字符三项都包括
            var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");

            //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等
            var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
            var enoughRegex = new RegExp("(?=.{4,}).*", "g");
            if (false == enoughRegex.test($(this).val())) {
                html = '<label style="color: red">密码位数不够</label>';
            } else if (strongRegex.test($(this).val())) {
                html = '密码强度：<label style="color: #00DD00">强</label>';
            } else if (mediumRegex.test($(this).val())) {
                html = '密码强度：<label style="color: yellow">中</label>';
            } else {
                html = '密码强度：<label style="color: red">弱</label>';
            }
            $('#passstrength').html(html);
            return true;
        });
    });
</script>

</body>

</html>

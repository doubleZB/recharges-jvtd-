<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/27
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <%@include file="/WEB-INF/pages/common/head.jsp"%>
</head>
<style>
    .checklist1 {
        border: 1px solid #dedede;
        width: 500px;
        overflow: auto;
        padding: 20px;
        padding-top: 5px;
        margin: 10px 120px;
    }

    .checklist1 > div {
        float: left;
        display: block;
        margin-left: 10px;
        margin-top: 10px;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">供应商支持卡品</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-btn-group">
                        <button type="button" class="am-btn am-btn-warning am-radius">移动</button>
                        <button type="button" class="am-btn am-btn-warning am-radius">联通</button>
                        <button type="button" class="am-btn am-btn-warning am-radius">电信</button>{{checkId | json}}
                    </div>
                    <hr>
                    <table class="am-table am-table-striped am-table-hover">
                        <thead>
                        <tr>
                            <th>
                                <label class="am-checkbox-inline">
                                    <input id="all" type="checkbox" @click="checked">序号
                                </label>
                            </th>
                            <th>面值/流量颗粒</th>
                            <th>产品编码</th>
                            <th>供货商产品编码</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in product1">
                            <td>
                                <label class="am-checkbox-inline">
                                    <input class="check" type="checkbox" value="{{item.id}}" name="checkbox" v-model="checkId" @click="checked1($event)">
                                    {{$index+1}}
                                </label>
                            </td>
                            <td>{{item.mz}}</td>
                            <td>{{item.productCode}}</td>
                            <td class="after">
                                <input type="text" class="checkcheck" value="{{item.supplierCode}}" disabled="true">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <hr/>
                    <div class="am-cf">
                        共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="#">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                <li><a href="#">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li class="am-active"><a href="#" style="padding: .5rem .4rem;">GO</a></li>
                                <li>
                                    <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;">
                                </li>
                                <li class="am-disabled"><a href="#">页</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
<script>
    Array.prototype.unique = function () {
        this.sort();
        var re = [this[0]];
        for (var i = 1; i < this.length; i++) {
            if (this[i] !== re[re.length - 1]) {
                re.push(this[i]);
            }
        }
        return re;
    };
    var vm = new Vue({
        el: "#content",
        data: {
            checkId: [],
            checkStatus:[],
            product1: [
                {
                    id: 1234,
                    mz: "10M",
                    productCode: "1001",
                    supplierCode: "Y453"
                },
                {
                    id: 2234,
                    mz: "10M",
                    productCode: "1001",
                    supplierCode: "Y453"
                },
                {
                    id: 3234,
                    mz: "10M",
                    productCode: "1001",
                    supplierCode: "Y453"
                },
                {
                    id: 4234,
                    mz: "10M",
                    productCode: "1001",
                    supplierCode: ""
                },
            ],
            json: [],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        },
        methods: {
            checked: function () {
                if ($("#all").prop("checked")) {
                    $("input[name='checkbox']").prop("checked", true);
                    $(".checkcheck").prop("disabled",false);
                    for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                        this.checkId.push($("input[name='checkbox']:checked").eq(i).val());
                    }
                    this.checkId = this.checkId.unique();
                } else {
                    this.checkId = [];
                    $(".checkcheck").prop("disabled",true);
                    $("input[name='checkbox']").prop("checked", false);
                }
            },
            checked1: function (e) {
                if ($("input[name='checkbox']:checked").length == $("input[name='checkbox']").length) {
                    $("#all").prop("checked", true);
                } else {
                    $("#all").prop("checked", false);
                }
                if($(e.target).prop("checked")){
                    $(e.target).parent().parent().siblings(".after").children().prop("disabled",false);
                }else{
                    $(e.target).parent().parent().siblings(".after").children().prop("disabled",true);
                }
            }
        }
    })
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/11/14
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qudaojiakuanjilu.css">
</head>
<style>
    *{
        moz-user-select: auto;
        -moz-user-select:auto;
        -o-user-select: auto;
        -khtml-user-select: auto;
        -webkit-user-select: auto;
        -ms-user-select: auto;
        user-select: auto;
    }
    .checklist {
        border: 1px solid #dedede;
        width: 500px;
        overflow: auto;
        margin: 10px 120px;
        padding: 10px;
        padding-top: 5px;
    }

    .checklist > div {
        float: left;
        display: block;
        margin-left: 10px;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">渠道加款记录</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form-inline" role="form">
                <div class="am-form-group">
                    <input style="width: 200px;float: left;" type="text" class="am-form-field" placeholder="选择日期"
                           data-am-datepicker readonly required/>
                </div>
                <div class="am-form-group">
                    <input style="width: 200px;float: left;" type="text" class="am-form-field" placeholder="选择日期"
                           data-am-datepicker readonly required/>
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input v-model="hd" type="text" class="am-form-field" placeholder="供应商名称"
                           style="width: 200px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input v-model="hd" type="text" class="am-form-field" placeholder="账户名"
                           style="width: 200px;float: left;">
                </div>
                <button type="submit" class="am-btn am-btn-warning"
                        style="width: 120px;margin:auto;margin-top: 20px">查询
                </button>
            </form>
            <hr>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>账户名</th>
                    <th>供应商名称</th>
                    <th>加款金额</th>
                    <th>加款说明</th>
                    <th>加款日期</th>
                    <th>操作人</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td>{{item.zhm}}</td>
                    <td>{{item.mc}}</td>
                    <td>{{item.je}}</td>
                    <td>{{item.sm}}</td>
                    <td>{{item.rq}}</td>
                    <td>{{item.cgl}}</td>
                </tr>
                </tbody>
            </table>
            <hr>
            <div class="am-cf">
                共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                <div class="am-fr">
                    <ul class="am-pagination" style="margin: 0">
                        <li><a href="#">上一页</a></li>
                        <li class="am-disabled"><a
                                href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                        <li><a href="#">下一页</a></li>
                        <li class="am-disabled"><a href="#">|</a></li>
                        <li>
                            <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;">
                        </li>
                        <li class="am-active"><a href="#" style="padding: .5rem .4rem;">GO</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script>
    var vm = new Vue({
        el: "#content",
        data: {
            checkID: [],
            lx: "",
            hd: "",
            yys: "",
            sf: "",
            json: [
                {
                    zhm: "bjjvtd",
                    mc: "北京聚通达",
                    je: "500",
                    sm: "加款说明",
                    rq: "2016-10-18 18:00",
                    cgl: "zhangsan"
                },
                {
                    zhm: "bjjvtd",
                    mc: "北京聚通达",
                    je: "500",
                    sm: "加款说明",
                    rq: "2016-10-18 18:00",
                    cgl: "zhangsan"
                },
                {
                    zhm: "bjjvtd",
                    mc: "北京聚通达",
                    je: "500",
                    sm: "加款说明",
                    rq: "2016-10-18 18:00",
                    cgl: "zhangsan"
                },
                {
                    zhm: "bjjvtd",
                    mc: "北京聚通达",
                    je: "500",
                    sm: "加款说明",
                    rq: "2016-10-18 18:00",
                    cgl: "zhangsan"
                }
            ],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            },
            item: {}
        }
    })
</script>
</body>
</html>

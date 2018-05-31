<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/11/14
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qudaoyueguanli.css">
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">渠道余额管理</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form-inline" role="form">
                <div class="am-form-group" style="margin-top: 20px">
                    <input v-model="hd" type="text" class="am-form-field" placeholder="渠道名称或ID"
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
                    <th>供应商名称</th>
                    <th>账户名</th>
                    <th>余额提醒阀值</th>
                    <th>提醒手机号</th>
                    <th>余额</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td>{{item.mc}}</td>
                    <td>{{item.zhm}}</td>
                    <td>{{item.fz}}</td>
                    <td>{{item.sjh}}</td>
                    <td>{{item.yu}}</td>
                    <td>
                        <div class="am-btn-toolbar">
                            <a class="am-btn am-btn-link" style="padding: 0;"
                               data-am-modal="{target: '#your-modal'}">加款</a>
                        </div>
                    </td>
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
    <!--编辑按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">商户加款
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">账户名：</label>
                        <div style="display:block;float:left;width:300px;text-align:left;padding-top: .6em;">jvtd</div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">供应商：</label>
                        <div style="display:block;float:left;width:300px;text-align:left;padding-top: .6em;">北京聚通达</div>
                    </div>
                    <div class="am-form-group" style="margin-top: 20px">
                        <label class="am-u-sm-2 am-form-label">加款金额：</label>
                        <input v-model="hd" type="text" class="am-form-field" placeholder="加款金额"
                               style="width: 300px;float: left;">
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">充值说明：</label>
                        <textarea class="" rows="5" id="doc-ta-1" style="width: 300px;"></textarea>
                    </div>
                    <hr>
                    <div class="am-form-group">
                        <div>
                            <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin: auto;">提交
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
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
                    mc: "聚通达",
                    zhm: "jvtd",
                    fz: "500",
                    sjh: "13591872464",
                    yu: "1000.00",
                },
                {
                    mc: "聚通达",
                    zhm: "jvtd",
                    fz: "500",
                    sjh: "13591872464",
                    yu: "1000.00",
                },
                {
                    mc: "聚通达",
                    zhm: "jvtd",
                    fz: "500",
                    sjh: "13591872464",
                    yu: "1000.00",
                },
                {
                    mc: "聚通达",
                    zhm: "jvtd",
                    fz: "500",
                    sjh: "13591872464",
                    yu: "1000.00",
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

<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/11/14
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qudaochongzhijiankong.css">
</head>
<style>
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">渠道充值监控</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form-inline" role="form">
                <div class="am-form-group">
                    <select class="select1" v-model="lx" style="width: 200px;float: left;">
                        <option value="">渠道类型</option>
                        <option value="流量">流量</option>
                        <option value="话费">话费</option>
                    </select>
                </div>
                <div class="am-form-group">
                    <select class="select1" v-model="yys" style="width: 200px;float: left;">
                        <option value="">运营商</option>
                        <option value="移动">移动</option>
                        <option value="联通">联通</option>
                        <option value="电信">电信</option>
                    </select>
                </div>
                <div class="am-form-group">
                    <select class="select1" v-model="sf" style="width: 200px;float: left;">
                        <option value="">省份</option>
                        <option value="北京">北京</option>
                        <option value="山西">山西</option>
                    </select>
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input v-model="hd" type="text" class="am-form-field" placeholder="渠道名称或ID"
                           style="width: 200px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input v-model="hd" type="text" class="am-form-field" placeholder="供应商"
                           style="width: 200px;float: left;">
                </div>
                <button type="submit" class="am-btn am-btn-warning"
                        style="width: 120px;margin:auto;margin-top: 20px">查询
                </button>
                <button type="submit" class="am-btn am-btn-success"
                        style="width: 120px;margin:auto;margin-top: 20px">导出
                </button>
            </form>
            <hr>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>渠道ID</th>
                    <th>渠道名称</th>
                    <th>渠道类型</th>
                    <th>省份</th>
                    <th>运营商</th>
                    <th>成功率</th>
                    <th>成功条数</th>
                    <th>失败条数</th>
                    <th>结果未知条数</th>
                    <th>时间</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td>{{item.id}}</td>
                    <td>{{item.mc}}</td>
                    <td>{{item.lx}}</td>
                    <td>{{item.sf}}</td>
                    <td>{{item.yys}}</td>
                    <td>{{item.cgl}}</td>
                    <td>{{item.cgs}}</td>
                    <td>{{item.sbs}}</td>
                    <td>{{item.wz}}</td>
                    <td>{{item.sj}}</td>
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
                    id: 1234,
                    mc: "A",
                    lx: "流量",
                    sf: "北京",
                    yys: "移动",
                    cgl: "98%",
                    cgs: 100,
                    sbs: 8,
                    wz: 2,
                    flb: 30,
                    sj: "最近30分钟"
                },
                {
                    id: 1234,
                    mc: "A",
                    lx: "流量",
                    sf: "北京",
                    yys: "移动",
                    cgl: "98%",
                    cgs: 100,
                    sbs: 8,
                    wz: 2,
                    flb: 30,
                    sj: "最近30分钟"
                },
                {
                    id: 1234,
                    mc: "A",
                    lx: "流量",
                    sf: "北京",
                    yys: "移动",
                    cgl: "98%",
                    cgs: 100,
                    sbs: 8,
                    wz: 2,
                    flb: 30,
                    sj: "最近30分钟"
                },
                {
                    id: 1234,
                    mc: "A",
                    lx: "流量",
                    sf: "北京",
                    yys: "移动",
                    cgl: "98%",
                    cgs: 100,
                    sbs: 8,
                    wz: 2,
                    flb: 30,
                    sj: "最近30分钟"
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

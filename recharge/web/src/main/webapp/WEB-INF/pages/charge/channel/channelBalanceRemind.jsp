<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/11/14
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qudaoyuetixing.css">
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
            <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">渠道余额提醒</strong>
            </div>
        </div>
        <hr>
        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form am-form-horizontal">
                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">供应商名称：</label>
                    <select class="select1" style="width: 300px;float: left;">
                        <option value="">供应商名称</option>
                        <option value="聚通达1">聚通达1</option>
                        <option value="聚通达2">聚通达2</option>
                    </select>
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <label class="am-u-sm-2 am-form-label">账户名：</label>
                    <input v-model="hd" type="text" class="am-form-field" placeholder="账户名"
                           style="width: 300px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <label class="am-u-sm-2 am-form-label">余额提醒阈值：</label>
                    <input v-model="hd" type="text" class="am-form-field" placeholder="余额提醒阈值"
                           style="width: 300px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <label class="am-u-sm-2 am-form-label">短信发送手机号：</label>
                    <input v-model="hd" type="text" class="am-form-field" placeholder="短信发送手机号"
                           style="width: 300px;float: left;"><span style="line-height: 35px;margin-left: 10px;color: #ccc;">多个手机号用英文分号间隔，允许最多添加2个手机号</span>
                </div>
                <div class="am-form-group">
                    <div>
                        <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin: auto; margin-left: 180px;">提交
                        </button>
                    </div>
                </div>
            </form>
            <hr/>
            <p>注：.....</p>
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
            json: [
                {
                    id: "1234",
                    mc: "聚通达",
                    lxr: "zhangsan",
                    sjh: "13591872464",
                    ry: "李四"
                },
                {
                    id: "1234",
                    mc: "聚通达",
                    lxr: "zhangsan",
                    sjh: "13591872464",
                    ry: "李四"
                },
                {
                    id: "1234",
                    mc: "聚通达",
                    lxr: "zhangsan",
                    sjh: "13591872464",
                    ry: "李四"
                },
                {
                    id: "1234",
                    mc: "聚通达",
                    lxr: "zhangsan",
                    sjh: "13591872464",
                    ry: "李四"
                }
            ],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        }
    })
</script>
</body>
</html>
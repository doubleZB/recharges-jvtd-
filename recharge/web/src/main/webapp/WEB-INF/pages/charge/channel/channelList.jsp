<%--
  Created by IntelliJ IDEA.
  User: lenovo
  渠道管理
  Date: 2016/11/14
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qudaoliebiao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layer/skin/layer.css">
</head>
<style>
    .checklist {
        border: 1px solid #dedede;
        width: 500px;
        overflow: auto;
        margin: 10px 150px;
        padding: 10px;
        padding-top: 5px;
    }

    .checklist > div {
        float: left;
        display: block;
        margin-left: 10px;
    }
    .am-btn{
        border-radius: 5px;
    }
    input,select{
        color:#848181;
    }
    .am-u-sm-2 {
        width: 150px;
    }
    @media only screen and (min-width: 641px) {
        .am-modal-dialog {
            width: 640px;
        }
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
    .checklist2 {
        border: 1px solid #dedede;
        overflow: auto;
        margin: 10px 120px;
        padding: 10px;
        padding-top: 5px;
    }
    .checklist2 div{
        float: left;
        margin-right: 10px;
    }
    #content{
        margin-top: 20px;
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

    iframe {
        margin-left: 120px;
        border: 1px solid #ccc;
    }

    .chose {
        margin-left: 120px;
    }

    #listModal .am-u-sm-2 {
        width: 100%;
        text-align: center;
    }
    th,td{
        text-align:center;
    }
    input,select{
        color:#848181;
    }
    .checklist2 {
        border: 1px solid #dedede;
        overflow: auto;
        margin: 10px 120px;
        padding: 10px;
        padding-top: 5px;
    }
    .checklist2 div{
        float: left;
        margin-right: 10px;
    }




    .IptCon{
        width:150px;
        height:35px;
    }
    .IptCon > input{
        width:100%;
        height:35px;
        border:1px solid #ccc;
    }
    .posi{
        width:150px;
        position: absolute;
        left:0;
        top:37px;
        display: none;
        border:1px solid #ccc;
        background: #fff;
        height: auto;
        max-height: 140px;
        overflow-y: scroll;
        background: #fff;
        z-index: 100;
    }
    .posi li{
        line-height: 35px;
        cursor: pointer;
    }
    .qwe{
        display: block;
        width:100%;
        height:100%;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body" style="padding-top: 20px;">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">渠道管理<span></span></a></li>
                <input type="hidden" value="${adminLoginUser.adminName}" id="userId"/>
                <li><a href="#tab2">新增</a><%--{{checkID | json}}--%></li>
            </ul>
            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <input type="hidden" value="{{cx|json}}" id="toFindListData">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" style="width: 150px;float: left;" id="business_type" v-model="chaxun.lx" @change="clearmz()">
                                <option value="0">渠道类型</option>
                                <option value="1">流量</option>
                                <option value="2">话费</option>
                                <option value="3">视频会员</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" style="width: 150px;float: left;" id="operator" v-model="chaxun.yys">
                                <option value="0">运营商</option>
                                <option value="1">移动</option>
                                <option value="2">联通</option>
                                <option value="3">电信</option>
                                <option value="4">优酷</option>
                                <option value="5">爱奇艺</option>
                                <option value="6">腾讯</option>
                                <option value="7">搜狐</option>
                                <option value="8">乐视</option>
                                <option value="9">PPTV</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <%--<select class="select1" style="width: 200px;float: left;" v-model="chaxun.sf">--%>
                            <%--<option v-for="option in sheng" :value="option.value">{{option.text}}</option>--%>
                            <%--</select>--%>
                            <input type="text"  class="am-form-field" id="cxsf" placeholder="选择省份"
                                   style="width: 150px;float: left;" @focus="opensf()">
                        </div>
                        <div class="am-form-group" style="margin-top: 0px">
                            <input type="text"  class="am-form-field" id="money" placeholder="包体&面值"
                                   style="width: 150px;float: left;font-size: 12px;" @focus="faceValue()">
                        </div>
                        <%--<div class="clearfix"></div>--%>
                        <div class="am-form-group" style="margin-top: 0px">
                            <input type="text" class="am-form-field" placeholder="渠道简称"
                                   style="width: 150px;float: left;" v-model="chaxun.jc">
                        </div>
                        <div class="am-form-group" style="margin-top: 0px">
                            <div style="position: relative;float:left;">
                                <div class="IptCon">
                                    <input type="text" v-model="searchQuery" value="" class="searchIpt" placeholder="请选择供应商">
                                </div>
                                <div class="posi" style="padding: 0;">
                                    <simple-grid :data-list="supplier" :columns="columns" :search-key="searchQuery">
                                    </simple-grid>
                                </div>
                            </div>
                            <template  id="grid-template">
                                <ul style="padding: 0;margin: 0;">
                                    <li v-for="(index,entry) in dataList | filterBy searchKey">
                                        <span v-for="col in columns" num="{{entry[col.ind]}}" class="qwe">{{entry[col.name]}}</span>
                                    </li>
                                </ul>
                            </template>
                        </div>
                        <div class="am-form-group" style="margin-top: 0px">
                            <select class="select1" style="width: 150px;float: left;" v-model="chaxun.zt">
                                <option value="0">选择状态</option>
                                <option value="1">开启</option>
                                <option value="2">测试中</option>
                                <option value="3">关闭</option>
                            </select>
                        </div>
                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin:auto;margin-top: 0px"  onclick="getChannelsDetail()">查询
                        </button>
                        <hr>
                        <div class="am-u-sm-12 am-u-md-8" style="padding-left: 0;">
                            <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: auto;" @click="piliang1()">批量修改</button>
                            <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: auto;" @click="piliang2()" id="yijianxiugai">一键修改</button>
                            <label class="am-checkbox-inline">
                                <input type="checkbox" class="tiaojian" @click="firstCheck()">按查询条件
                            </label>
                            <input type="hidden" id="editDataSubData" value="{{ checkID | json }}">
                        </div>
                        <div style="clear:both;"></div>
                        <hr>
                    </form>

                    <table class="am-table am-table-striped am-table-hover">
                        <thead>
                        <tr>
                            <th>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" class="checkall" @click="checked()"> 序号
                                </label>
                            </th>
                            <th>渠道ID</th>
                            <th>渠道类型</th>
                            <th>渠道简称</th>
                            <th>归属供应商</th>
                            <th>运营商</th>
                            <th>省份</th>
                            <th>产品</th>
                            <th>面值</th>
                            <th>成本折扣</th>
                            <th>状态</th>
                            <th>分流比</th>
                            <%--<th>结果未知时常</th>--%>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="tbody1">
                        <tr v-for="item in json">
                            <td>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" value="{{item.id}}" name="checkbox" v-model="checkID" @click="checked1()">
                                    {{$index+1}}
                                </label>
                            </td>
                            <td>{{item.id}}</td>
                            <td>{{item.businessType}}</td>
                            <td>{{item.channelName}}</td>
                            <td>{{item.supplyId}}</td>
                            <td>{{item.operatorId}}</td>
                            <td>{{item.provinceId}}</td>
                            <td>{{item.positionCode}}</td>
                            <td>{{item.amount}}</td>
                            <td>{{item.costDiscount}}</td>
                            <td>{{item.status}}</td>
                            <td>{{item.weight}}</td>
                            <td>
                                <a class="am-btn am-btn-link" style="padding: 0;" @click="model($index)">修改</a>
                                <%--<a @click="remove($index)" class="am-btn am-btn-link"--%>
                                   <%--style="padding: 0;color: #F37B1D;">删除</a>--%>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="sj" style="display: none; color: red;font-size: 2em;text-align: center;">没有找到数据</div>
                    <hr>
                    <div class="am-cf">
                        共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="javascript:void(0);" onclick="getChannelsDetail('{{page.dq-1}}');">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                <li><a href="javascript:void(0);"  onclick="getChannelsDetail('{{page.dq+1}}','{{page.all}}');">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                </li>
                                <li class="am-active"><a href="#" style="padding: .5rem .4rem;" onclick="gotoPage()">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">渠道简称：</label>
                            <div style="width: 300px;float: left;">
                                <input type="text" placeholder="2~8个汉字，仅限中文" v-model="form.qdjc"  onblur="tongdaomingcheng(this.value)">
                            </div>
                            <span id="qudaomingcheng"></span>
                        </div>

                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">渠道类型：</label>
                            <label class="am-radio-inline">
                                <input class="qdradio1" type="radio" value="1" name="qdradio" v-model="form.qdlx"  onclick="qudaoleixingxiugai(1)"> 流量
                            </label>
                            <label class="am-radio-inline">
                                <input class="qdradio1" type="radio" value="2" name="qdradio" v-model="form.qdlx"  onclick="qudaoleixingxiugai(2)"> 话费
                            </label>
                            <label class="am-radio-inline">
                                <input class="qdradio1" type="radio" value="3" name="qdradio" v-model="form.qdlx"  onclick="qudaoleixingxiugai(3)"> 视频会员
                            </label>
                        </div>

                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">供应商：</label>
                            <div style="position: relative;float:left;">
                                <div class="IptCon">
                                    <input type="text" v-model="searchQuery" value="" class="searchIpt" placeholder="请选择供应商" style="width: 300px;">
                                </div>
                                <div class="posi" style="padding: 0;width: 300px">
                                    <simple-grid :data-list="supplier" :columns="columns" :search-key="searchQuery">
                                    </simple-grid>
                                </div>
                            </div>
                            <template  id="grid-template">
                                <ul style="padding: 0;margin: 0;">
                                    <li v-for="(index,entry) in dataList | filterBy searchKey">
                                        <span v-for="col in columns" num="{{entry[col.ind]}}">{{entry[col.name]}}</span>
                                    </li>
                                </ul>
                            </template>
                        </div>
                        <div class="am-form-group" id="operatorYld">
                            <label class="am-u-sm-2 am-form-label">运营商：</label>
                            <select style="width: 300px;float: left;" v-model="form.yys" @change="qudaochanpin()">
                                <option value="0">运营商</option>
                                <option value="1">移动</option>
                                <option value="2">联通</option>
                                <option value="3">电信</option>
                            </select>
                        </div>

                        <div class="am-form-group" style="display: none" id="videoVip">
                            <label class="am-u-sm-2 am-form-label">运营商：</label>
                            <select style="width: 300px;float: left;" v-model="form.yys" @change="qudaochanpin()">
                                <option value="0">运营商</option>
                                <option value="4">优酷</option>
                                <option value="5">爱奇艺</option>
                                <option value="6">腾讯</option>
                                <option value="7">搜狐</option>
                                <option value="8">乐视</option>
                                <option value="9">PPTV</option>
                            </select>
                        </div>

                        <div class="am-form-group" id="provinceAll">
                            <label class="am-u-sm-2 am-form-label">省份：</label>
                            <div class="am-checkbox" style="display: block;width: 80px;float: left;padding-left: 20px;">
                                <label>
                                    <input type="checkbox" class="checkk checkM" name="sfradio" @click="checkk()">全选
                                </label>
                            </div>
                            <div style="clear: both;"></div>
                            <div class="checklist">
                                <div class="am-checkbox" v-for="item in xzsf">
                                    <label>
                                        <input type="checkbox" class="checkM" value="{{item.id}}" name="sf" v-model="form.sf" @click="nocheck()"> {{item.value}}
                                    </label>
                                </div>

                            </div>
                        </div>
                        <template v-if="splx==1"><%--流量--%>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">支持卡品：</label>
                                <label class="am-radio-inline">
                                    <input type="radio" value="1" name="docInlineRadio1" v-model="form.lllx"> 全国流量（漫游）
                                </label>
                                <label class="am-radio-inline">
                                    <input type="radio" value="2" name="docInlineRadio1" v-model="form.lllx"> 省内流量（不漫游）
                                </label>
                                <div class="checklist">
                                    <div class="am-checkbox" v-for="item in liuliang">
                                        <label>
                                            <input type="checkbox" value="{{item.positionCode}}" v-model="form.ll">{{item.name}}({{item.amount}}元)
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </template>
                        <template  v-if="splx==2"><%--话费--%>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">支持卡品：</label>
                                <div class="checklist">
                                    <div class="am-checkbox" v-for="item in huafei">
                                        <label>
                                            <input type="checkbox" value="{{item.positionCode}}" v-model="form.hf">{{item.name}}({{item.amount}}元)
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </template>

                        <template v-if="splx==3"><%--视频会员--%>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">支持卡品：</label>
                                <div class="checklist">
                                    <div class="am-checkbox" v-for="item in member">
                                        <label>
                                            <input type="checkbox" value="{{item.positionCode}}" v-model="form.hy">{{item.name}}({{item.amount}}元)
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </template>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">成本折扣：</label>
                            <input type="text" class="am-form-field" placeholder="允许四位小数"
                                   style="width: 120px;float: left;" v-model="form.zk" onblur="zhekou(this.value)" ><span
                                style="line-height:35px;color: red;margin-left: 10px;" id="zhekous"></span>

                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">分流比：</label>
                            <input type="text" class="am-form-field" placeholder="1~100任意整数"
                                   style="width: 120px;float: left;" v-model="form.flb" onblur="fenliubi(this.value)"><span
                                style="line-height:35px;color: red;margin-left: 10px;" id="iszhengshu" ></span>

                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">选择状态：</label>
                            <select style="width: 300px;float: left;" v-model="form.zt">
                                <option value="0">选择状态</option>
                                <option value="1">开启</option>
                                <option value="2">测试中</option>
                                <option value="3">关闭</option>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <div class="am-u-sm-2 am-u-sm-offset-1">
                                <button type="button" class="am-btn am-btn-warning"
                                        style="width: 120px;margin:20px auto;margin-left: 60px;" onclick="addChannels()">提交
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
    <!--编辑按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">渠道修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <input type="hidden" class="am-form-field" placeholder=""
                           style="width: 120px;float: left;" id="channelsid">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">成本折扣：</label>
                        <input type="text" class="am-form-field" placeholder="折扣应该是大于0小于2的小数"
                               style="width: 120px;float: left;" v-model="form.zk" id="formzk" >
                        <span id="zk-bianji"></span>
                        <span style="line-height:35px;color: red;margin-left: 10px;float:left;" id="bjzhekouspan"></span>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">分流比：</label>
                        <input type="text" class="am-form-field" placeholder="1~100任意整数"
                               style="width: 120px;float: left;" v-model="form.flb" id="formflb" ><span id="flb-bianji"></span><span
                            style="line-height:35px;color: red;margin-left: 10px;float:left;" id="bjfenliubispan"></span>
                    </div>
                    <%-- <div class="am-form-group">
                         <label class="am-u-sm-2 am-form-label">结果未知时长：</label>
                         <input type="text" class="am-form-field" placeholder=""
                                style="width: 120px;float: left;" v-model="form.sc"><span
                             style="line-height:35px;margin-left: 10px;float:left;">分钟</span>
                     </div>--%>
                    <div class="am-form-group">
                          <label class="am-u-sm-2 am-form-label">选择状态：</label>
                          <select style="width: 300px;float: left;" v-model="form.zt"  id="chooseStatus">
                              <option value="0">选择状态</option>
                              <option value="1">开启</option>
                              <option value="2">测试中</option>
                              <option value="3">关闭</option>
                          </select>
                    </div>
                    <div class="am-form-group">
                        <div class="am-u-sm-2 am-u-sm-offset-1">
                            <button type="button" class="am-btn am-btn-warning"
                                    style="width: 200px;margin:20px auto;margin-left: 75px;" onclick="bianjia()">提交
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!--省份选择弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="sfChose">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">
                <a href="javascript: void(0)" class="am-close am-close-spin" @click="closesf()">&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">省份：</label>
                        <div class="am-checkbox" style="display: block;float: left;">
                            <label>
                                <input type="checkbox" class="cxsf" @click="cxsfqx()"> 全部选择<br>
                            </label>
                        </div>
                        <div style="clear: both;"></div>
                        <div class="checklist2">
                            <div class="am-checkbox" v-for="s in xzsf">
                                <label>
                                    <input type="checkbox" name="cxsf" value="{{s.id}}" v-model="chaxun.sf"
                                           @click="cxsfcheck()">{{s.value}}
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <hr>
            <div class="am-form-group">
                <div>
                    <button type="button" class="am-btn am-btn-warning"
                            style="width: 120px;margin: auto;" @click="closesf()">确定
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!--批量按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="pl_modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">批量修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">修改折扣：</label>
                        <div style="width: 300px;float: left;">
                            <input type="text" placeholder="允许四位小数" id="piliangzhekou">
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">修改分流比：</label>
                        <div style="width: 300px;float: left;">
                            <input type="text" placeholder="请输入1~100整数" id="piliangfenliubi">
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">选择状态：</label>
                        <select style="width: 300px;float: left;" id="chooseStatusTwo">
                            <option value="0">选择状态</option>
                            <option value="1">开启</option>
                            <option value="2">测试中</option>
                            <option value="3">关闭</option>
                        </select>
                    </div>
                    <hr>
                    <div class="am-form-group">
                        <div>
                            <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: auto;" onclick="piliangxiugaitijiao()">提交
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="am-modal am-modal-no-btn" tabindex="-1" id="pl_modal1">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">一键修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">修改折扣：</label>
                        <div style="width: 300px;float: left;">
                            <input type="text" placeholder="允许四位小数" id="piliangyijianzhekou">
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">修改分流比：</label>
                        <div style="width: 300px;float: left;">
                            <input type="text" placeholder="请输入1~100整数" id="piliangyijianfenliubi">
                        </div>
                    </div>
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">选择状态：</label>
                        <select style="width: 300px;float: left;" id="chooseStatusThree">
                            <option value="0">选择状态</option>
                            <option value="1">开启</option>
                            <option value="2">测试中</option>
                            <option value="3">关闭</option>
                        </select>
                    </div>
                    <hr>
                    <div class="am-form-group">
                        <div>
                            <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: auto;" onclick="piliangyijianxiugaitijiao()">提交
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>




    <!-- content end -->
    <!--面值颗粒选择弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="mzChose">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">
                <a href="javascript: void(0)" class="am-close am-close-spin" @click="closemz()">&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">面值：</label>
                        <div class="am-checkbox" style="display: block;float: left;">
                            <label>
                                <input type="checkbox" class="cxmz" @click="cxmzqx()"> 全部选择<br>
                            </label>
                        </div>
                        <div style="clear: both;"></div>
                        <div class="checklist2">
                            <div class="am-checkbox" v-for="s in optionMz">
                                <label>
                                    <input type="checkbox"  name="cxmz" value="{{s.code}}" v-model="cx.mz"
                                           @click="cxmzcheck()">{{s.name}}({{s.amount}}元)
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <hr>
            <div class="am-form-group">
                <div>
                    <button type="button" class="am-btn am-btn-warning"
                            style="width: 120px;margin: auto;" @click="closemz()">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script src="${pageContext.request.contextPath}/static/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/format/format.js"></script>
<script>
    var inds;
    $(".posi").on("click",$(".posi li span"),function(e){
        var strNode=e.target.innerHTML.split("li");
        if(strNode.length>2){
            return false;
        };
        e.stopPropagation();
        $(".IptCon>input").val(e.target.innerHTML);
        $(".posi").css("display","none");
        inds=e.target.getAttribute("num");
    });
    $(document).click(function(e){
        e.stopPropagation();
        $(".posi").css("display","none");
    })
    $(".searchIpt").click(function(e){
        e.stopPropagation();
    })
    $(".searchIpt").focus(function(){
        $(".posi").css("display","block");
    });
    Vue.component('simple-grid', {
        template: '#grid-template',
        props: ['dataList', 'columns', 'searchKey'],
    })

    $(function(){
        getSupplier();//执行函数
        getSupplierprovince();
        getproductcode();
        getChannelsDetail();

    });
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

    var arr = [];
    var cxmzarr = [];
    var Off = true;
    var cxsfarr = [];
    var vm = new Vue({
        el: "#content",
        data: {
            cx: {
                mz: [],
            },
            optionMz:[],
            splx:1,

            searchQuery: '',
            columns: [{
                name: 'name',
                ind:'id'
            }],

            checkID: [],
            lx: "",
            hd: "",
            yys: "",
            sf: "",
            json: [
            ],
            chaxun:{
                lx:"0",
                yys:"0",
                sf:[],
                jc:"",
                gys:"0",
                zt:"0"
            },
            form:{
                qdjc:"",
                gys:"0",
                yys:"0",
                sf:[],
                qdlx:"",
                lllx:"",
                ll:[],
                hf:[],
                hy:[],
                zk:"",
                flb:"",
                sc:"",
                zt:"0"
            },
            sheng: [
//                {text: '省份', value: '0'},
                {text: '上海', value: '1'},
                {text: '云南', value: '2'},
                {text: '内蒙古', value: '3'},
                {text: '北京', value: '4'},
                {text: '吉林', value: '5'},
                {text: '四川', value: '6'},
                {text: '天津', value: '7'},
                {text: '宁夏', value: '8'},
                {text: '安徽', value: '9'},
                {text: '山东', value: '10'},
                {text: '山西', value: '11'},
                {text: '广东', value: '12'},
                {text: '广西', value: '13'},
                {text: '新疆', value: '14'},
                {text: '江苏', value: '15'},
                {text: '江西', value: '16'},
                {text: '河北', value: '17'},
                {text: '河南', value: '18'},
                {text: '浙江', value: '19'},
                {text: '海南', value: '20'},
                {text: '湖北', value: '21'},
                {text: '湖南', value: '22'},
                {text: '甘肃', value: '23'},
                {text: '福建', value: '24'},
                {text: '西藏', value: '25'},
                {text: '贵州', value: '26'},
                {text: '辽宁', value: '27'},
                {text: '重庆', value: '28'},
                {text: '陕西', value: '29'},
                {text: '青海', value: '30'},
                {text: '黑龙江', value: '31'},
                {text: '全国', value: '32'},
            ],
            supplier:[],
            xzgys:[],
            xzsf:[],
            liuliang:[],
            member:[],
            huafei:[],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            },
            item: {}
        },
        ready:function(){
            this.firstCheck();
        },
        methods: {
            remove: function (index) {
                var  userId=$("#userId").val();
                var msg = confirm("你确定删除此记录吗？");
                if (msg == true) {
                    var id=this.json[index].id;
                    $.ajax({
                        url: "${pageContext.request.contextPath}/channels/deleteChannel",
                        async:false,
                        type:'POST',
                        data:{"id":id,"userId":userId},
                        dataType:"json",
                        success: function(data){
                            if(data==1){
                                layer.msg("删除成功~~~~~~~~~~~~~");
                                location.reload();
                            }else{
                                layer.msg("删除失败~~~~~~~~~~~~~");
                            }
                        }
                    });
                }
            },
            cxmzqx: function () {
                if ($(".cxmz").prop("checked")) {
                    $("input[name='cxmz']").prop("checked", true);
                    for (var i = 0; i < $("input[name='cxmz']").length; i++) {
                        cxmzarr.push($("input[name='cxmz']:checked").eq(i).val());
                    }
                    this.cx.mz = cxmzarr.filter(function (element, index, self) {
                        return self.indexOf(element) === index;
                    });
                } else {
                    this.cx.mz = [];
                    $("input[name='cxmz']").prop("checked", false);
                }
            },
            faceValue:function(){
                var businessType = $("#business_type").val();
                var operator = $("#operator").val();
                if(businessType!=0){
                    if(operator!=0){
                        mz(businessType,operator);
                        $("#mzChose").modal({
                            closeViaDimmer: false
                        });
                    }else{
                        layer.msg("请选择运营商!");
                    }
                }else{
                    layer.msg("请选择商品类型!");
                }
            },
            closemz:function(){
                var a =[];
                $("#mzChose").modal("close");
                for(let i = 0; i<this.optionMz.length;i++){
                    for(let j = 0;j<this.cx.mz.length;j++){
                        if(this.optionMz[i].code == this.cx.mz[j]){
                            a.push(this.optionMz[i].name+"("+this.optionMz[i].amount+"元)");
                        }
                    };
                }
                $("#money").val(a);
            },
            clearmz:function(){
                this.cx.mz = [];
                $("#money").val("");
            },
            cxmzcheck: function () {
                if ($("input[name='cxmz']:checked").length == $("input[name='cxmz']").length) {
                    $(".cxmz").prop("checked", true);
                } else {
                    $(".cxmz").prop("checked", false);
                }
            },
            checked: function () {
                if ($(".checkall").prop("checked")) {
                    $("input[name='checkbox']").prop("checked", true);
                    for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                        arr.push($("input[name='checkbox']:checked").eq(i).val());
                    }
                    this.checkID = arr.unique();
                } else {
                    this.checkID = [];
                    arr = [];
                    $("input[name='checkbox']").prop("checked", false);
                }
            },
            checked1: function () {
                if ($("input[name='checkbox']:checked").length == this.json.length) {
                    $(".checkall").prop("checked", true);
                } else {
                    $(".checkall").prop("checked", false);
                }
            },
            nocheck: function () {
                if ($("input[name='sf']:checked").length == $("input[name='sf']").length) {
                    $(".checkk").prop("checked", true);
                } else {
                    $(".checkk").prop("checked", false);
                }
            },
            fensheng:function (a) {
                if(a == 1){
                    this.form.sf = [];
                    $("input[name='sf']").prop("checked", false);
                }else{
                    $("input[name='sf']").prop("checked", true);
                    for (var i = 0; i < $("input[name='sf']").length; i++) {
                        this.form.sf.push($("input[name='sf']:checked").eq(i).val());
                    }
                }
            },
            checkk: function () {
                if ($(".checkk").prop("checked")) {
                    $("input[name='sf']").prop("checked", true);
                    for (var i = 0; i < $("input[name='sf']").length; i++) {
                        this.form.sf.push($("input[name='sf']:checked").eq(i).val());
                    }
                    this.form.sf = this.form.sf.unique();
                } else {
                    this.form.sf = [];
                    $("input[name='sf']").prop("checked", false);
                }
            },
            cxsfqx: function () {
                if ($(".cxsf").prop("checked")) {
                    $("input[name='cxsf']").prop("checked", true);
                    for (var i = 0; i < $("input[name='cxsf']").length; i++) {
                        cxsfarr.push($("input[name='cxsf']:checked").eq(i).val());
                    }
                    this.chaxun.sf = cxsfarr.filter(function (element, index, self) {
                        return self.indexOf(element) === index;
                    });
                } else {
                    this.chaxun.sf = [];
                    $("input[name='cxsf']").prop("checked", false);
                }
            },
            opensf:function(){
                $("#sfChose").modal({
                    closeViaDimmer: false
                });
            },
            closesf:function(){
                var a =[];
                $("#sfChose").modal("close");
                for(let i = 0; i<this.xzsf.length;i++){
                    for(let j = 0;j<this.chaxun.sf.length;j++){
                        if(this.xzsf[i].id == this.chaxun.sf[j]){
                            a.push(this.xzsf[i].value);
                        }
                    };
                }
                $("#cxsf").val(a);
            },
            cxsfcheck: function () {

                if ($("input[name='cxsf']:checked").length == $("input[name='cxsf']").length) {
                    $(".cxsf").prop("checked", true);
                } else {
                    $(".cxsf").prop("checked", false);
                }
            },
            //获取商品
            qudaochanpin:function () {
                var busstype=this.form.qdlx;
                var operator=this.form.yys;
                var suppleir=inds;
                if( busstype == 1){//流量
                    $.ajax({
                        url: "${pageContext.request.contextPath}/channels/getSupplierProduct",
                        async:false,
                        type:'POST',
                        data:{"businessType":busstype,"operator":operator,"supplyId":suppleir},
                        dataType:"json",
                        success: function(data){
                            vm.liuliang=data;
                            if(data==""){
                                layer.msg("该供应商没有相应产品");
                            }
                        }
                    });
                    this.splx = 1;
                    this.form.hf = [];
                }else if( busstype == 2){//话费
                    $.ajax({
                        url: "${pageContext.request.contextPath}/channels/getSupplierProduct",
                        async:false,
                        type:'POST',
                        data:{"businessType":busstype,"operator":operator,"supplyId":suppleir},
                        dataType:"json",
                        success: function(data){
                            vm.huafei=data;
                            if(data==""){
                                layer.msg("该供应商没有相应产品");
                            }
                        }
                    });
                    this.splx = 2;
                    this.form.ll = [];
                }else{
                    $.ajax({
                        url: "${pageContext.request.contextPath}/channels/getSupplierProduct",
                        async:false,
                        type:'POST',
                        data:{"businessType":busstype,"operator":operator,"supplyId":suppleir},
                        dataType:"json",
                        success: function(data){
                            vm.member=data;
                            console.log("视频会员"+vm.member);
                            if(data==""){
                                layer.msg("该供应商没有相应产品");
                            }
                        }
                    });

                    this.splx = 3;
                    this.form.ll = [];
                    this.form.hf = [];
                }
            },
            model: function (index) {
                $('#your-modal').modal();
                $("#channelsid").val(this.json[index].id);
                this.form.zk= this.json[index].costDiscount;
                this.form.flb= this.json[index].weight;

                //this.form.zt=this.json[index].status;
            },
            //批量修改状态
            piliang1:function(){
                if(this.checkID.length == 0){
                    alert("请选择想要修改的数据！")
                }else{
                    $("#pl_modal").modal();
                }
            },
            piliang2:function(){
                if(this.chaxun.yys == "0"){
                    alert("请选择运营商！")
                }else if (this.chaxun.lx == "0"){
                    alert("请选择类型！")
                }else if ($(".IptCon>input").val()==""){
                    inds=0;
                    if (inds == 0){
                        alert("请选择供应商！")
                    }
                } else{
                    $("#pl_modal1").modal();
                }
            },

            //按查询条件checkbox
            firstCheck:function(){
                if($(".tiaojian").prop("checked")){
                    this.checkID=[];
                    $("input[name='checkbox']").prop("disabled", true);
                    $("input[name='checkbox']").prop("checkID", true);
                    $(".checkall").prop("disabled", true);
                    $("#yijianxiugai").prop("disabled", false);
                }else{
                    $("#yijianxiugai").prop("disabled", true);
                    $("input[name='checkbox']").prop("disabled", false);
                    $("input[name='checkbox']").prop("checkID", false);
                    $(".checkall").prop("disabled", false);
                }
            }
        }
    });
    //查询面值
    function mz(businessType,operator){
        $.ajax({
            url: "${pageContext.request.contextPath}/store/getPositionCard.do",
            type:'POST',
            async:false,
            data:{"storeType":businessType,"operator":operator},
            dataType:"json",
            error:function(){
                alert("错误");
            },
            success: function(data){
                vm.optionMz = data;
            }
        });
    }
    var gongyingshang="";

    //获取供应商
    function getSupplier(){
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getsupplierandchannles",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                vm.xzgys=data;
                gongyingshang=data;
                vm.supplier=data;
            }
        });
    }
    function qudaoleixingxiugai(a){
        if(a==3){
            $("#operatorYld").hide();
            $("#videoVip").show();
            $("#provinceAll").hide();
//            $(".checkM").prop("checked",true).attr("disabled","disabled");
//            vm.checkk();
        }else{
            $("#operatorYld").show();
            $("#videoVip").hide();
            $("#provinceAll").show();
//            $(".checkM").prop("checked",false).removeAttr("disabled");
        }
        var shuzu=[];
        $(gongyingshang).each(function (i){
            if(gongyingshang[i].businessType==a){
                shuzu.push(gongyingshang[i]);
                vm.xzgys=shuzu;
                vm.form.gys = 0;
                vm.form.yys=0;
            }
        });
    }


    var huoqushangpinbianma="";
    //获取商品编码
    function getproductcode(){
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getproduct",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                huoqushangpinbianma=data;
            }
        });
    }
    var huoqushenfeng="";
    //获取省份
    function getSupplierprovince(){
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getprovince",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                vm.xzsf=data;
                huoqushenfeng=data;
            }
        });
    }

    //增加渠道
    function addChannels(){
        var userId=$("#userId").val();
        var channelnam="";
//        alert($(".IptCon>input").val())
        if(vm.form.qdjc==""){
            layer.msg("渠道简称不能为空");
            return;
        }else{
            var reg =/^[\u4e00-\u9fa5]{2,8}$/;
            channelname =vm.form.qdjc;
            if (!reg.test(channelname)) {
                layer.msg("渠道简称请输入2到8位汉字");
                return ;
            }
        }
        if($(".IptCon>input").val()==""){
            inds=0;
        }
        if(inds==0){
            layer.msg("请选择供应商");
            return;
        }

        var operator=vm.form.yys;
        if(operator==0){
            layer.msg("请选择运营商");
            return;
        }

        var channeltype=vm.form.qdlx;
        if(channeltype==""){
            layer.msg("渠道类型不能为空");
            return;
        }
        var producttype;//流量产品类型
        var product; //渠道产品

        if(channeltype==1){

            var province=vm.form.sf.toString()+",";
//        alert(province);
            if(province==","){
                layer.msg("请选择省份");
                return;
            }

            producttype =vm.form.lllx;
            if(producttype==""){
                layer.msg("请选择流量渠道产品类型");
                return;
            }
            var a=vm.form.ll.length;

            if(a==0){
                layer.msg("请选择渠道产品");
                return;
            }else{
                product=vm.form.ll.toString()+",";
            }

        }else  if(channeltype==2){


            var province=vm.form.sf.toString()+",";
//        alert(province);
            if(province==","){
                layer.msg("请选择省份");
                return;
            }

            producttype =3;
            var b=vm.form.hf.length;

            if(b==0){
                layer.msg("请选择话费渠道产品");
                return;
            }else{
                product=vm.form.hf.toString()+",";
            }
        }else  if(channeltype==3){
            var province=32+",";

            producttype =4;
            var c=vm.form.hy.length;

            if(c==0){
                layer.msg("请选择视频会员渠道产品");
                return;
            }else{
                product=vm.form.hy.toString()+",";
            }
        }

        var discount=vm.form.zk;
        if(discount==""){
            layer.msg("请输入折扣价");
            return;
        }else{
            var rex = /^\d{1}[.]\d{0,4}$|^\d{1}$/;
            if (!rex.test(discount)) {
                layer.msg("折扣应该是大于0小于2的小数");
                return ;
            }
        }

        var splitRatio=vm.form.flb;//分流比
        if(splitRatio==""){
            layer.msg("分流比不能为空");
            return;
        }else {
            var reg =/^([1-9]\d?|100)$/ ;
            if (!reg.test(splitRatio)) {
                layer.msg("分流比:请输入1~100任意整数");
                return ;
            }
        }
        var state=vm.form.zt;
        if(state==0){
            layer.msg("请选择通道状态");
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/insertChannels",
            type:'POST',
            dataType:"text",
            async:false,
            data:{"channelName":channelname,"supplier":inds,"operator":operator,"channelType":channeltype,
                "province":province,"productType":producttype,"product":product,"discount":discount,
                "splitRatio":splitRatio,"state":state,"userId":userId
            },
            success: function(data){
                alert(data);
                location.reload();
            }
        });
    }

    //渠道查询
    function getChannelsDetail(pageNums,pageAll){
//        alert(inds);
        if(pageNums==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        if(inds==null){
            inds=0;
        }
        if($(".IptCon>input").val()==""){
            inds=0;
        }
        var pageNumsint=parseInt(pageNums);
        var pageAllint=parseInt(pageAll);
        if(pageNumsint>pageAllint){
            layer.msg("当前是最后一页啦，没有下一页！");
            return;
        }
        var pageNum=pageNums;
        var lx=vm.chaxun.lx;
        var yys=vm.chaxun.yys;
        var sf=vm.chaxun.sf.toString()+",";
        var jc=vm.chaxun.jc;
        var zt=vm.chaxun.zt;
        var gys=inds;
        var parvalue=vm.cx.mz.toString();
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getChannelDetail",
            async:false,
            type:'POST',
            data:{"lx":lx,"yys":yys,"sf":sf,"jc":jc,"zt":zt,"pageNum":pageNum,"gys":gys,"parvalue":parvalue},
            dataType:"json",
            beforeSend: function () {
                layer.load();
            },
            success: function(data){
                layer.closeAll('loading');
                var obj=data.list;

                $(obj).each(function (index) {
                    //匹配供应商
                    $(gongyingshang).each(function (i){
                        if(data.list[index].supplyId==gongyingshang[i].id){
                            data.list[index].supplyId=gongyingshang[i].name;
                        }
                    });
                    //匹配省份
                    $(huoqushenfeng).each(function (j){
                        if(data.list[index].provinceId==huoqushenfeng[j].id){
                            data.list[index].provinceId=huoqushenfeng[j].value;
                        }

                    });

                    //匹配商品
                    $(huoqushangpinbianma).each(function (k){
                        if(data.list[index].positionCode==huoqushangpinbianma[k].code){
                            data.list[index].positionCode=huoqushangpinbianma[k].packageSize;
                        }
                    });


                    if(data.list[index].businessType==1){
                        data.list[index].businessType="流量";
                    }
                    if(data.list[index].businessType==2){
                        data.list[index].businessType="话费";
                    }
                    if(data.list[index].businessType==3){
                        data.list[index].businessType="视频会员";
                    }
                    if(data.list[index].operatorId==1){
                        data.list[index].operatorId="移动";
                    }
                    if(data.list[index].operatorId==2){
                        data.list[index].operatorId="联通";
                    }
                    if(data.list[index].operatorId==3){
                        data.list[index].operatorId="电信";
                    }
                    if(data.list[index].operatorId==4){
                        data.list[index].operatorId="优酷";
                    }
                    if(data.list[index].operatorId==5){
                        data.list[index].operatorId="爱奇艺";
                    }
                    if(data.list[index].operatorId==6){
                        data.list[index].operatorId="腾讯";
                    }
                    if(data.list[index].operatorId==7){
                        data.list[index].operatorId="搜狐";
                    }
                    if(data.list[index].operatorId==8){
                        data.list[index].operatorId="乐视";
                    }
                    if(data.list[index].status==1){
                        data.list[index].status="开启";
                    }
                    if(data.list[index].status==2){
                        data.list[index].status="测试中";
                    }
                    if(data.list[index].status==3){
                        data.list[index].status="关闭";
                    }

                })
                vm.json = obj;
                vm.page.ts=data.total;
                vm.page.dq=data.pageNum;
                vm.page.all=data.pages;
                if(data.list=="" ){
                    $("#tbody1").hide();
                    $(".sj").show();
                    vm.chaxun.lx="0";
                    vm.chaxun.zt="0";
                    vm.chaxun.yys="0";
//                    vm.chaxun.gys="0";
//                    vm.chaxun.name="";
                }else{
                    $("#tbody1").show();
                    $(".sj").hide();
                }
            }
        });
    }


    //编辑
    function bianjia(){
        var userId=$("#userId").val();
        var id=$("#channelsid").val();
        var formzk=$("#formzk").val();
       var chooseStatus = $("#chooseStatus").val();
        if(formzk==""){
            layer.msg("折扣价不能为空");
            return;
        }else{
            var reg =/^\d{1}[.]\d{0,4}$|^\d{1}$/;
            if (!reg.test(formzk)) {
                layer.tips("折扣应该是大于0小于2的小数", "#bjzhekouspan", 1);
                return ;
            }
        }
        var formflb=$("#formflb").val();
        if(formflb==""){
            layer.msg("分流比不能为空");
            return;
        }else{
            var reg =/^([1-9]\d?|100)$/ ;
            if (!reg.test(formflb)) {
                layer.tips("分流比:请输入1~100任意整数", "#bjfenliubispan", 1);
                return ;
            }
        }
        if(chooseStatus==0){
            layer.msg("请选择状态");
            return;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/updateChannel",
            async:false,
            type:'POST',
            data:{"id":id,"weight":formflb,"costDiscount":formzk,"userId":userId,"status":chooseStatus},
            dataType:"json",
            success: function(data){
                if(data==1){
                    layer.msg("修改成功了~~~~~~~~~~~~~");
                    location.reload();
                }else{
                    layer.msg("修改失败了~~~~~~~~~~~~~");
                }
            }
        });
    }
    //跳页
    function gotoPage(){
        var pagenum = $("#goto-page-num").val();
        if(pagenum==""){
            layer.msg("输入跳转页数");
            return;
        }else {
            getChannelsDetail(pagenum);
        }
    }
    //名称校验
    function  tongdaomingcheng(date){
        var reg =/^[\u4e00-\u9fa5]{2,8}$/;
        if(date=="") {
            $("#qudaomingcheng").text("");
            return;
        }else {
            if (!reg.test(date)) {
                $("#qudaomingcheng").text("请输入2到8位汉字").css({
                    "color":"red",
                    "margin-left":"15px",
                    "line-height":"35px"
                });
                return ;
            }else{

                $("#qudaomingcheng").text("输入正确").css({
                    "color":"green",
                    "margin-left":"15px",
                    "line-height":"35px"
                });
            }
        }
    }
    //分流比
    function fenliubi(date){
        var reg =/^([1-9]\d?|100)$/ ;

        if(date=="") {
            $("#iszhengshu").text("");
            return;
        }else {
            if (!reg.test(date)) {
                /!*  layer.tips("请输入1~100任意整数", "#iszhengshu", 1);*!/
                $("#iszhengshu").text("请输入1~100任意整数").css({
                    "color":"red",
                    "margin-left":"15px",
                    "line-height":"35px"
                });
            }else{
                $("#iszhengshu").text("输入正确").css({
                    "color":"green",
                    "margin-left":"15px",
                    "line-height":"35px"
                });
            }
        }
    }
    //折扣校验
    function zhekou(date){
        var reg =/^\d{1}[.]\d{0,4}$|^\d{1}$/;
        if(date=="") {
            $("#iszhengshu").text("");
            return;
        }else {
            if (!reg.test(date)) {
                $("#zhekous").text("折扣应该是大于0小于2的小数").css({
                    "color":"red",
                    "margin-left":"15px",
                    "line-height":"35px"
                });
                /!* layer.tips("折扣应该是大于0小于2的小数", "#zhekous", 1);*!/
                return ;
            }else{
                $("#zhekous").text("输入正确").css({
                    "color":"green",
                    "margin-left":"15px",
                    "line-height":"35px"
                });
            }
        }
    }
    //在增加渠道的时候 每当切换供应商 运营商就会清空
    function qiehuan(){
        vm.form.yys=0;
        vm.huafei=[];
        vm.liuliang=[];
        vm.member=[];
    }

    //批量修改
    function piliangxiugaitijiao(){
        var userId=$("#userId").val();
        var checkID=vm.checkID.toString()+",";
        var discount=$("#piliangzhekou").val();
        var chooseStatusTwo=$("#chooseStatusTwo").val();
        if(discount=="") {
            layer.msg("输入修改折扣");
            return ;
        }else{
            var rex = /^\d{1}[.]\d{0,4}$|^\d{1}$/;
            if (!rex.test(discount)) {
                layer.msg("折扣应该是大于0小于2的小数");
                return ;
            }
        }
        var weith=$("#piliangfenliubi").val();
        if(weith=="") {
            layer.msg("输入分流比");
            return ;
        }else{
            var reg =/^([1-9]\d?|100)$/ ;
            if (!reg.test(weith)) {
                layer.msg("输入1~100任意数的分流比");
                return ;
            }
        }
        if(chooseStatusTwo==0) {
            layer.msg("请选择状态！");
            return ;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/updatePageChannel",
            async:false,
            type:'POST',
            data:{"checkID":checkID,"discount":discount,"weith":weith ,"userId":userId,"status":chooseStatusTwo},
            dataType:"json",
            error:function(){
                alert("修改失败,重新修改!!");
            },
            success: function(data){
                if(data==0){
                    layer.msg("修改失败");

                }else{
                    layer.msg("修改成功");
                    location.reload();
                }
            }
        });
    }
    //一键修改
    function piliangyijianxiugaitijiao(){
        var userId=$("#userId").val();
        var chooseStatusThree=$("#chooseStatusThree").val();
        var channelName=vm.chaxun.jc;
        if(channelName==""){
            channelName=null;
        }
        var businessType=vm.chaxun.lx;
        var supplyId=inds;
        var operatorId=vm.chaxun.yys;
        var provinceId=vm.chaxun.sf.toString()+",";
        var status=vm.chaxun.zt;
        var parvalue=vm.cx.mz.toString();
        if(vm.chaxun.yys == "0"){
            alert("请选择运营商！")
        }else if (vm.chaxun.lx == "0"){
            alert("请选择类型！")
        }else if (inds == 0){
            alert("请选择供应商！")
        }
        var discount=$("#piliangyijianzhekou").val();
        if(discount=="") {
            layer.msg("输入修改折扣");
            return ;
        }else{
            var rex = /^\d{1}[.]\d{0,4}$|^\d{1}$/;
            if (!rex.test(discount)) {
                layer.msg("折扣应该是大于0小于2的小数");
                return ;
            }
        }
        var weith=$("#piliangyijianfenliubi").val();
        if(weith=="") {
            layer.msg("输入分流比");
            return ;
        }else{
            var reg =/^([1-9]\d?|100)$/ ;
            if (!reg.test(weith)) {
                layer.msg("输入1~100任意数的分流比");
                return ;
            }
        }
        if(chooseStatusThree==0) {
            layer.msg("请选择状态");
            return ;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/updateAllChannel",
            async:false,
            type:'POST',
            data:{"channelName":channelName,"discount":discount,"weith":weith,
                "businessType":businessType,"supplyId":supplyId,"operatorId":operatorId,
                "provinceId":provinceId,"status":status,"userId":userId,"parvalue":parvalue,"chooseStatusThree":chooseStatusThree},
            dataType:"json",
            error:function(){
                alert("修改失败,重新修改!!");
            },
            success: function(data){
                if(data==0){
                    layer.msg("因输入条件有误修改失败");
                }else{
                    layer.msg("修改成功");
                    location.reload();
                    $("#piliangyijianfenliubi").val("");
                    $("#piliangyijianzhekou").val("");
                }
            }
        });
    }

</script>
</body>
</html>

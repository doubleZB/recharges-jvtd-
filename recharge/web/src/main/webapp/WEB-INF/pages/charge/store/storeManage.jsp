<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/huojiaguanli.css">
</head>
<style>
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
        margin-left:3px;
    }
    .IptCon > input{
        width:100%;
        height:33px;
        border:1px solid #ccc;
    }
    .posi{
        width:200px;
        position: absolute;
        padding: 0;
        left:0;
        top:14px;
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
        list-style: none;
        padding:0;
        margin:0;
    }
    .posi li span{
        display: block;
        width:100%;
        height:100%;
    }
    .posiNode li{
        line-height: 35px;
        cursor: pointer;
        list-style: none;
        padding:0;
        margin:0;
    }
    .posiNode{
        width:200px;
        position: absolute;
        padding: 0;
        left:0;
        top:14px;
        display: none;
        border:1px solid #ccc;
        background: #fff;
        height: auto;
        max-height: 140px;
        overflow-y: scroll;
        background: #fff;
        z-index: 100;
    }
    .qwe{
        display: block;
        width:100%;
        height:100%;
    }
    .lcy_wer{
        width: 200px;
    }
    .lcy_list{
        list-style: none;
        margin-top:-10px;
    }
    .lcy_list>li, .lcy_list1>li{
        line-height: 30px;
    }
    /* 弹窗里面的下拉选项 */
    .lcy_list1{
        background: #fff;
        list-style: none;
        margin-top: -10px;
        text-align: left;
        /*margin: 0 auto;*/
        padding-left: 10px;
    }

</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">货架管理</a></li>
                <li><a href="#tab2">新增</a></li>
                <input type="hidden" value="{{cx|json}}" id="toFindListData">
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv" style="margin-top: 0px;">
                <div class="am-tab-panel am-active" id="tab1">
                    <div class="am-form-inline" role="form">
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" id="hjlx" style="width: 200px;float: left;" v-model="cx.lx" @change="clearmz()">
                                <option value="1" selected>流量</option>
                                <option value="2">话费</option>
                                <option value="3">视频会员</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" id="hjyys" v-model="cx.yys" style="width: 200px;float: left;">
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
                            <input type="text"  class="am-form-field" id="cxsf" placeholder="选择省份"
                                   style="width: 200px;float: left;" @focus="opensf()">
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <input type="text"  class="am-form-field" id="xzmz" placeholder="面值颗粒"
                                   style="width: 200px;float: left;" @focus="openmz()">
                        </div>
                        <%--<div class="am-form-group" style="margin-top: 0;">
                            <input type="text"  class="am-form-field" id="zk_start" placeholder="折扣起始"
                                   style="width: 100px;float: left;" >
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <input type="text"  class="am-form-field" id="zk_end" placeholder="折扣结束"
                                   style="width: 95px;float: left;" >
                        </div>--%>

                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" id="zk_type"  style="width: 106px;float: left;">
                                <option value="1">折扣等于</option>
                                <option value="2">折扣大于等于</option>
                                <option value="3">折扣大于</option>
                                <option value="4">折扣小于等于</option>
                                <option value="5">折扣小于</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <input type="number"   min="0" max="2"  class="am-form-field" id="zk_num"
                                   style="width: 90px;float: left;" >
                        </div>
                        <div class="clearfix"></div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" id="select1" v-model="cx.yz" onchange="displayGYS()" style="width: 200px;float:left">
                                <%--<option value="0">渠道匹配原则</option>--%>
                                <option value="2">系统匹配</option>
                                <option value="1">指定渠道</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;width: 200px;" id="spl">
                            <%--<select class="select1" id="supplierName" style="width: 200px;float: left;">--%>
                            <%--<option value="0">渠道供应商</option>--%>
                            <%--</select>--%>
                            <div style="position: relative;float:left;" class="IptCon">
                                <input type="text" style="width: 200px;float: left;" class="searchIpt"  oninput="getkeylist(this.value)"   id="supplierNameId" placeholder="渠道供应商" value="">
                                <ul class="posiNode">
                                </ul>
                            </div>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" v-model="cx.zt" id="chaxunzhuangtai" style="width: 200px;float: left;">
                                <option value="0" selected>状态</option>
                                <option value="1">上架</option>
                                <option value="2">下架</option>
                                <option value="3">永久下架</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <input v-model="cx.hjmc" type="text" class="am-form-field" placeholder="货架名称"
                                   style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <input v-model="cx.hjid" type="text" class="am-form-field" placeholder="货架id"
                                   style="width: 200px;float: left;">
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <input v-model="cx.qdmc"  type="hidden" class="am-form-field" placeholder="渠道名称"
                                   style="width: 200px;float: left;">
                        </div>
                        <button class="am-btn am-btn-warning"
                                style="width: 120px;border-radius: 5px;" @click="toFindListData(1)">查询
                        </button>
                    </div>
                    <div class="am-u-sm-12 am-u-md-8" style="padding-left: 0;margin-top: 10px;">
                        <button type="button" class="am-btn am-btn-warning" @click="openOne"
                                style="width: 120px;margin: auto;border-radius: 5px;">修改状态
                        </button>
                        <button type="button" class="am-btn am-btn-warning" @click="openTwo"
                                style="width: 120px;margin: auto;border-radius: 5px;">修改折扣
                        </button>
                        <button type="button" class="am-btn am-btn-warning" @click="openThree"
                                style="width: 120px;margin: auto;border-radius: 5px;">修改渠道
                        </button>
                        <input type="checkbox" id="one_key">按条件一键修改
                        <input type="hidden" id="editDataSubData" value="{{ checkID | json }}">
                    </div>
                    <div style="width:100%;">
                        <table class="am-table am-table-striped am-table-hover">
                            <thead>
                            <tr>
                                <th>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" class="checkall" @click="checked"> 序号
                                    </label>
                                </th>
                                <th>货架ID</th>
                                <th>货架组名称</th>
                                <th>商品类型</th>
                                <th>运营商</th>
                                <th>省份</th>
                                <th>产品</th>
                                <th>面值</th>
                                <th>生效范围</th>
                                <th>价格折扣</th>
                                <th>渠道匹配原则</th>
                                <th>指定供应商</th>
                                <th>状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="item in json">
                                <td>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" value="{{item.id}}" name="checkbox" v-model="checkID" v-if="item.status !='永久下架'" @click="checked1()">
                                        {{$index+1}}
                                    </label>
                                </td>
                                <td>{{item.id}}</td>
                                <td>{{item.groupName}}</td>
                                <td class="ggg">{{item.productTyoe}}</td>
                                <td>{{item.yys}}</td>
                                <td>{{item.privanceName}}</td>
                                <td>{{item.flowSize}}</td>
                                <td>{{item.amount}}元</td>
                                <td>{{item.activeScope}}</td>
                                <td>{{item.zk}}</td>
                                <td>{{item.sendType}}</td>
                                <%--<td><a href="javascript:void(0);" v-if="item.sendType == '指定渠道'" onclick="watchSupply('{{item.id}}','{{item.sendType}}')">查看</a></td>--%>
                                <td><span  v-if="item.sendType == '指定渠道'" >{{item.supply_name}}</span></td>
                                <td v-if="item.status =='上架'" style="color: green;">{{item.status}}</td>
                                <td v-if="item.status =='下架'" style="color: #ffc41d;">{{item.status}}</td>
                                <td v-if="item.status =='永久下架'" style="color: red;">{{item.status}}</td>
                            </tr>
                            <tr id="noDataToShow"><td colspan="12"><span  style="color: red;font-size: 18px;">查无数据</span></td></tr>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                    <div class="am-cf">
                        每页显示
                        <select id="pageNumSet" onchange="reloadPage(1)">
                            <option value="10">10</option>
                            <option value="30">30</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                            <option value="300">300</option>
                            <option value="500">500</option>
                            <option value="1000">1000</option>
                        </select>
                        条&nbsp;&nbsp;&nbsp;&nbsp;
                        共 <span>{{page.all}}</span> 页 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a  href="javascript:void(0)" onclick="reloadPage({{page.dq-1}})">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                <li><a href="javascript:void(0)" onclick="reloadPage({{page.dq+1}})">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();" style="padding: .5rem .4rem;">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal">
                        <div v-show="nff">
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">货架组名称：</label>
                                <%--<select class="am-form-field" id="xzhjz" style="width: 300px;float: left;" v-model="form.xhjz">--%>
                                <%--<option v-for="option in hjzmc" :value="option.id">{{option.name}}</option>--%>
                                <%--</select>--%>
                                <div style="position: relative;float:left;">
                                    <div class="IptCon IptContIpt"  style="width:300px;">
                                        <div style="position:absolute;width:300px;margin-top:-20px;" id="xzhjz"></div>
                                        <input type="text" v-model="searchQuery" value="" class="searchIpt" placeholder="货架组名称" style="width: 300px;">
                                    </div>
                                    <div class="posi" style="padding: 0;width: 300px;top:33px;left:3px;">
                                        <simple-grid :data-list="hjzmc" :columns="columns" :search-key="searchQuery">
                                        </simple-grid>
                                    </div>
                                </div>
                                <template  id="grid-template">
                                    <ul style="padding: 0;margin: 0;">
                                        <li v-for="(index,entry) in dataList | filterBy searchKey">
                                            <span v-for="col in columns" value="{{entry[col.ind]}}">{{entry[col.name]}}</span>
                                        </li>
                                    </ul>
                                </template>
                            </div>
                            <div class="am-form-group">
                                <template v-if="choose">
                                    <div class="iframe1"
                                         style="width: 650px;max-height:321px;margin-left: 120px;overflow: auto;">
                                        <table class="am-table am-table-striped am-table-hover am-table-bordered"
                                               style="margin-bottom: 0;">
                                            <thead>
                                            <tr>
                                                <th>
                                                    <label class="am-checkbox-inline">
                                                        <input type="checkbox" class="tableall" checked="checked" @click="tablecheck()">ID
                                                    </label>
                                                </th>
                                                <th>商品类型</th>
                                                <th>运营商</th>
                                                <th>省份</th>
                                                <th>流量包</th>
                                                <th>面值</th>
                                                <th>生效范围</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="item in mytable">
                                                <td>
                                                    <label class="am-checkbox-inline">
                                                        <input type="checkbox" value="{{item.id}}" checked="checked" name="table"
                                                               v-model="form.mytable" @click="tablecheck1()">
                                                        {{item.id}}
                                                    </label>
                                                </td>
                                                <td>{{item.businessType}}</td>
                                                <td>{{item.operator}}</td>
                                                <td>{{item.province}}</td>
                                                <td>{{item.flowPackageSize}}</td>
                                                <td>{{item.amount}}元</td>
                                                <td>{{item.scope}}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </template>
                            </div>
                            <div class="am-form-group">
                                <a href="######" class="chose"
                                   data-am-modal="{target: '#modal', closeViaDimmer: 0, width: 750}" id="choseProduct">选择商品</a><span id="num" style="margin-left: 10px;color: #a4a4a4;display: none;">所选商品数量 <strong id="number"></strong></span><br>

                                <input type="hidden" id="selectData" value="{{form | json}}">
                            </div>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">折扣价格：</label>
                                <input v-model="form.zk" type="text" class="am-form-field" id="isSmallNum"
                                       style="width: 120px;float: left;" @keyup="isSmallNum()" ><span
                                    style="line-height:35px;color: #a4a4a4;margin-left: 10px;">允许四位小数</span>
                            </div>
                            <%--<div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">选择渠道：</label>
                                <label class="am-radio-inline">
                                    <input type="radio" value="2" checked="checked" name="docInlineRadio1" v-model="form.xzqd"
                                           @click="radioCheck(1)"> 系统自动匹配
                                </label>
                                <label class="am-radio-inline">
                                    <input type="radio" value="1"  name="docInlineRadio1" id="xzgysqd" v-model="form.xzqd"
                                           @click="radioCheck(2)"> 指定渠道
                                </label>
                                <template v-if="ngys">
                                    <div class="checklist">
                                        <div class="am-checkbox" v-for="item in gys">
                                            <label>
                                                <input type="checkbox" value="{{item.id}}" v-model="form.xzgys">
                                                {{item.name}}
                                            </label>
                                        </div>
                                    </div>
                                </template>
                            </div>--%>
                            <div class="clearfix"></div>
                            <div class="am-form-group" style="margin-top: 0;">
                                <select class="select1" id="select2" v-model="cx_yz" onchange="displayGYS()" @change="displayGYS" style="width: 200px;margin-left: 119px;">
                                    <option value="2">系统匹配</option>
                                    <option value="1">指定渠道</option>
                                </select>
                            </div>

                            <%-- <div class="am-form-group" style="margin-top: 0;width: 200px;" id="spl">
                                 <div style="position: relative;float:left;" class="IptCon">
                                     <input type="text" style="width: 200px;float: left;" class="searchIpt" oninput="getkeylist(this.value)" id="supplierNameId" placeholder="渠道供应商" value="">
                                       <ul class="posiNode" style="display: none;" >
                                           <li  v-for="item in gys" value="{{item.id}}" v-model="form.xzgys">{{item.name}}</li>
                                       </ul>
                                 </div>
                             </div>--%>

                            <div v-show="lcyInShow" class="lcy_wer"  style="margin-left: 119px;">
                                <input type="text" data-val="lcySearch" @keyup="ching" v-model='lcySearch'/>
                                <ul style="padding:20px;margin: 0;" class="lcy_list" v-show="lcyShow">
                                    <%--<li  v-for="item in LcyRandom" value="{{item.id}}" v-model="form.xzgys" @click="lcyTop(item.name,item.id)">{{item.name}}</li>--%>
                                    <li  v-for="item in LcyRandom"  @click="lcyTop(item.name,item.id)">{{item.name}}</li>
                                </ul>
                            </div>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">选择状态：</label>
                                <select style="width: 300px;float: left;" v-model="form.xzzt">
                                    <option value="1">上架</option>
                                    <option value="2">下架</option>
                                </select>
                            </div>
                            <div class="am-form-group">
                                <div class="am-u-sm-2 am-u-sm-offset-1">
                                    <button type="button" class="am-btn am-btn-warning"
                                            style="width: 120px;margin:20px auto; border-radius: 5px;margin-left: 30px;" @click="checkToSubmit()">提交
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!--弹出框-->
    <my-component></my-component>
    <!--新增选择弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">商品类型：</label>
                        <select class="am-form-field" style="width: 300px;float: left;" v-model="form2.splx" @change="splx()">
                            <option value="1" selected>流量</option>
                            <option value="2">话费</option>
                            <option value="3">视频会员</option>
                        </select>
                    </div>
                    <div class="am-form-group" id="operatorYLD">
                        <label class="am-u-sm-2 am-form-label">运营商：</label>
                        <select class="am-form-field" style="width: 300px;float: left;" @change="switchLl()" v-model="form2.yys">
                            <option value="1" selected>移动</option>
                            <option value="2">联通</option>
                            <option value="3">电信</option>
                        </select>
                    </div>
                    <div class="am-form-group" style="display: none" id="videoVip">
                        <label class="am-u-sm-2 am-form-label">运营商：</label>
                        <select class="am-form-field" style="width: 300px;float: left;" @change="switchLl()" v-model="form2.yys">
                            <option value="4">优酷</option>
                            <option value="5">爱奇艺</option>
                            <option value="6">腾讯</option>
                            <option value="7">搜狐</option>
                            <option value="8">乐视</option>
                            <option value="9">PPTV</option>
                        </select>
                    </div>
                    <div class="am-form-group" id="shengfen">
                        <label class="am-u-sm-2 am-form-label">省份：</label>
                        <div class="am-checkbox" style="display: block;float: left;">
                            <label>
                                <input type="checkbox" class="xzsf" @click="qxsf()"> 全部选择<br>
                            </label>
                        </div>
                        <div style="clear: both;"></div>
                        <div class="checklist">
                            <div class="am-checkbox" v-for="s in xsf">
                                <label>
                                    <input type="checkbox" name="sf" value="{{s.key}}" v-model="form2.yxsf"
                                           @click="sfcheck()">{{s.value}}
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="am-form-group" id="chanpin" style="display: none">
                        <label class="am-u-sm-2 am-form-label">产品：</label>
                        <div class="am-checkbox" style="display: block;float: left;">
                            <label>
                                <input type="checkbox" class="xzcp" @click="qxcp()"> 全部选择<br>
                            </label>
                        </div>
                        <div style="clear: both;"></div>
                        <div class="checklist">
                            <div class="am-checkbox" v-for="item in member">
                                <label>
                                    <input type="checkbox" name="cp" value="{{item.code}}" v-model="form2.yxcp"
                                           @click="cpcheck()">{{item.packageSize}}
                                </label>
                            </div>
                        </div>
                    </div>
                    <template v-if="liu">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">流量大小：</label>
                            <div class="am-checkbox" style="display: block;float: left;">
                                <label>
                                    <input type="checkbox" class="liucheck" @click="qxll()"> 全部选择
                                </label>
                            </div>
                            <div style="clear: both;"></div>
                            <div class="checklist">
                                <div class="am-checkbox" v-for="item in liuliang">
                                    <label>
                                        <input type="checkbox" name="ll" value="{{item.code}}" v-model="form2.yxll"
                                               @click="llcheck()">{{item.packageSize}}({{item.amount}}元)
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="am-form-group" id="fanwei">
                            <label class="am-u-sm-2 am-form-label">生效范围：</label>
                            <div style="float: left;">
                                <label class="am-radio-inline">
                                    <input type="radio" value="1" v-model="form2.sxfw" name="docInlineRadio" checked="checked"> 全国
                                </label>
                                <label class="am-radio-inline">
                                    <input type="radio" value="2" v-model="form2.sxfw" name="docInlineRadio"> 省内
                                </label>
                            </div>
                        </div>
                    </template>
                    <template v-else>
                        <div class="am-form-group" id="mianzhi">
                            <label class="am-u-sm-2 am-form-label">面值：</label>
                            <div class="am-checkbox" style="display: block;float: left;">
                                <label>
                                    <input type="checkbox" class="huacheck" @click="qxhf ()"> 全部选择
                                </label>
                            </div>
                            <div style="clear: both;"></div>
                            <div class="checklist">
                                <div class="am-checkbox" v-for="item in huafei">
                                    <label>
                                        <input type="checkbox" name="hf" value="{{item.code}}" v-model="form2.yxhf"
                                               @click="hfcheck()">{{item.packageSize}}
                                    </label>
                                </div>
                            </div>
                        </div>
                    </template>
                    <input type="hidden" id="subData" value="{{form2 | json}}">
                </form>
            </div>
            <hr>
            <div class="am-form-group">
                <div>
                    <button type="button" class="am-btn am-btn-warning"
                            style="width: 120px;margin: auto;" @click="sure()">提交
                    </button>
                </div>
            </div>
        </div>
    </div>
    <!--查看供应商按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="listModal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">供应商
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr style="margin-bottom: 0;">
            <div class="am-modal-bd" id="ckgystb">
                <table class="am-table am-table-striped"  style="margin-bottom: 0;">
                    <thead>
                    <tr><th>供应商ID</th><th>供应商类型</th><th>供应商名称</th></tr>
                    </thead>
                    <tbody>
                    <tr><td>供应商ID</td><td>供应商类型</td><td>供应商名称</td></tr>
                    </tbody>
                </table>
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
                            <div class="am-checkbox" v-for="s in options2">
                                <label>
                                    <input type="checkbox" name="cxsf" value="{{s.key}}" v-model="cx.sf"
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
                        <label class="am-u-sm-2 am-form-label">省份：</label>
                        <div class="am-checkbox" style="display: block;float: left;">
                            <label>
                                <input type="checkbox" class="cxmz" @click="cxmzqx()"> 全部选择<br>
                            </label>
                        </div>
                        <div style="clear: both;"></div>
                        <div class="checklist2">
                            <div class="am-checkbox" v-for="s in optionMz">
                                <label>
                                    <input type="checkbox" name="cxmz" value="{{s.code}}" v-model="cx.mz"
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
<!--或加管理页面三个按钮弹层-->
<template id="myTemplate">
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close @click="jdf2()">&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div v-show="show1" id="show1">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">选择状态：</label>
                            <select style="width: 300px;float: left;" id="editStatus">
                                <option value="0">选择状态</option>
                                <option value="2">下架</option>
                                <option value="1">上架</option>
                                <option value="3">永久下架</option>
                            </select>
                        </div>
                    </div>
                    <div v-show="show2"  id="show2">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">修改折扣：</label>
                            <input type="text" class="am-form-field" id="editZk" placeholder="修改折扣"
                                   style="width: 300px;float: left;">
                        </div>
                    </div>
                    <div v-show="show3"  id="show3">
                        <div class="am-form-group">
                            <label class="am-radio-inline"><strong>渠道匹配原则：</strong></label>
                            <label class="am-radio-inline">
                                <input type="radio" value="1" id="editgys" class="plradio" name="raio1" v-model="pljson.plradio" @click="plRadioClick(1)"> 指定渠道
                            </label>
                            <label class="am-radio-inline">
                                <input type="radio" value="0" class="plradio" checked name="raio1" v-model="pljson.plradio" @click="plRadioClick(0)"> 系统自动匹配
                            </label>

                        </div>
                        <div v-show="lcyInShow" class="lcy_wer"  style="margin:0 auto">
                            <input v-if="lcyInShow" style="width:160px" type="text" data-val="lcySearch" @keyup="ching" v-model='lcySearch'/>
                            <ul style="width:160px" class="lcy_list1" v-show="lcyShow">
                                <%--<li  v-for="item in LcyRandom" value="{{item.id}}" pid="{{item.id}}" v-model="form.xzgys" @click="lcyTop(item.name,item.id)">{{item.name}}</li>--%>
                                <li  v-for="item in LcyRandom"  pid="{{item.id}}" @click="lcyTop(item.name,item.id)">{{item.name}}</li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <hr>
            <input type="hidden" id="editSendType" value="{{ pljson | json }}">
            <input type="hidden" id="jdf1" value="">

            <div class="am-form-group">
                <div>
                    <input type="hidden" id="editType">
                    <button type="button" class="am-btn am-btn-warning"
                            style="width: 120px;margin: auto;" onclick="editStoreData();">提交
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>
<style>

</style>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script>
    var flog=false;
    $("#supplierNameId").focus(function(){
        $(".posiNode").css("display","block");
    });
    $(".posiNode").on("click",$(".posi li"),function(e){
        flog=true;
        var strNode=e.target.innerHTML.split("li");
        if(strNode.length>2){
            return false;
        };
        e.stopPropagation();
        $("#supplierNameId").val(e.target.innerHTML);
        $("#supplierNameId").attr("value",e.target.getAttribute("value"));
        $(".posiNode").css("display","none");
        inds=e.target.getAttribute("value");
        vm.form.xhjz=inds;
    });


    var inds;
    $(".posi").on("click",$(".posi li span"),function(e){
        var strNode=e.target.innerHTML.split("li");
        if(strNode.length>2){
            return false;
        };
        e.stopPropagation();
        $(".IptContIpt>input").val(e.target.innerHTML);
        $(".IptContIpt>input").attr("value",e.target.getAttribute("value"));
        $(".posi").css("display","none");
        inds=e.target.getAttribute("value");
        vm.form.xhjz=inds;
    });
    $(document).click(function(e){
        e.stopPropagation();
        $(".posi").css("display","none");
        $(".posiNode").css("display","none");
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
    function mz(a,b){
        $.ajax({
            url: "/store/getPositionCard",
            type:'POST',
            async:false,
            data:{"storeType":a,"operator":b},
            dataType:"json",
            error:function(){
                alert("错误");
            },
            success: function(data){
                vm.$set('optionMz',data);
            }
        });
    }
    var arr = [];
    var arr1 = [];
    var arr2 = [];
    var arr3 = [];
    var arr4 = [];
    var arrcp = [];
    var table = [];
    var cxsfarr = [];
    var cxmzarr = [];
    var products =[];
    var pageInfo={
        ts: 0,
        dq: 0,
        all: 0
    };
    var YDll=${flowPositionYD};//移动流量
    var LTll=${flowPositionLT};//联通流量
    var DXll=${flowPositionDX};//电信流量
    var YDhf=${PhonecostPositionYD};//移动话费数据
    var LThf=${PhonecostPositionLT};//联通话费数据
    var DXhf=${PhonecostPositionDX};//电信话费数据
    var Ykhy=${PositionYK};//优酷会员数据
    var Aqyhy=${PositionAQY};//爱奇艺会员数据
    var TXhy=${PositionTX};//腾讯会员数据
    var Shhy=${PositionSH};//搜狐会员数据
    var Lshy=${PositionLS};//乐视会员数据
    var supplies = ${supplies};//所有供应商
    var LLgys = ${suppliesLL};//流量供应商
    var HFgys = ${suppliesHF};//话费供应商
    var HYgys = ${suppliesHY};//所有视频会员供应商
    var pageContent=[];


    var vm = new Vue({
        el: "#content",
        data: {
            supplies:${supplies},
            nff: true,
            liu: true,
            ngys: false,
            pid:"",
            form: {
                xhjz: "",
                mytable: [],
                zk: "",
                xzgys: [],
                xzqd: "",
                xzzt: "1"
            },
            form2: {
                splx: "0",
                yys: "0",
                yxsf: [],
                yxll: [],
                yxcp: [],
                yxhf:[],
                sxfw: ""
            },
            searchQuery: '',
            columns: [{
                name: 'name',
                ind:'id'
            }],
            people: [],
            liuliang: YDll,//流量档位
            huafei:YDhf,//话费档位
            member:[],//视频会员档位
            hjzmc: ${groups},//货架组选择框数据
            xsf: ${dicts},//弹框省份多选
            gys: LLgys,//指定供应商
            xlx: "0",
            choose: false,
            checkID: [],
            tableID: [],
            cx: {
                lx: "1",
                yys: "0",
                sf: [],
                mz: [],
                yz: "1",
                zt: "1",
                hjmc: "",
                qdmc: "",
                hjid:""
            },
            options2: ${dicts},//查询页面省份条件
            optionMz:[],
            mytable:[],
            json: [],
            page: pageInfo,
            LcyRandom:[],
            lcySearch:'',
            lcyShow:true,
            cx_yz:'',
            lcyInShow:false
        },
        ready: function () {

        },
        components: {
            "my-component": {
                template: "#myTemplate",
                data: function () {
                    return {
                        msg: "",
                        ggg:true,
                        show1: true,
                        show2: false,
                        show3: false,
                        plgys:false,
                        list:LLgys,
                        list2: HFgys,
                        pljson:{
                            plradio:"",
                            pll:[],
                            phf:[],
                            plcheck:[]
                        },
                        LcyRandom:[],
                        lcyShow:true,
                        lcyInShow:true,
                        lcySearch:''
                    }
                },
                methods:{
                    jdf2:function(){
                        this.lcyInShow=false;
                    },
                    ching:function(){
                        this.LcyRandom=[];
                        vm.lcySearch="";
                        var _list = vm.supplies;
                        for(var i=0;i<_list.length;i++){
                            if(_list[i].name.indexOf(this.lcySearch) >=0 ){
                                this.LcyRandom.push(_list[i])
                            }
//                            if(this.list2[i].name.indexOf(this.lcySearch) >=0 ){
//                                this.LcyRandom.push(this.list2[i])
//                            }
                            if(this.lcySearch.length > 0){
                                this.lcyShow = true
                            }else{
                                this.lcyShow = false
                            }

                        }
                    },
                    lcyTop:function(va,e){
                        this.lcySearch=va;
                        this.lcyShow=false;
                        vm,lcySearch=e;
                        $("#jdf1").val(e);
                    },
                    plRadioClick:function (a) {
                        if (a == 0) {
//                            alert('自动')
                            this.plgys = false;
                            this.pljson.plradio='0';
                            this.pljson.pll=[];
                            this.pljson.phf=[];
                            this.pljson.plcheck = [];
                            this.lcyInShow=false
                            this.lcySearch=''
                        } else {
//                            alert('指定')
                            this.pljson.plradio='1';
                            this.lcySearch = ''
                            this.lcyInShow = true
                            if($(".ggg").eq(0).text() == "流量"){
                                this.ggg = true;
                            }else{
                                this.ggg = false;
                            }
                            this.plgys = true;
                        }
                    },
                    pllcheck: function() {
                        if ($("input[name='pll']:checked")) {
                            $("input[name='phf']").prop("checked", false);
                            this.pljson.phf=[];
                            this.pljson.plcheck = this.pljson.pll;
                        }
                    },
                    phfcheck: function() {
                        if ($("input[name='phf']:checked")) {
                            $("input[name='pll']").prop("checked", false);
                            this.pljson.pll=[]
                            this.pljson.plcheck = this.pljson.phf;
                        }
                    }
                }
            }
        },
        methods: {

            remove: function (index) {
                this.json.splice(index, 1)
            },
            // 梁程英添加
            ching:function(){
                this.LcyRandom=[];
                $("#jdf1").val("");
                for(var i=0;i<this.gys.length;i++){

                    if(this.gys[i].name.indexOf(this.lcySearch)>=0){
                        this.LcyRandom.push(this.gys[i])
                    }
                    if(this.lcySearch.length>0){
                        this.lcyShow=true
                    }else{
                        this.lcyShow=false
                    }

                }
            },
            lcyTop:function(va,e){
                this.lcySearch=va;
                this.lcyShow=false;
                vm.form.xzgys=e;
            },
            displayGYS:function(){
                console.log(this.cx_yz)
                vm.form.xzqd=this.cx_yz;
                if(this.cx_yz=='1'){
                    this.lcyInShow=true;
                }else{
                    this.lcyInShow=false;
                    this.lcySearch=''
                }
            },
            // 梁程英添加结束
            openOne: function () {
                //alert(1)
                $("#editType").val(1);
                var one_key = $("#one_key").is(":checked");
                if(one_key==true){
                    var chaxunzhuangtai = $("#chaxunzhuangtai").val();
                    if(chaxunzhuangtai==3){
                        layer.msg("永久下架的商品不允许一键上下架！");
                        return;
                    }

                    this.$children[0].msg = "状态修改";
                    /* this.$children[0].show1 = true;
                     this.$children[0].show2 = false;
                     this.$children[0].show3 = false;*/
                    jQuery("#show1").show();
                    jQuery("#show2,#show3").hide();
                    $("#your-modal").modal();
                    return;
                }
                var editDataSubData = $("#editDataSubData").val();
                if(editDataSubData==''||editDataSubData=='[]'){
                    layer.msg("请选择货架");
                    return;
                }
                if(editDataSubData=='[  null]'){
                    layer.msg("没有可修改货架");
                    return;
                }
                this.$children[0].msg = "状态修改";
                //this.$children[0].show1 = true;
                // this.$children[0].show2 = false;
                //this.$children[0].show3 = false;
                jQuery("#show1").show();
                jQuery("#show2,#show3").hide();
                $("#your-modal").modal();
            },
            openTwo: function () {
                //alert(2)
                $("#editType").val(2);
//                vm.$components.lcyInShow = false;
                var one_key = $("#one_key").is(":checked");
                if(one_key==true){
                    this.$children[0].msg = "修改折扣";
                    // this.$children[0].show1 = false;
                    //this.$children[0].show2 = true;
                    // this.$children[0].show3 = false;
                    jQuery("#show2").show();
                    jQuery("#show1,#show3").hide();
                    $("#your-modal").modal();
                    return;
                }
                var editDataSubData = $("#editDataSubData").val();
                if(editDataSubData==''||editDataSubData=='[]'){
                    layer.msg("请选择货架");
                    return;
                }
                if(editDataSubData=='[  null]'){
                    layer.msg("没有可修改货架");
                    return;
                }
                this.$children[0].msg = "修改折扣";
                //  this.$children[0].show1 = false;
                //  this.$children[0].show2 = true;
                //  this.$children[0].show3 = false;
                jQuery("#show2").show();
                jQuery("#show1,#show3").hide();

                $("#your-modal").modal();
            },
            openThree: function () {
                vm.$children[1].plRadioClick(1);
                $('#editgys').prop('checked',true).parent().next().find('.plradio').prop('checked',false);
                //alert(330)
                $("#editType").val(3);
                var one_key = $("#one_key").is(":checked");
                if(one_key==true){
                    layer.msg("修改渠道不支持一键修改！");
                    return;
                }
                var editDataSubData = $("#editDataSubData").val();
                if(editDataSubData==''|| editDataSubData=='[]'){
                    layer.msg("请选择货架");
                    return;
                }
                if(editDataSubData=='[null]'){
                    layer.msg("没有可修改货架");
                    return;
                }
                this.$children[0].msg = "修改渠道";
                /*this.$children[0].show1 = false;
                 this.$children[0].show2 = false;
                 this.$children[0].show3 = true;*/
                 this.$children[0].plgys = false;

//                this.$children[0].pljson.plradio = 0;

//                $("#show3").css('display','block');
//                $("#show2").css('display','none');
//                $("#show1").css('display','none');
//                $("#your-modal").modal();

                jQuery("#show3").show();
                jQuery("#show1,#show2").hide();
                $("#your-modal").modal();
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
            sure: function () {
                //$("#modal").modal("close");//关闭选择弹框
//                this.choose = true;
                if(this.form2.splx==1){//走流量
                    if(this.form2.yxsf.length==0){
                        layer.msg("请勾选省份");
                    }else {
                        if(this.form2.yxll.length==0){
                            layer.msg("请勾选流量大小");
                        }else {
                            vm.$set('form.mytable',[]);
                            $("#modal").modal("close");//关闭选择弹框
                            this.choose = true;
                            var index = layer.open({type:3});
                            $.ajax({
                                url: "/store/findProductions",
                                type:'POST',
                                async:false,
                                data:{"subData":$("#subData").val()},
                                dataType:"json",
                                error:function(){
                                    $(this).addClass("done");
                                },
                                success: function(data){
                                    layer.close(index);
                                    if(data.success){
                                        vm.$set('mytable',data.object);
                                        $("#choseProduct").text("重新选择商品");
                                        $("#num").show();
                                        $("#number").text(vm.mytable.length);
                                    }else {
                                        vm.$set('form.mytable',[]);
                                        layer.msg(data.message);
                                        $("#num").hide();
                                    }
                                }
                            });

                        }
                    }
                }else if(this.form2.splx==2){//走话费
                    if(this.form2.yxsf.length==0){
                        layer.msg("请勾选省份");
                    }else {
                        if(this.form2.yxhf.length==0){
                            layer.msg("请勾选话费面值");
                        }else {
                            vm.$set('form.mytable',[]);
                            $("#modal").modal("close");//关闭选择弹框
                            this.choose = true;
                            var index = layer.open({type:3});
                            $.ajax({
                                url: "/store/findProductions",
                                type:'POST',
                                async:false,
                                data:{"subData":$("#subData").val()},
                                dataType:"json",
                                error:function(){
                                    $(this).addClass("done");
                                },
                                success: function(data){
                                    layer.close(index);
                                    if(data.success){
                                        vm.$set('mytable',data.object);
                                        $("#number").text(vm.mytable.length);
                                    }else {
                                        vm.$set('form.mytable',[]);
                                        layer.msg(data.message);
                                    }
                                }
                            });

                        }
                    }
                }else{
                    if(this.form2.yxcp.length==0){
                        layer.msg("请勾选视频会员产品");
                    }else {
                        vm.$set('form.mytable',[]);
                        $("#modal").modal("close");//关闭选择弹框
                        this.choose = true;
                        var index = layer.open({type:3});
                        $.ajax({
                            url: "/store/findProductions",
                            type:'POST',
                            async:false,
                            data:{"subData":$("#subData").val()},
                            dataType:"json",
                            error:function(){
                                $(this).addClass("done");
                            },
                            success: function(data){
                                layer.close(index);
                                if(data.success){
                                    vm.$set('mytable',data.object);
                                    $("#number").text(vm.mytable.length);
                                }else {
                                    vm.$set('form.mytable',[]);
                                    layer.msg(data.message);
                                }
                            }
                        });
                    }
                }
            },
            switchLl:function(){
                if(this.form2.splx==1){//流量
                    if(this.form2.yys==1){
                        this.liuliang=YDll;
                    }else if(this.form2.yys==2){
                        this.liuliang=LTll;
                    }else {
                        this.liuliang=DXll;
                    }
                }else if (this.form2.splx==2){//话费
                    if(this.form2.yys==1){
                        this.huafei=YDhf;
                    }else if(this.form2.yys==2){
                        this.huafei=LThf;
                    }else {
                        this.huafei=DXhf;
                    }
                }else{
                    if(this.form2.yys==4){//视频会员
                        this.member=Ykhy;
                    }else if(this.form2.yys==5){
                        this.member=Aqyhy;
                    }else if(this.form2.yys==6){
                        this.member=TXhy;
                    }else if(this.form2.yys==7){
                        this.member=Shhy;
                    }else if(this.form2.yys==8){
                        this.member=Lshy;
                    }
                }
            },
            qxsf: function () {
                if ($(".xzsf").prop("checked")) {
                    $("input[name='sf']").prop("checked", true);
                    for (var i = 0; i < $("input[name='sf']").length; i++) {
                        arr2.push($("input[name='sf']:checked").eq(i).val());
                    }
                    this.form2.yxsf = arr2.unique();
                } else {
                    this.form2.yxsf = [];
                    $("input[name='sf']").prop("checked", false);
                }
            },
            sfcheck: function () {
                if ($("input[name='sf']:checked").length == this.xsf.length) {
                    $(".xzsf").prop("checked", true);
                } else {
                    $(".xzsf").prop("checked", false);
                }
            },
            cxsfqx: function () {
                if ($(".cxsf").prop("checked")) {
                    $("input[name='cxsf']").prop("checked", true);
                    for (var i = 0; i < $("input[name='cxsf']").length; i++) {
                        cxsfarr.push($("input[name='cxsf']:checked").eq(i).val());
                    }
                    this.cx.sf = cxsfarr.filter(function (element, index, self) {
                        return self.indexOf(element) === index;
                    });
                } else {
                    this.cx.sf = [];
                    $("input[name='cxsf']").prop("checked", false);
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
            openmz:function(){
                var a = $("#hjlx").val();
                var b = $("#hjyys").val();
                if(b!=0){
                    mz(a,b);
                    $("#mzChose").modal({
                        closeViaDimmer: false
                    });
                }else{
                    //alert("请选择运营商!");
                    layer.msg("请选择运营商!");
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
                $("#xzmz").val(a);
            },
            clearmz:function(){
                this.cx.mz = [];
                $("#xzmz").val("");
            },
            opensf:function(){
                $("#sfChose").modal({
                    closeViaDimmer: false
                });
            },
            closesf:function(){
                var a =[];
                $("#sfChose").modal("close");
                for(let i = 0; i<this.options2.length;i++){
                    for(let j = 0;j<this.cx.sf.length;j++){
                        if(this.options2[i].key == this.cx.sf[j]){
                            a.push(this.options2[i].value);
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
            cxmzcheck: function () {
                if ($("input[name='cxmz']:checked").length == $("input[name='cxmz']").length) {
                    $(".cxmz").prop("checked", true);
                } else {
                    $(".cxmz").prop("checked", false);
                }
            },
            qxll: function () {
                if ($(".liucheck").prop("checked")) {
                    $("input[name='ll']").prop("checked", true);
                    for (var i = 0; i < $("input[name='ll']").length; i++) {
                        arr3.push($("input[name='ll']:checked").eq(i).val());
                    }
                    this.form2.yxll = arr3.unique();
                } else {
                    this.form2.yxll = [];
                    $("input[name='ll']").prop("checked", false);
                }
            },
            llcheck: function () {
                if ($("input[name='ll']:checked").length == this.liuliang.length) {
                    $(".liucheck").prop("checked", true);
                } else {
                    $(".liucheck").prop("checked", false);
                }
            },
            qxcp:function(){
                if ($(".xzcp").prop("checked")) {
                    $("input[name='cp']").prop("checked", true);
                    for (var i = 0; i < $("input[name='cp']").length; i++) {
                        arrcp.push($("input[name='cp']:checked").eq(i).val());
                    }
                    this.form2.yxcp = arrcp.unique();
                } else {
                    this.form2.yxcp = [];
                    $("input[name='cp']").prop("checked", false);
                }
            },
            cpcheck: function () {
                if ($("input[name='cp']:checked").length == this.member.length) {
                    $(".xzcp").prop("checked", true);
                } else {
                    $(".xzcp").prop("checked", false);
                }
            },
            qxhf: function () {
                if ($(".huacheck").prop("checked")) {
                    $("input[name='hf']").prop("checked", true);
                    for (var i = 0; i < $("input[name='hf']").length; i++) {
                        arr4.push($("input[name='hf']:checked").eq(i).val());
                    }
                    this.form2.yxhf = arr4.unique();
                } else {
                    this.form2.yxhf = [];
                    $("input[name='hf']").prop("checked", false);
                }
            },
            hfcheck: function () {
                if ($("input[name='hf']:checked").length == this.huafei.length) {
                    $(".huacheck").prop("checked", true);
                } else {
                    $(".huacheck").prop("checked", false);
                }
            },
            splx: function () {
                if (this.form2.splx == 1 || this.form2.splx == 0) {
                    this.liu = true;
                    this.form2.yxhf = [];
                    //视频会员
                    $("#operatorYLD").show();
                    $("#videoVip").hide();
                    $("#shengfen").show();
                    $("#chanpin").hide();
                    $(".fanwei").show();
                    $("#mianzhi").show();
//                    $(".xzsf").prop("checked",false).removeAttr("disabled");
//                    vm.qxsf();
                }else if(this.form2.splx == 2){
                    this.liu = false;
                    this.form2.yxll = [];
                    //话费会员
                    $("#operatorYLD").show();
                    $("#videoVip").hide();
                    $("#shengfen").show();
                    $("#chanpin").hide();
                    $("#mianzhi").show();
                    $(".fanwei").show();
//                    $(".xzsf").prop("checked",false).removeAttr("disabled");
//                    vm.qxsf();
                }else{
                    //视频会员
                    $("#operatorYLD").hide();
                    $("#videoVip").show();
                    $("#shengfen").hide();
                    $("#chanpin").show();
                    $(".fanwei").hide();
                    $("#mianzhi").hide();
                    this.form2.yxsf=32;//全国
//                    $(".xzsf").prop("checked",true).attr("disabled","disabled");
//                    vm.qxsf();
                    this.liu=false;
                    setTimeout(function(){$("#mianzhi").hide();},10)
                }

                if(this.form2.splx==1){//流量
                    if(this.form2.yys==1){
                        this.liuliang=YDll;
                    }else if(this.form2.yys==2){
                        this.liuliang=LTll;
                    }else {
                        this.liuliang=DXll;
                    }
                    this.gys = LLgys;
                }else if(this.form2.splx==2) {//话费
                    if(this.form2.yys==1){
                        this.huafei=YDhf;
                    }else if(this.form2.yys==2){
                        this.huafei=LThf;
                    }else {
                        this.huafei=DXhf;
                    }
                    this.gys = HFgys;
                }else{
                    if(this.form2.yys==4){
                        this.member=Ykhy;
                    }else if(this.form2.yys==5){
                        this.member=Aqyhy;
                    }else if(this.form2.yys==6){
                        this.member=TXhy;
                    }else if(this.form2.yys==7){
                        this.member=Shhy;
                    }else if(this.form2.yys==8) {
                        this.member=Lshy;
                    }
                    this.gys = HYgys;
                }
            },
            radioCheck: function (a) {
                if (a == 1) {
                    this.ngys = false;
                    this.form.xzgys = [];
                } else {
                    this.ngys = true;
                }
            },
            checkToSubmit:function(){
//                alert($("#selectData").val());
                if ($(".IptContIpt>input").val()==""){
                    this.form.xhjz='';
                }
                if(this.form.xhjz==''){
                    layer.tips("请选择货架组","#xzhjz",2);
                    return;
                }
//                alert($("#selectData").val());
                if(this.form.mytable.length==0){
                    layer.tips("请选择并勾选商品","#choseProduct",1);
                    return;
                }
                var isSmallNum = $("#isSmallNum").val();
                if(/^\d{1}[.]\d{0,4}$|^\d{1}$/.test(isSmallNum)){
                    if(isSmallNum>=2){
                        layer.tips("折扣应该是大于0小于2的小数","#isSmallNum",1);
                        return;
                    }
                }else{
                    layer.tips("折扣应该是大于0小于2的小数","#isSmallNum",1);
                    return;
                }

                if(this.form.xzqd==1){
                    if(this.form.xzgys.length==0){
                        layer.tips("请勾选下面的供应商！","#xzgysqd",1);
                        return;
                    }
                }
                var index = layer.open({type:3});
                console.log($("#selectData").val());
                $.ajax({
                    url: "/store/addStore",
                    type:'POST',
                    async:false,
                    data:{"addStoreData":$("#selectData").val()},
                    dataType:"json",
                    error:function(){
                        $(this).addClass("done");
                    },
                    success: function(data){
                        layer.close(index);
                        layer.msg(data.message);
                    }
                });
                alert("数据OK");
            },
            isSmallNum:function(){
                var isSmallNum = $("#isSmallNum").val();
                if(/^\d{1}[.]\d{0,4}$|^\d{1}$/.test(isSmallNum)){
                    if(isSmallNum>2){
                        layer.tips("折扣应该是大于0小于2的小数","#isSmallNum",1);
                    }
                }else{
                    layer.tips("折扣应该是大于0小于2的小数","#isSmallNum",1);
                }
            },
            toFindListData:function(pageNumber){
                var sendType = $("#select1").val();
                var toFindListData = $("#toFindListData").val();
                var zk_type = $("#zk_type").val();
                var zk_num = $("#zk_num").val();
                var supplierName = $("#supplierNameId").attr("value");
                if($("#supplierNameId").val()==""){
                    supplierName=0;
                }
                if(supplierName==""){
                    supplierName=0;
                }

                var index = layer.open({type:3});
                var pageNumSet = $("#pageNumSet").val();
                $.ajax({
                    url: "/store/findStoreList?pageNumber="+pageNumber+"&pageSize="+pageNumSet,
                    type:'POST',
                    async:false,
                    data:{
                        "subData":toFindListData,
                        "zk_type":zk_type,
                        "zk_num":zk_num,
                        "supplierID":supplierName
                    },
                    dataType:"json",
                    error:function(){
                        $(this).addClass("done");
                    },
                    success: function(data){
                        layer.close(index);
                        arr = [];
                        pageInfo.ts=data.total;
                        pageInfo.dq=data.pageNum;
                        pageInfo.all=data.pages;
                        if(data.list == ""){
                            $("#noDataToShow").show();
                        }else{
                            $("#noDataToShow").hide();
                        }
                        vm.$set('json',data.list);
                        vm.checkID =[];
                        $(".checkall").prop("checked", false);
                    }
                });
            },
            tablecheck: function () {
                if ($(".tableall").prop("checked")) {
                    $("input[name='table']").prop("checked", true);
                    for (var i = 0; i < $("input[name='table']").length; i++) {
                        table.push($("input[name='table']:checked").eq(i).val());
                    }
                    this.tableID = table.unique();
                    this.form.mytable = this.tableID;
                    //alert(this.$parent.mytable);
                    $("#number").text(this.tableID.length);
                } else {
                    this.tableID = [];
                    this.form.mytable = [];
                    $("input[name='table']").prop("checked", false);
                    $("#number").text(this.tableID.length);
                }
            },
            tablecheck1:function () {
                if ($("input[name='table']:checked").length == this.mytable.length) {
                    $(".tableall").prop("checked", true);
                    $("#number").text($("input[name='table']:checked").length)
                } else {
                    $(".tableall").prop("checked", false);
                    $("#number").text($("input[name='table']:checked").length)
                }
            }

        }
    });

    function editStoreData(){
//        console.log(vm,lcySearch)
        var editType = $("#editType").val();
        var one_key = $("#one_key").is(":checked");
        if(one_key==true) {
            console.log(vm)
//            vm.$components.lcyInShow = false
//            if (editType == 3) {
//                vm.$components.lcyInShow = false
//            }
        }
        if(one_key==true){
            if(editType==1){
                var editStatus = $("#editStatus").val();
                if(editStatus==0){
                    layer.tips("请选择上下架状态","#editStatus",1);
                    return;
                }
                var msg ="";
                if(editStatus==3){
                    msg ="确定一键永久下架？";
                }else if(editStatus==1){
                    msg ="确定一键上架？";
                }else if(editStatus==2){
                    msg ="确定一键下架？";
                }
                layer.confirm(msg, {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    editStatus = $("#editStatus").val();
                    var toFindListData = $("#toFindListData").val();
                    var zk_type = $("#zk_type").val();
                    var zk_num = $("#zk_num").val();
//                    var supplierName = $("#supplierName").val();
                    var supplierName = $("#supplierNameId").attr("value");
                    if($("#supplierNameId").val()==""){
                        supplierName=0;
                    }
                    if(supplierName==""){
                        supplierName=0;
                    }
                    var index = layer.load(0, {shade: [0.3,'#000']});
                    $.ajax({
                        url: "/store/editZk_one_key",
                        type:'POST',
                        async:false,
                        data:{
                            "status":editStatus,
                            "subData":toFindListData,
                            "zk_type":zk_type,
                            "zk_num":zk_num,
                            "supplierID":supplierName
                        },
                        dataType:"json",
                        error:function(){
                            layer.closeAll();
                        },
                        success: function(data){
                            layer.msg(data.message);
                            layer.close(index);
                            if(data.success){
                                $("#your-modal").modal("close");
                                reloadPage(pageInfo.dq);
                            }
                        }
                    });
                }, function(){
                    layer.closeAll();
                });
                return;
            }else if(editType==2){
                var editZk = $("#editZk").val();
                if(/^\d{1}[.]\d{0,4}$|^\d{1}$/.test(editZk)){
                    if(editZk>=2){
                        layer.tips("折扣应该是大于0小于2的小数","#editZk",1);
                        return;
                    }
                }else{
                    layer.tips("折扣应该是大于0小于2的小数","#editZk",1);
                    return;
                }
                var toFindListData = $("#toFindListData").val();
                var zk_type = $("#zk_type").val();
                var zk_num = $("#zk_num").val();
//                var supplierName = $("#supplierName").val();
                var supplierName = $("#supplierNameId").attr("value");
                if($("#supplierNameId").val()==""){
                    supplierName=0;
                }
                if(supplierName==""){
                    supplierName=0;
                }
                layer.confirm("确定一键修改折扣为"+editZk, {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    var index = layer.open({type:3});
                    $.ajax({
                        url: "/store/editZk_one_key",
                        type:'POST',
                        async:false,
                        data:{
                            "subData":toFindListData,
                            "zk_type":zk_type,
                            "zk_num":zk_num,
                            "supplierID":supplierName,
                            "zk_to_update":editZk
                        },
                        dataType:"json",
                        error:function(){
                            $(this).addClass("done");
                        },
                        success: function(data){
                            layer.msg(data.message);
                            layer.close(index);
                            if(data.success){
                                $("#your-modal").modal("close");
                                reloadPage(pageInfo.dq);
                            }
                        }
                    });
                }, function(){
                    layer.closeAll();
                });
                return;
            }
        }

        var editDataSubData = $("#editDataSubData").val();
        if(editDataSubData==''||editDataSubData=='[]'){
            layer.msg("请选择货架");
            return;
        }
        if(editDataSubData=='[  null]'){
            layer.msg("没有可修改货架");
            return;
        }
        if(editType==1){
            var editStatus = $("#editStatus").val();
            if(editStatus==0){
                layer.tips("请选择上下架状态","#editStatus",1);
                return;
            }
            var msg ="";
            if(editStatus==3){
                msg ="确定永久下架？";
            }else if(editStatus==1){
                msg ="确定上架？";
            }else if(editStatus==2){
                msg ="确定下架？";
            }

            layer.confirm(msg, {
                btn: ['确定','取消'] //按钮
            }, function(){
                var index = layer.load(0, {shade: [0.3,'#000']});
                $.ajax({
                    url: "/store/editStatus",
                    type:'POST',
                    async:false,
                    data:{"subData":editDataSubData,status:editStatus},
                    dataType:"json",
                    error:function(){
                        layer.closeAll();
                    },
                    success: function(data){
                        layer.msg(data.message);
                        layer.close(index);
                        if(data.success){
                            $("#your-modal").modal("close");
                            reloadPage(pageInfo.dq);
                        }
                    }
                });
            }, function(){
                layer.closeAll();
            });

        }else if(editType==2){//修改折扣
            var editZk = $("#editZk").val();
            if(/^\d{1}[.]\d{0,4}$|^\d{1}$/.test(editZk)){
                if(editZk>=2){
                    layer.tips("折扣应该是大于0小于2的小数","#editZk",1);
                    return;
                }
            }else{
                layer.tips("折扣应该是大于0小于2的小数","#editZk",1);
                return;
            }
            var index = layer.open({type:3});
            $.ajax({
                url: "/store/editZk",
                type:'POST',
                async:false,
                data:{"subData":editDataSubData,discount:editZk},
                dataType:"json",
                error:function(){
                    $(this).addClass("done");
                },
                success: function(data){
                    layer.msg(data.message);
                    layer.close(index);
                    if(data.success){
                        $("#your-modal").modal("close");
                        reloadPage(pageInfo.dq);
                    }
                }
            });
        }else if(editType==3){

            // var type = vm.$children[0].pljson.plradio;
            //  console.log(type);
            //var plcheck = vm.$children[0].pljson.plcheck;
            //console.log(plcheck);
            var editSendType = $("#editSendType").val();
//            console.log(editSendType)
            /* if(type==1&&plcheck.length==0){
             layer.tips("请勾选下面的供应商！","#editgys",1);
             return;
             }*/
            console.log($("#jdf1").val())
            if( vm.lcyInShow && $("#jdf1").val()){
                layer.tips("请勾选下面的供应商！","#editgys",1);
                return;
            }
            console.log("pll=="+JSON.parse(editSendType).pll.length)
            console.log("plcheck=="+JSON.parse(editSendType).plcheck.length)
            console.log(JSON.parse(editSendType).pll.length)
            console.log(editDataSubData)
            var index = layer.open({type:3});
            var obj1=JSON.parse(editSendType)
            obj1.plcheck.push(parseInt($("#jdf1").val()));

            editSendType=JSON.stringify(obj1);

            console.log(editSendType)
            $.ajax({
                url: "/store/editSendType",
                type:'POST',
                async:false,
                data:{"subData":editSendType,"storeIds":editDataSubData},
                dataType:"json",
                error:function(){
                    $(this).addClass("done");
                },
                success: function(data){
                    layer.close(index);
                    layer.msg(data.message);
                    if(data.success){
                        $("#your-modal").modal("close");
                        reloadPage(pageInfo.dq);
                    }
                    vm.$data.lcyInShow=false;
                }
            });
        }
    }

    function reloadPage(pageNumber){
        var sendType = $("#select1").val();
        var toFindListData = $("#toFindListData").val();
        var zk_type = $("#zk_type").val();
        var zk_num = $("#zk_num").val();
        var supplierName = $("#supplierNameId").attr("value");
        if($("#supplierNameId").val()==""){
            supplierName=0;
        }
        if(supplierName==""){
            supplierName=0;
        }
//        if(sendType==1 && supplierName==0){
//            layer.msg("请选择渠道供应商！");
//            return;
//        }
        var index = layer.load(0, {shade: [0.3,'#000']});
        var pageNumSet = $("#pageNumSet").val();
        $.ajax({
            url: "/store/findStoreList?pageNumber="+pageNumber+"&pageSize="+pageNumSet,
            type:'POST',
            async:false,
            data:{
                "subData":toFindListData,
                "zk_type":zk_type,
                "zk_num":zk_num,
                "supplierID":supplierName
            },
            dataType:"json",
            error:function(){
                layer.close(index);
            },
            success: function(data){
                arr = [];
                pageInfo.ts=data.total;
                pageInfo.dq=data.pageNum;
                pageInfo.all=data.pages;
                vm.$set('json',data.list);
                if(data.list == ""){
                    $("#noDataToShow").show();
                }else{
                    $("#noDataToShow").hide();
                }
                vm.checkID =[];
                $(".checkall").prop("checked", false);
                layer.close(index);
            }
        });
    }

    function gotoPage(){
        var pagenum = $("#gotoPage").val();
        if(pagenum==''){
            layer.tips("请输入页数！","#gotoPage",{tips: 3});
            return;
        }
        reloadPage(pagenum);
    }

    function watchSupply(storeId,sendType){
        if(sendType=='系统匹配'){
            layer.msg("系统匹配不支持查看指定供应商!");
            return;
        }else {
            var index = layer.open({type:3});
            $.ajax({
                url: "/store/watchSupply",
                type:'POST',
                async:false,
                data:{"storeId":storeId},
                dataType:"json",
                error:function(){
                    $(this).addClass("done");
                },
                success: function(data){
                    var html="<table class='am-table'  style='margin-bottom: 0;text-align: center;'><thead><tr><th>供应商ID</th><th>供应商类型</th><th>供应商名称</th></tr></thead><tbody>";

                    $.each(data, function(idx, obj) {
                        var type = obj.businessType;
                        if(type==1){
                            html+="<tr><td>"+obj.id+"</td><td>流量</td><td>"+obj.name+"</td></tr>";
                        }else if(type==2){
                            html+="<tr><td>"+obj.id+"</td><td>话费</td><td>"+obj.name+"</td></tr>";
                        }else{
                            html+="<tr><td>"+obj.id+"</td><td>视频会员</td><td>"+obj.name+"</td></tr>";
                        }
                    });
                    html+="</tbody></table>";

                    $("#ckgystb").html(html);

                    $("#listModal").modal();
                    layer.close(index);
                }
            });
        }
    }

    $(document).ready(function(){
        var html = "<option value='0'>渠道供应商</option>";
        var ConTxt="";
//        console.log(supplies);

        $.each(supplies, function(idx, obj) {
            html += "<option value='"+obj.id+"'>"+obj.id+"_"+obj.name+"</option>";
            ConTxt+= "<li value='"+obj.id+"'>"+obj.name+"</li>";
        });
        vm.people=supplies;
        $("#supplierName").html(html);
        $(".posiNode").html(ConTxt);
        $("#supplierNameId").change(function(){
            console.log(1)
        })
        /* $(window).mousedown(function(e){
         console.log(e.which);
         if(e.which==1){
         getkeylist($("#supplierNameId").val())
         }
         })*/
        $(document).keyup(function(e){
//             console.log(e.keyCode)
            var kc = e.keyCode;
            if((kc>=48&&kc<=57)||kc==32||kc==8){
                getkeylist($("#supplierNameId").val());
                if($("#supplierNameId").val()==0){
                    flog=false;
                }
            }
        })
    });

    document.getElementById("supplierNameId").addEventListener("change",function(e){
        console.log("inputting!!");
    });
    function getkeylist(key) {
//        console.log(key);
        if(key=="") {
            jQuery("#supplierNameId").attr("value","");
        }
        var html = "<option value='0'>渠道供应商</option>";
        var ConTxt = "";
        $.each(supplies, function (i, obj) {
            if (key == "") {
                html += "<option value='" + obj.id + "'>" + obj.id + "_" + obj.name + "</option>";
                ConTxt += "<li value='" + obj.id + "'>" + obj.name + "</li>";
            }
            else {
                if (obj.name.indexOf(key) > -1) {
                    html += "<option value='" + obj.id + "'>" + obj.id + "_" + obj.name + "</option>";
                    ConTxt += "<li value='" + obj.id + "'>" + obj.name + "</li>";
                }
            }
        })

        $("#supplierName").html(html);
        $(".posiNode").html(ConTxt);
    }
    $("#supplierNameId").focus(function(){
        getkeylist($("#supplierNameId").val())
    })
    $("#supplierNameId").blur(function(){
        setTimeout(function () {
            if(!flog){
                $("#supplierNameId").val("");
                $("#supplierNameId").attr("value","");
                flog=false;
            }
        }, 200)
    })
    function displayGYS(){
        var objS = document.getElementById("select1");
        var value = objS.options[objS.selectedIndex].value;
        if(value==1){
            $("#spl").show();
        }else {
            $("#spl").hide();
            $("#supplierName").val("0");
        }
    }




</script>
</body>
</html>
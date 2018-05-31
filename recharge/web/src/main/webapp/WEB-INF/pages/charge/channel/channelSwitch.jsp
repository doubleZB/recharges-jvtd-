<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/11/14
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qudaokaiguanweihu.css">
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
    .am-u-sm-2 {
        width: 150px;
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
        width:200px;
        height:35px;
    }
    .IptCon > input{
        width:100%;
        height:35px;
        border:1px solid #ccc;
    }
    .posi{
        width:200px;
        position: absolute;
        left:0;
        top:35px;
        display: none;
        border:1px solid #ccc;
        background: #fff;
        height: auto;
        max-height: 140px;
        overflow-y: scroll;
        z-index: 100;
    }
    .posi li{
        line-height: 35px;
        cursor: pointer;
    }
    .posi li span{
        padding-left:20px;
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
        <div class="am-cf am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf" style="padding: 0;"><strong class="am-text-primary am-text-lg" style="font-size: 14px;">渠道开关维护</strong>
                <input type="hidden" value="${adminLoginUser.adminName}" id="userId"/>
            </div>
        </div>

        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form-inline" role="form">
                <div class="am-form-group"><%--{{typ | json}}--%>
                    <select class="select1" style="width: 200px;float: left;" v-model="typ">
                        <option value="0">渠道类型</option>
                        <option value="1">流量</option>
                        <option value="2">话费</option>
                        <option value="3">视频会员</option>
                    </select>
                </div>
                <div class="am-form-group">
                    <select class="select1" style="width: 200px;float: left;" v-model="yys">
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
                <div class="am-form-group">
                    <select class="select1" style="width: 200px;float: left;" v-model="sta">
                        <option value="0">状态</option>
                        <option value="1">开启</option>
                        <option value="2">测试中</option>
                        <option value="3">人工关闭</option>
                    </select>
                </div>
                <div class="clearfix"></div>
                <div class="am-form-group" style="margin-top: 0;">
                    <%--<select class="select1" style="margin-top:20px;width: 200px;float: left;" v-model="sf">--%>
                    <%--<option v-for="option in sheng" :value="option.value">{{option.text}}</option>--%>
                    <%--</select>--%>
                    <input type="text"  class="am-form-field" id="cxsf" placeholder="选择省份"
                           style="width: 200px;float: left;margin-top: 20px" @focus="opensf()">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <%--<select class="select1" style="width: 200px;float: left;" v-model="gys">--%>
                    <%--<option  value="0">请选择供应商111</option>--%>
                    <%--<option v-for="option in supplier" :value="option.id">{{option.name}}</option>--%>
                    <%--</select>--%>
                    <div id="app" style="position: relative;float:left;">
                        <div class="IptCon">
                            <input type="text" v-model="searchQuery" class="searchIpt" placeholder="请选择供应商">
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
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" class="am-form-field" placeholder="渠道简称"
                           style="width: 200px;float: left;" v-model="name">
                </div>
                <button type="button" class="am-btn am-btn-warning"
                        style="width: 120px;margin:auto;margin-top: 20px"  onclick="getChannelList()">查询
                </button>
            </form>
            <hr>
            <div class="am-u-sm-12 am-u-md-8" style="margin-bottom: 20px;padding-left: 0;">
                <button type="button" class="am-btn am-btn-warning"
                        style="width: 120px;margin: auto;" @click="open()">批量状态修改
                </button>
                <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: auto;" @click="yijian()" id="yijianxiugai">一键修改</button>
                <label class="am-checkbox-inline">
                    <input type="checkbox" class="tiaojian" @click="firstCheck()">按查询条件
                </label>
                <%-- <span id="ssp">{{ checkID | json }}</span>--%>
            </div>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>
                        <label class="am-checkbox-inline">
                            <input type="checkbox" @click="checked"> 序号
                        </label>
                    </th>
                    <th>ID</th>
                    <th>渠道类型</th>
                    <th>渠道简称</th>
                    <th>渠道供应商</th>
                    <th>运营商</th>
                    <th>省份</th>
                    <th>状态</th>
                    <th>操作原因</th>
                    <th>时间</th>
                    <th>操作人员</th>
                </tr>
                </thead>
                <tbody  id="tbody1">
                <tr v-for="item in json">
                    <td class="first">
                        <label class="am-checkbox-inline">
                            <input type="checkbox" value="{{item.id}}" name="checkbox" class="checkbox"
                                   v-model="checkID" @click="checked2($index)">
                            {{$index+1}}
                        </label>
                    </td>
                    <td>{{item.id}}</td>
                    <td>{{item.businessType}}</td>
                    <td>{{item.channelName}}</td>
                    <td>{{item.supplyId}}</td>
                    <td>{{item.operatorId}}</td>
                    <td>{{item.provinceId}}</td>
                    <td>{{item.status}}</td>
                    <td>{{item.updateReason}}</td>
                    <td>{{item.dateStr}}</td>
                    <td>{{item.updateName}}</td>
                    <%-- <td>{{item.ry}}</td>--%>
                </tr>
                </tbody>
            </table>
            <hr>
            <div class="sj" style="display: none; color: red;font-size: 2em;text-align: center;">没有找到数据</div>
            <div class="am-cf">
                共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                <div class="am-fr">
                    <ul class="am-pagination" style="margin: 0">
                        <li><a href="javascript:void(0);" onclick="getChannelList('{{page.dq-1}}');">上一页</a></li>
                        <li class="am-disabled"><a
                                href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                        <li><a href="javascript:void(0);"  onclick="getChannelList('{{page.dq+1}}','{{page.all}}');">下一页</a></li>
                        <li class="am-disabled"><a href="#">|</a></li>
                        <li>
                            <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                        </li>
                        <li class="am-active"><a href="#" style="padding: .5rem .4rem;" onclick="gotoPage()">GO</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
    <!--编辑按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">状态修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">{{msg}}</label>
                        <select style="width: 300px;" v-model="selected">
                            <option v-for="option in options" :value="option.value">{{option.text}}</option>
                        </select>
                    </div>

                    <input class="am-u-sm-6" type="hidden" value="${adminLoginUser.adminName}" id="adminname"  />

                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">操作原因：</label>
                        <textarea class="" rows="5" id="doc-ta-1" style="width: 300px;"></textarea>
                    </div>
                    <hr>
                    <div class="am-form-group">
                        <div>
                            <button  type="button"  class="am-btn am-btn-warning" style="width: 120px;margin: auto;"  onclick="editor()">提交
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
                            <div class="am-checkbox" v-for="s in sheng">
                                <label>
                                    <input type="checkbox" name="cxsf" value="{{s.value}}" v-model="sf"
                                           @click="cxsfcheck()">{{s.text}}
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
    <!--一键修改按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="modal2">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">状态修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">状态修改</label>
                        <select style="width: 300px;" v-model="selected">
                            <option v-for="option in options" :value="option.value">{{option.text}}</option>
                        </select>
                    </div>

                    <input class="am-u-sm-6" type="hidden" value="${adminLoginUser.adminName}" id="adminnames"   style="width:300px;float: left;border:0;"/>

                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">操作原因：</label>
                        <textarea class="" rows="5" id="doc-ta-2" style="width: 300px;"></textarea>
                    </div>
                    <hr>
                    <div class="am-form-group">
                        <div>
                            <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: auto;"  onclick="piliangyijianxiugaitijiao()">提交
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
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
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





    /* vm.checkID
     vm.json.selected*/
    $(".selectlist").attr("disabled", true);
    var arr = [];
    var cxsfarr = [];
    var Off = true;
    var vm = new Vue({
        el: "#content",
        data: {
            checkID: [],
            typ:"0",
            sta:"0",
            yys:"0",
            name:"",
            msg: "",
            sf:[],
            searchQuery: '',
            columns: [{
                name: 'name',
                ind:'id'
            }],
            gys:"0",
            selected: "",
            options: [
                {text: '开启', value: '1'},
                {text: '测试中', value: '2'},
                {text: '人工关闭', value: '3'},
            ],
            json: [
                {
                    id: "4234",
                    lx: "流量",
                    mc: "A",
                    yys: "移动",
                    sf: "北京",
                    selected: "4",
                    yz: "正常开启",
                    ry: "小三",
                    sj: "2016-10-15 19:00"
                }
            ],
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
            supplier:[

            ],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        },
        ready:function(){
            this.firstCheck();
        },
        methods: {
            open: function () {
                var len = $("input[name='checkbox']:checked").length;
                if (len !== 0) {
                    $('#your-modal').modal();
                    this.msg = "状态修改：";
                    this.selected = 1;
                } else {
                    alert("请选择想要修改的数据")
                }
            },
            show: function (a, index) {
                var s = $(".selectlist").eq(index).parents("td").siblings(".first").children("label").children().prop("checked");
                if (s) {
                    $('#your-modal').modal();
                    this.msg = "当前状态：";
                    this.selected = a;
                }
            },
            checked: function () {
                if (Off) {
                    $("input[name='checkbox']").prop("checked", true);
                    $(".selectlist").attr("disabled", false);
                    for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                        arr.push($("input[name='checkbox']:checked").eq(i).val());
                    }
                    this.checkID = arr.unique();
                    Off = false;
                } else {
                    this.checkID = [];
                    $("input[name='checkbox']").prop("checked", false);
                    $(".selectlist").attr("disabled", true);
                    Off = true;
                }
            },
            checked2:function (index) {
                if($("input[name='checkbox']").eq(index).prop("checked")){
                    $("input[name='checkbox']").eq(index).parents().parents().siblings(".select2").children().prop("disabled",false);
                }else{
                    $("input[name='checkbox']").eq(index).parents().parents().siblings(".select2").children().prop("disabled",true);
                }
            },
            yijian:function(){
                if(this.yys == "0"){
                    alert("请选择运营商！")
                } else if (this.typ == "0"){
                    alert("请选择类型！")
                }else if($(".IptCon>input").val()==""){
                    inds=0;
                    if (inds == 0){
                        alert("请选择供应商！")
                    }
                }else{
                    $("#modal2").modal();
                }

            },
            cxsfqx: function () {
                if ($(".cxsf").prop("checked")) {
                    $("input[name='cxsf']").prop("checked", true);
                    for (var i = 0; i < $("input[name='cxsf']").length; i++) {
                        cxsfarr.push($("input[name='cxsf']:checked").eq(i).val());
                    }
                    this.sf = cxsfarr.filter(function (element, index, self) {
                        return self.indexOf(element) === index;
                    });
                } else {
                    this.sf = [];
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
                for(let i = 0; i<this.sheng.length;i++){
                    for(let j = 0;j<this.sf.length;j++){
                        if(this.sheng[i].value == this.sf[j]){
                            a.push(this.sheng[i].text);
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

    $(function(){
        getSupplier();
        getChannelList();//执行函数

    });

    var gongyingshang="";
    //获取供应商
    function getSupplier(){
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getsupplierandchannles",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                gongyingshang=data;
                vm.supplier=data;
            }
        });
    }

    //查询分页
    function getChannelList(pageNums,pageAll){
        var pageNumsint=parseInt(pageNums);
        var pageAllint=parseInt(pageAll);
        if(pageNumsint==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }

        if(pageNumsint>pageAllint){
            layer.msg("当前是最后一页啦，没有下一页！");
            return;
        }
        var typ=vm.typ;
        var sta=vm.sta;
        var yys=vm.yys;
        var name=vm.name;
        var sf=vm.sf.toString()+",";
        if(inds==null){
            inds=0;
        }
        if($(".IptCon>input").val()==""){
            inds=0;
        }
        var gys=inds;
        /* var pageNum=pageNums;*/
        var index = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getchannellist",
            async:false,
            type:'POST',
            data:{"typ":typ,"sta":sta,"yys":yys,"name":name,"pageNum":pageNums,"sf":sf,"gys":gys},
            dataType:"json",
            success: function(data){
                layer.close(index);
                var obj=data.list;

                $(obj).each(function (index) {

                    //匹配供应商
                    $(gongyingshang).each(function (i){
                        if(data.list[index].supplyId==gongyingshang[i].id){
                            data.list[index].supplyId=gongyingshang[i].name;
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

                    if(data.list[index].provinceId==1){
                        data.list[index].provinceId="上海";
                    }
                    if(data.list[index].provinceId==2){
                        data.list[index].provinceId="云南";
                    }
                    if(data.list[index].provinceId==3){
                        data.list[index].provinceId="内蒙古";
                    }
                    if(data.list[index].provinceId==4){
                        data.list[index].provinceId="北京";
                    }
                    if(data.list[index].provinceId==5){
                        data.list[index].provinceId="吉林";
                    }
                    if(data.list[index].provinceId==6){
                        data.list[index].provinceId="四川";
                    }
                    if(data.list[index].provinceId==7){
                        data.list[index].provinceId="天津";
                    }
                    if(data.list[index].provinceId==8){
                        data.list[index].provinceId="宁夏";
                    }
                    if(data.list[index].provinceId==9){
                        data.list[index].provinceId="安徽";
                    }
                    if(data.list[index].provinceId==10){
                        data.list[index].provinceId="山东";
                    }
                    if(data.list[index].provinceId==11){
                        data.list[index].provinceId="山西";
                    }
                    if(data.list[index].provinceId==12){
                        data.list[index].provinceId="广东";
                    }
                    if(data.list[index].provinceId==13){
                        data.list[index].provinceId="广西";
                    }
                    if(data.list[index].provinceId==14){
                        data.list[index].provinceId="新疆";
                    }
                    if(data.list[index].provinceId==15){
                        data.list[index].provinceId="江苏";
                    }
                    if(data.list[index].provinceId==16){
                        data.list[index].provinceId="江西";
                    }
                    if(data.list[index].provinceId==17){
                        data.list[index].provinceId="河北";
                    }
                    if(data.list[index].provinceId==18){
                        data.list[index].provinceId="河南";
                    }
                    if(data.list[index].provinceId==19){
                        data.list[index].provinceId="浙江";
                    }
                    if(data.list[index].provinceId==20){
                        data.list[index].provinceId="海南";
                    }
                    if(data.list[index].provinceId==21){
                        data.list[index].provinceId="湖北";
                    }
                    if(data.list[index].provinceId==22){
                        data.list[index].provinceId="湖南";
                    }
                    if(data.list[index].provinceId==23){
                        data.list[index].provinceId="甘肃";
                    }
                    if(data.list[index].provinceId==24){
                        data.list[index].provinceId="福建";
                    }
                    if(data.list[index].provinceId==25){
                        data.list[index].provinceId="西藏";
                    }
                    if(data.list[index].provinceId==26){
                        data.list[index].provinceId="贵州";
                    }
                    if(data.list[index].provinceId==27){
                        data.list[index].provinceId="辽宁";
                    }
                    if(data.list[index].provinceId==28){
                        data.list[index].provinceId="重庆";
                    }
                    if(data.list[index].provinceId==29){
                        data.list[index].provinceId="陕西";
                    }
                    if(data.list[index].provinceId==30){
                        data.list[index].provinceId="青海";
                    }
                    if(data.list[index].provinceId==31){
                        data.list[index].provinceId="黑龙江";
                    }
                    if(data.list[index].provinceId==32){
                        data.list[index].provinceId="全国";
                    }
                })
                vm.json= obj;
                vm.page.ts=data.total;
                vm.page.dq=data.pageNum;
                vm.page.all=data.pages;

                if(data.list=="" ){
                    $("#tbody1").hide();
                    $(".sj").show();
                    vm.typ="0";
                    vm.sta="0";
                    vm.yys="0";
                    vm.name="";
                }else{
                    $("#tbody1").show();
                    $(".sj").hide();
                }
            }
        });
    }
    // 修改功能
    function editor(){
        var userId=$("#userId").val();

        var checkID=vm.checkID.toString()+",";
        var selecte=vm.selected;
        var username=$("#adminname").val();
        var yuanyin=$("#doc-ta-1").val();
        if( yuanyin==null && yuanyin==""){
            alert("修改原因不能为空！")
        }else if(yuanyin.length>30){
            layer.msg("修改原因不超过30个字");
        }else{
            $.ajax({
                url: "${pageContext.request.contextPath}/channels/editorchannelswitch",
                async:false,
                type:'POST',
                data:{"checkID":checkID,"selecte":selecte,"username":username,"yuanyin":yuanyin,"userId":userId},
                dataType:"json",
                error:function(){
                    layer.msg("很遗憾修改失败，从新修改吧");
                },
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

    }
    //跳页
    function gotoPage(){
        var pagenum = $("#goto-page-num").val();
        if(pagenum==""){
            layer.msg("输入跳转页数");
            return;
        }
        getChannelList(pagenum);
    }




    //一键修改
    function piliangyijianxiugaitijiao(){
        var userId=$("#userId").val();
        var channelName=vm.name;
        if(channelName==""){
            channelName=null;
        }
        var businessType=vm.typ;
        var supplyId=inds;
        var operatorId=vm.yys;
        var provinceId=vm.sf.toString()+",";
        var status=vm.sta;
        if(vm.yys == "0"){
            alert("请选择运营商！");
            return;
        }else if (vm.lx == "0"){
            alert("请选择类型！");
            return;
        }else if($(".IptCon>input").val()==""){
            inds=0;
        }
        if(inds==0){
            layer.msg("请选择供应商");
            return;
        }
        var selecte=vm.selected;
        var username=$("#adminnames").val();
        var yuanyin=$("#doc-ta-2").val();
        if(yuanyin.length>30){
            layer.msg("修改原因不超过30个字");
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/channels/updateallswitchchannel",
            async:false,
            type:'POST',
            data:{"channelName":channelName,"username":username,"selecte":selecte,
                "businessType":businessType,"supplyId":supplyId,"operatorId":operatorId,
                "provinceId":provinceId,"status":status,"yuanyin":yuanyin,"userId":userId},
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
                    vm.selected="";
                    $("#doc-ta-2").val("");
                }
            }
        });
    }



</script>
</body>
</html>

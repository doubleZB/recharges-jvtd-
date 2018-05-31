<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/16
  Time: 15:21
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
    html{
        moz-user-select: inherit;
        -moz-user-select: inherit;
        -o-user-select: inherit;
        -khtml-user-select: inherit;
        -webkit-user-select: inherit;
        -ms-user-select: inherit;
        user-select: inherit;
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
    * {
        margin: 0;
        padding: 0;
        list-style: none;
    }

    a,
    a:hover {
        text-decoration: none;
    }

    pre {
        font-family: '微软雅黑'
    }

    .box {
        width: 970px;
        padding: 10px 20px;
        background-color: #fff;
        margin: 10px auto;
    }

    .box a {
        padding-right: 20px;
    }

    .demo1,
    .demo2
    {
        margin: 40px 0;

    }
    .demo1 input,
    .demo2 input{
        height: 35px;
        background-image:none;
        font-size: 14px;
        padding-left:10px ;
    }
    #operate{
        font-size: 14px;
    }
    h3 {
        margin: 10px 0;
    }

    .layinput {
        height: 22px;
        line-height: 22px;
        width: 150px;
        margin: 0;
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
    } #content{
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
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">

        <div class="am-tab-panel am-active" id="tab1">
            <input type="hidden" value="{{cx|json}}" id="toFindListData">

            <form class="am-form-inline" role="form">
                <div class="am-form-group demo1" style="margin-top: 20px">
                    <input class="inline laydate-icon" name="orderTimeOne" id="startDate" placeholder="开始时间" style="width:160px; margin-right:2px;">
                </div>
                <div class="am-form-group demo2" style="margin-top: 20px">
                    <input class="inline laydate-icon"  name="orderTimeTwo" id="endDate"  placeholder="结束时间" style="width:160px;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input name="rechargeMobile"  id="rechargeMobile" type="text" class="am-form-field" placeholder="充值号码"
                           style="width: 150px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input  name="userCnName" id="userCnName" type="text" class="am-form-field" placeholder="商户简称"
                            style="width: 150px;float: left;">
                </div>

                <div class="am-form-group" style="margin-top: 20px">
                    <input  name="supplyName" id="supplyName" type="text" class="am-form-field" placeholder="供应商"
                            style="width: 150px;float: left;">
                </div>
                <div class="am-form-group">
                    <select  name="businessType" id="businessType" class="select1" style="width: 150px;float: left;"  @change="clearmz()">
                        <option value="">商品类型</option>
                        <option value="1">流量</option>
                        <option value="2">话费</option>
                        <option value="3">视频会员</option>
                    </select>
                </div>
                <div class="clearfix"></div>
                <div class="am-form-group">
                    <select  name="operator" id="operator" class="select1" style="width: 150px;float: left;">
                        <option value="">运营商</option>
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
                    <input type="text"  class="am-form-field" id="money" placeholder="面值颗粒"
                           style="width: 150px;float: left;" @focus="faceValue()">
                </div>
                <div class="am-form-group">
                    <select  name="key" id="provinceId" class="select1" style="width: 150px;float: left;">
                        <option value="">省份</option>
                    </select>
                </div>
                <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin-top: 20px;" onclick="reloadPage(1)">查询
                </button>
            </form>
            <hr>
            <div class="am-u-sm-12 am-u-md-8" style="margin-bottom: 20px;padding-left: 0;">
                <button type="button" class="am-btn am-btn-warning"
                        style="width: 120px;margin: auto;" onclick="succeed()">设置成功
                </button>
                <button type="button" class="am-btn am-btn-warning"
                        style="width: 120px;margin: auto;" onclick="failed()">设置失败
                </button>
            </div>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>
                        <label class="am-checkbox-inline">
                            <input type="checkbox" @click="checked" class="checkall"> 序号
                        </label>
                    </th>
                    <th>ID</th>
                    <th>订单号</th>
                    <th>充值号码</th>
                    <th>商品类型</th>
                    <th>运营商</th>
                    <th>省份</th>
                    <th>商品</th>
                    <th>商户简称</th>
                    <th>供应商</th>
                    <th>开始充值时间</th>
                    <th>未知时长</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>
                        <label class="am-checkbox-inline">
                            <input type="checkbox" name='checkbox' value="{{item.orderNum}}" v-model="checkID" @click="clearcheck()" />
                            {{$index+1}}
                        </label>
                    </td>
                    <td>{{item.id}}</td>
                    <td>{{item.orderNum}}</td>
                    <td>{{item.rechargeMobile}}</td>
                    <td>{{item.businessType==1?"流量":item.businessType==2?"话费":item.businessType==3?"视频会员":""}}</td>
                    <td>{{item.operator==8?"乐视":item.operator==7?"搜狐":item.operator==6?"腾讯":item.operator==5?"爱奇艺":item.operator==4?"优酷":item.operator==3?"电信":item.operator==2?"联通":item.operator==1?"移动":" "}}</td>
                    <td>{{item.value}}</td>
                    <td>{{item.packageSize}}</td>
                    <td>{{item.userCnName}}</td>
                    <td>{{item.supplyName}}</td>
                    <td>{{item.submitTime.substring(0,19)}}</td>
                    <td>{{item.minute}}</td>
                    <td style="display:none">{{item.callbackUrl}}</td>
                    <td style="display:none">{{item.customId}}</td>
                    <td style="display:none">{{item.token}}</td>
                </tr>
                </tbody>
            </table>
             <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>
            <hr>
            <div class="am-cf">
                共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                <div class="am-fr">
                    <ul class="am-pagination" style="margin: 0">
                        <li><a href="javascript:void(0);" onclick="reloadPage('{{page.dq-1}}');">上一页</a></li>
                        <li class="am-disabled"><a
                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                        <li><a href="javascript:void(0);"  onclick="reloadPage('{{page.dq+1}}');">下一页</a></li>
                        <li class="am-disabled"><a href="#">|</a></li>
                        <li>
                        <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                        </li>
                        <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();"  style="padding: .5rem .4rem;">GO</a></li>
                    </ul>
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
                                        <input type="checkbox" name="cxmz" value="{{s.code}}" v-model="cx.mz"
                                               @click="cxmzcheck()">{{s.name}}
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
</div>
<script>
    var startDate = {
        elem: '#startDate',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2000-01-01 23:59:59', //设定最小日期为当前日期
        max: '2099-06-16 23:59:59', //最大日期
        istime: true,
        istoday: false,
        choose: function(datas){
            endDate.min = datas; //开始日选好后，重置结束日的最小日期
            endDate.startDate = datas //将结束日的初始值设定为开始日
        }
    };
    var endDate = {
        elem: '#endDate',
        format: 'YYYY-MM-DD hh:mm:ss',
        min: '2000-01-01 23:59:59',
        max: '2099-06-16 23:59:59',
        istime: true,
        istoday: false,
        choose: function(datas){
            startDate.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(startDate);
    laydate(endDate);
</script>
<script>
    var arr = [];
    var dataAll = {
        json: [],
        cx: {
            mz: [],
        },
        optionMz:[],
        checkID:[],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    var cxmzarr = [];
    var Off = true;
    var vm = new Vue({
        el: "#content",
        data:dataAll,
        methods: {
            remove: function (index) {
                this.json.splice(index, 1)
            },
            checked: function () {
                if (Off) {
                    $("input[name='checkbox']").prop("checked", true);
                    for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                        arr.push($("input[name='checkbox']:checked").eq(i).val());
                    }
                    this.checkID = arr.unique();
                    Off = false;
                } else {
                    this.checkID = [];
                    $("input[name='checkbox']").prop("checked", false);
                    Off = true;
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
                var businessType = $("#businessType").val();
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
                            a.push(this.optionMz[i].name);
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
            clearcheck:function(){
                if ($("input[name='checkbox']:checked").length == this.json.length) {
                    $(".checkall").prop("checked", true);
                } else {
                    $(".checkall").prop("checked", false);
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
                dataAll.optionMz = data;
            }
        });
    }
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
    //查找省份表
    $(function(){
        var pid = $("#provinceId").val();
        $.ajax({
            url:"${pageContext.request.contextPath }/order/dict.do",
            type:"post",
            data:{
                key:pid
            },
            dataType:"json",
            success:function(obj){
                for (var i in obj) {
                    $("#provinceId").append("<option value='" + obj[i].key + "'>" + obj[i].value + "</option>");
                }
            }
        });
    });
    function reloadPage(pageNum){
        $(".checkall").prop("checked", false);
        dataAll.checkID = [];
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var toFindListData = $("#toFindListData").val();

        var startDate = $("#startDate").val().split("-")[1];
        var endDate = $("#endDate").val().split("-")[1];
        var startDateOne = $("#startDate").val().split("-")[2];
        var endDateOne = $("#endDate").val().split("-")[2];
        var startDateTwo = $("#startDate").val().split("-")[0];
        var endDateTwo = $("#endDate").val().split("-")[0];

        //开始时间
        var startTime=$("#startDate").val();
        //结束时间
        var endTime=$("#endDate").val();
        var rechargeMobile=$("#rechargeMobile").val();
        var userCnName=$("#userCnName").val();
        var supplyName=$("#supplyName").val();
        var operator=$("#operator").val();
        var provinceId=$("#provinceId").val();
        var businessType=$("#businessType").val();

        if(startDateTwo != endDateTwo && startDateTwo != "" && endDateTwo != "") {
            alert("请选择相同年份");
            return false;
        }else if(startDateTwo != "" && endDateTwo == "") {
            alert("请输入结束日期");
            return false;
        }else if(startDateTwo == "" && endDateTwo != ""){
            alert("请输入开始日期");
            return false;
        }else if(startDate > endDate){
            alert("开始日期不可以大于结束日期");
            return false;
        }else if(startDate !== endDate) {
            alert("请选择相同月份");
            return false;
        }else if(startDateOne > endDateOne){
            alert("开始日期不可以大于结束日期");
            return false;
        }else{
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url: "/order/resultUnknownList?pageNumber="+pageNum+"&pageSize=10",
                type:'POST',
                dataType:"json",
                data:{
                    startTime:startTime,
                    endTime:endTime,
                    rechargeMobile:rechargeMobile.trim(),
                    userCnName:userCnName.trim(),
                    supplyName:supplyName.trim(),
                    operator:operator.trim(),
                    value:provinceId,
                    businessType:businessType,
                    subData:toFindListData
                },
                async:false,
                error:function(){
                    layer.msg("错误！");
                    layer.close(loadingIndex);
                },
                success: function(data){
                    if(data.list.length>0){
                        $(".sj").hide();
                        layer.close(loadingIndex);
                        dataAll.json = data.list;
                        dataAll.page.ts = data.total;
                        dataAll.page.dq = data.pageNum;
                        dataAll.page.all = data.pages;
                    }else{
                        layer.close(loadingIndex);
                        dataAll.json = data.list;
                        dataAll.page.ts = 0;
                        dataAll.page.dq = 0;
                        dataAll.page.all = 0;
                        $(".sj").show();
                    }
                }
            });
        }

    }
    //到几页
    function gotoPage(){
        var pageNum = $("#gotoPage").val();
        if(pageNum==''){
            layer.tips("请输入页数！","#gotoPage",{tips: 3});
            return;
        }
        reloadPage(pageNum);
    }

    //设置成功
    function succeed(){
        var startTime=$("#startDate").val();
        var len = $("input[name='checkbox']:checked").length;
        if (len == 0) {
            alert("请选择想要修改的序号！");
        }else{
            var check=dataAll.checkID.toString();
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url: "${pageContext.request.contextPath}/order/successfullyOrder.do",
                type:'POST',
                dataType:"json",
                async:false,
                data:{
                    'ids':check,
                    startTime:startTime
                },
                error:function(){
                },
                success: function(obj){
                    layer.close(loadingIndex);
                    if(obj){
                        alert("OK!");
                        location.reload();
                    }else{
                        alert("NO!");
                        location.reload();
                    }
                }
            });
        }
    }

    //设置失败
    function failed(){
        var startTime=$("#startDate").val();
        var len = $("input[name='checkbox']:checked").length;
        if (len == 0) {
            alert("请选择想要修改的序号！");
        }else{
            var check=dataAll.checkID.toString();
            var loadingIndex = layer.open({type:3});
            $.ajax({
                url: "${pageContext.request.contextPath}/order/defeatedOrder.do",
                type:'POST',
                dataType:"json",
                async:false,
                data:{
                    'ids':check,
                    startTime:startTime
                },
                error:function(){
                },
                success: function(obj){
                    layer.close(loadingIndex);
                    if(obj){
                        alert("OK!");
                        location.reload();
                    }else{
                        alert("NO!");
                        location.reload();
                    }
                }
            });
        }
    }

</script>
</body>
</html>
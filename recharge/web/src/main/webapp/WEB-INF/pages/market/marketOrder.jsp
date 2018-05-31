<%--
  Created by IntelliJ IDEA.
  User: lihuimin流量话费订单查询
  Date: 2017/9/13
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html>
        <head>
            <title>Title</title>
            <%@include file="/WEB-INF/pages/common/head.jsp"%>
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
        
        html {
            moz-user-select: inherit;
            -moz-user-select: inherit;
            -o-user-select: inherit;
            -khtml-user-select: inherit;
            -webkit-user-select: inherit;
            -ms-user-select: inherit;
            user-select: inherit;
        }
        
        .am-btn {
            border-radius: 5px;
        }
        
        input,
        select {
            color: #848181;
            font-size: 12px;
            height: 20px;
        }
        
        .select1 {
            height: 30px;
            border-color: #ccc;
        }
        
        .am-table>caption+thead>tr:first-child>td,
        .am-table>caption+thead>tr:first-child>th,
        .am- table>colgroup+thead>tr:first-child>td,
        .am-table>colgroup+thead>tr:first-child>th,
        .am- table>thead:first-child>tr:first-child>td,
        .am-table>thead:first-child>tr:first-child>th {
            border-top: 0;
            font-size: 12px;
        }
        
        .am-table>tbody>tr>td,
        .am-table>tbody>tr>th,
        .am-table>tfoot>tr>td,
        .am-table>tfoot>tr>th,
        .am- table>thead>tr>td,
        .am-table>thead>tr>th {
            padding: .7rem;
            line-height: 1.6;
            vertical-align: top;
            border-top: 1px solid #ddd;
            font-size: 12px;
        }
        
        * {
            margin: 0;
            padding: 0;
            list-style: none;
            font-size: 12px;
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
        .demo2 {
            margin: 40px 0;
        }
        
        .demo1 input,
        .demo2 input {
            height: 35px;
            background-image: none;
            font-size: 14px;
            padding-left: 10px;
        }
        
        #operate {
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
        
        .fu div {
            float: left;
            margin-right: 20px;
            line-height: 40px;
        }
        
        .am-checkbox input[type=checkbox],
        .am-checkbox-inline input[type=checkbox],
        .am-radio input[type=radio],
        .am-radio-inline input[type=radio] {
            float: left;
            margin-left: -20px;
            outline: 0;
            margin-top: 2px;
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
            <div class="admin-content-body" style="">
                <div class="admin-content" id="content" v-cloak>
                    <div class="am-tab-panel am-active" id="tab1">
                        <input type="hidden" value="${orderNum}" id="orderNumBacktrack">
                        <div class="am-form-inline" role="form">
                            <div class="am-form-group demo1" style="margin-top: 10px">
                                <input class="inline laydate-icon" name="orderTimeOne" id="startDate" placeholder="开始时间" style="width:150px; margin-right:2px;height: 30px;font-size: 12px;">
                            </div>
                            <div class="am-form-group demo2" style="margin-top: 10px">
                                <input class="inline laydate-icon" name="orderTimeTwo" id="endDate" placeholder="结束时间" style="width:150px;height: 30px;font-size: 12px;">
                            </div>
                            <div class="am-form-group" style="margin-top: 10px">
                                <input name="rechargeMobile" id="recharge_mobile" type="text" class="am-form-field" placeholder="手机号码" style="width: 100px;float: left;height: 30px;font-size: 12px;">
                                <span style="line-height:26px;padding: 0 10px;" class="pl" @click="plClick()"><a href="javascript:;">批量</a></span>
                            </div>
                            <div class="am-form-group" style="margin-top: 10px">
                                <input name="userCnName" id="user_cn_name" type="text" class="am-form-field" placeholder="商户简称" style="width: 100px;float: left;height: 30px;font-size: 12px;">
                            </div>
                            <div class="am-form-group" style="margin-top: 10px">
                                <input name="customId" id="customId" type="text" class="am-form-field" placeholder="商家订单号" style="width: 100px;float: left;height: 30px;font-size: 12px;">
                            </div>
                            <div class="am-form-group" style="margin-top: 10px">
                                <input name="orderNum" id="orderNum" type="text" class="am-form-field" placeholder="平台订单号" style="width: 100px;float: left;height: 30px;font-size: 12px;">
                            </div>
                            <div class="am-form-group" style="margin-top: 10px">
                                <select name="businessType" id="business_type" class="select1" style="width: 100px;float: left;" @change="clearmz()">
                                    <option value="">商品类型</option>
                                    <option value="1">流量</option>
                                    <option value="2">话费</option>
                                    <option value="3">视频会员</option>
                                </select>
                            </div>
                            <div class="am-form-group" style="margin-top: 10px">
                                <select name="operator" id="operator" class="select1" style="width: 100px;float: left;">
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
                            <!-- <div class="clearfix"></div> -->
                            <div class="am-form-group" style="margin-top: 10px">
                                <select name="key" id="province_id" class="select1" style="width: 100px;float: left;">
                                    <option value="">省份</option>
                                </select>
                            </div>
                            <div class="am-form-group" style="margin-top: 10px">
                                <select name="status" id="status" class="select1" style="width: 100px;float: left;" placeholder="订单状态">
                                    <option value="">订单状态</option>
                                    <option value="1">创建订单</option>
                                    <option value="2">无可充货架</option>
                                    <option value="3">无可充渠道</option>
                                    <option value="4">余额不足</option>
                                    <option value="5">缓存中</option>
                                    <option value="6">充值中</option>
                                    <option value="7">成功</option>
                                    <option value="8">失败</option>
                                    <option value="9">支付失败</option>
                                </select>
                            </div>
                            <button type="button" class="am-btn am-btn-warning" style="margin-top: 10px;font-size:12px;" onclick="reloadPage(1)">查询
                            </button>
                        </div>
                        <div class="fu">
                            <div>订单数<span> {{page.ts}} </span></div>
                            <div>交易额 <span id="volume"></span></div>
                            <div>支付总额 <span id="sumPay"></span></div>
                        </div>
                        <div class="tableHeight" style="overflow-y: scroll;max-height: 400px;clear: both;">
                            <table class="am-table am-table-striped am-table-hover" style="min-width: 1700px;">
                                <thead>
                                    <tr>
                                        <th>
                                            <label class="am-checkbox-inline" style="font-size: 12px;">
                                                <input type="checkbox" @click="checked" class="checkall"> 序号
                                            </label>
                                        </th>
                                        <th>订单状态</th>
                                        <th>手机号码</th>
                                        <th>商品类型</th>
                                        <th>运营商</th>
                                        <th>省份</th>
                                        <th>商品</th>
                                        <th>面值</th>
                                        <th>支付金额</th>
                                        <th>折扣</th>
                                        <th>商户简称</th>
                                        <th>下单时间</th>
                                        <th>充值完成时间</th>
                                        <th>平台订单号</th>
                                        <th>商家订单号</th>
                                        <th>是否退款</th>
                                        <th>推送状态</th>
                                        <%--<th>操作</th>--%>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="item in json">
                                        <td>
                                            {{$index+1}}
                                        </td>
                                        <%----%>
                                        <td v-if="item.status==1" style="color: #F37B1D;">创建订单</td>
                                        <td v-if="item.status==2" style="color: #F37B1D;">无可充货架</td>
                                        <td v-if="item.status==3" style="color: #F37B1D;">无可充渠道</td>
                                        <td v-if="item.status==4" style="color: #F37B1D;">余额不足</td>
                                        <td v-if="item.status==5" style="color: #F37B1D;">缓存中</td>
                                        <td v-if="item.status==6" style="color: #F37B1D;">充值中</td>
                                        <td v-if="item.status==7" style="color: green;">成功</td>
                                        <td v-if="item.status==8" style="color: #F37B1D;">失败</td>
                                        <td v-if="item.status==9" style="color: #F37B1D;">支付失败</td>
                                        <%----%>
                                        <td>{{item.rechargeMobile}}</td>
                                        <td>{{item.businessType==1?"流量":item.businessType==2?"话费":""}}</td>
                                        <td>{{item.operator==3?"电信":item.operator==2?"联通":item.operator==1?"移动":" "}}</td>
                                        <td>{{item.value}}</td>
                                        <td>{{item.packageSize}}</td>
                                        <td>{{item.money}}</td>
                                        <td>{{item.amount}}</td>
                                        <td>{{item.orderPriceDiscount}}</td>
                                        <td>{{item.userCnName}}</td>
                                        <td>{{item.orderTime.substring(0,19)}}</td>
                                        <td>{{item.orderReturnTime.substring(0,19)}}</td>
                                        <td><a class="tips" href="javascript:void(0);" class="orderNum" onclick="order_num('{{item.orderNum}}','{{item.orderTime}}')">{{item.orderNum}}</a></td>
                                        <td class="tips2">{{item.customId}}</td>
                                        <td>{{item.refundStatus==2?"退款失败":item.refundStatus==1?"退款成功":" "}}</td>
                                        <td>{{item.pushStatus==1?"推送成功":item.pushStatus==2?"推送失败":item.pushStatus==3?"未推送":""}}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>
                        <div class="am-cf" style="line-height: 40px;">
                            共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                            <div class="am-fr">
                                <ul class="am-pagination" style="margin: 0">
                                    <li><a href="javascript:void(0);" onclick="reloadPage('{{page.dq-1}}');">上一页</a></li>
                                    <li class="am-disabled"><a href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                    <li><a href="javascript:void(0);" onclick="reloadPage('{{page.dq+1}}');">下一页</a></li>
                                    <li class="am-disabled"><a href="#">|</a></li>
                                    <li>
                                        <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                                    </li>
                                    <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();" style="padding: .5rem .4rem;">GO</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <%--批量弹窗--%>
                    <div class="am-modal am-modal-no-btn" tabindex="-1" id="plClick">
                        <div class="am-modal-dialog">
                            <div class="am-modal-hd">
                                <a href="javascript: void(0)" class="am-close am-close-spin" @click="closemz()">&times;</a>
                            </div>
                            <hr>
                            <div class="am-modal-bd">
                                <form class="am-form am-form-horizontal">
                                    <div class="am-form-group">
                                        <p>一次至多输入50个号码,以回车键输入</p>
                                        <textarea name="" id="phoneNum" cols="30" rows="10"></textarea>
                                    </div>
                                </form>
                            </div>
                            <hr>
                            <div class="am-form-group">
                                <div>
                                    <button type="button" class="am-btn am-btn-warning"
                                            style="width: 120px;margin: auto;" onclick="phoneNumSure()">确定
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
            </div>
        </body>
        <script>

            var hei = $(window).height() - 160;
        $(".tableHeight").css("max-height",hei);
        $(".tableHeight").css("height",hei);
        var startDate = {
            elem: '#startDate',
            format: 'YYYY-MM-DD hh:mm:ss',
            min: '2000-01-01 23:59:59', //设定最小日期为当前日期
            max: '2099-06-16 23:59:59', //最大日期
            istime: true,
            istoday: false,
            choose: function(datas) {
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
            choose: function(datas) {
                startDate.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };
        laydate(startDate);
        laydate(endDate);

        </script>
        <script>

            function phoneNumSure(){
                var number = $("#phoneNum").val().split("\n").splice(",");
                if(number.length>50){
                    alert("一次至多输入50个号码！")
                }else{
                    $("#recharge_mobile").val(number);
                    $("#plClick").modal("close");
                    $("#phoneNum").val("");
                }
            }
        function order_num(num, orderTime) {
            var index = layer.open({
                type: 2,
                content: "${pageContext.request.contextPath}/order/water.do?orderNum=" + num + "&orderTime=" + orderTime,
                area: ['320px', '195px'],
            });
            layer.full(index);
        }
        var dataAll = {
            json: [],
            page: {
                ts: "",
                dq: "",
                all: ""
            }
        };
        var arr = [];
        var Off = true;
        var vm = new Vue({
            el: "#content",
            data: dataAll,
            methods: {
                plClick:function(){
                    $("#plClick").modal({
                        closeViaDimmer: false
                    });
                },
                remove: function(index) {
                    this.json.splice(index, 1)
                },
                closemz:function(){
                    var a =[];
                    $("#mzChose").modal("close");
                    $("#plClick").modal("close");
                    $("#money").val(a);
                }
            }
        });

        Array.prototype.unique = function() {
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
        $(function() {
            var pid = $("#province_id").val();
            $.ajax({
                url: "${pageContext.request.contextPath }/order/dict.do",
                //async:false,
                type: "post",
                data: {
                    key: pid
                },
                dataType: "json",
                success: function(obj) {
                    for (var i in obj) {
                        $("#province_id").append("<option value='" + obj[i].key + "'>" + obj[i].value + "</option>");
                    }
                }
            });
        });

        function reloadPage(pageNum) {
            $(".checkall").prop("checked", false);
            if (pageNum == 0) {
                layer.msg("当前是第一页啦，没有上一页！");
                return;
            }
            var startDate = $("#startDate").val().split("-")[1];
            var endDate = $("#endDate").val().split("-")[1];
            var startDate1 = $("#startDate").val().split("-")[2];
            var endDate1 = $("#endDate").val().split("-")[2];
            var startDate2 = $("#startDate").val().split("-")[0];
            var endDate2 = $("#endDate").val().split("-")[0];
            //开始时间
            var startTime = $("#startDate").val();
            //结束时间
            var endTime = $("#endDate").val();
            var recharge_mobile = $("#recharge_mobile").val();
            var user_cn_name = $("#user_cn_name").val();
            var orderNum = $("#orderNum").val();
            var customId = $("#customId").val();
            var operator = $("#operator").val();
            var province_id = $("#province_id").val();
            var business_type = $("#business_type").val();
            var status = $("#status").val();

            if (startDate2 != endDate2 && startDate2 != "" && endDate2 != "") {
                alert("请选择相同年份");
                return false;
            } else if (startDate2 != "" && endDate2 == "") {
                alert("请输入结束日期");
                return false;
            } else if (startDate2 == "" && endDate2 != "") {
                alert("请输入开始日期");
                return false;
            } else if (startDate > endDate) {
                alert("开始日期不可以大于结束日期");
                return false;
            } else if (startDate !== endDate) {
                alert("请选择相同月份");
                return false;
            } else if (startDate1 > endDate1) {
                alert("开始日期不可以大于结束日期");
                return false;
            } else {
                var loadingIndex = layer.open({
                    type: 3
                });
                $.ajax({
                    url: "/order/orderMarketList?pageNumber=" + pageNum + "&pageSize=50",
                    type: 'POST',
                    dataType: "json",
                    data: {
                        startTime: startTime,
                        endTime: endTime,
                        rechargeMobile: recharge_mobile.trim(),
                        userCnName: user_cn_name.trim(),
                        orderNum: orderNum.trim(),
                        customId: customId.trim(),
                        operator: operator.trim(),
                        value: province_id,
                        businessType: business_type,
                        status: status,
                    },
                    async: false,
                    error: function() {
                        layer.msg("错误！");
                        layer.close(loadingIndex);
                    },
                    success: function(data) {
                        if (data.list.length > 0) {
                            $(".sj").hide();
                            layer.close(loadingIndex);
                            dataAll.json = data.list;
                            dataAll.page.ts = data.total;
                            dataAll.page.dq = data.pageNum;
                            dataAll.page.all = data.pages;
                        } else {
                            layer.close(loadingIndex);
                            dataAll.json = data.list;
                            dataAll.page.ts = 0;
                            dataAll.page.dq = 0;
                            dataAll.page.all = 0;
                            $(".sj").show();
                            $("#volume").text("0");
                            $("#sumPay").text("0");
                        }
                    }
                });
                $.ajax({
                    url: "/order/selectMarketOrderList.do",
                    type: 'POST',
                    dataType: "json",
                    data: {
                        startTime: startTime,
                        endTime: endTime,
                        rechargeMobile: recharge_mobile.trim(),
                        userCnName: user_cn_name.trim(),
                        orderNum: orderNum.trim(),
                        customId: customId.trim(),
                        operator: operator.trim(),
                        value: province_id,
                        businessType: business_type,
                        status: status,
                    },
                    async: false,
                    success: function(data) {
                        var amount = (data[0].amount);
                        var money = (data[0].money);
                        $("#volume").text(money);
                        $("#sumPay").text(amount);
                    }
                })
            }
        }

        //到几页
        function gotoPage() {
            var pagenum = $("#gotoPage").val();
            if (pagenum == '') {
                layer.tips("请输入页数！", "#gotoPage", {
                    tips: 3
                });
                return;
            }
            reloadPage(pagenum);
        }

        </script>
</html>

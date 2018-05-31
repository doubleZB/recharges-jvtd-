<%--
  Created by IntelliJ IDEA.
  User: 缓存订单
  Date: 2017/8/11
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/static/css/Hui-iconfont/1.0.8/iconfont.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/h-ui/css/animations.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/h-ui/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/assets/css/amazeui.min.css"/>
    <!--[if lt IE 9]>
    <link href="<%=request.getContextPath()%>/static/css/h-ui/css/H-ui.ie.css" rel="stylesheet" type="text/css"/>
    <![endif]-->
    <!--[if IE 6]>
    <script type="text/javascript" src="resource/lib/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>缓存订单管理</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/css/h-ui/js/modernizr.custom.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/css/h-ui/js/H-ui.min.js"></script>
    <script src="${path}/static/js/layer-v3.0.3/layer/layer.js"></script>
    <script src="${path}/static/laydate/laydate.js"></script>
    <script src="https://cdn.bootcss.com/vue/2.4.0/vue.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/css/h-ui/js/app.js"></script>
    <script src="<%=request.getContextPath()%>/static/assets/js/amazeui.min.js"></script>
</head>
<body>
<div id="app" v-cloak>
    <div class="info-list">
        <div class="search-box mt-20">

            <input type="text" class="input-text radius" placeholder="开始时间" name="start_time" id="start_time"
                   onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
            <input type="text" class="input-text radius" placeholder="结束时间" name="end_time" id="end_time"
                   onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
            <input type="text" class="input-text radius" placeholder="商户" name="user_name" id="user_name">
            <input type="text" class="input-text radius" placeholder="供应商" name="supply_name" id="supply_name">
            <select class="select radius" name="business_type" id="business_type">
                <option value="">全部</option>
                <option value="1">流量</option>
                <option value="2">话费</option>
            </select>
            <select class="select radius" name="operator" id="operator">
                <option value="">运营商</option>
                <option value="1">移动</option>
                <option value="2">联通</option>
                <option value="3">电信</option>
            </select>
            <select class="select radius" name="province" id="province">
                <option value="">省份</option>
                <c:forEach var="province" items="${dict}">
                    <option value="${province.id}">${province.value}</option>
                </c:forEach>
            </select>
            <input type="text" class="input-text radius" placeholder="充值手机号" name="mobile" id="mobile">
            <a class="	btn btn-primary radius size-S" @click="loadPage(1)">查询</a>
        </div>
        <div class="search-box mt-10">
            <span class="pos-r"> <input type="checkbox" class="checkall"> 全选 </span>
            <button class="btn btn-primary radius continue">继续充值</button>
            <button class="btn btn-primary radius returnfalse">按商户要求返失败</button>
            <span class="pos-r" style="top: 3px; left: 10px;"> <input type="checkbox" id="checkalls" class="checkalls"> 按条件一键修改 </span>
        </div>
        <table class="table table-border table-bordered table-hover radius">
            <tr>
                <th style="width: 60px">
                    序号
                </th>
                <th>平台订单号</th>
                <th>商户</th>
                <th>商户订单号</th>
                <th>手机号</th>
                <th>商品类型</th>
                <th>运营商</th>
                <th>省份</th>
                <th>商品</th>
                <th>支付金额</th>
                <th>供应商</th>
                <th>缓存时间</th>
            </tr>
            <tr v-for="(list,index) in lists">
                <td><input type="checkbox" name="radioS" style="float: left;" :value="list.orderNum" class="checked">{{index+1}}
                </td>
                <td>{{list.orderNum}}</td>
                <td>{{list.userName}}</td>
                <td>{{list.customid}}</td>
                <td>{{list.mobile}}</td>
                <td>{{list.businessType | getBusinessType}}</td>
                <td>{{list.operator | getOperator}}</td>

                <td>{{list.province | getProvince}}</td>
                <td>{{list.productName}}</td>
                <td>{{list.payCount}}元</td>
                <td>{{list.supplyName}}</td>
                <td>{{list.cacheTime | getCacheTime}}</td>
            </tr>
        </table>
        <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

        <hr>
        <div class="am-cf page">
            每页显示&nbsp;
            <select name="eachPageNumber" id="rangNum" @change="loadPage(1)">
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>&nbsp;条&nbsp;&nbsp共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span>
            页
            <div class="am-fr">
                <ul class="am-pagination" style="margin: 0">
                    <li><a href="javascript:void(0);" @click="prePage(parseInt(page.dq)-1)">上一页</a></li>
                    <li class="am-disabled"><a href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                    <li><a href="javascript:void(0);" @click="nextPage(parseInt(page.dq)+1)">下一页</a></li>
                    <%--<li class="am-disabled"><a href="#">|</a></li>--%>
                    <li>
                        <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;"
                               id="goto-page-num"
                               onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                    </li>
                    <li class="am-active"><a href="javascript:void(0);" @click="gotoPage();"
                                             style="padding: .5rem .4rem;">GO</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script>

    //格式化日期
    Date.prototype.format = function (format) {
        var date = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S+": this.getMilliseconds()
        };
        if (/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
        }
        for (var k in date) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1
                        ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
            }
        }
        return format;
    }

    //所有省数组
    var dict = ${dict_json};
    var app = new Vue({
        el: "#app",
        data: {
            lists: [],
            page: {
                ts: 0,
                dq: 0,
                all: 0
            },
        },
        methods: {
            prePage: function (pageNum) {
                if (pageNum == 0) {
                    layer.msg("已经是第一页了")
                    return false
                }

                reloadPage(pageNum)
            },
            nextPage: function (pageNum) {
                if (pageNum > this.page.all) {
                    layer.msg("已经是最后一页了")
                    return false
                }

                reloadPage(pageNum)
            },
            loadPage: function (pageNum) {
                reloadPage(pageNum)
            },
            gotoPage: function () {

                var pageNum = $("#gotoPage").val();
                if (pageNum <= 0) {
                    layer.msg("已经是第一页了")
                    return false
                }
                if (pageNum > this.page.all) {
                    layer.msg("已经是最后一页了")
                    return false
                }
                if (pageNum == '') {
                    layer.tips("请输入页数！", "#gotoPage", {
                        tips: 3
                    });
                    return;
                }
                reloadPage(pageNum);
            },

            modifyData: function (id) { //修改数据方法

            },
            deleteData: function (id) {
                //执行删除数据方法
                $mc.layer.confirm("确定要删除该条数据吗？", function () {

                })
            },
            resertPage: function () {
                $mc.layer.confirm("确定要清空所填写的数据吗？", function () {

                })
            }
        },
        filters: {
            getStr: function (operator) {
                var opraters = operator.split(',');
                var str = ""
                for (var i in opraters) {
                    if (opraters[i] == 0) {
                        str += "全部运营商";
                    }
                    if (opraters[i] == 1) {
                        str += "移动";
                    }
                    if (opraters[i] == 2) {
                        str += "联通";
                    }
                    if (opraters[i] == 3) {
                        str += "电信";
                    }
                    if (i < opraters.length - 1) {
                        str += ","
                    }
                }
                return str;
            },
            getPositioncode: function (positionCode) {
                if (!positionCode) {
                    return ""
                }
                var list = positionCode.split(',');
                var str = ""
                for (var i in list) {
                    if (list[i] == '111') {
                        str += "全部移动流量";
                    }
                    if (list[i] == '222') {
                        str += "全部联通流量";
                    }
                    if (list[i] == '333') {
                        str += "全部电流";
                    }
                    if (list[i] == '444') {
                        str += "全移话";
                    }
                    if (list[i] == '555') {
                        str += "全联话";
                    }
                    if (list[i] == '666') {
                        str += "全电话";
                    }
                    if (i < list.length - 1) {
                        str += ","
                    }
                }
                return str;
            },
            getStatus: function (status) {
                if (status == 0) {
                    return "开启"
                }
                if (status == 1) {
                    return "关闭"
                }
            },
            getBusinessType: function (business_type) {
                if (business_type == 1) {
                    return "流量";
                }
                if (business_type == 2) {
                    return "话费";
                }
            },
            getOperator: function (operator) {
                if (operator == 1) {
                    return "移动";
                }
                if (operator == 2) {
                    return "联通";
                }
                if (operator == 3) {
                    return "电信";
                }
            },
            getProvince: function (province) {
                if (province == 0) {
                    return "全部省份"
                }
                for (var i in dict) {
                    if (dict[i].id == province) {
                        return dict[i].value
                    }
                }
            },
            getCacheTime: function (cache_time) {
                var date = new Date();
                date.setTime(cache_time);
                return date.format('yyyy-MM-dd hh:mm:ss')
            },
        },
    })
    reloadPage(1)

    function reloadPage(pageNum) {
        var start_time = $("#start_time").val();
        var end_time = $("#end_time").val();
        var user_name = $("#user_name").val().trim();
        var supply_name = $("#supply_name").val().trim();
        var business_type = $("#business_type").val();
        var operator = $("#operator").val();
        var province = $("#province").val();
        var mobile = $("#mobile").val().trim();

        var rangNum = $("#rangNum").val();
        /*var url = ""
         if (start_time != "") {
         url += "&startTime=" + start_time
         }
         if (end_time != "") {
         url += "&endTime=" + end_time
         }
         if (user_name != "") {
         url += "&userName=" + user_name
         }
         if (supply_name != "") {
         url += "&supplyName=" + supply_name
         }
         if (business_type != "") {
         url += "&businessType=" + business_type
         }
         if (operator != "") {
         url += "&operator=" + operator
         }
         if (province != "") {
         url += "&province=" + province
         }
         if (mobile != "") {
         url += "&mobile=" + mobile
         }*/

        var loadingIndex = layer.load();
        $.ajax({
            url: "/cache/cacheOrderSearch?pageNumber=" + pageNum + "&pageSize=" + rangNum,
            type: 'POST',
            dataType: "json",
            data: {
                startTime: start_time,
                endTime: end_time,
                userName: user_name,
                supplyName: supply_name,
                businessType: business_type,
                operator: operator,
                province: province,
                mobile: mobile
            },
            async: false,
            error: function () {
                layer.msg("错误！");
                layer.closeAll('loading');
            },
            success: function (data) {
                var list = data.list
                if (list && list.length > 0) {
                    app.lists = list;
                    app.page.ts = data.total;
                    app.page.dq = data.pageNum;
                    app.page.all = data.pages;
                    $(".sj").hide();
                    $(".page").show();
                    $('.continue').attr("disabled",false);
                } else {
                    app.lists = list;
                    $(".sj").show();
                    $(".page").hide();
                    app.page.ts = 0;
                    app.page.dq = 0;
                    app.page.all = 0;
                    $('.continue').attr("disabled",true);
                }
                layer.closeAll('loading');
            }
        });
    }

    $(function () {
        //点击全部勾选
        $(".checkall").change(function () {
            var v = $(this).is(":checked")
            $(".checked").prop("checked", v)
        })

        $(".checked").click(function () {
            if ($("input[name='radioS']:checked").length == $("input[name='radioS']").length) {
                $(".checkall").prop("checked", true);
            } else {
                $(".checkall").prop("checked", false);
            }
        });


        //点击继续充值
        continueCharge()
    })

    function continueCharge() {
        $(".continue,.returnfalse").on("click", function () {

            var start_time = $("#start_time").val();
            var end_time = $("#end_time").val();
            var user_name = $("#user_name").val().trim();
            var supply_name = $("#supply_name").val().trim();
            var business_type = $("#business_type").val();
            var operator = $("#operator").val();
            var province = $("#province").val();
            var mobile = $("#mobile").val().trim();
            var rangNum = $("#rangNum").val();

            var that = $(this)
            var ischeckalls = 1;
            if ($('#checkalls').is(':checked')) {
                ischeckalls = 2;
            }
            var order_numbers = []
            $(".checked:checked").each(function () {
                order_numbers.push($(this).val())
            })
            if (order_numbers.length > 0 || ischeckalls == 2) {
                var status, msg;
                if ($(this).hasClass("continue")) {
                    status = 2
                    msg = "确定要继续充值？"
                }
                if ($(this).hasClass("returnfalse")) {
                    status = 3
                    msg = "确定要返回失败？"
                }
                if (confirm(msg)) {
                    that.attr("disabled", "disabled")
                    $.post("/cache/continueCharge",
                            {
                                orderNums: order_numbers,
                                OrderStatus: status,
                                startTime: start_time,
                                endTime: end_time,
                                userName: user_name,
                                supplyName: supply_name,
                                businessType: business_type,
                                operator: operator,
                                province: province,
                                mobile: mobile,
                                ischeckalls: ischeckalls
                            }, function (data) {
                                if (data) {
                                    layer.msg("操作成功")
                                    $(".checked").prop("checked", false);
                                    $("#checkalls").prop("checked", false);
                                } else {
                                    layer.msg("操作失败")
                                }
                                that.removeAttr("disabled")
                                setTimeout(function () {
                                    reloadPage(app.page.dq)
                                }, 1000)
                            })
                } else {
                    return false
                }
            } else {
                layer.msg("请选择一条订单")
                return false
            }
        })
    }
</script>

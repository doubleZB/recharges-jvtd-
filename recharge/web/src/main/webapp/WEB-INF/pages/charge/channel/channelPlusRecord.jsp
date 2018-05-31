<%--
  Created by IntelliJ IDEA.
  User: lyp
  Date: 2017/4/17
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.util.Properties" %>
<html>
<%
    String path = "";
    String FileName = "";

    Properties pro = new Properties();
    String realpath = request.getRealPath("/WEB-INF/classes");
    try{
        //读取配置文件
        FileInputStream in = new FileInputStream(realpath+"/config.properties");
        pro.load(in);
    }
    catch(FileNotFoundException e){
        out.println(e);
    }
    catch(IOException e){out.println(e);}

//通过key获取配置文件
    path = pro.getProperty("certificate_image");
    FileName = pro.getProperty("FileName");
%>
<head>
    <meta charset="utf-8">
    <title>渠道加款记录</title>
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css" />
    <link rel="stylesheet" href="${path}/static/css/authentication.css">
</head>
<style>
    li {
        list-style-type: none;
    }

    .am-modal-bd {
        padding: 0;
    }

    .am-modal-bd td {
        margin-top: 20px;
        display: inline-block;
    }

    address,
    blockquote,
    dl,
    fieldset,
    figure,
    hr,
    ol,
    p,
    pre,
    ul {
        margin-bottom: 0;
    }

    .parent_list {
        position: relative;
    }

    .list {
        display: none;
        position: absolute;
        background: #fff;
        width: 300px;
        margin-top: 16px;
        border: 1px solid #dedede;
        text-align: left;
    }

    .list ul {
        padding: 0;
        margin: 0;
        height: 214px;
        overflow: auto;
    }

    .list li {
        padding: 10px 15px;
        border-bottom: 1px solid #dedede;
    }

    .list li:last-child {
        border-bottom: none;
    }
</style>

<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">渠道账户加款记录</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form-inline" role="form">
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" class="am-form-field" id="applyStartTime" placeholder="开始时间" style="width: 200px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" class="am-form-field" id="applyEndTime" placeholder="结束时间" style="width: 200px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" class="am-form-field" id="supplyName" placeholder="供应商名称" style="width: 200px;float: left;">
                </div>
                <%--<div class="am-form-group" style="margin-top: 20px">--%>
                    <%--<input type="text" class="am-form-field" placeholder="账户名" style="width: 200px;float: left;">--%>
                <%--</div>--%>
                <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-top: 20px" onclick="selectChannelAddFund(1)">查询
                </button>
            </form>

            <hr>
            <%--<div style="padding:20px 0;">加款总额：10万元</div>--%>
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>供应商名称</th>
                    <%--<th>账户名</th>--%>
                    <th>加款金额</th>
                    <th>备注</th>
                    <th>加款时间</th>
                    <th>加款凭证</th>
                    <th>运营申请人</th>
                    <th>财务确认人</th>
                    <th>确认时间</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td>{{item.supplyId}}</td>
                    <td style="display:none;">{{item.id}}</td>
                    <%--<td>{{item.zhm}}</td>--%>
                    <td>{{item.amount}}</td>
                    <td>{{item.remark}}</td>
                    <td>{{item.applyTimestr}}</td>
                    <td>
                        <a href="#" @click="pingzheng(item.id)">查看</a>
                    </td>
                    <td>{{item.applyUserid}}</td>
                    <td>{{item.auditUserid}}</td>
                    <td>{{item.auditTimestr}}</td>
                </tr>
                </tbody>
            </table>
            <hr>
            <div class="am-cf">
                每页显示&nbsp;
                <select name="select" id="rangNum" onchange="rangNum()">
                    <option value="0">10</option>
                    <option value="1">20</option>
                    <option value="2">50</option>
                    <option value="3">100</option>
                </select>&nbsp;条&nbsp;&nbsp;共 <span id="countNum">{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                <div class="am-fr">
                    <ul class="am-pagination" style="margin: 0">
                        <li><a href="javascript:void(0);" onclick="selectChannelAddFund('{{page.dq-1}}');">上一页</a></li>
                        <li class="am-disabled"><a href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                        <li><a href="javascript:void(0);"  onclick="selectChannelAddFund('{{page.dq+1}}');">下一页</a></li>
                        <li class="am-disabled"><a href="#">|</a></li>

                        <li class="am-active"><a href="#" style="padding: .5rem .4rem;" onclick="gotoPage()">GO</a></li>
                        <li>
                            <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                        </li>
                        <li class="am-disabled"><a href="#">页</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
    <!-- 弹框 -->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">渠道加款申请
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <table>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">供应商：</td>
                        <td>
                            <div>商通</div>
                        </td>
                    </tr>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">账户名：</td>
                        <td>
                            <div>bjjvtd</div>
                        </td>
                    </tr>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">加款金额：</td>
                        <td>
                            <div>10万元</div>
                        </td>
                    </tr>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">申请备注：</td>
                        <td>
                            <div>XXXXXXXXX</div>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;text-align: right;">加款时间：</td>
                        <td>
                            <div class="am-form-group">
                                <input type="text" class="am-form-field" placeholder="加款时间" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" style="width: 300px;float: left;">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <hr>
            <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin: 20px">提交</button>
        </div>
    </div>
    <!-- 查看凭证 -->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="pingzheng">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">查看凭证
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <img style="width: 100%;" >
                <%--<img src="${path}/static/images/2.png" style="width: 100%;">--%>
            </div>
        </div>
    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/assets/js/amazeui.min.js"></script>
<script src="${path}/static//js/vue.js"></script>
<script src="${path}/static/laydate/laydate.js"></script>
<script src="${path}/static/layer/layer.js"></script>
<script>
    $(function(){
        selectSupplier();
        selectAdminUser();
        selectChannelAddFund(1);
    });
    var vm = new Vue({
        el: "#content",
        data: {
            searchKey: "",
            json: [],
            page: {
                ts: 0,
                dq: 0,
                all: 0
            },
            item: {}
        },
        ready: function() {
            var start = {
                elem: '#applyStartTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                min: '2000-01-01 00:00:00', //设定最小日期为当前日期
                max: '2020-06-16 23:59:59', //最大日期
                istime: true,
                istoday: false,
                choose: function(datas) {
                    end.min = datas; //开始日选好后，重置结束日的最小日期
                    end.start = datas //将结束日的初始值设定为开始日
                }
            };
            var end = {
                elem: '#applyEndTime',
                format: 'YYYY-MM-DD hh:mm:ss',
                min: '2000-01-01 00:00:00',
                max: '2020-06-16 23:59:59',
                istime: true,
                istoday: false,
                choose: function(datas) {
                    start.max = datas; //结束日选好后，重置开始日的最大日期
                }
            };
            laydate(start);
            laydate(end);
        },
        methods: {
            //查看凭证
            pingzheng: function(param) {
                $('#pingzheng').modal('open');
                $.ajax({
                    url: "${pageContext.request.contextPath}/channels/selectImgUrlNameI",
                    async:false,
                    type:'POST',
                    data:{"id":param},
                    dataType:"json",
                    success: function(data){
                        var obj = data;
                        $(obj).each(function (index) {
                            var urlName = obj[index].certificate
                            var url = "//"+'<%=path%>'+"/"+'<%=FileName%>'+"/"+urlName;
                            $("img").attr({ src: url });
                        });
                    }
                });
            }
        }
    })


    /*
     查询供应商
     */
    var supplierName = "";
    function  selectSupplier(){
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectSupplier",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                supplierName = data;
                vm.list = data;
            }
        });
    };
    /*
        获取操作人员
     */
    var adminName=""
    function selectAdminUser(){
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectAdminUser",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                adminName = data;
            }
        });
    };


    /**
     * 数据查询
     * @param param
     */
    function selectChannelAddFund(param,param2){
        var pageNumber = param;
        var num = $("#rangNum").val();
        var nums="";
        if(num==0){
            nums=10;
        }else if(num==1){
            nums=20;
        }else if(num==2){
            nums=50;
        }else if(num==3){
            nums=100;
        }
        if(param2==undefined){
            param2=nums;
        }
        var pageSize = param2;
        var countNum = $("#countNum").val();

        var state = 2;
        var applyStartTime = $("#applyStartTime").val();
        var applyEndTime = $("#applyEndTime").val();
        if(applyStartTime!=null&&applyEndTime==null){
            layer.msg("请选择结束时间");
           // alert("请选择结束时间");
            return ;
        }else if(applyStartTime==null&&applyEndTime!=null){
            layer.msg("请选择开始时间");
            //alert("请选择开始时间");
            return ;
        }


        var supplyName = $("#supplyName").val();
        var obj="";
        var index = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectUserChannelAddFund",
            async:false,
            type:'POST',
            data:{"applyStartTime":applyStartTime,"applyEndTime":applyEndTime,"supplyName":supplyName,"pageNumber":pageNumber,"pageSize":pageSize,"state":state},
            dataType:"json",
            success: function(data){
                layer.close(index);
                if(data==01){
                    layer.msg("没有该供应商");
                    return;
                }
                obj = data.list;
                $(obj).each(function (index) {
                    //匹配供应商
                    $(supplierName).each(function (i){
                        if(obj[index].supplyId==supplierName[i].id){
                            obj[index].supplyId=supplierName[i].name;
                        };
                    });
                    //匹配用户名
                    $(adminName).each(function (i){
                        if(obj[index].applyUserid==adminName[i].id){
                            obj[index].applyUserid=adminName[i].adminName;
                        };
                    });
                    $(adminName).each(function (i){
                        if(obj[index].auditUserid==adminName[i].id){
                            obj[index].auditUserid=adminName[i].adminName;
                        };
                    });
                });
                vm.json = obj;
                vm.page.ts=data.total;
                vm.page.dq=data.pageNum;
                vm.page.all=data.pages;
            }
        });
    };


    //跳页
    function gotoPage(){
        var pagenum = $("#goto-page-num").val();
        if(pagenum==""){
            layer.msg("输入跳转页数");
            return;
        }else {
            selectChannelAddFund(pagenum);
        }
    };
    /**
     * 选择现实多少行
     */
    function rangNum(){
        var num = $("#rangNum").val();
        var nums="";
        if(num==0){
            nums=10;
        }else if(num==1){
            nums=20;
        }else if(num==2){
            nums=50;
        }else if(num==3){
            nums=100;
        }
        var fist = 1;
        selectChannelAddFund(fist,nums);
    };


</script>
</body>

</html>

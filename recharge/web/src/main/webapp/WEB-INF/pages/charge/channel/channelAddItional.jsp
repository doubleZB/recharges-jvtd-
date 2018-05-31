<%@ page import="com.jtd.recharge.dao.po.AdminUser" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.util.Properties" %><%--
  Created by IntelliJ IDEA.
  User: lyp
  Date: 2017/4/18
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title></title>
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
    .am-table>caption+thead>tr:first-child>td, .am-table>caption+thead>tr:first-child>th, .am- table>colgroup+thead>tr:first-child>td, .am-table>colgroup+thead>tr:first-child>th, .am- table>thead:first-child>tr:first-child>td, .am-table>thead:first-child>tr:first-child>th {
        border-top: 0;
        font-size: 14px;
    }
    .am-table>tbody>tr>td, .am-table>tbody>tr>th, .am-table>tfoot>tr>td, .am-table>tfoot>tr>th, .am- table>thead>tr>td, .am-table>thead>tr>th {
        padding: .7rem;
        line-height: 1.6;
        vertical-align: top;
        border-top: 1px solid #ddd;
        font-size: 14px;
    }
</style>

<body>
<!-- content start -->
<%
    AdminUser adminUser=(AdminUser)request.getSession().getAttribute("adminLoginUser");
%>
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0 am-padding-top-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">渠道加款申请</strong>
            </div>
            <div class="am-fr am-cf">
                <a href="#" class="am-text-primary am-text-default" @click="kuang">加款申请</a>
            </div>
        </div>
        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form-inline" role="form" style="float: left;">
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" id="applyStartTime"class="am-form-field"  placeholder="申请开始时间" style="width: 200px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" id="applyEndTime" class="am-form-field"  placeholder="申请结束时间" style="width: 200px;float: left;">
                </div>
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" id="supplyName" class="am-form-field" placeholder="供应商名称" style="width: 200px;float: left;">
                </div>
                <%--<div class="am-form-group" style="margin-top: 20px">--%>
                <%--<input type="text" class="am-form-field" placeholder="账户名" style="width: 200px;float: left;">--%>
                <%--</div>--%>
            </form>
            <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-left:5px;float:left;margin-top: 20px" onclick="selectChannelAddFund(1)">查询
            </button>
            <hr style="display: block;width: 100%;">
            <table class="am-table am-table-striped am-table-hover">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>供应商名称</th>
                    <%--<th>账户名</th>--%>
                    <th>加款金额</th>
                    <th>申请备注</th>
                    <th>申请时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in json">
                    <td>{{$index+1}}</td>
                    <td style="display:none;">{{item.id}}</td>
                    <td>{{item.supplyId}}</td>
                    <%--<td>{{item.zhm}}</td>--%>
                    <td>{{item.amount}}</td>
                    <td>{{item.remark}}</td>
                    <td>{{item.applyTimestr}}</td>
                    <td v-if="item.state == 1">申请中</td>
                    <td v-if="item.state == 2">确认成功</td>
                    <td v-if="item.state == 3">确认失败</td>
                    <td v-if="item.state == 1">
                        <a href="#" onclick="deleteUserChannelAddfund({{item.id}})">取消</a>
                    </td>
                    <td v-if="item.state == 2">
                        <a href="#" @click="pingzheng(item.id)">查看</a>
                    </td>
                    <td v-if="item.state == 3">
                        <a href="#">取消</a>
                    </td>
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
                        <li class="am-disabled"><a href="#"><span> {{page.dq}}</span>/<span>{{page.all}}</span></a></li>
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
            <div class="am-modal-bd"style="padding: 15px;">
                <table>
                    <tr>
                        <td style="width: 80px;text-align: right;">供应商：</td>
                        <td>
                            <div class="am-form-group parent_list">
                                <input type="text" id="supplierNameId" class="am-form-field" placeholder="" style="width: 300px;float: left;" v-model="searchKey" @focus="focus">
                            </div>
                            <div class="list">
                                <ul>
                                    <li v-for="item in list | filterBy searchKey" @click="checkValue">{{item.name}}</li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">账户名：</td>
                        <td>
                            <div><%=adminUser.getAdminName() %></div>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;text-align: right;">加款金额：</td>
                        <td>
                            <div class="am-form-group">
                                <input type="text" id="money" class="am-form-field" placeholder="加款金额" style="width: 300px;float: left;">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;text-align: right;">备注：</td>
                        <td>
                            <div class="am-form-group">
                                <input type="text" id="remark" class="am-form-field" placeholder="不超过30字" style="width: 300px;float: left;">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <hr>
            <form>
                <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: 20px" onclick="insertUserChannelAddfund()">提交</button>
            </form>
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
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/laydate/laydate.js"></script>
<script src="${path}/static/layer/layer.js"></script>
<script>

    $(function(){
        selectSupplierInit();
        selectChannelAddFund(1);
    });
    var vm = new Vue({
        el: "#content",
        data: {
            searchKey: "",
            json:[

            ],
            list: [],
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
            kuang: function() {
                $('#your-modal').modal('open');
            },
            focus: function() {
                $('.list').show();
            },
            hide: function() {
                $('.list').hide();
            },
            checkValue: function(e) {
                var txt = $(e.target).text();
                $('.parent_list input').val(txt);
                this.hide();
            },
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
    function  selectSupplierInit(){
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


        var applyStartTime = $("#applyStartTime").val();
        var applyEndTime = $("#applyEndTime").val();
        var supplyName = $("#supplyName").val();
        var index = layer.open({type:3});
        var obj="";
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectUserChannelAddFund",
            async:false,
            type:'POST',
            data:{"applyStartTime":applyStartTime,"applyEndTime":applyEndTime,"supplyName":supplyName,"pageNumber":pageNumber,"pageSize":pageSize},
            dataType:"json",
            success: function(data){
                layer.close(index);
                obj = data.list;
                $(obj).each(function (index) {
                    //匹配供应商
                    $(supplierName).each(function (i){
                        if(obj[index].supplyId==supplierName[i].id){
                            obj[index].supplyId=supplierName[i].name;
                        }
                    });
                });
                vm.json = obj;
                vm.page.ts=data.total;
                vm.page.dq=data.pageNum;
                vm.page.all=data.pages;
            }
        });
    };
    /*
    申请加款
     */
    function insertUserChannelAddfund(){
        var supplierName = $("#supplierNameId").val();
        if(supplierName.length==0){
            layer.msg("供应商不能为空");
            return ;
        }else {
           var result =  selectSupplier(supplierName);
            if(result.length==0){
                layer.msg("请输入正确的供应商");
                return ;
            }
        }
        var money = $("#money").val();
        var reg =/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/ ;
        if(money.length==0){
            layer.msg("金额不能为空");
            return ;
        }else if(!reg.test(money)){
            layer.msg("请输入正确的金额");
            return ;
        }
        var remark = $("#remark").val();
        if(remark.length==0){
            layer.msg("备注不能为空");
            return ;
        }else if(remark.length>30){
            layer.msg("很遗憾长度超过30个字");
            return ;
        }
        var index = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/insertUserChannelAddfund",
            async:false,
            type:'POST',
            data:{"supplyName":supplierName,"money":money,"remark":remark},
            dataType:"json",
            success: function(data){
                layer.close(index);
                if(data==1){
                    layer.msg("申请成功！");
                    location.href="${pageContext.request.contextPath}/channels/channelAdditional.do";
                    $("#money").val("");
                    $("#remark").val("");
                   // $("#your-modal").hide();
                   //location.reload();
                }else if(data==0){
                    layer.msg("亲，申请失败请从新申请哦！");
                }
            }
        });
    }
    /**
     * 取消加款
     * @param param
     */
    function deleteUserChannelAddfund(param){
            $.ajax({
                url: "${pageContext.request.contextPath}/channels/deleteUserChannelAddFund",
                async:false,
                type:'POST',
                data:{"id":param},
                dataType:"json",
                success: function(data){
                    if(data==1){
                        layer.msg("取消成功！");
                        location.reload();
                    }else if(data==0){
                        layer.msg("亲，取消失败请从新取消哦！");
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
            selectChannelAddFund(pagenum);
        }
    }
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
    }
    /**
     * 判断添加的供应商是否存在
     * @param param
     * @returns {string}
     */
    function selectSupplier(param){
        var supplierParam = param ;
        var resulet = "";
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectSupplier",
            async:false,
            type:'POST',
            data:{"supplyName":supplierParam},
            dataType:"json",
            success: function(data){
                resulet = data;
            }
        });
        return resulet;
    }
</script>
</body>
</html>

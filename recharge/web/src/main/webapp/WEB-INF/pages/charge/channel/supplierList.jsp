<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2016/11/14
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/gongyinghsangliebiao.css">
</head>
<style>
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
    #checklist1,#checklist2,#checklist3 {
        /*border: 1px solid #dedede;*/
        width: 350px;
        float: left;
        overflow: auto;
        padding: 10px;
        padding-top: 5px;
    }
    #checklist1 div,#checklist2 div,#checklist3 div {
        float: left;
        display: block;
        margin-left: 10px;
        text-align: center;
    }
    .lx2{
        display: none;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body" style="padding-top: 20px;">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">供应商列表<span>{{hd}}{{yys}}{{sf}}</span></a></li>
                <input type="hidden" value="${adminLoginUser.adminName}" id="userId"/>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group">
                            <input  type="text" class="am-form-field" placeholder="供应商名称"
                                    style="width: 200px;float: left;" id="suppleirname">
                        </div>
                        <div class="am-form-group">
                            <select style="width: 200px;float: left; height:35px" id="supplierleixing">
                                <option value="0">类型</option>
                                <option value="1">流量</option>
                                <option value="2">话费</option>
                                <option value="3">视频会员</option>
                            </select>
                        </div>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-top: 20px" onclick="getsupplier()">查询</button>
                    </form>
                    <hr>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>

                            <th>供应商ID</th>
                            <th>供应商类型</th>
                            <th>供应商名称</th>
                            <th>供应商全称</th>
                            <th>查看供应商产品</th>
                            <th>联系人</th>
                            <th>联系方式</th>
                            <th>销售人员</th>
                            <th>联系地址</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="tbody">

                        </tbody>
                    </table>
                    <hr>
                    <div class="am-cf">
                        共 <span id="tiaojilu"></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span id="ye"></span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="javascript:void(0);"  id="shangquery" onclick="getsupplier(1)">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span id="yeshu">  </span>/<span id="zongshu">  </span></a></li>
                                <li><a href="javascript:void(0);" onclick="getsupplier(2)">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>

                                <li>
                                    <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" style="padding: .5rem .4rem;" id="go" onclick="getsupplier(3)">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group" style="margin-top: 20px">
                            <label class="am-u-sm-2 am-form-label">供应商名称：</label>
                            <input   type="text" class="am-form-field" placeholder="供应商名称"
                                     style="width: 300px;float: left;" id="mingcheng">
                        </div>

                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">渠道类型：</label>
                            <label class="am-radio-inline">
                                <input type="radio" value="1" name="docInlineRadio"> 流量
                            </label>
                            <label class="am-radio-inline">
                                <input type="radio" value="2" name="docInlineRadio"> 话费
                            </label>
                            <label class="am-radio-inline">
                                <input type="radio" value="3" name="docInlineRadio"> 视频会员
                            </label>
                        </div>
                        <div class="am-form-group" style="margin-top: 20px">
                            <label class="am-u-sm-2 am-form-label">联系人：</label>
                            <input  type="text" class="am-form-field" placeholder="联系人"
                                    style="width: 300px;float: left;" id="lianxiren">
                        </div>
                        <div class="am-form-group" style="margin-top: 20px">
                            <label class="am-u-sm-2 am-form-label">联系方式：</label>
                            <input  type="text" class="am-form-field" placeholder="联系方式"
                                    style="width: 300px;float: left;" id="lianxifansghi">
                        </div>
                        <div class="am-form-group" style="margin-top: 20px">
                            <label class="am-u-sm-2 am-form-label">销售人员：</label>
                            <input  type="text" class="am-form-field" placeholder="销售人员"
                                    style="width: 300px;float: left;" id="xiaoshou">
                        </div>

                        <div class="am-form-group" style="margin-top: 20px">
                            <label class="am-u-sm-2 am-form-label">联系地址：</label>
                            <input  type="text" class="am-form-field" placeholder="联系地址"
                                    style="width: 300px;float: left;" id="dizhi">
                        </div>
                        <hr>
                        <div class="am-form-group">
                            <div>
                                <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin: auto;" onclick="addsupplier()">提交
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
    <!--查看按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="ck_modal">

        <div class="am-modal-dialog">
            <div class="am-modal-hd">查看供应商产品
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd" style="overflow: auto">
                <div class="lx1" style="font-size: 30px;text-align: center;color: red;">请稍后...</div>
                <div class="lx2">
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">移动：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklist1" id="checklist1">

                        </div>
                    </div>
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">联通：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklist2" id="checklist2">

                        </div>
                    </div>
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">电信：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklist3" id="checklist3">

                        </div>
                    </div>
                </div>
                <div class="lx3">
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">优酷：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklistYk" id="checklistYk">

                        </div>
                    </div>
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">爱奇艺：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklistAqy" id="checklistAqy">

                        </div>
                    </div>
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">腾讯：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklistTx" id="checklistTx">

                        </div>
                    </div>
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">搜狐：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklistSh" id="checklistSh">

                        </div>
                    </div>
                    <div class="am-form-group" style="overflow: auto">
                        <label class="am-u-sm-2 am-form-label" style="float:left;display: block;line-height:35px;width: 80px">乐视：</label>
                        <%--<div style="clear: both;"></div>--%>
                        <div class="checklistLs" id="checklistLs">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--编辑按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">

        <input type="hidden" id="su-ids">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">供应商修改
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd">
                <form class="am-form am-form-horizontal">
                    <div class="am-form-group" style="margin-top: 20px">
                        <label class="am-u-sm-2 am-form-label">供应商名称：</label>
                        <input  type="text" class="am-form-field" placeholder="供应商名称"
                                style="width: 300px;float: left;"  id="su-name">
                    </div>

                    <div class="am-form-group" style="margin-top: 20px">
                        <label class="am-u-sm-2 am-form-label">供应商全称：</label>
                        <input  type="text" class="am-form-field" placeholder="供应商全称"
                                style="width: 300px;float: left;"  id="su-allName">
                    </div>

                    <div class="am-form-group">
                        <label class="am-u-sm-2 am-form-label">渠道类型：</label>
                        <span id="su-leixing" style="display: inline-block;margin-left:-370px;margin-top:9px;"></span>
                        <input  type="hidden" id="su-leixingid">
                    </div>

                    <div class="am-form-group" style="margin-top: 20px">
                        <label class="am-u-sm-2 am-form-label">联系人：</label>
                        <input  type="text" class="am-form-field" placeholder="联系人"
                                style="width: 300px;float: left;" id="su-lianxiren">
                    </div>

                    <div class="am-form-group" style="margin-top: 20px">
                        <label class="am-u-sm-2 am-form-label">联系方式：</label>
                        <input type="text" class="am-form-field" placeholder="联系方式"
                               style="width: 300px;float: left;" id="su-lianximobile">
                    </div>
                    <div class="am-form-group" style="margin-top: 20px">
                        <label class="am-u-sm-2 am-form-label">销售人员：</label>
                        <input  type="text" class="am-form-field" placeholder="销售人员"
                                style="width: 300px;float: left;" id="su-sellam">
                    </div>
                    <div class="am-form-group" style="margin-top: 20px">
                        <label class="am-u-sm-2 am-form-label">联系地址：</label>
                        <input  type="text" class="am-form-field" placeholder="联系地址"
                                style="width: 300px;float: left;" id="su-address">
                    </div>
                    <hr>
                    <div class="am-form-group">
                        <div>
                            <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin: auto;" onclick="update()">提交
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
<script>
    var vm = new Vue({
        el: "#content",
        data: {
            hd: "",
            yys: "",
            sf: "",
            json: [
                {
                    id: "1234",
                    type: "流量",
                    mc: "聚通达",
                    lxr: "zhangsan",
                    sjh: "13591872464",
                    ry: "李四"
                },
                {
                    id: "1234",
                    type: "话费",
                    mc: "聚通达",
                    lxr: "zhangsan",
                    sjh: "13591872464",
                    ry: "李四"
                },
                {
                    id: "1234",
                    type: "流量",
                    mc: "聚通达",
                    lxr: "zhangsan",
                    sjh: "13591872464",
                    ry: "李四"
                },
                {
                    id: "1234",
                    type: "流量",
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

    $(function(){
        getsupplier()//执行函数
    });


    //供应商添加
    function addsupplier(){
        var name=$("#mingcheng").val();

        var lx=$("input[type='radio']:checked").val();
        var lianxiren=$("#lianxiren").val();
        var lianxifansghi=$("#lianxifansghi").val();
        var xiaoshou=$("#xiaoshou").val();
        var dizhi=$("#dizhi").val();
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/addsupplier",
            async:false,
            type:'POST',
            data:{"name":name,"businessType":lx,"contactName":lianxiren,"contactMobile":lianxifansghi,"sellman":xiaoshou,"channelAddress":dizhi},
            dataType:"json",
            error:function(){
                alert("添加失败,重新添加!!");
            },
            success: function(data){

                if(data==1){
                    alert("添加成功！");
                }else{
                    alert("添加失败,重新添加!!");
                }
                getsupplier();
            }
        });
    }

    //供应商查询
    function getsupplier(data){

        var yeshu=parseInt($("#yeshu").text());
        var name=$("#suppleirname").val();
        var leixing=$("#supplierleixing").val();
        var pageNum;
        var zongshu=parseInt($("#zongshu").text());
        if(data==1){
            pageNum=yeshu-1;

            if(pageNum==0){
                layer.msg("当前为第一页");
                return ;
            }
        }else if(data==2){
            var pageNum=yeshu+1;
            if(pageNum>zongshu){
                layer.msg("当前为最后一页");
                return ;
            }
        }else if(data==3){
            pageNum=$("#goto-page-num").val();
            if(pageNum==""){
                layer.msg("输入跳转页数");
                return;
            }
        }else if(data==undefined){
            pageNum=1;
        }

        var html;
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/quertsuppleir",
            async:false,
            type:'POST',
            data:{"name":name,"pageNumber":pageNum,"leixing":leixing},
            dataType:"json",
            success: function(data){
                var obj=data.list;
                $(obj).each(function (index) {
                    var lxname;
                    if(obj[index].businessType==1){
                        lxname="流量";
                    }else if(obj[index].businessType==2){
                        lxname="话费";
                    }else if(obj[index].businessType==3){
                        lxname="视频会员";
                    }
                    html+='<tr>' +
                            '<td class="sid">'+obj[index].id+'</td>'+
                            '<td class="slx">'+lxname+'<input type="hidden" value="'+obj[index].businessType+'"/></td>'+
                            '<td class="sname">'+obj[index].name+'</td>'+
                            '<td class="sallName">'+obj[index].allName+'</td>'+
                            '<td class="cname"><a class="am-btn am-btn-link" pro='+obj[index].businessType+' style="padding: 0;" onclick="chakan(this)">查看</a></td>'+
                            '<td class="cnname">'+obj[index].contactName+'</td>'+
                            '<td class="cmobile">'+obj[index].contactMobile+'</td>'+
                            '<td class="ssellman">'+obj[index].sellman+'</td>'+
                            '<td class="scaddress">'+obj[index].channelAddress+'</td>'+
                            '<td class="am-btn-toolbar"> <a class="am-btn am-btn-link" style="padding: 0;" onclick="bianji(this)">修改</a></td>'+
                            '</tr>'
                })
                $("#tbody").empty();
                $("#tbody").append(html);
                $("#tiaojilu").empty();
                $("#tiaojilu").append(data.total)
                $("#ye").empty();
                $("#ye").append(data.pages)
                $("#yeshu").empty();
                $("#yeshu").append(data.pageNum)
                $("#zongshu").empty();
                $("#zongshu").append(data.pages)
            }
        });
    }
    //查看按钮事件
    function chakan(a){
        <%--var id=$(a).parent().siblings(".sid").text();--%>
        <%--location.href = "${pageContext.request.contextPath}/channels/CheckCardProductList.d?supplyId="+id;--%>
        $(".lx1").show();
        $("#checklist1").empty();
        $("#checklist2").empty();
        $("#checklist3").empty();
        $("#checklistYk").empty();
        $("#checklistAqy").empty();
        $("#checklistTx").empty();
        $("#checklistSh").empty();
        $("#checklistLs").empty();
        $("#ck_modal").modal();
        var id=$(a).parent().siblings(".sid").text();
        if($(a).attr("pro")==3){
            $(".lx3").show();
            $(".lx2").hide();
        }else{
            $(".lx3").hide();
            $(".lx2").show();
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getSupplierProduct",
            async:false,
            type:'POST',
            data:{"supplyId":id},
            dataType:"json",
            success: function(data){
                if(data==""){
                    layer.msg("该供应商没有相应产品");
                    return;
                }
                $(".lx1").hide();
//                $(".lx2").show();
                var obj=data;
                var htmlyd="";
                var htmllt="";
                var htmldx="";
                var htmlyk="";
                var htmlaqy="";
                var htmltx="";
                var htmlsh="";
                var htmlls="";
                $(obj).each(function (index) {
                    // alert(obj[index].operator)

                    if(obj[index].operator==1){
                        htmlyd+='<div style="float: left;margin:0px 10px;">'+obj[index].name.substr(2,6)+'</div>';
                    }
                    if(obj[index].operator==2){
                        htmllt+='<div style="float: left;margin:0px 10px;">'+obj[index].name.substr(2,6)+'</div>';
                    }
                    if(obj[index].operator==3){
                        htmldx+='<div style="float: left;margin:0px 10px;">'+obj[index].name.substr(2,6)+'</div>';
                    }
                    if(obj[index].operator==4){
                        htmlyk+='<div style="float: left;margin:0px 10px;">'+obj[index].name+'</div>';
                    }
                    if(obj[index].operator==5){
                        htmlaqy+='<div style="float: left;margin:0px 10px;">'+obj[index].name+'</div>';
                    }
                    if(obj[index].operator==6){
                        htmltx+='<div style="float: left;margin:0px 10px;">'+obj[index].name+'</div>';
                    }
                    if(obj[index].operator==7){
                        htmlsh+='<div style="float: left;margin:0px 10px;">'+obj[index].name+'</div>';
                    }
                    if(obj[index].operator==8){
                        htmlls+='<div style="float: left;margin:0px 10px;">'+obj[index].name+'</div>';
                    }
                })
                $("#checklist1").append(htmlyd);
                $("#checklist2").append(htmllt);
                $("#checklist3").append(htmldx);
                $("#checklistYk").append(htmlyk);
                $("#checklistAqy").append(htmlaqy);
                $("#checklistTx").append(htmltx);
                $("#checklistSh").append(htmlsh);
                $("#checklistLs").append(htmlls);
            }
        });
    }



    //编辑获取数据
    function bianji(date){
        $("#your-modal").modal();
        $("#su-ids").val($(date).parent().siblings(".sid").text());
        $("#su-leixingid").val($(date).parent().siblings(".slx").children().val());
        if($(date).parent().siblings(".slx").children().val()==1){
            $("#su-leixing").text("流量");
        }else if($(date).parent().siblings(".slx").children().val()==2){
            $("#su-leixing").text("话费");
        }
        $("#su-name").val($(date).parent().siblings(".sname").text());
        $("#su-allName").val($(date).parent().siblings(".sallName").text());
        $("#su-lianxiren").val($(date).parent().siblings(".cnname").text());
        $("#su-lianximobile").val($(date).parent().siblings(".cmobile").text());
        $("#su-sellam").val($(date).parent().siblings(".ssellman").text());
        $("#su-address").val($(date).parent().siblings(".scaddress").text());
    }

    //名称校验
    function  tongdaomingcheng(date){
        var reg =/^[\u4e00-\u9fa5]{2,8}$/;
        if(date=="") {
            return;
        }else {
            if (!reg.test(date)) {
                layer.tips("请输入2到8位汉字", "#qudaomingcheng", 1);
                return ;
            }
        }
    }

    //修改功能
    function update(){
        var userId=$("#userId").val();
        var id= $("#su-ids").val();
//        var stype= $("#su-leixingid").val();
        var name= $("#su-name").val();
        var allName=$("#su-allName").val();
        if(name==""){
            layer.msg("供应商名称为必填项");
            return;
        }else {
            var reg =/^[\u4e00-\u9fa5]{2,8}$/;
            if (!reg.test(name)) {
                layer.msg("供应商名称请输入2到8位汉字");
                return ;
            }
        }
        if(allName==""){
            layer.msg("供应商全称为必填项");
            return;
        }else {
            var regs =/^[\u4e00-\u9fa5]{2,20}$/;
            if (!regs.test(name)) {
                layer.msg("供应商全称请输入2到20位汉字");
                return ;
            }
        }
        var people= $("#su-lianxiren").val();
        if(people!=""){
            var reg =/^[\u4e00-\u9fa5]{2,4}$/;
            if (!reg.test(people)) {
                layer.msg("联系人2到4位汉字");
                return ;
            }
        }

        var mobile= $("#su-lianximobile").val();
        var semlla= $("#su-sellam").val();
        if(semlla!=""){
            var reg =/^[\u4e00-\u9fa5]{2,4}$/;
            if (!reg.test(semlla)) {
                layer.msg("销售人2到4位汉字");
                return ;
            }
        }

        var suaddress= $("#su-address").val();
        if(suaddress!=""){
            var size=suaddress.length;
            if (size>30) {
                layer.msg("地址长度为30,现在长度为"+size);
                return ;
            }
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/editorsupplier",
            async:false,
            type:'POST',
            data:{"id":id,"name":name,"allName":allName,"people":people,"mobile":mobile,"semlla":semlla,"address":suaddress,"name":name,"userId":userId},
            dataType:"json",
            error:function(){
                alert("修改失败,重新修改!!");
            },
            success: function(data){
                if(data==1){
                    alert("修改成功");
                    location.reload();
                }else{
                    alert("修改失败");
                }
            }
        });
    }
</script>
</body>
</html>

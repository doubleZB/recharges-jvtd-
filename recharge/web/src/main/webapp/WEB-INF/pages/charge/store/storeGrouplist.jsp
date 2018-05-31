<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/huojiamingchengguanli.css">
</head>
<style>
    input,select{
        color:#848181;
        display: block;
        margin-right: 10px;;
        margin-bottom: 15px;;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body" style="padding-top: 20px;">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">货架名称管理</a></li>
                <li><a href="#tab2">新增</a></li>
            </ul>
            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <div style="clear: both;">
                        <div class="am-form-group" style="margin-top: 0px">
                            <input type="text" class="am-form-field" placeholder="货架组名称"
                                   style="width: 150px;float: left;" id="name">
                        </div>
                        <div class="am-form-group" style="margin-top: 0px">
                            <input type="text" class="am-form-field" placeholder="移动折扣"
                                   style="width: 150px;float: left;" id="mobileDiscount">
                        </div>
                        <div class="am-form-group" style="margin-top: 0px">
                            <input type="text" class="am-form-field" placeholder="联通折扣"
                                   style="width: 150px;float: left;" id="unicomDiscount">
                        </div>
                        <div class="am-form-group" style="margin-top: 0px">
                            <input type="text" class="am-form-field" placeholder="电信折扣"
                                   style="width: 150px;float: left;"id="telecomDiscount" >
                        </div>
                        <div class="am-form-group" style="margin-top: 0px">
                            <input type="text" class="am-form-field" placeholder="渠道名称"
                                   style="width: 150px;float: left;" id="supplyName">
                        </div>
                    </div>
                    <button type="button" class="am-btn am-btn-warning"
                            style="width: 120px;margin:auto;margin-top: 0px"  onclick="reloadPage(1)">查询
                    </button>
                    <table class="am-table am-table-striped">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>货架名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in json">
                            <td>{{item.id}}</td>
                            <td>{{item.name}}</td>
                            <td>
                                <div class="am-btn-toolbar">
                                    <a class="am-btn am-btn-link" style="padding: 0;"  @click="bianji($index)">修改</a>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
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
                                    <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                </li>
                                <li class="am-active"><a href="javascript:void(0);" onclick="gotoPage();"  style="padding: .5rem .4rem;">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group">
                            <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">货架名称：</label>
                            <div style="width: 300px;float: left;">
                                <input type="text" id="doc-ipt-3" placeholder="2~50个任意字，允许数字">
                            </div>
                        </div>
                    </form>
                        <div class="am-form-group">
                            <div class="am-u-sm-12">
                                <button  onclick="addStoreGroup()" class="am-btn am-btn-warning" style="width: 120px;margin: auto;border-radius: 5px;margin-left: 100px;">提交</button>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
    <!--编辑按钮弹出框-->
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd">编辑货架组名称
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <hr>
            <div class="am-modal-bd" style="overflow: auto">
                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label" style="line-height: 35px;">货架组名称：</label>
                    <input type="hidden" id="groupId" name="groupId" value="">
                    <input type="text" class="am-form-field" id="groupName" name="groupName" placeholder="4~50个汉字"
                           style="width: 300px;float: left;">
                </div>
            </div>
               <%-- <div class="am-form-group">
                    <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">货架组名称：</label>
                    <div style="width: 300px;float: left;">
                        <input type="hidden" id="groupId" name="groupId" value="">
                        <input type="text" id="groupName" name="groupName" value="" placeholder="4~10个汉字，仅限中文">
                    </div>
                </div>--%>
                <hr>
                <div class="am-form-group">
                    <div>
                        <button  class="am-btn am-btn-warning" style="width: 120px;margin: auto;border-radius: 5px;" onclick="repareGroup({{page.dq}});">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script>
    var dataAll = {
        json: [],
        page: {
            ts: "",
            dq: "",
            all: ""
        }
    };
    $.ajax({
        url: "/store/findStoreGrouplist?pageNumber=1&pageSize=10",
        type:'POST',
        async:false,
        dataType:"json",
        error:function(){
            $(this).addClass("done");
        },
        success: function(data){
            dataAll.json = data.list;
            dataAll.page.ts = data.total;
            dataAll.page.dq = data.pageNum;
            dataAll.page.all = data.pages;
        }
    });
    var vm = new Vue({
        el: "#content",
        data:dataAll,
        methods:{
            bianji:function (index) {
                $('#your-modal').modal();
                $("#groupName").val(this.json[index].name);
                $("#groupId").val(this.json[index].id);
            }
        }
    });

    function addStoreGroup(){
        var groupname = $("#doc-ipt-3").val();
//        var test =/^[\u4e00-\u9fa5]{4,10}$/;
        if(groupname.trim().length>50 || groupname.trim().length<2 ){
            layer.tips("2-50个任意字，允许数字！","#doc-ipt-3");
            return;
        }
        if(groupname!=''){
            var index = layer.open({type:3});
            $.ajax({
                url: "/store/addStoreGrouplist",
                type:'POST',
                data:{"groupname":groupname.trim()},
                dataType:"json",
                error:function(){
                    $(this).addClass("done");
                },
                success: function(data){
                    layer.close(index);
                    layer.alert(data.message);
                    if(data.success){
                        $("#doc-ipt-3").val("");
                        $("#doc-ipt-3").focus();
                        reloadPage(1);
                    }
                }
            });
        }else{
            layer.tips('货架名称不能为空！', '#doc-ipt-3', {
                tips: [2, '#FF0000']
            });
        }
    }
    /**
     * 条件分页查询
     * @param pageNum
     */
    function reloadPage(pageNum){
        if(pageNum==0){
            layer.msg("当前是第一页啦，没有上一页！");
            return;
        }
        var name= $("#name").val();
        var supplyName=$("#supplyName").val();
        var mobileDiscount= $("#mobileDiscount").val();
        var unicomDiscount= $("#unicomDiscount").val();
        var telecomDiscount= $("#telecomDiscount").val();

//      if( name!= '' && mobileDiscount!=null && unicomDiscount!=null && telecomDiscount!=null &&supplyName!="" ){
//            alert("货架组，折扣，渠道不能同时存在！");
//            return;
//        }
         var operator="";
        var discountPrice="";
        if(mobileDiscount!=""){
            operator+=1+",";
            discountPrice+=mobileDiscount+",";
        }
        if(unicomDiscount!=""){
            operator+=2+",";
            discountPrice+=unicomDiscount+",";
        }
        if(telecomDiscount!=""){
            operator+=3+",";
            discountPrice+=telecomDiscount+",";
        }

        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "/store/selectProductGroupCondition?pageNumber="+pageNum+"&pageSize=10",
            type:'POST',
            dataType:"json",
            data:{name:name,operator:operator,discountPrice:discountPrice,supplyName:supplyName},
            error:function(){
            },
            success: function(data){
                layer.close(loadingIndex);
                dataAll.json = data.list;
                dataAll.page.ts = data.total;
                dataAll.page.dq = data.pageNum;
                dataAll.page.all = data.pages;
            }
        });
    }
    function gotoPage(){
        var pagenum = $("#goto-page-num").val();
        if(pagenum==''){
            layer.tips("请输入页数！","#goto-page-num",{tips: 3});
            return;
        }
        reloadPage(pagenum);
    }

    function repareGroup(pageNum){
        var groupId = $("#groupId").val();
        var groupName = $("#groupName").val();

        if(groupId==''||groupName==''){
            layer.msg("货架组名称不能为空！");
            return;
        }
        var loadingIndex = layer.open({type:3});
        $.ajax({
            url: "/store/repareGroup?groupId="+groupId,
            type:'POST',
            data:{
                "groupName":groupName
            },
            dataType:"json",
            error:function(){
                $(this).addClass("done");
            },
            success: function(data){
                layer.close(loadingIndex);
                if(data.success){
                    reloadPage(pageNum);
                    //关闭窗口，清空数据
                    $('#your-modal').modal();
                    $("#groupName").val("");
                    $("#groupId").val("");
                }
                layer.alert(data.message);
            }
        });
    }
</script>
</body>
</html>

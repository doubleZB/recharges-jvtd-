<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.util.Properties" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/18
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<html>
<head>
    <meta charset="utf-8">
    <title>渠道加款确认（财务）</title>
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
                <strong class="am-text-primary am-text-lg">渠道加款确认（财务）</strong>
            </div>
        </div>
        <hr style="margin: 10px 0px 0px 0px;">
        <div class="am-tab-panel am-active" id="tab1">
            <form class="am-form-inline" role="form">
                <div class="am-form-group" style="margin-top: 20px">
                    <input type="text" class="am-form-field" id="supplyName" placeholder="供应商名称" style="width: 200px;float: left;">
                </div>
                <%--<div class="am-form-group" style="margin-top: 20px">--%>
                    <%--<input type="text" class="am-form-field" placeholder="账户名" style="width: 200px;float: left;">--%>
                <%--</div>--%>
                <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-left:5px;margin-top: 20px" onclick="selectChannelAddFund(1)">查询
                </button>
            </form>

            <hr>
            <table class="am-table am-table-striped am-table-hover" style="font-size: 14px;">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>供应商名称</th>
                    <th>申请人员</th>
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
                    <td>{{item.applyUserid}}</td>
                    <td>{{item.amount}}</td>
                    <td>{{item.remark}}</td>
                    <td>{{item.applyTimestr}}</td>
                    <td v-if="item.state == 1">申请中</td>
                    <td>
                        <a href="#" @click="sure(item.id,item.supplyId,item.applyUserid,item.amount,item.remark)">确认加款</a>
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
            <div class="am-modal-bd" style="padding: 0 20px;">
                <table>
                    <tr style="text-align: left;">
                        <input type="hidden" id="recordId">
                        <td style="width: 80px;text-align: right;">供应商：</td>
                        <td>
                            <div id="supplyerName"></div>
                        </td>
                    </tr>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">账户名：</td>
                        <td>
                            <div id="adminName"></div>
                        </td>
                    </tr>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">加款金额：</td>
                        <td>
                            <div id="money"></div>
                        </td>
                    </tr>
                    <tr style="text-align: left;">
                        <td style="width: 80px;text-align: right;">申请备注：</td>
                        <td>
                            <div id="remark"></div>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;text-align: right;">加款时间：</td>
                        <td>
                            <div class="am-form-group">
                                <input type="text"  id="auditTime" class="am-form-field" placeholder="加款时间" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" style="width: 300px;float: left;">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 80px;text-align: right;"></td>
                        <td>
                            <div class="am-form-group am-form-file">
                                <button type="button" class="am-btn am-btn-warning am-btn-sm">
                                    <i class="am-icon-cloud-upload"></i> 选择要上传的文件</button>
                                <input id="docformfile" type="file" accept="image/jpg,image/jpeg,image/png"
                                      />
                            </div>
                            <div id="file-list"></div>
                        </td>
                    </tr>
                </table>
            </div>
            <hr>
            <button type="submit" class="am-btn am-btn-warning" style="width: 120px;margin: 20px" onclick="updateUserChannelAddfund()">提交</button>
        </div>
    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/assets/js/amazeui.min.js"></script>
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/laydate/laydate.js"></script>
<script src="${path}/static/layer/layer.js"></script>
<script src="${path}/static/js/upload.js"></script>
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
            json: [ ],
            list: [ ],
            page: {
                ts: 0,
                dq: 0,
                all: 0
            },
            item: {}
        },
        ready: function() {},
        methods: {
            sure: function(paramId,paramSupplyName,paramAdminName,paramPrice,paramRemark) {
                $('#your-modal').modal('open');
               $("#recordId").val(paramId);
               $("#supplyerName").text(paramSupplyName);
                $("#adminName").text(paramAdminName);
                $("#money").text(paramPrice);
                $("#remark").text(paramRemark);
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

        var state = 1;
        var supplyName = $("#supplyName").val();
        var obj="";
        var index = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/selectUserChannelAddFund",
            async:false,
            type:'POST',
            data:{"supplyName":supplyName,"pageNumber":pageNumber,"pageSize":pageSize,"state":state},
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
                    //匹配用户名
                    $(adminName).each(function (i){
                        if(obj[index].applyUserid==adminName[i].id){
                            obj[index].applyUserid=adminName[i].adminName;
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

    //上传图片
    $(function() {
        $('#docformfile').on('change', function() {

            var fileNames = '';
            var index = layer.open({type:3});
            $("#docformfile").upload({
                url: '${pageContext.request.contextPath }/channels/customerUpload.do',
                params: {},
                type:'POST',
                dataType: 'json',
                onSend: function (obj, str) {
                    return true;
                },
                onComplate: function (data) {
                    layer.close(index);
                    if(data.status==1){
                        layer.msg("上传图片成功");
                    }
                    fileNames += '<span class="am-badge" id="newName">' + data.reurl + '</span> ';
                    $('#file-list').html(fileNames);
                }
            });
            $("#docformfile").upload("ajaxSubmit");
          });
    });

    /**
     * 确认加款
     */
    function updateUserChannelAddfund(){
        var id=$("#recordId").val();
        var auditTime = $("#auditTime").val();
        var docformfile = $("#newName").text();

        if(auditTime.length==0){
            layer.msg("请选择时间");
            return;
        }
        if(docformfile.length==0){
            layer.msg("请上传图片");
            return;
        }
        var index = layer.open({type:3});
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/updateUserChannelAddfund",
            async:false,
            data:{"id":id,"auditTime":auditTime,"certificate":docformfile},
            type:'POST',
            dataType:"json",
            success: function(data){
                layer.close(index);
                if(data==1){
                    layer.msg("确认成功");
                    location.reload();
                    var auditTime = $("#auditTime").val("");
                    var docformfile = $("#docformfile").val("");
                }else if(data==0){
                    layer.msg("亲，确认失败请从新确认哦！");
                }
            }
        });
    }



</script>
</body>
</html>

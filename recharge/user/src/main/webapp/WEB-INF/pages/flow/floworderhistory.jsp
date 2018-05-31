<%--
  Created by IntelliJ IDEA.
  User: 订单记录
  Date: 2016/12/1
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-cn">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link rel="stylesheet" href="${path}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/static/css/common.css">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/css/z-dingdanjilu.css">
    <style>
        .am-table td {
            border-top: 1px solid #ddd!important;
        }
        .z_number{
            width:100px;
        }
        .texttable > thead > tr > th {
            padding:0!important;
            padding: 10px 0px !important;
        }
        .texttable > thead > tr > td {
            padding:0!important;
        }
        .am-form .am-form-group input{
            line-height:20px;
        }
        .am-form .am-form-group select {
            line-height:1.5;
        }
    </style>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<%
    HttpSession s = request.getSession();
%>
<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="col-lg-12">
            <div class="col-lg-12 m-box m-market" style="padding:0;">
                <div class="box-title" style="margin-bottom:30px;">订单记录</div>
                <div class="box-content">

                    <div class="col-lg-12 col-md-12 col-xs-12">
                        <!--  上半部分   -->
                        <div class="z_jl_tabContent">
                            <form class="am-form">
                                <fieldset>
                                    <!--   手机号输入   -->
                                    <div class="am-form-group">
                                        <input type="text" class="" id="doc-ipt-email-1" placeholder="手机号">
                                        <input type="text" id="doc-ipt-email-2" placeholder="下单开始时间" >
                                        <input type="text" id="doc-ipt-email-3" placeholder="下单结束时间" >

                                        <select id="doc-select-0">
                                            <option value="0">来源</option>
                                            <option value="1">接口充值</option>
                                            <option value="2">页面充值</option>
                                        </select>
                                        <span class="am-form-caret"></span>

                                    </div>
                                    <!--下拉-->
                                    <div class="am-form-group" id="z_info">

                                        <select id="doc-select-1">
                                        </select>
                                        <span class="am-form-caret"></span>

                                        <select class="z_select" id="doc-select-2">

                                            <option value="0">运营商</option>
                                            <option value="1">移动</option>
                                            <option value="2">联通</option>
                                            <option value="3">电信</option>

                                        </select>
                                        <span class="am-form-caret"></span>
                                        <input type="text" class="" id="doc-ipt-email-5" placeholder="备注">
                                        <span class="am-form-caret"></span>
                                        <select id="doc-select-4">
                                            <option value="0">状态</option>
                                            <option value="1">创建订单</option>
                                            <option value="2">提交失败</option>
                                            <option value="4">余额不足</option>
                                            <option value="5">待充值</option>
                                            <option value="6">充值中</option>
                                            <option value="7">成功</option>
                                            <option value="8">失败</option>
                                            <option value="9">支付失败</option>
                                        </select>

                                        <span class="am-form-caret"></span>
                                        <input type="hidden" value="<%=s.getAttribute("userId")%>" id="adminid"/>
                                        <select id="doc-select-6" class="am-margin-top-lg">
                                            <option value="0">子账号</option>
                                        </select>
                                        <input type="text" class="" id="doc-ipt-email-7" placeholder="商家订单号" style="margin-top: 29px">
                                        <button type="button" class="am-btn am-btn-default am-radius am-fl am-margin-top-lg" onclick="fanye()" style="width:120px;background: #F37B1D;border-color:#F37B1D;">查询</button>
                                    </div>

                                    <div id="test"></div>

                                </fieldset>
                            </form>
                            <div class="z_shu">
                                <!--   计数    -->
                                <p style="border-bottom:1px solid #ddd;">
                                    <a >记录条数： <span id="jilutiaoshu">0</span></a>
                                    <a >累计消费金额： <span id="leijijinge">0</span></a>
                                    <a href="javascript:void(0);" onclick="exportLabType()" style="color: #1c68f2;text-decoration: underline;">下载当前数据</a>
                                </p>
                            </div>

                            <!--    表格   -->
                            <table class="am-table table-striped table-hover texttable" style="font-size: 14px;">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th class="z_number">订单号</th>
                                    <th>手机号</th>
                                    <th>省份</th>
                                    <th>运营商</th>
                                    <th>流量包</th>
                                    <th>支付金额</th>
                                    <th>下单时间</th>
                                    <th>归属账号</th>
                                    <th>来源</th>
                                    <th class="z_number">商家订单号</th>
                                    <th>订单状态</th>
                                    <th>备注</th>
                                </tr>
                                </thead>
                                <tbody id="tbody1">
                                </tbody>
                            </table>
                            <div class="am-cf">
                                <div class="sj" style="display: none; color: red;font-size: 2em;text-align: center;">没有找到数据</div>
                                共 <span id="total">0</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span id="yeshu">0</span> 页
                                <div class="am-fr">
                                    <ul class="am-pagination" style="margin: 0">
                                        <li><a href="javascript:void(0);"  id="shangquery" onclick="fanye(1)">上一页</a></li>
                                        <li class="am-disabled"><a href="#"><span id="ye"> 1 </span>/<span id="zongshu"> 1 </span></a></li>
                                        <li><a href="javascript:void(0);" onclick="fanye(2)">下一页</a></li>
                                        <li>
                                            <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;" id="goto-page-num" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" >
                                        </li>
                                        <li class="am-active"><a href="javascript:void(0);" style="padding: .5rem .4rem;" id="go" onclick="fanye(3)">GO</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/bootstrap.min.js"></script>
<script src="${path}/static/js/z-dingdanjilu.js"></script>
<script src="${path}/static/js/layer.js"></script>
<script src="${path}/static/js/laydate.js"></script>
<!--[if (gte IE 9)|!(IE)]><!-->
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="${path}/static/assets/js/amazeui.ie8polyfill.min.js"></script>

<![endif]-->
<script type="text/javascript">
    $(function(){
        selectAllUser();
        getSupplierprovince()//执行函数
        getPositioncode();
        ddh();
        getChildAccount()

    });
    //获取省份
    var huoqushenfeng="";
    function getSupplierprovince(){
        var html='<option value="0">省份</option>';
        $.ajax({
            url: "${pageContext.request.contextPath}/userclient/getprovince",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                var obj=data;
                huoqushenfeng=data;
                $(obj).each(function (i) {
                    html+=' <option value="'+obj[i].key+'">'+obj[i].value+'</option>'
                });
              $("#doc-select-1").empty();
                $("#doc-select-1").append(html);
            }
        });
    }
    //获取所有用户
    var allUser="";
    function selectAllUser(){
        $.ajax({
            url: "${pageContext.request.contextPath}/userclient/selectAllUser",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                allUser=data;
            }
        });
    }
    var huoqushangpinbianma="";
    function getPositioncode(){
        $.ajax({
            url: "${pageContext.request.contextPath}/userclient/getproductcode",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                huoqushangpinbianma=data;
            }
        });
    }

/*查询翻页*/
  function fanye(data){
      var pageNumber=parseInt($("#ye").text());
      var zuihou=parseInt($("#zongshu").text())
      var pageNum;
      if(data==1){
          pageNum=pageNumber-1;

          if(pageNum==0){
              alert("当前为第一页");
              return ;
          }
      }else if(data==2){
          var pageNum=pageNumber+1;
          if(pageNum>zuihou){
              alert("当前为最后一页");
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

      var html="";
      var model =  $("#doc-ipt-email-1").val();
      var startime =  $("#doc-ipt-email-2").val();

      var endtime =  $("#doc-ipt-email-3").val();

      if(startime!=""&&endtime==""){
          layer.msg("请选择结束时间");
          return;
      }
      var laiyuan =  $("#doc-select-0").val();
      var province =  $("#doc-select-1").val();
      var yunyingshang =  $("#doc-select-2").val();
      var status =  $("#doc-select-4").val();
      var remark = $("#doc-ipt-email-5").val();//备注
      var childAccount = $("#doc-select-6").val();//子账号
      var merchantOrderNumber = $("#doc-ipt-email-7").val();//商家订单号
      if(0==childAccount){
          childAccount=""
      }
      var bussnesstype=1;
      var userid=$("#adminid").val();//用来取用户名
      getamountcount();
      var inde = layer.open({type:3});
      $.ajax({
          url: "${pageContext.request.contextPath}/userclient/usersideorder",
          async:false,
          type:'POST',
          dataType:"json",
          data:{"mobile":model,"startime":startime,"endtime":endtime,"source":laiyuan,"provinceid":province,"operator":yunyingshang,
              "status":status,"bussinessType":bussnesstype,"userid":userid,"pageNum":pageNum,"remark":remark,"childAccount":childAccount,"merchantOrderNumber":merchantOrderNumber},
          error:function(){
              layer.close(inde);
              layer.msg("加载失败请重新登录");
          },
          success: function(data){
              layer.close(inde);
              var obj=data.list;
              var yys;
              var sf;
              var zt;
              var laiyuan;
              var bianma;
              var remarkStr;
              var userName;
              $(obj).each(function (i) {
                  var index=i+1;
                  if(obj[i].operator==1){
                      yys="移动";

                  }else if(obj[i].operator==2){
                      yys="联通";
                  }else if(obj[i].operator==3){
                      yys="电信";
                  }
                  $(huoqushangpinbianma).each(function (j) {
                      if(obj[i].positionCode==huoqushangpinbianma[j].code){
                          bianma=huoqushangpinbianma[j].packageSize;
                      }
                  });
                  $(huoqushenfeng).each(function (j) {
                      if(obj[i].provinceId==huoqushenfeng[j].key){
                          sf=huoqushenfeng[j].value;
                      }
                  });
                  $(allUser).each(function (j) {
                      if(obj[i].userId==allUser[j].id){
                          userName=allUser[j].userCnName;
                      }
                  });
                  if(obj[i].status==6){
                      zt="充值中";
                  }else if(obj[i].status==7){
                      zt="成功";
                  }else if(obj[i].status==8){
                      zt="失败";
                  }else if(obj[i].status==9){
                      zt="支付失败";
                  }else if(obj[i].status==1){
                      zt="创建订单";
                  }else if(obj[i].status==2){
                      zt="提交失败";
                  }else if(obj[i].status==3){
                      zt="提交失败";
                  }else if(obj[i].status==4){
                      zt="余额不足";
                  }else if(obj[i].status==5){
                      zt="待充值";
                  }



                  if(obj[i].source==1){
                      laiyuan="接口";
                  }else if(obj[i].source==2){
                      laiyuan="页面";
                  }
                  if(obj[i].remark==null||obj[i].remark==""){
                      remarkStr="无";
                  }else if(obj[i].remark!=null||obj[i].remark!=""){
                      remarkStr=obj[i].remark;
                  }
                  html+=  ' <tr>'+
                          '<td >'+index+'</td>'+
                          '<td class="z_ddh">'+obj[i].orderNum+'</td>'+
                          '<td>'+obj[i].rechargeMobile+'</td>'+
                          '<td>'+sf+'</td>'+
                          '<td>'+yys+'</td>'+
                          '<td>'+bianma+'</td>'+
                          '<td>'+obj[i].amount+'</td>'+
                          '<td>'+obj[i].dateStr+'</td>'+
                          '<td>'+userName+'</td>'+
                          '<td>'+laiyuan+'</td>'+
                          '<td class="z_ddh">'+obj[i].customid+'</td>'+
                          '<td>'+zt+'</td>'+
                          '<td>'+remarkStr+'</td>'+
                          '</tr>';
              });
              $("#tbody1").empty();
              $("#tbody1").append(html);
              $("#jilutiaoshu").empty();
              $("#jilutiaoshu").append(data.total);
              $("#total").empty();
              $("#total").append(data.total);
              $("#yeshu").empty();
              $("#yeshu").append(data.pages);
              $("#ye").empty();
              $("#ye").append(data.pageNum)
              $("#zongshu").empty();
              $("#zongshu").append(data.pages)
              if(data.list=="" ){
                  $("#tbody1").hide();
                  $(".sj").show();

              }else{
                  $("#tbody1").show();
                  $(".sj").hide();
              }
          }
      });
      ddh();
  }


//订单号特效
  function ddh(){
      $(".z_ddh").each(function() {
          $(".z_ddh").click(function() {
              var html = $(this).text();
              var _this = $(this);
              layer.tips(html, _this, {
                  tips: [1, '#01AAED'],
                  time: 4000
              });
          });
      });
  };
/*获取查询金额*/
  function getamountcount(){
      var model =  $("#doc-ipt-email-1").val();
      var startime =  $("#doc-ipt-email-2").val();
      var endtime =  $("#doc-ipt-email-3").val();
      var laiyuan =  $("#doc-select-0").val();
      var province =  $("#doc-select-1").val();
      var yunyingshang =  $("#doc-select-2").val();
      var status =  $("#doc-select-4").val();
      var remark = $("#doc-ipt-email-5").val();//备注
      var childAccount = $("#doc-select-6").val();//子账号
      var merchantOrderNumber = $("#doc-ipt-email-7").val();//商家订单号

      if(0==childAccount){
          childAccount=""
      }
      var bussnesstype=1;
      var userid=$("#adminid").val();//用来取用户名

      $.ajax({
          url: "${pageContext.request.contextPath}/userclient/getamountcount",
          async:false,
          type:'POST',
          dataType:"json",
          data:{"mobile":model,"startime":startime,"endtime":endtime,"source":laiyuan,"provinceid":province,"operator":yunyingshang,
              "status":status,"bussinessType":bussnesstype,"userid":userid,"remark":remark,"childAccount":childAccount,"merchantOrderNumber":merchantOrderNumber},
          success: function(data){
              var obj=data;

              $("#leijijinge").empty();
              $("#leijijinge").append(obj);
          }
      });
  }



    /* 用于日期特效的 */
    laydate.skin('molv');
    laydate({
        elem: '#doc-ipt-email-2',
        event: 'focus',
        choose: function() {
            date();
        }
    });
    laydate({
        elem: '#doc-ipt-email-3',
        event: 'focus',
        choose: function() {
            date();
        }
    });

    //日期选择函数
    function date() {
        var startTime = $("#doc-ipt-email-2").val();
        var endTime = $("#doc-ipt-email-3").val();
        var start = startTime.split("-");
        var startYear = start[0];
        var startMonth = start[1];
        var startDay = start[2];
        var end = endTime.split("-");
        var endYear = end[0];
        var endMonth = end[1];
        var endDay = end[2];
        <!-- console.log(startYear,endYear); -->
        if (startTime !=""&&endTime == "") {
            alert("请选择结束时间！");
            return ;
        } else if (startYear != endYear) {
            alert("结束时间与开始时间需在同一年！");
            return ;
        } else if (startMonth != endMonth) {
            alert("结束时间与开始时间需在同一月！");
            return ;
        } else if (startDay > endDay) {
            alert("结束时间不能小于开始时间！");
            return ;
        };
    };


    function exportLabType() {
        var amount = $("#total").html();
        if(amount>40000){
            alert("一次下载数据不能超过4万！");
            return;
        }else{
            var startTime = $("#doc-ipt-email-2").val();
            var endTime = $("#doc-ipt-email-3").val();
            var bussnesstype=1;
            var model =  $("#doc-ipt-email-1").val();
            var laiyuan =  $("#doc-select-0").val();
            var province =  $("#doc-select-1").val();
            var yunyingshang =  $("#doc-select-2").val();
            var status =  $("#doc-select-4").val();
            var remark = $("#doc-ipt-email-5").val();//备注
            var childAccount = $("#doc-select-6").val();//子账号
            var merchantOrderNumber = $("#doc-ipt-email-7").val();//商家订单号
            window.open("<%=request.getContextPath()%>/orderExport/exportOrder?startTime="+startTime+"&endTime="+endTime+"&businessType="+bussnesstype
            +"&mobile="+model+"&source="+laiyuan+"&provinceid="+province+"&operator="+yunyingshang
            +"&status="+status+"&remark="+remark+"&childAccount="+childAccount+"&merchantOrderNumber="+merchantOrderNumber);
        }
    }

//获取子账号
    function getChildAccount(){
        var html= '<option value="0">当前账号</option>';
        $.ajax({
            url: "${pageContext.request.contextPath}/userclient/getChildAccount",
            async:false,
            type:'POST',
            dataType:"json",
            success: function(data){
                if(data!=null) {
                    var obj = data;
                    $(obj).each(function (index) {
                        html += '<option value=' + obj[index].id + '>' + obj[index].userCnName + '</option>';
                    })

                }else{
                    html += '<option value="">无子账号</option>';
                }
                $("#doc-select-6").empty();
                $("#doc-select-6").append(html);
            }
        });
    }


</script>
</body>
</html>

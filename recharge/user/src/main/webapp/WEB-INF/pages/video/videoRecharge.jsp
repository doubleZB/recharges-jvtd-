<%--
  Created by IntelliJ IDEA.
  User: liyabin
  Date: 2018/3/23
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>视频会员充值</title>
    <link rel="stylesheet" href="${path}/static/css/reset.css" />
    <link rel="stylesheet" href="${path}/static/css/query.css" />
    <script src="${path}/static/js/jquery.min.js"></script>
    <script src="${path}/static/js/swiper.animate1.0.2.min.js"></script>
</head>
<body>
<input  type="hidden" value="${users.id}" id="userId"/>
<div class='wear' id="wear" v-cloak>
    <div class="lcy_wear">
        <h1>视屏会员充值</h1>
        <ul>
            <li :class='sty==0?"active":""' @click='cz'>单个充值</li>
            <li :class='sty==1?"active":""' @click='cz2'>批量充值</li>
        </ul>
        <div>
            <div>
                <ul>
                    <li :class='index==numTit?"active":""' v-for='(i,index) in list' @click='TitTab(i.value,index)'>
                        {{ i.name }}
                    </li>
                </ul>
                <dl>
                    <dd v-show='sty==0'>
                        <label>
                            <input type="tel" :placeholder="txtcon" v-model='tel' />
                        </label>
                        <div>
                            <p>面值</p>
                            <ol>
                                <li  :class='index==numME? "active": "" ' v-for='(i,index) in listM ' @click='TitME(i,index) '>
                                    <h3>{{i.packagesSize}}</h3>
                                    <p>售价{{i.amount}}</p>
                                </li>
                            </ol>
                        </div>
                        <div>
                            <p>充值类型</p>
                            <ul>
                                <li :class='index==numClass? "active": "" ' v-for='(i,index) in lx ' @click='TitClass(i.value,i.operatorId,index) '>
                                    {{i.name}}
                                </li>
                            </ul>
                        </div>
                        <h5>支付金额:
                            <span>{{payMoney}}</span>元</h5>
                        <input type="button" @click='immediately ' value="提交">
                    </dd>
                    <dt v-show='sty==1'>
                        <dl>
                    <dd>
                        <h1>输入手机号或邮箱:</h1>
                        <p>（一行一个，最多 一千个）
                        </p>
                    </dd>
                    <dt>
                        <textarea name="" id="z_phoneNum" cols="30" rows="10" style="line-height:20px;"  @change="getMobilePhone" :placeholder="txtcon" v-model="textAr"></textarea>
                    <p>充值记录数:{{numberAllL}}</p>
                    </dt>
                </dl>
                <label>
                    <span>选择充值类型：</span>
                    <select v-model='Member' @change="classAll">
                        <option value='0'>普通会员</option>
                        <option value='1'>超级会员</option>
                    </select>
                </label>
                <label>
                    <span>选择卡品：</span>
                    <select v-model='Denomination' @change="AllKa">
                        <option :value="index" v-for="(i,index) in listMD">{{i.packagesSize}}</option>
                    </select>
                    <p>需支付总金额：
                        <span>{{AllMoney}}</span>元</p>
                </label>
                <input type="button" @click='immediatelyTwo ' value="提交">
                <input type="button" @click='onl ' value="清空重置">
                </dt>
                </dl>
                <div class="BombBox" v-show='BombBoxShow'>
                    <h3>提示</h3>
                    <form>
                        <label>
                            <span>支付密码：</span>
                            <input type="password" v-model='passWordZ'>
                        </label>
                        <label>
                            <input type="button" value="确定" @click='Determine'>
                            <input type="button" value="取消" @click='cancel'>
                        </label>
                    </form>
                </div>
                <ol>
                    <li>
                        温馨提示:
                    </li>
                    <li>1. 请确认您在 对应视频网站的登录方式，输入手机或邮箱完成充值。</li>
                    <li>2. 新用户输入手机号充值，将以手机号为登录账号自动注册视频网站会员账号。</li>
                    <li>3.充值时长：在原有账号的会员有效期之后延长。</li>
                </ol>
            </div>

        </div>
    </div>
</div>
<script src="${path}/static/js/vue.js"></script>
<script src="${path}/static/js/vue-resource.js"></script>
<script src="${path}/static/js/query.js"></script>
<script >
    function submitPayPassword(){
        var pagePayPassword=this.passWordZ;
        $.ajax({
            url: "${pageContext.request.contextPath}/Recharge/payPassword",
            data:{"pagePayPassword":pagePayPassword},
            async:false,
            type:'POST',
            dataType:"json",
            error:function(){
                alert("系统错误，请重新登录");
            },
            success: function(data){
                if(data){
                    this.passWordZ.val("")
                    rechargeSumbit();
                }else{
                    $("#bd-map").text("密码错误,支付失败").css('color','red');
                }
            }
        });
    }

    /**
     * 单号手机充值
     */
    function  rechargeSumbit(){
        var mobile =this.tel;
        var userId=$("#userId").val();
        var positionCode=
        var urlName="videoRechargeUrl";
        if(userId==""){
            alert("用户超时从新登陆");
            return ;
        }
        if(positionCode==""){
            alert("请选择充值产品");
            return ;
        }
        if(mobile==""){
            alert("请输入手机号");
            return ;
        }
        $.ajax({
            url: "${pageContext.request.contextPath}/Recharge/send",
            data:{"mobile":mobile,"userId":userId,"positionCode":positionCode,"urlName":urlName},
            async:false,
            type:'POST',
            dataType:"json",
            error:function(){
                alert("系统错误，请重新登录");
            },
            success: function(data){
                var code = data.statusCode;
                if(code=="1000"){
                    alert("提交成功，去订单明细查询");
                    $("#mobile").val("");
                    window.location.reload();
                }else{
                    var msg=data.statusMsg;
                    alert("提交失败"+code+","+msg);
                }
            }
        });
    }


//    批量充值
    z_phoneNum.onchange=function(){

    }
    function getMobilePhone(){
        $("#z_phoneNum").val();
        console.log(213)
    }




    function skip(){
        window.location="<%=request.getContextPath()%>/admin/administrate.do";
    }
</script>
</body>

</html>

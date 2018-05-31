<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/11
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>云通信管理控制台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/swiper.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
</head>
<style>
    [v-cloak] {
        display: none;
    }
</style>
<body>
<div class="wrap">
    <div class="header">
        <div class="top">
            <div class="logo">
                <a href="#"><img src="<%=request.getContextPath()%>/static/images/logo.png"></a>
            </div>
            <div class="inform">
                <a href="http://47.94.42.84:8080/recharge/login?userName=${adminLoginUser.adminName}&userPass=${adminLoginUser.password}">
                    统计功能
                </a>
                <span class="line">|</span>
                <a href="#" class="account">${adminLoginUser.adminName}</a>
                <span class="line">|</span>
                <a href="<%=request.getContextPath()%>/admin/LoginOut" class="quit">退出</a>
            </div>
        </div>
    </div>
    <div class="container" id="content" v-cloak>
        <div class="left">
            <ul class="tabs" style="background-color:#0e90d2;overflow: auto;width: 85px;overflow-x: hidden">
                <li class="on" v-for="item in lst">
                    <a href="javascript:;">
                        <div class="lefttab"></div>
                        <span>{{item.name}}</span>
                    </a>
                </li>
            </ul>
            <c:forEach items="${menuSet}" varStatus="varStatus" var="item">
                <c:choose> 
	                <c:when test="${varStatus.index==0}" >
		            <div class="con nav_l" style="width: 180px;overflow-x: hidden">
		            </c:when>
		            <c:otherwise>
		            <div class="con nav_l" style="display: none;width: 180px;overflow-x: hidden" >
		            </c:otherwise>
	            </c:choose>
				<div class="nr">
                     <ul class="listnav listnav1">
                         <li v-for="item in ${item}">
                             <h2>{{item.tittle}}<span class="fa fa-caret-up"></span></h2>
                             <div class="nav2" style="display: block">
                                 <a href="javascript:;" class="nava" v-for="a in item.next"
                                    @click="link(a.url,$event)">{{a.name}}</a>
                             </div>
                         </li>
                     </ul>
                </div>
                </div>
            </c:forEach>

           
        </div>
        <div class="right" style="height: auto;">
            <div class="r_header">
                <div class="fa fa-angle-double-left" @click="prev()"></div>
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <%--<div class="swiper-slide off"><span>标签页</span><i class="fa fa-close"></i></div>--%>
                        <%--<div class="swiper-slide"><span>标签页标签页</span><i class="fa fa-close"></i></div>--%>
                        <%--<div class="swiper-slide"><span>标签页标签页</span><i class="fa fa-close"></i></div>--%>
                        <%--<div class="swiper-slide"><span>标签页标签页</span><i class="fa fa-close"></i></div>--%>
                        <%--<div class="swiper-slide"><span>标签页标签页标签页标签页</span><i class="fa fa-close"></i></div>--%>
                    </div>
                </div>
                <div class="fa fa-angle-double-right" @click="next()"></div>
            </div>
            <iframe id="first" src="<%=request.getContextPath()%>/index/fist.do" width="100%"></iframe>
        </div>
    </div>
</div>
<!--<div class="footer">
<p>© Copyright 2016 云通信平台 京ICP备09068088-5 北京聚通达科技股份有限公司</p>
</div>-->
</body>
<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/vue.js"></script>
<script src="<%=request.getContextPath()%>/static/js/swiper.min.js"></script>
<script>
    var hei = $(window).height();
    var arr = [];
    //扩展数组方法:删除指定元素
    Array.prototype.rmove = function(val) {
        var index = this.indexOf(val);
        while(index>-1){
            this.splice(index, 1);
            index = this.indexOf(val);
        }
        return this;
    };
    //扩展数组方法：查找指定元素的下标
    Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    //tab 点击
    function tab(e){
        var index = $(e).index();
        $(".swiper-slide").eq(index).addClass("off").siblings().removeClass("off");
        $("iframe").eq(index).show().siblings("iframe").hide();
    }
    //标签删除
    function faClose(e){
        var index = $(e).parents().index();
        var h = $(window).height();
        var t = $(e).siblings("span").text();
        arr.rmove(t);
        if(index == 0 && $("iframe").length == 1){
            $(".swiper-slide").eq(index).remove();
            $("iframe").eq(index).remove();
            $(".right").append('<iframe id="first" src="<%=request.getContextPath()%>/index/fist.do" width="100%" height="'+ (h - 133) +'px"></iframe>');
        }else{
            $(".swiper-slide").eq(index).remove();
            $("iframe").eq(index).remove();
            $("iframe").eq($("iframe").length-1).show().siblings("iframe").hide();
            $(".swiper-slide").eq($("iframe").length-1).addClass("off").siblings().removeClass("off");
        }
    }
    $(function () {
        var vm = new Vue({
            el: "#content",
            data: ${menuJson},
            ready:function(){
                var wid = $(".right").width();
                $(".swiper-container").css("width",wid-60);
                mySwiper = new Swiper ('.swiper-container', {
                    slidesPerView: 10,
                    spaceBetween: 5,
                    preventLinksPropagation : false,
                    onClick: function(swiper){
                       /* alert(mySwiper.clickedIndex);
                        $(".swiper-slide").eq(mySwiper.clickedIndex).addClass("off").siblings().removeClass("off");
                        $("iframe").eq(mySwiper.clickedIndex).show().siblings("iframe").hide();*/
                    }
                })
            },
            methods: {
                prev:function(){
                    mySwiper.slidePrev();
                },
                next:function(){
                    mySwiper.slideNext();
                },
                link:function (url,e) {
                    //$("iframe").prop("src", url);
                    var txt = $(e.target).text();
                    var index = arr.indexOf(txt);
                    if(index == -1) {
                        $(".right").append('<iframe src="<%=request.getContextPath()%>' + url + '" width="100%" height="' + (hei - 133) + 'px"></iframe>');
                        $("iframe").eq($("iframe").length - 1).show().siblings("iframe").hide();
                    }
                }
            }
        });
        //左侧tab
        $(".tabs li").each(function (index) {
            if (index == 0) {
                $(".lefttab").eq(index).addClass("m6");
            } else if (index == 1) {
                $(".lefttab").eq(index).addClass("m3");
            } else {
                $(".lefttab").eq(index).addClass("m1");
            }
            $(".tabs li").eq(0).addClass("on").siblings().removeClass("on");
            $(".tabs li").eq(index).click(function () {
                $(".tabs li").eq(index).addClass("on").siblings().removeClass("on");
                $(".nav_l").eq(index).show().siblings(".nav_l").hide();
            });
        });
        //tab点击
        $(".listnav .nava").eq(0).addClass("cur").siblings().removeClass("cur");
        $(".listnav .nava").click(function () {
            var txt  = $(this).text();
            var index = arr.indexOf(txt);
            $("#first").remove();
            $(".listnav .nava").removeClass("cur");
            $(this).addClass("cur");
            console.log();
            if(index == -1){
                arr.push(txt);
                mySwiper.appendSlide('<div class="swiper-slide" onclick="tab(this)"><span>'+ txt +'</span><i class="fa fa-close" onclick="faClose(this)"></i></div>');
                $(".swiper-slide").eq(mySwiper.slides.length-1).addClass("off").siblings().removeClass("off");
            }else{
                $("iframe").eq(index).show().siblings("iframe").hide();
                $(".swiper-slide").eq(index).addClass("off").siblings().removeClass("off");
            }
        });

        //tab下拉
        var Off = true;
        $(".nav2").hide();
        $(".listnav1 li h2").each(function (i) {
            $(this).click(function () {
                if($(".nav2").eq(i).css("display") == "block"){
                    $(".nav2").eq(i).slideUp();
                    $(".listnav li span").eq(i).removeClass("fa-caret-down").addClass("fa-caret-up");
                }else{
                    for( var j = 0;j < $(".nav2").length; j++){
                        $(".nav2").eq(j).slideUp();
                        $(".listnav li span").eq(j).removeClass("fa-caret-up").addClass("fa-caret-up");
                    }
                    $(".nav2").eq(i).slideDown();
                    $(".listnav li span").eq(i).removeClass("fa-caret-up").addClass("fa-caret-down");
                }
            })
        });
        //三角形状态
        function asd(i) {
            var css = $(".listnav1 li span").eq(i).attr("class");
            if (css == "fa fa-caret-down") {
                $(".listnav li span").eq(i).removeClass("fa-caret-down").addClass("fa-caret-up");
            } else {
                $(".listnav li span").eq(i).removeClass("fa-caret-up").addClass("fa-caret-down");
            }
        }
        var hei = $(window).height();
        //设置高度
        $(".left").css("height", hei-40);
        $(".tabs").css("height", hei-120);
        $(".right").css("height", hei - 80);
        $("iframe").css("height", hei - 133);
    })
</script>
</html>

document.writeln("<div class='sideNav' id='sidenav'><div class='sideNav_left'>");
document.writeln("<ul><li style='margin-top:0;'><a href='"+path+"/homePage/SMS1.jsp'>短信</a></li><li><a href='"+path+"/homePage/dxyzm.jsp'>短信验证码</a></li><li><a href='"+path+"/homePage/dxtztd.jsp'>营销短信</a></li><li><a href='"+path+"/homePage/yyyzm.jsp'>语音验证码</a></li><li><a href='"+path+"/homePage/gjdx.jsp'>国际短信</a></li></ul>");
document.writeln("<ul><li><a href='"+path+"/homePage/yuyin.jsp'>语音</a></li><li><a href='"+path+"/homePage/yyth.jsp'>语音通话</a></li><li><a href='"+path+"/homePage/dhhy.jsp'>电话会议</a></li><li><a href='"+path+"/homePage/dhhb.jsp'>电话回拨</a></li><li><a href='"+path+"/homePage/yytz.jsp'>语音通知</a></li></ul>");
document.writeln("<ul><li><a href='"+path+"/homePage/sssp.jsp'>视频</a></li><li><a href='"+path+"/homePage/sssp.jsp'>音视频通话</a></li><li><a href='"+path+"/homePage/sssp.jsp'>音视频会议</a></li></ul>");
document.writeln("<ul><li><a href='"+path+"/homePage/chanpingim.jsp'>IM即时消息</a></li><li><a href='"+path+"/homePage/chanpingim.jsp'>单聊</a></li><li><a href='"+path+"/homePage/chanpingim.jsp'>群组</a></li><li><a href='"+path+"/homePage/chanpingim.jsp'>IM客服</a></li></ul>");
document.writeln("<ul><li><a href='"+path+"/homePage/zzfw.jsp'>增值服务</a></li><li><a href='"+path+"/homePage/llcz.jsp'>流量充值</a></li><li><a href='"+path+"/homePage/hfcz.jsp'>话费充值</a></li></ul>");
document.writeln("</div><div class='sideNav_right'><span id='sidenav_span'>产品目录<img src='"+path+"/homePage/images/jiantou.png' alt=''></span></div></div>");
document.writeln("");



$(function () {
    //左侧导航
    var nav = document.getElementById('sidenav');
    var nav1 = document.getElementById('sidenav_span');
    nav.onmouseover = function () {
        xianshi(0, 20);
    }
    nav.onmouseout = function () {
        xianshi(-200, -20);
    }
    var dingshiqi = null;

    function xianshi(juli, sudu) {
        var nav = document.getElementById('sidenav');
        clearInterval(dingshiqi);
        dingshiqi = setInterval(function () {
            if (nav.offsetLeft == juli) {
				$(".sideNav_right").hide();
                clearInterval(dingshiqi);
				if(nav.offsetLeft==-200){
					$(".sideNav_right").show();
				}
            } else {
                nav.style.left = nav.offsetLeft + sudu + 'px';
            }
        }, 30);
    }

})

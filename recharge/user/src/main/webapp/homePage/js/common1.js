$(function(){
	
	//轮播
	//banner();
	//关于我们
	//about();
	
	//媒体报道点击显示内容
	//mtbd();
	//400电话hover
	phone();







})







var t=null;
var n=0;/**动态变化**/
var count;
/************************/
$(function(){
	count=$(".pic li").length;

	$(".pic li:not(:first-child)").hide();
	
	$(".dian ul li").click(function(){
		var index=$(this).text()-1;
		n=index;

		$(".pic li").filter(":visible").fadeOut(300).siblings().eq(index).fadeIn(1000);
		

		$(this).addClass("bg");
		$(this).siblings().removeClass("bg");
	});
	/***定义定时器3秒执行一次****/
	t=setInterval("autoMove()",3000);
	
	/****当鼠标进入时候定时器停止，移除时候定时器开启****/
	$(".dian").hover(function(){clearInterval(t)}, function(){t = setInterval("autoMove()", 3000);});
});

/***定义自动轮播函数****/
function autoMove(){
	
	if(n>=(count-1)){
		n=0;
	}else{
		++n;
	}
	/*****给li执行匹配的事件*****/
	$(".dian ul li").eq(n).trigger("click");
}


















//轮播





//解决问题

$(window).scroll(function(){
	bbb($(".application").eq(0),$(".atext1").eq(0),$(".atext2").eq(0),"imgAnimation","bottomtotop");
	bbb($(".application2").eq(0),$(".atext3").eq(0),$(".atext4").eq(0),"imgAnimation","bounce");
	bbb($(".application2").eq(1),$(".atext3").eq(1),$(".atext4").eq(1),"imgAnimation","bounce");
	bbb($(".application").eq(1),$(".atext1").eq(1),$(".atext2").eq(1),"bounce","bottomtotop");
})


function bbb(btn,btn1,btn2,css1,css2){
	var to = btn.offset().top;
	var hscro = $(document).scrollTop();
	if(to-hscro<0){
	
			btn1.addClass(css1);
			btn2.addClass(css2);
	}
	else{
		
			btn1.removeClass(css1);
			btn2.removeClass(css2);
	}


	
	
}

//关于我们

// function about(){
	
// 		$(".about_l").click(function(){
			
// 			$(this).addClass("active").siblings().removeClass("active");
// 			$(".about-r").eq($(this).index()-1).show().siblings().hide();

// 		});
// }

//媒体报道点击显示内容
// function mtbd(){
	
// 		$(".mtbd_list .media").click(function(){
			
// 			$(this).parent().hide();
// 			$(".mtbd_con").eq($(this).index()).show();
			
// 		});
		
// 		$("#myScrollspy li:last").click(function(){
// 			$(".mtbd_list").show();
// 			$(".mtbd_con").hide();
			
// 		})
// }
// $(".sider2")

//400电话hover
function phone(){
	$(".sider1").hover(function(){
		$(this).css("display","none");
		$(".sider2").css("display","block")
	});
	
	$(".sider2").mouseout(function(){
		$(this).css("display","none");
		$(".sider1").css("display","block")
		
	});
	
	
	
	
	
}



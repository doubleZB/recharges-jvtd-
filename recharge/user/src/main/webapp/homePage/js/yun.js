$(function(){
	
	
/*设置框架高度*/
	var hei = $(window).height() - $('.header').height();
	$('#mainFrame').css('height',hei);
	
	
	
	
/*隐藏侧边栏*/
var asd = true;
$(".glyphicon-align-justify").click(function() { 
	if(asd){
		$(".subnav").animate({left:"-220px"},500)
		$(".main").animate({marginLeft:'0px'},500);
		asd = false;
	}else{
		$(".subnav").animate({left:"0px"},500)
		$(".main").animate({marginLeft:'220px'},500);
		asd = true;
	}
	/*if($(".subnav")==!is("hidden")){
		alert(111);
		$(".subnav").animate({width: 'toggle' ,left:'-220px'}); 
		$(".main").animate({marginLeft:'0'});
	}else{
		$(".subnav").animate({width: 'toggle' ,left:'0'}); 
		$(".main").animate({marginLeft:'-220px'});
	
	
	}*/


});



/*主菜单下拉*/
$(".dropdown-toggle").click(function() { 
	$(this).next().slideToggle(500); 

});





















})






function myBrowser() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
    var isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari浏览器
    if (isIE) {
        var IE5 = IE55 = IE6 = IE7 = IE8 = false;
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        IE55 = fIEVersion == 5.5;
        IE6 = fIEVersion == 6.0;
        IE7 = fIEVersion == 7.0;
        IE8 = fIEVersion == 8.0;
        if (IE55) {
            return "IE55";
        }
        if (IE6) {
            return "IE6";
        }
        if (IE7) {
            return "IE7";
        }
        if (IE8) {
            return "IE8";
        }
    } //isIE end
    if (isFF) {
        return "FF";
    }
    if (isOpera) {
        return "Opera";
    }
} //myBrowser() end
//以下是调用上面的函数
//if (myBrowser() == "FF") {
//    $("#z_wrapper").html("您的浏览器版本过低，请使用高版本的浏览器打开网站！");
//    $("#z_wrapper").css({
//        "font-size": "24px",
//        "text-align": "center",
//        "line-height": "200px",
//        "color": "red"
//    });
//}
//if (myBrowser() == "Opera") {
//    alert("我是 Opera");
//}
//if (myBrowser() == "Safari") {
//    alert("我是 Safari");
//}
if (myBrowser() == "IE55") {
    $("#z_wrapper").html("您的浏览器版本过低，请使用高版本的浏览器打开网站！");
    $("#z_wrapper").css({
        "font-size": "24px",
        "text-align": "center",
        "line-height": "200px",
        "color": "red"
    });
}
if (myBrowser() == "IE6") {
    $("#z_wrapper").html("您的浏览器版本过低，请使用高版本的浏览器打开网站！");
    $("#z_wrapper").css({
        "font-size": "24px",
        "text-align": "center",
        "line-height": "200px",
        "color": "red"
    });
}
if (myBrowser() == "IE7") {
    $("#z_wrapper").html("您的浏览器版本过低，请使用高版本的浏览器打开网站！");
    $("#z_wrapper").css({
        "font-size": "24px",
        "text-align": "center",
        "line-height": "200px",
        "color": "red"
    });
}
if (myBrowser() == "IE8") {
    $("#z_wrapper").html("您的浏览器版本过低，请使用高版本的浏览器打开网站！");
    $("#z_wrapper").css({
        "font-size": "24px",
        "text-align": "center",
        "line-height": "200px",
        "color": "red"
    });
}

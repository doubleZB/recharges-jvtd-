$(function() {
    //新闻下拉
    $(".navnav").mouseenter(function() {
        $(this).find(".xiala").css("display", "block");
        $(this).find(".xiala1").css("display", "block");
        $(this).find(".xiala2").css("display", "block");
    })
    $(".navnav").mouseleave(function() {
        $(this).find(".xiala").css("display", "none");
        $(this).find(".xiala1").css("display", "none");
        $(this).find(".xiala2").css("display", "none");
    })
})
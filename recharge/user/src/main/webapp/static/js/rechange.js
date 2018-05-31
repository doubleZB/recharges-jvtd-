;
$(function () {
    //$(".am-tabs-bd").css({
    //    "user-select":"text"
    //});
    $(".z_leftList li").each(function () {
        $(".z_leftList li").click(function () {
            var index = $(this).index();
            $(this).addClass("active").siblings().removeClass("active");
            if (index == 0) {
                $("#change1").show();
                $("#change1").siblings().hide();
            } else if (index == 1) {
                $("#change2").show();
                $("#change2").siblings().hide();
            } else if (index == 2) {
                $("#change3").show();
                $("#change3").siblings().hide();
            } else if (index == 3) {
                $("#change4").show();
                $("#change4").siblings().hide();
            } else if (index == 4) {
                $("#change5").show();
                $("#change5").siblings().hide();
            }
        });
    });
});

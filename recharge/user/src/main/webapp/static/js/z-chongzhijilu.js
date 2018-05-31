$(function () {
    //充值tab切换
    $(".z_jl_tab li").each(function () {
        $(".z_jl_tabContent").eq(1).hide();
        $(".z_jl_tab li").on("click", function () {
            $(this).addClass("active").siblings().removeClass("active");
            if ($(this).index() == 0) {
                $(".z_jl_tabContent").eq(0).show();
                $(".z_jl_tabContent").eq(1).hide();
            } else {
                $(".z_jl_tabContent").eq(0).hide();
                $(".z_jl_tabContent").eq(1).show();
            }
        });
    });


});

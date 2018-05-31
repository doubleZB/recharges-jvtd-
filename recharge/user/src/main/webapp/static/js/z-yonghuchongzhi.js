$(function () {
    //充值tab切换
    $(".z_cz_tab li").each(function () {
        $(".z_cz_tab li").on("click", function () {
            $(this).addClass("active").siblings().removeClass("active");
            if ($(this).index() == 0) {
                $(".z_cz_tabContent").eq(0).show();
                $(".z_cz_tabContent").eq(1).hide();
            } else {
                $(".z_cz_tabContent").eq(0).hide();
                $(".z_cz_tabContent").eq(1).show();
            }
        });
    });

    var fuLi = window.parent.$(".z_selectNext li");
    (jQuery);
    $(".z_cz_tab li").eq(0).click(function () {
        fuLi.each(function () {
            if ($(this).index() == 2) {
                $(this).addClass("active").siblings().removeClass("active");
            }
            //                    console.log(fuLi)
        });

    });
    $(".z_cz_tab li").eq(1).click(function () {
        fuLi.each(function () {
            if ($(this).index() == 3) {
                $(this).addClass("active").siblings().removeClass("active");
            }
            //                    console.log(fuLi)
        });
    });



});

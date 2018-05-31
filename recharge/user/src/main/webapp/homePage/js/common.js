$(function () {

    $(".guanbi").click(function () {
        $(".xuanfu").hide();
    })


    //新闻下拉
    $(".navnav").mouseenter(function () {
        $(this).find(".xiala").css("display", "block");
        $(this).find(".xiala1").css("display", "block");
        $(this).find(".xiala2").css("display", "block");
    })
    $(".navnav").mouseleave(function () {
        $(this).find(".xiala").css("display", "none");
        $(this).find(".xiala1").css("display", "none");
        $(this).find(".xiala2").css("display", "none");
    })

    //向下滚动隐藏二级导航
    console.log(document.body.scrollHeight);
    var a = document.body.scrollHeight;
    window.addEventListener("scroll", function (event) {
        var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
        //      console.log(scrollTop);
        if (scrollTop > 20) {

            $(".xiala").hide();
            $(".xiala1").hide();
            $(".xiala2").hide();


        }
    });


    //返回顶部	

    //    $(".xuanfu3").click(function () {
    //        $('html,body').animate({
    //            scrollTop: '0px'
    //        }, 400);
    //    });

   // $(".nav_2 ul li a").each(function (index, elements) {
   //     $(elements).click(function () {
   //         $(this).addClass("heng").parent().siblings().children().removeClass("heng");
   //     })
   // })

    var Fimg = $(".sssp_main .fwal .fwal1 img").width();
    $(".sssp_main .fwal .fwal1 img").css("height", Fimg);
    
    var Fimg1 = $(".fwal_nr1 img").width();
    $(".fwal_nr1 img").css("height", Fimg1);

})

$(function () {
    $(".sssp_main .fwal .fwal1 .p2").each(function () {
        var maxwidth = 120;
        if ($(this).text().length > maxwidth) {
            $(this).text($(this).text().substring(0, maxwidth));
            $(this).html($(this).html() + '...');
        }
    })
})


$(function () {
    //锚点
    $('a[href^="#"]').on('click', function (e) {
        e.preventDefault();

        var target = this.hash,
            $target = $(target);
        $('html, body').stop().animate({
            'scrollTop': $target.offset().top - 60
        }, 900, 'swing', function () {
            window.location.hash = target;
        });
    });

})


$(function () {
    $(".a1").mouseover(function () {
        $(".a1 img").attr("src", "/images/yuying_img7.png");
    });
    $(".a1").mouseout(function () {
        $(".a1 img").attr("src", "/images/yuying_img6.png");
    });
    $(".a2").mouseover(function () {
        $(".a2 img").attr("src", "/images/yuying_img8.png");
    });
    $(".a2").mouseout(function () {
        $(".a2 img").attr("src", "/images/yuying_img9.png");
    });
    $(".a3").mouseover(function () {
        $(".a3 img").attr("src", "/images/yuying_img10.png");
    });
    $(".a3").mouseout(function () {
        $(".a3 img").attr("src", "/images/yuying_img11.png");
    });

    //-------------------------------------------------------



    $(".dianhua").hover(function () {
        $(this).find("a").animate({
            right: "120px"
        });
    }, function () {
        $(this).find("a").animate({
            right: "0px"
        });
    })

})


$(function () {
    var system = {};
    var p = navigator.platform;
    system.win = p.indexOf("Win") == 0;
    system.mac = p.indexOf("Mac") == 0;
    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
    if (system.win || system.mac || system.xll) { //如果是电脑跳转到

    } else { //如果是手机,跳转到
        window.location.href = "/moblie/index.html";
    }
})
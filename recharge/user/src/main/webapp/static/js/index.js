$(function () {
    $(".z_home").click(function () {
        $("iframe").attr("src", "z-home.html");
    });
    //一级菜单点击事件
    $(".z_select li").each(function () {

        $(".z_select li").on("click", function () {
            //获取一级ul
            var selectNext = $(".z_selectNext ul");
            //首页li点击
            if ($(this).index($(".z_home")) == 0) {
                //二级ul消失
                $(".z_selectNext").hide();
            } else {
                //二级ul出现
                $(".z_selectNext").show();
            };
            //一级li点击时css样式改变
            $(this).addClass("z_bgColor").siblings().removeClass("z_bgColor");
            //其他一级li点击
            if ($(this).index() == 1) {

                var html = "<li>流量应用</li><li class='active'><a href='javascript:' onclick='hFist();'>应用概览</a></li><li onclick='hSecond();'><a href='javascript:'>单号充值</a></li><li onclick='hTwo();'><a href='javascript:'>批量充值</a></li><li onclick='hThree();'><a href='javascript:'>订单记录</a></li><li onclick='hFouth();'><a href='javascript:'>商品包统计</a></li>";
                selectNext.html(html);
                //改变内容
                $("iframe").attr("src", "z-liuliangchongzhi.html");
            } else if ($(this).index() == 2) {
                var html = "<li>话费应用</li><li class='active'><a href='javascript:' onclick='hF();'>应用概览</a></li><li onclick='hS();'><a href='javascript:'>单号充值</a></li><li onclick='hT();'><a href='javascript:'>批量充值</a></li><li onclick='hO();'><a href='javascript:'>订单记录</a></li><li onclick='hP();'><a href='javascript:'>商品包统计</a></li>";
                selectNext.html(html);
                $("iframe").attr("src", "z-huafeichongzhi.html");
            } else if ($(this).index() == 3) {
                selectNext.html("<li>财务管理</li><li class='active'><a href='javascript:'>账单明细</a></li>");
                $("iframe").attr("src", "z-caiwuguanli.html");
            } else if ($(this).index() == 4) {
                selectNext.html("<li>账户管理</li><li class='active' onclick='userMessage();'><a href='javascript:'>账户信息</a></li><li onclick='changePasd();'><a href='javascript:'>密码管理</a></li><li onclick='remind();'><a href='javascript:'>余额提醒</a></li>");
                $("iframe").attr("src", "z-zhanghuxinxi.html");
            } else if ($(this).index() == 5) {
                selectNext.html("<li>开发者中心</li><li class='active' onclick=\"allClick('developerAccount.html');\"><a href='javascript:'>开发者账号</a></li><li onclick=\"allClick('rechange.html');\"><a href='javascript:'>话费充值</a></li>");
                $("iframe").attr("src", "developerAccount.html");
            };
            //二级菜单点击
            $(".z_selectNext li").each(function () {
                $(".z_selectNext li").on("click", function () {
                    var index = $(this).index();
                    if (index != 0) {
                        $(this).addClass("active").siblings().removeClass("active");
                    };
                });
            });
        });


    });



});

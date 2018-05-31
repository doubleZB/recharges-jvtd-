$(function () {
    //tab切换
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

    //密码验证
    //登录密码
    $(".z_dSave").click(function () {
        var oldPassword = $("#z_oldPassword").val();
        var newPassword = $("#z_newPassword").val();
        var againPassword = $("#z_againPassword").val();
        if (oldPassword == "") {
            $("#z_oldPassword").siblings(".z_warn").text("原密码不能为空");
        } else if (newPassword == "") {
            $("#z_oldPassword").siblings(".z_warn").text("");
            $("#z_newPassword").siblings(".z_warn").text("新密码不能为空");
        } else if (againPassword == "") {
            $("#z_newPassword").siblings(".z_warn").text("");
            $("#z_againPassword").siblings(".z_warn").text("再输入密码不能为空");
        } else if (newPassword != againPassword) {
            $("#z_againPassword").siblings(".z_warn").text("密码输入错误");
        } else {
            $(".z_warn").text("");
        }
    });
    //支付密码
    //首次设置
    $(".z_pFirst").click(function () {
        var newPass = $("#z_newPass").val();
        var againPass = $("#z_againPass").val();
        if (newPass == "") {
            $("#z_newPass").siblings(".z_warn").text("密码不能为空");
        } else if (againPass == "") {
            $("#z_newPass").siblings(".z_warn").text("");
            $("#z_againPass").siblings(".z_warn").text("确认密码不能为空");
        } else if (newPass != againPass) {
            $("#z_againPass").siblings(".z_warn").text("密码输入错误");
        } else {
            $(".z_warn").text("");
        }
    });

    //重新绑定

    $(".z_pAgain").click(function () {
        var oldPassd = $("#z_oldPassd").val();
        var newPassd = $("#z_newPassd").val();
        var againPassd = $("#z_againPassd").val();
        if (oldPassd == "") {
            $("#z_oldPassd").siblings(".z_warn").text("原密码不能为空");
        } else if (newPassd == "") {
            $("#z_oldPassd").siblings(".z_warn").text("");
            $("#z_newPassd").siblings(".z_warn").text("新密码不能为空");
        } else if (againPassd == "") {
            $("#z_newPassd").siblings(".z_warn").text("");
            $("#z_againPassd").siblings(".z_warn").text("再输入密码不能为空");
        } else if (newPassd != againPassd) {
            $("#z_againPassd").siblings(".z_warn").text("密码输入错误");
        } else {
            $(".z_warn").text("");
        }
    });



});

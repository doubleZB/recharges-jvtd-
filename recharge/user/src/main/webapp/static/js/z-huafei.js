$(function () {

    //流量话费按钮
    $(".z_H_btn").click(function () {
        $(this).each(function () {
            if ($(this).index() == 1) {
                $(".z-all").text("200");
            } else {
                $(".z-all").text("400");
            }
        });
    });


    $(".z_tju .z_runChart").each(function () {
        $(".z_tju .z_runChart").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
        });
    });
    $(".z_tjbb .z_runChart").each(function () {
        $(".z_tjbb .z_runChart").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
        });
    });
    var yf;
    var rq;
    //获取时间
    function mouthChange() {
        var fill = function (d) {
            return d < 10 ? '0' + d : d.toString();
        };

        var current = new Date();
        //年
        var year = current.getFullYear();
        //月
        var month = current.getMonth() + 1;
        //日
        var day = current.getDate();

        //        console.log("当前月份为" + year + '-' + fill(month) + '-' + day);

        var calendar = [];
        var tody = [];


        for (var i = 0; i < 7; i++) {
            calendar.push(fill(month) + "月");
            tody.push(year + '-' + fill(month) + '-' + day);
            month -= 1;
            day -= 1;
            if (month <= 0) {
                year -= 1;
                month = 12;
            }
            if (day <= 0) {
                month -= 1;
                day = 31;
            }
        }

        yf = calendar.sort();
        rq = tody.sort();

    }

    mouthChange();

    //-------------图表数据--------------------------------------------------


    //日周月

    var mouths = yf;
    var weeks = ["前六周", "前五周", "前四周", "前三周", "前两周", "前一周", "本周"];
    var days = rq;
    //sdf(mouths);

    $(".z_tju .z_runChart").eq(0).click(function () {
        sdf(mouths);
    });
    $(".z_tju .z_runChart").eq(1).click(function () {
        sdf(weeks);
    });
    $(".z_tju .z_runChart").eq(2).click(function () {
        sdf(days);
    });
    //// 基于准备好的dom，初始化echarts实例
    //function sdf(a) {
    //    var myChart = echarts.init(document.getElementById('main'));
    //
    //    // 指定图表的配置项和数据
    //    var option = {
    //        title: {
    //            text: ''
    //        },
    //        tooltip: {
    //            trigger: 'axis'
    //        },
    //        legend: {
    //            data: []
    //        },
    //        toolbox: {
    //            feature: {
    //                saveAsImage: {}
    //            }
    //        },
    //        grid: {
    //            left: '3%',
    //            right: '4%',
    //            bottom: '3%',
    //            containLabel: true
    //        },
    //        xAxis: [{
    //            type: 'category',
    //            boundaryGap: false,
    //            name: '时间',
    //            data: a
    //        }],
    //        yAxis: [{
    //            type: 'value',
    //            name: '充值笔数'
    //        }],
    //        series: [{
    //            name: '发送成功',
    //            type: 'line',
    //            stack: '总量',
    //            areaStyle: {
    //                normal: {}
    //            },
    //            data: [5, 1, 3, 14, 2, 5, 2]
    //        }, {
    //            name: '发送失败',
    //            type: 'line',
    //            stack: '总量',
    //            areaStyle: {
    //                normal: {}
    //            },
    //            data: [2, 5, 1, 3, 4, 2, 1]
    //        }, {
    //            name: '未知',
    //            type: 'line',
    //            stack: '总量',
    //            areaStyle: {
    //                normal: {}
    //            },
    //            data: [1, 2, 3, 4, 3, 3, 5]
    //        }]
    //    };


        // 使用刚指定的配置项和数据显示图表。
    //    myChart.setOption(option);
    //
    //}

    //报表数据

    //月
    $(".z_tjbb .z_runChart").eq(0).click(function () {
        $(".z_times").each(function () {

        });
    });


});

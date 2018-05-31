    var dateCount = getLastDay(2017, 2);
    //流量话费按钮
    $("#doc-select-1").change(function () {
        var txt = $(this).val();
        console.log(txt)
        var arrMonth = [];
        var arrDays = [];
        if (txt == 1) {
            $("#doc-select-2").html("");
            $("#doc-select-2").append("<option value='Value'>请选择年份</option>");
            for (var i = 2017; i <= 2020; i++) {
                $("#doc-select-2").append("<option value='Value'>" + i + "</option>");
            }

        } else if (txt == 2) {
            $("#doc-select-2").html("");
            $("#doc-select-2").append("<option value='Value'>请选择月份</option>");
            for (var y = 2017; y <= 2020; y++) {
                for (var j = 1; j <= 12; j++) {
                    $("#doc-select-2").append("<option value='Value'>" + y + "-" + j + "</option>");
                }
            }

        } else {
            $("#doc-select-2").html("");
            $("#doc-select-3").html("");
        }
    });

    function getLastDay(year, month) {
        var new_year = year; //取当前的年份           
        var new_month = month++; //取下一个月的第一天，方便计算（最后一天不固定）           
        if (month > 12) //如果当前大于12月，则年份转到下一年           
        {
            new_month -= 12; //月份减           
            new_year++; //年份增           
        }
        var new_date = new Date(new_year, new_month, 1); //取当年当月中的第一天           
        var date_count = (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate(); //获取当月的天数         
        var last_date = new Date(new_date.getTime() - 1000 * 60 * 60 * 24); //获得当月最后一天的日期  
        return date_count;
    }

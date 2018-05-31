$(function () {
    //$("#doc-ipt-email-2").change(function () {
    //    alert(111);
    //    date();
    //});
    //$("#doc-ipt-email-3").change(function () {
    //    date();
    //});

    //日期选择函数
    function date() {
        var startTime = $("#doc-ipt-email-2").val();
        var endTime = $("#doc-ipt-email-3").val();
        var start = startTime.split("-");
        var startYear = start[0];
        var startMonth = start[1];
        var startDay = start[2];
        var end = endTime.split("-");
        var endYear = end[0];
        var endMonth = end[1];
        var endDay = end[2];
        <!-- console.log(startYear,endYear); -->
        if (startTime !=""&&endTime == "") {
            alert("请选择结束时间！");
            console.log();
        } else if (startYear != endYear) {
            alert("结束时间与开始时间需在同一年！");
            console.log();
        } else if (startMonth != endMonth) {
            alert("结束时间与开始时间需在同一月！");
            console.log();
        } else if (startDay > endDay) {
            alert("结束时间不能小于开始时间！");
            console.log();
        };
    };

    /*//联动
     $.fn.ProvinceCity = function () {
     var _self = this;

     _self.data("yys", ["运营商", "运营商"]);
     _self.data("llb", ["流量包", "流量包"]);

     //分别获取2个下拉框
     var $sel1 = _self.find("#doc-select-2");
     var $sel2 = _self.find("#doc-select-3");
     //运营商下拉
     if (_self.data("yys")) {
     $sel1.append("<option value='" + _self.data("yys")[1] + "'>" + _self.data("yys")[0] + "</option>");
     }
     $.each(GP, function (index, data) {
     $sel1.append("<option value='" + data + "'>" + data + "</option>");
     });
     //流量包下拉
     if (_self.data("llb")) {
     $sel2.append("<option value='" + _self.data("llb")[1] + "'>" + _self.data("llb")[0] + "</option>");
     }

     //联动 控制
     var index1 = "";
     $sel1.change(function () {
     //清空其它下拉框
     $sel2[0].options.length = 0;
     index1 = this.selectedIndex;
     if (index1 == 0) { //当选择的为 “运营商” 时
     if (_self.data("llb")) {
     $sel2.append("<option value='" + _self.data("llb")[1] + "'>" + _self.data("llb")[0] + "</option>");
     }
     } else {
     $.each(GT[index1 - 1], function (index, data) {
     $sel2.append("<option value='" + data + "'>" + data + "</option>");
     });

     }
     }).change();

     };*/


});

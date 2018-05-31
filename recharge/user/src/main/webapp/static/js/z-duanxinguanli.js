$(function () {
    var myChart1 = echarts.init(document.getElementById('z_dT1'));
    var myChart2 = echarts.init(document.getElementById('z_dT2'));
    var myChart3 = echarts.init(document.getElementById('z_dT3'));
    option = {
        title: {
            text: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['2016-03', '2016-04', '2016-05', '2016-06', '2016-07'],
            bottom: '2%'
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '10%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            data: ['1日', '2日', '3日', '4日', '5日', '6日', '7日', '8日', '9日', '10日', '11日', '12日', '13日', '14日', '15日', '16日', '17日', '18日', '19日', '20日', '21日', '22日', '23日', '24日', '25日', '26日', '27日', '28日', '29日', '30日', '31日']
            }],
        yAxis: [{
            type: 'value'
            }],
        series: [{
            name: '2016-03',
            type: 'line',
            stack: '总量',
            areaStyle: {
                normal: {}
            },
            data: [1.5, 1, 0.4, 0.8, 1, 1.2, 1.5, 1.1, 0.2, 2, 1.5, 0.8, 0.3, 4, 3, 2, 1, 4, 5, 6, 5, 8, 2, 3, 1, 5, 4, 2, 1, 3, 1]
            }, {
            name: '2016-04',
            type: 'line',
            stack: '总量',
            areaStyle: {
                normal: {}
            },
            data: []
            }, {
            name: '2016-05',
            type: 'line',
            stack: '总量',
            areaStyle: {
                normal: {}
            },
            data: []
            }, {
            name: '2016-06',
            type: 'line',
            stack: '总量',
            areaStyle: {
                normal: {}
            },
            data: []
            }, {
            name: '2016-07',
            type: 'line',
            stack: '总量',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            areaStyle: {
                normal: {}
            },
            data: []
            }]
    };
    myChart1.setOption(option);
    myChart2.setOption(option);
    myChart3.setOption(option);
});

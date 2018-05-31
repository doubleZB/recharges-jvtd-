<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/shangpinguanli.css">
</head>
<style>
    .checklist1 {
        border: 1px solid #dedede;
        width: 500px;
        overflow: auto;
        padding: 20px;
        padding-top: 5px;
        margin:10px 120px;
    }

    .checklist1 > div {
        float: left;
        display: block;
        margin-left: 10px;
        margin-top: 10px;
    }
</style>
<body>
<!-- content start -->
<div class="admin-content" id="content" v-cloak>
    <div class="admin-content-body">
        <div class="am-tabs" data-am-tabs="{noSwipe: 1}">
            <ul class="am-tabs-nav am-nav am-nav-tabs">
                <li class="am-active"><a href="#tab1">商品管理</a></li>
                <li><a href="#tab2">新增</a></li>
            </ul>

            <div class="am-tabs-bd am-tabs-bd-ofv">
                <div class="am-tab-panel am-active" id="tab1">
                    <form class="am-form-inline" role="form">
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" v-model="clx" style="width: 200px;height:35px;float: left;">
                                <option v-for="option in options3" :value="option.value">{{option.text}}</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" v-model="cyys" style="width: 200px;height:35px;float: left;">
                                <option v-for="option in options1" :value="option.value">{{option.text}}</option>
                            </select>
                        </div>
                        <div class="am-form-group" style="margin-top: 0;">
                            <select class="select1" v-model="csf" style="width: 200px;height:35px;float: left;">
                                <option v-for="option in options2" :value="option.value">{{option.text}}</option>
                            </select>
                        </div>
                        <div class="clearfixed"></div>
                        <div class="am-form-group">
                            <select class="select1" v-model="cfw" style="width: 200px;height:35px;float: left;">
                                <option v-for="option in options4" :value="option.value">{{option.text}}</option>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <input v-model="cmzkl" type="text" class="am-form-field" placeholder="面值&流量颗粒"
                                   style="width: 200px;height:35px;float: left;">
                        </div>
                        <button type="button" class="am-btn am-btn-warning" @click="chaxun"
                                style="width: 120px;margin:auto;margin-top: 20px">查询
                        </button>
                        <button type="button" class="am-btn am-btn-warning" style="width: 120px;margin:auto;margin-top: 20px" onclick="initProductData()">初始化商品数据
                        </button>
                    </form>
                    <hr>
                    <div class="am-u-sm-12 am-u-md-8" style="margin-bottom: 20px;padding-left: 0;">
                        <button type="button" class="am-btn am-btn-warning" @click="xiugai"
                                style="width: 120px;margin: auto;">修改
                        </button>
                        <button type="button" class="am-btn am-btn-warning"
                                style="width: 120px;margin: auto;">删除
                        </button>
                        <span id="ssp">{{ checkID | json }}</span>
                    </div>
                    <table class="am-table am-table-striped am-table-hover">
                        <thead>
                        <tr>
                            <th>
                                <label class="am-checkbox-inline">
                                    <input id="all" type="checkbox" @click="checked">序号
                                </label>
                            </th>
                            <th v-for="col in lcolumns">{{col.name}}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <template v-if="sel">
                            <tr v-for="item in product1">
                                <td>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox"  value="{{item.id}}" name="checkbox" v-model="checkID" @click="checked1()">
                                        {{$index+1}}
                                    </label>
                                </td>
                                <td>{{item.id}}</td>
                                <td>{{item.lx}}</td>
                                <td>{{item.yys}}</td>
                                <td>{{item.sf}}</td>
                                <td>{{item.mz}}</td>
                                <td>{{item.kl}}</td>
                                <td>{{item.fw}}</td>
                                <td>{{item.yxq}}</td>
                                <td>{{item.sj}}</td>
                                <td>{{item.cs}}</td>
                                <!--<td>
                                    <div class="am-btn-toolbar">
                                        <a class="am-btn am-btn-link" style="padding: 0;"
                                           data-am-modal="{target: '#your-modal'}">编辑</a>
                                        <a @click="remove($index)" class="am-btn am-btn-link"
                                           style="padding: 0;color: #F37B1D;">删除</a>
                                    </div>
                                </td>-->
                            </tr>
                        </template>
                        <template v-else>
                            <tr v-for="item in product2">
                                <td>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" value="{{item.id}}" name="checkbox" v-model="checkID" @click="checked1()">
                                        {{$index+1}}
                                    </label>
                                </td>
                                <td>{{item.id}}</td>
                                <td>{{item.lx}}</td>
                                <td>{{item.yys}}</td>
                                <td>{{item.sf}}</td>
                                <td>{{item.mz}}</td>
                            </tr>
                        </template>
                        </tbody>
                    </table>
                    <hr/>
                    <div class="am-cf">
                        共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span> 页
                        <div class="am-fr">
                            <ul class="am-pagination" style="margin: 0">
                                <li><a href="#">上一页</a></li>
                                <li class="am-disabled"><a
                                        href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                                <li><a href="#">下一页</a></li>
                                <li class="am-disabled"><a href="#">|</a></li>
                                <li>
                                    <input type="text" style="width: 30px;height: 30px;margin-bottom: 5px;">
                                </li>
                                <li class="am-active"><a href="#" style="padding: .5rem .4rem;">GO</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="am-tab-panel" id="tab2">
                    <form class="am-form am-form-horizontal">
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">商品类型：</label>
                            <select class="am-form-field" v-model="xlx" style="width: 300px;float: left;" @change="change()">
                                <option v-for="option in options3" :value="option.value">{{option.text}}</option>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">运营商：</label>
                            <select style="width: 300px;float: left;">
                                <option value="1">移动</option>
                                <option value="2">联通</option>
                                <option value="3">电信</option>
                            </select>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">省份：</label>
                            <select style="width: 300px;float: left;" >
                                <c:forEach items="${dicts}" var="dict">
                                    <option value="${dict.key}">${dict.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <template v-if="ok">
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">流量大小：</label>
                                <div class="am-checkbox" style="display: block;width: 200px;float: left;">
                                    <label>
                                        <input type="checkbox"> 全部选择
                                    </label>
                                </div>
                                <div style="clear: both;"></div>
                                <div class="checklist1">
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 100M(10元)
                                        </label>
                                    </div>
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 500M(30元)
                                        </label>
                                    </div>
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 1024M(50元)
                                        </label>
                                    </div>
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 2G(100元)
                                        </label>
                                    </div>

                                </div>
                            </div>
                            <div class="am-form-group" style="margin-top: 1.5rem;">
                                <label class="am-u-sm-2 am-form-label">生效范围：</label>
                                <label class="am-radio-inline">
                                    <input type="radio" value="" name="docInlineRadio"> 全国(可漫游)
                                </label>
                                <label class="am-radio-inline">
                                    <input type="radio" name="docInlineRadio"> 省内（不可漫游）
                                </label>
                            </div>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">有效期：</label>
                                <select style="width: 300px;float: left;">
                                    <option value="option1">选择有效期</option>
                                    <option value="option2">当月</option>
                                    <option value="option3">一个月</option>
                                    <option value="option4">两个月</option>
                                </select>
                            </div>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">生效时间：</label>
                                <label class="am-radio-inline">
                                    <input type="radio" value="" name="docInlineRadio1"> 即时生效
                                </label>
                                <label class="am-radio-inline">
                                    <input type="radio" name="docInlineRadio1"> 次月生效
                                </label>
                            </div>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">有效期：</label>
                                <select style="width: 300px;float: left;">
                                    <option value="option1">选择现充次数</option>
                                    <option value="option2">不限</option>
                                    <option value="option3">一次</option>
                                    <option value="option4">两次</option>
                                </select>
                            </div>
                        </template>
                        <template v-else>
                            <div class="am-form-group">
                                <label class="am-u-sm-2 am-form-label">省份：</label>
                                <div class="am-checkbox" style="display: block;width: 200px;float: left;">
                                    <label>
                                        <input type="checkbox"> 全部选择
                                    </label>
                                </div>
                                <div style="clear: both;"></div>
                                <div class="checklist1">
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 10元
                                        </label>
                                    </div>
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 20元
                                        </label>
                                    </div>
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 30元
                                        </label>
                                    </div>
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 50元
                                        </label>
                                    </div>
                                    <div class="am-checkbox">
                                        <label>
                                            <input type="checkbox"> 100元
                                        </label>
                                    </div>
                                    <div class="am-checkbox" style="line-height: 35px;height: 35px;">
                                        <label>
                                            <input type="checkbox" style="margin-top: 11px;margin-right: 5px;"> 其他面值
                                        </label>
                                        <input type="text" class="am-form-field" placeholder="多个面值用英文分号间隔"
                                               style="width: 200px;margin-left:10px;height: 35px;float:right;">
                                    </div>
                                </div>
                            </div>
                        </template>
                        <div class="am-form-group">
                            <div class="am-u-sm-2 am-u-sm-offset-1">
                                <button type="submit" class="am-btn am-btn-warning"
                                        style="width: 120px;margin:20px auto;">提交
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- content end -->
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
<script src="${pageContext.request.contextPath}/static/js/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vue-resource.js"></script>
<script>
    Array.prototype.unique = function () {
        this.sort();
        var re = [this[0]];
        for (var i = 1; i < this.length; i++) {
            if (this[i] !== re[re.length - 1]) {
                re.push(this[i]);
            }
        }
        return re;
    };
    var vm = new Vue({
        el: "#content",
        data: {
            ok: true,
            checkID: [],
            arr: [],
            sel: true,
            clx: "1",
            cyys: "",
            csf: "",
            cfw: "",
            cmzkl: "",
            xlx: "1",
            xsf: "",
            lcolumns: [
                {name: "商品ID"},
                {name: "商品类型"},
                {name: "运营商"},
                {name: "省份"},
                {name: "面值"},
                {name: "流量颗粒"},
                {name: "生效范围"},
                {name: "有效期"},
                {name: "生效时间"},
                {name: "现充次数"}
            ],
            product1: [
                {
                    id: 1234,
                    lx: "流量",
                    yys: "移动",
                    sf: "北京",
                    mz: "30",
                    kl: "500M",
                    fw: "全国",
                    yxq: "3个月",
                    sj: "3个月",
                    cs: "不限"
                },
                {
                    id: 2234,
                    lx: "流量",
                    yys: "电信",
                    sf: "山西",
                    mz: "10",
                    kl: "100M",
                    fw: "全国",
                    yxq: "3个月",
                    sj: "3个月",
                    cs: "不限"
                },
                {
                    id: 3234,
                    lx: "流量",
                    yys: "移动",
                    sf: "北京",
                    mz: "30",
                    kl: "500M",
                    fw: "全国",
                    yxq: "3个月",
                    sj: "3个月",
                    cs: "不限"
                },
                {
                    id: 4234,
                    lx: "流量",
                    yys: "电信",
                    sf: "山西",
                    mz: "10",
                    kl: "100M",
                    fw: "全国",
                    yxq: "3个月",
                    sj: "3个月",
                    cs: "不限"
                }

            ],
            product2: [
                {id: 5234, lx: "话费", yys: "联通", sf: "河北", mz: "50", kl: "", fw: "", yxq: ""},
                {id: 6234, lx: "话费", yys: "移动", sf: "陕西", mz: "10", kl: "", fw: "", yxq: ""},
                {id: 7234, lx: "话费", yys: "联通", sf: "河北", mz: "50", kl: "", fw: "", yxq: ""},
                {id: 8234, lx: "话费", yys: "移动", sf: "陕西", mz: "10", kl: "", fw: "", yxq: ""}
            ],
            options1: [
                {text: '运营商', value: ''},
                {text: '移动', value: '1'},
                {text: '联通', value: '2'},
                {text: '电信', value: '3'}
            ],
            options2: [
                {text: '省份', value: ''},
                {text: '河北省', value: '1'},
                {text: '山西省', value: '2'},
                {text: '辽宁省', value: '3'},
                {text: '吉林省', value: '4'},
                {text: '黑龙江省', value: '5'},
                {text: '江苏省', value: '6'},
                {text: '浙江省', value: '7'},
                {text: '安徽省', value: '8'},
                {text: '福建省', value: '9'},
                {text: '江西省', value: '10'},
                {text: '山东省', value: '11'},
                {text: '河南省', value: '12'},
                {text: '湖北省', value: '13'},
                {text: '湖南省', value: '14'},
                {text: '广东省', value: '15'},
                {text: '海南省', value: '16'},
                {text: '四川省', value: '17'},
                {text: '贵州省', value: '18'},
                {text: '云南省', value: '19'},
                {text: '陕西省', value: '20'},
                {text: '甘肃省', value: '21'},
                {text: '青海省', value: '22'},
                {text: '台湾省', value: '23'},
                {text: '北京市', value: '24'},
                {text: '天津市', value: '25'},
                {text: '重庆市', value: '26'},
                {text: '上海市', value: '27'},
                {text: '广西壮族自治区', value: '28'},
                {text: '内蒙古自治区', value: '29'},
                {text: '西藏自治区', value: '30'},
                {text: '宁夏回族自治区', value: '31'},
                {text: '新疆维吾尔自治区', value: '32'},
                {text: '香港特别行政区', value: '33'},
                {text: '澳门特别行政区', value: '34'}
            ],
            options3: [
                {text: '流量', value: '1'},
                {text: '话费', value: '2'}
            ],
            options4: [
                {text: '生效范围', value: ''},
                {text: '全国', value: '1'},
                {text: '省内', value: '2'}
            ],
            json: [],
            page: {
                ts: 4,
                dq: 1,
                all: 1
            }
        },
        methods: {
            chaxun: function () {
                if (this.clx == "1") {
                    if ($("#all").prop("checked")) {
                        $("#all").prop("checked", false);
                    }
                    this.checkID = [];
                    this.arr = [];
                    this.lcolumns = "";
                    this.lcolumns = [
                        {name: "商品ID"},
                        {name: "商品类型"},
                        {name: "运营商"},
                        {name: "省份"},
                        {name: "面值"},
                        {name: "流量颗粒"},
                        {name: "生效范围"},
                        {name: "有效期"},
                        {name: "生效时间"},
                        {name: "现充次数"}
                    ];
                    this.sel = true;
                } else {
                    if ($("#all").prop("checked")) {
                        $("#all").prop("checked", false);
                    }
                    this.checkID = [];
                    this.arr = [];
                    this.sel = false;
                    this.lcolumns = "";
                    this.lcolumns = [
                        {name: "商品ID"},
                        {name: "商品类型"},
                        {name: "运营商"},
                        {name: "省份"},
                        {name: "面值"}
                    ];
                }
            },
            remove: function (index) {
                var msg = confirm("你确定删除此记录吗？");
                if (msg == true) {
                    this.json.splice(index, 1)
                } else {
                    return false;
                }
            },
            checked: function () {
                if ($("#all").prop("checked")) {
                    $("input[name='checkbox']").prop("checked", true);
                    for (var i = 0; i < $("input[name='checkbox']").length; i++) {
                        this.arr.push($("input[name='checkbox']:checked").eq(i).val());
                    }
                    this.checkID = this.arr.unique();
                } else {
                    this.checkID = [];
                    $("input[name='checkbox']").prop("checked", false);
                }
            },
            checked1: function () {
                if ($("input[name='checkbox']:checked").length == this.arr.unique().length) {
                    $("#all").prop("checked", true);
                } else {
                    $("#all").prop("checked", false);
                }
            },
            xiugai: function () {
                var len = $("input[name='checkbox']:checked").length;
                if (len == 0) {
                    alert("请选择想要修改的序号！");
                }
            },
            change:function () {
                if(this.ok == true){
                    this.ok = false;
                }else{
                    this.ok = true;
                }
            }
        }
    });
    function initProductData(){
        var index = layer.open({type:3});
        $.ajax({
            url: "/product/initProductData",
            type:'POST',
            async:false,
            data:{"addStoreData":$("#selectData").val()},
            dataType:"text",
            error:function(){
                $(this).addClass("done");
            },
            success: function(data){
                layer.close(index);
                layer.msg(data);
            }
        });
    }

</script>
</body>
</html>

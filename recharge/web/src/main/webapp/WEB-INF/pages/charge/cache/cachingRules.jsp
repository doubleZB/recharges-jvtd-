<%--
  Created by IntelliJ IDEA.
  User: 缓存规则
  Date: 2017/8/11
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath()%>/static/css/Hui-iconfont/1.0.8/iconfont.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/h-ui/css/animations.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/h-ui/css/app.css"/>
    <link rel="stylesheet" href="${path}/static/assets/css/amazeui.min.css"/>
    <!--[if lt IE 9]>
    <link href="<%=request.getContextPath()%>/static/css/h-ui/css/H-ui.ie.css" rel="stylesheet" type="text/css"/>
    <![endif]-->
    <!--[if IE 6]>
    <script type="text/javascript" src="resource/lib/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title></title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/css/h-ui/js/modernizr.custom.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/css/h-ui/js/H-ui.min.js"></script>
    <script src="${path}/static/assets/js/amazeui.min.js"></script>
    <script src="${path}/static/assets/js/jquery.form.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/layer-v3.0.3/layer/layer.js"></script>
    <script src="https://cdn.bootcss.com/vue/2.4.0/vue.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/css/h-ui/js/app.js"></script>
</head>
<style>
    .layui-layer-iframe {
        min-height: calc(100% - 100px);
    }

    .layui-layer-content {
        min-height: calc(100% - 42px);
    }

    .layui-layer-content iframe {
        min-height: 440px;
    }

    .posi {
        width: 160px;
        position: absolute;
        left: 0;
        top: 43px;
        border: 1px solid #ccc;
        background: #fff;
        height: auto;
        max-height: 140px;
        overflow-y: scroll;
        background: #fff;
        z-index: 100;
    }

    .posi li {
        line-height: 35px;
        cursor: pointer;
    }

    .qwe {
        display: block;
        width: 100%;
        height: 100%;
    }

    ol, ul {
        padding: 0;
    }

    address, blockquote, dl, fieldset, figure, hr, ol, p, pre, ul {
        margin: 0;
    }

    .input-box .left {
        width: 100px;
        text-align: right;
        font-size: 14px;
        margin-right: 10px;
        font-weight: bold;
    }

    body {
        font-size: 14px;
    }

    .input-box .right label {
        font-weight: normal;
        cursor: pointer;
        margin-right: 10px;
        margin-bottom: 0;
    }

    .mt-n-20 {
        margin-top: -20px;
    }

    .mt-n-20 label {
        line-height: 1;
    }
    input[type=checkbox], input[type=radio]{
        margin-top:0;
    }
</style>
<body>
<div id="app" v-cloak>
    <div class="tab-box">
        <div :class="['tab', tab1 ? 'active':'' ]" @click="setTab(0)">缓存规则查询</div>
        <div :class="['tab', tab2 ? 'active':'']" @click="setTab(1)">添加规则</div>
    </div>
    <div class="info-list" v-show="listshow">
        <div class="search-box">
            <select class="select radius" name="businessType" id="businessType">
                <option value="">全部</option>
                <option value="1">流量</option>
                <option value="2">话费</option>
            </select>
            <select class="select radius" name="cacheType" id="cacheType">
                <option value="">类型</option>
                <option value="1">商户</option>
                <option value="2">供应商</option>
            </select>
            <input type="text" class="input-text radius" placeholder="商户简称&供应商"
                   name="objectName" id="objectName">
            <select class="select radius" name="status" id="status">
                <option value="">状态</option>
                <option value="1">开启</option>
                <option value="2">关闭</option>
            </select>
            <a class="	btn btn-primary radius size-S" @click="loadPage(1)">查询</a>
        </div>
        <table class="table table-border table-bordered table-hover radius">
            <tr>
                <th>序号</th>
                <th>业务类型</th>
                <th>缓存类型</th>
                <th>约束对象</th>
                <th>运营商</th>
                <th>省份</th>
                <th>卡品</th>
                <th>开关状态</th>
                <th>操作</th>
            </tr>
            <tr v-for="list in lists">
                <td>{{list.id}}</td>
                <td>{{list.businessType==1?'流量':'话费'}}</td>
                <td>{{list.cacheType==1?'商户':'供应商'}}</td>
                <td>{{list.objectName}}</td>
                <td>{{list.operator | getStr}}</td>
                <td>{{list.provinceStr}}</td>
                <td>{{list.positionCodeStr}}</td>
                <td>{{list.status | getStatus}}</td>
                <td>
                    <a class="btn btn-warning radius size-MINI" @click="modifyData(list.id,list)">编辑</a>
                    <a class="btn btn-danger radius size-MINI" @click="deleteData(list.id)">删除</a>
                </td>
            </tr>
        </table>
        <div class="sj" style="color:red;text-align:center;font-size:20px;display: none;">暂无数据</div>

        <hr>
        <div class="am-cf page">
            每页显示&nbsp;
            <select name="eachPageNumber" id="rangNum" @change="loadPage(1)">
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select>&nbsp;条&nbsp;&nbsp共 <span>{{page.ts}}</span>条记录&nbsp;&nbsp;&nbsp;&nbsp;共 <span>{{page.all}}</span>
            页
            <div class="am-fr">
                <ul class="am-pagination" style="margin: 0">
                    <li><a href="javascript:void(0);" @click="prePage(parseInt(page.dq)-1)">上一页</a></li>
                    <li class="am-disabled"><a href="#"><span> {{page.dq}} </span>/<span> {{page.all}} </span></a></li>
                    <li><a href="javascript:void(0);" @click="nextPage(parseInt(page.dq)+1)">下一页</a></li>
                    <%--<li class="am-disabled"><a href="#">|</a></li>--%>
                    <li>
                        <input type="text" id="gotoPage" style="width: 30px;height: 30px;margin-bottom: 5px;"
                               id="goto-page-num"
                               onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                               onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                    </li>
                    <li class="am-active"><a href="javascript:void(0);" @click="gotoPage();"
                                             style="padding: .5rem .4rem;">GO</a></li>
                </ul>
            </div>
        </div>

    </div>

    <div class="info-box" v-show="infoshow">
        <form id="addRulesForm" method="post" enctype="multipart/form-data">
            <div class="input-box">
                <div class="left f-l tabR">业务类型：</div>
                <div class="right f-l">
                    <select class="select" onchange="selectkapin()" name="businessType" id="businessTypeAdd">
                        <option value="1" selected="selected">流量</option>
                        <option value="2">话费</option>
                    </select>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l tabR">缓存类型：</div>
                <div class="right f-l">
                    <label><input type="radio" name="cacheType" value="1" id="user" checked="checked"
                                  @click="Set_SubList(1)" onclick="selectkapin()">&nbsp;商户</label>
                    <label><input type="radio" name="cacheType" value="2" id="supply"
                                  @click="Set_SubList(2)" onclick="selectkapin()">&nbsp;供应商</label>
                </div>
            </div>

            <div class="input-box" v-if="type==1">
                <div class="left f-l tabR"> 商户：</div>
                <div class="right f-l pos-r">
                    <input type="text" class="input-text radius" :data-id="seluser" @focus="GetUserValList"
                           v-model="userVal" oninput="getKeyUserList(this.value)" name="userCnName" id="userCnName"
                           placeholder="商户简称" style="width: 160px;"/>
                    <div class="posi shclass" v-show="user" style="padding: 0;">
                        <ul class="posiNodeUser">
                            <li v-for="list in userCnNames"
                                @click="setUserVal(list.userCnName,list.id)">
                                {{list.userCnName}}
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="input-box" v-else-if="type==2">
                <div class="left f-l tabR"> 供应商：</div>
                <div class="right f-l pos-r">
                    <input type="text" class="input-text radius" :data-id="selgys" @focus="GetValList" v-model="gysval"
                           oninput="getKeyListSupplier(this.value)" name="supplierId" id="supplierId"
                           placeholder="供应商名称"
                           style="width: 160px;"/>
                    <div class="posi gysclass" v-show="gys" style="padding: 0;">
                        <ul class="posiNode">
                            <li v-for="list in supplier"
                                @click="setGysVal(list.name,list.id)">
                                {{list.name}}
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l tabR">运营商：</div>
                <div class="right f-l">
                    <label><input type="checkbox" name="allyunying" :checked="(ch1&&ch2&&ch3)||ch"
                                  @click="checkAllOp()" value="0">&nbsp;全部</label>
                    <label><input type="checkbox" name="operator" value="1" :checked="ch1" @click="checkOp(1)"/>&nbsp;移动</label>
                    <label><input type="checkbox" name="operator" value="2" :checked="ch2" @click="checkOp(2)"/>&nbsp;联通</label>
                    <label><input type="checkbox" name="operator" value="3" :checked="ch3" @click="checkOp(3)"/>&nbsp;电信</label>
                </div>
            </div>

            <div class="input-box" style="    font-size: 14px;">
                <div class="left f-l tabR">卡品：</div>
                <div class="right f-l allkapin" style="width: 80%;">
                    <div v-show="kalist[1].length > 0 && ch1">
                        <label><input type="checkbox" name="allkapin0" @click="clickallop1($event.target.checked)"
                                      :checked="kapch1f">&nbsp;全部移动</label>
                        <div class="mt-n-20">
                            <label v-for="kap,inx in kalist[1]"><input type="checkbox" name="kapin1"
                                                                       :checked="kap.kapch"
                                                                       @click="cc1($event.target.checked,inx)"
                                                                       :value="kap.positionCode"
                                                                       :data-businessType="kap.businessType"
                                                                       :data-operator="kap.operator">&nbsp;{{kap.name}}</label>
                        </div>
                    </div>


                    <div v-show="kalist[2].length > 0 && ch2">
                        <label><input type="checkbox" name="allkapin" @click="clickallop2($event.target.checked)"
                                      :checked="kapch2f">&nbsp;全部联通</label>
                        <div class="mt-n-20">
                            <label v-for="kap,inx in kalist[2]"><input type="checkbox" name="kapin2"
                                                                       :checked="kap.kapch"
                                                                       @click="cc2($event.target.checked,inx)"
                                                                       :value="kap.positionCode"
                                                                       :data-businessType="kap.businessType"
                                                                       :data-operator="kap.operator">&nbsp;{{kap.name}}</label>
                        </div>
                    </div>

                    <div v-show="kalist[3].length > 0 && ch3">
                        <label><input type="checkbox" name="allkapin2" @click="clickallop3($event.target.checked)"
                                      :checked="kapch3f">&nbsp;全部电信</label>

                        <div class="mt-n-20">
                            <label v-for="kap,inx in kalist[3]"><input type="checkbox" name="kapin3"
                                                                       :checked="kap.kapch"
                                                                       @click="cc3($event.target.checked,inx)"
                                                                       :value="kap.positionCode"
                                                                       :data-businessType="kap.businessType"
                                                                       :data-operator="kap.operator">&nbsp;{{kap.name}}</label>
                        </div>
                    </div>
                </div>

            </div>
            <div class="input-box">
                <div class="left f-l tabR">省份：</div>
                <div class="right f-l" style="width: 80%;">
                    <label>
                        <input type="checkbox" name="allchengshi" @click="clickallcity()"/>&nbsp;全部
                    </label>
                    <div class="mt-n-20">
                        <label v-for="city in citys">
                            <div style="display: inline-block"><input type="checkbox" name="city" @click="clickcity()"
                                                                      :value="city.id"/>&nbsp;{{city.value}}
                            </div>
                        </label>
                    </div>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l tabR">缓存开关：</div>
                <div class="right f-l">
                    <select class="select" name="statusAdd" id="statusAdd">
                        <option value="1">开启</option>
                        <option value="2">关闭</option>
                    </select>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l">&nbsp;</div>
                <div class="right f-l">
                    <button class="btn btn-primary radius size-S submit" type="button">提交</button>
                    <button class="btn btn-danger radius size-S" type="button" @click="resertPage()">清空</button>
                </div>
            </div>
        </form>
    </div>

</div>
</body>
</html>
<script>
    var app = new Vue({
        el: "#app",
        data: {
            tab1: true,
            tab2: false,
            listshow: true,
            infoshow: false,
            ch: false,
            citych: false,
            kapch1: false,
            kapch2: false,
            kapch3: false,
            kapch1f: false,
            kapch2f: false,
            kapch3f: false,

            ch1: false,
            ch2: false,
            ch3: false,

            gysval: "",
            gys: false,
            selgys: 0,
            supplier: [],

            user: false,
            seluser: 0,
            userVal: "",
            userCnNames: [],

            type: 1,
            lists: [],
            page: {
                ts: 0,
                dq: 0,
                all: 0
            },
            json: [],
            citys: [],
            kalist: [[], [], [], []]
        },
        methods: {
            checksupplyoruser: function () {
                var returnVal = true
                layer.load(1)
                var userId
                var supplier
                if (this.type == 1) {
                    userId = this.seluser
                } else {
                    supplier = this.selgys
                }
                $.ajax({
                    url: "<%=request.getContextPath()%>/cache/checkRuleIlegle",
                    async: false,
                    data: {
                        userId: userId,
                        supplier: supplier,
                        businessType: $("#businessTypeAdd").val(),
                        cacheType: this.type
                    },
                    success: function (data) {
                        if (data == "false") {
                            layer.msg("同一商户或供应商只能添加一种规则")
                            app.seluser = 0
                            app.selgys = 0
                            app.userVal = ""
                            app.gysval = ""
                            returnVal = false
                        }
                        layer.closeAll("loading")
                    },
                    error: function (data) {
                        layer.msg("服务器返回错误")
                        returnVal = false
                    }
                })
                return returnVal
            },
            clickallop1: function (e) {
                console.log(e)
                var $arr = this.kalist[1];
                this.kapch1f = e;
                $arr.map(function (o) {
                    o.kapch = e
                });
//                console.log($arr)
                this.kalist[1] = $arr;
            },

            clickallop2: function (e) {
                var $arr = this.kalist[2];
                this.kapch2f = e;
                $arr.map(function (o) {
                    o.kapch = e
                });
                this.kalist[2] = $arr;
            },

            clickallop3: function (e) {
                var $arr = this.kalist[3];
                this.kapch3f = e;
                $arr.map(function (o) {
                    o.kapch = e
                });
                this.kalist[3] = $arr;
            },

            clickallcity: function () {
                if ($("input[name='allchengshi']:checked").length > 0) {
                    $("input[name='city']").each(function (i, e) {
                        $(e).prop("checked", true)
                    })
                } else {
                    $("input[name='city']").each(function (i, e) {
                        $(e).prop("checked", false)
                    })
                }
            },
            clickcity: function () {
                if ($("input[name='city']:checked").length == $("input[name='city']").length) {
                    $("input[name='allchengshi']").prop("checked", true)
                } else {
                    $("input[name='allchengshi']").prop("checked", false)
                }
            },
            cc1: function (e, inx) {
                console.log(e, inx)
                var $arr = this.kalist[1];
                $arr[inx].kapch = e;
                if ($("input[name='kapin1']:checked").length == $("input[name='kapin1']").length) {
                    this.kapch1f = true;
                } else {
                    this.kapch1f = false;
                }
            },
            cc2: function (e, inx) {
                console.log(e)
                var $arr = this.kalist[2];
                $arr[inx].kapch = e;
                if ($("input[name='kapin2']:checked").length == $("input[name='kapin2']").length) {
                    this.kapch2f = true;
                } else {
                    this.kapch2f = false;
                }
            },
            cc3: function (e, inx) {
                console.log(e)
                var $arr = this.kalist[3];
                $arr[inx].kapch = e;
                if ($("input[name='kapin3']:checked").length == $("input[name='kapin3']").length) {
                    this.kapch3f = true;
                } else {
                    this.kapch3f = false;
                }
            },
            checkAllOp: function () {
                this.ch = !this.ch;
                this.ch1 = this.ch2 = this.ch3 = this.ch;
                this.kapch1f = this.kapch2f = this.kapch3f = false;
                if (!this.ch) {
                    this.kalist[1].map(function (o) {
                        o['kapch'] = false;
                    });
                    this.kalist[2].map(function (o) {
                        o['kapch'] = false;
                    });
                    this.kalist[3].map(function (o) {
                        o['kapch'] = false;
                    });
                }
            },
            checkOp: function (i) {
                if (i == 1) {
                    this.ch1 = !this.ch1;
                    this.kapch1f = false;
                    if (!this.ch1) {
                        this.kalist[1].map(function (o) {
                            o['kapch'] = false;
                        });
                        console.log(this.kalist[1])
                    }
                }
                if (i == 2) {
                    this.ch2 = !this.ch2;
                    this.kapch2f = false;
                    if (!this.ch2) {
                        this.kalist[2].map(function (o) {
                            o['kapch'] = false;
                        });
                        console.log(this.kalist[2])
                    }
                }
                if (i == 3) {
                    this.ch3 = !this.ch3;
                    this.kapch3f = false;
                    if (!this.ch3) {
                        this.kalist[3].map(function (o) {
                            o['kapch'] = false;
                        });
                        console.log(this.kalist[3])
                    }
                }
                if (this.ch1 && this.ch2 && this.ch3) {
                    this.ch = true;
                } else {
                    this.ch = false;
                }
            },
            setTab: function (tab) {
                if (tab === 0) {
                    this.tab1 = true;
                    this.tab2 = false;
                    this.listshow = true;
                    this.infoshow = false;
                }
                else {
                    this.tab1 = false;
                    this.tab2 = true;
                    this.listshow = false;
                    this.infoshow = true;
                }
            },
            GetValList: function () {
                this.gys = true;
                this.supplier;
            },
            GetUserValList: function () {
                this.user = true;
                this.userCnNames;
            },
            Set_SubList: function (index) {
                this.type = index;
                if (index == 1) {
                    jQuery(".posiNode").html("");
                    getKeyUserList("");
                }
                else {
                    jQuery(".posiNodeUser").html("");
                    getKeyListSupplier("");
                }

            },
            setUserVal: function (val, id) {
                this.userVal = val;
                this.seluser = id;
                this.user = false;
            },
            setGysVal: function (val, id) {
                this.gysval = val;
                this.selgys = id;
                this.gys = false;
            },
            modifyData: function (id, list) { //修改数据方法
//                console.log(list);
//                var id = list.id;
                var businessType = list.businessType;
                console.log(businessType);
                var cacheType = list.cacheType;
                console.log(cacheType);
                var objectName = list.objectName;
                console.log(objectName)
                var index = layer.open({
                    type: 2,
                    title: '编辑缓存',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['95%', "100%"],
                    offset: ['20px', '20px'],
                    content: '<%=request.getContextPath()%>/cache/editRule?id=' + id + "&pageNum=" + app.page.dq + "&businessType" + businessType + "&cacheType" + cacheType + "&objectName" + objectName
                });
                window.name = index
                layer.iframeAuto(index)
            },
            deleteData: function (id) {
                //执行删除数据方法
                layer.confirm("确定要删除该条数据吗？", function (index) {
                    var index = layer.load()
                    $.get('<%=request.getContextPath()%>/cache/deleteRule', {id: id}, function (data) {
                        if (data) {
                            layer.close(index);
                            layer.msg("删除成功")

                            setTimeout(function () {
                                reloadPage(app.page.dq)
                            }, 1000)
                        }
                    })

                })
            },
            resertPage: function () {
                layer.confirm("确定要清空所填写的数据吗？", function (index) {
                    app.selgys = 0
                    app.seluser = 0
                    app.userVal = ""
                    app.gysval = ""
                    app.user = false
                    app.gys = false
                    app.type = 1
                    app.kalist = [[], [], [], []]
                    app.ch = false
                    app.ch1 = false
                    app.ch2 = false
                    app.ch3 = false
                    app.kapch1 = false
                    app.kapch2 = false
                    app.kapch3 = false
                    document.getElementById("addRulesForm").reset()
                    layer.close(index)
                })
            },

            prePage: function (pageNum) {
                if (pageNum == 0) {
                    layer.msg("已经是第一页了")
                    return false
                }

                reloadPage(pageNum)
            },
            nextPage: function (pageNum) {
                if (pageNum > this.page.all) {
                    layer.msg("已经是最后一页了")
                    return false
                }

                reloadPage(pageNum)
            },
            loadPage: function (pageNum) {
                reloadPage(pageNum)
            },

            gotoPage: function () {
                var pageNum = $("#gotoPage").val();
                if (pageNum <= 0) {
                    layer.msg("已经是第一页了")
                    return false
                }
                if (pageNum > this.page.all) {
                    layer.msg("已经是最后一页了")
                    return false
                }
                if (pageNum == '') {
                    layer.tips("请输入页数！", "#gotoPage", {
                        tips: 3
                    });
                    return;
                }
                reloadPage(pageNum);
            },
        },

        filters: {
            getStr: function (operator) {
                var opraters = operator.split(',');
                var str = ""
                for (var i in opraters) {
                    if (opraters[i] == 0) {
                        str += "全部运营商";
                    }
                    if (opraters[i] == 1) {
                        str += "移动";
                    }
                    if (opraters[i] == 2) {
                        str += "联通";
                    }
                    if (opraters[i] == 3) {
                        str += "电信";
                    }
                    if (i < opraters.length - 1) {
                        str += ","
                    }
                }
                return str;
            },
            getStatus: function (status) {
                if (status == 1) {
                    return "开启"
                }
                if (status == 2) {
                    return "关闭"
                }
            }
        },
    });
    //执行函数
    $(function () {
//        alert(1)
        getSupplierProvince();
        mainAccount();
        getSupplier();
        selectkapin();
        reloadPage(1)
    });

    //获取省份
    function getSupplierProvince() {
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getprovince",
            async: false,
            type: 'POST',
            dataType: "json",
            success: function (data) {
                app.citys = data;
            }
        });
    }

    function reloadPage(pageNum) {
        var index = layer.load();
        var businessType = $("#businessType").val();
        var cacheType = $("#cacheType").val();
        var objectName = $("#objectName").val().trim();
        var status = $("#status").val();
        var rangNum = $("#rangNum").val();
        var url = ""
        if (businessType != "") {
            url += "&businessType=" + businessType
        }
        if (cacheType != "") {
            url += "&cacheType=" + cacheType
        }
        if (objectName != "") {
            url += "&objectName=" + objectName
        }
        if (status != "") {
            url += "&status=" + status
        }

        var loadingIndex = layer.load();
        $.ajax({
            url: "/cache/cacheRuleSearch?pageNumber=" + pageNum + "&pageSize=" + rangNum + url,
            type: 'POST',
            dataType: "json",
            data: {},
            async: false,
            error: function () {
                layer.msg("错误！");
                layer.close(index);
                layer.closeAll('loading');
            },
            success: function (data) {
                var list = data.list
                if (list && list.length > 0) {
                    app.lists = list;
                    app.page.ts = data.total;
                    app.page.dq = data.pageNum;
                    app.page.all = data.pages;
                    $(".sj").hide();
                    $(".page").show();
                } else {
                    app.lists = list;
                    $(".sj").show();
                    $(".page").hide();
                    app.page.ts = 0;
                    app.page.dq = 0;
                    app.page.all = 0;
                }
                layer.closeAll('loading');
            }
        });
    }

    //获取供应商
    function getSupplier() {
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getsupplierandchannles",
            async: false,
            type: 'POST',
            dataType: "json",
            success: function (data) {
                app.supplier = data;
            }
        })
    }

    //查询所有主账户
    function mainAccount() {
        $.ajax({
            url: "/user/selectUserList",
            type: 'POST',
            dataType: "json",
            data: {},
            async: false,
            error: function () {
                layer.msg("错误！");
            },
            success: function (data) {
                app.userCnNames = data;
            }
        });
    }

    //到几页
    function gotoPage() {
        var pageNum = $("#gotoPage").val();
        if (pageNum == '') {
            layer.tips("请输入页数！", "#gotoPage", {
                tips: 3
            });
            return;
        }
        reloadPage(pageNum);
    }

    $(document).ready(function () {
        var ConTxt = "";
        var ConTxtUser = "";
        $.each(app.supplier, function (idx, obj) {
            ConTxt += "<li value='" + obj.id + "'>" + obj.name + "</li>";
        });
        $("#supplierId").change(function () {
        });


        $.each(app.userCnNames, function (idx, obj) {
            ConTxtUser += "<li value='" + obj.id + "'>" + obj.userCnName + "</li>";
        });
        $("#userCnName").change(function () {
        })

        $(document).keyup(function (e) {
            var kc = e.keyCode;
            if ((kc >= 48 && kc <= 57) || kc == 32 || kc == 8) {
                getKeyListSupplier($("#supplierId").val());
            }
        })

        //当点击选择了商户或者运营商
        $(".posi").click(function () {
            if (!app.checksupplyoruser()) {
                return false
            }
            selectkapin()
        })
//        $("input[name='operator'],input[name='allyunying']").click(function () {
//            selectkapin()
//        })

        //提交表单，添加规则
        $(".submit").click(function () {
            var businessType = $("#businessTypeAdd :selected").val()
            var status = $("#statusAdd :selected").val()
            var cacheType = $("input[name='cacheType']:checked").val()
            var objectName = "";
            var userId = app.seluser
            var supplier = app.selgys
            if (cacheType == 1) {
                objectName = app.userVal
            } else if (cacheType == 2) {
                objectName = app.gysval
            }

            var operator = []
            if ($("input[name='allyunying']:checked").length > 0) {
                operator = ["0"]
            } else {
                $("input[name='operator']:checked").each(function (i, e) {
                    operator.push($(e).val())
                })
            }
            operator = operator.join(",")
            var kapin = []
            if (businessType == 1) {
                if (app.kapch1f) {
                    kapin.push("111")
                } else {
                    $("input[name='kapin1']:checked").each(function (i, e) {
                        if ($(e).data("operator") == 1)
                            kapin.push($(e).val())
                    })
                }
                if (app.kapch2f) {
                    kapin.push("222")
                } else {
                    $("input[name='kapin2']:checked").each(function (i, e) {
                        if ($(e).data("operator") == 2)
                            kapin.push($(e).val())
                    })
                }
                if (app.kapch3f) {
                    kapin.push("333")
                } else {
                    $("input[name='kapin3']:checked").each(function (i, e) {
                        if ($(e).data("operator") == 3)
                            kapin.push($(e).val())
                    })
                }
            } else {
                if (app.kapch1f) {
                    kapin.push("444")
                } else {
                    $("input[name='kapin1']:checked").each(function (i, e) {
                        if ($(e).data("operator") == 1)
                            kapin.push($(e).val())
                    })
                }
                if (app.kapch2f) {
                    kapin.push("555")
                } else {
                    $("input[name='kapin2']:checked").each(function (i, e) {
                        if ($(e).data("operator") == 2)
                            kapin.push($(e).val())
                    })
                }
                if (app.kapch3f) {
                    kapin.push("666")
                } else {
                    $("input[name='kapin3']:checked").each(function (i, e) {
                        if ($(e).data("operator") == 3)
                            kapin.push($(e).val())
                    })
                }
            }
            kapin = kapin.join(",")

            var city = []
            if ($("input[name='allchengshi']:checked").length > 0) {
                city = ["0"]
            } else {
                $("input[name='city']:checked").each(function (i, e) {
                    city.push(parseInt($(e).val()))
                })
            }
            city = city.join(",")

            if (userId == "" && supplier == "") {
                layer.msg("请选择商户或供应商")
                return false
            }
            if (objectName == "") {
                layer.msg("请选择商户或供应商")
                return false
            }
            if (operator.length == "") {
                layer.msg("请选择运营商")
                return false
            }
            if (kapin.length == "") {
                layer.msg("请选择卡品")
                return false
            }
            if (city.length == "") {
                layer.msg("请选择城市")
                return false
            }
            if (!status) {
                layer.msg("请选择缓存开关")
                return false
            }

            $(".submit").attr("disabled", "disabled")
            var data = {
                businessType: businessType,
                cacheType: cacheType,
                userId: userId,
                supplier: supplier,
                operator: operator,
                positionCode: kapin,
                province: city,
                status: status,
                objectName: objectName
            }
            $.post("<%=request.getContextPath()%>/cache/saveRule", data, function (data) {
                var data = $.parseJSON(data)
                if (data.status == "true") {
                    layer.msg(data.msg)
                    $(".submit").removeAttr("disabled")
                    app.selgys = 0
                    app.seluser = 0
                    app.userVal = ""
                    app.gysval = ""
                    app.user = false
                    app.gys = false
                    app.kalist = [[], [], [], []]
                    app.ch = false
                    app.ch1 = false
                    app.ch2 = false
                    app.ch3 = false
                    app.kapch1 = false
                    app.kapch2 = false
                    app.kapch3 = false
                    app.Set_SubList(1)
                    document.getElementById("addRulesForm").reset()
                    app.type = 1
                    setTimeout(function () {
                        reloadPage(app.page.dq)
                        app.setTab(0)
                        reloadPage(1)
                    }, 1000)
                } else {
                    layer.msg(data.msg)
                    $(".submit").removeAttr("disabled")
                }
            })
        });
    });

    //查询卡品
    function selectkapin() {
        var businessTypeAdd = $("#businessTypeAdd :selected").val();
        var cacheType = $("input[name='cacheType']:checked").val();
        var userId = app.seluser;
        var supplier = app.selgys;
        var operator = [0]
//        if (app.ch) {
//            operator = [0]
//        } else {
//            if (app.ch1) {
//                operator.push(1)
//            }
//            if (app.ch2) {
//                operator.push(2)
//            }
//            if (app.ch3) {
//                operator.push(3)
//            }
//        }
        app.ch = app.ch1 = app.ch2 = app.ch3 = false;
        app.kapch1 = app.kapch2 = app.kapch3 = false;
        app.kapch1f = app.kapch2f = app.kapch3f = false;


        if (businessTypeAdd && cacheType && (userId || supplier)) {
            $.post("<%=request.getContextPath()%>/cache/getPosition", {
                businessType: businessTypeAdd,
                cacheType: cacheType,
                userId: userId,
                supplier: supplier,
                operator: operator.join(",")
            }, function (data) {
                var list = $.parseJSON(data)
                var kapinArr = [];
                kapinArr[3] = [];
                kapinArr[1] = [];
                kapinArr[2] = [];
                for (var i in list) {
                    kapinArr[list[i].operator].push(list[i])
                }
                kapinArr.map(function (parent) {
                    parent.map(function (child) {
                        child['kapch'] = false
                    })
                });
                console.log(kapinArr);
                app.kalist = kapinArr;

                console.log(app.kalist)
            })
        } else {
            var kapinArr = []
            kapinArr[3] = []
            kapinArr[1] = []
            kapinArr[2] = []
            app.kalist = kapinArr

        }
    }

    function getKeyListSupplier(key) {

        var ConTxt1 = "";
        var arr = new Array();
        $.each(app.supplier, function (i, obj1) {
            arr.push(obj1.name);
            if (key == "" && obj1.name != null) {
                ConTxt1 += "<li value='" + obj1.id + "'>" + obj1.name + "</li>";
            }
            else {
                if (obj1.name != null && obj1.name.indexOf(key) > -1) {
                    ConTxt1 += "<li value='" + obj1.id + "'>" + obj1.name + "</li>";
                }
            }
        })

        setTimeout(function () {
            $(".posi").hover(function () {
            }, function () {
                app.gys = false;
            })
            $(".posiNode").html(ConTxt1).find("li").off().on("click", function () {
                app.gysval = $(this).html();
                app.selgys = $(this).attr("value");
                app.gys = false;
            });
        }, 200)

    }

    function getKeyUserList(key) {
        var ConTxt = "";
        $.each(app.userCnNames, function (i, obj) {
            if (key == "" && obj.userCnName != null) {
                ConTxt += "<li value='" + obj.id + "'>" + obj.userCnName + "</li>";
                app.userVal = "";
                app.seluser = 0;
            }
            else {
                if (obj.userCnName != null && obj.userCnName.indexOf(key) > -1) {
                    ConTxt += "<li value='" + obj.id + "'>" + obj.userCnName + "</li>";
                }
            }
        })
        setTimeout(function () {
            $(".posi").mouseout(function () {
            })
            $(".posiNodeUser").html(ConTxt).find("li").off().on("click", function () {
                app.userVal = $(this).html();
                app.seluser = $(this).attr("value");
                app.user = false;
            })
        }, 200)


    }
</script>

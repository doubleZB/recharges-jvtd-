<%--
  Created by IntelliJ IDEA.
  User: 缓存规则
  Date: 2017/8/11
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <script src="<%=request.getContextPath()%>/static/js/layer-v3.0.3/layer/layer.js"></script>
    <script src="${path}/static/assets/js/jquery.form.js"></script>
    <script src="https://cdn.bootcss.com/vue/2.4.0/vue.min.js"></script>
    <script src="<%=request.getContextPath()%>/static/css/h-ui/js/app.js"></script>
</head>
<style>
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

    ol, ul {
        padding: 0;
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
<div id="appEdit" v-cloak>

    <div class="info-box">
        <form id="editRulesForm" method="post" enctype="multipart/form-data">
            <div class="input-box">
                <div class="left f-l">业务类型：</div>
                <div class="right f-l">
                    <select class="select" name="businessType" id="businessTypeAdd" disabled>
                        <option value="1" <c:if test="${role.getBusinessType()==1}"> selected="selected"</c:if>>流量
                        </option>
                        <option value="2" <c:if test="${role.getBusinessType()==2}"> selected="selected"</c:if>>话费
                        </option>
                    </select>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l">缓存类型：</div>
                <div class="right f-l">
                <span><input type="radio" name="cacheType" value="1" id="user"
                <c:if test="${role.getCacheType()==1}"> checked="checked" </c:if>
                             @click="Set_SubList(1)" disabled>&nbsp;商户</span>
                    <span><input type="radio" name="cacheType" value="2" id="supply"
                    <c:if test="${role.getCacheType()==2}"> checked="checked" </c:if>
                                 @click="Set_SubList(2)" disabled>&nbsp;供应商</span>
                </div>
            </div>

            <%--<div class="input-box" v-if="type===1">--%>
            <div class="input-box" v-show="type===1">
                <div class="left f-l"> 商户：</div>
                <div class="right f-l pos-r">
                    <input type="text" class="input-text radius" :data-id="seluser" disabled name="userCnName"
                           id="userCnName"
                           placeholder="商户简称" value="${role.objectName}" style="width: 160px;"/>
                    <div class="posi shclass" v-show="user" style="padding: 0;">
                        <ul class="posiNodeUser">
                            <li v-for="list in userCnNames" @click="setUserVal(list.userCnName,list.id)">
                                {{list.userCnName}}
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <%--<div class="input-box" v-else-if="type===2">--%>
            <div class="input-box" v-show="type===2">
                <div class="left f-l"> 供应商：</div>
                <div class="right f-l pos-r">
                    <input type="text" class="input-text radius" :data-id="selgys" v-model="gysval"
                           name="supplierId" id="supplierId"
                           disabled
                           style="width: 160px;"/>
                    <%--   <div class="posi gysclass" v-show="gys" style="padding: 0;">
                           <ul class="posiNode">
                               <li v-for="list in supplier" @click="setVal(list.name,list.id)">{{list.name}}</li>
                           </ul>
                       </div>--%>
                    <%--  <input type="text" class="input-text radius" :data-id="selgys" @focus="GetValList" v-model="gysval"
                             oninput="getKeyListSupplier(this.value)" name="supplierId" id="supplierId"
                             placeholder="供应商名称" :value="gysval"
                             style="width: 160px;"/>
                      <div class="posi gysclass" v-show="gys" style="padding: 0;">
                          <ul class="posiNode">
                              <li v-for="list in supplier" @click="setVal(list.name,list.id)">{{list.name}}</li>
                          </ul>
                      </div>--%>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l">运营商：</div>
                <div class="right f-l">
                    <label><input type="checkbox" name="allyunying" :checked="(ch1&&ch2&&ch3)||ch"
                                  @click="checkAllOp()"/>&nbsp;全部</label>
                    <label><input type="checkbox" name="operator" value="1" :checked="ch1" @click="checkOp(1)"/>&nbsp;移动</label>
                    <label><input type="checkbox" name="operator" value="2" :checked="ch2" @click="checkOp(2)"/>&nbsp;联通</label>
                    <label><input type="checkbox" name="operator" value="3" :checked="ch3" @click="checkOp(3)"/>&nbsp;电信</label>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l">卡品：</div>
                <div class="right f-l allkapin" style="width: 80%;">
                    <%--<div v-show="kalist[1].length>0 || kapch1">--%>
                    <div v-show="ch1 && kalist[1].length>0">
                        <label><input type="checkbox" name="allkapin0" @click="allChec(1,$event.target.checked)"
                                      :checked="kapch1 && childJ1" class="childJ1">&nbsp;全部移动</label>
                        <div class="mt-n-20">
                            <label v-for="kap,inx in kalist[1]"><input type="checkbox" name="kapin1"
                                                                       :checked="kap.kapch"
                                                                       :value="kap.positionCode"
                                                                       :data-businessType="kap.businessType"
                                                                       :data-operator="kap.operator"
                                                                       class="status1Child"
                                                                       @click="child(1,inx,$event.target.checked)">&nbsp;{{kap.name}}</label>
                        </div>
                    </div>


                    <%--<div v-show="kalist[2].length>0 || kapch2">--%>
                    <div v-show="ch2 && kalist[2].length>0">
                        <label><input type="checkbox" name="allkapin" @click="allChec(2,$event.target.checked)"
                                      :checked="kapch2 && childJ2" class="childJ2">&nbsp;全部联通</label>
                        <div class="mt-n-20">
                            <label v-for="kap,inx in kalist[2]"><input type="checkbox" name="kapin2"
                                                                       :checked="kap.kapch"
                                                                       :value="kap.positionCode"
                                                                       :data-businessType="kap.businessType"
                                                                       :data-operator="kap.operator"
                                                                       class="status2Child"
                                                                       @click="child(2,inx,$event.target.checked)">&nbsp;{{kap.name}}</label>
                        </div>
                    </div>


                    <%--<div v-show="kalist[3].length>0 || kapch3">--%>
                    <div v-show="ch3 && kalist[3].length>0">

                        <label><input type="checkbox" name="allkapin2" @click="allChec(3,$event.target.checked)"
                                      :checked="kapch3 && childJ3" class="childJ3">&nbsp;全部电信</label>
                        <div class="mt-n-20">
                            <label v-for="kap,inx in kalist[3]"><input type="checkbox" name="kapin3"
                                                                       :checked="kap.kapch"
                                                                       :value="kap.positionCode"
                                                                       :data-businessType="kap.businessType"
                                                                       :data-operator="kap.operator"
                                                                       class="status3Child"
                                                                       @click="child(3,inx,$event.target.checked)">&nbsp;{{kap.name}}</label>
                        </div>
                    </div>

                </div>
            </div>
            <div class="input-box">
                <div class="left f-l">省份：</div>
                <div class="right f-l" style="width: 80%;">
                    <label>
                        <input type="checkbox" name="allchengshi" @click="clickallcity($event.target.checked)"
                               :checked="provinceCheck"/>&nbsp;全部
                    </label>
                    <div class="mt-n-20">
                        <label v-for="city,inx in citys">
                            <input type="checkbox" name="city" @click="clickcity(inx,$event.target.checked)"
                                   :checked="city.checked"
                                   :value="city.id"/>&nbsp;{{city.value}}
                        </label>
                    </div>
                </div>
            </div>

            <div class="input-box">
                <div class="left f-l">缓存开关：</div>
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
                    <button class="btn btn-danger radius size-S" @click="resertPage()" type="button">清空</button>
                </div>
            </div>
        </form>
    </div>

</div>
</body>
</html>
<script>

    var cacheType =  ${role.getCacheType()};
    var app = new Vue({
        el: "#appEdit",
        data: {
            ch: ${"0".equals(role.getOperator())?true:false},
            <%--citych: ${("0").equals(role.getProvince())?true:false},--%>
            arrKapin: [],
            kapch3: false,
            kapch1: false,
            kapch2: false,
            positionCode: "${rulePositionCode}",
            province: "${role.getProvince()}",
            provinceCheck: false,
            operator: "${role.getOperator()}",

            ch1: false,
            ch2: false,
            ch3: false,

            childJ1: true,
            childJ2: true,
            childJ3: true,


            checked1: false,
            checked2: false,
            checked3: false,

            gysval: "",
            gys: false,
            selgys: 0,
            supplier: [],

            user: false,
            seluser: 0,
            userVal: "",
            userCnNames: [],

            type: cacheType == 1 ? 1 : 2,
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
            allChec: function (num, $checked) {
                var that = this;

                if (num == 1) {
                    that.checked1 = true;
                    that.childJ1 = that.kapch1 = $checked;
                } else if (num == 2) {
                    that.checked2 = true;
                    that.childJ2 = that.kapch2 = $checked;
                } else {
                    that.checked3 = true;
                    that.childJ3 = that.kapch3 = $checked;
                }
                var $arr = this.kalist[num];
                $arr.map(function (o) {
                    o.kapch = $checked
                });
            },
            child: function (num, inx, $checked) {
                var that = this;
                var $arr = this.kalist[num];
                $arr[inx].kapch = $checked;
                if (num == 1) {
                    that.checked1 = true;
                    if ($("input[name='kapin1']:checked").length == $("input[name='kapin1']").length) {
                        console.log('全選')
                        that.childJ1 = that.kapch1 = true;
                    } else {
                        console.log('全不選')
                        that.childJ1 = false;
                    }

                } else if (num == 2) {
                    that.checked2 = true;
                    if ($("input[name='kapin2']:checked").length == $("input[name='kapin2']").length) {
                        console.log('全選')
                        that.childJ2 = that.kapch2 = true;
                    } else {
                        console.log('全不選')
                        that.childJ2 = false;
                    }
                } else {
                    that.checked3 = true;
                    if ($("input[name='kapin3']:checked").length == $("input[name='kapin3']").length) {
                        console.log('全選')
                        that.childJ3 = that.kapch3 = true;
                    } else {
                        console.log('全不選')
                        that.childJ3 = false;
                    }
                }
                console.log(this.province)
            },
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
            /*        clickallop1: function () {
             this.kapch1 = !this.kapch1
             },
             clickallop2: function () {
             this.kapch2 = !this.kapch2
             },
             clickallop3: function () {
             this.kapch3 = !this.kapch3
             },*/
            clickallcity: function ($checked) {
                this.provinceCheck = $checked;
                if ($checked) {
                    this.citys.map(function (o) {
                        o['checked'] = true
                    })
                } else {
                    this.citys.map(function (o) {
                        o['checked'] = false
                    })
                }
                console.log(this.province)
            },
            clickcity: function (inx, $checked) {
                this.citys[inx]['checked'] = $checked;
                if ($("input[name='city']:checked").length == $("input[name='city']").length) {
                    this.provinceCheck = true;

                } else {
                    this.provinceCheck = false;
                }
                console.log(this.province)
            },
            checkAllOp: function () {
                this.ch = !this.ch;
                this.ch1 = this.ch2 = this.ch3 = this.ch
                if (!this.ch) {
//                    alert('取消')
                    this.kalist[1].map(function (o) {
                        o['kapch'] = false;
                    })
                    this.kalist[2].map(function (o) {
                        o['kapch'] = false;
                    })
                    this.kalist[3].map(function (o) {
                        o['kapch'] = false;
                    })
                }
            },
            checkOp: function (i) {
                if (i == 1) {
                    this.ch1 = !this.ch1;
                    if (!this.ch1) {
                        this.childJ1 = false;
                        this.kalist[1].map(function (o) {
                            o['kapch'] = false;
                        })
                        console.log(this.kalist[1])
                    }
                }
                if (i == 2) {
                    this.ch2 = !this.ch2;
                    if (!this.ch2) {
                        this.childJ2 = false;
                        this.kalist[2].map(function (o) {
                            o['kapch'] = false;
                        })
                    }
                }
                if (i == 3) {
                    this.ch3 = !this.ch3;
                    if (!this.ch3) {
                        this.childJ3 = false;
                        this.kalist[3].map(function (o) {
                            o['kapch'] = false;
                        })
                    }
                }
                if (this.ch1 && this.ch2 && this.ch3) {
                    this.ch = true
                } else {
                    this.ch = false;
                }
            },
            GetValList: function () {
                this.gys = true;
                this.supplier;
            },
            setVal: function (val, id) {
                this.gysval = val;
                this.selgys = id;
                this.gys = false;
                this.checksupplyoruser()
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
                this.checksupplyoruser()
            },
            modifyData: function (id) { //修改数据方法
                layer.open({
                    type: 2,
                    title: '编辑缓存',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['800px', '100%'],
                    content: '<%=request.getContextPath()%>/cache/editRule?id=' + id
                });
            },
            deleteData: function (id) {
                //执行删除数据方法
                layer.confirm("确定要删除该条数据吗？", function (index) {
                    layer.close(index);
                })
            },
            resertPage: function () {
                layer.confirm("确定要清空所填写的数据吗？", function (index) {
                    document.getElementById("editRulesForm").reset()
                    layer.close(index)
                })
            },


            issubstr: function (arr1, obj) {
                var arr = arr1.split(",");
                console.log(arr1);
                for (var i in arr) {
                    if (obj == arr[i]) {
                        return true
                    }
                }
                return false
            }
        },

    });
    //执行函数
    $(function () {
        getSupplierProvince();
        mainAccount();
        getSupplier();

        if (cacheType == 1) {
            app.seluser = ${role.getUserId()}
                    app.userVal = "${role.getObjectName()}"
        }
        if (cacheType == 2) {
            app.selgys = ${role.getSupplier()}
                    app.gysval = "${role.getObjectName()}"
        }

        var list = ${positionCode};
        console.log(app.positionCode);
        var kalist = [];
        kalist[3] = [];
        kalist[1] = [];
        kalist[2] = [];
        for (var i in list) {
            app.arrKapin.push(list[i].id);
            kalist[list[i].operator].push(list[i]);
        }

        app.kapch1 = (app.issubstr(app.positionCode, "111") || app.issubstr(app.positionCode, "444")) ? true : false;
        app.kapch2 = (app.issubstr(app.positionCode, "222") || app.issubstr(app.positionCode, "555")) ? true : false;
        app.kapch3 = (app.issubstr(app.positionCode, "333") || app.issubstr(app.positionCode, "666")) ? true : false;
        app.ch = app.operator == "0" ? true : false;

        if (app.ch) {
            app.ch1 = true;
            app.ch2 = true;
            app.ch3 = true;
        } else {
            app.ch1 = app.issubstr(app.operator, '1');
            app.ch2 = app.issubstr(app.operator, '2');
            app.ch3 = app.issubstr(app.operator, '3');
        }


        // -- 移动
        kalist[1].map(function (o) {
            if (app.kapch1) {
                o['kapch'] = true;
            } else {
                o['kapch'] = checkChecked(o.positionCode)
            }

        });
        // -- 联通
        kalist[2].map(function (o) {
            if (app.kapch2) {
                o['kapch'] = true;
            } else {
                o['kapch'] = checkChecked(o.positionCode)
            }
        });
        // -- 电信
        kalist[3].map(function (o) {
            if (app.kapch3) {
                o['kapch'] = true;
            } else {
                o['kapch'] = checkChecked(o.positionCode)
            }
        });

        console.log(kalist);
        app.kalist = kalist;
    });

    function checkChecked(obj) {
        var list = app.positionCode;
        var arr = list.split(",");
//        console.log(list);
//        console.log(obj);
        return arr.indexOf(obj) != -1 ? true : false;
    }

    //获取省份
    function getSupplierProvince() {
        $.ajax({
            url: "${pageContext.request.contextPath}/channels/getprovince",
            async: false,
            type: 'POST',
            dataType: "json",
            success: function (data) {
                if (app.province == '0') {
                    app.provinceCheck = true;
                    data.map(function (o) {
                        o['checked'] = true
                    })
                } else {
                    data.map(function (o) {
                        o['checked'] = app.issubstr(app.province, o.id)
                    })
                }
                console.log(data);
                app.citys = data;
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

    $(document).ready(function () {
        var ConTxt = "";
        var ConTxtUser = "";
        $.each(app.supplier, function (idx, obj) {
            ConTxt += "<li value='" + obj.id + "'>" + obj.name + "</li>";
        });
//    $(".posiNode").html(ConTxt);
        $("#supplierId").change(function () {
        });


        $.each(app.userCnNames, function (idx, obj) {
            ConTxtUser += "<li value='" + obj.id + "'>" + obj.userCnName + "</li>";
        });
//    $(".posiNodeUser").html(ConTxtUser);
        $("#userCnName").change(function () {
        })

        $(document).keyup(function (e) {
            var kc = e.keyCode;
            if ((kc >= 48 && kc <= 57) || kc == 32 || kc == 8) {
                getKeyListSupplier($("#supplierId").val());
                //getKeyUserList($("#userCnName").val());
            }
        })

        //当点击选择了商户或者运营商
        $(".posi").click(function () {
            if (!app.checksupplyoruser()) {
                return false
            }
            selectkapin()
        })
        $("input[name='operator'],input[name='allyunying']").click(function () {
//            selectkapin()
        })

        //提交表单，添加规则
        $(".submit").click(function () {
            submit()
        });
    });

    function submit() {
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
            if (app.kapch1 && app.childJ1) {
                kapin.push("111")
            } else {
                $("input[name='kapin1']:checked").each(function (i, e) {
                    if ($(e).data("operator") == 1)
                        kapin.push($(e).val())
                })
            }
            if (app.kapch2 && app.childJ2) {
                kapin.push("222")
            } else {
                $("input[name='kapin2']:checked").each(function (i, e) {
                    if ($(e).data("operator") == 2)
                        kapin.push($(e).val())
                })
            }
            if (app.kapch3 && app.childJ3) {
                kapin.push("333")
            } else {
                $("input[name='kapin3']:checked").each(function (i, e) {
                    if ($(e).data("operator") == 3)
                        kapin.push($(e).val())
                })
            }
        } else {
            if (app.kapch1 && app.childJ1) {
                kapin.push("444")
            } else {
                $("input[name='kapin1']:checked").each(function (i, e) {
                    if ($(e).data("operator") == 1)
                        kapin.push($(e).val())
                })
            }
            if (app.kapch2 && app.childJ2) {
                kapin.push("555")
            } else {
                $("input[name='kapin2']:checked").each(function (i, e) {
                    if ($(e).data("operator") == 2)
                        kapin.push($(e).val())
                })
            }
            if (app.kapch3 && app.childJ3) {
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
            id:${role.getId()},
            businessType: businessType,
            cacheType: cacheType,
            userId: userId,
            supplier: supplier,
            operator: operator,
            positionCode: kapin,
            province: city,
            status: status,
            objectName: objectName,
            createUser: "${role.getCreateUser()}",
            createTime: "${role.getCreateTime()}",
        }

        $.ajax({
            type: 'POST',
            url: "<%=request.getContextPath()%>/cache/saveRule",
            data: data,
            success: function (data) {
//                var data = $.parseJSON(data)
                if (data.status == "true") {
                    layer.msg(data.msg)
                    setTimeout(function () {
                        parent.reloadPage(${pageNum})
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }, 1000)
                } else {
                    layer.msg(data.msg)
                    $(".submit").removeAttr("disabled")
                }

            },
            error: function (data) {
                layer.msg("编辑失败:" + data.statusText)
                $(".submit").removeAttr("disabled")
            },
            dataType: "json"
        });
    }

    //查询卡品
    function selectkapin() {
        var businessTypeAdd = $("#businessTypeAdd :selected").val()
        var cacheType = $("input[name='cacheType']:checked").val()
        var userId = app.seluser
        var supplier = app.selgys
        var operator = []
        if (app.ch) {
            operator = [0]
        } else {
            if (app.ch1) {
                operator.push(1)
            }
            if (app.ch2) {
                operator.push(2)
            }
            if (app.ch3) {
                operator.push(3)
            }
        }

        app.kapch1 = false
        app.kapch2 = false
        app.kapch3 = false
        if (businessTypeAdd && cacheType && (userId || supplier) && operator.length > 0) {
            $.post("<%=request.getContextPath()%>/cache/getPosition", {
                businessType: businessTypeAdd,
                cacheType: cacheType,
                userId: userId,
                supplier: supplier,
                operator: operator.join(",")
            }, function (data) {
                var list = $.parseJSON(data)
                var kapinArr = []
                kapinArr[3] = []
                kapinArr[1] = []
                kapinArr[2] = []
                for (var i in list) {
                    kapinArr[list[i].operator].push(list[i])
                }

                kapinArr.map(function (parent) {
                    parent.map(function (child) {
                        child['kapch'] = false
                    })
                })
                console.log(kapinArr)
                app.kalist = kapinArr
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
                app.checksupplyoruser()
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
                app.checksupplyoruser()
            })
        }, 200)
    }
</script>

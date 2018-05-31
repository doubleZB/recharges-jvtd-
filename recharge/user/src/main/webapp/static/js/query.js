
var vm = new Vue({
    el: '#wear',
    data: {
        list: [{ name: "优酷",value:"4" }, { name: "腾讯" ,value:"6"}, { name: "爱奇艺",value:"5" },{ name: "搜狐",value:"7" },{ name: "乐视",value:"8" },{ name: "PPTV",value:"9" }],
        listM: [],
        listMD:[],
        lx: [{ name: "普通会员",value:"0" },{ name: "超级会员",value:"1" }],
        filter: /^1[3|4|5|8][0-9]\d{8}$/,
        szReg: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
        QQE: /^[1-9]{1}\d{4-10}$/,
        payType: 1,
        colorShow: false,
        showZ: false,
        tel: '',//手机号
        numTit: 0,
        numClass: 0,
        Member:'0',//选择充值类型
        Denomination: '1',//选择面额
        numME: 0,
        payMoney: '00',//单个充值的金额
        AllMoney: '',//多个充值的总金额
        sty: 0,
        passWordZ: '',//支付密码
        textAr: '',//批量手机号的molder
        BombBoxShow: false,//支付弹框
        numberAllL:'',
        numberStat:'',
        txtcon:'请输入手机号或者邮箱',
    },
    created:function(){
        this.$http.post('/video/videoProduct', {
            operator: 4
        }, {
            emulateJSON: true
        }).then(function (res) {
            console.log(res.data);
            var date = res.data.result;
            /*this.lx = res.data.list;*/
            this.operator = 4;
            // listM 面值
            this.listM = res.data.list;
            this.payMoney = this.listM[0].amount;//金额

        })
       //批量
        this.$http.post('/video/videoProduct', {
            operator: this.list[this.numTit].value,
            videoType: this.Member,
        }, {
            emulateJSON: true
        }).then(function (res) {
            console.log(res.data);
            this.listMD = res.data.list;
            this.Denomination=0;
            //this.mediaId = opId;
            if(res.data.list.length==0){
                this.AllMoney=0
            }else{
                this.AllMoney=this.numberAllL*this.listMD[0].amount*this.listMD[0].discountPrice;
            }
        })
    },
    methods: {

        TitTab: function (even, num) {
            console.log(1232)
            this.numTit = num;
            if(even==6){
                this.txtcon='请输入手机号或者邮箱,QQ'
            }else{
                this.txtcon='请输入手机号或者邮箱';
            }
            // 优酷，爱奇艺#选择下面的充值类型的接口
            this.$http.post('/video/videoProduct', {
                operator: even
            }, {
                emulateJSON: true
            }).then(function (res) {
                console.log(res.data);
                var date = res.data.result;
                /*this.lx = res.data.list;*/
                this.operator = even;
                    // listM 面值
                    this.listM = res.data.list;
                this.payMoney = this.listM[0].amount;//金额
                this.numClass = 0;
                this.$http.post('/video/videoProduct', {
                    operator: this.list[this.numTit].value,
                    videoType: this.lx[this.numClass].value,
                }, {
                    emulateJSON: true
                }).then(function (res) {
                    console.log(res.data);
                    this.listM = res.data.list;
                    this.numME = 0;
                    if(res.data.list.length==0){
                        this.payMoney=0;
                    }else{
                        this.payMoney = this.listM[0].amount;//金额
                    }

                })
            })
            //批量
            console.log(this.Member)
            this.$http.post('/video/videoProduct', {
                operator: this.list[this.numTit].value,
                videoType: this.Member,
            }, {
                emulateJSON: true
            }).then(function (res) {
                console.log(res.data);
                this.listMD = res.data.list;
                //this.mediaId = opId;
                if(res.data.list.length==0){
                    this.AllMoney=0
                }else{
                    this.AllMoney=this.numberAllL*this.listMD[0].amount*this.listMD[0].discountPrice;
                }
            })
        },

        TitME: function (even, num) {
            // 面值选择
            console.log(even, num);
            this.numME = num;
            this.payMoney = even.amount;//金额
        },
        TitClass: function (videoType, opId, num) {
            //console.log(videoType, opId, num);
            this.numClass = num;
            console.log(this.list[this.numTit].value)
            // 充值类型选面值的接口
            this.$http.post('/video/videoProduct', {
                operator: this.list[this.numTit].value,
                videoType: videoType,
            }, {
                emulateJSON: true
            }).then(function (res) {
                console.log(res.data);
                this.listM = res.data.list;
                this.numME = 0;

                if( this.listM.length==0){
                    this.payMoney=0;
                }else{
                    this.payMoney = this.listM[0].amount;//金额
                }

            })
        },
        immediately: function () {
            // 单个充值的提交
            if (this.tx != 1 && this.tel != '' || this.QQE.test(this.tel) || this.filter.test(this.tel) || this.szReg.test(this.tel)) {
                this.BombBoxShow = true;
                this.showZ = true;
            } else if (this.tx == 1 && this.tel != '' && this.filter.test(this.tel) || this.szReg.test(this.tel)) {
                this.BombBoxShow = true;
            } else {
                alert('请正确输入')
            }
        },
        surePayType: function () {
            // 提交充值
            this.BombBoxShow = true;
        },
        cz: function () {
            // 没有用  但是不要删
            this.sty = 0;
        },
        cz2: function () {
            // 没有用  但是不要删
            this.sty = 1;
        },
        onl: function () {
            // 清空重置
            this.textAr = '';
            this.AllMoney=0;
            console.log(this.AllMoney)

        },
        immediatelyTwo: function () {
            // 批量充值的提交
            this.BombBoxShow = true;
        },
        Determine: function () { //支付确定
            var pagePayPassword=this.passWordZ;
            var that=this;
            var urlName = "videoRechargeUrl";
            $.ajax({
                url: "/Recharge/payPassword",
                data:{"pagePayPassword":pagePayPassword},
                async:false,
                type:'POST',
                dataType:"json",
                error:function(){
                    alert("系统错误，请重新登录");
                },
                success: function(data){
                    if(data) {
                    if(that.sty==0){
                            that.passWordZ = '';
                            var mobile = that.tel;
                            var userId = $("#userId").val();
                            var positionCode = that.listM[that.numME].positionCode; //面值
                            console.log(that.listM[that.numME].positionCode)
                            //this.list[this.numTit].value
                            if (userId == "") {
                                alert("用户超时从新登陆");
                                return;
                            }
                            if (positionCode == "") {
                                alert("请选择充值产品");
                                return;
                            }
                            if (mobile == "") {
                                alert("请输入手机号");
                                return;
                            }
                            $.ajax({
                                url: "/Recharge/send",
                                data: {
                                    "mobile": mobile,
                                    "userId": userId,
                                    "positionCode": positionCode,
                                    "urlName": urlName,
                                    "operator": that.list[that.numTit].value
                                },
                                async: false,
                                type: 'POST',
                                dataType: "json",
                                error: function () {
                                    alert("系统错误，请重新登录");
                                },
                                success: function (data) {
                                    var code = data.statusCode;
                                    if (code == "1000") {
                                        alert("提交成功，去订单明细查询");
                                        $("#mobile").val("");
                                        that.BombBoxShow = false;
                                        window.location.reload();
                                    } else {
                                        var msg = data.statusMsg;
                                        alert("提交失败" + code + "," + msg);
                                    }
                                }
                            });
                        }else{
                            $.ajax({
                                url: "/video/sendSumbitFlow",
                                type:'POST',
                                data:{
                                    mobileIds:that.numberStat,
                                    videoCode:that.listMD[that.Denomination].positionCode,
                                    operator:that.list[that.numTit].value,
                                    moneyAll:that.AllMoney,
                                    urlName:urlName
                                },
                                dataType:"json",
                                error:function(){
                                    alert("请重新登录");
                                },
                                success: function(data){
                                    if(data==1000){
                                        alert("提交成功");
                                        that.BombBoxShow = false;
                                        window.location.reload();
                                    }
                                    if(data==1010){
                                        alert("金额不足，请充值");
                                    }
                                }
                            });}
                    }else{
                        alert('密码错误,支付失败')
                        //$("#bd-map").text("密码错误,支付失败").css('color','red');
                    }
                }
            });

            },
        cancel: function () { //支付取消
            this.passWordZ = '';
            this.BombBoxShow = false;
        },
        classAll:function(){

            this.$http.post('/video/videoProduct', {
                operator: this.list[this.numTit].value,
                videoType: this.Member,
            }, {
                emulateJSON: true
            }).then(function (res) {
                console.log(res.data);
                this.listMD = res.data.list;
                if(res.data.list.length==0){
                    this.AllMoney=0
                }else{
                    this.AllMoney=this.numberAllL*this.listMD[this.Denomination].amount*this.listMD[this.Denomination].discountPrice;
                }
                //this.mediaId = opId;
            })
        },
        AllKa:function(){
            console.log(this.listMD[this.Denomination].positionCode);
            this.AllMoney=this.numberAllL*this.listMD[this.Denomination].amount*this.listMD[this.Denomination].discountPrice;
        },
        getMobilePhone:function(){

            var phoneNumber = $("#z_phoneNum").val();
            var number = phoneNumber.split("\n");
            var reg = /^1[34578]\d{9}$/;
            var last = number.pop();

            //判断最后一位是否为空
            if (last == "") {
                //验证手机号格式
                for (var i = 0; i < number.length; i++) {
                    if (reg.test(number[i]) == false) {
                        alert("第" + (i + 1) + "行号码有误！");
                        return ;
                    };
                };  console.log(number.length)
                this.numberAllL=number.length;
                console.log( this.Denomination)
                if(this.listMD.length==0){
                    this.AllMoney=0
                }else{
                    this.AllMoney=this.numberAllL*this.listMD[this.Denomination].amount*this.listMD[this.Denomination].discountPrice;
                }

            }
            if (last != "") {
                number.push(last);
                // alert(number);
                console.log(number)
                this.numberAllL=number.length;
                console.log( this.Denomination)
                if(this.listMD.length==0){
                    this.AllMoney=0
                }else{
                    this.AllMoney=this.numberAllL*this.listMD[this.Denomination].amount*this.listMD[this.Denomination].discountPrice;
                }
                //验证手机号格式
                for (var i = 0; i < number.length; i++) {

                    if (reg.test(number[i]) == false) {
                        alert("第" + (i + 1) + "行号码有误！");
                        return ;
                    };
                };
            }
            //提示重复手机号码
            var res = [];
            number.sort();
            for (var i = 0; i < number.length;) {
                var count = 0;
                for (var j = i; j < number.length; j++) {
                    if (number[i] == number[j]) {
                        count++;
                    }
                }
                res.push([number[i], count]);
                i += count;
            }
            this.numberStat=number
            //res 二维数维中保存了 值和值的重复数
            var repeat = [];
            for (var i = 0; i < res.length; i++) {
                if (parseInt(res[i][1]) > 1 && reg.test(res[i][0])) {
//                    alert("值:" + res[i][0] + "重复次数:" + res[i][1])
                    repeat.push("此号码:" + res[i][0] + ",次数:" + res[i][1]+"<br/>");
                    alert("此号码:" + res[i][0] + ",次数:" + res[i][1])
                }
            };
            //if(repeat != ""){
            //    pop(repeat);
            //};
        }
    }
})


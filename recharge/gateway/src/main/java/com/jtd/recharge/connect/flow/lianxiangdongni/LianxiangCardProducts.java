package com.jtd.recharge.connect.flow.lianxiangdongni;

import java.util.Hashtable;

/**
 * Created by Administrator on 2017/5/25.
 */
public class LianxiangCardProducts {
    //安徽省
    public static Hashtable<String, String> Anhui_liuliang=null;
    public static String AnhuiFlow(String productId) throws Exception {
        if (Anhui_liuliang == null) {
            Anhui_liuliang = new Hashtable<String, String>();
            //移动
            Anhui_liuliang.put("100018", "201893");//2M
            Anhui_liuliang.put("100001", "201894");//10M
            Anhui_liuliang.put("100002", "201895");//30M
            Anhui_liuliang.put("100003", "201896");//70M
            Anhui_liuliang.put("100004", "204918");//100M
            Anhui_liuliang.put("100005", "201897");//150M
            Anhui_liuliang.put("100006", "204946");//300M
            Anhui_liuliang.put("100007", "201898");//500M
            Anhui_liuliang.put("100017", "201899");//700M
            Anhui_liuliang.put("100008", "201900");//1G
            Anhui_liuliang.put("100009", "201901");//2G
            Anhui_liuliang.put("100010", "201902");//3G
            Anhui_liuliang.put("100011", "201903");//4G
            Anhui_liuliang.put("100012", "201904");//6G
            Anhui_liuliang.put("100013", "201905");//11G
            //联通
            Anhui_liuliang.put("200001", "202697");//20M
            Anhui_liuliang.put("200003", "202594");//50M
            Anhui_liuliang.put("200004", "202591");//100M
            Anhui_liuliang.put("200005", "202592");//200M
            Anhui_liuliang.put("200007", "202593");//500M
            Anhui_liuliang.put("200008", "202696");//1G
            //电信
            Anhui_liuliang.put("300002", "202471");//10M
            Anhui_liuliang.put("300003", "202472");//30M
            Anhui_liuliang.put("300005", "202473");//100M
            Anhui_liuliang.put("300006", "202474");//200M
            Anhui_liuliang.put("300008", "202827");//500M
            Anhui_liuliang.put("300009", "202475");//1G
        }
        return (String) Anhui_liuliang.get(productId);
    }
    //北京
    public static Hashtable<String, String> Beijing_liuliang=null;
    public static String BeijingFlow(String productId) throws Exception {
        if (Beijing_liuliang == null) {
            Beijing_liuliang = new Hashtable<String, String>();
            //移动
            Beijing_liuliang.put("100018", "202797");//2M
            Beijing_liuliang.put("100001", "202196");//10M
            Beijing_liuliang.put("100002", "202758");//30M
            Beijing_liuliang.put("100003", "202814");//70M
            Beijing_liuliang.put("100004", "204914");//100M
            Beijing_liuliang.put("100005", "202044");//150M
            Beijing_liuliang.put("100006", "204942");//300M
            Beijing_liuliang.put("100007", "202045");//500M
            Beijing_liuliang.put("100017", "202197");//700M
            Beijing_liuliang.put("100008", "202046");//1G
            Beijing_liuliang.put("100009", "202198");//2G
            Beijing_liuliang.put("100010", "202798");//3G
            Beijing_liuliang.put("100011", "202759");//4G
            Beijing_liuliang.put("100012", "202199");//6G
            Beijing_liuliang.put("100013", "202047");//11G
            //联通
            Beijing_liuliang.put("200011", "202140");//10M
            Beijing_liuliang.put("200001", "202570");//20M
            Beijing_liuliang.put("200003", "202574");//50M
            Beijing_liuliang.put("200004", "202564");//100M
            Beijing_liuliang.put("200005", "202568");//200M
            Beijing_liuliang.put("200007", "202572");//500M
            Beijing_liuliang.put("200008", "202566");//1024M
            //电信
            Beijing_liuliang.put("300002", "202869");//10M
            Beijing_liuliang.put("300003", "202539");//30M
            Beijing_liuliang.put("300005", "202870");//100M
            Beijing_liuliang.put("300006", "202540");//200M
            Beijing_liuliang.put("300008", "202871");//500M
            Beijing_liuliang.put("300009", "202541");//1024M
        }
        return (String) Beijing_liuliang.get(productId);
    }
    //福建
    public static Hashtable<String, String> Fujian_liuliang=null;
    public static String FujianFlow(String productId) throws Exception {
        if (Fujian_liuliang == null) {
            Fujian_liuliang = new Hashtable<String, String>();
            //移动
            Fujian_liuliang.put("100018", "201906");//2M
            Fujian_liuliang.put("100001", "201907");//10M
            Fujian_liuliang.put("100002", "201908");//30M
            Fujian_liuliang.put("100003", "201909");//70M
            Fujian_liuliang.put("100004", "204920");//100M
            Fujian_liuliang.put("100005", "201910");//150M
            Fujian_liuliang.put("100006", "204948");//300M
            Fujian_liuliang.put("100007", "201911");//500M
            Fujian_liuliang.put("100017", "201912");//700M
            Fujian_liuliang.put("100008", "201913");//1G
            Fujian_liuliang.put("100009", "201914");//2G
            Fujian_liuliang.put("100010", "201915");//3G
            Fujian_liuliang.put("100011", "201916");//4G
            Fujian_liuliang.put("100012", "201917");//6G
            Fujian_liuliang.put("100013", "201918");//11G
            //联通
            Fujian_liuliang.put("200001", "202606");//20M
            Fujian_liuliang.put("200003", "202607");//50M
            Fujian_liuliang.put("200004", "202605");//100M
            Fujian_liuliang.put("200005", "202608");//200M
            Fujian_liuliang.put("200007", "202609");//500M
            Fujian_liuliang.put("200008", "202655");//1024M
            //电信
            Fujian_liuliang.put("300002", "202476");//10M
            Fujian_liuliang.put("300003", "202828");//30M
            Fujian_liuliang.put("300005", "202477");//100M
            Fujian_liuliang.put("300006", "202829");//200M
            Fujian_liuliang.put("300008", "202478");//500M
            Fujian_liuliang.put("300009", "202479");//1024M
        }
        return (String) Fujian_liuliang.get(productId);
    }
    //甘肃
    public static Hashtable<String, String> Gansu_liuliang=null;
    public static String GansuFlow(String productId) throws Exception {
        if (Gansu_liuliang == null) {
            Gansu_liuliang = new Hashtable<String, String>();
            //移动
            Gansu_liuliang.put("100018", "202177");//2M
            Gansu_liuliang.put("100001", "202019");//10M
            Gansu_liuliang.put("100002", "202178");//30M
            Gansu_liuliang.put("100003", "202020");//70M
            Gansu_liuliang.put("100004", "204937");//100M
            Gansu_liuliang.put("100005", "202021");//150M
            Gansu_liuliang.put("100006", "204967");//300M
            Gansu_liuliang.put("100007", "202179");//500M
            Gansu_liuliang.put("100017", "202022");//700M
            Gansu_liuliang.put("100008", "202180");//1G
            Gansu_liuliang.put("100009", "202023");//2G
            Gansu_liuliang.put("100010", "202181");//3G
            Gansu_liuliang.put("100011", "202024");//4G
            Gansu_liuliang.put("100012", "202025");//6G
            Gansu_liuliang.put("100013", "202182");//11G
            //联通
            Gansu_liuliang.put("200001", "202725");//20M
            Gansu_liuliang.put("200003", "202726");//50M
            Gansu_liuliang.put("200004", "202724");//100M
            Gansu_liuliang.put("200005", "202653");//200M
            Gansu_liuliang.put("200007", "202654");//500M
            Gansu_liuliang.put("200008", "202652");//1024M
            //电信
            Gansu_liuliang.put("300002", "202518");//10M
            Gansu_liuliang.put("300003", "202519");//30M
            Gansu_liuliang.put("300005", "202863");//100M
            Gansu_liuliang.put("300006", "202520");//200M
            Gansu_liuliang.put("300008", "202864");//500M
            Gansu_liuliang.put("300009", "202521");//1024M
        }
        return (String) Gansu_liuliang.get(productId);
    }
    //广东
    public static Hashtable<String, String> Guangdong_liuliang=null;
    public static String GuangdongFlow(String productId) throws Exception {
        if (Guangdong_liuliang == null) {
            Guangdong_liuliang = new Hashtable<String, String>();
            //移动
            Guangdong_liuliang.put("100018", "202113");//2M
            Guangdong_liuliang.put("100001", "202120");//10M
            Guangdong_liuliang.put("100002", "202121");//30M
            Guangdong_liuliang.put("100003", "202122");//70M
            Guangdong_liuliang.put("100004", "204933");//100M
            Guangdong_liuliang.put("100005", "202123");//150M
            Guangdong_liuliang.put("100006", "204963");//300M
            Guangdong_liuliang.put("100007", "202124");//500M
            Guangdong_liuliang.put("100017", "201974");//700M
            Guangdong_liuliang.put("100008", "202125");//1G
            Guangdong_liuliang.put("100009", "202126");//2G
            Guangdong_liuliang.put("100010", "202127");//3G
            Guangdong_liuliang.put("100011", "202128");//4G
            Guangdong_liuliang.put("100012", "202129");//6G
            Guangdong_liuliang.put("100013", "202130");//11G
            //联通
            Guangdong_liuliang.put("200001", "202704");//20M
            Guangdong_liuliang.put("200003", "202705");//50M
            Guangdong_liuliang.put("200004", "202703");//100M
            Guangdong_liuliang.put("200005", "202603");//200M
            Guangdong_liuliang.put("200007", "202604");//500M
            Guangdong_liuliang.put("200008", "202602");//1024M
            //电信
            Guangdong_liuliang.put("300002", "202495");//10M
            Guangdong_liuliang.put("300003", "202843");//30M
            Guangdong_liuliang.put("300005", "202496");//100M
            Guangdong_liuliang.put("300006", "202819");//200M
            Guangdong_liuliang.put("300008", "202844");//500M
            Guangdong_liuliang.put("300009", "202497");//1024M
        }
        return (String) Guangdong_liuliang.get(productId);
    }
    //广西
    public static Hashtable<String, String> Guangxi_liuliang=null;
    public static String GuangxiFlow(String productId) throws Exception {
        if (Guangxi_liuliang == null) {
            Guangxi_liuliang = new Hashtable<String, String>();
            //移动
            Guangxi_liuliang.put("100018", "202745");//2M
            Guangxi_liuliang.put("100001", "201978");//10M
            Guangxi_liuliang.put("100002", "202119");//30M
            Guangxi_liuliang.put("100003", "201979");//70M
            Guangxi_liuliang.put("100004", "204921");//100M
            Guangxi_liuliang.put("100005", "202137");//150M
            Guangxi_liuliang.put("100006", "204949");//300M
            Guangxi_liuliang.put("100007", "201980");//500M
            Guangxi_liuliang.put("100017", "202746");//700M
            Guangxi_liuliang.put("100008", "202138");//1G
            Guangxi_liuliang.put("100009", "201981");//2G
            Guangxi_liuliang.put("100010", "202778");//3G
            Guangxi_liuliang.put("100011", "201982");//4G
            Guangxi_liuliang.put("100012", "201983");//6G
            Guangxi_liuliang.put("100013", "202139");//11G
            //联通
            Guangxi_liuliang.put("200001", "202613");//20M
            Guangxi_liuliang.put("200003", "202615");//50M
            Guangxi_liuliang.put("200004", "202610");//100M
            Guangxi_liuliang.put("200005", "202612");//200M
            Guangxi_liuliang.put("200007", "202614");//500M
            Guangxi_liuliang.put("200008", "202611");//1024M
            //电信
            Guangxi_liuliang.put("300002", "202845");//10M
            Guangxi_liuliang.put("300003", "202498");//30M
            Guangxi_liuliang.put("300005", "202846");//100M
            Guangxi_liuliang.put("300006", "202499");//200M
            Guangxi_liuliang.put("300008", "202500");//500M
            Guangxi_liuliang.put("300009", "202847");//1024M
        }
        return (String) Guangxi_liuliang.get(productId);
    }
    //贵州
    public static Hashtable<String, String> Guizhou_liuliang=null;
    public static String GuizhouFlow(String productId) throws Exception {
        if (Guizhou_liuliang == null) {
            Guizhou_liuliang = new Hashtable<String, String>();
            //移动
            Guizhou_liuliang.put("100018", "202750");//2M
            Guizhou_liuliang.put("100001", "201993");//10M
            Guizhou_liuliang.put("100002", "202158");//30M
            Guizhou_liuliang.put("100003", "201994");//70M
            Guizhou_liuliang.put("100004", "204926");//100M
            Guizhou_liuliang.put("100005", "202159");//150M
            Guizhou_liuliang.put("100006", "204956");//300M
            Guizhou_liuliang.put("100007", "201995");//500M
            Guizhou_liuliang.put("100017", "202160");//700M
            Guizhou_liuliang.put("100008", "202751");//1G
            Guizhou_liuliang.put("100009", "202793");//2G
            Guizhou_liuliang.put("100010", "202161");//3G
            Guizhou_liuliang.put("100011", "201996");//4G
            Guizhou_liuliang.put("100012", "202162");//6G
            Guizhou_liuliang.put("100013", "201997");//11G
            //联通
            Guizhou_liuliang.put("200001", "202661");//20M
            Guizhou_liuliang.put("200003", "202662");//50M
            Guizhou_liuliang.put("200004", "202659");//100M
            Guizhou_liuliang.put("200005", "202730");//200M
            Guizhou_liuliang.put("200007", "202731");//500M
            Guizhou_liuliang.put("200008", "202660");//1024M
            //电信
            Guizhou_liuliang.put("300002", "202821");//10M
            Guizhou_liuliang.put("300003", "202507");//30M
            Guizhou_liuliang.put("300005", "202853");//100M
            Guizhou_liuliang.put("300006", "202508");//200M
            Guizhou_liuliang.put("300008", "202854");//500M
            Guizhou_liuliang.put("300009", "202509");//1024M
        }
        return (String) Guizhou_liuliang.get(productId);
    }
    //海南
    public static Hashtable<String, String> Hainan_liuliang=null;
    public static String HainanFlow(String productId) throws Exception {
        if (Hainan_liuliang == null) {
            Hainan_liuliang = new Hashtable<String, String>();
            //移动
            Hainan_liuliang.put("100018", "201984");//2M
            Hainan_liuliang.put("100001", "202147");//10M
            Hainan_liuliang.put("100002", "202747");//30M
            Hainan_liuliang.put("100003", "202810");//70M
            Hainan_liuliang.put("100004", "204924");//100M
            Hainan_liuliang.put("100005", "201985");//150M
            Hainan_liuliang.put("100006", "204954");//300M
            Hainan_liuliang.put("100007", "201986");//500M
            Hainan_liuliang.put("100017", "202148");//700M
            Hainan_liuliang.put("100008", "201987");//1G
            Hainan_liuliang.put("100009", "202150");//2G
            Hainan_liuliang.put("100010", "202790");//3G
            Hainan_liuliang.put("100011", "202748");//4G
            Hainan_liuliang.put("100012", "202151");//6G
            Hainan_liuliang.put("100013", "201988");//11G
            //联通
            Hainan_liuliang.put("200001", "202720");//20M
            Hainan_liuliang.put("200003", "202642");//50M
            Hainan_liuliang.put("200004", "202639");//100M
            Hainan_liuliang.put("200005", "202640");//200M
            Hainan_liuliang.put("200007", "202641");//500M
            Hainan_liuliang.put("200008", "202719");//1024M
            //电信
            Hainan_liuliang.put("300002", "202501");//10M
            Hainan_liuliang.put("300003", "202848");//30M
            Hainan_liuliang.put("300005", "202820");//100M
            Hainan_liuliang.put("300006", "202502");//200M
            Hainan_liuliang.put("300008", "202849");//500M
            Hainan_liuliang.put("300009", "202503");//1024M
        }
        return (String) Hainan_liuliang.get(productId);
    }
    //河北
    public static Hashtable<String, String> Hebei_liuliang=null;
    public static String HebeiFlow(String productId) throws Exception {
        if (Hebei_liuliang == null) {
            Hebei_liuliang = new Hashtable<String, String>();
            //移动
            Hebei_liuliang.put("100018", "202065");//2M
            Hebei_liuliang.put("100001", "202205");//10M
            Hebei_liuliang.put("100002", "202066");//30M
            Hebei_liuliang.put("100003", "202206");//70M
            Hebei_liuliang.put("100004", "202786");//100M
            Hebei_liuliang.put("100005", "202067");//150M
            Hebei_liuliang.put("100006", "204952");//300M
            Hebei_liuliang.put("100007", "202207");//500M
            Hebei_liuliang.put("100017", "202068");//700M
            Hebei_liuliang.put("100008", "202069");//1G
            Hebei_liuliang.put("100009", "202070");//2G
            Hebei_liuliang.put("100010", "202765");//3G
            Hebei_liuliang.put("100011", "202071");//4G
            Hebei_liuliang.put("100012", "202208");//6G
            Hebei_liuliang.put("100013", "202072");//11G
            //联通
            Hebei_liuliang.put("200001", "202630");//20M
            Hebei_liuliang.put("200003", "202631");//50M
            Hebei_liuliang.put("200004", "202628");//100M
            Hebei_liuliang.put("200005", "202712");//200M
            Hebei_liuliang.put("200007", "202713");//500M
            Hebei_liuliang.put("200008", "202629");//1024M
            //电信
            Hebei_liuliang.put("300002", "202435");//10M
            Hebei_liuliang.put("300003", "202293");//30M
            Hebei_liuliang.put("300005", "202436");//100M
            Hebei_liuliang.put("300006", "202437");//200M
            Hebei_liuliang.put("300008", "202294");//500M
            Hebei_liuliang.put("300009", "202438");//1024M
        }
        return (String) Hebei_liuliang.get(productId);
    }
    //河南
    public static Hashtable<String, String> Henan_liuliang=null;
    public static String HenanFlow(String productId) throws Exception {
        if (Henan_liuliang == null) {
            Henan_liuliang = new Hashtable<String, String>();
            //移动
            Henan_liuliang.put("100018", "201938");//2M
            Henan_liuliang.put("100001", "201939");//10M
            Henan_liuliang.put("100002", "201940");//30M
            Henan_liuliang.put("100003", "201941");//70M
            Henan_liuliang.put("100004", "204935");//100M
            Henan_liuliang.put("100005", "201942");//150M
            Henan_liuliang.put("100006", "204965");//300M
            Henan_liuliang.put("100007", "201943");//500M
            Henan_liuliang.put("100017", "201944");//700M
            Henan_liuliang.put("100008", "201945");//1G
            Henan_liuliang.put("100009", "201946");//2G
            Henan_liuliang.put("100010", "201947");//3G
            Henan_liuliang.put("100011", "201948");//4G
            Henan_liuliang.put("100012", "201949");//6G
            Henan_liuliang.put("100013", "201950");//11G
            //联通
            Henan_liuliang.put("200001", "202715");//20M
            Henan_liuliang.put("200003", "202716");//50M
            Henan_liuliang.put("200004", "202714");//100M
            Henan_liuliang.put("200005", "202633");//200M
            Henan_liuliang.put("200007", "202634");//500M
            Henan_liuliang.put("200008", "202632");//1024M
            //电信
            Henan_liuliang.put("300002", "202835");//10M
            Henan_liuliang.put("300003", "202817");//30M
            Henan_liuliang.put("300005", "202836");//100M
            Henan_liuliang.put("300006", "202487");//200M
            Henan_liuliang.put("300008", "202488");//500M
            Henan_liuliang.put("300009", "202837");//1024M
        }
        return (String) Henan_liuliang.get(productId);
    }
    //黑龙江
    public static Hashtable<String, String> Heilongjiang_liuliang=null;
    public static String HeilongjiangFlow(String productId) throws Exception {
        if (Heilongjiang_liuliang == null) {
            Heilongjiang_liuliang = new Hashtable<String, String>();
            //移动
            Heilongjiang_liuliang.put("100018", "202097");//2M
            Heilongjiang_liuliang.put("100001", "202773");//10M
            Heilongjiang_liuliang.put("100002", "202226");//30M
            Heilongjiang_liuliang.put("100003", "202807");//70M
            Heilongjiang_liuliang.put("100004", "204929");//100M
            Heilongjiang_liuliang.put("100005", "202227");//150M
            Heilongjiang_liuliang.put("100006", "204959");//300M
            Heilongjiang_liuliang.put("100007", "202098");//500M
            Heilongjiang_liuliang.put("100017", "202099");//700M
            Heilongjiang_liuliang.put("100008", "202228");//1G
            Heilongjiang_liuliang.put("100009", "202100");//2G
            Heilongjiang_liuliang.put("100010", "202774");//3G
            Heilongjiang_liuliang.put("100011", "202229");//4G
            Heilongjiang_liuliang.put("100012", "202808");//6G
            Heilongjiang_liuliang.put("100013", "202230");//11G
            //联通
            Heilongjiang_liuliang.put("200001", "202681");//20M
            Heilongjiang_liuliang.put("200003", "202682");//50M
            Heilongjiang_liuliang.put("200004", "202679");//100M
            Heilongjiang_liuliang.put("200005", "202680");//200M
            Heilongjiang_liuliang.put("200007", "202741");//500M
            Heilongjiang_liuliang.put("200008", "202740");//1024M
            //电信
            Heilongjiang_liuliang.put("300002", "202453");//10M
            Heilongjiang_liuliang.put("300003", "202454");//30M
            Heilongjiang_liuliang.put("300005", "202455");//100M
            Heilongjiang_liuliang.put("300006", "202456");//200M
            Heilongjiang_liuliang.put("300008", "202457");//500M
            Heilongjiang_liuliang.put("300009", "202458");//1024M
        }
        return (String) Heilongjiang_liuliang.get(productId);
    }
    //湖北
    public static Hashtable<String, String> Hubei_liuliang=null;
    public static String HubeiFlow(String productId) throws Exception {
        if (Hubei_liuliang == null) {
            Hubei_liuliang = new Hashtable<String, String>();
            //移动
            Hubei_liuliang.put("100018", "201951");//2M
            Hubei_liuliang.put("100001", "201952");//10M
            Hubei_liuliang.put("100002", "201953");//30M
            Hubei_liuliang.put("100003", "201954");//70M
            Hubei_liuliang.put("100004", "204936");//100M
            Hubei_liuliang.put("100005", "201955");//150M
            Hubei_liuliang.put("100006", "204966");//300M
            Hubei_liuliang.put("100007", "201956");//500M
            Hubei_liuliang.put("100017", "201957");//700M
            Hubei_liuliang.put("100008", "201958");//1G
            Hubei_liuliang.put("100009", "201959");//2G
            Hubei_liuliang.put("100010", "201960");//3G
            Hubei_liuliang.put("100011", "201961");//4G
            Hubei_liuliang.put("100012", "201962");//6G
            Hubei_liuliang.put("100013", "202108");//11G
            //联通
            Hubei_liuliang.put("200001", "202645");//20M
            Hubei_liuliang.put("200003", "202647");//50M
            Hubei_liuliang.put("200004", "202721");//100M
            Hubei_liuliang.put("200005", "202644");//200M
            Hubei_liuliang.put("200007", "202646");//500M
            Hubei_liuliang.put("200008", "202643");//1024M
            //电信
            Hubei_liuliang.put("300002", "202526");//10M
            Hubei_liuliang.put("300003", "202838");//30M
            Hubei_liuliang.put("300005", "202490");//100M
            Hubei_liuliang.put("300006", "202491");//200M
            Hubei_liuliang.put("300008", "202839");//500M
            Hubei_liuliang.put("300009", "202818");//1024M
        }
        return (String) Hubei_liuliang.get(productId);
    }
    //湖南
    public static Hashtable<String, String> Hunan_liuliang=null;
    public static String HunanFlow(String productId) throws Exception {
        if (Hunan_liuliang == null) {
            Hunan_liuliang = new Hashtable<String, String>();
            //移动
            Hunan_liuliang.put("100018", "201963");//2M
            Hunan_liuliang.put("100001", "202109");//10M
            Hunan_liuliang.put("100002", "201964");//30M
            Hunan_liuliang.put("100003", "201965");//70M
            Hunan_liuliang.put("100004", "204925");//100M
            Hunan_liuliang.put("100005", "202110");//150M
            Hunan_liuliang.put("100006", "204955");//300M
            Hunan_liuliang.put("100007", "201966");//500M
            Hunan_liuliang.put("100017", "202776");//700M
            Hunan_liuliang.put("100008", "201967");//1G
            Hunan_liuliang.put("100009", "201968");//2G
            Hunan_liuliang.put("100010", "202111");//3G
            Hunan_liuliang.put("100011", "201969");//4G
            Hunan_liuliang.put("100012", "202112");//6G
            Hunan_liuliang.put("100013", "201970");//11G
            //联通
            Hunan_liuliang.put("200001", "202650");//20M
            Hunan_liuliang.put("200003", "202651");//50M
            Hunan_liuliang.put("200004", "202648");//100M
            Hunan_liuliang.put("200005", "202649");//200M
            Hunan_liuliang.put("200007", "202723");//500M
            Hunan_liuliang.put("200008", "202722");//1024M
            //电信
            Hunan_liuliang.put("300002", "202840");//10M
            Hunan_liuliang.put("300003", "202492");//30M
            Hunan_liuliang.put("300005", "202841");//100M
            Hunan_liuliang.put("300006", "202493");//200M
            Hunan_liuliang.put("300008", "202494");//500M
            Hunan_liuliang.put("300009", "202842");//1024M
        }
        return (String) Hunan_liuliang.get(productId);
    }
    //吉林
    public static Hashtable<String, String> Jilin_liuliang=null;
    public static String JilinFlow(String productId) throws Exception {
        if (Jilin_liuliang == null) {
            Jilin_liuliang = new Hashtable<String, String>();
            //移动
            Jilin_liuliang.put("100018", "202220");//2M
            Jilin_liuliang.put("100001", "202092");//10M
            Jilin_liuliang.put("100002", "202093");//30M
            Jilin_liuliang.put("100003", "202221");//70M
            Jilin_liuliang.put("100004", "204915");//100M
            Jilin_liuliang.put("100005", "202094");//150M
            Jilin_liuliang.put("100006", "204943");//300M
            Jilin_liuliang.put("100007", "202222");//500M
            Jilin_liuliang.put("100017", "202772");//700M
            Jilin_liuliang.put("100008", "202806");//1G
            Jilin_liuliang.put("100009", "202223");//2G
            Jilin_liuliang.put("100010", "202095");//3G
            Jilin_liuliang.put("100011", "202224");//4G
            Jilin_liuliang.put("100012", "202096");//6G
            Jilin_liuliang.put("100013", "202225");//11G
            //联通
            Jilin_liuliang.put("200001", "202578");//20M
            Jilin_liuliang.put("200003", "202579");//50M
            Jilin_liuliang.put("200004", "202575");//100M
            Jilin_liuliang.put("200005", "202577");//200M
            Jilin_liuliang.put("200007", "202688");//500M
            Jilin_liuliang.put("200008", "202576");//1024M
            //电信
            Jilin_liuliang.put("300002", "202449");//10M
            Jilin_liuliang.put("300003", "202450");//30M
            Jilin_liuliang.put("300005", "202825");//100M
            Jilin_liuliang.put("300006", "202451");//200M
            Jilin_liuliang.put("300008", "202826");//500M
            Jilin_liuliang.put("300009", "202452");//1024M
        }
        return (String) Jilin_liuliang.get(productId);
    }
    //江苏
    public static Hashtable<String, String> Jiangsu_liuliang=null;
    public static String JiangsuFlow(String productId) throws Exception {
        if (Jiangsu_liuliang == null) {
            Jiangsu_liuliang = new Hashtable<String, String>();
            //移动
            Jiangsu_liuliang.put("100018", "202101");//2M
            Jiangsu_liuliang.put("100001", "202102");//10M
            Jiangsu_liuliang.put("100002", "202231");//30M
            Jiangsu_liuliang.put("100003", "202103");//70M
            Jiangsu_liuliang.put("100004", "204923");//100M
            Jiangsu_liuliang.put("100005", "202232");//150M
            Jiangsu_liuliang.put("100006", "204951");//300M
            Jiangsu_liuliang.put("100007", "202775");//500M
            Jiangsu_liuliang.put("100017", "202809");//700M
            Jiangsu_liuliang.put("100008", "202233");//1G
            Jiangsu_liuliang.put("100009", "202104");//2G
            Jiangsu_liuliang.put("100010", "202816");//3G
            Jiangsu_liuliang.put("100011", "202105");//4G
            Jiangsu_liuliang.put("100012", "202106");//6G
            Jiangsu_liuliang.put("100013", "202234");//11G
            //联通
            Jiangsu_liuliang.put("200001", "202623");//20M
            Jiangsu_liuliang.put("200003", "202624");//50M
            Jiangsu_liuliang.put("200004", "202621");//100M
            Jiangsu_liuliang.put("200005", "202622");//200M
            Jiangsu_liuliang.put("200007", "202708");//500M
            Jiangsu_liuliang.put("200008", "202707");//1024M
            //电信
            Jiangsu_liuliang.put("300002", "202459");//10M
            Jiangsu_liuliang.put("300003", "202460");//30M
            Jiangsu_liuliang.put("300005", "202461");//100M
            Jiangsu_liuliang.put("300006", "202462");//200M
            Jiangsu_liuliang.put("300008", "202463");//500M
            Jiangsu_liuliang.put("300009", "202464");//1024M
        }
        return (String) Jiangsu_liuliang.get(productId);
    }
    //江西
    public static Hashtable<String, String> Jiangxi_liuliang=null;
    public static String JiangxiFlow(String productId) throws Exception {
        if (Jiangxi_liuliang == null) {
            Jiangxi_liuliang = new Hashtable<String, String>();
            //移动
            Jiangxi_liuliang.put("100018", "201919");//2M
            Jiangxi_liuliang.put("100001", "201920");//10M
            Jiangxi_liuliang.put("100002", "201921");//30M
            Jiangxi_liuliang.put("100003", "201922");//70M
            Jiangxi_liuliang.put("100004", "204934");//100M
            Jiangxi_liuliang.put("100005", "201923");//150M
            Jiangxi_liuliang.put("100006", "204964");//300M
            Jiangxi_liuliang.put("100007", "201924");//500M
            Jiangxi_liuliang.put("100017", "201925");//700M
            Jiangxi_liuliang.put("100008", "201926");//1G
            Jiangxi_liuliang.put("100009", "201927");//2G
            Jiangxi_liuliang.put("100010", "202063");//3G
            Jiangxi_liuliang.put("100011", "201928");//4G
            Jiangxi_liuliang.put("100012", "201929");//6G
            Jiangxi_liuliang.put("100013", "201930");//11G
            //联通
            Jiangxi_liuliang.put("200001", "202710");//20M
            Jiangxi_liuliang.put("200003", "202711");//50M
            Jiangxi_liuliang.put("200004", "202709");//100M
            Jiangxi_liuliang.put("200005", "202626");//200M
            Jiangxi_liuliang.put("200007", "202627");//500M
            Jiangxi_liuliang.put("200008", "202625");//1024M
            //电信
            Jiangxi_liuliang.put("300002", "202830");//10M
            Jiangxi_liuliang.put("300003", "202480");//30M
            Jiangxi_liuliang.put("300005", "202831");//100M
            Jiangxi_liuliang.put("300006", "202481");//200M
            Jiangxi_liuliang.put("300008", "202482");//500M
            Jiangxi_liuliang.put("300009", "202832");//1024M
        }
        return (String) Jiangxi_liuliang.get(productId);
    }
    //辽宁
    public static Hashtable<String, String> Liaoning_liuliang=null;
    public static String LiaoningFlow(String productId) throws Exception {
        if (Liaoning_liuliang == null) {
            Liaoning_liuliang = new Hashtable<String, String>();
            //移动
            Liaoning_liuliang.put("100018", "202804");//2M
            Liaoning_liuliang.put("100001", "202215");//10M
            Liaoning_liuliang.put("100002", "202770");//30M
            Liaoning_liuliang.put("100003", "202089");//70M
            Liaoning_liuliang.put("100004", "204927");//100M
            Liaoning_liuliang.put("100005", "202216");//150M
            Liaoning_liuliang.put("100006", "204957");//300M
            Liaoning_liuliang.put("100007", "202090");//500M
            Liaoning_liuliang.put("100017", "202217");//700M
            Liaoning_liuliang.put("100008", "202091");//1G
            Liaoning_liuliang.put("100009", "202787");//2G
            Liaoning_liuliang.put("100010", "202218");//3G
            Liaoning_liuliang.put("100011", "202771");//4G
            Liaoning_liuliang.put("100012", "202219");//6G
            Liaoning_liuliang.put("100013", "202805");//11G
            //联通
            Liaoning_liuliang.put("200001", "202666");//20M
            Liaoning_liuliang.put("200003", "202668");//50M
            Liaoning_liuliang.put("200004", "202663");//100M
            Liaoning_liuliang.put("200005", "202665");//200M
            Liaoning_liuliang.put("200007", "202667");//500M
            Liaoning_liuliang.put("200008", "202664");//1024M
            //电信
            Liaoning_liuliang.put("300002", "202446");//10M
            Liaoning_liuliang.put("300003", "202300");//30M
            Liaoning_liuliang.put("300005", "202447");//100M
            Liaoning_liuliang.put("300006", "202301");//200M
            Liaoning_liuliang.put("300008", "202448");//500M
            Liaoning_liuliang.put("300009", "202302");//1024M
        }
        return (String) Liaoning_liuliang.get(productId);
    }
    //内蒙古
    public static Hashtable<String, String> Neimenggu_liuliang=null;
    public static String NeimengguFlow(String productId) throws Exception {
        if (Neimenggu_liuliang == null) {
            Neimenggu_liuliang = new Hashtable<String, String>();
            //移动
            Neimenggu_liuliang.put("100018", "202079");//2M
            Neimenggu_liuliang.put("100001", "202080");//10M
            Neimenggu_liuliang.put("100002", "202081");//30M
            Neimenggu_liuliang.put("100003", "202768");//70M
            Neimenggu_liuliang.put("100004", "204930");//100M
            Neimenggu_liuliang.put("100005", "202082");//150M
            Neimenggu_liuliang.put("100006", "204960");//300M
            Neimenggu_liuliang.put("100007", "202083");//500M
            Neimenggu_liuliang.put("100017", "202084");//700M
            Neimenggu_liuliang.put("100008", "202085");//1G
            Neimenggu_liuliang.put("100009", "202769");//2G
            Neimenggu_liuliang.put("100010", "202214");//3G
            Neimenggu_liuliang.put("100011", "202086");//4G
            Neimenggu_liuliang.put("100012", "202087");//6G
            Neimenggu_liuliang.put("100013", "202088");//11G
            //联通
            Neimenggu_liuliang.put("200001", "202686");//20M
            Neimenggu_liuliang.put("200003", "202687");//50M
            Neimenggu_liuliang.put("200004", "202685");//100M
            Neimenggu_liuliang.put("200005", "202561");//200M
            Neimenggu_liuliang.put("200007", "202562");//500M
            Neimenggu_liuliang.put("200008", "202560");//1024M
            //电信
            Neimenggu_liuliang.put("300002", "202442");//10M
            Neimenggu_liuliang.put("300003", "202298");//30M
            Neimenggu_liuliang.put("300005", "202443");//100M
            Neimenggu_liuliang.put("300006", "202444");//200M
            Neimenggu_liuliang.put("300008", "202299");//500M
            Neimenggu_liuliang.put("300009", "202445");//1024M
        }
        return (String) Neimenggu_liuliang.get(productId);
    }
    //宁夏
    public static Hashtable<String, String> Ningxia_liuliang=null;
    public static String NingxiaFlow(String productId) throws Exception {
        if (Ningxia_liuliang == null) {
            Ningxia_liuliang = new Hashtable<String, String>();
            //移动
            Ningxia_liuliang.put("100018", "202026");//2M
            Ningxia_liuliang.put("100001", "202183");//10M
            Ningxia_liuliang.put("100002", "202027");//30M
            Ningxia_liuliang.put("100003", "202028");//70M
            Ningxia_liuliang.put("100004", "204931");//100M
            Ningxia_liuliang.put("100005", "202184");//150M
            Ningxia_liuliang.put("100006", "204961");//300M
            Ningxia_liuliang.put("100007", "202029");//500M
            Ningxia_liuliang.put("100017", "202185");//700M
            Ningxia_liuliang.put("100008", "202030");//1G
            Ningxia_liuliang.put("100009", "202031");//2G
            Ningxia_liuliang.put("100010", "202186");//3G
            Ningxia_liuliang.put("100011", "202754");//4G
            Ningxia_liuliang.put("100012", "202811");//6G
            Ningxia_liuliang.put("100013", "202032");//11G
            //联通
            Ningxia_liuliang.put("200001", "202589");//20M
            Ningxia_liuliang.put("200003", "202695");//50M
            Ningxia_liuliang.put("200004", "202693");//100M
            Ningxia_liuliang.put("200005", "202694");//200M
            Ningxia_liuliang.put("200007", "202590");//500M
            Ningxia_liuliang.put("200008", "202588");//1024M
            //电信
            Ningxia_liuliang.put("300002", "202824");//10M
            Ningxia_liuliang.put("300003", "202865");//30M
            Ningxia_liuliang.put("300005", "202522");//100M
            Ningxia_liuliang.put("300006", "202527");//200M
            Ningxia_liuliang.put("300008", "202528");//500M
            Ningxia_liuliang.put("300009", "202529");//1024M
        }
        return (String) Ningxia_liuliang.get(productId);
    }
    //青海
    public static Hashtable<String, String> Qinghai_liuliang=null;
    public static String QinghaiFlow(String productId) throws Exception {
        if (Qinghai_liuliang == null) {
            Qinghai_liuliang = new Hashtable<String, String>();
            //移动
            Qinghai_liuliang.put("100018", "202187");//2M
            Qinghai_liuliang.put("100001", "202033");//10M
            Qinghai_liuliang.put("100002", "202034");//30M
            Qinghai_liuliang.put("100003", "202188");//70M
            Qinghai_liuliang.put("100004", "204940");//100M
            Qinghai_liuliang.put("100005", "202755");//150M
            Qinghai_liuliang.put("100006", "204970");//300M
            Qinghai_liuliang.put("100007", "202189");//500M
            Qinghai_liuliang.put("100017", "202035");//700M
            Qinghai_liuliang.put("100008", "202036");//1G
            Qinghai_liuliang.put("100009", "202190");//2G
            Qinghai_liuliang.put("100010", "202037");//3G
            Qinghai_liuliang.put("100011", "202812");//4G
            Qinghai_liuliang.put("100012", "202038");//6G
            Qinghai_liuliang.put("100013", "202756");//11G
            //联通
            Qinghai_liuliang.put("200001", "202677");//20M
            Qinghai_liuliang.put("200003", "202739");//50M
            Qinghai_liuliang.put("200004", "202737");//100M
            Qinghai_liuliang.put("200005", "202738");//200M
            Qinghai_liuliang.put("200007", "202678");//500M
            Qinghai_liuliang.put("200008", "202676");//1024M
            //电信
            Qinghai_liuliang.put("300002", "202530");//10M
            Qinghai_liuliang.put("300003", "202531");//30M
            Qinghai_liuliang.put("300005", "202532");//100M
            Qinghai_liuliang.put("300006", "202866");//200M
            Qinghai_liuliang.put("300008", "202533");//500M
            Qinghai_liuliang.put("300009", "202534");//1024M
        }
        return (String) Qinghai_liuliang.get(productId);
    }
    //山东
    public static Hashtable<String, String> Shandong_liuliang=null;
    public static String ShandongFlow(String productId) throws Exception {
        if (Shandong_liuliang == null) {
            Shandong_liuliang = new Hashtable<String, String>();
            //移动
            Shandong_liuliang.put("100018", "202064");//2M
            Shandong_liuliang.put("100001", "201931");//10M
            Shandong_liuliang.put("100002", "201932");//30M
            Shandong_liuliang.put("100003", "202742");//70M
            Shandong_liuliang.put("100004", "204932");//100M
            Shandong_liuliang.put("100005", "201933");//150M
            Shandong_liuliang.put("100006", "204962");//300M
            Shandong_liuliang.put("100007", "202788");//500M
            Shandong_liuliang.put("100017", "201934");//700M
            Shandong_liuliang.put("100008", "201935");//1G
            Shandong_liuliang.put("100009", "202743");//2G
            Shandong_liuliang.put("100010", "201936");//3G
            Shandong_liuliang.put("100011", "202789");//4G
            Shandong_liuliang.put("100012", "201937");//6G
            Shandong_liuliang.put("100013", "202744");//11G
            //联通
            Shandong_liuliang.put("200001", "202596");//20M
            Shandong_liuliang.put("200003", "202700");//50M
            Shandong_liuliang.put("200004", "202698");//100M
            Shandong_liuliang.put("200005", "202699");//200M
            Shandong_liuliang.put("200007", "202597");//500M
            Shandong_liuliang.put("200008", "202595");//1024M
            //电信
            Shandong_liuliang.put("300002", "202483");//10M
            Shandong_liuliang.put("300003", "202833");//30M
            Shandong_liuliang.put("300005", "202484");//100M
            Shandong_liuliang.put("300006", "202834");//200M
            Shandong_liuliang.put("300008", "202485");//500M
            Shandong_liuliang.put("300009", "202486");//1024M
        }
        return (String) Shandong_liuliang.get(productId);
    }
    //山西
    public static Hashtable<String, String> Shanxi_liuliang=null;
    public static String ShanxiFlow(String productId) throws Exception {
        if (Shanxi_liuliang == null) {
            Shanxi_liuliang = new Hashtable<String, String>();
            //移动
            Shanxi_liuliang.put("100018", "202073");//2M
            Shanxi_liuliang.put("100001", "202074");//10M
            Shanxi_liuliang.put("100002", "202209");//30M
            Shanxi_liuliang.put("100003", "202766");//70M
            Shanxi_liuliang.put("100004", "204919");//100M
            Shanxi_liuliang.put("100005", "202210");//150M
            Shanxi_liuliang.put("100006", "204947");//300M
            Shanxi_liuliang.put("100007", "202075");//500M
            Shanxi_liuliang.put("100017", "202076");//700M
            Shanxi_liuliang.put("100008", "202211");//1G
            Shanxi_liuliang.put("100009", "202077");//2G
            Shanxi_liuliang.put("100010", "202212");//3G
            Shanxi_liuliang.put("100011", "202078");//4G
            Shanxi_liuliang.put("100012", "202767");//6G
            Shanxi_liuliang.put("100013", "202213");//11G
            //联通
            Shanxi_liuliang.put("200001", "202600");//20M
            Shanxi_liuliang.put("200003", "202601");//50M
            Shanxi_liuliang.put("200004", "202598");//100M
            Shanxi_liuliang.put("200005", "202599");//200M
            Shanxi_liuliang.put("200007", "202702");//500M
            Shanxi_liuliang.put("200008", "202701");//1024M
            //电信
            Shanxi_liuliang.put("300002", "202295");//10M
            Shanxi_liuliang.put("300003", "202439");//30M
            Shanxi_liuliang.put("300005", "202296");//100M
            Shanxi_liuliang.put("300006", "202440");//200M
            Shanxi_liuliang.put("300008", "202441");//500M
            Shanxi_liuliang.put("300009", "202297");//1024M
        }
        return (String) Shanxi_liuliang.get(productId);
    }
    //陕西
    public static Hashtable<String, String> ShanxiWest_liuliang=null;
    public static String ShanxiWestFlow(String productId) throws Exception {
        if (ShanxiWest_liuliang == null) {
            ShanxiWest_liuliang = new Hashtable<String, String>();
            //移动
            ShanxiWest_liuliang.put("100018", "202011");//2M
            ShanxiWest_liuliang.put("100001", "202172");//10M
            ShanxiWest_liuliang.put("100002", "202012");//30M
            ShanxiWest_liuliang.put("100003", "202173");//70M
            ShanxiWest_liuliang.put("100004", "204928");//100M
            ShanxiWest_liuliang.put("100005", "202013");//150M
            ShanxiWest_liuliang.put("100006", "204958");//300M
            ShanxiWest_liuliang.put("100007", "202014");//500M
            ShanxiWest_liuliang.put("100017", "202174");//700M
            ShanxiWest_liuliang.put("100008", "202015");//1G
            ShanxiWest_liuliang.put("100009", "202175");//2G
            ShanxiWest_liuliang.put("100010", "202016");//3G
            ShanxiWest_liuliang.put("100011", "202176");//4G
            ShanxiWest_liuliang.put("100012", "202017");//6G
            ShanxiWest_liuliang.put("100013", "202018");//11G
            //联通
            ShanxiWest_liuliang.put("200001", "202736");//20M
            ShanxiWest_liuliang.put("200003", "202675");//50M
            ShanxiWest_liuliang.put("200004", "202672");//100M
            ShanxiWest_liuliang.put("200005", "202673");//200M
            ShanxiWest_liuliang.put("200007", "202674");//500M
            ShanxiWest_liuliang.put("200008", "202735");//1024M
            //电信
            ShanxiWest_liuliang.put("300002", "202516");//10M
            ShanxiWest_liuliang.put("300003", "202860");//30M
            ShanxiWest_liuliang.put("300005", "202823");//100M
            ShanxiWest_liuliang.put("300006", "202861");//200M
            ShanxiWest_liuliang.put("300008", "202517");//500M
            ShanxiWest_liuliang.put("300009", "202862");//1024M
        }
        return (String) ShanxiWest_liuliang.get(productId);
    }
    //上海
    public static Hashtable<String, String> Shanghai_liuliang=null;
    public static String ShanghaiFlow(String productId) throws Exception {
        if (Shanghai_liuliang == null) {
            Shanghai_liuliang = new Hashtable<String, String>();
            //移动
            Shanghai_liuliang.put("100018", "202048");//2M
            Shanghai_liuliang.put("100001", "202049");//10M
            Shanghai_liuliang.put("100002", "202799");//30M
            Shanghai_liuliang.put("100003", "202760");//70M
            Shanghai_liuliang.put("100004", "202883");//100M
            Shanghai_liuliang.put("100005", "202050");//150M
            Shanghai_liuliang.put("100006", "204971");//300M
            Shanghai_liuliang.put("100007", "202886");//500M
            Shanghai_liuliang.put("100017", "202052");//700M
            Shanghai_liuliang.put("100008", "202761");//1G
            Shanghai_liuliang.put("100009", "202800");//2G
            Shanghai_liuliang.put("100010", "202053");//3G
            Shanghai_liuliang.put("100011", "202054");//4G
            Shanghai_liuliang.put("100012", "202055");//6G
            Shanghai_liuliang.put("100013", "202762");//11G
            //联通
            Shanghai_liuliang.put("200001", "202554");//20M
            Shanghai_liuliang.put("200003", "202881");//50M
            Shanghai_liuliang.put("200004", "202879");//100M
            Shanghai_liuliang.put("200005", "202880");//200M
            Shanghai_liuliang.put("200007", "202555");//500M
            Shanghai_liuliang.put("200008", "202553");//1024M
            //电信
            Shanghai_liuliang.put("300002", "202874");//10M
            Shanghai_liuliang.put("300003", "202780");//30M
            Shanghai_liuliang.put("300004", "202781");//50M
            Shanghai_liuliang.put("300005", "202875");//100M
            Shanghai_liuliang.put("300006", "202783");//200M
            Shanghai_liuliang.put("300008", "202876");//500M
            Shanghai_liuliang.put("300009", "202785");//1024M
        }
        return (String) Shanghai_liuliang.get(productId);
    }
    //四川
    public static Hashtable<String, String> Sichuan_liuliang=null;
    public static String SichuanFlow(String productId) throws Exception {
        if (Sichuan_liuliang == null) {
            Sichuan_liuliang = new Hashtable<String, String>();
            //移动
            Sichuan_liuliang.put("100018", "202152");//2M
            Sichuan_liuliang.put("100001", "201989");//10M
            Sichuan_liuliang.put("100002", "202153");//30M
            Sichuan_liuliang.put("100003", "201990");//70M
            Sichuan_liuliang.put("100004", "204916");//100M
            Sichuan_liuliang.put("100005", "202749");//150M
            Sichuan_liuliang.put("100006", "204944");//300M
            Sichuan_liuliang.put("100007", "202154");//500M
            Sichuan_liuliang.put("100017", "202791");//700M
            Sichuan_liuliang.put("100008", "202155");//1G
            Sichuan_liuliang.put("100009", "201991");//2G
            Sichuan_liuliang.put("100010", "201992");//3G
            Sichuan_liuliang.put("100011", "202156");//4G
            Sichuan_liuliang.put("100012", "202792");//6G
            Sichuan_liuliang.put("100013", "202157");//11G
            //联通
            Sichuan_liuliang.put("200001", "202582");//20M
            Sichuan_liuliang.put("200003", "202583");//50M
            Sichuan_liuliang.put("200004", "202580");//100M
            Sichuan_liuliang.put("200005", "202689");//200M
            Sichuan_liuliang.put("200007", "202690");//500M
            Sichuan_liuliang.put("200008", "202581");//1024M
            //电信
            Sichuan_liuliang.put("300002", "202850");//10M
            Sichuan_liuliang.put("300003", "202504");//30M
            Sichuan_liuliang.put("300005", "202505");//100M
            Sichuan_liuliang.put("300006", "202851");//200M
            Sichuan_liuliang.put("300008", "202506");//500M
            Sichuan_liuliang.put("300009", "202852");//1024M
        }
        return (String) Sichuan_liuliang.get(productId);
    }
    //天津
    public static Hashtable<String, String> Tianjin_liuliang=null;
    public static String TianjinFlow(String productId) throws Exception {
        if (Tianjin_liuliang == null) {
            Tianjin_liuliang = new Hashtable<String, String>();
            //移动
            Tianjin_liuliang.put("100018", "202200");//2M
            Tianjin_liuliang.put("100001", "202801");//10M
            Tianjin_liuliang.put("100002", "202815");//30M
            Tianjin_liuliang.put("100003", "202056");//70M
            Tianjin_liuliang.put("100004", "204917");//100M
            Tianjin_liuliang.put("100005", "202057");//150M
            Tianjin_liuliang.put("100006", "204945");//300M
            Tianjin_liuliang.put("100007", "202201");//500M
            Tianjin_liuliang.put("100017", "202058");//700M
            Tianjin_liuliang.put("100008", "202202");//1G
            Tianjin_liuliang.put("100009", "202763");//2G
            Tianjin_liuliang.put("100010", "202802");//3G
            Tianjin_liuliang.put("100011", "202203");//4G
            Tianjin_liuliang.put("100012", "202059");//6G
            Tianjin_liuliang.put("100013", "202204");//11G
            //联通
            Tianjin_liuliang.put("200001", "202692");//20M
            Tianjin_liuliang.put("200003", "202587");//50M
            Tianjin_liuliang.put("200004", "202584");//100M
            Tianjin_liuliang.put("200005", "202585");//200M
            Tianjin_liuliang.put("200007", "202586");//500M
            Tianjin_liuliang.put("200008", "202691");//1024M
            //电信
            Tianjin_liuliang.put("300002", "202542");//10M
            Tianjin_liuliang.put("300003", "202872");//30M
            Tianjin_liuliang.put("300005", "202543");//100M
            Tianjin_liuliang.put("300006", "202873");//200M
            Tianjin_liuliang.put("300008", "202544");//500M
            Tianjin_liuliang.put("300009", "202545");//1024M
        }
        return (String) Tianjin_liuliang.get(productId);
    }
    //西藏
    public static Hashtable<String, String> Xizang_liuliang=null;
    public static String XizangFlow(String productId) throws Exception {
        if (Xizang_liuliang == null) {
            Xizang_liuliang = new Hashtable<String, String>();
            //移动
            Xizang_liuliang.put("100018", "202168");//2M
            Xizang_liuliang.put("100001", "202002");//10M
            Xizang_liuliang.put("100002", "202169");//30M
            Xizang_liuliang.put("100003", "202003");//70M
            Xizang_liuliang.put("100004", "204938");//100M
            Xizang_liuliang.put("100005", "202004");//150M
            Xizang_liuliang.put("100006", "204968");//300M
            Xizang_liuliang.put("100007", "202005");//500M
            Xizang_liuliang.put("100017", "202006");//700M
            Xizang_liuliang.put("100008", "202007");//1G
            Xizang_liuliang.put("100009", "202008");//2G
            Xizang_liuliang.put("100010", "202170");//3G
            Xizang_liuliang.put("100011", "202009");//4G
            Xizang_liuliang.put("100012", "202171");//6G
            Xizang_liuliang.put("100013", "202010");//11G
            //联通
            Xizang_liuliang.put("200001", "202728");//20M
            Xizang_liuliang.put("200003", "202729");//50M
            Xizang_liuliang.put("200004", "202727");//100M
            Xizang_liuliang.put("200005", "202657");//200M
            Xizang_liuliang.put("200007", "202658");//500M
            Xizang_liuliang.put("200008", "202656");//1024M
            //电信
            Xizang_liuliang.put("300002", "202512");//10M
            Xizang_liuliang.put("300003", "202513");//30M
            Xizang_liuliang.put("300005", "202858");//100M
            Xizang_liuliang.put("300006", "202514");//200M
            Xizang_liuliang.put("300008", "202859");//500M
            Xizang_liuliang.put("300009", "202515");//1024M
        }
        return (String) Xizang_liuliang.get(productId);
    }
    //新疆
    public static Hashtable<String, String> Xinjiang_liuliang=null;
    public static String XinjiangFlow(String productId) throws Exception {
        if (Xinjiang_liuliang == null) {
            Xinjiang_liuliang = new Hashtable<String, String>();
            //移动
            Xinjiang_liuliang.put("100018", "202191");//2M
            Xinjiang_liuliang.put("100001", "202039");//10M
            Xinjiang_liuliang.put("100002", "202192");//30M
            Xinjiang_liuliang.put("100003", "202040");//70M
            Xinjiang_liuliang.put("100004", "204922");//100M
            Xinjiang_liuliang.put("100005", "202193");//150M
            Xinjiang_liuliang.put("100006", "204950");//300M
            Xinjiang_liuliang.put("100007", "202041");//500M
            Xinjiang_liuliang.put("100017", "202796");//700M
            Xinjiang_liuliang.put("100008", "202813");//1G
            Xinjiang_liuliang.put("100009", "202757");//2G
            Xinjiang_liuliang.put("100010", "202194");//3G
            Xinjiang_liuliang.put("100011", "202042");//4G
            Xinjiang_liuliang.put("100012", "202043");//6G
            Xinjiang_liuliang.put("100013", "202195");//11G
            //联通
            Xinjiang_liuliang.put("200001", "202619");//20M
            Xinjiang_liuliang.put("200003", "202706");//50M
            Xinjiang_liuliang.put("200004", "202616");//100M
            Xinjiang_liuliang.put("200005", "202618");//200M
            Xinjiang_liuliang.put("200007", "202620");//500M
            Xinjiang_liuliang.put("200008", "202617");//1024M
            //电信
            Xinjiang_liuliang.put("300002", "202535");//10M
            Xinjiang_liuliang.put("300003", "202867");//30M
            Xinjiang_liuliang.put("300005", "202536");//100M
            Xinjiang_liuliang.put("300006", "202868");//200M
            Xinjiang_liuliang.put("300008", "202537");//500M
            Xinjiang_liuliang.put("300009", "202538");//1024M
        }
        return (String) Xinjiang_liuliang.get(productId);
    }
    //云南
    public static Hashtable<String, String> Yunnan_liuliang=null;
    public static String YunnanFlow(String productId) throws Exception {
        if (Yunnan_liuliang == null) {
            Yunnan_liuliang = new Hashtable<String, String>();
            //移动
            Yunnan_liuliang.put("100018", "201998");//2M
            Yunnan_liuliang.put("100001", "202163");//10M
            Yunnan_liuliang.put("100002", "202752");//30M
            Yunnan_liuliang.put("100003", "202164");//70M
            Yunnan_liuliang.put("100004", "204913");//100M
            Yunnan_liuliang.put("100005", "202794");//150M
            Yunnan_liuliang.put("100006", "204941");//300M
            Yunnan_liuliang.put("100007", "202165");//500M
            Yunnan_liuliang.put("100017", "201999");//700M
            Yunnan_liuliang.put("100008", "202000");//1G
            Yunnan_liuliang.put("100009", "202166");//2G
            Yunnan_liuliang.put("100010", "202001");//3G
            Yunnan_liuliang.put("100011", "202167");//4G
            Yunnan_liuliang.put("100012", "202753");//6G
            Yunnan_liuliang.put("100013", "202795");//11G
            //联通
            Yunnan_liuliang.put("200001", "202558");//20M
            Yunnan_liuliang.put("200003", "202559");//50M
            Yunnan_liuliang.put("200004", "202556");//100M
            Yunnan_liuliang.put("200005", "202557");//200M
            Yunnan_liuliang.put("200007", "202684");//500M
            Yunnan_liuliang.put("200008", "202683");//1024M
            //电信
            Yunnan_liuliang.put("300002", "202855");//10M
            Yunnan_liuliang.put("300003", "202510");//30M
            Yunnan_liuliang.put("300005", "202511");//100M
            Yunnan_liuliang.put("300006", "202856");//200M
            Yunnan_liuliang.put("300008", "202822");//500M
            Yunnan_liuliang.put("300009", "202857");//1024M
        }
        return (String) Yunnan_liuliang.get(productId);
    }
    //浙江
    public static Hashtable<String, String> Zhejiang_liuliang=null;
    public static String ZhejiangFlow(String productId) throws Exception {
        if (Zhejiang_liuliang == null) {
            Zhejiang_liuliang = new Hashtable<String, String>();
            //移动
            Zhejiang_liuliang.put("100018", "202107");//2M
            Zhejiang_liuliang.put("100001", "202235");//10M
            Zhejiang_liuliang.put("100002", "201882");//30M
            Zhejiang_liuliang.put("100003", "201883");//70M
            Zhejiang_liuliang.put("100004", "201880");//100M
            Zhejiang_liuliang.put("100005", "202134");//150M
            Zhejiang_liuliang.put("100006", "204953");//300M
            Zhejiang_liuliang.put("100007", "201885");//500M
            Zhejiang_liuliang.put("100017", "201886");//700M
            Zhejiang_liuliang.put("100008", "201887");//1G
            Zhejiang_liuliang.put("100009", "201888");//2G
            Zhejiang_liuliang.put("100010", "201889");//3G
            Zhejiang_liuliang.put("100011", "201890");//4G
            Zhejiang_liuliang.put("100012", "201891");//6G
            Zhejiang_liuliang.put("100013", "201892");//11G
            //联通
            Zhejiang_liuliang.put("200001", "202637");//20M
            Zhejiang_liuliang.put("200003", "202638");//50M
            Zhejiang_liuliang.put("200004", "202635");//100M
            Zhejiang_liuliang.put("200005", "202717");//200M
            Zhejiang_liuliang.put("200007", "202718");//500M
            Zhejiang_liuliang.put("200008", "202636");//1024M
            //电信
            Zhejiang_liuliang.put("300002", "202465");//10M
            Zhejiang_liuliang.put("300003", "202466");//30M
            Zhejiang_liuliang.put("300005", "202467");//100M
            Zhejiang_liuliang.put("300006", "202468");//200M
            Zhejiang_liuliang.put("300008", "202469");//500M
            Zhejiang_liuliang.put("300009", "202470");//1024M
        }
        return (String) Zhejiang_liuliang.get(productId);
    }
    //重庆
    public static Hashtable<String, String> Chongqing_liuliang=null;
    public static String ChongqingFlow(String productId) throws Exception {
        if (Chongqing_liuliang == null) {
            Chongqing_liuliang = new Hashtable<String, String>();
            //移动
            Chongqing_liuliang.put("100018", "202060");//2M
            Chongqing_liuliang.put("100001", "202764");//10M
            Chongqing_liuliang.put("100002", "202061");//30M
            Chongqing_liuliang.put("100003", "202803");//70M
            Chongqing_liuliang.put("100004", "204939");//100M
            Chongqing_liuliang.put("100005", "202062");//150M
            Chongqing_liuliang.put("100006", "204969");//300M
            Chongqing_liuliang.put("100007", "202303");//500M
            Chongqing_liuliang.put("100017", "202304");//700M
            Chongqing_liuliang.put("100008", "202305");//1G
            Chongqing_liuliang.put("100009", "202306");//2G
            Chongqing_liuliang.put("100010", "202307");//3G
            Chongqing_liuliang.put("100011", "202308");//4G
            Chongqing_liuliang.put("100012", "202309");//6G
            Chongqing_liuliang.put("100013", "202310");//11G
            //联通
            Chongqing_liuliang.put("200001", "202670");//20M
            Chongqing_liuliang.put("200003", "202734");//50M
            Chongqing_liuliang.put("200004", "202732");//100M
            Chongqing_liuliang.put("200005", "202733");//200M
            Chongqing_liuliang.put("200007", "202671");//500M
            Chongqing_liuliang.put("200008", "202669");//1024M
            //电信
            Chongqing_liuliang.put("300002", "202549");//10M
            Chongqing_liuliang.put("300003", "202877");//30M
            Chongqing_liuliang.put("300005", "202550");//100M
            Chongqing_liuliang.put("300006", "202878");//200M
            Chongqing_liuliang.put("300008", "202551");//500M
            Chongqing_liuliang.put("300009", "202552");//1024M
        }
        return (String) Chongqing_liuliang.get(productId);
    }
}

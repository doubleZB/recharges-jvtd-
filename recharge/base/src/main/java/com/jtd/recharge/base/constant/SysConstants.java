package com.jtd.recharge.base.constant;


import com.jtd.recharge.base.util.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jipengkun
 */
public class SysConstants {


    public static class Queue {
        public static String SUBMIT_QUEUE = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_QUEUE");
        public static String SUBMIT_SLOW_QUEUE = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_SLOW_QUEUE");
        public static String REPORT_QUEUE = PropertiesUtils.loadProperties("config.properties").getProperty("REPORT_QUEUE");
        public static String DIRECT_QUEUE = PropertiesUtils.loadProperties("config.properties").getProperty("DIRECT_QUEUE");
        public static String PUSH_QUEUE = PropertiesUtils.loadProperties("config.properties").getProperty("PUSH_QUEUE");
        public static String CACHE_QUEUE = PropertiesUtils.loadProperties("config.properties").getProperty("CACHE_QUEUE");
        public static String SUBMIT_QUERE_ALONE = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_QUERE_ALONE");
        public static String SUBMIT_QUERE_ALONE_LIST = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_QUERE_ALONE_LIST");
        public static String SUBMIT_THREAD_NUM = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_THREAD_NUM");
        public static String SUBMIT_ALONE_NUM = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_ALONE_NUM");
        public static String SUBMIT_ALONE_LIST_NUM = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_ALONE_LIST_NUM");
        public static String SUBMIT_SLOW_THREAD_NUM = PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_SLOW_THREAD_NUM");
        public static String REPORT_THREAD_NUM = PropertiesUtils.loadProperties("config.properties").getProperty("REPORT_THREAD_NUM");
        public static String PUSH_THREAD_NUM = PropertiesUtils.loadProperties("config.properties").getProperty("PUSH_THREAD_NUM");
        public static String CACHE_THREAD_NUM=PropertiesUtils.loadProperties("config.properties").getProperty("CACHE_THREAD_NUM");
        public static String ALONE_SUPPLY_MAX_NUM=PropertiesUtils.loadProperties("config.properties").getProperty("ALONE_SUPPLY_MAX_NUM");
        public static String ALONE_SUPPLY_LIST_MAX_NUM=PropertiesUtils.loadProperties("config.properties").getProperty("ALONE_SUPPLY_LIST_MAX_NUM");

        //开关
        public static String REPORT_QUEUE_SWITCH=PropertiesUtils.loadProperties("config.properties").getProperty("REPORT_QUEUE_SWITCH");
        public static String SUBMIT_ALONE_SWITCH=PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_ALONE_SWITCH");
        public static String PUSH_QUEUE_SWITCH=PropertiesUtils.loadProperties("config.properties").getProperty("PUSH_QUEUE_SWITCH");
        public static String SUBMIT_QUEUE_SWITCH=PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_QUEUE_SWITCH");
        public static String SUBMIT_SLOW_QUEUE_SWITCH=PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_SLOW_QUEUE_SWITCH");
        public static String CACHE_QUEUE_SWITCH=PropertiesUtils.loadProperties("config.properties").getProperty("CACHE_QUEUE_SWITCH");
        public static String SUBMIT_QUERE_ALONE_LIST_SWITCH=PropertiesUtils.loadProperties("config.properties").getProperty("SUBMIT_QUERE_ALONE_LIST_SWITCH");

    }

    public static long TIMELONGHT = 1000;//等待时长

    public static String SUCCESS = "1000";//处理成功
    public static String FAIL = "2000";//处理失败

    public static Map<Integer,String> businessTypeMap = null;//业务类型map
    public static Map<Integer,String> operatorMap = null;//运营商map
    public static Map<Integer,String> scopeMap = null;//生效范围map
    public static Map<Integer,String> validity_timeMap = null;//有效期map
    public static Map<Integer,String> effect_timeMap = null;//生效时间map
    public static Map<Integer,String> storeStstusMap = null;//货架状态map
    public static Map<Integer,String> send_typeMap = null;//匹配原则map
    public static Map<String,String> alone_mao = null;//单通道map-只绑定一个IP

    static {
        send_typeMap = new HashMap<>();
        send_typeMap.put(1,"指定渠道");
        send_typeMap.put(2,"系统匹配");

        storeStstusMap = new HashMap<>();
        storeStstusMap.put(1,"上架");
        storeStstusMap.put(2,"下架");
        storeStstusMap.put(3,"永久下架");

        effect_timeMap = new HashMap<>();
        effect_timeMap.put(1,"即时生效");
        effect_timeMap.put(2,"次月生效");


        scopeMap = new HashMap<>();
        scopeMap.put(1,"全国");
        scopeMap.put(2,"省内");

        validity_timeMap = new HashMap<>();
        validity_timeMap.put(1,"当月");
        validity_timeMap.put(2,"1个月");
        validity_timeMap.put(3,"3个月");
        validity_timeMap.put(4,"6个月");

        businessTypeMap = new HashMap<>();
        businessTypeMap.put(1,"流量");
        businessTypeMap.put(2,"话费");
        businessTypeMap.put(3,"视频会员");

        operatorMap = new HashMap<>();
        operatorMap.put(1,"移动");
        operatorMap.put(2,"联通");
        operatorMap.put(3,"电信");
        operatorMap.put(4,"优酷");
        operatorMap.put(5,"爱奇艺");
        operatorMap.put(6,"腾讯");
        operatorMap.put(7,"搜狐");
        operatorMap.put(8,"乐视");

        alone_mao=new HashMap<>();
        alone_mao.put("flowzuowangHBJT","true");
        alone_mao.put("flowzuowangHBJD","true");
        alone_mao.put("flowzuowangHBJDXC","true");
    }

    public static class UserStatus {
        public static String open = "1";//开启
        public static String close = "0";//关闭
    }

    /**
     * 业务类型 1流量 2话费
     */
    public static class BusinessType {
        public static int flow = 1;//流量
        public static int telbill = 2;//话费
        public static int videoVip = 3;//视频会员
    }

    /**
     * 来源
     */
    public static class Source {
        public static int gateway = 1;
        public static int page = 2;
    }


    /**
     * 来源
     */
    public static class ChannelStatus {
        public static int open = 1;
        public static int close =3;
    }

    /**
     * 开关
     */
    public static class Switch {
        public static int NO = 1;//开启
        public static int OFF =2;//关闭
    }

//    public static String PHONE_SUM = PropertiesUtils.loadProperties("config.properties").getProperty("PHONE_SUM");
    public static String PHONE_SUM_FLOW = PropertiesUtils.loadProperties("config.properties").getProperty("PHONE_SUM_FLOW");
    public static String PHONE_SUM_TELBILL = PropertiesUtils.loadProperties("config.properties").getProperty("PHONE_SUM_TELBILL");

    //用户余额缓存KEY前缀
    public  static String USER_BLANCE="user_blance_";
    //缓存-商户KEY前缀-流量
    public static String CACHE_USER_FLOW_KEY=PropertiesUtils.loadProperties("config.properties").getProperty("CACHE_USER_FLOW");
    //缓存-商户KEY前缀-话费
    public static String CACHE_USER_TEL_KEY=PropertiesUtils.loadProperties("config.properties").getProperty("CACHE_USER_TEL");
    //缓存-供应商KEY前缀
    public static String CACHE_SUPPLY_KEY=PropertiesUtils.loadProperties("config.properties").getProperty("CACHE_SUPPLY");
    //特殊通道-商户-submit
    public static String TIME_USER_SUBMIT="_time_submit";
    //特殊通道-商户-report
    public static String TIME_USER="_time";
    //特殊通道-供应商-submit
    public static String TIME_SUPPLY_SUBMIT="_supply_time";

}

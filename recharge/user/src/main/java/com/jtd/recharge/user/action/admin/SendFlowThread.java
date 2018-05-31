package com.jtd.recharge.user.action.admin;



import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.PropertiesUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lyp  on 2017/1/12.
 */
public class SendFlowThread implements  Runnable {

    private static Properties prop = PropertiesUtils.loadProperties("config.properties");
    private Log log = LogFactory.getLog(this.getClass());
    List<String[]> arrayList=null;
    private String remark="";

    public SendFlowThread(List<String[]> list ,String remark){
        arrayList=list;
        this.remark=remark;
    }

    public void run(){
        log.info("send flow");
        if(arrayList.size()>0){
            int i=0;
                        for (String[] arrayStr: arrayList){
                            try {
                                i=i+1;
                                log.info("执行次数---" + i);
                                String token = arrayStr[2];//token
                                String mobile = arrayStr[0];
                                String customId = ""+System.currentTimeMillis();//商户订单号
                                String code = arrayStr[1];//档位编码
                                String urlName = arrayStr[3];//流量或者话费地址或者视频会员
                                String callbackUrl = "http://116.62.28.7:8090/test/callback";//回调url
                                String source = "2";//页面充值标识
                                log.info("获取参数完毕---" + i);
                                Map paramMap = new HashMap();
                                paramMap.put("mobile", mobile);
                                paramMap.put("customId", customId);
                                paramMap.put("code", code);
                                paramMap.put("callbackUrl", callbackUrl);
                                paramMap.put("sign", DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
                                paramMap.put("token", token);
                                paramMap.put("source", source);
                                if("videoRechargeUrl".equals(urlName)){
                                    String operator= arrayStr[4];
                                    paramMap.put("operator", operator);
                                }
                                paramMap.put("remark", remark);
                                log.info("封装参数完毕---" + i);
                                String url = prop.getProperty(urlName);
                                log.info("获取路径---" + url);
                                log.info("page batch request---" + JSON.toJSONString(paramMap));

                                long statrTime= System.currentTimeMillis();
                                log.info("获取路径---" + url);

                                String contenet= HttpTookit.doPostParam(url, paramMap, "utf-8");
                                log.info("page batch send request---" + contenet);

                                long endStirng = System.currentTimeMillis();
                                long result=endStirng-statrTime;
                                log.info("运行时间---" + result);


                                //Thread.sleep(1000);
                            }catch (Exception e) {
                                e.printStackTrace();
                               // log.error("页面批量轮询线程异常",e);
                                continue;
                            }
                        }
        }
    }
}

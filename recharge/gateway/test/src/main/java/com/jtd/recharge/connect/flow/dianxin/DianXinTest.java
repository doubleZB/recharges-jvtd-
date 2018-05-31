package com.jtd.recharge.connect.flow.dianxin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpTookit;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/15.
 */
public class DianXinTest {
    private static final String host = "http://118.85.207.90:9100/osa/interface";
    private static final String sysCode = "S20440";
    private static final String appCode = "A01009";
    private static final String version = "1";
    private static final String attach = "hello,189.cn";
    private static final String method = "CTEC-01012.3.1";
    public static void main(String[] args) throws Exception{
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String reqTime = sdf.format(new Date());
//        SimpleDateFormat sdf2 = new SimpleDateFormat("YYMMddHHMM");
//        UUID uuid = UUID.randomUUID();
//        String transactionId = sysCode+appCode+sdf2.format(new Date())+ uuid;//报文流水号
//        String sign  = DigestUtils.md5Hex(transactionId+"5d23a80050d649b69d544290fb1825e1");
//
//        HashMap<String , String> head=new HashMap<String,String>();
//        head.put("sysCode",sysCode);
//        head.put("appCode",appCode);
//        head.put("transactionId",transactionId);
//        head.put("reqTime",reqTime);
//        head.put("method",method);
//        head.put("version",version);
//        head.put("attach",attach);
//        head.put("sign",sign);
//        String headObejct= JSON.toJSONString(head);
//
//
//        String phoneNumber = "13311221762";
//        String prodOfferCode = "7240110001000007";
//        String serialnum = "12";
//        String merchantOrderId =System.currentTimeMillis()+"";
//        HashMap<String ,String > biz = new HashMap<String ,String>();
//        biz.put("phoneNumber",phoneNumber);
//        biz.put("prodOfferCode",prodOfferCode);
//        biz.put("serialnum",serialnum);
//        biz.put("merchantOrderId",merchantOrderId);
//        String bizObejct= JSON.toJSONString(biz);
//
//        HashMap map=new HashMap();
//        map.put("head",headObejct);
//        map.put("biz",bizObejct);
//        String Object= JSON.toJSONString(map);
//        String resultContent = "";
//        resultContent = HttpTookit.doPost(host,Object);
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);

    }


}

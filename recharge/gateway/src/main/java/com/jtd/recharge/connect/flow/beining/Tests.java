package com.jtd.recharge.connect.flow.beining;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.bean.ChargeSubmitResponse;

import java.util.HashMap;

/**
 * Created by liyabin on 2017/8/31.
 */
public class Tests {
     public  static  void main(String args[]) throws Exception {
         String username = "17610088085";
         String action = "charge";
         String host = "http://api.rd.yunpaas.cn/return/flow/beining";
         String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq1bT4BIqgnprbg1IHo3+HrHIjw0dG0gs+H8Fq/Vi9Rq08nUbtXCGB26z8g7aw2EjrOvVcj5ur1KHfUxY9lNpW2kJgG7bPhJksi6X/gBnrRCF17BI74akN1++S0PJVcK9FEkPV0aV3MdU26w/D9+KZuqdL0IJ81h8/M2pB8kfilQzMcfRCGlkWofecajSmL8XJ4Nf69W63q2FMl8Vw9iGjobf9fe5uEDHskJdLz4PThfYNHihmmZKiqhvxFVigP9IeMCO82EJJLQeWGVJkpv7WylEpksomSkIi3cmXnqOmwxkMvU5rNIXm48XT02EM9rN0WxIFTfERib8nB27InkbkQIDAQAB";
         String ss = "{\"password\":\"507F513353702B50C145D5B7D138095C\",\"orderlist\":[{\"productcode\":\"100100101000030\",\"orderid\":\"c2017083116503231543417\",\"mobile\":\"18635941576\"}],\"callbackurl\":\"http://api.rd.yunpaas.cn/return/flow/beining\"}";
         String data = RSA.encrypt(ss.getBytes(), publicKey);
         HashMap map = new HashMap();
         map.put("action", action);
         map.put("username", username);
         map.put("data", data);
         String postString = JSONObject.toJSONString(map);

         /**
          * 提交消息
          */
         ChargeSubmitResponse response = new ChargeSubmitResponse();
         String resultContent = HttpClients.doPost(host, postString, "application/x-www-form-urlencoded");
         System.out.print(resultContent);
     }
}

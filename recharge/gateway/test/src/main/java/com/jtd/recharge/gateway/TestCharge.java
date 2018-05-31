package com.jtd.recharge.gateway;


import com.jtd.recharge.base.util.HttpTookit;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 流量充值
 */
public class TestCharge {

    @Test
    public void testFlowCharge() throws Exception {
        String token = "3b7c265333f249309bb8c19ebfb8278f";//token
        //String mobile = "13810962241";//移动
        String mobile = "18692296461";//联通
        //String mobile = "13331086792";//电信
        String customId = "20180628140111";//商户订单号
        String code = "200003";//档位编码
        String callbackUrl = "http://way.10044.cn/recharge/callback?appKey=724aeebf40e4467e9bec71390ab6f0c9&sprID=68";//回调url


        Map paramMap = new HashMap();
        paramMap.put("mobile", mobile);
        paramMap.put("customId", customId);
        paramMap.put("code", code);
        paramMap.put("callbackUrl", callbackUrl);
        paramMap.put("sign", DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
        System.out.println("sign" + DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
        paramMap.put("remark", "聚通达");
        paramMap.put("token", token);


        String url = "http://api.yunpaas.cn/gateway/flow/charge";
        //String url = "http://localhost:8080/gateway/flow/charge";
//        String url = "http://api.rd.yunpaas.cn/gateway/flow/charge";

        //for(int i=0;i<11;i++) {
        String contenet = HttpTookit.doPostParam(url, paramMap, "utf-8");
        System.out.println("云通信流量网关返回---" + contenet);
    //}
    }

    @Test
    public void testTelbillCharge() throws Exception {
        String token = "8f9147a6ebd949579078cf732f61116d";//token
        String mobile = "13810962243";//移动
        //String mobile = "18612877701";//联通
        //String mobile = "13331086792";//电信
        String customId = "2219926efc2441702";//商户订单号
        String code = "400010";//档位编码
        String callbackUrl = "http://116.62.28.7:8090/test/callback";//回调url


        Map paramMap = new HashMap();
        paramMap.put("mobile", mobile);
        paramMap.put("customId", customId);
        paramMap.put("code", code);
        paramMap.put("callbackUrl", callbackUrl);
        paramMap.put("sign", DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
        System.out.println("sign" + DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
        paramMap.put("token", token);


        //String url = "http://api.yunpaas.cn/gateway/telbill/charge";
        //String url = "http://api.rd.yunpaas.cn/gateway/telbill/charge";
        String url = "http://localhost:8080/gateway/telbill/charge";

        String contenet = HttpTookit.doPostParam(url, paramMap, "utf-8");
        System.out.println("云通信话费网关返回---" + contenet);

    }

}

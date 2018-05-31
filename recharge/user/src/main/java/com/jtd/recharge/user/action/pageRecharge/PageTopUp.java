package com.jtd.recharge.user.action.pageRecharge;

import com.jtd.recharge.base.util.HttpTookit;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/12/27.
 */
@Controller
@RequestMapping("/pageTopUp")
public class PageTopUp{




    public void pageTopUp(String phonenumber,String key,String coded ) throws Exception {
        String token = key;//token
        String mobile = phonenumber;//将充值的手机号
        String customId = "2219926efc24417098759969d2b22636";//商户订单号
        String code = coded;//档位编码
        String callbackUrl = "api.yunpaas.cn/Test/callback";//回调url(必填)


        Map paramMap = new HashMap();
        paramMap.put("mobile", mobile);
        paramMap.put("customId", customId);
        paramMap.put("code", code);
        paramMap.put("callbackUrl", callbackUrl);
        paramMap.put("sign", DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
        System.out.println("sign" + DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
        paramMap.put("token", token);


        //String url = "http://api.rd.yunpaas.cn/gateway/flow/charge";
        String url = "http://localhost:8080/gateway/flow/charge";
        String contenet = HttpTookit.doPostParam(url, paramMap, "utf-8");
        System.out.println("云通信流量网关返回---" + contenet);

    }

}

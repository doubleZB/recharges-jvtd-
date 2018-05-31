<%--
  Date: 2017/4/13
  Time: 14:32
  To change this template use File | Settings | File Templates.
  代码描述
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>云通信</title>
    <link rel="stylesheet" href="${path}/static/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/static/css/common.css">
    <link rel="stylesheet" href="${path}/static/css/amazeui.min.css">
    <link rel="stylesheet" href="${path}/static/css/paraiso.light.css">

    <style>
        body {
            background: none;
            font-size: 14px;
        }

        p {
            margin: 0;
            padding: 0;
        }

        .z_list {
            background: #fff;
            padding: 10px 25px;
        }

        .z_list li {
            line-height: 40px;
            font-size: 12px;
            color: #666;
        }

        .z_list li span {
            margin-right: 10px;
        }

        .z_list li span i {
            margin-right: 5px;
        }

    </style>
</head>

<body>

<div class="container" style="padding:23px;">

    <div class="row">
        <div class="am-u-lg-12" style="padding:0;">
            <div class="am-u-lg-12 m-box m-market" style="padding:0;background:#eee;">
                <ul class="z_list">
                    <li>代码描述：流量直充接口,流量查询接口,状态回调接口</li>
                </ul>
                    <pre>
                        <code>
package com.jtd.example.flow;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowSubmit {

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(30000)
                .setConnectTimeout(30000)
                .setSocketTimeout(30000).build();

        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * 流量直充接口
     * @throws Exception
     */
    @Test
    public void testFlowSubmit() throws Exception {
        String token = "6ed5cdddf3ce4847aedb22b024394c1f";//token
        String mobile = "13811111111";//将充值的手机号
        String customId = "2219926efc24417098759969d2b22636";//商户订单号
        String code = "100001";//档位编码
        String callbackUrl = "http://255.255.255.255/test/callback";//回调url 需要修改


        Map paramMap = new HashMap();
        paramMap.put("mobile",mobile);
        paramMap.put("customId",customId);
        paramMap.put("code",code);
        paramMap.put("callbackUrl",callbackUrl);
        paramMap.put("sign",DigestUtils.md5Hex(token+mobile+customId+code+callbackUrl));
        System.out.println("sign"+DigestUtils.md5Hex(token+mobile+customId+code+callbackUrl));
        paramMap.put("token",token);


        String url = "http://api.rd.yunpaas.cn/gateway/flow/charge";

        String contenet = doPostParam(url, paramMap, "utf-8");//使用httpclient post

        System.out.println("云通信流量网关返回---"+contenet);

    }

    /**
     * 订单状态查询
     * @throws Exception
     */
    @Test
    public void testFlowQuery() throws Exception {
        String token = "6ed5cdddf3ce4847aedb22b024394c1f";//token
        String orderNum = "6ed5cdddf3ce4847aedb22b024394c1f";//将充值的手机号


        Map paramMap = new HashMap();
        paramMap.put("orderNum",orderNum);
        paramMap.put("token",token);


        String url = "http://api.yunpaas.cn/gateway/flow/queryOrder";

        String contenet = doPostParam(url, paramMap, "utf-8");//使用httpclient post

        System.out.println("云通信流量网关返回---"+contenet);

    }

    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params	请求的参数
     * @param charset	编码格式
     * @return	页面内容
     */
    public static String doPostParam(String url,Map<String,String> params,String charset) throws Exception {
        System.out.println("请求url---" + url);
        if (StringUtils.isBlank(url)) {
            return null;
        }

        List<NameValuePair> pairs = null;
        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        HttpPost httpPost = new HttpPost(url);
        if (pairs != null && pairs.size() > 0) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

}

                        </code>
                    </pre>

                <pre>
                    <code>
package com.jtd.example.flow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test")
public class FlowCallback {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 状态回调接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/callback")
    @ResponseBody
    public String flowRecharge(HttpServletRequest request, HttpServletResponse response) {
        String mobile =  request.getParameter("mobile");
        String status = request.getParameter("status");
        String customId = request.getParameter("customId");
        String sign = request.getParameter("sign");

        System.out.println("/test/callback-----------");

        System.out.println("mobile---" + mobile);
        System.out.println("status---" + status);
        System.out.println("customId---" + customId);
        System.out.println("sign---" + sign);


        /**
         * 业务逻辑
         */
        return "SUCCESS";
    }
}

                    </code>
                </pre>
            </div>

        </div>

    </div>
</div>
<script src="${path}/static/js/jquery.min.js"></script>
<script src="${path}/static/js/amazeui.min.js"></script>
<script src="${path}/static/js/highlight.pack.js"></script>
<script>
    hljs.initHighlightingOnLoad();
</script>
</body>
</html>

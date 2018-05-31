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
                    <li>代码描述：流量查询</li>
                </ul>
                    <pre>
                        <code>
package com.jtd.recharge.iot.gateway.action;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.redis.core.script.DigestUtils;

import com.jtd.recharge.base.util.HttpTookit;

public class IotQueryTest {
	public static void main(String[] args) {
		//String host = "http://localhost:8080/iot-gateway/iotQuery/usages";
		String host = "http://manager.iot.yunpaas.cn:9008/iotQuery/usages";
		try {

			Map<String, String> params = new TreeMap<>();
			String token = "xxxxx";
			params.put("token", token);
			params.put("iccid", "xxx");
			params.put("msisdn", "xxx");
			StringBuilder buffer = new StringBuilder(token);
			for (Map.Entry<String, String> entry : params.entrySet()) {
				buffer.append(entry.getKey()).append(entry.getValue());
			}
			buffer.append(token);
			String sign = DigestUtils.sha1DigestAsHex(buffer.toString());
			params.put("sign", sign);
			String result = HttpTookit.doGet(host, params);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

                        </code>
                    </pre>

                <%--<pre>--%>
                    <%--<code>--%>
<%--package com.jtd.example.flow;--%>

<%--import org.apache.commons.logging.Log;--%>
<%--import org.apache.commons.logging.LogFactory;--%>
<%--import org.springframework.stereotype.Controller;--%>
<%--import org.springframework.web.bind.annotation.RequestMapping;--%>
<%--import org.springframework.web.bind.annotation.ResponseBody;--%>

<%--import javax.servlet.http.HttpServletRequest;--%>
<%--import javax.servlet.http.HttpServletResponse;--%>

<%--@Controller--%>
<%--@RequestMapping("/test")--%>
<%--public class FlowCallback {--%>

    <%--private Log log = LogFactory.getLog(this.getClass());--%>

    <%--/**--%>
     <%--* 状态回调接口--%>
     <%--* @param request--%>
     <%--* @param response--%>
     <%--* @return--%>
     <%--*/--%>
    <%--@RequestMapping("/callback")--%>
    <%--@ResponseBody--%>
    <%--public String flowRecharge(HttpServletRequest request, HttpServletResponse response) {--%>
        <%--String mobile =  request.getParameter("mobile");--%>
        <%--String status = request.getParameter("status");--%>
        <%--String customId = request.getParameter("customId");--%>
        <%--String sign = request.getParameter("sign");--%>

        <%--System.out.println("/test/callback-----------");--%>

        <%--System.out.println("mobile---" + mobile);--%>
        <%--System.out.println("status---" + status);--%>
        <%--System.out.println("customId---" + customId);--%>
        <%--System.out.println("sign---" + sign);--%>


        <%--/**--%>
         <%--* 业务逻辑--%>
         <%--*/--%>
        <%--return "SUCCESS";--%>
    <%--}--%>
<%--}--%>

                    <%--</code>--%>
                <%--</pre>--%>
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

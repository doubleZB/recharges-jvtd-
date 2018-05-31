package com.jtd.recharge.connect.flow.zuowang;

import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.connect.video.SupplyConfig;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 卓望流量充值回执处理
 */
@Controller
@RequestMapping("return")
public class ZuoWangFlowReport {

    private final Logger logger = LoggerFactory.getLogger(ZuoWangFlowReport.class);
    private Map supplyMap = (Map) SupplyConfig.getConfig().get("flowzuowang");

    @RequestMapping(value = "/flow/zhuowang")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("卓望流量充值回执参数: {}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));
        String mobile = request.getParameter("mobile");
        String status = request.getParameter("status");
        String customId = request.getParameter("customId");


        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("srcTransId", customId);
        paramMap.put("customerId", String.valueOf(supplyMap.get("user_id")));
        paramMap.put("mobile", mobile);
        if ("7".equals(status)) {
            paramMap.put("resultCode", "00000");
            paramMap.put("resultMsg", "充值成功");
        } else {
            paramMap.put("resultCode", "9999");
            paramMap.put("resultMsg", "充值失败");
        }
        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = paramMap.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            if (i>0) sb.append("&");
            sb.append(list.get(i)).append("=").append(paramMap.get(list.get(i)));
        }
        sb.append(String.valueOf(supplyMap.get("key")));
        paramMap.put("sign", DigestUtils.md5Hex(sb.toString()));

        try {
            String result = HttpTookit
                    .doPost(String.valueOf(supplyMap.get("callback_url")),
                            JSONObject.fromObject(paramMap).toString());
            logger.info("卓望响应: {}", result);

            JSONObject resultObj = JSONObject.fromObject(result);
            response.getWriter().print(String.valueOf(resultObj.get("status")).toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("fail");
        }
    }

}

package com.jtd.recharge.connect.flow.zuowang;

import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.connect.video.SupplyConfig;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 卓望流量充值
 */
@Controller
@RequestMapping(value = "/flow")
public class ZuoWangFlowRequest {

    private final Logger logger = LoggerFactory.getLogger(ZuoWangFlowRequest.class);
    private Map supplyMap = (Map) SupplyConfig.getConfig().get("flowzuowang");

    @ResponseBody
    @RequestMapping(value = "/zhuowang/charge")
   public JSONObject request(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject){
        logger.info("卓望充值请求参数 getParameterMap: {}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));
        logger.info("卓望充值请求参数: {}", jsonObject);
        JSONObject result = new JSONObject();
        if (Checking(jsonObject)){
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", "90b5eaa389d940e48cb78dc870c21c7e");
            paramMap.put("mobile", jsonObject.getString("mobile"));
            paramMap.put("customId", jsonObject.getString("srcTransId"));
            paramMap.put("callbackUrl", String.valueOf(supplyMap.get("report_callback_url")));
            paramMap.put("code", jsonObject.getString("srcPrdCode"));
            StringBuffer sb = new StringBuffer();
            sb.append(paramMap.get("token")).append(paramMap.get("mobile"))
                    .append(paramMap.get("customId")).append(paramMap.get("code"))
                    .append(paramMap.get("callbackUrl"));
            paramMap.put("sign", DigestUtils.md5Hex(sb.toString()));

            try {
                JSONObject resultJson =
                        JSONObject.fromObject(HttpTookit.doPost(String.valueOf(supplyMap.get("host")), paramMap));
                logger.info("卓望: 充值云通信请求结果: {}", resultJson);
                if ("1000".equals(resultJson.get("statusCode"))) {
                    result.put("resultCode", "00000");
                    result.put("resultMsg", "提交成功");
                } else {
                    result.put("resultCode", "99999");
                    result.put("resultMsg", resultJson.getString("statusMsg"));
                }
            } catch (Exception e) {
                logger.info("系统异常，错误信息{}", e.getMessage());
                result.put("resultCode", "89999");
                result.put("resultMsg", e.getMessage());
            }
        } else {
            result.put("resultCode", "99999");
            result.put("resultMsg", "验签失败");
        }
        return result;
   }

   @ResponseBody
   @RequestMapping(value = "queryOrder")
   public JSONObject queryOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        logger.info("卓望查询订单, 请求参数: {}", jsonObject);
        logger.info("卓望查询订单, 请求参数: getParameterMap{}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));
        JSONObject resultJson = new JSONObject();

       Map<String, String> paramMap = new HashMap<>();
       paramMap.put("token", String.valueOf(supplyMap.get("token")));
       paramMap.put("customId", jsonObject.getString("srcTransId"));
       paramMap.put("phone", jsonObject.getString("mobile"));

       try {
           JSONObject result =
                    JSONObject.fromObject(HttpTookit.doPost(String.valueOf(supplyMap.get("query_host")), paramMap));
           logger.info("卓望:请求平台查询充值结果 {}", result);
            if ("1".equals(result.get("status"))) {
                resultJson.put("resultCode", "6".equals(result.get("statusCode")) ? "00001"
                        : "7".equals(result.get("statusCode")) ? "00000" : "99999");
                resultJson.put("resultMsg", result.get("statusMsg"));
            } else {
                resultJson.put("resultCode", "99999");
                resultJson.put("resultMsg", result.get("statusMsg"));
            }
       } catch (Exception e) {
           logger.info("系统异常，错误信息{}", e.getMessage());
           resultJson.put("resultCode", "89999");
           resultJson.put("resultMsg", e.getMessage());
       }

       return resultJson;
   }

    /**
     * 对单层json进行key字母排序
     * @param json
     * @return
     */
    private static JSONObject getSortJson(JSONObject json){
        Iterator<String> iteratorKeys = json.keys();
        SortedMap map = new TreeMap();
        while (iteratorKeys.hasNext()) {
            String key = iteratorKeys.next().toString();
            String vlaue = json.optString(key);
            map.put(key, vlaue);
        }
        JSONObject json2 = JSONObject.fromObject(map);
        return json2;
    }

    private boolean Checking(JSONObject jsonObject) {
        JSONObject object = getSortJson(jsonObject);
        object.remove("sign"); // 移除sign
        logger.info("卓望验签: 排序后的请求参数: {}", object);

        Iterator<String> iteratorKeys = object.keys();
        StringBuffer sb = new StringBuffer();
        while (iteratorKeys.hasNext()) {
            String key = iteratorKeys.next();
            sb.append("&").append(key).append("=").append(object.get(key));
        }
        sb.replace(0,1, "");
        sb.append(String.valueOf(supplyMap.get("key"))); // key
        logger.info("卓望验签: 代签名字符串: {}, 签名后字符串: {}", sb.toString(), DigestUtils.md5Hex(sb.toString()));

        return jsonObject.get("sign").equals(DigestUtils.md5Hex(sb.toString()));
    }
}

package com.jtd.recharge.connect.flow.dongmeng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by lhm on 2017/11/30.
 * 中国—东盟信息港股份有限公司
 */
@Service
public class DongMengFlowRequest   implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---东盟信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String effecttype = (String) supplyMap.get("effecttype");
        String platformid = (String) supplyMap.get("platformid");
        String security = (String) supplyMap.get("security");
        String cburl = (String) supplyMap.get("callback_url");
        String mobile = chargeRequest.getMobile();
        String productid = chargeRequest.getPositionCode();
        String channelNum = chargeRequest.getChannelNum();
        String timestamp=Long.toString((long) (System.currentTimeMillis()));

        String strtmpproduct = DesUtil.encrypt(productid, security);
        String strtmpmobile = DesUtil.encrypt(mobile, security);
        String struserno = platformid+channelNum;

        Map<String, String> cloudmap = new HashMap<String,String>();
        cloudmap.put("mobile", mobile);
        cloudmap.put("userorderno", struserno);
        cloudmap.put("timestamp", timestamp);
        cloudmap.put("productid", productid);
        cloudmap.put("platformid", platformid);
        cloudmap.put("security", security);
        String sourceStr = createLinkString(cloudmap);
        String sign = DigestUtils.md5Hex(sourceStr);

        JsonObject jo = new JsonObject();
        jo.addProperty("mobile", strtmpmobile);
        jo.addProperty("effecttype", effecttype);
        jo.addProperty("userorderno", struserno);
        jo.addProperty("timestamp", timestamp);
        jo.addProperty("productid", strtmpproduct);
        jo.addProperty("sign", sign);
        jo.addProperty("cburl", URLEncoder.encode(cburl));

        log.info("8、发送流程：发送供应商---东盟信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ jo.toString());

        ChargeSubmitResponse response = new ChargeSubmitResponse();

        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(host);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //			connection.setDoInput(true);
            connection.setDoOutput(true);
            String tmpplatformid = "platformid="+"\""+platformid+"\"";
            connection.setRequestProperty("Authorization", tmpplatformid);
            connection.setRequestProperty("Accept",  "application/json;charset=UTF-8");
            connection.setRequestProperty("Content-Type","application/json");

            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jo.toString());
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String responseLine = "";

            while((responseLine = reader.readLine()) != null) {
                stringBuffer.append(new String(responseLine.getBytes()));
            }
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---东盟信息----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到东盟信息！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---东盟信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + stringBuffer.toString()+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        String resultContent = stringBuffer.toString();
        JSONObject object=JSON.parseObject(resultContent);
        String ret_code=object.getString("resultcode");
        String msg=object.getString("resultdescription");
        if(ret_code.equals("00000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(channelNum);
            log.info("8、发送流程：发送供应商---东盟信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到东盟信息成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(ret_code);
            response.setStatusMsg(msg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---东盟信息----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到东盟信息失败！原因："+msg+"请咨询供应商！");
        }
        return response;
    }

    public static  String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
}

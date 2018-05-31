package com.jtd.recharge.connect.flow.baishitong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.Ascii;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.flow.baimiaojunye.DesUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyabin on 2017/9/27.
 */
public class BaiShiTongFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        ChargeSubmitResponse response = new ChargeSubmitResponse();

        Long timestamp =System.currentTimeMillis()/1000L;
        String host = (String) supplyMap.get("host");//充值地址
        String account = (String) supplyMap.get("account");//account
        String apiKey = (String) supplyMap.get("apiKey");//appKey
        String range = (String) supplyMap.get("range");//appKey
        String action = (String) supplyMap.get("action");//回调地址
        String mobile = chargeRequest.getMobile();//充值手机号
        String productid = chargeRequest.getPositionCode();//充值流量代码
        String outerTid = chargeRequest.getChannelNum();//订单号

        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> mapSgin = new HashMap<String, String>();
        try {
            map.put("account", account);
            map.put("action", action);
            map.put("phone", mobile);
            map.put("size", productid);
            map.put("range", range);
            map.put("timeStamp", String.valueOf(timestamp));
            map.put("orderNo",outerTid);

            mapSgin.put("account", account);
            mapSgin.put("action", action);
            mapSgin.put("phone", mobile);
            mapSgin.put("size", productid);
            mapSgin.put("range", range);
            mapSgin.put("timeStamp", String.valueOf(timestamp));

            String sign = Ascii.formatUrlMap(mapSgin, false);
            log.info("8、发送流程---发送供应商-百事通流量:"+apiKey+sign+apiKey);
            map.put("sign",DigestUtils.md5Hex(apiKey+sign+apiKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonMap = Ascii.formatUrlMap(map, false);

        log.info("8、发送流程---发送供应商-百事通流量--mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 封装应用参数json报文请求参数---" + jsonMap);

        /**
         * 提交消息
         */
        String resultha;
        try {
            resultha =  HttpTookit.doPostParam(host,map,"UTF-8");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商--百事通流量----mobile=" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交到百事通流量！原因：" + e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage() + "请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + "  发送供应商--百事通流量---" + jsonMap + "   ------返回数据：" + JSON.toJSONString(resultha));
        JSONObject object=JSON.parseObject(resultha);
        String respCode=object.getString("respCode");
        String respMsg=object.getString("respMsg");
        if(respCode.equals("0000")){
            String orderId=object.getString("orderID");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderId);
            log.info("8、发送流程：发送供应商--百事通----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到百事通成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respMsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商--百事通----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到百事通失败！原因："+respMsg+"请咨询供应商！");
        }


        return response;
    }

}

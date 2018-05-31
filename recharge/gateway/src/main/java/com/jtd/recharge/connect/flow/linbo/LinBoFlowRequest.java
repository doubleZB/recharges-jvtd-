package com.jtd.recharge.connect.flow.linbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @autor lhm
 * 琳博流量 充值
 */
@Service
public class LinBoFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---琳博流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String user = (String) supplyMap.get("user");
        String key = (String) supplyMap.get("key");
        String mobile= chargeRequest.getMobile();
        String productId = chargeRequest.getPositionCode();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String userReqNo = chargeRequest.getChannelNum();
        String nonce= Base64.encodeBase64String((user+":"+timestamp).getBytes());
        String sign= DigestUtils.md5Hex(user+key+timestamp);
        String Authorization="sign=\""+sign+"\",nonce=\""+nonce+"\"";
        log.info("8、验证头："+Authorization);
        Map param = new HashMap();
        param.put("mobile",mobile);
        param.put("productId",productId);
        param.put("userReqNo",userReqNo);

        log.info("8、发送流程：发送供应商---琳博流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent =HttpTookit.doPostParamLinBo(host,param,Authorization);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---琳博流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到琳博流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---琳博流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("status");
        String message=object.getString("message");
        if("10000".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(userReqNo);
            log.info("8、发送流程：发送供应商---琳博流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到琳博流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(message+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---琳博流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到琳博流量失败！原因："+message+"请咨询供应商！");
        }
        return response;
    }

}

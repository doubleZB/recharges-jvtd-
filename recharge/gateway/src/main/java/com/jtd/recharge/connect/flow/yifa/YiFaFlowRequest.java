package com.jtd.recharge.connect.flow.yifa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 义发流量 on 2017/7/21
 * lhm
 */
@Service
public class YiFaFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        log.info("8、发送流程--发送供应商---义发流量---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String account = (String) supplyMap.get("account");
        String action = (String) supplyMap.get("action");
        String apiKey = (String) supplyMap.get("apiKey");
        String v = (String) supplyMap.get("v");
        String mobile = chargeRequest.getMobile();
        String code = chargeRequest.getPositionCode();

        String orderID = chargeRequest.getChannelNum();

        String sign= DigestUtils.md5Hex("account="+account+"&mobile="+mobile+"&package="+code+"&key="+apiKey);

        Map param = new HashMap();
        param.put("account",account);
        param.put("action",action);
        param.put("v",v);
        param.put("mobile",mobile);
        param.put("package",code);
        param.put("sign",sign);
        param.put("OutTradeNo",orderID);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---义发流量； 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---义发流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到义发流量流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---义发流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("Code");
        String Message=object.getString("Message");
        if(respCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到义发流量成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(Message);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到义发流量失败！原因："+Message);
        }
        return response;
    }

}

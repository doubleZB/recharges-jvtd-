package com.jtd.recharge.connect.flow.weiwang;

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
 * @autor jipengkun
 * 微网 流量 充值
 */
@Service
public class WeiWangFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String apiKey = (String) supplyMap.get("apiKey");
        String host = (String) supplyMap.get("host");
        String outTradeNo = chargeRequest.getChannelNum();
        String account = (String) supplyMap.get("account");
        String mobile = chargeRequest.getMobile();
        String packageSize = chargeRequest.getPackageSize();

        String sign= DigestUtils.md5Hex("account="+account+"&mobile="+mobile+"&package="+packageSize+"&key="+apiKey);


        Map param = new HashMap();
        param.put("Action","charge");
        param.put("V","1.1");
        param.put("Range","0");
        param.put("OutTradeNo",outTradeNo);
        param.put("Account",account);
        param.put("Mobile",mobile);
        param.put("Package",packageSize);
        param.put("Sign",sign);

        log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---微网----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("Code");
        if(respCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setChannelNum(chargeRequest.getChannelNum());
            response.setStatusMsg(respCode+"请咨询供应商！");
            response.setStatusCode(respCode);
            log.info("8、发送流程：发送供应商---微网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到微网失败！原因："+respCode+"请咨询供应商！");
        }
        return response;
    }
}

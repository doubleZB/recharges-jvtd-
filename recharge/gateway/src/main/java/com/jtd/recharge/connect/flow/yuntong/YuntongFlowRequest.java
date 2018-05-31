package com.jtd.recharge.connect.flow.yuntong;

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
 * @autor
 * 云通通信
 */
@Service
public class YuntongFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程：发送供应商---云通通信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String apiKey = (String) supplyMap.get("apiKey");
        String account = (String) supplyMap.get("account");
        String action = "Charge";
        String phone = chargeRequest.getMobile();
        String size = chargeRequest.getPackageSize();
        String range = "0";
        String timeStamp = new Long(System.currentTimeMillis()).toString();
        String orderNo = chargeRequest.getChannelNum();

        String sign= DigestUtils.md5Hex(apiKey+"account="+account+"&action="+action+"&phone="+phone+"&range="+range+"&size="+size+"&timeStamp="+timeStamp+apiKey);


        Map param = new HashMap();
        param.put("account",account);
        param.put("action",action);
        param.put("phone",phone);
        param.put("size",size);
        param.put("range",range);
        param.put("timeStamp",timeStamp);
        param.put("orderNo",orderNo);
        param.put("sign",sign);

        log.info("8、发送流程：发送供应商---云通通信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---云通通信----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云通通信！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---云通通信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("respCode");
        if(respCode.equals("0000")){
            String orderID=object.getString("orderID");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---云通通信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云通通信成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---云通通信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云通通信失败！"+respCode+"请咨询供应商！");

        }
        return response;
    }
}

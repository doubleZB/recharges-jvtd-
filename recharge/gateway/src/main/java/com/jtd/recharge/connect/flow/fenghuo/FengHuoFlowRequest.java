package com.jtd.recharge.connect.flow.fenghuo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/9/25.
 *
 * 烽火流量
 */
@Service
public class FengHuoFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---烽火流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String userCode = (String) supplyMap.get("userCode");
        String SecretKey = (String) supplyMap.get("SecretKey");

        String transId =new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() * (1000 - 100) + 100);
        String phoneNum = chargeRequest.getMobile();
        String cardCode = chargeRequest.getPositionCode();
        String channelOrderId = chargeRequest.getChannelNum();
        int openType=1;

        String ss=cardCode+channelOrderId+openType+phoneNum+transId+userCode+SecretKey;

        log.info("8、发送流程：发送供应商---烽火流量----ss "+ss);

        String sign=DigestUtils.md5Hex(cardCode+channelOrderId+openType+phoneNum+transId+userCode+SecretKey);

        log.info("8、发送流程：发送供应商---烽火流量----sign "+sign);

        Map<String, Object> param = new HashMap();
        param.put("cardCode", cardCode);
        param.put("channelOrderId", channelOrderId);
        param.put("openType", openType);
        param.put("phoneNum", phoneNum);
        param.put("transId", transId);
        param.put("userCode", userCode);
        param.put("sign", sign);


        log.info("8、发送流程：发送供应商---烽火流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(param));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---烽火流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到烽火流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---烽火流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String ret_code=object.getString("rspCode");
        String errMsg=object.getString("rspMsg");
        if(ret_code.equals("P00000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(channelOrderId);
            log.info("8、发送流程：发送供应商---烽火流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到烽火流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(ret_code);
            response.setStatusMsg(errMsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---烽火流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到烽火流量失败！原因："+errMsg+"请咨询供应商！");
        }
        return response;
    }

}
